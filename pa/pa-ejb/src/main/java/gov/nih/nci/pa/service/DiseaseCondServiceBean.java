package gov.nih.nci.pa.service;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.service.impl.DiseaseCondServiceImpl;

/**
 * 
 * @author Harsha
 *
 */
@Stateless
public class DiseaseCondServiceBean implements DiseaseCondServiceRemote, DiseaseCondServiceLocal {

    /**
     * @param protocolID Long
     * @return DiseaseConditionDTO
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DiseaseConditionDTO> getDiseaseCondition(Long protocolID)
            throws PAException {
       return new DiseaseCondServiceImpl().getDiseaseCondition(protocolID);
    }

}
