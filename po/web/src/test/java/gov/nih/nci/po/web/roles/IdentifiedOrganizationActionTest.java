package gov.nih.nci.po.web.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.AbstractIdentifiedEntity;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceStub;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.util.PrivateAccessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.commons.beanutils.BeanUtils;
import org.displaytag.properties.SortOrderEnum;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class IdentifiedOrganizationActionTest extends AbstractPoTest {
    private IdentifiedOrganizationAction action;

    @Before
    public void setUp() {
        action = new IdentifiedOrganizationAction();
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
    public void testGetAvailableStatusForAddForm() {
        List expected = new ArrayList();
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

    private void verifyAvailStatusForEditForm(RoleStatus roleStatus){
        action.getRole().setId(1L);
        PrivateAccessor.invokePrivateMethod(action.getRole(), AbstractIdentifiedEntity.class, "setPriorAsString",
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

                    private IdentifiedOrganization create(Long playerId, Long id) {
                        IdentifiedOrganization ro = new IdentifiedOrganization();
                        ro.setPlayer(new Organization());
                        ro.getPlayer().setId(playerId);
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
        assertEquals(ResearchOrganizationAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(ResearchOrganizationAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());
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
}
