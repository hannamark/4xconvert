package gov.nih.nci.po.web.util.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.curation.CurateOrganizationAction;
import gov.nih.nci.po.web.curation.CuratePersonAction;
import gov.nih.nci.po.web.roles.HealthCareFacilityAction;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.validator.DelegatingValidatorContext;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.ValidatorContext;


public class UsOrCanadaPhoneValidatorTest extends AbstractPoTest {

    UsOrCanadaPhoneValidator validator;
    
    ValidatorContext validatorContext = new DelegatingValidatorContext(new CurateOrganizationAction());
    
    @Before
    public void init() {
        validator = new UsOrCanadaPhoneValidator();
        validator.setValidatorContext(validatorContext);
        
    }
    
    @Test
    public void isValidOrgPhone() throws ValidationException {
        String fieldName = "organization.phone";
        Organization org = new Organization();
        org.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        org.setPostalAddress(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("111-222-3333");
        org.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        org.getPhone().add(ph2);
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
    public void isNotValidOrgPhone() throws ValidationException {
        String fieldName = "organization.phone";
        Organization org = new Organization();
        org.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        org.setPostalAddress(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("1112223333");
        org.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        org.getPhone().add(ph2);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isNotValidExtPhone() throws ValidationException {
        String fieldName = "organization.phone";
        Organization org = new Organization();
        org.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        org.setPostalAddress(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("111-222-3333");
        org.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x");
        org.getPhone().add(ph2);
        CurateOrganizationAction action = new CurateOrganizationAction();
        action.setOrganization(org);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isValidPerPhone() throws ValidationException {
        String fieldName = "person.phone";
        Person per = new Person();
        per.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        per.setPostalAddress(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("111-222-3333");
        per.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        per.getPhone().add(ph2);
        CuratePersonAction action = new CuratePersonAction();
        action.setPerson(per);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertFalse(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isNotValidPerPhone() throws ValidationException {
        String fieldName = "person.phone";
        Person per = new Person();
        per.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        per.setPostalAddress(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("1112223333");
        per.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        per.getPhone().add(ph2);
        CuratePersonAction action = new CuratePersonAction();
        action.setPerson(per);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isValidPerPhoneNotUS() throws ValidationException {
        String fieldName = "person.phone";
        Person per = new Person();
        per.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("Colombia", "170", "CO", "COL"));
        per.setPostalAddress(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("1112223333");
        per.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        per.getPhone().add(ph2);
        CuratePersonAction action = new CuratePersonAction();
        action.setPerson(per);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertFalse(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isValidHcfPhone() throws ValidationException {
        String fieldName = "role.phone";
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        hcf.getPostalAddresses().add(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("111-222-3333");
        hcf.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        hcf.getPhone().add(ph2);
        HealthCareFacilityAction action = new HealthCareFacilityAction();
        action.setRole(hcf);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertFalse(validatorContext.hasActionErrors());
    }
    
    @Test
    public void isNotValidHcfPhone() throws ValidationException {
        String fieldName = "role.phone";
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(2L);
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        hcf.getPostalAddresses().add(addr1);
        PhoneNumber ph1 = new PhoneNumber();
        ph1.setValue("1112223333");
        hcf.getPhone().add(ph1);
        PhoneNumber ph2 = new PhoneNumber();
        ph2.setValue("222-333-4444x5555");
        hcf.getPhone().add(ph2);
        HealthCareFacilityAction action = new HealthCareFacilityAction();
        action.setRole(hcf);
        ValueStack valueStack = ActionContext.getContext().getValueStack();
        valueStack.set(fieldName, null);
        validator.setValueStack(valueStack);
        validator.setFieldName(fieldName);
        validator.validate(action);
        assertTrue(validatorContext.hasActionErrors());
    }
   
}
