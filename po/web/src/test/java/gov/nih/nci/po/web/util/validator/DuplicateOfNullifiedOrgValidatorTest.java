package gov.nih.nci.po.web.util.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.curation.CurateOrganizationAction;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.DelegatingValidatorContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.ValidatorContext;


public class DuplicateOfNullifiedOrgValidatorTest extends AbstractPoTest {

    DuplicateOfNullifiedOrgValidator validator;
    String fieldName = "duplicateOf";
    ValidatorContext validatorContext = new DelegatingValidatorContext(new CurateOrganizationAction());
    
    @Before
    public void init() {
        validator = new DuplicateOfNullifiedOrgValidator();
        validator.setValidatorContext(validatorContext);
        
    }
    
    @Test
    public void isValid() throws ValidationException {
        Organization org = new Organization();
        org.setId(2L);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertFalse(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isValid2() throws ValidationException {
        Organization org = new Organization();
        org.setId(2L);
        org.setStatusCode(EntityStatus.ACTIVE);
        HealthCareFacility hcf = new HealthCareFacility();
        Ii id = new Ii();
        id.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        hcf.getOtherIdentifiers().add(id);
        org.getHealthCareFacilities().add(hcf);
        
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, new Organization());
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        validator.validate(action);
        assertFalse(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isValid3() throws ValidationException {
        Organization org = new Organization();
        org.setId(2L);
        org.setStatusCode(EntityStatus.ACTIVE);
        HealthCareFacility hcf = new HealthCareFacility();
        Ii id = new Ii();
        id.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        hcf.getOtherIdentifiers().add(id);
        org.getHealthCareFacilities().add(hcf);
        
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        Organization dup = new Organization();
        dup.setId(2L);
        valueStack.set(fieldName, dup);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        validator.validate(action);
        assertFalse(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isNotValid() throws ValidationException {
        Organization org = new Organization();
        org.setId(3L);
        org.setStatusCode(EntityStatus.NULLIFIED);
        HealthCareFacility hcf = new HealthCareFacility();
        Ii id = new Ii();
        id.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        hcf.getOtherIdentifiers().add(id);
        org.getHealthCareFacilities().add(hcf);
        
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isNotValid2() throws ValidationException {
        Organization org = new Organization();
        org.setId(3L);
        org.setStatusCode(EntityStatus.NULLIFIED);
        HealthCareFacility hcf = new HealthCareFacility();
        Ii id = new Ii();
        id.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        hcf.getOtherIdentifiers().add(id);
        org.getHealthCareFacilities().add(hcf);
        
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        action.setDuplicateOf(null);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isNotValid3() throws ValidationException {
        Organization org = new Organization();
        org.setId(4L);
        org.setStatusCode(EntityStatus.NULLIFIED);
        HealthCareFacility hcf = new HealthCareFacility();
        Ii id = new Ii();
        id.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        hcf.getOtherIdentifiers().add(id);
        org.getHealthCareFacilities().add(hcf);
        
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, new Organization());
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        action.setDuplicateOf(null);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }  
    
    
   
}
