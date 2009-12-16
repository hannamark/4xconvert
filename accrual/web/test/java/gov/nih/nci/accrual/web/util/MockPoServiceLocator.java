package gov.nih.nci.accrual.web.util;

import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.rmi.RemoteException;

public class MockPoServiceLocator implements PoServiceLocator {
    private final PersonEntityServiceRemote personEntityService = new MockPoPersonEntityService();
    private final  PatientCorrelationServiceRemote patientService = new MockPatientCorrelationServiceRemote();
    private final OrganizationEntityServiceRemote organizationEntityService = new MockPoOrganizationEntityService();
    
    public PersonEntityServiceRemote getPersonEntityService()
            throws RemoteException {
        return personEntityService;
    }

    public PatientCorrelationServiceRemote getPatientCorrelationService()
            throws RemoteException {
        return patientService;
    }
    
    public OrganizationEntityServiceRemote getOrganizationEntityService() throws RemoteException {
    	return organizationEntityService;
    }
}
