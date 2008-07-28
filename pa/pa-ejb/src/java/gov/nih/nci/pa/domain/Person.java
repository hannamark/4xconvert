package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


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
    private List<HealthCareProvider> healthCareProviders = new ArrayList<HealthCareProvider>();
    
    
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
}
