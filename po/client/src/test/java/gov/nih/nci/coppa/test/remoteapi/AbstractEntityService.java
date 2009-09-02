package gov.nih.nci.coppa.test.remoteapi;

import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractEntityService {
    private OrganizationEntityServiceRemote orgService;
    private PersonEntityServiceRemote personService;
    private PatientCorrelationServiceRemote patientService;

    public OrganizationEntityServiceRemote getOrgService() {
        return orgService;
    }
    
    public PersonEntityServiceRemote getPersonService() {
        return personService;
    }
    
    public PatientCorrelationServiceRemote getPatientService() {
        return patientService;
    }

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
        if (personService == null) {
            personService = RemoteServiceHelper.getPersonEntityService();
        }
        if (patientService == null) {
            patientService = RemoteServiceHelper.getPatientCorrelationService();
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
        personService = null;
        patientService = null;
        RemoteServiceHelper.close();
    }
}
