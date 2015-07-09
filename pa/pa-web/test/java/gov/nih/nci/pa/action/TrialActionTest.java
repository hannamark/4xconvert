package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

public class TrialActionTest extends TrialAbstractActionTest {

    @Before
    public void setUp() throws PAException {
        
         super.setUp();
        
    }
    
    @Test
    public void testQuery() throws PAException {
        
        beforeQuery();
        String result = trialViewAction.query();
        
        assertEquals("success",result);
        assert(trialViewAction.getStudyDataDiscrepancyList().size() ==0);
        
     
    }
    
    @Test
    public void testAdd() throws PAException, IOException {
      
        String result= trialViewAction.addOrEdit();
        assertEquals(null,result);
        assertEquals(null,trialViewAction.getStudyDataDiscrepancyList());
        
        
    }
    
    @Test
    public void testEdit() throws PAException, IOException {
       
        trialViewAction.setId(1L);
        String result= trialViewAction.addOrEdit();
        assertEquals(null,result);
        assertEquals(null,trialViewAction.getStudyDataDiscrepancyList());
        
    }
    
    @Test
    public void testAddRecordChange() throws PAException, IOException {
      
        String result= trialViewAction.addOrEditRecordChange();
        assertEquals(null,result);
        assertEquals(null,trialViewAction.getStudyRecordChangeList());
        
    }
    
    @Test
    public void testEditRecordChange() throws PAException, IOException {
       
        trialViewAction.setId(1L);
        String result= trialViewAction.addOrEditRecordChange();
        assertEquals(null,result);
        assertEquals(null,trialViewAction.getStudyRecordChangeList());
        
    }
    
    @Test
    public void testSuccessfulAdd() throws PAException, IOException {
        trialViewAction.successfulAdd();
        
        assertEquals("Data Discrepancy has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSuccessfulAddRecordChange() throws PAException, IOException {
        trialViewAction.successfulAddRecordChange();
        
        assertEquals("Record Change has been added successfully", ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testDelete() throws PAException {
        String[] objectsToDelete = new String[1];
        objectsToDelete[0] ="1";
        trialViewAction.setObjectsToDelete(objectsToDelete);
        String result = trialViewAction.delete();
        assertEquals("success",result);
        assertEquals(Constants.MULTI_DELETE_MESSAGE, ServletActionContext.getRequest().getAttribute(
                Constants.SUCCESS_MESSAGE));
        
    }
    
    @Test
    public void testSaveFinalChanges() throws PAException {
        
      String result =  trialViewAction.saveFinalChanges();
      assertEquals("success",result);
      
    }
    
}
