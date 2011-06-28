/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.pa.service.util.MockPAServiceUtils;
import gov.nih.nci.pa.service.util.MockRegistryUserServiceBean;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegulatoryInformationBean;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TrialRegistrationServiceTest extends AbstractHibernateTestCase {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TrialRegistrationBeanLocal bean = new TrialRegistrationBeanLocal();
    StudyProtocolServiceLocal studyProtocolService = new StudyProtocolBeanLocal();
    StudyOverallStatusServiceLocal studyOverallStatusService = new StudyOverallStatusBeanLocal();
    StudyIndldeServiceLocal studyIndldeService  = new StudyIndldeBeanLocal();
    StudyResourcingServiceLocal studyResourcingService = new StudyResourcingBeanLocal();
    DocumentServiceLocal documentService = new DocumentBeanLocal();
    StudyDiseaseServiceLocal studyDiseaseService = new StudyDiseaseBeanLocal();
    ArmServiceLocal armService = new ArmBeanLocal();
    PlannedActivityServiceLocal plannedActivityService = new PlannedActivityBeanLocal();
    StratumGroupServiceLocal subGroupsService = new StratumGroupBeanLocal();
    StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal();
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = new StudyOutcomeMeasureBeanLocal();
    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = new StudyRegulatoryAuthorityBeanLocal();
    private StudyContactServiceLocal studyContactSvc = new StudyContactBeanLocal();
    private RegulatoryInformationServiceRemote regulatoryInfoSvc = new RegulatoryInformationBean();
    private ServiceLocator paSvcLoc;
    private DocumentWorkflowStatusServiceLocal documentWrkService;
    private PoServiceLocator poSvcLoc;
    private OrganizationEntityServiceRemote poOrgSvc;
    private PersonEntityServiceRemote poPersonSvc;

    private Ii spIi;

    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        bean.setStudyProtocolService(studyProtocolService);
        bean.setStudyOverallStatusService(studyOverallStatusService);
        bean.setStudyIndldeService(studyIndldeService);
        bean.setStudyResourcingService(studyResourcingService);
        bean.setDocumentService(documentService);
        bean.setStudyDiseaseService(studyDiseaseService);
        bean.setArmService(armService);
        bean.setPlannedActivityService(plannedActivityService);
        bean.setSubGroupsService(subGroupsService);
        bean.setStudySiteService(studySiteService);
        bean.setStudySiteContactService(studySiteContactService);
        bean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        bean.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        bean.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        bean.setRegulatoryInfoBean(regulatoryInfoSvc);
        bean.setUserServiceLocal(new MockRegistryUserServiceBean());
        bean.setPaServiceUtils(new MockPAServiceUtils());
        CSMUserService.setRegistryUserService(new MockCSMUserService());
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        documentWrkService = mock (DocumentWorkflowStatusServiceLocal.class);
        when (paSvcLoc.getDocumentWorkflowStatusService()).thenReturn(documentWrkService);
        when (paSvcLoc.getLookUpTableService()).thenReturn(new MockLookUpTableServiceBean());
        StudyProtocolServiceLocal studyProtocolSvc = mock(StudyProtocolServiceLocal.class);
        when(paSvcLoc.getStudyProtocolService()).thenReturn(studyProtocolSvc);
        when (documentWrkService.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(new DocumentWorkflowStatusDTO());
        StudySiteServiceLocal studySiteSrv = mock (StudySiteServiceLocal.class);
        when(paSvcLoc.getStudySiteService()).thenReturn(studySiteSrv);
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("1"));
        when(studySiteSrv.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenReturn(
                Arrays.asList(studySiteDTO));
        OrganizationCorrelationServiceRemote ocsr = mock(OrganizationCorrelationServiceRemote.class);
        when(ocsr.getPoResearchOrganizationByEntityIdentifier(any(Ii.class))).thenReturn(IiConverter.convertToPoResearchOrganizationIi("1"));
        when(ocsr.createResearchOrganizationCorrelations(any(String.class))).thenReturn(1L);
        when(paSvcLoc.getOrganizationCorrelationService()).thenReturn(ocsr);

        MailManagerServiceLocal mailSvc = mock(MailManagerServiceLocal.class);

        StudyMilestoneServiceBean studyMilestoneSvc = new StudyMilestoneServiceBean();
        studyMilestoneSvc.setStudyProtocolService(studyProtocolService);
        when(paSvcLoc.getStudyMilestoneService()).thenReturn(studyMilestoneSvc);
        when(paSvcLoc.getStudyIndldeService()).thenReturn(studyIndldeService);
        when(paSvcLoc.getDocumentService()).thenReturn(documentService);
        when(paSvcLoc.getStudyContactService()).thenReturn(studyContactSvc);
        when(paSvcLoc.getStudyRegulatoryAuthorityService()).thenReturn(studyRegulatoryAuthorityService);
        bean.setOcsr(ocsr);
        bean.setMailManagerSerivceLocal(mailSvc);
        setupPoSvc();
    }

    private void setupPoSvc() throws NullifiedEntityException, NullifiedRoleException, PAException, TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        poPersonSvc = mock(PersonEntityServiceRemote.class);

        ResearchOrganizationCorrelationServiceRemote roCorrelationSvc =
            mock(ResearchOrganizationCorrelationServiceRemote.class);
        ClinicalResearchStaffCorrelationServiceRemote crsSvc = mock(ClinicalResearchStaffCorrelationServiceRemote.class);
        HealthCareProviderCorrelationServiceRemote hcpSvc = mock(HealthCareProviderCorrelationServiceRemote.class);

        ResearchOrganizationDTO roDTO = new ResearchOrganizationDTO();
        roDTO.setPlayerIdentifier(IiConverter.convertToIi(1L));
        ClinicalResearchStaffDTO crsDTO = new ClinicalResearchStaffDTO();
        crsDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoClinicalResearchStaffIi("1")));
        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
        when(poSvcLoc.getPersonEntityService()).thenReturn(poPersonSvc);
        when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(roCorrelationSvc);
        when(poSvcLoc.getClinicalResearchStaffCorrelationService()).thenReturn(crsSvc);
        when(poSvcLoc.getHealthCareProviderCorrelationService()).thenReturn(hcpSvc);

        OrganizationDTO org = new OrganizationDTO();
        org.setIdentifier(IiConverter.convertToIi(1L));
        org.setName(EnOnConverter.convertToEnOn("Org Name"));
        org.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        org.setPostalAddress(AddressConverterUtil.create("2115 Executive Blvd.", "", "Rockville", "MD", "27852", "USA"));

        HealthCareProviderDTO hcpDTO = new HealthCareProviderDTO();
        hcpDTO.setIdentifier(DSetConverter.convertIiToDset(IiConverter.convertToPoHealtcareProviderIi("1")));
        hcpDTO.setPlayerIdentifier(IiConverter.convertToPoOrganizationIi("1"));

        when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(org);
        when(poPersonSvc.getPerson(any(Ii.class))).thenReturn(new PersonDTO());
        when(roCorrelationSvc.getCorrelation(any(Ii.class))).thenReturn(roDTO);
        when(crsSvc.getCorrelation(any(Ii.class))).thenReturn(crsDTO);
        when(hcpSvc.getCorrelation(any(Ii.class))).thenReturn(hcpDTO);
    }

    @Test
    public void createInterventionalStudyProtocolTest() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(PAUtil.isIiNull(ii));
    }

    @Test
    public void nullSiteIdentifiers() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = null;

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(PAUtil.isIiNull(ii));
    }

    @Test
    public void nullStudyOverallStatus() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Overall Status cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = null;
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullDocuments() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Protocol Document is required.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = null;

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullLeadOrganization() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Lead Organization cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = null;
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullPrincipalInvestigator() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Principal Investigator cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = null;
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullSponsorOrganization() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Sponsor Organization cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = null;
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullLeadOrganizationSiteIdentifier() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Identifier DTO cannot be null");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = null;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyContacts() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("One of StudyContact or StudySiteContact has to be used");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = null;
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullSummary4Organization() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Summary 4 Organization cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = null;

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullSummary4StudyResourcing() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Summary Four Study Resourcing DTO cannot be null");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = null;
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyRegulatoryAuthority() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Regulatory Information fields must be Entered.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = null;

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolProperties() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = null;
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolStartDate() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol start date cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        studyProtocolDTO.setStartDate(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolStartDateTypeCode() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol start date type code cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        studyProtocolDTO.setStartDateTypeCode(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolPrimaryCompletionDate() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol primary completion date cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        studyProtocolDTO.setPrimaryCompletionDate(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolPrimaryCompletionDateTypeCode() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol primary completion date type code cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolCtGovXmlIndicator() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol Ct.gov XML indicator cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        studyProtocolDTO.setCtgovXmlRequiredIndicator(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();

        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));

        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();

        StudyResourcingDTO summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, Arrays.asList(icDoc, protocolDoc, irbDoc), leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4Org, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void createInterventionalStudyProtocolValidationTest() throws Exception {
        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(spIi);
        studyProtocolDTO.setUserLastCreated(null);
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(DateUtils.addYears(new Date(), -1)));
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<DocumentDTO> documentDTOs = documentService.getDocumentsByStudyProtocol(spIi);
        DocumentDTO badFileTypeDoc = new DocumentDTO();
        badFileTypeDoc.setFileName(StConverter.convertToSt("badfile.xml"));
        badFileTypeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));
        documentDTOs.add(badFileTypeDoc);
        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        StudySiteDTO leadOrganizationSiteIdentifierDTO  = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        try {
            bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                    sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, new ArrayList<StudySiteDTO>(),
                    null, null, null, null, null, null, BlConverter.convertToBl(Boolean.FALSE));
            fail();
        } catch (PAException e) {
            assertTrue(StringUtils.contains(e.getMessage(),"Validation Exception Submitter is required."));
            assertTrue(StringUtils.contains(e.getMessage(),"Validation Exception Anticipated Primary Completion Date must be in future."));
            assertTrue(StringUtils.contains(e.getMessage(),"Document badfile.xml has an invalid file type."));
        }
    }

    @Test
    public void updateTest() throws Exception {
        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(spIi);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<DocumentDTO> documentDTOs = documentService.getDocumentsByStudyProtocol(spIi);
        OrganizationDTO leadOrganizationDTO = new  OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        OrganizationDTO sponsorOrganizationDTO = new  OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        try {
            bean.update(studyProtocolDTO, overallStatusDTO, null, studyIndldeDTOs, studyResourcingDTOs, documentDTOs,
                    null, null, null, null, null, null, null, null, null, BlConverter.convertToBl(Boolean.FALSE));
            fail();
        } catch (PAException e) {
        }
    }

    @Test
    public void testSendTSRXML() throws PAException {
        bean.sendTSRXML(null, null, null);
        bean.sendTSRXML(spIi, null, new ArrayList<StudyInboxDTO>());
        bean.sendTSRXML(spIi, CdConverter.convertToCd(MilestoneCode.READY_FOR_TSR), new ArrayList<StudyInboxDTO>());
        List<StudyInboxDTO> inboxList = new ArrayList<StudyInboxDTO>();
        StudyInboxDTO inboxDto = new StudyInboxDTO();
        Ivl<Ts> ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        inboxDto.setInboxDateRange(ivlTs);
        inboxList.add(inboxDto);
        bean.sendTSRXML(spIi, CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_SENT),inboxList);
        inboxList .remove(0);
        ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        ivlTs.setHigh(TsConverter.convertToTs(new Timestamp(0)));
        inboxDto.setInboxDateRange(ivlTs);
        inboxList.add(inboxDto);
        bean.setPaServiceUtils(mock(PAServiceUtils.class));
        bean.sendTSRXML(spIi, CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_SENT),inboxList);
    }
}
