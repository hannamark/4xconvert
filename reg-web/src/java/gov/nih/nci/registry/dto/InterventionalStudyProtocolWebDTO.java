/**
 * 
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;

/**
 * @author Bala Nair
 * 
 */
public class InterventionalStudyProtocolWebDTO {

    private String trialTitle;
    private String trialPhase;

    /**
     * @param iso
     * InterventionalStudyProtocolWebDTO object
     */
    public InterventionalStudyProtocolWebDTO(InterventionalStudyProtocolDTO iso) {
        super();
        this.trialPhase = iso.getPhaseCode().getCode();
        this.trialTitle = iso.getOfficialTitle().getValue();
    }

    /**
     * @return result
     */
    public String getTrialTitle() {
        return trialTitle;
    }
    /**
     * @param trialTitle trialTitle
     */
    public void setTrialTitle(String trialTitle) {
        this.trialTitle = trialTitle;
    }
    /**
     * @return result
     */
    public String getTrialPhase() {
        return trialPhase;
    }
    /**
     * @param trialPhase trialPhase
     */
    public void setTrialPhase(String trialPhase) {
        this.trialPhase = trialPhase;
    }


}
