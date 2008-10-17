package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration for Identifier Type Code.
 * 
 * @author hgao
 * @since 10/16/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public enum IdentifierTypeCode implements CodedEnum<String> {

    /** NCI. */
    NCI("NCI"),
    /** NCT. */
    NCT("NCT"),
    /** Lead Organization. */
    LEAD_ORGANIZATION("Lead Organization");

    private String code;

    /**
     * Constructor for IdentifierTypeCode.
     * 
     * @param code
     */
    private IdentifierTypeCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    public String getCode() {
        return code;
    }

    /**
     *@return String DisplayName
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }

    /**
     * @param code
     *            code
     * @return IdentifierTypeCode
     */
    public static IdentifierTypeCode getByCode(String code) {
        return getByClassAndCode(IdentifierTypeCode.class, code);
    }

    /**
     * construct a array of display names for Identifier Type coded Enum.
     * 
     * @return String[] display names for Identifier Type Code
     */
    public static String[] getDisplayNames() {
        IdentifierTypeCode[] absStatusCodes = IdentifierTypeCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }
}
