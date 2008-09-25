package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Map;

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.web.AbstractPoTest;

import org.hibernate.validator.AssertFalse;
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

    @Test(expected = NullPointerException.class)
    public void testStartNoOrgId() {
        assertEquals(CurateOrganizationAction.CURATE_RESULT, action.start());
    }

    @Test
    public void testCurate() throws EntityValidationException {
        assertEquals(Action.SUCCESS, action.curate());
    }

    @Test
    public void testReject() {
        action.getOrganization().setId(1L);
        assertEquals(Action.SUCCESS, action.reject());
        assertEquals(EntityStatus.REJECTED, action.getOrganization().getStatusCode());
    }
    
    @Test
    public void testMarkAsDuplicate() {
        action.getOrganization().setId(1L);
        action.setDuplicateOfId(2L);
        assertEquals(Action.SUCCESS, action.markAsDuplicate());
        assertEquals(EntityStatus.REJECTED, action.getOrganization().getStatusCode());
        assertEquals(2L, action.getOrganization().getDuplicateOf().getId().longValue());
    }

    @Test
    public void changeCurrentChangeRequest() {
        assertEquals(CurateOrganizationAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(CurateOrganizationAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());
    }

    @Test
    public void testCrProperty() {
        assertNotNull(action.getCr());
        action.setCr(null);
        assertNull(action.getCr());
    }

    @Test
    public void testOrganizationProperty() {
        assertNotNull(action.getOrganization());
        action.setOrganization(null);
        assertNull(action.getOrganization());
    }
    
    @Test
    public void testGetSelectChangeRequests() {
        action.getOrganization().setId(1L);
        OrganizationCR cr1 = new OrganizationCR();
        cr1.setId(1L);
        action.getOrganization().getChangeRequests().add(cr1);
        OrganizationCR cr2 = new OrganizationCR();
        cr2.setId(2L);
        action.getOrganization().getChangeRequests().add(cr2);
        Map<String, String> selectChangeRequests = action.getSelectChangeRequests();
        assertEquals(2, selectChangeRequests.size());
        selectChangeRequests.values();
        int i = 1;
        for (String value : selectChangeRequests.values()) {
            assertEquals("CR-ID-" + i, value);
            i++;
        }
    }
}
