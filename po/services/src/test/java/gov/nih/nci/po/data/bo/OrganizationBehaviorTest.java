package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OrganizationBehaviorTest {

    @Test
    public void testBasicGetterSetters() {
        Organization org = new Organization();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "name");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "postalAddress");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "statusCode");
        
        assertFalse(org.isUsOrCanadaAddress());
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        org.setPostalAddress(addr1);
        assertTrue(org.isUsOrCanadaAddress());
    }

}
