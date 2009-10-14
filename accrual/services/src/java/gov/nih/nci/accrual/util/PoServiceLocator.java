package gov.nih.nci.accrual.util;

import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.rmi.RemoteException;

/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */

public interface PoServiceLocator {   
   
    /**
     * 
     * @return PersonEntityServiceRemote
     * @throws RemoteException err
     */
    PersonEntityServiceRemote getPersonEntityService() throws RemoteException;
    
    /**
     * @return PatientCorrelationServiceRemote
     * @throws RemoteException e
     */
    PatientCorrelationServiceRemote getPatientCorrelationService() throws RemoteException;
}
