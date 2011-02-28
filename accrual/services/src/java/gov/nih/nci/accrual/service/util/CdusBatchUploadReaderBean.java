/**
 *  The software subject to this notice and license includes both human readable
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

import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.service.PatientServiceLocal;
import gov.nih.nci.accrual.service.PerformedActivityServiceLocal;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.SubmissionServiceLocal;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.AccrualSubmissionStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This class read CSV file and validates the input.
 * @author vrushali
 *
 */
@Stateless
@Local(CdusBatchUploadReaderServiceLocal.class)
public class CdusBatchUploadReaderBean extends BaseBatchUploadReader implements CdusBatchUploadReaderServiceLocal {
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
     * The index of the identifier of a line (i.e it's type: PATIENT, ACCRUAL_COUNT, COLLECTION, etc.).
     */
    private static final int LINE_IDENTIFIER_INDEX = 0;
    private static final int COLLECTION_EMAIL_INDEX = 9;
    private static final int COLLECTION_PROTOCOL_INDEX = 0;
    private static final int STUDY_CUTOFF_DATE_INDEX = 3;
    private static final int PATIENT_ID_INDEX = 2;
    private static final int PATIENT_ZIP_INDEX = 3;
    private static final int PATIENT_COUNTRY_CODE_INDEX = 4;
    private static final int PATIENT_DOB_INDEX = 5;
    private static final int PATIENT_GENDER_INDEX = 6;
    private static final int PATIENT_ETHNICITY_INDEX = 7;
    private static final int PATIENT_PAYMENT_METHOD_INDEX = 8;
    private static final int PATIENT_REG_DATE_INDEX = 9;
    private static final int PATIENT_REG_INST_ID_INDEX = 10;
    private static final int PATIENT_DISEASE_INDEX = 20;


    private static final Logger LOG = Logger.getLogger(CdusBatchUploadReaderBean.class);

    /**
     * 
     * {@inheritDoc}
     */
    public BatchValidationResults validateBatchData(File file)  {
        StringBuffer errMsg = new StringBuffer();
        BatchValidationResults results = new BatchValidationResults();
        results.setFileName(file.getName());
        try {
            CSVReader parser = new CSVReader(new FileReader(file), ',');
            List<String[]> lines = parser.readAll();
            setPatientsIdList(new ArrayList<String>());
            long lineNumber = 0;
            for (String[] line : lines) {
                ++lineNumber;
                if (StringUtils.equalsIgnoreCase("COLLECTIONS", line[LINE_IDENTIFIER_INDEX]) 
                        && line.length > COLLECTION_EMAIL_INDEX) {
                    results.setMailTo(line[COLLECTION_EMAIL_INDEX]);
                }
                errMsg.append(validateBatchData(line, lineNumber)).append('\n');
            }
            results.setErrors(new StringBuilder(errMsg.toString().trim()));
            if (StringUtils.isEmpty(errMsg.toString().trim())) {
                results.setValidatedLines(lines);
                results.setPassedValidation(true);
            }
        } catch (IOException e) {
            LOG.error("error reading the file ", e);
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public BatchImportResults importBatchData(BatchValidationResults results) throws PAException, RemoteException {
        BatchImportResults importResults = new BatchImportResults();
        importResults.setMailTo(results.getMailTo());
        if (!results.isPassedValidation()) {
            return importResults;
        }
        List<String[]> lines = results.getValidatedLines();
        
        String[] studyLine = BatchUploadUtils.getStudyLine(lines);
        Integer totalNumberOfAccruals = BatchUploadUtils.getTotalNumberOfAccruals(lines);

        //1. Load trial. This will be replaced
        Ii spIi = IiConverter.convertToAssignedIdentifierIi(studyLine[1]);
        StudyProtocolDTO spDto = PaRegistry.getStudyProtocolService().getStudyProtocol(spIi);

        //2. Create submission
        String cutoff = studyLine[STUDY_CUTOFF_DATE_INDEX];
        String label = "Batch Accrual Submission for Study Protocol " + spIi.getExtension() + " on " + cutoff;
        Date cutoffDate = BatchUploadUtils.getDate(cutoff);
        createSubmission(label, cutoffDate, spDto.getIdentifier(), totalNumberOfAccruals);

        //3. Create patients, then the study subjects
        List<String[]> patientsLines = BatchUploadUtils.getPatientInfo(lines);
        Map<String, List<String>> raceMap = BatchUploadUtils.getPatientRaceInfo(lines);
        int count = 0;
        for (String[] p : patientsLines) {
            List<String> races = raceMap.get(p[PATIENT_ID_INDEX]);
            PatientDto patient = patientService.create(parsePatient(p, races));
            StudySubjectDto studySubject = 
                studySubjectService.create(parseStudySubject(p, spDto.getIdentifier(),  patient.getIdentifier()));

            Date regDate = BatchUploadUtils.getDate(p[PATIENT_REG_DATE_INDEX]);
            PerformedSubjectMilestoneDto psm = new PerformedSubjectMilestoneDto();
            psm.setStudySubjectIdentifier(studySubject.getIdentifier());
            psm.setStudyProtocolIdentifier(spDto.getIdentifier());
            psm.setRegistrationDate(TsConverter.convertToTs(new Timestamp(regDate.getTime())));
            performedActivityService.create(psm);
            count++;
        }
        
        importResults.setTotalImports(count);
        return importResults;
    }

    /**
     * Handles creation of the submission for this batch import.  Will close previous submissions and set any pending
     * study subjects active.
     */
    private void createSubmission(String label, Date cutoffDate, Ii studyProtocolIi, Integer totalAccruals) 
    throws RemoteException, PAException {
        SubmissionDto oldSubmission = submissionService.getOpenSubmission(studyProtocolIi);
        if (oldSubmission != null) {
            oldSubmission.setStatusCode(CdConverter.convertToCd(AccrualSubmissionStatusCode.SUBMITTED));
            Ivl<Ts> ivl = oldSubmission.getStatusDateRange();
            ivl.setHigh(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
            oldSubmission.setStatusDateRange(ivl);
            submissionService.update(oldSubmission);

            List<StudySubjectDto> subjects = studySubjectService.getByStudyProtocol(studyProtocolIi);
            for (StudySubjectDto subject : subjects) {
                if (StringUtils.equalsIgnoreCase(FunctionalRoleStatusCode.PENDING.getCode(), 
                        CdConverter.convertCdToString(subject.getStatusCode()))) {
                    subject.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
                    subject.setStatusDateRange(
                            IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
                    studySubjectService.update(subject);
                }
            }
        }
        SubmissionDto submission = new SubmissionDto();
        submission.setLabel(StConverter.convertToSt(label));
        submission.setCutOffDate(TsConverter.convertToTs(new Timestamp(cutoffDate.getTime())));
        submission.setDescription(StConverter.convertToSt(label));
        submission.setStudyProtocolIdentifier(studyProtocolIi);
        submission.setTotalNumberOfAccruals(IntConverter.convertToInt(totalAccruals));
        submission.setStatusCode(CdConverter.convertToCd(AccrualSubmissionStatusCode.OPENED));
        submissionService.create(submission);   
    }

    private PatientDto parsePatient(String[] line, List<String> races) throws PAException {
        PatientDto patient = new PatientDto();
        patient.setZip(StConverter.convertToSt(line[PATIENT_ZIP_INDEX]));
        patient.setBirthDate(TsConverter.convertToTs(new Timestamp(
                BatchUploadUtils.getPatientDOB(line[PATIENT_DOB_INDEX]).getTime())));
        patient.setGenderCode(CdConverter.convertToCd(
                CDUSPatientGenderCode.getByCode(line[PATIENT_GENDER_INDEX])));
        patient.setEthnicCode(CdConverter.convertToCd(
                CDUSPatientEthnicityCode.getByCode(line[PATIENT_ETHNICITY_INDEX])));
        if (CollectionUtils.isNotEmpty(races)) {
            patient.setRaceCode(DSetEnumConverter.convertSetToDSet(
                    CDUSPatientRaceCode.getCodesByCdusCodes(races)));
        }
        patient.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.PENDING));
        patient.setStatusDateRangeLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));

        //Default to United States if no country code is provided
        String countryCode = StringUtils.isEmpty(line[PATIENT_COUNTRY_CODE_INDEX]) ? "US" 
                : line[PATIENT_COUNTRY_CODE_INDEX];
        Country country = countryService.getByCode(countryCode);
        patient.setCountryIdentifier(IiConverter.convertToCountryIi(country.getId()));
        return patient;
    }

    private StudySubjectDto parseStudySubject(String[] line, Ii spIi, Ii patientIi) throws PAException {
        StudySubjectDto studySubject = new StudySubjectDto();
        studySubject.setStudyProtocolIdentifier(spIi);
        studySubject.setPatientIdentifier(patientIi);
        studySubject.setAssignedIdentifier(StConverter.convertToSt(line[PATIENT_ID_INDEX]));
        CDUSPaymentMethodCode pmc = 
            CDUSPaymentMethodCode.getByCode(line[PATIENT_PAYMENT_METHOD_INDEX]);
        if (pmc != null) {
            studySubject.setPaymentMethodCode(CdConverter.convertToCd(pmc.getValue()));
        }

        //Assuming that the id here is the local identifier
        St studySiteId = StConverter.convertToSt(line[PATIENT_REG_INST_ID_INDEX]);
        studySubject.setStudySiteIdentifier(
                searchStudySiteService.getStudySiteIdentifierByLocalIdentifier(spIi, studySiteId));

        //Get disease by meddra code once Marwan finishes the disease portion.
        String medraCode = line[PATIENT_DISEASE_INDEX];
        LOG.debug("Found disease: " + medraCode);
        studySubject.setDiseaseIdentifier(null);

        studySubject.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studySubject.setStatusDateRange(
                IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        return studySubject;
    }

    /**
     * This method split the data into key and value.
     * @param data data
     * @param lineNumber lineNumber
     * @return errMsg if any
     */
    private String validateBatchData(String[] data, long lineNumber) {
        String key = data[0];
        List<String> values = Arrays.asList((String[]) ArrayUtils.subarray(data, 1, data.length));
        StringBuffer errMsg = new StringBuffer();
        if (LIST_OF_ELEMENT.containsKey(key) && LIST_OF_ELEMENT.get(key) != values.size()) {
            errMsg.append(key).append(appendLineNumber(lineNumber));
            errMsg.append(" does not have correct number of elements.");
        }
        validateProtocolNumber(key, values, errMsg, lineNumber);
        validatePatientID(key, values, errMsg, lineNumber);
        validatePatientsMandatoryData(key, values, errMsg, lineNumber);
        validatePatientRaceData(key, values, errMsg, lineNumber);
        validateAccuralCount(key, values, errMsg, lineNumber);
        return errMsg.toString();
    }

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg err if any
     * @param lineNumber 
     */
    private void validateProtocolNumber(String key, List<String> values, StringBuffer errMsg, long lineNumber) {
        String protocolId = null;
        if (values.size() > COLLECTION_PROTOCOL_INDEX) {
            protocolId = values.get(COLLECTION_PROTOCOL_INDEX).trim();
        }
        if (StringUtils.isEmpty(protocolId)) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
            .append(" must contain a valid NCI protocol identifier or the CTEP/DCP identifier.");
        } else if (!isValidProtocolId(lineNumber, protocolId)) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
            .append(" is not a valid NCI or CTEP/DCP identifier.");
        }
    }

