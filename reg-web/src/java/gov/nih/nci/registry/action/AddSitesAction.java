package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.dto.SubmittedOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.TooManyMethods" })
public class AddSitesAction extends BaseSearchTrialAction { 

    private static final String CONFIRMATION = "confirmation";
    static final String NO_AFFILIATION_ERR_MSG = "We are unable to determine your organization affiliation."
            + " This is why you will not be able to use this page to add sites."
            + " Please update your account information using My Account page.";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AddSitesAction.class);

    private static final String FAILURE_MESSAGE = "failureMessage";
    private static final int LIMIT = 100;
    static final String RESULTS_SESSION_KEY = "AddSitesAction.records";

    private SearchProtocolCriteria criteria = new SearchProtocolCriteria();

    private RegistryUserServiceLocal registryUserService;

    private ParticipatingOrgServiceLocal participatingOrgService;

    private ParticipatingSiteServiceLocal participatingSiteService;

    private StudyProtocolServiceLocal studyProtocolService;

    private StudySiteContactServiceLocal studySiteContactService;

    private PAServiceUtils paServiceUtils;

    private final List<AddSiteResult> summary = new ArrayList<AddSiteResult>();

    @Override
    public String execute() throws PAException {
        reset();
        if (getUserAffiliation() == null) {
            getServletRequest().setAttribute(FAILURE_MESSAGE,
                    NO_AFFILIATION_ERR_MSG);
        }
        return SUCCESS;
    }

    private void reset() {
        setRecords(new ArrayList<StudyProtocolQueryDTO>());
        getServletRequest().getSession().removeAttribute(RESULTS_SESSION_KEY);
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     * @throws IOException
     *             IOException
     */
    @SuppressWarnings("unchecked")
    public String validateSiteData() throws PAException, IOException {
        List<StudyProtocolQueryDTO> trials = (List<StudyProtocolQueryDTO>) getServletRequest()
                .getSession().getAttribute(RESULTS_SESSION_KEY);
        if (CollectionUtils.isEmpty(trials)) {
            throw new PAException("No trials found.");
        }

        JSONObject root = new JSONObject();
        JSONArray errors = new JSONArray();
        root.put("errors", errors);

        for (StudyProtocolQueryDTO trial : trials) {
            validateSitesData(trial, errors);
        }

        getResponse().setContentType("application/json");
        Writer writer = getResponse().getWriter();
        writer.write(root.toString());
        writer.flush();
        return null;
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("unchecked")
    public String save() throws PAException {

        List<StudyProtocolQueryDTO> trials = (List<StudyProtocolQueryDTO>) getServletRequest()
                .getSession().getAttribute(RESULTS_SESSION_KEY);
        if (CollectionUtils.isEmpty(trials)) {
            addActionError("Unexpected error: no trials found.");
            return ERROR;
        }

        for (StudyProtocolQueryDTO trial : trials) {
            saveSitesData(trial);
        }

        clearErrorsAndMessages();
        reset();
        return CONFIRMATION;
    }

    private void saveSitesData(StudyProtocolQueryDTO trial) {
        List<SubmittedOrganizationDTO> sites = collectSitesForTrial(trial);
        for (SubmittedOrganizationDTO siteDTO : sites) {
            saveSiteData(trial, siteDTO);
        }
    }

    private void saveSiteData(StudyProtocolQueryDTO trial,
            SubmittedOrganizationDTO siteDTO) {

        try {
            OrganizationDTO siteOrg = paServiceUtils
                    .getPOOrganizationEntity(IiConverter
                            .convertToPoOrganizationIi(siteDTO.getSitePoId()));
            if (siteOrg != null) {
                siteDTO.setName(EnOnConverter.convertEnOnToString(siteOrg
                        .getName()));
            }

            final AddUpdateSiteHelper helper = new AddUpdateSiteHelper(
                    paServiceUtils, siteDTO, participatingSiteService,
                    studyProtocolService, getRegistryUser(),
                    registryUserService, studySiteContactService, this, trial
                            .getStudyProtocolId().toString(),
                    siteDTO.getSitePoId());
            helper.addSite();

            summary.add(new AddSiteResult(trial, siteDTO, "SUCCESS"));
        } catch (PAException e) {
            LOG.error(e, e);
            summary.add(new AddSiteResult(trial, siteDTO, "FAILURE: "
                    + e.getMessage()));
        }
    }

    private void validateSitesData(StudyProtocolQueryDTO trial, JSONArray errors) {
        List<SubmittedOrganizationDTO> sites = collectSitesForTrial(trial);
        for (SubmittedOrganizationDTO siteDTO : sites) {
            clearErrorsAndMessages();
            StringBuilder sb = new StringBuilder();

            // first check to see if user has specified a duplicate org.
            for (SubmittedOrganizationDTO otherDTO : sites) {
                if (otherDTO.getIndex() != siteDTO.getIndex()
                        && StringUtils.equals(otherDTO.getSitePoId(),
                                siteDTO.getSitePoId())) {
                    sb.append("Looks like you have specified this organization as a site twice on this trial. ");
                    break;
                }
            }

            new ParticipatingSiteValidator(siteDTO, this, this, paServiceUtils)
                    .validate();
            if (hasFieldErrors()) {
                sb.append(collectFieldErrors());
            }

            if (sb.length() > 0) {
                JSONObject siteErrors = new JSONObject();
                siteErrors.put("spID", trial.getStudyProtocolId());
                siteErrors.put("index", siteDTO.getIndex());
                siteErrors.put("errors", sb.toString());
                errors.put(siteErrors);
            }
        }
    }

    private String collectFieldErrors() {
        StringBuilder sb = new StringBuilder();
        for (List<String> list : getFieldErrors().values()) {
            for (String str : list) {
                sb.append(str);
                sb.append(". ");
            }
        }
        return sb.toString().replace("..", ".");
    }

    private List<SubmittedOrganizationDTO> collectSitesForTrial(
            StudyProtocolQueryDTO trial) {
        List<SubmittedOrganizationDTO> list = new ArrayList<SubmittedOrganizationDTO>();
        for (int i = 0; i < CollectionUtils.size(trial
                .getOrgsThatCanBeAddedAsSite()); i++) {
            SubmittedOrganizationDTO site = buildSiteFromRequestParameters(
                    trial.getStudyProtocolId(), i);
            if (!site.isBlank()) {
                list.add(site);
            }
        }
        return list;
    }

    private SubmittedOrganizationDTO buildSiteFromRequestParameters(Long spID,
            int index) {
        HttpServletRequest r = getServletRequest();
        SubmittedOrganizationDTO site = new SubmittedOrganizationDTO();
        site.setIndex(index);
        site.setSitePoId(StringUtils.defaultString(r.getParameter(String
                .format("trial_%s_site_%s_org_poid", spID, index))));
        site.setSiteLocalTrialIdentifier(StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_localID", spID,
                        index))).trim());

        String investigatorID = StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_pi_poid", spID,
                        index))).trim();
        site.setInvestigatorId(investigatorID.isEmpty() ? null : Long
                .parseLong(investigatorID));

        site.setProgramCode(StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_pgcode", spID,
                        index))).trim());
        site.setRecruitmentStatus(StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_status", spID,
                        index))).trim());
        site.setRecruitmentStatusDate(StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_statusDate",
                        spID, index))).trim());
        site.setDateOpenedforAccrual(StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_dateOpen", spID,
                        index))).trim());
        site.setDateClosedforAccrual(StringUtils.defaultString(
                r.getParameter(String.format("trial_%s_site_%s_dateClosed",
                        spID, index))).trim());
        return site;
    }

    /**
     * @return String
     * @throws PAException
     *             PAException
     */
    public String search() throws PAException {
        try {
            validateForm();
            final StudyProtocolQueryCriteria spQueryCriteria = convertToStudyProtocolQueryCriteria();
            searchAndSort(spQueryCriteria);
            applyAdditionalFiltersToSearchResults();
            checkForExcessiveNumberOfResults();
            checkForNoResults();
            getServletRequest().getSession().setAttribute(RESULTS_SESSION_KEY,
                    getRecords());
            return SUCCESS;
        } catch (PAException e) {
            LOG.error(e, e);
            getServletRequest().setAttribute(FAILURE_MESSAGE, e.getMessage());
            reset();
            return ERROR;
        }

    }

    private void checkForNoResults() throws PAException {
        if (getRecords().isEmpty()) {
            throw new PAException(
                    "No trials which match the criteria and to which you can add sites found.");
        }
    }

    private void checkForExcessiveNumberOfResults() throws PAException {
        if (getRecords().size() > LIMIT) {
            throw new PAException(
                    "Search criteria you provided has produced "
                            + getRecords().size()
                            + " (!) matching trials,"
                            + " which is too many to manage at once on this page. The limit is "
                            + LIMIT
                            + ". "
                            + "If you could, please revise the criteria to produce a smaller number of trials"
                            + " that will be more manageable.");
        }
    }

    private void applyAdditionalFiltersToSearchResults() throws PAException {
        setRecords(new ArrayList<StudyProtocolQueryDTO>(getRecords()));
        filterOutNonSiteRegistrableTrials();
        filterOutTrialsThatHaveSitesAlready();

    }

    private void filterOutTrialsThatHaveSitesAlready() throws PAException {
        final Collection<OrganizationDTO> familyMembers = getUserOrgFamilyMembers();
        CollectionUtils.filter(getRecords(), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                StudyProtocolQueryDTO dto = (StudyProtocolQueryDTO) o;
                try {
                    Collection<OrganizationDTO> candidates = participatingOrgService
                            .getOrganizationsThatAreNotSiteYet(
                                    dto.getStudyProtocolId(), familyMembers);
                    dto.setOrgsThatCanBeAddedAsSite(new ArrayList<OrganizationDTO>(
                            candidates));
                    return !candidates.isEmpty();
                } catch (PAException e) {
                    LOG.error(e, e);
                    throw new RuntimeException(e); // NOPMD
                }
            }
        });

    }

    @SuppressWarnings("deprecation")
    private Collection<OrganizationDTO> getUserOrgFamilyMembers()
            throws PAException {
        OrganizationDTO affiliation = getUserAffiliation();
        if (affiliation == null) {
            throw new PAException(NO_AFFILIATION_ERR_MSG);
        }
        List<Long> siblingsPoIds = FamilyHelper.getAllRelatedOrgs(IiConverter
                .convertToLong(affiliation.getIdentifier()));
        Collection<OrganizationDTO> allMembersList = new TreeSet<OrganizationDTO>(
                new Comparator<OrganizationDTO>() {
                    @Override
                    public int compare(OrganizationDTO o1, OrganizationDTO o2) {
                        return IiConverter.convertToLong(o1.getIdentifier())
                                .compareTo(
                                        IiConverter.convertToLong(o2
                                                .getIdentifier()));
                    }
                });
        allMembersList.add(affiliation);
        for (Long poID : siblingsPoIds) {
            OrganizationDTO sibling = paServiceUtils
                    .getPOOrganizationEntity(IiConverter.convertToIi(poID));
            if (sibling != null) {
                allMembersList.add(sibling);
            }
        }
        
        List<OrganizationDTO> properlySortedList = new ArrayList<OrganizationDTO>();
        properlySortedList.add(affiliation);
        allMembersList.remove(affiliation);
        properlySortedList.addAll(allMembersList);
        return properlySortedList;
    }

    /**
     * 
     */
    private void filterOutNonSiteRegistrableTrials() {
        CollectionUtils.filter(getRecords(), new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                StudyProtocolQueryDTO dto = (StudyProtocolQueryDTO) o;
                return dto.isSiteSelfRegistrable();
            }
        });
    }

    /**
     * @return OrganizationDTO
     * @throws PAException
     *             PAException
     */
    @SuppressWarnings("deprecation")
    public OrganizationDTO getUserAffiliation() throws PAException {
        RegistryUser loggedInUser = getRegistryUser();
        if (loggedInUser != null) {
            final Long orgId = loggedInUser.getAffiliatedOrganizationId();
            if (orgId != null) {
                return paServiceUtils.getPOOrganizationEntity(IiConverter
                        .convertToIi(orgId));
            }
        }
        return null;
    }

    private RegistryUser getRegistryUser() throws PAException {
        String loginName = getServletRequest().getRemoteUser();
        return registryUserService.getUser(loginName);
    }

    private StudyProtocolQueryCriteria convertToStudyProtocolQueryCriteria() {
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setOfficialTitle(criteria.getOfficialTitle());
        if (StringUtils.isNotBlank(criteria.getIdentifier())) {
            queryCriteria.setAnyTypeIdentifier(criteria.getIdentifier().trim());
        }
        queryCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        queryCriteria.setTrialCategory("p");

        List<String> dws = new ArrayList<String>();
        for (DocumentWorkflowStatusCode code : DocumentWorkflowStatusCode
                .values()) {
            if (code.isAcceptedOrAbove()) {
                dws.add(code.getCode());
            }
        }
        queryCriteria.setDocumentWorkflowStatusCodes(dws);
        return queryCriteria;
    }

    private void validateForm() throws PAException {
        if (StringUtils.isBlank(criteria.getIdentifier())
                && StringUtils.isBlank(criteria.getOfficialTitle())) {
            throw new PAException(
                    "Please provide a search criteria; otherwise the number of trials returned may be unmanageable.");
        }
    }

    @Override
    public void prepare() {
        super.prepare();
        this.registryUserService = PaRegistry.getRegistryUserService();
        this.participatingOrgService = PaRegistry.getParticipatingOrgService();
        this.studyProtocolService = PaRegistry.getStudyProtocolService();
        this.participatingSiteService = PaRegistry
                .getParticipatingSiteService();
        this.studySiteContactService = PaRegistry.getStudySiteContactService();
        this.paServiceUtils = new PAServiceUtils();
    }

    /**
     * @return the criteria
     */
    public SearchProtocolCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
     */
    public void setCriteria(SearchProtocolCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * @param registryUserService
     *            RegistryUserServiceLocal ;
     */
    public void setRegistryUserService(
            RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @author Denis G. Krylov
     * 
     */
    public static final class AddSiteResult {

        private final StudyProtocolQueryDTO trial;
        private final SubmittedOrganizationDTO siteDTO;
        private final String result;

        /**
         * @param trial
         *            trial
         * @param siteDTO
         *            siteDTO
         * @param result
         *            result
         */
        public AddSiteResult(StudyProtocolQueryDTO trial,
                SubmittedOrganizationDTO siteDTO, String result) {
            this.trial = trial;
            this.siteDTO = siteDTO;
            this.result = result;
        }

        /**
         * @return the trial
         */
        public StudyProtocolQueryDTO getTrial() {
            return trial;
        }

        /**
         * @return the siteDTO
         */
        public SubmittedOrganizationDTO getSiteDTO() {
            return siteDTO;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }

    }

    /**
     * @return the summary
     */
    public List<AddSiteResult> getSummary() {
        return summary;
    }

    /**
     * @param paServiceUtils the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

    /**
     * @param participatingSiteService the participatingSiteService to set
     */
    public void setParticipatingSiteService(
            ParticipatingSiteServiceLocal participatingSiteService) {
        this.participatingSiteService = participatingSiteService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(
            StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(
            StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param participatingOrgService the participatingOrgService to set
     */
    public void setParticipatingOrgService(
            ParticipatingOrgServiceLocal participatingOrgService) {
        this.participatingOrgService = participatingOrgService;
    }

}
