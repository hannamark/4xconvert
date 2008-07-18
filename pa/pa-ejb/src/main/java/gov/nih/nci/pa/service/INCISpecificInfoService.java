package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.NCISpecificInfoDTO;

/**
 * 
 * @author gnaveh
 *
 */
public interface INCISpecificInfoService {

    /**
    * 
    * @param pId ProtocolID
    * @return NCISpecificInfoDto   
    * @throws PAException on error 
    */
    NCISpecificInfoDTO getNCISpecificInfo(String pId) throws PAException;

    /**
     * 
     * @param nciSpecificInfoData NCISpecificInfoData 
     * @return NCISpecificInfoDto   
     * @throws PAException on error 
     */
     NCISpecificInfoDTO updateNCISpecificData(NCISpecificInfoData nciSpecificInfoData) throws PAException;
}