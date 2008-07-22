package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Abstraction Status codes.
 *
 * @author Gil Naveh
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum AbstractionStatusCode implements CodedEnum<String> {

     /**
     * submitted.
     */
     SUBMITTED("Submitted"), 
     /**
      * On Hold.
      */
     ON_HOLD("On Hold"),
     /**
      * Rejected.
      */
     REJECTED("Rejected"),
     /**
      * Accepted.
      */
     ACCEPTED("Accepted"),
     /**
      * Abstracted.
      */
     ABSTRACTED("Abstracted"),
     /**
      * Abstracted Verified.
      */
     ABSTRACTED_VERIFIED("Abstracted Verified"),
     /**
      * Abstracted Not Verified.
      */
     ABSTRACTED_NOT_VERIFIED("Abstracted Not Verified");
     
          
     private String code;

     /**
      * Constructor for AbstractionStatusCode.
      * @param code
      */
     private AbstractionStatusCode(String code) {
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
      * @return AbstractionStatusCode 
      */
     public static AbstractionStatusCode getByCode(String code) {
         return getByClassAndCode(AbstractionStatusCode.class, code);
     }
     
     /**
      * construct a array of display names for Abstracted Status coded Enum.
      * @return String[] display names for Abstracted Status Code
      */
     public static String[]  getDisplayNames() {
         AbstractionStatusCode[] absStatusCodes = AbstractionStatusCode.values();
         String[] codedNames = new String[absStatusCodes.length];
         for (int i = 0; i < absStatusCodes.length; i++) {
             codedNames[i] = absStatusCodes[i].getCode();
         }
         return codedNames;
     }        
}
