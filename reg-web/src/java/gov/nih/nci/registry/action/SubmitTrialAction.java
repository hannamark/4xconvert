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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.RegulatoryAuthOrgDTO;
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
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author Bala Nair
 * @author Harsha
 *
 */
@SuppressWarnings("unchecked")
public class SubmitTrialAction extends ManageFileAction implements ServletResponseAware, Preparable {
    private static final long serialVersionUID = -7644860242308952142L;
    private static final String REDIRECT_TO_SEARCH = "redirect_to_search";
    private static final Logger LOG = Logger.getLogger(SubmitTrialAction.class);
    private Long cbValue;
    private String page;
    private Long id = null;
    private HttpServletResponse servletResponse;
    private String trialAction = "submit";
    private static String sessionTrialDTO = "trialDTO";
    private TrialDTO trialDTO;
    private final TrialUtil  trialUtil = new TrialUtil();
    private String sum4FundingCatCode;

    /**
     *
     * @return res
     */
    @Override
    public String execute() {
        if (StringUtils.isEmpty(sum4FundingCatCode)) {
            setTrialAction("");
            ServletActionContext.getRequest().setAttribute(
                    "failureMessage" , "Summary 4 Funding Sponsor Type is required to continue onto registration.");
            return REDIRECT_TO_SEARCH;
        }
        trialDTO = new TrialDTO();
        trialDTO.setResponsiblePartyType("PI");
        trialDTO.setTrialType("Interventional");
        trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
        TrialValidator.removeSessionAttributes();
        trialUtil.populateRegulatoryList(trialDTO);
        trialDTO.setSummaryFourFundingCategoryCode(sum4FundingCatCode);
        setPageFrom("submitTrial");
        return SUCCESS;
    }


    /**
     * create protocol.
     *
     * @return String
     */
    public String create() {
        try {
            trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
            if (trialDTO == null) {
               return ERROR;
            }
            TrialUtil util = new TrialUtil();
            StudyProtocolDTO studyProtocolDTO = null;
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.YES);
            if (trialDTO.getTrialType().equals("Interventional")) {
                studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            } else {
                studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            }
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(UsernameHolder.getUser()));
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            for (DocumentDTO dto : documentDTOs) {
               dto.setIdentifier(null);
            }
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            OrganizationDTO sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            StudySiteDTO leadOrgSiteIdDTO = util.convertToleadOrgSiteIdDTO(trialDTO);

