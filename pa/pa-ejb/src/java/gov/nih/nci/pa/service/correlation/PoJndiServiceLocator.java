package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public class PoJndiServiceLocator implements PoServiceLocator {

    /**
     * @return PersonEntityServiceRemote
     * @throws PAException on error
     */
    public PersonEntityServiceRemote getPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }    
    
    
    /** 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    
    
    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException e
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService()  
    throws PAException { 
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
            + "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
}
