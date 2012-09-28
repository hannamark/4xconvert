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
package gov.nih.nci.accrual.service.batch;

import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DateValidator;

/**
 * @author Igor Merenko
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.AppendCharacterWithChar" })
public class BaseValidatorBatchUploadReader extends BaseBatchUploadReader {  

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     * @param sp studyprotocol
     */
    protected void validateAccrualCount(String key, List<String> values, StringBuffer errMsg, long lineNumber,
            StudyProtocolDTO sp) {
        if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", key)) {
            validateStudyType(errMsg, sp);
            String accrualStudySite = AccrualUtil.safeGet(values, ACCRUAL_STUDY_SITE_INDEX);
            if (StringUtils.isEmpty(accrualStudySite)) {
                errMsg.append("Accrual study site is missing").append(appendLineNumber(lineNumber)).append("\n");
            }
            String accrualCount = AccrualUtil.safeGet(values, ACCRUAL_COUNT_INDEX);
            if (StringUtils.isEmpty(accrualCount)) {
                errMsg.append("Accrual count is missing").append(appendLineNumber(lineNumber)).append("\n");
            }
        }
    }

    private void validateStudyType(StringBuffer errMsg, StudyProtocolDTO sp) {
        if (sp != null && !sp.getProprietaryTrialIndicator().getValue()) {
            errMsg.append("Accrual count has been provided for a non Industrial study. This is not allowed.\n");
        }
    }
    
    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     * @param sp study protocol
     * @param genderCriterion gender
     * @param icd9Code 
     * @param sdcCode 
     * @param checkDisease 
     */
    @SuppressWarnings({ "PMD.AvoidDeeplyNestedIfStmts", "PMD.ExcessiveParameterList" })
 // CHECKSTYLE:OFF More than 7 Parameters
    protected void validatePatientsMandatoryData(String key, List<String> values, StringBuffer errMsg, long lineNumber,
            StudyProtocolDTO sp, PatientGenderCode genderCriterion, boolean sdcCode, boolean icd9Code, 
            boolean checkDisease) {
        // CHECKSTYLE:ON
        if (StringUtils.equalsIgnoreCase("PATIENTS", key)) {
            boolean trialType = true;            
            if (sp != null && sp.getProprietaryTrialIndicator().getValue()) {
                trialType = false;
            }
            if (trialType) {
                List<String> patientsIdList = new ArrayList<String>();
                isPatientIdUnique(getPatientId(values), errMsg, lineNumber, patientsIdList);
                String pBirthDate = AccrualUtil.safeGet(values, PATIENT_BRITH_DATE_INDEX);
                if (StringUtils.isEmpty(pBirthDate)) {
                    errMsg.append("Patient birth date is missing for patient ID ").append(getPatientId(values))
                    .append(appendLineNumber(lineNumber)).append("\n");
                } else if (!new DateValidator().isValid(pBirthDate, "yyyyMM", Locale.getDefault())) {
                    errMsg.append("Patient birth date must be in YYYYMM format for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n");
                }
                String countryCode = AccrualUtil.safeGet(values, PATIENT_COUNTRY_CODE_INDEX);
                if (!getCountryService().isValidAlpha2(countryCode)) {
                    errMsg.append("Please enter valid alpha2 country code for patient ID ").append(getPatientId(values))
                        .append(appendLineNumber(lineNumber)).append("\n");
                }
                validateGender(values, errMsg, lineNumber, genderCriterion);
                validateEthnicity(values, errMsg, lineNumber);
                validateDateOfEntry(values, errMsg, lineNumber);
                validateDiseaseCode(values, errMsg, lineNumber, sp, sdcCode, icd9Code, checkDisease);
                String paymentMethod = AccrualUtil.safeGet(values, PATIENT_PAYMENT_METHOD_INDEX);
                if (!StringUtils.isEmpty(paymentMethod) && !PATIENT_PAYMENT_METHOD.contains(paymentMethod.trim())) {
                    errMsg.append("Please enter valid patient payment method for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n");
                }
            } else {
                errMsg.append("Individual Patients should not be added to Industrial Trials for patient ID ")
                .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n");
            }
        }
    }

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validatePatientRaceData(String key, List<String> values, StringBuffer errMsg, long lineNumber) {
        if (StringUtils.equalsIgnoreCase("PATIENT_RACES", key)) {
            String pRaceCode = AccrualUtil.safeGet(values, PATIENT_RACE_CODE_INDEX);
            if (StringUtils.isEmpty(pRaceCode)) {
                errMsg.append("Patient race code is missing for patient ID ").append(getPatientId(values))
                    .append(appendLineNumber(lineNumber)).append("\n");
            } else if (!PATIENT_RACE_CODE.contains(pRaceCode.trim())) {
                errMsg.append("Patient race code is not valid for patient ID ").append(getPatientId(values))
                    .append(appendLineNumber(lineNumber)).append("\n");
            }
        }
    }

    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validateDateOfEntry(List<String> values, StringBuffer errMsg, long lineNumber) {
        String dateOfEntry = AccrualUtil.safeGet(values, PATIENT_DATE_OF_ENTRY_INDEX);
        if (StringUtils.isEmpty(dateOfEntry)) {
            errMsg.append("Patient date of entry is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        } else if (!new DateValidator().isValid(dateOfEntry, "yyyyMMdd", Locale.getDefault())) {
            errMsg.append("Patient date of entry must be in YYYYMMDD format for patient ID ")
                .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n");
        }
    }

    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validateEthnicity(List<String> values, StringBuffer errMsg, long lineNumber) {
        String ethnicity = AccrualUtil.safeGet(values, PATIENT_ETHNICITY_INDEX);
        if (StringUtils.isEmpty(ethnicity)) {
            errMsg.append("Patient ethnicity is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        } else if (!PATIENT_ETHNICITY.contains(ethnicity.trim())) {
            errMsg.append("Please enter valid patient ethnicity for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        }
    }

    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    private void validateGender(List<String> values, StringBuffer errMsg, long lineNumber, 
            PatientGenderCode genderCriterion) {
        String genderCode = AccrualUtil.safeGet(values, PATIENT_GENDER_CODE_INDEX);
        if (StringUtils.isEmpty(genderCode)) {
            errMsg.append("Patient gender is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        } else if (!PATIENT_GENDER.contains(genderCode.trim())) {
            errMsg.append("Must be a valid patient gender for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        } else if (PatientGenderCode.FEMALE.equals(genderCriterion)
                && CDUSPatientGenderCode.getByCode(genderCode).getCode().equals(PatientGenderCode.MALE.getCode())
            || PatientGenderCode.MALE.equals(genderCriterion)
                && CDUSPatientGenderCode.getByCode(genderCode).getCode().equals(PatientGenderCode.FEMALE.getCode())) {
            errMsg.append("Gender must not be " + genderCode + " for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        }
    }

    /**
     * Validates that the patient disease is provided and valid. If a study has the primary purpose 'Prevention', the
     * Meddra/ICD9 Disease code is not required.
     * @param values
     * @param errMsg
     * @param lineNumber
     * @param sp study protocol
     * @param icd9Code 
     * @param sdcCode 
     * @param checkDisease 
     */
    @SuppressWarnings({ "PMD.ExcessiveParameterList" })
    void validateDiseaseCode(List<String> values, StringBuffer errMsg, long lineNumber, StudyProtocolDTO sp, 
        boolean sdcCode, boolean icd9Code, boolean checkDisease) {
        PrimaryPurposeCode purpose = null;
        if (sp != null) {
            purpose = PrimaryPurposeCode.getByCode(CdConverter.convertCdToString(sp.getPrimaryPurposeCode()));
        }
        String code = AccrualUtil.safeGet(values, PATIENT_DISEASE_INDEX);
        if (StringUtils.isEmpty(code) && purpose != PrimaryPurposeCode.PREVENTION && !checkDisease) {
            errMsg.append("Patient Disease SDC or ICD9 Code is missing or not recognized for patient ID ")
            .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n");
        } else if (checkCodeExist(errMsg, code, sdcCode, icd9Code, lineNumber)) {
            errMsg.append("Patient Disease SDC or ICD9 Code is invalid for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append("\n");
        }
    }

    private boolean checkCodeExist(StringBuffer errMsg, String code, boolean sdcCode, 
            boolean icd9Code, long lineNumber) {
        SDCDiseaseDTO sdc = getDisease(code, errMsg);
        ICD9DiseaseDTO icd9 = getICD9Disease(code, errMsg);
        if (sdc != null && !sdcCode) {
            errMsg.append("Patient SDC Disease Code is used instaed of ICD9 Code ")
            .append(appendLineNumber(lineNumber)).append("\n");   
            return false;
        } else if (icd9 != null && !icd9Code) {
            errMsg.append("Patient ICD9 Disease Code is used instaed of SDC Code ")
            .append(appendLineNumber(lineNumber)).append("\n"); 
            return false;
        } 
        return StringUtils.isNotEmpty(code) && sdc == null && icd9 == null;
    }
    

    private void isPatientIdUnique(String patId, StringBuffer errMsg, long lineNumber, List<String> patientsIdList) {
        if (patientsIdList.contains(patId)) {
            errMsg.append("Patient identifier " + patId + " is not unique ").append(appendLineNumber(lineNumber))
                .append("\n");
        } else {
            patientsIdList.add(patId);
        }
    }  

}
