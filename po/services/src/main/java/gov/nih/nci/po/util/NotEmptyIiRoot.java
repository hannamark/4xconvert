package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check that a String is not empty (not null and length > 0) or that a Collection (or array) is not empty (not null and
 * length > 0).
 *
 */
@Documented
@ValidatorClass(NotEmptyIiRootValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyIiRoot {
    /**
     * get the message.
     */
    String message() default "{validator.notEmptyIiRoot}";
    
    /**
     * get the sql column name.
     */
    String columnName() default "assigned_identifier_root";
}
