/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Hugh Reinhart
 * @since 01/14/2009
 */
public enum MilestoneCode implements CodedEnum<String> {

    /** 1. */
    SUBMISSION_RECEIVED("Submission Received Date", true, false, null, true, true),
    /** 2. */
    SUBMISSION_ACCEPTED("Submission Acceptance Date", true, false, null, true, true),
    /** 3. */
    SUBMISSION_REJECTED("Submission Rejection Date", true, false, null, true, true),
    /** 4. */
    ADMINISTRATIVE_PROCESSING_START_DATE("Administrative processing start date", false, false, null, true, true),
    /** 5. */
    ADMINISTRATIVE_PROCESSING_COMPLETED_DATE("Administrative processing completed date", false, false,
            MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, true, true),
    /** 6. */
    SCIENTIFIC_PROCESSING_START_DATE("Scientific processing start date", false, false, null, true, true),
    /** 7. */
    SCIENTIFIC_PROCESSING_COMPLETED_DATE("Scientific processing completed date", false, false,
            MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, true, true),
    /** 8. */
    READY_FOR_QC("Ready for QC Date", false, true, null, true, true),
    /** 9. */
    QC_START("QC Start Date", false, false, MilestoneCode.READY_FOR_QC, true, true),
    /** 10. */
    QC_COMPLETE("QC Completed Date", false, true, MilestoneCode.QC_START, false, false),
    /** 11. */
    TRIAL_SUMMARY_SENT("Trial Summary Report Sent Date", false, true, null, false, false),
    /** 12. */
    TRIAL_SUMMARY_FEEDBACK("Submitter Trial Summary Report Feedback Date", false, false,
             MilestoneCode.TRIAL_SUMMARY_SENT, false, false),
    /** 13. */
    INITIAL_ABSTRACTION_VERIFY("Initial Abstraction Verified Date", true, true, null, false, false),
    /** 14. */
    INITIAL_SUBMISSION_TO_CLINICALTRIALS_GOV_DATE("Initial Submission To Clinicaltrials.gov Date", true,
            false, null, false, false),
    /** 15. */
    ONGOING_ABSTRACTION_VERIFICATION("On-going Abstraction Verified Date", false, true, null, false, false),
    /** 16. */
    LATE_REJECTION_DATE("Late Rejection Date", true, false, MilestoneCode.SUBMISSION_ACCEPTED, false, false);

