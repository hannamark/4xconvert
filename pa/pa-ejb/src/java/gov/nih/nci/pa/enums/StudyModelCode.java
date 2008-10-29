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
public enum StudyModelCode implements CodedEnum<String> {

    /**
    * Cohort.
    */
    COHORT("Cohort"), 
    /**
     * Case-control.
     */
    CASE_CONTROL("Case-control"),
    /**
     * Case-only.
     */
    CASE_ONLY("Case-only"),
    /**
     * Case-crossover.
     */
    CASE_CROSSOVER("Case-crossover"),
    /**
     * Ecologic or community studies.
     */
    ECOLOGIC_OR_COMMUNITY_STUDIES("Ecologic or community studies"),
    /**
     * Family-based.
     */
    FAMILY_BASED("Family-based"),
    /**
     * Other.
     */
    OTHER("Other");
    
         
    private String code;

    /**
     * Constructor for StudyModelCode.
     * @param code
     */
    private StudyModelCode(String code) {
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
     * @return StudyModelCode 
     */
    public static StudyModelCode getByCode(String code) {
        return getByClassAndCode(StudyModelCode.class, code);
    }
    
    /**
     * construct a array of display names for Abstracted Status coded Enum.
     * @return String[] display names for Abstracted Status Code
     */
    public static String[]  getDisplayNames() {
        StudyModelCode[] absStatusCodes = StudyModelCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }        
    
}