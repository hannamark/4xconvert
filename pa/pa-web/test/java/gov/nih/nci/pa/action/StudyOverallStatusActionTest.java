/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusActionTest extends AbstractPaActionTest {
    private StudyOverallStatusAction testAction;

    @Test
    public void testDisplayStatus() throws Exception {
        StudyProtocolQueryDTO spDTO = new StudyProtocolQueryDTO(); 
        spDTO.setStudyProtocolId(1L);
        HttpServletRequest httpReq = ServletActionContext.getRequest();
        HttpSession httpSess = httpReq.getSession();
        httpSess.setAttribute(Constants.TRIAL_SUMMARY, spDTO);
        
        testAction = new StudyOverallStatusAction();
        testAction.prepare();
        testAction.execute();
        assertEquals(ActualAnticipatedTypeCode.ACTUAL.getCode(), testAction.getStartDateType());
        assertEquals("01/01/2000" , testAction.getStartDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), testAction.getCompletionDateType());
        assertEquals("04/15/2010" , testAction.getCompletionDate());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(), testAction.getCurrentTrialStatus());
        assertEquals("02/01/2008", testAction.getStatusDate());
    }
    
    @Test
    public void testDisplayHistory() {
        assertTrue(true);
    }
    
    @Test
    public void updateStatus() {
        assertTrue(true);
    }
}
