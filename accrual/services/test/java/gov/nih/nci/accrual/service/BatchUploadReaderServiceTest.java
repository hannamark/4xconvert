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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.service.util.BatchImportResults;
import gov.nih.nci.accrual.service.util.BatchValidationResults;
import gov.nih.nci.accrual.service.util.CdusBatchUploadReaderBean;
import gov.nih.nci.accrual.service.util.CountryBean;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.POPatientBean;
import gov.nih.nci.accrual.service.util.SearchStudySiteBean;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.SDCDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author vrushali
 */
public class BatchUploadReaderServiceTest {
    private Ii abbreviatedIi;
    private Ii completeIi;
    private ServiceLocatorPaInterface paSvcLocator;
    private CountryService countryService = new CountryBean();
    private CdusBatchUploadReaderBean readerService;
    private SubmissionServiceLocal submissionService = new SubmissionBean();
    private StudySubjectServiceLocal studySubjectService = new StudySubjectBean();
    private MailManagerServiceRemote mailService;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        abbreviatedIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId());
        completeIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(2).getId());
        
        mailService = mock(MailManagerServiceRemote.class);
        readerService = new CdusBatchUploadReaderBean();
        readerService.setCountryService(countryService);
        readerService.setStudySubjectService(studySubjectService);
        readerService.setSubmissionService(submissionService);
        readerService.setPerformedActivityService(new PerformedActivityBean());
        readerService.setSearchStudySiteService(new SearchStudySiteBean());
        PatientBeanLocal patientBean = new PatientBeanLocal();
        patientBean.setCountryService(countryService);
        patientBean.setPatientCorrelationSvc(new POPatientBean());        
        readerService.setPatientService(patientBean);
        
        StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
            public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii ii = (Ii) args[0];
                StudyProtocolDTO dto = new StudyProtocolDTO();
                if (StringUtils.equals(ii.getExtension(), "NCI-2009-00001")) {
                    dto.setIdentifier(abbreviatedIi);
                } else if (StringUtils.equals(ii.getExtension(), "NCI-2010-00003")) {
                    dto.setIdentifier(completeIi);
                }
                return dto;
            }
        });
        
        final SDCDiseaseDTO disease = new SDCDiseaseDTO();
        disease.setIdentifier(IiConverter.convertToIi(TestSchema.diseases.get(0).getId()));
        SDCDiseaseServiceRemote diseaseSvc = mock(SDCDiseaseServiceRemote.class);
        when(diseaseSvc.getByCode(any(String.class))).thenAnswer(new Answer<SDCDiseaseDTO>() {
            public SDCDiseaseDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String medraCode = (String) args[0];
                List<String> validCodes = Arrays.asList("10053571", "10010029");
                if (validCodes.contains(medraCode)) {
                    return disease;
                } else {
                    return null;
                }
            }
        });
        
        paSvcLocator = mock(ServiceLocatorPaInterface.class);
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        when(paSvcLocator.getDiseaseService()).thenReturn(diseaseSvc);
        
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);
        
        PoServiceLocator poServiceLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poServiceLoc);
        
        IdentifiedOrganizationCorrelationServiceRemote identifiedOrgCorrelationSvc 
            = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        when(identifiedOrgCorrelationSvc.search(any(IdentifiedOrganizationDTO.class))).thenAnswer(new Answer<List<IdentifiedOrganizationDTO>>() {
            public List<IdentifiedOrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                IdentifiedOrganizationDTO org = (IdentifiedOrganizationDTO) args[0];
                if (StringUtils.equals(IiConverter.convertToString(org.getAssignedId()), "CTEP")) {
                    IdentifiedOrganizationDTO result = new IdentifiedOrganizationDTO();
                    result.setPlayerIdentifier(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                    return Arrays.asList(result); 
                } else {
                    return new ArrayList<IdentifiedOrganizationDTO>();
                }
            }
        });
        
        OrganizationEntityServiceRemote orgSvc = mock(OrganizationEntityServiceRemote.class);
        when(orgSvc.getOrganization(any(Ii.class))).thenAnswer(new Answer<OrganizationDTO>() {
            public OrganizationDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii ii = (Ii) args[0];
                OrganizationDTO org = new OrganizationDTO();
                if (StringUtils.equalsIgnoreCase(IiConverter.convertToString(ii), "1")) {
                    org.setIdentifier(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                } else if (StringUtils.equalsIgnoreCase(IiConverter.convertToString(ii), "2")) {
                    org.setIdentifier(IiConverter.convertToIi(TestSchema.organizations.get(1).getId()));
                } else {
                    org = null;
                }
                return org;
            }
        });
        
        PatientCorrelationServiceRemote poPatientSvc = mock(PatientCorrelationServiceRemote.class);
        
        Ii ii = IiConverter.convertToPoPersonIi("1");
        PatientDTO patient = new PatientDTO();
        patient.setIdentifier(DSetConverter.convertIiToDset(ii));
        patient.setPlayerIdentifier(ii);
        when(poPatientSvc.getCorrelation(any(Ii.class))).thenReturn(patient);
        when(poPatientSvc.createCorrelation(any(PatientDTO.class))).thenReturn(ii);
        
        PersonEntityServiceRemote poPersonSvc = mock(PersonEntityServiceRemote.class);
       
        PersonDTO personDto = new PersonDTO();
        personDto.setIdentifier(IiConverter.convertToPoPersonIi("1"));
        personDto.setName(EnPnConverter.convertToEnPn("1", "2", "3", "4", "5"));
        Ad adr = AddressConverterUtil.create("street", "deliv", "city", "MD", "20000", "USA");
        personDto.setPostalAddress(adr);
        when(poPersonSvc.getPerson(any(Ii.class))).thenReturn(personDto);
        
        when(poServiceLoc.getPatientCorrelationService()).thenReturn(poPatientSvc);
        when(poServiceLoc.getPersonEntityService()).thenReturn(poPersonSvc);
        when(poServiceLoc.getIdentifiedOrganizationCorrelationService()).thenReturn(identifiedOrgCorrelationSvc);
        when(poServiceLoc.getOrganizationEntityService()).thenReturn(orgSvc);
    }
    
    @Test
    public void testReading() throws URISyntaxException {
        File file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(file);
        readerService.sendValidationErrorEmail(results);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(StringUtils.isNotEmpty(results.get(0).getMailTo()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
        verify(mailService, times(1)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        
        file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());        
        results = readerService.validateBatchData(file);
        readerService.sendValidationErrorEmail(results);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertTrue(StringUtils.isNotEmpty(results.get(0).getMailTo()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verify(mailService, times(0)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        
        file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        results = readerService.validateBatchData(file);
        readerService.sendValidationErrorEmail(results);
        assertEquals(1, results.size());
        assertTrue(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isEmpty(results.get(0).getErrors().toString()));
        assertTrue(StringUtils.isNotEmpty(results.get(0).getMailTo()));
        assertFalse(results.get(0).getValidatedLines().isEmpty());
        verify(mailService, times(0)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        
        file = new File(this.getClass().getResource("/CDUS.zip").toURI());
        List<BatchValidationResults> validationResults = readerService.validateBatchData(file);
        readerService.sendValidationErrorEmail(results);
        assertEquals(2, validationResults.size());
        for (BatchValidationResults result : validationResults) {
            assertTrue(result.isPassedValidation());
            assertTrue(StringUtils.isEmpty(result.getErrors().toString()));
            assertTrue(StringUtils.isNotEmpty(result.getMailTo()));
            assertFalse(result.getValidatedLines().isEmpty());
        }
        verify(mailService, times(0)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
    }
    
    @Test
    public void testIsVaildProtocolId() throws PAException, TooManyResultsException, URISyntaxException {
        StudyProtocolServiceRemote mockSpService = mock(StudyProtocolServiceRemote.class);
        when(paSvcLocator.getStudyProtocolService()).thenReturn(mockSpService);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);
        
        File file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        List<BatchValidationResults> results = readerService.validateBatchData(file);
        assertEquals(1, results.size());
        assertFalse(results.get(0).isPassedValidation());
        assertTrue(StringUtils.isNotEmpty(results.get(0).getErrors().toString()));
        assertTrue(StringUtils.isNotEmpty(results.get(0).getMailTo()));
        assertTrue(results.get(0).getValidatedLines().isEmpty());
    }
    
    @Test
    public void testPerformBatchImport() throws Exception {
        assertEquals(2, submissionService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        File file = new File(this.getClass().getResource("/CDUS_Abbreviated.txt").toURI());
        List<BatchImportResults> importResults = readerService.importBatchData(file);
        readerService.sendConfirmationEmail(importResults);
        assertEquals(1, importResults.size());
        assertEquals(72, importResults.get(0).getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.get(0).getFileName());
        assertEquals(3, submissionService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        verify(mailService, times(1)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
                
        assertEquals(0, submissionService.getByStudyProtocol(completeIi).size());
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());
        
        file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        importResults = readerService.importBatchData(file);
        readerService.sendConfirmationEmail(importResults);
        assertEquals(1, importResults.size());
        assertEquals(24, importResults.get(0).getTotalImports());
        assertEquals("CDUS_Complete.txt", importResults.get(0).getFileName());
        assertEquals(1, submissionService.getByStudyProtocol(completeIi).size());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());
        verify(mailService, times(1)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        
        file = new File(this.getClass().getResource("/CDUS_Complete-modified.txt").toURI());
        importResults = readerService.importBatchData(file);
        readerService.sendConfirmationEmail(importResults);
        assertEquals(0, importResults.size());
        verify(mailService, times(0)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
    }
    
    @Test
    public void testPerformImportOfArchive() throws Exception {
        File file = new File(this.getClass().getResource("/CDUS.zip").toURI());
        
        assertEquals(2, submissionService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(2, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(0, submissionService.getByStudyProtocol(completeIi).size());
        assertEquals(0, studySubjectService.getByStudyProtocol(completeIi).size());
        
        List<BatchImportResults> importResults = readerService.importBatchData(file);
        readerService.sendConfirmationEmail(importResults);
        assertEquals(2, importResults.size());
        assertEquals(72, importResults.get(0).getTotalImports());
        assertEquals("CDUS_Abbreviated.txt", importResults.get(0).getFileName());
        assertEquals(3, submissionService.getByStudyProtocol(abbreviatedIi).size());
        assertEquals(74, studySubjectService.getByStudyProtocol(abbreviatedIi).size());
        
        assertEquals(24, importResults.get(1).getTotalImports());
        assertEquals("CDUS_Complete.txt", importResults.get(1).getFileName());
        assertEquals(1, submissionService.getByStudyProtocol(completeIi).size());
        assertEquals(24, studySubjectService.getByStudyProtocol(completeIi).size());
        verify(mailService, times(1)).sendMailWithAttachment(anyString(), anyString(), anyString(), any(File[].class));
        mailService = mock(MailManagerServiceRemote.class);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
    }
}
