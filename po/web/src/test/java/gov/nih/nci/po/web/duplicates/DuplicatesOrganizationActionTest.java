package gov.nih.nci.po.web.duplicates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.service.DuplicatesOrganizationSearchCriteria;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;


public class DuplicatesOrganizationActionTest extends AbstractPoTest {

    
    @Test
    public void search() {
        
        assertEquals(ActionSupport.SUCCESS, action.search());
    }
    
    private DuplicatesOrganizationAction action;

    @Before
    public void setUp() {
        action = new DuplicatesOrganizationAction();
        assertNotNull(action.getCriteria());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        DuplicatesOrganizationSearchCriteria initial = action.getCriteria();
        action.prepare();
        assertSame(initial, action.getCriteria());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        action.setRootKey("a");
        action.prepare();
        assertNull(action.getCriteria());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        DuplicatesOrganizationSearchCriteria c = new DuplicatesOrganizationSearchCriteria();
        action.setRootKey("a");
        getSession().setAttribute(action.getRootKey(), c);
        action.prepare();
        assertSame(c, action.getCriteria());
    }
    
    @Test
    public void testStart() {
        assertEquals(DuplicatesOrganizationAction.SUCCESS, action.start());
    }

}
