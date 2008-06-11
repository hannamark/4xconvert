package gov.nih.nci.pa.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
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
public class AnnotationAction extends ActionSupport {

      
    private String username = null;

    private String password = null;

/**
 * 
 * @return tag if user name was not submitted
 */
    @RequiredStringValidator(message = "User Name is required field")

    public String getUsername() {

        return username;
    }

    /**
     * 
     * @param value tag
     */
    public void setUsername(String value) {

        username = value;
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
   //need to replace with a method login(getUsername(),getPassword()) 
   //that checks with CSM if user exists and has Permission
      if (!getUsername().equals("Admin") || !getPassword().equals("Admin")) {
            addActionError("Invalid user name or password! Please try again!");
           
        return ERROR;
    
      } else {
        return SUCCESS;
      }
  
    }
    
    
 
} 