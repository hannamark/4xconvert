package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class CurateOrganizationActionTest extends AbstractPoTest {
    private CurateOrganizationAction action;

    @Before
    public void setUp() {
        action = new CurateOrganizationAction();
        assertNotNull(action.getOrganization());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        Organization initial = action.getOrganization();
        action.prepare();
        assertSame(initial, action.getOrganization());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        action.setRootKey("a");
        action.prepare();
        assertNull(action.getOrganization());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        Organization o = new Organization();
        action.setRootKey("a");
        getSession().setAttribute(action.getRootKey(), o);
        action.prepare();
        assertSame(o, action.getOrganization());
    }

    @Test
    public void testStart() {
        action.getOrganization().setId(1L);
        assertEquals(CurateOrganizationAction.CURATE_RESULT, action.start());
        assertEquals(1l, action.getOrganization().getId().longValue());
        assertEquals("name", action.getOrganization().getName());
    }

    @Test (expected = NullPointerException.class)
    public void testStartNoOrgId() {
        assertEquals(CurateOrganizationAction.CURATE_RESULT, action.start());
    }

    @Test
    public void testCurate() throws EntityValidationException {
        assertEquals(Action.SUCCESS, action.curate());
    }

    @Test
    public void changeCurrentChangeRequest() {
        assertEquals(CurateOrganizationAction.CURATE_RESULT, action.changeCurrentChangeRequest());
        
        action.getCr().setId(1L);
        assertEquals(CurateOrganizationAction.CURATE_RESULT, action.changeCurrentChangeRequest());
    }
}
