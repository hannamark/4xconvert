package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;

/**
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
    DiseaseCondServiceLocal getDiseaseConditionService();
    
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
    
}
