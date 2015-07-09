package gov.nih.nci.pa.action;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyNotesServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerBeanLocal;
import gov.nih.nci.pa.service.util.MailManagerService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;

@Ignore
public abstract class TrialAbstractActionTest extends AbstractPaActionTest {

    ResultsReportingCoverSheetAction reportingCoverSheetAction =null;
    StudyProtocolDTO studyProtocolDTO;
    TrialViewAction trialViewAction = new TrialViewAction();
    
    @Before
    public void setUp() throws PAException {
        reportingCoverSheetAction = new ResultsReportingCoverSheetAction();
        
      
     
        
        MockCSMUserService mockCSMUserService = new MockCSMUserService();
        CSMUserService.setInstance(mockCSMUserService);
        
         reportingCoverSheetAction.setServletRequest(getRequest());
         trialViewAction.setServletRequest(getRequest());
         
         StudyNotesServiceLocal notesService = mock(StudyNotesServiceLocal.class);
         StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
         PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
         MailManagerService mailManagerService = mock(MailManagerBeanLocal.class);
         
         reportingCoverSheetAction.setStudyProtocolId(1L);
         reportingCoverSheetAction.setStudyNotesService(notesService);
         reportingCoverSheetAction.setStudyProtocolService(studyProtocolService);
         reportingCoverSheetAction.setPaServiceUtil(paServiceUtils);
         reportingCoverSheetAction.setMailManagerService(mailManagerService);
         
         trialViewAction.setStudyProtocolId(1L);
         trialViewAction.setStudyNotesService(notesService);
         trialViewAction.setStudyProtocolService(studyProtocolService);
         trialViewAction.setPaServiceUtil(paServiceUtils);
         trialViewAction.setMailManagerService(mailManagerService);
         
         StudyProtocolDTO studyProtocolDTO = new StudyProtocolDTO();
         studyProtocolDTO.setUseStandardLanguage(BlConverter.convertToBl(false));
         studyProtocolDTO.setDateEnteredInPrs(BlConverter.convertToBl(false));
         studyProtocolDTO.setDesigneeAccessRevoked(BlConverter.convertToBl(false));
         studyProtocolDTO.setDesigneeAccessRevokedDate(TsConverter.convertToTs(new Date()));
         studyProtocolDTO.setChangesInCtrpCtGov(BlConverter.convertToBl(false));
         studyProtocolDTO.setChangesInCtrpCtGovDate(TsConverter.convertToTs(new Date()));
         studyProtocolDTO.setSendToCtGovUpdated(BlConverter.convertToBl(false));
         
         when(studyProtocolService.getStudyProtocol(IiConverter.convertToStudyProtocolIi(1L))).thenReturn(studyProtocolDTO);
      
    }
    
    public void beforeQuery() {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
     
        
    }
    
    
    
}
