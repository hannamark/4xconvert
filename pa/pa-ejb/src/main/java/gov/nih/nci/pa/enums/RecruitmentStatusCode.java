package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration  for Recruitment Status codes.
 *
 * @author Gil Naveh
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum RecruitmentStatusCode implements CodedEnum<String> {

     /**
     * Not Yet Recruiting.
     */
     NOT_YET_RECRUITING("Not Yet Recruiting"), 
     /**
      *Recruiting.
      */
      RECRUITING("Recruiting"),      
     /**
      * Enrolling by invitation.
      */
     ENROLLING_BY_INVITATION("Enrolling by Invitation"),
     /**
      * Active, not recruiting.
      */
     ACTIVE_NOT_RECRUITING("Active, not recruiting"),
     /**
      * Completed.
      */
     COMPLETED("Completed"),
     /**
      * Suspended.
      */
     SUSPENDED("Suspended"),
     /**
      * Terminated.
      */
     TERMINATED("Terminated"),
     /**
      * Withdrawn.
      */
     WITHDRAWN("Withdrawn");
 
               
     private String code;

     /**
      * Constructor for RecruitmentStatusCode.
      * @param code
      */
     private RecruitmentStatusCode(String code) {
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
     public static RecruitmentStatusCode getByCode(String code) {
         return getByClassAndCode(RecruitmentStatusCode.class, code);
     }
}
