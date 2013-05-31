package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;

import org.junit.Test;

public class ValidZipValidatorTest {

    ValidZipValidator validator = new ValidZipValidator();

    private static Country US = new Country(null, null, null, "USA");
    private static Country CANADA = new Country(null, null, null, "CAN");
    private static Country OTHER_COUNTRY = new Country(null, null, null, "OTH");

    @Test
    public void nonAddressType() {
        assertFalse(validator.isValid(new String()));
    }

    @Test
    public void nullParam() {
        assertTrue(validator.isValid(null));
    }

    @Test
    public void addressWithNoCountry() {
        Address address = new Address();
        address.setCountry(null);
        assertTrue(validator.isValid(address));
    }

    @Test
    public void addressWithCountryWithNoZip() {
        Address address = new Address();
        address.setCountry(US);
        assertFalse(validator.isValid(address));
        address.setCountry(CANADA);
        assertFalse(validator.isValid(address));
        address.setCountry(OTHER_COUNTRY);
        assertTrue(validator.isValid(address));
    }

    @Test
    public void addressWithCountryWithZip() {
        Address address = new Address();
        address.setPostalCode("12345");
        address.setCountry(US);
        assertTrue(validator.isValid(address));
        address.setCountry(CANADA);
        assertTrue(validator.isValid(address));
        address.setCountry(OTHER_COUNTRY);
        assertTrue(validator.isValid(address));
    }
}
