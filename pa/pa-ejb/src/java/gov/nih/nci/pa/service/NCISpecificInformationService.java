package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
/**
 * 
 * @author gnaveh
 *
 */
public interface NCISpecificInformationService {

    /**
    * 
    * @param studyProtocolID studyProtocolID
    * @return NCISpecificInfoDto   
    * @throws PAException on error 
    */
    NCISpecificInfoDTO getNCISpecificInfo(Long studyProtocolID) throws PAException;

    /**
     * 
     * @param nciSpecificInformationData NCISpecificInformationData 
     * @return NCISpecificInfoDto   
     * @throws PAException on error 
     */
     NCISpecificInfoDTO updateNCISpecificInfo(NCISpecificInformationData nciSpecificInformationData) throws PAException;
}