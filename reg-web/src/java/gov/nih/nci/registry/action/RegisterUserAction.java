/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.mail.MailManager;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.EncoderDecoder;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Bala Nair
 *
 */

@SuppressWarnings({ "PMD" })
public class RegisterUserAction extends ActionSupport {
    
    private static final Logger LOG  = Logger.getLogger(RegisterUserAction.class);
    private RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
    private String userAction;

    /**
     * Send e-mail to the registering user.
     * @return String
     */
    public String sendMail() {

        try {
            // record found, send him an email
            final MailManager mailManager = new MailManager();
            // first validate the form fields before sending e-mail
            validateForm(false);
            if (hasFieldErrors()) {
                return Constants.REGISTER_USER_ERROR;
            }
            mailManager.sendConfirmationMail(
                    registryUserWebDTO.getLoginName(), registryUserWebDTO.getPassword());
            LOG.info(" sending email to " + registryUserWebDTO.getLoginName());
            ServletActionContext.getRequest().
                                setAttribute("emailAddress" , registryUserWebDTO.getLoginName());
            
        } catch (Exception ex) {
            LOG.error("error while sending e-mail");
            return Constants.APPLICATION_ERROR;

        }
        return Constants.CONFIRMATION;

    }
    
    /**
     * activate registry user.
     * @return String
     */
    public String activate() {

        String decodedLoginName = null;
        String decodedPassword  = null;
        try {
            String loginName = ServletActionContext.getRequest().
                                    getParameter("loginName");
            String password = ServletActionContext.getRequest().
                                    getParameter("password");
            
            // decode only if the value is not null, since user can select my account link
            EncoderDecoder encoderDecoder = new gov.nih.nci.registry.util.EncoderDecoder();
            if (loginName != null) {
               decodedLoginName =  encoderDecoder.
                                           decodeString(loginName);
               ServletActionContext.getRequest().
                                       setAttribute("loginName" , decodedLoginName);
               
            }
            if (password != null) {
               decodedPassword =  encoderDecoder.
                                           decodeString(password);
               ServletActionContext.getRequest().
                                      setAttribute("password" , decodedPassword);
    
            }
            
            // check if user already exists
            RegistryUser registryUser = RegistryServiceLocator.getRegistryUserService().
                                            getUser(decodedLoginName);
            
            if (registryUser != null) {
                registryUserWebDTO = new RegistryUserWebDTO(
                                           registryUser, decodedLoginName, decodedPassword);
              } else {
                registryUserWebDTO.setLoginName(decodedLoginName);
                registryUserWebDTO.setPassword(decodedPassword);
                registryUserWebDTO.setRetypePassword(decodedPassword);
                  ServletActionContext.getRequest().setAttribute("isNewUser", true);
            }
        } catch (Exception ex) {
            LOG.error("error while activating user :" + decodedLoginName);
            return Constants.APPLICATION_ERROR;
            
        }        
        // show the My Account page
        return Constants.MY_ACCOUNT;

    }
    
    /**
     * Show My Account Page.
     * @return String
     */
    public String showMyAccount() {
        //check if users accepted the desclaimer if not show one
        String strDesclaimer = (String) ServletActionContext.getRequest().getSession().getAttribute("disclaimer");
        if (strDesclaimer == null || !strDesclaimer.equals("accept")) {
            return "show_Disclaimer_Page";
        }
        String loginName = null;
        try {
            loginName =  ServletActionContext.
                                        getRequest().getRemoteUser();
            // retrieve user info
            RegistryUser registryUser = RegistryServiceLocator.getRegistryUserService().
                                            getUser(loginName);
            User csmUser = CSMUserService.getInstance().getCSMUser(loginName);
            if (registryUser != null && csmUser != null) {
                registryUserWebDTO = new RegistryUserWebDTO(
                                           registryUser, loginName, csmUser.getPassword());
            }
        } catch (Exception ex) {
            LOG.error("error while displaying My Account page for user :" + loginName);
            return Constants.APPLICATION_ERROR;
            
        }
        
        // show the My Account page
        return Constants.MY_ACCOUNT;

    }
    
