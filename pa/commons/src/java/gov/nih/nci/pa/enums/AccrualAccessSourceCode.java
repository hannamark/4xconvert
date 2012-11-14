package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Hugh Reinhart
 * @since Nov 9, 2012
 */
public enum AccrualAccessSourceCode implements CodedEnum<String> {

    /** Site Request. */
    PA_SITE_REQUEST("Site Request"),
    /** Admin Provided. */
    REG_ADMIN_PROVIDED("Admin Provided"),
    /** Site Admin Role. */
    REG_SITE_ADMIN_ROLE("Site Admin Role"),
    /** Family Access. */
    REG_FAMILY_ADMIN_ROLE("Family Access");

    private final String code;

    private AccrualAccessSourceCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @param code code
     * @return CheckOutType with the given code
     */
    public static AccrualAccessSourceCode getByCode(String code) {
        return getByClassAndCode(AccrualAccessSourceCode.class, code);
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * Gets the name of the enum.
     * @return the name of the enum
     */
    public String getName() {
        return name();
    }
}
