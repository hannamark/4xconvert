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
 * Base {@link com.opensymphony.xwork2.Action} class for ownership management
 * sub-classes.
 * 
 * @author Denis G. Krylov
 * 
 */
public abstract class AbstractManageOwnershipAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger
            .getLogger(AbstractManageOwnershipAction.class);
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
     * @param trialId
     *            the trialId to set
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
     * @param isSelected
     *            the isSelected to set
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
     * @param regUserId
     *            the regUserId to set
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
     * @param isOwner
     *            the isOwner to set
     */
    public void setOwner(boolean isOwner) {
        this.owner = isOwner;
    }

    /**
     * load initial view.
     * 
     * @return String the action result.
     * @throws PAException
     *             the pa exception.
     */
    public String search() throws PAException {
        performSearch();
        return VIEW_RESULTS;
    }

    private void performSearch() throws PAException {        
        ServletActionContext.getRequest().getSession()
                .removeAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        ServletActionContext
                .getRequest()
                .getSession()
                .removeAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST);

        try {            
            RegistryUser loggedInUser = getRegistryUser();
            Long affiliatedOrgId = loggedInUser.getAffiliatedOrganizationId();
            getOrgMembers(affiliatedOrgId);
            getOrgTrials(affiliatedOrgId);
        } catch (PAException e) {
            LOG.error(e.getMessage());
            throw new PAException(e);
        }
    }
    
    /**
     * @return RegistryUser
     * @throws PAException PAException
     */
    protected RegistryUser getRegistryUser() throws PAException {
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        return PaRegistry.getRegistryUserService().getUser(loginName);
    }    

    private void getOrgMembers(Long affiliatedOrgId) throws PAException {
        RegistryUser criteria = new RegistryUser();
        criteria.setAffiliatedOrganizationId(affiliatedOrgId);
        List<RegistryUser> regUsers = PaRegistry.getRegistryUserService()
                .search(criteria);
        registryUsers.clear();
        for (RegistryUser user : regUsers) {
            SelectedRegistryUser selectedRegUser = new SelectedRegistryUser();
            selectedRegUser.setRegistryUser(user);
            registryUsers.add(selectedRegUser);
        }
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(AbstractManageOwnershipAction.REG_USERS_LIST,
                        registryUsers);
    }

    private void getOrgTrials(Long affiliatedOrgId) throws PAException {
        List<StudyProtocol> trials = getStudyProtocols(affiliatedOrgId);
        studyProtocols.clear();
        for (StudyProtocol sp : trials) {
            SelectedStudyProtocol selectedStudyProtocol = new SelectedStudyProtocol();
            selectedStudyProtocol.setStudyProtocol(sp);
            selectedStudyProtocol.setNciIdentifier(PADomainUtils
                    .getAssignedIdentifierExtension(sp));
            selectedStudyProtocol
                    .setLeadOrgId(PADomainUtils.getLeadOrgSpId(sp));
            studyProtocols.add(selectedStudyProtocol);
        }
        ServletActionContext
                .getRequest()
                .getSession()
                .setAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST,
                        studyProtocols);
    }

    /**
     * @param affiliatedOrgId
     *            affiliatedOrgId
     * @return List<StudyProtocol>
     * @throws PAException
     *             PAException
     */
    public abstract List<StudyProtocol> getStudyProtocols(Long affiliatedOrgId)
            throws PAException;

    /**
     * set if user is owner or not.
     * 
     * @throws PAException
     *             the pa exception
     */
    public void setRegUser() throws PAException {
        if (regUserId != null) {
            SelectedRegistryUser regUser = getRegUser(regUserId);
            if (regUser != null) {
                regUser.setSelected(owner);
                ServletActionContext
                        .getRequest()
                        .getSession()
                        .setAttribute(
                                AbstractManageOwnershipAction.REG_USERS_LIST,
                                registryUsers);
            }
        }
    }

    @SuppressWarnings("unchecked")    // NOPMD
    private SelectedRegistryUser getRegUser(Long rUserId) {
        SelectedRegistryUser regUser = null;
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        for (SelectedRegistryUser srUser : registryUsers) {
            if (rUserId.equals(srUser.getRegistryUser().getId())) {
                regUser = srUser;
            }
        }
        return regUser;
    }

    /**
     * set if trial is selected or not.
     * 
     * @throws PAException
     *             the pa exception
     */
    public void setTrial() throws PAException {
        if (trialId != null) {
            SelectedStudyProtocol studyProtocol = getStudyProtocol(trialId);
            if (studyProtocol != null) {
                studyProtocol.setSelected(selected);
                ServletActionContext
                        .getRequest()
                        .getSession()
                        .setAttribute(
                                AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST,
                                studyProtocols);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private SelectedStudyProtocol getStudyProtocol(Long tId) {
        SelectedStudyProtocol sp = null;
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext
                .getRequest()
                .getSession()
                .getAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST);
        for (SelectedStudyProtocol selSP : studyProtocols) {
            if (tId.equals(selSP.getStudyProtocol().getId())) {
                sp = selSP;
            }
        }
        return sp;
    }

    /**
     * view results from session..
     * 
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String view() {
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext
                .getRequest()
                .getSession()
                .getAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST);

        return VIEW_RESULTS;
    }

    /**
     * assign ownership.
     * 
     * @return String
     * @throws PAException
     * @throws PAException
     *             the pa exception
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
                        assignSuccessMsg());
            } else {
                ServletActionContext
                        .getRequest()
                        .setAttribute(
                                FAILURE_MSG,
                                "Please select at least one organizational member and one trial to assign ownership");
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    e.getMessage());
            throw new PAException(e);
        }
        return view();
    }

    /**
     * @return String
     */
    public abstract String assignSuccessMsg();

    /**
     * @return String
     */
    public abstract String unassignSuccessMsg();

    /**
     * Unassign ownership.
     * 
     * @return String
     * @throws PAException
     * @throws PAException
     *             the pa exception
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
                        unassignSuccessMsg());
            } else {
                ServletActionContext
                        .getRequest()
                        .setAttribute(
                                FAILURE_MSG,
                                "Please select at least one organizational member and one trial to unassign ownership");
            }
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    e.getMessage());
            throw new PAException(e);
        }

        return view();
    }

    /**
     * Updates ownership.
     * 
     * @param registryUserID
     *            userId
     * @param trialID
     *            tId
     * @param assign
     *            assign
     * @throws PAException
     *             PAException
     */
    public abstract void updateOwnership(Long registryUserID, Long trialID,
            boolean assign) throws PAException;

    @SuppressWarnings("unchecked")
    private List<Long> getSelectedUserIds() {
        List<Long> selectedUserIds = new ArrayList<Long>();
        registryUsers = (List<SelectedRegistryUser>) ServletActionContext
                .getRequest().getSession()
                .getAttribute(AbstractManageOwnershipAction.REG_USERS_LIST);
        for (SelectedRegistryUser user : registryUsers) {
            if (user.isSelected()) {
                selectedUserIds.add(user.getRegistryUser().getId());
            }
        }
        return selectedUserIds;
    }

    @SuppressWarnings("unchecked")
    private List<Long> getSelectedTrialIds() {
        List<Long> selectedTrialIds = new ArrayList<Long>();
        studyProtocols = (List<SelectedStudyProtocol>) ServletActionContext
                .getRequest()
                .getSession()
                .getAttribute(
                        AbstractManageOwnershipAction.STUDY_PROTOCOLS_LIST);
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
     * @param registryUsers
     *            the registryUsers to set
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
     * @param studyProtocols
     *            the studyProtocols to set
     */
    public void setStudyProtocols(List<SelectedStudyProtocol> studyProtocols) {
        this.studyProtocols = studyProtocols;
    }

}