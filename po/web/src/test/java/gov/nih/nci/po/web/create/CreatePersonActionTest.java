package gov.nih.nci.po.web.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.web.AbstractPoTest;

import java.util.Iterator;
import java.util.Set;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Action;


public class CreatePersonActionTest extends AbstractPoTest {

    CreatePersonAction action;

    @Before
    public void setUp() {
        action = new CreatePersonAction();
        assertNotNull(action.getPerson());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        Person initial = action.getPerson();
        action.prepare();
        assertSame(initial, action.getPerson());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        action.setRootKey("a");
        action.prepare();
        assertNull(action.getPerson());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        Person o = new Person();
        action.setRootKey("a");
        getSession().setAttribute(action.getRootKey(), o);
        action.prepare();
        assertSame(o, action.getPerson());
    }

    @Test
    public void testStart() {
        assertNull(action.getPerson().getPostalAddress());
        action.getPerson().setStatusCode(EntityStatus.ACTIVE);
        assertEquals(CreatePersonAction.INPUT, action.start());
        assertEquals(EntityStatus.PENDING, action.getPerson().getStatusCode());
        assertNotNull(action.getPerson().getPostalAddress());
        assertNotNull(action.getPerson().getPostalAddress().getCountry());
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
        assertEquals("person.create.success", ActionHelper.getMessages().get(0));
    }

}
