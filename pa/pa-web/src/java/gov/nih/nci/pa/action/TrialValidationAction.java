package gov.nih.nci.pa.action;
      
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
      
/**
* @author Hong Gao
*
*/
public class TrialValidationAction extends ActionSupport {

    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    
    /**  
     * @return res
     */
    public String query() {
        try {        

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);             
                
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            StudyProtocolQueryDTO spqDto = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession().
                getAttribute(Constants.TRIAL_SUMMARY);
            CorrelationUtils cUtils = new CorrelationUtils();
            
            copy(spDTO);
            copy(spqDto);
            copyLO(cUtils.getPAOrganizationByIndetifers(spqDto.getLeadOrganizationId(), null));
            copyPI(cUtils.getPAPersonByIndetifers(spqDto.getPiId(), null));
            copySummaryFour(PaRegistry.getStudyResourcingService().getsummary4ReportedResource(studyProtocolIi));
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return "edit";
    }

    /**
     * 
     * @return String
     */
    public String update() {
        try {

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return "edit";
    }
    
    private void copy(StudyProtocolDTO spDTO) {        
        gtdDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        gtdDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        gtdDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        gtdDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        gtdDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
    }
    
    private void copy(StudyProtocolQueryDTO spqDTO) {
        gtdDTO.setLocalProtocolIdentifer(spqDTO.getLocalStudyProtocolIdentifier());
    }
    private void copyLO(Organization o) {
        gtdDTO.setLeadOrganizationIdentifer(o.getIdentifier());
        gtdDTO.setLeadOrganizationName(o.getName());
    }
    private void copyPI(Person p) {
        gtdDTO.setPiIdentifier(p.getIdentifier());
        gtdDTO.setPiName(p.getFullName());
    }
    private void copySummaryFour(StudyResourcingDTO srDTO) throws  PAException {
        if (srDTO == null) {
            return;
        }
        CorrelationUtils cUtils = new CorrelationUtils();
        Organization o = cUtils.getPAOrganizationByIndetifers(
                        Long.valueOf(srDTO.getOrganizationIdentifier().getExtension()), null);
        gtdDTO.setSummaryFourOrgIdentifer(o.getIdentifier());
        gtdDTO.setSummaryFourOrgName(o.getName());
        if (srDTO.getTypeCode() != null) {
            gtdDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
    }

    private void updateStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setOfficialTitle(StConverter.convertToSt(gtdDTO.getOfficialTitle()));
        spDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(gtdDTO.getPhaseCode()))); 
        spDTO.setPrimaryPurposeCode(
                CdConverter.convertToCd(PrimaryPurposeCode.getByCode(gtdDTO.getPrimaryPurposeCode())));
        spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(gtdDTO.getPrimaryPurposeOtherText()));
        spDTO.setPhaseOtherText(StConverter.convertToSt(gtdDTO.getPhaseOtherText()));
        PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
    }
    
    private void updateStudyParticipation(Ii studyProtocolIi) throws PAException {
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION));
        List<StudyParticipationDTO> srDtos = PaRegistry.getStudyParticipationService()
            .getByStudyProtocol(studyProtocolIi, spDto);
        if (srDtos == null || srDtos.isEmpty() || srDtos.size() > 1) {
            throw new PAException(" Lead Organization is either null , or more than one lead is found for a "  
                    + " given Study Protocol id = " +  studyProtocolIi.getExtension());
        }
        spDto = srDtos.get(0);
        OrganizationCorrelationServiceBean ocb = new OrganizationCorrelationServiceBean();
        Long ro = ocb.createResearchOrganizationCorrelations(gtdDTO.getLeadOrganizationIdentifer());
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getLeadOrganizationIdentifer()));
        spDto.setResearchOrganizationIi(IiConverter.convertToIi(ro));
        PaRegistry.getStudyParticipationService().update(spDto);
        
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