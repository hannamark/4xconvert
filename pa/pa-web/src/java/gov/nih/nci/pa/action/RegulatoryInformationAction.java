package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
import gov.nih.nci.pa.dto.RegulatoryAuthorityWebDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
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
    //private String selectedAuthOrg = null;
    private RegulatoryAuthorityWebDTO webDTO = new RegulatoryAuthorityWebDTO();
    //
    private List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList();
    private String selectedRegAuth = null;

    /**
     * Method to save the regulatory information to the database.
     * 
     * @return String success or failure
     */
    public String update() {
        validateForm();
        String orgName;
        try {
            if (hasFieldErrors()) {
                return query();
            }            
            orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(Long.valueOf(selectedRegAuth),
                    "RegulatoryAuthority");
            String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                    Long.valueOf(getLst()), "Country");
            webDTO.setTrialOversgtAuthOrgName(orgName);
            webDTO.setTrialOversgtAuthCountry(countryName);
            String user = ServletActionContext.getRequest().getRemoteUser();
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            // Update InterventionalSP
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            if (webDTO.getSection801Indicator().equals("")) {
                spDTO.setSection801Indicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.valueOf(webDTO.getSection801Indicator())));
            }
            if (webDTO.getFdaRegulatedInterventionIndicator().equals("")) {
                spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                        .getFdaRegulatedInterventionIndicator())));
            }
            if (webDTO.getDelayedPostingIndicator().equals("")) {
                spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setDelayedpostingIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                        .getDelayedPostingIndicator())));
            }
            if (webDTO.getDataMonitoringIndicator().equals("")) {
                spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(null));
            } else {
                spDTO.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(Boolean.valueOf(webDTO
                        .getDataMonitoringIndicator())));
            }
            PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
            // Update StudyRegulatoryAuthority
            StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                    .getByStudyProtocol(studyProtocolIi);
            StudyRegulatoryAuthorityDTO sraDTO = new StudyRegulatoryAuthorityDTO();
            sraDTO.setStudyProtocolIdentifier(studyProtocolIi);
            sraDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(selectedRegAuth));
            if (sraFromDatabaseDTO == null) {
                PaRegistry.getStudyRegulatoryAuthorityService().create(sraDTO);
            } else {
                sraFromDatabaseDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(selectedRegAuth));
                PaRegistry.getStudyRegulatoryAuthorityService().update(sraFromDatabaseDTO);
            }
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            return query();
        } catch (PAException e) {            
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return query();
        }
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
                    .getByStudyProtocol(studyProtocolIi);
            //on error page if country and reg auth are chosen
            if (getSelectedRegAuth() != null) {
                regIdAuthOrgList = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                        Long.valueOf(getLst()));                
                setSelectedRegAuth(getSelectedRegAuth());
            }
            countryList = PaRegistry.getRegulatoryInformationService().getDistinctCountryNames();
            if (authorityDTO != null) { // load values from database
                StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = PaRegistry.getStudyRegulatoryAuthorityService()
                        .getByStudyProtocol(studyProtocolIi);
                StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
                if (spDTO.getSection801Indicator().getValue() != null) {
                    webDTO.setSection801Indicator(BlConverter.convertToString(spDTO.getSection801Indicator()));
                }
                if (spDTO.getFdaRegulatedIndicator().getValue() != null) {
                    webDTO.setFdaRegulatedInterventionIndicator(BlConverter.convertToString(spDTO
                            .getFdaRegulatedIndicator()));
                }
                if (spDTO.getDelayedpostingIndicator().getValue() != null) {
                    webDTO.setDelayedPostingIndicator(BlConverter.convertToString(spDTO.getDelayedpostingIndicator()));
                }
                if (spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue() != null) {
                    webDTO.setDataMonitoringIndicator((BlConverter.convertToString(spDTO
                            .getDataMonitoringCommitteeAppointedIndicator())));
                }
                Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
                List<Long> regInfo = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityInfo(sraId);
                setLst(regInfo.get(1).toString());
                //set selected the name of the regulatory authority chosen
                regIdAuthOrgList = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                        Long.valueOf(regInfo.get(1).toString()));
                setSelectedRegAuth(regInfo.get(0).toString());
            } 
            ispDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            return SUCCESS;
        }
        return SUCCESS;
    }

    private void validateForm() {
        if (!PAUtil.isNotNullOrNotEmpty(getLst())) {
            addFieldError("lst", "Country is required field");
        }
        if (!PAUtil.isNotNullOrNotEmpty(getSelectedRegAuth())) {            
            addFieldError("selectedRegAuth", "Oversight Authority is required field");
        }
        
        if (!PAUtil.isNotNullOrNotEmpty(webDTO.getFdaRegulatedInterventionIndicator())) {
            addFieldError("webDTO.fdaRegulatedInterventionIndicator", 
                    "FDA Regulated Intervention Indicator is required field");
        }
    }
    
    /**
     * @return String success or failure
     */
    public String getRegAuthoritiesList() {
        try {
            String countryId = ServletActionContext.getRequest().getParameter("countryid");
            if (!countryId.equals("")) {
                regIdAuthOrgList = PaRegistry.getRegulatoryInformationService().getRegulatoryAuthorityNameId(
                    Long.valueOf(countryId));
            } else {
                RegulatoryAuthOrgDTO defaultVal = new RegulatoryAuthOrgDTO();
                defaultVal.setName("-Select Country-");
                regIdAuthOrgList.add(defaultVal);
            }

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
     * @param countryList
     *            the countryList to set
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
     * @param lst
     *            the lst to set
     */
    public void setLst(String lst) {
        this.lst = lst;
    }



    /**
     * @return the webDTO
     */
    public RegulatoryAuthorityWebDTO getWebDTO() {
        return webDTO;
    }

    /**
     * @param webDTO
     *            the webDTO to set
     */
    public void setWebDTO(RegulatoryAuthorityWebDTO webDTO) {
        this.webDTO = webDTO;
    }

    /**
     * @return the regIdAuthOrgList
     */
    public List<RegulatoryAuthOrgDTO> getRegIdAuthOrgList() {
        return regIdAuthOrgList;
    }

    /**
     * @param regIdAuthOrgList
     *            the regIdAuthOrgList to set
     */
    public void setRegIdAuthOrgList(List<RegulatoryAuthOrgDTO> regIdAuthOrgList) {
        this.regIdAuthOrgList = regIdAuthOrgList;
    }

    /**
     * @return the selectedRegAuth
     */
    public String getSelectedRegAuth() {
        return selectedRegAuth;
    }

    /**
     * @param selectedRegAuth the selectedRegAuth to set
     */
    public void setSelectedRegAuth(String selectedRegAuth) {
        this.selectedRegAuth = selectedRegAuth;
    }
}
