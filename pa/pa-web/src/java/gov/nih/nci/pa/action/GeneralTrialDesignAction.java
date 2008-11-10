package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * action for edit general trial design.
 * @author NAmiruddin
 *
 */
public class GeneralTrialDesignAction extends ActionSupport {
    
    private Long studyProtocolId = null;
    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    
    /**  
     * @return res
     */
    public String query() {
        try {            
            StudyProtocolQueryDTO  studyProtocolQueryDTO = 
                PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(studyProtocolId));
                
            StudyProtocolDTO spDTO = null;
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(IiConverter.convertToIi(studyProtocolId));
            copy(spDTO);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return "edit";
    }

    
    private void copy(StudyProtocolDTO spDTO) {        
        gtdDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        gtdDTO.setPublicTitle(spDTO.getPublicTitle().getValue());
        gtdDTO.setAssignedIdentifier(spDTO.getAssignedIdentifier().getExtension());
        gtdDTO.setAcronym(spDTO.getAcronym().getValue());
        gtdDTO.setPublicDescription(spDTO.getPublicDescription().getValue());
        gtdDTO.setScientificDescription(spDTO.getScientificDescription().getValue());
        gtdDTO.setKeywordText(spDTO.getKeywordText().getValue());
    }

    /**
     * 
     * @return gtdDTO
     */
    public GeneralTrialDesignWebDTO getGtdDTO() {
        return gtdDTO;
    }

    /**
     * 
     * @param gtdDTO gtdDTO
     */
    public void setGtdDTO(GeneralTrialDesignWebDTO gtdDTO) {
        this.gtdDTO = gtdDTO;
    }
    
    

}
