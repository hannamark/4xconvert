package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.common.CurationStatus;

import org.junit.Test;

public class OrganizationBehaviorTest {
    
    @Test
    public void testBasicGetterSetters() {
        Organization org = new Organization();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "name");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "abbreviationName");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "primaryContactInfo");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(org, "curationStatus");
    }

    @Test
    public void testDuplicateBehavior() {
        Organization org = new Organization();
        Organization dupOrg = new Organization();
        org.setCurationStatus(CurationStatus.CURATED);
        org.setDuplicateOf(dupOrg);
        assertNull(org.getDuplicateOf());
        org.setCurationStatus(CurationStatus.REJECTED);
        org.setDuplicateOf(dupOrg);
        assertSame(dupOrg, org.getDuplicateOf());       
    }
}
