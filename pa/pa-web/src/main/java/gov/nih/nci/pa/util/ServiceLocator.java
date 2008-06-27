package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;

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

}
