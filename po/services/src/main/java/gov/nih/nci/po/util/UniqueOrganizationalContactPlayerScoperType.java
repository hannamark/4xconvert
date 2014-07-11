package gov.nih.nci.po.util;

import org.hibernate.validator.ValidatorClass;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the player of an org contact is unique for the scoper and contact type, ignoring NULLIFIED records.
 *
 * @author kkanchinadam
 */
@Documented
@ValidatorClass(UniqueOrganizationalContactPlayerScoperTypeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueOrganizationalContactPlayerScoperType {

    /**
     * get the message.
     */
    String message() default "{validator.uniqueOrganizationalContactPlayerScoperType}";
}
