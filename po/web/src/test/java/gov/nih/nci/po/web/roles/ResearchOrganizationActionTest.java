package gov.nih.nci.po.web.roles;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.opensymphony.xwork2.Action;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceStub;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PrivateAccessor;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import org.displaytag.properties.SortOrderEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class ResearchOrganizationActionTest extends AbstractRoleActionTest {
    private ResearchOrganizationAction action;

    @Before
    public void setUp() {
        action = new ResearchOrganizationAction();
        assertNotNull(action.getRole());
        assertNotNull(action.getCr());
        assertNotNull(action.getOrganization());
    }
    
    @Test
    public void testUsFormat() {
        action.setRole(null);
        action.prepare();
        assertFalse(action.isUsOrCanadaFormat());
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        action.getRole().getPostalAddresses().add(addr1);
        assertTrue(action.isUsOrCanadaFormat());
        Address addr2 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("Tazmania", "999", "TZ", "TAZ"));
        action.getRole().getPostalAddresses().add(addr2);
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
    @SuppressWarnings("deprecation")
    public void testPrepareNoOrgId() throws Exception {
        action.setRole(null);
        action.prepare();
        assertSame(action.getRole().getTypeCode(), action.getResearchOrganizationType());
        assertSame(action.getOrganization(), action.getRole().getPlayer());

        action.setResearchOrganizationType(new ResearchOrganizationType());
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
        assertEquals(ResearchOrganizationSortCriterion.ID.name(), action.getResults().getSortCriterion());
        assertEquals(SortOrderEnum.ASCENDING, action.getResults().getSortDirection());
    }

    @Test
    public void list() {
        assertEquals(Action.SUCCESS, action.list());
    }

    @Test
    public void testAdd() throws JMSException, CSException {
        ResearchOrganizationAction action = mock(ResearchOrganizationAction.class);
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
        ResearchOrganization o = new ResearchOrganization();
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

        action.getRole().setId(null);
        Collection<RoleStatus> availableStatus = action.getAvailableStatus();

        assertTrue(availableStatus.containsAll(expected));
        assertTrue(expected.containsAll(availableStatus));
    }


    @Override
    protected void verifyAvailStatusForEditForm(AbstractRole role, RoleStatus roleStatus) {
        role.setId(1L);
        PrivateAccessor.invokePrivateMethod(role, AbstractRole.class, "setPriorAsString",
                new Object[] { roleStatus.name() });
        Collection<RoleStatus> allowedTransitions = new ArrayList<RoleStatus>();

        if (roleStatus == RoleStatus.PENDING) {
            allowedTransitions.add(RoleStatus.PENDING);
        } else {
            allowedTransitions.addAll(roleStatus.getAllowedTransitions());

        }

        if (roleStatus != RoleStatus.ACTIVE) {
            allowedTransitions.remove(RoleStatus.ACTIVE);
        }

        assertTrue(allowedTransitions.containsAll(getAction().getAvailableStatus()));
        assertTrue(getAction().getAvailableStatus().containsAll(allowedTransitions));
    }
    
    @Test
    public void testGetAvailableStatusForEditForm() {
        ResearchOrganization role = action.getRole();
        verifyAvailStatusForEditForm(role, RoleStatus.ACTIVE);
        verifyAvailStatusForEditForm(role, RoleStatus.NULLIFIED);
        verifyAvailStatusForEditForm(role, RoleStatus.PENDING);
        verifyAvailStatusForEditForm(role, RoleStatus.SUSPENDED);
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

        action = new ResearchOrganizationAction() {
            private static final long serialVersionUID = 1L;

            @Override
            protected ResearchOrganizationServiceLocal getRoleService() {
                return new ResearchOrganizationServiceStub() {
                    @Override
                    public List<ResearchOrganization> search(SearchCriteria<ResearchOrganization> criteria) {
                        List<ResearchOrganization> results = new ArrayList<ResearchOrganization>();
                        results.add(create(playerId, 1L));
                        results.add(create(playerId, 2L));
                        results.add(create(playerId, 3L));
                        results.add(create(playerId, 4L));
                        results.add(create(playerId, 5L));
                        return results;
                    }

                    private ResearchOrganization create(Long pId, Long id) {
                        ResearchOrganization ro = new ResearchOrganization();
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
        Iterator<ResearchOrganization> iterator = action.getAvailableDuplicateOfs().iterator();
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
        ResearchOrganizationCR cr1 = new ResearchOrganizationCR();
        cr1.setId(1L);
        action.getRole().getChangeRequests().add(cr1);
        ResearchOrganizationCR cr2 = new ResearchOrganizationCR();
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

    @Override
    AbstractRoleAction<?, ?, ?> getAction() {
        return action;
    }

    @Test
    public void testUpdatePendingToActive() throws JMSException {

        PrivateAccessor.invokePrivateMethod(action.getRole(), AbstractRole.class, "setPriorAsString",
                new Object[] { RoleStatus.PENDING.name() });

        action.getRole().setStatus(RoleStatus.ACTIVE);

        String response = action.edit();

        assertEquals(Action.ERROR, response);
    }
}
