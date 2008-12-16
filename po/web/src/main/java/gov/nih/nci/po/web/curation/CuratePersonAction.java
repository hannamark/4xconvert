package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
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
 * Action class to handle curation of Person entities.
 */
public class CuratePersonAction extends ActionSupport implements Preparable {
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
                    fieldName = "person"
                )
            })
    public String curate() throws JMSException {
        PoRegistry.getPersonService().curate(getPerson());
        ActionHelper.saveMessage(getText("person.curate.success"));
        return SUCCESS;
    }
    
    /**
     * @return org to curate
     */
    public Person getPerson() {
        return person;
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
}