    /**
     * create/update registry user.
     * @return String
     */
    public String updateAccount() {
        String redirectPage =  null;
        try {
            // first validate the form fields before creating user
            validateForm(true);
            if (hasFieldErrors()) {
                return Constants.MY_ACCOUNT_ERROR;
            }
            
            // convert RegistryUserWebDTO to RegistryUser
            RegistryUser registryUser = new RegistryUser();
            if (registryUserWebDTO.getId() != null 
                    && registryUserWebDTO.getId().length() > 0) {
                        registryUser.setId(new Long(
                                registryUserWebDTO.getId()));
            }
            if (registryUserWebDTO.getCsmUserId() != null
                    && registryUserWebDTO.getCsmUserId().length() > 0) {
                        registryUser.setCsmUserId(new Long(
                                registryUserWebDTO.getCsmUserId()));
            } 
            registryUser.setFirstName(registryUserWebDTO.getFirstName());
            registryUser.setLastName(registryUserWebDTO.getLastName());
            registryUser.setMiddleName(registryUserWebDTO.getMiddleName());
            registryUser.setAddressLine(registryUserWebDTO.getAddressLine());
            registryUser.setCity(registryUserWebDTO.getCity());
            registryUser.setState(registryUserWebDTO.getState());
            registryUser.setPostalCode(registryUserWebDTO.getPostalCode());
            registryUser.setCountry(registryUserWebDTO.getCountry());
            registryUser.setPhone(registryUserWebDTO.getPhone());
            registryUser.setAffiliateOrg(registryUserWebDTO.getAffiliateOrg());    
            if (registryUserWebDTO.getPrsOrgName() != null) {
                registryUser.setPrsOrgName(registryUserWebDTO.getPrsOrgName());
            }
            
            // check if it's  update action
            if (registryUser.getId() != null) {
                String loginName =  ServletActionContext.
                                            getRequest().getRemoteUser();
                if (loginName != null) {
                    redirectPage = "myAccount";
                } else {
                    userAction =  "reset";
                    redirectPage = "redirect_to_login";
                }
                // first update the CSM user
                User csmUser = CSMUserService.getInstance().
                                        updateCSMUser(registryUser, 
                                        registryUserWebDTO.getLoginName(), 
                                        registryUserWebDTO.getPassword());
                
                //now update the RegistryUser
                registryUser =  RegistryServiceLocator.getRegistryUserService().
                                        updateUser(registryUser); 
                
            } else { //create user
                // first create the CSM user
                userAction =  "create";
                redirectPage = "redirect_to_login";
                User csmUser = CSMUserService.getInstance().
                                        createCSMUser(registryUser, 
                                        registryUserWebDTO.getLoginName(), 
                                        registryUserWebDTO.getPassword());
                
                if (csmUser != null) {
                    registryUser.setCsmUserId(csmUser.getUserId());
                }
                
                //now create the RegistryUser
                registryUser =  RegistryServiceLocator.getRegistryUserService().
                                        createUser(registryUser);           
    
            }
            if (ServletActionContext.
                    getRequest().getRemoteUser() != null) {
                ServletActionContext.getRequest().setAttribute(
                                    Constants.SUCCESS_MESSAGE, "Your account was successfully updated");
            }
            
        } catch (Exception ex) {
            LOG.error("error while updating user info");
            return Constants.APPLICATION_ERROR;
            
        }        
        // show the My Account page
        //return Constants.MY_ACCOUNT;
        
        //redirect to the appropriate page
        return redirectPage;

    }
    
    /**
     * @return the registryUserWebDTO
     */
    public RegistryUserWebDTO getRegistryUserWebDTO() {
        return registryUserWebDTO;
    }

