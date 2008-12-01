package gov.nih.nci.pa.iso.dto;

import gov.nih.nci.coppa.iso.St;

/**
* @author Hugh Reinhart
* @since 11/30/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class DiseaseDTO extends BaseDTOWithStatusCode {
    private static final long serialVersionUID = 155517890L;
    
    St diseaseCode;
    St ntTermIdentifier;
    St preferredName;
    St menuDisplayName;
    
    /**
     * @return the diseaseCode
     */
    public St getDiseaseCode() {
        return diseaseCode;
    }
    /**
     * @param diseaseCode the diseaseCode to set
     */
    public void setDiseaseCode(St diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
    /**
     * @return the ntTermIdentifier
     */
    public St getNtTermIdentifier() {
        return ntTermIdentifier;
    }
    /**
     * @param ntTermIdentifier the ntTermIdentifier to set
     */
    public void setNtTermIdentifier(St ntTermIdentifier) {
        this.ntTermIdentifier = ntTermIdentifier;
    }
    /**
     * @return the preferredName
     */
    public St getPreferredName() {
        return preferredName;
    }
    /**
     * @param preferredName the preferredName to set
     */
    public void setPreferredName(St preferredName) {
        this.preferredName = preferredName;
    }
    /**
     * @return the menuDisplayName
     */
    public St getMenuDisplayName() {
        return menuDisplayName;
    }
    /**
     * @param menuDisplayName the menuDisplayName to set
     */
    public void setMenuDisplayName(St menuDisplayName) {
        this.menuDisplayName = menuDisplayName;
    }
}
