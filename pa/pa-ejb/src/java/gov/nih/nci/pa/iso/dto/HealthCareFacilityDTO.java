/**
 * 
 */
package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;

import java.util.ArrayList;
import java.util.List;

/**
 * HealthCareFacilityDTO for transferring HealthCareFacility object .
 * @author Hugh Reinhart
 * @since 08/23/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */
public class HealthCareFacilityDTO extends BaseDTO {
    private Ii organizationIi;
    private List<Ii> studyParticipations = new ArrayList<Ii>();
    /**
     * @return the organizationIi
     */
    public Ii getOrganizationIi() {
        return organizationIi;
    }
    /**
     * @param organizationIi the organizationIi to set
     */
    public void setOrganizationIi(Ii organizationIi) {
        this.organizationIi = organizationIi;
    }
    /**
     * @return the studyParticipations
     */
    public List<Ii> getStudyParticipations() {
        return studyParticipations;
    }
    /**
     * @param studyParticipations the studyParticipations to set
     */
    public void setStudyParticipations(List<Ii> studyParticipations) {
        this.studyParticipations = studyParticipations;
    }
}
