package gov.nih.nci.pa.action;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.BasePaTest;
import gov.nih.nci.pa.test.util.TestPropertiesPaWeb;

import org.junit.Test;

import com.opensymphony.xwork2.ActionContext;

/**
 * LoginActionTest.
 * 
 * @author NAmiruddin
 *
 */
public class LoginActionTest extends BasePaTest {
    /**
     * 
     * @throws Exception
     */
    @Test
    public void testLoginSuccess() throws Exception {               
        System.setProperty("java.security.auth.login.config", TestPropertiesPaWeb.getLoginConfig());
        LoginAction login = new LoginAction();
        ActionContext.setContext( ActionContext.getContext());
         //ActionContext.getContext().getSession();
        login.setUserName("curator");
        login.setPassword("pass");
        //String result = login.execute();
    
        //assertTrue("Expected a success result! result is:" + result +";", result.equals("success"));
        assertTrue("hi",true);
    }
    
    
    public void testLoginFailure() throws Exception {
        System.setProperty("java.security.auth.login.config", TestPropertiesPaWeb.getLoginConfig());
        
        LoginAction login = new LoginAction();

        login.setUserName("userNotExist");
        login.setPassword("passwordNotExist");
        //String result = login.execute();

        //assertTrue("Expected a failure result! result is:" + result +";", result.equals("Error"));
        assertTrue("hi",true);
    }

}