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

import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * This class read CSV file and validates the input.
 * @author vrushali
 *
 */
@Stateless
@Local(CdusBatchUploadReaderServiceLocal.class)
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.CyclomaticComplexity" })
public class CdusBatchUploadReaderBean extends BaseBatchUploadReader implements CdusBatchUploadReaderServiceLocal {
    private static final Logger LOG = Logger.getLogger(CdusBatchUploadReaderBean.class); 
    
    @EJB
    private CdusBatchUploadDataValidatorLocal cdusBatchUploadDataValidator;
    @EJB
    private SubjectAccrualServiceLocal subjectAccrualService;
    @EJB
    private BatchFileService batchFileSvc;
    
    private static final int RESULTS_LEN = 1000;
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final String DEFAULTBIRTHDATE = "100101";
    private static final String NCI_TRIAL_IDENTIFIER = "${nciTrialIdentifier}";

    // Cache for disease Ii's
    private static CacheManager cacheManager;
    private static final String DISEASE_CACHE_KEY = "BATCH_DISEASE_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 500;
    private static final long CACHE_TIME = 43200;

    private Cache getDiseaseCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(DISEASE_CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                    CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(DISEASE_CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(DISEASE_CACHE_KEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BatchValidationResults> validateBatchData(BatchFile batchFile)  {
        List<BatchValidationResults> results = new ArrayList<BatchValidationResults>();
        ZipFile zip = null;
        try {
            File file = new File(batchFile.getFileLocation());
            boolean archive = StringUtils.equals(StringUtils.substringAfter(file.getName(), "."), "zip");
            if (archive) {
                 zip = new ZipFile(file, ZipFile.OPEN_READ);
                Enumeration<? extends ZipEntry> files = zip.entries();
                while (files.hasMoreElements()) {
                    ZipEntry entry = files.nextElement();
                    File f = File.createTempFile(StringUtils.substringBefore(entry.getName(), "."), ".txt");
                    IOUtils.copy(zip.getInputStream(entry), FileUtils.openOutputStream(f));
                    batchFileProcessing(results, batchFile, entry.getName(), f);
                }
            } else {
                batchFileProcessing(results, batchFile, 
                        AccrualUtil.getFileNameWithoutRandomNumbers(file.getName()), file);
            }
            if (zip != null) {
                zip.close();
            }
        } catch (Exception e) {
            LOG.error("Error validating batch files.", e);
        } 
        return results;
    }
    
    private void batchFileProcessing(List<BatchValidationResults> results, BatchFile batchFile, 
            String fileName, File file) throws PAException {
        BatchValidationResults result = cdusBatchUploadDataValidator.validateSingleBatchData(
                file, batchFile.getSubmitter());
        result.setFileName(fileName);
        results.add(result);
        validateAndProcessData(batchFile, result);
    }

    private void validateAndProcessData(BatchFile batchFile, BatchValidationResults validationResult)
            throws PAException {
        AccrualCollections collection = new AccrualCollections();
        collection.setNciNumber(validationResult.getNciIdentifier());
        collection.setPassedValidation(validationResult.isPassedValidation());
        batchFileSvc.update(batchFile, collection);
        if (!validationResult.isPassedValidation()) {
            if (validationResult.getErrors() != null) {
                collection.setResults(StringUtils.left(validationResult.getErrors().toString(), RESULTS_LEN));
            }
            sendValidationErrorEmail(validationResult, batchFile);
        } else {
            batchFile.setPassedValidation(true);
            batchFile.setProcessed(true);
            BatchImportResults importResults = importBatchData(batchFile, validationResult);
            if (importResults.getErrors() != null) {
                collection.setResults(StringUtils.left(importResults.getErrors().toString(), RESULTS_LEN));
            }
            collection.setTotalImports(importResults.getTotalImports());
            sendConfirmationEmail(importResults, batchFile);
        }
        collection.setChangeCode(validationResult.getChangeCode());
        if (StringUtils.isNotBlank(collection.getResults())) {
            batchFile.setResults(collection.getResults());
        }
        batchFileSvc.update(batchFile, collection);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    public BatchImportResults importBatchData(BatchFile batchFile, BatchValidationResults validationResult) 
            throws PAException {
        CaseSensitiveUsernameHolder.setUser(batchFile.getUserLastCreated().getLoginName());
        RegistryUser user = batchFile.getSubmitter();
        BatchImportResults importResult = new BatchImportResults();
        StringBuffer errMsg = new StringBuffer();
        int count = 0;        
        importResult.setFileName(validationResult.getFileName());
        List<String[]> lines = validationResult.getValidatedLines();
        String[] studyLine = BatchUploadUtils.getStudyLine(lines);
        String studyProtocolId = studyLine[1];
        StudyProtocolDTO spDto = getStudyProtocol(studyProtocolId, errMsg);
        Ii ii = DSetConverter.convertToIi(spDto.getSecondaryIdentifiers()); 
        importResult.setNciIdentifier(ii.getExtension());
        if (AccrualChangeCode.NO.equals(validationResult.getChangeCode())) {
            Long accrualCnts = subjectAccrualService.getAccrualCounts(spDto.getProprietaryTrialIndicator().getValue(), 
                    IiConverter.convertToLong(spDto.getIdentifier()));
            if (accrualCnts == 0) {
                validationResult.setChangeCode(AccrualChangeCode.YES);
            }
        }
        if (AccrualChangeCode.NO.equals(validationResult.getChangeCode())) {
            importResult.setSkipBecauseOfChangeCode(true);
            return importResult;
        }
        Map<Ii, Int> accrualLines = BatchUploadUtils.getAccrualCounts(lines);
        List<String[]> patientLines = BatchUploadUtils.getPatientInfo(lines);
        Map<String, List<String>> raceMap = BatchUploadUtils.getPatientRaceInfo(lines);
        count = generateSubjectAccruals(spDto.getIdentifier(), patientLines, 
                batchFile.getSubmissionTypeCode(), raceMap, validationResult, errMsg);
        if (spDto.getProprietaryTrialIndicator().getValue()) {
            importResult.setIndustrialTrial(true);
            importResult.setIndustrialCounts(BatchUploadUtils.getStudySiteCounts(lines));
        }
        for (Ii partSiteIi : accrualLines.keySet()) {
            //We're assuming this is the assigned identifier for the organization associated with the health care 
            //facility of the study site.
            SearchStudySiteResultDto studySite = 
                getSearchStudySiteService().getStudySiteByOrg(spDto.getIdentifier(), partSiteIi);
            subjectAccrualService.updateSubjectAccrualCount(studySite.getStudySiteIi(), accrualLines.get(partSiteIi), 
                    user, batchFile.getSubmissionTypeCode());
            count++;
        }
        importResult.setTotalImports(count);
        importResult.setErrors(new StringBuilder(errMsg.toString().trim()));
        return importResult;
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    private int generateSubjectAccruals(Ii studyProtocolIi, List<String[]> patientLines,
            AccrualSubmissionTypeCode submissionType, Map<String, List<String>> raceMap,
            BatchValidationResults results, StringBuffer errMsg) throws PAException {
        int count = 0;
        long startTime = System.currentTimeMillis();
        Long userId = AccrualCsmUtil.getInstance().getCSMUser(CaseSensitiveUsernameHolder.getUser()).getUserId();
        Map<SubjectAccrualKey, Long[]> listOfStudySubjects = getStudySubjectService().getSubjectAndPatientKeys(
                IiConverter.convertToLong(studyProtocolIi), false);
        for (String[] p : patientLines) {
            List<String> races = raceMap.get(p[BatchFileIndex.PATIENT_ID_INDEX]);
            Ii studySiteOrgIi = results.getListOfOrgIds().get(p[BatchFileIndex.PATIENT_REG_INST_ID_INDEX]);
            Long studySiteIi  = results.getListOfPoStudySiteIds().get(studySiteOrgIi.getExtension());            
            SubjectAccrualDTO saDTO = parserSubjectAccrual(p, submissionType, races, 
                    IiConverter.convertToIi(studySiteIi));
            try {
                Long[] ids = listOfStudySubjects.get(new SubjectAccrualKey(IiConverter.convertToLong(
                        saDTO.getParticipatingSiteIdentifier()), 
                        StConverter.convertToString(saDTO.getAssignedIdentifier())));
                if (ids == null) {
                    subjectAccrualService.create(saDTO, studyProtocolIi, userId);
                } else {
                    saDTO.setIdentifier(IiConverter.convertToIi(ids[0]));
                    subjectAccrualService.update(saDTO, studyProtocolIi, userId, ids);
                }
                count++;
            } catch (PAException e) {
                errMsg.append("Error for StudySubject Id: " 
                    + saDTO.getAssignedIdentifier().getValue() + ", " + e.getLocalizedMessage() + "\n");
            }
        }
        LOG.info("Time to process a single Batch File data: " 
                + (System.currentTimeMillis() - startTime) / RESULTS_LEN + " seconds");
        return count;
    }
    
    private SubjectAccrualDTO parserSubjectAccrual(String[] line, AccrualSubmissionTypeCode submissionType, 
            List<String> races, Ii studySiteIi) throws PAException {
        SubjectAccrualDTO saDTO = new SubjectAccrualDTO();
        saDTO.setAssignedIdentifier(StConverter.convertToSt(line[BatchFileIndex.PATIENT_ID_INDEX]));
        saDTO.setRegistrationDate(
                TsConverter.convertToTs(BatchUploadUtils.getDate(line[BatchFileIndex.PATIENT_REG_DATE_INDEX])));
        saDTO.setZipCode(StConverter.convertToSt(line[BatchFileIndex.PATIENT_ZIP_INDEX]));
        saDTO.setBirthDate(AccrualUtil.yearMonthStringToTs(StringUtils.isEmpty(line[BatchFileIndex.PATIENT_DOB_INDEX]) 
                ? DEFAULTBIRTHDATE : line[BatchFileIndex.PATIENT_DOB_INDEX]));
        saDTO.setGender(CdConverter.convertToCd(
                CDUSPatientGenderCode.getByCode(line[BatchFileIndex.PATIENT_GENDER_INDEX]).getValue()));
        saDTO.setEthnicity(CdConverter.convertToCd(
                CDUSPatientEthnicityCode.getByCode(line[BatchFileIndex.PATIENT_ETHNICITY_INDEX]).getValue()));
        if (CollectionUtils.isNotEmpty(races)) {
            saDTO.setRace(DSetEnumConverter.convertSetToDSet(CDUSPatientRaceCode.getCodesByCdusCodes(races)));
        }
        
        //Default to United States if no country code is provided
        String countryCode = StringUtils.isEmpty(line[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX]) ? "US" 
                : line[BatchFileIndex.PATIENT_COUNTRY_CODE_INDEX];
        saDTO.setCountryCode(CdConverter.convertStringToCd(countryCode));        
        CDUSPaymentMethodCode pmc = CDUSPaymentMethodCode.getByCode(line[BatchFileIndex.PATIENT_PAYMENT_METHOD_INDEX]);
        if (pmc != null) {
            saDTO.setPaymentMethod(CdConverter.convertToCd(pmc.getValue()));
        }
        saDTO.setParticipatingSiteIdentifier(studySiteIi);
        saDTO.setRegistrationGroupId(StConverter.convertToSt(line[BatchFileIndex.PATIENT_REG_GROUP_ID_INDEX]));
        saDTO.setSubmissionTypeCode(CdConverter.convertToCd(submissionType));
        parseSubjectDisease(line, saDTO);
        return saDTO;
    }

    private void parseSubjectDisease(String[] line, SubjectAccrualDTO saDTO) throws PAException {
        String diseaseCode = line[BatchFileIndex.PATIENT_DISEASE_INDEX];
        if (StringUtils.isEmpty(diseaseCode)) {
            return;
        }        
        AccrualDisease dis = null;
        StringTokenizer disease = new StringTokenizer(diseaseCode, ";");
        while (disease.hasMoreElements()) {
            String code = AccrualUtil.checkIfStringHasForwardSlash(disease.nextElement().toString());
            if (StringUtils.isEmpty(code)) {
                continue;
            }
            Element element = getDiseaseCache().get(code);
            if (element == null) {
                dis = getDiseaseService().getByCode(code);
                if (dis != null) {
                    element = new Element(dis.getDiseaseCode(), IiConverter.convertToIi(dis.getId()));
                    getDiseaseCache().put(element);
                }
            }
            if (code.toUpperCase(Locale.US).charAt(0) == 'C') {
                saDTO.setSiteDiseaseIdentifier((Ii) element.getValue());
            } else {
                saDTO.setDiseaseIdentifier((Ii) element.getValue());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendValidationErrorEmail(BatchValidationResults result, BatchFile batchFile) throws PAException {
        String subj = PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("accrual.error.subject");
        String body = PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("accrual.error.body");
        String regUserName = batchFile.getSubmitter().getFirstName() + " " + batchFile.getSubmitter().getLastName();
        
        body = body.replace("${submissionDate}", getFormatedCurrentDate());
        body = body.replace("${SubmitterName}", regUserName);
        body = body.replace("${CurrentDate}", getFormatedCurrentDate());
        StringBuffer errorReport = new StringBuffer();
        if (!result.isPassedValidation()) {
            errorReport.append(String.format("Errors in batch file: %s\n\n%s\n", result.getFileName(), 
                    result.getErrors()));
            String errors = result.getErrors().toString();
            int count = 1;
            StringBuffer numberedErrors = new StringBuffer();
            StringTokenizer st1 = new StringTokenizer(errors, "\n");
            while (st1.hasMoreTokens()) {
                numberedErrors.append(count).append(".\t").append(st1.nextToken()).append(" \n");
                count++;
            }
            body = body.replace("${fileName}", result.getFileName());
            body = body.replace("${errors}", numberedErrors.toString());
            if (result.getNciIdentifier() == null) {
                result.setNciIdentifier("");
            }
            body = body.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
            subj = subj.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
        }
        sendEmail(batchFile.getSubmitter().getEmailAddress(), subj, body);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendConfirmationEmail(BatchImportResults result, BatchFile batchFile) throws PAException {
        String subject = PaServiceLocator.getInstance().getLookUpTableService()
                .getPropertyValue("accrual.confirmation.subject");
        String body = "";
        if (result.isSkipBecauseOfChangeCode()) {
            body = PaServiceLocator.getInstance().getLookUpTableService()
                    .getPropertyValue("accrual.changeCode.body");
        } else if (result.isIndustrialTrial())  {
            body = PaServiceLocator.getInstance().getLookUpTableService()
                    .getPropertyValue("accrual.industrialTrial.body");
            StringBuffer studySiteCounts = new StringBuffer();
            for (String partSiteIi : result.getIndustrialCounts().keySet()) {
                studySiteCounts.append(partSiteIi).append(" : ")
                .append(result.getIndustrialCounts().get(partSiteIi));
            }
            body = body.replace("${studySiteCounts}", studySiteCounts);
        } else {
            body = PaServiceLocator.getInstance().getLookUpTableService()
                .getPropertyValue("accrual.confirmation.body");
        }
        String regUserName = batchFile.getSubmitter().getFirstName() + " " + batchFile.getSubmitter().getLastName();
        body = body.replace("${submissionDate}", getFormatedCurrentDate());
        body = body.replace("${SubmitterName}", regUserName);
        body = body.replace("${CurrentDate}", getFormatedCurrentDate());
        StringBuffer confirmation = new StringBuffer();
        if (result.getTotalImports() > 0) {
            confirmation.append(String.format("Sucessfully imported %s patients/accrual counts from %s.\n", 
                    result.getTotalImports(), result.getFileName()));
        }
        body = body.replace("${fileName}", result.getFileName());
        if (!result.isSkipBecauseOfChangeCode() && !result.isIndustrialTrial()) {
            body = body.replace("${count}", String.valueOf(result.getTotalImports()));
        }
        body = body.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
        subject = subject.replace(NCI_TRIAL_IDENTIFIER, result.getNciIdentifier());
        sendEmail(batchFile.getSubmitter().getEmailAddress(), subject, body);
    }

    private void sendEmail(String to, String subject, String msg) {
        if (StringUtils.isNotBlank(msg)) {
            PaServiceLocator.getInstance().getMailManagerService().sendMailWithHtmlBody(to, subject, msg);
        }
    }

    /**
     * Gets the current date properly formatted.
     * @return The current date properly formatted.
     */
    String getFormatedCurrentDate() {
        return DateFormatUtils.format(new Date(), DATE_PATTERN);
    }

    /**
     * @param cdusBatchUploadDataValidator the cdusBatchUploadDataValidator to set
     */
    public void setCdusBatchUploadDataValidator(CdusBatchUploadDataValidatorLocal cdusBatchUploadDataValidator) {
        this.cdusBatchUploadDataValidator = cdusBatchUploadDataValidator;
    }
    
    /**
     * @param subjectAccrualService the subject accrual service to set
     */
    public void setSubjectAccrualService(SubjectAccrualServiceLocal subjectAccrualService) {
        this.subjectAccrualService = subjectAccrualService;
    }

    /**
     * @param batchFileSvc the batchFileSvc to set
     */
    public void setBatchFileSvc(BatchFileService batchFileSvc) {
        this.batchFileSvc = batchFileSvc;
    }
}
