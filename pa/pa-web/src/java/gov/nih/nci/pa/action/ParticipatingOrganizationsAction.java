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
    
    private String facOrganizationName;
    private String facNCINumber;     
    private String facCity;   
    private String facState;  
    private String facZipPostalCode;
    private String facCountry;    
    private String facLeadOrganization;
    private String facSiteRecruitmentStatus;    
    private String facSiteRecruitmentStatusDate;
    
    /**
     * @author hreinhart
     *
     */
    private static class PseudoPo {
        private static final long ID = 99L;
        public static Ii poOrgIi = IiConverter.convertToIi(ID);
        public static Ii poHcfIi = IiConverter.convertToIi(ID);

        static HealthCareFacilityDTO getHealthCareFacility(Ii orgIi) {
            HealthCareFacilityDTO hfDto = new HealthCareFacilityDTO();
            hfDto.setIi(poHcfIi);
            hfDto.setOrganizationIi(poOrgIi);
            return hfDto;
        }
        static Ii poCreateHealthCareFacility(HealthCareFacilityDTO newHcf) {
            return poHcfIi;
        }
    }

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
        // Call popup

        // Naveen's pseudo code
        Ii poOrganizationIi = PseudoPo.poOrgIi;
        HealthCareFacilityDTO hcf = PseudoPo.getHealthCareFacility(poOrganizationIi);
        Ii poHealthCareFacilityIi = hcf.getIi();
        if (PAUtil.isIiNull(poHealthCareFacilityIi)) {
            poHealthCareFacilityIi = PseudoPo.poCreateHealthCareFacility(hcf);
        }

        // load form beans
        setFacOrganizationName("Joe's Bar and Grill");
        setFacNCINumber("NCI009");
        setFacCity("Ocean City");
        setFacState("MD");
        setFacZipPostalCode("20639");
        setFacCountry("USA");
        setFacLeadOrganization(null);
        setFacSiteRecruitmentStatus(null);
        setFacSiteRecruitmentStatusDate(null);

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

    /**
     * @return the facOrganizationName
     */
    public String getFacOrganizationName() {
        return facOrganizationName;
    }

    /**
     * @param facOrganizationName the facOrganizationName to set
     */
    public void setFacOrganizationName(String facOrganizationName) {
        this.facOrganizationName = facOrganizationName;
    }

    /**
     * @return the facNCINumber
     */
    public String getFacNCINumber() {
        return facNCINumber;
    }

    /**
     * @param facNCINumber the facNCINumber to set
     */
    public void setFacNCINumber(String facNCINumber) {
        this.facNCINumber = facNCINumber;
    }

    /**
     * @return the facCity
     */
    public String getFacCity() {
        return facCity;
    }

    /**
     * @param facCity the facCity to set
     */
    public void setFacCity(String facCity) {
        this.facCity = facCity;
    }

    /**
     * @return the facState
     */
    public String getFacState() {
        return facState;
    }

    /**
     * @param facState the facState to set
     */
    public void setFacState(String facState) {
        this.facState = facState;
    }

    /**
     * @return the facZipPostalCode
     */
    public String getFacZipPostalCode() {
        return facZipPostalCode;
    }

    /**
     * @param facZipPostalCode the facZipPostalCode to set
     */
    public void setFacZipPostalCode(String facZipPostalCode) {
        this.facZipPostalCode = facZipPostalCode;
    }

    /**
     * @return the facCountry
     */
    public String getFacCountry() {
        return facCountry;
    }

    /**
     * @param facCountry the facCountry to set
     */
    public void setFacCountry(String facCountry) {
        this.facCountry = facCountry;
    }

    /**
     * @return the facLeadOrganization
     */
    public String getFacLeadOrganization() {
        return facLeadOrganization;
    }

    /**
     * @param facLeadOrganization the facLeadOrganization to set
     */
    public void setFacLeadOrganization(String facLeadOrganization) {
        this.facLeadOrganization = facLeadOrganization;
    }

    /**
     * @return the facSiteRecruitmentStatus
     */
    public String getFacSiteRecruitmentStatus() {
        return facSiteRecruitmentStatus;
    }

    /**
     * @param facSiteRecruitmentStatus the facSiteRecruitmentStatus to set
     */
    public void setFacSiteRecruitmentStatus(String facSiteRecruitmentStatus) {
        this.facSiteRecruitmentStatus = facSiteRecruitmentStatus;
    }

    /**
     * @return the facSiteRecruitmentStatusDate
     */
    public String getFacSiteRecruitmentStatusDate() {
        return facSiteRecruitmentStatusDate;
    }

    /**
     * @param facSiteRecruitmentStatusDate the facSiteRecruitmentStatusDate to set
     */
    public void setFacSiteRecruitmentStatusDate(String facSiteRecruitmentStatusDate) {
        this.facSiteRecruitmentStatusDate = PAUtil.normalizeDateString(facSiteRecruitmentStatusDate);
    }

}
