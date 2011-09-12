package gov.nih.nci.accrual.util;

import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * A class for all Po look-ups.
 * @author Kalpana Guthikonda
 *
 */
public class PoJndiServiceLocator implements PoServiceLocator {
    
    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        String serverInfo = "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * {@inheritDoc}
     */
    public PatientCorrelationServiceRemote getPatientCorrelationService() {
        String serverInfo = "/po/PatientCorrelationServiceBean/remote";
        return (PatientCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        String serverInfo = "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationCorrelationService() {
        String serverInfo = "/po/IdentifiedOrganizationCorrelationServiceBean/remote";
        return (IdentifiedOrganizationCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        String serverInfo = "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }   
}
