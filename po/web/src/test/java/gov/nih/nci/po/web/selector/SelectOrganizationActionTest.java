package gov.nih.nci.po.web.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.StrutsOrganizationSearchCriteria;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.selector.SelectOrganizationAction;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class SelectOrganizationActionTest extends AbstractPoTest {

    @Test
    public void search() {

        assertEquals(Action.SUCCESS, action.search());
    }

    private SelectOrganizationAction action;

    @Before
    public void setUp() {
        action = new SelectOrganizationAction();
        assertNotNull(action.getCriteria());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        StrutsOrganizationSearchCriteria initial = action.getCriteria();
        action.prepare();
        assertSame(initial, action.getCriteria());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        action.setRootKey("a");
        action.prepare();
        assertNull(action.getCriteria());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        StrutsOrganizationSearchCriteria c = new StrutsOrganizationSearchCriteria();
        action.setRootKey("a");
        getSession().setAttribute(action.getRootKey(), c);
        action.prepare();
        assertSame(c, action.getCriteria());
    }

    @Test
    public void testStart() {
        assertEquals(Action.SUCCESS, action.start());
    }

    @Test
    public void testDetail() {
        action.setOrganization(new Organization());
        action.getOrganization().setId(1L);
        assertEquals(SelectOrganizationAction.DETAIL_RESULT, action.detail());
    }

    @Test
    public void testOrganizationProperty() {
        assertNotNull(action.getOrganization());
        action.setOrganization(null);
        assertNull(action.getOrganization());
    }

    @Test
    public void testSourceProperty() {
        assertNotNull(action.getSource());
        action.setSource(null);
        assertNull(action.getSource());
    }

    @Test
    public void testRootKeyProperty() {
        assertNull(action.getRootKey());
        action.setRootKey("");
        assertNotNull(action.getRootKey());
    }

    @Test
    public void testCriteriaProperty() {
        assertNotNull(action.getCriteria());
        action.setCriteria(null);
        assertNull(action.getCriteria());
    }
}
