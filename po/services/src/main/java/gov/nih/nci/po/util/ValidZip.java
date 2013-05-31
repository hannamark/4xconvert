package gov.nih.nci.po.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * @author Hugh Reinhart
 * @since May 28, 2013
 */
@Documented
@ValidatorClass(ValidZipValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidZip {

    /**
     * get the message.
     */
    String message() default "{validator.validZip}";
}
