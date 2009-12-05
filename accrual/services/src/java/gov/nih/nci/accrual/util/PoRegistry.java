package gov.nih.nci.accrual.util;

import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.rmi.RemoteException;


/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */
public final class PoRegistry {


    private static final PoRegistry PO_REGISTRY = new PoRegistry();
    private PoServiceLocator poServiceLocator;
    
    /**
     * @return the PO_REGISTRY
     */
    public static PoRegistry getInstance() {
        return PO_REGISTRY;
    }
    

    /**
     * Constructor for the singleton instance.
     */
    private PoRegistry() {
        this.poServiceLocator = new PoJndiServiceLocator();
    }
    /**
     * @return the serviceLocator
     */
    public PoServiceLocator getPoServiceLocator() {
        return this.poServiceLocator;
    }
    
    /**
     * 
     * @param poServiceLocator poServiceLocator
     */
    public void setPoServiceLocator(PoServiceLocator poServiceLocator) {
        this.poServiceLocator  = poServiceLocator;
    }
    /**
     * 
     * @return PersonEntityServiceRemote
     * @throws RemoteException e
     */
    public static PersonEntityServiceRemote getPersonEntityService() 
        throws RemoteException {
        return getInstance().getPoServiceLocator().getPersonEntityService();
    }
    
    /**
     * 
     * @return PatientCorrelationServiceRemote
     * @throws RemoteException the exception
     */
    public static PatientCorrelationServiceRemote getPatientCorrelationService() throws RemoteException {
        return getInstance().getPoServiceLocator().getPatientCorrelationService();
    }
    
    /**
     * 
     * @return OrganizationEntityServiceRemote
     * @throws RemoteException e
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws RemoteException {
        return getInstance().getPoServiceLocator().getOrganizationEntityService();
    }
}
