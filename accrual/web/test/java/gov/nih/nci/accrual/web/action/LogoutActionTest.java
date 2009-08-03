package gov.nih.nci.accrual.web.action;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class LogoutActionTest extends AbstractAccrualActionTest {

    LogoutAction action;

    @Before
    public void initAction() {
        action = new LogoutAction();
    }

    @Test
    public void inSessionTest() {
        assertEquals(Action.SUCCESS, action.logout());
    }
}
