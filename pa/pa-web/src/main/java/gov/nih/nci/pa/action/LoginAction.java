package gov.nih.nci.pa.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.AuthenticationManager;

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

    private String userName = null;

    private String password = null;

/**
 * 
 * @return tag if user name was not submitted
 */
    @RequiredStringValidator(message = "User Name is required field")
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
    @RequiredStringValidator(message = "Password is required field")

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
      if (!getUserName().equals("admin") || !getPassword().equals("admin")) {
            addActionError("Invalid user name or password! Please try again!");           
        return ERROR;    
      } else {
        return SUCCESS;
      }
    }
 
    /*
     if (login(getUserName(), getPassword())) {
       return SUCCESS;
     } else {
       addActionError("Invalid user name or password! Please try again!");
       return ERROR;
     }     
    }
    */
    
    /**
     * 
     * @param checkUserName tag
     * @param checkPassword tag
     * @return tag
     */
    public Boolean login(String checkUserName, String checkPassword) {
    
        try {
            AuthenticationManager authenticationManager = SecurityServiceProvider.getAuthenticationManager("pa");
            return authenticationManager.login(checkUserName, checkPassword);          
          } catch (Exception cse) {
             //LOG.info("CSException is: " + cse);
             addActionError("CSException is: " + cse);
             return false;
          }
    }
    
 
} 