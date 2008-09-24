/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.HealthCareFacilityDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the participating organizations.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD")
public class ParticipatingOrganizationsAction extends ActionSupport 
        implements Preparable {
    private static final long serialVersionUID = 123412653L;

    private StudyProtocolServiceRemote sProService;
    private StudyParticipationServiceRemote sPartService;
    private PAHealthCareFacilityServiceRemote pahfService;
    private PAOrganizationServiceRemote paoService;

    private Ii spIi;
    private List<OrganizationWebDTO> organizationList;

    /** 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        sProService = PaRegistry.getStudyProtocolService();
        sPartService = PaRegistry.getStudyParticipationService();
        pahfService = PaRegistry.getPAHealthCareFacilityService();
        paoService = PaRegistry.getPAOrganizationService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
        .getRequest().getSession()
        .getAttribute(Constants.TRIAL_SUMMARY);

        spIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return Action result.
     * @throws Exception exception.
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return SUCCESS;
    }

    /**  
     * @return result
     * @throws Exception exception
     */
    public String create() throws Exception {
        clearErrorsAndMessages();
        enforceBusinessRules();
        if (hasActionErrors()) {
            return Action.SUCCESS;
        }
        if (!hasActionErrors()) {
//            addActionError("Create succeeded.");
        }
        loadForm();
        return Action.SUCCESS;
    }


    private void loadForm() throws Exception {
        organizationList = new ArrayList<OrganizationWebDTO>();
        List<StudyParticipationDTO> spList = sPartService.getStudyParticipationByStudyProtocol(spIi);
        for (StudyParticipationDTO sp : spList) {
            HealthCareFacilityDTO hf = pahfService.getHealthCareFacility(sp.getHealthcareFacilityIi());
            Organization orgBo = new Organization();
            orgBo.setId(IiConverter.convertToLong(hf.getOrganizationIi()));
            orgBo = paoService.getOrganizationByIndetifers(orgBo);
            OrganizationWebDTO orgWebDTO = new OrganizationWebDTO();
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            orgWebDTO.setRecruitmentStatus(CdConverter.convertCdToString(sp.getStatusCode()));
            orgWebDTO.setRecruitmentStatusDate(PAUtil.normalizeDateString(
                    TsConverter.convertToTimestamp(sp.getStatusDateRangeLow()).toString()));
            organizationList.add(orgWebDTO);
        }
    }

    /**
     * This method is used to enforce the business rules which are form specific or
     * based on an interaction between services.
     */
    private void enforceBusinessRules() {
    }

    /**
     * @return the organizationList
     */
    public List<OrganizationWebDTO> getOrganizationList() {
        return organizationList;
    }

    /**
     * @param organizationList the organizationList to set
     */
    public void setOrganizationList(List<OrganizationWebDTO> organizationList) {
        this.organizationList = organizationList;
    }

}
