package gov.nih.nci.po.web.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Iterator;
import java.util.Set;

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;


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
        assertNull(action.getOrganization().getPostalAddress());
        action.getOrganization().setStatusCode(EntityStatus.ACTIVE);
        assertEquals(CreateOrganizationAction.INPUT, action.start());
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

}
