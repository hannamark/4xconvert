package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.common.CurationStatus;
import gov.nih.nci.po.data.common.PersonType;

import org.junit.Test;

/**
 * test for person.
 * @author Scott Miller
 */
public class PersonBehaviorTest {

    /**
     * Test the getters and setters.
     */
    @Test
    public void testBasicGetterSetters() {
        Person person = new Person();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "firstName");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "lastName");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "middleName");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "suffix");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "prefix");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "dateOfBirth");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "preferredContactInfo");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "curationStatus");
    }

    /**
     * Test the investigator flag flow.
     */
    @Test
    public void testIsInvestigator() {
        Person person = new Person();
        assertFalse(person.isInvestigator());
        person.getTypes().add(new PersonType("abc"));
        assertFalse(person.isInvestigator());
        person.getTypes().add(new PersonType(PersonType.INVESTIGATOR));
        assertTrue(person.isInvestigator());
        person.getTypes().clear();
        person.getTypes().add(new PersonType(PersonType.INVESTIGATOR));
        assertTrue(person.isInvestigator());
    }

    /**
     * test duplocate getter setter.
     */
    @Test
    public void testDuplicateBehavior() {
        Person person = new Person();
        Person dupPerson = new Person();
        person.setCurationStatus(CurationStatus.CURATED);
        person.setDuplicateOfPerson(dupPerson);
        assertNull(person.getDuplicateOf());
        person.setCurationStatus(CurationStatus.REJECTED);
        person.setDuplicateOfPerson(dupPerson);
        assertSame(dupPerson, person.getDuplicateOf());
    }
}
