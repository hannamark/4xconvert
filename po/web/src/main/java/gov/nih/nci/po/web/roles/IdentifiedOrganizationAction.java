package gov.nih.nci.po.web.roles;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationSortCriterion;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoRegistry;

import java.util.ArrayList;

import javax.jms.JMSException;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * Action to manage IdentifiedOrganization(s).
 *
 * @author smatyas
 */
public class IdentifiedOrganizationAction
    extends AbstractRoleAction<IdentifiedOrganization, IdentifiedOrganizationCR, IdentifiedOrganizationServiceLocal>
    implements Preparable {

    private static final long serialVersionUID = 1L;
    private IdentifiedOrganization role = new IdentifiedOrganization();
    private IdentifiedOrganizationCR cr = new IdentifiedOrganizationCR();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRole() == null) {
            setRole(new IdentifiedOrganization());
        }

        if (getRole().getPlayer() == null) { //if not set, then set to default
            getRole().setPlayer(getOrganization());
        }
        if (getRole().getScoper() == null) { //if not set, then set to default
            getRole().setScoper(new Organization());
        }
        if (getRole().getAssignedIdentifier() == null) { //if not set, then set to default
            getRole().setAssignedIdentifier(new Ii());
        }
        getRole().getAssignedIdentifier().setNullFlavor(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentifiedOrganization getRole() {
        return role;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setRole(IdentifiedOrganization role) {
        this.role = role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentifiedOrganizationCR getCr() {
        return cr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCr(IdentifiedOrganizationCR cr) {
        this.cr = cr;
    }

    /**
     * {@inheritDoc}
     */
    @Validations(
        customValidators = { @CustomValidator(type = "hibernate", fieldName = "role" ,
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "identifiedOrganization") })
        },
        requiredFields = {
            @RequiredFieldValidator(type = ValidatorType.SIMPLE,
                    fieldName = "role.assignedIdentifier.extension",
                    message = "Extension must be set")
            ,
            @RequiredFieldValidator(type = ValidatorType.SIMPLE,
                    fieldName = "role.assignedIdentifier.root",
                    message = "Root must be set")
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
                parameters = { @ValidationParameter(name = "resourceKeyBase", value = "identifiedOrganization") })
        },
        requiredFields = {
            @RequiredFieldValidator(type = ValidatorType.SIMPLE,
                    fieldName = "role.assignedIdentifier.extension",
                    message = "Extension must be set")
            ,
            @RequiredFieldValidator(type = ValidatorType.SIMPLE,
                    fieldName = "role.assignedIdentifier.root",
                    message = "Root must be set")
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
    protected SearchCriteria<IdentifiedOrganization> getDuplicateCriteria() {
        IdentifiedOrganization dupOfBOCrit = new IdentifiedOrganization();
        AnnotatedBeanSearchCriteria<IdentifiedOrganization> duplicateOfCriteria
            = new AnnotatedBeanSearchCriteria<IdentifiedOrganization>(dupOfBOCrit);
        dupOfBOCrit.setPlayer(getOrganization());
        return duplicateOfCriteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void defaultConstructorInit() {
        setResults(new PaginatedList<IdentifiedOrganization>(0,
                new ArrayList<IdentifiedOrganization>(), PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
                IdentifiedOrganizationSortCriterion.ID.name(), SortOrderEnum.ASCENDING));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IdentifiedOrganizationServiceLocal getRoleService() {
        return PoRegistry.getInstance().getServiceLocator().getIdentifiedOrganizationService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SearchCriteria<IdentifiedOrganization> getSearchCriteria() {
        IdentifiedOrganization boCrit = new IdentifiedOrganization();
        AnnotatedBeanSearchCriteria<IdentifiedOrganization> criteria
            = new AnnotatedBeanSearchCriteria<IdentifiedOrganization>(boCrit);
        Organization player = new Organization();
        player.setId(getOrganization().getId());
        boCrit.setPlayer(player);
        return criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<IdentifiedOrganizationSortCriterion> getSortCriterion() {
        return IdentifiedOrganizationSortCriterion.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAddSuccessMessageKey() {
        return "identifiedorganization.create.success";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getEditSuccessMessageKey() {
        return "identifiedorganization.update.success";
    }
}
