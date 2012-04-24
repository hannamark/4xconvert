/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class OrganizationSearchActionTest extends AbstractPaActionTest {

    /**
     * @throws java.lang.Exception
     */
    @Override
    @Before
    public void setUp() {
        List<Country> countryList = new ArrayList<Country>();
        Country usa = new Country();
        usa.setName("United States");
        usa.setAlpha2("US");
        usa.setAlpha3("USA");
        countryList.add(usa);
        getSession().setAttribute("countrylist", countryList);

        PaRegistry.getInstance().setServiceLocator(
                new MockPaRegistryServiceLocator());
        PoRegistry.getInstance()
                .setPoServiceLocator(new MockPoServiceLocator());

        ServletActionContext.getRequest().getSession()
                .setAttribute(Constants.IS_SU_ABSTRACTOR, Boolean.TRUE);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#execute()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testExecute() throws PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        assertEquals("success", action.execute());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#showDetailspopup()}
     * .
     * @throws PAException 
     * @throws NullifiedRoleException 
     * @throws NullifiedEntityException 
     */
    @Test
    public final void testShowDetailspopup() throws NullifiedEntityException, NullifiedRoleException, PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        action.setOrgID("1");
        String fwd = action.showDetailspopup();
        assertEquals("details", fwd);
        assertNotNull(action.getOrganization());
        assertEquals("OrgName", action.getOrganization().getName());
        assertEquals("1", action.getOrganization().getId());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#query()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testQueryEmptyCriteria() throws PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        String fwd = action.query();
        assertEquals("error", fwd);
        assertTrue(ServletActionContext.getRequest()
                .getAttribute(Constants.FAILURE_MESSAGE).toString()
                .contains("At least one criterion must be provided"));
    }
    
    @Test
    public final void testQuery() throws PAException {
        OrganizationSearchAction action = new OrganizationSearchAction();
        action.getCriteria().setIdentifier("1");
        String fwd = action.query();
        assertEquals("success", fwd);
        assertEquals(1, action.getResults().size());
        assertEquals("4648", action.getResults().get(0).getId());
      
    }
    

}