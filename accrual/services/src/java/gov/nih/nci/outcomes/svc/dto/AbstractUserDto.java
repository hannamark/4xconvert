package gov.nih.nci.outcomes.svc.dto;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.outcomes.svc.util.SvcConstants;
import gov.nih.nci.pa.enums.USStateCode;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Vrushali
 *
 */
public abstract class AbstractUserDto extends AbstractBaseOutSvcDto {

    /**
     *
     */
    private static final long serialVersionUID = -2634784781070526530L;

    private St identity;
    private St password;
    private St retypePassword;
    private St firstName;
    private St middleName;
    private St lastName;
    private St address;
    private St city;
    private St state;
    private St postalCode;
    private St country;
    private St phone;
    private St affiliateOrg;
    private St prsOrg;
    private Ii treatmentSiteIdentifier;
    private Ii physicianIdentifier;
    private static final String USER_ACCOUNT = "userAccount";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final Pattern ONE_NON_ALPHA_NUMERIC = Pattern.compile("(.*\\p{Punct}.*)+");
    private static final Pattern ONE_DIGIT = Pattern.compile("(.*\\p{Digit}.*)+");

    /**
     * @param identity the identity to set
     */
    public void setIdentity(St identity) {
        this.identity = identity;
    }
    /**
     * @return the identity
     */
    public St getIdentity() {
        return identity;
    }
    /**
     * @return the password
     */
    public St getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(St password) {
        this.password = password;
    }
    /**
     * @param retypePassword the retypePassword to set
     */
    public void setRetypePassword(St retypePassword) {
        this.retypePassword = retypePassword;
    }
    /**
     * @return the retypePassword
     */
    public St getRetypePassword() {
        return retypePassword;
    }
    /**
     * @return the firstName
     */
    public St getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(St firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the middleName
     */
    public St getMiddleName() {
        return middleName;
    }
    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(St middleName) {
        this.middleName = middleName;
    }
    /**
     * @return the lastName
     */
    public St getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(St lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the address
     */
    public St getAddress() {
        return address;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(St address) {
        this.address = address;
    }
    /**
     * @return the city
     */
    public St getCity() {
        return city;
    }
    /**
     * @param city the city to set
     */
    public void setCity(St city) {
        this.city = city;
    }
    /**
     * @return the firstName
     */
    public St getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(St state) {
        this.state = state;
    }
    /**
     * @return the postalCode
     */
    public St getPostalCode() {
        return postalCode;
    }
    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(St postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return the country
     */
    public St getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(St country) {
        this.country = country;
    }
    /**
     * @return the phone
     */
    public St getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(St phone) {
        this.phone = phone;
    }
    /**
     * @return the affiliateOrg
     */
    public St getAffiliateOrg() {
        return affiliateOrg;
    }
    /**
     * @param affiliateOrg the affiliateOrg to set
     */
    public void setAffiliateOrg(St affiliateOrg) {
        this.affiliateOrg = affiliateOrg;
    }
    /**
     * @return the prsOrg
     */
    public St getPrsOrg() {
        return prsOrg;
    }
    /**
     * @param prsOrg the prsOrg to set
     */
    public void setPrsOrg(St prsOrg) {
        this.prsOrg = prsOrg;
    }

    /**
     * @return the treatmentSiteIdentifer
     */
    public Ii getTreatmentSiteIdentifier() {
        return treatmentSiteIdentifier;
    }
    /**
     * @param treatmentSiteIdentifier the treatmentSiteIdentifier to set
     */
    public void setTreatmentSiteIdentifier(Ii treatmentSiteIdentifier) {
        this.treatmentSiteIdentifier = treatmentSiteIdentifier;
    }
    /**
     * @return the physicianIdentifier
     */
    public Ii getPhysicianIdentifier() {
        return physicianIdentifier;
    }
    /**
     * @param physicianIdentifier the physicianIdentifier to set
     */
    public void setPhysicianIdentifier(Ii physicianIdentifier) {
        this.physicianIdentifier = physicianIdentifier;
    }
    /**
     * @throws OutcomesException e
     */
    @Override
    public void validate() throws OutcomesException {
        validateEmailAddress();
        validatePassword();
        validateRetypePassword();
        if (!PAUtil.isCdNull(getAction()) 
                && (getAction().equals(SvcConstants.CREATE)
                || getAction().equals(SvcConstants.UPDATE))) {
         // validate first name
            if (PAUtil.isStNull(firstName)) {
                throw new OutcomesFieldException(getClass(),
                        USER_ACCOUNT + ".firstName", "> Please enter a First Name");
            }
            // validate last name
            if (PAUtil.isStNull(lastName)) {
                throw new OutcomesFieldException(getClass(),
                        USER_ACCOUNT + ".lastName", "> Please enter a Last Name");
            }
            // validate country
            validateCountry();
            validateState();
            // validate phone number
            if (PAUtil.isStNull(phone)) {
                throw new OutcomesFieldException(getClass(),
                        USER_ACCOUNT + ".phone", "> Please enter a Phone Number");
            }
            // validate organization
            if (PAUtil.isStNull(affiliateOrg)) {
                throw new OutcomesFieldException(getClass(),
                        USER_ACCOUNT + ".affiliateOrg", "> Please enter an Organization Affiliation");
            }
            // validate treatment site
            if (PAUtil.isIiNull(treatmentSiteIdentifier)) {
                throw new OutcomesFieldException(getClass(),
                        USER_ACCOUNT + ".treatmentSite", "> Please select a Treatment Site");
            }
            // validate physician
            if (PAUtil.isIiNull(physicianIdentifier)) {
                throw new OutcomesFieldException(getClass(),
                        USER_ACCOUNT + ".physician", "> Please select a Physician");
            }
        }

        super.validate();
    }
    /**
     * @throws OutcomesFieldException on err
     */
    private void validateEmailAddress() throws OutcomesFieldException {
        if (PAUtil.isStNull(identity)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".identity", "> Please enter an Email Address");
        } else if (!PAUtil.isValidEmail(StConverter.convertToString(identity))) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".identity",
                    "> Please enter a valid Email Address");
        }
    }

    /**
     * @throws OutcomesException on err
     */
    private void validatePassword()throws OutcomesException {
        // password must have min 8 chars with at least one not alpha-numeric char and one digit
        String dotPassword = ".password";
        Matcher oneNonAlphaNumericMatcher = ONE_NON_ALPHA_NUMERIC.matcher(StConverter.convertToString(
                password));
        Matcher oneDigitMatcher = ONE_DIGIT.matcher(StConverter.convertToString(password));

        if (PAUtil.isStNull(password)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + dotPassword, "> Please enter a Password");
        } else if (StConverter.convertToString(password).length() < MIN_PASSWORD_LENGTH) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + dotPassword,
                                 "> Please enter a Password that is at least 8 characters in length");
        } else if (!oneNonAlphaNumericMatcher.find(0)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + dotPassword,
                                 "> Please enter a Password that contains at least one special character");
        } else if (!oneDigitMatcher.find(0)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + dotPassword,
                                 "> Please enter a Password that contains at least one digit");
        }
    }

    /**
     * @throws OutcomesFieldException on err
     */
    private void validateRetypePassword() throws OutcomesFieldException {
        Matcher oneNonAlphaNumericMatcher = ONE_NON_ALPHA_NUMERIC.matcher(StConverter.convertToString(
                password));
        Matcher oneDigitMatcher = ONE_DIGIT.matcher(StConverter.convertToString(password));

        if (PAUtil.isStNull(retypePassword)
         || PAUtil.isStNull(password)
         || StConverter.convertToString(password).length() < MIN_PASSWORD_LENGTH
         || !oneNonAlphaNumericMatcher.find(0)
         || !oneDigitMatcher.find(0)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT
                    + ".retypePassword", "> Please retype the Password");
        } else if (!password.equals(retypePassword)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".retypePassword",
                                 "> Please retype the Password - the passwords must match");
        }
    }

    /**
     * @throws OutcomesFieldException on err

     */
    private void validateState() throws OutcomesFieldException  {
        if (PAUtil.isStNull(state)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".state",
                                 "> Please select a State (select 'None' for non-US countries)");
        } else if (PAUtil.isStNull(country)) {
            if ("USA".equals(StConverter.convertToString(country))) {
                List<USStateCode> states = Arrays.asList(USStateCode.values());
                boolean stateFound = false;
                for (USStateCode stateIterator : states) {
                   if (stateIterator.getName().equals(state)) {
                       stateFound = true;
                       break; // since match has been found
                   }
                }
                if ("INTERNATIONAL".equals(StConverter.convertToString(state))) {
                    throw new OutcomesFieldException(getClass(), USER_ACCOUNT
                            + ".state", "> Please select a State of the United States");
                } else if (!stateFound) {
                    throw new OutcomesFieldException(getClass(), USER_ACCOUNT
                            + ".state", "Invalid state selected.");
                }

            } else {
                if (!"INTERNATIONAL".equals(StConverter.convertToString(state))) {
                    throw new OutcomesFieldException(getClass(), USER_ACCOUNT
                            + ".state", "> Please select 'None' for non-US countries");
                }
            }
        }
    }
    private void validateCountry() throws OutcomesException {
        if (PAUtil.isStNull(country)) {
            throw new OutcomesFieldException(getClass(),
                    USER_ACCOUNT + ".country", "> Please enter a Country");
        }
    }
}
