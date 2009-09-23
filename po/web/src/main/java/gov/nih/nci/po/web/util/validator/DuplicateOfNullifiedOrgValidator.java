package gov.nih.nci.po.web.util.validator;

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.web.curation.CurateOrganizationAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Used to validate a Organization being nullified.
 * @author mshestop
 */
public class DuplicateOfNullifiedOrgValidator extends FieldValidatorSupport {

    /**
     * {@inheritDoc}
     */
    public void validate(Object object) throws ValidationException {
        ValueStack stack = ActionContext.getContext().getValueStack();
        String fieldName = getFieldName();
        Organization duplicateOf = (Organization) getFieldValue(fieldName, object);
        
        stack.push(object);
        Organization org = ((CurateOrganizationAction) object).getOrganization();
        if (EntityStatus.NULLIFIED.equals(org.getStatusCode()) 
                && hasCtepRoles(org)
                && (duplicateOf == null || duplicateOf.getId() == null)) {
            addActionError(object);
        }
        stack.pop();
    }
    
    private boolean hasCtepRoles(Organization org) {
        return org.getId() != null && org.isAssociatedWithCtepRoles();
    }
    
}
