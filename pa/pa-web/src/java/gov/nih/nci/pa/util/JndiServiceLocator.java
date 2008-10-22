package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.SubGroupsServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PAResearchOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * 
 * @author Harsha
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
     * @return DiseaseConditionService
     */
    public DiseaseCondServiceRemote getDiseaseConditionService() {
        return (DiseaseCondServiceRemote) JNDIUtil.lookup("pa/DiseaseCondServiceBean/remote");
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
     * @return OrganizationEntityServiceRemote
     */
    public OrganizationEntityServiceRemote getPoOrganizationEntityService() {
        String serverInfo = "jnp://" + PaPropertyReader.getLookUpServerInfo()
                + "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
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
     * @return ResearchOrganizationService
     */
    public PAResearchOrganizationServiceRemote getPAResearchOrganizationService() {
        return (PAResearchOrganizationServiceRemote) JNDIUtil.lookup("pa/PAResearchOrganizationServiceBean/remote");
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
     * @return DocumentService
     */
    public DocumentServiceRemote getDocumentService() {
        return (DocumentServiceRemote) JNDIUtil.lookup("pa/DocumentServiceBean/remote");
    }

    /**
     * @return HealthCareFacilityCorrelationServiceRemote
     */
    public HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() {
        String serverInfo = "jnp://" + PaPropertyReader.getLookUpServerInfo()
            + "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }
    
    /**
     * @return SubGroupsService
     */
    public SubGroupsServiceRemote getSubGroupsService() {
        return (SubGroupsServiceRemote) JNDIUtil.lookup("pa/SubGroupsServiceBean/remote");
    }
    
    /**
     * @return PersonEntityServiceRemote
     */
    public PersonEntityServiceRemote getPoPersonEntityService() {
        String serverInfo = "jnp://" + PaPropertyReader.getLookUpServerInfo()
                                + "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }    
    
    /**
     * @return HealthCareProviderCorrelationServiceRemote
     */
    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() {
        String serverInfo = "jnp://" + PaPropertyReader.getLookUpServerInfo()
                                + "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    } 
    
    /**
     * @return PAHealthCareProviderRemote
     */
    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        return (PAHealthCareProviderRemote) JNDIUtil.lookup(
                                            "pa/PAHealthCareProviderServiceBean/remote");
    }
    
    /**
     * @return StudyParticipationService
     */
    public StudyParticipationContactServiceRemote getStudyParticipationContactService() {
        return (StudyParticipationContactServiceRemote) JNDIUtil.lookup(
                                            "pa/StudyParticipationContactServiceBean/remote");
    }
}
