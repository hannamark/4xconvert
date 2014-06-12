package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Comment;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;
import gov.nih.nci.po.web.util.validator.Addressable;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.exceptions.CSException;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle curation of Person entities.
 */
public class CuratePersonAction extends AbstractPoAction implements Addressable, Preparable {
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

    private Person person = new Person();
    private String rootKey;
    private PersonCR cr = new PersonCR();
    private Person duplicateOf = new Person();
    private String comments;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            person = (Person) getSession().getAttribute(getRootKey());
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
        person = PoRegistry.getPersonService().getById(person.getId());
        initializeCollections(person);
        setRootKey(PoHttpSessionUtil.addAttribute(person));
        if (!person.getChangeRequests().isEmpty()) {
            cr = person.getChangeRequests().iterator().next();
        }
        findAndSetCr(cr.getId());
        return CURATE_RESULT;
    }

    private void initializeCollections(Person p) {
        p.getEmail().size();
        p.getFax().size();
        p.getPhone().size();
        p.getTty().size();
        p.getUrl().size();
        p.getComments().size();
    }

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing the announcement
     * @throws CSException CSException
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "person"),
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "person.phone",
                    message = "US and Canadian telephone numbers must match ###-###-####(x#*).") ,
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "person.fax",
                    message = "US and Canadian fax numbers must match ###-###-####(x#*)."),
            @CustomValidator(type = "usOrCanadaPhone", fieldName = "person.tty",
                    message = "US and Canadian tty numbers must match ###-###-####(x#*).")
            })
    public String curate() throws JMSException, CSException {
        // PO-1098 - for some reason, the duplicate of wasn't getting set properly by struts when we tried to
        // set person.duplicateOf.id directly, so we're setting it manually
        if (duplicateOf != null && duplicateOf.getId() != null) {
            getPerson().setDuplicateOf(duplicateOf);
        }
        addCommentToOrg();
        String middleName = getPerson().getMiddleName();
        if (StringUtils.isNotEmpty(middleName) 
                && (middleName.contains("(") || middleName.contains(")"))) {
            middleName = middleName.replace("(", "").replace(")", "");
        }
        getPerson().setMiddleName(middleName);
        PoRegistry.getPersonService().curate(getPerson());
        ActionHelper.saveMessage(getText("person.curate.success"));
        return SUCCESS;
    }
    
    private void addCommentToOrg() throws CSException {
        if (StringUtils.isNotBlank(comments)) {
            Comment c = new Comment();
            c.setCreateDate(new Date());           
            c.setValue(comments);
            c.setUser(SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(ServletActionContext.getRequest().getRemoteUser()));
            getPerson().getComments().add(c);
        }
    }

    /**
     * @return org to curate
     */
    public Person getPerson() {
        return person;
    }
    
    /**
     * Method for pulling this value in struts xml.
     * @return the organization id as a string.
     */
    public String getPersonId() {
        if (getPerson() != null && getPerson().getId() != null) {
            return this.getPerson().getId().toString();
        }
        return "";
    }

    /**
     * @param org to curate
     */
    public void setPerson(Person org) {
        this.person = org;
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
    public PersonCR getCr() {
        return cr;
    }

    /**
     * @param cr active change request
     */
    public void setCr(PersonCR cr) {
        this.cr = cr;
    }

    private void findAndSetCr(Long id) {
        if (id != null) {
            this.cr = PoRegistry.getGenericService().getPersistentObject(PersonCR.class, id);
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
        Set<PersonCR> unprocessedChangeRequests = person.getChangeRequests();
        for (PersonCR changeRequest : unprocessedChangeRequests) {
            treeMap.put(changeRequest.getId().toString(), "CR-ID-" + changeRequest.getId().toString());
        }
        return treeMap;
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotOrganizationalContactCount() {
        OrganizationalContactServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getOrganizationalContactService();
        return service.getHotRoleCount(person);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotHealthCareProviderCount() {
        HealthCareProviderServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getHealthCareProviderService();
        return service.getHotRoleCount(person);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotClinicalResearchStaffCount() {
        ClinicalResearchStaffServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getClinicalResearchStaffService();
        return service.getHotRoleCount(person);
    }

    /**
     * @return number of role that need the curator's attention.
     */
    public int getHotIdentifiedPersonCount() {
        IdentifiedPersonServiceLocal service  = PoRegistry.getInstance()
                .getServiceLocator().getIdentifiedPersonService();
        return service.getHotRoleCount(person);
    }

    /**
     * @return the duplicateOf
     */
    public Person getDuplicateOf() {
        return duplicateOf;
    }

    /**
     * @param duplicateOf the duplicateOf to set
     */
    public void setDuplicateOf(Person duplicateOf) {
        this.duplicateOf = duplicateOf;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUsOrCanadaFormat() {
        return this.person.isUsOrCanadaAddress();
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
}
