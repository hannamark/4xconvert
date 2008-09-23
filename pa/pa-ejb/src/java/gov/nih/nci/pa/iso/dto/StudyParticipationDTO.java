/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
 * StudyParticipationDTO for transferring StudyParticipation object .
 * @author Hugh Reinhart
 * @since 07/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class StudyParticipationDTO extends OrganizationFunctionalRoleDTO {
    Ii healthcareFacilityIi;
    Cd functionalCode;
    St localStudyProtocolIdentifier;
    
    /**
     * @return the healthcareFacilityIi
     */
    public Ii getHealthcareFacilityIi() {
        return healthcareFacilityIi;
    }
    /**
     * @param healthcareFacilityIi the healthcareFacilityIi to set
     */
    public void setHealthcareFacilityIi(Ii healthcareFacilityIi) {
        this.healthcareFacilityIi = healthcareFacilityIi;
    }
    /**
     * @return the functionalCode
     */
    public Cd getFunctionalCode() {
        return functionalCode;
    }
    /**
     * @param functionalCode the functionalCode to set
     */
    public void setFunctionalCode(Cd functionalCode) {
        this.functionalCode = functionalCode;
    }
    /**
     * @return the localStudyProtocolIdentifier
     */
    public St getLocalStudyProtocolIdentifier() {
        return localStudyProtocolIdentifier;
    }
    /**
     * @param localStudyProtocolIdentifier the localStudyProtocolIdentifier to set
     */
    public void setLocalStudyProtocolIdentifier(St localStudyProtocolIdentifier) {
        this.localStudyProtocolIdentifier = localStudyProtocolIdentifier;
    }
}
