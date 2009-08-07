package gov.nih.nci.accrual.web.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.web.util.AccrualConstants;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class WelcomeActionTest extends AbstractAccrualActionTest {

    private static String AR_LOGOUT = "logout";
    private static String AR_ACCRUAL_ADMIN = "adminWelcome";
    private static String AR_SUBMITTING_SITES = "publicWelcome";

    WelcomeAction action;

    @Before
    public void initAction() {
        action = new WelcomeAction();
    }

    @Test
    public void administratorRoleTest() {
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setUserInRole(AccrualConstants.ROLE_ADMINISTRATOR, true);
        assertEquals(AR_ACCRUAL_ADMIN, action.execute());
        assertEquals(AccrualConstants.ROLE_ADMINISTRATOR, ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE));
    }

    @Test
    public void submitterRoleTest() {
        ((MockHttpServletRequest) ServletActionContext.getRequest()).setUserInRole(AccrualConstants.ROLE_PUBLIC, true);
        assertEquals(AR_SUBMITTING_SITES, action.execute());
        assertEquals(AccrualConstants.ROLE_PUBLIC, ServletActionContext.getRequest().getSession().getAttribute(AccrualConstants.SESSION_ATTR_ROLE));
    }

    @Test
    public void nullRoleTest() {
        assertEquals(AR_LOGOUT, action.execute());
    }

}
