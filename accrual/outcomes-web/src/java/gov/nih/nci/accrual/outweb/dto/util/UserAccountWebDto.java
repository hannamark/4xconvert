/**
 * 
 */
package gov.nih.nci.accrual.outweb.dto.util;

import gov.nih.nci.accrual.outweb.action.AbstractAccrualAction;
import gov.nih.nci.accrual.outweb.util.PaServiceLocator;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.outcomes.svc.dto.AbstractUserDto;
import gov.nih.nci.outcomes.svc.dto.UserSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesException;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.USStateCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bala Nair
 *
 */
@SuppressWarnings({"PMD.TooManyFields", "PMD.CyclomaticComplexity" })
public class UserAccountWebDto extends AbstractUserDto {
    private St treatmentSite;
    private St physician;
    private transient String authServiceUrl = "https://dorian.training.cagrid.org:8443/wsrf/services/cagrid/Dorian"; 
    
    
    /**
     * @param authServiceUrl the authServiceUrl to set
     */
    public void setAuthServiceUrl(String authServiceUrl) {
        this.authServiceUrl = authServiceUrl;
    }


    /**
     * @return the authServiceUrl
     */
    public String getAuthServiceUrl() {
        return authServiceUrl;
    }


    /**
     * 
     */
    private static final long serialVersionUID = 6474236166553870629L;

    /**
     * Perform user account validations.
     * @param action action to add for errors
     */
    public void validate(AbstractAccrualAction action) {
        action.clearFieldErrors();
        try {
            validate();
        } catch (final OutcomesFieldException e) {
            action.addFieldError(e.getField(), e.getLocalizedMessage());
        } catch (final OutcomesException e) {
            action.addActionError(e.getLocalizedMessage());
        }
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
        } catch (final Exception e) {
            // just return empty list
            countries = new ArrayList<Country>();
        }
        return countries;
    }
    /**
     * Gets the svc dto.
     * 
     * @return the svc dto
     */
    public UserSvcDto getSvcDto() {
        final UserSvcDto svcDto = new UserSvcDto();
        svcDto.setIdentifier(getIdentifier());
        svcDto.setIdentity(getIdentity());
        svcDto.setPassword(getPassword());
        svcDto.setFirstName(getFirstName());
        svcDto.setMiddleName(getMiddleName());
        svcDto.setLastName(getLastName());
        svcDto.setAddress(getAddress());
        svcDto.setAffiliateOrg(getAffiliateOrg());
        svcDto.setCity(getCity());
        svcDto.setCountry(getCountry());
        svcDto.setPhone(getPhone());
        svcDto.setTreatmentSiteIdentifier(getTreatmentSiteIdentifier());
        svcDto.setPhysicianIdentifier(getPhysicianIdentifier());
        svcDto.setPostalCode(getPostalCode());
        svcDto.setPrsOrg(getPrsOrg());
        svcDto.setRetypePassword(getRetypePassword());
        svcDto.setState(getState());
        svcDto.setEmail(getEmail());
        return svcDto;
    }


    /**
     * @param treatmentSite the treatmentSite to set
     */
    public void setTreatmentSite(St treatmentSite) {
        this.treatmentSite = treatmentSite;
    }


    /**
     * @return the treatmentSite
     */
    public St getTreatmentSite() {
        return treatmentSite;
    }


    /**
     * @param physician the physician to set
     */
    public void setPhysician(St physician) {
        this.physician = physician;
    }


    /**
     * @return the physician
     */
    public St getPhysician() {
        return physician;
    }
}