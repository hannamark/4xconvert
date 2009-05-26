package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

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
    
}
