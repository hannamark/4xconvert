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
public enum FunctionalCode implements CodedEnum<String> {

    /**
     * Treating Site.
     */
    TREATING_SITE("Treating Site"), 
     /**
      * Data Management Center.
      */
    DATA_MANAGEMENT_CENTER("Data Management Center"),
     /**
      * Protocol Management Center.
      */
    PROTOCOL_MANAGEMENT_CENTER("Protocol Management Center");
     
          
     private String code;

     /**
      * Constructor for FunctionCode.
      * @param code
      */
     private FunctionalCode(String code) {
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
      * @return FunctionalCode 
      */
     public static FunctionalCode getByCode(String code) {
         return getByClassAndCode(FunctionalCode.class, code);
     }
     
     /**
      * construct a array of display names for FunctionalCode Enum.
      * @return String[] display names for FunctionalCode
      */
     public static String[]  getDisplayNames() {
         FunctionalCode[] codes = FunctionalCode.values();
         String[] codedNames = new String[codes.length];
         for (int i = 0; i < codes.length; i++) {
             codedNames[i] = codes[i].getCode();
         }
         return codedNames;
     }        
}

