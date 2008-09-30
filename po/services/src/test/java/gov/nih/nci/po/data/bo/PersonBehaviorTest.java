package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

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
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "suffix");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "prefix");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person, "statusCode");
    }

    /**
     * test duplocate getter setter.
     */
    @Test
    public void testDuplicateBehavior() {
        Person person = new Person();
        Person dupPerson = new Person();
        person.setStatusCode(EntityStatus.ACTIVE);
        person.setDuplicateOfPerson(dupPerson);
        assertNull(person.getDuplicateOf());
        person.setStatusCode(EntityStatus.NULLIFIED);
        person.setDuplicateOfPerson(dupPerson);
        assertSame(dupPerson, person.getDuplicateOf());
    }
}
