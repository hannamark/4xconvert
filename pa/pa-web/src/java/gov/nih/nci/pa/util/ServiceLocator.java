package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;


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
    * @return StudyOverallStatusServiceRemote
    */
    OrganizationEntityServiceRemote getPoOrganizationEntityService();
    
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
     * @return HealthCareFacilityService
     */
    PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService();
}
