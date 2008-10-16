/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.Constants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        spDTO = new StudyProtocolQueryDTO(); 
        spDTO.setStudyProtocolId(1L);
        HttpServletRequest httpReq = ServletActionContext.getRequest();
        HttpSession httpSess = httpReq.getSession();
        httpSess.setAttribute(Constants.TRIAL_SUMMARY, spDTO);
        
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
        testAction.history();
        List<StudyOverallStatusWebDTO> rslt = testAction.getOverallStatusList();
        assertEquals(2, rslt.size());
        assertEquals(StudyStatusCode.APPROVED.getDisplayName(), rslt.get(0).getStatusCode());
        assertEquals("01/01/2008", rslt.get(0).getStatusDate());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName(), rslt.get(1).getStatusCode());
        assertEquals("02/01/2008", rslt.get(1).getStatusDate());
    }   
}
