/**
 * 
 */
package gov.nih.nci.registry.test.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.registry.service.MockOrganizationEntityService;
import gov.nih.nci.registry.service.MockOrganizationalContactCorrelationService;
import gov.nih.nci.registry.service.MockPersonEntityService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * @author Vrushali
 *
 */
public class MockPoServiceLocator implements PoServiceLocator {

        private final OrganizationEntityServiceRemote orgEntityServiceRemote = new MockOrganizationEntityService();
        private final HealthCareFacilityCorrelationServiceRemote hcfService = null; 
        private final ResearchOrganizationCorrelationServiceRemote roService = null;
        private final OversightCommitteeCorrelationServiceRemote ocService = null;
        private final PersonEntityServiceRemote personEntityService = new MockPersonEntityService();
        private final ClinicalResearchStaffCorrelationServiceRemote crsService = null;
        private final HealthCareProviderCorrelationServiceRemote hcpService = null;
        private final OrganizationalContactCorrelationServiceRemote orgContact = new MockOrganizationalContactCorrelationService();
          
        public OrganizationEntityServiceRemote getOrganizationEntityService()
                throws PAException {
           return orgEntityServiceRemote;
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
    }

