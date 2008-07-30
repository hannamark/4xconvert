package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
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
    * @return NCISpecificInformationDto   
    * @throws PAException on error 
    */
    NCISpecificInformationDTO getNCISpecificInformation(Long studyProtocolID) throws PAException;

    /**
     * 
     * @param nciSpecificInformationData NCISpecificInformationData 
     * @return NCISpecificInformationDto   
     * @throws PAException on error 
     */
     NCISpecificInformationDTO updateNCISpecificInformation(
                    NCISpecificInformationData nciSpecificInformationData) throws PAException;
}