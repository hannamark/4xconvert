package gov.nih.nci.po.data.bo;

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

}
