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
public final class PoRegistry {


    private static final PoRegistry PO_REGISTRY = new PoRegistry();
    private PoServiceLocator poServiceLocator;
    
    /**
     * @return the PO_REGISTRY
     */
    public static PoRegistry getInstance() {
        return PO_REGISTRY;
    }
    

    /**
     * Constructor for the singleton instance.
     */
    private PoRegistry() {
        this.poServiceLocator = new PoJndiServiceLocator();
    }
    
    /**
     * 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        return getInstance().getPoServiceLocator().getOrganizationEntityService();
    }
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error 
     */
    public static HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() 
        throws PAException {
        return getInstance().getPoServiceLocator().getHealthCareProverService();
    }
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static ResearchOrganizationCorrelationServiceRemote
        getResearchOrganizationCorrelationService() throws PAException {
        return getInstance().getPoServiceLocator().getResearchOrganizationCorrelationService();
    } 

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OversightCommitteeCorrelationServiceRemote
        getOversightCommitteeCorrelationService() throws PAException {
        return getInstance().getPoServiceLocator().getOversightCommitteeCorrelationService();
    } 

    /**
     * @return the serviceLocator
     */
    public PoServiceLocator getPoServiceLocator() {
        return this.poServiceLocator;
    }
    
    /**
     * 
     * @param poServiceLocator poServiceLocator
     */
    public void setPoServiceLocator(PoServiceLocator poServiceLocator) {
        this.poServiceLocator  = poServiceLocator;
    }
    /**
     * 
     * @return PersonEntityServiceRemote
     * @throws PAException e
     */
    public static PersonEntityServiceRemote getPersonEntityService() 
        throws PAException {
        return getInstance().getPoServiceLocator().getPersonEntityService();
    }
    /**
     * @return ClinicalResearchStaffCorrelationServiceRemote
     * @throws PAException e
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()  
    throws PAException { 
        return getInstance().getPoServiceLocator().getClinicalResearchStaffCorrelationService();
    }
    /**
     * 
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException e
     */
    public static HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() 
    throws PAException { 
        return getInstance().getPoServiceLocator().getHealthCareProviderCorrelationService();
    }
    /**
     * 
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException e
     */
    public static OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() 
    throws PAException {
       return getInstance().getPoServiceLocator().getOrganizationalContactCorrelationService(); 
    }
    
    /**
     * 
     * @return PatientCorrelationServiceRemote
     * @throws PAException the PA exception
     */
    public static PatientCorrelationServiceRemote getPatientCorrelationService() throws PAException {
        return getInstance().getPoServiceLocator().getPatientCorrelationService();
    }
}
