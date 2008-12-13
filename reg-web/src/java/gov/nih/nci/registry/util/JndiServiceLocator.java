package gov.nih.nci.registry.util;

import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.pa.util.JNDIUtilPO;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 *
 * @author Harsha
 *
 */
/**
 * @author hreinhart
 * 
 */
@SuppressWarnings("PMD")
public class JndiServiceLocator implements ServiceLocator {
    /**
     * @return protocol service
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return (StudyProtocolServiceRemote) JNDIUtil.lookup("pa/StudyProtocolServiceBean/remote");
    }

    /**
     * @return PAOrganizationServiceRemote remote interface
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return (PAOrganizationServiceRemote) JNDIUtil.lookup("pa/PAOrganizationServiceBean/remote");
    }

    /**
     * @return PAPersonService
     */
    public PAPersonServiceRemote getPAPersonService() {
        return (PAPersonServiceRemote) JNDIUtil.lookup("pa/PAPersonServiceBean/remote");
    }

    /**
     * @return RegulatoryInformationServiceRemote
     */
    public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
        return (RegulatoryInformationServiceRemote) JNDIUtil.lookup("pa/RegulatoryInformationBean/remote");
    }

    /**
     * @return StudyOverallStatusServiceRemote
     */
    public StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return (StudyOverallStatusServiceRemote) JNDIUtil.lookup("pa/StudyOverallStatusServiceBean/remote");
    }

    /**
     * @return StudyResourcingServiceRemote
     */
    public StudyResourcingServiceRemote getStudyResoucringService() {
        return (StudyResourcingServiceRemote) JNDIUtil.lookup("pa/StudyResourcingServiceBean/remote");
    }

    /**
     * @return StudyResourcingServiceRemote
     */
    public StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() {
        return (StudyRegulatoryAuthorityServiceRemote) JNDIUtil.lookup("pa/StudyRegulatoryAuthorityServiceBean/remote");
    }

    /**
     * @return LookUpTableServiceRemote
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        return (LookUpTableServiceRemote) JNDIUtil.lookup("pa/LookUpTableServiceBean/remote");
    }

    /**
     * @return ProtocolQueryServiceRemote
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return (ProtocolQueryServiceLocal) JNDIUtil.lookup("pa/ProtocolQueryServiceBean/local");
    }

    /**
     * @return HealthCareFacilityService
     */
    public PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService() {
        return (PAHealthCareFacilityServiceRemote) JNDIUtil.lookup("pa/PAHealthCareFacilityServiceBean/remote");
    }

    /**
     * @return StudyParticipationService
     */
    public StudyParticipationServiceRemote getStudyParticipationService() {
        return (StudyParticipationServiceRemote) JNDIUtil.lookup("pa/StudyParticipationServiceBean/remote");
    }

    /**
     * @return StudySiteAccrualStatusService
     */
    public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
        return (StudySiteAccrualStatusServiceRemote) JNDIUtil.lookup("pa/StudySiteAccrualStatusServiceBean/remote");
    }

    /**
     * 
     * @return DocumentService
     */
    public DocumentServiceRemote getDocumentService() {
        return (DocumentServiceRemote) JNDIUtil.lookup("pa/DocumentServiceBean/remote");
    }

    /**
     * 
     * @return StudyContactService
     */
    public StudyIndldeServiceRemote getStudyIndldeService() {
        return (StudyIndldeServiceRemote) JNDIUtil.lookup("pa/StudyIndldeServiceBean/remote");
    }

    /**
     * 
     * @return StudyContactService
     */
    public StudyContactServiceRemote getStudyContactService() {
        return (StudyContactServiceRemote) JNDIUtil.lookup("pa/StudyContactServiceBean/remote");
    }

    /**
     * 
     * @return RegistryUserService
     */
    public RegistryUserServiceRemote getRegistryUserService() {
        return (RegistryUserServiceRemote) JNDIUtil.lookup("pa/RegistryUserServiceBean/remote");
    }

    /**
     * @throws PAException on e
     * @return PersonEntityServiceRemote
     */
    public PersonEntityServiceRemote getPoPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo() + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtilPO.lookup(serverInfo);
    }

    /**
     * @return HealthCareProviderCorrelationServiceRemote
     */
    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() {
        // String serverInfo = "jnp://" + PaPropertyReader.getLookUpServerInfo()
        // + "/po/HealthCareProviderCorrelationServiceBean/remote";
        // return (HealthCareProviderCorrelationServiceRemote)
        // JNDIUtil.lookupPo(serverInfo);
        return null;
    }

    /**
     * 
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    public OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationalContactCorrelationServiceBean/remote";
        return (OrganizationalContactCorrelationServiceRemote) JNDIUtilPO.lookup(serverInfo);
    }

    /**
     * @throws PAException e
     * @return OrganizationEntityServiceRemote
     */
    public OrganizationEntityServiceRemote getPoOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtilPO.lookup(serverInfo);
    }
    
    /**
     * @throws PAException e
     * @return IdentifiedOrganizationCorrelationServiceRemote
     */    
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
        + "/po/IdentifiedOrganizationCorrelationServiceBean/remote";
        return (IdentifiedOrganizationCorrelationServiceRemote) JNDIUtilPO.lookup(serverInfo);       
    }

    /**
     * @throws PAException e
     * @return IdentifiedPersonCorrelationServiceRemote
     */    
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
        + "/po/IdentifiedPersonCorrelationServiceBean/remote";
        return (IdentifiedPersonCorrelationServiceRemote) JNDIUtilPO.lookup(serverInfo);       
    }
    
    /**
     * @throws PAException e
     * @return StudyParticipationContactServiceRemote
     */    
    public StudyParticipationContactServiceRemote getStudyParticipationContactService() throws PAException {
        String serverInfo = "jnp://" + PaEarPropertyReader.getLookUpServerInfo()
        + "/pa/StudyParticipationContactServiceBean/remote";
        return (StudyParticipationContactServiceRemote) JNDIUtilPO.lookup(serverInfo);        
    } 
}
