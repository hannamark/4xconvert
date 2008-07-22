package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;
import gov.nih.nci.pa.service.NCISpecificInformationServiceRemote;
/**
 *
 * @author Harsha
 *
 */
public interface ServiceLocator {

    /**
     *
     * @return ProtocolServiceLocal protocolServiceLocal
     */
    ProtocolServiceLocal getProtocolService();


    /**
     * @return ProtocolOrganizationServiceRemote
     */
    ProtocolOrganizationServiceRemote getProtocolOrganizationService();



   /**
    * @return NCISpecificInformationServiceRemote
    */
    NCISpecificInformationServiceRemote getNCISpecificInformationService();

    /**
     *
     * @return DiseaseCondServiceRemote
     */
    DiseaseCondServiceLocal getDiseaseConditionService();
}
