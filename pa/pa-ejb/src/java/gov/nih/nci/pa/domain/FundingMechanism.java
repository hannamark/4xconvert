package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Look up table for funding mechanism. 
 * 
 * @author Naveen Amiruddin
 * @since 07/24/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without  express written permission of the
 * copyright holder, NCI.
 */

@Entity
@Table(name = "FUNDING_MECHANISM")
public class FundingMechanism {

    private Long id;
    private String fundingMechanismCode;
    
    /**
     * set id.
     * @param id id
     */
     public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)       
    public Long getId() {
        return this.id;
    }
    

    /**
     * 
     * @return fundingMechanismCode
     */
    @Column(name = "funding_mechanism_code")
    public String getFundingMechanismCode() {
        return fundingMechanismCode;
    }

    /**
     * 
     * @param fundingMechanismCode fundingMechanismCode
     */
    public void setFundingMechanismCode(String fundingMechanismCode) {
        this.fundingMechanismCode = fundingMechanismCode;
    }
    
}
