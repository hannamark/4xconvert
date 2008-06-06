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
public enum ParticipationType implements CodedEnum<String> {

     /**
     * Cancer center.
     */
     CANCER_CENTER("Cancer center"), 
     /**
      * Cancer center.
      */
      CLINICAL_CENTER("Clinical center"),
     /**
      * Consortium.
      */
     CONSORTIUM("Consortium"),
     /**
      * Group.
      */
     GROUP("Group"),
     /**
      * Intergroup.
      */
     INTERGROUP("Intergroup"),
     /**
      * Multi-center.
      */
     MULTI_CENTER("Multi-center"),
     /**
      * Network.
      */
     NETWORK("Network"),
     /**
      * Single institution.
      */
     SINGLE_INSTITUTE("Single institution");
     
     private String code;

     /**
      * Constructor for TrialStatusCode.
      * @param code
      */
     private ParticipationType(String code) {
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
     public static ParticipationType getByCode(String code) {
         return getByClassAndCode(ParticipationType.class, code);
     }
}
