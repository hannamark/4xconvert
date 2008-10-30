package gov.nih.nci.pa.domain;


import gov.nih.nci.pa.enums.StudyContactRoleCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;



/**
 * This class is an abstract concept that contains attributes common to all types of study documents.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name =  "STUDY_CONTACT")
public class StudyContact extends PersonFunctionalRole {

    private static final long serialVersionUID = 1234567890L;
    
    private String addressLine;
    private String deliveryAddressLine;
    private String city;
    private String state;
    private String postalCode;
    private Country country;
    private StudyContactRoleCode studyContactRoleCode;    
    private HealthCareProvider healthCareProvider;
    private Boolean primaryIndicator;
   

    /**
     * 
     * @return addressLine
     */
    @Column(name = "ADDRESS_LINE ")
    public String getAddressLine() {
        return addressLine;
    }

    /**
     * 
     * @param addressLine  addressLine
     */
    
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    /**
     * 
     * @return deliveryAddressLine
     */
    @Column(name = "DELIVERY_ADDRESS_LINE")
    public String getDeliveryAddressLine() {
        return deliveryAddressLine;
    }

    /**
     * 
     * @param deliveryAddressLine deliveryAddressLine
     */
    public void setDeliveryAddressLine(String deliveryAddressLine) {
        this.deliveryAddressLine = deliveryAddressLine;
    }

    /**
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return postalCode
     */
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 
     * @param postalCode postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 
     * @return country
     */
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", updatable = false)
    public Country getCountry() {
        return country;
    }

    /**
     * 
     * @param country country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * 
     * @return healthCareProvider healthCareProvider
     */
    @ManyToOne
    @JoinColumn(name = "HEALTHCARE_PROVIDER_ID", updatable = false)
    @NotNull
    public HealthCareProvider getHealthCareProvider() {
        return healthCareProvider;
    }

    /**
     * 
     * @param healthCareProvider healthCareProvider
     */
    public void setHealthCareProvider(HealthCareProvider healthCareProvider) {
        this.healthCareProvider = healthCareProvider;
    }
    
    /**
     * 
     * @return primaryIndicator primaryIndicator
     */
    @Column(name = "PRIMARY_INDICATOR")
    public Boolean getPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * 
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(Boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }


    /**
     * @return the studyContactRoleCode studyContactRoleCode
     */
    @Column(name = "ROLE_CODE")
    public StudyContactRoleCode getStudyContactRoleCode() {
        return studyContactRoleCode;
    }

    /**
     * @param studyContactRoleCode the studyContactRoleCode to set
     */
    public void setStudyContactRoleCode(StudyContactRoleCode studyContactRoleCode) {
        this.studyContactRoleCode = studyContactRoleCode;
    }
}
