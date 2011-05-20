/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;


/**
 * @author asharma
 *
 */
public class StudyProtocolQueryActionTest extends AbstractPaActionTest {

    StudyProtocolQueryAction spqAction;
    StudyProtocolQueryCriteria criteria;

    @Before
    public void setUp() throws PAException {
        spqAction = new StudyProtocolQueryAction();
        criteria = new StudyProtocolQueryCriteria();
        criteria.setNciIdentifier("NCI-2009-00001");
        getRequest().setUserInRole(Constants.SUABSTRACTOR, true);
        UsernameHolder.setUser("suAbstractor");
        getSession().setAttribute(Constants.IS_SU_ABSTRACTOR, Boolean.TRUE);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#execute()}.
     */
    @Test
    public void testExecute() throws PAException {
        assertEquals("success", spqAction.execute());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#showCriteria()}.
     */
    @Test
    public void testShowCriteria() throws PAException {
        assertEquals("criteriaProtected", spqAction.showCriteria());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#query()}.
     */
    @Test
    public void testQuery() throws PAException {
        assertEquals("success", spqAction.query());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#view()}.
     */
    @Test
    public void testView() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("view", spqAction.view());
        List<String> commands = spqAction.getCheckoutCommands();
        assertEquals(2, commands.size());
        assertTrue(commands.contains("adminCheckOut"));
        assertTrue(commands.contains("scientificCheckOut"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#viewTSR()}.
     */
    @Test
    public void testViewTSR() throws PAException {
        getRequest().setupAddParameter("studyProtocolId", "1");
        assertEquals("none", spqAction.viewTSR());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminCheckOut()}.
     */
    @Test
    public void testAdminCheckOut() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.adminCheckOut());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#adminCheckOut()}.
     */
    @Test
    public void testAdminCheckIn() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.adminCheckOut());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#scientificCheckOut()}.
     */
    @Test
    public void testScientificCheckOut() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.scientificCheckOut());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.StudyProtocolQueryAction#scientificCheckIn()}.
     */
    @Test
    public void testScientificCheckIn() throws PAException {
        spqAction.setStudyProtocolId(1L);
        assertEquals("viewRefresh", spqAction.scientificCheckIn());
    }

}