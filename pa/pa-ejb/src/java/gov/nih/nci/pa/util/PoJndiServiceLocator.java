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

}
