package gov.nih.nci.po.web.create;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.Iterator;
import java.util.Set;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
        // so after setting the root key, we have to clear out the session
        // manually to test this case
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
        assertEquals(EntityStatus.PENDING, action.getOrganization()
                .getStatusCode());
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
    public void create() throws JMSException, CSException {

        CreateOrganizationAction action = mock(CreateOrganizationAction.class);
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                User user = mock(User.class);
                return user;
            }
        }).when(action).getLoggedInUser();

        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Organization org = new Organization();
                org.setName("Mayo Clinic");
                gov.nih.nci.po.data.bo.Comment comment1 = new gov.nih.nci.po.data.bo.Comment();
                comment1.setValue("test");
                org.getComments().add(comment1);
                return org;
            }
        }).when(action).getOrganization();
        doCallRealMethod().when(action).create();

        assertEquals(Action.SUCCESS, action.create());
        assertEquals(1, ActionHelper.getMessages().size());

    }
}
