package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.FastDateFormat;

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
        setRootKey(PoHttpSessionUtil.addAttribute(organization));
        if (!organization.getChangeRequests().isEmpty()) {
            cr = organization.getChangeRequests().iterator().next();
        }
        findAndSetCr(cr.getId());
        return CURATE_RESULT;
    }

    private void initializeCollections(Contactable contactable) {
        contactable.getEmail().size();
        contactable.getFax().size();
        contactable.getPhone().size();
        contactable.getTty().size();
        contactable.getUrl().size();
    }

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing the announcement
     */
    @Validations(customValidators = {
                @CustomValidator(type = "hibernate",
                    fieldName = "organization"
                )
            })
    public String curate() throws JMSException {
        // PO-1098 - for some reason, the duplicate of wasn't getting set properly by struts when we tried to
        // set organization.duplicateOf.id directly, so we're setting it manually
        if (duplicateOf != null && duplicateOf.getId() != null) {
            getOrganization().setDuplicateOf(duplicateOf);
        }
        PoRegistry.getOrganizationService().curate(getOrganization());
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
        HealthCareFacilityServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getHealthCareFacilityService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotResearchOrganizationCount() {
        ResearchOrganizationServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getResearchOrganizationService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotIdentifiedOrganizationCount() {
        IdentifiedOrganizationServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getIdentifiedOrganizationService();
        return service.getHotRoleCount(organization);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotOversightCommitteeCount() {
        OversightCommitteeServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getOversightCommitteeService();
        return service.getHotRoleCount(organization);
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
