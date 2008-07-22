package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.NCISpecificInfoDAO;
import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.dto.NCISpecificInfoData;
import gov.nih.nci.pa.service.PAException;

/**
 * 
 * @author gnaveh
 *
 */
public class NCISpecificInfoServiceImpl {

    /**
     * @param spid ProtocolID
     * @return TrialDesignDTO   
     * @throws PAException on error 
     */
    public NCISpecificInfoDTO getNCISpecificInfo(String spid) throws PAException {                 
       return new NCISpecificInfoDAO().getNCISpecificInfo(spid);      
    }
    
    /**
     * @param nciSpecificInfoData the abstracted data
     * @return NCI Specific Info DTO object
     * @throws PAException on error
     */
    public NCISpecificInfoDTO 
              updateNCISpecificData(NCISpecificInfoData nciSpecificInfoData) throws PAException {               
        return new NCISpecificInfoDAO().updateNCISpecificData(nciSpecificInfoData);      
     }

}