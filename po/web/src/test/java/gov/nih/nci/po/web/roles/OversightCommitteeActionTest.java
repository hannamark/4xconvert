package gov.nih.nci.po.web.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceStub;
import gov.nih.nci.po.service.OversightCommitteeSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.util.PrivateAccessor;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import java.util.ArrayList;
import java.util.Collection;
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

public class OversightCommitteeActionTest extends AbstractPoTest {
    private OversightCommitteeAction action;

    @Before
    public void setUp() {
        action = new OversightCommitteeAction();
        assertNotNull(action.getRole());
        assertNotNull(action.getCr());
        assertNotNull(action.getOrganization());
    }

    @Test
    public void testUsFormat() {
        assertFalse(action.isUsOrCanadaFormat());
    }


    @Test
    public void testPrepareNoRole() {
        action.setRole(null);
        action.prepare();
        assertNotNull(action.getRole());
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
    public void testPrepareNoOrgId() throws Exception {
        action.setRole(null);
        action.prepare();
        assertSame(action.getOrganization(), action.getRole().getPlayer());

        // calling again exercises the path where the object already has the player set
        Organization o = action.getOrganization();
        action.setOrganization(null);
        action.prepare();
        assertSame(o, action.getRole().getPlayer());
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
        assertEquals(OversightCommitteeSortCriterion.ID.name(), action.getResults().getSortCriterion());
        assertEquals(SortOrderEnum.ASCENDING, action.getResults().getSortDirection());
    }

    @Test
    public void list() {
        assertEquals(Action.SUCCESS, action.list());
    }

    @Test
    public void testAdd() throws JMSException, CSException {
        OversightCommitteeAction action = mock(OversightCommitteeAction.class);
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                User user = mock(User.class);
                return user;
            }
        }).when(action).getLoggedInUser();

        doCallRealMethod().when(action).getBaseRole();
        doCallRealMethod().when(action).getRoleService();
        doCallRealMethod().when(action).add();
        
        assertEquals(Action.SUCCESS, action.add());
    }

    @Test
    public void testEdit() throws JMSException {
        assertEquals(Action.SUCCESS, action.edit());
    }

    @Test
    public void testEditWithDuplicate() throws JMSException {
        OversightCommittee o = new OversightCommittee();
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

    private void verifyAvailStatusForEditForm(RoleStatus roleStatus) {
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

        action = new OversightCommitteeAction() {
            private static final long serialVersionUID = 1L;

            @Override
            protected OversightCommitteeServiceLocal getRoleService() {
                return new OversightCommitteeServiceStub() {
                    @Override
                    public List<OversightCommittee> search(SearchCriteria<OversightCommittee> criteria) {
                        List<OversightCommittee> results = new ArrayList<OversightCommittee>();
                        results.add(create(playerId, 1L));
                        results.add(create(playerId, 2L));
                        results.add(create(playerId, 3L));
                        results.add(create(playerId, 4L));
                        results.add(create(playerId, 5L));
                        return results;
                    }

                    private OversightCommittee create(Long pId, Long id) {
                        OversightCommittee ro = new OversightCommittee();
                        ro.setPlayer(new Organization());
                        ro.getPlayer().setId(pId);
                        ro.setId(id);
                        return ro;
                    }
                };
            }
        };
        action.getRole().setId(5L);
        action.getOrganization().setId(1L);
        Iterator<OversightCommittee> iterator = action.getAvailableDuplicateOfs().iterator();
        assertEquals(1L, iterator.next().getId().longValue());
        assertEquals(2L, iterator.next().getId().longValue());
        assertEquals(3L, iterator.next().getId().longValue());
        assertEquals(4L, iterator.next().getId().longValue());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void changeCurrentChangeRequest() {
        assertEquals(AbstractRoleAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action
                .changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(AbstractRoleAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action
                .changeCurrentChangeRequest());
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
        OversightCommitteeCR cr1 = new OversightCommitteeCR();
        cr1.setId(1L);
        action.getRole().getChangeRequests().add(cr1);
        OversightCommitteeCR cr2 = new OversightCommitteeCR();
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
}
