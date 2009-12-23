package gov.nih.nci.po.web.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.web.AbstractPoTest;

import java.util.Iterator;
import java.util.Set;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Action;


public class CreateOrganizationActionTest extends AbstractPoTest {

    CreateOrganizationAction action;

    @Before
    public void setUp() {
        action = new CreateOrganizationAction();
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
        // can only set root key to the key of an object in the session,
        // so after setting the root key, we have to clear out the session manually to test this case
        action.setRootKey("abc-123");
        getSession().clearAttributes();

        action.prepare();
        assertNull(action.getOrganization());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        Organization o = new Organization();
        String rootKey = "a";
        getSession().setAttribute(rootKey, o);
        action.setRootKey(rootKey);
        action.prepare();
        assertSame(o, action.getOrganization());
    }

    @Test
    public void testStart() {
        assertNull(action.getOrganization().getPostalAddress());
        action.getOrganization().setStatusCode(EntityStatus.ACTIVE);
        assertEquals(Action.INPUT, action.start());
        assertEquals(EntityStatus.PENDING, action.getOrganization().getStatusCode());
        assertNotNull(action.getOrganization().getPostalAddress());
        assertNotNull(action.getOrganization().getPostalAddress().getCountry());
    }

    @Test
    public void getAvailableStatus() {
        Set<EntityStatus> availableStatus = action.getAvailableStatus();
        assertEquals(2, availableStatus.size());
        Iterator<EntityStatus> iterator = availableStatus.iterator();
        assertEquals(EntityStatus.PENDING, iterator.next());
        assertEquals(EntityStatus.ACTIVE, iterator.next());
    }

    @Test
    public void create() throws JMSException {
        assertEquals(Action.SUCCESS, action.create());
        assertEquals(1, ActionHelper.getMessages().size());
        assertEquals("organization.create.success", ActionHelper.getMessages().get(0));
    }

    @Test
    public void getOrganizationIdPropertyOrgNull() {
        action.setOrganization(null);
        assertEquals("", action.getOrganizationId());
    }

    @Test
    public void getOrganizationIdPropertyOrgWithId() {
        action.setOrganization(new Organization());
        action.getOrganization().setId(1L);
        assertEquals("1", action.getOrganizationId());
    }
}
