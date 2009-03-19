package gov.nih.nci.registry.action;


import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDTO;
import gov.nih.nci.pa.dto.TrialFundingDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.TrialUtil;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.organization.OrganizationDTO;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Vrushali
 */
public class AmendmentTrialAction extends ActionSupport implements ServletResponseAware {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(AmendmentTrialAction.class);
    private TrialDTO trialDTO = new TrialDTO();
    private TrialFundingDTO trialFundingDTO = new TrialFundingDTO();
    private OrganizationDTO selectedLeadOrg = null;
    private String poLeadPiFullName = null;
    private OrganizationDTO selectedSponsor = null;
    // Summary 4 Sponsor
    private OrganizationDTO selectedSummary4Sponsor = null;

    /**
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }
    /**
     * 
     * @return res
     */
    public String view() {
        String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
        try {
        Ii studyProtocolIi = IiConverter.convertToIi(pId);
        StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        StudyProtocolQueryDTO spqDto = RegistryServiceLocator.getProtocolQueryService().
            getTrialSummaryByStudyProtocolId(
                Long.valueOf(studyProtocolIi.getExtension()));
        
        CorrelationUtils cUtils = new CorrelationUtils();
        selectedLeadOrg = new OrganizationDTO();
        TrialUtil util = new TrialUtil();
        util.copy(spDTO, trialDTO);
        util.copy(spqDto, trialDTO);
        util.copyLO(cUtils.getPAOrganizationByIndetifers(spqDto.getLeadOrganizationId(), null), trialDTO);
        selectedLeadOrg.setName(EnOnConverter.convertToEnOn(trialDTO.getLeadOrganizationName()));
        selectedLeadOrg.setIdentifier(IiConverter.convertToIi(trialDTO.getLeadOrganizationIdentifier()));
        util.copyPI(cUtils.getPAPersonByIndetifers(spqDto.getPiId(), null), trialDTO);
        poLeadPiFullName = trialDTO.getPiName();
        util.copyResponsibleParty(studyProtocolIi, trialDTO);
        util.copySponsor(studyProtocolIi, trialDTO);
        selectedSponsor = new OrganizationDTO();
        selectedSponsor.setIdentifier(IiConverter.convertToIi(trialDTO.getSponsorIdentifier()));
        selectedSponsor.setName(EnOnConverter.convertToEnOn(trialDTO.getSponsorName()));
        util.copyNctNummber(studyProtocolIi, trialDTO);
        util.copySummaryFour(RegistryServiceLocator.getStudyResourcingService().
                getsummary4ReportedResource(studyProtocolIi), trialDTO);

        LOG.info("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            // e.printStackTrace();
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }
    /**
     * @return the trialDTO
     */
    public TrialDTO getTrialDTO() {
        return trialDTO;
    }
    /**
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(TrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }
    /**
     * @return the trialFundingDTO
     */
    public TrialFundingDTO getTrialFundingDTO() {
        return trialFundingDTO;
    }
    /**
     * @param trialFundingDTO the trialFundingDTO to set
     */
    public void setTrialFundingDTO(TrialFundingDTO trialFundingDTO) {
        this.trialFundingDTO = trialFundingDTO;
    }
    /**
     * @return the selectedLeadOrg
     */
    public OrganizationDTO getSelectedLeadOrg() {
        return selectedLeadOrg;
    }
    /**
     * @param selectedLeadOrg the selectedLeadOrg to set
     */
    public void setSelectedLeadOrg(OrganizationDTO selectedLeadOrg) {
        this.selectedLeadOrg = selectedLeadOrg;
    }
    
    /**
     * @return the poLeadPiFullName
     */
    public String getPoLeadPiFullName() {
        return poLeadPiFullName;
    }
    /**
     * @param poLeadPiFullName the poLeadPiFullName to set
     */
    public void setPoLeadPiFullName(String poLeadPiFullName) {
        this.poLeadPiFullName = poLeadPiFullName;
    }
    /**
     * @return the selectedSponsor
     */
    public OrganizationDTO getSelectedSponsor() {
        return selectedSponsor;
    }
    /**
     * @param selectedSponsor the selectedSponsor to set
     */
    public void setSelectedSponsor(OrganizationDTO selectedSponsor) {
        this.selectedSponsor = selectedSponsor;
    }
    /**
     * @return the selectedSummary4Sponsor
     */
    public OrganizationDTO getSelectedSummary4Sponsor() {
        return selectedSummary4Sponsor;
    }
    /**
     * @param selectedSummary4Sponsor the selectedSummary4Sponsor to set
     */
    public void setSelectedSummary4Sponsor(OrganizationDTO selectedSummary4Sponsor) {
        this.selectedSummary4Sponsor = selectedSummary4Sponsor;
    }
    
}