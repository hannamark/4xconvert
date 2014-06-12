package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the test class for HealthCareFacility.
 * 
 * @author Rohit Gupta
 * 
 */
public class HealthCareFacilityConverterTest extends AbstractRoleConverterTest {

    private gov.nih.nci.po.webservices.types.HealthCareFacility hcf;

    private gov.nih.nci.po.data.bo.HealthCareFacility hcfBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.HealthCareFacility
        hcf = new HealthCareFacility();
        hcf.setId(34l);
        hcf.setName("National Cancer Institute");
        hcf.setStatus(EntityStatus.INACTIVE);
        hcf.setCtepId("345678");
        populateOrganizationRoleJaxbObject(hcf);

        // setting up gov.nih.nci.po.data.bo.HealthCareFacility
        hcfBo = new gov.nih.nci.po.data.bo.HealthCareFacility();
        hcfBo.setId(23l);
        hcfBo.setName("Mayo Clinic");
        hcfBo.setStatus(RoleStatus.ACTIVE);
        gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
        assIden.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
        assIden.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
        assIden.setExtension("34565432");
        hcfBo.getOtherIdentifiers().add(assIden);
        populateOrganizationRoleBOObject(hcfBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        gov.nih.nci.po.data.bo.HealthCareFacility retHCFBo = hcfConverter
                .convertFromJaxbToBO(hcf);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(hcf, retHCFBo);

        // Assertion for HealthCareFacility Address
        checkAddressForConvertJaxbToBO(hcf, retHCFBo);

        // Assertion for HealthCareFacility Contact
        checkContactsForConvertJaxbToBO(hcf, retHCFBo);
    }

    @Test
    public void testConvertJaxbToBOForNullHealthCareFacility() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        // HealthCareFacility is null
        gov.nih.nci.po.data.bo.HealthCareFacility retHCFBo = hcfConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retHCFBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        // Address is null
        hcf.getAddress().clear();
        gov.nih.nci.po.data.bo.HealthCareFacility retHCFBo = hcfConverter
                .convertFromJaxbToBO(hcf);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(hcf, retHCFBo);

        // Assertion for HealthCareFacility Address
        Assert.assertEquals(new HashSet<Address>(),
                retHCFBo.getPostalAddresses());

        // Assertion for HealthCareFacility Contact
        checkContactsForConvertJaxbToBO(hcf, retHCFBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        // Contact is null
        hcf.getContact().clear();
        gov.nih.nci.po.data.bo.HealthCareFacility retHCFBo = hcfConverter
                .convertFromJaxbToBO(hcf);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(hcf, retHCFBo);

        // Assertion for HealthCareFacility Address
        checkAddressForConvertJaxbToBO(hcf, retHCFBo);

        // Assertion for HealthCareFacility Contact
        Assert.assertEquals(new ArrayList<Email>(), retHCFBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retHCFBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retHCFBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retHCFBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retHCFBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        gov.nih.nci.po.webservices.types.HealthCareFacility retHCF = hcfConverter
                .convertFromBOToJaxB(hcfBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(hcfBo, retHCF);

        // Assertion for HealthCareFacility Address
        checkAddressForConvertBOToJaxb(hcfBo, retHCF);

        // Assertion for HealthCareFacility Contact
        checkContactsForConvertBOToJaxb(hcfBo, retHCF);
    }

    @Test
    public void testConvertBOToJaxbForNullHealthCareFacility() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        // HealthCareFacility is null
        gov.nih.nci.po.webservices.types.HealthCareFacility retHCF = hcfConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retHCF);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        // Address is null
        hcfBo.setPostalAddresses(null);
        gov.nih.nci.po.webservices.types.HealthCareFacility retHCF = hcfConverter
                .convertFromBOToJaxB(hcfBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(hcfBo, retHCF);

        // Assertion for HealthCareFacility Address
        Assert.assertEquals(new ArrayList<Address>(), retHCF.getAddress());

        // Assertion for HealthCareFacility Contact
        checkContactsForConvertBOToJaxb(hcfBo, retHCF);
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        HealthCareFacilityConverter hcfConverter = new HealthCareFacilityConverter();
        // Contact is null
        hcfBo.getEmail().clear();
        hcfBo.getPhone().clear();
        hcfBo.getFax().clear();
        hcfBo.getTty().clear();
        hcfBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.HealthCareFacility retHCF = hcfConverter
                .convertFromBOToJaxB(hcfBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(hcfBo, retHCF);

        // Assertion for HealthCareFacility Address
        checkAddressForConvertBOToJaxb(hcfBo, retHCF);

        // Assertion for HealthCareFacility Contact
        Assert.assertEquals(new ArrayList<Contact>(), retHCF.getContact());
    }

    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.HealthCareFacility hcf,
            gov.nih.nci.po.data.bo.HealthCareFacility retHCFBo) {
        Assert.assertEquals(hcf.getId(), retHCFBo.getId());
        Assert.assertEquals(hcf.getName(), retHCFBo.getName());
        Assert.assertEquals(new Long(hcf.getOrganizationId()), retHCFBo
                .getPlayer().getId());
        // If EntityStatus is 'INACTIVE' then it should be mapped to 'SUSPENDED'
        if (EntityStatus.INACTIVE.value().equalsIgnoreCase(
                hcf.getStatus().value())) {
            Assert.assertEquals(RoleStatus.SUSPENDED.name(), retHCFBo
                    .getStatus().name());
        } else {
            Assert.assertEquals(hcf.getStatus().value(), retHCFBo.getStatus()
                    .name());
        }

        String boCtepId = PoServiceUtil.getOrgRoleBoCtepId(retHCFBo);
        Assert.assertEquals(hcf.getCtepId(), boCtepId);
    }

    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.HealthCareFacility hcfBo,
            gov.nih.nci.po.webservices.types.HealthCareFacility retHCF) {
        Assert.assertEquals(hcfBo.getId(), retHCF.getId());
        Assert.assertEquals(hcfBo.getName(), retHCF.getName());
        Assert.assertEquals(hcfBo.getPlayer().getId(),
                new Long(retHCF.getOrganizationId()));
        Assert.assertEquals(hcfBo.getStatus().name(), retHCF.getStatus()
                .value());

        String boCtepId = PoServiceUtil.getOrgRoleBoCtepId(hcfBo);
        Assert.assertEquals(boCtepId, retHCF.getCtepId());
    }

}
