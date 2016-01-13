package gov.nih.nci.registry.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrgDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.ParticipatingOrgServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gov.nih.nci.pa.enums.StudyStatusCode.ACTIVE;
import static gov.nih.nci.pa.enums.StudyStatusCode.APPROVED;
import static gov.nih.nci.pa.enums.StudyStatusCode.ENROLLING_BY_INVITATION;
import static gov.nih.nci.pa.enums.StudyStatusCode.IN_REVIEW;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL;
import static gov.nih.nci.pa.enums.StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;

/**
 * To manage program code assignments.
 * For details refer to PO-9192 (attachment PPT) page 10
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ProgramCodeAssignmentAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 4866645110688822061L;
    private static final String UTF_8 = "UTF-8";
    private static final Logger LOG = Logger.getLogger(ProgramCodeAssignmentAction.class);
    private static final String IS_SITE_ADMIN = "isSiteAdmin";
    private static final String ERROR_MSG_KEY = "msg";
    private static final String AJAX_RETURN_SUCCESS_KEY = "status";

    private static final StudyStatusCode[] ACTIVE_PROTOCOL_STATUSES = new StudyStatusCode[]{ACTIVE, APPROVED,
            IN_REVIEW, ENROLLING_BY_INVITATION,
            TEMPORARILY_CLOSED_TO_ACCRUAL,
            TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION};


    private FamilyProgramCodeService familyProgramCodeService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private RegistryUserServiceLocal registryUserService;
    private ParticipatingOrgServiceLocal participatingOrgService;
    private StudyProtocolServiceLocal studyProtocolService;

    private List<OrgFamilyDTO> affiliatedFamilies;
    private Long familyPoId;
    private FamilyDTO familyDto;
    private String pgcFilter;
    private Long studyProtocolId;

    /**
     *  Will return the studyProtocolId
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * Will set the studyProtocolId
     * @param studyProtocolId the studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * Gets affiliatedFamilies
     * @return the affiliatedFamilies
     */
    public List<OrgFamilyDTO> getAffiliatedFamilies() {
        return affiliatedFamilies;
    }

    /**
     * Sets affiliatedFamilies
     * @param affiliatedFamilies  the affiliatedFamilies
     */
    public void setAffiliatedFamilies(List<OrgFamilyDTO> affiliatedFamilies) {
        this.affiliatedFamilies = affiliatedFamilies;
    }

    /**
     * Sets familyProgramCodeService
     * @param familyProgramCodeService  the familyProgramCodeService
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }


    /**
     * Will populate the affiliatedFamilies
     * @param orgId the orgd
     * @throws PAException - is thrown on any backend exception
     */
    private void loadAffiliatedFamilies(Long orgId) throws PAException {
        setAffiliatedFamilies(FamilyHelper.getByOrgId(orgId));
    }

    /**
     * Gets familyPoId
     * @return  familyPoId - the familyPOId
     */
    public Long getFamilyPoId() {
        return familyPoId;
    }

    /**
     * Sets familyPoId
     * @param familyPoId - the familyPoId
     */
    public void setFamilyPoId(Long familyPoId) {
        this.familyPoId = familyPoId;
    }

    /**
     * The program code filter
     * @return - the pgcFilter
     */
    public String getPgcFilter() {
        return pgcFilter;
    }

    /**
     * The program code filter
     * @param pgcFilter - the program code filter
     */
    public void setPgcFilter(String pgcFilter) {
        this.pgcFilter = pgcFilter;
    }

    /**
     * The FamilyDto
     * @return the familyDto
     */
    public FamilyDTO getFamilyDto() {
        return familyDto;
    }

    /**
     * Sets the FamilyDto
     * @param familyDto the familyDto
     */
    public void setFamilyDto(FamilyDTO familyDto) {
        this.familyDto = familyDto;
    }
    /**
     * @return ProtocolQueryServiceLocal
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }


    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * Will return the participatingOrgService
     * @return the participatingOrgService
     */
    public ParticipatingOrgServiceLocal getParticipatingOrgService() {
        return participatingOrgService;
    }

    /**
     * Will set the participatingOrgService
     * @param participatingOrgService  the participatingOrgService
     */
    public void setParticipatingOrgService(ParticipatingOrgServiceLocal participatingOrgService) {
        this.participatingOrgService = participatingOrgService;
    }

    /**
     * Will return the study protocol service
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * Will set the study protocol service
     * @param studyProtocolService the studyProtocolService
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * Will initialize the action
     */
    @Override
    public void prepare() {
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
        registryUserService = PaRegistry.getRegistryUserService();
        participatingOrgService = PaRegistry.getParticipatingOrgService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
    }

    /**
     * The RegistryUserServiceLocal
     * @return    the registryUserService
     */
    public RegistryUserServiceLocal getRegistryUserService() {
        return registryUserService;
    }

    /**
     * Sets the RegistryUserServiceLocal
     * @param registryUserService the RegistryUserServiceLocal
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * Will set the basic view action
     * @return - the view name
     */
    @Override
    public String execute() {
       return showDefaultViewPage();
    }

    /**
     * Will be invoked when user changes the family in dropdown.
     * @return - the view name
     */
    public String changeFamily() {
       return showDefaultViewPage();
    }

    /**
     *  Will return the trial data to be shown on UI
     * @return  StreamResult - the json object array
     * @throws UnsupportedEncodingException - for encoding issues
     */
    public StreamResult findTrials() throws UnsupportedEncodingException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        populateTrials(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
    }

    /**
     *  Will unassign program code from trial
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult unassignProgramCode() throws IOException {
        String programCode = ServletActionContext.getRequest().getParameter("pgc");
        try {
            studyProtocolService.unAssignProgramCode(studyProtocolId, programCode);
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "REMOVED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("unassignProgramCode - studyProtocol:"
                    + getStudyProtocolId()
                    + "programCode:" + programCode);
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }


    /**
     *  Will assign program code from trial
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult assignProgramCode() throws IOException {
        String programCode = ServletActionContext.getRequest().getParameter("pgc");
        try {
            studyProtocolService.assignProgramCodesToTrials(Arrays.asList(studyProtocolId),
                    familyPoId, Arrays.asList(programCode));
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "ADDED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("assignProgramCode - studyProtocol:"
                    + getStudyProtocolId()
                    + "programCode:" + programCode);
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }


    /**
     *  Will assign program codes to the trials
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult assignProgramCodesToTrials() throws IOException {
        String codes = ServletActionContext.getRequest().getParameter("pgcList");
        String trialIds = ServletActionContext.getRequest().getParameter("studyProtocolList");

        try {
             List<Long> studyProtocolIds = new ArrayList<Long>();
            for (String trialId : trialIds.split(",")) {
                studyProtocolIds.add(Long.parseLong(trialId));
            }

            studyProtocolService.assignProgramCodesToTrials(studyProtocolIds,
                    familyPoId, Arrays.asList(codes.split(",")));
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "ADDED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("assignProgramCodesToTrials - studyProtocolIds:"
                    + trialIds
                    + "programCodes:" + codes);
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }



    /**
     *  Will unassign program codes from the trials
     * @return  StreamResult - the json object array
     * @throws IOException - for encoding issues
     */
    public StreamResult unassignProgramCodesFromTrials() throws IOException {
        String codes = ServletActionContext.getRequest().getParameter("pgcList");
        String trialIds = ServletActionContext.getRequest().getParameter("studyProtocolList");

        try {
            List<Long> studyProtocolIds = new ArrayList<Long>();
            for (String trialId : trialIds.split(",")) {
                studyProtocolIds.add(Long.parseLong(trialId));
            }

            studyProtocolService.unassignProgramCodesFromTrials(studyProtocolIds,
                    Arrays.asList(codes.split(",")));
            JSONObject root = new JSONObject();
            root.put(AJAX_RETURN_SUCCESS_KEY, "REMOVED");
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("unassignProgramCodesFromTrials - studyProtocolIds:"
                    + trialIds
                    + "programCodes:" + codes);
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }

    /**
     * Will return the sites and investigators
     *
     * @return json having sites and investigators
     * @throws IOException - when error
     */
    public StreamResult participation() throws IOException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);

        try {
            if (familyPoId != null && studyProtocolId != null) {
                List<ParticipatingOrgDTO> treatingSites = participatingOrgService.getTreatingSites(studyProtocolId);
                List<Long> associatedOrgIds = FamilyHelper.getRelatedOrgsInFamily(familyPoId);
                for (ParticipatingOrgDTO site : treatingSites) {
                    if (associatedOrgIds.contains(Long.valueOf(site.getPoId()))) {
                        JSONObject json = new JSONObject();
                        json.put("site", site.getName());

                        List<PaPersonDTO> allInvestigators = new ArrayList<PaPersonDTO>();
                        allInvestigators.addAll(site.getPrincipalInvestigators());
                        allInvestigators.addAll(site.getSubInvestigators());
                        StringBuilder sb = new StringBuilder();
                        for (PaPersonDTO person : allInvestigators) {
                            sb.append(sb.length() > 0 ? "; " : "");
                            sb.append(person.getLastName());
                            sb.append(", ");
                            sb.append(person.getFirstName());
                        }
                        json.put("investigator", sb.toString());
                        arr.put(json);
                    }
                }
            }

            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (PAException pae) {
            LOG.error("error while checking my participation", pae);
            ServletActionContext.getResponse().addHeader(ERROR_MSG_KEY, pae.getMessage());
            ServletActionContext.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, pae.getMessage());
        }
        return null;
    }

    private void populateTrials(JSONArray arr) {
      LOG.debug("populating trials [familyPOId : " + familyPoId + "]");
      try {

          if (familyPoId != null) {
              loadFamily();
              StudyProtocolQueryCriteria spQueryCriteria = new StudyProtocolQueryCriteria();
              spQueryCriteria.populateReportingPeriodStatusCriterion(familyDto.findStartDate(),
                      familyDto.getReportingPeriodEndDate(), ACTIVE_PROTOCOL_STATUSES);
              spQueryCriteria.setExcludeRejectProtocol(true);
              spQueryCriteria.setExcludeTerminatedTrials(true);
              Long affliatedOrgId = getAffiliatedOrganizationId();
              if (affliatedOrgId != null) {
                spQueryCriteria.getParticipatingSiteIds().add(affliatedOrgId);
              }

              List<StudyProtocolQueryDTO> trials = protocolQueryService.getStudyProtocolByCriteria(spQueryCriteria,
                      SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO);
              for (StudyProtocolQueryDTO trial : trials) {
                 JSONObject o = new JSONObject();
                 o.put("studyProtocolId", trial.getStudyProtocolId());
                 o.put("nciIdentifier", trial.getNciIdentifier());
                 o.put("title", trial.getOfficialTitle());
                 o.put("identifiers", trial.getAllIdentifiersAsString());
                 o.put("leadOrganizationName", trial.getLeadOrganizationName());
                 o.put("piFullName", trial.getPiFullName());
                 o.put("trialStatus", trial.getStudyStatusCode().getDisplayName());
                 o.put("DT_RowId", "trial_" + trial.getStudyProtocolId());

                 JSONArray pgcArr = new JSONArray();
                 for (ProgramCodeDTO pg : trial.getProgramCodes()) {
                     JSONObject pgcObj = new JSONObject();
                     pgcObj.put("id", pg.getId());
                     pgcObj.put("code", pg.getProgramCode());
                     pgcObj.put("name", pg.getProgramName());
                     pgcArr.put(pgcObj);
                 }
                 o.put("programCodes", pgcArr);
                 arr.put(o);
              }
          }

      } catch (PAException  e) {
        LOG.error("Error while searching trials", e);
      }

    }

    /**
     * Will load data and return the view name
     * @return - the view name
     */
    private String showDefaultViewPage()  {

        String returnPage = ERROR;

        try {

            if (isSiteAdmin()) {
                Long affiliagedOrgId = getAffiliatedOrganizationId();
                loadAffiliatedFamilies(affiliagedOrgId);
                loadFamily();
                returnPage = SUCCESS;
            }

        } catch (PAException e) {
            LOG.error("Error while loading data", e);
        }

        return returnPage;

    }

    /**
     * Loads the family from database
     */
    private void loadFamily() {
        familyDto = familyPoId != null ? familyProgramCodeService.getFamilyDTOByPoId(familyPoId) : null;
    }

    /**
     * Will return the affiliated site from logged-in user.
     * @return  affiliatedOrganizationId
     * @throws PAException on error
     */
    private Long getAffiliatedOrganizationId() throws PAException {
        HttpServletRequest request = ServletActionContext.getRequest();
        RegistryUser registryUser = registryUserService.getUser(request.getRemoteUser());
        return registryUser.getAffiliatedOrganizationId();
    }

    /**
     * Will check if user session have siteAdmin attribute.
     * @return true for site administrator
     */
    private boolean isSiteAdmin() {
        Object value =  ServletActionContext.getRequest().getSession().getAttribute(IS_SITE_ADMIN);
        return value != null && (boolean) value;
    }

}
