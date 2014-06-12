package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareProvider;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the test class for HealthCareProviderConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class HealthCareProviderConverterTest extends AbstractRoleConverterTest {

    private gov.nih.nci.po.webservices.types.HealthCareProvider hcp;

    private gov.nih.nci.po.data.bo.HealthCareProvider hcpBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.HealthCareProvider
        hcp = new HealthCareProvider();
        hcp.setId(45l);
        hcp.setLicense("Test license");
        hcp.setStatus(EntityStatus.PENDING);
        populatePersonRoleJaxbObject(hcp);

        // setting up gov.nih.nci.po.data.bo.HealthCareProvider
        hcpBo = new gov.nih.nci.po.data.bo.HealthCareProvider();
        hcpBo.setId(12l);
        hcpBo.setCertificateLicenseText("license");
        hcpBo.setStatus(RoleStatus.ACTIVE);
        populatePersonRoleBOObject(hcpBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        gov.nih.nci.po.data.bo.HealthCareProvider retHCPBo = hcpConverter
                .convertFromJaxbToBO(hcp);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(hcp, retHCPBo);

        // Assertion for HealthCareProvider Address
        checkAddressForConvertJaxbToBO(hcp, retHCPBo);

        // Assertion for HealthCareProvider Contact
        checkContactsForConvertJaxbToBO(hcp, retHCPBo);
    }

    @Test
    public void testConvertJaxbToBOForNullHealthCareProvider() {

        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        // HealthCareProvider is null
        gov.nih.nci.po.data.bo.HealthCareProvider retHCPBo = hcpConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retHCPBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        // Address is null
        hcp.getAddress().clear();
        gov.nih.nci.po.data.bo.HealthCareProvider retHCPBo = hcpConverter
                .convertFromJaxbToBO(hcp);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(hcp, retHCPBo);

        // Assertion for HealthCareProvider Address
        Assert.assertEquals(new HashSet<Address>(),
                retHCPBo.getPostalAddresses());

        // Assertion for HealthCareProvider Contact
        checkContactsForConvertJaxbToBO(hcp, retHCPBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        // Contact is null
        hcp.getContact().clear();
        gov.nih.nci.po.data.bo.HealthCareProvider retHCPBo = hcpConverter
                .convertFromJaxbToBO(hcp);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(hcp, retHCPBo);

        // Assertion for HealthCareProvider Address
        checkAddressForConvertJaxbToBO(hcp, retHCPBo);

        // Assertion for HealthCareProvider Contact
        Assert.assertEquals(new ArrayList<Email>(), retHCPBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retHCPBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retHCPBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retHCPBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retHCPBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        gov.nih.nci.po.webservices.types.HealthCareProvider retHCP = hcpConverter
                .convertFromBOToJaxB(hcpBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(hcpBo, retHCP);

        // Assertion for HealthCareProvider Address
        checkAddressForConvertBOToJaxb(hcpBo, retHCP);

        // Assertion for HealthCareProvider Contact
        checkContactsForConvertBOToJaxb(hcpBo, retHCP);
    }

    @Test
    public void testConvertBOToJaxbForNullHealthCareProvider() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        // HealthCareProvider is null
        gov.nih.nci.po.webservices.types.HealthCareProvider retHCP = hcpConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retHCP);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        // Address is null
        hcpBo.setPostalAddresses(null);
        gov.nih.nci.po.webservices.types.HealthCareProvider retHCP = hcpConverter
                .convertFromBOToJaxB(hcpBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(hcpBo, retHCP);

        // Assertion for HealthCareProvider Address
        Assert.assertEquals(new ArrayList<Address>(), retHCP.getAddress());

        // Assertion for HealthCareProvider Contact
        checkContactsForConvertBOToJaxb(hcpBo, retHCP);
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        HealthCareProviderConverter hcpConverter = new HealthCareProviderConverter();
        // Contact is null
        hcpBo.getEmail().clear();
        hcpBo.getPhone().clear();
        hcpBo.getFax().clear();
        hcpBo.getTty().clear();
        hcpBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.HealthCareProvider retHCP = hcpConverter
                .convertFromBOToJaxB(hcpBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(hcpBo, retHCP);

        // Assertion for HealthCareProvider Address
        checkAddressForConvertBOToJaxb(hcpBo, retHCP);

        // Assertion for HealthCareProvider Contact
        Assert.assertEquals(new ArrayList<Contact>(), retHCP.getContact());
    }

    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.HealthCareProvider hcp,
            gov.nih.nci.po.data.bo.HealthCareProvider retHCPBo) {
        Assert.assertEquals(hcp.getId(), retHCPBo.getId());
        Assert.assertEquals(hcp.getLicense(),
                retHCPBo.getCertificateLicenseText());
        Assert.assertEquals(new Long(hcp.getPersonId()), retHCPBo.getPlayer()
                .getId());
        Assert.assertEquals(new Long(hcp.getOrganizationId()), retHCPBo
                .getScoper().getId());
        Assert.assertEquals(hcp.getStatus().value(), retHCPBo.getStatus()
                .name());
    }

    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.HealthCareProvider hcpBo,
            gov.nih.nci.po.webservices.types.HealthCareProvider retHCP) {
        Assert.assertEquals(hcpBo.getId(), retHCP.getId());
        Assert.assertEquals(hcpBo.getCertificateLicenseText(),
                retHCP.getLicense());
        Assert.assertEquals(hcpBo.getPlayer().getId(),
                new Long(retHCP.getPersonId()));
        Assert.assertEquals(hcpBo.getScoper().getId(),
                new Long(retHCP.getOrganizationId()));
        Assert.assertEquals(hcpBo.getStatus().name(), retHCP.getStatus()
                .value());
    }

}
