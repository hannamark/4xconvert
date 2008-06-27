package gov.nih.nci.pa.util;


import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;

/**
 * 
 * @author Harsha
 *
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public final class PaRegistry {
    
    /**
     * Number of records to display by default in displaytag controlled tables.
     */
    public static final int DEFAULT_RECORDS_PER_PAGE = 20;
    private static final PaRegistry PA_REGISTRY = new PaRegistry();
    private ServiceLocator serviceLocator;

    /**
     * Constructor for the singleton instance.
     */
    private PaRegistry() {
        this.serviceLocator = new JndiServiceLocator();
    }
    
    /**
     * @return the PO_REGISTRY
     */
    public static PaRegistry getInstance() {
        return PA_REGISTRY;
    }
    
    /**
     * Gets the org service from the service locator.
     * @return the service.
     */
    public static ProtocolServiceLocal getProtocolService() {
        return getInstance().getServiceLocator().getProtocolService();
    }
    
    /**
     * Gets the org service from the service locator.
     * @return ProtocolOrganizationServiceRemote
     */
    public static ProtocolOrganizationServiceRemote getrotocolOrganizationService() {
        return getInstance().getServiceLocator().getProtocolOrganizationService();
    }
    
    /**
     * @return the serviceLocator
     */
    public ServiceLocator getServiceLocator() {
        return this.serviceLocator;
    }
    
    /**
     * @param serviceLocator the serviceLocator to set
     */
    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }
}
