package gov.nih.nci.po.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Used to validate that the scoper is unique for the given player, ignoring NULLIFIED records.  If there is no player,
 * the scoper is considered valid.
 *
 * @author slustbader
 */
@Documented
@ValidatorClass(UniquePlayerScoperPlayerOptionalValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePlayerScoperPlayerOptional {
    /**
     * returns the friendly-type name.
     */
    String friendlyName();

    /**
     * the article to be displayed before the friendly name, such as "A" or "An".
     */
    String article() default "A";

    /**
     * get the message.
     */
    String message() default "{validator.uniquePlayerScoperPlayerOptional}";
}
