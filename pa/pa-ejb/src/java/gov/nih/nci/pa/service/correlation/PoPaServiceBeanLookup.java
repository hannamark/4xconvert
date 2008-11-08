package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals" })
public class PoPaServiceBeanLookup  {

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
     * @return HealthCareFacilityCorrelationServiceRemote
     * @throws PAException on error
     */
    public static ResearchOrganizationCorrelationServiceRemote
        getResearchOrganizationCorrelationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/po/ResearchOrganizationCorrelationServiceBean/remote";
        return (ResearchOrganizationCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
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

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyProtocolServiceRemote
        getStudyProtocolService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyProtocolServiceBean/remote";
        return (StudyProtocolServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyParticipationServiceRemote
        getStudyParticipationService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyParticipationServiceBean/remote";
        return (StudyParticipationServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyParticipationContactRemote
     * @throws PAException on error
     */
    public static StudyParticipationContactServiceRemote
        getStudyParticipationContactService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyParticipationContactServiceBean/remote";
        return (StudyParticipationContactServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    /**
     * @return StudyProtocolServiceRemote
     * @throws PAException on error
     */
    public static StudyContactServiceRemote
        getStudyContactService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                                + "/pa/StudyContactServiceBean/remote";
        return (StudyContactServiceRemote) JNDIUtil.lookup(serverInfo);
    } 

    
}
