package gov.nih.nci.accrual.web.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.web.util.AccrualConstants;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class WelcomeActionTest extends AbstractAccrualActionTest {

    private static String AR_CTRO = "ctroWelcome";
    private static String AR_SUBMITTING_SITES = "publicWelcome";

    WelcomeAction action;

    @Before
    public void initAction() {
        action = new WelcomeAction();
    }

    @Test
    public void reportViewerRoleTest() {
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setUserInRole(AccrualConstants.ROLE_REPORTING, true);
        assertEquals(AR_CTRO, action.execute());
        assertEquals(AccrualConstants.ROLE_CTRO, ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE));
    }

    @Test
    public void abstractorRoleTest() {
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setUserInRole(AccrualConstants.ROLE_CTRO, true);
        assertEquals(AR_CTRO, action.execute());
        assertEquals(AccrualConstants.ROLE_CTRO, ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE));
    }

    @Test
    public void submitterRoleTest() {
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setUserInRole(AccrualConstants.ROLE_PUBLIC, true);
        assertEquals(AR_SUBMITTING_SITES, action.execute());
        assertEquals(AccrualConstants.ROLE_PUBLIC, ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE));
    }

    @Test
    public void nullRoleTest() {
        assertEquals(null, action.execute());
    }

}
