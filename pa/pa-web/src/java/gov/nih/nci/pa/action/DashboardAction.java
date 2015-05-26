/**
 * 
 */
package gov.nih.nci.pa.action; // NOPMD

import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_QC_COMPLETE;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_QC_START;
import static gov.nih.nci.pa.enums.MilestoneCode.ADMINISTRATIVE_READY_FOR_QC;
import static gov.nih.nci.pa.enums.MilestoneCode.READY_FOR_TSR;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE;
import static gov.nih.nci.pa.enums.MilestoneCode.SCIENTIFIC_READY_FOR_QC;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_ACCEPTED;
import static gov.nih.nci.pa.enums.MilestoneCode.SUBMISSION_RECEIVED;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.util.Constants.IS_ADMIN_ABSTRACTOR;
import static gov.nih.nci.pa.util.Constants.IS_SCIENTIFIC_ABSTRACTOR;
import static gov.nih.nci.pa.util.Constants.IS_SU_ABSTRACTOR;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyOnholdServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.search.StudyProtocolOptions.MilestoneFilter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Denis G. Krylov
 * 
 */


@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods",
        "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class DashboardAction extends AbstractCheckInOutAction implements Preparable,
        ServletRequestAware {

    private static final String DASHBOARD_TITLE = "dashboardTitle";
    private static final String NCT_IDENTIFIER = "nctIdentifier";
    private static final String SUMMARY_DTO = "summaryDTO";
    private static final String QUERY_DTO = "queryDTO";
    private static final String NON_ABSTRACTOR_LANDING = "nonAbstractorLanding";
    private static final String SU_ABSTRACTOR_LANDING = "suAbstractorLanding";
    private static final String ABSTRACTOR_LANDING = "abstractorLanding";
    private static final String DASHBOARD_SEARCH_RESULTS = "dashboardSearchResults";

    private static final Logger LOG = Logger.getLogger(DashboardAction.class);

    private static final String LAST = "last";
    private static final String ANY = "any";

    private static final long serialVersionUID = 8458441253215157815L;

    private static final Long ANYONE = -1L;
    private static final Long NOONE = -2L;
    private static final Long ME = -3L;

    private static final String TOGGLE_RESULTS_TAB = "toggleResultsTab";
    private static final String TOGGLE_DETAILS_TAB = "toggleDetailsTab";

    private HttpServletRequest request;

    private ProtocolQueryServiceLocal protocolQueryService;
    private InterventionServiceLocal interventionService;
    private PDQDiseaseServiceLocal pdqDiseaseService;
    private StudyProtocolService studyProtocolService;    
    private StudyOnholdServiceLocal onholdService;
    private PAServiceUtils serviceUtils = new PAServiceUtils();

    // fields that capture search criteria
    private Long checkedOutBy;
    private Long assignee;
    private List<String> processingPriority = new ArrayList<String>();
    private String submittedOnOrAfter;
    private String submittedOnOrBefore;
    private String submittingOrgId;
    private String submittedBy;
    private List<String> submissionType = new ArrayList<String>();
    private String nciSponsored;
    private List<String> onHoldStatus = new ArrayList<String>();
    private String ctepDcpCategory;
    private List<String> onHoldReason = new ArrayList<String>();
    private String milestoneType;
    private String milestone;
    private List<String> processingStatus = new ArrayList<String>();
    private List<String> anatomicSites = new ArrayList<String>();
    private List<String> interventions = new ArrayList<String>();
    private List<String> diseases = new ArrayList<String>();
    private Boolean adminAbstraction;
    private Boolean adminQC;
    private Boolean scientificAbstraction;
    private Boolean scientificQC;
    private Boolean readyForTSR;
    private Boolean submittedUnaccepted;
    
    // Details tab updatable fields
    private Long assignedTo;
    private String newProcessingPriority;
    private String processingComments;
    

    private List<String> checkoutCommands = new ArrayList<String>();
    private Map<String, String> onHoldValuesMap = new HashMap<String, String>();

    @Override
    public String execute() {
        clearSearchSessionAttributes();
       
        if (!canAccessDashboard()) {
            return NON_ABSTRACTOR_LANDING;
        }
        if (isInRole(IS_SU_ABSTRACTOR)) {
            return determineLandingPage();
        } else {
            try {
                return search(buildCriteriaByUserRole().toArray(
                        new StudyProtocolQueryCriteria[0]));
            } catch (PAException e) {
                LOG.error(e, e);
                request.setAttribute(Constants.FAILURE_MESSAGE,
                        e.getLocalizedMessage());
                return determineLandingPage();
            }
        }
        
    }
    
    /**
     *  Set values for onHold Drop down
     */
    private void setOnHoldDisplayValues()  {
        try {
        OnholdReasonCode [] keys =  OnholdReasonCode.values();
        for (OnholdReasonCode key :keys) {
            String value = onholdService.getReasonCategoryValue(key.getName());
            onHoldValuesMap.put(key.getCode(), key.getCode() + " (" + value + ")");
        }
        } catch (Exception e) {
            LOG.error("Error in setting on hold values " + e.getMessage());
        }
        
    }

    private List<StudyProtocolQueryCriteria> buildCriteriaByUserRole()
            throws PAException {
        // need two criterias here because
        // gov.nih.nci.pa.service.search.StudyProtocolQueryBeanSearchCriteria
        // does not support disjunction (OR) very well. Making it do so is
        // somewhat messy...

        // First.
        setCheckedOutBy(CSMUserService.getInstance()
                .getCSMUser(UsernameHolder.getUser()).getUserId());
        final StudyProtocolQueryCriteria criteriaOne = buildCriteria();
        criteriaOne.setExcludeRejectProtocol(true);
        criteriaOne.setExcludeTerminatedTrials(true);
        setCheckedOutBy(null);

        // Second.
        if (isInRole(IS_ADMIN_ABSTRACTOR)) {
            setAdminAbstraction(true);
            setAdminQC(true);
            setSubmittedUnaccepted(true);
        }
        if (isInRole(IS_SCIENTIFIC_ABSTRACTOR)) {
            setScientificAbstraction(true);
            setScientificQC(true);
        }
        setReadyForTSR(true);
        final StudyProtocolQueryCriteria criteriaTwo = buildCriteria();
        criteriaTwo.setExcludeRejectProtocol(true);
        criteriaTwo.setExcludeTerminatedTrials(true);
        return Arrays.asList(criteriaOne, criteriaTwo);
    }

    private boolean canAccessDashboard() {
        return isInRole(IS_SU_ABSTRACTOR) || isInRole(IS_ADMIN_ABSTRACTOR)
                || isInRole(IS_SCIENTIFIC_ABSTRACTOR);
    }

    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(request.getSession().getAttribute(roleFlag));
    }

    /**
     * 
     */
    private void clearSearchSessionAttributes() {
        final HttpSession session = request.getSession();
        session.removeAttribute(DASHBOARD_SEARCH_RESULTS);
        session.removeAttribute(QUERY_DTO);
        session.removeAttribute(SUMMARY_DTO);
        session.removeAttribute(Constants.TRIAL_SUMMARY);
        session.removeAttribute(Constants.STUDY_PROTOCOL_II);
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String search() throws PAException {
        clearSearchSessionAttributes();
        if (!canAccessDashboard()) {
            return NON_ABSTRACTOR_LANDING;
        }
        
        try {
            StudyProtocolQueryCriteria criteria = buildCriteria();
            return search(criteria);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return determineLandingPage();
    }

    /**
     * @param criteria
     * @return
     */
    private String search(StudyProtocolQueryCriteria... criteriaList) {
        try {
            List<StudyProtocolQueryDTO> results = new ArrayList<StudyProtocolQueryDTO>();
            for (StudyProtocolQueryCriteria criteria : criteriaList) {
                List<StudyProtocolQueryDTO> currentResults = protocolQueryService
                        .getStudyProtocolByCriteria(criteria,
                                SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO,
                                SKIP_OTHER_IDENTIFIERS);
                protocolQueryService.populateMilestoneHistory(currentResults);
                results.addAll(currentResults);
            }
            eliminateDupes(results);
            request.getSession()
                    .setAttribute(DASHBOARD_SEARCH_RESULTS, results);
            toggleResultsTab();
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return determineLandingPage();
    }

    private void eliminateDupes(List<StudyProtocolQueryDTO> trials) {
        TreeSet<StudyProtocolQueryDTO> set = new TreeSet<StudyProtocolQueryDTO>(
                new Comparator<StudyProtocolQueryDTO>() {
                    @Override
                    public int compare(StudyProtocolQueryDTO dto1,
                            StudyProtocolQueryDTO dto2) {
                        return dto1.getStudyProtocolId().compareTo(
                                dto2.getStudyProtocolId());
                    }
                });
        set.addAll(trials);
        trials.clear();
        trials.addAll(set);
    }

    /**
     * @return
     */
    private String determineLandingPage() {
        if (isInRole(IS_SU_ABSTRACTOR)) {
            return SU_ABSTRACTOR_LANDING;
        } else {
            return ABSTRACTOR_LANDING;
        }
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    @Override
    public String view() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setStudyProtocolId(getStudyProtocolId());
        try {
            StudyProtocolQueryDTO queryDTO = protocolQueryService
                    .getStudyProtocolByCriteria(criteria).get(0);
            final HttpSession session = request.getSession();
            session.setAttribute(QUERY_DTO, queryDTO);

            StudyProtocolQueryDTO summaryDTO = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(getStudyProtocolId());
            session.setAttribute(SUMMARY_DTO, summaryDTO);
            session.setAttribute(NCT_IDENTIFIER, getServiceUtils()
                    .getStudyIdentifier(IiConverter
                            .convertToStudyProtocolIi(getStudyProtocolId()),
                            PAConstants.NCT_IDENTIFIER_TYPE));
            session.setAttribute(Constants.TRIAL_SUMMARY, summaryDTO);
            session.setAttribute(Constants.STUDY_PROTOCOL_II,
                    IiConverter.convertToStudyProtocolIi(getStudyProtocolId()));

            checkoutCommands = new ArrayList<>();
            ActionUtils.setCheckoutCommands(summaryDTO, checkoutCommands);
            ActionUtils.runTrialStatusTransitionValidations(summaryDTO, session);

            toggleDetailsTab();
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return determineLandingPage();
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public String save() throws PAException {
        try {
            StudyProtocolDTO studyDTO = studyProtocolService
                    .getStudyProtocol(IiConverter.convertToIi(getStudyProtocolId()));
            final Ii assignedUser = studyDTO.getAssignedUser();
            final Ii newAssignedUser = IiConverter.convertToIi(assignedTo);

            studyDTO.setComments(StConverter.convertToSt(processingComments));
            if (isInRole(IS_SU_ABSTRACTOR)) {
                studyDTO.setProcessingPriority(IntConverter
                        .convertToInt(newProcessingPriority));
                studyDTO.setAssignedUser(newAssignedUser);
            }
            studyProtocolService.updateStudyProtocol(studyDTO);

            // if assigned user's changed, check out the trial in that name.
            if (isInRole(IS_SU_ABSTRACTOR)
                    && ((ISOUtil.isIiNull(assignedUser) && !ISOUtil
                            .isIiNull(newAssignedUser))
                            || (!ISOUtil.isIiNull(assignedUser) && ISOUtil
                                    .isIiNull(newAssignedUser)) || (!ISOUtil
                            .isIiNull(assignedUser)
                            && !ISOUtil.isIiNull(newAssignedUser) && !assignedUser
                            .getExtension().equals(
                                    newAssignedUser.getExtension())))) {
                getStudyCheckoutService().handleTrialAssigneeChange(getStudyProtocolId());
            }
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    getText("dashboard.save.success"));
            return view();
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return determineLandingPage();
    }

    /**
     * @return loopback
     */
    public String loopback() {
        toggleResultsTab();
        return determineLandingPage();
    }

    /**
     * 
     */
    private void toggleResultsTab() {
        request.setAttribute(TOGGLE_RESULTS_TAB, true);
    }

    private void toggleDetailsTab() {
        request.setAttribute(TOGGLE_DETAILS_TAB, true);
    }

    private StudyProtocolQueryCriteria buildCriteria() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setDocumentWorkflowStatusCodes(processingStatus);
        criteria.setProcessingPriority(processingPriority);
        criteria.setCtepDcpCategory(ctepDcpCategory);
        criteria.setSummary4AnatomicSitesAsStrings(anatomicSites);
        criteria.setInterventionIdsAsStrings(interventions);
        criteria.setPdqDiseasesAsStrings(diseases);
        for (String code : submissionType) {
            criteria.getTrialSubmissionTypes().add(
                    SubmissionTypeCode.getByCode(code));
        }
        for (String code : onHoldReason) {
            criteria.getOnholdReasons().add(OnholdReasonCode.getByCode(code));
        }
        criteria.setSubmitterAffiliateOrgId(submittingOrgId);
        criteria.setNciSponsored(StringUtils.isBlank(nciSponsored) ? null
                : "true".equals(nciSponsored));
        buildCheckOutCriteria(criteria);
        buildAssigneeCriteria(criteria);
        buildSubmissionTimelineCriteria(criteria);
        buildOnHoldCriteria(criteria);
        buildMilestoneCriteria(criteria);
        return criteria;
    }

    /**
     * @param criteria
     */
    @SuppressWarnings({ "PMD.NPathComplexity", "unchecked" })
    private void buildMilestoneCriteria(StudyProtocolQueryCriteria criteria) {
        
        if (ANY.equalsIgnoreCase(milestoneType)
                && StringUtils.isNotBlank(milestone)) {
            criteria.setCurrentOrPreviousMilestone(MilestoneCode
                    .getByCode(milestone));
        }
        if (LAST.equalsIgnoreCase(milestoneType)
                && StringUtils.isNotBlank(milestone)) {
            criteria.getStudyMilestone().add(milestone);
        }

        final List<MilestoneFilter> filter = criteria.getMilestoneFilters();
        if (Boolean.TRUE.equals(scientificAbstraction)) {
            filter.add(new MilestoneFilter(Arrays.asList(SUBMISSION_ACCEPTED,
                    ADMINISTRATIVE_PROCESSING_START_DATE,
                    ADMINISTRATIVE_PROCESSING_COMPLETED_DATE,
                    ADMINISTRATIVE_READY_FOR_QC, ADMINISTRATIVE_QC_START,
                    ADMINISTRATIVE_QC_COMPLETE), Arrays
                    .asList(SCIENTIFIC_PROCESSING_START_DATE)));
        }
        if (Boolean.TRUE.equals(adminAbstraction)) {
            filter.add(new MilestoneFilter(Arrays.asList(SUBMISSION_ACCEPTED),
                    ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(adminQC)) {
            filter.add(new MilestoneFilter(Arrays
                    .asList(ADMINISTRATIVE_READY_FOR_QC), ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(readyForTSR)) {
            filter.add(new MilestoneFilter(Arrays.asList(READY_FOR_TSR),
                    ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(submittedUnaccepted)) {
            filter.add(new MilestoneFilter(Arrays.asList(SUBMISSION_RECEIVED),
                    ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(scientificQC)) {
            filter.add(new MilestoneFilter(Arrays
                    .asList(SCIENTIFIC_READY_FOR_QC), ListUtils.EMPTY_LIST));
        }
        if (Boolean.TRUE.equals(scientificAbstraction) 
             || Boolean.TRUE.equals(adminAbstraction)
             || Boolean.TRUE.equals(adminQC)
             || Boolean.TRUE.equals(scientificQC)) {
                  criteria.setExcludeRejectProtocol(true);
        }
    }

    /**
     * @param criteria
     */
    private void buildOnHoldCriteria(StudyProtocolQueryCriteria criteria) {
        if (onHoldStatus.contains(PAConstants.ON_HOLD)) {
            criteria.setHoldStatus(PAConstants.ON_HOLD);
        }
        if (onHoldStatus.contains(PAConstants.NOT_ON_HOLD)) {
            criteria.setHoldStatus(PAConstants.NOT_ON_HOLD);
        }
        if (onHoldStatus.contains(PAConstants.ON_HOLD_ANYTIME)) {
            criteria.setHoldRecordExists(true);
        }
    }

    /**
     * @param criteria
     * @throws PAException 
     */
    private void buildSubmissionTimelineCriteria(
            StudyProtocolQueryCriteria criteria) throws PAException {
        final Date onOrAfter = PAUtil
                        .dateStringToDateTime(submittedOnOrAfter);
        final Date onOrBefore = PAUtil.endOfDay(PAUtil
                .dateStringToDateTime(submittedOnOrBefore));
        criteria.setSubmittedOnOrAfter(onOrAfter);       
        criteria.setSubmittedOnOrBefore(onOrBefore);
        
        if (onOrAfter != null && onOrBefore != null
                && onOrAfter.after(onOrBefore)) {
            throw new PAException(
                    "Submission timeline dates are inconsistent and will never produce results. "
                            + "Please correct");
        }
    }

    /**
     * @param criteria
     * @throws PAException
     */
    private void buildCheckOutCriteria(StudyProtocolQueryCriteria criteria)
            throws PAException {
        if (checkedOutBy != null) {
            if (ANYONE.equals(checkedOutBy)) {
                criteria.setCheckedOut(true);
            } else if (NOONE.equals(checkedOutBy)) {
                criteria.setCheckedOut(false);
            } else {
                criteria.setStudyLockedBy(true);
                criteria.setUserLastCreated(getUserIdentifier(checkedOutBy));
            }
        }
    }
    
    /**
     * @param criteria
     * @throws PAException
     */
    private void buildAssigneeCriteria(StudyProtocolQueryCriteria criteria)
            throws PAException {
        if (assignee != null) {
            if (ME.equals(assignee)) {     
                criteria.setAssignedUserId(CSMUserService.getInstance()
                        .getCSMUser(UsernameHolder.getUser()).getUserId());
            } else {
                criteria.setAssignedUserId(assignee);
            }
        }
    }

    private String getUserIdentifier(long userId) throws PAException {
        return CSMUserService.getInstance().getCSMUserById(userId)
                .getLoginName();
    }

    /**
     * @param servletRequest
     *            the servletRequest to set
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
    }

    @Override
    public void prepare() {
        ActionUtils.setUserRolesInSession(request);
        determineProperPageTitle();
        protocolQueryService = PaRegistry.getProtocolQueryService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        interventionService = PaRegistry.getInterventionService();
        pdqDiseaseService = PaRegistry.getDiseaseService();
        onholdService = PaRegistry.getStudyOnholdService();
        setStudyCheckoutService(PaRegistry.getStudyCheckoutService());
        setOnHoldDisplayValues();
    }

    private void determineProperPageTitle() {
        if (isInRole(IS_ADMIN_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.admin"));
        }
        if (isInRole(IS_SCIENTIFIC_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.scientific"));
        }
        if (isInRole(IS_SCIENTIFIC_ABSTRACTOR) && isInRole(IS_ADMIN_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.adminAndScientific"));
        }
        if (isInRole(IS_SU_ABSTRACTOR)) {
            request.setAttribute(DASHBOARD_TITLE,
                    getText("dashboard.title.super"));
        }
    }

    /**
     * @return Map<String, String>
     * @throws PAException
     *             PAException
     */
    public Map<Long, String> getCheckedOutByList() throws PAException {
        Map<Long, String> map = new LinkedHashMap<Long, String>();
        map.put(ANYONE, getText("dashboard.anyone"));
        map.put(NOONE, getText("dashboard.noone"));
        map.putAll(CSMUserService.getInstance().getAbstractors());
        return map;
    }
    
    /**
     * @return Map<String, String>
     * @throws PAException
     *             PAException
     */
    public Map<Long, String> getAssigneeList() throws PAException {
        Map<Long, String> map = new LinkedHashMap<Long, String>();
        map.put(ME, getText("dashboard.me"));        
        map.putAll(CSMUserService.getInstance().getAbstractors());
        return map;
    }
    
    @Override
    public String adminCheckOut() throws PAException {
        super.adminCheckOut();
        return view();
    }
    
    @Override
    public String scientificCheckOut() throws PAException {
        super.scientificCheckOut();
        return view();
    }
    
    @Override
    public String adminAndScientificCheckOut() throws PAException {
        super.adminAndScientificCheckOut();
        if (!hasActionErrors()) {
            request.setAttribute(Constants.SUCCESS_MESSAGE,
                    getText("studyProtocol.trial.checkOut.adminAndScientific"));
        }
        return view();
    }
    
    @Override
    public String adminCheckIn() throws PAException {     
        super.adminCheckIn();
        return view();
    }
    
    @Override
    public String scientificCheckIn() throws PAException {
        super.scientificCheckIn();
        return view();
    }
    
    @Override
    public String adminAndScientificCheckIn() throws PAException {        
        super.adminAndScientificCheckIn();
        return view();
    }
    
    /**
     * @return List<InterventionWebDTO>
     * @throws PAException  PAException
     */
    @SuppressWarnings("deprecation")
    public List<InterventionDTO> getInterventionsList() throws PAException {
        final List<InterventionDTO> list = new ArrayList<InterventionDTO>();
        if (getInterventions() != null) {
            for (String id : getInterventions()) {
                list.add(interventionService.get(IiConverter.convertToIi(id)));
            }
        }
        return list;
    }
    
    /**
     * @return List<DiseaseWebDTO>
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public List<DiseaseWebDTO> getDiseasesList() throws PAException {
        final List<DiseaseWebDTO> list = new ArrayList<DiseaseWebDTO>();
        if (getDiseases() != null) {
            for (String id : getDiseases()) {
                list.add(new DiseaseWebDTO(pdqDiseaseService.get(IiConverter
                        .convertToIi(id))));
            }
        }
        return list;
    }

    /**
     * @return the checkedOutBy
     */
    public Long getCheckedOutBy() {
        return checkedOutBy;
    }

    /**
     * @param checkedOutBy
     *            the checkedOutBy to set
     */
    public void setCheckedOutBy(Long checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    /**
     * @return the submittedOnOrAfter
     */
    public String getSubmittedOnOrAfter() {
        return submittedOnOrAfter;
    }

    /**
     * @param submittedOnOrAfter
     *            the submittedOnOrAfter to set
     */
    public void setSubmittedOnOrAfter(String submittedOnOrAfter) {
        this.submittedOnOrAfter = submittedOnOrAfter;
    }

    /**
     * @return the submittedOnOrBefore
     */
    public String getSubmittedOnOrBefore() {
        return submittedOnOrBefore;
    }

    /**
     * @param submittedOnOrBefore
     *            the submittedOnOrBefore to set
     */
    public void setSubmittedOnOrBefore(String submittedOnOrBefore) {
        this.submittedOnOrBefore = submittedOnOrBefore;
    }

    /**
     * @return the submittingOrgId
     */
    public String getSubmittingOrgId() {
        return submittingOrgId;
    }

    /**
     * @param submittingOrgId
     *            the submittingOrgId to set
     */
    public void setSubmittingOrgId(String submittingOrgId) {
        this.submittingOrgId = submittingOrgId;
    }

    /**
     * @return the submissionType
     */
    public List<String> getSubmissionType() {
        return submissionType;
    }

    /**
     * @param submissionType
     *            the submissionType to set
     */
    public void setSubmissionType(List<String> submissionType) {
        this.submissionType = submissionType;
    }

    /**
     * @return the nciSponsored
     */
    public String getNciSponsored() {
        return nciSponsored;
    }

    /**
     * @param nciSponsored
     *            the nciSponsored to set
     */
    public void setNciSponsored(String nciSponsored) {
        this.nciSponsored = nciSponsored;
    }

    /**
     * @return the onHoldStatus
     */
    public List<String> getOnHoldStatus() {
        return onHoldStatus;
    }

    /**
     * @param onHoldStatus
     *            the onHoldStatus to set
     */
    public void setOnHoldStatus(List<String> onHoldStatus) {
        this.onHoldStatus = onHoldStatus;
    }

    /**
     * @return the ctepDcpCategory
     */
    public String getCtepDcpCategory() {
        return ctepDcpCategory;
    }

    /**
     * @param ctepDcpCategory
     *            the ctepDcpCategory to set
     */
    public void setCtepDcpCategory(String ctepDcpCategory) {
        this.ctepDcpCategory = ctepDcpCategory;
    }

    /**
     * @return the onHoldReason
     */
    public List<String> getOnHoldReason() {
        return onHoldReason;
    }

    /**
     * @param onHoldReason
     *            the onHoldReason to set
     */
    public void setOnHoldReason(List<String> onHoldReason) {
        this.onHoldReason = onHoldReason;
    }

    /**
     * @return the milestoneType
     */
    public String getMilestoneType() {
        return milestoneType;
    }

    /**
     * @param milestoneType
     *            the milestoneType to set
     */
    public void setMilestoneType(String milestoneType) {
        this.milestoneType = milestoneType;
    }

    /**
     * @return the milestone
     */
    public String getMilestone() {
        return milestone;
    }

    /**
     * @param milestone
     *            the milestone to set
     */
    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

    /**
     * @return the processingStatus
     */
    public List<String> getProcessingStatus() {
        return processingStatus;
    }

    /**
     * @param processingStatus
     *            the processingStatus to set
     */
    public void setProcessingStatus(List<String> processingStatus) {
        this.processingStatus = processingStatus;
    }

    /**
     * @return the adminAbstraction
     */
    public Boolean getAdminAbstraction() {
        return adminAbstraction;
    }

    /**
     * @param adminAbstraction
     *            the adminAbstraction to set
     */
    public void setAdminAbstraction(Boolean adminAbstraction) {
        this.adminAbstraction = adminAbstraction;
    }

    /**
     * @return the adminQC
     */
    public Boolean getAdminQC() {
        return adminQC;
    }

    /**
     * @param adminQC
     *            the adminQC to set
     */
    public void setAdminQC(Boolean adminQC) {
        this.adminQC = adminQC;
    }

    /**
     * @return the scientificAbstraction
     */
    public Boolean getScientificAbstraction() {
        return scientificAbstraction;
    }

    /**
     * @param scientificAbstraction
     *            the scientificAbstraction to set
     */
    public void setScientificAbstraction(Boolean scientificAbstraction) {
        this.scientificAbstraction = scientificAbstraction;
    }

    /**
     * @return the scientificQC
     */
    public Boolean getScientificQC() {
        return scientificQC;
    }

    /**
     * @param scientificQC
     *            the scientificQC to set
     */
    public void setScientificQC(Boolean scientificQC) {
        this.scientificQC = scientificQC;
    }

    
    /**
     * @return the assignedTo
     */
    public Long getAssignedTo() {
        return assignedTo;
    }

    /**
     * @param assignedTo
     *            the assignedTo to set
     */
    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * @return the newProcessingPriority
     */
    public String getNewProcessingPriority() {
        return newProcessingPriority;
    }

    /**
     * @param newProcessingPriority
     *            the newProcessingPriority to set
     */
    public void setNewProcessingPriority(String newProcessingPriority) {
        this.newProcessingPriority = newProcessingPriority;
    }

    /**
     * @return the processingComments
     */
    public String getProcessingComments() {
        return processingComments;
    }

    /**
     * @param processingComments
     *            the processingComments to set
     */
    public void setProcessingComments(String processingComments) {
        this.processingComments = processingComments;
    }

    /**
     * @return the checkoutCommands
     */
    public List<String> getCheckoutCommands() {
        return checkoutCommands;
    }

    /**
     * @param checkoutCommands
     *            the checkoutCommands to set
     */
    public void setCheckoutCommands(List<String> checkoutCommands) {
        this.checkoutCommands = checkoutCommands;
    }

    /**
     * @return the readyForTSR
     */
    public Boolean getReadyForTSR() {
        return readyForTSR;
    }

    /**
     * @param readyForTSR
     *            the readyForTSR to set
     */
    public void setReadyForTSR(Boolean readyForTSR) {
        this.readyForTSR = readyForTSR;
    }

    /**
     * @return the submittedUnaccepted
     */
    public Boolean getSubmittedUnaccepted() {
        return submittedUnaccepted;
    }

    /**
     * @param submittedUnaccepted
     *            the submittedUnaccepted to set
     */
    public void setSubmittedUnaccepted(Boolean submittedUnaccepted) {
        this.submittedUnaccepted = submittedUnaccepted;
    }

    /**
     * @return the submittedBy
     */
    public String getSubmittedBy() {
        return submittedBy;
    }

    /**
     * @param submittedBy the submittedBy to set
     */
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    /**
     * @return the processingPriority
     */
    public List<String> getProcessingPriority() {
        return processingPriority;
    }

    /**
     * @param processingPriority the processingPriority to set
     */
    public void setProcessingPriority(List<String> processingPriority) {
        this.processingPriority = processingPriority;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
  

    /**
     * @return the serviceUtils
     */
    public PAServiceUtils getServiceUtils() {
        return serviceUtils;
    }

    /**
     * @param serviceUtils the serviceUtils to set
     */
    public void setServiceUtils(PAServiceUtils serviceUtils) {
        this.serviceUtils = serviceUtils;
    }

    /**
     * @return the assignee
     */
    public Long getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(Long assignee) {
        this.assignee = assignee;
    }

    /**
     * @return the anatomicSites
     */
    public List<String> getAnatomicSites() {
        return anatomicSites;
    }

    /**
     * @param anatomicSites the anatomicSites to set
     */
    public void setAnatomicSites(List<String> anatomicSites) {
        this.anatomicSites = anatomicSites;
    }

    /**
     * @return the interventions
     */
    public List<String> getInterventions() {
        return interventions;
    }

    /**
     * @param interventions the interventions to set
     */
    public void setInterventions(List<String> interventions) {
        this.interventions = interventions;
    }

    /**
     * @return the diseases
     */
    public List<String> getDiseases() {
        return diseases;
    }

    /**
     * @param diseases
     *            the diseases to set
     */
    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }

    /**
     * @return onHoldValuesMap
     */
    public Map<String, String> getOnHoldValuesMap() {
        return onHoldValuesMap;
    }

    /**
     * @param onHoldValuesMap onHoldValuesMap
     */
    public void setOnHoldValuesMap(Map<String, String> onHoldValuesMap) {
        this.onHoldValuesMap = onHoldValuesMap;
    }

}
