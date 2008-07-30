package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.NCISpecificInformationDAO;
import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
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
    public NCISpecificInformationDTO getNCISpecificInformation(
                                     Long studyProtocolID) throws PAException {                 
       return new NCISpecificInformationDAO().getNCISpecificInformation(studyProtocolID);      
    }
    
    /**
     * @param nciSpecificInformationData the abstracted data
     * @return NCI Specific Info DTO object
     * @throws PAException on error
     */
    public NCISpecificInformationDTO 
       updateNCISpecificInformation(NCISpecificInformationData nciSpecificInformationData) 
                                                                        throws PAException {               
        return new NCISpecificInformationDAO().updateNCISpecificInformation(nciSpecificInformationData);      
     }

}