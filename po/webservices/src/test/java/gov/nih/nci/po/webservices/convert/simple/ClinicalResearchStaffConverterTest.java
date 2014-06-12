package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the test class for ClinicalResearchStaffConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class ClinicalResearchStaffConverterTest extends
        AbstractRoleConverterTest {

    private gov.nih.nci.po.webservices.types.ClinicalResearchStaff crs;

    private gov.nih.nci.po.data.bo.ClinicalResearchStaff crsBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.ClinicalResearchStaff
        crs = new gov.nih.nci.po.webservices.types.ClinicalResearchStaff();
        crs.setId(15l);
        crs.setStatus(EntityStatus.ACTIVE);
        populatePersonRoleJaxbObject(crs);

        // setting up gov.nih.nci.po.data.bo.ClinicalResearchStaff
        crsBo = new gov.nih.nci.po.data.bo.ClinicalResearchStaff();
        crsBo.setId(21l);
        crsBo.setStatus(RoleStatus.NULLIFIED);
        populatePersonRoleBOObject(crsBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {

        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        gov.nih.nci.po.data.bo.ClinicalResearchStaff retCRSBo = crsConverter
                .convertFromJaxbToBO(crs);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(crs, retCRSBo);

        // Assertion for ClinicalResearchStaff Address
        checkAddressForConvertJaxbToBO(crs, retCRSBo);

        // Assertion for ClinicalResearchStaff Contact
        checkContactsForConvertJaxbToBO(crs, retCRSBo);
    }

    @Test
    public void testConvertJaxbToBOForNullOrganizationalContact() {

        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // ClinicalResearchStaff is null
        gov.nih.nci.po.data.bo.ClinicalResearchStaff retCRSBo = crsConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retCRSBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // Address is null
        crs.getAddress().clear();
        gov.nih.nci.po.data.bo.ClinicalResearchStaff retCRSBo = crsConverter
                .convertFromJaxbToBO(crs);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(crs, retCRSBo);

        // Assertion for ClinicalResearchStaff Address
        Assert.assertEquals(new HashSet<Address>(),
                retCRSBo.getPostalAddresses());

        // Assertion for ClinicalResearchStaff Contact
        checkContactsForConvertJaxbToBO(crs, retCRSBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // Contact is null
        crs.getContact().clear();
        gov.nih.nci.po.data.bo.ClinicalResearchStaff retCRSBo = crsConverter
                .convertFromJaxbToBO(crs);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(crs, retCRSBo);

        // Assertion for ClinicalResearchStaff Address
        checkAddressForConvertJaxbToBO(crs, retCRSBo);

        // Assertion for ClinicalResearchStaff Contact
        Assert.assertEquals(new ArrayList<Email>(), retCRSBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retCRSBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retCRSBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retCRSBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retCRSBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        gov.nih.nci.po.webservices.types.ClinicalResearchStaff retCRS = crsConverter
                .convertFromBOToJaxB(crsBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Address
        checkAddressForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Contact
        checkContactsForConvertBOToJaxb(crsBo, retCRS);
    }

    @Test
    public void testConvertBOToJaxbForNullClinicalResearchStaff() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // ClinicalResearchStaff is null
        gov.nih.nci.po.webservices.types.ClinicalResearchStaff retCRS = crsConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retCRS);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // Address is null
        crsBo.setPostalAddresses(null);
        gov.nih.nci.po.webservices.types.ClinicalResearchStaff retCRS = crsConverter
                .convertFromBOToJaxB(crsBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Address
        Assert.assertEquals(new ArrayList<Address>(), retCRS.getAddress());

        // Assertion for ClinicalResearchStaff Contact
        checkContactsForConvertBOToJaxb(crsBo, retCRS);

    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // Contact is null
        crsBo.getEmail().clear();
        crsBo.getPhone().clear();
        crsBo.getFax().clear();
        crsBo.getTty().clear();
        crsBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.ClinicalResearchStaff retCRS = crsConverter
                .convertFromBOToJaxB(crsBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Address
        checkAddressForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Contact
        Assert.assertEquals(new ArrayList<Contact>(), retCRS.getContact());
    }

    @Test
    public void testConvertBOToJaxbForNullPhoneContact() {
        ClinicalResearchStaffConverter crsConverter = new ClinicalResearchStaffConverter();
        // Phone Contact is null
        crsBo.getPhone().clear();

        gov.nih.nci.po.webservices.types.ClinicalResearchStaff retCRS = crsConverter
                .convertFromBOToJaxB(crsBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Address
        checkAddressForConvertBOToJaxb(crsBo, retCRS);

        // Assertion for ClinicalResearchStaff Contact
        Assert.assertNotNull(retCRS.getContact());

        for (Contact contact : retCRS.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(crsBo.getEmail().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.fail("Testcase failed as returned ContactList is containing PHONE which is not expected.");
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(crsBo.getFax().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(crsBo.getTty().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(crsBo.getUrl().get(0).getValue(),
                        contact.getValue());
            }
        }

    }

    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.ClinicalResearchStaff crs,
            gov.nih.nci.po.data.bo.ClinicalResearchStaff retCRSBo) {
        Assert.assertEquals(new Long(crs.getId()), retCRSBo.getId());
        Assert.assertEquals(new Long(crs.getPersonId()), retCRSBo.getPlayer()
                .getId());
        Assert.assertEquals(new Long(crs.getOrganizationId()), retCRSBo
                .getScoper().getId());
        Assert.assertEquals(crs.getStatus().value(), retCRSBo.getStatus()
                .name());
    }

    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.ClinicalResearchStaff crsBo,
            gov.nih.nci.po.webservices.types.ClinicalResearchStaff retCRS) {
        Assert.assertEquals(crsBo.getId(), new Long(retCRS.getId()));
        Assert.assertEquals(crsBo.getPlayer().getId(),
                new Long(retCRS.getPersonId()));
        Assert.assertEquals(crsBo.getScoper().getId(),
                new Long(retCRS.getOrganizationId()));
        Assert.assertEquals(crsBo.getStatus().name(), retCRS.getStatus()
                .value());
    }

}
