package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.ResearchOrganization;
import org.hibernate.validator.Validator;
import org.hibernate.validator.ValidatorClass;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the typeCode is not null when status changes.
 * 
 * @author gax
 *
 */
public class ResearchOrganizationTypeCodeValidator
        implements Validator<ResearchOrganizationTypeCodeValidator.ResearchOrganizationTypeCode>, Serializable {

    /**
     * Validates that the typeCode is not null when status changes.
     */
    @Documented
    @ValidatorClass(ResearchOrganizationTypeCodeValidator.class)
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface ResearchOrganizationTypeCode {

        /**
         * get the message.
         */
        String message() default "{validator.researchOrganizationTypeCode}";
    }


    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(ResearchOrganizationTypeCode parameters) {
        //do nothing
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.MissingBreakInSwitch")
    public boolean isValid(Object value) {
        if (!(value instanceof ResearchOrganization)) {
            return false;
        }
        ResearchOrganization ro = (ResearchOrganization) value;
        if (ro.getStatus() == null) {
            return true;
        }
        switch(ro.getStatus()) {
            case NULLIFIED:
            case PENDING:
                return true;
            default:
                return ro.getTypeCode() != null;
        }
    }
}
