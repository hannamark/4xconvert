/*
 * Copyright Notice. Copyright 2008, ScenPro, Inc, (“caBIG™ Participant”). The Protocol Abstraction (PA) Application was
 * created with NCI funding and is part of the caBIG™ initiative. The software subject to this notice and license
 * includes both human readable source code form and machine readable, binary, object code form (the “caBIG™ Software”).
 * This caBIG™ Software License (the “License”) is between caBIG™ Participant and You. “You (or “Your”) shall mean a
 * person or an entity, and all other entities that control, are controlled by, or are under common control with the
 * entity. “Control” for purposes of this definition means (i) the direct or indirect power to cause the direction or
 * management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the
 * outstanding shares, or (iii) beneficial ownership of such entity. License. Provided that You agree to the conditions
 * described below, caBIG™ Participant grants You a non-exclusive, worldwide, perpetual, fully-paid-up, no-charge,
 * irrevocable, transferable and royalty-free right and license in its rights in the caBIG™ Software, including any
 * copyright or patent rights therein, to (i) use, install, disclose, access, operate, execute, reproduce, copy, modify,
 * translate, market, publicly display, publicly perform, and prepare derivative works of the caBIG™ Software in any
 * manner and for any purpose, and to have or permit others to do so; (ii) make, have made, use, practice, sell, and
 * offer for sale, import, and/or otherwise dispose of caBIG™ Software (or portions thereof); (iii) distribute and have
 * distributed to and by third parties the caBIG™ Software and any modifications and derivative works thereof; and (iv)
 * sublicense the foregoing rights set out in (i), (ii) and (iii) to third parties, including the right to license such
 * rights to further third parties. For sake of clarity, and not by way of limitation, caBIG™ Participant shall have no
 * right of accounting or right of payment from You or Your sub licensees for the rights granted under this License.
 * This License is granted at no charge to You. Your downloading, copying, modifying, displaying, distributing or use of
 * caBIG™ Software constitutes acceptance of all of the terms and conditions of this Agreement. If You do not agree to
 * such terms and conditions, You have no right to download, copy, modify, display, distribute or use the caBIG™
 * Software. 1. Your redistributions of the source code for the caBIG™ Software must retain the above copyright notice,
 * this list of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in
 * object code form must reproduce the above copyright notice, this list of conditions and the disclaimer of Article 6
 * in the documentation and/or other materials provided with the distribution, if any. 2. Your end-user documentation
 * included with the redistribution, if any, must include the following acknowledgment: “This product includes software
 * developed by ScenPro, Inc.” If You do not include such end-user documentation, You shall include this acknowledgment
 * in the caBIG™ Software itself, wherever such third-party acknowledgments normally appear. 3. You may not use the
 * names “ScenPro, Inc.”, “The National Cancer Institute”, “NCI”, “Cancer Bioinformatics Grid” or “caBIG™” to endorse or
 * promote products derived from this caBIG™ Software. This License does not authorize You to use any trademarks,
 * service marks, trade names, logos or product names of either caBIG™ Participant, NCI or caBIG™, except as required to
 * comply with the terms of this License. 4. For sake of clarity, and not by way of limitation, You may incorporate this
 * caBIG™ Software into Your proprietary programs and into any third party proprietary programs. However, if You
 * incorporate the caBIG™ Software into third party proprietary programs, You agree that You are solely responsible for
 * obtaining any permission from such third parties required to incorporate the caBIG™ Software into such third party
 * proprietary programs and for informing Your sub licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before incorporating the caBIG™ Software into
 * such third party proprietary software programs. In the event that You fail to obtain such permissions, You agree to
 * indemnify caBIG™ Participant for any claims against caBIG™ Participant by such third parties, except to the extent
 * prohibited by law, resulting from Your failure to obtain such permissions. 5. For sake of clarity, and not by way of
 * limitation, You may add Your own copyright statement to Your modifications and to the derivative works, and You may
 * provide additional or different license terms and conditions in Your sublicenses of modifications of the caBIG™
 * Software, or any derivative works of the caBIG™ Software as a whole, provided Your use, reproduction, and
 * distribution of the Work otherwise complies with the conditions stated in this License. 6. THIS caBIG™ SOFTWARE IS
 * PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
 * ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG™ SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.exception.PADuplicateException;
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
    static final String ACT_FACILITY_SAVE = "facilitySave";
    static final String ACT_EDIT = "edit";
    static final String ACT_DELETE = "delete";
    private StudyParticipationServiceRemote sPartService;
    private StudySiteAccrualStatusServiceRemote ssasService;
    private StudyParticipationContactServiceRemote sPartContactService;
    OrganizationCorrelationServiceRemote oCService;
    CorrelationUtilsRemote cUtils;
    
    private Ii spIi;
    List<OrganizationWebDTO> organizationList = null;
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
        facilitySaveOrUpdate();
        if (hasFieldErrors()) {
            return ERROR;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        return ACT_FACILITY_SAVE;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String facilityUpdate() throws Exception {
        setRecStatus(ServletActionContext.getRequest().getParameter("recStatus"));
        setRecStatusDate(ServletActionContext.getRequest().getParameter("recStatusDate"));
        setTargetAccrualNumber(ServletActionContext.getRequest().getParameter("targetAccrualNumber"));
        facilitySaveOrUpdate();
        if (hasFieldErrors()) {
            return "error_edit";
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        return ACT_EDIT;
    }

    /**
     * @throws Exception exception
     */
    public void facilitySaveOrUpdate() throws Exception {
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
              .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab != null) {
            Organization org = tab.getFacilityOrganization();
            orgFromPO.setOrgCity(org.getCity());
            orgFromPO.setOrgCountry(org.getCountryName());
            orgFromPO.setOrgName(org.getName());
            orgFromPO.setOrgZip(org.getPostalCode());
        }
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return;
        }
        StudyParticipationDTO sp;
        if (tab.getStudyParticipationId() != null) {
            sp = sPartService.get(IiConverter.convertToIi(tab.getStudyParticipationId()));
            Integer iTargetAccrual = (targetAccrualNumber == null) ? null : Integer.parseInt(targetAccrualNumber);
            if (IntConverter.convertToInteger(sp.getTargetAccrualNumber()) != iTargetAccrual) {
                sp.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
                try {
                    sp = sPartService.update(sp);
                } catch (PADuplicateException e) {
                    addFieldError("editOrg.name", e.getMessage());
                    return;
                }
            }
        } else {
            String poOrgId = tab.getFacilityOrganization().getIdentifier();
            Long paHealthCareFacilityId = oCService.createHealthCareFacilityCorrelations(poOrgId);
            sp = new StudyParticipationDTO();
            sp.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
            sp.setHealthcareFacilityIi(IiConverter.convertToIi(paHealthCareFacilityId));
            sp.setIdentifier(null);
            sp.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
            sp.setStatusDateRangeLow(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
            sp.setStudyProtocolIdentifier(spIi);
            sp.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
            try {
                sp = sPartService.create(sp);
            } catch (PADuplicateException e) {
                addFieldError("editOrg.name", e.getMessage());
                return;
            }
        }
        List<StudySiteAccrualStatusDTO> currentStatus = ssasService
                .getCurrentStudySiteAccrualStatusByStudyParticipation(sp.getIdentifier());
        if (currentStatus.isEmpty() || !currentStatus.get(0).getStatusCode().getCode().equals(recStatus)
                || !currentStatus.get(0).getStatusDate().equals(PAUtil.dateStringToTimestamp(recStatusDate))) {
          StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
          ssas.setIdentifier(IiConverter.convertToIi((Long) null));
          ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(recStatus)));
          ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(recStatusDate)));
          ssas.setStudyParticipationIi(sp.getIdentifier());
          ssas = ssasService.createStudySiteAccrualStatus(ssas);
        }
        tab.setStudyParticipationId(IiConverter.convertToLong(sp.getIdentifier()));
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (PAUtil.isNotEmpty(tab.getFacilityOrganization().getName())) {
            setOrganizationName("for " + tab.getFacilityOrganization().getName());
        }
        setCurrentAction("edit");
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
        return ACT_EDIT;
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
            if (PAUtil.isEmpty(email)) {
                addFieldError("personContactWebDTO.email", getText("error.enterEmailAddress"));
                hasErrors = true;
            }
            if (PAUtil.isNotEmpty(email) && (!PAUtil.isValidEmail(email))) {
                addFieldError("personContactWebDTO.email", getText("error.enterValidEmail"));
                hasErrors = true;
            }
            if (PAUtil.isEmpty(telephone)) {
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
                    gov.nih.nci.pa.dto.PersonDTO personDTO = EnPnConverter.convertToPaPersonDTO(selectedPersTO);
                    personContactWebDTO.setFirstName(personDTO.getFirstName());
                    personContactWebDTO.setLastName(personDTO.getLastName());
                    personContactWebDTO.setMiddleName(personDTO.getMiddleName());
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
            telephone = telephone.replaceAll(" ", "");
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
        if (PAUtil.isEmpty(email)) {
            addFieldError("personContactWebDTO.email", getText("error.enterEmailAddress"));
        }
        if (PAUtil.isNotEmpty(email) && (!PAUtil.isValidEmail(email))) {
            addFieldError("personContactWebDTO.email", getText("error.enterValidEmail"));
        }
        if (PAUtil.isEmpty(telephone)) {
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
            gov.nih.nci.pa.dto.PersonDTO personDTO =  EnPnConverter.convertToPaPersonDTO(selectedPersTO);
            personContactWebDTO.setFirstName(personDTO.getFirstName());
            personContactWebDTO.setLastName(personDTO.getLastName());
            personContactWebDTO.setMiddleName(personDTO.getMiddleName());
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
        clearErrorsAndMessages();
        if (PAUtil.isEmpty(getRecStatus())) {
            addFieldError("recStatus", getText("error.participatingStatus"));
        }
        if (PAUtil.isEmpty(getRecStatusDate())) {
            addFieldError("recStatusDate", getText("error.participatingStatusDate"));
        }
        if (PAUtil.isEmpty(orgFromPO.getOrgName())) {
            addFieldError("editOrg.name", "Please choose an organization");
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
