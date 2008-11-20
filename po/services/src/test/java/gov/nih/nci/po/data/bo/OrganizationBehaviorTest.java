package gov.nih.nci.po.data.bo;

import org.junit.Test;

public class OrganizationBehaviorTest {

    @Test
    public void testBasicGetterSetters() {
        Organization org = new Organization();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "name");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "postalAddress");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "statusCode");
    }

}
