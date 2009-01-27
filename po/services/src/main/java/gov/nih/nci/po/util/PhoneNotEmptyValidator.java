package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.PhoneNumber;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.hibernate.validator.Validator;
import org.hibernate.validator.ValidatorClass;

/**
 * Validates that the phone list is not empty when status changes.
 *
 * @author gax
 *
 */
public class PhoneNotEmptyValidator
        implements Validator<PhoneNotEmptyValidator.PhoneNotEmpty>, Serializable {

    /**
     * Validates that the phone list is not empty when status changes.
     */
    @Documented
    @ValidatorClass(PhoneNotEmptyValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface PhoneNotEmpty {

        /**
         * get the message.
         */
        String message() default "{validator.phoneNotEmptyValidator}";
    }


    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(PhoneNotEmpty parameters) {
        //do nothing
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.MissingBreakInSwitch")
    public boolean isValid(Object value) {
        if (!(value instanceof ClinicalResearchStaff
                || value instanceof HealthCareProvider
                || value instanceof OrganizationalContact)) {
            return false;
        }
        CuratableRole<?, ?> ro = (CuratableRole<?, ?>) value;
        if (ro.getStatus() == null) {
            return true;
        }
        switch(ro.getStatus()) {
            case NULLIFIED:
            case PENDING:
                return true;
            default:
                List<PhoneNumber> list = ((Contactable) value).getPhone();
                return !list.isEmpty();
        }
    }
}
