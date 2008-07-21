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
     * Ancillary.
     */
     ANCILLARY("Ancillary"), 
     /**
      * Correlative.
      */
     CORRELATIVE("Correlative") , 
     /**
      * Early Detection.
      */
     EARLY_DETECTION("Early Detection") ,
     /**
      * Diagnostic.
      */
     DIAGNOSTIC("Diagnostic") , 
     /**
      * Epidemiologic.
      */
     EPIDEMIOLOGIC("Epidemiologic") ,
     /**
      * Observational.
      */
     OBSERVATIONAL("Observational") ,
     /**
      * Outcome.
      */
     OUTCOME("Outcome") ,
     /**
      * Prevention.
      */
     PREVENTION("Prevention") ,
     /**
      * Screening.
      */
     SCREENING("Screening") , 
     /**
      * Supportive Care.
      */
     SUPPORTIVE_CARE("Supportive Care") , 
     /**
      * Therapeutic.
      */
     THERAPEUTIC("Therapeutic"); 

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
}

