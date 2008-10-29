package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
*
* @author Kalpana Guthikonda
* @since 10/23/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public enum TimePerspectiveCode implements CodedEnum<String> {

    /**
    * Prospective.
    */
    PROSPECTIVE("Prospective"), 
    /**
     * Retrospective.
     */
    RETROSPECTIVE("Retrospective"),
    /**
     * Other.
     */
    OTHER("Other");
    
         
    private String code;

    /**
     * Constructor for TimePerspectiveCode.
     * @param code
     */
    private TimePerspectiveCode(String code) {
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
     * @return TimePerspectiveCode 
     */
    public static TimePerspectiveCode getByCode(String code) {
        return getByClassAndCode(TimePerspectiveCode.class, code);
    }
    
    /**
     * construct a array of display names for Abstracted Status coded Enum.
     * @return String[] display names for Abstracted Status Code
     */
    public static String[]  getDisplayNames() {
        TimePerspectiveCode[] absStatusCodes = TimePerspectiveCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }        
    

}
