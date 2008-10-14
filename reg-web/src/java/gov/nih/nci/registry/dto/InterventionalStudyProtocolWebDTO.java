/**
 * 
 */
package gov.nih.nci.registry.dto;

import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;

/**
 * @author Bala Nair
 * 
 */
public class InterventionalStudyProtocolWebDTO {

    private String trialTitle;
    private String trialPhase;
    private String nciAccessionNumber;



    /**
     * @param iso
     * InterventionalStudyProtocolWebDTO object
     */
    public InterventionalStudyProtocolWebDTO(InterventionalStudyProtocolDTO iso) {
        super();
        this.trialPhase = iso.getPhaseCode().getCode();
        this.trialTitle = iso.getOfficialTitle().getValue();
        this.nciAccessionNumber =  IiConverter.convertToString(iso.getIdentifier());
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
    
    /**
     * @return result
     */
    public String getNciAccessionNumber() {
        return nciAccessionNumber;
    }
    /**
     * @param nciAccessionNumber nciAccessionNumber
     */
    public void setNciAccessionNumber(String nciAccessionNumber) {
        this.nciAccessionNumber = nciAccessionNumber;
    }


}
