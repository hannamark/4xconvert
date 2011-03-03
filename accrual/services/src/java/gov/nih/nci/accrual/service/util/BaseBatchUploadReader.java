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
package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.service.PatientServiceLocal;
import gov.nih.nci.accrual.service.PerformedActivityServiceLocal;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.SubmissionServiceLocal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DateValidator;

/**
 * Base Batch reader.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class BaseBatchUploadReader {
    private static final int COLLECTION_ELEMENT_SIZE = 10;
    private static final int PATIENTS_ELEMENT_SIZE = 23;
    private static final int PATIENT_RACES_ELEMENT_SIZE = 3;
    private static final int ACCRUAL_COUNT_ELEMENT_SIZE = 2;
    /**
     * List of elements.
     */
    protected static final Map<String, Integer> LIST_OF_ELEMENT = new HashMap<String, Integer>();
    static {
        LIST_OF_ELEMENT.put("COLLECTIONS", COLLECTION_ELEMENT_SIZE);
        LIST_OF_ELEMENT.put("PATIENTS", PATIENTS_ELEMENT_SIZE);
        LIST_OF_ELEMENT.put("PATIENT_RACES", PATIENT_RACES_ELEMENT_SIZE);
        LIST_OF_ELEMENT.put("ACCRUAL_COUNT", ACCRUAL_COUNT_ELEMENT_SIZE);
    }
    private static final int PATIENT_BRITH_DATE_INDEX = 4;
    private static final int PATIENT_GENDER_CODE_INDEX = 5;
    private static final int PATIENT_ETHNICITY_INDEX = 6;
    private static final int PATIENT_DATE_OF_ENTRY_INDEX = 8;
    private static final int PATIENT_REG_INST_ID_INDEX = 10;
    
    private static final List<String> PATIENT_GENDER = new ArrayList<String>();
    static {
        PATIENT_GENDER.addAll(Arrays.asList("1", "2", "9"));
    }
    private static final List<String> PATIENT_ETHNICITY = new ArrayList<String>();
    static {
        PATIENT_ETHNICITY.addAll(Arrays.asList("1", "2", "8", "9"));
    }
    private static final int PATIENT_RACE_CODE_INDEX = 2;
    private static final List<String> PATIENT_RACE_CODE = new ArrayList<String>();
    private static final int ACCURAL_COUNT_INDEX = 1;
    static {
        PATIENT_RACE_CODE.addAll(Arrays.asList("01", "03", "04", "05", "06", "98", "99"));
    }
    private static final int PATIENT_ID_INDEX = 1;
    private static final List<String> KEY_WITH_PATIENTS_IDS = new ArrayList<String>();
    static {
        KEY_WITH_PATIENTS_IDS.addAll(Arrays.asList("PATIENTS", "PATIENT_RACES"));
    }
    
    private List<String> patientsIdList = new ArrayList<String>();
    @EJB 
    private SubmissionServiceLocal submissionService;
    @EJB
    private StudySubjectServiceLocal studySubjectService;
    @EJB
    private PatientServiceLocal patientService;
    @EJB
    private CountryService countryService;
    @EJB
    private PerformedActivityServiceLocal performedActivityService;
    @EJB
    private SearchStudySiteService searchStudySiteService;

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number 
     */
    protected void validateAccuralCount(String key, List<String> values, StringBuffer errMsg, long lineNumber) {
        if (StringUtils.equalsIgnoreCase("ACCRUAL_COUNT", key)) {
            String accrualCOunt = StringUtils.trim(values.get(ACCURAL_COUNT_INDEX));
            if (StringUtils.isEmpty(accrualCOunt)) {
                errMsg.append("Accrual count is missing.").append(appendLineNumber(lineNumber));
            }
        }
    }
    /**
     * 
     * @param lineNumber line Number
     * @return string
     */
    protected String appendLineNumber(long lineNumber) {
        return " at line " + lineNumber;
    }
    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validatePatientsMandatoryData(String key, List<String> values, StringBuffer errMsg,
            long lineNumber) {
        if (StringUtils.equalsIgnoreCase("PATIENTS", key)) {
            isPatientIdUnique(getPatientId(values), errMsg, lineNumber);
            String pBirthDate = StringUtils.trim(values.get(PATIENT_BRITH_DATE_INDEX));
            if (StringUtils.isEmpty(pBirthDate)) {
                errMsg.append("Patient birth date is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
            } else if (!new DateValidator().isValid(pBirthDate, "yyyyMM", Locale.getDefault())) {
                errMsg.append("Patient birth date must be in YYYYMM format for patient ID ").append(getPatientId(
                        values)).append(appendLineNumber(lineNumber)).append('\n');
            }
            validateGender(values, errMsg, lineNumber);
            validateEthnicity(values, errMsg, lineNumber);
            validateDateOfEntry(values, errMsg, lineNumber);
            validateRegInstCode(values, errMsg, lineNumber);
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
            String pRaceCode = StringUtils.trim(values.get(PATIENT_RACE_CODE_INDEX));
            if (StringUtils.isEmpty(pRaceCode)) {
                errMsg.append("Patient race code is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
            } else if (!PATIENT_RACE_CODE.contains(pRaceCode.trim())) {
                errMsg.append("Patient race code is not valid for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
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
        String dateOfEntry = StringUtils.trim(values.get(PATIENT_DATE_OF_ENTRY_INDEX));
        if (StringUtils.isEmpty(dateOfEntry)) {
            errMsg.append("Patient date of entry is missing for patient ID ").append(getPatientId(values))
            .append(appendLineNumber(lineNumber)).append('\n');
        } else if (!new DateValidator().isValid(dateOfEntry, "yyyyMMdd", Locale.getDefault())) {
            errMsg.append("Patient date of entry must be in YYYYMMDD format for patient ID ")
            .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append('\n');
        }
    }
    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validateEthnicity(List<String> values, StringBuffer errMsg, long lineNumber) {
        String ethnicity = StringUtils.trim(values.get(PATIENT_ETHNICITY_INDEX));
        if (StringUtils.isEmpty(ethnicity)) {
            errMsg.append("Patient ethnicity is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
        } else if (!PATIENT_ETHNICITY.contains(ethnicity.trim())) {
            errMsg.append("Please enter valid patient ethnicity for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
        }
    }
    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number 
     */
    protected void validateGender(List<String> values, StringBuffer errMsg, long lineNumber) {
        String genderCode = StringUtils.trim(values.get(PATIENT_GENDER_CODE_INDEX));
        if (StringUtils.isEmpty(genderCode)) {
            errMsg.append("Patient gender is missing for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
        } else if (!PATIENT_GENDER.contains(genderCode.trim())) {
            errMsg.append("Must be a valid patient gender for patient ID ").append(getPatientId(values))
                .append(appendLineNumber(lineNumber)).append('\n');
        }
    }
    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    protected void validateRegInstCode(List<String> values, StringBuffer errMsg, long lineNumber) {
       String regInstID = StringUtils.trim(values.get(PATIENT_REG_INST_ID_INDEX));
       if (StringUtils.isEmpty(regInstID)) {
           errMsg.append("Patient Reg Inst Code is missing for patient ID ").append(getPatientId(values))
           .append(appendLineNumber(lineNumber)).append('\n');
       } 
    }

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg err if any
     * @param lineNumber line Number 
     */
    protected void validatePatientID(String key, List<String> values, StringBuffer errMsg, long lineNumber) {
        if (KEY_WITH_PATIENTS_IDS.contains(key) && StringUtils.isEmpty(getPatientId(values))) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
              .append(" must contain a patient identifier that is unique within the study.\n");
        } 
    }
    
    private void isPatientIdUnique(String patId, StringBuffer errMsg, long lineNumber) {
        if (patientsIdList.contains(patId)) {
            errMsg.append("Patient identifier " + patId + " is not unique ").append(appendLineNumber(lineNumber))
                .append('\n');
        } else {
            patientsIdList.add(patId);
        }
    }
    
    /**
     * 
     * @param values values
     * @return err if any
     */
    private String getPatientId(List<String> values) {
        return values.get(PATIENT_ID_INDEX).trim();
    }
    
    /**
     * @param patientsIdList the patientsIdList to set
     */
    public void setPatientsIdList(List<String> patientsIdList) {
        this.patientsIdList = patientsIdList;
    }
    
    /**
     * @return the submissionService
     */
    public SubmissionServiceLocal getSubmissionService() {
        return submissionService;
    }
    
    /**
     * @param submissionService the submissionService to set
     */
    public void setSubmissionService(SubmissionServiceLocal submissionService) {
        this.submissionService = submissionService;
    }
    
    /**
     * @return the studySubjectService
     */
    public StudySubjectServiceLocal getStudySubjectService() {
        return studySubjectService;
    }
    
    /**
     * @param studySubjectService the studySubjectService to set
     */
    public void setStudySubjectService(StudySubjectServiceLocal studySubjectService) {
        this.studySubjectService = studySubjectService;
    }
    
    /**
     * @return the patientService
     */
    public PatientServiceLocal getPatientService() {
        return patientService;
    }
    
    /**
     * @param patientService the patientService to set
     */
    public void setPatientService(PatientServiceLocal patientService) {
        this.patientService = patientService;
    }
    
    /**
     * @return the countryService
     */
    public CountryService getCountryService() {
        return countryService;
    }
    
    /**
     * @param countryService the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
    
    /**
     * @return the performedActivityService
     */
    public PerformedActivityServiceLocal getPerformedActivityService() {
        return performedActivityService;
    }
    
    /**
     * @param performedActivityService the performedActivityService to set
     */
    public void setPerformedActivityService(PerformedActivityServiceLocal performedActivityService) {
        this.performedActivityService = performedActivityService;
    }
    
    /**
     * @return the searchStudySiteService
     */
    public SearchStudySiteService getSearchStudySiteService() {
        return searchStudySiteService;
    }
    
    /**
     * @param searchStudySiteService the searchStudySiteService to set
     */
    public void setSearchStudySiteService(SearchStudySiteService searchStudySiteService) {
        this.searchStudySiteService = searchStudySiteService;
    }
}
