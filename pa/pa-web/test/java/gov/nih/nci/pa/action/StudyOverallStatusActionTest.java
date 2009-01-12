/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.Constants;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusActionTest extends AbstractPaActionTest {
    private StudyOverallStatusAction testAction;
    StudyProtocolQueryDTO spDTO;

    @Before 
    public void setupStudyOverallStatusActionTests() throws Exception {
        testAction = new StudyOverallStatusAction();
        testAction.prepare();
    }

    @Test
    public void testDisplayStatus() throws Exception {
        testAction.execute();
        assertEquals(ActualAnticipatedTypeCode.ACTUAL.getCode(), testAction.getStartDateType());
        assertEquals("01/01/2000" , testAction.getStartDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), testAction.getCompletionDateType());
        assertEquals("04/15/2010" , testAction.getCompletionDate());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(), testAction.getCurrentTrialStatus());
        assertEquals("02/01/2008", testAction.getStatusDate());
    }
    
    @Test
    public void testDisplayHistory() throws Exception {
        testAction.execute();
        testAction.historypopup();
        List<StudyOverallStatusWebDTO> rslt = testAction.getOverallStatusList();
        assertEquals(2, rslt.size());
        assertEquals(StudyStatusCode.APPROVED.getDisplayName(), rslt.get(0).getStatusCode());
        assertEquals("01/01/2008", rslt.get(0).getStatusDate());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName(), rslt.get(1).getStatusCode());
        assertEquals("02/01/2008", rslt.get(1).getStatusDate());
    }
    
    @Test
    public void testTransitionToAdministrativelyComplete() throws Exception {
        String tracker18328 = "Current Trial Status Date and Primary Completion Date must be the same when " +
                "Current Trial Status is 'Administratively Complete'.";
        testAction.execute();
        
        // completion date != status date
        testAction.setCurrentTrialStatus(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());
        testAction.setStatusDate("3/1/2008");
        testAction.setStatusReason("statusReason");
        testAction.setCompletionDate("3/2/2008");
        testAction.setCompletionDateType("Actual");
        testAction.update();
        assertNull(ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
        assertTrue(testAction.getActionErrors().contains(tracker18328));
        
        // anticipated completion date 
        testAction.setCompletionDate("3/1/2008");
        testAction.setCompletionDateType("Anticipated");
        testAction.update();
        assertNull(ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
        
        // successful update
        testAction.setCompletionDateType("Actual");
        testAction.update();
        assertEquals(Constants.UPDATE_MESSAGE, ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
    }
}
