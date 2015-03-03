/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.web.roles;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.opensymphony.xwork2.Action;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceStub;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * @author Scott Miller
 * @author Rohit Gupta
 *
 */
public class HealthCareFacilityActionTest extends AbstractRoleActionTest {
    private HealthCareFacilityAction action;

    @Before
    public void setUp() {
        action = new HealthCareFacilityAction();
        assertNotNull(action.getRole());
        assertNotNull(action.getCr());
        assertNotNull(action.getOrganization());
        getSession().setAttribute("a-1", new Object());
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
    public void testInput() {
        assertNull(action.getRootKey());
        assertNull(action.getRole().getId());
        assertEquals(Action.INPUT, action.input());
        assertEquals(RoleStatus.PENDING, action.getRole().getStatus());
        assertNotNull(action.getRootKey());
        assertTrue(action.getRootKey().startsWith("hcf"));
    }

    @Test
    public void testInitialize() {
        action.initialize(action.getRole());
        action.initializeCollections(action.getRole());
    }

    @Test
    public void testPrepareNoPersonId() throws Exception {
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
    public void testPrepareNoRootKey() throws Exception {
        HealthCareFacility initial = action.getRole();
        action.prepare();
        assertSame(initial, action.getRole());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        // can only set root key to the key of an object in the session,
        // so after setting the root key, we have to clear out the session manually to test this case
        action.setRootKey("abc-123");
        getSession().clearAttributes();

        action.prepare();
        //assertNull(action.getRole());
        assertNotNull(action.getRole());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        HealthCareFacility o = new HealthCareFacility();
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
        assertNotNull(action.getOrganization());
        action.setOrganization(null);
        assertNull(action.getOrganization());
    }

    @Test
    public void testRoleProperty() {
        assertSame(action.getRole(), action.getBaseRole());
        assertNotNull(action.getRole());
        action.setRole(null);
        assertNull(action.getRole());

        action.setBaseRole(new HealthCareFacility());
        assertSame(action.getRole(), action.getBaseRole());
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
        HealthCareFacilityAction action = mock(HealthCareFacilityAction.class);
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
        HealthCareFacility o = new HealthCareFacility();
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
                new Object[]{roleStatus.name()});
        Collection<RoleStatus> allowedTransitions = new ArrayList<RoleStatus>();

   
        allowedTransitions.addAll(roleStatus.getAllowedTransitions());
       

        if (roleStatus != RoleStatus.ACTIVE) {
            allowedTransitions.remove(RoleStatus.ACTIVE);
        }

        assertTrue(allowedTransitions.containsAll(getAction().getAvailableStatus()));
        assertTrue(getAction().getAvailableStatus().containsAll(allowedTransitions));
    }

    @Test
    public void testGetAvailableStatusForEditForm() {
        HealthCareFacility role = action.getRole();
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

        action = new HealthCareFacilityAction() {
            private static final long serialVersionUID = 1L;

            @Override
            protected HealthCareFacilityServiceLocal getRoleService() {
                return new HealthCareFacilityServiceStub() {
                    @Override
                    public List<HealthCareFacility> search(SearchCriteria<HealthCareFacility> criteria) {
                        List<HealthCareFacility> results = new ArrayList<HealthCareFacility>();
                        results.add(create(playerId, 1L));
                        results.add(create(playerId, 2L));
                        results.add(create(playerId, 3L));
                        results.add(create(playerId, 4L));
                        results.add(create(playerId, 5L));
                        return results;
                    }

                    private HealthCareFacility create(Long pId, Long id) {
                        HealthCareFacility ro = new HealthCareFacility();
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
        Iterator<HealthCareFacility> iterator = action.getAvailableDuplicateOfs().iterator();
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
        assertSame(action.getCr(), action.getBaseCr());
        assertNotNull(action.getCr());
        action.setCr(null);
        assertNull(action.getCr());

        action.setBaseCr(new HealthCareFacilityCR());
        assertSame(action.getCr(), action.getBaseCr());
    }

    @Test
    public void testGetSelectChangeRequests() {
        action.getRole().setId(1L);
        HealthCareFacilityCR cr1 = new HealthCareFacilityCR();
        cr1.setId(1L);
        action.getRole().getChangeRequests().add(cr1);
        HealthCareFacilityCR cr2 = new HealthCareFacilityCR();
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

    /**
     * {@inheritDoc}
     */
    @Override
    AbstractRoleAction<HealthCareFacility, HealthCareFacilityCR, HealthCareFacilityServiceLocal> getAction() {
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
