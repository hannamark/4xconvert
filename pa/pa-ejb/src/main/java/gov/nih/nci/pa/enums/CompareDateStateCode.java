package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Compare Date State codes.
 *
 * @author Gil Naveh
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum CompareDateStateCode implements CodedEnum<String> {

     /**
     * anticipated.
     */
     ANTICIPATED("Anticipated"), 
     /**
      * actual.
      */
     ACTUAL("Actual");
     
     private String code;

     /**
      * Constructor for CompareDateStateCode.
      * @param code
      */
     private CompareDateStateCode(String code) {
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
      * @return CompareDateStateCode 
      */
     public static CompareDateStateCode getByCode(String code) {
         return getByClassAndCode(CompareDateStateCode.class, code);
     }
}
