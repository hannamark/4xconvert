package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.FundingMechanism;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.types.ResearchOrganizationType;

import java.util.ArrayList;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test class for ResearchOrganizationConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class ResearchOrganizationConverterTest extends
        AbstractRoleConverterTest {

    private gov.nih.nci.po.webservices.types.ResearchOrganization researchOrg;

    private gov.nih.nci.po.data.bo.ResearchOrganization researchOrgBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.ResearchOrganization
        researchOrg = new ResearchOrganization();
        researchOrg.setId(654l);
        researchOrg.setName("National Cancer Institute");
        researchOrg.setType(ResearchOrganizationType.CCR);
        researchOrg.setFundingMechanism(FundingMechanism.P_30);
        researchOrg.setStatus(EntityStatus.NULLIFIED);
        researchOrg.setCtepId("1234567");
        populateOrganizationRoleJaxbObject(researchOrg);

        // setting up gov.nih.nci.po.data.bo.ResearchOrganization
        researchOrgBo = new gov.nih.nci.po.data.bo.ResearchOrganization();
        researchOrgBo.setId(765l);
        researchOrgBo.setName("Mayo Clinic");
        researchOrgBo
                .setTypeCode(new gov.nih.nci.po.data.bo.ResearchOrganizationType(
                        "RSB", "description"));
        researchOrgBo.setFundingMechanism(
                new gov.nih.nci.po.data.bo.FundingMechanism("U10", "Cooperative Clinical Research Cooperative Agreements", 
                        "Cooperative Agreements", FundingMechanismStatus.ACTIVE));
        // set status to 'SUSPENDED' to test Status mapping to INACTIVE
        researchOrgBo.setStatus(RoleStatus.SUSPENDED);
        gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
        assIden.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
        assIden.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
        assIden.setExtension("9898909");
        researchOrgBo.getOtherIdentifiers().add(assIden);
        populateOrganizationRoleBOObject(researchOrgBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        gov.nih.nci.po.data.bo.ResearchOrganization retROBo = roConverter
                .convertFromJaxbToBO(researchOrg);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(researchOrg, retROBo);

        // Assertion for ResearchOrganization Address
        checkAddressForConvertJaxbToBO(researchOrg, retROBo);

        // Assertion for ResearchOrganization Contact
        checkContactsForConvertJaxbToBO(researchOrg, retROBo);
    }

    @Test
    public void testConvertJaxbToBOForNullResearchOrganization() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        // ResearchOrganization is null
        gov.nih.nci.po.data.bo.ResearchOrganization retROBo = roConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retROBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        // Address is null
        researchOrg.getAddress().clear();
        gov.nih.nci.po.data.bo.ResearchOrganization retROBo = roConverter
                .convertFromJaxbToBO(researchOrg);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(researchOrg, retROBo);

        // Assertion for ResearchOrganization Address
        Assert.assertEquals(new HashSet<Address>(),
                retROBo.getPostalAddresses());

        // Assertion for ResearchOrganization Contact
        checkContactsForConvertJaxbToBO(researchOrg, retROBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        // Contact is null
        researchOrg.getContact().clear();
        gov.nih.nci.po.data.bo.ResearchOrganization retROBo = roConverter
                .convertFromJaxbToBO(researchOrg);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(researchOrg, retROBo);

        // Assertion for ResearchOrganization Address
        checkAddressForConvertJaxbToBO(researchOrg, retROBo);

        // Assertion for ResearchOrganization Contact
        Assert.assertEquals(new ArrayList<Email>(), retROBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retROBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retROBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retROBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retROBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        gov.nih.nci.po.webservices.types.ResearchOrganization retRO = roConverter
                .convertFromBOToJaxB(researchOrgBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(researchOrgBo, retRO);

        // Assertion for ResearchOrganization Address
        checkAddressForConvertBOToJaxb(researchOrgBo, retRO);

        // Assertion for ResearchOrganization Contact
        checkContactsForConvertBOToJaxb(researchOrgBo, retRO);
    }

    @Test
    public void testConvertBOToJaxbForNullResearchOrganization() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        // ResearchOrganization is null
        gov.nih.nci.po.webservices.types.ResearchOrganization retRO = roConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retRO);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        // Address is null
        researchOrgBo.setPostalAddresses(null);
        gov.nih.nci.po.webservices.types.ResearchOrganization retRO = roConverter
                .convertFromBOToJaxB(researchOrgBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(researchOrgBo, retRO);

        // Assertion for ResearchOrganization Address
        Assert.assertEquals(new ArrayList<Address>(), retRO.getAddress());

        // Assertion for ResearchOrganization Contact
        checkContactsForConvertBOToJaxb(researchOrgBo, retRO);
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        ResearchOrganizationConverter roConverter = new ResearchOrganizationConverter();
        // Contact is null
        researchOrgBo.getEmail().clear();
        researchOrgBo.getPhone().clear();
        researchOrgBo.getFax().clear();
        researchOrgBo.getTty().clear();
        researchOrgBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.ResearchOrganization retRO = roConverter
                .convertFromBOToJaxB(researchOrgBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(researchOrgBo, retRO);

        // Assertion for ResearchOrganization Address
        checkAddressForConvertBOToJaxb(researchOrgBo, retRO);

        // Assertion for ResearchOrganization Contact
        Assert.assertEquals(new ArrayList<Contact>(), retRO.getContact());
    }

    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.ResearchOrganization researchOrg,
            gov.nih.nci.po.data.bo.ResearchOrganization retROBo) {
        Assert.assertEquals(researchOrg.getId(), retROBo.getId());
        Assert.assertEquals(researchOrg.getName(), retROBo.getName());
        Assert.assertEquals(researchOrg.getType().value(), retROBo
                .getTypeCode().getCode());
        Assert.assertEquals(researchOrg.getFundingMechanism().value(), retROBo
                .getFundingMechanism().getCode());
        Assert.assertEquals(new Long(researchOrg.getOrganizationId()), retROBo
                .getPlayer().getId());
        Assert.assertEquals(researchOrg.getStatus().value(), retROBo
                .getStatus().name());

        String boCtepId = PoServiceUtil.getOrgRoleBoCtepId(retROBo);
        Assert.assertEquals(researchOrg.getCtepId(), boCtepId);
    }

    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.ResearchOrganization researchOrgBo,
            gov.nih.nci.po.webservices.types.ResearchOrganization retRO) {
        Assert.assertEquals(researchOrgBo.getId(), retRO.getId());
        Assert.assertEquals(researchOrgBo.getName(), retRO.getName());
        Assert.assertEquals(researchOrgBo.getTypeCode().getCode(), retRO
                .getType().value());
        Assert.assertEquals(researchOrgBo.getFundingMechanism().getCode(), retRO
                .getFundingMechanism().value());
        Assert.assertEquals(researchOrgBo.getPlayer().getId(),
                new Long(retRO.getOrganizationId()));
        // If RoleStatus is 'SUSPENDED' then it should be mapped to 'INACTIVE'
        if (RoleStatus.SUSPENDED.name().equalsIgnoreCase(
                researchOrgBo.getStatus().name())) {
            Assert.assertEquals(EntityStatus.INACTIVE.value(), retRO
                    .getStatus().value());
        } else {
            Assert.assertEquals(researchOrgBo.getStatus().name(), retRO
                    .getStatus().value());
        }

        String boCtepId = PoServiceUtil.getOrgRoleBoCtepId(researchOrgBo);
        Assert.assertEquals(boCtepId, retRO.getCtepId());
    }

}
