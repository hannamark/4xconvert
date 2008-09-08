package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Indicates the manner in which the site is participating on the trial.
 *
 * @author Naveen Amiruddin
 * @since 07/22/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum StudyParticipationFunctionalCode implements CodedEnum<String> {

    /**
     * Treating Site.
     */
    TREATING_SITE("Treating Site"), 
     /**
      * Data Management Center.
      */
    DATA_MANAGEMENT_CENTER("Data Management Center"),
    /**
     * Lead organization.
     */
    LEAD_ORAGANIZATION("Lead Organization"),
    
     /**
      * Protocol Management Center.
      */
    PROTOCOL_MANAGEMENT_CENTER("Protocol Management Center");
     
          
     private String code;

     /**
      * Constructor for StudyParticipationFunctionalCode.
      * @param code
      */
     private StudyParticipationFunctionalCode(String code) {
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
      * @return StudyParticipationFunctionalCode 
      */
     public static StudyParticipationFunctionalCode getByCode(String code) {
         return getByClassAndCode(StudyParticipationFunctionalCode.class, code);
     }
     
     /**
      * construct a array of display names for StudyParticipationFunctionalCode Enum.
      * @return String[] display names for StudyParticipationFunctionalCode
      */
     public static String[]  getDisplayNames() {
         StudyParticipationFunctionalCode[] codes = StudyParticipationFunctionalCode.values();
         String[] codedNames = new String[codes.length];
         for (int i = 0; i < codes.length; i++) {
             codedNames[i] = codes[i].getCode();
         }
         return codedNames;
     }        
}

