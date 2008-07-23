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
public enum ReportingDataSetMethodCode implements CodedEnum<String> {

    /**
     * Abbreviated.
     */
    ABBREVIATED("Abbreviated"), 
     /**
      * Complete.
      */
    COMPLETE("Complete"),
     /**
      * AE.
      */
     AE("AE");
     
          
     private String code;

     /**
      * Constructor for ReportingDataSetMethodCode.
      * @param code
      */
     private ReportingDataSetMethodCode(String code) {
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
      * @return ReportingDataSetMethodCode 
      */
     public static ReportingDataSetMethodCode getByCode(String code) {
         return getByClassAndCode(ReportingDataSetMethodCode.class, code);
     }
     
     /**
      * construct a array of display names for ReportingDataSetMethodCode Enum.
      * @return String[] display names for ReportingDataSetMethodCode
      */
     public static String[]  getDisplayNames() {
         ReportingDataSetMethodCode[] codes = ReportingDataSetMethodCode.values();
         String[] codedNames = new String[codes.length];
         for (int i = 0; i < codes.length; i++) {
             codedNames[i] = codes[i].getCode();
         }
         return codedNames;
     }        
}

