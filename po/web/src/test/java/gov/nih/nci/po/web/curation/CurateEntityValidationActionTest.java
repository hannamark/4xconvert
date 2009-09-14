package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;


public class CurateEntityValidationActionTest extends AbstractPoTest {

    private CurateEntityValidationAction action;

    @Before
    public void setUp() {
        action = new CurateEntityValidationAction();
        assertNotNull(action.getOrganization());
        assertNotNull(action.getDuplicateOf());
    }
    

    @Test
    public void testOrganizationProperty() {
        assertNotNull(action.getOrganization());
        action.setOrganization(null);
        assertNull(action.getOrganization());
    }


    @Test
    public void testDuplicateOfProperty() {
        assertNotNull(action.getDuplicateOf());
        action.setDuplicateOf(null);
        assertNull(action.getDuplicateOf());
    }

    @Test
    public void testPrepare() {
        try {
            action.prepare();
            fail("expected NPE");
        } catch (NullPointerException e) {
        }
        action.getOrganization().setId(1L);
        try {
            action.prepare();
            fail("expected NPE");
        } catch (NullPointerException e) {
        }
        action.getDuplicateOf().setId(1L);
        action.prepare();
    }
    
    @Test
    public void testGetAssociatedPlayedRolesForOrganization() {
        assertTrue(action.getAssociatedPlayedRolesForOrganization().isEmpty());
    }
}
