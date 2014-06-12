package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Person;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This is the test class for PersonConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class PersonConverterTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.Person person;

    private gov.nih.nci.po.data.bo.Person personBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.Person
        person = new Person();
        person.setId(987l);
        person.setPrefix("Mr.");
        person.setFirstName("John");
        person.setMiddleName("L");
        person.setLastName("Doe");
        person.setSuffix("Sr");
        person.setStatus(EntityStatus.PENDING);

        person.setAddress(getJaxbAddressList().get(0));

        person.setCtepId("25879");

        person.getContact().addAll(getJaxbContactList());

        // setting up gov.nih.nci.po.data.bo.Person
        personBo = new gov.nih.nci.po.data.bo.Person();
        personBo.setId(765l);
        personBo.setPrefix("Ms");
        personBo.setFirstName("Nancy");
        personBo.setMiddleName("G");
        personBo.setLastName("Brown");
        personBo.setSuffix("IV");
        personBo.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.INACTIVE);

        personBo.setPostalAddress(getBoAddressList().get(0));

        gov.nih.nci.iso21090.Ii assignedIdentifier = new gov.nih.nci.iso21090.Ii();
        assignedIdentifier.setRoot(PoConstants.PERSON_CTEP_ID_ROOT);
        assignedIdentifier
                .setIdentifierName(PoConstants.PERSON_CTEP_ID_IDENTIFIER_NAME);
        assignedIdentifier.setExtension("341234");
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();
        identifiedPerson.setStatus(RoleStatus.ACTIVE);
        identifiedPerson.setAssignedIdentifier(assignedIdentifier);
        personBo.getIdentifiedPersons().add(identifiedPerson);

        populateBOContacts(personBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {

        PersonConverter pConverter = new PersonConverter();
        gov.nih.nci.po.data.bo.Person retPersonBo = pConverter
                .convertFromJaxbToBO(person);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Address
        checkAddressForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Contact List
        checkContactsForConvertJaxbToBO(person, retPersonBo);

    }

    @Test
    public void testConvertJaxbToBOForNullPerson() {
        PersonConverter pConverter = new PersonConverter();
        // person is null while calling the converter
        gov.nih.nci.po.data.bo.Person retPersonBo = pConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retPersonBo);
    }

    @Test
    public void testConvertJaxbToBOForNullAddress() {
        PersonConverter pConverter = new PersonConverter();
        // address is null while calling the converter
        person.setAddress(null);
        gov.nih.nci.po.data.bo.Person retPersonBo = pConverter
                .convertFromJaxbToBO(person);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Address
        Assert.assertEquals(null, retPersonBo.getPostalAddress());

        // Assertion for Contact List
        checkContactsForConvertJaxbToBO(person, retPersonBo);
    }

    @Test
    public void testConvertJaxbToBOForNullContact() {

        PersonConverter pConverter = new PersonConverter();
        // Contact is null while calling the converter
        person.getContact().clear();

        gov.nih.nci.po.data.bo.Person retPersonBo = pConverter
                .convertFromJaxbToBO(person);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Address
        checkAddressForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Contact List
        Assert.assertEquals(new ArrayList<Email>(), retPersonBo.getEmail());
        Assert.assertEquals(new ArrayList<PhoneNumber>(),
                retPersonBo.getPhone());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retPersonBo.getFax());
        Assert.assertEquals(new ArrayList<PhoneNumber>(), retPersonBo.getTty());
        Assert.assertEquals(new ArrayList<URL>(), retPersonBo.getUrl());

    }

    @Test
    public void testConvertJaxbToBOForNullCtep() {
        PersonConverter pConverter = new PersonConverter();
        // CtepId is null
        person.setCtepId(null);
        gov.nih.nci.po.data.bo.Person retPersonBo = pConverter
                .convertFromJaxbToBO(person);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Address
        checkAddressForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Contact List
        checkContactsForConvertJaxbToBO(person, retPersonBo);

        // Assertion for Ctep
        Assert.assertEquals(new HashSet<IdentifiedPerson>(),
                retPersonBo.getIdentifiedPersons());
    }

    @Test
    public void testConvertBOToJaxb() {

        PersonConverter pConverter = new PersonConverter();
        gov.nih.nci.po.webservices.types.Person retPerson = pConverter
                .convertFromBOToJaxB(personBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Address
        checkAddressForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Contact details
        checkContactsForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Ctep
        Assert.assertEquals(personBo.getIdentifiedPersons().iterator().next()
                .getAssignedIdentifier().getExtension(), retPerson.getCtepId());
    }

    @Test
    public void testConvertBOToJaxbForNullPerson() {

        PersonConverter pConverter = new PersonConverter();
        gov.nih.nci.po.webservices.types.Person retPerson = pConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retPerson);
    }

    @Test
    public void testConvertBOToJaxbForNullAddress() {

        PersonConverter pConverter = new PersonConverter();
        // address is null
        personBo.setPostalAddress(null);
        gov.nih.nci.po.webservices.types.Person retPerson = pConverter
                .convertFromBOToJaxB(personBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Address
        Assert.assertEquals(null, retPerson.getAddress());

        // Assertion for Contact details
        checkContactsForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Ctep
        Assert.assertEquals(personBo.getIdentifiedPersons().iterator().next()
                .getAssignedIdentifier().getExtension(), retPerson.getCtepId());
    }

    @Test
    public void testConvertBOToJaxbForNullContact() {

        PersonConverter pConverter = new PersonConverter();
        // Contact is null
        personBo.getEmail().clear();
        personBo.getPhone().clear();
        personBo.getFax().clear();
        personBo.getTty().clear();
        personBo.getUrl().clear();

        gov.nih.nci.po.webservices.types.Person retPerson = pConverter
                .convertFromBOToJaxB(personBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Address
        checkAddressForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Contact details
        Assert.assertEquals(new ArrayList<Contact>(), retPerson.getContact());

        // Assertion for Ctep
        Assert.assertEquals(personBo.getIdentifiedPersons().iterator().next()
                .getAssignedIdentifier().getExtension(), retPerson.getCtepId());
    }

    @Test
    public void testConvertBOToJaxbForNullCtep() {

        PersonConverter pConverter = new PersonConverter();
        // Ctep is set to empty
        personBo.getIdentifiedPersons().clear();
        gov.nih.nci.po.webservices.types.Person retPerson = pConverter
                .convertFromBOToJaxB(personBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Address
        checkAddressForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Contact details
        checkContactsForConvertBOToJaxb(personBo, retPerson);

        // Assertion for Ctep
        Assert.assertEquals(null, retPerson.getCtepId());
    }

    /**
     * This method is used to compare the basic attribute of Person object after
     * JAXB to BO Conversion
     * 
     * @param person
     *            JAXB object
     * @param retPersonBo
     *            BO object after conversion
     */
    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Person person,
            gov.nih.nci.po.data.bo.Person retPersonBo) {
        Assert.assertEquals(person.getId(), retPersonBo.getId());
        Assert.assertEquals(person.getFirstName(), retPersonBo.getFirstName());
        Assert.assertEquals(person.getMiddleName(), retPersonBo.getMiddleName());
        Assert.assertEquals(person.getLastName(), retPersonBo.getLastName());
        Assert.assertEquals(person.getPrefix(), retPersonBo.getPrefix());
        Assert.assertEquals(person.getSuffix(), retPersonBo.getSuffix());
        Assert.assertEquals(person.getStatus().value(), retPersonBo
                .getStatusCode().name());
    }

    /**
     * This method is used to compare the address of Person object after JAXB to
     * BO Conversion
     * 
     * @param person
     *            JAXB object
     * @param retPersonBo
     *            BO object after conversion
     */
    private void checkAddressForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Person person,
            gov.nih.nci.po.data.bo.Person retPersonBo) {
        Assert.assertEquals(person.getAddress().getLine1(), retPersonBo
                .getPostalAddress().getStreetAddressLine());
        Assert.assertEquals(person.getAddress().getLine2(), retPersonBo
                .getPostalAddress().getDeliveryAddressLine());
        Assert.assertEquals(person.getAddress().getCity(), retPersonBo
                .getPostalAddress().getCityOrMunicipality());
        Assert.assertEquals(person.getAddress().getStateOrProvince(),
                retPersonBo.getPostalAddress().getStateOrProvince());
        Assert.assertEquals(person.getAddress().getCountry().name(),
                retPersonBo.getPostalAddress().getCountry().getAlpha3());
    }

    /**
     * This method is used to compare the Contact details of Person object after
     * JAXB to BO Conversion
     * 
     * @param person
     *            JAXB object
     * @param retPersonBo
     *            BO object after conversion
     */
    private void checkContactsForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Person person,
            gov.nih.nci.po.data.bo.Person retPersonBo) {
        Assert.assertEquals("my.email@mayoclinic.org", retPersonBo.getEmail()
                .get(0).getValue());
        Assert.assertEquals("571-456-1245", retPersonBo.getPhone().get(0)
                .getValue());
        Assert.assertEquals("571-456-1245", retPersonBo.getFax().get(0)
                .getValue());
        Assert.assertEquals("571-123-1123", retPersonBo.getTty().get(0)
                .getValue());
        Assert.assertEquals("http://www.mayoclinic.org", retPersonBo.getUrl()
                .get(0).getValue());
    }

    /**
     * This method is used to compare the basic attribute of Person object after
     * BO to JAXB Conversion
     * 
     * @param personBo
     *            BO object
     * @param retPerson
     *            JAXB object after conversion
     */
    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Person personBo,
            gov.nih.nci.po.webservices.types.Person retPerson) {
        Assert.assertEquals(personBo.getId(), retPerson.getId());
        Assert.assertEquals(personBo.getFirstName(), retPerson.getFirstName());
        Assert.assertEquals(personBo.getMiddleName(), retPerson.getMiddleName());
        Assert.assertEquals(personBo.getLastName(), retPerson.getLastName());
        Assert.assertEquals(personBo.getPrefix(), retPerson.getPrefix());
        Assert.assertEquals(personBo.getSuffix(), retPerson.getSuffix());
        Assert.assertEquals(personBo.getStatusCode().name(), retPerson
                .getStatus().value());
    }

    /**
     * This method is used to compare the address of Person object after BO to
     * JAXB Conversion
     * 
     * @param personBo
     *            BO object
     * @param retPerson
     *            JAXB object after conversion
     */
    private void checkAddressForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Person personBo,
            gov.nih.nci.po.webservices.types.Person retPerson) {
        Assert.assertEquals(personBo.getPostalAddress().getStreetAddressLine(),
                retPerson.getAddress().getLine1());
        Assert.assertEquals(personBo.getPostalAddress()
                .getDeliveryAddressLine(), retPerson.getAddress().getLine2());
        Assert.assertEquals(
                personBo.getPostalAddress().getCityOrMunicipality(), retPerson
                        .getAddress().getCity());
        Assert.assertEquals(personBo.getPostalAddress().getStateOrProvince(),
                retPerson.getAddress().getStateOrProvince());
        Assert.assertEquals(personBo.getPostalAddress().getCountry()
                .getAlpha3(), retPerson.getAddress().getCountry().name());
    }

    /**
     * This method is used to compare the address of Person object after BO to
     * JAXB Conversion
     * 
     * @param personBo
     *            BO object
     * @param retPerson
     *            JAXB object after conversion
     */
    private void checkContactsForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Person personBo,
            gov.nih.nci.po.webservices.types.Person retPerson) {
        for (Contact contact : retPerson.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(personBo.getEmail().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(personBo.getPhone().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(personBo.getFax().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(personBo.getTty().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(personBo.getUrl().get(0).getValue(),
                        contact.getValue());
            }
        }

        Assert.assertEquals("my.test@nci.gov", retPerson.getContact().get(0)
                .getValue());
        Assert.assertEquals("571-563-0987", retPerson.getContact().get(1)
                .getValue());
        Assert.assertEquals("571-576-0912", retPerson.getContact().get(2)
                .getValue());
        Assert.assertEquals("571-123-4567", retPerson.getContact().get(3)
                .getValue());
        Assert.assertEquals("http://nih.gov", retPerson.getContact().get(4)
                .getValue());
    }

}
