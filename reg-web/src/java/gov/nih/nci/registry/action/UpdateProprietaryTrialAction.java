/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.CommonsConstant;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * The Class UpdateProprietaryTrialAction.
 *
 * @author Kalpana Guthikonda
 * @since May 18 2010
 */
public class UpdateProprietaryTrialAction extends ManageFileAction implements ServletResponseAware {

    private static final Logger LOG = Logger.getLogger(UpdateProprietaryTrialAction.class);
    private static final long serialVersionUID = 1L;
    private static final String SESSION_TRIAL_DTO = "trialDTO";
    private HttpServletResponse servletResponse;
    private ProprietaryTrialDTO trialDTO;
    private final TrialUtil  util = new TrialUtil();
    private String trialAction = null;
    private final TrialUtil trialUtil = new TrialUtil();
    private static final int TRIAL_TITLE_MAX_LENGTH = 4000;

    /**
     * View.
     * @return res
     */
    public String view() {
        TrialValidator.removeSessionAttributes();
        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(pId));
            trialDTO = new ProprietaryTrialDTO();
            util.getProprietaryTrialDTOFromDb(studyProtocolIi, trialDTO);
            setDocumentsInSession();

            ServletActionContext.getRequest().getSession().setAttribute("trialDTO", trialDTO);
            setPageFrom("updateProprietaryTrial");
            LOG.debug("Trial retrieved: " + trialDTO.getOfficialTitle());
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial " + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Review.
     * @return st
     */
    public String review() {
        clearErrorsAndMessages();
        try {
            enforceBusinessRules();
            if (hasFieldErrors()) {
                ServletActionContext.getRequest().setAttribute(
                        "failureMessage" , "The form has errors and could not be submitted, "
                        + "please check the fields highlighted below");
                return ERROR;
            }
            if (hasActionErrors()) {
                return ERROR;
            }
            trialDTO.setDocDtos(getTrialDocuments());
        } catch (IOException e) {
            addActionError(e.getMessage());
            return ERROR;
        }
        ServletActionContext.getRequest().getSession().removeAttribute(
                DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName());
        ServletActionContext.getRequest().getSession().removeAttribute(DocumentTypeCode.OTHER.getShortName());
        ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_DTO, trialDTO);
        return "review";
    }

    /**
     * Cancel.
     * @return s
     */
    public String cancel() {
        TrialValidator.removeSessionAttributes();
        return "redirect_to_search";
    }

    /**
     * Edits the.
     * @return s
     */
    public String edit() {
        trialDTO = (ProprietaryTrialDTO) ServletActionContext.getRequest().getSession().getAttribute("trialDTO");
        setDocumentsInSession();
        try {
            trialUtil.copyParticipatingSites(IiConverter.convertToStudyProtocolIi(
                    Long.parseLong(trialDTO.getIdentifier())), trialDTO);
        } catch (Exception e) {
            LOG.error("Exception occured while calling edit() " + e);
            return ERROR;
        }
        return "edit";
    }


    /**
     * Update.
     * @return the string
     */
    public String update() {
        trialDTO  = (ProprietaryTrialDTO) ServletActionContext.getRequest().getSession().
            getAttribute(SESSION_TRIAL_DTO);
        if (trialDTO == null) {
            return ERROR;
        }
        try {
            trialDTO.setPropritaryTrialIndicator(CommonsConstant.NO);
            StudyProtocolDTO studyProtocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
            util.convertToStudyProtocolDTO(trialDTO, studyProtocolDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(ServletActionContext.
                    getRequest().getRemoteUser()));
            OrganizationDTO leadOrganizationDTO = util.convertToLeadOrgDTO(trialDTO);
            St leadOrganizationIdentifier = StConverter.convertToSt(trialDTO.getLeadOrgTrialIdentifier());
            St nctIdentifier = StConverter.convertToSt(trialDTO.getNctIdentifier());
            Cd summary4TypeCode = CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode());
            OrganizationDTO summary4organizationDTO = util.convertToSummary4OrgDTO(trialDTO);
            List<StudySiteAccrualStatusDTO> siteAccrualStatusDTOList = getParticipatingSitesForUpdate(
                    trialDTO.getParticipatingSitesList());
            List<StudySiteDTO> siteDTOList = util.getStudySiteToUpdate(trialDTO.getParticipatingSitesList());
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos(),
                    studyProtocolDTO.getIdentifier());

           PaRegistry.getProprietaryTrialService().update(studyProtocolDTO, leadOrganizationDTO,
                   summary4organizationDTO, leadOrganizationIdentifier,
                    nctIdentifier, summary4TypeCode, documentDTOs, siteDTOList, siteAccrualStatusDTOList);
            StudyProtocolDTO protocolDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(
                    IiConverter.convertToStudyProtocolIi(Long.parseLong(trialDTO.getIdentifier())));
            TrialValidator.removeSessionAttributes();
            ServletActionContext.getRequest().getSession().setAttribute("protocolId",
                    protocolDTO.getIdentifier().getExtension());
        } catch (PAException e) {
            LOG.error(e);
            setDocumentsInSession();
            addActionError(RegistryUtil.removeExceptionFromErrMsg(e.getMessage()));
            return ERROR;
        }
        setTrialAction("update");
        return "redirect_to_search";
    }

    /**
     * Enforce business rules.
     */
    private void enforceBusinessRules() throws IOException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (StringUtils.isEmpty(trialDTO.getOfficialTitle())) {
            addFieldError("trialDTO.officialTitle", getText("error.submit.trialTitle"));
        } else if (trialDTO.getOfficialTitle().length() > TRIAL_TITLE_MAX_LENGTH) {
            addFieldError("trialDTO.officialTitle", getText("error.submit.trialTitleLength"));
        }
        if (StringUtils.isEmpty(trialDTO.getNctIdentifier()) && StringUtils.isEmpty(trialDTO.getPhaseCode())
                && StringUtils.isEmpty(trialDTO.getPrimaryPurposeCode())) {
            //if the nct Number is not present throw error phase code and primary purpose codes
            addFieldError("trialDTO.phaseCode", getText("error.submit.trialPhase"));
            addFieldError("trialDTO.primaryPurposeCode", getText("error.submit.trialPurpose"));
        }
        checkSummary4Funding();
        checkNctAndDoc(session);
        validateDocuments();
        TrialValidator validator = new TrialValidator();
        validateProtocolDocUpdate(session, validator);
        validateOtherDocUpdate(session, validator);
        checkSubmittingOrgRules();
    }

    private void checkSummary4Funding() {
        if (!StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && StringUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            addFieldError("summary4FundingSponsor", "Select the Summary 4 Funding Sponsor");
        }
        if (StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && !StringUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            addFieldError("trialDTO.summaryFourFundingCategoryCode", "Select the Summary 4 Funding Sponsor Type");
        }
    }

    private void checkNctAndDoc(HttpSession session) {
        if (StringUtils.isEmpty(trialDTO.getNctIdentifier())
                && StringUtils.isEmpty(getProtocolDocFileName())
                && session.getAttribute(DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName()) == null) {
            addFieldError("trialDTO.nctIdentifier", "Provide either NCT Number or Protocol Trial Template.\n");
            addFieldError("trialDTO.protocolDocFileName", "Provide either NCT Number or Protocol Trial Template.\n");
        }
    }

    private void checkSubmittingOrgRules() {
        PAServiceUtils paServiceUtils = new PAServiceUtils();
        for (SubmittedOrganizationDTO dto : trialDTO.getParticipatingSitesList()) {
            if (StringUtils.isEmpty(dto.getSiteLocalTrialIdentifier())) {
                addActionError("For " + dto.getName() + " Organization cannot have a null Local Trial Identifier ");
            }
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO = convertToStudySiteAccrualStatusDTO(dto);
            StudySiteDTO studySiteDTO = getSubmittingStudySiteDTO(dto);
            String errMsg = paServiceUtils.validateRecuritmentStatusDateRule(studySiteAccrualStatusDTO, studySiteDTO);
            if (StringUtils.isNotEmpty(errMsg)) {
                addActionError(errMsg);
            }
        }
    }

    /**
     * Gets the submitting study site dto.
     * @return the submitting study site dto
     */
    private StudySiteDTO getSubmittingStudySiteDTO(SubmittedOrganizationDTO dto) {
        StudySiteDTO siteDTO = new StudySiteDTO();
        siteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(dto.getSiteLocalTrialIdentifier()));
        siteDTO.setProgramCodeText(StConverter.convertToSt(dto.getProgramCode()));
        if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                && StringUtils.isNotEmpty(dto.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(),
                    dto.getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(dto.getDateOpenedforAccrual())
                && StringUtils.isEmpty(dto.getDateClosedforAccrual())) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dto.getDateOpenedforAccrual(),
                    null));
        }
        return siteDTO;
    }

    /**
     * Convert to study site accrual status dto.
     * @param trialDto the trial dto
     * @return the study site accrual status dto
     */
    private StudySiteAccrualStatusDTO convertToStudySiteAccrualStatusDTO(SubmittedOrganizationDTO dto) {
        StudySiteAccrualStatusDTO isoDto = new StudySiteAccrualStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(dto
                .getRecruitmentStatus())));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(dto
                .getRecruitmentStatusDate())));
        return isoDto;
    }

    private void setDocumentsInSession() {
        List<DocumentDTO> documentISOList;
        try {
            documentISOList = PaRegistry.getDocumentService().getDocumentsByStudyProtocol(
                  IiConverter.convertToIi(trialDTO.getIdentifier()));
            if (!(documentISOList.isEmpty())) {
                TrialDocumentWebDTO webDto = null;
                for (DocumentDTO docDTO : documentISOList) {
                     webDto = new TrialDocumentWebDTO(docDTO);
                     ServletActionContext.getRequest().getSession().setAttribute(DocumentTypeCode.getByCode(
                            webDto.getTypeCode()).getShortName(), webDto);
                }
            }
        } catch (PAException e) {
            LOG.error("exception while setting Document in session", e);
        }
    }

    private List<StudySiteAccrualStatusDTO> getParticipatingSitesForUpdate(List<SubmittedOrganizationDTO> ps)
    throws PAException {
        List<StudySiteAccrualStatusDTO> ssaDTO = new ArrayList<StudySiteAccrualStatusDTO>();
        for (SubmittedOrganizationDTO dto : ps) {
            StudySiteAccrualStatusDTO ssasOld = PaRegistry.getStudySiteAccrualStatusService()
             .getCurrentStudySiteAccrualStatusByStudySite(IiConverter.convertToIi(dto.getId()));
            StudySiteAccrualStatusDTO ssas =  new StudySiteAccrualStatusDTO();
            ssas.setStudySiteIi(ssasOld.getStudySiteIi());
            ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(dto.getRecruitmentStatus())));
            ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(dto.getRecruitmentStatusDate())));
            ssaDTO.add(ssas);
        }
        return ssaDTO;
    }

    /**
     * Sets the servlet response.
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * Gets the servlet response.
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * Gets the trial dto.
     * @return the trialDTO
     */
    public ProprietaryTrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * Sets the trial dto.
     * @param trialDTO the trialDTO to set
     */
    public void setTrialDTO(ProprietaryTrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

    /**
     * Gets the trial action.
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }

    /**
     * Sets the trial action.
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }
}
