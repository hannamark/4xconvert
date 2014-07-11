package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.Validator;
import org.hibernate.validator.ValidatorClass;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that either a phone or an email address is specified.
 * 
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class PhoneOrEmailRequiredValidator implements
        Validator<PhoneOrEmailRequiredValidator.PhoneOrEmailRequired>,
        Serializable {

    /**
     * Validates that the phone list is not empty when status changes.
     */
    @Documented
    @ValidatorClass(PhoneOrEmailRequiredValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface PhoneOrEmailRequired {

        /**
         * get the message.
         */
        String message() default "{validator.phoneOrEmailRequired}";
    }

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(PhoneOrEmailRequired parameters) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "PMD.MissingBreakInSwitch", "rawtypes",
            "PMD.CyclomaticComplexity" })
    public boolean isValid(Object value) {
        if (!(value instanceof Contactable)) {
            return true;
        }
        if ((value instanceof CuratableEntity<?, ?>)
                && ((CuratableEntity<?, ?>) value).getStatusCode() == EntityStatus.NULLIFIED) {
            return true;
        }
        if ((value instanceof CuratableRole<?, ?>)
                && (((CuratableRole<?, ?>) value).getStatus() == RoleStatus.NULLIFIED || ((CuratableRole<?, ?>) value)
                        .getStatus() == RoleStatus.PENDING)) {
            return true;
        }
        Contactable contactable = (Contactable) value;
        return CollectionUtils.isNotEmpty(contactable.getEmail())
                || CollectionUtils.isNotEmpty(contactable.getPhone());
    }
}
