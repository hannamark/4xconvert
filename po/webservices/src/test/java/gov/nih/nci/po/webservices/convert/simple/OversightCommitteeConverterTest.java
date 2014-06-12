package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.OversightCommitteeType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the test class for OversightCommitteeConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class OversightCommitteeConverterTest extends AbstractRoleConverterTest {

    private gov.nih.nci.po.webservices.types.OversightCommittee overComm;

    private gov.nih.nci.po.data.bo.OversightCommittee overCommBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.OversightCommittee
        overComm = new OversightCommittee();
        overComm.setId(765l);
        overComm.setType(OversightCommitteeType.ETHICS_COMMITTEE);
        overComm.setStatus(EntityStatus.PENDING);
        populateOrganizationRoleJaxbObject(overComm);

        // setting up gov.nih.nci.po.data.bo.OversightCommittee
        overCommBo = new gov.nih.nci.po.data.bo.OversightCommittee();
        overCommBo.setId(765l);
        overCommBo
                .setTypeCode(new gov.nih.nci.po.data.bo.OversightCommitteeType(
                        "Research Ethics Board"));
        overCommBo.setStatus(RoleStatus.ACTIVE);
        populateOrganizationRoleBOObject(overCommBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        gov.nih.nci.po.data.bo.OversightCommittee retOCBo = ocConverter
                .convertFromJaxbToBO(overComm);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(overComm, retOCBo);

        // Assertion for OversightCommittee Address
        checkAddressForConvertJaxbToBO(overComm, retOCBo);

        // Assertion for OversightCommittee Contact
        checkContactsForConvertJaxbToBO(overComm, retOCBo);
    }

    @Test
    public void testConvertJaxbToBOForNullOversightCommittee() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        // OversightCommittee is null
        gov.nih.nci.po.data.bo.OversightCommittee retOCBo = ocConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retOCBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        // Address is null
        overComm.getAddress().clear();
        gov.nih.nci.po.data.bo.OversightCommittee retOCBo = ocConverter
                .convertFromJaxbToBO(overComm);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(overComm, retOCBo);

        // Assertion for OversightCommittee Address
        Assert.assertEquals(new HashSet<Address>(),
                retOCBo.getPostalAddresses());

        // Assertion for OversightCommittee Contact
        checkContactsForConvertJaxbToBO(overComm, retOCBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        // Contact is null
        overComm.getContact().clear();
        gov.nih.nci.po.data.bo.OversightCommittee retOCBo = ocConverter
                .convertFromJaxbToBO(overComm);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(overComm, retOCBo);

        // Assertion for OversightCommittee Address
        checkAddressForConvertJaxbToBO(overComm, retOCBo);

        // Assertion for OversightCommittee Contact
        Assert.assertEquals(new ArrayList<Email>(), retOCBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOCBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOCBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retOCBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retOCBo.getUrl());
    }

    @Test
    public void testConvertBOToJaxb() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        gov.nih.nci.po.webservices.types.OversightCommittee retOC = ocConverter
                .convertFromBOToJaxB(overCommBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(overCommBo, retOC);

        // Assertion for OversightCommittee Address
        checkAddressForConvertBOToJaxb(overCommBo, retOC);

        // Assertion for OversightCommittee Contact
        checkContactsForConvertBOToJaxb(overCommBo, retOC);
    }

    @Test
    public void testConvertBOToJaxbForNullOversightCommittee() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        // OversightCommittee is null
        gov.nih.nci.po.webservices.types.OversightCommittee retOC = ocConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retOC);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        // Address is null
        overCommBo.setPostalAddresses(null);
        gov.nih.nci.po.webservices.types.OversightCommittee retOC = ocConverter
                .convertFromBOToJaxB(overCommBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(overCommBo, retOC);

        // Assertion for OversightCommittee Address
        Assert.assertEquals(new ArrayList<Address>(), retOC.getAddress());

        // Assertion for OversightCommittee Contact
        checkContactsForConvertBOToJaxb(overCommBo, retOC);
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {
        OversightCommitteeConverter ocConverter = new OversightCommitteeConverter();
        // Contact is null
        overCommBo.getEmail().clear();
        overCommBo.getPhone().clear();
        overCommBo.getFax().clear();
        overCommBo.getTty().clear();
        overCommBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.OversightCommittee retOC = ocConverter
                .convertFromBOToJaxB(overCommBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(overCommBo, retOC);

        // Assertion for OversightCommittee Address
        checkAddressForConvertBOToJaxb(overCommBo, retOC);

        // Assertion for OversightCommittee Contact
        Assert.assertEquals(new ArrayList<Contact>(), retOC.getContact());
    }

    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.OversightCommittee overComm,
            gov.nih.nci.po.data.bo.OversightCommittee retOCBo) {
        Assert.assertEquals(overComm.getId(), retOCBo.getId());
        Assert.assertEquals(overComm.getType().value(), retOCBo.getTypeCode()
                .getCode());
        Assert.assertEquals(new Long(overComm.getOrganizationId()), retOCBo
                .getPlayer().getId());
        Assert.assertEquals(overComm.getStatus().value(), retOCBo.getStatus()
                .name());
    }

    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.OversightCommittee overCommBo,
            gov.nih.nci.po.webservices.types.OversightCommittee retOC) {
        Assert.assertEquals(overCommBo.getId(), retOC.getId());
        Assert.assertEquals(overCommBo.getTypeCode().getCode(), retOC.getType()
                .value());
        Assert.assertEquals(overCommBo.getPlayer().getId(),
                new Long(retOC.getOrganizationId()));
        Assert.assertEquals(overCommBo.getStatus().name(), retOC.getStatus()
                .value());
    }

}
