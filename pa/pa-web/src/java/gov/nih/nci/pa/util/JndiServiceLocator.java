package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.PAOrganizationServiceRemote;
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
     * @return PAOrganizationServiceRemote remote interface
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return (PAOrganizationServiceRemote) JNDIUtil.lookup("pa/PAOrganizationServiceBean/remote");
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
