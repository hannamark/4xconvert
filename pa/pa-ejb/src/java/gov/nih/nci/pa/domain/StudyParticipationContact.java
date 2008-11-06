/**
 * 
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StudyContactRoleCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author Hugh Reinhart
 * @since 09/29/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@Table(name = "STUDY_PARTICIPATION_CONTACT")
public class StudyParticipationContact extends PersonFunctionalRole {
    private static final long serialVersionUID = 123489324790L;

    private String addressLine;
    private String deliveryAddressLine;
    private String city;
    private String state;
    private String postalCode;
    private Country country;
        
    private Boolean primaryIndicator;
    private StudyContactRoleCode roleCode;
    private StudyParticipation studyParticipation;
    private String phone;
    private String email;
    /**
     * @return the phone
     */
    @Column(name = "telephone")
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the email
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the addressLine
     */
    @Column(name = "ADDRESS_LINE")
    public String getAddressLine() {
        return addressLine;
    }
    /**
     * @param addressLine the addressLine to set
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
    /**
     * @return the deliveryAddressLine
     */
    @Column(name = "DELIVERY_ADDRESS_LINE")
    public String getDeliveryAddressLine() {
        return deliveryAddressLine;
    }
    /**
     * @param deliveryAddressLine the deliveryAddressLine to set
     */
    public void setDeliveryAddressLine(String deliveryAddressLine) {
        this.deliveryAddressLine = deliveryAddressLine;
    }
    /**
     * @return the city
     */
    @Column(name = "CITY")
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
    @Column(name = "STATE")
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
     * @return the postalCode
     */
    @Column(name = "POSTAL_CODE")
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * @return the country
     */
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", updatable = false)
    public Country getCountry() {
        return country;
    }
    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }
    /**
     * @return the primaryIndicator
     */
    @Column(name = "PRIMARY_INDICATOR")
    public Boolean getPrimaryIndicator() {
        return primaryIndicator;
    }
    /**
     * @param primaryIndicator the primaryIndicator to set
     */
    public void setPrimaryIndicator(Boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }
    /**
     * @return the roleCode
     */
    @Column(name = "ROLE_CODE")
    @Enumerated(EnumType.STRING)
    public StudyContactRoleCode getRoleCode() {
        return roleCode;
    }
    /**
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(StudyContactRoleCode roleCode) {
        this.roleCode = roleCode;
    }
    /**
     * @return the studyParticipation
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PARTICIPATION_ID")
    @NotNull
    public StudyParticipation getStudyParticipation() {
        return studyParticipation;
    }
    /**
     * @param studyParticipation the studyParticipation to set
     */
    public void setStudyParticipation(StudyParticipation studyParticipation) {
        this.studyParticipation = studyParticipation;
    }
}
