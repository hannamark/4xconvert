/**
 *
 */
package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.web.dto.util.UserAccountWebDTO;
import gov.nih.nci.accrual.web.mail.MailManager;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.accrual.web.util.EncoderDecoder;
import gov.nih.nci.accrual.web.util.PaServiceLocator;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Lisa Kelley
 *
 */
@SuppressWarnings({ "PMD" })
public class UserAccountAction extends AbstractAccrualAction {

    private static final long serialVersionUID = 1L;
    private UserAccountWebDTO userAccount = new UserAccountWebDTO();
    private String userAction = "";
    /** Create. */
    public static final String CREATE = "create";

    /**
     * @return success
     */
    @Override
    public String execute() {
        return SUCCESS;
    }

    /**
     * Send e-mail to the registering user.
     * @return String
     */
    public String request() {
        UserAccountWebDTO.validate(userAccount, this, false);
        if (hasFieldErrors()) {
            return ActionSupport.INPUT;
        }

        try {
            final MailManager mailManager = new MailManager();
            mailManager.sendConfirmationMail(userAccount.getLoginName(), userAccount.getPassword());
            LOG.info(" sending email to " + userAccount.getLoginName());
        } catch (Exception e) {
            LOG.error("error while sending e-mail");
            return ERROR;
        }

        return "requestConfirmation";
    }

    /**
     * activate user.
     * @return String
     */
    public String activate() {
        String encodedLoginName = ServletActionContext.getRequest().getParameter("loginName");
        String encodedPassword  = ServletActionContext.getRequest().getParameter("password");
        String loginName = null;
        String password = null;

        if (encodedLoginName != null) {
            loginName = EncoderDecoder.decodeString(encodedLoginName);
        }
        if (encodedPassword != null) {
            password = EncoderDecoder.decodeString(encodedPassword);
        }

        RegistryUser user = null;
        try {
            user = PaServiceLocator.getInstance().getRegistryUserService().getUser(loginName);
        } catch (Exception e) {
            LOG.error("error while activating user :" + loginName);
            return ERROR;
        }

        if (user == null) { // new user
            userAccount.setLoginName(loginName);
            userAccount.setPassword(password);
        } else { // user already exists
            userAccount = new UserAccountWebDTO(user, loginName, password);
        }

        return CREATE;
    }

    /**
     * create/update user.
     * @return String
     */
    public String create() {
        UserAccountWebDTO.validate(userAccount, this, true);
        if (hasFieldErrors()) {
            return ActionSupport.INPUT;
        }

        String actionResult =  null;
        try {
            RegistryUser user = new RegistryUser();
            if (PAUtil.isNotEmpty(userAccount.getId())) {
                user.setId(Long.valueOf(userAccount.getId()));
            }
            if (PAUtil.isNotEmpty(userAccount.getCsmUserId())) {
                user.setCsmUserId(Long.valueOf(userAccount.getCsmUserId()));
            }
            user.setFirstName(userAccount.getFirstName());
            user.setLastName(userAccount.getLastName());
            user.setMiddleName(userAccount.getMiddleName());
            user.setAddressLine(userAccount.getAddress());
            user.setCity(userAccount.getCity());
            user.setState(userAccount.getState());
            user.setPostalCode(userAccount.getZipCode());
            user.setCountry(userAccount.getCountry());
            user.setPhone(userAccount.getPhoneNumber());
            user.setAffiliateOrg(userAccount.getOrganization());
            user.setPrsOrgName(userAccount.getPrsOrganization());

            if (PAUtil.isEmpty(userAccount.getId())) {
                // create the CSM user
                User csmUser = AccrualCsmUtil.getInstance().
                    createCSMUser(user, userAccount.getLoginName(), userAccount.getPassword());

                if (csmUser != null) {
                    user.setCsmUserId(csmUser.getUserId());
                }

                // create the user
                PaServiceLocator.getInstance().getRegistryUserService().createUser(user);

                userAction = "createAccount";
                actionResult = "redirectToLogin";
            } else {
                // update the CSM user
                AccrualCsmUtil.getInstance().updateCSMUser(user, userAccount.getLoginName(), userAccount.getPassword());

                // update the user
                PaServiceLocator.getInstance().getRegistryUserService().updateUser(user);

                if (ServletActionContext.getRequest().getRemoteUser() == null) {
                    userAction = "resetPassword";
                    actionResult = "redirectToLogin";
                } else {
                    userAction = "updateAccount";
                    actionResult = CREATE;
                }
            }
        } catch (Exception e) {
            LOG.error("error while updating user info");
            actionResult = ERROR;
        }

        return actionResult;
    }

    /**
     * Show My Account Page.
     * @return String
     */
    public String updateAccount() {
        //check if users accepted the disclaimer if not show one
        String disclaimer = (String) ServletActionContext.getRequest().getSession().
            getAttribute(AccrualConstants.SESSION_ATTR_DISCLAIMER);
        if (disclaimer == null || !disclaimer.equals(AccrualConstants.DISCLAIMER_ACCEPTED)) {
            return AccrualConstants.AR_DISCLAIMER;
        }

        String loginName = ServletActionContext.getRequest().getRemoteUser();
        try {
            // retrieve user info
            RegistryUser user = PaServiceLocator.getInstance().getRegistryUserService().getUser(loginName);
            User csmUser = AccrualCsmUtil.getInstance().getCSMUser(loginName);
            if (user != null && csmUser != null) {
                userAccount = new UserAccountWebDTO(user, loginName, csmUser.getPassword());
            }
        } catch (Exception e) {
            LOG.error("error while displaying My Account page for user :" + loginName);
            return ERROR;
        }

        return CREATE;

    }

    /**
     * @return the userAccount
     */
    public UserAccountWebDTO getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(UserAccountWebDTO userAccount) {
        this.userAccount = userAccount;
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