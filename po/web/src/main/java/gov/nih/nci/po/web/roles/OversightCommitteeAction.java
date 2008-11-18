package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeSortCriterion;
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
 * Action to manage OversightCommittee(s).
 *
 * @author smatyas
 */
public class OversightCommitteeAction extends
        AbstractRoleAction<OversightCommittee, OversightCommitteeCR, OversightCommitteeServiceLocal> implements
        Preparable {

    private static final long serialVersionUID = 1L;
    private OversightCommittee role = new OversightCommittee();
    private OversightCommitteeCR cr = new OversightCommitteeCR();

    /**
     * {@inheritDoc}
     */
    public OversightCommittee getRole() {
        return role;
    }

    /**
     * {@inheritDoc}
     */
    public void setRole(OversightCommittee role) {
        this.role = role;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCR getCr() {
        return cr;
    }

    /**
     * {@inheritDoc}
     */
    public void setCr(OversightCommitteeCR cr) {
        this.cr = cr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OversightCommitteeCR getBaseCr() {
        return getCr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OversightCommittee getBaseRole() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseCr(OversightCommitteeCR baseCr) {
        setCr(baseCr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseRole(OversightCommittee baseRole) {
        setRole(baseRole);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRole() == null) {
            setRole(new OversightCommittee());
        }
        if (getRole().getPlayer() == null) { // if not set, then set to default
            getRole().setPlayer(getOrganization());
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Validations(
        customValidators = { @CustomValidator(type = "hibernate", fieldName = "role" ,
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "oversightCommittee") })
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
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "oversightCommittee") })
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
    protected SearchCriteria<OversightCommittee> getDuplicateCriteria() {
        OversightCommittee dupOfBOCrit = new OversightCommittee();
        AnnotatedBeanSearchCriteria<OversightCommittee> duplicateOfCriteria
            = new AnnotatedBeanSearchCriteria<OversightCommittee>(dupOfBOCrit);
        dupOfBOCrit.setPlayer(getOrganization());
        return duplicateOfCriteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void defaultConstructorInit() {
        setResults(new PaginatedList<OversightCommittee>(0, new ArrayList<OversightCommittee>(),
                PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null, OversightCommitteeSortCriterion.ID.name(),
                SortOrderEnum.ASCENDING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OversightCommitteeServiceLocal getRoleService() {
        return PoRegistry.getInstance().getServiceLocator().getOversightCommitteeService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SearchCriteria<OversightCommittee> getSearchCriteria() {
        OversightCommittee boCrit = new OversightCommittee();
        AnnotatedBeanSearchCriteria<OversightCommittee> criteria = new AnnotatedBeanSearchCriteria<OversightCommittee>(
                boCrit);
        Organization player = new Organization();
        player.setId(getOrganization().getId());
        boCrit.setPlayer(player);
        return criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<OversightCommitteeSortCriterion> getSortCriterion() {
        return OversightCommitteeSortCriterion.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAddSuccessMessageKey() {
        return "oversightcommittee.create.success";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getEditSuccessMessageKey() {
        return "oversightcommittee.update.success";
    }
}
