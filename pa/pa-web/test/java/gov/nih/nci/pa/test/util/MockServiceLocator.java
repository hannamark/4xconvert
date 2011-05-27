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
package gov.nih.nci.pa.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PlannedMarker;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.CountryRegAuthorityDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseAlternameDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyObjectiveDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseAlternameServiceLocal;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedSubstanceAdministrationServiceRemote;
import gov.nih.nci.pa.service.ProprietaryTrialManagementServiceLocal;
import gov.nih.nci.pa.service.StratumGroupBeanLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyRelationshipServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.TrialRegistrationServiceLocal;
import gov.nih.nci.pa.service.audittrail.AuditTrailServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PDQTrialAbstractionServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQTrialRegistrationServiceBeanRemote;
import gov.nih.nci.pa.service.util.PDQUpdateGeneratorTaskServiceLocal;
import gov.nih.nci.pa.service.util.PDQXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.security.cgmm.constants.CGMMConstants;
import gov.nih.nci.service.MockOrganizationCorrelationService;
import gov.nih.nci.service.MockPlannedActivityService;
import gov.nih.nci.service.MockProtocolQueryService;
import gov.nih.nci.service.MockRegistryUserService;
import gov.nih.nci.service.MockStudyDiseaseService;
import gov.nih.nci.service.MockStudyIndIdeService;
import gov.nih.nci.service.MockStudyMilestoneService;
import gov.nih.nci.service.MockStudyOnholdService;
import gov.nih.nci.service.MockStudyOverallStatusService;
import gov.nih.nci.service.MockStudyProtocolService;
import gov.nih.nci.service.MockStudySiteService;
import gov.nih.nci.service.util.MockLookUpTableServiceBean;
import gov.nih.nci.service.util.MockStudySiteAccrualAccessService;
import gov.nih.nci.service.util.MockTSRReportGeneratorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.nci.commons.audit.AuditLogDetail;
import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.AuditType;


