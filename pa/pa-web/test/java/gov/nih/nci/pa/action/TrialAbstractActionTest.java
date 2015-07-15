package gov.nih.nci.pa.action;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyNotesServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.MailManagerBeanLocal;
import gov.nih.nci.pa.service.util.MailManagerService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;

@Ignore
public abstract class TrialAbstractActionTest extends AbstractPaActionTest {

    ResultsReportingCoverSheetAction reportingCoverSheetAction =null;
    StudyProtocolDTO studyProtocolDTO;
    TrialViewAction trialViewAction = new TrialViewAction();
    ServiceLocator paRegSvcLoc = null;
    TrialDocumentWebDTO trialDocumentWebDTO;
    
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
         DocumentServiceLocal docService = mock(DocumentServiceLocal.class);
         RegistryUserServiceLocal registryUserServiceLocal = mock(RegistryUserServiceLocal.class);
         paRegSvcLoc = mock(ServiceLocator.class);
         
         reportingCoverSheetAction.setStudyProtocolId(1L);
         reportingCoverSheetAction.setStudyNotesService(notesService);
         reportingCoverSheetAction.setStudyProtocolService(studyProtocolService);
         reportingCoverSheetAction.setPaServiceUtil(paServiceUtils);
         reportingCoverSheetAction.setMailManagerService(mailManagerService);
         
         List<DocumentDTO> isoList = new ArrayList<DocumentDTO>();
         DocumentDTO dto = new DocumentDTO();
         dto.setIdentifier(IiConverter.convertToIi(1L));
         dto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.AFTER_RESULTS));
         dto.setFileName(StConverter.convertToSt("FileName"));
         dto.setDateLastCreated(TsConverter.convertToTs(new Date()));
         dto.setStudyProtocolIdentifier(IiConverter.convertToIi(1L));
         dto.setUserLastUpdated(StConverter.convertToSt("User"));
         isoList.add(dto);
         
         trialViewAction.setStudyProtocolId(1L);
         trialViewAction.setStudyNotesService(notesService);
         trialViewAction.setStudyProtocolService(studyProtocolService);
         trialViewAction.setPaServiceUtil(paServiceUtils);
         trialViewAction.setMailManagerService(mailManagerService);
         trialViewAction.setServletRequest(getRequest());
         
         trialDocumentWebDTO = new TrialDocumentWebDTO();
         trialDocumentWebDTO.setTypeCode("");
         trialDocumentWebDTO.setFileName("");
         trialViewAction.setTrialDocumentWebDTO(trialDocumentWebDTO);
         
         when(docService.getReportsDocumentsByStudyProtocol(IiConverter.convertToStudyProtocolIi(1L))).thenReturn(isoList);
         when(paRegSvcLoc.getDocumentService()).thenReturn(docService);
         when(paRegSvcLoc.getRegistryUserService()).thenReturn(registryUserServiceLocal);
         
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
       
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        
        
    }
    
    
    
}
