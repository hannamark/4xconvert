package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Trial Status codes.
 *
 * @author Gil Naveh
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ReportDataSetMethod implements CodedEnum<String> {

     /**
     * Abbreviated.
     */
     ABBREVIATED("Abbreviated"), 
     /**
      * Completed.
      */
     COMPLETED("Completed"),
     /**
      * AE.
      */
     AE("AE");
     
     private String code;

     /**
      * Constructor for TrialStatusCode.
      * @param code
      */
     private ReportDataSetMethod(String code) {
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
      * @return TrialStatusCode 
      */
     public static ReportDataSetMethod getByCode(String code) {
         return getByClassAndCode(ReportDataSetMethod.class, code);
     }
}
