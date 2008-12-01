package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class StudyDiseaseDTO extends StudyDTO {
    private static final long serialVersionUID = 177737890L;

    Ii diseaseIdentifier;
    Bl leadDiseaseIndicator;
    /**
     * @return the diseaseIdentifier
     */
    public Ii getDiseaseIdentifier() {
        return diseaseIdentifier;
    }
    /**
     * @param diseaseIdentifier the diseaseIdentifier to set
     */
    public void setDiseaseIdentifier(Ii diseaseIdentifier) {
        this.diseaseIdentifier = diseaseIdentifier;
    }
    /**
     * @return the leadDiseaseIndicator
     */
    public Bl getLeadDiseaseIndicator() {
        return leadDiseaseIndicator;
    }
    /**
     * @param leadDiseaseIndicator the leadDiseaseIndicator to set
     */
    public void setLeadDiseaseIndicator(Bl leadDiseaseIndicator) {
        this.leadDiseaseIndicator = leadDiseaseIndicator;
    }
}
