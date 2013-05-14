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

import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SubjectAccrualKey;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Igor Merenko
 */
@Stateless
@Local(CdusBatchUploadDataValidatorLocal.class)
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.TooManyMethods", 
                    "PMD.AvoidDeeplyNestedIfStmts", "PMD.AppendCharacterWithChar", "PMD.NPathComplexity" })
public class CdusBatchUploadDataValidator extends BaseValidatorBatchUploadReader implements
        CdusBatchUploadDataValidatorLocal {
    
    private static final Logger LOG = Logger.getLogger(CdusBatchUploadDataValidator.class); 
    private RegistryUser ru;
    private StudyProtocolDTO sp;
    private Map<String, Long> listOfPoIds;
    private Map<String, String> listOfCtepIds;
    private Map<String, Ii> listOfOrgIds;
    private static final int TIME_SECONDS = 1000;
    private static final String SUABSTRACTOR = "SuAbstractor";
    private String codeSystemFile;
    private boolean checkDisease;
    private boolean patientCheck;
    private Set<SubjectAccrualKey> patientsFromBatchFile = new HashSet<SubjectAccrualKey>();

    @EJB
    private SubjectAccrualServiceLocal subjectAccrualService;

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchValidationResults validateSingleBatchData(File file, RegistryUser user)  {
        long startTime = System.currentTimeMillis();        
        ru = user;
        listOfPoIds = new HashMap<String, Long>();
        listOfCtepIds = new HashMap<String, String>();
        listOfOrgIds = new HashMap<String, Ii>();
        codeSystemFile = null;
        checkDisease = false;
        patientCheck = false;
        patientsFromBatchFile = new HashSet<SubjectAccrualKey>();
        StringBuffer errMsg = new StringBuffer();
        BatchValidationResults results = new BatchValidationResults();
        results.setFileName(file.getName());
        try {
            //We are parsing in this indirect manner instead of using the build in CSVReader because the reader does
            //not properly handle lines with trailing whitespace characters. A defect has been filed and can be viewed
            //at http://sourceforge.net/tracker/?func=detail&atid=773541&aid=3217444&group_id=148905
            List<String[]> lines = new ArrayList<String[]>();
            LineIterator lineIterator = FileUtils.lineIterator(file);
            long lineNumber = 0;
            String protocolId = null;
            while (lineIterator.hasNext()) {
                String[] line = AccrualUtil.csvParseAndTrim(lineIterator.nextLine());
                lines.add(line);
                ++lineNumber;
                if (line != null 
                        && StringUtils.equalsIgnoreCase("COLLECTIONS", line[BatchFileIndex.LINE_IDENTIFIER_INDEX]) 
                        && line.length > 1) {
                    protocolId = line[1];
                    String changeCode = line.length > BatchFileIndex.CHANGE_CODE_INDEX 
                            ? line[BatchFileIndex.CHANGE_CODE_INDEX] : null;
                    results.setNciIdentifier(protocolId);
                    if (StringUtils.isNotEmpty(changeCode)) {
                        AccrualChangeCode cc = AccrualChangeCode.getByCode(changeCode);
                        if (cc == null) {
                            errMsg.append("Found invalid change code " + changeCode 
                                    + ". Valid value for COLLECTIONS.Change_Code are 1 and 2.\n");
                        } else {
                            results.setChangeCode(cc);
                        }
                    }
                    sp = getStudyProtocol(protocolId, errMsg);
                    if (sp != null) {
                        Long spId = IiConverter.convertToLong(sp.getIdentifier());
                        checkDisease = getDiseaseService().diseaseCodeMandatory(spId);
                        Ii ii = DSetConverter.convertToIi(sp.getSecondaryIdentifiers());
                        results.setNciIdentifier(ii.getExtension());
                        try {
                            List<Long> ids = new ArrayList<Long>();
                            List<SearchStudySiteResultDto> isoStudySiteList = getSearchStudySiteService()
                                    .getTreatingSites(spId);
                            for (SearchStudySiteResultDto iso : isoStudySiteList) {
                                listOfPoIds.put(IiConverter.convertToString(iso.getOrganizationIi()),
                                        IiConverter.convertToLong(iso.getStudySiteIi()));
                            }
                            for (Map.Entry<String, Long> entry : listOfPoIds.entrySet()) {
                                ids.add(IiConverter.convertToLong(IiConverter.convertToPoOrganizationIi(
                                        entry.getKey())));
                            }
                            
                            if (!ids.isEmpty()) {
                                List<IdentifiedOrganizationDTO> identifiedOrgs = PoRegistry
                                        .getIdentifiedOrganizationCorrelationService()
                                        .getCorrelationsByPlayerIdsWithoutLimit(ids.toArray(
                                                new Long[ids.size()])); // NOPMD
                                for (IdentifiedOrganizationDTO idOrgDTO : identifiedOrgs) {
                                if (IiConverter.CTEP_ORG_IDENTIFIER_ROOT.equals(idOrgDTO.getAssignedId().getRoot())) {
                                        listOfCtepIds.put(idOrgDTO.getAssignedId().getExtension(), 
                                            idOrgDTO.getPlayerIdentifier().getExtension());
                                    }
                                }
                            }
                        } catch (PAException e) {
                            LOG.error("Error loading study sites for a trial in validateBatchData.", e);
                        }
                    }
                }
                try {
                    errMsg.append(validateBatchData(line, lineNumber, protocolId));
                } catch (Exception e) {
                    errMsg.append(e.getLocalizedMessage());
                }
            }
            if (StringUtils.isEmpty(protocolId)) {
                errMsg.append("No Study Protocol Identifier could be found in the given file.");
            }
            if (StringUtils.isEmpty(errMsg.toString().trim())) {
            Map<String, List<String>> raceMap = BatchUploadUtils.getPatientRaceInfo(lines);
            List<String[]> patientLines = BatchUploadUtils.getPatientInfo(lines);
            for (String[] p : patientLines) {
                List<String> races = raceMap.get(p[BatchFileIndex.PATIENT_ID_INDEX]);
                if (races == null) {
                    errMsg.append("Patient race code is missing for patient ID ")
                    .append(p[BatchFileIndex.PATIENT_ID_INDEX]).append("\n");
                } 
              }
             validateDiseaseCodeSystem(errMsg);
            }
            results.setErrors(new StringBuilder(errMsg.toString().trim()));          
            if (StringUtils.isEmpty(errMsg.toString().trim())) {
                results.setValidatedLines(lines);
                results.setPassedValidation(true);
                results.setListOfOrgIds(listOfOrgIds);
                results.setListOfPoStudySiteIds(listOfPoIds);

                if (isSuAbstractor()) {
                    for (Long studySiteId : listOfPoIds.values()) {
                        try {
                             subjectAccrualService.createAccrualAccess(ru, studySiteId);
                        } catch (NumberFormatException e) {
                             LOG.error("NumberFormatException while creating Accrual access.", e);
                        } catch (PAException e) {
                            LOG.error("Error creating Accrual access.", e);
                        }
                    }
                }
            } else {
                LOG.info(errMsg.toString());
            }
        } catch (IOException e) {
            errMsg.append("Unable to open the batch file: ").append(file.getName());
            results.setErrors(new StringBuilder(errMsg.toString().trim()));
            LOG.error("error reading the file " + file.getName(), e);
        }
        LOG.info("Time to validate a single Batch File data: " 
                + (System.currentTimeMillis() - startTime) / TIME_SECONDS + " seconds");
        return results;
    }

    /**
     * Validate that the code system in the batch file matches the code system for the trial from the database. 
     */
    private void validateDiseaseCodeSystem(StringBuffer errMsg) {
        Long spId = IiConverter.convertToLong(sp.getIdentifier()); 
        String codeSystemDB = getDiseaseService().getTrialCodeSystem(spId);
        try {
            if (codeSystemDB != null && !StringUtils.equals(codeSystemDB, codeSystemFile)) {
                Map<SubjectAccrualKey, Long[]> listOfStudySubjects = getStudySubjectService()
                        .getSubjectAndPatientKeys(spId, true);
                Set<SubjectAccrualKey> patientsFromDB = listOfStudySubjects.keySet();
                if (!patientsFromBatchFile.containsAll(patientsFromDB)) {
                    errMsg.append("Please use same Disease code system used for the trial (" + codeSystemDB + ").\n");
                }
             }
        } catch (Exception e) {
            errMsg.append(e.getLocalizedMessage());
        }
    }

    /**
     * This method split the data into key and value.
     * @param data data
     * @param lineNumber lineNumber
     * @param the expected protocol id
     * @return errMsg if any
     */
    private String validateBatchData(String[] data, long lineNumber, String expectedProtocolId) {
        String key = data != null ? data[0] : null;
        if (!LIST_OF_ELEMENT.containsKey(key)) {
            return StringUtils.EMPTY;
        }
        List<String> values = Arrays.asList((String[]) ArrayUtils.subarray(data, 1, data.length));
        StringBuffer errMsg = new StringBuffer();
        if (LIST_OF_ELEMENT.containsKey(key) && LIST_OF_ELEMENT.get(key) != values.size()) {
            errMsg.append(key).append(appendLineNumber(lineNumber));
            errMsg.append(" does not have correct number of elements.\n");
        }
        validateProtocolNumber(key, values, errMsg, lineNumber, expectedProtocolId);
        validatePatientID(key, values, errMsg, lineNumber);
        validateStudySiteAccrualAccessCode(key, values, errMsg, lineNumber);
        if (StringUtils.equalsIgnoreCase("PATIENTS", key) && !patientCheck && codeSystemFile == null) {
            String code = AccrualUtil.safeGet(values, PATIENT_DISEASE_INDEX);
            if (StringUtils.isNotEmpty(code)) {
                StringTokenizer disease = new StringTokenizer(code, ";");
                while (disease.hasMoreElements()) {
                    String diseaseCode = AccrualUtil.checkIfStringHasForwardSlash(disease.nextElement().toString());
                    AccrualDisease dis = getDiseaseService().getByCode(diseaseCode);
                    if (dis != null) {
                        codeSystemFile = dis.getCodeSystem();
                        patientCheck = true;
                        break;
                    }
                }                
            }
        }
        validatePatientsMandatoryData(key, values, errMsg, lineNumber, sp, codeSystemFile, checkDisease);
        validateRegisteringInstitutionCode(key, values, errMsg, lineNumber);
        validatePatientRaceData(key, values, errMsg, lineNumber);
        validateAccrualCount(key, values, errMsg, lineNumber, sp);
        return errMsg.toString();
    }
    
    /**
     * 
     * @param values values
     * @param errMsg if any
     * @param lineNumber line Number
     */
    private void validateRegisteringInstitutionCode(String key, List<String> values, StringBuffer errMsg,
            long lineNumber) {
        if (StringUtils.equals("PATIENTS", key)) {
            String registeringInstitutionID = AccrualUtil.safeGet(values, 
                    BatchFileIndex.PATIENT_REG_INST_ID_INDEX - 1);
            if (StringUtils.isEmpty(registeringInstitutionID)) {
                errMsg.append("Patient Registering Institution Code is missing for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append("\n");
            } else {
                if (!isCorrectOrganizationId(registeringInstitutionID, errMsg)) {
                    return;
                }
                validatePatientTreatingSite(registeringInstitutionID, errMsg, values, lineNumber);
            }
        }
    }
    
    private void validateStudySiteAccrualAccessCode(String key, List<String> values, StringBuffer errMsg,
            long lineNumber) {
        String studySiteID = null;
        if (StringUtils.equals("PATIENTS", key)) {
            studySiteID = AccrualUtil.safeGet(values, BatchFileIndex.PATIENT_REG_INST_ID_INDEX - 1);
        } else if (StringUtils.equals("ACCRUAL_COUNT", key)) {
            studySiteID = AccrualUtil.safeGet(values, BatchFileIndex.ACCRUAL_COUNT_STUDY_SITE_ID_INDEX - 1);
        }
        if (!StringUtils.isEmpty(studySiteID)) {
            if (!isCorrectOrganizationId(studySiteID, errMsg)) {
                return;
            }
            validateTreatingSiteAndAccrualAccess(studySiteID, errMsg, lineNumber, values);
        }
    }

    /*
     * Test that Registering Institution Code is NCI PO ID or CTEP ID
     */
    private boolean isCorrectOrganizationId(String registeringInstitutionID, StringBuffer errMsg) {
        String msg = "The Registering Institution Code must be a valid PO or CTEP ID. Code: " 
                + registeringInstitutionID + "\n";
        if (listOfPoIds.containsKey(registeringInstitutionID)) {
            if (listOfOrgIds.get(registeringInstitutionID) == null) {
                listOfOrgIds.put(registeringInstitutionID, 
                    IiConverter.convertToIi(registeringInstitutionID));
            }
            return true;
        } else if (listOfCtepIds.containsKey(registeringInstitutionID)) {
            if (listOfOrgIds.get(registeringInstitutionID) == null) {
                listOfOrgIds.put(registeringInstitutionID, 
                   IiConverter.convertToIi(listOfCtepIds.get(registeringInstitutionID)));
            }
            return true;
        }
        try {
            if (StringUtils.isNumeric(registeringInstitutionID)) {
                OrganizationDTO poOrganization = PoRegistry.getOrganizationEntityService()
                    .getOrganization(IiConverter.convertToPoOrganizationIi(registeringInstitutionID));
                if (poOrganization != null) {
                    return true;
                }
            }
        } catch (NullifiedEntityException e) {
            errMsg.append(msg);
            return false;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }
        try {
            Ii identifier = getPoHcfByCtepId(IiConverter.convertToIdentifiedOrgEntityIi(registeringInstitutionID));
            if (identifier != null && identifier.getExtension() != null) {
                return true;
            }
        } catch (PAException e) {
            LOG.debug(e.getMessage());
        }
        errMsg.append(msg);
        return false;
    }

    /**
     * getPoHcfByCtepId.
     * @param ctepHcfIi ii
     * @return ii
     * @throws PAException when error
     */
    private Ii getPoHcfByCtepId(Ii ctepHcfIi) throws PAException {
        HealthCareFacilityDTO criteria = new HealthCareFacilityDTO();
        criteria.setIdentifier(DSetConverter.convertIiToDset(ctepHcfIi));
        List<HealthCareFacilityDTO> hcfs =
            PoRegistry.getHealthCareFacilityCorrelationService().search(criteria);
        if (hcfs.isEmpty()) {
            throw new PAException("Provided HCF CTEP ID: "
                    + ctepHcfIi.getExtension()
                    + " but did not find a corresponding HCF in PO.");
        } else if (hcfs.size() > 1) {
            throw new PAException("more than 1 HCF found for given CTEP ID: "
                    + ctepHcfIi.getExtension());
        }
        return DSetConverter.convertToIi(hcfs.get(0).getIdentifier());
    }
    
    /**
     * Test that the treating site ctep id exists for the particular trial being used.
     * Do not validate if trial cannot be found as that validation is already being done 
     * on the COLLECTION line.
     */
    private void validatePatientTreatingSite(String regInstID, StringBuffer errMsg, List<String> values, 
            long lineNumber) {
        if (listOfPoIds.containsKey(regInstID) || listOfCtepIds.containsKey(regInstID)) {
            return;           
        }
        addUpPatientRegisteringInstitutionCode(values, errMsg, lineNumber);
    }
    
    /**
     * Test that the treating site ctep id exists for the particular trial being used. 
     * And that the user has accrual access to the site.
     * Do not validate if trial cannot be found as that validation is already being done 
     * on the COLLECTION line.
     * @throws PAException 
     */
    private void validateTreatingSiteAndAccrualAccess(String regInstID, StringBuffer errMsg, 
        long lineNumber, List<String> values) {
        if (listOfPoIds.containsKey(regInstID)  || listOfCtepIds.containsKey(regInstID)) {
            assertUserAllowedSiteAccess(sp.getIdentifier(), regInstID, errMsg, lineNumber, values);
            return;
        }
        addAccrualSiteValidationError(regInstID, errMsg, lineNumber);
    }
    
    /**
     * Assert batch submitter has accrual access to sites.
     * @param studyProtocolIi the study protocol ii
     * @param regInstID site ID provided in file.
     * @param errMsg msg buffer
     * @param lineNumber location of input
     * @param values the line values
     */
    protected void assertUserAllowedSiteAccess(Ii studyProtocolIi, String regInstID, 
            StringBuffer errMsg, long lineNumber, List<String> values) {
        try {
            Long studySiteIi  = null;
            String poId = null;
            studySiteIi = listOfPoIds.get(regInstID);
            if (studySiteIi == null) {
                poId = listOfCtepIds.get(regInstID);
            }
            if (StringUtils.isNotEmpty(poId)) {
                studySiteIi = listOfPoIds.get(poId);
            }
            patientsFromBatchFile.add(new SubjectAccrualKey(studySiteIi, getPatientId(values)));
            if (!isSuAbstractor() 
                    && !AccrualUtil.isUserAllowedAccrualAccess(IiConverter.convertToIi(studySiteIi), ru)) {
                addAccrualAccessBySiteError(regInstID, errMsg, lineNumber);
            }
        } catch (PAException e) {
            addAccrualAccessBySiteError(regInstID, errMsg, lineNumber);
        }
    }
    
    private void addUpPatientRegisteringInstitutionCode(List<String> values, StringBuffer errMsg, 
            long lineNumber) {
        errMsg.append("Patient Registering Institution Code is invalid for patient ID ").append(getPatientId(values))
        .append(appendLineNumber(lineNumber)).append("\n");
    }
    
    private void addAccrualAccessBySiteError(String studySiteID, StringBuffer errMsg, 
            long lineNumber) {
        errMsg.append("User " + ru.getFirstName() + " " + ru.getLastName() 
                + " does not have accrual access to Study Site ID " + studySiteID)
        .append(appendLineNumber(lineNumber)).append("\n");
    }
    
    private void addAccrualSiteValidationError(String siteId, StringBuffer errMsg, 
            long lineNumber) {
        errMsg.append("Accrual study site ").append(siteId)
        .append(" is not valid")
        .append(appendLineNumber(lineNumber)).append("\n");
    }

    /**
     * 
     * @param key key
     * @param values values
     * @param errMsg err if any
     * @param lineNumber 
     * @param expectedProtocolId the protocol id expected to be seen.
     */
    private void validateProtocolNumber(String key, List<String> values, StringBuffer errMsg, long lineNumber, 
            String expectedProtocolId) {
        String protocolId = AccrualUtil.safeGet(values, BatchFileIndex.COLLECTION_PROTOCOL_INDEX);
        if (StringUtils.isEmpty(protocolId)) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
                .append(" must contain a valid NCI protocol identifier or the CTEP/DCP identifier.\n");
        } else if (!StringUtils.equalsIgnoreCase(protocolId, expectedProtocolId)) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
            .append(" does not contain the same protocol identifier as the one specified in the COLLECTIONS line.\n");
        } else if (StringUtils.equals(key, "COLLECTIONS")) {
            validateProtocolStatus(key, errMsg, lineNumber, protocolId);    
        }
    }
    
    /**
     * Validates that the study protocol id is valid and active.
     * @param key  the key
     * @param errMsg error messages
     * @param lineNumber line number
     * @param protocolId the study protocol id
     */
    private void validateProtocolStatus(String key, StringBuffer errMsg, long lineNumber, String protocolId) {
        if (sp == null) {
            errMsg.append(key).append(appendLineNumber(lineNumber)).append(protocolId)
            .append(" is not a valid NCI or CTEP/DCP identifier.\n");
        } else if (!StringUtils.equalsIgnoreCase(sp.getStatusCode().getCode(), ActStatusCode.ACTIVE.getCode())) {
            errMsg.append(key).append(appendLineNumber(lineNumber)).append(" with the identifier ")
                .append(protocolId).append(" is not an Active study.\n");   
        } else if (!hasAccrualAccess(sp.getIdentifier())) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
            .append(CsmUserUtil.getGridIdentityUsername(ru.getCsmUser().getLoginName()))
            .append(" does not have accrual access to the study protocol with the identifier ").append(protocolId)
            .append(" \n");
        }
    }
    
    private boolean hasAccrualAccess(Ii spIi) {
        try {
            boolean hasAccess = BlConverter.convertToBool(getSearchTrialService().isAuthorized(spIi, 
                    IiConverter.convertToIi(ru.getId())));
            boolean superAbs = isSuAbstractor();
            if (hasAccess || superAbs) {
                return true;
            }
        } catch (Exception e) {
            LOG.error("Error determining accrual access for " + ru.getCsmUser().getLoginName() + ".", e);
            return false;
        }
        return false;
    }
    
    private boolean isSuAbstractor() {
        CSMUserUtil userService = CSMUserService.getInstance();
        try {     
            return userService.isUserInGroup(ru.getCsmUser().getLoginName(), SUABSTRACTOR);
        } catch (Exception e) {
            LOG.error("Error determining user role for " + ru.getCsmUser().getLoginName() + ".", e);
            return false;
        }
    }    
  
    private void validatePatientID(String key, List<String> values, StringBuffer errMsg, long lineNumber) {
        if (KEY_WITH_PATIENTS_IDS.contains(key) && StringUtils.isEmpty(getPatientId(values))) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
                .append(" must contain a patient identifier that is unique within the study.\n");
        }
    }
    
    /**
     * @param subjectAccrualService the subject accrual service to set
     */
    public void setSubjectAccrualService(SubjectAccrualServiceLocal subjectAccrualService) {
        this.subjectAccrualService = subjectAccrualService;
    }
}
