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
public class PoJndiServiceLocator implements PoServiceLocator {
    
    private static final String JNP = "jnp://";

    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareProverService() throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public ResearchOrganizationCorrelationServiceRemote
        getResearchOrganizationCorrelationService() throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/ResearchOrganizationCorrelationServiceBean/remote";
        return (ResearchOrganizationCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public OversightCommitteeCorrelationServiceRemote
        getOversightCommitteeCorrelationService() throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OversightCommitteeCorrelationServiceBean/remote";
        return (OversightCommitteeCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    }
    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    public PersonEntityServiceRemote getPersonEntityService() throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    }    

    /**
     * @return ClinicalResearchStaffCorrelationServiceBean
     * @throws PAException e
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()  
    throws PAException { 
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    }
    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    } 
    /**
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() 
    throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OrganizationalContactCorrelationServiceBean/remote";
        return (OrganizationalContactCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * @return PatientCorrelationServiceRemote
     * @throws PAException on error
     */
    public PatientCorrelationServiceRemote getPatientCorrelationService()
            throws PAException {
        String serverInfo = JNP + PaEarPropertyReader.getLookUpServerInfo()
        + "/po/PatientCorrelationServiceBean/remote";
        return (PatientCorrelationServiceRemote) PoJNDIUtil.lookupPo(serverInfo);
    } 




}
