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

import javax.jms.JMSException;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to manage ResearchOrganization(s).
 *
 * @author smatyas
 */
public class ResearchOrganizationAction
    extends AbstractRoleAction<ResearchOrganization, ResearchOrganizationCR, ResearchOrganizationServiceLocal>
    implements Preparable {

    private static final long serialVersionUID = 1L;
    private ResearchOrganization role = new ResearchOrganization();
    private ResearchOrganizationCR cr = new ResearchOrganizationCR();

    /**
     * {@inheritDoc}
     */
    public ResearchOrganization getRole() {
        return role;
    }

    /**
     * {@inheritDoc}
     */
    public void setRole(ResearchOrganization role) {
        this.role = role;
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCR getCr() {
        return cr;
    }

    /**
     * {@inheritDoc}
     */
    public void setCr(ResearchOrganizationCR cr) {
        this.cr = cr;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ResearchOrganizationCR getBaseCr() {
        return getCr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResearchOrganization getBaseRole() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseCr(ResearchOrganizationCR baseCr) {
        setCr(baseCr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseRole(ResearchOrganization baseRole) {
        setRole(baseRole);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRole() == null) {
            setRole(new ResearchOrganization());
        }

        if (getRole().getPlayer() == null) { //if not set, then set to default
            getRole().setPlayer(getOrganization());
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Validations(
        customValidators = { @CustomValidator(type = "hibernate", fieldName = "role" ,
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "researchOrganization") })
        }
    )
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public String add() throws JMSException {
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Validations(
        customValidators = { @CustomValidator(type = "hibernate", fieldName = "role" ,
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "researchOrganization") })
        }
    )
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
    public String edit() throws JMSException {
        return super.edit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
    protected void defaultConstructorInit() {
        setResults(new PaginatedList<ResearchOrganization>(0,
                new ArrayList<ResearchOrganization>(), PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
                ResearchOrganizationSortCriterion.ID.name(), SortOrderEnum.ASCENDING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResearchOrganizationServiceLocal getRoleService() {
        return PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
    protected Class<ResearchOrganizationSortCriterion> getSortCriterion() {
        return ResearchOrganizationSortCriterion.class;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAddSuccessMessageKey() {
        return "researchorganization.create.success";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getEditSuccessMessageKey() {
        return "researchorganization.update.success";
    }
}
