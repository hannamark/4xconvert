/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.PAHealthCareProviderDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.PAHealthCareFacilityDTO;
import gov.nih.nci.pa.iso.dto.PersonWebDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * Action class for viewing and editing the participating organizations.
 *
 * @author Hugh Reinhart, Harsha
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 *
 */
@Validation
@SuppressWarnings("PMD")
public class ParticipatingOrganizationsAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 123412653L;
    private static final int AD_CITY_IDX = 1;
    private static final int AD_COUNTRY_IDX = 3;
    private static final int AD_ZIP_IDX = 2;
    private static final String ACT_FACILITY_SAVE = "facilitySave";
    private static final String ACT_EDIT = "edit";
    private static final String ACT_DELETE = "delete";
    private StudyProtocolServiceRemote sProService;
    private StudyParticipationServiceRemote sPartService;
    private StudySiteAccrualStatusServiceRemote ssasService;
    private PAHealthCareFacilityServiceRemote pahfService;
    private PAOrganizationServiceRemote paoService;
    private StudyParticipationContactServiceRemote sPartContactService;
    private HealthCareFacilityCorrelationServiceRemote pohfService;
    private HealthCareProviderCorrelationServiceRemote pohcpService;
    private Ii spIi;
    private List<OrganizationWebDTO> organizationList = null;
    private OrganizationDTO selectedOrgDTO = null;
    private Organization editOrg;
    private static final int THREE = 3;
    private static final String DISPLAYJSP = "displayJsp";
    private Long cbValue;
    private String recStatus;
    private String recStatusDate;
    private PersonDTO selectedPersTO = null;
    private List<StudyParticipationContactDTO> spContactDTO;
    private List<PersonWebDTO> personWebDTOList;
    private boolean newParticipation = false;
    private PersonWebDTO personContactWebDTO;
    private String organizationName;
    private OrgSearchCriteria orgFromPO = new OrgSearchCriteria();
    private String currentAction = "create";

    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        sProService = PaRegistry.getStudyProtocolService();
        sPartService = PaRegistry.getStudyParticipationService();
        ssasService = PaRegistry.getStudySiteAccrualStatusService();
        pahfService = PaRegistry.getPAHealthCareFacilityService();
        paoService = PaRegistry.getPAOrganizationService();
        pohfService = PaRegistry.getHealthCareFacilityCorrelationService();
        sPartContactService = PaRegistry.getStudyParticipationContactService();
        pohcpService = PaRegistry.getHealthCareProviderCorrelationService();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
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
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        loadForm();
        setNewParticipation(true);
        return Action.SUCCESS;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String facilitySave() throws Exception {
        clearErrorsAndMessages();
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return ERROR;
        }
        // 1) get the po identifer from input field ( user selected from pop-up)
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("You must select an organization before adding.");
            return SUCCESS;
        }
        String poOrgId = tab.getFacilityOrganization().getIdentifier();
        // 2) using the poIdentifer call
        // po.getHealthcareFacility(poOrganizationid) (this
        //
        // if (poHealthcareFacilityIi == null) {
        // poHealthcareFacilityIi =
        // pa.regisitry.poCreateHealthcareFacility(poISOHealthcareFacilityDTO)
        //
        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setPlayerIdentifier(tab.getPoOrganizationIi());
        List<HealthCareFacilityDTO> poHfList = pohfService.search(hcf);
        if (poHfList.isEmpty()) {
            tab.setPoHealthCareFacilityIi(pohfService.createCorrelation(hcf));
        }
        // 3) at this point we have both poOrganizationIi &
        // poHealthcareFacilityIi
        // 4) check if PA has organization and healthcarefacitly (local copy)
        // 5) paOrganizationIi =
        // PAOrganizationServiceRemote.getOrganizationByIndetifers(poOrganizationIi)
        // (this method is implemented )
        Organization org = new Organization();
        org.setIdentifier(poOrgId);
        Organization paOrg = paoService.getOrganizationByIndetifers(org);
        // 6) if (paOrganizationIi == null ) {
        // create an organization in pa
        // paOrganizationIi =
        // PAOrganizationServiceRemote.createOrganization(Organization)
        // (this method is implemented)
        // }
        if (paOrg == null) {
            org.setCity(tab.getFacilityOrganization().getCity());
            org.setCountryName(tab.getFacilityOrganization().getCountryName());
            org.setName(tab.getFacilityOrganization().getName());
            org.setPostalCode(tab.getFacilityOrganization().getPostalCode());
            paOrg = paoService.createOrganization(org);
        }
        // 7) paHealthcareFacilityIi =
        // PAHealthcareFacilityServiceRemote.getHealthcareFacility(poHealthcareFacilityIi)
        // 8) if (paHealthcareFacilityIi == null ) {
        // create an HealthcareFacilityIi in pa
        // paHealthcareFacilityIi =
        // PAHealthcareFacilityServiceRemote.createHealthcareFacility(HealthcareFacility)
        // (this method is NOT impemented)
        // }
        PAHealthCareFacilityDTO hfDto = null;
        List<PAHealthCareFacilityDTO> hfList = pahfService.getByOrganization(IiConverter.convertToIi(poOrgId));
        if (hfList.size() > 0) {
            hfDto = hfList.get(0);
        } else {
            hfDto = new PAHealthCareFacilityDTO();
            hfDto.setAssignedIdentifier(StConverter.convertToSt("1234"));
            hfDto.setOrganizationIi(IiConverter.convertToIi(paOrg.getId()));
            hfDto = pahfService.create(hfDto);
        }
        // 9) now you have paOrganizationIi & paHealthcareFacilityIi and you can
        // create Participations and Studyprotocol
        StudyParticipationDTO sp = new StudyParticipationDTO();
        sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        sp.setHealthcareFacilityIi(hfDto.getIdentifier());
        sp.setIdentifier(IiConverter.convertToIi((Long) null));
        sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP Identifier"));
        sp.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        sp.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2001")));
        sp.setStudyProtocolIi(spIi);
        sp = sPartService.create(sp);
        tab.setStudyParticipationId(IiConverter.convertToLong(sp.getIdentifier()));
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(getRecStatus())));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(getRecStatusDate())));
        ssas.setStudyParticipationIi(sp.getIdentifier());
        ssas = ssasService.createStudySiteAccrualStatus(ssas);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (tab.getFacilityOrganization().getName() != null) {
            organizationName = "for " + tab.getFacilityOrganization().getName();
        }
        // ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
        // Constants.CREATE_MESSAGE);
        loadForm();
        setCurrentAction("edit");
        return ACT_FACILITY_SAVE;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String facilityUpdate() throws Exception {
        clearErrorsAndMessages();
        // enforceBusinessRules();
        String resStatus = ServletActionContext.getRequest().getParameter("recStatus");
        String resStatusDate = ServletActionContext.getRequest().getParameter("resStatusDate");
        if (!PAUtil.isNotNullOrNotEmpty(resStatus)) {
            addFieldError("recStatus", getText("error.participatingStatus"));
        }
        if (!PAUtil.isNotNullOrNotEmpty(resStatusDate)) {
            addFieldError("recStatusDate", getText("error.participatingStatusDate"));
        }
        setCurrentAction("edit");
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("System error getting participating orgainzation data from session.");
            return SUCCESS;
        }
        Organization org = new Organization();
        if (tab.getFacilityOrganization().getId() != null) {
            org.setId(tab.getFacilityOrganization().getId());
            org = paoService.getOrganizationByIndetifers(org);
        } else {
            org = tab.getFacilityOrganization();
        }
        if (hasFieldErrors()) {
            orgFromPO.setOrgCity(org.getCity());
            orgFromPO.setOrgCountry(org.getCountryName());
            orgFromPO.setOrgName(org.getName());
            orgFromPO.setOrgZip(org.getPostalCode());
            if (PAUtil.isNotNullOrNotEmpty(resStatus)) {
                this.setRecStatus(resStatus);
            }
            if (PAUtil.isNotNullOrNotEmpty(resStatusDate)) {
                this.setRecStatusDate(resStatusDate);
            }
            return "error_edit";
        }

        List<StudySiteAccrualStatusDTO> currentStatus = ssasService
                .getCurrentStudySiteAccrualStatusByStudyParticipation(IiConverter.convertToIi(tab
                        .getStudyParticipationId()));
        if (currentStatus.isEmpty() || !currentStatus.get(0).getStatusCode().getCode().equals(resStatus)
                || !currentStatus.get(0).getStatusDate().equals(PAUtil.dateStringToTimestamp(resStatusDate))) {
            StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
            ssas.setIdentifier(IiConverter.convertToIi((Long) null));
            ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(resStatus)));
            ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(resStatusDate)));
            ssas.setStudyParticipationIi(IiConverter.convertToIi(tab.getStudyParticipationId()));
            ssas = ssasService.createStudySiteAccrualStatus(ssas);
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        orgFromPO.setOrgCity(org.getCity());
        orgFromPO.setOrgCountry(org.getCountryName());
        orgFromPO.setOrgName(org.getName());
        orgFromPO.setOrgZip(org.getPostalCode());

        this.setRecStatus(resStatus);
        this.setRecStatusDate(resStatusDate);
        return this.ACT_EDIT;
        // return ACT_FACILITY_SAVE;
        // return "displayJsp";
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        setCurrentAction("edit");
        StudyParticipationDTO spDto = sPartService.get(IiConverter.convertToIi(cbValue));
        PAHealthCareFacilityDTO hfDto = pahfService.get(spDto.getHealthcareFacilityIi());
        Organization org = new Organization();
        org.setId(IiConverter.convertToLong(hfDto.getOrganizationIi()));
        editOrg = paoService.getOrganizationByIndetifers(org);
        orgFromPO.setOrgCity(editOrg.getCity());
        orgFromPO.setOrgCountry(editOrg.getCountryName());
        orgFromPO.setOrgName(editOrg.getName());
        orgFromPO.setOrgZip(editOrg.getPostalCode());
        List<StudySiteAccrualStatusDTO> statusList = ssasService
                .getCurrentStudySiteAccrualStatusByStudyParticipation(spDto.getIdentifier());
        if (!statusList.isEmpty()) {
            this.setRecStatus(statusList.get(0).getStatusCode().getCode());
            this.setRecStatusDate(TsConverter.convertToTimestamp(statusList.get(0).getStatusDate()).toString());
        }
        setNewParticipation(false);
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(org);
        tab.setPoHealthCareFacilityIi(null);
        tab.setPoOrganizationIi(null);
        tab.setNewParticipation(false);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (editOrg.getName() != null) {
            organizationName = "for " + editOrg.getName();
        }
        // prepare the other two variables for display [personWebDTOList and
        // personWebDTO]
        personWebDTOList = new ArrayList<PersonWebDTO>();
        List<PersonWebDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudyParticpationId(tab.getStudyParticipationId(), "STUDY_PRINCIPAL_INVESTIGATOR");
        List<PersonWebDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                tab.getStudyParticipationId(), "STUDY_SUB_INVESTIGATOR");
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add((PersonWebDTO) principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add((PersonWebDTO) subInvresults.get(i));
        }
        List<PersonWebDTO> resultsList = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                tab.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
        if (resultsList != null && resultsList.size() > 0) {
            personContactWebDTO = resultsList.get(0);
        }
        return this.ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        clearErrorsAndMessages();
        sPartService.delete(IiConverter.convertToIi(cbValue));
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        loadForm();
        return this.ACT_DELETE;
    }

    private void loadForm() throws Exception {
        organizationList = new ArrayList<OrganizationWebDTO>();
        StudyParticipationDTO srDTO = new StudyParticipationDTO();
        srDTO
                .setFunctionalCode(CdConverter.convertStringToCd(StudyParticipationFunctionalCode.TREATING_SITE
                        .getCode()));
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIi, srDTO);
        for (StudyParticipationDTO sp : spList) {
            List<StudySiteAccrualStatusDTO> ssasList = ssasService
                    .getCurrentStudySiteAccrualStatusByStudyParticipation(sp.getIdentifier());
            PAHealthCareFacilityDTO hf = pahfService.get(sp.getHealthcareFacilityIi());
            Organization orgBo = new Organization();
            orgBo.setId(IiConverter.convertToLong(hf.getOrganizationIi()));
            orgBo = paoService.getOrganizationByIndetifers(orgBo);
            OrganizationWebDTO orgWebDTO = new OrganizationWebDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            if (ssasList.isEmpty()) {
                orgWebDTO.setRecruitmentStatus("unknown");
                orgWebDTO.setRecruitmentStatusDate("unknown");
            } else {
                orgWebDTO.setRecruitmentStatus(CdConverter.convertCdToString(ssasList.get(0).getStatusCode()));
                orgWebDTO.setRecruitmentStatusDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                        ssasList.get(0).getStatusDate()).toString()));
            }
            organizationList.add(orgWebDTO);
        }
    }

    /**
     * @return the organizationList
     * @throws Exception on error.
     */
    public String displayOrg() throws Exception {
        clearErrorsAndMessages();
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        selectedOrgDTO = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0);
        // store selection
        Organization org = new Organization();
        org.setCity(selectedOrgDTO.getPostalAddress().getPart().get(AD_CITY_IDX).getValue());
        org.setCountryName(selectedOrgDTO.getPostalAddress().getPart().get(AD_COUNTRY_IDX).getCode());
        org.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        org.setName(selectedOrgDTO.getName().getPart().get(0).getValue());
        org.setPostalCode(selectedOrgDTO.getPostalAddress().getPart().get(AD_ZIP_IDX).getValue());
        editOrg = new Organization();
        editOrg.setCity(selectedOrgDTO.getPostalAddress().getPart().get(AD_CITY_IDX).getValue());
        editOrg.setCountryName(selectedOrgDTO.getPostalAddress().getPart().get(AD_COUNTRY_IDX).getCode());
        editOrg.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        editOrg.setName(selectedOrgDTO.getName().getPart().get(0).getValue());
        editOrg.setPostalCode(selectedOrgDTO.getPostalAddress().getPart().get(AD_ZIP_IDX).getValue());
        // setting the org values to the member var
        orgFromPO.setOrgCity(selectedOrgDTO.getPostalAddress().getPart().get(AD_CITY_IDX).getValue());
        orgFromPO.setOrgCountry(selectedOrgDTO.getPostalAddress().getPart().get(AD_COUNTRY_IDX).getCode());
        orgFromPO.setOrgName(selectedOrgDTO.getName().getPart().get(0).getValue());
        orgFromPO.setOrgZip(selectedOrgDTO.getPostalAddress().getPart().get(AD_ZIP_IDX).getValue());
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setPoOrganizationIi(selectedOrgDTO.getIdentifier());
        tab.setFacilityOrganization(org);
        setNewParticipation(false);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return DISPLAYJSP;
    }

    /**
     * This method is called upon clicking the second tab (Investigators).
     *
     * @return result
     * @throws Exception on error
     */
    public String getStudyParticipationContacts() throws Exception {
        clearErrorsAndMessages();
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        returnDisplaySPContacts(tab);
        return "display_StudyPartipants";
    }

    /**
     * @return the result
     * @throws Exception on error.
     */
    public String saveStudyParticipationContact() throws Exception {
        clearErrorsAndMessages();
        boolean isPrimaryContact = false;
        boolean hasErrors = false;
        String roleCode = ServletActionContext.getRequest().getParameter("rolecode");
        String persId = ServletActionContext.getRequest().getParameter("persid");
        if (persId == null) {
            isPrimaryContact = true;
            persId = ServletActionContext.getRequest().getParameter("contactpersid");
            String email = ServletActionContext.getRequest().getParameter("email");
            String telephone = ServletActionContext.getRequest().getParameter("tel");
            if (persId.equals("")) {
                addFieldError("personContactWebDTO.firstName", getText("Please lookup and select person"));
                hasErrors = true;
            } else {
                selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(
                        EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            }
            if (!PAUtil.isNotNullOrNotEmpty(email)) {
                addFieldError("personContactWebDTO.email", getText("error.enterEmailAddress"));
                hasErrors = true;
            }
            if (PAUtil.isNotNullOrNotEmpty(email) && (!PAUtil.isValidEmail(email))) {
                addFieldError("personContactWebDTO.email", getText("error.enterValidEmail"));
                hasErrors = true;
            }
            if (!PAUtil.isNotNullOrNotEmpty(telephone)) {
                addFieldError("personContactWebDTO.telephone", getText("error.enterPhoneNumber"));
                hasErrors = true;
            }
            if (hasErrors) {
                personContactWebDTO = new PersonWebDTO();
                personContactWebDTO.setEmail(email);
                personContactWebDTO.setTelephone(telephone);
                if (persId != null && !persId.equals("")) {
                    personContactWebDTO.setSelectedPersId(Long.valueOf(persId));
                }
                if (selectedPersTO != null && selectedPersTO.getName() != null) {
                    personContactWebDTO.setFirstName((selectedPersTO.getName().getPart()).get(1).getValue());
                    personContactWebDTO.setLastName((selectedPersTO.getName().getPart()).get(0).getValue());
                    personContactWebDTO.setMiddleName((selectedPersTO.getName().getPart()).get(2).getValue());
                }
                return "error_prim_contacts";
            }
        }

        selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(
                EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        // Step 1: Check if a Health care provider exists for a given
        // identitfier
        Long healthCareProvider = PaRegistry.getPAHealthCareProviderService().findHcpByIdentifier(
                IiConverter.convertToLong(selectedPersTO.getIdentifier()));
        if (healthCareProvider == null) { // Since, Healthcareprovider is
                                            // null, create one
            PAHealthCareProviderDTO healthCareProviderDTO = new PAHealthCareProviderDTO();
            healthCareProviderDTO.setFirstName((selectedPersTO.getName().getPart()).get(1).getValue());
            healthCareProviderDTO.setLastName((selectedPersTO.getName().getPart()).get(0).getValue());
            healthCareProviderDTO.setMiddleName((selectedPersTO.getName().getPart()).get(2).getValue());
            healthCareProviderDTO.setAssignedIdentifier(IiConverter.convertToLong(selectedPersTO.getIdentifier()));
            healthCareProvider = PaRegistry.getPAHealthCareProviderService().createPAHealthCareProvider(
                    healthCareProviderDTO);
        }
        // Step 2: create a record in PO
        if (pohcpService.getCorrelation(selectedPersTO.getIdentifier()) == null) {
            HealthCareProviderDTO healthCareProviderDTO = new HealthCareProviderDTO();
            healthCareProviderDTO.setPersonIdentifier(selectedPersTO.getIdentifier());
            // pohcpService.createCorrelation(healthCareProviderDTO); uncomment
            // this later!
        }
        // Step 3: Check the Study Participation contact Info
        Long studyPartContactPrincipalInv = PaRegistry.getPAHealthCareProviderService()
                .getStudyParticationContactByPersonAndSPId(IiConverter.convertToLong(selectedPersTO.getIdentifier()),
                        organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRINCIPAL_INVESTIGATOR");
        Long studyPartContactSubInv = PaRegistry.getPAHealthCareProviderService()
                .getStudyParticationContactByPersonAndSPId(IiConverter.convertToLong(selectedPersTO.getIdentifier()),
                        organizationsTabWebDTO.getStudyParticipationId(), "STUDY_SUB_INVESTIGATOR");
        boolean studyPartContact = ((studyPartContactPrincipalInv == null) && (studyPartContactSubInv == null));
        if (studyPartContact && !isPrimaryContact) {
            createStudyParticationContactRecord(healthCareProvider, organizationsTabWebDTO, isPrimaryContact, roleCode);
        }
        // Handle the condition when the changing the Primary Contact;
        if (isPrimaryContact) {
            // Check if a different person is chosen this time.
            // Check for a row with StudyParticipation and rolecode =
            // "STUDY_PRIMARY_CONTACT"
            List returnlist = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                    organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
            if (returnlist.size() > 0) {
                sPartContactService.delete(IiConverter.convertToIi(((PersonWebDTO) returnlist.get(0)).getId()));
            }
            boolean validationError = createStudyParticationContactRecord(healthCareProvider, organizationsTabWebDTO,
                    isPrimaryContact, StudyContactRoleCode.STUDY_PRIMARY_CONTACT.getCode());
            if (validationError) {
                personContactWebDTO = new PersonWebDTO();
                personContactWebDTO.setFirstName((selectedPersTO.getName().getPart()).get(1).getValue());
                personContactWebDTO.setLastName((selectedPersTO.getName().getPart()).get(0).getValue());
                personContactWebDTO.setMiddleName((selectedPersTO.getName().getPart()).get(2).getValue());
                personContactWebDTO.setSelectedPersId(Long.valueOf(selectedPersTO.getIdentifier().getExtension()));
                personContactWebDTO.setEmail(ServletActionContext.getRequest().getParameter("email"));
                personContactWebDTO.setTelephone(ServletActionContext.getRequest().getParameter("tel"));
                return "error_prim_contacts";
            }
        }
        // This makes a fresh db call to show the result on the JSP
        if (!isPrimaryContact) {
            return returnDisplaySPContacts(organizationsTabWebDTO);
        } else {
            personContactWebDTO = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                    organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT").get(0);
            return "display_primContacts";
        }
    }

    /**
     * @return the result
     * @throws Exception on error
     */
    public String deleteStudyPartContact() throws Exception {
        clearErrorsAndMessages();
        String invId = ServletActionContext.getRequest().getParameter("persid");
        StudyParticipationContactDTO contactDTO = sPartContactService.get(IiConverter.convertToIi(invId));
        Long identifier = IiConverter.convertToLong(contactDTO.getHealthCareProviderIi());
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        sPartContactService.delete(IiConverter.convertToIi(invId));
        // If a primary contact also exists delete that as well
        List returnlist = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
        if (returnlist.size() > 0) {
            StudyParticipationContactDTO contactDTODB = sPartContactService.get(IiConverter
                    .convertToIi(((PersonWebDTO) returnlist.get(0)).getId()));
            Long identifierDB = IiConverter.convertToLong(contactDTODB.getHealthCareProviderIi());
            if (identifier.equals(identifierDB)) {
                sPartContactService.delete(IiConverter.convertToIi(((PersonWebDTO) returnlist.get(0)).getId()));
            }
        }
        return returnDisplaySPContacts(organizationsTabWebDTO);
    }

    /**
     * @return the result
     * @throws Exception on error.
     */
    public String getStudyParticipationPrimContact() throws Exception {
        clearErrorsAndMessages();
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        List<PersonWebDTO> resultsList = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
        if (resultsList != null && resultsList.size() > 0) {
            personContactWebDTO = resultsList.get(0);
        }
        return "display_StudyPartipants_Contacts";
    }

    private String returnDisplaySPContacts(ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO)
    throws Exception {
        personWebDTOList = new ArrayList<PersonWebDTO>();
        List<PersonWebDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudyParticpationId(organizationsTabWebDTO.getStudyParticipationId(),
                        "STUDY_PRINCIPAL_INVESTIGATOR");
        List<PersonWebDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_SUB_INVESTIGATOR");
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add((PersonWebDTO) principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add((PersonWebDTO) subInvresults.get(i));
        }
        return "display_spContacts";
    }

    /**
     *
     * @return the result
     * @throws Exception on erro
     */
    public String getDisplaySPContacts() throws Exception {
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        personWebDTOList = new ArrayList<PersonWebDTO>();
        List<PersonWebDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudyParticpationId(organizationsTabWebDTO.getStudyParticipationId(),
                        "STUDY_PRINCIPAL_INVESTIGATOR");
        List<PersonWebDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_SUB_INVESTIGATOR");
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add((PersonWebDTO) principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add((PersonWebDTO) subInvresults.get(i));
        }
        return "display_spContacts";
    }

    /**
     * @return the result
     * @throws Exception on error.
     */
    public String setAsPrimaryContact() throws Exception {
        clearErrorsAndMessages();
        String contactPersId = ServletActionContext.getRequest().getParameter("contactpersid");
        String editmode = ServletActionContext.getRequest().getParameter("editmode");
        Long identifier = PaRegistry.getPAHealthCareProviderService().getIdentifierBySPCId(Long.valueOf(contactPersId));
        selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(EnOnConverter.convertToOrgIi(identifier));
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        Long healthCareProvider = PaRegistry.getPAHealthCareProviderService().findHcpByIdentifier(
                IiConverter.convertToLong(selectedPersTO.getIdentifier()));
        // check if primary contact already exists; if it exists delete it
        List returnlist = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
        if (returnlist.size() > 0) {
            sPartContactService.delete(IiConverter.convertToIi(((PersonWebDTO) returnlist.get(0)).getId()));
        }
        boolean validationError = createStudyParticationContactRecord(healthCareProvider, organizationsTabWebDTO, true,
                StudyContactRoleCode.STUDY_PRIMARY_CONTACT.getCode());
        if (validationError) {
            return "error_prim_contacts";
        }
        if ("yes".equals(editmode)) {
            return getStudyParticipationPrimContact();
        }
        return returnDisplaySPContacts(organizationsTabWebDTO);
    }

    private boolean createStudyParticationContactRecord(Long healthCareProvider,
            ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO, boolean isPrimaryContact, String roleCode)
            throws Exception {
        clearErrorsAndMessages();
        boolean returnValue = false;
        Long spId = organizationsTabWebDTO.getStudyParticipationId();
        StudyParticipationContactDTO spContactDTOSave = new StudyParticipationContactDTO();
        StudyParticipationDTO spDTO = sPartService.get(IiConverter.convertToIi(spId));
        spContactDTOSave.setStudyParticipationIi(spDTO.getIdentifier());
        if (isPrimaryContact) {
            String email = ServletActionContext.getRequest().getParameter("email");
            String telephone = ServletActionContext.getRequest().getParameter("tel");
            ArrayList<String> emailList = new ArrayList<String>();
            ArrayList<String> telList = new ArrayList<String>();
            emailList.add(email);
            telList.add(telephone);
            DSet<Tel> list = new DSet<Tel>();
            list = DSetConverter.convertListToDSet(emailList, "EMAIL", list);
            list = DSetConverter.convertListToDSet(telList, "PHONE", list);
            spContactDTOSave.setTelecomAddresses(list);
            spContactDTOSave.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.STUDY_PRIMARY_CONTACT
                    .getCode()));
        } else {
            spContactDTOSave.setRoleCode(CdConverter.convertStringToCd(roleCode));
        }
        spContactDTOSave.setStatusCode(CdConverter.convertStringToCd("Active"));
        spContactDTOSave.setStudyProtocolIi(spIi);
        spContactDTOSave.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProvider));
        sPartContactService.create(spContactDTOSave);
        ServletActionContext.getRequest().getSession().removeAttribute("emailEntered");
        ServletActionContext.getRequest().getSession().removeAttribute("telephoneEntered");
        return returnValue;
    }

    /**
     * @return the result
     * @throws Exception on error.
     */
    public String saveStudyParticipationPrimContact() throws Exception {
        clearErrorsAndMessages();
        String contactPersId = ServletActionContext.getRequest().getParameter("contactpersid");
        selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(
                EnOnConverter.convertToOrgIi(Long.valueOf(contactPersId)));
        return "display_StudyPartipants_Contacts";
    }

    private void enforceBusinessRulesforPrimaryContact() {
        String persId = ServletActionContext.getRequest().getParameter("contactpersid");
        String email = ServletActionContext.getRequest().getParameter("email");
        String telephone = ServletActionContext.getRequest().getParameter("tel");
        if (personContactWebDTO == null) {
            addFieldError("personContactWebDTO.firstName", getText("error.chooseContact"));
        }
        if (personContactWebDTO != null && !(personContactWebDTO.getFirstName().length() > 0)) {
            addFieldError("personContactWebDTO.firstName", getText("error.chooseContact"));
        }
        if (!PAUtil.isNotNullOrNotEmpty(email)) {
            addFieldError("personContactWebDTO.email", getText("error.enterEmailAddress"));
        }
        if (PAUtil.isNotNullOrNotEmpty(email) && (!PAUtil.isValidEmail(email))) {
            addFieldError("personContactWebDTO.email", getText("error.enterValidEmail"));
        }
        if (!PAUtil.isNotNullOrNotEmpty(telephone)) {
            addFieldError("personContactWebDTO.telephone", getText("error.enterPhoneNumber"));
        }
    }

    /**
     * @return the result
     * @throws Exception on error.
     */
    public String refreshPrimaryContact() throws Exception {
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        List<PersonWebDTO> resultsList = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
        if (resultsList != null && resultsList.size() > 0) {
            personContactWebDTO = resultsList.get(0);
        }
        return "display_primContacts";
    }

    /**
     * @return the result
     * @throws Exception on error.
     */
    public String displayStudyParticipationPrimContact() throws Exception {
        clearErrorsAndMessages();
        String contactPersId = ServletActionContext.getRequest().getParameter("contactpersid");
        String editmode = ServletActionContext.getRequest().getParameter("editmode");
        Long identifier = null;
        personContactWebDTO = new PersonWebDTO();
        if ("yes".equals(editmode)) {
            identifier = PaRegistry.getPAHealthCareProviderService().getIdentifierBySPCId(Long.valueOf(contactPersId));
            selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(EnOnConverter.convertToOrgIi(identifier));
            personContactWebDTO.setSelectedPersId(Long.valueOf(identifier));
        } else {
            selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(contactPersId)));
            personContactWebDTO.setSelectedPersId(Long.valueOf(contactPersId));
        }
        personContactWebDTO.setFirstName((selectedPersTO.getName().getPart()).get(1).getValue());
        personContactWebDTO.setLastName((selectedPersTO.getName().getPart()).get(0).getValue());
        personContactWebDTO.setMiddleName((selectedPersTO.getName().getPart()).get(2).getValue());
        String email = (String) ServletActionContext.getRequest().getSession().getAttribute("emailEntered");
        String telephone = (String) ServletActionContext.getRequest().getSession().getAttribute("telephoneEntered");
        if (email != null) {
            personContactWebDTO.setEmail(email);
        }
        if (telephone != null) {
            personContactWebDTO.setTelephone(telephone);
        }
        return "display_primContacts";
    }

    /**
     * This method is used to enforce the business rules which are form specific
     * or based on an interaction between services.
     */
    private void enforceBusinessRules() {
        if (!PAUtil.isNotNullOrNotEmpty(getRecStatus())) {
            addFieldError("recStatus", getText("error.participatingStatus"));
        }
        if (!PAUtil.isNotNullOrNotEmpty(getRecStatusDate())) {
            addFieldError("recStatusDate", getText("error.participatingStatusDate"));
        }
        if (!(orgFromPO.getOrgName().length() > 0)) {
            addFieldError("editOrg.name", getText("Please choose an organization"));
        }
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
     * @return the selectedOrgDTO
     */
    public OrganizationDTO getSelectedOrgDTO() {
        return selectedOrgDTO;
    }

    /**
     * @param selectedOrgDTO the selectedOrgDTO to set
     */
    public void setSelectedOrgDTO(OrganizationDTO selectedOrgDTO) {
        this.selectedOrgDTO = selectedOrgDTO;
    }

    /**
     * @return the cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue the cbValue to set
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return the recStatus
     */
    public String getRecStatus() {
        return recStatus;
    }

    /**
     * @param recStatus the recStatus to set
     */
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    /**
     * @return the recStatusDate
     */
    public String getRecStatusDate() {
        return recStatusDate;
    }

    /**
     * @param recStatusDate the recStatusDate to set
     */
    public void setRecStatusDate(String recStatusDate) {
        this.recStatusDate = PAUtil.normalizeDateString(recStatusDate);
    }

    /**
     * @return the spContactDTO
     */
    public List<StudyParticipationContactDTO> getSpContactDTO() {
        return spContactDTO;
    }

    /**
     * @param spContactDTO the spContactDTO to set
     */
    public void setSpContactDTO(List<StudyParticipationContactDTO> spContactDTO) {
        this.spContactDTO = spContactDTO;
    }

    /**
     * @return the newParticipation
     */
    public boolean isNewParticipation() {
        return newParticipation;
    }

    /**
     * @param newParticipation the newParticipation to set
     */
    public void setNewParticipation(boolean newParticipation) {
        this.newParticipation = newParticipation;
    }

    /**
     * @return the editOrg
     */
    public Organization getEditOrg() {
        return editOrg;
    }

    /**
     * @param editOrg the editOrg to set
     */
    public void setEditOrg(Organization editOrg) {
        this.editOrg = editOrg;
    }

    /**
     * @return the personWebDTOList
     */
    public List<PersonWebDTO> getPersonWebDTOList() {
        return personWebDTOList;
    }

    /**
     * @param personWebDTOList the personWebDTOList to set
     */
    public void setPersonWebDTOList(List<PersonWebDTO> personWebDTOList) {
        this.personWebDTOList = personWebDTOList;
    }

    /**
     * @return the personContactWebDTO
     */
    public PersonWebDTO getPersonContactWebDTO() {
        return personContactWebDTO;
    }

    /**
     * @param personContactWebDTO the personContactWebDTO to set
     */
    public void setPersonContactWebDTO(PersonWebDTO personContactWebDTO) {
        this.personContactWebDTO = personContactWebDTO;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the orgFromPO
     */
    public OrgSearchCriteria getOrgFromPO() {
        return orgFromPO;
    }

    /**
     * @param orgFromPO the orgFromPO to set
     */
    public void setOrgFromPO(OrgSearchCriteria orgFromPO) {
        this.orgFromPO = orgFromPO;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }
}
