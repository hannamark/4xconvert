package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration for Organization Type code.
 * 
 * @author hgao
 * @since 10/16/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public enum OrganizationTypeCode implements CodedEnum<String> {

    /** Lead. */
    LEAD("Lead"),
    /** Participating. */
    PARTICIPATING("Participating");

    private String code;

    /**
     * Constructor for OrganizationTypeCode.
     * 
     * @param code
     */
    private OrganizationTypeCode(String code) {
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
     * @return OrganizationTypeCode
     */
    public static OrganizationTypeCode getByCode(String code) {
        return getByClassAndCode(OrganizationTypeCode.class, code);
    }

    /**
     * construct a array of display names for Organization Type coded Enum.
     * 
     * @return String[] display names for Identifier Type Code
     */
    public static String[] getDisplayNames() {
        OrganizationTypeCode[] absStatusCodes = OrganizationTypeCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }
}
