package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Test for getters and setters.
 *
 * @author Steve Matyas
 */
public class BOGetterSetterTest {

    /**
     * Test URL getters and setters.
     */
    @Test
    public void testURLGetterSetters() {
        URL url = new URL();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(url);

    }

    /**
     * Test Email getters and setters.
     */
    @Test
    public void testEmailGetterSetters() {
        Email email = new Email();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(email);

    }

    /**
     * Test PhoneNumber getter and setters.
     */
    @Test
    public void testPhoneNumberGetterSetters() {
        PhoneNumber phoneNumber = new PhoneNumber();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(phoneNumber);
    }

    /**
     * Test PhoneNumber comparison.
     */
    @Test
    public void testPhoneNumberComparison() {
        PhoneNumber phone = new PhoneNumber();
        assertSame(phone.equals(phone), true);
        assertSame(phone.equals(null), false);
        assertSame(phone.equals(new Object()), false);

        PhoneNumber phone2 = new PhoneNumber();
        assertSame(phone.equals(phone2), false);

    }

    /**
     * Test Address getter and setters.
     */
    @Test
    public void testAddressGetterSetters() {
        Address address = new Address("street", "city", "state", "postalCode", new Country("name", "numeric", "alpha2",
                "alpha3"));
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(address);
    }

    /**
     * Test State getter and setters.
     */
    @Test
    public void testStateGetterSetters() {
        State state = new State();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(state);
    }
}
