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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.util.SearchStudySiteResultDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.PatientBeanLocal;
import gov.nih.nci.accrual.service.PerformedActivityBean;
import gov.nih.nci.accrual.service.StudySubjectBean;
import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.service.SubjectAccrualBeanLocal;
import gov.nih.nci.accrual.service.SubjectAccrualServiceLocal;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.service.util.CountryBean;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.MockCsmUtil;
import gov.nih.nci.accrual.service.util.SearchStudySiteBean;
import gov.nih.nci.accrual.service.util.SearchStudySiteService;
import gov.nih.nci.accrual.service.util.SearchTrialService;
import gov.nih.nci.accrual.service.util.SubjectAccrualCountBean;
import gov.nih.nci.accrual.util.AbstractAccrualHibernateTestCase;
import gov.nih.nci.accrual.util.MockCSMUserService;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.PoRegistry;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorAccInterface;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.ICD9DiseaseDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.SDCDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ICD9DiseaseServiceRemote;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.SDCDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Abstract class that sets up information necessary to process batch files.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
public abstract class AbstractBatchUploadReaderTest extends AbstractAccrualHibernateTestCase {
    protected PoServiceLocator poServiceLoc;
    protected Ii abbreviatedIi;
    protected Ii completeIi;
    protected Ii inactiveIi;
    protected Ii preventionIi;
    protected ServiceLocatorPaInterface paSvcLocator;
    protected CountryService countryService = new CountryBean();
    protected CdusBatchUploadReaderBean readerService;
    protected StudySubjectServiceLocal studySubjectService = new StudySubjectBean();
    protected CdusBatchUploadDataValidator cdusBatchUploadDataValidator = new CdusBatchUploadDataValidator();
    protected MailManagerServiceRemote mailService;
    protected BatchFileService batchFileSvc = new BatchFileServiceBeanLocal();
    private static final String ERROR_SUBJECT_KEY = "accrual.error.subject";
    private static final String ERROR_SUBJECT_VALUE = "accrual.error.subject- ${nciTrialIdentifier}";
    private static final String ERROR_BODY_KEY = "accrual.error.body";
    private static final String ERROR_BODY_VALUE = "accrual.error.body - ${nciTrialIdentifier}, ${SubmitterName}, ${CurrentDate}, ${fileName}, ${errors}.";
    private static final String CONFIRMATION_SUBJECT_KEY = "accrual.confirmation.subject";
    private static final String CONFIRMATION_SUBJECT_VALUE = "accrual.confirmation.subject- ${nciTrialIdentifier}";
    private static final String CONFIRMATION_BODY_KEY = "accrual.confirmation.body";
    private static final String CONFIRMATION_BODY_VALUE = "accrual.confirmation.body - ${nciTrialIdentifier}, ${SubmitterName}, ${CurrentDate}, ${fileName}, ${errors}, ${count}.";
    
