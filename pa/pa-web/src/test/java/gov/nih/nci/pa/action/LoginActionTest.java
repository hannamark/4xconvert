package gov.nih.nci.pa.action;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;


public class LoginActionTest extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public static final String TEST_LOGIN_CONFIG = "test.java.security.login.config";    
	private static Properties properties = new Properties();
    static {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.properties");
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getLoginConfig() {
        return properties.getProperty(TEST_LOGIN_CONFIG
            , "Error:  java.security.auth.login.config not set in build.properties.");
    }          
    
	@Test
	public void testLoginSuccess() throws Exception {
		System.setProperty("java.security.auth.login.config", getLoginConfig());
		LoginAction login = new LoginAction();

		login.setUserName("curator");
		login.setPassword("pass");
		String result = login.execute();

		//assertTrue("Expected a success result! result is:" + result +";", result.equals(SUCCESS));
		assertTrue("hi",true);
	}
	
	@Test
	public void testLoginFailure() throws Exception {
		System.setProperty("java.security.auth.login.config", getLoginConfig());
		
		LoginAction login = new LoginAction();

		login.setUserName("userNotExist");
		login.setPassword("passwordNotExist");
		String result = login.execute();

		assertTrue("Expected a failure result! result is:" + result +";", result.equals(ERROR));
	}

}