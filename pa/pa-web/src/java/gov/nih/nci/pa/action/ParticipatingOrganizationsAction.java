/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.OrganizationWebDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.PersonWebDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOOrgDisplayConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

/*********
 * PLEASE DO NOT CODE-REVIEW : THIS REQUIRES CLEAN UP.
 * 
 */
@Validation
@SuppressWarnings("PMD")
public class ParticipatingOrganizationsAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 123412653L;
    private static final String ACT_FACILITY_SAVE = "facilitySave";
    private static final String ACT_EDIT = "edit";
    private static final String ACT_DELETE = "delete";
    private StudyParticipationServiceRemote sPartService;
    private StudySiteAccrualStatusServiceRemote ssasService;
    private StudyParticipationContactServiceRemote sPartContactService;
    private OrganizationCorrelationServiceBean oCService;
    private CorrelationUtils cUtils;
    
    private Ii spIi;
    private List<OrganizationWebDTO> organizationList = null;
    private OrganizationDTO selectedOrgDTO = null;
    private Organization editOrg;
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
    private String targetAccrualNumber;

    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        sPartService = PaRegistry.getStudyParticipationService();
        ssasService = PaRegistry.getStudySiteAccrualStatusService();
        sPartContactService = PaRegistry.getStudyParticipationContactService();
        cUtils = new CorrelationUtils();
        oCService = new OrganizationCorrelationServiceBean();
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
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab == null) {
            loadForm();
            addActionError("You must select an organization before adding.");
            return SUCCESS;
        }
        String poOrgId = tab.getFacilityOrganization().getIdentifier();
        Long paHealthCareFacilityId = oCService.createHealthCareFacilityCorrelations(poOrgId);
        StudyParticipationDTO sp = new StudyParticipationDTO();
        sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        sp.setHealthcareFacilityIi(IiConverter.convertToIi(paHealthCareFacilityId));
        sp.setIdentifier(null);
        sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP Identifier"));
        sp.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        sp.setStatusDateRangeLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        sp.setStudyProtocolIdentifier(spIi);
        sp.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
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
        String resStatus = ServletActionContext.getRequest().getParameter("recStatus");
        String resStatusDate = ServletActionContext.getRequest().getParameter("recStatusDate");
        String paramTargetAccrualNumber = ServletActionContext.getRequest().getParameter("targetAccrualNumber");
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
            org = cUtils.getPAOrganizationByIndetifers(tab.getFacilityOrganization().getId(), null);
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

        StudyParticipationDTO spDto = sPartService.get(IiConverter.convertToIi(tab.getStudyParticipationId()));
        if (IntConverter.convertToInteger(spDto.getTargetAccrualNumber()) != new Integer(getTargetAccrualNumber())) {
            spDto.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
            sPartService.update(spDto);
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
        this.setTargetAccrualNumber(paramTargetAccrualNumber);
        return this.ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        setCurrentAction("edit");
        StudyParticipationDTO spDto = sPartService.get(IiConverter.convertToIi(cbValue));
        editOrg = cUtils.getPAOrganizationByPAHealthCareFacilityId(
                IiConverter.convertToLong(spDto.getHealthcareFacilityIi()));
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
        if (IntConverter.convertToInteger(spDto.getTargetAccrualNumber()) ==  null) {
            setTargetAccrualNumber(null);
        } else {
            setTargetAccrualNumber(IntConverter.convertToInteger(spDto.getTargetAccrualNumber()).toString());
        }
        setNewParticipation(false);
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(editOrg);
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
            Organization orgBo = cUtils.getPAOrganizationByPAHealthCareFacilityId(
                    IiConverter.convertToLong(sp.getHealthcareFacilityIi()));
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
            if (IntConverter.convertToInteger(sp.getTargetAccrualNumber()) == null) {
                orgWebDTO.setTargetAccrualNumber(null);
            } else {
                orgWebDTO.setTargetAccrualNumber(IntConverter.convertToInteger(sp.getTargetAccrualNumber()).toString());
            }
            organizationList.add(orgWebDTO);
        }
    }

    /**
     * @return the organizationList
     * @throws Exception on error.
     */
    public String displayOrg() throws Exception {
        gov.nih.nci.pa.dto.OrganizationDTO paOrgDTO = new gov.nih.nci.pa.dto.OrganizationDTO();
        clearErrorsAndMessages();
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        selectedOrgDTO = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0);
        // convert the PO DTO to the pa domain
        paOrgDTO = ISOOrgDisplayConverter.convertPoOrganizationDTO(selectedOrgDTO);
        // store selection
        Organization org = new Organization();
        org.setCity(paOrgDTO.getCity());
        org.setCountryName(paOrgDTO.getCountry());
        org.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        org.setName(paOrgDTO.getName());
        org.setPostalCode(paOrgDTO.getZip());
        editOrg = new Organization();
        editOrg.setCity(paOrgDTO.getCity());
        editOrg.setCountryName(paOrgDTO.getCountry());
        editOrg.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        editOrg.setName(paOrgDTO.getName());
        editOrg.setPostalCode(paOrgDTO.getZip());
        // setting the org values to the member var
        orgFromPO.setOrgCity(paOrgDTO.getCity());
        orgFromPO.setOrgCountry(paOrgDTO.getCountry());
        orgFromPO.setOrgName(paOrgDTO.getName());
        orgFromPO.setOrgZip(paOrgDTO.getZip());
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
        if (selectedPersTO == null) {
            selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
        }
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                                    .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        createStudyParticationContactRecord(tab, isPrimaryContact, roleCode);
        // This makes a fresh db call to show the result on the JSP
        if (!isPrimaryContact) {
            return returnDisplaySPContacts(tab);
        } else {
            personContactWebDTO = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                    tab.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT").get(0);
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
        //Long identifier = IiConverter.convertToLong(contactDTO.getHealthCareProviderIi());
        Long identifier = IiConverter.convertToLong(contactDTO.getClinicalResearchStaffIi());
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
            Long identifierDB = IiConverter.convertToLong(contactDTODB.getClinicalResearchStaffIi());
            if (identifier.equals(identifierDB)) {
                sPartContactService.delete(IiConverter.convertToIi(((PersonWebDTO) returnlist.get(0)).getId()));
            }
        }
        return returnDisplaySPContacts(organizationsTabWebDTO);
    }
       
    /**
     * @return the result
     * @throws Exception on error
     */
    public String deleteStudyPartContact1() throws Exception {
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

    
    private boolean doesSPCRecordExistforPerson(Long persid) throws Exception {
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
            if (((PersonWebDTO) principalInvresults.get(i)).getSelectedPersId().equals(persid)) {
                return true;
            }
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            if (((PersonWebDTO) subInvresults.get(i)).getPaPersonId().equals(persid)) {
                return true;
            }
        }
        return false;
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
        PersonWebDTO webDTO = PaRegistry.getPAHealthCareProviderService().getIdentifierBySPCId(Long.valueOf(
                contactPersId));
        //selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(EnOnConverter.convertToOrgIi(identifier));
        ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO = (ParticipatingOrganizationsTabWebDTO)
        ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        // check if primary contact already exists; if it exists delete it
        List returnlist = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
                organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
        if (returnlist.size() > 0) {
            sPartContactService.delete(IiConverter.convertToIi(((PersonWebDTO) returnlist.get(0)).getId()));
        }
        boolean validationError = createStudyParticationContactRecord(organizationsTabWebDTO, true,
                                                            StudyContactRoleCode.STUDY_PRIMARY_CONTACT.getCode());
        if (validationError) {
            return "error_prim_contacts";
        }
        if ("yes".equals(editmode)) {
            return getStudyParticipationPrimContact();
        }
        return returnDisplaySPContacts(organizationsTabWebDTO);
    }
    
    
    private boolean createStudyParticationContactRecord(ParticipatingOrganizationsTabWebDTO organizationsTabWebDTO, 
                                                                              boolean isPrimaryContact, String roleCode)
                                                                                                    throws Exception {
        String orgPoIdentifier = organizationsTabWebDTO.getFacilityOrganization().getIdentifier();       
        Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean().createClinicalResearchStaffCorrelations(
                orgPoIdentifier, IiConverter.convertToString(selectedPersTO.getIdentifier()));
        StudyProtocolQueryDTO studyProtocolQueryDto =  (StudyProtocolQueryDTO) ServletActionContext.getRequest().
                                                                    getSession().getAttribute(Constants.TRIAL_SUMMARY);
        String trialType = studyProtocolQueryDto.getStudyProtocolType();
        Long healthCareProviderIi = null;
        if (trialType.startsWith("Interventional")) {
            healthCareProviderIi = new HealthCareProviderCorrelationBean().createHealthCareProviderCorrelationBeans(
                    orgPoIdentifier, IiConverter.convertToString(selectedPersTO.getIdentifier()));
        }
        StudyParticipationContactDTO participationContactDTO = new StudyParticipationContactDTO();
        participationContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
        if (healthCareProviderIi != null) {
            participationContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));
        }
        if (!isPrimaryContact) {
            participationContactDTO.setRoleCode(CdConverter.convertStringToCd(roleCode));
        } else {
            participationContactDTO.setRoleCode(CdConverter.convertStringToCd("Study Primary Contact"));
        }
        participationContactDTO.setStudyProtocolIdentifier(
                IiConverter.convertToIi(studyProtocolQueryDto.getStudyProtocolId()));
        participationContactDTO.setStatusCode(CdConverter.convertStringToCd(StatusCode.PENDING.getCode()));
        participationContactDTO.setStudyParticipationIi(IiConverter.convertToIi(
                organizationsTabWebDTO.getStudyParticipationId()));
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
            participationContactDTO.setTelecomAddresses(list);
            //if a old record exists delete it and create a new one
            List<PersonWebDTO> resultsList = PaRegistry.getPAHealthCareProviderService().
            getPersonsByStudyParticpationId(
                    organizationsTabWebDTO.getStudyParticipationId(), "STUDY_PRIMARY_CONTACT");
            if (resultsList.size() > 0) {
                Long idToDelete = ((PersonWebDTO) resultsList.get(0)).getId();
                sPartContactService.delete(IiConverter.convertToIi(idToDelete));
            }
            sPartContactService.create(participationContactDTO);
        }
        if (!isPrimaryContact && !doesSPCRecordExistforPerson(
                                Long.valueOf(selectedPersTO.getIdentifier().getExtension()))) {
            sPartContactService.create(participationContactDTO);
        }
        return true;
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
     * SET AS PRIMARY CONTACT. This method does not save it simply sets the values.
     * @return the result
     * @throws Exception on error.
     */
    public String displayStudyParticipationPrimContact() throws Exception {
        clearErrorsAndMessages();
        String contactPersId = ServletActionContext.getRequest().getParameter("contactpersid");
        String editmode = ServletActionContext.getRequest().getParameter("editmode");
        PersonWebDTO webDTO = null;
        personContactWebDTO = new PersonWebDTO();
        if ("yes".equals(editmode)) {
            webDTO = PaRegistry.getPAHealthCareProviderService().getIdentifierBySPCId(Long.valueOf(contactPersId));
            personContactWebDTO.setFirstName(webDTO.getFirstName());
            personContactWebDTO.setLastName(webDTO.getLastName());
            personContactWebDTO.setMiddleName(webDTO.getMiddleName());
            personContactWebDTO.setSelectedPersId(webDTO.getSelectedPersId());
        } else {
            selectedPersTO = PaRegistry.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(contactPersId)));
            personContactWebDTO.setSelectedPersId(Long.valueOf(selectedPersTO.getIdentifier().getExtension()));
            personContactWebDTO.setFirstName((selectedPersTO.getName().getPart()).get(1).getValue());
            personContactWebDTO.setLastName((selectedPersTO.getName().getPart()).get(0).getValue());
            personContactWebDTO.setMiddleName((selectedPersTO.getName().getPart()).get(2).getValue());
        }
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

    /**
     * @return the targetAccrualNumber
     */
    public String getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(String targetAccrualNumber) {
        Integer tInt;
        try {
            tInt = Integer.parseInt(targetAccrualNumber);
            this.targetAccrualNumber = tInt.toString();
        } catch (NumberFormatException e) {
            this.targetAccrualNumber  = null;
        }
    }
}
