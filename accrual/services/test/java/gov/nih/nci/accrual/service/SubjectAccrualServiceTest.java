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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.POPatientDTO;
import gov.nih.nci.accrual.service.batch.AbstractBatchUploadReaderTest;
import gov.nih.nci.accrual.service.batch.BatchFileServiceBeanLocal;
import gov.nih.nci.accrual.service.exception.IndexedInputValidationException;
import gov.nih.nci.accrual.service.util.CountryBean;
import gov.nih.nci.accrual.service.util.POPatientService;
import gov.nih.nci.accrual.service.util.SubjectAccrualCountService;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorAccInterface;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.DSetEnumConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ICD9DiseaseServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for the subject accrual service.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public class SubjectAccrualServiceTest extends AbstractBatchUploadReaderTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private SubjectAccrualBeanLocal bean;
    public SubjectAccrualCountService accCountSvc;
    public StudySiteServiceRemote studySiteSvc;
    
    @Before
    public void setUp() throws Exception {
        RegistryUserServiceRemote regSvc = mock(RegistryUserServiceRemote.class);
        when(regSvc.getUser(any(String.class))).thenReturn(TestSchema.registryUsers.get(0));
        when(paSvcLocator.getRegistryUserService()).thenReturn(regSvc);
      
        POPatientService poPatientSvc = mock(POPatientService.class);
        POPatientDTO patientDTO = new POPatientDTO();
        patientDTO.setIdentifier(IiConverter.convertToIi(1L));
        patientDTO.setPlayerIdentifier(IiConverter.convertToIi(1L));
        when(poPatientSvc.create(any(POPatientDTO.class))).thenReturn(patientDTO);
        when(poPatientSvc.update(any(POPatientDTO.class))).thenReturn(patientDTO);
        when(poPatientSvc.get(any(Ii.class))).thenReturn(patientDTO);
        
        PatientBeanLocal patientService = new PatientBeanLocal();
        patientService.setCountryService(new CountryBean());
        patientService.setPatientCorrelationSvc(poPatientSvc);
        
        PerformedActivityBean performedActivitySvc = new PerformedActivityBean();
        accCountSvc = mock(SubjectAccrualCountService.class);
        
        bean = new SubjectAccrualServiceBean();
        bean.setSubjectAccrualCountSvc(accCountSvc);
        bean.setPatientService(patientService);
        bean.setStudySubjectService(new StudySubjectBean());
        bean.setPerformedActivityService(performedActivitySvc);
        bean.setCountryService(new CountryBean());
        bean.setBatchFileService(new BatchFileServiceBeanLocal());
        bean.setBatchService(readerService);
        
        studySiteSvc = mock(StudySiteServiceRemote.class);
        when(studySiteSvc.get(any(Ii.class))).thenReturn(Converters.get(StudySiteConverter.class).convertFromDomainToDto(TestSchema.participatingSites.get(0)));        
        
        ICD9DiseaseServiceRemote icd9DiseaseSvc = mock(ICD9DiseaseServiceRemote.class);
        
        when(paSvcLocator.getICD9DiseaseService()).thenReturn(icd9DiseaseSvc);
        when(paSvcLocator.getStudySiteService()).thenReturn(studySiteSvc);
        ServiceLocatorAccInterface accSvcLocator = mock(ServiceLocatorAccInterface.class);
        when(accSvcLocator.getPerformedActivityService()).thenReturn(performedActivitySvc);
        
        AccrualServiceLocator.getInstance().setServiceLocator(accSvcLocator);
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);

    }

    @Test
    public void manageSubjectAccruals() throws Exception {
        List<SubjectAccrualDTO> results = bean.manageSubjectAccruals(new ArrayList<SubjectAccrualDTO>());
        assertTrue(results.isEmpty());
        
        SubjectAccrualDTO dto = new SubjectAccrualDTO();
        dto.setAssignedIdentifier(StConverter.convertToSt("Patient-1"));
        dto.setBirthDate(AccrualUtil.yearMonthStringToTs("01/2000"));
        dto.setGender(CdConverter.convertToCd(PatientGenderCode.MALE));
        dto.setEthnicity(CdConverter.convertToCd(PatientEthnicityCode.NOT_HISPANIC));
        dto.setRace(DSetConverter.convertCdListToDSet(Arrays.asList(CdConverter.convertToCd(PatientRaceCode.AMERICAN_INDIAN))));
        dto.setCountryCode(CdConverter.convertStringToCd(TestSchema.countries.get(0).getAlpha2()));
        dto.setZipCode(StConverter.convertToSt("22222"));
        dto.setRegistrationDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2000")));
        dto.setPaymentMethod(CdConverter.convertToCd(PaymentMethodCode.MEDICAID));
        dto.setDiseaseIdentifier(IiConverter.convertToIi(TestSchema.diseases.get(0).getId()));
        dto.setParticipatingSiteIdentifier(IiConverter.convertToIi(TestSchema.participatingSites.get(0).getId()));
        
        results = bean.manageSubjectAccruals(Arrays.asList(dto));
        assertEquals(1, results.size());
        assertFalse(ISOUtil.isIiNull(results.get(0).getIdentifier()));
        validateSubjectAccrualDTO(dto, results.get(0));
        
        dto = results.get(0);
        dto.setRegistrationDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2001")));
        dto.setGender(CdConverter.convertToCd(PatientGenderCode.FEMALE));
        dto.setPaymentMethod(CdConverter.convertToCd(PaymentMethodCode.MEDICARE));
        
        results = bean.manageSubjectAccruals(Arrays.asList(dto));
        assertEquals(1, results.size());
        assertFalse(ISOUtil.isIiNull(results.get(0).getIdentifier()));
        validateSubjectAccrualDTO(dto, results.get(0));
    }
    
    @Test
    public void manageSubjectAccrualRequiredValidation() throws Exception {
        thrown.expect(IndexedInputValidationException.class);
        thrown.expectMessage("is a required field.");
        bean.manageSubjectAccruals(Arrays.asList(new SubjectAccrualDTO()));
    }
    
    @Test
    public void manageSubjectAccrualInvalidValuesValidation() throws Exception {
        thrown.expect(IndexedInputValidationException.class);
        thrown.expectMessage("is not a valid value for");
        
        SubjectAccrualDTO dto = new SubjectAccrualDTO();
        dto.setAssignedIdentifier(StConverter.convertToSt("Patient-1"));
        dto.setBirthDate(AccrualUtil.yearMonthStringToTs("01/2000"));
        dto.setGender(CdConverter.convertStringToCd("FOO"));
        dto.setEthnicity(CdConverter.convertStringToCd("FOO"));
        dto.setRace(DSetConverter.convertCdListToDSet(Arrays.asList(CdConverter.convertToCd(PatientRaceCode.AMERICAN_INDIAN))));
        dto.setCountryCode(CdConverter.convertStringToCd("FOO"));
        dto.setZipCode(StConverter.convertToSt("22222"));
        dto.setRegistrationDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("01/01/2000")));
        dto.setPaymentMethod(CdConverter.convertStringToCd("FOO"));
        dto.setDiseaseIdentifier(IiConverter.convertToIi(1234L));
        dto.setParticipatingSiteIdentifier(IiConverter.convertToIi(1234L));
        
        bean.manageSubjectAccruals(Arrays.asList(dto));
    }
    
    @Test
    public void deleteSubject() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Method not yet implemented.");
        
        bean.deleteSubjectAccrual(IiConverter.convertToIi(1L));
    }
    
    private StudySite createAccessibleStudySite() {
        StudySite ss = new StudySite();
        ss.setLocalStudyProtocolIdentifier("my treating site");
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        ss.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        ss.setStudyProtocol(TestSchema.studyProtocols.get(0));
        TestSchema.addUpdObject(ss);
        StudySiteAccrualAccess ssAccAccess = new StudySiteAccrualAccess();
        ssAccAccess.setStudySite(ss);
        ssAccAccess.setRegistryUser(TestSchema.registryUsers.get(0));
        ssAccAccess.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssAccAccess.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        TestSchema.addUpdObject(ssAccAccess);
        return ss;
    }
  
    @Test
    public void updateSubjectAccrualCountWoutExistingCount() throws Exception {
        StudySite ss = createAccessibleStudySite(); 
        when(studySiteSvc.get(any(Ii.class))).thenReturn(new StudySiteConverter().convertFromDomainToDto(ss));
        bean.updateSubjectAccrualCount(IiConverter.convertToIi(ss.getId()), IntConverter.convertToInt(100));
    }
    
    @Test 
    public void updateSubjectAccrualIiFailureNull() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Site Ii must be valid.");
        bean.updateSubjectAccrualCount(null, IntConverter.convertToInt(100));
    }
    
    @Test 
    public void updateSubjectAccrualIiFailureNone() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Site Ii must be valid.");
        bean.updateSubjectAccrualCount(new Ii(), IntConverter.convertToInt(100));
    }
   
    @Test 
    public void updateSubjectAccrualUserAuthFailure() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("User does not have accrual access to site.");
        bean.updateSubjectAccrualCount(IiConverter.convertToIi(1L), IntConverter.convertToInt(100));
    }

    @Test
    public void updateSubjectAccrualCountWithExistingCount() throws Exception {
        StudySite ss = createAccessibleStudySite(); 
        when(studySiteSvc.get(any(Ii.class))).thenReturn(new StudySiteConverter().convertFromDomainToDto(ss));
        StudySiteSubjectAccrualCount count = new StudySiteSubjectAccrualCount();
        count.setStudySite(ss);
        count.setStudyProtocol(TestSchema.studyProtocols.get(0));
        count.setAccrualCount(new Integer(50));
        TestSchema.addUpdObject(count);
        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();
        bean.updateSubjectAccrualCount(IiConverter.convertToIi(ss.getId()), IntConverter.convertToInt(100));
    }
   
    @Test
    public void submitBatchData() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        Ed batchData = new Ed();
        batchData.setData(FileUtils.readFileToByteArray(file));
        
        bean.submitBatchData(batchData);
        
        List<BatchFile> readyForProcessing = bean.getBatchFileService().getBatchFilesAvailableForProcessing();
        assertFalse(readyForProcessing.isEmpty());
        assertEquals(1, readyForProcessing.size());
        
        BatchFile bf = readyForProcessing.get(0);
        assertTrue(bf.isPassedValidation());
        assertFalse(bf.isProcessed());
        assertNotNull(bf.getFileLocation());
        FileUtils.deleteQuietly(new File(bf.getFileLocation()));
    }
    
    @Test
    public void submitBatchDataNullChecks() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Null batch files are not allowed. Please provide a valid batch file.");
        
        Ed batchData = new Ed();
        bean.submitBatchData(batchData);
    }
    
    @Test
    public void search() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Method not yet implemented.");
        
        bean.search(IiConverter.convertToIi(1L), IiConverter.convertToIi(1L), null, null, null);
    }
    
    private void validateSubjectAccrualDTO(SubjectAccrualDTO expected, SubjectAccrualDTO given) {
        assertEquals(StConverter.convertToString(expected.getAssignedIdentifier()), StConverter.convertToString(given.getAssignedIdentifier()));
        assertEquals(AccrualUtil.tsToYearMonthString(expected.getBirthDate()), AccrualUtil.tsToYearMonthString(given.getBirthDate()));
        assertEquals(CdConverter.convertCdToString(expected.getGender()), CdConverter.convertCdToString(given.getGender()));
        assertEquals(CdConverter.convertCdToString(expected.getEthnicity()), CdConverter.convertCdToString(given.getEthnicity()));
        assertEquals(DSetEnumConverter.convertDSetToCsv(PatientRaceCode.class, expected.getRace()), 
                DSetEnumConverter.convertDSetToCsv(PatientRaceCode.class, given.getRace()));
        assertEquals(CdConverter.convertCdToString(expected.getCountryCode()), CdConverter.convertCdToString(given.getCountryCode()));
        assertEquals(StConverter.convertToString(expected.getZipCode()), StConverter.convertToString(given.getZipCode()));
        assertEquals(AccrualUtil.tsToYearMonthString(expected.getRegistrationDate()), AccrualUtil.tsToYearMonthString(given.getRegistrationDate()));
        assertEquals(CdConverter.convertCdToString(expected.getPaymentMethod()), CdConverter.convertCdToString(given.getPaymentMethod()));
        assertEquals(IiConverter.convertToLong(expected.getDiseaseIdentifier()), IiConverter.convertToLong(given.getDiseaseIdentifier()));
        assertEquals(IiConverter.convertToLong(expected.getParticipatingSiteIdentifier()), IiConverter.convertToLong(given.getParticipatingSiteIdentifier()));
    }
}
