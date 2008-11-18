package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.service.SortCriterion;
import gov.nih.nci.po.web.GenericSearchServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author smatyas
 *
 * @param <ROLE>
 * @param <ROLECR>
 * @param <ROLESERVICE>
 */
public abstract class AbstractRoleAction<ROLE extends Correlation,
    ROLECR extends CorrelationChangeRequest<ROLE>, ROLESERVICE extends GenericStructrualRoleServiceLocal<ROLE>>
        extends ActionSupport {

    private static final String UNCHECKED = "unchecked";
    private static final long serialVersionUID = 1L;
    /**
     * The action execution was successful. Show result view to the end user.
     */
    public static final String CHANGE_CURRENT_CHANGE_REQUEST_RESULT = "changeCurrentChangeRequest";
    private Organization organization = new Organization();
    private PaginatedList<ROLE> results;

    /**
     * Default Constructor (force subclasses to initialize.
     */
    public AbstractRoleAction() {
        super();
        defaultConstructorInit();
    }

    /**
     * Implement to provide default constructor initialization.
     */
    protected abstract void defaultConstructorInit();

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.SignatureDeclareThrowsException", UNCHECKED })
    public String input() throws Exception {
        if (getBaseRole().getId() == null) {
            getBaseRole().setStatus(RoleStatus.PENDING);
        }
        if (!getBaseRole().getChangeRequests().isEmpty()) {
            setBaseCr((ROLECR) getBaseRole().getChangeRequests().iterator().next());
        }
        return INPUT;
    }

    /**
     * @return success
     */
    public String start() {
        search();
        return SUCCESS;
    }

    /**
     * @return success
     */
    public String list() {
        search();
        return SUCCESS;
    }

    /**
     * Generic method to search and fill the getResults() property using the getSearchCriteria() and getSortCriterion().
     */
    protected void search() {
        GenericSearchServiceUtil.search(getRoleService(), getSearchCriteria(), getResults(), getSortCriterion());
    }

    /**
     * @return sort criterion type to be used by search method
     */
    protected abstract Class<? extends SortCriterion<ROLE>> getSortCriterion();

    /**
     * @return criteria to be used by search method
     */
    protected abstract SearchCriteria<ROLE> getSearchCriteria();

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing announcement
     */
    public String add() throws JMSException {
        getRoleService().curate(getBaseRole());
        list();
        ActionHelper.saveMessage(getText(getAddSuccessMessageKey()));
        return SUCCESS;
    }

    /**
     * @return message key
     */
    protected abstract String getAddSuccessMessageKey();

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing announcement
     */
    public String edit() throws JMSException {
        getRoleService().curate(getBaseRole());
        list();
        ActionHelper.saveMessage(getText(getEditSuccessMessageKey()));
        return SUCCESS;
    }

    /**
     * @return message key
     */
    protected abstract String getEditSuccessMessageKey();

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter works properly.
     * @return to add/edit/remove
     */
    public abstract ROLE getBaseRole();


    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter works properly.
     * @param role to add/edit/remove
     */
    public abstract void setBaseRole(ROLE role);

    /**
     * @return organization player
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization player
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter works properly.
     * @return active change request
     */
    public abstract ROLECR getBaseCr();

    /**
     * Force sub-classes to override so that the PersistentObjectTypeConverter works properly.
     * @param cr active change request
     */
    public abstract void setBaseCr(ROLECR cr);

    /**
     * @return the service for the ROLE
     */
    protected abstract ROLESERVICE getRoleService();

    /**
     * @return success
     */
    public String changeCurrentChangeRequest() {
        return CHANGE_CURRENT_CHANGE_REQUEST_RESULT;
    }

    /**
     * @return the list of entries for the select CR pull-down
     */
    @SuppressWarnings(UNCHECKED)
    public Map<String, String> getSelectChangeRequests() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        Set<ROLECR> unprocessedChangeRequests = getBaseRole().getChangeRequests();
        for (ROLECR changeRequest : unprocessedChangeRequests) {
            treeMap.put(changeRequest.getId().toString(), "CR-ID-" + changeRequest.getId().toString());
        }
        return treeMap;
    }

    /**
     * @return the allowable EntityStatus values
     */
    public Collection<RoleStatus> getAvailableStatus() {
        if (getBaseRole().getId() != null) {
            return getBaseRole().getPriorStatus().getAllowedTransitions();
        } else {
            List<RoleStatus> set = new ArrayList<RoleStatus>();
            set.add(RoleStatus.PENDING);
            set.add(RoleStatus.ACTIVE);
            return set;
        }
    }

    /**
     * @return search results
     */
    public PaginatedList<ROLE> getResults() {
        return results;
    }

    /**
     * @param results search results
     */
    protected void setResults(PaginatedList<ROLE> results) {
        this.results = results;
    }

    /**
     * @return list of available duplicate of entries for the current ResearchOrganization
     */
    public List<ROLE> getAvailableDuplicateOfs() {
        List<ROLE> duplicateOfList = getRoleService().search(getDuplicateCriteria());
        remove(duplicateOfList, getBaseRole().getId());
        return duplicateOfList;
    }

    /**
     * @return the SearchCriteria used to search for duplicateOf for the role.
     */
    protected abstract SearchCriteria<ROLE> getDuplicateCriteria();

    /**
     * Remove matching PersistentObject(s) from the list.
     * @param duplicateOfList list to search
     * @param id to find
     */
    protected void remove(List<? extends PersistentObject> duplicateOfList, Long id) {
        if (id != null && CollectionUtils.isNotEmpty(duplicateOfList)) {
            for (Iterator<? extends PersistentObject> itr = duplicateOfList.iterator(); itr.hasNext();) {
                PersistentObject ro = itr.next();
                if (ro.getId().equals(id)) {
                    itr.remove();
                }
            }
        }
    }
}
