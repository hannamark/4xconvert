package gov.nih.nci.registry.util;

import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
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
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * Interface used to locate the services used by pa-web.
 * 
 * @author Harsha
 * 
 */
public interface ServiceLocator {
    /**
     * 
     * @return StudyProtocolServiceRemote studyProtocolServiceRemote
     */
    StudyProtocolServiceRemote getStudyProtocolService();

    /**
     * @return ProtocolOrganizationServiceRemote
     */
    PAOrganizationServiceRemote getPAOrganizationService();

    /**
     * 
     * @return DiseaseCondServiceRemote
     */
    DiseaseCondServiceRemote getDiseaseConditionService();

    /**
     * 
     * @return PAPersonServiceRemote
     */
    PAPersonServiceRemote getPAPersonService();

    /**
     * 
     * @return RegulatoryInformationServiceRemote
     */
    RegulatoryInformationServiceRemote getRegulatoryInformationService();

    /**
     * 
     * @return StudyOverallStatusServiceRemote
     */
    StudyOverallStatusServiceRemote getStudyOverallStatusService();

    /**
     * 
     * @return StudyResourcingServiceRemote
     */
    StudyResourcingServiceRemote getStudyResoucringService();

    /**
     * 
     * @return StudyRegulatoryAuthorityServiceRemote
     */
    StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService();

    /**
     * 
     * @return LookUpTableServiceRemote
     */
    LookUpTableServiceRemote getLookUpTableService();

    /**
     * 
     * @return ProtocolQueryServiceRemote
     */
    ProtocolQueryServiceLocal getProtocolQueryService();

    /**
     * @return StudyParticipationServiceRemote
     */
    StudyParticipationServiceRemote getStudyParticipationService();

    /**
     * @return PAHealthCareFacilityServiceRemote
     */
    PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService();

    /**
     * @return StudySiteAccrualStatusServiceRemote
     */
    StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService();

    /**
     * 
     * @return DocumentServiceRemote
     */
    DocumentServiceRemote getDocumentService();

    /**
     * 
     * @return StudyContactServiceRemote
     */
    StudyContactServiceRemote getStudyContactService();

    /**
     * 
     * @return getStudyIndldeService
     */
    StudyIndldeServiceRemote getStudyIndldeService();

    /**
     * 
     * @return RegistryUserServiceRemote
     */
    RegistryUserServiceRemote getRegistryUserService();

    /**
     * @throws PAException on e
     * @return StudyOverallStatusServiceRemote
     */
    OrganizationEntityServiceRemote getPoOrganizationEntityService() throws PAException;

    /**
     * @throws PAException on e
     * @return PersonEntityServiceRemote
     */
    PersonEntityServiceRemote getPoPersonEntityService() throws PAException;

    /**
     * 
     * @return OrganizationalContactCorrelationServiceRemote
     * @throws PAException on error
     */
    OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService() throws PAException;
}
