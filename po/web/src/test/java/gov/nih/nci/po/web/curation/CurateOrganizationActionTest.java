package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.po.service.MockOrganizationService;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.util.PoRegistry;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class CurateOrganizationActionTest extends AbstractPoTest {
    private CurateOrganizationAction action;
    private MockOrganizationService mos;

    @Before
    public void setUp() {
        action = new CurateOrganizationAction();
        mos = (MockOrganizationService) PoRegistry.getInstance().getServiceLocator().getOrganizationService();
    }
    
    @Test
    public void testPrepare() throws Exception {
        assertNotNull(action.getOrganization());
        assertNull(action.getOrganization().getId());
        //simulate request param for organization.id is provided
        action.getOrganization().setId(1L);
        //add org to stub's private map so that it may be found.. 
        mos.update(action.getOrganization());
        action.prepare();
        assertNotNull(action.getOrganization());
        assertEquals(1l, action.getOrganization().getId().longValue());
        assertEquals("name", action.getOrganization().getName());
    }
    
    @Test
    public void testPrepareNoOrgId() throws Exception {
        assertNotNull(action.getOrganization());
        action.prepare();
        assertNotNull(action.getOrganization());
        assertEquals(null, action.getOrganization().getId());
    }

    @Test
    public void testStart() {
        assertEquals(Action.SUCCESS, action.start());
    }

    @Test
    public void testCurate() {
        assertEquals(Action.SUCCESS, action.curate());
    }

}
