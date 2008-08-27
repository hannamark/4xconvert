package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.web.util.PoRegistry;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle curation of Organization entities.
 */
public class CurateOrganizationAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private Organization organization = new Organization();


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (organization.getId() != null) {
            organization = PoRegistry.getOrganizationService().getOrganization(organization.getId());
        }
    }

    /**
     * @return show start page
     */
    public String start() {
        return SUCCESS;
    }
    
    /**
     * @return success
     */
    @Validations(
            customValidators = {
                @CustomValidator(
                    type = "hibernate",
                    fieldName = "organization",
                    parameters = {
                        @ValidationParameter(
                            name = "includes",
                            value = "name")
                    }
                )
            }
        )
    public String curate() {
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


}
