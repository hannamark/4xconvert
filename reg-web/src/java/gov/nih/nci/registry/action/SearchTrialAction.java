/***
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Bala Nair
 *
 */
public class SearchTrialAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private List<StudyProtocolQueryDTO> records = new ArrayList<StudyProtocolQueryDTO>();
    private SearchProtocolCriteria criteria = new SearchProtocolCriteria();
    private Long studyProtocolId = null;
    private String trialAction;
    private Long identifier = null;
    /**
     * @return the identifier
     */
    public Long getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }
    private final TrialUtil trialUtil = new TrialUtil();
    private static final Set<StudyStatusCode> UPDATEABLE_STATUS = new HashSet<StudyStatusCode>();
    static {
        UPDATEABLE_STATUS.add(StudyStatusCode.DISAPPROVED);
        UPDATEABLE_STATUS.add(StudyStatusCode.WITHDRAWN);
        UPDATEABLE_STATUS.add(StudyStatusCode.COMPLETE);
        UPDATEABLE_STATUS.add(StudyStatusCode.ADMINISTRATIVELY_COMPLETE);
    }

    private String usercreated;
    /**
     * @return the usercreated
     */
    public String getUsercreated() {
        return usercreated;
    }

    /**
     * @param usercreated the usercreated to set
     */
    public void setUsercreated(String usercreated) {
        this.usercreated = usercreated;
    }

    /**
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }

    /**
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * @return res
     */
    @Override
    public String execute() {
        if (StringUtils.isNotEmpty(trialAction) && (trialAction.equalsIgnoreCase("submit")
                || trialAction.equalsIgnoreCase("amend") || trialAction.equalsIgnoreCase("update"))) {
            String pId = (String) ServletActionContext.getRequest().getSession().getAttribute("protocolId");
            studyProtocolId = Long.valueOf(pId);
            getCriteria().setMyTrialsOnly(true);
            return view();
        }
        return SUCCESS;

    }

    /**
     * @return res
     */
    public String showCriteria() {
        return "criteria";
    }

    /**
     * @return res
     */
    public String query() {
        try {
            // validate the form elements
            validateForm();
            if (hasFieldErrors()) {
                return ERROR;
            }
            List<StudyProtocolQueryDTO> studyProtocolList =
                PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(convertToStudyProtocolQueryCriteria());
            records.addAll(studyProtocolList);
            checkToShowSendXml();
            checkToShowUpdate();
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            ServletActionContext.getRequest().setAttribute("failureMessage" , e.getMessage());
            return ERROR;
        }
    }

    private void checkToShowSendXml() throws PAException {
        for (StudyProtocolQueryDTO queryDto : records) {
            String dwfs = queryDto.getDocumentWorkflowStatusCode().getCode();
            List<String> abstractedCodes =
                Arrays.asList(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode(),
                        DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode());
            if (!queryDto.isProprietaryTrial() && abstractedCodes.contains(dwfs)
                        && queryDto.getCtgovXmlRequiredIndicator() && queryDto.isSearcherTrialOwner()) {
                queryDto.setShowSendXml(true);
            }
        }
    }

    private void checkToShowUpdate() {
        for (StudyProtocolQueryDTO queryDto : records) {
            DocumentWorkflowStatusCode dwfs = queryDto.getDocumentWorkflowStatusCode();
            StudyStatusCode statusCode = queryDto.getStudyStatusCode();
            if (dwfs == null || statusCode == null) {
                queryDto.setUpdate("");
            }

            if (statusCode != null && DocumentWorkflowStatusCode.isStatusAcceptedOrAbove(dwfs)
                        && queryDto.isSearcherTrialOwner() && !UPDATEABLE_STATUS.contains(statusCode)) {
                queryDto.setUpdate("Update");
            } else  {
                queryDto.setUpdate("");
            }

            if (queryDto.isProprietaryTrial() && DocumentWorkflowStatusCode.isStatusAcceptedOrAbove(dwfs)
                    && queryDto.isSearcherTrialOwner()) {
                queryDto.setUpdate("Update");
            }
        }
    }

    /**
     * @return StudyProtocolQueryCriteria
     * @throws PAException
     */
    private StudyProtocolQueryCriteria convertToStudyProtocolQueryCriteria() throws PAException {

        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setOfficialTitle(criteria.getOfficialTitle());
        queryCriteria.setPhaseCode(criteria.getPhaseCode());
        queryCriteria.setPrimaryPurposeCode(criteria.getPrimaryPurposeCode());
        queryCriteria.setPhaseAdditionalQualifierCode(criteria.getPhaseAdditionalQualifierCode());
        if (StringUtils.isNotEmpty(criteria.getIdentifierType())
                 && StringUtils.isNotEmpty(criteria.getIdentifier())) {
            if (criteria.getIdentifierType().equals(Constants.IDENTIFIER_TYPE_NCI)) {
                queryCriteria.setNciIdentifier(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                        Constants.IDENTIFIER_TYPE_LEAD_ORG)) {
                queryCriteria.setLeadOrganizationTrialIdentifier(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                        Constants.IDENTIFIER_TYPE_NCT)) {
                queryCriteria.setNctNumber(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                    Constants.IDENTIFIER_TYPE_OTHER_IDENTIFIER)) {
                queryCriteria.setOtherIdentifier(criteria.getIdentifier());
            }
        }
        if (StringUtils.isNotEmpty(criteria.getOrganizationId())) {
            queryCriteria.setLeadOrganizationId(criteria.getOrganizationId().toString());
        }
        if (criteria.getParticipatingSiteId() != null
                && criteria.getParticipatingSiteId().trim().length() > 0) {
            queryCriteria.setParticipatingSiteId(criteria.getParticipatingSiteId().toString());
        }
        queryCriteria.setOrganizationType(criteria.getOrganizationType());
        if (criteria.isMyTrialsOnly()) {
            queryCriteria.setMyTrialsOnly(Boolean.TRUE);
        } else {
            queryCriteria.setMyTrialsOnly(Boolean.FALSE);
        }
        queryCriteria.setUserLastCreated(ServletActionContext.getRequest().getRemoteUser());
        // exclude rejected protocols during search
        queryCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        if (StringUtils.isNotEmpty(criteria.getPrincipalInvestigatorId())) {
            queryCriteria.setPrincipalInvestigatorId(criteria.getPrincipalInvestigatorId());
        }
        String loginName =  ServletActionContext.getRequest().getRemoteUser();
        RegistryUser loggedInUser = PaRegistry.getRegistryUserService().getUser(loginName);
        queryCriteria.setUserId(loggedInUser.getId());

        return queryCriteria;
    }

    /**
     *
     * @return records
     */
    public List<StudyProtocolQueryDTO> getRecords() {
        return records;
    }

    /**
     *
     * @return SearchProtocolCriteria SearchProtocolCriteria
     */
    public SearchProtocolCriteria getCriteria() {
        return criteria;
    }

    /**
     *
     * @param criteria SearchProtocolCriteria
     */
    public void setCriteria(SearchProtocolCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     *
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     *
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    private void loadPropTrial(Ii studyProtocolIi) throws PAException, NullifiedRoleException {
        ProprietaryTrialDTO trialDTO = new ProprietaryTrialDTO();
        trialUtil.getProprietaryTrialDTOFromDb(studyProtocolIi, trialDTO);
        ServletActionContext.getRequest().setAttribute("leadOrganizationName",
                trialDTO.getLeadOrganizationName());
        ServletActionContext.getRequest().setAttribute("leadOrgTrialIdentifier",
                trialDTO.getLeadOrgTrialIdentifier());
        ServletActionContext.getRequest().setAttribute("nctIdentifier", trialDTO.getNctIdentifier());
        ServletActionContext.getRequest().setAttribute("assignedIdentifier",
                trialDTO.getAssignedIdentifier());
        ServletActionContext.getRequest().setAttribute("summaryFourOrgName",
                trialDTO.getSummaryFourOrgName());
        ServletActionContext.getRequest().setAttribute("summaryFourFundingCategoryCode",
                trialDTO.getSummaryFourFundingCategoryCode());
        ServletActionContext.getRequest().setAttribute("participatingSitesList",
                trialDTO.getParticipatingSitesList());
    }

    private void loadNonPropTrial(Ii studyProtocolIi, boolean maskFields) throws PAException, NullifiedRoleException {
        TrialDTO trialDTO = new TrialDTO();
        trialUtil.getTrialDTOFromDb(studyProtocolIi, trialDTO);
        if (trialDTO.getTrialType().equals("InterventionalStudyProtocol")) {
           trialDTO.setTrialType("Interventional");
        } else if (trialDTO.getTrialType().equals("ObservationalStudyProtocol")) {
           trialDTO.setTrialType("Observational");
        }
        ServletActionContext.getRequest().setAttribute("trialDTO", trialDTO);
        getReponsibleParty(trialDTO, maskFields);
        if (!maskFields && !(trialDTO.getFundingDtos().isEmpty())) {
            // put an entry in the session and store TrialFunding
             ServletActionContext.getRequest().setAttribute(Constants.TRIAL_FUNDING_LIST,
                      trialDTO.getFundingDtos());
        }
        if (!maskFields && !(trialDTO.getIndIdeDtos().isEmpty())) {
            // put an entry in the session and store TrialFunding
            ServletActionContext.getRequest().setAttribute(Constants.STUDY_INDIDE, trialDTO.getIndIdeDtos());
        }
    }

    private StudyProtocolDTO loadTrial(Ii studyProtocolIi, boolean maskFields)
        throws PAException, NullifiedRoleException {
        ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
        StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (!PAUtil.isBlNull(protocolDTO.getProprietaryTrialIndicator())
                && BlConverter.convertToBoolean(protocolDTO.getProprietaryTrialIndicator())) {
            // prop trial
            loadPropTrial(studyProtocolIi);
        } else {
            // non prop trial
            loadNonPropTrial(studyProtocolIi, maskFields);
        }
        return protocolDTO;
    }

    /**
     * @return res
     */
    public String view() {
        boolean maskFields = false;
        try {
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            if (usercreated != null && !usercreated.equals(ServletActionContext.getRequest().getRemoteUser())) {
                maskFields = true;
            }
            StudyProtocolDTO protocolDTO = loadTrial(studyProtocolIi, maskFields);
            queryTrialDocsAndSetAttributes(studyProtocolIi, protocolDTO, maskFields);
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        } catch (NullifiedRoleException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    private void queryTrialDocsAndSetAttributes(Ii studyProtocolIi,
            StudyProtocolDTO protocolDTO, boolean maskFields) throws PAException {
        ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
        // query the trial documents
        List<DocumentDTO> documentISOList = PaRegistry.getDocumentService()
                .getDocumentsByStudyProtocol(studyProtocolIi);
        if (!maskFields && !(documentISOList.isEmpty())) {
            ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
        }
    }

    /**
     * @return result
     */
    public String viewDoc() {
        try {
            Ii studyProtocolIi =
                (Ii) ServletActionContext.getRequest().getSession().getAttribute("spidfromviewresults");
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            String nciId = PAUtil.getAssignedIdentifierExtension(spDTO);
            DocumentDTO docDTO = PaRegistry.getDocumentService().get(IiConverter.convertToIi(identifier));
            String documentPath = PAUtil.getDocumentFilePath(identifier,
                    StConverter.convertToString(docDTO.getFileName()), nciId);

            File downloadFile = new File(documentPath);
            FileInputStream inputStream = new FileInputStream(downloadFile);

            HttpServletResponse servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/x-unknown");
            servletResponse.setHeader("Cache-Control", "cache");
            servletResponse.setHeader("Pragma", "cache");
            servletResponse.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
            servletResponse.setContentLength(inputStream.available());

            ServletOutputStream out = servletResponse.getOutputStream();
            IOUtils.copy(inputStream, out);
            out.flush();
            out.close();
        } catch (IOException err) {
            LOG.error("TrialDocumentAction failed with IOException: " + err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            query();
            return ERROR;
        } catch (Exception e) {
            LOG.error("Exception occured while retrieving document " + e);
        }
        return NONE;
    }

    /**
     * validate the search trial form elements.
     */
    private void validateForm() {
        validateEmptyIdentifierType();
        validateEmptyIdentifier();
        validateOrganizationType();
    }

    private void validateEmptyIdentifierType() {
        if (StringUtils.isNotEmpty(criteria.getIdentifierType()) && StringUtils.isEmpty(criteria.getIdentifier())) {
            addFieldError("criteria.identifier", getText("error.search.identifier"));
        }
    }

    private void validateEmptyIdentifier() {
        if (StringUtils.isNotEmpty(criteria.getIdentifier()) && StringUtils.isEmpty(criteria.getIdentifierType())) {
            addFieldError("criteria.identifierType", getText("error.search.identifierType"));
        }
    }

    private void validateOrganizationType() {
        if (StringUtils.isNotEmpty(criteria.getOrganizationType()) && (criteria.getOrganizationId() == null
                && criteria.getParticipatingSiteId() == null)) {
            addFieldError("criteria.organizationId", getText("error.search.organization"));

        }
    }

    private void getReponsibleParty(TrialDTO trialDTO, boolean maskFields) throws PAException, NullifiedRoleException {
        if (!maskFields) {
            ServletActionContext.getRequest().setAttribute(Constants.RESP_PARTY, trialDTO.getResponsiblePartyType());
            if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("Sponsor")) {
                if (StringUtils.isNotEmpty(trialDTO.getResponsiblePersonName())) {
                    ServletActionContext.getRequest().setAttribute(Constants.RESP_PARTY_CONTACT,
                            trialDTO.getResponsiblePersonName());
                }
                if (StringUtils.isNotEmpty(trialDTO.getResponsibleGenericContactName())) {
                    ServletActionContext.getRequest().setAttribute(Constants.RESP_PARTY_CONTACT,
                            trialDTO.getResponsibleGenericContactName());
                }
            }
            ServletActionContext.getRequest().setAttribute(Constants.SPONSOR, trialDTO.getSponsorName());
            ServletActionContext.getRequest().setAttribute(Constants.RESP_PARTY_PHONE, trialDTO.getContactPhone());
            ServletActionContext.getRequest().setAttribute(Constants.RESP_PARTY_EMAIL, trialDTO.getContactEmail());
        }
    }

    /**
     *
     * @return st
     */
    public String getMyPartiallySavedTrial() {
        StudyProtocolStageDTO criteriaSpDTO = new StudyProtocolStageDTO();
        criteriaSpDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser()));
        criteriaSpDTO.setOfficialTitle(StConverter.convertToSt(criteria.getOfficialTitle()));
        criteriaSpDTO.setPhaseCode(CdConverter.convertStringToCd(criteria.getPhaseCode()));
        criteriaSpDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(criteria.getPrimaryPurposeCode()));
        criteriaSpDTO.setPhaseAdditionalQualifierCode(CdConverter.convertStringToCd(
                criteria.getPhaseAdditionalQualifierCode()));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
        try {
            List<StudyProtocolStageDTO> spStageDTOs =  PaRegistry.getStudyProtocolStageService().search(criteriaSpDTO,
                    limit);
            records = convertToSpQueryDTO(spStageDTOs);
        } catch (PAException e) {
            LOG.equals(e.getMessage());
            addActionError("Exception :" + e.getMessage());
        } catch (TooManyResultsException e) {
            LOG.equals("Exception :" + e.getMessage());
        }
        ServletActionContext.getRequest().setAttribute("partialSubmission", "yes");
        return SUCCESS;
    }
    /**
     *
     * @return view
     */
    public String partiallySubmittedView() {
        if (studyProtocolId == null) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        BaseTrialDTO trialDTO = new BaseTrialDTO();
        try {
            trialDTO =  trialUtil.getTrialDTOForPartiallySumbissionById(studyProtocolId.toString());
            if (trialDTO instanceof TrialDTO) {
                if (StringUtils.isNotEmpty(((TrialDTO) trialDTO).getSelectedRegAuth())) {
                    String orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(Long.valueOf(
                            ((TrialDTO) trialDTO).getSelectedRegAuth()), "RegulatoryAuthority");
                    ((TrialDTO) trialDTO).setTrialOversgtAuthOrgName(orgName);
                }
                if (StringUtils.isNotEmpty(((TrialDTO) trialDTO).getLst())) {
                    String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                            Long.valueOf(((TrialDTO) trialDTO).getLst()), "Country");
                    ((TrialDTO) trialDTO).setTrialOversgtAuthCountryName(countryName);
                }
            }
            ServletActionContext.getRequest().setAttribute("trialDTO", trialDTO);
            ServletActionContext.getRequest().setAttribute("partialSubmission", "search");
        } catch (PAException e) {
            addActionError(e.getMessage());
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
     return "partialView";
    }
    private List<StudyProtocolQueryDTO> convertToSpQueryDTO(List<StudyProtocolStageDTO> spStageDTOs) {
        StudyProtocolQueryDTO spQueryDTO;
        List<StudyProtocolQueryDTO> returnList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolStageDTO studyProtocolStageDTO : spStageDTOs) {
            spQueryDTO = new StudyProtocolQueryDTO();
            spQueryDTO.setStudyProtocolId(IiConverter.convertToLong(studyProtocolStageDTO.getIdentifier()));
            spQueryDTO.setOfficialTitle(StConverter.convertToString(studyProtocolStageDTO.getOfficialTitle()));
            spQueryDTO.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getPhaseCode())));
            spQueryDTO.setPrimaryPurpose(CdConverter.convertCdToString(
                studyProtocolStageDTO.getPrimaryPurposeCode()));
            spQueryDTO.setPrimaryPurposeOtherText(CdConverter.convertCdToString(
                studyProtocolStageDTO.getPrimaryPurposeAdditionalQualifierCode()));
            spQueryDTO.setLocalStudyProtocolIdentifier(StConverter.convertToString(
                studyProtocolStageDTO.getLocalProtocolIdentifier()));

            spQueryDTO.setLeadOrganizationId(IiConverter.convertToLong(
                studyProtocolStageDTO.getLeadOrganizationIdentifier()));
            if (PAUtil.isIiNotNull(studyProtocolStageDTO.getLeadOrganizationIdentifier())) {
                spQueryDTO.setLeadOrganizationName(trialUtil.getOrgName(
                      studyProtocolStageDTO.getLeadOrganizationIdentifier()));
            }
            spQueryDTO.setPiId(IiConverter.convertToLong(studyProtocolStageDTO.getPiIdentifier()));
            if (PAUtil.isIiNotNull(studyProtocolStageDTO.getPiIdentifier())) {
                spQueryDTO.setPiFullName(trialUtil.getPersonName(studyProtocolStageDTO.getPiIdentifier()));
            }
            spQueryDTO.setStudyStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(
                studyProtocolStageDTO.getTrialStatusCode())));
            spQueryDTO.setStudyStatusDate(TsConverter.convertToTimestamp(studyProtocolStageDTO.getTrialStatusDate()));
            spQueryDTO.setUserLastCreated(StConverter.convertToString(studyProtocolStageDTO.getUserLastCreated()));
            if (!PAUtil.isBlNull(studyProtocolStageDTO.getProprietaryTrialIndicator())
                    && BlConverter.convertToBoolean(studyProtocolStageDTO.getProprietaryTrialIndicator())) {
                spQueryDTO.setProprietaryTrial(true);
            } else {
                spQueryDTO.setProprietaryTrial(false);
            }
            returnList.add(spQueryDTO);
        }
        return returnList;
    }

    /**
     * Send xml.
     *
     * @return the string
     */
    public String sendXml() {
        Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        RegistryUserWebDTO regUserWebDto = RegistryUtil.getRegistryUserWebDto(loginName);
        String fullName = regUserWebDto.getFirstName() + " " + regUserWebDto.getLastName();
        String emailAddress = regUserWebDto.getEmailAddress();
        try {
             List<AbstractionCompletionDTO> errorList = PaRegistry.getAbstractionCompletionService()
                     .validateAbstractionCompletion(studyProtocolIi);
             if (CollectionUtils.isEmpty(errorList) || !hasAnyAbstractionErrors(errorList)) {
                PaRegistry.getMailManagerService().sendXMLAndTSREmail(fullName, emailAddress, studyProtocolIi);
             } else {
                  ServletActionContext.getRequest().setAttribute("failureMessage" ,
                         "As Abstraction is not valid, sending letter is disabled .");
             }
        } catch (PAException e) {
             addActionError("Exception while sending XML email:" + e.getMessage());
        }
        return query();
    }
    private boolean hasAnyAbstractionErrors(List<AbstractionCompletionDTO> errorList) {
        boolean errorExist = false;
         for (AbstractionCompletionDTO  absDto : errorList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                errorExist = true;
                break;
            }
         }
        return errorExist;
     }

}
