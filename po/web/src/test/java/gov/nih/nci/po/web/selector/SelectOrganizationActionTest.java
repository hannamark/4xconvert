package gov.nih.nci.po.web.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class SelectOrganizationActionTest extends AbstractPoTest {

    @Test
    public void search() {
        assertEquals(Action.SUCCESS, action.search());
        assertTrue(action.hasActionErrors());
    }

    private SelectOrganizationAction action;

    @Before
    public void setUp() {
        action = new SelectOrganizationAction();
        assertNotNull(action.getCriteria());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        OrganizationSearchCriteria initial = action.getCriteria();
        action.prepare();
        assertSame(initial, action.getCriteria());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        // can only set root key to the key of an object in the session,
        // so after setting the root key, we have to clear out the session manually to test this case
        action.setRootKey("abc-123");
        getSession().clearAttributes();

        action.prepare();
        assertNull(action.getCriteria());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        OrganizationSearchCriteria c = new OrganizationSearchCriteria();
        String rootKey = "a";
        getSession().setAttribute(rootKey, c);
        action.setRootKey(rootKey);
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
        action.setRootKey("abc-123");
        assertNotNull(action.getRootKey());
        action.setRootKey("");
        assertNotNull(action.getRootKey());
        action.setRootKey(null);
        assertNull(action.getRootKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRootKeyProperty() {
        assertNull(action.getRootKey());
        action.setRootKey("abc-321");
    }

    @Test
    public void testCriteriaProperty() {
        assertNotNull(action.getCriteria());
        action.setCriteria(null);
        assertNull(action.getCriteria());
    }
}
