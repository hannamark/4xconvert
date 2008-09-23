package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

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
        return (OrganizationEntityServiceRemote) JNDIUtil.lookup(serverInfo);
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
    

}
