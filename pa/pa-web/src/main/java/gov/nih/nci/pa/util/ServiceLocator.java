package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;
import gov.nih.nci.pa.service.TrialDesignServiceRemote;
import gov.nih.nci.pa.service.NCISpecificInfoServiceRemote;
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
     * @return TrialDesignService
     */
    TrialDesignServiceRemote getTrialDesignService();

   /**
    * @return NCISpecificInfoService
    */
    NCISpecificInfoServiceRemote getNCISpecificInfoService();

    /**
     *
     * @return DiseaseCondServiceRemote
     */
    DiseaseCondServiceLocal getDiseaseConditionService();
}
