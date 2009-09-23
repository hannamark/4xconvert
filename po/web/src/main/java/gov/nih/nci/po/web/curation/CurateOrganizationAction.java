package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.service.CurateEntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.EJBException;
import javax.jms.JMSException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.FastDateFormat;
import org.hibernate.exception.ExceptionUtils;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle curation of Organization entities.
 */
public class CurateOrganizationAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1L;
    /**
     * The action execution was successful. Show result view to the end user.
     */
    public static final String CURATE_RESULT = "curate";
    /**
     * The action execution was successful. Show result view to the end user.
     */
    public static final String CHANGE_CURRENT_CHANGE_REQUEST_RESULT = "changeCurrentChangeRequest";
    /**
     * Default date time format.
     */
    static final FastDateFormat DEFAULT_CHANGEREQUEST_VALUE_DATE_FORMAT = FastDateFormat
            .getInstance("yyyy/MM/dd HH:MM:ss");

    private Organization organization = new Organization();
    private String rootKey;
    private OrganizationCR cr = new OrganizationCR();
    private Organization duplicateOf = new Organization();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            organization = (Organization) getSession().getAttribute(getRootKey());
        }

        findAndSetCr(cr.getId());
    }

    private HttpSession getSession() {
        return PoHttpSessionUtil.getSession();
    }

    /**
     * @return show start page
     */
    public String start() {
        organization = PoRegistry.getOrganizationService().getById(organization.getId());
        initializeCollections(organization);
        initializeCtepRoles(organization);
        setRootKey(PoHttpSessionUtil.addAttribute(organization));
        if (!organization.getChangeRequests().isEmpty()) {
            cr = organization.getChangeRequests().iterator().next();
        }
        findAndSetCr(cr.getId());
        return CURATE_RESULT;
    }
    
    private void initializeCtepRoles(Organization org) {
      //need this for duplicate validation check
      org.getHealthCareFacilities().size();
      org.getResearchOrganizations().size();
      org.isAssociatedWithCtepRoles();
      
    }

    private void initializeCollections(Contactable contactable) {
        contactable.getEmail().size();
        contactable.getFax().size();
        contactable.getPhone().size();
        contactable.getTty().size();
        contactable.getUrl().size();
    }

    /**
     * Curate method w/ struts validation.
     * @return success
     * @throws JMSException if an error occurred while publishing the announcement
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "organization"),
            @CustomValidator(type = "duplicateOfNullifiedOrg", fieldName = "duplicateOf", 
                    message = "A duplicate Organization must be provided.")
            })
    public String curate() throws JMSException {
        // PO-1196 - We are using a struts validator to make sure that when an org with associated ctep roles
        // is nullified, it must have a duplicateOf set. The reason we are using a struts validator instead of a 
        // hibernate validator is the comment below.
        // PO-1098 - for some reason, the duplicate of wasn't getting set properly by struts when we tried to
        // set organization.duplicateOf.id directly, so we're setting it manually
        if (duplicateOf != null && duplicateOf.getId() != null) {
            getOrganization().setDuplicateOf(duplicateOf);
        }

        try {
            PoRegistry.getOrganizationService().curate(getOrganization());
        } catch (EJBException e) {
            /*
             * we are catching the EJBExcetion and then interrogating it for the root cause and if the root cause is
             * what we expect then we'll throw the root cause. Next, our custom result exception-mapping within our
             * action mapping will ultimately redirects to our curateError.jsp page. NOTE: We are redirecting and
             * passing the organization and duplicateOf as request params to ensure that a separate HB Session is used
             * since the current HB Session is dirty and has validation errors within it. Also, we know that doing so 
             * will abandon any changes made by the user to the Organization but, is of little concern in this case 
             * because the Organization was being NULLIFIED.
             */
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            if (rootCause instanceof CurateEntityValidationException) {
                throw (CurateEntityValidationException) rootCause;
            }
            throw e;
        }

        ActionHelper.saveMessage(getText("organization.curate.success"));
        return SUCCESS;
    }

    /**
     * @return org to curate
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param org to curate
     */
    public void setOrganization(Organization org) {
        this.organization = org;
    }

    /**
     * 
     * @return the session key of the root object (org or person)
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     * 
     * @param rootKey the session key of the root object.
     */
    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
    }

    /**
     * @return active change request
     */
    public OrganizationCR getCr() {
        return cr;
    }

    /**
     * @param cr active change request
     */
    public void setCr(OrganizationCR cr) {
        this.cr = cr;
    }

    private void findAndSetCr(Long id) {
        if (id != null) {
            this.cr = PoRegistry.getGenericService().getPersistentObject(OrganizationCR.class, id);
        }
    }

    /**
     * @return success
     */
    public String changeCurrentChangeRequest() {
        findAndSetCr(getCr().getId());
        return CHANGE_CURRENT_CHANGE_REQUEST_RESULT;
    }

    /**
     * @return the list of entries for the select CR pull-down
     */
    public Map<String, String> getSelectChangeRequests() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        Set<OrganizationCR> unprocessedChangeRequests = organization.getChangeRequests();
        for (OrganizationCR changeRequest : unprocessedChangeRequests) {
            treeMap.put(changeRequest.getId().toString(), "CR-ID-" + changeRequest.getId().toString());
        }
        return treeMap;
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotHealthCareFacilityCount() {
        HealthCareFacilityServiceLocal service = PoRegistry.getInstance().getServiceLocator()
                .getHealthCareFacilityService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotResearchOrganizationCount() {
        ResearchOrganizationServiceLocal service = PoRegistry.getInstance().getServiceLocator()
                .getResearchOrganizationService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotIdentifiedOrganizationCount() {
        IdentifiedOrganizationServiceLocal service = PoRegistry.getInstance().getServiceLocator()
                .getIdentifiedOrganizationService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotOversightCommitteeCount() {
        OversightCommitteeServiceLocal service = PoRegistry.getInstance().getServiceLocator()
                .getOversightCommitteeService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotOrganizationalContactCount() {
        OrganizationalContactServiceLocal service = PoRegistry.getInstance().getServiceLocator()
                .getOrganizationalContactService();
        return service.getScoperHotRoleCount(organization);
    }

    /**
     * @return the duplicateOf
     */
    public Organization getDuplicateOf() {
        return duplicateOf;
    }

    /**
     * @param duplicateOf the duplicateOf to set
     */
    public void setDuplicateOf(Organization duplicateOf) {
        this.duplicateOf = duplicateOf;
    }
}
