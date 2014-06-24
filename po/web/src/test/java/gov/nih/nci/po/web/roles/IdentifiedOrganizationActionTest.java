package gov.nih.nci.po.web.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceStub;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.util.PrivateAccessor;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.displaytag.properties.SortOrderEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.opensymphony.xwork2.Action;

public class IdentifiedOrganizationActionTest extends AbstractPoTest {
    private IdentifiedOrganizationAction action;

    @Before
    public void setUp() {
        action = new IdentifiedOrganizationAction();
        assertNotNull(action.getRole());
        assertNotNull(action.getCr());
        assertNotNull(action.getOrganization());
    }

    @Test
    public void testPrepareNoRole() {
        action.setRole(null);
        action.prepare();
        assertNotNull(action.getRole());
    }
    
    @Test
    public void testUsFormat() {
        assertFalse(action.isUsOrCanadaFormat());
    }

    @Test
    public void testPrepareWithRoleId() {
        Correlation role = action.getRole();
        action.getRole().setId(1L);
        action.prepare();
        assertNotSame(role, action.getRole());
        assertEquals(1L, action.getRole().getId().longValue());
    }

    @Test
    public void testPrepare() throws Exception {
        action.setRole(null);
        assertNull(action.getRole());
        action.setOrganization(new Organization());
        action.prepare();
        assertSame(action.getOrganization(), action.getRole().getPlayer());
        assertNotNull(action.getRole().getScoper());
        assertNotNull(action.getRole().getAssignedIdentifier());
        assertNull(action.getRole().getAssignedIdentifier().getNullFlavor());

        // calling again exercises the path where the object already has the player set
        Organization o = action.getOrganization();
        Organization scoper = action.getRole().getScoper();
        Ii id = action.getRole().getAssignedIdentifier();
        action.setOrganization(null);
        action.prepare();
        assertSame(o, action.getRole().getPlayer());
        assertSame(scoper, action.getRole().getScoper());
        assertSame(id, action.getRole().getAssignedIdentifier());
        assertNull(action.getRole().getAssignedIdentifier().getNullFlavor());
    }

    @Test
    public void testStart() {
        assertEquals(Action.SUCCESS, action.start());
    }

    @Test
    public void testOrganizationProperty() {
        assertNotNull(action.getOrganization());
        action.setOrganization(null);
        assertNull(action.getOrganization());
    }

    @Test
    public void testRoleProperty() {
        assertNotNull(action.getRole());
        action.setRole(null);
        assertNull(action.getRole());
    }

    @Test
    public void testResultsProperty() {
        assertNotNull(action.getResults());
        assertEquals(0, action.getResults().getFullListSize());
        assertNotNull(action.getResults().getList());
        assertEquals(PoRegistry.DEFAULT_RECORDS_PER_PAGE, action.getResults().getObjectsPerPage());
        assertEquals(1, action.getResults().getPageNumber());
        assertEquals(null, action.getResults().getSearchId());
        assertEquals(ResearchOrganizationSortCriterion.ID.name(), action.getResults().getSortCriterion());
        assertEquals(SortOrderEnum.ASCENDING, action.getResults().getSortDirection());
    }

    @Test
    public void list() {
        assertEquals(Action.SUCCESS, action.list());
    }

