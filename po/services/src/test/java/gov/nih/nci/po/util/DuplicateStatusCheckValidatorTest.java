package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;

import org.junit.Test;


public class DuplicateStatusCheckValidatorTest {

    DuplicateStatusCheckValidator validator = new DuplicateStatusCheckValidator();
    
    @Test
    public void isValid() {
        assertTrue(validator.isValid(new Organization()));
        assertTrue(validator.isValid(new Person()));
        assertTrue(validator.isValid(new String()));
        
        Organization organization = new Organization();
        organization.setStatusCode(EntityStatus.NULLIFIED);
        organization.setDuplicateOf(new Organization());
        assertTrue(validator.isValid(organization));
        
        Person person = new Person();
        person.setStatusCode(EntityStatus.NULLIFIED);
        person.setDuplicateOf(new Person());
        assertTrue(validator.isValid(person));
    }
    
    @Test
    public void isInvalid() {
        Organization organization = new Organization();
        organization.setStatusCode(EntityStatus.ACTIVE);
        organization.setDuplicateOf(new Organization());
        assertFalse(validator.isValid(organization));
        
        Person person = new Person();
        person.setStatusCode(EntityStatus.ACTIVE);
        person.setDuplicateOf(new Person());
        assertFalse(validator.isValid(person));
    }
}
