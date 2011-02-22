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
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class AssignOwnershipActionTest extends AbstractPaActionTest {
    private final AssignOwnershipAction action = new AssignOwnershipAction();
    private final RegistryUserService regUserSvc = mock(RegistryUserService.class);

    @Test
    public void testcsmUsersNamesProperty() {
        assertNull(action.getUsers());
        action.setUsers(new ArrayList<TrialOwner>());
        assertNotNull(action.getUsers());
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
