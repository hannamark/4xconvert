/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.TooManyResultsException;
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
public class PersonSearchActionTest extends AbstractPaActionTest {

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
     * {@link gov.nih.nci.pa.action.PersonSearchAction#execute()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testExecute() throws PAException {
        PersonSearchAction action = new PersonSearchAction();
        assertEquals("success", action.execute());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.OrganizationSearchAction#query()}.
     * 
     * @throws PAException
     */
    @Test
    public final void testQueryEmptyCriteria() throws PAException {
        PersonSearchAction action = new PersonSearchAction();
        String fwd = action.query();
        assertEquals("error", fwd);
        assertTrue(ServletActionContext.getRequest()
                .getAttribute(Constants.FAILURE_MESSAGE).toString()
                .contains("At least one criterion must be provided"));
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PersonSearchAction#query()}.
     * @throws PAException 
     */
    @Test
    public final void testQuery() throws PAException {
        PersonSearchAction action = new PersonSearchAction();
        action.getCriteria().setId("1");
        String fwd = action.query();
        assertEquals("success", fwd);
        assertEquals(1, action.getResults().size());
        assertEquals(1, action.getResults().get(0).getId().longValue());
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.action.PersonSearchAction#showDetailspopup()}.
     * @throws TooManyResultsException 
     * @throws PAException 
     * @throws NullifiedRoleException 
     * @throws NullifiedEntityException 
     */
    @Test
    public final void testShowDetailspopup() throws NullifiedEntityException, NullifiedRoleException, PAException, TooManyResultsException {
        PersonSearchAction action = new PersonSearchAction();
        action.setPersonID("1");
        String fwd = action.showDetailspopup();
        assertEquals("details", fwd);
        assertNotNull(action.getPerson());
        assertEquals("1", action.getPerson().getId().toString());
    }
}
