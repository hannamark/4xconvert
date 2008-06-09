package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for IDE Trail Indicator codes.
 *
 * @author Gil Naveh
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum IDETrailIndicatorCode implements CodedEnum<String> {

     /**
     * Yes.
     */
     YES("Yes"), 
     /**
      * No.
      */
     NO("No");
     
     private String code;

     /**
      * Constructor for IDETrailIndicatorCode.
      * @param code
      */
     private IDETrailIndicatorCode(String code) {
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
      * @return IDETrailIndicatorCode 
      */
     public static IDETrailIndicatorCode getByCode(String code) {
         return getByClassAndCode(IDETrailIndicatorCode.class, code);
     }
}
