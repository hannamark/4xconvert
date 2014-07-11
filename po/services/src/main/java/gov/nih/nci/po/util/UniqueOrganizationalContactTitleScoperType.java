package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the title of an org contact is unique for the scoper and contact type, ignoring NULLIFIED records.
 *
 * @author slustbader, kkanchinadam
 */
@Documented
@ValidatorClass(UniqueOrganizationalContactTitleScoperTypeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueOrganizationalContactTitleScoperType {

    /**
     * get the message.
     */
    String message() default "{validator.uniqueOrganizationalContactTitleScoperType}";
}
