package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks to make sure that the duplicateOf and status valid.
 * @author smatyas
 */
@Documented
@ValidatorClass(DuplicateStatusCheckValidator.class)
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateStatusCheck {
    /**
     * get the message.
     */
    String message() default "{validator.curatable}";
}
