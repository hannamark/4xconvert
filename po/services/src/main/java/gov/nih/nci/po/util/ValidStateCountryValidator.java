package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.State;
import org.hibernate.validator.Validator;

import java.io.Serializable;

/**
 * Validates that the State Or Province of an Address type is valid for the specified Country.
 *
 * @author smatyas
 *
 */
public class ValidStateCountryValidator implements Validator<ValidStateCountry>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(ValidStateCountry parameters) {
        //do nothing
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (value == null) {
            return true;
        }
        if (!(value instanceof Address)) {
            return false;
        }
        Address address = (Address) value;
        boolean isValid = address.getCountry() == null;
        isValid = isValid || (address.getCountry() != null && address.getCountry().getStates().isEmpty());
        isValid = isValid || (address.getCountry() != null && !address.getCountry().getStates().isEmpty()
                && address.getStateOrProvince() != null && isStateOrProvinceMemberOfCountry(address));
        return isValid;
    }

    private boolean isStateOrProvinceMemberOfCountry(Address address) {
        for (State state : address.getCountry().getStates()) {
            if (state.getCode().equals(address.getStateOrProvince())) {
                return true;
            }
        }
        return false;
    }

}
