package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        
        assertFalse(person.isUsOrCanadaAddress());
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        person.setPostalAddress(addr1);
        assertTrue(person.isUsOrCanadaAddress());
    }

}
