package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ensure only CTEP-owned role may be active.
 */
@Documented
@ValidatorClass(OnlyCtepOwnedMayBeActiveValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyCtepOwnedMayBeActive {
    /**
     * get the message.
     */
    String message() default "Only CTEP may activate role";
}
