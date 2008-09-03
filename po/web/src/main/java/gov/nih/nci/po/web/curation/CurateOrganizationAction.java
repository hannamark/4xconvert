package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;
import gov.nih.nci.po.web.util.PoRegistry;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class to handle curation of Organization entities.
 */
public class CurateOrganizationAction extends ActionSupport implements Preparable {
    /**
     * The action execution was successful. Show result
     * view to the end user.
     */
    public static final String CURATE_RESULT = "curate";
    private static final long serialVersionUID = 1L;
    private Organization organization = new Organization();

    private String rootKey;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            organization = (Organization) getSession().getAttribute(getRootKey());
        }
    }

    private HttpSession getSession() {
        return PoHttpSessionUtil.getSession();
    }

    /**
     * @return show start page
     */
    public String start() {
        organization = PoRegistry.getOrganizationService().getOrganization(organization.getId());
        initializeCollections(organization);
        setRootKey(PoHttpSessionUtil.addAttribute(organization));
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
     * @throws EntityValidationException if a validation error is found when attempting to update
     */
    @Validations(customValidators = {
                @CustomValidator(type = "hibernate", 
                    fieldName = "organization", 
                    parameters = {
                            @ValidationParameter(name = "excludes", value = "statusCode")
                    }
                )
            })
    public String curate() throws EntityValidationException {
        PoRegistry.getOrganizationService().accept(getOrganization());
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

}
