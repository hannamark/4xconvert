package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class OrganizationBehaviorTest {

    @Test
    public void testBasicGetterSetters() {
        Organization org = new Organization();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "name");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "abbreviatedName");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "postalAddress");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "statusCode");
    }

    @Test
    public void testDuplicateBehavior() {
        Organization org = new Organization();
        Organization dupOrg = new Organization();
        org.setStatusCode(EntityStatus.CURATED);
        org.setDuplicateOfOrg(dupOrg);
        assertNull(org.getDuplicateOf());
        org.setStatusCode(EntityStatus.REJECTED);
        org.setDuplicateOfOrg(dupOrg);
        assertSame(dupOrg, org.getDuplicateOf());
    }
}
