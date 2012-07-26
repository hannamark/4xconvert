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

import gov.nih.nci.accrual.service.PatientServiceLocal;
import gov.nih.nci.accrual.service.PerformedActivityServiceLocal;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.SearchStudySiteService;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Base Batch reader.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class BaseBatchUploadReader {
    private static final Logger LOG = Logger.getLogger(BaseBatchUploadReader.class);
    private static final int COLLECTION_ELEMENT_SIZE = 10;
    private static final int PATIENTS_ELEMENT_SIZE = 23;
    private static final int PATIENT_RACES_ELEMENT_SIZE = 3;
    private static final int ACCRUAL_COUNT_ELEMENT_SIZE = 3;    
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
    
    /**
     * patient country code index.
     */
    protected static final int PATIENT_COUNTRY_CODE_INDEX = 3;
    /**
     * patient birthday date index.
     */
    protected static final int PATIENT_BRITH_DATE_INDEX = 4;
    /**
     * patient gender code index.
     */
    protected static final int PATIENT_GENDER_CODE_INDEX = 5;
    /**
     * patient ethnicity index.
     */
    protected static final int PATIENT_ETHNICITY_INDEX = 6;
    /**
     * patient date of entry index.
     */
    protected static final int PATIENT_DATE_OF_ENTRY_INDEX = 8;

    /**
     * patient gender.
     */
    protected static final List<String> PATIENT_GENDER = new ArrayList<String>();
    static {
        PATIENT_GENDER.addAll(Arrays.asList("1", "2", "9"));
        PATIENT_GENDER.addAll(Arrays.asList(PatientGenderCode.getDisplayNames()));
    }
    /**
     * patient ethnicity.
     */
    protected static final List<String> PATIENT_ETHNICITY = new ArrayList<String>();
    static {
        PATIENT_ETHNICITY.addAll(Arrays.asList("1", "2", "8", "9"));
        PATIENT_ETHNICITY.addAll(Arrays.asList(PatientEthnicityCode.getDisplayNames()));
    }
    /**
     * patient race code index.
     */
    protected static final int PATIENT_RACE_CODE_INDEX = 2;
    /**
     * patient race code.
     */
    protected static final List<String> PATIENT_RACE_CODE = new ArrayList<String>();
    private static final int PATIENT_ID_INDEX = 1;
    /**
     * accrual count study index.
     */
    protected static final int ACCRUAL_COUNT_STUDY_INDEX = 0;
    /**
     * accrual study site index.
     */
    protected static final int ACCRUAL_STUDY_SITE_INDEX = 1;
    /**
     * accrual count index.
     */
    protected static final int ACCRUAL_COUNT_INDEX = 2;
    static {
        PATIENT_RACE_CODE.addAll(Arrays.asList("01", "03", "04", "05", "06", "98", "99"));
        PATIENT_RACE_CODE.addAll(Arrays.asList(PatientRaceCode.getDisplayNames()));
    }
    private static final int ACCRUAL_COUNT_STUDY_SITE_ID_INDEX = 1;
    /**
     * key with patients ids.
     */
    protected static final List<String> KEY_WITH_PATIENTS_IDS = new ArrayList<String>();
    static {
        KEY_WITH_PATIENTS_IDS.addAll(Arrays.asList("PATIENTS", "PATIENT_RACES"));
    }
    /**
     * patient disease index.
     */
    protected static final int PATIENT_DISEASE_INDEX = 20;
    
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
    @EJB
    private SearchTrialService searchTrialService;

    
    
    /**
     * Gets the study protocol with the given id, be it NCI, CTEP or DCP identifier.
     * @param protocolId the protocol id
     * @return the study protocol with the given id or null if no such study exists
     */
    protected StudyProtocolDTO getStudyProtocol(String protocolId) {
        StudyProtocolServiceRemote spSvc = PaServiceLocator.getInstance().getStudyProtocolService();
        StudyProtocolDTO foundStudy = null;
        Ii protocolIi = IiConverter.convertToAssignedIdentifierIi(protocolId);
        if (StringUtils.startsWith(protocolId, "NCI")) {
            foundStudy = spSvc.loadStudyProtocol(protocolIi);
        } else {
            //No good way to distinguish if the id is CTEP or DCP so we'll just have to perform a lookup and see
            //if we get a match.
            protocolIi.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
            foundStudy = spSvc.loadStudyProtocol(protocolIi);
            protocolIi.setRoot(IiConverter.DCP_STUDY_PROTOCOL_ROOT);
            foundStudy = foundStudy != null ? foundStudy : spSvc.loadStudyProtocol(protocolIi);
        }
        return foundStudy;
    }
    
    /**
     * Loads the disease with the given meddra code, returning null if no such disease can be found.
     * @param meddraCode the meddra code of the disease to retrieve
     * @param errMsg the error messages
     * @return the disease with the give meddra code or null if no such disease can be found
     */
    protected SDCDiseaseDTO getDisease(String meddraCode, StringBuffer errMsg) {
        SDCDiseaseDTO disease = null;
        try {
            disease = PaServiceLocator.getInstance().getDiseaseService().getByCode(meddraCode);
        } catch (PAException e) {
            LOG.error("Error retrieving disease." , e);
            errMsg.append("Unable to load the meddra diease with code ")
                .append(meddraCode).append(" from the database.\n");
        }
        return disease;
    }
    
    /**
     * Loads the disease with the given ICD9 code, returning null if no such disease can be found.
     * @param code the ICD9 code of the disease to retrieve
     * @param errMsg the error messages
     * @return the disease with the give ICD9 code or null if no such disease can be found
     */
    protected ICD9DiseaseDTO getICD9Disease(String code, StringBuffer errMsg) {
        ICD9DiseaseDTO disease = null;
        try {
            disease = PaServiceLocator.getInstance().getICD9DiseaseService().getByCode(code);
        } catch (PAException e) {
            LOG.error("Error retrieving ICD9 disease." , e);
            errMsg.append("Unable to load the ICD9 diease with code ")
                .append(code).append(" from the database.\n");
        }
        return disease;
    }
    
    /**
     * Retrieves the PO identifier of the organization related with the given identifier.
     * @param orgIdentifier the CTEP/DCP identifier or the po id of the org
     * @param errMsg the error messages
     * @return the po identifier of the org
     */
    protected Ii getOrganizationIi(String orgIdentifier, StringBuffer errMsg) {
        Ii resultingIi = null;
        try {
            resultingIi = BatchUploadUtils.getOrganizationIi(orgIdentifier);
        } catch (Exception e) {
            LOG.error("Error retrieving study site organization." , e);
            errMsg.append("Unable to load an organization with the id ").append(orgIdentifier)
            .append(" from the database.\n");
        }
        return resultingIi;
    }
    
    /**
     * 
     * @param values values
     * @return err if any
     */
    protected String getPatientId(List<String> values) {
        return AccrualUtil.safeGet(values, PATIENT_ID_INDEX);
    }   
    
    /**
     * 
     * @param values values
     * @return err if any
     */
    protected String getAccrualCountStudySiteId(List<String> values) {
        return AccrualUtil.safeGet(values, ACCRUAL_COUNT_STUDY_SITE_ID_INDEX);
    }   
    
    /**
     * 
     * @param lineNumber line Number
     * @return string
     */
    protected String appendLineNumber(long lineNumber) {
        return " at line " + lineNumber + " ";
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
    /**
     * @return the searchTrialService
     */
    public SearchTrialService getSearchTrialService() {
        return searchTrialService;
    }
    /**
     * @param searchTrialService the searchTrialService to set
     */
    public void setSearchTrialService(SearchTrialService searchTrialService) {
        this.searchTrialService = searchTrialService;
    }
}
