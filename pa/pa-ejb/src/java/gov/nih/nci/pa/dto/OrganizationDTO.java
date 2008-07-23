package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * Organization DTO  for transferring Organization object .
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class OrganizationDTO implements Serializable {
    
    static final long serialVersionUID = 1283476876L;
    
    private Long id;
    private String nciIdentifier;
    private String name;
    private String city;
    private String country;
    
    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }
    /**
     * 
     * @param id system unique id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 
     * @return nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     * 
     * @param nciIdentifier application unique identifier 
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
    /**
     * 
     * @return name organization name
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @param name organization name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return country
     */
    public String getCountry() {
        return country;
    }
    /**
     * 
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    
    
    

}
