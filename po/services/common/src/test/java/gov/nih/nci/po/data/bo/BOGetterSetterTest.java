package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.common.Country;
import gov.nih.nci.po.data.common.DegreeType;
import gov.nih.nci.po.data.common.State;

import java.util.Date;

import org.junit.Test;

public class BOGetterSetterTest {

    /**
     * Test License getter and setters
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testLicenseGetterSetters() {
       License license = new License(new Person(), new Date(), new Organization(), "detail");
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(license);

    }

    /**
     * Test Degree Type getter and setters
     */
    @Test
    public void testDegreeTypeGetterSetters() {
       DegreeType degreeType = new DegreeType("code","name");
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(degreeType);

    }

    /**
     * Test Degree getters and setters
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testDegreeGetterSetters() {
       Degree degree = new Degree();
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(degree);

    }

    /**
     * Test URL getters and setters
     */
    @Test
    public void testURLGetterSetters() {
       URL url = new URL();
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(url);

    }

    /**
     * Test Email getters and setters
     */
    @Test
    public void testEmailGetterSetters() {
       Email email = new Email();
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(email);

    }

    /**
     * Test PhoneNumber getter and setters
     */
    @Test
    public void testPhoneNumberGetterSetters() {
       PhoneNumber phoneNumber = new PhoneNumber();
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(phoneNumber);
    }

    /**
     * Test PhoneNumber comparison
     */
    @Test
    public void testPhoneNumberComparison(){
        PhoneNumber phone = new PhoneNumber();
        assertSame(phone.equals(phone),true);
        assertSame(phone.equals(null),false);
        assertSame(phone.equals(new Object()),false);

        PhoneNumber phone2 = new PhoneNumber();
        assertSame(phone.equals(phone2),false);

    }

    /**
     * Test Address getter and setters
     */
    @Test
    public void testAddressGetterSetters() {
       Address address = new Address("street", "city", "state", "postalCode",
               new Country("name","numeric", "alpha2", "alpha3"));
       GetterSetterTesterUtil.assertBasicGetterSetterBehavior(address);
    }

    /**
     * Test Speciality getter and setters
     */
    @Test
    public void testSpecialityGetterSetters() {
        Speciality speciality = new Speciality("Name",true,true,new Person());
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(speciality);
    }

    /**
     * Test State getter and setters
     */
    @Test
    public void testStateGetterSetters() {
        State state =  new State();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(state);
    }

    /**
     * Test State getter and setters
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testCertificateGetterSetters() {
        Certificate certificate =  new Certificate(new Person(), new Date(), new Organization(), "detail");
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(certificate);
    }

    /**
     * Test Person getter and setters
     */
    /*
    @Test
    public void testPersonGetterSetters() {
        Person person = new Person();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(person);
    }
    */
}
