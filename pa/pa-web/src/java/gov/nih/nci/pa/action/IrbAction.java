package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.ContactWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for IRB.
 *
 * @author Hugh Reinhart
 * @since 11/21/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.SignatureDeclareThrowsException", "PMD.CyclomaticComplexity"
        , "PMD.NPathComplexity", "PMD.TooManyFields" })
public class IrbAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1230909090L;

    private static final String YES = "Yes";
    private static final String NO = "No";
    private StudyParticipationServiceRemote sPartService;
    private StudyProtocolServiceRemote sProtService;
    private OrganizationCorrelationServiceBean orgCorrService;
    private CorrelationUtils correlationUtils;
    private Ii spIdIi;

    private Map<String, String>  siteRelatedList;
    private Map<String, String>  candidateBoardList;
    private Map<String, String>  candidateAffiliationList;

    private String approvalStatus;
    private String approvalNumber;
    private String siteRelated;
    
    private ContactWebDTO ct = new ContactWebDTO();
    
    private String contactAffiliation;
    private String boardChanged;
    private String newOrgId;
    private String newOrgName;

    /**
     * @throws Exception exception
     */
    public void prepare() throws Exception {
        sProtService = PaRegistry.getStudyProtocolService();
        sPartService = PaRegistry.getStudyParticipationService();
        orgCorrService = new OrganizationCorrelationServiceBean();
        correlationUtils = new CorrelationUtils();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
            .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
        setSiteRelatedList();
        setCandidateAffiliationList();
    }

    /**
     * @return action
     * @throws Exception exception
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return SUCCESS;
    }
    
    /**
     * @return action
     * @throws Exception exception
     */
    public String fromPO() throws Exception {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        loadOrg(orgId);
        return SUCCESS;
    }
    
    /*
     * Business rules which are form specific or multi-service.
     */
    private void businessRules() {
        if ((getApprovalStatus() == null) || (getApprovalStatus().length() == 0)) {
            addActionError("Must select an approval status.  ");
        }
        if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.equals(getApprovalStatusEnum())
            || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.equals(getApprovalStatusEnum())) {
            if (getSiteRelated() == null) {
                addActionError("Must select if this is a site related board approval.  ");
            }
            if ((ct.getName() == null) || (ct.getName().length() == 0)) {
                addActionError("The organziation name must be selected.  ");
            } else {
                if ((ct.getAddress() == null) || (ct.getAddress().length() == 0)) {
                    addActionError("Address must be set; use PO Curation tool.  ");
                }
                if ((ct.getCity() == null) || (ct.getCity().length() == 0)) {
                    addActionError("City must be set; use PO Curation tool.  ");
                }
                if ((ct.getState() == null) || (ct.getState().length() == 0)) {
                    addActionError("State must be set; use PO Curation tool.  ");
                }
                if ((ct.getZip() == null) || (ct.getZip().length() == 0)) {
                    addActionError("Zip/postal code must be set; use PO Curation tool.  ");
                }
                if ((ct.getCountry() == null) || (ct.getCountry().length() == 0)) {
                    addActionError("Country must be set; use PO Curation tool.  ");
                }
                if ((ct.getPhone() == null) || (ct.getPhone().length() == 0)) {
                    addActionError("A phone number must be set; use PO Curation tool.  ");
                }
                if ((ct.getEmail() == null) || (ct.getEmail().length() == 0)) {
                    addActionError("A contact e-mail address must be set; use PO Curation tool.  ");
                }
            }
        }
        
    }

    /**
     * @return action
     * @throws Exception exception
     */
    public String save() throws Exception {
        businessRules();
        if (hasActionErrors()) {
            setCandidateBoardList();
            return SUCCESS;
        }
        try {
            if (ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.equals(getApprovalStatusEnum())) {
                saveSubmissionNotRequired();
            } else if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.equals(getApprovalStatusEnum()) 
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.equals(getApprovalStatusEnum())) {
                saveSubmissionRequired();
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            setCandidateBoardList();
            return SUCCESS;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return SUCCESS;
    }
    
    private void saveSubmissionNotRequired() throws Exception {
        StudyProtocolDTO dto = sProtService.getStudyProtocol(spIdIi);
        dto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(false));
        sProtService.updateStudyProtocol(dto);
        
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
        for (StudyParticipationDTO sp : spList) {
            if (!PAUtil.isCdNull(sp.getReviewBoardApprovalStatusCode())) {
                sp.setReviewBoardApprovalStatusCode(null);
                sPartService.update(sp);
            }
        }
    }
    
    private void saveSubmissionRequired() throws Exception {
        Ii sPartToUpdate = this.getStudyParticipationToUpdate();
        String poOrgId = ct.getName();
        if (poOrgId == null || poOrgId.equals("")) {
            throw new PAException("Board name must be set for '" + getApprovalStatus() + "'.  ");
        }
        Long oversightCommitteeId = orgCorrService.createOversightCommitteeCorrelations(poOrgId);
        
        StudyParticipationDTO partDto = sPartService.get(sPartToUpdate);
        partDto.setOversightCommitteeIi(IiConverter.convertToIi(oversightCommitteeId));
        partDto.setReviewBoardApprovalNumber(StConverter.convertToSt(getApprovalNumber()));
        partDto.setReviewBoardApprovalStatusCode(CdConverter.convertToCd(getApprovalStatusEnum()));
        sPartService.update(partDto);
        
        StudyProtocolDTO studyDto = sProtService.getStudyProtocol(spIdIi);
        studyDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(true));
        sProtService.updateStudyProtocol(studyDto);
    }
    
    private Ii getStudyParticipationToUpdate() throws Exception {
        Ii sPartToUpdate = null;
        // if site related use candidate affiliation selected else use lead organization
        if (getSiteRelated().equals(YES)) {
            if (getContactAffiliation() == null || getContactAffiliation().equals("")) {
                throw new PAException("Contact affiliation required for site related board.  ");
            }
            List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
            for (StudyParticipationDTO sp : spList) {
                Long hcfId = IiConverter.convertToLong(sp.getHealthcareFacilityIi());
                if (hcfId != null) {
                    Organization paOrg = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(hcfId);
                    if (paOrg.getIdentifier().equals(getContactAffiliation())) {
                        sPartToUpdate = sp.getIdentifier();
                    }
                }
            }
            if (sPartToUpdate == null) {
                throw new PAException("Unable to locate study participation to store IRB data.  ");
            }
        } else {
            List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
            for (StudyParticipationDTO sp : spList) {
                if (StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getCode().equals(
                        sp.getFunctionalCode().getCode())) {
                    sPartToUpdate = sp.getIdentifier();
                }
            }
            if (sPartToUpdate == null) {
                throw new PAException("Unable to get lead organization to store IRB data.  ");
            }
        }
        return sPartToUpdate;
    }

    private void setSiteRelatedList() {
        siteRelatedList = new HashMap<String, String>();
        siteRelatedList.put(YES, YES);
        siteRelatedList.put(NO, NO);
    }

    private void setCandidateBoardList() throws Exception {
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
        candidateBoardList = new HashMap<String, String>();
        for (StudyParticipationDTO sp : spList) {
            if (CdConverter.convertCdToString(sp.getFunctionalCode()).equals(
                    StudyParticipationFunctionalCode.TREATING_SITE.getCode())
                || CdConverter.convertCdToString(sp.getFunctionalCode()).equals(
                    StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getCode())) {
                Long id = IiConverter.convertToLong(sp.getHealthcareFacilityIi());
                if (id != null) {
                    Organization org = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(id);
                    candidateBoardList.put(org.getIdentifier(), org.getName());
                }
            }
            Long id = IiConverter.convertToLong(sp.getOversightCommitteeIi());
            if (id != null) {
                Organization org = correlationUtils.getPAOrganizationByPAOversightCommitteeId(id);
                candidateBoardList.put(org.getIdentifier(), org.getName());
            }
        }
        if (!PAUtil.isEmpty(newOrgId)) {
            candidateBoardList.put(newOrgId, newOrgName);
        }
    }

    private void setCandidateAffiliationList() throws Exception {
        List<StudyParticipationDTO> spList = sPartService.getByStudyProtocol(spIdIi);
        candidateAffiliationList = new HashMap<String, String>();
        for (StudyParticipationDTO sp : spList) {
            if (CdConverter.convertCdToString(sp.getFunctionalCode()).equals(
                    StudyParticipationFunctionalCode.TREATING_SITE.getCode())) {
                Long id = IiConverter.convertToLong(sp.getHealthcareFacilityIi());
                if (id != null) {
                    Organization org = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(
                            IiConverter.convertToLong(sp.getHealthcareFacilityIi()));
                    candidateAffiliationList.put(org.getIdentifier(), org.getName());
                }
            }
        }
    }

    private void loadForm() throws Exception {
        setCandidateBoardList();
        StudyProtocolDTO study = sProtService.getStudyProtocol(spIdIi);
        Boolean b = BlConverter.covertToBoolean(study.getReviewBoardApprovalRequiredIndicator());
        if (b == null || !b) {
            setApprovalStatus(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode());
            setApprovalNumber(null);
            setSiteRelated(null);
            ct.setName(null);
            ct.setAddress(null);
            ct.setCity(null);
            ct.setState(null);
            ct.setZip(null);
            ct.setCountry(null);
            ct.setPhone(null);
            ct.setEmail(null);
            setContactAffiliation(null);
        } else {
            List<StudyParticipationDTO> partList = sPartService.getByStudyProtocol(spIdIi);
            for (StudyParticipationDTO part : partList) {
                if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                        part.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                            part.getReviewBoardApprovalStatusCode().getCode())) {
                    
                  setApprovalStatus(part.getReviewBoardApprovalStatusCode().getCode());
                  setApprovalNumber(StConverter.convertToString(part.getReviewBoardApprovalNumber()));
                  setSiteRelated(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getCode().
                          equals(CdConverter.convertCdToString(part.getFunctionalCode())) ? NO : YES);
                  Organization paOrg = correlationUtils.getPAOrganizationByPAOversightCommitteeId(
                          IiConverter.convertToLong(part.getOversightCommitteeIi()));
                  loadOrg(paOrg.getIdentifier());
                  paOrg = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(
                          IiConverter.convertToLong(part.getHealthcareFacilityIi()));
                  setContactAffiliation(paOrg.getIdentifier());
                }
            }
        }
    }
    
    private void loadOrg(String poOrgId) throws Exception {
        OrganizationDTO poOrg = PaRegistry.getPoOrganizationEntityService().
            getOrganization(IiConverter.converToPoOrganizationIi(poOrgId));
        if (poOrg == null) {
            throw new PAException("Error getting organization data from PO for id = " + poOrgId
                    + ".  Check that PO service is running and databases are synchronized.  ");
        }
        newOrgId = poOrgId;
        newOrgName = EnOnConverter.convertEnOnToString(poOrg.getName());
        setCandidateBoardList();
        ct = new ContactWebDTO();
        ct.setName(newOrgId);
        
        List<Adxp> adxpList = poOrg.getPostalAddress().getPart();
        for (Adxp adxp : adxpList) {
            if (adxp instanceof AdxpAl) {
                ct.setAddress(adxp.getValue());
            }
            if (adxp instanceof AdxpCty) {
                ct.setCity(adxp.getValue());
            }
            if (adxp instanceof AdxpSta) {
                ct.setState(adxp.getValue());
            }
            if (adxp instanceof AdxpZip) {
                ct.setZip(adxp.getValue());
            }
            if (adxp instanceof AdxpCnt) {
                ct.setCountry(adxp.getCode());
            }
        }
        Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
        boolean eMailSet = false;
        boolean phoneSet = false;
        for (Object tel : telList) {
            if (!eMailSet && tel instanceof TelEmail) {
                ct.setEmail(((TelEmail) tel).getValue().getSchemeSpecificPart());
                eMailSet = true;
            }
            if (!phoneSet && tel instanceof TelPhone) {
                ct.setPhone(((TelPhone) tel).getValue().getSchemeSpecificPart());
                phoneSet = true;
            }
        }
    }
    
    private ReviewBoardApprovalStatusCode getApprovalStatusEnum() {
        return ReviewBoardApprovalStatusCode.getByCode(getApprovalStatus());
    }
    
    /**
     * @return the approvalStatus
     */
    public String getApprovalStatus() {
        return approvalStatus;
    }

    /**
     * @param approvalStatus the approvalStatus to set
     */
    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    /**
     * @return the approvalNumber
     */
    public String getApprovalNumber() {
        return approvalNumber;
    }

    /**
     * @param approvalNumber the approvalNumber to set
     */
    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    /**
     * @return the siteRelatedList
     */
    public Map<String, String> getSiteRelatedList() {
        return siteRelatedList;
    }

    /**
     * @return the siteRelated
     */
    public String getSiteRelated() {
        return siteRelated;
    }

    /**
     * @param siteRelated the siteRelated to set
     */
    public void setSiteRelated(String siteRelated) {
        this.siteRelated = siteRelated;
    }

    /**
     * @return the candidateBoardList
     */
    public Map<String, String> getCandidateBoardList() {
        return candidateBoardList;
    }

    /**
     * @return the candidateAffiliationList
     */
    public Map<String, String> getCandidateAffiliationList() {
        return candidateAffiliationList;
    }

    /**
     * @return the contactAffiliation
     */
    public String getContactAffiliation() {
        return contactAffiliation;
    }

    /**
     * @param contactAffiliation the contactAffiliation to set
     */
    public void setContactAffiliation(String contactAffiliation) {
        this.contactAffiliation = contactAffiliation;
    }

    /**
     * @return the boardChanged
     */
    public String getBoardChanged() {
        return boardChanged;
    }

    /**
     * @param boardChanged the boardChanged to set
     */
    public void setBoardChanged(String boardChanged) {
        this.boardChanged = boardChanged;
    }

    /**
     * @return the ct
     */
    public ContactWebDTO getCt() {
        return ct;
    }

    /**
     * @param ct the ct to set
     */
    public void setCt(ContactWebDTO ct) {
        this.ct = ct;
    }

    /**
     * @return the newOrgId
     */
    public String getNewOrgId() {
        return newOrgId;
    }

    /**
     * @param newOrgId the newOrgId to set
     */
    public void setNewOrgId(String newOrgId) {
        this.newOrgId = newOrgId;
    }

    /**
     * @return the newOrgName
     */
    public String getNewOrgName() {
        return newOrgName;
    }

    /**
     * @param newOrgName the newOrgName to set
     */
    public void setNewOrgName(String newOrgName) {
        this.newOrgName = newOrgName;
    }

}
