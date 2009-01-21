package gov.nih.nci.registry.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

import gov.nih.nci.pa.enums.CodedEnum;
/**
 * 
 * @author Vrushali
 *
 */
public enum  TrialStatusReasonCode implements CodedEnum<String> {
    /**
     * Temporarily Closed to Accrual.
     */     
    TEMPORARILY_CLOSED_TO_ACCRUAL ("Temporarily Closed to Accrual") ,
    /**
     * Temporarily Closed To Accrual and Intervention.
     */
    TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION ("Temporarily Closed to Accrual and Intervention") ,
    /**
     * Withdrawn.
     */
    WITHDRAWN("Withdrawn") , 
    /**
     * Administratively Complete.
     */
    ADMINISTRATIVELY_COMPLETE("Administratively Complete");
    
         private String code;

         /**
          * Constructor for TrialStatusCode.
          * @param code
          */

         private TrialStatusReasonCode(String code) {
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
         public static TrialStatusReasonCode getByCode(String code) {
             return getByClassAndCode(TrialStatusReasonCode.class, code);
         }

         /**
          * construct a array of display names for Trial Status coded Enum.
          * @return String[] display names for StudyStatusCode
          */
         public static String[]  getDisplayNames() {
             TrialStatusCode[] trialStatusCodes = TrialStatusCode.values();
             String[] codedNames = new String[trialStatusCodes.length];
             for (int i = 0; i < trialStatusCodes.length; i++) {
                 codedNames[i] = trialStatusCodes[i].getCode();
             }
             return codedNames;
         }

}