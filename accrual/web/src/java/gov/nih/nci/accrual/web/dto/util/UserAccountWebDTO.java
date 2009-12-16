/**
 * 
 */
package gov.nih.nci.accrual.web.dto.util;

import gov.nih.nci.accrual.dto.UserDto;
import gov.nih.nci.accrual.web.action.AbstractAccrualAction;
import gov.nih.nci.accrual.web.util.PaServiceLocator;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.USStateCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bala Nair
 *
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.CyclomaticComplexity" })
public class UserAccountWebDTO {

    private String  id;
    private String  loginName;
    private String  password;
    private String  retypePassword;
    private String  firstName;
    private String  middleName;
    private String  lastName;
    private String  address;
    private String  city;
    private String  state;
    private String  zipCode;
    private String  country;
    private String  phoneNumber;
    private String  organization;
    private String  prsOrganization;
    private String  treatmentSiteId;
    private String  treatmentSite;
    private String  physicianId;
    private String  physician;
    private static final String USER_ACCOUNT = "userAccount";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern ONE_NON_ALPHA_NUMERIC = Pattern.compile("(.*\\p{Punct}.*)+");
    private static final Pattern ONE_DIGIT = Pattern.compile("(.*\\p{Digit}.*)+");
    
    /**
     *  Default Constructor.
     */
    public UserAccountWebDTO() {
        super();
    }
    
    /**
     * 
     * @param user user
     * @param treatmentSite treatmentSite
     * @param physician physician
     */
    public UserAccountWebDTO(UserDto user, String treatmentSite, String physician) {
        this.id              = IiConverter.convertToString(user.getIdentifier());
        this.loginName       = StConverter.convertToString(user.getLoginName());
        this.password        = StConverter.convertToString(user.getPassword());
        this.retypePassword  = password;
        this.firstName       = StConverter.convertToString(user.getFirstName());
        this.middleName      = StConverter.convertToString(user.getMiddleName());
        this.lastName        = StConverter.convertToString(user.getLastName());
        this.address         = StConverter.convertToString(user.getAddress());
        this.city            = StConverter.convertToString(user.getCity());
        this.state           = StConverter.convertToString(user.getState());
        this.zipCode         = StConverter.convertToString(user.getPostalCode());
        this.country         = StConverter.convertToString(user.getCountry());
        this.phoneNumber     = StConverter.convertToString(user.getPhone());
        this.organization    = StConverter.convertToString(user.getAffiliateOrg());
        this.prsOrganization = StConverter.convertToString(user.getPrsOrg());
        if (user.getPoOrganizationIdentifier() != null) {
            this.treatmentSiteId = user.getPoOrganizationIdentifier().getExtension();
        }        
        this.treatmentSite = treatmentSite;
        if (user.getPoPersonIdentifier() != null) {
            this.physicianId = user.getPoPersonIdentifier().getExtension();
        }        
        this.physician = physician;
    }
    
    /**
     * Perform user account validations.
     * @param userAccount userAccount
     * @param action action to add for errors
     * @param validateAll validateAll flag
     */
    public static void validate(UserAccountWebDTO userAccount, AbstractAccrualAction action, boolean validateAll) {
        action.clearFieldErrors();
        validateEmailAddress(userAccount.getLoginName(), action);
        validatePassword(userAccount.getPassword(), action);
        validateRetypePassword(userAccount.getRetypePassword(), userAccount.getPassword(), action);
        
        if (validateAll) {
            // validate first name
            action.addFieldErrorIfEmpty(userAccount.getFirstName(), 
                                        USER_ACCOUNT + ".firstName", 
                                        "> Please enter a First Name");
        
            // validate last name
            action.addFieldErrorIfEmpty(userAccount.getLastName(), 
                                        USER_ACCOUNT + ".lastName", 
                                        "> Please enter a Last Name");
            
            validateState(userAccount.getState(), userAccount.getCountry(), action);
            
            // validate country
            action.addFieldErrorIfEmpty(userAccount.getCountry(), 
                                        USER_ACCOUNT + ".country", 
                                        "> Please enter a Country");
            
            // validate phone number
            action.addFieldErrorIfEmpty(userAccount.getPhoneNumber(), 
                                        USER_ACCOUNT + ".phoneNumber", 
                                        "> Please enter a Phone Number");
            
            // validate organization
            action.addFieldErrorIfEmpty(userAccount.getOrganization(), 
                                        USER_ACCOUNT + ".organization", 
                                        "> Please enter an Organization Affiliation");
            
            // validate treatment site
            action.addFieldErrorIfEmpty(userAccount.getTreatmentSite(), 
                                        USER_ACCOUNT + ".treatmentSite", 
                                        "> Please select a Treatment Site");
            
            // validate physician
            action.addFieldErrorIfEmpty(userAccount.getPhysician(), 
                                        USER_ACCOUNT + ".physician", 
                                        "> Please select a Physician");
        }
    }
    
    /**
     * @param loginName loginName
     * @param action action to add for errors
     */
    public static void validateEmailAddress(String loginName, AbstractAccrualAction action) {
        if (PAUtil.isEmpty(loginName)) {
            action.addFieldError(USER_ACCOUNT + ".loginName", "> Please enter an Email Address");
        } else if (!PAUtil.isValidEmail(loginName)) {
            action.addFieldError(USER_ACCOUNT + ".loginName", "> Please enter a valid Email Address");
        }
    }
    
