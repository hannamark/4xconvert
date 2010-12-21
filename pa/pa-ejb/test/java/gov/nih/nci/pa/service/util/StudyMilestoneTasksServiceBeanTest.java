/**
 *
 */
package gov.nih.nci.pa.service.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyInboxServiceBean;
import gov.nih.nci.pa.service.StudyIndldeBeanLocal;
import gov.nih.nci.pa.service.StudyMilestoneBeanLocal;
import gov.nih.nci.pa.service.StudyOnholdBeanLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusBeanLocal;
import gov.nih.nci.pa.service.StudyProtocolBeanLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityBeanLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteBeanLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.Arrays;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class StudyMilestoneTasksServiceBeanTest {

    Session sess;
    StudyMilestoneBeanLocal result = new StudyMilestoneBeanLocal();
    StudyMilestoneTasksServiceBean taskBean = new StudyMilestoneTasksServiceBean();

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        TestSchema.primeData();
        sess = HibernateUtil.getCurrentSession();
        taskBean.setSmRemote(result);
        result.setStudyOnholdService(new StudyOnholdBeanLocal());
        result.setStudyInboxService(new StudyInboxServiceBean());
        AbstractionCompletionServiceBean abstractionBean = new AbstractionCompletionServiceBean();
        result.setAbstractionCompletionService(abstractionBean);
        abstractionBean.setStudyProtocolService(new StudyProtocolBeanLocal());
        abstractionBean.setStudySiteService(new StudySiteBeanLocal());
        abstractionBean.setStudyRegulatoryAuthorityService(new StudyRegulatoryAuthorityBeanLocal());
        abstractionBean.setStudyOverallStatusService(new StudyOverallStatusBeanLocal());
        abstractionBean.setStudyIndldeService(new StudyIndldeBeanLocal());
        abstractionBean.setRegulatoryInfoBean(new RegulatoryInformationBean());
        StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = mock(StudyOutcomeMeasureServiceLocal.class);
        abstractionBean.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        StudyOutcomeMeasureDTO outcomeDto = new StudyOutcomeMeasureDTO();
        outcomeDto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
        when(studyOutcomeMeasureService.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(outcomeDto));
        DocumentServiceLocal documentServiceLocal = mock(DocumentServiceLocal.class);
        abstractionBean.setDocumentServiceLocal(documentServiceLocal);
        when(documentServiceLocal.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<DocumentDTO>());
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PlannedActivityServiceLocal plannedActivityService = mock(PlannedActivityServiceLocal.class);
        PlannedActivityDTO plannedDTO = new PlannedActivityDTO();
        plannedDTO.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        plannedDTO.setIdentifier(IiConverter.convertToIi("1"));
        abstractionBean.setPlannedActivityService(plannedActivityService);
        InterventionServiceLocal interventionSvc = mock(InterventionServiceLocal.class);
        abstractionBean.setInterventionSvc(interventionSvc);
        InterventionDTO interventionDto = new InterventionDTO();
        interventionDto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
        when(interventionSvc.get(any(Ii.class))).thenReturn(interventionDto);
        when(plannedActivityService.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(plannedDTO));
        DocumentWorkflowStatusServiceLocal mockDocWrkBean = mock(DocumentWorkflowStatusServiceLocal.class);
        DocumentWorkflowStatusDTO dw = new DocumentWorkflowStatusDTO();
        dw.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.VERIFICATION_PENDING));
        when(mockDocWrkBean.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dw);
        when(paRegSvcLoc.getDocumentWorkflowStatusService()).thenReturn(mockDocWrkBean);
        OrganizationCorrelationServiceRemote orgCorrBean = mock(OrganizationCorrelationServiceRemote.class);
        when(paRegSvcLoc.getOrganizationCorrelationService()).thenReturn(orgCorrBean);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        StudyContactServiceLocal studyContactService = mock(StudyContactServiceLocal.class);
        abstractionBean.setStudyContactService(studyContactService);
        when(studyContactService.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudyContactDTO>());
        StudySiteContactServiceLocal studySiteContactService = mock(StudySiteContactServiceLocal.class);
        abstractionBean.setStudySiteContactService(studySiteContactService);
        when(studySiteContactService.getByStudyProtocol(any(Ii.class))).thenReturn(new ArrayList<StudySiteContactDTO>());
        ArmServiceLocal armService = mock(ArmServiceLocal.class);
        abstractionBean.setArmService(armService);
        ArmDTO armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("arm Name"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.NO_INTERVENTION));
        when(armService.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(armDto));
        abstractionBean.setStudyResourcingService(mock(StudyResourcingServiceLocal.class));
        abstractionBean.setStudyDiseaseService(mock(StudyDiseaseServiceLocal.class));
        StudyRecruitmentStatusServiceLocal mockStudyRecruitBean = mock(StudyRecruitmentStatusServiceLocal.class);
        StudyRecruitmentStatusDTO recruitDto = new StudyRecruitmentStatusDTO();
        recruitDto.setStatusCode(CdConverter.convertToCd(StudyRecruitmentStatusCode.NOT_YET_RECRUITING));
        when(mockStudyRecruitBean.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(recruitDto);
        when(mockStudyRecruitBean.getByStudyProtocol(any(Ii.class))).thenReturn(Arrays.asList(recruitDto));
        abstractionBean.setStudyRecruitmentStatusServiceLocal(mockStudyRecruitBean);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceBean#performTask()}.
     */
    @Test
    public void testPerformTask() throws PAException {
        taskBean.performTask();
    }

}
