/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.GridAccountServiceBean;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.mail.MailManager;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.EncoderDecoder;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.cgmm.constants.CGMMConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Bala Nair
 *
 */
@SuppressWarnings({ "PMD" })
public class RegisterUserAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG  = Logger.getLogger(RegisterUserAction.class);
    private RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
    private Map<String, String> identityProviders = PaRegistry.getGridAccountService().getIdentityProviders();
    private String userAction;
    private String selectedIdentityProvider;
    private static final int MIN_UN = 4;
    private static final int MAX_UN = 15;
    

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
        mailManager.sendConfirmationMail(registryUserWebDTO.getEmailAddress());
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

        String emailAddress = ServletActionContext.getRequest().getParameter("emailAddress");

        // decode only if the value is not null, since user can select my account link
        EncoderDecoder encoderDecoder = new gov.nih.nci.registry.util.EncoderDecoder();
        if (emailAddress != null) {
            decodedEmailAddress =  encoderDecoder.decodeString(emailAddress);
            ServletActionContext.getRequest().setAttribute("emailAddress" , decodedEmailAddress);
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
                    csmUser == null ? "" : csmUser.getPassword());
            if (registryUser.getAffiliatedOrganizationId() != null) {
                loadAdminUsers(registryUser.getAffiliatedOrganizationId());
            }
        } else {
            registryUserWebDTO.setEmailAddress(decodedEmailAddress);
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
            if (registryUser.getAffiliatedOrganizationId() != null
                 && registryUser.getAffiliatedOrgUserType() != null 
                 && !(registryUser.getAffiliatedOrgUserType().equals(UserOrgType.PENDING_ADMIN)
                         || registryUser.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN))) {
                loadAdminUsers(registryUser.getAffiliatedOrganizationId());
            }
            
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
        validateForm(true, registryUserWebDTO.getId() != null);
        
        if (hasFieldErrors()) {
             if (registryUserWebDTO.getAffiliatedOrganizationId() != null) {
                 loadAdminUsers(registryUserWebDTO.getAffiliatedOrganizationId());
             }
            return Constants.MY_ACCOUNT_ERROR;
        }

        // convert RegistryUserWebDTO to RegistryUser
        RegistryUser registryUser = new RegistryUser();
        try {
            BeanUtils.copyProperties(registryUser, registryUserWebDTO);
        } catch (Exception e) {
           LOG.error("ERROR COPYING PROPERTIES.", e);
           return Constants.APPLICATION_ERROR;
        }
        if (registryUserWebDTO.isRequestAdminAccess()) {
            registryUser.setAffiliatedOrgUserType(UserOrgType.PENDING_ADMIN);
        } else {
            registryUser.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        }
        // check if it's  update action
        if (registryUser.getId() != null && registryUser.getId() != 0) {
            String loginName =  ServletActionContext.getRequest().getRemoteUser();
            if (loginName != null) {
                redirectPage = "myAccount";
            } else {
                userAction =  "reset";
                redirectPage = "redirect_to_login";
            }

            try {                
                if (!registryUserWebDTO.getOldPassword().equals(registryUserWebDTO.getPassword())) {
                    // updating the grid password programmatically
                    PaRegistry.getGridAccountService().changePassword(registryUserWebDTO.getUsername(), 
                            registryUserWebDTO.getOldPassword(), registryUserWebDTO.getPassword());
                }
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
            registryUser.setId(null);
            // first create the CSM user
            userAction =  "create";
            redirectPage = "redirect_to_login";
            try {
                GridAccountServiceRemote gridService = PaRegistry.getGridAccountService();
                //First create the grid account if one doesn't already 
                if (!registryUserWebDTO.isHasExistingGridAccount()) {
                    String results = gridService.createGridAccount(registryUser, registryUserWebDTO.getUsername(), 
                            registryUserWebDTO.getPassword());
                    LOG.debug("Grid User Creation Results: " + results);
                } 
                
                //Then the csm user account being sure to retrieve the long form grid username
                String username = gridService.getFullyQualifiedUsername(registryUserWebDTO.getUsername(), 
                        registryUserWebDTO.getPassword(), GridAccountServiceBean.GRID_URL);
                User csmUser = CSMUserService.getInstance().createCSMUser(registryUser, username, 
                        registryUserWebDTO.getPassword());

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
     * Forward to existing account page. 
     * @return success
     */
    public String existingGridAccount() {
        //Setting the default idp here
        selectedIdentityProvider = identityProviders.size() != 1 ? null : identityProviders.values().iterator().next();
        return Constants.EXISTING_GRID_ACCOUNT;
    }
    
    /**
     * creates an account from an existing grid account.
     * @return the forward
     */
    public String registerExistingGridAccount() {
        GridAccountServiceRemote gridService = PaRegistry.getGridAccountService();
        Map<String, String> userInfo = gridService.authenticateUser(registryUserWebDTO.getUsername(), 
                    registryUserWebDTO.getPassword(), getSelectedIdentityProvider());
        
        if (userInfo.isEmpty()) {
            addActionError(getText("errors.password.mismatch"));
            return Constants.EXISTING_GRID_ACCOUNT;
        }
        
        registryUserWebDTO.setRetypePassword(registryUserWebDTO.getPassword());
        registryUserWebDTO.setEmailAddress(userInfo.get(CGMMConstants.CGMM_EMAIL_ID));
        registryUserWebDTO.setFirstName(userInfo.get(CGMMConstants.CGMM_FIRST_NAME));
        registryUserWebDTO.setLastName(userInfo.get(CGMMConstants.CGMM_LAST_NAME));
        registryUserWebDTO.setHasExistingGridAccount(true);
        return Constants.MY_ACCOUNT;
    }
    
    /**
     * validate the  form elements.
     */
    private void validateForm(boolean isMyAccountPage, boolean isAccountEdit)  {     
        Map<String, String> addFieldError = new HashMap<String, String>(); 
        InvalidValue[] invalidValues = null;
        
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
            ClassValidator<RegistryUserWebDTO> classValidator = new ClassValidator(registryUserWebDTO.getClass());
            invalidValues = classValidator.getInvalidValues(registryUserWebDTO);
            for (int i = 0; i < invalidValues.length; i++) {
                addFieldError.put("registryUserWebDTO." + invalidValues[i].getPropertyName(), 
                        getText(invalidValues[i].getMessage().trim()));
            }
            addErrors(addFieldError); 
            if (!isAccountEdit && PAUtil.isNotEmpty(registryUserWebDTO.getUsername())) {
               if (registryUserWebDTO.getUsername().length() < MIN_UN 
                   || registryUserWebDTO.getUsername().length() > MAX_UN) {
                   addFieldError("registryUserWebDTO.username", getText("error.register.usernameLength"));
               }    
            }
            if (isAccountEdit && registryUserWebDTO.getAffiliatedOrganizationId() == null) {
                   registryUserWebDTO.setAffiliateOrg(""); 
                   addFieldError("registryUserWebDTO.affiliateOrg", getText("error.register.affiliateOrg"));
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
            
            if (PAUtil.isNotEmpty(registryUserWebDTO.getUsername()) && !isAccountEdit 
                    && !registryUserWebDTO.isHasExistingGridAccount()
                    && PaRegistry.getGridAccountService().doesGridAccountExist(registryUserWebDTO.getUsername())) {
                addFieldError("registryUserWebDTO.username", getText("error.register.usernameTaken"));
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
    
    private void addErrors(Map<String, String> err) {
        if (!err.isEmpty()) {
            for (String msg : err.keySet()) {
                addFieldError(msg, err.get(msg));
            }
        }
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
/**
     * @return the identity providers
     */
    public Map<String, String> getIdentityProviders() {
        return identityProviders;
    }
    
    /**
     * @param identityProviders the identity providers to set
     */
    public void setIdentityProviders(Map<String, String> identityProviders) {
        this.identityProviders = identityProviders;
    }
    
    /**
     * @return the selected identity provider
     */
    public String getSelectedIdentityProvider() {
        return selectedIdentityProvider;
    }
    
    /**
     * @param selectedIdentityProvider the selected identity provider to set
     */
    public void setSelectedIdentityProvider(String selectedIdentityProvider) {
        this.selectedIdentityProvider = selectedIdentityProvider;
    }
    /**
     * @return s
     */
    public String loadAdminUsers() {
        String affOrgId = ServletActionContext.getRequest().getParameter("affiliatedOrgId");
        ServletActionContext.getRequest().getSession().removeAttribute("adminUsers");
        if (StringUtils.isNotEmpty(affOrgId)) {
            loadAdminUsers(Long.parseLong(affOrgId));
        }
        return "viewAdminUser";
    }
    private void loadAdminUsers(Long affOrgId) {
        RegistryUser criteriaUser = new RegistryUser();
        criteriaUser.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        criteriaUser.setAffiliatedOrganizationId(affOrgId);
        try {
             List<RegistryUser> adminUsers = PaRegistry.getRegisterUserService().search(criteriaUser);
             ServletActionContext.getRequest().getSession().setAttribute("adminUsers", adminUsers);
             ServletActionContext.getRequest().setAttribute("orgSelected", affOrgId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
        }
    }
}
