package gov.nih.nci.po.web.duplicates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.DuplicatesOrganizationSearchCriteria;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

public class DuplicatesOrganizationActionTest extends AbstractPoTest {

    @Test
    public void search() {

        assertEquals(ActionSupport.SUCCESS, action.search());
    }

    private DuplicatesOrganizationAction action;

    @Before
    public void setUp() {
        action = new DuplicatesOrganizationAction();
        assertNotNull(action.getCriteria());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        DuplicatesOrganizationSearchCriteria initial = action.getCriteria();
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
        DuplicatesOrganizationSearchCriteria c = new DuplicatesOrganizationSearchCriteria();
        action.setRootKey("a");
        getSession().setAttribute(action.getRootKey(), c);
        action.prepare();
        assertSame(c, action.getCriteria());
    }

    @Test
    public void testStart() {
        assertEquals(DuplicatesOrganizationAction.SUCCESS, action.start());
    }

    @Test
    public void testDetail() {
        action.setOrganization(new Organization());
        action.getOrganization().setId(1L);
        assertEquals(DuplicatesOrganizationAction.DETAIL_RESULT, action.detail());
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
