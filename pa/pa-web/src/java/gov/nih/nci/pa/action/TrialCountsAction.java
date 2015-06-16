package gov.nih.nci.pa.action;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.util.Constants.IS_SU_ABSTRACTOR;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.PAConstants;
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
        data.put("DT_RowId", "Total");
        arr.put(data);
    }

    // CHECKSTYLE:ON
    /**
     * @return
     * @throws PAException
     */
    private List<StudyProtocolQueryDTO> getTrialsForMilestoneCount()
            throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setExcludeRejectProtocol(true);
        criteria.setExcludeTerminatedTrials(true);
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

}
