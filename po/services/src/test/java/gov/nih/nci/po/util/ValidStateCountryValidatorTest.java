package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.State;

import org.junit.Test;


public class ValidStateCountryValidatorTest {

    ValidStateCountryValidator validator = new ValidStateCountryValidator();
    
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
    @SuppressWarnings("deprecation")
    public void addressWithCountryWithNoStates() {
        Address address = new Address();
        address.setCountry(new Country());
        assertTrue(address.getCountry().getStates().isEmpty());
        assertTrue(validator.isValid(address));
    }
    
    @Test
    @SuppressWarnings("deprecation")
    public void addressWithCountryWithStatesButNoStateOrProvinceSet() {
        Address address = new Address();
        address.setCountry(new Country());
        State state1 = create("AA");
        State state2 = create("BB");
        State state3 = create("CC");
        address.getCountry().getStates().add(state1);
        address.getCountry().getStates().add(state2);
        address.getCountry().getStates().add(state3);
        assertFalse(validator.isValid(address));
    }
    
    @Test
    @SuppressWarnings("deprecation")
    public void addressWithCountryWithStatesWithStateOrProvinceSetButValueIsInvalid() {
        Address address = new Address();
        address.setCountry(new Country());
        State state1 = create("AA");
        State state2 = create("BB");
        State state3 = create("CC");
        address.getCountry().getStates().add(state1);
        address.getCountry().getStates().add(state2);
        address.getCountry().getStates().add(state3);
        address.setStateOrProvince("ZZ");
        assertFalse(validator.isValid(address));
    }
    
    @Test
    @SuppressWarnings("deprecation")
    public void addressWithCountryWithStatesWithStateOrProvinceSetAndValueIsValid() {
        Address address = new Address();
        address.setCountry(new Country());
        State state1 = create("AA");
        State state2 = create("BB");
        State state3 = create("CC");
        address.getCountry().getStates().add(state1);
        address.getCountry().getStates().add(state2);
        address.getCountry().getStates().add(state3);
        address.setStateOrProvince("BB");
        assertTrue(validator.isValid(address));
    }

    private State create(String code) {
        State state1 = new State();
        state1.setCode(code);
        return state1;
    }
    
}
