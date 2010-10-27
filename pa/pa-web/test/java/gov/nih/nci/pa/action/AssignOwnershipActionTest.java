/**
 *
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class AssignOwnershipActionTest extends AbstractPaActionTest {
    private AssignOwnershipAction action = new AssignOwnershipAction();

    @Test
    public void testcsmUsersNamesProperty() {
        assertNull(action.getUsers());
        action.setUsers(new ArrayList<TrialOwner>());
        assertNotNull(action.getUsers());
    }
    @Test
    public void testview() {
        assertEquals("success",action.view());
        Ii ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.view());

    }
    @Test
    public void testSave() {
        Ii ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        assertTrue(action.getActionErrors().contains("Please select user to change ownership."));

        getRequest().setupAddParameter("csmUserId", "user1@mail.nih.gov");
        ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        assertTrue(action.getActionErrors().contains("Please select user to change ownership."));

        getRequest().setupAddParameter("csmUserId", "user3@mail.nih.gov");
        ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());

    }
}
