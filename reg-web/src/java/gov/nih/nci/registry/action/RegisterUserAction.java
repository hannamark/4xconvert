/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.mail.MailManager;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.EncoderDecoder;

import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.apache.commons.lang.StringUtils;
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
        // record found, send him an email
        final MailManager mailManager = new MailManager();
        // first validate the form fields before sending e-mail
        validateForm(false, false);
        if (hasFieldErrors()) {
            return Constants.REGISTER_USER_ERROR;
        }
        mailManager.sendConfirmationMail(registryUserWebDTO.getEmailAddress(), registryUserWebDTO.getPassword());
        LOG.info("Sending email to " + registryUserWebDTO.getEmailAddress());
        ServletActionContext.getRequest().setAttribute("emailAddress" , registryUserWebDTO.getEmailAddress());
        return Constants.CONFIRMATION;
    }

    /**
     * activate registry user.
     * @return String
     */
    public String activate() {
        String decodedEmailAddress = null;
        String decodedPassword  = null;

        String emailAddress = ServletActionContext.getRequest().getParameter("emailAddress");
        String password = ServletActionContext.getRequest().getParameter("password");

        // decode only if the value is not null, since user can select my account link
        EncoderDecoder encoderDecoder = new gov.nih.nci.registry.util.EncoderDecoder();
        if (emailAddress != null) {
            decodedEmailAddress =  encoderDecoder.decodeString(emailAddress);
            ServletActionContext.getRequest().setAttribute("emailAddress" , decodedEmailAddress);
        }

        if (password != null) {
            decodedPassword =  encoderDecoder.decodeString(password);
            ServletActionContext.getRequest().setAttribute("password" , decodedPassword);
        }
        
        RegistryUser registryUser = null;
        User csmUser = null;
        try {
            // check if user already exists
            registryUser = PaRegistry.getRegisterUserService().getUser(decodedEmailAddress);
            
            if (registryUser != null && registryUser.getCsmUserId() != null) {
                csmUser = CSMUserService.getInstance().getCSMUserById(registryUser.getCsmUserId());
            }
            
        } catch (Exception ex) {
            LOG.error("error while activating user :" + decodedEmailAddress);
            return Constants.APPLICATION_ERROR;
        }
        
        if (registryUser != null) {
            registryUserWebDTO = new RegistryUserWebDTO(registryUser, csmUser == null ? "" : csmUser.getLoginName(), 
                    decodedPassword);
        } else {
            registryUserWebDTO.setEmailAddress(decodedEmailAddress);
            registryUserWebDTO.setPassword(decodedPassword);
            registryUserWebDTO.setRetypePassword(decodedPassword);
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
        RegistryUser registryUser = null;
        User csmUser = null;

        try {
            loginName =  ServletActionContext.getRequest().getRemoteUser();
            // retrieve user info
            registryUser = PaRegistry.getRegisterUserService().getUser(loginName);
            csmUser = CSMUserService.getInstance().getCSMUser(loginName);
        } catch (Exception ex) {
            LOG.error("error while displaying My Account page for user :" + loginName);
            return Constants.APPLICATION_ERROR;   
        }
        
        if (registryUser != null && csmUser != null) {
            registryUserWebDTO = new RegistryUserWebDTO(registryUser, loginName, csmUser.getPassword());
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

        // first validate the form fields before creating user
        validateForm(true, StringUtils.isNotEmpty(registryUserWebDTO.getId()));
        
        if (hasFieldErrors()) {
            return Constants.MY_ACCOUNT_ERROR;
        }

        // convert RegistryUserWebDTO to RegistryUser
        RegistryUser registryUser = new RegistryUser();
        if (StringUtils.isNotEmpty(registryUserWebDTO.getId())) {
            registryUser.setId(Long.valueOf(registryUserWebDTO.getId()));
        }
        
        if (StringUtils.isNotEmpty(registryUserWebDTO.getCsmUserId())) {
            registryUser.setCsmUserId(Long.valueOf(registryUserWebDTO.getCsmUserId()));
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
        registryUser.setPrsOrgName(registryUserWebDTO.getPrsOrgName());
        registryUser.setEmailAddress(registryUserWebDTO.getEmailAddress());
        
        if (StringUtils.isNotEmpty(registryUserWebDTO.getTreatmentSiteId())) {
            registryUser.setPoOrganizationId(Long.valueOf(registryUserWebDTO.getTreatmentSiteId()));
        }

        if (StringUtils.isNotEmpty(registryUserWebDTO.getPhysicianId())) {
            registryUser.setPoPersonId(Long.valueOf(registryUserWebDTO.getPhysicianId()));
        }

        // check if it's  update action
        if (registryUser.getId() != null) {
            String loginName =  ServletActionContext.getRequest().getRemoteUser();
            if (loginName != null) {
                redirectPage = "myAccount";
            } else {
                userAction =  "reset";
                redirectPage = "redirect_to_login";
            }

            try {
                // first update the CSM user
                CSMUserService.getInstance().updateCSMUser(registryUser, registryUserWebDTO.getUsername(), 
                        registryUserWebDTO.getPassword());

                //now update the RegistryUser
                PaRegistry.getRegisterUserService().updateUser(registryUser); 
            } catch (Exception e) {
                LOG.error("error while updating user info");
                return Constants.APPLICATION_ERROR;
            }
        } else { //create user
            // first create the CSM user
            userAction =  "create";
            redirectPage = "redirect_to_login";
            try {
                
                //First create the grid account.
                String results = PaRegistry.getGridAccountService().createGridAccount(registryUser, 
                        registryUserWebDTO.getUsername(), registryUserWebDTO.getPassword());
                LOG.debug("Grid User Creation Results: " + results);
                    
                
                //Then the csm user account
                User csmUser = CSMUserService.getInstance().createCSMUser(registryUser, 
                        registryUserWebDTO.getUsername(), registryUserWebDTO.getPassword());

                if (csmUser != null) {
                    registryUser.setCsmUserId(csmUser.getUserId());
                }

                //now create the RegistryUser
                registryUser =  PaRegistry.getRegisterUserService().createUser(registryUser);
            } catch (Exception e) {
                LOG.error("error while creating user info");
                return Constants.APPLICATION_ERROR;            
            }
        }
            
        if (ServletActionContext.getRequest().getRemoteUser() != null) {
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, 
                    "Your account was successfully updated");
        }

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
    private void validateForm(boolean isMyAccountPage, boolean isAccountEdit)  {
        if (PAUtil.isEmpty(registryUserWebDTO.getPassword())) {
            addFieldError("registryUserWebDTO.password", getText("error.register.password"));
        }
        
        if (PAUtil.isEmpty(registryUserWebDTO.getRetypePassword())) {
            addFieldError("registryUserWebDTO.retypePassword", getText("error.register.retypePassword"));
        }
        
        if (PAUtil.isNotEmpty(registryUserWebDTO.getPassword()) 
                && PAUtil.isNotEmpty(registryUserWebDTO.getRetypePassword())
                && !registryUserWebDTO.getPassword().equals(registryUserWebDTO.getRetypePassword())) {            
            addFieldError("registryUserWebDTO.retypePassword", getText("error.register.matchPassword"));
        }
        
        if (PAUtil.isNotEmpty(registryUserWebDTO.getPassword()) 
                && !PaRegistry.getGridAccountService().isValidGridPassword(registryUserWebDTO.getPassword())) {
            addFieldError("registryUserWebDTO.password", getText("error.register.invalidPassword"));
        }
        
        if (PAUtil.isEmpty(registryUserWebDTO.getEmailAddress())) {
            addFieldError("registryUserWebDTO.emailAddress", getText("error.register.emailAddress"));
        }

        // check if the login name is a valid e-mail address
        if (PAUtil.isNotEmpty(registryUserWebDTO.getEmailAddress()) 
                && !RegistryUtil.isValidEmailAddress(registryUserWebDTO.getEmailAddress())) {
            addFieldError("registryUserWebDTO.emailAddress", getText("error.register.invalidEmailAddress"));
        }
        
        // if it's My Account page validate required fields
        if (isMyAccountPage) {
            
            if (PAUtil.isEmpty(registryUserWebDTO.getUsername())) {
                addFieldError("registryUserWebDTO.username", getText("error.register.username"));
            }
            
            if (PAUtil.isNotEmpty(registryUserWebDTO.getUsername()) && !isAccountEdit
                    && PaRegistry.getGridAccountService().doesGridAccountExist(registryUserWebDTO.getUsername())) {
                addFieldError("registryUserWebDTO.username", getText("error.register.usernameTaken"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getFirstName())) {
                addFieldError("registryUserWebDTO.firstName", getText("error.register.firstName"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getLastName())) {
                addFieldError("registryUserWebDTO.lastName", getText("error.register.lastName"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getAddressLine())) {
                addFieldError("registryUserWebDTO.addressLine", getText("error.register.streetAddress"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getCity())) {
                addFieldError("registryUserWebDTO.city", getText("error.register.city"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getState())) {
                addFieldError("registryUserWebDTO.state", getText("error.register.state"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getCountry())) {
                addFieldError("registryUserWebDTO.country", getText("error.register.country"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getPostalCode())) {
                addFieldError("registryUserWebDTO.postalCode", getText("error.register.zipCode"));
            }
            
            if (PAUtil.isEmpty(registryUserWebDTO.getPhone())) {
                addFieldError("registryUserWebDTO.phone", getText("error.register.phone"));
            }
            if (PAUtil.isEmpty(registryUserWebDTO.getAffiliateOrg())) {
                addFieldError("registryUserWebDTO.affiliateOrg",
                        getText("error.register.affiliateOrg"));
            }
            
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
