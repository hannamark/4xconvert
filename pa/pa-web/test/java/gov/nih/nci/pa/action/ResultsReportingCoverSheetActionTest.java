package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
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
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

public class ResultsReportingCoverSheetActionTest  extends AbstractPaActionTest {

    ResultsReportingCoverSheetAction reportingCoverSheetAction =null;
    StudyProtocolDTO studyProtocolDTO;
    
    @Before
    public void setUp() throws PAException {
        reportingCoverSheetAction = new ResultsReportingCoverSheetAction();
        
      
     
        
        MockCSMUserService mockCSMUserService = new MockCSMUserService();
        CSMUserService.setInstance(mockCSMUserService);
        
         reportingCoverSheetAction.setServletRequest(getRequest());
         
         StudyNotesServiceLocal notesService = mock(StudyNotesServiceLocal.class);
         StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
         PAServiceUtils paServiceUtils = mock(PAServiceUtils.class);
         MailManagerService mailManagerService = mock(MailManagerBeanLocal.class);
         
         reportingCoverSheetAction.setStudyProtocolId(1L);
         reportingCoverSheetAction.setStudyNotesService(notesService);
         reportingCoverSheetAction.setStudyProtocolService(studyProtocolService);
         reportingCoverSheetAction.setPaServiceUtil(paServiceUtils);
         reportingCoverSheetAction.setMailManagerService(mailManagerService);
         
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
    
    @Test
    public void testQuery() throws PAException {
        
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
     
        
        String result = reportingCoverSheetAction.query();
        
        assertEquals("success",result);
        assert(reportingCoverSheetAction.getStudyDataDiscrepancyList().size() ==0);
        
        
     
    }
    
    @Test
    public void testAdd() throws PAException, IOException {
      
        String result= reportingCoverSheetAction.addOrEdit();
        assertEquals(null,result);
        
    }
    
    @Test
    public void testEdit() throws PAException, IOException {
       
        reportingCoverSheetAction.setId(1L);
        String result= reportingCoverSheetAction.addOrEdit();
        assertEquals(null,result);
        
    }
    
    @Test
    public void testAddRecordChange() throws PAException, IOException {
      
        String result= reportingCoverSheetAction.addOrEditRecordChange();
        assertEquals(null,result);
        
    }
    
    @Test
    public void testEditRecordChange() throws PAException, IOException {
       
        reportingCoverSheetAction.setId(1L);
        String result= reportingCoverSheetAction.addOrEditRecordChange();
        assertEquals(null,result);
        
    }
    
    @Test
    public void testSuccessfulAdd() throws PAException, IOException {
        reportingCoverSheetAction.successfulAdd();
        
        assertEquals("Data Discrepancy has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSuccessfulAddRecordChange() throws PAException, IOException {
        reportingCoverSheetAction.successfulAddRecordChange();
        
        assertEquals("Record Change has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testDelete() throws PAException {
        String[] objectsToDelete = new String[1];
        objectsToDelete[0] ="1";
        reportingCoverSheetAction.setObjectsToDelete(objectsToDelete);
        String result = reportingCoverSheetAction.delete();
        assertEquals("success",result);
        assertEquals(Constants.MULTI_DELETE_MESSAGE, ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSaveFinalChanges() throws PAException {
        
      String result =  reportingCoverSheetAction.saveFinalChanges();
      assertEquals("success",result);
      
    }
}
