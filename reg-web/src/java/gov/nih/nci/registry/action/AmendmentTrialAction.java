/***
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate, execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions, You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice, this list of  conditions  and the  disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs, You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author Vrushali
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class AmendmentTrialAction extends ManageFileAction implements ServletResponseAware {
    private static final long serialVersionUID = 1L;
    private HttpServletResponse servletResponse;
    private static final Logger LOG = Logger.getLogger(AmendmentTrialAction.class);
    private TrialDTO trialDTO;
    private String trialAction = null;
    private String studyProtocolId = null;
    private static String sessionTrialDTO = "trialDTO";
    private final TrialUtil trialUtil = new TrialUtil();

    /**
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     *
     * @return res
     */
    public String view() {
        // clear the session
        TrialValidator.removeSessionAttributes();
        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            if (studyProtocolId == null) {
                studyProtocolId = pId;
            }
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            TrialUtil util = new TrialUtil();
            trialDTO = new TrialDTO();
            util.getTrialDTOFromDb(studyProtocolIi, trialDTO);
            TrialValidator.addSessionAttributes(trialDTO);
            ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
            setPageFrom("amendTrial");
            LOG.info("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * @return the trialDTO
     */
    public TrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(TrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

    /**
     * Clears the session variables and redirect to search.
     *
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        return "redirect_to_search";
    }

    /**
     *
     * @return s
     */
    @SuppressWarnings("unchecked")
    public String review() {
        try {
            clearErrorsAndMessages();
            enforceBusinessRules();
            List<TrialDocumentWebDTO> docDTOList = addDocDTOToList();
            String errorReturn = handleErrors();
            if (errorReturn != null) {
                return errorReturn;
            }
            populateList(docDTOList);
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            trialDTO.setDocDtos(docDTOList);
            // get the document and put in list add the IndIde,FundingList
            populateIndIdes();
            populateGrantList();
            if (trialDTO.getXmlRequired()) {
                trialUtil.setOversgtInfo(trialDTO);
            }
            List<Ii> otherIdsList =
                    (List<Ii>) ServletActionContext.getRequest().getSession().getAttribute(
                            Constants.SECONDARY_IDENTIFIERS_LIST);
            if (otherIdsList != null) {
                trialDTO.setSecondaryIdentifierAddList(otherIdsList);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute(sessionTrialDTO, trialDTO);
        LOG.info("Calling the review page...");
        return "review";
    }

    @SuppressWarnings("unchecked")
    private void populateGrantList() {
        List<TrialFundingWebDTO> grantList =
                (List<TrialFundingWebDTO>) ServletActionContext.getRequest().getSession().getAttribute(
                        Constants.GRANT_LIST);
        if (grantList != null) {
            trialDTO.setFundingDtos(grantList);
        }
    }

    @SuppressWarnings("unchecked")
    private void populateIndIdes() {
        List<TrialIndIdeDTO> indList =
                (List<TrialIndIdeDTO>) ServletActionContext.getRequest().getSession().getAttribute(
                        Constants.INDIDE_LIST);
        if (indList != null) {
            trialDTO.setIndIdeDtos(indList);
        }
    }

    private String handleErrors() {
        if (hasFieldErrors()) {
            ServletActionContext.getRequest().setAttribute("failureMessage",
                    "The form has errors and could not be submitted, please check the fields highlighted below");
            TrialValidator.addSessionAttributes(trialDTO);
            trialUtil.populateRegulatoryList(trialDTO);
            return ERROR;
        }
        if (hasActionErrors()) {
            TrialValidator.addSessionAttributes(trialDTO);
            trialUtil.populateRegulatoryList(trialDTO);
            return ERROR;
        }
        return null;
    }

    /**
     *
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        trialUtil.populateRegulatoryList(trialDTO);
        TrialValidator.addSessionAttributes(trialDTO);
        setDocumentsInSession(trialDTO);
        return "edit";
    }

    /**
     *
     * @return s
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public String amend() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        if (trialDTO == null) {
            return ERROR;
        }
        TrialUtil util = new TrialUtil();
        Ii amendId = null;
        try {
            if (!trialDTO.getXmlRequired()) {
                trialDTO.setFdaRegulatoryInformationIndicator(null);
                trialDTO.setDataMonitoringCommitteeAppointedIndicator(null);
                trialDTO.setDelayedPostingIndicator(null);
                trialDTO.setSection801Indicator(null);
            }
            StudyProtocolDTO studyProtocolDTO = util.convertToStudyProtocolDTOForAmendment(trialDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext.getRequest()
                    .getRemoteUser()));
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            // updated only if the ctGovXmlRequired is true
            OrganizationDTO sponsorOrgDTO = null;
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            }
            StudySiteDTO leadOrgSiteIdDTO = util.convertToleadOrgSiteIdDTO(trialDTO);

            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studySiteContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO, null);
            Ii responsiblePartyContactIi = null;
            // updated only if the ctGovXmlRequired is true
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                    studyContactDTO = util.convertToStudyContactDTO(trialDTO);
                } else {
                    studySiteContactDTO = util.convertToStudySiteContactDTO(trialDTO);
                    if (StringUtils.isNotBlank(trialDTO.getResponsiblePersonName())) {
                        responsiblePartyContactIi =
                                IiConverter.convertToPoPersonIi(trialDTO.getResponsiblePersonIdentifier());
                    }
                    if (StringUtils.isNotBlank(trialDTO.getResponsibleGenericContactName())) {
                        responsiblePartyContactIi =
                                IiConverter.convertToPoOrganizationalContactIi(trialDTO
                                        .getResponsiblePersonIdentifier());
                    }
                }
            }
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, null));
            studyIdentifierDTOs.add(util.convertToCTEPStudySiteDTO(trialDTO, null));
            studyIdentifierDTOs.add(util.convertToDCPStudySiteDTO(trialDTO, null));
            // updated only if the ctGovXmlRequired is true
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = null;
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                studyRegAuthDTO = util.getStudyRegAuth(null, trialDTO);
            }
            amendId =
                    PaRegistry.getTrialRegistrationService().amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                            studyResourcingDTOs, documentDTOs, leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO,
                            leadOrgSiteIdDTO, studyIdentifierDTOs, studyContactDTO, studySiteContactDTO,
                            summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactIi, studyRegAuthDTO,
                            BlConverter.convertToBl(Boolean.FALSE));
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId", amendId.getExtension());
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", amendId);
        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
            TrialValidator.addSessionAttributes(trialDTO);
            ServletActionContext.getRequest().getSession().removeAttribute("secondaryIdentifiersList");
            trialDTO.setSecondaryIdentifierAddList(null);
            trialUtil.removeAssignedIdentifierFromSecondaryIds(trialDTO);
            trialUtil.populateRegulatoryList(trialDTO);
            setDocumentsInSession(trialDTO);
            return ERROR;
        }
        setTrialAction("amend");
        return "redirect_to_search";
    }

    /**
     * validate the submit trial form elements.
     *
     * @throws PAException
     */
    private void enforceBusinessRules() throws PAException {
        if (StringUtils.isBlank(trialDTO.getAmendmentDate())) {
            addFieldError("trialDTO.amendmentDate", getText("error.submit.amendmentDate"));
        }
        if (!RegistryUtil.isValidDate(trialDTO.getAmendmentDate())) {
            addFieldError("trialDTO.amendmentDate", getText("error.submit.invalidDate"));
        } else {
            Timestamp currentTimeStamp = new Timestamp((new Date()).getTime());
            if (currentTimeStamp.before(PAUtil.dateStringToTimestamp(trialDTO.getAmendmentDate()))) {
                addFieldError("trialDTO.amendmentDate", getText("error.submit.invalidAmendDate"));
            }
        }
        TrialValidator validator = new TrialValidator();
        Map<String, String> err = new HashMap<String, String>();
        err = validator.validateTrialDTO(trialDTO);
        addErrors(err);
        // validate trial status and dates specific for amendment
        if (StringUtils.isNotBlank(trialDTO.getStatusCode()) && RegistryUtil.isValidDate(trialDTO.getStatusDate())
                && RegistryUtil.isValidDate(trialDTO.getCompletionDate())
                && RegistryUtil.isValidDate(trialDTO.getStartDate())
                && validator.isTrialStatusOrDateChanged(trialDTO)) {
            Collection<String> errDate = validator.enforceBusinessRulesForDates(trialDTO);
            if (!errDate.isEmpty()) {
                for (String msg : errDate) {
                    addActionError(msg);
                }
            }
        }
        // validate the docs
        validateDocuments();
        // Only allow completing amendment submission of the pre-IRB approved study is the
        // current trial status 'In-Review' is replaced with 'Approved'.
        if (StringUtils.isNotBlank(trialDTO.getStatusCode())
                && trialDTO.getStatusCode().equalsIgnoreCase("In Review")) {
            addActionError("To Amend Submission of pre-IRB approved study replace "
                    + " current trial status 'In-Review' with 'Approved'");
        }
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
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

}
