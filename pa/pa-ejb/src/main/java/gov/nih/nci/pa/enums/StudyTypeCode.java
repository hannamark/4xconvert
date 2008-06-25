package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Study Type code.
 * database is mapped to protocol.intent_code
 * @author Naveen Amiruddin
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum StudyTypeCode implements CodedEnum<String> {

    /**
     * Treatment.
     */
    TREATMENT("Treatment"),
    /**
     * Prevention.
     */
    PREVENTION("Prevention") ,
    /**
     * Diagnostic.
     */
    DIAGNOSTIC("Diagnostic") , 
    /**
     * Supportive Care.
     */
    SUPPORTIVE_CARE("Supportive Care") , 
    /**
     * Screening.
     */
    SCREENING("Screening") , 
    /**
     * Health Services Research.
     */
    HEALTH_SERVICES_RESEARCH("Health Services Research") , 
    /** 
     * Basic Science.
     */
    BASIC_SCIENCE("Basic Science"), 
     /**
      * Other.
      */
     OTHER("Other"); 
     
     private String code;

     /**
      * Constructor for TrialStatusCode.
      * @param code
      */

     private StudyTypeCode(String code) {
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
      * @return Study Type Code 
      */
     public static StudyTypeCode getByCode(String code) {
         return getByClassAndCode(StudyTypeCode.class, code);
     }

     /**
      * construct a array of display names for Study Type coded Enum.
      * @return String[] display names for StudyTypeCode
      */
     public static String[]  getCodedNames() {
         StudyTypeCode[] studyTypeCodes = StudyTypeCode.values();
         String[] codedNames = new String[studyTypeCodes.length];
         for (int i = 0; i < studyTypeCodes.length; i++) {
             codedNames[i] = studyTypeCodes[i].getCode();
         }
         return codedNames;
     }     
}

