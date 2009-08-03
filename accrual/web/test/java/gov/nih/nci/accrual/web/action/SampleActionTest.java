package gov.nih.nci.accrual.web.action;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class SampleActionTest extends AbstractAccrualActionTest {

    SampleAction action;

    @Before
    public void initAction() {
        action = new SampleAction();
    }

    @Test
    public void executeTest() {
        // user selects type of report
        assertEquals(Action.SUCCESS, action.execute());
    }

    @Test
    public void getResultTest() {
        // user selects type of report
        assertEquals(Action.SUCCESS, action.execute());

        // user enters value
        action.setEnteredValue("2");

        // user presses "Get square" button
        assertEquals(Action.SUCCESS, action.getResult());
        assertEquals("4", action.getResultValue());
    }
}