    private String code;
    private boolean unique;
    private boolean validationTrigger;
    private MilestoneCode prerequisite;
    private boolean allowedIfOnhold;
    private boolean allowedInInBox;
    private static final Map<MilestoneCode, Set<DocumentWorkflowStatusCode>> ALLOWED_DWF_STATUSES;
    private static Set<MilestoneCode> tsrAndAbove = new HashSet<MilestoneCode>();
    static {
        Map<MilestoneCode, Set<DocumentWorkflowStatusCode>> tmp
                = new HashMap<MilestoneCode, Set<DocumentWorkflowStatusCode>>();

        Set<DocumentWorkflowStatusCode> tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.SUBMITTED);
        tmp.put(SUBMISSION_RECEIVED, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmp.put(SUBMISSION_ACCEPTED, Collections.unmodifiableSet(tmpSet));


        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.REJECTED);
        tmp.put(SUBMISSION_REJECTED, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(ADMINISTRATIVE_PROCESSING_START_DATE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(SCIENTIFIC_PROCESSING_START_DATE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(SCIENTIFIC_PROCESSING_COMPLETED_DATE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(READY_FOR_QC, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(QC_START, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(QC_COMPLETE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(TRIAL_SUMMARY_SENT, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(TRIAL_SUMMARY_FEEDBACK, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmp.put(INITIAL_ABSTRACTION_VERIFY, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(INITIAL_SUBMISSION_TO_CLINICALTRIALS_GOV_DATE, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(ONGOING_ABSTRACTION_VERIFICATION, Collections.unmodifiableSet(tmpSet));

        tmpSet = new HashSet<DocumentWorkflowStatusCode>();
        tmpSet.add(DocumentWorkflowStatusCode.ACCEPTED);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTED);
        tmpSet.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        tmpSet.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        tmp.put(LATE_REJECTION_DATE, Collections.unmodifiableSet(tmpSet));

        ALLOWED_DWF_STATUSES = Collections.unmodifiableMap(tmp);

        tsrAndAbove.add(MilestoneCode.TRIAL_SUMMARY_SENT);
        tsrAndAbove.add(MilestoneCode.TRIAL_SUMMARY_FEEDBACK);
        tsrAndAbove.add(MilestoneCode.INITIAL_ABSTRACTION_VERIFY);
        tsrAndAbove.add(MilestoneCode.INITIAL_SUBMISSION_TO_CLINICALTRIALS_GOV_DATE);
        tsrAndAbove.add(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION);
        tsrAndAbove.add(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION);

    }

    /**
     * Constructor for MilestoneCode.
     * @param code code
     * @param unique only one allowed per study
     * @param requiredDwfStatus required document workflow status
     * @param prerequisite prior milestone which must have been reached before this one
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    private MilestoneCode(String code, boolean unique, boolean validationTrigger, MilestoneCode prerequisite,
            boolean allowedIfOnhold, boolean allowedIfInBox) {
        this.code = code;
        this.unique = unique;
        this.validationTrigger = validationTrigger;
        this.prerequisite = prerequisite;
        this.allowedIfOnhold = allowedIfOnhold;
        this.allowedInInBox =  allowedIfInBox;
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
     * @return the uniqueConstraint
     */
    public boolean isUnique() {
        return unique;
    }


    /**
     * @return the validationTrigger
     */
    public boolean isValidationTrigger() {
        return validationTrigger;
    }

    /**
     * @return the prerequisite
     */
    public MilestoneCode getPrerequisite() {
        return prerequisite;
    }


    /**
     * @return the allowedIfOnhold
     */
    public boolean isAllowedIfOnhold() {
        return allowedIfOnhold;
    }
    /**
     *
     * @return the allowedIfInBox
     */
    public boolean isAllowedIfInBox() {
        return allowedInInBox;
    }
    /**
     * @param documentWorkflowStatusCode dwf status code to check
     * @return if milestone is valid for given dwf status code
     */
    public boolean isValidDwfStatus(DocumentWorkflowStatusCode documentWorkflowStatusCode) {
        return ALLOWED_DWF_STATUSES.get(this).contains(documentWorkflowStatusCode);
    }

    /**
     * @return all the valid document workflow statuses for this milestone
     */
    public List<DocumentWorkflowStatusCode> getValidDwfStatuses() {
        return new ArrayList<DocumentWorkflowStatusCode>(ALLOWED_DWF_STATUSES.get(this));
    }

    /**
     * @param code code
     * @return Study Type Code
     */
    public static MilestoneCode getByCode(String code) {
        return getByClassAndCode(MilestoneCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        MilestoneCode[] l = MilestoneCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

    /**
     * {@inheritDoc}
     */
    public String getNameByCode(String str) {
        return getByCode(str).name();
    }
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNamesForAddMilestone() {
        String[] codedNames = getDisplayNames();
        List<String> list = new ArrayList<String>(Arrays.asList(codedNames));
        list.remove(MilestoneCode.SUBMISSION_REJECTED.getCode());
        list.remove(MilestoneCode.LATE_REJECTION_DATE.getCode());
        codedNames = new String[list.size()];
        codedNames = list.toArray(codedNames);
        return codedNames;
    }
    /**
     *
     * @return milestone code for super user
     */
    public static String[] getDisplayNamesMilestoneForSuperUser() {
        String[] codedNames = getDisplayNames();
        List<String> list = new ArrayList<String>(Arrays.asList(codedNames));
        list.remove(MilestoneCode.SUBMISSION_REJECTED.getCode());
        codedNames = new String[list.size()];
        codedNames = list.toArray(codedNames);
        return codedNames;
    }

    /**
     * Checks if is above trial summary report.
     * @param mc the mc
     * @return true, if is above trial summary report
     */
    public static boolean isAboveTrialSummaryReport(MilestoneCode mc) {
        return tsrAndAbove.contains(mc);
    }
}
