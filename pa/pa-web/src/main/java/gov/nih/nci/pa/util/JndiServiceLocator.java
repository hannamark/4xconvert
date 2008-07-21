package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;
import gov.nih.nci.pa.service.NCISpecificInfoServiceRemote;

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
     * @return NCI Specific Information Service
     */
     public NCISpecificInfoServiceRemote getNCISpecificInfoService() {
       return (NCISpecificInfoServiceRemote) JNDIUtil.lookup("pa/NCISpecificInfoServiceBean/remote");
    }

    /**
     * @return DiseaseConditionService
     */
    public DiseaseCondServiceLocal getDiseaseConditionService() {
        return (DiseaseCondServiceLocal) JNDIUtil.lookup("pa/DiseaseCondServiceBean/local");
    }

}
