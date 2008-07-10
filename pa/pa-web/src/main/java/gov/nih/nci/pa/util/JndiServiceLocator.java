package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;
import gov.nih.nci.pa.service.TrialDesignServiceRemote;

/**
 * 
 * @author Harsha
 *
 */
public class JndiServiceLocator implements ServiceLocator {

    /**
     * @return protocol service
     */
    public ProtocolServiceLocal getProtocolService() {
        return (ProtocolServiceLocal) JNDIUtil.lookup("pa/ProtocolServiceBean/local");
    }
    /**
     * @return ProtocolOrganizationServiceRemote remote interface 
     */
    public ProtocolOrganizationServiceRemote getProtocolOrganizationService() {
        return (ProtocolOrganizationServiceRemote) JNDIUtil.lookup("pa/ProtocolOrganizationServiceBean/remote");
    }
    
    
    /**
     * @return Safety Trial Design Service
     */
    public TrialDesignServiceRemote getTrialDesignService() {
       return (TrialDesignServiceRemote) JNDIUtil.lookup("pa/TrialDesignServiceBean/remote");  
    }
}
