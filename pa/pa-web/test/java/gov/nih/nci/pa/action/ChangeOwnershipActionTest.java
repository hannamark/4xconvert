/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.LabelValueBean;
import gov.nih.nci.service.MockCSMUserService;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class ChangeOwnershipActionTest extends AbstractPaActionTest {
    private ChangeOwnershipAction action = new ChangeOwnershipAction();
    @Before
    public void setup() {
        CSMUserService.getInstance();
        CSMUserService.setRegistryUserService(new MockCSMUserService());
    }
    @Test
    public void testcsmUsersNamesProperty() {
        assertNull(action.getCsmUserNames());
        action.setCsmUserNames(new ArrayList<LabelValueBean>());
        assertNotNull(action.getCsmUserNames());
    }
    @Test
    public void testview() {
        assertEquals("success",action.view());
    }
    @Test 
    public void testSave() {
        Ii ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        assertTrue(action.getActionErrors().contains("Please do not select the same user to change ownership."));
        
        getRequest().setupAddParameter("csmUserId", "user1@mail.nih.gov");
        ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        assertTrue(action.getActionErrors().contains("Please do not select the same user to change ownership."));
        
        getRequest().setupAddParameter("csmUserId", "user3@mail.nih.gov");
        ii = IiConverter.convertToStudyProtocolIi(1L);
        getRequest().getSession().setAttribute(Constants.STUDY_PROTOCOL_II,ii);
        assertEquals("success",action.save());
        
    }
}
