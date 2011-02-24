/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.SelectedRegistryUser;
import gov.nih.nci.registry.util.SelectedStudyProtocol;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for managing user trial ownership.
 * @author kkanchinadam
 */
@SuppressWarnings("unchecked")
public class ManageTrialOwnershipAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG  = Logger.getLogger(ManageTrialOwnershipAction.class);
    private static final String REG_USERS_LIST = "regUsersList";
    private static final String STUDY_PROTOCOLS_LIST = "studyProtocolsList";
    private static final String VIEW_RESULTS = "viewResults";
    private List<SelectedStudyProtocol> studyProtocols = new ArrayList<SelectedStudyProtocol>();
    private List<SelectedRegistryUser> registryUsers = new ArrayList<SelectedRegistryUser>();
    private static final String SUCCESS_MSG = "successMessage";
    private static final String FAILURE_MSG = "failureMessage";
    private Long regUserId = null;
    private boolean owner;
    private Long trialId = null;
    private boolean selected;
    /**
     * @return the trialId
     */
    public Long getTrialId() {
        return trialId;
    }

    /**
     * @param trialId the trialId to set
     */
    public void setTrialId(Long trialId) {
        this.trialId = trialId;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param isSelected the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.selected = isSelected;
    }

    /**
     * @return the regUserId
     */
    public Long getRegUserId() {
        return regUserId;
    }

    /**
     * @param regUserId the regUserId to set
     */
    public void setRegUserId(Long regUserId) {
        this.regUserId = regUserId;
    }

    /**
     * @return the isOwner
     */
    public boolean isOwner() {
        return owner;
    }

    /**
     * @param isOwner the isOwner to set
     */
    public void setOwner(boolean isOwner) {
        this.owner = isOwner;
    }

    /**
     * load initial view.
     * @return String the action result.
     * @throws PAException the pa exception.
     */
    public String search() throws PAException {
        performSearch();
        return VIEW_RESULTS;
    }

    private void performSearch() throws PAException {
        String loginName = null;
        ServletActionContext.getRequest().getSession().removeAttribute(ManageTrialOwnershipAction.REG_USERS_LIST);
        ServletActionContext.getRequest().getSession().removeAttribute(ManageTrialOwnershipAction.STUDY_PROTOCOLS_LIST);

        try {
            loginName =  ServletActionContext.getRequest().getRemoteUser();
            RegistryUser loggedInUser = PaRegistry.getRegistryUserService().getUser(loginName);
            Long affiliatedOrgId = loggedInUser.getAffiliatedOrganizationId();
            getOrgMembers(affiliatedOrgId);
            getOrgTrials(affiliatedOrgId);
       } catch (PAException e) {
           LOG.error(e.getMessage());
           throw new PAException(e);
       }
    }

    private void getOrgMembers(Long affiliatedOrgId) throws PAException {
        RegistryUser criteria = new RegistryUser();
        criteria.setAffiliatedOrganizationId(affiliatedOrgId);
        List<RegistryUser> regUsers = PaRegistry.getRegistryUserService().search(criteria);
        registryUsers.clear();
        for (RegistryUser user : regUsers) {
            SelectedRegistryUser selectedRegUser = new SelectedRegistryUser();
            selectedRegUser.setRegistryUser(user);
            registryUsers.add(selectedRegUser);
        }
        ServletActionContext.getRequest().getSession().setAttribute(
                ManageTrialOwnershipAction.REG_USERS_LIST, registryUsers);
    }

    private void getOrgTrials(Long affiliatedOrgId) throws PAException {
        List<StudyProtocol> trials = PaRegistry.getProtocolQueryService()
        .getStudyProtocolByOrgIdentifier(affiliatedOrgId);
        studyProtocols.clear();
        for (StudyProtocol sp : trials) {
            SelectedStudyProtocol selectedStudyProtocol = new SelectedStudyProtocol();
            selectedStudyProtocol.setStudyProtocol(sp);
            selectedStudyProtocol.setNciIdentifier(PADomainUtils.getAssignedIdentifierExtension(sp));
            selectedStudyProtocol.setLeadOrgId(PADomainUtils.getLeadOrgSpId(sp));
            studyProtocols.add(selectedStudyProtocol);
        }
        ServletActionContext.getRequest().getSession().setAttribute(
                ManageTrialOwnershipAction.STUDY_PROTOCOLS_LIST, studyProtocols);
    }

    /**
     * set if user is owner or not.
     * @throws PAException the pa exception
     */
    public void setRegUser() throws PAException {
        if (regUserId != null) {
            SelectedRegistryUser regUser = getRegUser(regUserId);
            if (regUser != null) {
                regUser.setSelected(owner);
                ServletActionContext.getRequest().getSession().setAttribute(
                        ManageTrialOwnershipAction.REG_USERS_LIST, registryUsers);
            }
        }
    }

    private SelectedRegistryUser getRegUser(Long rUserId) {
        SelectedRegistryUser regUser = null;
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext.getRequest().getSession().
        getAttribute(ManageTrialOwnershipAction.REG_USERS_LIST);
        for (SelectedRegistryUser srUser : registryUsers) {
            if (rUserId.equals(srUser.getRegistryUser().getId())) {
                regUser = srUser;
            }
        }
        return regUser;
    }

    /**
     * set if trial is selected or not.
     * @throws PAException the pa exception
     */
    public void setTrial() throws PAException {
        if (trialId != null) {
            SelectedStudyProtocol studyProtocol = getStudyProtocol(trialId);
            if (studyProtocol != null) {
                studyProtocol.setSelected(selected);
                ServletActionContext.getRequest().getSession().setAttribute(
                        ManageTrialOwnershipAction.STUDY_PROTOCOLS_LIST, studyProtocols);
            }
        }
    }

    private SelectedStudyProtocol getStudyProtocol(Long tId) {
        SelectedStudyProtocol sp = null;
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext.getRequest().getSession().
        getAttribute(ManageTrialOwnershipAction.STUDY_PROTOCOLS_LIST);
        for (SelectedStudyProtocol selSP : studyProtocols) {
            if (tId.equals(selSP.getStudyProtocol().getId())) {
                sp = selSP;
            }
        }
        return sp;
    }

    /**
     * view results from session..
     * @return String
     */
    public String view() {
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext.getRequest().getSession().
        getAttribute(ManageTrialOwnershipAction.REG_USERS_LIST);
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext.getRequest().getSession().
        getAttribute(ManageTrialOwnershipAction.STUDY_PROTOCOLS_LIST);

        return VIEW_RESULTS;
    }

    /**
     * assign ownership.
     * @return String
     * @throws PAException
     * @throws PAException the pa exception
     */
    public String assignOwnership() throws PAException {
        try {
            List<Long> selectedUserIds = getSelectedUserIds();
            List<Long> selectedTrialIds = getSelectedTrialIds();
            if (!selectedUserIds.isEmpty() && !selectedTrialIds.isEmpty()) {
                for (Long userId : selectedUserIds) {
                    for (Long tId : selectedTrialIds) {
                        updateOwnership(userId, tId, true);
                    }
                }
                ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                        "Trial ownerships successfully assigned");
            } else {
                ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                "Please select at least one organizational member and one trial to assign ownership");
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG, e.getMessage());
            throw new PAException(e);
        }
        return view();
    }

    /**
     * Unassign ownership.
     * @return String
     * @throws PAException
     * @throws PAException the pa exception
     */
    public String unassignOwnership() throws PAException {
        try {
            List<Long> selectedUserIds = getSelectedUserIds();
            List<Long> selectedTrialIds = getSelectedTrialIds();
            if (!selectedUserIds.isEmpty() && !selectedTrialIds.isEmpty()) {
                for (Long userId : selectedUserIds) {
                    for (Long tId : selectedTrialIds) {
                        updateOwnership(userId, tId, false);
                    }
                }
                ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                        "Trial ownerships successfully unassigned");
            } else {
                ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                "Please select at least one organizational member and one trial to unassign ownership");
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG, e.getMessage());
            throw new PAException(e);
        }

        return view();
    }

    private void updateOwnership(Long userId, Long tId, boolean assign) throws PAException {
        // check if currently owner or not.
        boolean isOwner = PaRegistry.getRegistryUserService().isTrialOwner(userId, tId);
        if (assign && !isOwner) {
            PaRegistry.getRegistryUserService().assignOwnership(userId, tId);
        }

        if (!assign && isOwner) {
            PaRegistry.getRegistryUserService().removeOwnership(userId, tId);
        }
    }

    private List<Long> getSelectedUserIds() {
        List<Long> selectedUserIds = new ArrayList<Long>();
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext.getRequest().getSession().
        getAttribute(ManageTrialOwnershipAction.REG_USERS_LIST);
        for (SelectedRegistryUser user : registryUsers) {
            if (user.isSelected()) {
                selectedUserIds.add(user.getRegistryUser().getId());
            }
        }
        return selectedUserIds;
    }

    private List<Long> getSelectedTrialIds() {
        List<Long> selectedTrialIds = new ArrayList<Long>();
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext.getRequest().getSession().
        getAttribute(ManageTrialOwnershipAction.STUDY_PROTOCOLS_LIST);
        for (SelectedStudyProtocol sp : studyProtocols) {
            if (sp.isSelected()) {
                selectedTrialIds.add(sp.getStudyProtocol().getId());
            }
        }
        return selectedTrialIds;
    }

    /**
     * @return the registryUsers.
     */
    public List<SelectedRegistryUser> getRegistryUsers() {
        return registryUsers;
    }

    /**
     * @param registryUsers the registryUsers to set
     */
    public void setRegistryUsers(List<SelectedRegistryUser> registryUsers) {
        this.registryUsers = registryUsers;
    }

    /**
     * @return the studyProtocols
     */
    public List<SelectedStudyProtocol> getStudyProtocols() {
        return studyProtocols;
    }

    /**
     * @param studyProtocols the studyProtocols to set
     */
    public void setStudyProtocols(List<SelectedStudyProtocol> studyProtocols) {
        this.studyProtocols = studyProtocols;
    }

}
