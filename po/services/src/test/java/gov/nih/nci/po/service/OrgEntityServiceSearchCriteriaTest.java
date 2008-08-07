package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.GetterSetterTesterUtil;
import gov.nih.nci.po.data.bo.Organization;

import org.junit.Assert;
import org.junit.Test;

public class OrgEntityServiceSearchCriteriaTest {

    @Test
    public void testGettersAndSetters() throws Exception {
        AbstractOrganizationSearchCriteria osc = new OrgEntityServiceSearchCriteria();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(osc);
    }

    @Test
    public void testHasAtLeastOneCriterionSpecified() {
        OrgEntityServiceSearchCriteria noCrit = new OrgEntityServiceSearchCriteria();
        OrgEntityServiceSearchCriteria yesCrit = new OrgEntityServiceSearchCriteria();
        Organization o = new Organization();
        o.setName("name");
        yesCrit.setOrganization(o);
        Assert.assertTrue(!noCrit.hasOneCriterionSpecified() && yesCrit.hasOneCriterionSpecified());
    }

}
