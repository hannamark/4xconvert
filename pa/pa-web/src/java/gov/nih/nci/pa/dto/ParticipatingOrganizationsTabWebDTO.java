/**
 * 
 */
package gov.nih.nci.pa.dto;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;

/**
 * DTO class for storing form data used in participating org.
 * 
 * @author Hugh Reinhart
 * @since 10/03/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class ParticipatingOrganizationsTabWebDTO {
    private Ii poOrganizationIi;
    private Ii poHealthCareFacilityIi;
    private Long studyParticipationId;
    private Organization facilityOrganization;
    private boolean newParticipation = true;
    

    /**
     * @return the organizationIi
     */
    public Ii getPoOrganizationIi() {
        return poOrganizationIi;
    }
    /**
     * @param poOrganizationIi the organizationIi to set
     */
    public void setPoOrganizationIi(Ii poOrganizationIi) {
        this.poOrganizationIi = poOrganizationIi;
    }
    /**
     * @return the poHealthCareFacilityIi
     */
    public Ii getPoHealthCareFacilityIi() {
        return poHealthCareFacilityIi;
    }

    /**
     * @param poHealthCareFacilityIi the poHealthCareFacilityIi to set
     */
    public void setPoHealthCareFacilityIi(Ii poHealthCareFacilityIi) {
        this.poHealthCareFacilityIi = poHealthCareFacilityIi;
    }

    /**
     * @return the studyParticipationId
     */
    public Long getStudyParticipationId() {
        return studyParticipationId;
    }
    /**
     * @param studyParticipationId the studyParticipationId to set
     */
    public void setStudyParticipationId(Long studyParticipationId) {
        this.studyParticipationId = studyParticipationId;
    }

    /**
     * @return the facilityOrganization
     */
    public Organization getFacilityOrganization() {
        return facilityOrganization;
    }

    /**
     * @param facilityOrganization the facilityOrganization to set
     */
    public void setFacilityOrganization(Organization facilityOrganization) {
        this.facilityOrganization = facilityOrganization;
    }
    /**
     * @return the newParticipation
     */
    public boolean isNewParticipation() {
        return newParticipation;
    }
    /**
     * @param newParticipation the newParticipation to set
     */
    public void setNewParticipation(boolean newParticipation) {
        this.newParticipation = newParticipation;
    }

}
