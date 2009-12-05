package gov.nih.nci.accrual.web.dto.util;

import gov.nih.nci.accrual.web.util.PaServiceLocator;
import gov.nih.nci.pa.domain.Country;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Display helper class.
 * 
 * @author Lisa Kelley
 *
 */
public class TreatmentSiteWebDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String city;
    private String state;
    private String zipCode;
    private String country;    
    private String id;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
