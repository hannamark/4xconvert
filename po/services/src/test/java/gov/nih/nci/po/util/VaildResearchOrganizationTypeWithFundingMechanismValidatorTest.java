package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.FundingMechanism;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;

import org.junit.Test;

public class VaildResearchOrganizationTypeWithFundingMechanismValidatorTest {
    VaildResearchOrganizationTypeWithFundingMechanismValidator validator = new VaildResearchOrganizationTypeWithFundingMechanismValidator();

    @Test
    public void ResearchOrgTypeWithFundingMechanismsButNoFundingMechanismGiven() {
        FundingMechanism fm = new FundingMechanism("BXX", "BXX desc", "Category", FundingMechanismStatus.ACTIVE);
        ResearchOrganizationType rot = new ResearchOrganizationType("AA", "AA Desc");
        rot.getFundingMechanisms().add(fm);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setTypeCode(rot);
        assertTrue(validator.isValid(ro));
    }

    @Test
    public void ResearchOrgTypeWithFundingMechanismsWithFundingMechanismGiven() {
        FundingMechanism fm = new FundingMechanism("BXX", "BXX desc", "Category", FundingMechanismStatus.ACTIVE);
        ResearchOrganizationType rot = new ResearchOrganizationType("AA", "AA Desc");
        rot.getFundingMechanisms().add(fm);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setTypeCode(rot);
        ro.setFundingMechanism(fm);
        assertTrue(validator.isValid(ro));
    }

    @Test
    public void ResearchOrgTypeWithNoFundingMechanismsWithFundingMechanismGiven() {
        FundingMechanism fm = new FundingMechanism("BXX", "BXX desc", "Category", FundingMechanismStatus.ACTIVE);
        ResearchOrganizationType rot = new ResearchOrganizationType("AA", "AA Desc");

        ResearchOrganization ro = new ResearchOrganization();
        ro.setTypeCode(rot);
        ro.setFundingMechanism(fm);
        assertFalse(validator.isValid(ro));
    }

    @Test
    public void ResearchOrgTypeWithFundingMechanismsWithFundingMechanismGivenButFundingMechanismIsNotRelatedToResearchOrgType() {
        FundingMechanism fm = new FundingMechanism("BXX", "BXX desc", "Category", FundingMechanismStatus.ACTIVE);
        FundingMechanism fm2 = new FundingMechanism("BYY", "BYY desc", "Category", FundingMechanismStatus.ACTIVE);
        ResearchOrganizationType rot = new ResearchOrganizationType("AA", "AA Desc");
        rot.getFundingMechanisms().add(fm2);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setTypeCode(rot);
        ro.setFundingMechanism(fm);
        assertFalse(validator.isValid(ro));
    }

    @Test
    public void NoResearchOrgTypeGivenWithFundingMechanismGiven() {
        FundingMechanism fm = new FundingMechanism("BXX", "BXX desc", "Category", FundingMechanismStatus.ACTIVE);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setTypeCode(null);
        ro.setFundingMechanism(fm);
        assertTrue(validator.isValid(ro));
    }

    @Test
    public void NoResearchOrgTypeGivenWithNoFundingMechanismGiven() {
        ResearchOrganization ro = new ResearchOrganization();
        ro.setTypeCode(null);
        ro.setFundingMechanism(null);
        assertTrue(validator.isValid(ro));
    }

}
