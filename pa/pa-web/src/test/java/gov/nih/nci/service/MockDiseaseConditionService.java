package gov.nih.nci.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.PAException;

/**
 * adapted from PO.
 * @author
 *
 */
public class MockDiseaseConditionService implements DiseaseCondServiceLocal{

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