package gov.nih.nci.pa.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.pa.util.Constants;

/**
 * 
 * @author gnaveh
 * 
 */
 @Validation
 /**
  * Performs authentication services.  
  * Handles CSM authentication, using the CSM tables with encrypted passwords.
  */
public class LoginAction extends ActionSupport {      
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

   // private static final Logger LOG = Logger.getLogger(LoginAction.class);
     
    private String userName = null;

    private String password = null;
   
/**
 * 
 * @return tag if user name was not submitted
 */
    @RequiredStringValidator(message = Constants.USERNAME_REQ_ERROR)
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param value tag
     */
    public void setUserName(String value) {
        userName = value;
    }

    /**
     * 
     * @return tag
     */
    @RequiredStringValidator(message = Constants.PASSWORD_REQ_ERROR)

    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param value tag
     */
    public void setPassword(String value) {
        password = value;
    }


    /**
     * @throws Exception if invoking the method throws an exception.
     * @return error
     */
    public String execute() throws java.lang.Exception {  
   //need to replace with a method login(getUserName(),getPassword()) 
   //that checks with CSM if user exists and has Permission

     if (performAuthentication(getUserName(), getPassword())) {
         // if login success full , create an entry in session
         Map session = ActionContext.getContext().getSession();
         session.put(Constants.LOGGED_USER_NAME, userName);
       return SUCCESS;
     } else {
       addActionError("Invalid user name or password! Please try again!");
       return ERROR;
     }     
    }

    /**
     * 
     * @param checkUserName login user name
     * @param checkPassword password
     * @param checkApplicationContextName name of the application pa used for unit test
     * @return boolean
     */

    public boolean login(String checkUserName, String checkPassword, String checkApplicationContextName) {
        try {                    
            AuthenticationManager authenticationManager = 
               SecurityServiceProvider.getAuthenticationManager(checkApplicationContextName);
            return authenticationManager.login(checkUserName, checkPassword);          
          } catch (Exception cse) {
             addActionError("CSException is: " + cse);
             return false;
          }
    }
   
/**
 * 
 * @param checkUserName userName
 * @param checkPassword password
 * @param checkApplicationContextName application name (PA)
 * @return
 */
  private boolean performAuthentication(String checkUserName, String checkPassword) {
  

   boolean isUserAuthenticated = false;
   try {
       final AuthenticationManager authenticationManager = SecurityServiceProvider.getAuthenticationManager("pa");
       isUserAuthenticated = authenticationManager.login(checkUserName, checkPassword);
 
       return isUserAuthenticated;
   } catch (CSConfigurationException e) {
       //addActionError("\tUser is not authenticated. \n Reason: " + e.getMessage());
     return false;
   } catch (CSException e) {
       //addActionError("\tUser is not authenticated. \n Reason: " + e.getMessage());
      return false;
   }
 }

} 