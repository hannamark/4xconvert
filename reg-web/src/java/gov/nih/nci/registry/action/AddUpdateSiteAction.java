/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
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
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.exception.DuplicateParticipatingSiteException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Add a participating site to the trial.
 * 
 * @author Denis G. Krylov
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public class AddUpdateSiteAction extends ActionSupport implements Preparable {

    static final String SUCCESS_MESSAGE_KEY = "successMessage";
    private static final String WAIT = "wait";
    private static final String SESSION_TRIAL_NCI_ID_ATTRIBUTE = "NCI_ID";
    private static final String SESSION_TRIAL_TITLE_ATTRIBUTE = "TITLE";
    private static final String SESSION_TRIAL_LEAD_ORG_IDENTIFIER_ATTRIBUTE = "LEAD_ORG_ID";
    private static final long serialVersionUID = -5720501246071254426L;
    private static final Logger LOG = Logger
            .getLogger(AddUpdateSiteAction.class);

    private SubmittedOrganizationDTO siteDTO = new SubmittedOrganizationDTO();

    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    private TrialUtil trialUtil = new TrialUtil();

    private RegistryUserServiceLocal registryUserService;
    private ParticipatingSiteServiceLocal participatingSiteService;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudySiteContactServiceLocal studySiteContactService;
    private ProtocolQueryServiceLocal protocolQueryService;

    private boolean redirectToSummary;
    private String studyProtocolId;    

    /**
     * Prepare and display the site add/update pop-up.
     * 
     * @return status page if successful, error page otherwise
     */
    public String view() {
        try {
            populateSiteDTO();
            StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
            studyProtocolQueryDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                    Long.parseLong(studyProtocolId));
            ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_NCI_ID_ATTRIBUTE, 
                    studyProtocolQueryDTO.getNciIdentifier());
            ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_TITLE_ATTRIBUTE, 
                    studyProtocolQueryDTO.getOfficialTitle());
            ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_LEAD_ORG_IDENTIFIER_ATTRIBUTE, 
                    studyProtocolQueryDTO.getLocalStudyProtocolIdentifier());
            ServletActionContext
                    .getRequest()
                    .getSession()
                    .setAttribute(TrialUtil.SESSION_TRIAL_SITE_ATTRIBUTE,
                            getSiteDTO());
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getMessage());
            LOG.error(e, e);
            return ERROR;
        }
    }

    void populateSiteDTO() throws PAException {
        siteDTO.setId(null);
        RegistryUser loggedInUser = getRegistryUser();
        final String org = loggedInUser.getAffiliateOrg();
        final Long orgId = loggedInUser.getAffiliatedOrganizationId();
        if (StringUtils.isBlank(org) || orgId == null) {
            throw new PAException(
                    "We are unable to determine your affiliation with an organization. "
                            + "You will not be able to add your site to this trial. Sorry.");
        }
        siteDTO.setName(org);

        String poOrgId = orgId.toString();
        Ii poHcfIi = paServiceUtil.getPoHcfIi(poOrgId);
        Ii spID = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(getStudyProtocolId()));        
        StudySiteDTO studySiteDTO = trialUtil.getParticipatingSite(spID,
                poHcfIi);
        if (studySiteDTO != null) {
            // Participating site already exists. Preparing for 'update' mode.
            siteDTO = trialUtil.getSubmittedOrganizationDTO(studySiteDTO);
        }

    }

    /**
     * @return
     * @throws PAException
     */
    private RegistryUser getRegistryUser() throws PAException {
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        return registryUserService.getUser(loginName);
    }

    /**
     * Save or update the participating site.
     * 
     * @return status page if successful, error page otherwise
     */
    public String save() {
        String fwd = ERROR;
        final HttpSession session = ServletActionContext.getRequest()
                .getSession();
        try {
            clearErrorsAndMessages();
            enforceBusinessRulesForProprietary();
            if (!(hasActionErrors() || hasFieldErrors())) {
                if (StringUtils.isNotBlank(siteDTO.getId())) {
                    updateSite();
                    session.setAttribute(SUCCESS_MESSAGE_KEY,
                            getText("add.site.updateSuccess"));
                } else {
                    addSite();
                    session.setAttribute(SUCCESS_MESSAGE_KEY,
                            getText("add.site.success"));
                }
                redirectToSummary = true;
                fwd = SUCCESS;
            }
        } catch (Exception e) {
            LOG.error(e, e);
            if (!(hasActionErrors() || hasFieldErrors())) {
                RegistryUtil.setFailureMessage(e);
                addActionError("An internal error has occured.");
            }
        }
        return fwd;
    }

    private void updateSite() throws PAException {
        StudySiteDTO studySiteDTO = getStudySite();
        StudySiteAccrualStatusDTO accrualStatusDTO = getStudySiteAccrualStatus();

        Ii studySiteID = participatingSiteService.updateStudySiteParticipant(
                studySiteDTO, accrualStatusDTO).getIdentifier();
        clearInvestigatorsForPropTrialSite(studySiteID);
        addInvestigator(studySiteID);
    }

    private void clearInvestigatorsForPropTrialSite(Ii ssIi) throws PAException {
        List<StudySiteContactDTO> ssContDtoList = studySiteContactService
                .getByStudySite(ssIi);
        for (StudySiteContactDTO item : ssContDtoList) {
            studySiteContactService.delete(item.getIdentifier());
        }
    }

    private void addSite() throws PAException {
        Ii poHealthFacilID = getHealthcareFacilityID();
        StudySiteDTO studySiteDTO = getStudySite();
        StudySiteAccrualStatusDTO accrualStatusDTO = getStudySiteAccrualStatus();
        Ii studySiteID = null; // IiConverter.convertToStudySiteIi(studySiteIdentifier);

        try {
            studySiteID = participatingSiteService.createStudySiteParticipant(
                    studySiteDTO, accrualStatusDTO, poHealthFacilID)
                    .getIdentifier();
        } catch (DuplicateParticipatingSiteException e) {
            addFieldError("organizationName", e.getMessage());
            throw new PAException(e);
        }

        addInvestigator(studySiteID);
        createSiteRecordOwnership(studySiteID, getRegistryUser());
    }

    /**
     * @param studySiteID
     * @throws PAException
     */
    private void addInvestigator(Ii studySiteID) throws PAException {
        Ii investigatorIi = IiConverter.convertToPoPersonIi(siteDTO
                .getInvestigatorId().toString());
        addInvestigator(studySiteID, investigatorIi,
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode(),
                getRegistryUser().getAffiliatedOrganizationId().toString());
    }

    /**
     * @return
     * @throws PAException
     */
    private Ii getHealthcareFacilityID() throws PAException {
        Ii poHealthFacilID = paServiceUtil.getPoHcfIi(getRegistryUser()
                .getAffiliatedOrganizationId().toString());
        return poHealthFacilID;
    }

    private void createSiteRecordOwnership(Ii ssIi, RegistryUser registryUser)
            throws PAException {
        registryUserService.assignSiteOwnership(registryUser.getId(),
                IiConverter.convertToLong(ssIi));
    }

    private void addInvestigator(Ii ssIi, Ii investigatorIi, String role,
            String poOrgId) throws PAException {
        ClinicalResearchStaffDTO crsDTO = paServiceUtil.getCrsDTO(
                investigatorIi, poOrgId);
        StudyProtocolDTO spDTO = studyProtocolService
                .getStudyProtocol(IiConverter.convertToStudyProtocolIi(Long
                        .parseLong(getStudyProtocolId())));
        HealthCareProviderDTO hcpDTO = paServiceUtil.getHcpDTO(spDTO
                .getStudyProtocolType().getValue(), investigatorIi, poOrgId);
        participatingSiteService.addStudySiteInvestigator(ssIi, crsDTO, hcpDTO,
                null, role);
    }

    private StudySiteDTO getStudySite() {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO
                .setIdentifier(StringUtils.isNotBlank(siteDTO.getId()) ? IiConverter
                        .convertToStudySiteIi(Long.parseLong(siteDTO.getId()))
                        : IiConverter.convertToIi((Long) null));
        studySiteDTO.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.PENDING));
        studySiteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(new Date().getTime()), null));
        studySiteDTO
                .setStudyProtocolIdentifier(IiConverter
                        .convertToStudyProtocolIi(Long
                                .parseLong(getStudyProtocolId())));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter
                .convertToSt(siteDTO.getSiteLocalTrialIdentifier()));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt(siteDTO
                .getProgramCode()));
        if (StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())
                && StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())) {
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(siteDTO.getDateOpenedforAccrual(),
                            siteDTO.getDateClosedforAccrual()));
        }
        if (StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())
                && StringUtils.isEmpty(siteDTO.getDateClosedforAccrual())) {
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                    .convertToIvl(siteDTO.getDateOpenedforAccrual(), null));
        }
        return studySiteDTO;
    }

    private StudySiteAccrualStatusDTO getStudySiteAccrualStatus() {
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode
                .getByCode(siteDTO.getRecruitmentStatus())));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(siteDTO.getRecruitmentStatusDate())));
        return ssas;
    }

    /**
     * Keeps the user looking at the spinning wheel until a SUCCESS/EXCEPTION
     * occurs.
     * 
     * @return res
     */
    public String showWaitDialog() {
        ServletActionContext.getRequest().setAttribute(
                TrialUtil.SESSION_WAIT_MESSAGE_ATTRIBUTE,
                getText("add.site.wait"));
        return WAIT;
    }

    /**
     * @return the siteDTO
     */
    public SubmittedOrganizationDTO getSiteDTO() {
        return siteDTO;
    }

    /**
     * @param siteDTO
     *            the siteDTO to set
     */
    public void setSiteDTO(SubmittedOrganizationDTO siteDTO) {
        this.siteDTO = siteDTO;
    }

    // The following validation methods have been copied as-is from
    // gov.nih.nci.pa.action.ParticipatingOrganizationsAction and adjusted for
    // use here.
    // The original validation methods in ParticipatingOrganizationsAction are
    // hard-wired
    // into the action class and are not easily reusable; hence the copy &
    // paste.
    // This needs to be re-visited at a later point to see how we could extract
    // the validation
    // into common functionality and stay DRY. TO DO. Sorry.
    private void enforceBusinessRulesForProprietary() {
        String err = "error.submit.invalidDate"; // validate date and its format
        checkInvestigatorStatus();
        enforcePartialRulesForProp1(err);
        String strDateOpenedForAccrual = "accrualOpenedDate";
        String strDateClosedForAccrual = "accrualClosedDate";
        enforcePartialRulesForProp2(err, strDateOpenedForAccrual,
                strDateClosedForAccrual);
        enforcePartialRulesForProp3(strDateOpenedForAccrual,
                strDateClosedForAccrual);
        enforcePartialRulesForProp4(strDateOpenedForAccrual,
                strDateClosedForAccrual);
    }

    private void checkInvestigatorStatus() {
        if (siteDTO.getInvestigatorId() != null) {
            Ii investigatorIi = IiConverter.convertToPoPersonIi(siteDTO
                    .getInvestigatorId().toString());
            if (paServiceUtil.getPoPersonEntity(investigatorIi) == null) {
                addFieldError("investigator",
                        getText("error.nullifiedInvestigator"));
            }
        }

    }

    private void enforcePartialRulesForProp1(String err) {
        checkFieldError(
                StringUtils.isEmpty(siteDTO.getSiteLocalTrialIdentifier()),
                "localIdentifier", "error.siteLocalTrialIdentifier.required");
        checkFieldError(siteDTO.getInvestigatorId() == null, "investigator",
                "error.selectedPersId.required");
        checkFieldError(StringUtils.isEmpty(siteDTO.getRecruitmentStatus()),
                "statusCode",
                "error.participatingOrganizations.recruitmentStatus");
        if (!PAUtil.isValidDate(siteDTO.getRecruitmentStatusDate())) {
            addFieldError("statusDate", getText(err));
        } else if (PAUtil.isDateCurrentOrPast(siteDTO
                .getRecruitmentStatusDate())) {
            addFieldError("statusDate",
                    getText("error.submit.invalidStatusDate"));
        }
    }

    private void enforcePartialRulesForProp2(String err,
            String strDateOpenedForAccrual, String strDateClosedForAccrual) {
        if (StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())) {
            if (!PAUtil.isValidDate(siteDTO.getDateOpenedforAccrual())) {
                addFieldError(strDateOpenedForAccrual, getText(err));
            } else {
                checkFieldError(PAUtil.isDateCurrentOrPast(siteDTO
                        .getDateOpenedforAccrual()), strDateOpenedForAccrual,
                        "error.submit.invalidStatusDate");
            }
        }
        if (StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())) {
            if (!PAUtil.isValidDate(siteDTO.getDateClosedforAccrual())) {
                addFieldError(strDateClosedForAccrual, getText(err));
            } else {
                checkFieldError(PAUtil.isDateCurrentOrPast(siteDTO
                        .getDateClosedforAccrual()), strDateClosedForAccrual,
                        "error.submit.invalidStatusDate");

            }
        }
    }

    private void enforcePartialRulesForProp3(String strDateOpenedForAccrual,
            String strDateClosedForAccrual) {
        checkFieldError(
                StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())
                        && StringUtils.isEmpty(siteDTO
                                .getDateOpenedforAccrual()),
                strDateOpenedForAccrual, "error.proprietary.dateOpenReq");
        if (StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())
                && StringUtils.isNotEmpty(siteDTO.getDateClosedforAccrual())) {
            Timestamp dateOpenedDateStamp = PAUtil
                    .dateStringToTimestamp(siteDTO.getDateOpenedforAccrual());
            Timestamp dateClosedDateStamp = PAUtil
                    .dateStringToTimestamp(siteDTO.getDateClosedforAccrual());
            checkFieldError(dateClosedDateStamp.before(dateOpenedDateStamp),
                    strDateClosedForAccrual,
                    "error.proprietary.dateClosedAccrualBigger");
        }

    }

    // NOPMD
    private void enforcePartialRulesForProp4(String strDateOpenedForAccrual,
            String strDateClosedForAccrual) {
        if (StringUtils.isNotEmpty(siteDTO.getRecruitmentStatus())) {
            RecruitmentStatusCode recruitmentStatus = RecruitmentStatusCode
                    .getByCode(siteDTO.getRecruitmentStatus());
            if (recruitmentStatus.isNonRecruiting()) {
                if (StringUtils.isNotEmpty(siteDTO.getDateOpenedforAccrual())) {
                    addFieldError(strDateOpenedForAccrual,
                            "Date Opened for Acrual must be empty for "
                                    + siteDTO.getRecruitmentStatus()
                                    + " recruitment status");
                }
            } else if (StringUtils.isEmpty(siteDTO.getDateOpenedforAccrual())) {
                addFieldError(strDateOpenedForAccrual,
                        "Date Opened for Acrual must not be empty for "
                                + siteDTO.getRecruitmentStatus()
                                + " recruitment status");
            }
            if ((RecruitmentStatusCode.ADMINISTRATIVELY_COMPLETE.getCode()
                    .equalsIgnoreCase(siteDTO.getRecruitmentStatus()) || RecruitmentStatusCode.COMPLETED
                    .getCode().equalsIgnoreCase(siteDTO.getRecruitmentStatus()))
                    && StringUtils.isEmpty(siteDTO.getDateClosedforAccrual())) {
                addFieldError(strDateClosedForAccrual,
                        "Date Closed for Acrual must not be empty for "
                                + siteDTO.getRecruitmentStatus()
                                + " recruitment status");
            }
        }
    }

    private void checkFieldError(boolean condition, String fieldName,
            String textKey) {
        if (condition) {
            addFieldError(fieldName, getText(textKey));
        }
    }

    /**
     * @return the redirectToSummary
     */
    public boolean isRedirectToSummary() {
        return redirectToSummary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        registryUserService = PaRegistry.getRegistryUserService();
        participatingSiteService = PaRegistry.getParticipatingSiteService();
        studySiteContactService = PaRegistry.getStudySiteContactService();
    }

    /**
     * @return the studyProtocolId
     */
    public String getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId
     *            the studyProtocolId to set
     */
    public void setStudyProtocolId(String studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }
    
    /**
     * @param paServiceUtil PAServiceUtils
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }
    
    /**
     * @param trialUtil TrialUtil
     */
    public void setTrialUtil(TrialUtil trialUtil) {
        this.trialUtil = trialUtil;
    }
    
    /**
     * @param registryUserService RegistryUserServiceLocal
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }
    
    /**
     * @param participatingSiteService ParticipatingSiteServiceLocal
     */
    public void setParticipatingSiteService(
            ParticipatingSiteServiceLocal participatingSiteService) {
        this.participatingSiteService = participatingSiteService;
    }
    
    /**
     * @param studySiteContactService StudySiteContactServiceLocal
     */
    public void setStudySiteContactService(
            StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @param studyProtocolService StudyProtocolServiceLocal
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
    
    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }
}
