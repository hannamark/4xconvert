package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import javax.naming.NamingException;

public interface ServiceLocator {
    
    @SuppressWarnings("unchecked")
    <Z extends PoDto> CorrelationService getService(Class<Z> type) throws NamingException;
    
    PersonEntityServiceRemote getPersonService() throws NamingException;

    OrganizationEntityServiceRemote getOrganizationService() throws NamingException;

    HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityService() throws NamingException;

    ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffService() throws NamingException;

    HealthCareProviderCorrelationServiceRemote getHealthCareProviderService() throws NamingException;

    IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationService() throws NamingException;

    IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonService() throws NamingException;

    ResearchOrganizationCorrelationServiceRemote getResearchOrganizationService() throws NamingException;

    OversightCommitteeCorrelationServiceRemote getOversightCommitteeService() throws NamingException;

    OrganizationalContactCorrelationServiceRemote getOrganizationalContactService() throws NamingException;
}
