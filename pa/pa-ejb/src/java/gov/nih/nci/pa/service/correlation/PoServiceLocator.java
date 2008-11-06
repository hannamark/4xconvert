package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author NAmiruddin
 *
 */
public interface PoServiceLocator {

    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    PersonEntityServiceRemote getPersonEntityService() throws PAException;

    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException;

    /**
    *
    * @return HealthCareFacilityCorrelationServiceRemote
    * @throws PAException on error  
    */
    HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() throws PAException;

    
    /**
     * @return getPoClinicalResearchStaffCorrelationService
     * @throws PAException on error  
     */
    ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() throws PAException;
    

}