/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator {

    private final StudyProtocolServiceLocal studyProtocolService = new MockStudyProtocolService();
    private final StudyOverallStatusServiceLocal studyOverallStatusService = new MockStudyOverallStatusService();
    private final StudySiteServiceLocal studySiteService = new MockStudySiteService();
    private final PlannedActivityServiceLocal plannedActivityService = new MockPlannedActivityService();
    private final StudyOnholdServiceLocal studyOnholdService = new MockStudyOnholdService();
    private final ProtocolQueryServiceLocal protocolQueryService = new MockProtocolQueryService();
    private final StudyDiseaseServiceLocal studyDiseaseService = new MockStudyDiseaseService();
    private final StudyMilestoneServicelocal studyMilestoneService = new MockStudyMilestoneService();
    private final RegistryUserServiceLocal registryUserService = new MockRegistryUserService();
    private final StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService = new MockStudySiteAccrualAccessService();
    private final OrganizationCorrelationServiceRemote organizationCorrelationService = new MockOrganizationCorrelationService();
    private final LookUpTableServiceRemote lookUpService = new MockLookUpTableServiceBean();
    private final StudyIndldeServiceLocal studyIndIdeService = new MockStudyIndIdeService();
    private final TSRReportGeneratorServiceRemote tsrReportGeneratorService = new MockTSRReportGeneratorService();

    /**
     * @return mock service
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @return StudyOverallStatusServiceRemote
     */
    public StudyOverallStatusServiceLocal getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    /**
     * @return StudySiteServiceRemote
     */
    public StudySiteServiceLocal getStudySiteService() {
        return studySiteService;
    }

    /**
     * @return StudySiteAccrualStatusServiceRemote
     */
    public StudySiteAccrualStatusServiceLocal getStudySiteAccrualStatusService() {
        StudySiteAccrualStatusServiceLocal ssas = mock(StudySiteAccrualStatusServiceLocal.class);
        try {
            when(ssas.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(new StudySiteAccrualStatusDTO());
        } catch (PAException e) {
            //Unreachable
        }
        return ssas;
    }

    /**
     * @return InterventionServiceLocal
     */
    public InterventionServiceLocal getInterventionService() {
        InterventionServiceLocal svc = mock(InterventionServiceLocal.class);
        List<InterventionDTO> results = new ArrayList<InterventionDTO>();
        InterventionDTO dto = new InterventionDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("Test Int. 001"));
        dto.setCtGovTypeCode(CdConverter.convertToCd(InterventionTypeCode.DRUG));
        results.add(dto);

        dto = new InterventionDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setName(StConverter.convertToSt("Test Int. 002"));
        dto.setCtGovTypeCode(CdConverter.convertToCd(InterventionTypeCode.DEVICE));
        results.add(dto);
        try {
            when(svc.search(any(InterventionDTO.class))).thenReturn(results);
            when(svc.get(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return PlannedActivityServiceRemote
     */
    public PlannedActivityServiceLocal getPlannedActivityService() {
        return plannedActivityService;
    }

    /**
     * @return InterventionAlternateNameServiceRemote
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        List<InterventionAlternateNameDTO> results = new ArrayList<InterventionAlternateNameDTO>();
        InterventionAlternateNameDTO dto = new InterventionAlternateNameDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));
        dto.setInterventionIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("altername 1"));
        results.add(dto);

        dto = new InterventionAlternateNameDTO();
        dto.setIdentifier(IiConverter.convertToIi(2L));
        dto.setInterventionIdentifier(IiConverter.convertToIi(1L));
        dto.setName(StConverter.convertToSt("altername 2"));
        results.add(dto);

        InterventionAlternateNameServiceRemote svc = mock(InterventionAlternateNameServiceRemote.class);
        try {
            when(svc.getByIntervention(any(Ii.class))).thenReturn(results);
            when(svc.getByIntervention(any(Ii[].class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }


    /**
     * @return StudyOnholdServiceRemote
     */
    public StudyOnholdServiceLocal getStudyOnholdService() {
        return studyOnholdService;
    }

    /**
     * @return null
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        PAOrganizationServiceRemote svc = mock(PAOrganizationServiceRemote.class);
        try {
            when(svc.getOrganizationByIndetifers(any(Organization.class))).thenAnswer(new Answer<Organization>() {
                public Organization answer(InvocationOnMock invocation) throws Throwable {
                    Object[] args = invocation.getArguments();
                    Organization org = (Organization) args[0];
                    Organization result = new Organization();
                    if (org.getId().equals(1L)) {
                        result.setName("Organization #1");
                    } else if (org.getId().equals(2L)) {
                        result.setName("Organization #2");
                    }
                    return result;
                }
            });
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     *
     * @return PAPersonServiceRemote
     */
    public PAPersonServiceRemote getPAPersonService() {
        return null;
    }

    /**
     * @return RegulatoryInformationServiceRemote
     */
    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        RegulatoryInformationServiceRemote svc = mock(RegulatoryInformationServiceRemote.class);
        try {
            when(svc.getDistinctCountryNames()).thenReturn(new ArrayList<CountryRegAuthorityDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudyResourcingServiceLocal getStudyResoucringService() {
        StudyResourcingServiceLocal svc = mock(StudyResourcingServiceLocal.class);

        StudyResourcingDTO dto = new StudyResourcingDTO();
        dto.setActiveIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setFundingMechanismCode(CdConverter.convertStringToCd("CA"));
        dto.setIdentifier(IiConverter.convertToStudyResourcingIi(1L));
        dto.setInactiveCommentText(StConverter.convertToSt("test"));
        dto.setNciDivisionProgramCode(CdConverter.convertStringToCd(NciDivisionProgramCode.CCR.getCode()));
        dto.setNihInstitutionCode(CdConverter.convertStringToCd("NIH"));
        dto.setSerialNumber(StConverter.convertToSt("1"));
        try {
            when(svc.getStudyResourcingById(any(Ii.class))).thenReturn(dto);
            when(svc.getStudyResourcingByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyResourcingDTO>());
            when(svc.getSummary4ReportedResourcing(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public StudyRegulatoryAuthorityServiceLocal getStudyRegulatoryAuthorityService() {
        return null;
    }

    public LookUpTableServiceRemote getLookUpTableService() {
        return lookUpService;
    }

    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    public DocumentServiceLocal getDocumentService() {
        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.OTHER));
        docDTO.setFileName(StConverter.convertToSt("Protocol_Document.doc"));
        docDTO.setText(EdConverter.convertToEd("test".getBytes()));
        docDTO.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));

        DocumentServiceLocal svc = mock(DocumentServiceLocal.class);
        try {
            when(svc.get(any(Ii.class))).thenReturn(docDTO);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public StudySiteContactServiceLocal getStudySiteContactService() {
        return null;
    }

    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        PaPersonDTO paPersonDTO = new PaPersonDTO();
        paPersonDTO.setFullName("John Investigator");

        PAHealthCareProviderRemote svc = mock(PAHealthCareProviderRemote.class);
        try {
            when(svc.getPersonsByStudySiteId(any(Long.class), any(String.class))).thenReturn(Arrays.asList(paPersonDTO));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public StudyOutcomeMeasureServiceLocal getOutcomeMeasureService() {
        List<StudyOutcomeMeasureDTO> results = new ArrayList<StudyOutcomeMeasureDTO>();

        StudyOutcomeMeasureDTO dto = new StudyOutcomeMeasureDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setName(StConverter.convertToSt("Name"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setSafetyIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setTimeFrame(StConverter.convertToSt("sd"));
        results.add(dto);

        dto = new StudyOutcomeMeasureDTO();
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi("1"));
        dto.setName(StConverter.convertToSt("Name"));
        dto.setIdentifier(IiConverter.convertToIi("2"));
        dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.FALSE));
        dto.setSafetyIndicator(BlConverter.convertToBl(Boolean.TRUE));
        dto.setTimeFrame(StConverter.convertToSt("sd"));
        results.add(dto);

        StudyOutcomeMeasureServiceLocal svc = mock(StudyOutcomeMeasureServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(results);
            when(svc.get(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public ArmServiceLocal getArmService() {
        ArmServiceLocal svc = mock(ArmServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<ArmDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public StudyIndldeServiceLocal getStudyIndldeService() {
        return studyIndIdeService;
    }

    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() {
        CTGovXmlGeneratorServiceRemote svc = mock(CTGovXmlGeneratorServiceRemote.class);
        try {
            when(svc.generateCTGovXml(any(Ii.class))).thenReturn("Test XML");
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public AbstractionCompletionServiceRemote getAbstractionCompletionService() {
        AbstractionCompletionServiceRemote svc = mock(AbstractionCompletionServiceRemote.class);
        try {
            when(svc.validateAbstractionCompletion(any(Ii.class))).thenReturn(new ArrayList<AbstractionCompletionDTO>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    public DocumentWorkflowStatusServiceLocal getDocumentWorkflowStatusService() {
        return null;
    }

    /**
     * @return
     */
    public PDQDiseaseAlternameServiceLocal getDiseaseAlternameService() {
        PDQDiseaseAlternameServiceLocal svc = mock(PDQDiseaseAlternameServiceLocal.class);
        PDQDiseaseAlternameDTO dto = new PDQDiseaseAlternameDTO();
        dto.setAlternateName(StConverter.convertToSt("name1"));
        try {
            when(svc.getByDisease(any(Ii.class))).thenReturn(Arrays.asList(dto));
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return
     */
    public PDQDiseaseParentServiceRemote getDiseaseParentService() {
        PDQDiseaseParentServiceRemote svc = mock(PDQDiseaseParentServiceRemote.class);
        PDQDiseaseParentDTO dto = new PDQDiseaseParentDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setParentDiseaseIdentifier(IiConverter.convertToIi("1"));
        List<PDQDiseaseParentDTO> results = Arrays.asList(dto);

        try {
            when(svc.getByChildDisease(any(Ii.class))).thenReturn(results);
            when(svc.getByChildDisease(any(Ii[].class))).thenReturn(results);
            when(svc.getByParentDisease(any(Ii.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return
     */
    public PDQDiseaseServiceLocal getDiseaseService() {
        PDQDiseaseServiceLocal svc = mock(PDQDiseaseServiceLocal.class);
        PDQDiseaseDTO dto = new PDQDiseaseDTO();
        dto.setDiseaseCode(StConverter.convertToSt("code"));
        dto.setDisplayName(StConverter.convertToSt("disease"));
        dto.setNtTermIdentifier(StConverter.convertToSt("1"));
        dto.setPreferredName(StConverter.convertToSt("disease"));
        dto.setIdentifier(IiConverter.convertToIi("1"));
        try {
            when(svc.search(any(PDQDiseaseDTO.class))).thenReturn(Arrays.asList(dto));
            when(svc.get(any(Ii.class))).thenReturn(dto);
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * @return
     */
    public StudyDiseaseServiceLocal getStudyDiseaseService() {
        return studyDiseaseService;
    }

    /**
     * @return StudyContact
     */
    public StudyContactServiceLocal getStudyContactService() {
        StudyContactServiceLocal svc = mock (StudyContactServiceLocal.class);
        List<StudyContactDTO> results = new ArrayList<StudyContactDTO>();
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(results);
            when(svc.getByStudyProtocol(any(Ii.class), any(List.class))).thenReturn(results);
            when(svc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(results);
            when(svc.search(any(StudyContactDTO.class), any(gov.nih.nci.coppa.services.LimitOffset.class))).thenReturn(results);
        } catch (PAException e) {
            //Unreachable
        } catch (TooManyResultsException e) {
            //Unreachable
        }

        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public StudyMilestoneServicelocal getStudyMilestoneService() {
        return studyMilestoneService;
    }

    public TSRReportGeneratorServiceRemote getTSRReportGeneratorService() {
        return tsrReportGeneratorService;
    }

    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    public MailManagerServiceLocal getMailManagerService() {
        MailManagerServiceLocal svc = mock(MailManagerServiceLocal.class);
        return svc;
    }

    public StudyObjectiveServiceLocal getStudyObjectiveService() {
        StudyObjectiveServiceLocal svc = mock(StudyObjectiveServiceLocal.class);
        try {
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyObjectiveDTO>());
        } catch (PAException e) {
            //unreachable
        }
        return svc;
    }

    /**
     * @return the stratumGroupService
     */
    public StratumGroupServiceLocal getStratumGroupService() {
        return mock(StratumGroupBeanLocal.class);
    }

    /**
     * @return the studyMilestoneTasksService
     */
    public StudyMilestoneTasksServiceLocal getStudyMilestoneTasksService() {
        return mock(StudyMilestoneTasksServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessServiceLocal getStudySiteAccrualAccessService() {
        return studySiteAccrualAccessService;
    }

    public OrganizationCorrelationServiceRemote getOrganizationCorrelationService() {
        return organizationCorrelationService;
    }

    public StudyRecruitmentStatusServiceLocal getStudyRecruitmentStatusService() {
        return mock(StudyRecruitmentStatusServiceLocal.class);
    }
    /**
     * @return the studyInboxService
     */
    public StudyInboxServiceLocal getStudyInboxService() {
        StudyInboxServiceLocal svc = mock(StudyInboxServiceLocal.class);
        return svc;
    }

    public TrialRegistrationServiceLocal getTrialRegistrationService() {
        return mock(TrialRegistrationServiceLocal.class);
    }

    public StudySiteOverallStatusServiceLocal getStudySiteOverallStatusService() {
        StudySiteOverallStatusServiceLocal svc = mock(StudySiteOverallStatusServiceLocal.class);
        return svc;
    }

    public StudyCheckoutServiceLocal getStudyCheckoutService() {
        StudyCheckoutServiceLocal svc = mock(StudyCheckoutServiceLocal.class);
        return svc;
    }

    public PlannedSubstanceAdministrationServiceRemote getPlannedSubstanceAdministrationService() {
        PlannedSubstanceAdministrationServiceRemote svc = mock(PlannedSubstanceAdministrationServiceRemote.class);
        return svc;
    }

    public StudyRelationshipServiceLocal getStudyRelationshipService() {
        return null;
    }

    public StudyProtocolStageServiceLocal getStudyProtocolStageService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public GridAccountServiceRemote getGridAccountService() {
        GridAccountServiceRemote svc = mock(GridAccountServiceRemote.class);
        try {
            when(svc.createGridAccount(any(RegistryUser.class), any(String.class), any(String.class))).thenReturn("Success");
        } catch (PAException e) {
            //Unreachable
        }
        when(svc.doesGridAccountExist(any(String.class))).thenReturn(false);
        when(svc.isValidGridPassword(any(String.class))).thenReturn(true);
        when(svc.getIdentityProviders()).thenReturn(new HashMap<String, String>());

        Map<String, String> results = new HashMap<String, String>();
        results.put(CGMMConstants.CGMM_EMAIL_ID, "test@test.com");
        results.put(CGMMConstants.CGMM_FIRST_NAME, "firstName");
        results.put(CGMMConstants.CGMM_LAST_NAME, "lastName");
        when(svc.authenticateUser(any(String.class), any(String.class), any(String.class))).thenReturn(results);
        when(svc.getFullyQualifiedUsername(any(String.class), any(String.class),
                any(String.class))).thenAnswer(new Answer<String>() {
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        String username = (String) args[0];
                        return "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=" + username;
                    }
                });
        return svc;
    }

    public ProprietaryTrialManagementServiceLocal getProprietaryTrialService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public ParticipatingSiteServiceLocal getParticipatingSiteService() {
        return mock(ParticipatingSiteServiceLocal.class);
    }

    /**
     * {@inheritDoc}
     */
    public PDQXmlGeneratorServiceRemote getPDQXmlGeneratorService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQUpdateGeneratorTaskServiceLocal getPDQUpdateGeneratorTaskService() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQTrialAbstractionServiceBeanRemote getPDQTrialAbstractionServiceRemote() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PDQTrialRegistrationServiceBeanRemote getPDQTrialRegistrationServiceRemote() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setIdentifier(IiConverter.convertToIi(1L));

        PlannedMarkerServiceLocal svc = mock(PlannedMarkerServiceLocal.class);
        try {
            when(svc.getPlannedMarker(any(Ii.class))).thenReturn(dto);
            when(svc.get(any(Ii.class))).thenReturn(dto);
            when(svc.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<PlannedMarkerDTO>());
            when(svc.copy(any(Ii.class), any(Ii.class))).thenReturn(new HashMap<Ii, Ii>());
        } catch (PAException e) {
            //Unreachable
        }
        return svc;
    }

    /**
     * {@inheritDoc}
     */
    public AuditTrailServiceLocal getAuditTrailService() {
        AuditLogRecord markerRecord = new AuditLogRecord(AuditType.INSERT, "PLANNED_MARKER", 1L, "testUser", new Date());
        AuditLogDetail markerDetail = new AuditLogDetail(markerRecord, "name", "", "name");

        AuditLogRecord nciRecord = new AuditLogRecord(AuditType.INSERT, "STUDY_RESOURCING", 1L, "testUser", new Date());
        AuditLogDetail nciDetail = new AuditLogDetail(nciRecord, "programCodeText", "", "programCode");

        AuditTrailServiceLocal svc = mock(AuditTrailServiceLocal.class);

        when(svc.getAuditTrail(eq(StudyResourcing.class), any(Ii.class), any(Date.class), any(Date.class)))
            .thenReturn(Arrays.asList(nciDetail));
        when(svc.getAuditTrailByStudyProtocol(eq(PlannedMarker.class), any(Ii.class), any(Date.class), any(Date.class)))
            .thenReturn(Arrays.asList(markerDetail));
        return svc;
    }

}
