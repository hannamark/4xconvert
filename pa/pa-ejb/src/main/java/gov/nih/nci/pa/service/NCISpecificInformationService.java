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
    * @param pId ProtocolID
    * @return NCISpecificInfoDto   
    * @throws PAException on error 
    */
    NCISpecificInfoDTO getNCISpecificInfo(String pId) throws PAException;

    /**
     * 
     * @param nciSpecificInformationData NCISpecificInformationData 
     * @return NCISpecificInfoDto   
     * @throws PAException on error 
     */
     NCISpecificInfoDTO updateNCISpecificData(NCISpecificInformationData nciSpecificInformationData) throws PAException;
}