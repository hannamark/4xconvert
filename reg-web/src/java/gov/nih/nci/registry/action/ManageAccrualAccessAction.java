/**
 * 
 */
package gov.nih.nci.registry.action;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentByTrialDTO;
import gov.nih.nci.pa.dto.AccrualAccessAssignmentHistoryDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.CodedEnum;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.action.ManageAccrualAccessAction.AccrualAccessModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ScopedModelDriven;

/**
 * @author Denis G. Krylov
 * 
 */
public class ManageAccrualAccessAction extends ActionSupport implements
        ScopedModelDriven<AccrualAccessModel>, Preparable {

  

    private static final Logger LOG = Logger
            .getLogger(ManageAccrualAccessAction.class);
    
    private static final String SUCCESS_MSG = "successMessage";
    private static final String FAILURE_MSG = "failureMessage";    

    private AccrualAccessModel model;

    private String scopeKey;

    private String trialCategory;
    
    private String comments;
    
    private String trialsToAssign;
    
    private String trialsToUnassign;

    private RegistryUser currentUser;

    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService;

    private ProtocolQueryServiceLocal protocolQueryService;

    private RegistryUserServiceLocal registryUserService;

    private PAOrganizationServiceRemote paOrganizationService;

    private Long userId;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String BY_TRIAL = "byTrial";
    
    private static final String HISTORY = "history";

    @Override
    public String execute() throws PAException {
        model.setTrialCategory(TrialCategory.ALL);

        RegistryUser criteria = new RegistryUser();
        criteria.setAffiliatedOrganizationId(currentUser
                .getAffiliatedOrganizationId());
        model.setUsers(sort(registryUserService.search(criteria)));
        model.setUser(null);

        loadTrials();

        return SUCCESS;
    }

    private List<RegistryUser> sort(List<RegistryUser> users) {
        Collections.sort(users, new Comparator<RegistryUser>() {
            @Override
            public int compare(RegistryUser u1, RegistryUser u2) {
                return StringUtils.defaultString(u1.getLastName()).compareTo(
                        StringUtils.defaultString(u2.getLastName()));
            }
        });
        return users;
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String change() throws PAException {
        model.setUser(registryUserService.getUserById(userId));
        model.setTrialCategory(TrialCategory.valueOf(trialCategory));
        loadTrials();
        return SUCCESS;
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignAll() throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (Collection<StudyProtocolHolder> trialList : model
                .getUnassignedTrials().values()) {
            for (StudyProtocolHolder trialHolder : trialList) {
                if (trialHolder.isSelectable()) {
                    trialIDs.add(trialHolder.getProtocolDTO()
                            .getStudyProtocolId());
                }
            }
        }
        return assign(trialIDs);
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String unassignAll() throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (Collection<StudyProtocolQueryDTO> trialList : model
                .getAssignedTrials().values()) {
            for (StudyProtocolQueryDTO trial : trialList) {
                trialIDs.add(trial.getStudyProtocolId());
            }
        }
        return unassign(trialIDs);
    }
    
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignSelected() throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (String trialIdStr : this.trialsToAssign.split(";")) {
            trialIDs.add(Long.parseLong(trialIdStr));
        }
        return assign(trialIDs);
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String unassignSelected() throws PAException {
        Collection<Long> trialIDs = new HashSet<Long>();
        for (String trialIdStr : this.trialsToUnassign.split(";")) {
            trialIDs.add(Long.parseLong(trialIdStr));
        }
        return unassign(trialIDs);
    }
    

    /**
     * @param trialIDs
     * @return
     * @throws PAException
     */
    private String assign(Collection<Long> trialIDs) throws PAException {
        try {
            studySiteAccrualAccessService.assignTrialLevelAccrualAccess(
                    model.getUser(), trialIDs, comments, currentUser);
            ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                    getText("manage.accrual.access.trialsAssigned"));
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    getText("manage.accrual.access.error"));
        }
        return change();
    }
    
    /**
     * @param trialIDs
     * @return
     * @throws PAException
     */
    private String unassign(Collection<Long> trialIDs) throws PAException {
        try {
            studySiteAccrualAccessService.unassignTrialLevelAccrualAccess(
                    model.getUser(), trialIDs, comments, currentUser);
            ServletActionContext.getRequest().setAttribute(SUCCESS_MSG,
                    getText("manage.accrual.access.trialsUnassigned"));
        } catch (PAException e) {
            LOG.error(e, e);
            ServletActionContext.getRequest().setAttribute(FAILURE_MSG,
                    getText("manage.accrual.access.error"));
        }
        return change();
    }

    private void loadTrials() throws PAException {
        model.getAssignedTrials().clear();
        model.getUnassignedTrials().clear();
        if (currentUser.getAffiliatedOrganizationId() != null
                && model.getUser() != null) {
            List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
            // determine lead org's internal ID
            Organization org = findCurrentUsersOrg();
            loadInstitutionalPeerReviewedTrials(list, org);
            loadIndustrialTrials(list, org);
            filterOutNationalTrials(list);
            loadTrialsIntoModel(list);
        }
    }

    /**
     * @param list
     * @throws PAException
     */
    private void loadTrialsIntoModel(List<StudyProtocolQueryDTO> list)
            throws PAException {
        List<Long> trialIDs = studySiteAccrualAccessService
                .getActiveTrialLevelAccrualAccess(model.getUser());
        for (StudyProtocolQueryDTO trial : list) {
            if (trialIDs.contains(trial.getStudyProtocolId())) {
                addToAssigned(model.getAssignedTrials(), trial);
            } else {
                addToUnassigned(model.getUnassignedTrials(), trial);
            }
        }
    }

    /**
     * @param list
     */
    private void filterOutNationalTrials(List<StudyProtocolQueryDTO> list) {
        CollectionUtils.filter(list, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                StudyProtocolQueryDTO trial = (StudyProtocolQueryDTO) arg0;
                return StringUtils.isNotBlank(trial
                        .getSummary4FundingSponsorType())
                        && !SummaryFourFundingCategoryCode.NATIONAL.name()
                                .equals(trial
                                        .getSummary4FundingSponsorType());
            }
        });
    }

    /**
     * @param list
     * @param org
     * @throws PAException
     */
    private void loadIndustrialTrials(List<StudyProtocolQueryDTO> list,
            Organization org) throws PAException {
        if (TrialCategory.ALL == model.trialCategory
                || TrialCategory.INDUSTRIAL == model.trialCategory) {
            StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
            queryCriteria.setOrganizationType("Participating Site");
            queryCriteria
                    .setParticipatingSiteIds(Arrays.asList(org.getId()));
            queryCriteria.setTrialCategory("p");
            list.addAll(protocolQueryService
                    .getStudyProtocolByCriteria(queryCriteria));
        }
    }

    /**
     * @param list
     * @param org
     * @throws PAException
     */
    private void loadInstitutionalPeerReviewedTrials(
            List<StudyProtocolQueryDTO> list, Organization org)
            throws PAException {
        if (TrialCategory.ALL == model.trialCategory
                || TrialCategory.INSTITUTIONAL_PEER_REVIEWED == model.trialCategory) {
            StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
            queryCriteria
                    .setLeadOrganizationIds(Arrays.asList(org.getId()));
            queryCriteria.setTrialCategory("n");
            list.addAll(protocolQueryService
                    .getStudyProtocolByCriteria(queryCriteria));
        }
    }

    /**
     * @return
     * @throws PAException
     */
    private Organization findCurrentUsersOrg() throws PAException {
        Organization orgCriteria = new Organization();
        orgCriteria.setIdentifier(currentUser.getAffiliatedOrganizationId()
                .toString());
        Organization org = paOrganizationService
                .getOrganizationByIndetifers(orgCriteria);
        return org;
    }

    private void addToAssigned(Map<String, List<StudyProtocolQueryDTO>> map,
            StudyProtocolQueryDTO trial) {
        final String summary4FundingSponsorType = trial.isProprietaryTrial() ? SummaryFourFundingCategoryCode.INDUSTRIAL
                .name() : trial.getSummary4FundingSponsorType();
        if (StringUtils.isNotBlank(summary4FundingSponsorType)) {
            String code = SummaryFourFundingCategoryCode.valueOf(
                    summary4FundingSponsorType).getCode();
            List<StudyProtocolQueryDTO> list = map.get(code);
            if (list == null) {
                list = new ArrayList<StudyProtocolQueryDTO>();
                map.put(code, list);
            }
            list.add(trial);
        }
    }
    
    private void addToUnassigned(Map<String, List<StudyProtocolHolder>> map,
            StudyProtocolQueryDTO trial) {
        boolean selectable = trial.getDocumentWorkflowStatusCode()
                .isEligibleForAccrual();
        final String summary4FundingSponsorType = trial.isProprietaryTrial() ? SummaryFourFundingCategoryCode.INDUSTRIAL
                .name() : trial.getSummary4FundingSponsorType();
        if (StringUtils.isNotBlank(summary4FundingSponsorType)) {
            String code = SummaryFourFundingCategoryCode.valueOf(
                    summary4FundingSponsorType).getCode();
            List<StudyProtocolHolder> list = map.get(code);
            if (list == null) {
                list = new ArrayList<StudyProtocolHolder>();
                map.put(code, list);
            }
            list.add(new StudyProtocolHolder(trial, selectable));
        }
    }
    
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignmentHistory() throws PAException {
        model.setHistory(studySiteAccrualAccessService.getAccrualAccessAssignmentHistory());
        return HISTORY;
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    public String assignmentByTrial() throws PAException {
        model.getByTrial().clear();
        List<AccrualAccessAssignmentByTrialDTO> list = studySiteAccrualAccessService
                .getAccrualAccessAssignmentByTrial();
        for (AccrualAccessAssignmentByTrialDTO dto : list) {
            SummaryFourFundingCategoryCode code = dto.getCategoryCode();
            List<AccrualAccessAssignmentByTrialDTO> trials = model.getByTrial()
                    .get(code);
            if (trials == null) {
                trials = new ArrayList<AccrualAccessAssignmentByTrialDTO>();
                model.getByTrial().put(code, trials);
            }
            trials.add(dto);
        }
        return BY_TRIAL;
    }
    

    
    /**
     * @return String
     *  
     */
    public String historyPaging() {        
        return HISTORY;
    }
    
    /**
     * @return String
     *  
     */
    public String byTrialPaging() {        
        return BY_TRIAL;
    }
    


    /**
     * @return Collection
     */
    public Collection<TrialCategory> getTrialCategoryList() {
        return Arrays.asList(TrialCategory.values());
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    public static final class AccrualAccessModel implements Serializable {

        private TrialCategory trialCategory;

        private List<RegistryUser> users = new ArrayList<RegistryUser>();

        private RegistryUser user = new RegistryUser();

        private Map<String, List<StudyProtocolQueryDTO>> assignedTrials = 
                new LinkedHashMap<String, List<StudyProtocolQueryDTO>>();

        private Map<String, List<StudyProtocolHolder>> unassignedTrials = 
                new LinkedHashMap<String, List<StudyProtocolHolder>>();
        
        private List<AccrualAccessAssignmentHistoryDTO> history = new ArrayList<AccrualAccessAssignmentHistoryDTO>();
        
        private Map<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>> byTrial = 
                new TreeMap<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>>();

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * @return the trialCategory
         */
        public TrialCategory getTrialCategory() {
            return trialCategory;
        }

        /**
         * @param trialCategory
         *            the trialCategory to set
         */
        public void setTrialCategory(TrialCategory trialCategory) {
            this.trialCategory = trialCategory;
        }

        /**
         * @return the users
         */
        public List<RegistryUser> getUsers() {
            return users;
        }

        /**
         * @param users
         *            the users to set
         */
        public void setUsers(List<RegistryUser> users) {
            this.users = users;
        }

        /**
         * @return the user
         */
        public RegistryUser getUser() {
            return user;
        }

        /**
         * @param user
         *            the user to set
         */
        public void setUser(RegistryUser user) {
            this.user = user;
        }

        /**
         * @return the assignedTrials
         */
        public Map<String, List<StudyProtocolQueryDTO>> getAssignedTrials() {
            return assignedTrials;
        }

        /**
         * @param assignedTrials
         *            the assignedTrials to set
         */
        public void setAssignedTrials(
                Map<String, List<StudyProtocolQueryDTO>> assignedTrials) {
            this.assignedTrials = assignedTrials;
        }

        /**
         * @return the unassignedTrials
         */
        public Map<String, List<StudyProtocolHolder>> getUnassignedTrials() {
            return unassignedTrials;
        }

        /**
         * @param unassignedTrials
         *            the unassignedTrials to set
         */
        public void setUnassignedTrials(
                Map<String, List<StudyProtocolHolder>> unassignedTrials) {
            this.unassignedTrials = unassignedTrials;
        }
        
        /**
         * @return boolean
         */
        public boolean isHasAssignableTrials() {
            for (Collection<StudyProtocolHolder> trialList : getUnassignedTrials()
                    .values()) {
                for (StudyProtocolHolder trialHolder : trialList) {
                    if (trialHolder.isSelectable()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * @return the history
         */
        public List<AccrualAccessAssignmentHistoryDTO> getHistory() {
            return history;
        }

        /**
         * @param history the history to set
         */
        public void setHistory(List<AccrualAccessAssignmentHistoryDTO> history) {
            this.history = history;
        }

        /**
         * @return the byTrial
         */
        public Map<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>> getByTrial() {
            return byTrial;
        }

        /**
         * @param byTrial the byTrial to set
         */
        public void setByTrial(
                Map<SummaryFourFundingCategoryCode, List<AccrualAccessAssignmentByTrialDTO>> byTrial) {
            this.byTrial = byTrial;
        }

    }
    
    /**
     * @author Denis G. Krylov
     *
     */
    public static final class StudyProtocolHolder implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private StudyProtocolQueryDTO protocolDTO;
        private boolean selectable = true;
        
        
        
        /**
         * @param protocolDTO protocolDTO
         * @param selectable selectable
         */
        public StudyProtocolHolder(StudyProtocolQueryDTO protocolDTO,
                boolean selectable) {
            this.protocolDTO = protocolDTO;
            this.selectable = selectable;
        }
        /**
         * @return the protocolDTO
         */
        public StudyProtocolQueryDTO getProtocolDTO() {
            return protocolDTO;
        }
        /**
         * @param protocolDTO the protocolDTO to set
         */
        public void setProtocolDTO(StudyProtocolQueryDTO protocolDTO) {
            this.protocolDTO = protocolDTO;
        }
        /**
         * @return the selectable
         */
        public boolean isSelectable() {
            return selectable;
        }
        /**
         * @param selectable the selectable to set
         */
        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }
        
        
        
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    public static enum TrialCategory implements CodedEnum<String> {
        /**
         * 
         */
        ALL("All"),
        /**
         * 
         */
        INSTITUTIONAL_PEER_REVIEWED("Institutional/Externally Peer Reviewed"),
        /**
         * 
         */
        INDUSTRIAL("Industrial");

        private String code;

        /**
         * Constructor for StatusCode.
         * 
         * @param code
         */
        private TrialCategory(String code) {
            this.code = code;
            register(this);
        }

        /**
         * @return code coded value of enum
         */
        @Override
        public String getCode() {
            return code;
        }

        /**
         * @return String DisplayName
         */
        @Override
        public String getDisplayName() {
            return sentenceCasedName(this);
        }

        /**
         * @return String display name
         */
        public String getName() {
            return name();
        }

        /**
         * @param code
         *            code
         * @return StatusCode
         */
        public static ActiveInactiveCode getByCode(String code) {
            return getByClassAndCode(ActiveInactiveCode.class, code);
        }

        /**
         * construct a array of display names for Abstracted Status coded Enum.
         * 
         * @return String[] display names for Abstracted Status Code
         */
        public static String[] getDisplayNames() {
            ActiveInactiveCode[] absStatusCodes = ActiveInactiveCode.values();
            String[] codedNames = new String[absStatusCodes.length];
            for (int i = 0; i < absStatusCodes.length; i++) {
                codedNames[i] = absStatusCodes[i].getCode();
            }
            return codedNames;
        }

    }

    @Override
    public AccrualAccessModel getModel() {
        return model;
    }

    @Override
    public void prepare() throws PAException {
        setRegistryUserService(PaRegistry.getRegistryUserService());
        setStudySiteAccrualAccessService(PaRegistry
                .getStudySiteAccrualAccessService());
        setProtocolQueryService(PaRegistry.getProtocolQueryService());
        setPaOrganizationService(PaRegistry.getPAOrganizationService());
        currentUser = registryUserService.getUser(UsernameHolder.getUser());
    }

    @Override
    public String getScopeKey() {
        return scopeKey;
    }

    @Override
    public void setModel(AccrualAccessModel arg0) {
        this.model = arg0;
    }

    @Override
    public void setScopeKey(String arg0) {
        this.scopeKey = arg0;
    }

    /**
     * @param studySiteAccrualAccessService
     *            the studySiteAccrualAccessService to set
     */
    public void setStudySiteAccrualAccessService(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessService) {
        this.studySiteAccrualAccessService = studySiteAccrualAccessService;
    }

    /**
     * @return the trialCategory
     */
    public String getTrialCategory() {
        return trialCategory;
    }

    /**
     * @param trialCategory
     *            the trialCategory to set
     */
    public void setTrialCategory(String trialCategory) {
        this.trialCategory = trialCategory;
    }

    /**
     * @param registryUserService
     *            the registryUserService to set
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @param protocolQueryService
     *            the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param paOrganizationService
     *            the paOrganizationService to set
     */
    public void setPaOrganizationService(
            PAOrganizationServiceRemote paOrganizationService) {
        this.paOrganizationService = paOrganizationService;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the studiesToAssign
     */
    public String getTrialsToAssign() {
        return trialsToAssign;
    }

    /**
     * @param studiesToAssign the studiesToAssign to set
     */
    public void setTrialsToAssign(String studiesToAssign) {
        this.trialsToAssign = studiesToAssign;
    }

    /**
     * @return the trialsToUnassign
     */
    public String getTrialsToUnassign() {
        return trialsToUnassign;
    }

    /**
     * @param trialsToUnassign the trialsToUnassign to set
     */
    public void setTrialsToUnassign(String trialsToUnassign) {
        this.trialsToUnassign = trialsToUnassign;
    }

}
