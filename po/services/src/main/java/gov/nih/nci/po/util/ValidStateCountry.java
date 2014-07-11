package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the State Or Province of an Address type is valid for the specified Country.
 * 
 * @author smatyas
 *
 */
@Documented
@ValidatorClass(ValidStateCountryValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStateCountry {

    /**
     * get the message.
     */
    String message() default "{validator.validStateCountry}";
}