            List<StudySiteDTO> studyIdentifierDTOs = new ArrayList<StudySiteDTO>();
            studyIdentifierDTOs.add(util.convertToNCTStudySiteDTO(trialDTO, null));

            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studySiteContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO, null);
            Ii responsiblePartyContactIi = null;
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("PI")) {
                    studyContactDTO = util.convertToStudyContactDTO(trialDTO);
                } else {
                   studySiteContactDTO = util.convertToStudySiteContactDTO(trialDTO);
                   responsiblePartyContactIi = getResponsiblePartyContactIi();
               }
            }
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos(), null);
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = util.getStudyRegAuth(null, trialDTO);

            Ii studyProtocolIi =  PaRegistry.getTrialRegistrationService().
                createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs,
                    leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO, leadOrgSiteIdDTO,
                    studyIdentifierDTOs, studyContactDTO, studySiteContactDTO,
                    summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactIi, studyRegAuthDTO,
                    BlConverter.convertToBl(Boolean.FALSE));
             TrialValidator.removeSessionAttributes();
             ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
             ServletActionContext.getRequest().getSession().setAttribute("protocolId", studyProtocolIi.getExtension());
             if (StringUtils.isNotEmpty(trialDTO.getStudyProtocolId())) {
                PaRegistry.getStudyProtocolStageService().delete(IiConverter.convertToIi(
                        trialDTO.getStudyProtocolId()));
             }
        } catch (Exception e) {
            TrialValidator.addSessionAttributes(trialDTO);
            if (!RegistryUtil.setFailureMessage(e)) {
                addActionError("Error occured, please try again");
            }
            LOG.error("Exception occured while submitting trial", e);
            trialUtil.populateRegulatoryList(trialDTO);
            setDocumentsInSession(trialDTO);
            return ERROR;
        }
        return REDIRECT_TO_SEARCH;
    }


    /**
     * @param responsiblePartyContactIi
     * @return
     */
    private Ii getResponsiblePartyContactIi() {
        Ii responsiblePartyContactIi = null;
        if (StringUtils.isNotEmpty(trialDTO.getResponsiblePersonName())) {
            responsiblePartyContactIi = IiConverter.convertToPoPersonIi(trialDTO.getResponsiblePersonIdentifier());
        }
        if (StringUtils.isNotEmpty(trialDTO.getResponsibleGenericContactName())) {
             responsiblePartyContactIi = IiConverter.convertToPoOrganizationalContactIi(
                     trialDTO.getResponsiblePersonIdentifier());
        }
        return responsiblePartyContactIi;
    }

    /**
     * validate the submit trial form elements.
     * @throws IOException on document errors
     */
    private void validateForm() throws IOException {
        TrialValidator validator = new TrialValidator();
        Map<String, String> err = new HashMap<String, String>();
        err = validator.validateTrialDTO(trialDTO);
        addErrors(err);
        validateDocuments();
    }

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
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
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
     *
     * @return s
     */
    public String review() {
        try {
            clearErrorsAndMessages();
            validateForm();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute("failureMessage",
                                                               "The form has errors and could not be submitted, "
                                                                       + "please check the fields highlighted below");
                TrialValidator.addSessionAttributes(trialDTO);
                trialUtil.populateRegulatoryList(trialDTO);
                return ERROR;
            }
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            trialDTO.setDocDtos(getTrialDocuments());
            // add the IndIde,FundingList
            List<TrialIndIdeDTO> indList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
            if (indList != null) {
                trialDTO.setIndIdeDtos(indList);
            }

            List<TrialFundingWebDTO> grantList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
                .getSession().getAttribute(Constants.GRANT_LIST);
            if (grantList != null) {
                trialDTO.setFundingDtos(grantList);
            }

            List<Ii> otherIdsList = (List<Ii>) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
            if (otherIdsList != null) {
                trialDTO.setSecondaryIdentifierList(otherIdsList);
            }

            trialUtil.setOversgtInfo(trialDTO);

        } catch (IOException e) {
            LOG.error(e);
            return ERROR;
        } catch (PAException e) {
            LOG.error(e);
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        }
        TrialValidator.removeSessionAttributes();
        ServletActionContext.getRequest().getSession().setAttribute("trialDTO", trialDTO);
        return "review";
    }

    /**
     *
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        setTrialAction("");
        return REDIRECT_TO_SEARCH;
    }
    /**
     *
     * @return s
     */
    public String edit() {
        trialDTO = (TrialDTO) ServletActionContext.getRequest().getSession().getAttribute(sessionTrialDTO);
        TrialValidator.addSessionAttributes(trialDTO);
        trialUtil.populateRegulatoryList(trialDTO);
        setDocumentsInSession(trialDTO);
        return "edit";
    }
    /**
     * Gets the reg authorities list.
     *
     * @return String success or failure
     */
    public String getTrialOversightAuthorityOrganizationNameList() {
        try {
            String countryId = ServletActionContext.getRequest().getParameter("countryid");
            if (trialDTO == null) {
                trialDTO = new TrialDTO();
            }
            if (countryId != null && !("".equals(countryId))) {
                trialDTO.setRegIdAuthOrgList(PaRegistry.getRegulatoryInformationService()
                                    .getRegulatoryAuthorityNameId(Long.valueOf(countryId)));
            } else {
                RegulatoryAuthOrgDTO defaultVal = new RegulatoryAuthOrgDTO();
                defaultVal.setName("-Select Country-");
                List<RegulatoryAuthOrgDTO> regIdAuthOrgList = new ArrayList<RegulatoryAuthOrgDTO>();
                regIdAuthOrgList.add(defaultVal);
                trialDTO.setRegIdAuthOrgList(regIdAuthOrgList);
            }

        } catch (PAException e) {
            return SUCCESS;
        }
        return SUCCESS;
    }
    /**
     *
     * @return success or fail
     */
    public String partialSave() {
        try {
            List<Ii> otherIdsList = (List<Ii>) ServletActionContext.getRequest()
            .getSession().getAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
            if (otherIdsList != null) {
                trialDTO.setSecondaryIdentifierList(otherIdsList);
            }
            validateDocuments();
            trialDTO.setDocDtos(getTrialDocuments());
            trialDTO = (TrialDTO) trialUtil.saveDraft(trialDTO);
            ServletActionContext.getRequest().setAttribute("protocolId", trialDTO.getStudyProtocolId());
            ServletActionContext.getRequest().setAttribute("partialSubmission", "submit");
            ServletActionContext.getRequest().setAttribute("trialDTO", trialDTO);
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.SECONDARY_IDENTIFIERS_LIST);
        } catch (PAException e) {
            LOG.error(e.getMessage());
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        } catch (IOException e) {
            LOG.error(e.getMessage());
            addActionError(e.getMessage());
            return ERROR;
        }
        return "review";
    }
    /**
     *
     * @return s
     */
    public String completePartialSubmission() {
        TrialValidator.removeSessionAttributes();
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (StringUtils.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        trialDTO = new TrialDTO();
        try {
            trialDTO =  (TrialDTO) trialUtil.getTrialDTOForPartiallySumbissionById(pId);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST,
                    trialDTO.getIndIdeDtos());
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST,
                    trialDTO.getFundingDtos());
            ServletActionContext.getRequest().getSession().setAttribute(Constants.SECONDARY_IDENTIFIERS_LIST,
                    trialDTO.getSecondaryIdentifierList());
            setPageFrom("submitTrial");
            setDocumentsInSession(trialDTO);
        } catch (PAException e) {
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
        //trialUtil.populateRegulatoryList(trialDTO);
       return SUCCESS;
    }
    /**
     *
     * @return string
     */
    public String deletePartialSubmission() {
        String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
        if (StringUtils.isEmpty(pId)) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        try {
            PaRegistry.getStudyProtocolStageService().delete(IiConverter.convertToIi(pId));
        } catch (PAException e) {
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
        }
        setTrialAction("deletePartialSubmission");
        return REDIRECT_TO_SEARCH;
    }


    /**
     * @param sum4FundingCatCode the sum4FundingCatCode to set
     */
    public void setSum4FundingCatCode(String sum4FundingCatCode) {
        this.sum4FundingCatCode = sum4FundingCatCode;
    }


    /**
     * @return the sum4FundingCatCode
     */
    public String getSum4FundingCatCode() {
         return sum4FundingCatCode;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (this.trialDTO != null) {
            this.trialDTO.setPrimaryPurposeAdditionalQualifierCode(PAUtil
                    .lookupPrimaryPurposeAdditionalQualifierCode(this.trialDTO.getPrimaryPurposeCode()));
        }
    }
}
