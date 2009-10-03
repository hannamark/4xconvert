package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * A class for all Po look-ups.
 * @author NAmiruddin
 *
 */

public interface PoServiceLocator {

    
    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException;
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    HealthCareFacilityCorrelationServiceRemote getHealthCareProverService() throws PAException;
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() throws PAException;
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() throws PAException;
    /**
     * 
     * @return PersonEntityServiceRemote
     * @throws PAException err
     */
    PersonEntityServiceRemote getPersonEntityService() throws PAException;
    /**
     * 
     * @return ClinicalResearchStaffCorrelationServiceRemote
     * @throws PAException e
     */
    ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() throws PAException;
    /**
     * 
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException e
     */
    HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() throws PAException;
    /**
     * 
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException e
     */
    OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() throws PAException;
    /**
     * @return PatientCorrelationServiceRemote
     * @throws PAException e
     */
    PatientCorrelationServiceRemote getPatientCorrelationService() throws PAException;
}
