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
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
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
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Bala Nair
 * 
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.TooManyMethods" })
public class SearchTrialAction extends ActionSupport {
    private List<StudyProtocolQueryDTO> records = null;
    private SearchProtocolCriteria criteria = new SearchProtocolCriteria();
    private Long studyProtocolId = null;
    PAServiceUtils paServiceUtils = new PAServiceUtils();
    TrialUtil trialUtil = new TrialUtil();
    
    /**
     * @return res
     */
    public String execute() {
        //check if users accepted the desclaimer if not show one
        String strDesclaimer = (String) ServletActionContext.getRequest().getSession().getAttribute("disclaimer");
        if (strDesclaimer == null || !strDesclaimer.equals("accept")) {
            return "show_Disclaimer_Page";
        }
        String trialAction = (String) ServletActionContext.getRequest().getParameter("trialAction");
        if (PAUtil.isNotEmpty(trialAction) && (trialAction.equalsIgnoreCase("submit") 
                || trialAction.equalsIgnoreCase("amend") || trialAction.equalsIgnoreCase("update"))) {
            String pId = (String) ServletActionContext.getRequest().getSession().getAttribute("protocolId");
            ServletActionContext.getRequest().setAttribute("studyProtocolId", pId);
            studyProtocolId = Long.valueOf(pId);
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
            List<StudyProtocolQueryDTO> studyProtocolList = new ArrayList<StudyProtocolQueryDTO>();
            studyProtocolList = PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(
                    convertToStudyProtocolQueryCriteria());
            if (studyProtocolList != null) {
                records = new ArrayList<StudyProtocolQueryDTO>();
                // when selected search my trials
                if (StringUtils.isNotEmpty(criteria.getMyTrialsOnly()) && criteria.getMyTrialsOnly().equals("true")) {
                    String loginName =  ServletActionContext.getRequest().getRemoteUser();
                    for (StudyProtocolQueryDTO queryDto : studyProtocolList) {
                        if (PaRegistry.getRegisterUserService().hasTrialAccess(loginName, 
                              queryDto.getStudyProtocolId())) {
                           records.add(queryDto);
                        }
                    }
                } else {
                    // when selected search all trials
                    records = studyProtocolList;
                }
            }
            checkToShowSendXml();
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            ServletActionContext.getRequest().setAttribute(
                    "failureMessage" , e.getMessage());
            return ERROR;
        }
    }

