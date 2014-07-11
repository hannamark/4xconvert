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
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * Validates that date end date is not earlier than start date.
 * 
 * @author moweis
 * 
 */
public class OrderedDateValidator implements Validator<OrderedDateValidator.OrderedDate>, Serializable {

    /**
     * Validates that date end date is not earlier than start date.
     */
    @Documented
    @ValidatorClass(OrderedDateValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface OrderedDate {

        /**
         * Start Date property field name.
         */
        String startFieldName() default "startDate";

        /**
         * End Date property field name.
         */
        String endFieldName() default "endDate";

        /**
         * get the message.
         */
        String message() default "{validator.orderedDate}";
    }

    private static final long serialVersionUID = 1L;
    private String startFieldName;
    private String endFieldName;

    /**
     * {@inheritDoc}
     */
    public void initialize(OrderedDate parameters) {
        this.startFieldName = parameters.startFieldName();
        this.endFieldName = parameters.endFieldName();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        try {
            Field startField = value.getClass().getDeclaredField(startFieldName);
            Field endField = value.getClass().getDeclaredField(endFieldName);

            startField.setAccessible(true);
            endField.setAccessible(true);

            Date startDate = (Date) startField.get(value);
            Date endDate = (Date) endField.get(value);

            if (endDate == null) {
                return true;
            }
            return (startDate != null && endDate != null && DateUtils.truncatedCompareTo(startDate, endDate,
                    Calendar.DAY_OF_MONTH) <= 0);
        } catch (SecurityException e) {
            return false;
        } catch (NoSuchFieldException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    /**
     * @return the startFieldName
     */
    public String getStartFieldName() {
        return startFieldName;
    }

    /**
     * @param startFieldName the startFieldName to set
     */
    public void setStartFieldName(String startFieldName) {
        this.startFieldName = startFieldName;
    }

    /**
     * @return the endFieldName
     */
    public String getEndFieldName() {
        return endFieldName;
    }

    /**
     * @param endFieldName the endFieldName to set
     */
    public void setEndFieldName(String endFieldName) {
        this.endFieldName = endFieldName;
    }

}
