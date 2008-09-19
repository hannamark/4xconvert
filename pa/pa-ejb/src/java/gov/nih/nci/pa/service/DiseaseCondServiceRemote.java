package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dto.DiseaseConditionDTO;

import java.util.List;

import javax.ejb.Remote;

/**
 * 
 * @author hjayanna
 *
 */
@Remote
public interface DiseaseCondServiceRemote {
    
    /**
     * 
     * @param protocolID Long
     * @return diseaseConditionDTO
     * @throws PAException on exception
     */
    List<DiseaseConditionDTO> getDiseaseCondition(Long protocolID) throws PAException;
    

}