    /**
     * @param password password
     * @param action action to add for errors
     */
    public static void validatePassword(String password, AbstractAccrualAction action) {
        // password must have min 8 chars with at least one not alpha-numeric char and one digit
        String dotPassword = ".password";
        Matcher oneNonAlphaNumericMatcher = ONE_NON_ALPHA_NUMERIC.matcher(password);
        Matcher oneDigitMatcher = ONE_DIGIT.matcher(password);
        
        if (PAUtil.isEmpty(password)) {
            action.addFieldError(USER_ACCOUNT + dotPassword, "> Please enter a Password");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            action.addFieldError(USER_ACCOUNT + dotPassword, 
                                 "> Please enter a Password that is at least 8 characters in length");
        } else if (!oneNonAlphaNumericMatcher.find(0)) {
            action.addFieldError(USER_ACCOUNT + dotPassword, 
                                 "> Please enter a Password that contains at least one special character");
        } else if (!oneDigitMatcher.find(0)) {
            action.addFieldError(USER_ACCOUNT + dotPassword, 
                                 "> Please enter a Password that contains at least one digit");
        }
    }
    
    /**
     * @param retypePassword retypePassword
     * @param password password
     * @param action action to add for errors
     */
    public static void validateRetypePassword(String retypePassword, String password, AbstractAccrualAction action) {
        Matcher oneNonAlphaNumericMatcher = ONE_NON_ALPHA_NUMERIC.matcher(password);
        Matcher oneDigitMatcher = ONE_DIGIT.matcher(password);
        
        if (PAUtil.isEmpty(retypePassword)
         || PAUtil.isEmpty(password)
         || password.length() < MIN_PASSWORD_LENGTH
         || !oneNonAlphaNumericMatcher.find(0)
         || !oneDigitMatcher.find(0)) {
            action.addFieldError(USER_ACCOUNT + ".retypePassword", "> Please retype the Password");
        } else if (!password.equals(retypePassword)) {
            action.addFieldError(USER_ACCOUNT + ".retypePassword", 
                                 "> Please retype the Password - the passwords must match");
        }
    }
    
    /**
     * @param state state
     * @param country country
     * @param action action to add for errors
     */
    public static void validateState(String state, String country, AbstractAccrualAction action) {
        if (PAUtil.isEmpty(state)) {
            action.addFieldError(USER_ACCOUNT + ".state", 
                                 "> Please select a State (select 'None' for non-US countries)");
        } else if (PAUtil.isNotEmpty(country)) {
            if ("USA".equals(country)) {
                if ("INTERNATIONAL".equals(state)) {
                    action.addFieldError(USER_ACCOUNT + ".state", "> Please select a State of the United States");
                }
            } else {
                if (!"INTERNATIONAL".equals(state)) {
                    action.addFieldError(USER_ACCOUNT + ".state", "> Please select 'None' for non-US countries");
                }
            }
        }
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }
    
    /**
     * @param loginName the loginName to set
     */    
    public void setLoginName(String loginName) {
        if (loginName != null) {
            this.loginName = loginName.trim();
        }
    }
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param password the password to set
     */    
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the retypePassword
     */
    public String getRetypePassword() {
        return retypePassword;
    }

    /**
     * @param retypePassword the retypePassword to set
     */    
    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @param firstName the firstName to set
     */    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }
    
    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * @param lastName the lastName to set
     */    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }        
    
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }
    
    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
    
    /**
     * @param zipCode the zipCode to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }
    
    /**
     * @param country the country to set
     */    
    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * @param phoneNumber the phoneNumber to set
     */    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }
    
    /**
     * @param organization the organization to set
     */    
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the prsOrganization
     */
    public String getPrsOrganization() {
        return prsOrganization;
    }

    /**
     * @param prsOrganization the prsOrganization to set
     */
    public void setPrsOrganization(String prsOrganization) {
        this.prsOrganization = prsOrganization;
    }
    
    /**
     * @return the treatmentSiteId
     */
    public String getTreatmentSiteId() {
        return treatmentSiteId;
    }

    /**
     * @param treatmentSiteId the treatmentSiteId to set
     */
    public void setTreatmentSiteId(String treatmentSiteId) {
        this.treatmentSiteId = treatmentSiteId;
    }
    
    /**
     * @return the treatmentSite
     */
    public String getTreatmentSite() {
        return treatmentSite;
    }

    /**
     * @param treatmentSite the treatmentSite to set
     */
    public void setTreatmentSite(String treatmentSite) {
        this.treatmentSite = treatmentSite;
    }
    
    /**
     * @return the physicianId
     */
    public String getPhysicianId() {
        return physicianId;
    }

    /**
     * @param physicianId the physicianId to set
     */
    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }
    
    /**
     * @return the physician
     */
    public String getPhysician() {
        return physician;
    }

    /**
     * @param physician the physician to set
     */
    public void setPhysician(String physician) {
        this.physician = physician;
    }
    
    /**
     * @return the list of staging systems
     */
    public List<USStateCode> getStates() {
        return Arrays.asList(USStateCode.values());
    }
    
    /**
     * @return the list of countries
     */
    public List<Country> getCountries() {
        List<Country> countries;
        try {
            countries = PaServiceLocator.getInstance().getLookUpTableService().getCountries();
        } catch (Exception e) {
            // just return empty list
            countries = new ArrayList<Country>();
        }
        return countries;
    }
}