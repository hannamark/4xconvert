package gov.nih.nci.po.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Used to validate that the scoper is unique for the given player, ignoring NULLIFIED records.
 * 
 * @author smatyas
 */
@Documented
@ValidatorClass(UniquePlayerScoperValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePlayerScoper {
    /**
     * returns the friendly-type name.
     */
    String friendlyName();

    /**
     * get the message.
     */
    String message() default "{validator.uniquePlayerScoper}";
}
