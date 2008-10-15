package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * @author Kalpana Guthikonda
 * @since 10/13/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Remote
public interface SubGroupsServiceRemote {
    /**
     * @param studyProtocolIi Ii 
     * @return SubGroupsDTO
     * @throws PAException PAException
     */
    List<StratumGroupDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException;
    
    /**
     * @param sgDTO SubGroupsDTO 
     * @return DocumentDTO
     * @throws PAException PAException
     */
    StratumGroupDTO create(StratumGroupDTO sgDTO) throws PAException;
    
    /**
     * @param id Ii 
     * @return SubGroupsDTO
     * @throws PAException PAException
     */
    StratumGroupDTO get(Ii id) throws PAException;

    /**
     * @param sgDTO SubGroupsDTO 
     * @return SubGroupsDTO
     * @throws PAException PAException
     */
    StratumGroupDTO update(StratumGroupDTO sgDTO) throws PAException;

    /**
     * @param id Ii  
     * @return Boolean
     * @throws PAException PAException
     */
    Boolean delete(Ii id) throws PAException;    


}
