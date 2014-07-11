package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to validate that at least one of player or title is set.
 *
 * @author slustbader
 */
@Documented
@ValidatorClass(RequiredOrganizationalContactTitleOrPersonValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredOrganizationalContactTitleOrPerson {

    /**
     * get the message.
     */
    String message() default "{validator.requiredOrganizationalContactTitleOrPerson}";
}
