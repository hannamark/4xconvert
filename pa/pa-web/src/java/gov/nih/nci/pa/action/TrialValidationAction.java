/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
* @author Naveen AMiruddin
*
*/
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class TrialValidationAction extends ActionSupport {
    private static final String FALSE = "FALSE";
    private static final long serialVersionUID = -6587531774808791496L;
    private static final String EDIT = "edit";
    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    private OrganizationDTO selectedLeadOrg = null;
    private List<PaPersonDTO> persons = new ArrayList<PaPersonDTO>();
    private static final int MAXIMUM_CHAR = 200;
    private static final String SPONSOR = "sponsor";
    private static final String UNDEFINED = "undefined";
    private static final String REJECT_OPERATION = "Reject";
    private List<Country> countryList = new ArrayList<Country>();

    /**
     * @return res
     */
    public String query() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            TrialHelper helper = new TrialHelper();
            gtdDTO = helper.getTrialDTO(studyProtocolIi, "validation");
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return EDIT;
    }

    /**
     *
     * @return String
     */
    public String update() {
        enforceBusinessRules("");
        if (hasFieldErrors()) {
            return EDIT;
        }
        try {
        save();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return EDIT;
    }
    /**
     *
     * @return String
     */
    public String accept() {
        enforceBusinessRules("");
        //check if submission number is greater than 1 then it is amend
        if (isTrialForAmendment(gtdDTO.getSubmissionNumber())
                && PAUtil.isEmpty(gtdDTO.getAmendmentReasonCode())) {
           addFieldError("gtdDTO.amendmentReasonCode", "Amendment Reason Code is Required.");
        }
        if (hasFieldErrors()) {
            return EDIT;
        }
        try { 
            save();
            TrialHelper helper = new TrialHelper();
            
            createMilestones(MilestoneCode.SUBMISSION_ACCEPTED);
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Study Protocol Accepted");
            ServletActionContext.getRequest().getSession().setAttribute(Constants.DOC_WFS_MENU,
                    helper.setMenuLinks(DocumentWorkflowStatusCode.ACCEPTED));
            //send mail only if the trial is Amended
            if (isTrialForAmendment(gtdDTO.getSubmissionNumber())) {
                //send mail
                PoPaServiceBeanLookup.getMailManagerService()
                .sendAmendAcceptEmail(IiConverter.convertToIi(gtdDTO.getStudyProtocolId()));
            } else {
                PoPaServiceBeanLookup.getMailManagerService()
                .sendAcceptEmail(IiConverter.convertToIi(gtdDTO.getStudyProtocolId()));
            }
        } catch (Exception e) {
                ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return EDIT;
    }

    /**
     *
     * @return String
     */
    public String reject() {
        enforceBusinessRules(REJECT_OPERATION);
        if (hasFieldErrors()) {
            return EDIT;
        }
        try {
        save();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        ServletActionContext.getRequest().getSession().setAttribute("submissionNumber", gtdDTO.getSubmissionNumber());
        return "rejectReason";
    }

    /**
     *  @return String
     */
    public String rejectReason() {
        if (PAUtil.isEmpty(gtdDTO.getCommentText())) {
            addFieldError("gtdDTO.commentText", getText("Rejection Reason must be Entered"));
        }
        if (hasFieldErrors()) {
            return "rejectReason";
        }
        String submissionNo = "submissionNumber";
        try {
            Integer intSubNo = (Integer) ServletActionContext.getRequest().getSession().getAttribute(submissionNo); 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                Constants.STUDY_PROTOCOL_II);
            TrialHelper helper = new TrialHelper();
            
            //if trial is amend then hard delete 
            if (isTrialForAmendment(intSubNo)) {
                //send mail
                PoPaServiceBeanLookup.getMailManagerService()
                  .sendAmendRejectEmail(studyProtocolIi, gtdDTO.getCommentText());
                //PaRegistry.getStudyProtocolService().deleteStudyProtocol(studyProtocolIi);
                PaRegistry.getTrialRegistrationService().reject(studyProtocolIi, 
                  StConverter.convertToSt(gtdDTO.getCommentText()));
                ServletActionContext.getRequest().getSession().removeAttribute(submissionNo);
                ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_SUMMARY);
                ServletActionContext.getRequest().getSession().removeAttribute(Constants.STUDY_PROTOCOL_II);
                ServletActionContext.getRequest().getSession().removeAttribute(Constants.DOC_WFS_MENU); 
                return "amend_reject"; 
            } else {
                createMilestones(MilestoneCode.SUBMISSION_REJECTED);
                ServletActionContext.getRequest().getSession().setAttribute(Constants.DOC_WFS_MENU,
                    helper.setMenuLinks(DocumentWorkflowStatusCode.REJECTED));
            }    
            PaRegistry.getMailManagerService().sendRejectionEmail(studyProtocolIi);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        query();
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Study Protocol Rejected");
        ServletActionContext.getRequest().getSession().removeAttribute(submissionNo);
        return EDIT;
    }

    /**
     * Creates the milestones.
     * 
     * @param msc the msc
     * @throws PAException e
     */
    private void createMilestones(MilestoneCode msc) throws PAException {
        
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            PAServiceUtils paServiceUtil = new PAServiceUtils();
            paServiceUtil.createMilestone(studyProtocolIi, msc, StConverter.convertToSt(gtdDTO.getCommentText()));
            if (MilestoneCode.SUBMISSION_ACCEPTED.equals(msc)) {
               paServiceUtil.createMilestone(studyProtocolIi, MilestoneCode.READY_FOR_PDQ_ABSTRACTION, null);
            }   
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry.getProtocolQueryService()
            .getTrialSummaryByStudyProtocolId(Long.valueOf(studyProtocolIi.getExtension()));
        
            // put an entry in the session and store StudyProtocolQueryDTO
            ServletActionContext.getRequest().getSession().setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
    }

    private void save() throws PAException, NullifiedEntityException, NullifiedRoleException {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    Constants.STUDY_PROTOCOL_II);
            TrialHelper helper = new TrialHelper();
            helper.saveTrial(studyProtocolIi, gtdDTO, "Validation");
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry.getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(Long.valueOf(studyProtocolIi.getExtension()));
            // put an entry in the session and store StudyProtocolQueryDTO
            studyProtocolQueryDTO.setOfficialTitle(PAUtil.trim(studyProtocolQueryDTO.getOfficialTitle(),
                    PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE));
            ServletActionContext.getRequest().getSession().setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.DOC_WFS_MENU,
                    helper.setMenuLinks(studyProtocolQueryDTO.getDocumentWorkflowStatusCode()));
    }
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private void enforceBusinessRules(String operation) {   
        if (PAUtil.isEmpty(gtdDTO.getLocalProtocolIdentifier())) {
            addFieldError("gtdDTO.LocalProtocolIdentifier", getText("Organization Trial ID must be Entered"));
        }
        if (PAUtil.isEmpty(gtdDTO.getOfficialTitle())) {
            addFieldError("gtdDTO.OfficialTitle", getText("OfficialTitle must be Entered"));
        }
        if (REJECT_OPERATION.equalsIgnoreCase(operation) && PAUtil.isNotEmpty(gtdDTO.getProprietarytrialindicator()) 
                && gtdDTO.getProprietarytrialindicator().equalsIgnoreCase("true")) {
            if (PAUtil.isEmpty(gtdDTO.getNctIdentifier())) {
                if (PAUtil.isEmpty(gtdDTO.getPhaseCode())) {
                    addFieldError("gtdDTO.phaseCode", getText("error.phase"));
                }
                if (PAUtil.isEmpty(gtdDTO.getPrimaryPurposeCode())) {
                    addFieldError("gtdDTO.primaryPurposeCode", getText("error.primary"));
                }
            }
        } else {
            if (PAUtil.isEmpty(gtdDTO.getPhaseCode())) {
                addFieldError("gtdDTO.phaseCode", getText("error.phase"));
            }
            if (PAUtil.isEmpty(gtdDTO.getPrimaryPurposeCode())) {
                addFieldError("gtdDTO.primaryPurposeCode", getText("error.primary"));
            }
        }
        if (gtdDTO.getProprietarytrialindicator() == null 
                || gtdDTO.getProprietarytrialindicator().equalsIgnoreCase(FALSE)) {
            if (PAUtil.isNotEmpty(gtdDTO.getPrimaryPurposeCode())
                    && gtdDTO.getPrimaryPurposeCode().equalsIgnoreCase("other")
                    && PAUtil.isEmpty(gtdDTO.getPrimaryPurposeOtherText())) {
                addFieldError("gtdDTO.primaryPurposeOtherText",
                        getText("Primary Purpose Other other text must be entered"));
            }
            if (PAUtil.isNotEmpty(gtdDTO.getPhaseCode()) && gtdDTO.getPhaseCode().equalsIgnoreCase("other")
                    && PAUtil.isEmpty(gtdDTO.getPhaseOtherText())) {
                addFieldError("gtdDTO.phaseOtherText", getText("Phase Code other text must be entered"));
            }
            if (PAUtil.isNotEmpty(gtdDTO.getPrimaryPurposeOtherText())
                    && gtdDTO.getPrimaryPurposeOtherText().length() > MAXIMUM_CHAR) {
                addFieldError("gtdDTO.primaryPurposeOtherText", getText("error.spType.other.maximumChar"));
            }
            if (PAUtil.isEmpty(gtdDTO.getSponsorIdentifier())) {
                addFieldError("gtdDTO.sponsorName", getText("Sponsor must be entered"));
            }
            if (SPONSOR.equalsIgnoreCase(gtdDTO.getResponsiblePartyType())
                    && PAUtil.isEmpty(gtdDTO.getResponsiblePersonIdentifier())) {
                addFieldError("gtdDTO.responsibleGenericContactName",
                                getText("Please choose Either Personal Contact or Generic Contact "));
            }
            if (PAUtil.isEmpty(gtdDTO.getContactEmail())) {
                addFieldError("gtdDTO.contactEmail", getText("Email must be Entered"));
            }
            if (PAUtil.isNotEmpty(gtdDTO.getContactEmail()) && !PAUtil.isValidEmail(gtdDTO.getContactEmail())) {
                addFieldError("gtdDTO.contactEmail", getText("Email entered is not a valid format"));
            }
            if (PAUtil.isEmpty(gtdDTO.getContactPhone())) {
                addFieldError("gtdDTO.contactPhone", getText("Phone must be Entered"));
            }
        }
    }
    /**
     *
     * @return gtdDTO
     */
    public GeneralTrialDesignWebDTO getGtdDTO() {
        return gtdDTO;
    }

    /**
     *
     * @param gtdDTO gtdDTO
     */
    public void setGtdDTO(GeneralTrialDesignWebDTO gtdDTO) {
        this.gtdDTO = gtdDTO;
    }

    /**
     *
     * @return result
     */
    public String displayLeadOrganization() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (UNDEFINED.equalsIgnoreCase(orgId)) {
            return "display_org";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedLeadOrg = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
            gtdDTO.setLeadOrganizationName(selectedLeadOrg.getName().getPart().get(0).getValue());
            gtdDTO.setLeadOrganizationIdentifier(selectedLeadOrg.getIdentifier().getExtension());
        } catch (Exception e) {
            return "display_lead_org";
        }
        return "display_lead_org";
    }

    /**
     *
     * @return result
     */
    public String displayLeadPrincipalInvestigator() {
        PersonDTO selectedLeadPrincipalInvestigator;
        String persId = ServletActionContext.getRequest().getParameter("persId");
        if (UNDEFINED.equalsIgnoreCase(persId)) {
            return "display_lead_prinicipal_inv";
        }
        try {
            selectedLeadPrincipalInvestigator = PoRegistry.getPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gtdDTO.setPiIdentifier(selectedLeadPrincipalInvestigator.getIdentifier().getExtension());
            gov.nih.nci.pa.dto.PaPersonDTO personDTO = EnPnConverter
                    .convertToPaPersonDTO(selectedLeadPrincipalInvestigator);
            gtdDTO.setPiName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (Exception e) {
            return "display_lead_prinicipal_inv";
        }
        return "display_lead_prinicipal_inv";
    }

    /**
     *
     * @return result
     */
    public String displaySelectedSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO selectedSponsor = null;
        OrganizationDTO criteria = new OrganizationDTO();
        if (UNDEFINED.equalsIgnoreCase(orgId)) {
            return "display_selected_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedSponsor = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
            gtdDTO.setSponsorIdentifier(selectedSponsor.getIdentifier().getExtension());
            gtdDTO.setSponsorName(selectedSponsor.getName().getPart().get(0).getValue());
        } catch (Exception e) {
            return "display_selected_sponsor";
        }
        return "display_selected_sponsor";
    }

    /**
     *
     * @return result
     */
    public String displaySummary4FundingSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        OrganizationDTO selectedSummary4Sponsor = null;
        if (UNDEFINED.equalsIgnoreCase(orgId)) {
            return "display_summary4funding_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedSummary4Sponsor = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
            gtdDTO.setSummaryFourOrgName(selectedSummary4Sponsor.getName().getPart().get(0).getValue());
            gtdDTO.setSummaryFourOrgIdentifier(selectedSummary4Sponsor.getIdentifier().getExtension());
        } catch (Exception e) {
            return "display_summary4funding_sponsor";
        }
        return "display_summary4funding_sponsor";
    }

    /**
     *
     * @return result
     */
    public String displayCentralContact() {
        PersonDTO centralContact = null;
        String persId = ServletActionContext.getRequest().getParameter("persId");
        try {
            centralContact = PoRegistry.getPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gov.nih.nci.pa.dto.PaPersonDTO personDTO = EnPnConverter.convertToPaPersonDTO(centralContact);
            gtdDTO.setCentralContactIdentifier(centralContact.getIdentifier().getExtension());
            gtdDTO.setCentralContactName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (Exception e) {
            return "central_contact";
        }
        return "central_contact";
    }

    /**
     *
     * @return res
     */
    public String getOrganizationContacts() {
        String orgContactIdentifier = ServletActionContext.getRequest().getParameter("orgContactIdentifier");
        Ii contactIi = IiConverter.convertToPoOrganizationIi(orgContactIdentifier);
        OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
        contactDTO.setScoperIdentifier(contactIi);
        try {
            getCountriesList();
            List<OrganizationalContactDTO> list = PoRegistry.getOrganizationalContactCorrelationService().search(
                    contactDTO);
            for (OrganizationalContactDTO organizationalContactDTO : list) {
                try {
                    PersonDTO resultDTO = PoRegistry.getPersonEntityService().getPerson(
                            organizationalContactDTO.getPlayerIdentifier());
                    // persons.add(convertToPaPerson(resultDTO));
                    persons.add(EnPnConverter.convertToPaPersonDTO(resultDTO));
                } catch (NullifiedEntityException e) {
                    addActionError(e.getMessage());
                    ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
                    LOG.error("Exception occured while getting organization contact : " + e);
                    return "display_org_contacts";
                }
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
            LOG.error("Exception occured while getting organization contact : " + e);
            return "display_org_contacts";
        }
        return "display_org_contacts";
    }

    /**
     *
     * @return res
     */
    public String createOrganizationContacts() {
        String persId = null;
        try {
            //String orgId = ServletActionContext.getRequest().getParameter("orgId");
            persId = ServletActionContext.getRequest().getParameter("persId");
            PersonDTO selectedLeadPrincipalInvestigator = PoRegistry.getPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gtdDTO.setResponsiblePersonIdentifier(selectedLeadPrincipalInvestigator.getIdentifier().getExtension());
            gov.nih.nci.pa.dto.PaPersonDTO personDTO = EnPnConverter
                    .convertToPaPersonDTO(selectedLeadPrincipalInvestigator);
            gtdDTO.setResponsiblePersonName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (NullifiedEntityException e) {
            LOG.error("got Nullified exception from PO for person Id " + persId);
        } catch (PAException e) {
            LOG.error("got PAException for person Id " + persId);
        }
        return "display_responsible_contact";
    }

    /**
     *
     * @return selectedLeadOrg
     */
    public OrganizationDTO getSelectedLeadOrg() {
        return selectedLeadOrg;
    }

    /**
     *
     * @param selectedLeadOrg selectedLeadOrg
     */
    public void setSelectedLeadOrg(OrganizationDTO selectedLeadOrg) {
        this.selectedLeadOrg = selectedLeadOrg;
    }

    /**
     * @return the persons
     */
    public List<PaPersonDTO> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<PaPersonDTO> persons) {
        this.persons = persons;
    }


    @SuppressWarnings("unchecked")
    private void getCountriesList() throws PAException {
        countryList = (List) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
        if (countryList == null) {
            countryList = PaRegistry.getLookUpTableService().getCountries();
            ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
        }
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
    private boolean isTrialForAmendment(Integer submissionNumber) {
        if (submissionNumber > 1) {
            return true;
        }
        return false;
    }
}