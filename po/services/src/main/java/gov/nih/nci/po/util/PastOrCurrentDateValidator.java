package gov.nih.nci.po.util;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.validator.Validator;
import org.hibernate.validator.ValidatorClass;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Calendar;
import java.util.Date;

/**
 * Validates that date is not in the future.
 * 
 * @author moweis
 * 
 */
public class PastOrCurrentDateValidator implements Validator<PastOrCurrentDateValidator.PastOrCurrentDate>,
        Serializable {

    /**
     * Validates that date is not in the future.
     */
    @Documented
    @ValidatorClass(PastOrCurrentDateValidator.class)
    @Target({ ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface PastOrCurrentDate {
        /**
         * get the message.
         */
        String message() default "{validator.pastOrCurrentDate}";
    }

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(PastOrCurrentDate arg0) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if (value == null) {
            return true;
        } else if (!(value instanceof Date)) {
            return false;
        }
        Date date = (Date) value;
        Date today = new Date();
        return DateUtils.truncatedCompareTo(date, today, Calendar.DAY_OF_MONTH) <= 0;
    }
}
