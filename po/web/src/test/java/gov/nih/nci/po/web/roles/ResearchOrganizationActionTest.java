package gov.nih.nci.po.web.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceStub;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.util.PrivateAccessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.displaytag.properties.SortOrderEnum;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class ResearchOrganizationActionTest extends AbstractPoTest {
    private ResearchOrganizationAction action;

    @Before
    public void setUp() {
        action = new ResearchOrganizationAction();
    }

    @Test
    public void testPrepareNoOrgId() throws Exception {
        action.prepare();
    }

    @Test
    public void testPrepareWithOrgId() throws Exception {
        action.getOrganization().setId(1L);
        assertNull(action.getOrganization().getName());
        action.prepare();
        assertEquals(1l, action.getOrganization().getId().longValue());
        assertEquals("name", action.getOrganization().getName());
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
    public void testAdd() throws JMSException {
        assertEquals(Action.SUCCESS, action.add());
    }

    @Test
    public void testEdit() throws JMSException {
        assertEquals(Action.SUCCESS, action.edit());
    }

    @Test
    public void testGetAvailableStatusForAddForm() {
        List<EntityStatus> expected = new ArrayList<EntityStatus>();
        expected.add(EntityStatus.PENDING);
        expected.add(EntityStatus.ACTIVE);

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
        PrivateAccessor.invokePrivateMethod(action.getRole(), AbstractOrganizationRole.class, "setPriorAsString",
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

                    private ResearchOrganization create(Long playerId, Long id) {
                        ResearchOrganization ro = new ResearchOrganization();
                        ro.setPlayer(new Organization());
                        ro.getPlayer().setId(playerId);
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
        assertEquals(ResearchOrganizationAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action
                .changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(ResearchOrganizationAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action
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
}
