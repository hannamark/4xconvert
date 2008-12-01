package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class DiseaseAlternameDTO extends BaseDTOWithStatusCode {
    private static final long serialVersionUID = 1090967890L;
    
    St alternateName;
    Ii diseaseIdentifier;
    /**
     * @return the alternateName
     */
    public St getAlternateName() {
        return alternateName;
    }
    /**
     * @param alternateName the alternateName to set
     */
    public void setAlternateName(St alternateName) {
        this.alternateName = alternateName;
    }
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
}
