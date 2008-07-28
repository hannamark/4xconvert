package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.NCISpecificInfoDAO;
import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
import gov.nih.nci.pa.service.PAException;

/**
 * 
 * @author gnaveh
 *
 */
public class NCISpecificInformationServiceImpl {

    /**
     * @param studyProtocolID studyProtocolID
     * @return TrialDesignDTO   
     * @throws PAException on error 
     */
    public NCISpecificInfoDTO getNCISpecificInfo(Long studyProtocolID) throws PAException {                 
       return new NCISpecificInfoDAO().getNCISpecificInfo(studyProtocolID);      
    }
    
    /**
     * @param nciSpecificInformationData the abstracted data
     * @return NCI Specific Info DTO object
     * @throws PAException on error
     */
    public NCISpecificInfoDTO 
       updateNCISpecificInfo(NCISpecificInformationData nciSpecificInformationData) throws PAException {               
        return new NCISpecificInfoDAO().updateNCISpecificInfo(nciSpecificInformationData);      
     }

}