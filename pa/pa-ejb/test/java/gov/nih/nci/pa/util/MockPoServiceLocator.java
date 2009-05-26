package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.MockPoHealthCareFacilityCorrelationService;
import gov.nih.nci.pa.service.MockPoOrganizationEntityService;
import gov.nih.nci.pa.service.MockPoOversightCommitteeCorrelationService;
import gov.nih.nci.pa.service.MockPoResearchOrganizationCorrelationService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

public class MockPoServiceLocator implements PoServiceLocator {

    private final OrganizationEntityServiceRemote organizationEntityServiceRemote = new MockPoOrganizationEntityService();
    private final HealthCareFacilityCorrelationServiceRemote hcfService = new MockPoHealthCareFacilityCorrelationService(); 
    private final ResearchOrganizationCorrelationServiceRemote roService = new MockPoResearchOrganizationCorrelationService();
    private final OversightCommitteeCorrelationServiceRemote ocService = new MockPoOversightCommitteeCorrelationService();
    
    public OrganizationEntityServiceRemote getOrganizationEntityService()
            throws PAException {
       return organizationEntityServiceRemote;
    }

    public HealthCareFacilityCorrelationServiceRemote getHealthCareProverService()
    throws PAException {
        return hcfService;
    }
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public ResearchOrganizationCorrelationServiceRemote
        getResearchOrganizationCorrelationService() throws PAException {
        return roService ;
    }

    /**
     * @return OversightCommittee
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService()
            throws PAException {
        return ocService;
    } 
    

}
