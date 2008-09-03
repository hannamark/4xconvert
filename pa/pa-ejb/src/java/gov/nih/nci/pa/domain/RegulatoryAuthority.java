package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Governmental bodies that have the power to pass and enforce laws.
 * For example, the Medicines and Healthcare Products Regulatory Agency 
 * (MHRA)in UK, the Food and Drug Administration (FDA) in the USA.
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name =  "REGULATORY_AUTHORITY")
public class RegulatoryAuthority extends AbstractEntity {
        
    private static final long serialVersionUID = 1L;
    private String authorityName;
    private Country country;
    
    /**
     * @return the authorityName
     */     
    @Column(name = "AUTHORITY_NAME")
    public String getAuthorityName() {
            return authorityName;
    }
    /**
     * @param authorityName the authorityName to set
     */
    public void setAuthorityName(String authorityName) {
            this.authorityName = authorityName;
    }
    /**
     * @return the countryId
     */
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    public Country getCountry() {
            return country;
    }
    
    /**
     * @param country the country to set
     * 
     */
    public void setCountry(Country country) {
            this.country = country;
    }
}
