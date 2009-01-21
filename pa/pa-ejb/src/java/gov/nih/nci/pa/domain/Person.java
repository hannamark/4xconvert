package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.StatusCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;


/**
 * A human being. 
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Entity
public class Person extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    private String identifier;
    private StatusCode statusCode;
    
    private List<HealthCareProvider> healthCareProviders = new ArrayList<HealthCareProvider>();
    private List<ClinicalResearchStaff> clinicalResearchStaffs = new ArrayList<ClinicalResearchStaff>();
    
    
    /**
     * 
     * @return firstName first name
     */
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }
    /**
     * 
     * @param firstName firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * 
     * @return lastName lastName
     */
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }
    /**
     * 
     * @param lastName lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * 
     * @return middleName middleName
     */
    @Column(name = "MIDDLE_NAME")
    public String getMiddleName() {
        return middleName;
    }
    /**
     * 
     * @param middleName middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    /**
     * @return the identifier
     */
    @Column(name = "assigned_identifier")
    @NotNull
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 
     * @return statusCode
     */
    @Column(name = "STATUS_CODE")
    @Enumerated(EnumType.STRING)
    @NotNull
    public StatusCode getStatusCode() {
        return statusCode;
    }
    /**
     * 
     * @param statusCode statusCode
     */
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
    
    /**
     * 
     * @return healthCareProviders healthCareProviders
     */
    @OneToMany(mappedBy = "person")
    public List<HealthCareProvider> getHealthCareProviders() {
        return healthCareProviders;
    }
    /**
     * 
     * @param healthCareProviders healthCareProviders
     */
    public void setHealthCareProviders(List<HealthCareProvider> healthCareProviders) {
        this.healthCareProviders = healthCareProviders;
    }
    
    /**
     * 
     * @return clinicalResearchStaffs clinicalResearchStaffs
     */
    @OneToMany(mappedBy = "person")
    public List<ClinicalResearchStaff> getClinicalResearchStaffs() {
        return clinicalResearchStaffs;
    }
    /**
     * 
     * @param clinicalResearchStaffs clinicalResearchStaffs
     */
    public void setClinicalResearchStaffs(List<ClinicalResearchStaff> clinicalResearchStaffs) {
        this.clinicalResearchStaffs = clinicalResearchStaffs;
    }

    /**
     * 
     * @return fullName
     */
    @Transient
    public String getFullName() {
        if (lastName != null) {
            fullName = lastName;
        }
        if (firstName != null) {
            fullName = lastName + "," + firstName;
        }
        return fullName;
    }
    
    /**
     * 
     * @return fullName
     */
    @Transient
    public String getFirstMiddleLastName() {
        if (firstName != null) {
            fullName = firstName + " ";
        }
        if (middleName != null) {
            fullName =  firstName + " " + middleName + " ";
        }

        if (lastName != null) {
            fullName =  firstName + " " + middleName + " " + lastName + " ";
        }
        
        return fullName;
    }

}
