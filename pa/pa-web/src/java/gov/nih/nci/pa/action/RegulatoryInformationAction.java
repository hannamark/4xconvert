package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.NoStudyProtocolFoundException;
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
    private InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
    private List countryList = new ArrayList();
    private String lst = null;
    private String selectedAuthOrg = null;
    private RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();

    /**
     * Method to save the regulatory information to the database.
     * 
     * @return String success or failure
     * @throws Exception on error
     */
    public String update() throws Exception {
        String orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                Long.valueOf(selectedAuthOrg), "RegulatoryAuthority");
        String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(Long.valueOf(getLst()),
                "Country");
        webDTO.setTrialOversgtAuthOrgName(orgName);
        webDTO.setTrialOversgtAuthCountry(countryName);
        String user = ServletActionContext.getRequest().getRemoteUser();
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
        //Update InterventionalSP
        InterventionalStudyProtocolDTO ispFromDatabaseDTO = PaRegistry.getStudyProtocolService()
                .getInterventionalStudyProtocol(studyProtocolIi);
        ispFromDatabaseDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getSection801Indicator())));
        ispFromDatabaseDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getFdaRegulatedInterventionIndicator())));
        ispFromDatabaseDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getDelayedPostingIndicator())));
        ispFromDatabaseDTO.setDataMonitoringCommitteInd(BlConverter.convertToBl(Boolean.valueOf(webDTO
                .getDataMonitoringIndicator())));
        ispFromDatabaseDTO.setIndIdeIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getIdeTrialIndicator())));
        ispFromDatabaseDTO.setUserLastUpdated(StConverter.convertToSt(user));
        PaRegistry.getStudyProtocolService().updateInterventionalStudyProtocol(ispFromDatabaseDTO);
        
        
        // Update StudyRegulatoryAuthority
        StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                .getStudyRegulatoryAuthority(studyProtocolIi);
        StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
        sraDTO.setProtocolId(studyProtocolIi);
        sraDTO.setRegulatoryAuthorityId(IiConverter.convertToIi(selectedAuthOrg));
        sraDTO.setUserLastUpdated(StConverter.convertToSt(user));
        if (sraFromDatabaseDTO == null) {
            PaRegistry.getStudyRegulatoryAuthorityService().createStudyRegulatoryAuthority(sraDTO);
        } else {
            sraFromDatabaseDTO.setRegulatoryAuthorityId(IiConverter.convertToIi(selectedAuthOrg));
            PaRegistry.getStudyRegulatoryAuthorityService().updateStudyRegulatoryAuthority(sraFromDatabaseDTO);
        }
       // webDTO.setFdaRegulatedInterventionIndicator( )
        return SUCCESS;
    }

    /**
     * 
     * @return String success or failure
     */
    public String query() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            StudyRegulatoryAuthorityDTO authorityDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                    .getStudyRegulatoryAuthority(studyProtocolIi);
            //
            countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
            if (authorityDTO != null) { // load values from database
                StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                        .getStudyRegulatoryAuthority(studyProtocolIi);
                InterventionalStudyProtocolDTO ispFromDatabaseDTO = PaRegistry.getStudyProtocolService()
                        .getInterventionalStudyProtocol(studyProtocolIi);
                if (ispFromDatabaseDTO.getSection801Indicator().getValue() != null) {
                    webDTO.setSection801Indicator(BlConverter.convertToString(ispFromDatabaseDTO
                            .getSection801Indicator()));
                }
                if (ispFromDatabaseDTO.getFdaRegulatedIndicator().getValue() != null) {
                    webDTO.setFdaRegulatedInterventionIndicator(BlConverter.convertToString(ispFromDatabaseDTO
                            .getFdaRegulatedIndicator()));
                }
                if (ispFromDatabaseDTO.getDelayedpostingIndicator().getValue() != null) {
                    webDTO.setDelayedPostingIndicator(BlConverter.convertToString(ispFromDatabaseDTO
                            .getDelayedpostingIndicator()));
                }
                if (ispFromDatabaseDTO.getIndIdeIndicator().getValue() != null) {
                    webDTO.setIdeTrialIndicator((BlConverter.convertToString(ispFromDatabaseDTO
                            .getIndIdeIndicator())));
                }        
                if (ispFromDatabaseDTO.getDataMonitoringCommitteInd().getValue() != null) {
                    webDTO.setDataMonitoringIndicator((BlConverter.convertToString(ispFromDatabaseDTO
                            .getDataMonitoringCommitteInd())));
                }                 
                Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityId().getExtension());
                List<Long> regInfo = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityInfo(sraId);
                setLst(regInfo.get(1).toString());
            }
            ispDTO = PaRegistry.getStudyProtocolService().getInterventionalStudyProtocol(studyProtocolIi);
        } catch (NoStudyProtocolFoundException nspfe) {
            return SUCCESS;
        } catch (PAException e) {
            return SUCCESS;
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