    @Test
    public void testAdd() throws JMSException, CSException {
        IdentifiedOrganizationAction action = mock(IdentifiedOrganizationAction.class);
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                User user = mock(User.class);
                return user;
            }
        }).when(action).getLoggedInUser();

        doCallRealMethod().when(action).setRole(isA(IdentifiedOrganization.class));
        doCallRealMethod().when(action).getRole();
        doCallRealMethod().when(action).getBaseRole();
        doCallRealMethod().when(action).getRoleService();
        doCallRealMethod().when(action).add();
        
        action.setRole(new IdentifiedOrganization());
        action.getRole().setScoper(new Organization());
        action.getRole().getScoper().setId(5L);
        assertEquals(Action.SUCCESS, action.add());
    }

    @Test
    public void testEdit() throws JMSException {
        assertEquals(Action.SUCCESS, action.edit());
    }

    @Test
    public void testEditWithDuplicate() throws JMSException {
        IdentifiedOrganization o = new IdentifiedOrganization();
        action.setDuplicateOf(o);
        assertEquals(Action.SUCCESS, action.edit());
        assertNull(action.getRole().getDuplicateOf());

        o.setId(1L);
        action.setDuplicateOf(o);
        assertEquals(Action.SUCCESS, action.edit());
        assertEquals(1, action.getRole().getDuplicateOf().getId().longValue());
    }

    @Test
    public void testGetAvailableStatusForAddForm() {
        List<RoleStatus> expected = new ArrayList<RoleStatus>();
        expected.add(RoleStatus.PENDING);
        expected.add(RoleStatus.ACTIVE);

        action.getRole().setId(null);
        Collection<RoleStatus> availableStatus = action.getAvailableStatus();

        assertTrue(availableStatus.containsAll(expected));
        assertTrue(expected.containsAll(availableStatus));
    }

    @Test
    public void testGetAvailableStatusForEditForm() {
        verifyAvailStatusForEditForm(RoleStatus.ACTIVE);
        verifyAvailStatusForEditForm(RoleStatus.NULLIFIED);
        verifyAvailStatusForEditForm(RoleStatus.PENDING);
        verifyAvailStatusForEditForm(RoleStatus.SUSPENDED);
    }

    private void verifyAvailStatusForEditForm(RoleStatus roleStatus){
        action.getRole().setId(1L);
        PrivateAccessor.invokePrivateMethod(action.getRole(), AbstractRole.class, "setPriorAsString",
                new Object[] { roleStatus.name() });
        assertTrue(roleStatus.getAllowedTransitions().containsAll(action.getAvailableStatus()));
        assertTrue(action.getAvailableStatus().containsAll(roleStatus.getAllowedTransitions()));
    }

    @Test
    public void testGetAvailableDuplicateOfs() {
        final Long playerId = 1L;

        action.getRole().setId(null);
        action.getOrganization().setId(playerId);
        assertNull(action.getAvailableDuplicateOfs());

        action.getRole().setId(5L);
        action.getOrganization().setId(playerId);
        assertNull(action.getAvailableDuplicateOfs());

        action = new IdentifiedOrganizationAction() {
            private static final long serialVersionUID = 1L;

            @Override
            protected IdentifiedOrganizationServiceLocal getRoleService() {
                return new IdentifiedOrganizationServiceStub() {
                    @Override
                    public List<IdentifiedOrganization> search(SearchCriteria<IdentifiedOrganization> criteria) {
                        List<IdentifiedOrganization> results = new ArrayList<IdentifiedOrganization>();
                        results.add(create(playerId, 1L));
                        results.add(create(playerId, 2L));
                        results.add(create(playerId, 3L));
                        results.add(create(playerId, 4L));
                        results.add(create(playerId, 5L));
                        return results;
                    }

                    private IdentifiedOrganization create(Long pId, Long id) {
                        IdentifiedOrganization ro = new IdentifiedOrganization();
                        ro.setPlayer(new Organization());
                        ro.getPlayer().setId(pId);
                        ro.setId(id);
                        return ro;
                    }
                };
            };
        };
        action.getRole().setId(5L);
        action.getOrganization().setId(1L);
        Iterator<IdentifiedOrganization> iterator = action.getAvailableDuplicateOfs().iterator();
        assertEquals(1L, iterator.next().getId().longValue());
        assertEquals(2L, iterator.next().getId().longValue());
        assertEquals(3L, iterator.next().getId().longValue());
        assertEquals(4L, iterator.next().getId().longValue());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void changeCurrentChangeRequest() {
        assertEquals(AbstractRoleAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(AbstractRoleAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());
    }

    @Test
    public void testCrProperty() {
        assertNotNull(action.getCr());
        action.setCr(null);
        assertNull(action.getCr());
    }

    @Test
    public void testGetSelectChangeRequests() {
        action.getRole().setId(1L);
        IdentifiedOrganizationCR cr1 = new IdentifiedOrganizationCR();
        cr1.setId(1L);
        action.getRole().getChangeRequests().add(cr1);
        IdentifiedOrganizationCR cr2 = new IdentifiedOrganizationCR();
        cr2.setId(2L);
        action.getRole().getChangeRequests().add(cr2);
        Map<String, String> selectChangeRequests = action.getSelectChangeRequests();
        assertEquals(2, selectChangeRequests.size());
        selectChangeRequests.values();
        int i = 1;
        for (String value : selectChangeRequests.values()) {
            assertEquals("CR-ID-" + i, value);
            i++;
        }
    }
    
    @Test
    public void testGetAvailableReliability() {
        assertEquals(2, action.getAvailableReliability().size());
        Collection<IdentifierReliability> c = new HashSet<IdentifierReliability>();
        c.add(IdentifierReliability.UNV);
        c.add(IdentifierReliability.VRF);
        assertFalse(action.getAvailableReliability().retainAll(c));
        assertEquals(2, action.getAvailableReliability().size());
    }
}
