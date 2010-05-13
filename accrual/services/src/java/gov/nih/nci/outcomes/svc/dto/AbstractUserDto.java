package gov.nih.nci.outcomes.svc.dto;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.outcomes.svc.util.SvcConstants;
import gov.nih.nci.pa.enums.USStateCode;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.TooManyFields", "PMD.AvoidDuplicateLiterals" })
public abstract class AbstractUserDto extends AbstractBaseOutSvcDto {

    /**
     *
     */
    private static final long serialVersionUID = -2634784781070526530L;

    private St email;
    private St identity;
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

    
    /**
     * @return the email
     */
    public St getEmail() {
        return email;
    }
    
    /**
     * @param email the email to set
     */
    public void setEmail(St email) {
        this.email = email;
    }
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
        validateIdentity();
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
        if (PAUtil.isStNull(email)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".email", "> Please enter an Email Address");
        } else if (!PAUtil.isValidEmail(StConverter.convertToString(email))) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".email",
                    "> Please enter a valid Email Address");
        }
    }
    /**
     * @throws OutcomesFieldException on err
     */
    private void validateIdentity() throws OutcomesFieldException {
        if (PAUtil.isStNull(identity)) {
            throw new OutcomesFieldException(getClass(), USER_ACCOUNT + ".identity", 
                    "> Please enter your grid identity");
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
                final List<USStateCode> states = Arrays.asList(USStateCode.values());
                boolean stateFound = false;
                for (final USStateCode stateIterator : states) {
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