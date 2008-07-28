package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AssigningAuthorityCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * base identification for II.
 * @author NAmiruddin
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Identification extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;
    
    private AssigningAuthorityCode assigningAuthorityCode;
    private Boolean primaryIndicator; 
    private String identifierValue;
    
    /**
     * 
     * @return assigningAuthorityCode assigningAuthorityCode
     */
    @Column(name = "ASSIGNING_AUTHORITY_CODE")
    @Enumerated(EnumType.STRING)    
    public AssigningAuthorityCode getAssigningAuthorityCode() {
        return assigningAuthorityCode;
    }
    /**
     * 
     * @param assigningAuthorityCode assigningAuthorityCode
     */
    public void setAssigningAuthorityCode(
            AssigningAuthorityCode assigningAuthorityCode) {
        this.assigningAuthorityCode = assigningAuthorityCode;
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
     * 
     * @return identifierValue identifierValue
     */
    @Column(name = "IDENTIFIER_VALUE")
    public String getIdentifierValue() {
        return identifierValue;
    }
    /**
     * 
     * @param identifierValue identifierValue
     */
    public void setIdentifierValue(String identifierValue) {
        this.identifierValue = identifierValue;
    }
    
}
