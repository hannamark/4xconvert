package gov.nih.nci.service;

import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockDiseaseConditionService implements DiseaseCondServiceRemote{

    private final HashMap<Long, List<DiseaseConditionDTO>> diseaseCondMap = 
        new HashMap<Long, List<DiseaseConditionDTO>>();
    
    public MockDiseaseConditionService() {
        DiseaseConditionDTO conditionDTO = new DiseaseConditionDTO();
        conditionDTO.setDiseaseCode("What is This disease");
        List<DiseaseConditionDTO> list = new ArrayList<DiseaseConditionDTO>();
        list.add(conditionDTO);
        diseaseCondMap.put(1L, list);
    }
    public List<DiseaseConditionDTO> getDiseaseCondition(Long protocolID)
            throws PAException {
        List<DiseaseConditionDTO> l = diseaseCondMap.get(protocolID);
        if (l == null) l = Collections.EMPTY_LIST;
        return l;
    }
    
}