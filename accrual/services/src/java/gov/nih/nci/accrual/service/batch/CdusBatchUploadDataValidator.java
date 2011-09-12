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

import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVParser;

/**
 * @author Igor Merenko
 */
@Stateless
@Local(CdusBatchUploadDataValidatorLocal.class)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings("PMD.TooManyMethods")
public class CdusBatchUploadDataValidator extends BaseValidatorBatchUploadReader implements
        CdusBatchUploadDataValidatorLocal {
    
    private static final Logger LOG = Logger.getLogger(CdusBatchUploadDataValidator.class); 
    private RegistryUser ru;
    /**
     * {@inheritDoc}
     */
    @Override
    public BatchValidationResults validateSingleBatchData(File file, RegistryUser user)  {
        ru = user;
        StringBuffer errMsg = new StringBuffer();
        BatchValidationResults results = new BatchValidationResults();
        results.setFileName(file.getName());
        try {
            //We are parsing in this indirect manner instead of using the build in CSVReader because the reader does
            //not properly handle lines with trailing whitespace characters. A defect has been filed and can be viewed
            //at http://sourceforge.net/tracker/?func=detail&atid=773541&aid=3217444&group_id=148905
            List<String[]> lines = new ArrayList<String[]>();
            LineIterator lineIterator = FileUtils.lineIterator(file);
            CSVParser parser = new CSVParser();           
            long lineNumber = 0;
            String protocolId = null;
            while (lineIterator.hasNext()) {
                String[] line = parser.parseLine(StringUtils.strip(lineIterator.nextLine()));
                lines.add(line);
                ++lineNumber;
                if (StringUtils.equalsIgnoreCase("COLLECTIONS", line[BatchFileIndex.LINE_IDENTIFIER_INDEX])) {
                    protocolId = line[1];
                }                
                errMsg.append(validateBatchData(line, lineNumber, protocolId));
            }
            if (StringUtils.isEmpty(protocolId)) {
                errMsg.append("No Study Protocol Identifier could be found in the given file.");
            }
            results.setErrors(new StringBuilder(errMsg.toString().trim()));          
            if (StringUtils.isEmpty(errMsg.toString().trim())) {
                results.setValidatedLines(lines);
                results.setPassedValidation(true);
            } else {
                LOG.info(errMsg.toString());
            }
        } catch (IOException e) {
            errMsg.append("Unable to open the batch file: ").append(file.getName());
            results.setErrors(new StringBuilder(errMsg.toString().trim()));
            LOG.error("error reading the file " + file.getName(), e);
        }
        return results;
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    public List<BatchValidationResults> validateArchiveBatchData(File archiveFile, RegistryUser user) {
        List<BatchValidationResults> results = new ArrayList<BatchValidationResults>();
        try {
            ZipFile zip = new ZipFile(archiveFile, ZipFile.OPEN_READ);
            Enumeration<? extends ZipEntry> files = zip.entries();
            while (files.hasMoreElements()) {
                ZipEntry entry = files.nextElement();
                File file = File.createTempFile(StringUtils.substringBefore(entry.getName(), "."), ".txt");
                IOUtils.copy(zip.getInputStream(entry), FileUtils.openOutputStream(file));
                BatchValidationResults result = validateSingleBatchData(file, user);
                result.setFileName(entry.getName());
                results.add(result);
            }
        } catch (Exception e) {
            LOG.error("Error validating archive of batch files.", e);
        }
        return results;
    }

    
    /**
     * This method split the data into key and value.
     * @param data data
     * @param lineNumber lineNumber
     * @param the expected protocol id
     * @return errMsg if any
     */
    private String validateBatchData(String[] data, long lineNumber, String expectedProtocolId) {
        String key = data[0];
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
        validatePatientsMandatoryData(key, values, errMsg, lineNumber, getStudyProtocol(expectedProtocolId));
        validateRegisteringInstitutionCode(key, values, errMsg, lineNumber);
        validatePatientRaceData(key, values, errMsg, lineNumber);
        validateAccuralCount(key, values, errMsg, lineNumber);
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
            String registeringInstitutionID = StringUtils
                .trim(values.get(BatchFileIndex.PATIENT_REG_INST_ID_INDEX - 1));
            if (StringUtils.isEmpty(registeringInstitutionID)) {
                errMsg.append("Patient Registering Institution Code is missing for patient ID ")
                    .append(getPatientId(values)).append(appendLineNumber(lineNumber)).append('\n');
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
        if (StringUtils.equals("ACCRUAL_COUNT", key)) {
            String studySiteID = StringUtils
                .trim(values.get(BatchFileIndex.ACCRUAL_COUNT_STUDY_SITE_ID_INDEX - 1));
            if (!StringUtils.isEmpty(studySiteID)) {
                if (!isCorrectOrganizationId(studySiteID, errMsg)) {
                    return;
                }
                validateTreatingSiteAndAccrualAccess(studySiteID, errMsg, values, lineNumber);
            }
        }
    }

    /*
     * Test that Registering Institution Code is NCI PO ID or CTEP ID
     */
    private boolean isCorrectOrganizationId(String registeringInstitutionID, StringBuffer errMsg) {
        try {
            if (StringUtils.isNumeric(registeringInstitutionID)) {
                OrganizationDTO poOrganization = PoRegistry.getOrganizationEntityService()
                    .getOrganization(IiConverter.convertToPoOrganizationIi(registeringInstitutionID));
                if (poOrganization != null) {
                    return true;
                }
            }
        } catch (NullifiedEntityException e) {
            errMsg.append("The Registering Institution Code must be a valid PO or CTEP ID. Code: ")
                .append(registeringInstitutionID);

            return false;
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }

        try {
            getPoHcfByCtepId(IiConverter.convertToIdentifiedOrgEntityIi(registeringInstitutionID));
            return true;

        } catch (PAException e) {
            LOG.debug(e.getMessage());
        }

        errMsg.append("The Registering Institution Code must be a valid PO or CTEP ID. Code: ")
            .append(registeringInstitutionID);
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
        Ii studySiteOrgIi = getOrganizationIi(regInstID, errMsg);
        if (!isValidTreatingSite(studySiteOrgIi, values)) {
            addUpPatientRegisteringInstitutionCode(values, errMsg, lineNumber);           
        }        
    }
    
    private boolean isValidTreatingSite(Ii studySiteOrgIi, List<String> values) {
        StudyProtocolDTO spDto = getStudyProtocol(values.get(BatchFileIndex.COLLECTION_PROTOCOL_INDEX).trim());
        if (spDto == null) {
            return false;
        }
        
        try {
            if (ISOUtil.isIiNull(studySiteOrgIi)            
                || getSearchStudySiteService().getStudySiteByOrg(spDto.getIdentifier(), studySiteOrgIi) == null) {
                return false;
            }
        } catch (PAException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Test that the treating site ctep id exists for the particular trial being used. 
     * And that the user has accrual access to the site.
     * Do not validate if trial cannot be found as that validation is already being done 
     * on the COLLECTION line.
     * @throws PAException 
     */
    private void validateTreatingSiteAndAccrualAccess(String regInstID, StringBuffer errMsg, List<String> values, 
            long lineNumber) {
        Ii studySiteOrgIi = getOrganizationIi(regInstID, errMsg);
        if (!isValidTreatingSite(studySiteOrgIi, values)) {
            addAccrualSiteValidationError(values, errMsg, lineNumber);  
            return;
        }  
        assertUserAllowedSiteAccess(studySiteOrgIi, regInstID, errMsg, lineNumber);
    }
    
    /**
     * Assert batch submitter has accrual access to sites.
     * @param studySiteOrgIi site ii
     * @param regInstID site ID provided in file.
     * @param errMsg msg buffer
     * @param lineNumber location of input
     */
    protected void assertUserAllowedSiteAccess(Ii studySiteOrgIi, String regInstID, 
            StringBuffer errMsg, long lineNumber) {
        try {
            if (!AccrualUtil.isUserAllowedAccrualAccess(studySiteOrgIi, ru)) {
                addAccrualAccessBySiteError(regInstID, errMsg, lineNumber);
            }
        } catch (PAException e) {
            addAccrualAccessBySiteError(regInstID, errMsg, lineNumber);
        }
    }
    
    private void addUpPatientRegisteringInstitutionCode(List<String> values, StringBuffer errMsg, 
            long lineNumber) {
        errMsg.append("Patient Registering Institution Code is invalid for patient ID ").append(getPatientId(values))
        .append(appendLineNumber(lineNumber)).append('\n');
    }
    
    private void addAccrualAccessBySiteError(String studySiteID, StringBuffer errMsg, 
            long lineNumber) {
        errMsg.append("User " + ru.getFirstName() + " " + ru.getLastName() 
                + " does not have accrual access to Study Site ID " + studySiteID)
        .append(appendLineNumber(lineNumber)).append('\n');
    }
    
    private void addAccrualSiteValidationError(List<String> values, StringBuffer errMsg, 
            long lineNumber) {
        errMsg.append("Accrual study site ").append(getAccrualCountStudySiteId(values))
        .append(" is not valid")
        .append(appendLineNumber(lineNumber)).append('\n');
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
        String protocolId = null;
        if (values.size() > BatchFileIndex.COLLECTION_PROTOCOL_INDEX) {
            protocolId = values.get(BatchFileIndex.COLLECTION_PROTOCOL_INDEX).trim();
        }
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
        StudyProtocolDTO sp = getStudyProtocol(protocolId);
        if (sp == null) {
            errMsg.append(key).append(appendLineNumber(lineNumber)).append(protocolId)
            .append(" is not a valid NCI or CTEP/DCP identifier.\n");
        } else if (!StringUtils.equalsIgnoreCase(sp.getStatusCode().getCode(), ActStatusCode.ACTIVE.getCode())) {
            errMsg.append(key).append(appendLineNumber(lineNumber)).append(" with the identifier ")
                .append(protocolId).append(" is not an Active study.\n");   
        } else if (!hasAccrualAccess(sp.getIdentifier())) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
            .append(CsmUserUtil.getGridIdentityUsername(CaseSensitiveUsernameHolder.getUser()))
            .append(" does not have accrual access to the study protocol with the identifier ").append(protocolId);
        }
    }
    
    private boolean hasAccrualAccess(Ii spIi) {
        String user = CaseSensitiveUsernameHolder.getUser();
        try {
            Bl hasAccess = getSearchTrialService().isAuthorized(spIi, IiConverter.convertToIi(ru.getId()));
            return BlConverter.convertToBool(hasAccess);
        } catch (Exception e) {
            LOG.error("Error determining accrual access for " + user + ".", e);
            return false;
        }
    }    
  
    private void validatePatientID(String key, List<String> values, StringBuffer errMsg, long lineNumber) {
        if (KEY_WITH_PATIENTS_IDS.contains(key) && StringUtils.isEmpty(getPatientId(values))) {
            errMsg.append(key).append(appendLineNumber(lineNumber))
                .append(" must contain a patient identifier that is unique within the study.\n");
        }
    }
}
