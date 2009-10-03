package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.MockPoClinicalResearchStaffCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareFacilityCorrelationService;
import gov.nih.nci.pa.service.MockPoHealthCareProviderCorrelationService;
import gov.nih.nci.pa.service.MockPoOrganizationEntityService;
import gov.nih.nci.pa.service.MockPoOrganizationalContactCorrelationService;
import gov.nih.nci.pa.service.MockPoOversightCommitteeCorrelationService;
import gov.nih.nci.pa.service.MockPoPatientCorrelationService;
import gov.nih.nci.pa.service.MockPoPersonEntityService;
import gov.nih.nci.pa.service.MockPoResearchOrganizationCorrelationService;
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

public class MockPoServiceLocator implements PoServiceLocator {

    private final OrganizationEntityServiceRemote organizationEntityServiceRemote = new MockPoOrganizationEntityService();
    private final HealthCareFacilityCorrelationServiceRemote hcfService = new MockPoHealthCareFacilityCorrelationService(); 
    private final ResearchOrganizationCorrelationServiceRemote roService = new MockPoResearchOrganizationCorrelationService();
    private final OversightCommitteeCorrelationServiceRemote ocService = new MockPoOversightCommitteeCorrelationService();
    private final PersonEntityServiceRemote personEntityService = new MockPoPersonEntityService();
    private final ClinicalResearchStaffCorrelationServiceRemote crsService = new MockPoClinicalResearchStaffCorrelationService();
    private final HealthCareProviderCorrelationServiceRemote hcpService = new MockPoHealthCareProviderCorrelationService();
    private final OrganizationalContactCorrelationServiceRemote orgContact = new MockPoOrganizationalContactCorrelationService();
    private final  PatientCorrelationServiceRemote patientService = new MockPoPatientCorrelationService();
  
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

    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()
            throws PAException {
        return crsService;
    }

    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService()
            throws PAException {
        return hcpService;
    }

    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService()
            throws PAException {
        return orgContact;
    }

    public PersonEntityServiceRemote getPersonEntityService()
            throws PAException {
        return personEntityService;
    }

    public PatientCorrelationServiceRemote getPatientCorrelationService()
                   throws PAException {
        return patientService;
} 
    

}
