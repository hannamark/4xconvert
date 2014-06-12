package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.OrganizationalContact;
import gov.nih.nci.po.webservices.types.OrganizationalContactType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the test class for OrganizationalContactConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class OrganizationalContactConverterTest extends
        AbstractRoleConverterTest {

    private gov.nih.nci.po.webservices.types.OrganizationalContact orgContact;

    private gov.nih.nci.po.data.bo.OrganizationalContact orgContactBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.OrganizationalContact
        orgContact = new OrganizationalContact();
        orgContact.setId(56l);
        orgContact.setTitle("My Test Title");
        orgContact.setType(OrganizationalContactType.IRB);
        orgContact.setStatus(EntityStatus.PENDING);
        populatePersonRoleJaxbObject(orgContact);

        // setting up gov.nih.nci.po.data.bo.OrganizationalContact
        orgContactBo = new gov.nih.nci.po.data.bo.OrganizationalContact();
        orgContactBo.setId(1234l);
        orgContactBo.setTitle("Test Title BO");
        orgContactBo
                .setType(new gov.nih.nci.po.data.bo.OrganizationalContactType(
                        "Responsible Party"));
        orgContactBo.setStatus(RoleStatus.ACTIVE);
        populatePersonRoleBOObject(orgContactBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {

        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        gov.nih.nci.po.data.bo.OrganizationalContact retOCBo = ocConverter
                .convertFromJaxbToBO(orgContact);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(orgContact, retOCBo);

        // Assertion for OrganizationalContact Address
        checkAddressForConvertJaxbToBO(orgContact, retOCBo);

        // Assertion for OrganizationalContact Contact
        checkContactsForConvertJaxbToBO(orgContact, retOCBo);
    }

    @Test
    public void testConvertJaxbToBOForNullOrganizationalContact() {

        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        // OrganizationalContact is null
        gov.nih.nci.po.data.bo.OrganizationalContact retOCBo = ocConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retOCBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        // Address is null
        orgContact.getAddress().clear();
        gov.nih.nci.po.data.bo.OrganizationalContact retOCBo = ocConverter
                .convertFromJaxbToBO(orgContact);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(orgContact, retOCBo);

        // Assertion for OrganizationalContact Address
        Assert.assertEquals(new HashSet<Address>(),
                retOCBo.getPostalAddresses());

        // Assertion for OrganizationalContact Contact
        checkContactsForConvertJaxbToBO(orgContact, retOCBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        // Contact is null
        orgContact.getContact().clear();
        gov.nih.nci.po.data.bo.OrganizationalContact retOCBo = ocConverter
                .convertFromJaxbToBO(orgContact);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(orgContact, retOCBo);

        // Assertion for OrganizationalContact Address
        checkAddressForConvertJaxbToBO(orgContact, retOCBo);

        // Assertion for OrganizationalContact Contact
        Assert.assertEquals(new ArrayList<Email>(), retOCBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOCBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOCBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOCBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retOCBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        gov.nih.nci.po.webservices.types.OrganizationalContact retOC = ocConverter
                .convertFromBOToJaxB(orgContactBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(orgContactBo, retOC);

        // Assertion for OrganizationalContact Address
        checkAddressForConvertBOToJaxb(orgContactBo, retOC);

        // Assertion for OrganizationalContact Contact
        checkContactsForConvertBOToJaxb(orgContactBo, retOC);
    }

    @Test
    public void testConvertBOToJaxbForNullOrganizationalContact() {
        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        // OrganizationalContact is null
        gov.nih.nci.po.webservices.types.OrganizationalContact retOC = ocConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retOC);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        // Address is null
        orgContactBo.setPostalAddresses(null);
        gov.nih.nci.po.webservices.types.OrganizationalContact retOC = ocConverter
                .convertFromBOToJaxB(orgContactBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(orgContactBo, retOC);

        // Assertion for OrganizationalContact Address
        Assert.assertEquals(new ArrayList<Address>(), retOC.getAddress());

        // Assertion for OrganizationalContact Contact
        checkContactsForConvertBOToJaxb(orgContactBo, retOC);
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        OrganizationalContactConverter ocConverter = new OrganizationalContactConverter();
        // Contact is null
        orgContactBo.getEmail().clear();
        orgContactBo.getPhone().clear();
        orgContactBo.getFax().clear();
        orgContactBo.getTty().clear();
        orgContactBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.OrganizationalContact retOC = ocConverter
                .convertFromBOToJaxB(orgContactBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(orgContactBo, retOC);

        // Assertion for OrganizationalContact Address
        checkAddressForConvertBOToJaxb(orgContactBo, retOC);

        // Assertion for OrganizationalContact Contact
        Assert.assertEquals(new ArrayList<Contact>(), retOC.getContact());
    }

    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationalContact orgContact,
            gov.nih.nci.po.data.bo.OrganizationalContact retOCBo) {
        Assert.assertEquals(orgContact.getId(), retOCBo.getId());
        Assert.assertEquals(orgContact.getTitle(), retOCBo.getTitle());
        Assert.assertEquals(orgContact.getType().value(), retOCBo.getType()
                .getCode());
        Assert.assertEquals(new Long(orgContact.getPersonId()), retOCBo
                .getPlayer().getId());
        Assert.assertEquals(new Long(orgContact.getOrganizationId()), retOCBo
                .getScoper().getId());
        Assert.assertEquals(orgContact.getStatus().value(), retOCBo.getStatus()
                .name());
    }

    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.OrganizationalContact orgContactBo,
            gov.nih.nci.po.webservices.types.OrganizationalContact retOC) {
        Assert.assertEquals(orgContactBo.getId(), retOC.getId());
        Assert.assertEquals(orgContactBo.getTitle(), retOC.getTitle());
        Assert.assertEquals(orgContactBo.getType().getCode(), retOC.getType()
                .value());
        Assert.assertEquals(orgContactBo.getPlayer().getId(),
                new Long(retOC.getPersonId()));
        Assert.assertEquals(orgContactBo.getScoper().getId(),
                new Long(retOC.getOrganizationId()));
        Assert.assertEquals(orgContactBo.getStatus().name(), retOC.getStatus()
                .value());
    }

}