    @Before
    public void setUpReader() throws Exception {
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        TestSchema.primeData();
        abbreviatedIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId());
        inactiveIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(1).getId());
        completeIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(2).getId());
        preventionIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(3).getId());

        mailService = mock(MailManagerServiceRemote.class);
        readerService = new CdusBatchUploadReaderBean();
        readerService.setCountryService(countryService);
        readerService.setStudySubjectService(studySubjectService);
        readerService.setPerformedActivityService(new PerformedActivityBean());
        readerService.setBatchFileSvc(batchFileSvc);
        cdusBatchUploadDataValidator.setCountryService(countryService);
        cdusBatchUploadDataValidator.setStudySubjectService(studySubjectService);    
        cdusBatchUploadDataValidator.setPerformedActivityService(new PerformedActivityBean());
        readerService.setCdusBatchUploadDataValidator(cdusBatchUploadDataValidator);
       
        PatientBeanLocal patientBean = new PatientBeanLocal();
        cdusBatchUploadDataValidator.setPatientService(patientBean);
        readerService.setPatientService(patientBean);

        StudyProtocolServiceRemote spSvc = mock(StudyProtocolServiceRemote.class);
        when(spSvc.loadStudyProtocol(any(Ii.class))).thenAnswer(new Answer<StudyProtocolDTO>() {
            public StudyProtocolDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii ii = (Ii) args[0];
                StudyProtocolDTO dto = new StudyProtocolDTO();
                dto.setProprietaryTrialIndicator(BlConverter.convertToBl(false));
                Set<Ii> secondaryIdentifiers =  new HashSet<Ii>();
                Ii spSecId = new Ii();
                spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
                
                if (StringUtils.equals(ii.getExtension(), "NCI-2009-00001")
                		|| StringUtils.equals(ii.getExtension(), "S0512")) {
                    dto.setIdentifier(abbreviatedIi);
                    dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                    spSecId.setExtension("NCI-2009-00001");
                    secondaryIdentifiers.add(spSecId);
                    dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
                } else if (StringUtils.equals(ii.getExtension(), "NCI-2009-00002")) {
                    dto.setIdentifier(inactiveIi);
                    dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
                    spSecId.setExtension("NCI-2009-00002");
                    secondaryIdentifiers.add(spSecId);
                    dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
                } else if (StringUtils.equals(ii.getExtension(), "NCI-2010-00003")) {
                    dto.setIdentifier(completeIi);
                    dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                    spSecId.setExtension("NCI-2010-00003");
                    secondaryIdentifiers.add(spSecId);
                    dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
                } else if (StringUtils.equals(ii.getExtension(), "NCI-2009-00003")) {
                    dto.setIdentifier(preventionIi);
                    dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                    dto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));
                    spSecId.setExtension("NCI-2009-00003");
                    secondaryIdentifiers.add(spSecId);
                    dto.setSecondaryIdentifiers(DSetConverter.convertIiSetToDset(secondaryIdentifiers));
                } else {
                    dto = null;
                }
                return dto;
            }
        });
        when(spSvc.getStudyProtocol(any(Ii.class))).thenReturn(new StudyProtocolDTO());

        SearchStudySiteService sssSvc = mock(SearchStudySiteBean.class);
        readerService.setSearchStudySiteService(sssSvc);
        cdusBatchUploadDataValidator.setSearchStudySiteService(sssSvc);
        when(sssSvc.getTreatingSites(any(Long.class))).thenAnswer(new Answer<List<SearchStudySiteResultDto>>() {
            public List<SearchStudySiteResultDto> answer(InvocationOnMock invocation) throws Throwable {
            	List<SearchStudySiteResultDto> result = new ArrayList<SearchStudySiteResultDto>();
            	SearchStudySiteResultDto dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(1).getId()));
                dto.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(0).getName()));
                dto.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                result.add(dto);
                dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(2).getId()));
                dto.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(1).getName()));
                dto.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(1).getId()));
                result.add(dto);
                dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(4).getId()));
                dto.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(0).getName()));
                dto.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                result.add(dto);
                dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(6).getId()));
                dto.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(0).getName()));
                dto.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                result.add(dto);
                dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(7).getId()));
                dto.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(0).getName()));
                dto.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                result.add(dto);
                dto = new SearchStudySiteResultDto();
                dto.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(8).getId()));
                dto.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(1).getName()));
                dto.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(1).getId()));
                result.add(dto);
                return result;
            }
        });
        
        when(sssSvc.getStudySiteByOrg(any(Ii.class), any(Ii.class))).thenAnswer(new Answer<SearchStudySiteResultDto>() {
            public SearchStudySiteResultDto answer(InvocationOnMock invocation) throws Throwable {
            	SearchStudySiteResultDto result = new SearchStudySiteResultDto();
            	result.setStudySiteIi(IiConverter.convertToIi(TestSchema.studySites.get(8).getId()));
            	result.setOrganizationName(StConverter.convertToSt(TestSchema.organizations.get(1).getName()));
            	result.setOrganizationIi(IiConverter.convertToIi(TestSchema.organizations.get(1).getId()));
                return result;
            }
        });

        SearchTrialService searchTrialSvc = mock(SearchTrialService.class);
        when(searchTrialSvc.isAuthorized(any(Ii.class), any(Ii.class))).thenAnswer(new Answer<Bl>() {
            public Bl answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii spIi = (Ii) args[0];
                Bl result = new Bl();
                if (StringUtils.equals(spIi.getExtension(), "NCI-2009-00002")) {
                    result.setValue(Boolean.FALSE);
                } else {
                    result.setValue(Boolean.TRUE);
                }
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
        readerService.setSearchTrialService(searchTrialSvc);
        cdusBatchUploadDataValidator.setSearchTrialService(searchTrialSvc);
        cdusBatchUploadDataValidator.setSubjectAccrualService(mock(SubjectAccrualServiceLocal.class));

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
                }
                return null;
            }
        });
        when(diseaseSvc.get(any(Ii.class))).thenAnswer(new Answer<SDCDiseaseDTO>() {
            public SDCDiseaseDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii ii = (Ii) args[0];
                if (IiConverter.convertToLong(ii).equals(TestSchema.diseases.get(0).getId())) {
                    return disease;
                }
                return null;
            }
        });
        
        final ICD9DiseaseDTO icdDis = new ICD9DiseaseDTO();
        icdDis.setIdentifier(IiConverter.convertToIi(TestSchema.diseases.get(0).getId()));
        ICD9DiseaseServiceRemote icd9DiseaseSvc = mock(ICD9DiseaseServiceRemote.class);
        when(icd9DiseaseSvc.getByCode(any(String.class))).thenAnswer(new Answer<ICD9DiseaseDTO>() {
            public ICD9DiseaseDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String medraCode = (String) args[0];
                List<String> validCodes = Arrays.asList("10053581");
                if (validCodes.contains(medraCode)) {
                    return icdDis;
                }
                return null;
            }
        });
        
        SubjectAccrualCountBean accrualCountSvc = new SubjectAccrualCountBean();
        accrualCountSvc.setSearchTrialService(searchTrialSvc);
        SubjectAccrualBeanLocal subjectAccrualSvc = new SubjectAccrualBeanLocal();
        subjectAccrualSvc.setBatchFileService(new BatchFileServiceBeanLocal());
        subjectAccrualSvc.setCountryService(countryService);
        subjectAccrualSvc.setPatientService(patientBean);
        subjectAccrualSvc.setPerformedActivityService(new PerformedActivityBean());
        subjectAccrualSvc.setStudySubjectService(studySubjectService);
        subjectAccrualSvc.setSubjectAccrualCountSvc(accrualCountSvc);
        subjectAccrualSvc.setUseTestSeq(true);
        readerService.setSubjectAccrualService(subjectAccrualSvc);
        
        RegistryUserServiceRemote registryUserService = mock(RegistryUserServiceRemote.class);
        when(registryUserService.getUser(anyString())).thenReturn(TestSchema.registryUsers.get(0));
        
        StudySiteServiceRemote studySiteSvc = mock(StudySiteServiceRemote.class);
        when(studySiteSvc.get(any(Ii.class))).thenAnswer(new Answer<StudySiteDTO>() {
            public StudySiteDTO answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Ii ii = (Ii) args[0];
                if (ii != null && ii.getExtension() != null) {
                	for (StudySite ss : TestSchema.studySites) {
                		if (ss.getId().equals(IiConverter.convertToLong(ii))) {
                			return Converters.get(StudySiteConverter.class).convertFromDomainToDto(ss);
                		}
                	}
                }
                return Converters.get(StudySiteConverter.class).convertFromDomainToDto(TestSchema.studySites.get(1));
            }
        });
        
        paSvcLocator = mock(ServiceLocatorPaInterface.class);
        when(paSvcLocator.getStudyProtocolService()).thenReturn(spSvc);
        when(paSvcLocator.getMailManagerService()).thenReturn(mailService);
        when(paSvcLocator.getDiseaseService()).thenReturn(diseaseSvc);
        when(paSvcLocator.getICD9DiseaseService()).thenReturn(icd9DiseaseSvc);
        when(paSvcLocator.getStudySiteService()).thenReturn(studySiteSvc);
        when(paSvcLocator.getRegistryUserService()).thenReturn(registryUserService);
        LookUpTableServiceRemote lookuptableSvc = mock(LookUpTableServiceRemote.class);
        when(paSvcLocator.getLookUpTableService()).thenReturn(lookuptableSvc);
        when(paSvcLocator.getLookUpTableService().getPropertyValue(ERROR_SUBJECT_KEY)).thenReturn(ERROR_SUBJECT_VALUE);
        when(paSvcLocator.getLookUpTableService().getPropertyValue(ERROR_BODY_KEY)).thenReturn(ERROR_BODY_VALUE);
        when(paSvcLocator.getLookUpTableService().getPropertyValue(CONFIRMATION_SUBJECT_KEY)).thenReturn(CONFIRMATION_SUBJECT_VALUE);
        when(paSvcLocator.getLookUpTableService().getPropertyValue(CONFIRMATION_BODY_KEY)).thenReturn(CONFIRMATION_BODY_VALUE);
        
        PlannedActivityServiceRemote plannedActivitySvc = mock(PlannedActivityServiceRemote.class);
        when(paSvcLocator.getPlannedActivityService()).thenReturn(plannedActivitySvc);
        PlannedEligibilityCriterionDTO dto = new PlannedEligibilityCriterionDTO();
        List<PlannedEligibilityCriterionDTO> pecList = new ArrayList<PlannedEligibilityCriterionDTO>();
        dto.setCriterionName(StConverter.convertToSt(PaServiceLocator.ELIG_CRITERION_NAME_GENDER));
        dto.setEligibleGenderCode(CdConverter.convertToCd(PatientGenderCode.UNKNOWN));
        pecList.add(dto);
        when(paSvcLocator.getPlannedActivityService().getPlannedEligibilityCriterionByStudyProtocol(completeIi))
        		.thenReturn(pecList);
        PaServiceLocator.getInstance().setServiceLocator(paSvcLocator);

        
        poServiceLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poServiceLoc);
        setUpPoRegistry();
        
        ServiceLocatorAccInterface accSvcLocator = mock(ServiceLocatorAccInterface.class);
        when(accSvcLocator.getBatchUploadReaderService()).thenReturn(readerService);
        when(accSvcLocator.getSubjectAccrualCountService()).thenReturn(accrualCountSvc);
        
        CSMUserService.setInstance(new MockCSMUserService());
    }

    protected void setUpPoRegistry() throws NullifiedEntityException, NullifiedRoleException, EntityValidationException,
            CurationException {
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
                }
                return new ArrayList<IdentifiedOrganizationDTO>();
            }
        });
        
        when(identifiedOrgCorrelationSvc.getCorrelationsByPlayerIdsWithoutLimit(any(Long[].class)))
        .thenAnswer(new  Answer<List<IdentifiedOrganizationDTO>>() {
            public List<IdentifiedOrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                List<IdentifiedOrganizationDTO> ctepList = new ArrayList<IdentifiedOrganizationDTO>();
                IdentifiedOrganizationDTO result = new IdentifiedOrganizationDTO();
            	result.setAssignedId(new Ii());
            	result.getAssignedId().setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
            	result.getAssignedId().setExtension("CTEP");
                result.setPlayerIdentifier(IiConverter.convertToIi(TestSchema.organizations.get(0).getId()));
                ctepList.add(result);
                return ctepList;
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
                } else if (StringUtils.equalsIgnoreCase(IiConverter.convertToString(ii), "nullifiedorg")) {
                    throw new NullifiedEntityException(IiConverter.convertToIi("nullifiedorg"));
                } else {
                    org = null;
                }
                return org;
            }
        });
        
        OrganizationEntityServiceRemote organizationEntityService = mock(OrganizationEntityServiceRemote.class);
        when(organizationEntityService.getOrganization(any(Ii.class))).thenReturn(new OrganizationDTO());
        
        HealthCareFacilityCorrelationServiceRemote healthCareFacilityCorrelationService = 
            mock(HealthCareFacilityCorrelationServiceRemote.class);
        when(healthCareFacilityCorrelationService.search(any(HealthCareFacilityDTO.class)))
        .thenAnswer(new Answer<List<HealthCareFacilityDTO>>() {
            public List<HealthCareFacilityDTO> answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                HealthCareFacilityDTO hcf = (HealthCareFacilityDTO) args[0];
                List<HealthCareFacilityDTO> list = new ArrayList<HealthCareFacilityDTO>();
                HealthCareFacilityDTO dto = new HealthCareFacilityDTO();       
                DSet<Ii> dset = new DSet<Ii>();
                Ii ii = new Ii();
                ii.setRoot(DSetConverter.BASE_ROOT);
                if (StringUtils.equals(DSetConverter.getFirstInDSet(hcf.getIdentifier()).getExtension(), "NCI-CTRP")) {
                	ii.setExtension("NCI-CTRP");
                }
                Set<Ii> iis = new HashSet<Ii>();
                iis.add(ii);
                dset.setItem(iis);
                dto.setIdentifier(dset);
                list.add(dto);
                return list;
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
        when(poServiceLoc.getHealthCareFacilityCorrelationService()).thenReturn(healthCareFacilityCorrelationService);
    }
}
