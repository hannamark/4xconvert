/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ActiveInactiveCode implements CodedEnum<String> {

    /**
    * Active.
    */
    ACTIVE("Active"), 
    /**
     * Inactive.
     */
    INACTIVE("Inactive");
    
         
    private String code;

    /**
     * Constructor for StatusCode.
     * @param code
     */
    private ActiveInactiveCode(String code) {
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
     * @param code code
     * @return StatusCode 
     */
    public static StatusCode getByCode(String code) {
        return getByClassAndCode(StatusCode.class, code);
    }
    
    /**
     * construct a array of display names for Abstracted Status coded Enum.
     * @return String[] display names for Abstracted Status Code
     */
    public static String[]  getDisplayNames() {
        StatusCode[] absStatusCodes = StatusCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }
}
