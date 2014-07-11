package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.UsOrCanadaAddressHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.Validator;

import java.io.Serializable;

/**
 * @author Hugh Reinhart
 * @since May 28, 2013
 */
public class ValidZipValidator implements Validator<ValidZip>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(ValidZip parameters) {
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
        if (UsOrCanadaAddressHelper.isUsOrCanadaOrAustrailiaAddress(address)) {
            return !StringUtils.isEmpty(address.getPostalCode());
        }
        return true;
    }
}
