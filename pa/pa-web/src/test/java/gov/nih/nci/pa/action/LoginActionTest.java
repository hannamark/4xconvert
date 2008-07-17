package gov.nih.nci.pa.action;

import gov.nih.nci.pa.test.util.TestPropertiesPaWeb;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;


public class LoginActionTest extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	
	
	
	@Test
	public void testLoginSuccess() throws Exception {		        
		System.setProperty("java.security.auth.login.config", TestPropertiesPaWeb.getLoginConfig());
		LoginAction login = new LoginAction();

		login.setUserName("curator");
		login.setPassword("pass");
		String result = login.execute();

		assertTrue("Expected a success result! result is:" + result +";", result.equals(SUCCESS));
		//assertTrue("hi",true);
	}
	
	@Test
	public void testLoginFailure() throws Exception {
		System.setProperty("java.security.auth.login.config", TestPropertiesPaWeb.getLoginConfig());
		
		LoginAction login = new LoginAction();

		login.setUserName("userNotExist");
		login.setPassword("passwordNotExist");
		String result = login.execute();

		assertTrue("Expected a failure result! result is:" + result +";", result.equals(ERROR));
	}

}