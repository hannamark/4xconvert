package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Harsha
 * @since 08/05/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD")
public class RegulatoryInformationAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final String VIEW_PAGE = "view_page";
    private StudyProtocolDTO ispDTO = new StudyProtocolDTO();
    private List countryList = new ArrayList();
    private String lst = null;
    private String selectedAuthOrg = null;
    private RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();

    /**
     * Method to save the regulatory information to the database.
     * 
     * @return String success or failure
     */
    public String update()  {
        String orgName;
        try {
            orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                    Long.valueOf(selectedAuthOrg), "RegulatoryAuthority");
        String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(Long.valueOf(getLst()),
                "Country");
        webDTO.setTrialOversgtAuthOrgName(orgName);
        webDTO.setTrialOversgtAuthCountry(countryName);
        String user = ServletActionContext.getRequest().getRemoteUser();
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        //Update InterventionalSP
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService()
                .getStudyProtocol(studyProtocolIi);
        
        spDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getSection801Indicator())));
        spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getFdaRegulatedInterventionIndicator())));
        spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getDelayedPostingIndicator())));
        spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getDataMonitoringIndicator())));
        
        PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
        
        
        // Update StudyRegulatoryAuthority
        StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                .getByStudyProtocol(studyProtocolIi);
        StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
        sraDTO.setStudyProtocolIdentifier(studyProtocolIi);
        sraDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(selectedAuthOrg));
        if (sraFromDatabaseDTO == null) {
            PaRegistry.getStudyRegulatoryAuthorityService().create(sraDTO);
        } else {
            sraFromDatabaseDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(selectedAuthOrg));
            PaRegistry.getStudyRegulatoryAuthorityService().update(sraFromDatabaseDTO);
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
       } catch (PAException e) {
           addActionError(e.getMessage());
           ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
           return VIEW_PAGE;
        }
        
        return VIEW_PAGE;
    }

    /**
     * 
     * @return String success or failure
     */
    public String query() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            StudyRegulatoryAuthorityDTO authorityDTO = PaRegistry.getStudyRegulatoryAuthorityService().
                getByStudyProtocol(studyProtocolIi);
            //
            countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
            if (authorityDTO != null) { // load values from database
                StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                        .getByStudyProtocol(studyProtocolIi);
      
                StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService()
                        .getStudyProtocol(studyProtocolIi);
                if (spDTO.getSection801Indicator().getValue() != null) {
                    webDTO.setSection801Indicator(BlConverter.convertToString(spDTO
                            .getSection801Indicator()));
                }
                if (spDTO.getFdaRegulatedIndicator().getValue() != null) {
                    webDTO.setFdaRegulatedInterventionIndicator(BlConverter.convertToString(spDTO
                            .getFdaRegulatedIndicator()));
                }
                if (spDTO.getDelayedpostingIndicator().getValue() != null) {
                    webDTO.setDelayedPostingIndicator(BlConverter.convertToString(spDTO
                            .getDelayedpostingIndicator()));
                }
                if (spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue() != null) {
                    webDTO.setDataMonitoringIndicator((BlConverter.convertToString(spDTO
                            .getDataMonitoringCommitteeAppointedIndicator())));
                }                 
                Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
                List<Long> regInfo = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityInfo(sraId);
                setLst(regInfo.get(1).toString());
            }
            ispDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return VIEW_PAGE;
        }
        return SUCCESS;
    }

    /**
     * @return the countryList
     */
    public List getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the lst
     */
    public String getLst() {
        return lst;
    }

    /**
     * @param lst the lst to set
     */
    public void setLst(String lst) {
        this.lst = lst;
    }

    /**
     * @return the selectedAuthOrg
     */
    public String getSelectedAuthOrg() {
        return selectedAuthOrg;
    }

    /**
     * @param selectedAuthOrg the selectedAuthOrg to set
     */
    public void setSelectedAuthOrg(String selectedAuthOrg) {
        this.selectedAuthOrg = selectedAuthOrg;
    }

    /**
     * @return the webDTO
     */
    public RegulatoryAuthorityWebDTO getWebDTO() {
        return webDTO;
    }

    /**
     * @param webDTO the webDTO to set
     */
    public void setWebDTO(RegulatoryAuthorityWebDTO webDTO) {
        this.webDTO = webDTO;
    }
}
