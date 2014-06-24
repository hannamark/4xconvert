package gov.nih.nci.po.web.roles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceStub;
import gov.nih.nci.po.service.ResearchOrganizationSortCriterion;
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

public class HealthCareProviderActionTest extends AbstractPoTest {
    private HealthCareProviderAction action;

    @Before
    public void setUp() {
        action = new HealthCareProviderAction();
        assertNotNull(action.getRole());
        assertNotNull(action.getCr());
        assertNotNull(action.getPerson());
    }

    @Test
    public void testUsFormat() {
        action.setRole(null);
        action.prepare();
        assertFalse(action.isUsOrCanadaFormat());
        Address addr1 = new Address("defaultStreetAddress",
                "cityOrMunicipality", "defaultState", "12345", new Country(
                        "United States", "840", "US", "USA"));
        action.getRole().getPostalAddresses().add(addr1);
        assertTrue(action.isUsOrCanadaFormat());
        Address addr2 = new Address("defaultStreetAddress",
                "cityOrMunicipality", "defaultState", "12345", new Country(
                        "Tazmania", "999", "TZ", "TAZ"));
        action.getRole().getPostalAddresses().add(addr2);
        assertFalse(action.isUsOrCanadaFormat());
    }

    @Test
    public void testPrepareNoPersonId() throws Exception {
        action.setRole(null);
        action.prepare();
        assertSame(action.getPerson(), action.getRole().getPlayer());

        // calling again exercises the path where the object already has the
        // player set
        Person o = action.getPerson();
        action.setPerson(null);
        action.prepare();
        assertSame(o, action.getRole().getPlayer());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        HealthCareProvider initial = action.getRole();
        action.prepare();
        assertSame(initial, action.getRole());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        // can only set root key to the key of an object in the session,
        // so after setting the root key, we have to clear out the session
        // manually to test this case
        action.setRootKey("abc-123");
        getSession().clearAttributes();

        action.prepare();
        // assertNull(action.getRole());
        assertNotNull(action.getRole());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        HealthCareProvider o = new HealthCareProvider();
        String rootKey = "a";
        getSession().setAttribute(rootKey, o);
        action.setRootKey(rootKey);
        action.prepare();
        assertSame(o, action.getRole());
    }

    @Test
    public void testStart() {
        assertEquals(Action.SUCCESS, action.start());
    }

    @Test
    public void testOrganizationProperty() {
        assertNotNull(action.getPerson());
        action.setPerson(null);
        assertNull(action.getPerson());
    }

    @Test
    public void testRoleProperty() {
        assertSame(action.getRole(), action.getBaseRole());
        assertNotNull(action.getRole());
        action.setRole(null);
        assertNull(action.getRole());

        action.setBaseRole(new HealthCareProvider());
        assertSame(action.getRole(), action.getBaseRole());
    }

    @Test
    public void testResultsProperty() {
        assertNotNull(action.getResults());
        assertEquals(0, action.getResults().getFullListSize());
        assertNotNull(action.getResults().getList());
        assertEquals(PoRegistry.DEFAULT_RECORDS_PER_PAGE, action.getResults()
                .getObjectsPerPage());
        assertEquals(1, action.getResults().getPageNumber());
        assertEquals(null, action.getResults().getSearchId());
        assertEquals(ResearchOrganizationSortCriterion.ID.name(), action
                .getResults().getSortCriterion());
        assertEquals(SortOrderEnum.ASCENDING, action.getResults()
                .getSortDirection());
    }

    @Test
    public void list() {
        assertEquals(Action.SUCCESS, action.list());
    }

    @Test
    public void testAdd() throws JMSException, CSException {

        HealthCareProviderAction action = mock(HealthCareProviderAction.class);
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
        HealthCareProvider o = new HealthCareProvider();
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
        PrivateAccessor.invokePrivateMethod(action.getRole(),
                AbstractRole.class, "setPriorAsString",
                new Object[] { roleStatus.name() });
        assertTrue(roleStatus.getAllowedTransitions().containsAll(
                action.getAvailableStatus()));
        assertTrue(action.getAvailableStatus().containsAll(
                roleStatus.getAllowedTransitions()));
    }

    @Test
    public void testGetAvailableDuplicateOfs() {
        final Long playerId = 1L;

        action.getRole().setId(null);
        action.getPerson().setId(playerId);
        assertNull(action.getAvailableDuplicateOfs());

        action.getRole().setId(5L);
        action.getPerson().setId(playerId);
        assertNull(action.getAvailableDuplicateOfs());

        action = new HealthCareProviderAction() {
            private static final long serialVersionUID = 1L;

            @Override
            protected HealthCareProviderServiceLocal getRoleService() {
                return new HealthCareProviderServiceStub() {
                    @Override
                    public List<HealthCareProvider> search(
                            SearchCriteria<HealthCareProvider> criteria) {
                        List<HealthCareProvider> results = new ArrayList<HealthCareProvider>();
                        results.add(create(playerId, 1L));
                        results.add(create(playerId, 2L));
                        results.add(create(playerId, 3L));
                        results.add(create(playerId, 4L));
                        results.add(create(playerId, 5L));
                        return results;
                    }

                    private HealthCareProvider create(Long pId, Long id) {
                        HealthCareProvider ro = new HealthCareProvider();
                        ro.setPlayer(new Person());
                        ro.getPlayer().setId(pId);
                        ro.setId(id);
                        return ro;
                    }
                };
            }
        };
        action.getRole().setId(5L);
        action.getPerson().setId(1L);
        Iterator<HealthCareProvider> iterator = action
                .getAvailableDuplicateOfs().iterator();
        assertEquals(1L, iterator.next().getId().longValue());
        assertEquals(2L, iterator.next().getId().longValue());
        assertEquals(3L, iterator.next().getId().longValue());
        assertEquals(4L, iterator.next().getId().longValue());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void changeCurrentChangeRequest() {
        assertEquals(AbstractRoleAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT,
                action.changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(AbstractRoleAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT,
                action.changeCurrentChangeRequest());
    }

    @Test
    public void testCrProperty() {
        assertSame(action.getCr(), action.getBaseCr());
        assertNotNull(action.getCr());
        action.setCr(null);
        assertNull(action.getCr());

        action.setBaseCr(new HealthCareProviderCR());
        assertSame(action.getCr(), action.getBaseCr());
    }

    @Test
    public void testGetSelectChangeRequests() {
        action.getRole().setId(1L);
        HealthCareProviderCR cr1 = new HealthCareProviderCR();
        cr1.setId(1L);
        action.getRole().getChangeRequests().add(cr1);
        HealthCareProviderCR cr2 = new HealthCareProviderCR();
        cr2.setId(2L);
        action.getRole().getChangeRequests().add(cr2);
        Map<String, String> selectChangeRequests = action
                .getSelectChangeRequests();
        assertEquals(2, selectChangeRequests.size());
        selectChangeRequests.values();
        int i = 1;
        for (String value : selectChangeRequests.values()) {
            assertEquals("CR-ID-" + i, value);
            i++;
        }
    }

    @Test
    public void testRootKeyProperty() {
        assertNull(action.getRootKey());
        action.setRootKey("abc-123");
        assertNotNull(action.getRootKey());
        action.setRootKey("");
        assertNotNull(action.getRootKey());
        action.setRootKey(null);
        assertNull(action.getRootKey());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRootKeyProperty() {
        assertNull(action.getRootKey());
        action.setRootKey("abc-321");
    }

}
