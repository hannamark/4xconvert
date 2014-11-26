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
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
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
    private static final String PICK_A_SITE = "pickASite";

    private SubmittedOrganizationDTO siteDTO = new SubmittedOrganizationDTO();

    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    private TrialUtil trialUtil = new TrialUtil();

    private RegistryUserServiceLocal registryUserService;
    private ParticipatingSiteServiceLocal participatingSiteService;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudySiteContactServiceLocal studySiteContactService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private OrganizationEntityServiceRemote organizationService;
    

    private boolean redirectToSummary;
    private String studyProtocolId;    
    private String pickedSiteOrgPoId;

    /**
     * Prepare and display the site add/update pop-up.
     * 
     * @return status page if successful, error page otherwise
     */
    public String view() {
        try {
            prepareProtocolData();            
            populateSiteDTO();
            setSiteDtoInSession();
            // PO-8268 kicks in here. If user can actually update multiple
            // sites, we need to present a dialog
            // asking which one she or he wants to update.
            if (canUpdateMultipleSites()) {
                return PICK_A_SITE;
            } else {
                return SUCCESS;
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
            LOG.error(e, e);
            return ERROR;
        }
    }

    /**
     * @throws NumberFormatException
     * @throws PAException
     */
    private void prepareProtocolData() throws 
            PAException {
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(Long.parseLong(studyProtocolId));
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.TRIAL_SUMMARY, spDTO);             
        StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
        studyProtocolQueryDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                Long.parseLong(studyProtocolId));
        ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_NCI_ID_ATTRIBUTE, 
                studyProtocolQueryDTO.getNciIdentifier());
        ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_TITLE_ATTRIBUTE, 
                studyProtocolQueryDTO.getOfficialTitle());
        ServletActionContext.getRequest().getSession().setAttribute(SESSION_TRIAL_LEAD_ORG_IDENTIFIER_ATTRIBUTE, 
                studyProtocolQueryDTO.getLocalStudyProtocolIdentifier());
    }
    
    /**
     * @return String
     */
    public String pickSite() {
        try {
            makeSureUserDidNotManipulateSiteIdInForm();
            prepareProtocolData();
            populateSiteDTOBasedOnOrg(getPickedSiteOrgPoId());
            setSiteDtoInSession();
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getMessage());
            LOG.error(e, e);
            return ERROR;
        }

    }

    private void makeSureUserDidNotManipulateSiteIdInForm()
            throws NullifiedRoleException, PAException {
        if (CollectionUtils.find(getListOfSitesUserCanUpdate(),
                new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        return StringUtils.equals(
                                ((Organization) o).getIdentifier(),
                                getPickedSiteOrgPoId());

                    }
                }) == null) {
            throw new PAException(
                    "User is not allowed to update the selected site.");
        }
    }

    /**
     * 
     */
    private void setSiteDtoInSession() {
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(TrialUtil.SESSION_TRIAL_SITE_ATTRIBUTE,
                        getSiteDTO());
    }

    private boolean canUpdateMultipleSites() throws PAException,
            NullifiedRoleException {
        return StringUtils.isNotBlank(siteDTO.getId())
                && getListOfSitesUserCanUpdate().size() > 1;
    }

    /**
     * @return List<Organization>
     * @throws PAException
     *             PAException
     * @throws NullifiedRoleException
     *             NullifiedRoleException
     */
    public final List<Organization> getListOfSitesUserCanUpdate() throws PAException,
            NullifiedRoleException {
        RegistryUser loggedInUser = getRegistryUser();
        Ii spID = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(getStudyProtocolId()));
        return participatingSiteService.getListOfSitesUserCanUpdate(
                loggedInUser, spID);
    }

    void populateSiteDTO() throws PAException, NullifiedEntityException {        
        String poOrgId = getUserAffiliationPoOrgId();  
        populateSiteDTOBasedOnOrg(poOrgId);
    }

    /**
     * @param poOrgId
     * @throws NumberFormatException
     * @throws PAException
     * @throws NullifiedEntityException
     */
    private void populateSiteDTOBasedOnOrg(String poOrgId)
            throws PAException, NullifiedEntityException {
        Ii spID = IiConverter.convertToStudyProtocolIi(Long
                .parseLong(getStudyProtocolId()));        
        StudySiteDTO studySiteDTO = participatingSiteService.getParticipatingSite(spID, poOrgId);
        siteDTO.setId(null);
        siteDTO.setName(EnOnConverter.convertEnOnToString(organizationService
                .getOrganization(
                        IiConverter.convertToPoOrganizationIi(poOrgId))
                .getName()));
        if (studySiteDTO != null) {
            // Participating site already exists. Preparing for 'update' mode.
            siteDTO = trialUtil.getSubmittedOrganizationDTO(studySiteDTO);
        }
    }

    /**
     * @return UserAffiliationPoOrgId
     * @throws PAException PAException
     */
    public String getUserAffiliationPoOrgId() throws PAException {
        RegistryUser loggedInUser = getRegistryUser();
        final Long orgId = loggedInUser.getAffiliatedOrganizationId();
        if (orgId == null) {
            throw new PAException(
                    "We are unable to determine your affiliation with an organization. "
                            + "You will not be able to add your site to this trial. Sorry.");
        }
        return orgId.toString();
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
            new ParticipatingSiteValidator(siteDTO, this, this, paServiceUtil)
                    .validate();
            if (!(hasActionErrors() || hasFieldErrors())) {
                final AddUpdateSiteHelper helper = new AddUpdateSiteHelper(
                        paServiceUtil, siteDTO, participatingSiteService,
                        studyProtocolService, getRegistryUser(),
                        registryUserService, studySiteContactService, this,
                        getStudyProtocolId(), getRegistryUser()
                                .getAffiliatedOrganizationId().toString());
                if (StringUtils.isNotBlank(siteDTO.getId())) {
                    helper.updateSite();
                    session.setAttribute(SUCCESS_MESSAGE_KEY,
                            getText("add.site.updateSuccess"));
                } else {
                    helper.addSite();
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
        organizationService = PoRegistry.getOrganizationEntityService();
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

    /**
     * @return the pickedSiteOrgPoId
     */
    public String getPickedSiteOrgPoId() {
        return pickedSiteOrgPoId;
    }

    /**
     * @param pickedSiteOrgPoId the pickedSiteOrgPoId to set
     */
    public void setPickedSiteOrgPoId(String pickedSiteOrgPoId) {
        this.pickedSiteOrgPoId = pickedSiteOrgPoId;
    }

    /**
     * @param organizationService the organizationService to set
     */
    public void setOrganizationService(
            OrganizationEntityServiceRemote organizationService) {
        this.organizationService = organizationService;
    }
}
