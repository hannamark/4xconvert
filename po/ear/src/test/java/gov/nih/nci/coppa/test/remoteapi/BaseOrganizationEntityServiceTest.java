package gov.nih.nci.coppa.test.remoteapi;

import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;

public abstract class BaseOrganizationEntityServiceTest {
    protected OrganizationEntityServiceRemote orgService;

    /**
     * Init the test.
     * 
     * @throws NamingException on error.
     */
    @Before
    public void init() throws NamingException {
        if (orgService == null) {
            orgService = RemoteServiceHelper.getOrganizationEntityService();
        }
    }

    /**
     * cleanup after test is complete.
     * 
     * @throws NamingException on error.
     */
    @After
    public void cleanup() throws NamingException {
        orgService = null;
        RemoteServiceHelper.close();
    }
}
