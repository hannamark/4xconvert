package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.StConverter;
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
    
    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    
    /**  
     * @return res
     */
    public String query() {
        try {        

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);             
                
            StudyProtocolDTO spDTO = null;
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
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
     * @return result
     */
    public String update() {
        /*enforceBusinessRules();
        if (hasFieldErrors()) {
          return "edit";
        }*/
        try {

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);
            spDTO.setPublicTitle(StConverter
                .convertToSt(gtdDTO.getPublicTitle()));
            spDTO.setAcronym(StConverter
                .convertToSt(gtdDTO.getAcronym()));
            spDTO.setOfficialTitle(StConverter
                .convertToSt(gtdDTO.getOfficialTitle()));
            spDTO.setPublicDescription(StConverter
                .convertToSt(gtdDTO.getPublicDescription()));
            spDTO.setScientificDescription(StConverter
                .convertToSt(gtdDTO.getScientificDescription()));
            spDTO.setKeywordText(StConverter
                .convertToSt(gtdDTO.getKeywordText()));
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(
                    spDTO);
            copy(spDTO);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            //return "edit";
        }
        return "edit";
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
