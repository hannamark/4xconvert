package gov.nih.nci.registry.util;

import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author Bala Nair
 * 
 */
@SuppressWarnings("PMD")
public final class RegistryServiceLocator {
    /**
     * Number of records to display by default in display tag controlled tables.
     */
    public static final int DEFAULT_RECORDS_PER_PAGE = 20;
    private static final RegistryServiceLocator REG_REGISTRY = new RegistryServiceLocator();
    private ServiceLocator serviceLocator;

    /**
     * Constructor for the singleton instance.
     */
    private RegistryServiceLocator() {
        this.serviceLocator = new JndiServiceLocator();
    }

    /**
     * @return the regServiceLocator
     */
    public static RegistryServiceLocator getInstance() {
        return REG_REGISTRY;
    }

    /**
     * Gets the org service from the service locator.
     * 
     * @return the service.
     */
    public static StudyProtocolServiceRemote getStudyProtocolService() {
        return getInstance().getServiceLocator().getStudyProtocolService();
    }

    /**
     * Gets the org service from the service locator.
     * 
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
     * @return LookUpTableServiceRemote
     */
    public static LookUpTableServiceRemote getLookUpTableService() {
        return getInstance().getServiceLocator().getLookUpTableService();
    }

    /**
     * 
     * @return ProtocolQueryServiceRemote
     */
    public static ProtocolQueryServiceLocal getProtocolQueryService() {
        return getInstance().getServiceLocator().getProtocolQueryService();
    }

    /**
     * 
     * @return HealthCareFacilityServiceRemote
     */
    public static PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService() {
        return getInstance().getServiceLocator().getPAHealthCareFacilityService();
    }

    /**
     * 
     * @return StudyParticipationService
     */
    public static StudyParticipationServiceRemote getStudyParticipationService() {
        return getInstance().getServiceLocator().getStudyParticipationService();
    }

    /**
     * 
     * @return StudySiteAccrualStatusService
     */
    public static StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
        return getInstance().getServiceLocator().getStudySiteAccrualStatusService();
    }

    /**
     * 
     * @return DocumentServiceRemote
     */
    public static DocumentServiceRemote getDocumentService() {
        return getInstance().getServiceLocator().getDocumentService();
    }

    /**
     * 
     * @return DocumentServiceRemote
     */
    public static StudyContactServiceRemote getStudyContactService() {
        return getInstance().getServiceLocator().getStudyContactService();
    }

    /**
     * @return StudyIndldeServiceRemote
     * 
     */
    public static StudyIndldeServiceRemote getStudyIndldeService() {
        return getInstance().getServiceLocator().getStudyIndldeService();
    }

    /**
     * 
     * @return RegistryUserServiceRemote
     */
    public static RegistryUserServiceRemote getRegistryUserService() {
        return getInstance().getServiceLocator().getRegistryUserService();
    }

    /**
     * 
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        return getInstance().getServiceLocator().getPoOrganizationalContactCorrelationService();
    }

    /**
     * @throws PAException on error
     * @return OrganizationEntityServiceRemote
     */
    public static OrganizationEntityServiceRemote getPoOrganizationEntityService() throws PAException {
        return getInstance().getServiceLocator().getPoOrganizationEntityService();
    }
    
    /**
     * @throws PAException on e
     * @return PersonEntityServiceRemote
     */
    public static PersonEntityServiceRemote getPoPersonEntityService() throws PAException {
        return getInstance().getServiceLocator().getPoPersonEntityService();
    }    
    /**
     * @throws PAException e
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */    
    public static IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() 
        throws PAException {
        return getInstance().getServiceLocator().getIdentifiedOrganizationEntityService();
    }
    
    /**
     * @throws PAException e
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */    
    public static IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() 
        throws PAException {    
        return getInstance().getServiceLocator().getIdentifiedPersonEntityService();
    }
    
    /**
     * @throws PAException e
     * @return StudyParticipationContactServiceRemote
     */    
    public static StudyParticipationContactServiceRemote getStudyParticipationContactService() 
        throws PAException {    
        return getInstance().getServiceLocator().getStudyParticipationContactService();
    }
         
}
