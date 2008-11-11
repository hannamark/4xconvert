package gov.nih.nci.po.web.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceStub;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
    public void testResearchOrganizationProperty() {
        assertNotNull(action.getResearchOrganization());
        action.setResearchOrganization(null);
        assertNull(action.getResearchOrganization());
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

        action.getResearchOrganization().setId(null);
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
        action.getResearchOrganization().setId(1L);
        action.getResearchOrganization().setStatus(roleStatus);
        assertTrue(roleStatus.getAllowedTransitions().containsAll(action.getAvailableStatus()));
        assertTrue(action.getAvailableStatus().containsAll(roleStatus.getAllowedTransitions()));
    }

    @Test
    public void testGetAvailableDuplicateOfs() {
        final Long playerId = 1L;

        action.getResearchOrganization().setId(null);
        action.getOrganization().setId(playerId);
        assertNull(action.getAvailableDuplicateOfs());

        action.getResearchOrganization().setId(5L);
        action.getOrganization().setId(playerId);
        assertNull(action.getAvailableDuplicateOfs());

        action = new ResearchOrganizationAction() {
            private static final long serialVersionUID = 1L;

            @Override
            ResearchOrganizationServiceLocal getResearchOrganizationService() {
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
        action.getResearchOrganization().setId(5L);
        action.getOrganization().setId(1L);
        Iterator<ResearchOrganization> iterator = action.getAvailableDuplicateOfs().iterator();
        assertEquals(1L, iterator.next().getId().longValue());
        assertEquals(2L, iterator.next().getId().longValue());
        assertEquals(3L, iterator.next().getId().longValue());
        assertEquals(4L, iterator.next().getId().longValue());
        assertFalse(iterator.hasNext());
    }

}
