package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Trial Status codes.
 *
 * @author Naveen Amiruddin
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum StudyStatusCode implements CodedEnum<String> {

     /**
     * In Review.
     */
     IN_REVIEW("In Review"), 
     /**
      * Approved.
      */
     APPROVED("Approved") , 
     /**
      * Active.
      */
     ACTIVE("Active") ,
     /**
      * Closed to Accrual.
      */
     CLOSED_TO_ACCRUAL("Closed to Accrual") , 
     /**
      * Closed To Accrual And Intervention.
      */
     CLOSED_TO_ACCRUAL_AND_INTERVENTION("Closed To Accrual And Intervention") ,
     /**
      * Temporarily Closed To Accrual.
      */
     TEMPORARILY_CLOSED_TO_ACCRUAL("Temporarily Closed To Accrual") ,
     /**
      * Temporarily Closed To Accrual and Intervention.
      */
     TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION("Temporarily Closed To Accrual and Intervention") ,
     /**
      * Disapproved.
      */
     DISAPPROVED("Disapproved") ,
     /**
      * Withdrawn.
      */
     WITHDRAWN("Withdrawn") , 
     /**
      * Administratively Complete.
      */
     ADMINISTRATIVELY_COMPLETE("Administratively Complete") , 
     /**
      * Complete.
      */
     COMPLETE("Complete"); 

     private String code;

     /**
      * Constructor for TrialStatusCode.
      * @param code
      */

     private StudyStatusCode(String code) {
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
     public static StudyStatusCode getByCode(String code) {
         return getByClassAndCode(StudyStatusCode.class, code);
     }

     /**
      * construct a array of display names for Study Status coded Enum.
      * @return String[] display names for StudyStatusCode
      */
     public static String[]  getDisplayNames() {
         StudyStatusCode[] studyStatusCodes = StudyStatusCode.values();
         String[] codedNames = new String[studyStatusCodes.length];
         for (int i = 0; i < studyStatusCodes.length; i++) {
             codedNames[i] = studyStatusCodes[i].getCode();
         }
         return codedNames;
     }        
}
