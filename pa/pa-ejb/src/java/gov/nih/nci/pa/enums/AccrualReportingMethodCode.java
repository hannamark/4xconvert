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
public enum AccrualReportingMethodCode implements CodedEnum<String> {

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
      * Constructor for AccrualReportingMethodCode.
      * @param code
      */
     private AccrualReportingMethodCode(String code) {
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
      * @return AccrualReportingMethodCode 
      */
     public static AccrualReportingMethodCode getByCode(String code) {
         return getByClassAndCode(AccrualReportingMethodCode.class, code);
     }
     
     /**
      * construct a array of display names for AccrualReportingMethodCode Enum.
      * @return String[] display names for AccrualReportingMethodCode
      */
     public static String[]  getDisplayNames() {
         AccrualReportingMethodCode[] codes = AccrualReportingMethodCode.values();
         String[] codedNames = new String[codes.length];
         for (int i = 0; i < codes.length; i++) {
             codedNames[i] = codes[i].getCode();
         }
         return codedNames;
     }        
}

