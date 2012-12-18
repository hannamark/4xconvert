package gov.nih.nci.po.web.util.validator;

import gov.nih.nci.po.service.OrganizationSearchCriteria;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * Used to validate a OrganizationSearchCriteria object.
 * @author Denis G. Krylov
 */
public class OrganizationSearchCriteriaValidator extends FieldValidatorSupport {

    /**
     * {@inheritDoc}
     */
    public void validate(Object object) throws ValidationException {
        ValueStack stack = ActionContext.getContext().getValueStack();
        String fieldName = getFieldName();
        Object value = getFieldValue(fieldName, object);
        stack.push(object);
        if (value instanceof OrganizationSearchCriteria) {
            OrganizationSearchCriteria sc = (OrganizationSearchCriteria) value;
            try {
                sc.isValid();
            } catch (OneCriterionRequiredException e) {
                setDefaultMessage(e.getMessage());
                addActionError(object);
            }
        }
        stack.pop();
    }

}
