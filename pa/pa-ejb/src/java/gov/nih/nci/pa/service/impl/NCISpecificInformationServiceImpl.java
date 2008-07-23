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
     * @param spid ProtocolID
     * @return TrialDesignDTO   
     * @throws PAException on error 
     */
    public NCISpecificInfoDTO getNCISpecificInfo(String spid) throws PAException {                 
       return new NCISpecificInfoDAO().getNCISpecificInfo(spid);      
    }
    
    /**
     * @param nciSpecificInformationData the abstracted data
     * @return NCI Specific Info DTO object
     * @throws PAException on error
     */
    public NCISpecificInfoDTO 
       updateNCISpecificData(NCISpecificInformationData nciSpecificInformationData) throws PAException {               
        return new NCISpecificInfoDAO().updateNCISpecificData(nciSpecificInformationData);      
     }

}