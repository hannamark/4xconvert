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
package gov.nih.nci.accrual.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.batch.AbstractBatchUploadReaderTest;
import gov.nih.nci.accrual.service.batch.BatchImportResults;
import gov.nih.nci.accrual.service.batch.BatchValidationResults;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author vrushali
 */
public class BatchUploadReaderServiceTest extends AbstractBatchUploadReaderTest {
	   
    @Test
    public void testSuAbstractorBatchUpload() throws Exception {
    	
    	final StudyProtocol sp = new StudyProtocol();
        sp.setOfficialTitle("Test Sp1");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("1/1/2009"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2010"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setProprietaryTrialIndicator(true);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2012-00003");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(2));
        sp.setProprietaryTrialIndicator(false);
        TestSchema.addUpdObject(sp);
        
        StudySubject subj = new StudySubject();
        subj.setPatient(TestSchema.patients.get(0));
        subj.setAssignedIdentifier("001");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(sp);
        subj.setStudySite(TestSchema.studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("1/1/2001"));
        TestSchema.addUpdObject(subj);
        
    	StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
        	public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
        		StudyProtocolDTO dto = new StudyProtocolDTO();
        		dto.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        		Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        		Ii spSecId = new Ii();
        		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        		dto.setIdentifier(IiConverter.convertToIi(sp.getId()));
        		dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        		spSecId.setExtension("NCI-2012-00003");
        		secondaryIdentifiers.add(spSecId);
        		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
        		return dto;
        	}
            });
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
        CSMUserUtil csmUtil = mock(CSMUserService.getInstance().getClass());
        when(csmUtil.isUserInGroup(any(String.class), any(String.class))).thenReturn(true);
        CSMUserService.setInstance(csmUtil);
        


        SearchTrialService searchTrialSvc = mock(SearchTrialService.class);
        when(searchTrialSvc.isAuthorized(any(Ii.class), any(Ii.class))).thenAnswer(new Answer<Bl>() {
            public Bl answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii spIi = (Ii) args[0];
                Bl result = new Bl();
                result.setValue(Boolean.FALSE);
                return result;
            }
        });
        when(searchTrialSvc.getTrialSummaryByStudyProtocolIi(any(Ii.class))).thenAnswer(new Answer<SearchTrialResultDto>() {
            public SearchTrialResultDto answer(InvocationOnMock invocation) throws Throwable {
                SearchTrialResultDto result = new SearchTrialResultDto();
                result.setIndustrial(BlConverter.convertToBl(true));
                return result;
            }
            
        });
        cdusBatchUploadDataValidator.setSearchTrialService(searchTrialSvc);       
        
        File file = new File(this.getClass().getResource("/suAbs-accrual-count-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals("suAbs-accrual-count-batch-file.txt", importResults.getFileName());
        assertEquals(2, importResults.getTotalImports());       
    }

	@Test
	public void patientRCCoverage() throws URISyntaxException, PAException {
		File file = new File(this.getClass().getResource("/patientRaceCodeValidation2.txt").toURI());
		BatchFile batchFile = getBatchFile(file);
		List<BatchValidationResults> results = readerService .validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Patient race code is missing for patient ID 200708"));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
	}

	@Test
	public void patientICDO3Coverage() throws URISyntaxException, PAException {
		 AccrualDisease disease1 = new AccrualDisease();
	     disease1.setCodeSystem("ICD-O-3");
	     disease1.setDiseaseCode("C34.1");
	     AccrualDisease disease2 = new AccrualDisease();
	     disease2.setCodeSystem("ICD-O-3");
	     disease2.setDiseaseCode("8000");
	     when(diseaseSvc.getByCode("C34.1")).thenReturn(disease1);
	     when(diseaseSvc.getByCode("C341")).thenReturn(disease1);
	     when(diseaseSvc.getByCode("8000")).thenReturn(disease2);
	        
		File file = new File(this.getClass().getResource("/ICD-O-3_coverage.txt").toURI());
		BatchFile batchFile = getBatchFile(file);
		List<BatchValidationResults> results = readerService .validateBatchData(batchFile);
		assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("ICD-O-3_coverage.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 3, collection.getTotalImports());
	}
        
	@Test
	public void junitCoverage() throws URISyntaxException, PAException {
		File file = new File(this.getClass().getResource("/patientRaceCodeValidation.txt").toURI());
		BatchFile batchFile = getBatchFile(file);
		List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertFalse(results.get(0).getValidatedLines().isEmpty());

		PlannedActivityServiceRemote plannedActivitySvc = mock(PlannedActivityServiceRemote.class);
		when(plannedActivitySvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class)))
        .thenAnswer(new Answer<List<PlannedEligibilityCriterionDTO>>() {
            public List<PlannedEligibilityCriterionDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
                List<PlannedEligibilityCriterionDTO> pecList = new ArrayList<PlannedEligibilityCriterionDTO>();
                dto.setCriterionName(StConverter.convertToSt(PaServiceLocator.ELIG_CRITERION_NAME_GENDER));
                dto.setEligibleGenderCode(CdConverter.convertToCd(PatientGenderCode.FEMALE));
                pecList.add(dto);
                return pecList;
            }
        });
        when(paSvcLocator.getPlannedActivityService()).thenReturn(plannedActivitySvc);
        
        file = new File(this.getClass().getResource("/junit_coverage.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Found invalid change code 3. Valid value for COLLECTIONS.Change_Code are 1 and 2."));
        assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: 21"));
        assertTrue(StringUtils.contains(errorMsg, "PATIENTS at line 4  must contain a valid NCI protocol identifier or the CTEP/DCP identifier."));
        assertTrue(StringUtils.contains(errorMsg, "Patient Registering Institution Code is missing for patient ID 223694 at line 4"));
        assertTrue(StringUtils.contains(errorMsg, "Please enter valid alpha2 country code for patient ID 223694 at line 4"));
        assertTrue(StringUtils.contains(errorMsg, "Please enter valid patient payment method for patient ID 207747 at line 2"));
        assertTrue(StringUtils.contains(errorMsg, "Patient birth date must be in YYYYMM format for patient ID 208847 at line 3"));
        assertTrue(StringUtils.contains(errorMsg, "Please enter valid patient ethnicity for patient ID 208847 at line 3"));
        assertTrue(results.get(0).getValidatedLines().isEmpty()); 

        file = new File(this.getClass().getResource("/no_protocol.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "No Study Protocol Identifier could be found in the given file."));
        
        file = new File(this.getClass().getResource("/no_protocol.zip").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "No Study Protocol Identifier could be found in the given file."));
	}

	@Test
	public void testDiseaseCodes() throws Exception {
        AccrualDisease disease1 = new AccrualDisease();
        disease1.setCodeSystem("SDC");
        disease1.setDiseaseCode("code1");
        AccrualDisease disease2 = new AccrualDisease();
        disease2.setCodeSystem("SDC");
        disease2.setDiseaseCode("code2");
        File file = new File(this.getClass().getResource("/junit_coverage2.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        when(diseaseSvc.getTrialCodeSystem(anyLong())).thenReturn("SDC");

        // not found
        when(diseaseSvc.getByCode("code1")).thenReturn(null);
        when(diseaseSvc.getByCode("code2")).thenReturn(null);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Patient Disease Code is invalid for patient ID"));

        // found, code systems match
        when(diseaseSvc.getByCode("code1")).thenReturn(disease1);
        when(diseaseSvc.getByCode("code2")).thenReturn(disease2);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString())); 

        // found, code systems don't match, all existing in file
        when(diseaseSvc.getTrialCodeSystem(anyLong())).thenReturn("ICD9");
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString())); 

        // found, code systems don't match, file not inclusive of all existing
        StudySubject subj = new StudySubject();
        subj.setDisease(TestSchema.diseases.get(0));
        subj.setPatient(TestSchema.patients.get(0));
        subj.setAssignedIdentifier("xyzzy");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(TestSchema.studyProtocols.get(2));
        subj.setStudySite(TestSchema.studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("1/1/2001"));
        TestSchema.addUpdObject(subj);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString())); 
        errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Please use same Disease code system used for the trial"));
	}
	
    @Test
    public void completeBatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        BatchFile batchFile = getBatchFile(file);

        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 0);

        BatchFile r = getResultFromDb();
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Complete-modified.txt"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertFalse(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertFalse(StringUtils.isEmpty(collection.getResults()));
        assertNull(collection.getTotalImports());

        file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 1);

        r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Complete.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2010-00003", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 24, collection.getTotalImports());
    }

    @Test
    public void abbreviatedBatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Abbreviated.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 72, collection.getTotalImports());
    }

   @Test
    public void changeCode2BatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/CDUS_Abbreviated_cc2.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS_Abbreviated_cc2.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.NO, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 0, collection.getTotalImports());
    }

    @Test
    public void accrualCountBatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/accrual-count-invalid-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Accrual count has been provided for a non Industrial study. This is not allowed."));
        assertTrue(StringUtils.contains(errorMsg, "Accrual count is missing at line 2"));
        assertTrue(StringUtils.contains(errorMsg, "Accrual study site is missing at line 3"));
        assertTrue(StringUtils.contains(errorMsg, "The Registering Institution Code must be a valid PO or CTEP ID. Code: notvalidcode"));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 0);

        BatchFile r = getResultFromDb();
        assertFalse(r.isPassedValidation());
        assertFalse(r.isProcessed());
        assertTrue(r.getFileLocation().contains("accrual-count-invalid-batch-file.txt"));
        assertFalse(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        AccrualCollections collection = r.getAccrualCollections().get(0);
        assertFalse(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00001", collection.getNciNumber());
        assertFalse(StringUtils.isEmpty(collection.getResults()));
        assertNull(collection.getTotalImports());

        setStudyProtocolSvc();
        
        file = new File(this.getClass().getResource("/accrual-count-batch-file.txt").toURI());
        batchFile = getBatchFile(file);
        results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(1, 1);

        r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("accrual-count-batch-file.txt"));
        assertTrue(StringUtils.isEmpty(r.getResults()));
        assertEquals(1, r.getAccrualCollections().size());
        collection = r.getAccrualCollections().get(0);
        assertTrue(collection.isPassedValidation());
        assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
        assertEquals("NCI-2009-00003", collection.getNciNumber());
        assertTrue(StringUtils.isEmpty(collection.getResults()));
        assertEquals((Integer) 2, collection.getTotalImports());
}

    @Test
    public void abbreviatedPreventionBatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/cdus-abbreviated-prevention-study.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1);
    }
    
    @Test
    public void crfValuesBatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/cdus-abbreviated-with-crf-values.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verifyEmailsSent(0, 1);
    }
    
    @Test
    public void archiveBatchValidation() throws URISyntaxException, PAException {
        File file = new File(this.getClass().getResource("/CDUS.zip").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        batchFile.setPassedValidation(false);
        batchFile.setFileLocation(file.getAbsolutePath());
        batchFile.setSubmitter(TestSchema.registryUsers.get(0));
        batchFileSvc.update(batchFile);
        
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        assertEquals(3, validationResults.size());
        for (BatchValidationResults result : validationResults) {
            assertTrue(result.isPassedValidation());
            assertTrue(StringUtils.isEmpty(result.getErrors().toString()));
            assertFalse(result.getValidatedLines().isEmpty());
        }
        verifyEmailsSent(0, 3);

        BatchFile r = getResultFromDb();
        assertTrue(r.isPassedValidation());
        assertTrue(r.isProcessed());
        assertTrue(r.getFileLocation().contains("CDUS.zip"));
        assertEquals(3, r.getAccrualCollections().size());
    }

    @Test
    public void testIsValidProtocolId() throws PAException, TooManyResultsException, URISyntaxException {
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);

        File file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
    }

    @Test
    public void performCompleteBatchImport() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        assertEquals(1, validationResults.size());
        assertEquals(0, validationResults.get(0).getErrors().length());
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(24, importResults.getTotalImports());
        assertEquals("CDUS_Complete.txt", importResults.getFileName());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());
        verifyEmailsSent(0, 1);
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);

        file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        batchFile = getBatchFile(file);
        validationResults = readerService.validateBatchData(batchFile);
        assertEquals(1, validationResults.size());
        assertTrue(StringUtils.isNotBlank(validationResults.get(0).getErrors().toString()));
    }

    @Test
    public void performAbbreviatedBatchImport() throws Exception {
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        verifyEmailsSent(0, 1);
    }


    @Test
    public void performAbbreviatedPreventionBatchImport() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(preventionIi).size());

        File file = new File(this.getClass().getResource("/cdus-abbreviated-prevention-study.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("cdus-abbreviated-prevention-study.txt", importResults.getFileName());
        assertEquals(72, studySubjectService.getByStudyProtocol(preventionIi).size());
        verifyEmailsSent(0, 1);
    }

    @Test
    public void testPerformImportOfArchive() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS.zip").toURI());
        BatchFile batchFile = getBatchFile(file);
        
        batchFile.setPassedValidation(false);
        batchFile.setFileLocation(file.getAbsolutePath());
        batchFile.setSubmitter(TestSchema.registryUsers.get(0));
        batchFileSvc.update(batchFile);
        
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(0, studySubjectService.getByStudyProtocol(preventionIi).size());

        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);

        assertEquals("cdus-abbreviated-prevention-study.txt", validationResults.get(0).getFileName());
        assertEquals(72, studySubjectService.getByStudyProtocol(preventionIi).size());

        assertEquals("CDUS_Abbreviated.txt", validationResults.get(1).getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        assertEquals("CDUS_Complete.txt", validationResults.get(2).getFileName());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());

        verifyEmailsSent(0, 3);

        // resubmit to confirm that existing rows are only updated
        batchFile = getBatchFile(file);
        batchFile.setPassedValidation(false);
        batchFile.setFileLocation(file.getAbsolutePath());
        batchFile.setSubmitter(TestSchema.registryUsers.get(0));
        batchFileSvc.update(batchFile);

        validationResults = readerService.validateBatchData(batchFile);

        assertEquals("cdus-abbreviated-prevention-study.txt", validationResults.get(0).getFileName());
        assertEquals(72, studySubjectService.getByStudyProtocol(preventionIi).size());

        assertEquals("CDUS_Abbreviated.txt", validationResults.get(1).getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        assertEquals("CDUS_Complete.txt", validationResults.get(2).getFileName());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());

    }
    
    @Test
    public void duplicateFileImport() throws Exception {
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());

        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
    }
    
    @Test
    public void crfValuesFileImport() throws Exception {
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        File file = new File(this.getClass().getResource("/cdus-abbreviated-with-crf-values.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals(72, importResults.getTotalImports());
        assertEquals("cdus-abbreviated-with-crf-values.txt", importResults.getFileName());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        verifyEmailsSent(0, 1);
    }
    
    @Test
    public void accrualCountBatchFileImport() throws Exception {
    	
    	setStudyProtocolSvc();
        
        File file = new File(this.getClass().getResource("/accrual-count-batch-file.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> validationResults = readerService.validateBatchData(batchFile);
        BatchImportResults importResults = readerService.importBatchData(batchFile, validationResults.get(0));
        assertEquals("accrual-count-batch-file.txt", importResults.getFileName());
        assertEquals(2, importResults.getTotalImports());
        verifyEmailsSent(0, 1);        
    }
    
    @Test
    public void testOrganizationIdBelongsToPO() throws Exception {
        OrganizationEntityServiceRemote organizationEntityService = mock(OrganizationEntityServiceRemote.class);
        when(organizationEntityService.getOrganization(any(Ii.class))).thenReturn(new OrganizationDTO());

        HealthCareFacilityCorrelationServiceRemote healthCareFacilityCorrelationService = 
            mock(HealthCareFacilityCorrelationServiceRemote.class);
        when(healthCareFacilityCorrelationService.search(any(HealthCareFacilityDTO.class)))
            .thenReturn(new ArrayList<HealthCareFacilityDTO>());

        poServiceLoc = mock(PoServiceLocator.class);
        setUpPoRegistry();
        when(poServiceLoc.getOrganizationEntityService()).thenReturn(organizationEntityService);
        when(poServiceLoc.getHealthCareFacilityCorrelationService()).thenReturn(healthCareFacilityCorrelationService);
        
        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        assertTrue(results.get(0).isPassedValidation());
    }
    
    @Test
    public void testOrganizationIdBelongsToCTEP() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        assertTrue(results.get(0).isPassedValidation());
    }

    @Test
    public void birthDateYearOnlyOr000000() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS_Complete-BirthDates.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(getBatchFile(file));
        assertFalse(results.get(0).isPassedValidation());
        assertEquals(16, StringUtils.countMatches(results.get(0).getErrors().toString(), "Patient birth date must be in YYYYMM format"));
    }

    @Test
    public void testIndustrialTrialWithpatients() throws Exception {
        assertEquals(0, studySubjectService.getByStudyProtocol(preventionIi).size());
        setStudyProtocolSvc();
        File file = new File(this.getClass().getResource("/cdus-abbreviated-prevention-study.txt").toURI());
        BatchFile batchFile = getBatchFile(file);
        List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        String errorMsg = results.get(0).getErrors().toString();
        assertTrue(StringUtils.contains(errorMsg, "Individual Patients should not be added to Industrial Trials for patient ID 207747 at line 2"));
        assertTrue(StringUtils.contains(errorMsg, "Individual Patients should not be added to Industrial Trials for patient ID 223694 at line 3"));
        assertTrue(StringUtils.contains(errorMsg, "Individual Patients should not be added to Industrial Trials for patient ID 210081 at line 73"));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
    }

    @Test
     public void processChangeCode2BatchValidation() throws URISyntaxException, PAException {
         File file = new File(this.getClass().getResource("/CDUS-changecode2.txt").toURI());
         BatchFile batchFile = getBatchFile(file);
         List<BatchValidationResults> results = readerService.validateBatchData(batchFile);
         assertEquals(1, results.size());
         assertTrue(results.get(0).isPassedValidation());
         assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
         assertFalse(results.get(0).getValidatedLines().isEmpty());
         verifyEmailsSent(0, 1);

         BatchFile r = getResultFromDb();
         assertTrue(r.isPassedValidation());
         assertTrue(r.isProcessed());
         assertTrue(r.getFileLocation().contains("CDUS-changecode2.txt"));
         assertTrue(StringUtils.isEmpty(r.getResults()));
         assertEquals(1, r.getAccrualCollections().size());
         AccrualCollections collection = r.getAccrualCollections().get(0);
         assertTrue(collection.isPassedValidation());
         assertEquals(AccrualChangeCode.YES, collection.getChangeCode());
         assertEquals("NCI-2010-00003", collection.getNciNumber());
         assertTrue(StringUtils.isEmpty(collection.getResults()));
         assertEquals((Integer) 2, collection.getTotalImports());
         
     }

    private void setStudyProtocolSvc() throws PAException {        
        StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
        	public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
        		Object[] args = invocation.getArguments();
        		Ii ii = (Ii) args[0];
        		StudyProtocolDTO dto = new StudyProtocolDTO();
        		dto.setProprietaryTrialIndicator(BlConverter.convertToBl(true));
        		Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
        		Ii spSecId = new Ii();
        		spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        		dto.setIdentifier(abbreviatedIi);
        		dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
        		spSecId.setExtension("NCI-2009-00003");
        		secondaryIdentifiers.add(spSecId);
        		dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
        		return dto;
        	}
            });
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
	}
    
    private BatchFile getBatchFile(File file) {
        BatchFile bf = new BatchFile();
        bf.setFileLocation(file.getAbsolutePath());
        bf.setSubmitter(TestSchema.registryUsers.get(0));
        bf.setUserLastCreated(TestSchema.registryUsers.get(0).getCsmUser());
        bf.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        TestSchema.addUpdObject(bf);
        return bf;
    }

    private void verifyEmailsSent(int errorCount, int confirmationCount) {
        verify(mailService, times(errorCount)).sendMailWithHtmlBody(anyString(), contains("accrual.error.subject"), anyString());
        verify(mailService, times(confirmationCount)).sendMailWithHtmlBody(anyString(), contains("accrual.confirmation.subject"), anyString());
    }

    private BatchFile getResultFromDb() {
        Session session = PaHibernateUtil.getCurrentSession();
        session.clear();
        String hql = "from BatchFile bf join fetch bf.accrualCollections ac order by bf.id desc";
        Query query = session.createQuery(hql);
        List<BatchFile> queryList = query.list();
        return queryList.get(0);
    }
}
