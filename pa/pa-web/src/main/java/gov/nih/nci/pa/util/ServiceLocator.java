package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;
import gov.nih.nci.pa.service.SafetyRegulationServiceLocal;
import gov.nih.nci.pa.service.TrialDesignServiceRemote;
/**
 * 
 * @author Harsha
 *
 */
public interface ServiceLocator {
    
    /**
     * 
     * @return protocolservice
     */
    ProtocolServiceLocal getProtocolService();
    
    
    /**
     * @return ProtocolOrganizationServiceRemote
     */
    ProtocolOrganizationServiceRemote getProtocolOrganizationService();
    
    /**
     * @return safetyRegulationService
     */
    SafetyRegulationServiceLocal getSafetyRegulationService();
    
    /**
     * @return TrialDesignService
     */
    TrialDesignServiceRemote getTrialDesignService();
}
