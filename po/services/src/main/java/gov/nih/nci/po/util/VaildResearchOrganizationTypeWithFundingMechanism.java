package gov.nih.nci.po.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Checks to make sure that the duplicateOf and status valid.
 * @author smatyas
 */
@Documented
@ValidatorClass(VaildResearchOrganizationTypeWithFundingMechanismValidator.class)
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface VaildResearchOrganizationTypeWithFundingMechanism {
    /**
     * get the message.
     */
    String message() default "{validator.vaildResearchOrganizationTypeWithFundingMechanism}";
}
