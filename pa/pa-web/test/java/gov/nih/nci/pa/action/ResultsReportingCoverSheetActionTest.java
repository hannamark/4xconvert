package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

public class ResultsReportingCoverSheetActionTest  extends TrialAbstractActionTest {
  
    
    @Before
    public void setUp() throws PAException {
        
         super.setUp();
        
    }
    
    @Test
    public void testQuery() throws PAException {
        
        beforeQuery();
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