    /**
     * @param registryUserWebDTO the registryUserWebDTO to set
     */
    public void setRegistryUserWebDTO(RegistryUserWebDTO registryUserWebDTO) {
        this.registryUserWebDTO = registryUserWebDTO;
    }
    
    
    /**
     * validate the  form elements.
     */
    private void validateForm(boolean isMyAccountPage)  {
        
        if (PAUtil.isEmpty(registryUserWebDTO.getLoginName())) {
            addFieldError("registryUserWebDTO.loginName",
                    getText("error.register.emailAddress"));
        }
        if (PAUtil.isEmpty(registryUserWebDTO.getPassword())) {
            addFieldError("registryUserWebDTO.password",
                    getText("error.register.password"));
        }
        if (PAUtil.isEmpty(registryUserWebDTO.getRetypePassword())) {
            addFieldError("registryUserWebDTO.retypePassword",
                    getText("error.register.retypePassword"));
        }
        if (PAUtil.isNotEmpty(registryUserWebDTO.getPassword()) 
                && PAUtil.isNotEmpty(registryUserWebDTO.getRetypePassword())) {            
            if (!registryUserWebDTO.getPassword().equals(
                        registryUserWebDTO.getRetypePassword())) {
                addFieldError("registryUserWebDTO.retypePassword",
                    getText("error.register.matchPassword"));
            }
        }
        if (PAUtil.isNotEmpty(registryUserWebDTO.getPassword())) {            
            if (registryUserWebDTO.getPassword().length() 
                            < Constants.MIN_PASSWORD_LENGTH) {
                addFieldError("registryUserWebDTO.password",
                    getText("error.register.passwordLength"));
            }
        }
        // check if the login name is a valid e-mail address
        if (PAUtil.isNotEmpty(registryUserWebDTO.getLoginName())) {
            if (!RegistryUtil.isValidEmailAddress(
                    registryUserWebDTO.getLoginName())) {
                addFieldError("registryUserWebDTO.loginName",
                        getText("error.register.invalidEmailAddress"));                
            }            
        }
        
        // if it's My Account page validate required fields
        if (isMyAccountPage) {
            if (PAUtil.isEmpty(registryUserWebDTO.getFirstName())) {
                addFieldError("registryUserWebDTO.firstName",
                        getText("error.register.firstName"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getLastName())) {
                addFieldError("registryUserWebDTO.lastName",
                        getText("error.register.lastName"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getState())) {
                addFieldError("registryUserWebDTO.state",
                        getText("error.register.state"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getCountry())) {
                addFieldError("registryUserWebDTO.country",
                        getText("error.register.country"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getPhone())) {
                addFieldError("registryUserWebDTO.phone",
                        getText("error.register.phone"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getAffiliateOrg())) {
                addFieldError("registryUserWebDTO.affiliateOrg",
                        getText("error.register.affiliateOrg"));
            }
            /*
            if (PAUtil.isNotEmpty(registryUserWebDTO.getPhone())) {
                if (!RegistryUtil.isValidPhoneNumber(registryUserWebDTO.getPhone())) {
                    addFieldError("registryUserWebDTO.phone",
                            getText("error.register.invalidPhoneNumber"));                
                }
            }
            */
            if (PAUtil.isNotEmpty(registryUserWebDTO.getState())
                        && PAUtil.isNotEmpty(registryUserWebDTO.getCountry())) {
                if (registryUserWebDTO.getCountry().equalsIgnoreCase("United States")
                                && registryUserWebDTO.getState().startsWith("None")) {
                    addFieldError("registryUserWebDTO.state",
                            getText("error.register.validState"));
                    
                }
                if (!registryUserWebDTO.getCountry().equalsIgnoreCase("United States")
                        && !registryUserWebDTO.getState().startsWith("None")) {
                             addFieldError("registryUserWebDTO.state",
                             getText("error.register.validNonUSState"));
            
                }
                
            }
        }
    }

    /**
     * @return the userAction
     */
    public String getUserAction() {
        return userAction;
    }

    /**
     * @param userAction the userAction to set
     */
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

}
