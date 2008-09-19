package gov.nih.nci.pa.util;


import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;


/**
 * 
 * @author Harsha
 *
 */
@SuppressWarnings("PMD.UnusedPrivateField")
public final class PaRegistry {
    
    /**
     * Number of records to display by default in display tag controlled tables.
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
     * 
     * @return diseaseCondServiceRemote DiseaseCondServiceRemote
     */
    public static DiseaseCondServiceRemote getDiseaseService() {
        return getInstance().getServiceLocator().getDiseaseConditionService();
    }

    
    /**
     * Gets the org service from the service locator.
     * @return the service.
     */
    public static StudyProtocolServiceRemote getStudyProtocolService() {
        return getInstance().getServiceLocator().getStudyProtocolService();
    }
    
    /**
     * Gets the org service from the service locator.
     * @return PAOrganizationServiceRemote
     */
    public static PAOrganizationServiceRemote getPAOrganizationService() {
        return getInstance().getServiceLocator().getPAOrganizationService();
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
    
    /**
     * @return PAPerson Service.
     */
    public static PAPersonServiceRemote getPAPersonService() {
        return getInstance().getServiceLocator().getPAPersonService();
    }    
    
    /**
     * 
     * @return RegulatoryInformationServiceRemote
     */
    public static RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return getInstance().getServiceLocator().getRegulatoryInformationService();
    }

    /**
     * 
     * @return StudyOverallStatusServiceRemote
     */
    public static StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return getInstance().getServiceLocator().getStudyOverallStatusService();
    }

    /**
     * 
     * @return StudyResourcingServiceRemote
     */
    public static StudyResourcingServiceRemote getStudyResourcingService() {
        return getInstance().getServiceLocator().getStudyResoucringService();
    }
    
    /**
     * 
     * @return StudyRegulatoryAuthorityServiceRemote
     */
    public static StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() {
        return getInstance().getServiceLocator().getStudyRegulatoryAuthorityService();
    }
    
    /**
    *
    * @return OrganizationEntityServiceRemote
    */
    public static OrganizationEntityServiceRemote getPoOrganizationEntityService() {
    return getInstance().getServiceLocator().getPoOrganizationEntityService();
    }
    
    /**
    *
    * @return LookUpTableServiceRemote
    */
    public static LookUpTableServiceRemote getLookUpTableService() {
    return getInstance().getServiceLocator().getLookUpTableService();
    }
    
}
