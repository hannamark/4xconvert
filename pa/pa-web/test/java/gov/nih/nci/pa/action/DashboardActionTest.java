/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 * 
 */
public class DashboardActionTest extends AbstractPaActionTest {

    @Override
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());
    }

    @Test
    public void testNoDashboardAccess() throws PAException {
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        assertEquals("nonAbstractorLanding", action.execute());

    }

    @Test
    public void testSuAbstractorRedirectToSearchScreen() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        assertEquals("suAbstractorLanding", action.execute());
    }

    @Test
    public void testAdminScientificAbstractorSearch() throws PAException {
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        assertEquals("abstractorLanding", action.execute());
        final List trials = (List) getRequest().getSession().getAttribute(
                "workload");
        assertNotNull(trials);
        assertEquals(2, trials.size());
        assertNull(getRequest().getAttribute("toggleResultsTab"));
    }

    @Test
    public void testDateRangeFilter() throws PAException {
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/03/2015");
        action.setDateTo("06/05/2015");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        List<StudyProtocolQueryDTO> trials = (List) getRequest().getSession()
                .getAttribute("workload");
        assertEquals(1, trials.size());
        assertEquals(PAUtil.dateStringToTimestamp("06/04/2015"), trials.get(0)
                .getActiveHoldDate());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/01/2015");
        action.setDateTo("06/01/2015");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(1, trials.size());
        assertEquals(PAUtil.dateStringToTimestamp("06/01/2015"), trials.get(0)
                .getActiveHoldDate());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/01/2015");
        action.setDateTo("06/04/2015");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("05/30/2015");
        action.setDateTo("05/30/2015");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(0, trials.size());

        action = getAction();
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());

        action = getAction();
        action.setDateFrom("05/30/2015");
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());

        action = getAction();
        action.setDateTo("06/04/2015");
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());

        // ensure execute resets any active filter.
        action = getAction();
        action.setDateFrom("05/30/2015");
        action.setDateTo("06/04/2015");
        action.setDateFilterField("activeHoldDate");
        assertEquals("abstractorLanding", action.execute());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());
        assertNull(action.getDateFilterField());
        assertNull(action.getDateTo());
        assertNull(action.getDateFrom());

        // ensure invalid date format does not error out, assumes null date
        // instead
        action = getAction();
        action.setDateFilterField("activeHoldDate");
        action.setDateFrom("06/01/2015");
        action.setDateTo("56/01/2010");
        assertEquals("abstractorLanding", action.dateRangeFilter());
        trials = (List) getRequest().getSession().getAttribute("workload");
        assertEquals(2, trials.size());

    }

    @Test
    public void testSearch() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        loadCriteriaIntoAction(action);
        assertEquals("suAbstractorLanding", action.search());
        final List trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNotNull(trials);
        assertEquals(2, trials.size());
        assertEquals(true, getRequest().getAttribute("toggleResultsTab"));
    }

    @Test
    public void testTimelineValidation() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        DashboardAction action = getAction();
        loadCriteriaIntoAction(action);
        action.setSubmittedOnOrAfter("01/02/2012");
        action.setSubmittedOnOrBefore("01/01/2012");
        assertEquals("suAbstractorLanding", action.search());
        final List trials = (List) getRequest().getSession().getAttribute(
                "dashboardSearchResults");
        assertNull(trials);
        assertEquals(
                "Submission timeline dates are inconsistent and will never produce results. "
                        + "Please correct",
                getRequest().getAttribute(Constants.FAILURE_MESSAGE));
    }

    @Test
    public void testView() throws PAException {
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        getRequest().setUserInRole(Constants.SCIENTIFIC_ABSTRACTOR, true);
        getRequest().setUserInRole(Constants.ADMIN_ABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");

        DashboardAction action = getAction();
        PAServiceUtils serviceUtils = mock(PAServiceUtils.class);
        when(serviceUtils.getStudyIdentifier(any(Ii.class), any(String.class)))
                .thenReturn("NCTID");
        action.setStudyProtocolId(2L);
        action.setServiceUtils(serviceUtils);

        assertEquals("suAbstractorLanding", action.view());
        assertEquals(true, getRequest().getAttribute("toggleDetailsTab"));
        assertEquals("NCTID",
                getRequest().getSession().getAttribute("nctIdentifier"));
        assertTrue(action.getCheckoutCommands().contains("adminCheckIn"));
        assertTrue(action.getCheckoutCommands().contains("scientificCheckOut"));
    }

    private void loadCriteriaIntoAction(DashboardAction action) {
        action.setAdminAbstraction(true);
        action.setAdminQC(true);
        action.setAssignedTo(1L);
        action.setCheckedOutBy(1L);
        action.setCtepDcpCategory("true");
        action.setMilestone("SUBMISSION_ACCEPTED");
        action.setMilestoneType("last");
        action.setNciSponsored("true");
        action.setOnHoldReason(Arrays.asList("Invalid Grant"));
        action.setOnHoldStatus(Arrays.asList("onhold"));
        action.setProcessingComments("comments");
        action.setProcessingPriority(Arrays.asList("1"));
        action.setProcessingStatus(Arrays.asList("Accepted"));
        action.setReadyForTSR(true);
        action.setScientificAbstraction(true);
        action.setScientificQC(true);
        action.setSubmissionType(Arrays.asList("Original"));
        action.setSubmittedBy("ctrp");
        action.setSubmittedOnOrAfter("01/01/2012");
        action.setSubmittedOnOrBefore("01/01/2012");
        action.setSubmittedUnaccepted(true);
        action.setSubmittingOrgId("1");
    }

    /**
     * @throws PAException
     * 
     */
    private DashboardAction getAction() throws PAException {
        DashboardAction action = new DashboardAction();
        action.setServletRequest(getRequest());
        action.prepare();
        action.setProtocolQueryService(getProtocolQueryMock());
        return action;
    }

    /**
     * @return
     * @throws PAException
     */
    private ProtocolQueryServiceLocal getProtocolQueryMock() throws PAException {
        final ProtocolQueryServiceLocal mock = mock(ProtocolQueryServiceLocal.class);
        StudyProtocolQueryDTO dto1 = new StudyProtocolQueryDTO();
        dto1.setStudyProtocolId(1L);
        dto1.getAdminCheckout().setCheckoutBy("suAbstractor");
        dto1.setActiveHoldDate(PAUtil.dateStringToTimestamp("06/04/2015"));

        StudyProtocolQueryDTO dto2 = new StudyProtocolQueryDTO();
        dto2.setStudyProtocolId(2L);
        dto2.setActiveHoldDate(PAUtil.dateStringToTimestamp("06/01/2015"));
        when(
                mock.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2)));
        when(
                mock.getStudyProtocolByCriteria(
                        any(StudyProtocolQueryCriteria.class),
                        (ProtocolQueryPerformanceHints[]) anyVararg()))
                .thenReturn(new ArrayList(Arrays.asList(dto1, dto2)));
        when(mock.getTrialSummaryByStudyProtocolId(any(Long.class)))
                .thenReturn(dto1);
        return mock;
    }

}