package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.NCISpecificInformationServiceRemote;

/**
 *
 * @author Harsha
 *
 */
public class JndiServiceLocator implements ServiceLocator {

    /**
     * @return protocol service
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return (StudyProtocolServiceRemote) JNDIUtil.lookup("pa/StudyProtocolServiceBean/remote");
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
     public NCISpecificInformationServiceRemote getNCISpecificInformationService() {
       return (NCISpecificInformationServiceRemote) JNDIUtil.lookup("pa/NCISpecificInformationServiceBean/remote");
    }

    /**
     * @return DiseaseConditionService
     */
    public DiseaseCondServiceLocal getDiseaseConditionService() {
        return (DiseaseCondServiceLocal) JNDIUtil.lookup("pa/DiseaseCondServiceBean/local");
    }

    /**
     * @return PAPersonService
     */
    public PAPersonServiceRemote getPAPersonService() {
        return (PAPersonServiceRemote) JNDIUtil.lookup("pa/PAPersonServiceBean/remote");
    }
    
}
