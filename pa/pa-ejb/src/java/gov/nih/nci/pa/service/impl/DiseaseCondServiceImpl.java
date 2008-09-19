package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.DiseaseCondDAO;
import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

/**
 * 
 * @author Harsha
 *
 */
public class DiseaseCondServiceImpl implements DiseaseCondServiceRemote {

    /**
     * @param protocolID Long
     * @throws PAException on error
     * @return DiseaseConditionDTO
     */
    public List<DiseaseConditionDTO> getDiseaseCondition(Long protocolID)
            throws PAException {        
        return new DiseaseCondDAO().getDiseaseCondition(protocolID);
    }

}
