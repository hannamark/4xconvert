package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Comment;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.ScopedRole;
import gov.nih.nci.po.service.CurateEntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;
import gov.nih.nci.po.web.util.validator.Addressable;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.EJBException;
import javax.jms.JMSException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle curation of Organization entities.
 * @author Rohit Gupta
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity" })
public class CurateOrganizationAction extends AbstractPoAction implements Addressable, Preparable {
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
    private String comments;
    private boolean showNewStatusField = true;

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
      
      if (org.isAssociatedWithCtepRoles()) {
          showNewStatusField = false;
      }      
    }

    private void initializeCollections(Organization org) {
        org.getEmail().size();
        org.getFax().size();
        org.getPhone().size();
        org.getTty().size();
        org.getUrl().size();
        org.getComments().size();
        org.getPostalAddress().getCountry().getStates().size();
    }

    /**
     * Curate method w/ struts validation.
     * 
     * @return success
     * @throws JMSException
     *             if an error occurred while publishing the announcement
     * @throws CSException
     *             CSException
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "organization"),
            @CustomValidator(type = "duplicateOfNullifiedOrg", fieldName = "duplicateOf",
                    message = "A duplicate Organization must be provided."),
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "organization.phone",
                    message = "US and Canadian telephone numbers must match ###-###-####(x#*).") ,
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "organization.fax",
                    message = "US and Canadian fax numbers must match ###-###-####(x#*)."),
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "organization.tty",
                    message = "US and Canadian tty numbers must match ###-###-####(x#*).")
            })
    public String curate() throws JMSException, CSException {
        // PO-1196 - We are using a struts validator to make sure that when an org with associated ctep roles
        // is nullified, it must have a duplicateOf set. The reason we are using a struts validator instead of a
        // hibernate validator is the comment below.
        // PO-1098 - for some reason, the duplicate of wasn't getting set properly by struts when we tried to
        // set organization.duplicateOf.id directly, so we're setting it manually
        if (duplicateOf != null && duplicateOf.getId() != null) {
            getOrganization().setDuplicateOf(duplicateOf);
        }
        addCommentToOrg();
        try {
            if (isCRProcessingRequired(getOrganization())) {
                PoRegistry.getOrganizationService().curate(getOrganization()); 
            } else {
                PoRegistry.getOrganizationService().curateWithoutCRProcessing(getOrganization()); 
            }
            
        } catch (EJBException e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            /*
             * we are catching the EJBException and then interrogating it for the root cause and if the root cause is
             * what we expect then we'll throw the root cause. Next, our custom result exception-mapping within our
             * action mapping will ultimately redirects to our curateError.jsp page. NOTE: We are redirecting and
             * passing the organization and duplicateOf as request params to ensure that a separate HB Session is used
             * since the current HB Session is dirty and has validation errors within it. Also, we know that doing so
             * will abandon any changes made by the user to the Organization but, is of little concern in this case
             * because the Organization was being NULLIFIED.
             */
            Throwable rootCause = ExceptionUtils.getRootCause(e);            
            if (rootCause instanceof CurateEntityValidationException) {                
                final CurateEntityValidationException ex = (CurateEntityValidationException) rootCause;                
                LOG.error(ToStringBuilder.reflectionToString(ex.getErrors()));
                storeAsActionMessages(ex);
                throw ex;
            } else if (rootCause instanceof InvalidStateException) {
                final InvalidStateException ex = (InvalidStateException) rootCause;                
                LOG.error(ToStringBuilder.reflectionToString(ex.getInvalidValues()));
                storeAsActionMessages(ex);
                throw new CurateEntityValidationException(new HashMap<String, String[]>()); // NOPMD
            }
            throw e;
        }

        ActionHelper.saveMessage(getText("organization.curate.success"));
        return SUCCESS;
    }

    /**
     * @param ex InvalidStateException
     */
    @SuppressWarnings("rawtypes")
    void storeAsActionMessages(InvalidStateException ex) {
        for (InvalidValue iv : ex.getInvalidValues()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Message: " + iv.getMessage());
            sb.append(", ");
            sb.append("Entity/Role: "
                    + (iv.getRootBean() != null ? iv.getRootBean().getClass()
                            .getSimpleName() : "N/A"));
            sb.append(", ");
            if (iv.getRootBean() instanceof PersistentObject) {
                sb.append("Entity/Role ID: "
                        + ((PersistentObject) iv.getRootBean()).getId());
                sb.append(", ");
            }
            if (iv.getRootBean() instanceof ScopedRole) {
                sb.append("Scoper Organization ID: "
                        + ((ScopedRole) iv.getRootBean()).getScoper().getId());
                sb.append(", ");
            }
            if (iv.getRootBean() instanceof PlayedRole) {
                sb.append("Player ID: "
                        + ((PlayedRole) iv.getRootBean()).getPlayer().getId());
                sb.append(", ");
            }
            sb.append("Value with error: " + iv.getValue());
            sb.append(". ");
            addActionError(sb.toString());
            ActionHelper.saveMessage(sb.toString());
        }
    }

    /**
     * Override method.
     * @return curate
     * @throws JMSException JMSException
     * @throws CSException JMSException
     */
    public String override() throws JMSException, CSException {
        organization = PoRegistry.getOrganizationService().getById(organization.getId());      
        User overriddenBy = getLoggedInUser();
        PoRegistry.getOrganizationService().override(organization, overriddenBy);
        
        return start();
    }
    
    private void storeAsActionMessages(CurateEntityValidationException ex) {
        Map<String, String[]> errors = ex.getErrors();
        if (errors != null) {
            for (String[] errArray : errors.values()) {
                if (errArray != null) {
                    for (String msg : errArray) {
                        addActionError(msg);
                        ActionHelper.saveMessage(msg);
                    }
                }
            }
        }
    }

    private void addCommentToOrg() throws CSException {
        if (StringUtils.isNotBlank(comments)) {
            Comment c = new Comment();
            c.setCreateDate(new Date());           
            c.setValue(comments);
            c.setUser(SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(ServletActionContext.getRequest().getRemoteUser()));
            getOrganization().getComments().add(c);
        }
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
        PoHttpSessionUtil.validateSessionKey(rootKey);
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
     * @return success
     */
    public String removeCR() {
        PoRegistry.getOrganizationService().removeChangeRequest(getCr());   
        ActionHelper.saveMessage(getText("organization.removeCR.success")); 
        return start();
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

    /**
     * {@inheritDoc}
     */
    public boolean isUsOrCanadaFormat() {
        return this.organization.isUsOrCanadaAddress();
    }

    /**
     * Method for pulling this value in struts xml.
     * @return the organization id as a string.
     */
    public String getOrganizationId() {
        if (getOrganization() != null && getOrganization().getId() != null) {
            return this.getOrganization().getId().toString();
        }
        return "";
    }
    
    /**
     * Method for pulling this value in struts xml.
     * @return the duplicate id as a string.
     */
    public String getDuplicateOfId() {
        if (getDuplicateOf() != null && getDuplicateOf().getId() != null) {
            return this.getDuplicateOf().getId().toString();
        }
        return "";
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the showNewStatusField
     */
    public boolean isShowNewStatusField() {
        return showNewStatusField;
    }

    /**
     * @param showNewStatusField the showNewStatusField to set
     */
    public void setShowNewStatusField(boolean showNewStatusField) {
        this.showNewStatusField = showNewStatusField;
    }
    
    private boolean isCRProcessingRequired(Organization org) {
        boolean flag = false;
        if (PoServiceUtil.isOverriddenByCurrentUser(org) 
                || (org.getOverriddenBy() == null && PoServiceUtil.isOrgCreatedByCurrentUser(org))) {
            flag = true;
        }
        
        return flag;
    }
}
