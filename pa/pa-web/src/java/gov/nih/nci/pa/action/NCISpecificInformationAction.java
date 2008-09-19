package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author gnaveh
 * 
 */
@Validation
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class NCISpecificInformationAction extends ActionSupport {
    private static final Logger LOG = Logger.getLogger(NCISpecificInformationAction.class);
    private NCISpecificInformationWebDTO nciSpecificInformationWebDTO = new NCISpecificInformationWebDTO();
    private List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
    private String chosenOrg;

    /**
     * @return result
     */
    public String query() {
        LOG.info("Entering query");
        try {
            // Step 1 : get from StudyProtocol
            StudyProtocolDTO studyProtocolDTO = getStudyProtocol();
            // Step 2 : get from StudyResourcing
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            StudyResourcingDTO studyResourcingDTO = PaRegistry.getStudyResourcingService().getsummary4ReportedResource(
                    studyProtocolIi);
            nciSpecificInformationWebDTO = setNCISpecificDTO(studyProtocolDTO, studyResourcingDTO);
            if (studyResourcingDTO != null && studyResourcingDTO.getOrganizationIdentifier() != null) {
                // get the name of the organization using primary id
                Organization o = new Organization();
                o.setId(Long.valueOf(studyResourcingDTO.getOrganizationIdentifier().getExtension()));
                Organization org = PaRegistry.getPAOrganizationService().getOrganizationByIndetifers(o);
                // set the name
                nciSpecificInformationWebDTO.setOrganizationName(org.getName());
                // set the organization identifer
                nciSpecificInformationWebDTO.setOrganizationIi(org.getIdentifier());
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    /**
     * @return res
     */
    public String update() {
        boolean error = false;
        // Step1 : check for any errors
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())) {
            addActionError(getText("error.studyProtocol.accrualReportingMethodCode"));
            error = true;
        }
        if (!PAUtil.isNotNullOrNotEmpty(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())) {
            addActionError(getText("error.studyProtocol.summaryFourFundingCategoryCode"));
            error = true;
        }
        if (error) {
            return ERROR;
        }
        // Step2 : retrieve the studyprotocol
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        StudyResourcingDTO srDTO = new StudyResourcingDTO();
        try {
            // Step 0 : get the studyprotocol from database
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            // Step1 : update values to StudyProtocol
            spDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser())));
            spDTO.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode
                    .getByCode(nciSpecificInformationWebDTO.getAccrualReportingMethodCode())));
            // Step2 : update values to StudyResourcing
            srDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                    .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
            srDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser())));
            srDTO.setStudyProtocolIi(studyProtocolIi);
            // Step3: update studyprotocol
            spDTO = PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
            // Step 4: check if we have an organization for PO id
            String poIdentifer = nciSpecificInformationWebDTO.getOrganizationIi();
            Long orgId = null;
            if (PAUtil.isNotNullOrNotEmpty(poIdentifer)) {
                Organization o = new Organization();
                o.setIdentifier(poIdentifer);
                Organization org = PaRegistry.getPAOrganizationService().getOrganizationByIndetifers(o);
                if (org == null) {
                    // create a new org if its null
                    org = new Organization();
                    org.setIdentifier(poIdentifer);
                    org.setName(nciSpecificInformationWebDTO.getOrganizationName());
                    Organization crOrg = PaRegistry.getPAOrganizationService().createOrganization(org);
                    orgId = crOrg.getId();
                } else {
                    // get the org from the database
                    orgId = org.getId();
                }
            }
            // Step4 : find out if summary 4 records already exists
            StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService().getsummary4ReportedResource(
                    studyProtocolIi);
            if (summary4ResoureDTO == null) {
                // summary 4 record does not exist,so create a new one
                summary4ResoureDTO = new StudyResourcingDTO();
                summary4ResoureDTO.setStudyProtocolIi(studyProtocolIi);
                summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                        .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
                summary4ResoureDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest()
                        .getRemoteUser())));
                summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
                PaRegistry.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
            } else {
                // summary 4 record does exist,so so do an update
                summary4ResoureDTO.setStudyProtocolIi(studyProtocolIi);
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                        .getByCode(nciSpecificInformationWebDTO.getSummaryFourFundingCategoryCode())));
                summary4ResoureDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest()
                        .getRemoteUser())));
                summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
                PaRegistry.getStudyResourcingService().updateStudyResourcing(summary4ResoureDTO);
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        // nciSpecificInformationWebDTO = setNCISpecificDTO(spDTO , srDTO);
        return SUCCESS;
    }

    /**
     * 
     * @return nciSpecificInformationWebDTO
     */
    public NCISpecificInformationWebDTO getNciSpecificInformationWebDTO() {
        return nciSpecificInformationWebDTO;
    }

    /**
     * 
     * @param nciSpecificInformationWebDTO nciSpecificInformationWebDTO
     */
    public void setNciSpecificInformationWebDTO(NCISpecificInformationWebDTO nciSpecificInformationWebDTO) {
        this.nciSpecificInformationWebDTO = nciSpecificInformationWebDTO;
    }

    // @todo : catch and throw paexception
    private StudyProtocolDTO getStudyProtocol() {
        try {
            return PaRegistry.getStudyProtocolService().getStudyProtocol(
                    (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II));
        } catch (Exception e) {
            return null;
        }
    }

    private NCISpecificInformationWebDTO setNCISpecificDTO(StudyProtocolDTO spDTO, StudyResourcingDTO srDTO) {
        NCISpecificInformationWebDTO nciSpDTO = new NCISpecificInformationWebDTO();
        // Step2 : Assign the values to the action form
        if (spDTO != null && spDTO.getAccrualReportingMethodCode() != null) {
            nciSpDTO.setAccrualReportingMethodCode(spDTO.getAccrualReportingMethodCode().getCode());
        }
        if (srDTO != null) {
            if (srDTO.getTypeCode() != null) {
                nciSpDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
            }
            if (srDTO.getOrganizationIdentifier() != null) {
                nciSpDTO.setOrganizationIi(srDTO.getOrganizationIdentifier().getExtension());
            }
        }
        return nciSpDTO;
    }

    /**
     * 
     * @return result
     */
    public String getPoOrganizations() {
        String orgName = ServletActionContext.getRequest().getParameter("orgName");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setName(EnOnConverter.convertToEnOn(orgName));
        orgs = PaRegistry.getPoOrganizationEntityService().search(criteria);
        return SUCCESS;
    }

    /**
     * 
     * @return result
     */
    public String displayOrg() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        chosenOrg = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0).getName().getPart().get(0)
                .getValue();
        nciSpecificInformationWebDTO.setOrganizationName(chosenOrg);
        nciSpecificInformationWebDTO.setOrganizationIi(orgId);
        return SUCCESS;
    }

    /**
     * @return the orgs
     */
    public List<OrganizationDTO> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<OrganizationDTO> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the chosenOrg
     */
    public String getChosenOrg() {
        return chosenOrg;
    }

    /**
     * @param chosenOrg the chosenOrg to set
     */
    public void setChosenOrg(String chosenOrg) {
        this.chosenOrg = chosenOrg;
    }
}
