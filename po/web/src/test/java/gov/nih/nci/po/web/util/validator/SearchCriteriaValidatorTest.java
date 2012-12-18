package gov.nih.nci.po.web.util.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.selector.SelectOrganizationAction;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.DelegatingValidatorContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.ValidatorContext;


public class SearchCriteriaValidatorTest extends AbstractPoTest {

    OrganizationSearchCriteriaValidator validator;
    String fieldName = "criteria";
    ValidatorContext validatorContext = new DelegatingValidatorContext(new SelectOrganizationAction());
    
    @Before
    public void init() {
        validator = new OrganizationSearchCriteriaValidator();
        validator.setValidatorContext(validatorContext);
        
    }
    
    @Test
    public void validate() throws ValidationException {
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, new OrganizationSearchCriteria());
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(null);
        assertTrue(validatorContext.hasActionErrors());
        assertEquals("At least one criterion must be provided", validatorContext.getActionErrors().iterator().next());
    }
    
    @Test
    public void validateNonSearchCriteriaObject() throws ValidationException {
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, new String());
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(null);
        assertFalse(validatorContext.hasActionErrors());
    }
}
