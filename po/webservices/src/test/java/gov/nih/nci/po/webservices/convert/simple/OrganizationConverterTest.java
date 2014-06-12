package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Organization;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * This is the test class for OrganizationConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class OrganizationConverterTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.Organization organization;

    private gov.nih.nci.po.data.bo.Organization organizationBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.Organization
        organization = new Organization();
        organization.setId(345l);
        organization.setName("Mayo Clinic");
        organization.setStatus(EntityStatus.ACTIVE);

        organization.setAddress(getJaxbAddressList().get(0));

        organization.getContact().addAll(getJaxbContactList());

        // setting up gov.nih.nci.po.data.bo.Organization
        organizationBo = new gov.nih.nci.po.data.bo.Organization();
        organizationBo.setId(1234l);
        organizationBo.setName("Saint George Hospital");
        organizationBo
                .setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.NULLIFIED);

        organizationBo.setPostalAddress(getBoAddressList().get(0));

        populateBOContacts(organizationBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        OrganizationConverter oConverter = new OrganizationConverter();
        gov.nih.nci.po.data.bo.Organization retOrgBo = oConverter
                .convertFromJaxbToBO(organization);

        Assert.assertEquals(organization.getId(), retOrgBo.getId());
        Assert.assertEquals(organization.getName(), retOrgBo.getName());
        Assert.assertEquals(organization.getStatus().value(), retOrgBo
                .getStatusCode().name());

        // Assertion for Organization Address
        checkAddressForConvertJaxbToBO(organization, retOrgBo);

        // Assertion for Organization Contact
        checkContactsForConvertJaxbToBO(organization, retOrgBo);
    }

    @Test
    public void testConvertJaxbToBOForNullOrganization() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Organization is null
        gov.nih.nci.po.data.bo.Organization retOrgBo = oConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retOrgBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Address is null
        organization.setAddress(null);
        gov.nih.nci.po.data.bo.Organization retOrgBo = oConverter
                .convertFromJaxbToBO(organization);

        Assert.assertEquals(organization.getName(), retOrgBo.getName());

        // Assertion for Organization Address
        Assert.assertEquals(null, retOrgBo.getPostalAddress());

        // Assertion for Organization Contact
        checkContactsForConvertJaxbToBO(organization, retOrgBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Contact is null
        organization.getContact().clear();
        gov.nih.nci.po.data.bo.Organization retOrgBo = oConverter
                .convertFromJaxbToBO(organization);

        Assert.assertEquals(organization.getName(), retOrgBo.getName());

        // Assertion for Organization Address
        checkAddressForConvertJaxbToBO(organization, retOrgBo);

        // Assertion for Organization Contact
        Assert.assertEquals(new ArrayList<Email>(), retOrgBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOrgBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOrgBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOrgBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retOrgBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        OrganizationConverter oConverter = new OrganizationConverter();
        gov.nih.nci.po.webservices.types.Organization retOrganization = oConverter
                .convertFromBOToJaxB(organizationBo);

        Assert.assertEquals(organizationBo.getId(), retOrganization.getId());
        Assert.assertEquals(organizationBo.getName(), retOrganization.getName());
        Assert.assertEquals(organizationBo.getStatusCode().name(),
                retOrganization.getStatus().value());

        // Assertion for Organization Address
        checkAddressForConvertBOToJaxb(organizationBo, retOrganization);

        // Assertion for Organization Contact
        checkContactsForConvertBOToJaxb(organizationBo, retOrganization);
    }

    @Test
    public void testConvertBOToJaxbForNullOrganization() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Organization is null
        gov.nih.nci.po.webservices.types.Organization retOrganization = oConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retOrganization);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Address is null
        organizationBo.setPostalAddress(null);
        gov.nih.nci.po.webservices.types.Organization retOrganization = oConverter
                .convertFromBOToJaxB(organizationBo);

        Assert.assertEquals(organizationBo.getName(), retOrganization.getName());

        // Assertion for Organization Address
        Assert.assertEquals(null, retOrganization.getAddress());

        // Assertion for Organization Contact
        checkContactsForConvertBOToJaxb(organizationBo, retOrganization);
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Contact is null
        organizationBo.getEmail().clear();
        organizationBo.getPhone().clear();
        organizationBo.getFax().clear();
        organizationBo.getTty().clear();
        organizationBo.getUrl().clear();
        gov.nih.nci.po.webservices.types.Organization retOrganization = oConverter
                .convertFromBOToJaxB(organizationBo);

        Assert.assertEquals(organizationBo.getName(), retOrganization.getName());

        // Assertion for Organization Address
        checkAddressForConvertBOToJaxb(organizationBo, retOrganization);

        // Assertion for Organization Contact
        Assert.assertEquals(new ArrayList<Contact>(),
                retOrganization.getContact());
    }

    @Test
    public void testConvertBOToJaxbForNullCtep() {
        OrganizationConverter oConverter = new OrganizationConverter();
        // Ctep is null
        organizationBo.getIdentifiedOrganizations().clear();
        gov.nih.nci.po.webservices.types.Organization retOrganization = oConverter
                .convertFromBOToJaxB(organizationBo);

        Assert.assertEquals(organizationBo.getName(), retOrganization.getName());

        // Assertion for Organization Address
        checkAddressForConvertBOToJaxb(organizationBo, retOrganization);

        // Assertion for Organization Contact
        checkContactsForConvertBOToJaxb(organizationBo, retOrganization);
    }

    private void checkAddressForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Organization organization,
            gov.nih.nci.po.data.bo.Organization retOrgBo) {
        Assert.assertEquals(organization.getAddress().getLine1(), retOrgBo
                .getPostalAddress().getStreetAddressLine());
        Assert.assertEquals(organization.getAddress().getLine2(), retOrgBo
                .getPostalAddress().getDeliveryAddressLine());
        Assert.assertEquals(organization.getAddress().getCity(), retOrgBo
                .getPostalAddress().getCityOrMunicipality());
        Assert.assertEquals(organization.getAddress().getStateOrProvince(),
                retOrgBo.getPostalAddress().getStateOrProvince());
        Assert.assertEquals(organization.getAddress().getCountry().name(),
                retOrgBo.getPostalAddress().getCountry().getAlpha3());
    }

    private void checkContactsForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Organization organization,
            gov.nih.nci.po.data.bo.Organization retOrgBo) {
        Assert.assertEquals("my.email@mayoclinic.org",
                retOrgBo.getEmail().get(0).getValue());
        Assert.assertEquals("571-456-1245", retOrgBo.getPhone().get(0)
                .getValue());
        Assert.assertEquals("571-456-1245", retOrgBo.getFax().get(0).getValue());
        Assert.assertEquals("571-123-1123", retOrgBo.getTty().get(0).getValue());
        Assert.assertEquals("http://www.mayoclinic.org",
                retOrgBo.getUrl().get(0).getValue());
    }

    private void checkAddressForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Organization organizationBo,
            gov.nih.nci.po.webservices.types.Organization retOrganization) {

        Assert.assertEquals(organizationBo.getPostalAddress()
                .getStreetAddressLine(), retOrganization.getAddress()
                .getLine1());
        Assert.assertEquals(organizationBo.getPostalAddress()
                .getDeliveryAddressLine(), retOrganization.getAddress()
                .getLine2());
        Assert.assertEquals(organizationBo.getPostalAddress()
                .getCityOrMunicipality(), retOrganization.getAddress()
                .getCity());
        Assert.assertEquals(organizationBo.getPostalAddress()
                .getStateOrProvince(), retOrganization.getAddress()
                .getStateOrProvince());
        Assert.assertEquals(organizationBo.getPostalAddress().getCountry()
                .getAlpha3(), retOrganization.getAddress().getCountry().name());
    }

    private void checkContactsForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Organization organizationBo,
            gov.nih.nci.po.webservices.types.Organization retOrganization) {
        for (Contact contact : retOrganization.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(
                        organizationBo.getEmail().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(
                        organizationBo.getPhone().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(organizationBo.getFax().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(organizationBo.getTty().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(organizationBo.getUrl().get(0).getValue(),
                        contact.getValue());
            }
        }

        Assert.assertEquals("my.test@nci.gov", retOrganization.getContact()
                .get(0).getValue());
        Assert.assertEquals("571-563-0987", retOrganization.getContact().get(1)
                .getValue());
        Assert.assertEquals("571-576-0912", retOrganization.getContact().get(2)
                .getValue());
        Assert.assertEquals("571-123-4567", retOrganization.getContact().get(3)
                .getValue());
        Assert.assertEquals("http://nih.gov",
                retOrganization.getContact().get(4).getValue());
    }

}
