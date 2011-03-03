/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class AssignOwnershipActionTest extends AbstractPaActionTest {
    private final AssignOwnershipAction action = new AssignOwnershipAction();
    private final RegistryUserService regUserSvc = mock(RegistryUserService.class);
    private final OrganizationEntityServiceRemote orgEntSvc = mock(OrganizationEntityServiceRemote.class);

    @Before
    public void setup() throws TooManyResultsException {
        List<OrganizationDTO> orgList = new ArrayList<OrganizationDTO>();
        OrganizationDTO org = new OrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn("Org Name"));
        org.setIdentifier(IiConverter.convertToIi(1L));
        orgList.add(org);

        when(orgEntSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(orgList);
    }

    @Test
    public void testcsmUsersNamesProperty() {
        assertNull(action.getUsers());
        action.setUsers(new ArrayList<TrialOwner>());
        assertNotNull(action.getUsers());
    }

    @Test
    public void testDisplayAffiliatedOrg() throws TooManyResultsException {
        getRequest().setupAddParameter("orgId", "1");

        action.setOrgEntitySvc(orgEntSvc);
        assertEquals("display_affiliated_org", action.displayAffiliatedOrganization());
        assertEquals("Org Name", action.getCriteria().getAffiliatedOrgName());
        assertEquals(Long.valueOf(1), action.getCriteria().getAffiliatedOrgId());

        when(orgEntSvc.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(0));
        assertEquals("display_affiliated_org", action.displayAffiliatedOrganization());
        assertTrue(action.getActionErrors().iterator().next().startsWith("Too many"));

    }


    @Test
    public void testview() throws PAException {
        RegistryUser regUser = new RegistryUser();
        regUser.setLastName("LAST NAME");
        Set<RegistryUser> regUsers = new HashSet<RegistryUser>();
        regUsers.add(regUser);
        when(regUserSvc.getAllTrialOwners(anyLong())).thenReturn(regUsers);
        action.setRegistryUserService(regUserSvc);

        assertEquals("success",action.view());
        Ii ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.view());

        assertEquals("LAST NAME", action.getTrialOwners().iterator().next().getLastName());

    }
    @Test
    public void testSave() {
        Ii ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        assertEquals(1, action.getActionErrors().size());
        assertTrue(action.getActionErrors().contains("assignOwnership.user.error"));
        action.clearActionErrors();

        getRequest().setupAddParameter("csmUserId", "user1@mail.nih.gov");
        ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        assertEquals(1, action.getActionErrors().size());
        assertTrue(action.getActionErrors().contains("assignOwnership.user.error"));
        action.clearActionErrors();

        getRequest().setupAddParameter("csmUserId", "user3@mail.nih.gov");
        ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
    }
}
