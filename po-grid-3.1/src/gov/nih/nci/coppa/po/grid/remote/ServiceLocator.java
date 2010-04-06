package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import javax.naming.NamingException;

/**
 * Interface for looking up remote EJB services.
 */
public interface ServiceLocator {

    /**
     * Gets a CorrelationService.
     * @param <Z> Correlation DTO type
     * @param type Correlation DTO class
     * @return CorrleationService
     * @throws NamingException on error looking up the service
     */
    @SuppressWarnings("unchecked")
    <Z extends PoDto> CorrelationService getService(Class<Z> type) throws NamingException;

    /**
     * Gets the PersonService.
     * @return PersonService
     * @throws NamingException on error looking up the service
     */
    PersonEntityServiceRemote getPersonService() throws NamingException;

    /**
     * Gets the OrganizationService.
     * @return OrganizationService
     * @throws NamingException on error looking up the service
     */
    OrganizationEntityServiceRemote getOrganizationService() throws NamingException;

    /**
     * Gets the HealthCareFacilityService.
     * @return HealthCareFacilityService
     * @throws NamingException on error looking up the service
     */
    HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityService() throws NamingException;

    /**
     * Gets the ClinicalResearchStaffService.
     * @return ClinicalResearchStaffService
     * @throws NamingException on error looking up the service
     */
    ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffService() throws NamingException;

    /**
     * Gets the HealthCareProviderService.
     * @return HealthCareProviderService
     * @throws NamingException on error looking up the service
     */
    HealthCareProviderCorrelationServiceRemote getHealthCareProviderService() throws NamingException;

    /**
     * Gets the IdentifiedOrganizationService.
     * @return IdentifiedOrganizationService
     * @throws NamingException on error looking up the service
     */
    IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationService() throws NamingException;

    /**
     * Gets the IdentifiedPersonService.
     * @return IdentifiedPersonService
     * @throws NamingException on error looking up the service
     */
    IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonService() throws NamingException;

    /**
     * Gets the ResearchOrganizationService.
     * @return ResearchOrganizationService
     * @throws NamingException on error looking up the service
     */
    ResearchOrganizationCorrelationServiceRemote getResearchOrganizationService() throws NamingException;

    /**
     * Gets the OversightCommitteeService.
     * @return OversightCommitteeService
     * @throws NamingException on error looking up the service
     */
    OversightCommitteeCorrelationServiceRemote getOversightCommitteeService() throws NamingException;

    /**
     * Gets the OrganizationalContactService.
     * @return OrganizationalContactService
     * @throws NamingException on error looking up the service
     */
    OrganizationalContactCorrelationServiceRemote getOrganizationalContactService() throws NamingException;
    
    /**
     * Gets the PatientService.
     * @return PatientService
     * @throws NamingException on error looking up the service
     */
    PatientCorrelationServiceRemote getPatientService() throws NamingException;
    
    /**
     * Gets the BusinessService.
     * @return BusinessService.
     * @throws NamingException on error looking up the service.
     */
    BusinessServiceRemote getBusinessService() throws NamingException;
}
