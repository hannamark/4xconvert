package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoRegistry;

import java.util.ArrayList;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.Preparable;

/**
 * Action to manage ResearchOrganization(s).
 * 
 * @author smatyas
 */
public class ResearchOrganizationAction 
    extends AbstractRoleAction<ResearchOrganization, ResearchOrganizationCR, ResearchOrganizationServiceLocal> 
    implements Preparable {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @Override
    public void prepare() throws Exception {
        super.prepare();
        if (getRole().getPlayer() == null) { //if not set, then set to default
            getRole().setPlayer(getOrganization());
        }
    }

    /**
     * {@inheritDoc}
     */
    protected SearchCriteria<ResearchOrganization> getDuplicateCriteria() {
        ResearchOrganization dupOfBOCrit = new ResearchOrganization();
        AnnotatedBeanSearchCriteria<ResearchOrganization> duplicateOfCriteria 
            = new AnnotatedBeanSearchCriteria<ResearchOrganization>(dupOfBOCrit);
        dupOfBOCrit.setPlayer(getOrganization());
        return duplicateOfCriteria;
    }

    /**
     * {@inheritDoc}
     */
    protected void defaultConstructorInit() {
        setRole(new ResearchOrganization());
        setCr(new ResearchOrganizationCR());
        setResults(new PaginatedList<ResearchOrganization>(0,
                new ArrayList<ResearchOrganization>(), PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
                ResearchOrganizationSortCriterion.ID.name(), SortOrderEnum.ASCENDING));
    }

    /**
     * {@inheritDoc}
     */
    protected ResearchOrganizationServiceLocal getRoleService() {
        return PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService();
    }

    /**
     * {@inheritDoc}
     */
    protected SearchCriteria<ResearchOrganization> getSearchCriteria() {
        ResearchOrganization boCrit = new ResearchOrganization();
        AnnotatedBeanSearchCriteria<ResearchOrganization> criteria 
            = new AnnotatedBeanSearchCriteria<ResearchOrganization>(boCrit);
        Organization player = new Organization();
        player.setId(getOrganization().getId());
        boCrit.setPlayer(player);
        return criteria;
    }
    
    /**
     * {@inheritDoc}
     */
    protected Class<ResearchOrganizationSortCriterion> getSortCriterion() {
        return ResearchOrganizationSortCriterion.class;
    }
    /**
     * {@inheritDoc}
     */
    protected String getAddSuccessMessageKey() {
        return "researchorganization.create.success";
    }

    /**
     * {@inheritDoc}
     */
    protected String getEditSuccessMessageKey() {
        return "researchorganization.update.success";
    }
}
