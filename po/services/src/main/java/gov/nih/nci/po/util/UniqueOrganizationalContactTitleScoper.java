package gov.nih.nci.po.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Used to validate that the title of an organizational contact is unique for the scoper, ignoring NULLIFIED records.
 *
 * @author slustbader
 */
@Documented
@ValidatorClass(UniqueOrganizationalContactTitleScoperValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueOrganizationalContactTitleScoper {

    /**
     * get the message.
     */
    String message() default "{validator.uniqueOrganizationalContactTitleScoper}";
}
