package gov.nih.nci.pa.action;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.util.Constants.IS_SU_ABSTRACTOR;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author dkrylov
 * 
 */
public class TrialCountsAction extends ActionSupport implements Preparable,
        ServletRequestAware {

    private ProtocolQueryServiceLocal protocolQueryService;
    private LookUpTableServiceRemote lookUpService;
    private CSMUserUtil csmUserUtil;

    private static final long serialVersionUID = -8919884566276557104L;
    private static final String UTF_8 = "UTF-8";

    private HttpServletRequest request;

    // CHECKSTYLE:OFF

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult abstractorsWork() throws UnsupportedEncodingException,
            PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        abstractorsWork(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void abstractorsWork(JSONArray arr) throws PAException,
            JSONException {
        final Map<Long, String> users = csmUserUtil.getAbstractors();
        for (long userID : users.keySet()) {
            String userName = users.get(userID);
            final String loginName = csmUserUtil.getCSMUserById(userID)
                    .getLoginName();
            List<String> groups = csmUserUtil.getUserGroups(loginName);
            userName += (" (" + determinePostFixBasedOnGroupMembership(groups) + ")");
            // counts
            int admin = 0;
            int scientific = 0;
            int adminScientific = 0;

            StudyProtocolQueryCriteria criteria = getCriteria();
            criteria.setStudyLockedBy(true);
            criteria.setUserLastCreated(loginName);

            for (StudyProtocolQueryDTO dto : protocolQueryService
                    .getStudyProtocolByCriteria(criteria)) {
                final String adminBy = dto.getAdminCheckout().getCheckoutBy();
                final String sciBy = dto.getScientificCheckout()
                        .getCheckoutBy();
                if (loginName.equals(adminBy) && loginName.equals(sciBy)) {
                    adminScientific++;
                } else if (loginName.equals(adminBy)) {
                    admin++;
                } else if (loginName.equals(sciBy)) {
                    scientific++;
                }
            }

            // Put counts into JSON
            if (admin + scientific + adminScientific > 0) {
                JSONObject data = new JSONObject();
                data.put("name", userName);
                data.put("admin", admin);
                data.put("scientific", scientific);
                data.put("admin_scientific", adminScientific);
                data.put("user_id", userID);
                arr.put(data);
            }
        }
    }

    private String determinePostFixBasedOnGroupMembership(
            final List<String> groups) {
        boolean admin = groups.contains(Constants.ADMIN_ABSTRACTOR);
        boolean scientific = groups.contains(Constants.SCIENTIFIC_ABSTRACTOR);
        boolean sup = groups.contains(Constants.SUABSTRACTOR);
        return sup ? "SU" : (admin && scientific ? "AS"
                : (admin && !scientific ? "AS" : "SC"));
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult trialDist() throws UnsupportedEncodingException,
            PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        trialDist(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void trialDist(JSONArray arr) throws PAException, JSONException {
        final List<String> ranges = Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.trialdist").split(","));

        // TreeMap ensures holds are displayed in the same order as in property.
        final Map<String, Integer> countsMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return ranges.indexOf(s1) - ranges.indexOf(s2);
                    }
                });

        // All ranges must be in the map, even those with zero counts.
        for (String range : ranges) {
            countsMap.put(range, 0);
        }

        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getWorkload();
        for (final StudyProtocolQueryDTO dto : results) {
            int days = dto.getBizDaysSinceSubmitted();
            for (String range : ranges) {
                if (PAUtil.isInRange(days, range)) {
                    countsMap.put(range, countsMap.get(range) + 1);
                }
            }
        }

        for (String range : countsMap.keySet()) {
            JSONObject data = new JSONObject();
            data.put("range", range);
            data.put("count", (int) countsMap.get(range));
            data.put("DT_RowId", range);
            arr.put(data);
        }

    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult onHoldTrials() throws UnsupportedEncodingException,
            PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        onHoldTrials(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void onHoldTrials(JSONArray arr) throws PAException, JSONException {
        final List<String> holdCodes = Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.onholds").split(","));

        // TreeMap ensures holds are displayed in the same order as in property.
        final Map<String, Integer> countsMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return holdCodes.indexOf(s1) - holdCodes.indexOf(s2);
                    }
                });

        // All on-hold reasons must be in the map, even those with zero counts.
        for (String code : holdCodes) {
            countsMap.put(code, 0);
        }

        final List<StudyProtocolQueryDTO> results = getTrialsForOnHoldCount();
        for (StudyProtocolQueryDTO dto : results) {
            String code = dto.getActiveHoldReason().getCode();
            String cat = dto.getActiveHoldReasonCategory();
            String key = OnholdReasonCode.OTHER.getCode()
                    .equalsIgnoreCase(code) ? code + " (" + cat + ")" : code;
            Integer count = countsMap.get(key);
            if (count != null) {
                count++;
                countsMap.put(key, count);
            }
        }

        int total = 0;
        for (String code : countsMap.keySet()) {
            JSONObject data = new JSONObject();
            data.put("reason", code);
            data.put("reasonKey", code.replaceFirst("\\s+\\(", "_")
                    .replaceFirst("\\)", ""));
            data.put("count", (int) countsMap.get(code));
            data.put("DT_RowId", code);
            arr.put(data);
            total += countsMap.get(code);
        }
        JSONObject data = new JSONObject();
        data.put("reason", "Total");
        data.put("reasonKey", "Total");
        data.put("count", total);
        data.put("DT_RowId", "TotalHold");
        arr.put(data);
    }

    private List<StudyProtocolQueryDTO> getTrialsForOnHoldCount()
            throws PAException {
        StudyProtocolQueryCriteria criteria = getCriteria();
        criteria.setHoldStatus(PAConstants.ON_HOLD);
        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getStudyProtocolByCriteria(criteria, SKIP_ALTERNATE_TITLES,
                        SKIP_LAST_UPDATER_INFO, SKIP_OTHER_IDENTIFIERS);
        return results;
    }

    /**
     * @return
     */
    private StudyProtocolQueryCriteria getCriteria() {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setExcludeRejectProtocol(true);
        criteria.setExcludeTerminatedTrials(true);
        return criteria;
    }

    /**
     * @return StreamResult
     * @throws UnsupportedEncodingException
     *             UnsupportedEncodingException
     * 
     * @throws PAException
     *             PAException
     * @throws JSONException
     *             JSONException
     */
    public StreamResult milestonesInProgress()
            throws UnsupportedEncodingException, PAException, JSONException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        milestonesInProgress(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString()
                .getBytes(UTF_8)));
    }

    private void milestonesInProgress(JSONArray arr) throws PAException,
            JSONException {

        final List<String> milestoneCodes = Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.milestones").split(","));
        final List<StudyProtocolQueryDTO> results = getTrialsForMilestoneCount();
        final Map<String, Integer> countsMap = new TreeMap<>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return milestoneCodes.indexOf(s1)
                                - milestoneCodes.indexOf(s2);
                    }
                });

        // All milestones must be in the map, even those with zero counts.
        for (String code : milestoneCodes) {
            countsMap.put(code, 0);
        }

        for (StudyProtocolQueryDTO dto : results) {
            String code = dto.getMilestones().getLastMilestone().getMilestone()
                    .getCode();
            Integer count = countsMap.get(code);
            if (count == null) {
                count = 0;
            }
            count++;
            countsMap.put(code, count);
        }

        int total = 0;
        for (String code : countsMap.keySet()) {
            JSONObject data = new JSONObject();
            data.put("milestone", code);
            data.put("count", (int) countsMap.get(code));
            data.put("DT_RowId", code);
            arr.put(data);
            total += countsMap.get(code);
        }
        JSONObject data = new JSONObject();
        data.put("milestone", "Total");
        data.put("count", total);
        data.put("DT_RowId", "TotalMilestone");
        arr.put(data);
    }

    // CHECKSTYLE:ON
    /**
     * @return
     * @throws PAException
     */
    private List<StudyProtocolQueryDTO> getTrialsForMilestoneCount()
            throws PAException {
        StudyProtocolQueryCriteria criteria = getCriteria();
        criteria.setStudyMilestone(Arrays.asList(lookUpService
                .getPropertyValue("dashboard.counts.milestones").split(",")));
        criteria.setHoldStatus(PAConstants.NOT_ON_HOLD);
        List<StudyProtocolQueryDTO> results = protocolQueryService
                .getStudyProtocolByCriteria(criteria, SKIP_ALTERNATE_TITLES,
                        SKIP_LAST_UPDATER_INFO, SKIP_OTHER_IDENTIFIERS);
        protocolQueryService.populateMilestoneHistory(results);
        return results;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.request = servletRequest;
    }

    @Override
    public void prepare() {
        ActionUtils.setUserRolesInSession(request);
        if (!canSeeTrialCounts()) {
            throw new RuntimeException("Unautorized"); // NOPMD
        }
        protocolQueryService = PaRegistry.getProtocolQueryService();
        lookUpService = PaRegistry.getLookUpTableService();
        csmUserUtil = CSMUserService.getInstance();
    }

    private boolean canSeeTrialCounts() {
        return isInRole(IS_SU_ABSTRACTOR);
    }

    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(request.getSession().getAttribute(roleFlag));
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
     * @param lookUpService
     *            the lookUpService to set
     */
    public void setLookUpService(LookUpTableServiceRemote lookUpService) {
        this.lookUpService = lookUpService;
    }

    /**
     * @param csmUserUtil
     *            the csmUserUtil to set
     */
    public void setCsmUserUtil(CSMUserUtil csmUserUtil) {
        this.csmUserUtil = csmUserUtil;
    }

}
