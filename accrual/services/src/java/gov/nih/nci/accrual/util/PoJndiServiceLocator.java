package gov.nih.nci.accrual.util;

import gov.nih.nci.pa.util.PoJNDIUtil;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.rmi.RemoteException;

/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */
public class PoJndiServiceLocator implements PoServiceLocator {
    
    private static final String JNP = "jnp://";

    /**
     * @return PersonEntityServiceRemote
     * @throws RemoteException  on error
     */
    public PersonEntityServiceRemote getPersonEntityService() throws RemoteException {
        String serverInfo = JNP + PoPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return PatientCorrelationServiceRemote
     * @throws RemoteException on error
     */
    public PatientCorrelationServiceRemote getPatientCorrelationService()
            throws RemoteException {
        String serverInfo = JNP + PoPropertyReader.getLookUpServerInfo()
        + "/po/PatientCorrelationServiceBean/remote";
        return (PatientCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    } 

}
