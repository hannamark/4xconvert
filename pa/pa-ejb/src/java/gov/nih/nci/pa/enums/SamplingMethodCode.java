package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for SamplingMethodCode codes.
 *
 * @author Naveen Amiruddi 
 * @since 09/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum SamplingMethodCode implements CodedEnum<String> {

     /**
     * simple random sampling.
     */
    SIMPLE_RANDOM_SAMPLING("Simple Random Sampling"), 
     /**
      * Systematic sampling.
      */
    SYSTEMATIC_SAMPLING("Systematic Sampling"),
     /**
      * Stratified random sampling.
      */
    STRATIFIED_RANDOM_SAMPLING("Stratified random sampling"),
     /**
      * Cluster sampling.
      */
    CLUSTER_SAMPLING("Cluster sampling"),
     /**
      * Consecutive patient sampling.
      */
    CONSECUTIVE_PATIENT_SAMPLING("Consecutive patient sampling"),
     /**
      * Non-Probability Sample.
      */
    NON_PROBABILITY_SAMPLE("Non-Probability Sample");
     
          
     private String code;

     /**
      * Constructor for SamplingMethodCode.
      * @param code
      */
     private SamplingMethodCode(String code) {
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
      * @return SamplingMethodCode 
      */
     public static SamplingMethodCode getByCode(String code) {
         return getByClassAndCode(SamplingMethodCode.class, code);
     }
     
     /**
      * construct a array of display names for Abstracted Status coded Enum.
      * @return String[] display names for Abstracted Status Code
      */
     public static String[]  getDisplayNames() {
         SamplingMethodCode[] absStatusCodes = SamplingMethodCode.values();
         String[] codedNames = new String[absStatusCodes.length];
         for (int i = 0; i < absStatusCodes.length; i++) {
             codedNames[i] = absStatusCodes[i].getCode();
         }
         return codedNames;
     }        
}
