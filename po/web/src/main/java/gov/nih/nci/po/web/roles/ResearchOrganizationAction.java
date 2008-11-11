package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
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
import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to manage ResearchOrganization(s).
 * 
 * @author smatyas
 */
public class ResearchOrganizationAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private final PaginatedList<ResearchOrganization> results = new PaginatedList<ResearchOrganization>(0,
            new ArrayList<ResearchOrganization>(), PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
            ResearchOrganizationSortCriterion.ID.name(), SortOrderEnum.ASCENDING);

    private final ResearchOrganization boCrit = new ResearchOrganization();
    private final AnnotatedBeanSearchCriteria<ResearchOrganization> criteria 
        = new AnnotatedBeanSearchCriteria<ResearchOrganization>(boCrit);
    private Organization organization = new Organization();
    private ResearchOrganization researchOrganization = new ResearchOrganization();
    private ResearchOrganizationCR cr = new ResearchOrganizationCR();
    /**
     * The action execution was successful. Show result view to the end user.
     */
    public static final String CHANGE_CURRENT_CHANGE_REQUEST_RESULT = "changeCurrentChangeRequest";

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (organization.getId() != null) { //if set, load
            organization = PoRegistry.getOrganizationService().getById(organization.getId());
        }
        if (researchOrganization.getId() != null) { //if set, load
            researchOrganization = getResearchOrganizationService().getById(researchOrganization.getId());
        }
        if (researchOrganization.getPlayer() == null) { //if not set, then set to default
            researchOrganization.setPlayer(organization);
        }
        findAndSetCr(cr.getId());
    }

    /**
     * @return success
     */
    public String start() {
        if (!researchOrganization.getChangeRequests().isEmpty()) {
            cr = researchOrganization.getChangeRequests().iterator().next();
        }
        findAndSetCr(cr.getId());
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

    private void search() {
        Organization player = new Organization();
        player.setId(organization.getId());
        boCrit.setPlayer(player);
        GenericSearchServiceUtil.search(PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService(),
                criteria, getResults(), ResearchOrganizationSortCriterion.class);
    }

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing announcement
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "researchOrganization") })
    public String add() throws JMSException {
        PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService().curate(researchOrganization);
        list();
        ActionHelper.saveMessage(getText("researchorganization.create.success"));
        return SUCCESS;
    }

    /**
     * @return success
     * @throws JMSException if an error occurred while publishing announcement
     */
    @Validations(customValidators = { @CustomValidator(type = "hibernate", fieldName = "researchOrganization") })
    public String edit() throws JMSException {
        PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService().curate(researchOrganization);
        list();
        ActionHelper.saveMessage(getText("researchorganization.update.success"));
        return SUCCESS;
    }

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
     * @return search results
     */
    public PaginatedList<ResearchOrganization> getResults() {
        return results;
    }

    /**
     * @return to add/edit/remove
     */
    public ResearchOrganization getResearchOrganization() {
        return researchOrganization;
    }

    /**
     * @param researchOrganization to add/edit/remove
     */
    public void setResearchOrganization(ResearchOrganization researchOrganization) {
        this.researchOrganization = researchOrganization;
    }

    /**
     * @return the allowable EntityStatus values
     */
    @SuppressWarnings("unchecked")
    public Collection<RoleStatus> getAvailableStatus() {
        if (researchOrganization.getId() != null) {
            return researchOrganization.getStatus().getAllowedTransitions();
        } else {
            List set = new ArrayList();
            set.add(EntityStatus.PENDING);
            set.add(EntityStatus.ACTIVE);
            return set;
        }
    }
    
    ResearchOrganizationServiceLocal getResearchOrganizationService() {
        ResearchOrganizationServiceLocal service = PoRegistry.getInstance().getServiceLocator()
                .getResearchOrganizationService();
        return service;
    }
    
    /**
     * @return list of available duplicate of entries for the current ResearchOrganization
     */
    public List<ResearchOrganization> getAvailableDuplicateOfs() {
        List<ResearchOrganization> duplicateOfList = getResearchOrganizationService().search(getDuplicateCriteria());
        remove(duplicateOfList, getResearchOrganization().getId());
        return duplicateOfList;
    }

    private AnnotatedBeanSearchCriteria<ResearchOrganization> getDuplicateCriteria() {
        ResearchOrganization dupOfBOCrit = new ResearchOrganization();
        AnnotatedBeanSearchCriteria<ResearchOrganization> duplicateOfCriteria 
            = new AnnotatedBeanSearchCriteria<ResearchOrganization>(dupOfBOCrit);
        dupOfBOCrit.setPlayer(organization);
        return duplicateOfCriteria;
    }

    private void remove(List<? extends PersistentObject> duplicateOfList, Long id) {
        if (id != null && CollectionUtils.isNotEmpty(duplicateOfList)) {
            for (Iterator<? extends PersistentObject> itr = duplicateOfList.iterator(); itr.hasNext();) {
                PersistentObject ro = (PersistentObject) itr.next();
                if (ro.getId().equals(id)) {
                    itr.remove();
                }
            }
        }
    }
    
    private void findAndSetCr(Long id) {
        if (id != null) {
            this.cr = PoRegistry.getGenericService().getPersistentObject(ResearchOrganizationCR.class, id);
        }
    }
    
    /**
     * @return active change request
     */
    public ResearchOrganizationCR getCr() {
        return cr;
    }

    /**
     * @param cr active change request
     */
    public void setCr(ResearchOrganizationCR cr) {
        this.cr = cr;
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
        Set<ResearchOrganizationCR> unprocessedChangeRequests = researchOrganization.getChangeRequests();
        for (ResearchOrganizationCR changeRequest : unprocessedChangeRequests) {
            treeMap.put(changeRequest.getId().toString(), "CR-ID-" + changeRequest.getId().toString());
        }
        return treeMap;
    }
}
