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
package gov.nih.nci.accrual.service.batch;

import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * This class read CSV file and validates the input.
 * @author vrushali
 *
 */
@Stateless
@Local(CdusBatchUploadReaderServiceLocal.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CdusBatchUploadReaderBean extends BaseBatchUploadReader implements CdusBatchUploadReaderServiceLocal { 
    
    @EJB
    private CdusBatchUploadDataValidatorLocal cdusBatchUploadDataValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BatchValidationResults> validateBatchData(File file)  {
        List<BatchValidationResults> results = new ArrayList<BatchValidationResults>();
        boolean archive = StringUtils.equals(StringUtils.substringAfter(file.getName(), "."), "zip");       
        if (archive) {
            results.addAll(cdusBatchUploadDataValidator.validateArchiveBatchData(file));
        } else {
            results.add(cdusBatchUploadDataValidator.validateSingleBatchData(file));
        }
        return results;
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<BatchImportResults> importBatchData(File file) throws PAException {
        List<BatchValidationResults> validationResults = validateBatchData(file);
        for (BatchValidationResults validationResult : validationResults) {
            if (!validationResult.isPassedValidation()) {
                return new ArrayList<BatchImportResults>();
            }
        }
        //Only import the data if all files have passed validation
        return importBatchData(validationResults);
    }
       
    private List<BatchImportResults> importBatchData(List<BatchValidationResults> validationResults) 
        throws PAException {
        List<BatchImportResults> importResults = new ArrayList<BatchImportResults>();
        for (BatchValidationResults result : validationResults) {
            importResults.add(importBatchData(result));
        }
        return importResults;
    }
    
    /**
     * Imports a batch file if they have passed validation.
     * @param results the validation results
     * @return the batch import results
     * @throws PAException on error
     */
    private BatchImportResults importBatchData(BatchValidationResults results) throws PAException {
        BatchImportResults importResults = new BatchImportResults();
        if (!results.isPassedValidation()) {
            return importResults;
        }
        importResults.setFileName(results.getFileName());
        List<String[]> lines = results.getValidatedLines();
        String[] studyLine = BatchUploadUtils.getStudyLine(lines);
        
        String studyProtocolId = studyLine[1];
        StudyProtocolDTO spDto = getStudyProtocol(studyProtocolId);
       
        int count = createStudySubjects(lines, spDto);
        importResults.setTotalImports(count);
        return importResults;
    }

    /**
     * Handles creation of patients and study subjects.
     * @param lines the parsed lines
     * @param spDto the study protocol
     * @return the total number of create study subjects
     * @throws PAException on error
     */
    private int createStudySubjects(List<String[]> lines, StudyProtocolDTO spDto) throws PAException {
        //Create patients, then the study subjects
        List<String[]> patientsLines = BatchUploadUtils.getPatientInfo(lines);
        Map<String, List<String>> raceMap = BatchUploadUtils.getPatientRaceInfo(lines);
        int count = 0;
        for (String[] p : patientsLines) {
            List<String> races = raceMap.get(p[BatchFileIndex.PATIENT_ID_INDEX]);
            //We're assuming this is the assigned identifier for the organization associated with the health care 
            //facility of the study site.
            Ii studySiteOrgIi = getOrganizationIi(p[BatchFileIndex.PATIENT_REG_INST_ID_INDEX]);
            SearchStudySiteResultDto studySite = 
                getSearchStudySiteService().getStudySiteByOrg(spDto.getIdentifier(), studySiteOrgIi);
            
            PatientDto patient = getPatientService().create(parsePatient(p, races, studySite != null 
                    ? studySite.getOrganizationIi() : null));
            StudySubjectDto studySubject = getStudySubjectService().create(parseStudySubject(p, spDto.getIdentifier(),  
                    patient.getIdentifier(), studySite != null ? studySite.getStudySiteIi() : null));

            Date regDate = BatchUploadUtils.getDate(p[BatchFileIndex.PATIENT_REG_DATE_INDEX]);
            PerformedSubjectMilestoneDto psm = new PerformedSubjectMilestoneDto();
            psm.setStudySubjectIdentifier(studySubject.getIdentifier());
            psm.setStudyProtocolIdentifier(spDto.getIdentifier());
            psm.setRegistrationDate(TsConverter.convertToTs(new Timestamp(regDate.getTime())));
            getPerformedActivityService().createPerformedSubjectMilestone(psm);
            count++;
        }
        return count;
    }

   

    private PatientDto parsePatient(String[] line, List<String> races, Ii orgIi) throws PAException {
        PatientDto patient = new PatientDto();
        patient.setZip(StConverter.convertToSt(line[BatchFileIndex.PATIENT_ZIP_INDEX]));
        patient.setBirthDate(TsConverter.convertToTs(new Timestamp(
                BatchUploadUtils.getPatientDOB(line[BatchFileIndex.PATIENT_DOB_INDEX]).getTime())));
        patient.setGenderCode(CdConverter.convertToCd(
                CDUSPatientGenderCode.getByCode(line[BatchFileIndex.PATIENT_GENDER_INDEX]).getValue()));
        patient.setEthnicCode(CdConverter.convertToCd(
                CDUSPatientEthnicityCode.getByCode(line[BatchFileIndex.PATIENT_ETHNICITY_INDEX]).getValue()));
        if (CollectionUtils.isNotEmpty(races)) {
            patient.setRaceCode(DSetEnumConverter.convertSetToDSet(
                    CDUSPatientRaceCode.getCodesByCdusCodes(races)));
        }
        patient.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.PENDING));
        patient.setStatusDateRangeLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        patient.setOrganizationIdentifier(orgIi);
        
        //Default to United States if no country code is provided
        String countryCode = StringUtils.isEmpty(line[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX]) ? "US" 
                : line[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX];
        Country country = getCountryService().getByCode(countryCode);
        patient.setCountryIdentifier(IiConverter.convertToCountryIi(country.getId()));
        return patient;
    }

    private StudySubjectDto parseStudySubject(String[] line, Ii spIi, Ii patientIi, Ii studySiteIi) throws PAException {
        StudySubjectDto studySubject = new StudySubjectDto();
        studySubject.setStudyProtocolIdentifier(spIi);
        studySubject.setPatientIdentifier(patientIi);
        studySubject.setAssignedIdentifier(StConverter.convertToSt(line[BatchFileIndex.PATIENT_ID_INDEX]));
        CDUSPaymentMethodCode pmc = CDUSPaymentMethodCode.getByCode(line[BatchFileIndex.PATIENT_PAYMENT_METHOD_INDEX]);
        if (pmc != null) {
            studySubject.setPaymentMethodCode(CdConverter.convertToCd(pmc.getValue()));
        }
        if (!ISOUtil.isIiNull(studySiteIi)) {
            studySubject.setStudySiteIdentifier(studySiteIi);
        }

        String diseaseCode = line[BatchFileIndex.PATIENT_DISEASE_INDEX];

        if (StringUtils.isNotEmpty(diseaseCode)) {
            SDCDiseaseDTO disease = PaServiceLocator.getInstance().getDiseaseService().getByCode(diseaseCode);
            if (disease != null) {
                studySubject.setDiseaseIdentifier(disease.getIdentifier());
            } else {
                ICD9DiseaseDTO icd9Disease = PaServiceLocator.getInstance().getICD9DiseaseService()
                    .getByCode(diseaseCode);
                studySubject.setIcd9DiseaseIdentifier(icd9Disease.getIdentifier());
            }
        }
        

        studySubject.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studySubject.setStatusDateRange(
                IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        return studySubject;
    }

    

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendValidationErrorEmail(List<BatchValidationResults> validationResults) throws PAException {
        if (CollectionUtils.isEmpty(validationResults)) {
            return;
        }
        StringBuffer errorReport = new StringBuffer();
        for (BatchValidationResults result : validationResults) {
            if (!result.isPassedValidation()) {
                errorReport.append(String.format("Errors in batch file: %s\n\n%s\n", result.getFileName(), 
                        result.getErrors()));
            }
        }
        String subject = "Accrual Error Report";
        RegistryUser user = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                CaseSensitiveUsernameHolder.getUser());
        sendEmail(user.getEmailAddress(), subject, errorReport);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendConfirmationEmail(List<BatchImportResults> importResults) throws PAException {
        if (CollectionUtils.isEmpty(importResults)) {
            return;
        }
        StringBuffer confirmation = new StringBuffer();
        for (BatchImportResults result : importResults) {
            if (result.getTotalImports() > 0) {
                confirmation.append(String.format("Sucessfully imported %s patients from %s.\n", 
                        result.getTotalImports(), result.getFileName()));
            }
        }
        String subject = "Accrual Confirmation Report";
        RegistryUser user = PaServiceLocator.getInstance().getRegistryUserService().getUser(
                CaseSensitiveUsernameHolder.getUser());
        sendEmail(user.getEmailAddress(), subject, confirmation);
    }
    
    private void sendEmail(String to, String subject, StringBuffer msg) {
        if (StringUtils.isNotEmpty(msg.toString().trim())) {
            PaServiceLocator.getInstance().getMailManagerService().sendMailWithAttachment(to, subject, msg.toString(), 
                    null);
        }
    }

    /**
     * @param cdusBatchUploadDataValidator the cdusBatchUploadDataValidator to set
     */
    public void setCdusBatchUploadDataValidator(CdusBatchUploadDataValidatorLocal cdusBatchUploadDataValidator) {
        this.cdusBatchUploadDataValidator = cdusBatchUploadDataValidator;
    }
    
    
}
