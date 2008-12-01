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
public class DiseaseParentDTO extends BaseDTOWithStatusCode {
    private static final long serialVersionUID = 1765767890L;

    Ii diseaseIdentifier;
    Ii parentDiseaseIdentifier;
    St parentDiseaseCode;
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
     * @return the parentDiseaseIdentifier
     */
    public Ii getParentDiseaseIdentifier() {
        return parentDiseaseIdentifier;
    }
    /**
     * @param parentDiseaseIdentifier the parentDiseaseIdentifier to set
     */
    public void setParentDiseaseIdentifier(Ii parentDiseaseIdentifier) {
        this.parentDiseaseIdentifier = parentDiseaseIdentifier;
    }
    /**
     * @return the parentDiseaseCode
     */
    public St getParentDiseaseCode() {
        return parentDiseaseCode;
    }
    /**
     * @param parentDiseaseCode the parentDiseaseCode to set
     */
    public void setParentDiseaseCode(St parentDiseaseCode) {
        this.parentDiseaseCode = parentDiseaseCode;
    }
}