    private boolean isValidProtocolId(long lineNumber, String protocolId) {
        boolean isIdExists = false;
        try {
            StudyProtocolDTO dto = new StudyProtocolDTO();
            Ii protocolIi = new Ii();
            protocolIi.setExtension(protocolId);
            protocolIi.setIdentifierName(IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
            if (StringUtils.startsWith(protocolId, "NCI")) {
                protocolIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
            }
            //once we are able to identify CTEP/DCP format remove below comment
            /*if (DCP format) {
             *    protocolIi.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
             *} 
             *if (CTEP format) {
             *    protocolIi.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
             *}
             */
            dto.setIdentifier(protocolIi);
            LimitOffset pagingParams = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
            try {
                List<StudyProtocolDTO> dtos = PaRegistry.getStudyProtocolService().search(dto, pagingParams);
                if (CollectionUtils.isNotEmpty(dtos)) {
                    isIdExists = true; 
                }
            } catch (TooManyResultsException e) {
                LOG.error("error while validating protocol Number at line " + lineNumber , e);
            }
        } catch (PAException e) {
            LOG.error("error while validating protocol Number at line " + lineNumber , e);
        }
        return isIdExists;
    }
    
    /**
     * @param studySubjectService the studySubjectService to set
     */
    public void setStudySubjectService(StudySubjectServiceLocal studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    /**
     * @param patientService the patientService to set
     */
    public void setPatientService(PatientServiceLocal patientService) {
        this.patientService = patientService;
    }

    /**
     * @param countryService the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @param performedActivityService the performedActivityService to set
     */
    public void setPerformedActivityService(PerformedActivityServiceLocal performedActivityService) {
        this.performedActivityService = performedActivityService;
    }

    /**
     * @param searchStudySiteService the searchStudySiteService to set
     */
    public void setSearchStudySiteService(SearchStudySiteService searchStudySiteService) {
        this.searchStudySiteService = searchStudySiteService;
    }

    /**
     * @param submissionService the submissionService to set
     */
    public void setSubmissionService(SubmissionServiceLocal submissionService) {
        this.submissionService = submissionService;
    }
}