    private void checkToShowSendXml() throws PAException {
        if (records != null && !records.isEmpty()) {
            for (StudyProtocolQueryDTO queryDto : records) {
                if (queryDto.getIsProprietaryTrial().equalsIgnoreCase("false") 
                        && PAUtil.isAbstractedAndAbove(CdConverter.convertStringToCd(
                                queryDto.getDocumentWorkflowStatusCode().getCode()))
                         && queryDto.getCtgovXmlRequiredIndicator()) {               
                    queryDto.setShowSendXml(true);
                }
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
        if (PAUtil.isNotEmpty(criteria.getIdentifierType())
                 && PAUtil.isNotEmpty(criteria.getIdentifier())) {            
            if (criteria.getIdentifierType().equals(Constants.IDENTIFIER_TYPE_NCI)) {
                queryCriteria.setNciIdentifier(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                        Constants.IDENTIFIER_TYPE_LEAD_ORG)) {
                queryCriteria.setLeadOrganizationTrialIdentifier(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                        Constants.IDENTIFIER_TYPE_NCT)) {
                queryCriteria.setNctNumber(criteria.getIdentifier());
            }
        }
        if (criteria.getOrganizationId() != null 
                && criteria.getOrganizationId().trim().length() > 0) {
            queryCriteria.setLeadOrganizationId(criteria.getOrganizationId().toString());
        } 
        if (criteria.getParticipatingSiteId() != null
                && criteria.getParticipatingSiteId().trim().length() > 0) {
            queryCriteria.setParticipatingSiteId(criteria.getParticipatingSiteId().toString());            
        }
        queryCriteria.setOrganizationType(criteria.getOrganizationType());
        if (StringUtils.isNotEmpty(criteria.getMyTrialsOnly()) && criteria.getMyTrialsOnly().equals("true")) {
            queryCriteria.setMyTrialsOnly(Boolean.TRUE);
        } else {
            queryCriteria.setMyTrialsOnly(Boolean.FALSE);
        }
        queryCriteria.setUserLastCreated(ServletActionContext.getRequest().getRemoteUser());
        // exclude rejected protocols during search
        queryCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        if (PAUtil.isNotEmpty(criteria.getPrincipalInvestigatorId())) {
            queryCriteria.setPrincipalInvestigatorId(criteria.getPrincipalInvestigatorId());
        }
        String loginName =  ServletActionContext.getRequest().getRemoteUser();
        RegistryUser loggedInUser = PaRegistry.getRegisterUserService().getUser(loginName);
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

    /**
     * @return res
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    public String view() {
        boolean maskFields = false;
        try {
            // remove the session variables stored during a previous view if any
            
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            if (studyProtocolId == null) {
                String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
                studyProtocolIi = IiConverter.convertToIi(pId);
            }
            String usercreated = (String) ServletActionContext.getRequest().getParameter("usercreated");
            if (usercreated != null && !usercreated.equals(ServletActionContext.getRequest().getRemoteUser())) {
                    maskFields = true;
            }
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
            StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);
            if (!PAUtil.isBlNull(protocolDTO.getProprietaryTrialIndicator()) 
                  && BlConverter.covertToBoolean(protocolDTO.getProprietaryTrialIndicator())) {
              // prop trial
              String strNctNo = paServiceUtils.getStudyIdentifier(
                      studyProtocolIi, PAConstants.NCT_IDENTIFIER_TYPE);
              CorrelationUtils cUtils = new CorrelationUtils();
              StudyProtocolQueryDTO spqDto = PaRegistry.getProtocolQueryService()
                   .getTrialSummaryByStudyProtocolId(Long.valueOf(studyProtocolIi.getExtension()));
              Organization org = cUtils.getPAOrganizationByIi(IiConverter.convertToPaOrganizationIi(
                      spqDto.getLeadOrganizationId()));
              ServletActionContext.getRequest().setAttribute("leadOrganizationName", org.getName());
              ServletActionContext.getRequest().setAttribute("leadOrgTrialIdentifier",
                    spqDto.getLocalStudyProtocolIdentifier());
              ServletActionContext.getRequest().setAttribute("nctIdentifier", strNctNo);
            } else {
               // non prop trial
                TrialDTO trialDTO = new TrialDTO();
                trialUtil.getTrialDTOFromDb(studyProtocolIi, (TrialDTO) trialDTO);
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
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
            // query the trial documents
            List<DocumentDTO> documentISOList = PaRegistry.getDocumentService()
                    .getDocumentsByStudyProtocol(studyProtocolIi);
            // List <TrialDocumentWebDTO> documentList;
            if (!maskFields && !(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
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
    
    /**
     * @return result
     */
    public String viewDoc() {
        LOG.info("Entering viewProtocolDoc");
        try {
            String docId = ServletActionContext.getRequest().getParameter("identifier");
            //spidfromviewresults
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    "spidfromviewresults");
            //Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            DocumentDTO docDTO = PaRegistry.getDocumentService().get(IiConverter.convertToIi(docId));
            // InterventionalStudyProtocolWebDTO spDTO =
            // (InterventionalStudyProtocolWebDTO) ServletActionContext
            // .getRequest().getSession().getAttribute(Constants.PROTOCOL_DOCUMENT);
            StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
            sb.append(File.separator).append(PAUtil.getAssignedIdentifier(spDTO)).append(File.separator)
            .append(
                    docDTO.getIdentifier().getExtension()).append('-').append(docDTO.getFileName().getValue());
            File downloadFile = new File(sb.toString());
            HttpServletResponse servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/x-unknown");
            FileInputStream fileToDownload = new FileInputStream(downloadFile);
            servletResponse.setHeader("Cache-Control", "cache"); 
            servletResponse.setHeader("Pragma", "cache");
            servletResponse.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
            servletResponse.setContentLength(fileToDownload.available());
            int data;
            ServletOutputStream out = servletResponse.getOutputStream();
            while ((data = fileToDownload.read()) != -1) {
                out.write(data);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException err) {
            LOG.error("TrialDocumentAction failed with FileNotFoundException: " + err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            query();
            return ERROR;
        } catch (Exception e) {
            // e.printStackTrace();
            LOG.error("Exception occured while retrieving document " + e);
        }
        return NONE;
    }
    
    /**
     * validate the search trial form elements.
     */
    private void validateForm() {
        if (PAUtil.isNotEmpty(criteria.getIdentifierType()) 
                 && PAUtil.isEmpty(criteria.getIdentifier())) {
            addFieldError("criteria.identifier",
                    getText("error.search.identifier"));
        }
        if (PAUtil.isNotEmpty(criteria.getIdentifier()) 
                && PAUtil.isEmpty(criteria.getIdentifierType())) {
           addFieldError("criteria.identifierType",
                   getText("error.search.identifierType"));
       }
       if (PAUtil.isNotEmpty(criteria.getOrganizationType())
               && (criteria.getOrganizationId() == null 
                       && criteria.getParticipatingSiteId() == null)) {
           addFieldError("criteria.organizationId",
                   getText("error.search.organization"));

       }

    }
    
    private void getReponsibleParty(
                TrialDTO trialDTO, boolean maskFields) throws PAException, NullifiedRoleException {
            if (!maskFields) {
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY, trialDTO.getResponsiblePartyType());
                if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("Sponsor")) {
                    if (StringUtils.isNotEmpty(trialDTO.getResponsiblePersonName())) {
                    ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_CONTACT, trialDTO.getResponsiblePersonName());
                    }
                    if (StringUtils.isNotEmpty(trialDTO.getResponsibleGenericContactName())) {
                        ServletActionContext.getRequest().setAttribute(
                                    Constants.RESP_PARTY_CONTACT, trialDTO.getResponsibleGenericContactName());
                    }
                }
                ServletActionContext.getRequest().setAttribute(
                                Constants.SPONSOR, trialDTO.getSponsorName());
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_PHONE, trialDTO.getContactPhone()); 
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_EMAIL, trialDTO.getContactEmail()); 
            }
    }
    /**
     * 
     * @return st
     */
    public String getMyPartiallySavedTrial() {
        StudyProtocolStageDTO criteriaSpDTO = new StudyProtocolStageDTO();
        criteriaSpDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext
                .getRequest().getRemoteUser()));
        criteriaSpDTO.setOfficialTitle(StConverter.convertToSt(criteria.getOfficialTitle()));
        criteriaSpDTO.setPhaseCode(CdConverter.convertStringToCd(criteria.getPhaseCode()));
        criteriaSpDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(criteria.getPrimaryPurposeCode()));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
        try {
            List<StudyProtocolStageDTO> spStageDTOs =  PaRegistry.getStudyProtocolStageService()
                .search(criteriaSpDTO, limit);
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
        String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (PAUtil.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        BaseTrialDTO trialDTO = new BaseTrialDTO();
        try {
            trialDTO =  trialUtil.getTrialDTOForPartiallySumbissionById(pId);
            if (trialDTO instanceof TrialDTO) {
                if (PAUtil.isNotEmpty(((TrialDTO) trialDTO).getSelectedRegAuth())) {
                    String orgName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(Long.valueOf(
                            ((TrialDTO) trialDTO).getSelectedRegAuth()), "RegulatoryAuthority");
                    ((TrialDTO) trialDTO).setTrialOversgtAuthOrgName(orgName);
                }
                if (PAUtil.isNotEmpty(((TrialDTO) trialDTO).getLst())) {
                    String countryName = PaRegistry.getRegulatoryInformationService().getCountryOrOrgName(
                            Long.valueOf(((TrialDTO) trialDTO).getLst()), "Country");
                    ((TrialDTO) trialDTO).setTrialOversgtAuthCountryName(countryName);
                }
            }
            ServletActionContext.getRequest().setAttribute("trialDTO", trialDTO);
            ServletActionContext.getRequest().setAttribute("partialSubmission", "search");
            ServletActionContext.getRequest().setAttribute("protocolId", pId);
            
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
            spQueryDTO.setPrimaryPurposeOtherText(StConverter.convertToString(
                studyProtocolStageDTO.getPrimaryPurposeOtherText()));
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
                    && BlConverter.covertToBoolean(studyProtocolStageDTO.getProprietaryTrialIndicator())) {
                spQueryDTO.setIsProprietaryTrial("true");
            } else {
                spQueryDTO.setIsProprietaryTrial("");
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
        try {
            PaRegistry.getMailManagerService().sendXMLAndTSREmail(studyProtocolIi);
        } catch (PAException e) {
            addActionError("Exception while sending XML email:" + e.getMessage());
        }        
        return query();
    }
}
