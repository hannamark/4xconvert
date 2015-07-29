/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.services.grid.util.CaGridFormAuthenticatorValve;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyServiceLocal;
import gov.nih.nci.pa.service.util.GridAccountServiceBean;
import gov.nih.nci.pa.service.util.GridAccountServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerService;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.SAMLToAttributeMapper;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.dto.UserWebDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.EncoderDecoder;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Register User Action.
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength", 
                    "PMD.TooManyMethods", "PMD.TooManyFields", "PMD.ExcessiveMethodLength" })
public class RegisterUserAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1359534429821398453L;

    private static final Logger LOG  = Logger.getLogger(RegisterUserAction.class);
    
    private GridAccountServiceRemote gridAccountService;
    private OrganizationCorrelationServiceRemote organizationCorrelationService;
    private OrganizationEntityServiceRemote organizationEntityService;
    private RegistryUserService registryUserService;
    private FamilyServiceLocal familyService;

    
    private RegistryUserWebDTO registryUserWebDTO = new RegistryUserWebDTO();
    private UserWebDTO userWebDTO = new UserWebDTO();
    private Map<String, String> identityProviders;
    private String selectedIdentityProvider;
    private MailManagerService mailManagerService;
    private static final Properties REG_PROPERTIES = PaEarPropertyReader.getProperties();

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        gridAccountService = PaRegistry.getGridAccountService();
        organizationCorrelationService = PaRegistry.getOrganizationCorrelationService();
        organizationEntityService = PoRegistry.getOrganizationEntityService();
        registryUserService = PaRegistry.getRegistryUserService();
        familyService = PaRegistry.getFamilyService();
        mailManagerService = PaRegistry.getMailManagerService();
    }

    /**
     * Connects RegistryUser and CSM records.
     * @return string.
     */
    public String activate() {
        if (StringUtils.isEmpty(registryUserWebDTO.getEmailAddress())
                || StringUtils.isEmpty(userWebDTO.getUsername())) {
            addActionMessage("Invalid call of activate");
        } else {
            String email = new EncoderDecoder().decodeString(registryUserWebDTO.getEmailAddress());
            try {
                registryUserService.activateAccount(email, userWebDTO.getUsername());
                addActionMessage("Your account was successfully activated");
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        return "activation";
    }
    
    
    /**
     * Create Account Action.
     * @return redirect.
     */
    public String createAccount() {
        validateMyAccountForm();
        if (hasFieldErrors()) {
            if (registryUserWebDTO.getAffiliatedOrganizationId() != null) {
                loadAdminUsers(registryUserWebDTO.getAffiliatedOrganizationId());
            }
           return Constants.CREATE_ACCOUNT;
        }
        try {
            createPaOrgAsNeeded();
        } catch (Exception e) {
            LOG.error("Unable to create PO Org.", e);
            return Constants.APPLICATION_ERROR;
        }
        try {
            RegistryUser registryUser = getRegistryUser();
           
            if (StringUtils.isEmpty(userWebDTO.getUsername())) {
                createUserWithNoGridAccount();
            } else {
                createUserWithExistingGridAccount(registryUser);
            }

            registryUserService.createUser(registryUser);
        } catch (Exception e) {
            LOG.error("error while creating user info", e);
            return Constants.APPLICATION_ERROR;
        }
        return "confirmation";
    }
    
    private RegistryUser getRegistryUser() throws IllegalAccessException, InvocationTargetException {
        RegistryUser registryUser = new RegistryUser();
        BeanUtils.copyProperties(registryUser, registryUserWebDTO);
        registryUser.setId(null);
        registryUser.setCsmUser(null);
        registryUser.setSiteAccrualSubmitter(false);
        registryUser.setFamilyAccrualSubmitter(false);
        Calendar curCalendar = Calendar.getInstance();
        registryUser.setDateLastCreated(curCalendar.getTime());
        if (registryUserWebDTO.isRequestAdminAccess()) {
            registryUser.setAffiliatedOrgUserType(UserOrgType.PENDING_ADMIN);
        } else {
            registryUser.setAffiliatedOrgUserType(UserOrgType.MEMBER);
        }
        return registryUser;
    }
    
    private void createUserWithNoGridAccount() {
       
        EncoderDecoder encodeDecoder = new EncoderDecoder();

        String[] params = {registryUserWebDTO.getFirstName(), registryUserWebDTO.getLastName(),
                registryUserWebDTO.getAffiliateOrg(), registryUserWebDTO.getPhone(), 
                registryUserWebDTO.getEmailAddress(),
                REG_PROPERTIES.getProperty("register.mail.body.url") + "?registryUserWebDTO.emailAddress="
                + encodeDecoder.encodeString(registryUserWebDTO.getEmailAddress())
                + "&userWebDTO.username=INSERTUSERNAME"
        };
       mailManagerService.sendNewUserRequestEmail(params);
       String mailTo = registryUserWebDTO.getEmailAddress();
       String[] userParams = {registryUserWebDTO.getFirstName(), registryUserWebDTO.getLastName()};
        LOG.info("Sending email to " + registryUserWebDTO.getEmailAddress());
        mailManagerService.sendPleaseWaitEmail(mailTo, userParams);
        addActionMessage(getText("login.message.waitForAppSupport"));
    }

    private void createUserWithExistingGridAccount(RegistryUser registryUser) throws PAException {
        CSMUserUtil csmUserService = CSMUserService.getInstance();
        User csmUser = csmUserService.getCSMUser(userWebDTO.getUsername());
        if (csmUser == null) {
            csmUser = csmUserService.createCSMUser(registryUser, userWebDTO.getUsername(), null);
        } else {
            CSMUserService.getInstance().updateCSMUser(registryUser, userWebDTO.getUsername(), null);
        }
        csmUserService.assignUserToGroup(csmUser.getLoginName(), PaEarPropertyReader.getCSMSubmitterGroup());
        registryUser.setCsmUser(csmUser);
        gridAccountService.addGridUserToGroup(userWebDTO.getUsername(), 
                                              GridAccountServiceBean.GRIDGROUPER_SUBMITTER_GROUP);
        addActionMessage(getText("login.message.account.created"));
    }
    
    /**
     * Forward to existing account page.
     * @return success
     */
    public String existingGridAccount() {
        selectedIdentityProvider = getIdentityProviders().size() != 1 ? null
                : getIdentityProviders().values().iterator().next();
        return Constants.EXISTING_GRID_ACCOUNT;
    }
    
    /**
     * @return s
     */
    public String loadAdminUsers() {
        ServletActionContext.getRequest().getSession().removeAttribute("adminUsers");
        if (registryUserWebDTO.getAffiliatedOrganizationId() != 0) {
            loadAdminUsers(registryUserWebDTO.getAffiliatedOrganizationId());
        }
        return "loadAdminList";
    }
    
    /**
     * creates an account from an existing grid account.
     * @return the forward
     */
    public String registerExistingGridAccount() {
        Map<String, String> userInfo = gridAccountService.authenticateUser(userWebDTO.getUsername(),
                    userWebDTO.getPassword(), getSelectedIdentityProvider());
        String fullyQualifiedUsername = gridAccountService.getFullyQualifiedUsername(userWebDTO.getUsername(),
                userWebDTO.getPassword(), getSelectedIdentityProvider());
        if (userInfo.isEmpty()) {
            addActionError(getText("errors.password.mismatch"));
            return Constants.EXISTING_GRID_ACCOUNT;
        } else if (registryUserService.doesRegistryUserExist(fullyQualifiedUsername)) {
            addActionMessage(getText("login.message.account.exists"));
            return "confirmation";
        }
        ServletActionContext.getRequest().getSession().setAttribute("selectedIdentityProvider"
              , getSelectedIdentityProvider());
        registryUserWebDTO.setEmailAddress(userInfo.get(SAMLToAttributeMapper.EMAIL));
        registryUserWebDTO.setFirstName(userInfo.get(SAMLToAttributeMapper.FIRST_NAME));
        registryUserWebDTO.setLastName(userInfo.get(SAMLToAttributeMapper.LAST_NAME));
        userWebDTO.setUsername(fullyQualifiedUsername);
        return Constants.CREATE_ACCOUNT;
    }
    
    /**
     * request creation of a registry user.
     * @return String
     */
    public String requestAccount() {
        RegistryUser registryUser = null;
        User csmUser = null;
        String email = registryUserWebDTO.getEmailAddress();
        try {
            registryUser = registryUserService.getUser(email);

            if (registryUser != null) {
                csmUser = registryUser.getCsmUser();
            }

        } catch (Exception ex) {
            LOG.error("Error Retrieving User by Email :" + email);
            return Constants.APPLICATION_ERROR;
        }

        if (registryUser != null) {
            userWebDTO.setUsername(csmUser == null ? "" : csmUser.getLoginName());
            registryUserWebDTO = new RegistryUserWebDTO(registryUser);
            if (registryUser.getAffiliatedOrganizationId() != null) {
                loadAdminUsers(registryUser.getAffiliatedOrganizationId());
            }
        }

        return Constants.CREATE_ACCOUNT;
    }

    /**
     * Show My Account Page.
     * @return String
     */
    public String showMyAccount() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String loginName = request.getRemoteUser();
        RegistryUser registryUser = null;
        try {
            // retrieve user info
            registryUser = registryUserService.getUser(loginName);
        } catch (Exception ex) {
            LOG.error("error while displaying My Account page for user :" + loginName);
            return Constants.APPLICATION_ERROR;
        }
        if (registryUser != null && registryUser.getCsmUser() != null) {
            userWebDTO.setUsername(loginName);
            request.setAttribute("userName", CsmUserUtil.getGridIdentityUsername(loginName));
            registryUserWebDTO = new RegistryUserWebDTO(registryUser);
            if (isLoadAdminUsersScenario(registryUser)) {
                loadAdminUsers(registryUser.getAffiliatedOrganizationId());
            }
        }
        return Constants.MY_ACCOUNT;
    }

    private boolean isLoadAdminUsersScenario(RegistryUser registryUser) {
        return registryUser.getAffiliatedOrganizationId() != null
        && registryUser.getAffiliatedOrgUserType() != null
        && !(registryUser.getAffiliatedOrgUserType().equals(UserOrgType.PENDING_ADMIN)
                || registryUser.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN));
    }

    /**
     * create/update registry user.
     * @return String
     */
    public String updateAccount() {
        validateMyAccountForm();
        if (hasFieldErrors()) {
             if (registryUserWebDTO.getAffiliatedOrganizationId() != null) {
                 loadAdminUsers(registryUserWebDTO.getAffiliatedOrganizationId());
             }
             String loginName =  ServletActionContext.getRequest().getRemoteUser();
             ServletActionContext.getRequest().setAttribute("userName", 
                    CsmUserUtil.getGridIdentityUsername(loginName));
            return Constants.MY_ACCOUNT_ERROR;
        }
        try {
            createPaOrgAsNeeded();
        } catch (Exception e) {
            LOG.error("Unable to create PO Org.", e);
            return Constants.APPLICATION_ERROR;
        }
        // convert RegistryUserWebDTO to RegistryUser
        RegistryUser registryUser = null;
        UserOrgType currentUserOrgType = null;
        boolean affiliatedOrgUpdated = false;
        try {
            registryUser = registryUserService.getUserById(registryUserWebDTO.getId());
            if (registryUser != null) {
                currentUserOrgType = registryUser.getAffiliatedOrgUserType();
                affiliatedOrgUpdated = !registryUserWebDTO.getAffiliatedOrganizationId()
                    .equals(registryUser.getAffiliatedOrganizationId());
            }
        } catch (PAException e) {
            LOG.error("Error retrieving CSM User with Id = " + registryUserWebDTO.getId().toString(), e);
            return Constants.APPLICATION_ERROR;
        }

        try {
            if (registryUser == null) {
                registryUser = new RegistryUser();
            }
            BeanUtils.copyProperties(registryUser, registryUserWebDTO);
        } catch (Exception e) {
           LOG.error("ERROR COPYING PROPERTIES.", e);
           return Constants.APPLICATION_ERROR;
        }
        if (affiliatedOrgUpdated) {
            try {
                familyService.unassignAllAccrualAccess(registryUser, registryUser);
                registryUserService.removeAllOwnership(registryUser, 
                        FamilyHelper.getAllRelatedOrgs(registryUser.getAffiliatedOrganizationId()));
                ServletActionContext.getRequest().getSession().removeAttribute("isSiteAdmin");
            } catch (PAException e) {
                LOG.error("ERROR REMOVING ACCRUAL ACCESS.", e);
            }
        }
        if (registryUserWebDTO.isRequestAdminAccess()) {
            registryUser.setAffiliatedOrgUserType(UserOrgType.PENDING_ADMIN);
        } else {
            if (affiliatedOrgUpdated) {
                registryUser.setAffiliatedOrgUserType(UserOrgType.MEMBER);
            } else {
                registryUser.setAffiliatedOrgUserType(currentUserOrgType);
            }
        }
        try {
            String redirectPage = updateExistingUser(registryUser);
            addActionMessage("Your account was successfully updated");
            return affiliatedOrgUpdated ? "logout" : redirectPage;
        } catch (Exception e) {
            LOG.error("error while updating user info", e);
            return Constants.APPLICATION_ERROR;
        }
    }
    
    private String updateExistingUser(RegistryUser registryUser) throws PAException {
        String redirectPage;
        String loginName =  ServletActionContext.getRequest().getRemoteUser();
        if (loginName != null) {
            redirectPage = "myAccount";
        } else {
            addActionMessage(getText("login.message.reset"));
            redirectPage = "confirmation";
        }
        String userName = userWebDTO.getUsername();
        if (userName == null) {
            userName = registryUser.getCsmUser().getLoginName();
            if (userName == null) {
                throw new PAException("No user to update has been specified.");
            }
        }
        CSMUserService.getInstance().updateCSMUser(registryUser, userName, null);
        registryUserService.updateUser(registryUser);
        ServletActionContext.getRequest().setAttribute("userName", CsmUserUtil.getGridIdentityUsername(loginName));
        return redirectPage;
    }
    
    /**
     * Send e-mail to the registering user.
     * @return String
     */
    public String verifyEmail() {
        validateEmailEntry();
        if (hasFieldErrors()) {
            return Constants.REGISTER_USER_ERROR;
        }
        return requestAccount();
    }

    /**
     * @return s
     */
    public String viewAdminUsers() {
        ServletActionContext.getRequest().getSession().removeAttribute("adminUsers");
        if (registryUserWebDTO.getAffiliatedOrganizationId() != 0) {
            loadAdminUsers(registryUserWebDTO.getAffiliatedOrganizationId());
        }
        return "viewAdminUser";
    }

    private void createPaOrgAsNeeded() throws NullifiedEntityException, PAException {
        if (registryUserWebDTO.getAffiliatedOrganizationId() != null) {
            OrganizationDTO poOrgDTO =
                    organizationEntityService.getOrganization(IiConverter.convertToPoOrganizationIi(registryUserWebDTO
                        .getAffiliatedOrganizationId().toString()));
            if (poOrgDTO != null) {
                organizationCorrelationService.createPAOrganizationUsingPO(poOrgDTO);
            }
        }
    }

    private void validateEmailEntry() {
        String email = registryUserWebDTO.getEmailAddress();
        if (StringUtils.isEmpty(email)) {
            addFieldError("registryUserWebDTO.emailAddress", getText("error.register.emailAddress"));
        } else {
            if (!RegistryUtil.isValidEmailAddress(email)) {
                addFieldError("registryUserWebDTO.emailAddress", getText("error.register.invalidEmailAddress"));
            }

            List<RegistryUser> result = new ArrayList<RegistryUser>();
            try {
                RegistryUser registryUser = new RegistryUser();
                registryUser.setEmailAddress(email);
                result = registryUserService.search(registryUser);
            } catch (PAException e) {
                LOG.error("Unable to retrieve user by email: " + email);
            }
            if (!result.isEmpty()) {
                addFieldError("registryUserWebDTO.emailAddress", getText("error.register.emailAlreadyExists"));
            }
        }
    }

    private void validateMyAccountForm() {
        ClassValidator<RegistryUserWebDTO> validator = new ClassValidator<RegistryUserWebDTO>(RegistryUserWebDTO.class);
        for (InvalidValue invalidValue : validator.getInvalidValues(registryUserWebDTO)) {
            addFieldError("registryUserWebDTO." + invalidValue.getPropertyName(), getText(invalidValue.getMessage()
                .trim()));
        }
        if (registryUserWebDTO.getAffiliatedOrganizationId() == null) {
            registryUserWebDTO.setAffiliateOrg("");
            addFieldError("registryUserWebDTO.affiliateOrg", getText("error.register.affiliateOrg"));
        }
        if (StringUtils.isNotEmpty(registryUserWebDTO.getState())
                && StringUtils.isNotEmpty(registryUserWebDTO.getCountry())) {
            validateAddress();
        }
    }

    private void validateAddress() {
        if (registryUserWebDTO.getCountry().equalsIgnoreCase("United States")
                && registryUserWebDTO.getState().startsWith("None")) {
            addFieldError("registryUserWebDTO.state", getText("error.register.validState"));

        }
        if (!registryUserWebDTO.getCountry().equalsIgnoreCase("United States")
                && !registryUserWebDTO.getState().startsWith("None")) {
            addFieldError("registryUserWebDTO.state", getText("error.register.validNonUSState"));

        }
    }

    private void loadAdminUsers(Long affilOrgId) {
        RegistryUser criteriaUser = new RegistryUser();
        criteriaUser.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        criteriaUser.setAffiliatedOrganizationId(affilOrgId);
        try {
             List<RegistryUser> adminUsers = registryUserService.search(criteriaUser);
             ServletActionContext.getRequest().getSession().setAttribute("adminUsers", adminUsers);
             ServletActionContext.getRequest().setAttribute("orgSelected", affilOrgId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
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
     * getter for user web dto.
     * @return the user web dto.
     */
    public UserWebDTO getUserWebDTO() {
        return userWebDTO;
    }

    /**
     * setter for userWebDTO.
     * @param userWebDTO the userWebDTO
     */
    public void setUserWebDTO(UserWebDTO userWebDTO) {
        this.userWebDTO = userWebDTO;
    }

    /**
     * @return the identity providers
     */
    public Map<String, String> getIdentityProviders() {
        if (identityProviders == null) {
            identityProviders = CaGridFormAuthenticatorValve.getAuthSourceMap();
        }
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
     * @param gridAccountService the gridAccountService to set
     */
    public void setGridAccountService(GridAccountServiceRemote gridAccountService) {
        this.gridAccountService = gridAccountService;
    }



    /**
     * @param organizationCorrelationService the organizationCorrelationService to set
     */
    public void setOrganizationCorrelationService(OrganizationCorrelationServiceRemote organizationCorrelationService) {
        this.organizationCorrelationService = organizationCorrelationService;
    }



    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserService registryUserService) {
        this.registryUserService = registryUserService;
    }

}
