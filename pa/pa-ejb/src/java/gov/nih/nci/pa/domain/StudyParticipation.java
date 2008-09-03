package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.FunctionalCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * StudySite bean for managing StudySite.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "STUDY_PARTICIPATION")

public class StudyParticipation extends OrganizationFunctionalRole {
    private static final long serialVersionUID = 1234567890L;

    private FunctionalCode functionalCode;
    private Boolean leadOrganizationIndicator; 
    private String localStudyProtocolIdentifier;
    
    /**
     * 
     * @return functionalCode
     */
    @Column(name = "FUNCTIONAL_CODE")
    @Enumerated(EnumType.STRING)    
    public FunctionalCode getFunctionalCode() {
        return functionalCode;
    }
    /**
     * 
     * @param functionalCode functionalCode
     */
    public void setFunctionalCode(FunctionalCode functionalCode) {
        this.functionalCode = functionalCode;
    }
    /**
     * 
     * @return leadOrganizationIndicator
     */
    @Column(name = "LEAD_ORG_INDICATOR")
    public Boolean getLeadOrganizationIndicator() {
        return leadOrganizationIndicator;
    }
    /**
     * 
     * @param leadOrganizationIndicator leadOrganizationIndicator
     */
    public void setLeadOrganizationIndicator(Boolean leadOrganizationIndicator) {
        this.leadOrganizationIndicator = leadOrganizationIndicator;
    }
    /**
     * 
     * @return localStudyProtocolIdentifier
     */
    @Column(name = "LOCAL_SP_INDENTIFIER")
    public String getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     * 
     * @param localStudyProtocolIdentifier localStudyProtocolIdentifier
     */
    public void setLocalStudyProtocolIdentifier(String localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
}
