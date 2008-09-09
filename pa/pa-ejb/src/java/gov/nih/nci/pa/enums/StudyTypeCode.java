package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Reporting DataSet MethodCode.
 *
 * @author Naveen Amiruddin
 * @since 07/22/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum StudyTypeCode implements CodedEnum<String> {

    /** Interventional. */
    INTERVENTIONAL("Interventional"), 
     /*** Observational. */
    OBSERVATIONAL("Observational"),
     /**
      * AE.
      */
     AE("AE");
     
          
     private String code;

     /**
      * Constructor for StudyTypeCode.
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
      * @return StudyTypeCode 
      */
     public static StudyTypeCode getByCode(String code) {
         return getByClassAndCode(StudyTypeCode.class, code);
     }
     
     /**
      * construct a array of display names for StudyTypeCode Enum.
      * @return String[] display names for StudyTypeCode
      */
     public static String[]  getDisplayNames() {
         StudyTypeCode[] codes = StudyTypeCode.values();
         String[] codedNames = new String[codes.length];
         for (int i = 0; i < codes.length; i++) {
             codedNames[i] = codes[i].getCode();
         }
         return codedNames;
     }        
}

