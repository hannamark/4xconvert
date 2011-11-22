/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.registry.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

import gov.nih.nci.pa.enums.CodedEnum;

/**
 * @author asharma
 * 
 */
public enum BatchStringConstants implements CodedEnum<String> {

    /** */
    UNIQUE_TRIAL_IDENTIFIER("Unique Trial Identifier", "setUniqueTrialId"),
    /** */
    SUBMISSION_TYPE("Submission Type", "setSubmissionType"),
    /** */
    NCI_TRIAL_IDENTIFIER("NCI Trial Identifier", "setNciTrialIdentifier"),
    /** */
    CTGOV_XML_INDICATOR("ClinicalTrials.gov XML Required?", "setCtGovXmlIndicator"),
    /** */
    AMENDMENT_NUMBER("Amendment Number", "setAmendmentNumber"),
    /** */
    AMENDMENT_DATE("Amendment Date", "setAmendmentDate"),
    /** */
    LEAD_ORGANIZATION_TRIAL_IDENTIFIER("Lead Organization Trial Identifier", "setLocalProtocolIdentifier"),
    /** */
    NCT_NUMBER("NCT", "setNctNumber"),
    /** */
    OTHER_TRIAL_IDENTIFIER("Other Trial Identifier", "setOtherTrialIdentifiers"),
    /** */
    TITLE("Title", "setTitle"),
    /** */
    TRIAL_TYPE("Trial Type", "setTrialType"),
    /** */
    PRIMARY_PURPOSE("Primary Purpose", "setPrimaryPurpose"),
    /** */
    PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER("[Primary Purpose] Additional Qualifier",
            "setPrimaryPurposeAdditionalQualifierCode"),
    /** */
    PRIMARY_PURPOSE_OTHER_TEXT("[Primary Purpose] Other Text", "setPrimaryPurposeOtherText"),
    /** */
    PHASE("Phase", "setPhase"),
    /** */
    PHASE_ADDITIONAL_QUALIFIER("Pilot Trial?", "setPhaseAdditionalQualifierCode"),
    /** */
    SPONSOR_ORG_NAME("[Sponsor] Organization Name", "setSponsorOrgName"),
    /** */
    SPONSOR_CETP_ORG_NO("[Sponsor] Organization PO-ID", "setSponsorPOId"),
    /** */
    SPONSOR_STREET_ADDRESS("[Sponsor] Street Address", "setSponsorStreetAddress"),
    /** */
    SPONSOR_CITY("[Sponsor] City", "setSponsorCity"),
    /** */
    SPONSOR_STATE("[Sponsor] State/Province", "setSponsorState"),
    /** */
    SPONSOR_ZIP("[Sponsor] Zip/Postal code", "setSponsorZip"),
    /** */
    SPONSOR_COUNTRY("[Sponsor] Country", "setSponsorCountry"),
    /** */
    SPONSOR_EMAIL("[Sponsor] Email Address", "setSponsorEmail"),
    /** */
    SPONSOR_PHONE("[Sponsor] Phone", "setSponsorPhone"),
    /** */
    SPONSOR_TTY("[Sponsor] TTY", "setSponsorTTY"),
    /** */
    SPONSOR_FAX("[Sponsor] FAX", "setSponsorFax"),
    /** */
    SPONSOR_URL("[Sponsor] URL", "setSponsorURL"),
    /** */
    RESPONSIBLE_PARTY("Responsible Party", "setResponsibleParty"),
    /** */
    SPONSOR_CONTACT_TYPE("Sponsor Contact Type", "setSponsorContactType"),
    /** */
    SPONSOR_CONTACT_TITLE("[Sponsor Contact] Title", "setResponsibleGenericContactName"),
    /** */
    SPONSOR_CONTACT_FIRST_NAME("[Sponsor Contact] First Name", "setSponsorContactFName"),
    /** */
    SPONSOR_CONTACT_MIDDLE_NAME("[Sponsor Contact] Middle Name", "setSponsorContactMName"),
    /** */
    SPONSOR_CONTACT_LAST_NAME("[Sponsor Contact] Last Name", "setSponsorContactLName"),
    /** */
    SPONSOR_CONTACT_PERSON_ID("[Sponsor Contact] Person PO-ID", "setSponsorContactPOId"),
    /** */
    SPONSOR_CONTACT_STREET_ADDRESS("[Sponsor Contact] Street Address", "setSponsorContactStreetAddress"),
    /** */
    SPONSOR_CONTACT_CITY("[Sponsor Contact] City", "setSponsorContactCity"),
    /** */
    SPONSOR_CONTACT_STATE("[Sponsor Contact] State/Province", "setSponsorContactState"),
    /** */
    SPONSOR_CONTACT_ZIP("[Sponsor Contact] Zip/Postal code", "setSponsorContactZip"),
    /** */
    SPONSOR_CONTACT_COUNTRY("[Sponsor Contact] Country", "setSponsorContactCountry"),
    /** */
    SPONSOR_CONTACT_EMAIL_ID("[Sponsor Contact] Email Address", "setSponsorContactEmail"),
    /** */
    SPONSOR_CONTACT_PHONE("[Sponsor Contact] Phone", "setSponsorContactPhone"),
    /** */
    SPONSOR_CONTACT_TTY("[Sponsor Contact] TTY", "setSponsorContactTTY"),
    /** */
    SPONSOR_CONTACT_FAX("[Sponsor Contact] FAX", "setSponsorContactFax"),
    /** */
    SPONSOR_CONTACT_URL("[Sponsor Contact] URL", "setSponsorContactUrl"),
    /** */
    LEAD_ORG_NAME("[Lead Organization] Name", "setLeadOrgName"),
    /** */
    LEAD_ORG_PO_ID("[Lead Organization] Organization PO-ID", "setLeadOrgPOId"),
    /** */
    LEAD_ORG_STREET_ADDRESS("[Lead Organization] Street Address", "setLeadOrgStreetAddress"),
    /** */
    LEAD_ORG_CITY("[Lead Organization] City", "setLeadOrgCity"),
    /** */
    LEAD_ORG_STATE("[Lead Organization] State/Province", "setLeadOrgState"),
    /** */
    LEAD_ORG_ZIP("[Lead Organization] Zip/Postal code", "setLeadOrgZip"),
    /** */
    LEAD_ORG_COUNTRY("[Lead Organization] Country", "setLeadOrgCountry"),
    /** */
    LEAD_ORG_EMAIL("[Lead Organization] Email Address", "setLeadOrgEmail"),
    /** */
    LEAD_ORG_PHONE("[Lead Organization] Phone", "setLeadOrgPhone"),
    /** */
    LEAD_ORG_TTY("[Lead Organization] TTY", "setLeadOrgTTY"),
    /** */
    LEAD_ORG_FAX("[Lead Organization] FAX", "setLeadOrgFax"),
    /** */
    LEAD_ORG_URL("[Lead Organization] URL", "setLeadOrgUrl"),
    /** */
    LEAD_ORG_TYPE("[Lead Organization] Organization Type", "setLeadOrgType"),
    /** */
    PI_FIRST_NAME("[Principal Investigator] First Name", "setPiFirstName"),
    /** */
    PI_MIDDLE_NAME("[Principal Investigator] Middle Name", "setPiMiddleName"),
    /** */
    PI_LAST_NAME("[Principal Investigator] Last Name", "setPiLastName"),
    /** */
    PI_PERSON_PERSON_PO_ID("[Principal Investigator] Person PO-ID", "setPiPOId"),
    /** */
    PI_STREET_ADDRESS("[Principal Investigator] Street Address", "setPiStreetAddress"),
    /** */
    PI_CITY("[Principal Investigator] City", "setPiCity"),
    /** */
    PI_STATE("[Principal Investigator] State/Province", "setPiState"),
    /** */
    PI_ZIP("[Principal Investigator] Zip/Postal code", "setPiZip"),
    /** */
    PI_COUNTRY("[Principal Investigator] Country", "setPiCountry"),
    /** */
    PI_EMAIL("[Principal Investigator] Email Address", "setPiEmail"),
    /** */
    PI_PHONE("[Principal Investigator] Phone", "setPiPhone"),
    /** */
    PI_TTY("[Principal Investigator] TTY", "setPiTTY"),
    /** */
    PI_FAX("[Principal Investigator] FAX", "setPiFax"),
    /** */
    PI_URL("[Principal Investigator] URL", "setPiUrl"),
    /** */
    S4_FUND_CAT("Summary 4 Funding Category", "setSumm4FundingCat"),
    /** */
    S4_FUND_ORG_NAME("[Summary 4 Funding Sponsor/Source] Organization Name", "setSumm4OrgName"),
    /** */
    S4_FUND_ORG_ORG_PO_ID("[Summary 4 Funding Sponsor/Source] Organization PO-ID", "setSumm4OrgPOId"),
    /** */
    S4_FUND_ORG_STREET_ADDRESS("[Summary 4 Funding Sponsor/Source] Street Address", "setSumm4OrgStreetAddress"),
    /** */
    S4_FUND_CITY("[Summary 4 Funding Sponsor/Source] City", "setSumm4City"),
    /** */
    S4_FUND_STATE("[Summary 4 Funding Sponsor/Source] State/Province", "setSumm4State"),
    /** */
    S4_FUND_ZIP("[Summary 4 Funding Sponsor/Source] Zip/Postal code", "setSumm4Zip"),
    /** */
    S4_FUND_COUNTRY("[Summary 4 Funding Sponsor/Source ] Country", "setSumm4Country"),
    /** */
    S4_FUND_EMAIL("[Summary 4 Funding Sponsor/Source ] Email Address", "setSumm4Email"),
    /** */
    S4_FUND_PHONE("[Summary 4 Funding Sponsor/Source ] Phone", "setSumm4Phone"),
    /** */
    S4_FUND_TTY("[Summary 4 Funding Sponsor/Source ] TTY", "setSumm4TTY"),
    /** */
    S4_FUND_FAX("[Summary 4 Funding Sponsor/Source ] FAX", "setSumm4Fax"),
    /** */
    S4_FUND_URL("[Summary 4 Funding Sponsor/Source ] URL", "setSumm4Url"),
    /** */
    S4_PRG_CODE_TEXT("Program Code", "setProgramCodeText"),
    /** */
    NIH_GRANT_FUND_MC("[NIH Grant] Funding Mechanism", "setNihGrantFundingMechanism"),
    /** */
    NIH_GRANT_INSTITUTE_CODE("[NIH Grant] Institute Code", "setNihGrantInstituteCode"),
    /** */
    NIH_GRANT_SR_NO("[NIH Grant] Serial Number", "setNihGrantSrNumber"),
    /** */
    NIH_GRANT_NCI_DIV_CODE("[NIH Grant] NCI Division/Program Code", "setNihGrantNCIDivisionCode"),
    /** */
    CURRENT_TRIAL_STATUS("Current Trial Status", "setCurrentTrialStatus"),
    /** */
    REASON_FOR_STUDY_STOPPED("Why Study Stopped?", "setReasonForStudyStopped"),
    /** */
    CURRENT_TRIAL_STATUS_DATE("Current Trial Status Date", "setCurrentTrialStatusDate"),
    /** */
    STUDY_START_DATE("Study Start Date", "setStudyStartDate"),
    /** */
    STUDY_START_DATE_TYPE("Study Start Date Type", "setStudyStartDateType"),
    /** */
    PRIMARY_COMP_DATE("Primary Completion Date", "setPrimaryCompletionDate"),
    /** */
    PRIMARY_COMP_DATE_TYPE("Primary Completion Date Type", "setPrimaryCompletionDateType"),
    /** */
    IND_TYPE("IND/IDE Type", "setIndType"),
    /** */
    IND_NUMBER("IND/IDE Number", "setIndNumber"),
    /** */
    IND_GRANTOR("IND/IDE Grantor", "setIndGrantor"),
    /** */
    IND_HOLDER_TYPE("IND/IDE Holder Type", "setIndHolderType"),
    /** */
    IND_NIH_INSTITUTION("[IND/IDE] NIH Institution", "setIndNIHInstitution"),
    /** */
    IND_NCI_DIV_CODE("[IND/IDE] NCI Division /Program", "setIndNCIDivision"),
    /** */
    IND_HAS_EXPANDED_ACCESS("[IND/IDE] Has Expanded Access?", "setIndHasExpandedAccess"),
    /** */
    IND_EXPANED_ACCESS_STATUS("[IND/IDE] Expanded Access Status", "setIndExpandedAccessStatus"),
    /** */
    IND_EXEMPT_INDICATOR("[IND/IDE] Exempt Indicator", "setExemptIndicator"),
    /** */
    OVERSIGHT_AUTHORITY_COUNTRY("Oversight Authority Country", "setOversightAuthorityCountry"),
    /** */
    OVERSIGHT_AUTHORITY_ORG_NAME("Oversight Authority Organization Name", "setOversightOrgName"),
    /** */
    FDA_REGULATORY_INFORMATION_INDICATOR("FDA Regulatory Information Indicator", 
                                         "setFdaRegulatoryInformationIndicator"),
    /** */
    SECTION_801_INDICATOR("Section 801 Indicator", "setSection801Indicator"),
    /** */
    DELAYED_POSTING_INDICATOR("Delayed Posting Indicator", "setDelayedPostingIndicator"),
    /** */
    DATA_MONITORING_COMMITTEE_APPOINTED_INDICATOR("Data Monitoring Committee Appointed Indicator",
            "setDataMonitoringCommitteeAppointedIndicator"),
    /** */
    PROTOCOL_DOC_FILE_NAME("Protocol Document File Name", "setProtcolDocumentFileName"),
    /** */
    IRB_APPROVAL_DOC_FILE_NAME("IRB Approval Document File Name", "setIrbApprovalDocumentFileName"),
    /** */
    PARTICIPATIING_SITE_DOC_FILE_NAME("Participating Sites Document File Name", "setParticipatinSiteDocumentFileName"),
    /** */
    INFORMED_CONSENT_DOC_FILE_NAME("Informed Consent Document File Name", "setInformedConsentDocumentFileName"),
    /** */
    OTHER_TRIAL_DOC_FILE_NAME("Other Trial Related Document File Name", "setOtherTrialRelDocumentFileName"),
    /** */
    CHANGE_MEMO_DOC_FILE_NAME("Change Memo Document Name", "setChangeRequestDocFileName"),
    /** */
    PROTOCOL_HIGHLIGHTED_DOC_FILE_NAME("Protocol Highlight Document Name", "setProtocolHighlightDocFileName");

    private String code;
    private String methodName;

    /**
     * Constructor for BatchStringConstants.
     * @param code
     */

    private BatchStringConstants(String code, String methodName) {
        this.code = code;
        this.methodName = methodName;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return methodName coded value of enum
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return String DisplayName
     */
    @Override
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
     * @return BatchStringConstants
     */
    public static BatchStringConstants getByCode(String code) {
        return getByClassAndCode(BatchStringConstants.class, code);
    }

    /**
     * construct a array of display names for BatchStringConstantsEnum.
     * @return String[] display names for BatchStringConstants
     */
    public static String[] getDisplayNames() {
        BatchStringConstants[] batchStringConstants = BatchStringConstants.values();
        String[] codedNames = new String[batchStringConstants.length];
        for (int i = 0; i < batchStringConstants.length; i++) {
            codedNames[i] = batchStringConstants[i].getCode();
        }
        return codedNames;
    }

}
