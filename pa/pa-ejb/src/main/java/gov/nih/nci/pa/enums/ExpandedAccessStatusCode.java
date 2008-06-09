package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Expanded Access Status codes.
 *
 * @author Gil Naveh
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ExpandedAccessStatusCode implements CodedEnum<String> {

     /**
      *Available.
      */
      AVAILABLE("Available"),      
     /**
      * No longer available.
      */
     NO_LONGER_AVAILABLE("No longer available"),
     /**
      * Temporarily not available.
      */
     TEMPORARY_NOT_AVAILABLE("Temporarily not available"),
     /**
      * Approved for marketing.
      */
     APPROVED_FOR_MARKETING("Approved for marketing");
     
 
               
     private String code;

     /**
      * Constructor for ExpandedAccessStatusCode.
      * @param code
      */
     private ExpandedAccessStatusCode(String code) {
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
      * @return ExpandedAccessStatusCode 
      */
     public static ExpandedAccessStatusCode getByCode(String code) {
         return getByClassAndCode(ExpandedAccessStatusCode.class, code);
     }
}
