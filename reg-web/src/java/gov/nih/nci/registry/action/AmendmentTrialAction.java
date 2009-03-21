package gov.nih.nci.registry.action;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDTO;
import gov.nih.nci.pa.dto.TrialFundingDTO;
import gov.nih.nci.pa.dto.TrialIndIdeDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.TrialUtil;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Vrushali
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" , "PMD" })
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
    //form variable name
    private String resPartyContactFullName = null;
    private TrialIndIdeDTO indDTO = new TrialIndIdeDTO();
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
        //clear the session
        removeSessionAttributes();
        try {
            Ii studyProtocolIi = IiConverter.convertToIi(pId);
            populateDTO(studyProtocolIi);
            selectedLeadOrg = new OrganizationDTO();
            selectedLeadOrg.setName(EnOnConverter.convertToEnOn(trialDTO.getLeadOrganizationName()));
            selectedLeadOrg.setIdentifier(IiConverter.convertToIi(trialDTO.getLeadOrganizationIdentifier()));
            ServletActionContext.getRequest().getSession().setAttribute("PoLeadOrg", selectedLeadOrg);
            PersonDTO perDto = new PersonDTO();
            poLeadPiFullName = trialDTO.getPiName();
            perDto.setIdentifier(IiConverter.convertToIi(trialDTO.getPiIdentifier()));
            perDto.setName(RemoteApiUtil.convertToEnPn(trialDTO.getPiName(), null, null, null, null));
            ServletActionContext.getRequest().getSession().setAttribute("PoLeadPI", perDto);
            resPartyContactFullName = trialDTO.getResponsiblePersonName();
            //this to be removed when dto.variable name will be same as include jsp's form variable
            selectedSponsor = new OrganizationDTO();
            selectedSponsor.setIdentifier(IiConverter.convertToIi(trialDTO.getSponsorIdentifier()));
            selectedSponsor.setName(EnOnConverter.convertToEnOn(trialDTO.getSponsorName()));
            ServletActionContext.getRequest().getSession().setAttribute("Sponsorselected", selectedSponsor);
            //Copy to form variable 
            selectedSummary4Sponsor = new OrganizationDTO();
            selectedSummary4Sponsor.setIdentifier(IiConverter.convertToIi(trialDTO.getSponsorIdentifier()));
            selectedSummary4Sponsor.setName(EnOnConverter.convertToEnOn(trialDTO.getSponsorName()));
            if (!trialDTO.getIndDtos().isEmpty()) {
                ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    trialDTO.getIndDtos());
            }
            if (!trialDTO.getFundingDtos().isEmpty()) {
                ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    trialDTO.getFundingDtos());
            }
            // query the trial documents
            List<DocumentDTO> documentISOList = RegistryServiceLocator.getDocumentService()
                    .getDocumentsByStudyProtocol(studyProtocolIi);
            // List <TrialDocumentWebDTO> documentList;
            if (!(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }

            LOG.info("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }
    private void populateDTO(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        StudyProtocolQueryDTO spqDto = RegistryServiceLocator.getProtocolQueryService().
                getTrialSummaryByStudyProtocolId(
                    Long.valueOf(studyProtocolIi.getExtension()));
        CorrelationUtils cUtils = new CorrelationUtils();
        TrialUtil util = new TrialUtil();
        util.copy(spDTO, trialDTO);
        util.copy(spqDto, trialDTO);
        util.copyLO(cUtils.getPAOrganizationByIndetifers(spqDto.getLeadOrganizationId(), null), trialDTO);
        util.copyPI(cUtils.getPAPersonByIndetifers(spqDto.getPiId(), null), trialDTO);
        util.copyResponsibleParty(studyProtocolIi, trialDTO);
        util.copySponsor(studyProtocolIi, trialDTO);
        util.copyNctNummber(studyProtocolIi, trialDTO);
        util.copySummaryFour(RegistryServiceLocator.getStudyResourcingService().
                    getsummary4ReportedResource(studyProtocolIi), trialDTO);
        //Copy IND's
        List<StudyIndldeDTO> studyIndldeDTOList = RegistryServiceLocator.getStudyIndldeService()
            .getByStudyProtocol(studyProtocolIi);
        if (!(studyIndldeDTOList.isEmpty())) {
             util.copyINDIDEList(studyIndldeDTOList, trialDTO);
        }
        // query the study grants
        List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                    .getstudyResourceByStudyProtocol(studyProtocolIi);
        if (!(isoList.isEmpty())) {
            util.copyGrantList(isoList, trialDTO);
        }
        //copy the reason for trial
        StudyOverallStatusDTO sosDto = null;
        List<StudyOverallStatusDTO> sosList = RegistryServiceLocator.getStudyOverallStatusService()
                    .getCurrentByStudyProtocol(studyProtocolIi);
        if (!sosList.isEmpty()) {
            sosDto = sosList.get(0);
        }
        if (sosDto != null) {
            trialDTO.setReason(StConverter.convertToString(sosDto.getReasonText()));
        } else {
            trialDTO.setReason(null);
        }
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

    /**
     * @return the resPartyContactFullName
     */
    public String getResPartyContactFullName() {
        return resPartyContactFullName;
    }

    /**
     * @param resPartyContactFullName the resPartyContactFullName to set
     */
    public void setResPartyContactFullName(String resPartyContactFullName) {
        this.resPartyContactFullName = resPartyContactFullName;
    }
    
    /**
     * @return the indDTO
     */
    public TrialIndIdeDTO getIndDTO() {
        return indDTO;
    }

    /**
     * @param indDTO the indDTO to set
     */
    public void setIndDTO(TrialIndIdeDTO indDTO) {
        this.indDTO = indDTO;
    }

    /**
     * Sets the ind ide information in the collection.
     * 
     * @return result
     */
    public String addIdeIndIndicator() {
        String number = ServletActionContext.getRequest().getParameter("number");
        String grantor = ServletActionContext.getRequest().getParameter("grantor");
        String programCode = ServletActionContext.getRequest().getParameter("programcode");
        String expandedAccess = ServletActionContext.getRequest().getParameter("expandedaccess");
        String expandedAccessType = ServletActionContext.getRequest().getParameter("expandedaccesstype");
        String holderType = ServletActionContext.getRequest().getParameter("holdertype");
        String indIde = ServletActionContext.getRequest().getParameter("indIde");
        // TODO DO A NOT NULL CHECK HERE -Harsha
        if (number.equals("") && grantor.equals("") && programCode.equals("") && expandedAccess.equals("No")
                && expandedAccessType.equals("") && holderType.equals("") && indIde.equals("undefined")) {
            return SUCCESS;
        }
        TrialIndIdeDTO indIdeHolder = new TrialIndIdeDTO();
        indIdeHolder.setExpandedAccess(expandedAccess.equals("") ? "-" : expandedAccess);
        indIdeHolder.setExpandedAccessType(expandedAccessType.equals("") ? "-" : expandedAccessType);
        indIdeHolder.setGrantor(grantor.equals("") ? "-" : grantor);
        indIdeHolder.setHolderType(holderType.equals("") ? "-" : holderType);
        indIdeHolder.setNumber(number.equals("") ? "-" : number);
        indIdeHolder.setProgramCode(programCode.equals("") ? "-" : programCode);
        indIdeHolder.setIndIde(indIde.equals("") ? "-" : indIde);
        indIdeHolder.setRowId(UUID.randomUUID().toString());
        ArrayList<TrialIndIdeDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                trialDTO.getIndDtos().add(sessionList.get(i));
            }
            trialDTO.getIndDtos().add(indIdeHolder);
            sessionList.add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, sessionList);
        } else {
            trialDTO.getIndDtos().add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, trialDTO.getIndDtos());
        }
        // ideIndList.setList(ideInd);
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String deleteIndIde() {
        String rowid = ServletActionContext.getRequest().getParameter("uuid");
        ArrayList<TrialIndIdeDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
        TrialIndIdeDTO holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (TrialIndIdeDTO) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        trialDTO.getIndDtos().clear();
        for (int i = 0; i < sessionList.size(); i++) {
            trialDTO.getIndDtos().add(sessionList.get(i));
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, trialDTO.getIndDtos());
        // ideIndList.setList(ideInd);
        return "display_ideind";
    }

    /**
     * 
     * @return result
     */
    public String addGrant() {
        String fundingMechanismCode = ServletActionContext.getRequest().getParameter("fundingMechanismCode");
        String nihInstitutionCode = ServletActionContext.getRequest().getParameter("nihInstitutionCode");
        String serialNumber = ServletActionContext.getRequest().getParameter("serialNumber");
        String nciDivisionProgramCode = ServletActionContext.getRequest().getParameter("nciDivisionProgramCode");
        // TODO DO A NOT NULL CHECK HERE -Harsha
        TrialFundingDTO grantHolder = new TrialFundingDTO();
        grantHolder.setFundingMechanism(fundingMechanismCode);
        grantHolder.setInstituteCode(nihInstitutionCode);
        grantHolder.setSerialNumber(serialNumber);
        grantHolder.setNciDivisionProgramCode(nciDivisionProgramCode);
        grantHolder.setRowId(UUID.randomUUID().toString());
        ArrayList<TrialFundingDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.GRANT_LIST);
        LOG.info("trialDTO.getFundingDtos()" + trialDTO.getFundingDtos().isEmpty());
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                trialDTO.getFundingDtos().add(sessionList.get(i));
            }
            trialDTO.getFundingDtos().add(grantHolder);
            sessionList.add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, sessionList);
        } else {
            trialDTO.getFundingDtos().add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, 
                    trialDTO.getFundingDtos());
        }
        // ideIndList.setList(ideInd);
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String deleteGrant() {
        String rowid = ServletActionContext.getRequest().getParameter("uuid");
        ArrayList<TrialFundingDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.GRANT_LIST);
        TrialFundingDTO holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (TrialFundingDTO) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        trialDTO.getFundingDtos().clear();
        for (int i = 0; i < sessionList.size(); i++) {
            trialDTO.getFundingDtos().add(sessionList.get(i));
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, trialDTO.getFundingDtos());
        // ideIndList.setList(ideInd);
        return "display_grants";
    }
    /**
     * 
     */
    private void removeSessionAttributes() {
        ServletActionContext.getRequest().getSession().removeAttribute("indIdeList");
        ServletActionContext.getRequest().getSession().removeAttribute("grantList");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadOrg");
        ServletActionContext.getRequest().getSession().removeAttribute("PoLeadPI");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSponsor");
        ServletActionContext.getRequest().getSession().removeAttribute("Sponsorselected");
        ServletActionContext.getRequest().getSession().removeAttribute("PoResponsibleContact");
        ServletActionContext.getRequest().getSession().removeAttribute("PoSummary4Sponsor");
    }
    /**
     * Clears the session variables and redirect to search.
     * @return s
     */
    public String cancel() {
        removeSessionAttributes();
        return "redirect_to_search";
    }
    /**
     * clears the session variables and shows the review page.
     * @return s
     */
    public String save() {
      //add the IndIde,FundingList,protocolDocument,leadOrgId and LeadOrgName
        trialDTO.setTrialType("Interventional");
        OrganizationDTO selectLeadOrg = (OrganizationDTO) ServletActionContext.getRequest().
        getSession().getAttribute("PoLeadOrg");
        if (selectLeadOrg != null) {
            trialDTO.setLeadOrganizationIdentifier(selectLeadOrg.getIdentifier().getExtension());
            trialDTO.setLeadOrganizationName(selectLeadOrg.getName().getPart().get(0).getValue());
        }
        //sponsor
        OrganizationDTO selectsponsorOrg = (OrganizationDTO) ServletActionContext.getRequest().
        getSession().getAttribute("PoSponsor");
        if (selectsponsorOrg != null) {
            trialDTO.setSponsorIdentifier(selectsponsorOrg.getIdentifier().getExtension());
            trialDTO.setSponsorName(selectsponsorOrg.getName().getPart().get(0).getValue());
        }
        //pi
        PersonDTO selectedPerson = (PersonDTO) ServletActionContext.getRequest().
        getSession().getAttribute("PoLeadPI");
        if (selectedPerson != null) {
            trialDTO.setPiIdentifier(selectedPerson.getIdentifier().getExtension());
            trialDTO.setPiName(selectedPerson.getName().getPart().get(0).getValue());
        }
        //resPartyContactFullName 
        PersonDTO respContact = (PersonDTO) ServletActionContext.getRequest().getSession()
                    .getAttribute("PoResponsibleContact");
        if (respContact != null) {
            trialDTO.setResponsiblePersonName(respContact.getName().getPart().get(0).getValue());
            trialDTO.setResponsiblePersonIdentifier(respContact.getIdentifier().getExtension());
        }
        //selectSummary4Sponsor
        OrganizationDTO selectSummary4Sponsor = (OrganizationDTO) ServletActionContext.getRequest().
        getSession().getAttribute("PoSummary4Sponsor");
        if (selectSummary4Sponsor != null) {
            trialDTO.setSummaryFourOrgIdentifier(selectSummary4Sponsor.getIdentifier().getExtension());
            trialDTO.setSummaryFourOrgName(selectSummary4Sponsor.getName().toString());
        }
        ArrayList<TrialIndIdeDTO> sessionList = (ArrayList) ServletActionContext.getRequest().getSession()
        .getAttribute(Constants.INDIDE_LIST);
        if (sessionList != null) {
            for (int i = 0; i < sessionList.size(); i++) {
                trialDTO.getIndDtos().add(sessionList.get(i));
            }
        }
        ArrayList<TrialFundingDTO> grantList = (ArrayList) ServletActionContext.getRequest().getSession()
        .getAttribute(Constants.GRANT_LIST);
        if (sessionList != null) {
            for (int i = 0; i < grantList.size(); i++) {
                trialDTO.getFundingDtos().add(grantList.get(i));
            }
        }
        removeSessionAttributes();
        LOG.info("Calling the review page...");
        return "review";    
    }
}