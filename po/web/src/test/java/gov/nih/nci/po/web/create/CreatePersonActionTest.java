package gov.nih.nci.po.web.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
        // can only set root key to the key of an object in the session,
        // so after setting the root key, we have to clear out the session
        // manually to test this case
        action.setRootKey("abc-123");
        getSession().clearAttributes();

        action.prepare();
        assertNull(action.getPerson());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        Person o = new Person();
        String rootKey = "a";
        getSession().setAttribute(rootKey, o);
        action.setRootKey(rootKey);
        action.prepare();
        assertSame(o, action.getPerson());
    }

    @Test
    public void testStart() {
        assertNull(action.getPerson().getPostalAddress());
        action.getPerson().setStatusCode(EntityStatus.ACTIVE);
        assertEquals(Action.INPUT, action.start());
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
    public void create() throws Exception {

        CreatePersonAction action = mock(CreatePersonAction.class);
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                User user = mock(User.class);
                return user;
            }
        }).when(action).getLoggedInUser();

        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Person person = new Person();
                person.setFirstName("Rohit");
                person.setMiddleName("L");
                person.setLastName("Gupta");
                gov.nih.nci.po.data.bo.Comment comment1 = new gov.nih.nci.po.data.bo.Comment();
                comment1.setValue("test");
                person.getComments().add(comment1);
                return person;
            }
        }).when(action).getPerson();
        doCallRealMethod().when(action).create();

        assertEquals(Action.SUCCESS, action.create());
        assertEquals(1, ActionHelper.getMessages().size());
    }

    @Test
    public void getPersonIdPropertyOrgNull() {
        action.setPerson(null);
        assertEquals("", action.getPersonId());
    }

    @Test
    public void getPersonIdPropertyOrgWithId() {
        action.setPerson(new Person());
        action.getPerson().setId(1L);
        assertEquals("1", action.getPersonId());
    }
}
