package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author NAmiruddin
 *
 */

public class PoServiceBeanLookup  {

    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    public static PersonEntityServiceRemote getPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }    
    
    
    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException e
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()  
    throws PAException { 
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error
     */
    public static HealthCareProviderCorrelationServiceRemote 
        getHealthCareProviderCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static HealthCareFacilityCorrelationServiceRemote
        getHealthCareFacilityCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

    /**
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public static OrganizationalContactCorrelationServiceRemote
        getOrganizationalContactCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/OrganizationalContactCorrelationServiceBean/remote";
        return (OrganizationalContactCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 

}
