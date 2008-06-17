package gov.nih.nci.pa.service;

import java.util.List;

import gov.nih.nci.pa.dao.ProtocolDAO;
import gov.nih.nci.pa.dto.ProtocolDTO;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * @author Hugh Reinhart
 *
 */
@Stateless
public class ProtocolServiceBean  implements ProtocolServiceLocal, ProtocolServiceRemote {
    
    /**
     * @param sc ProtocolSearchCriteria
     * @return List ProtocolDTO    
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ProtocolDTO> getProtocol(ProtocolSearchCriteria sc) {
       try {
        ProtocolDAO dao = new ProtocolDAO();
        return dao.queryProtocol(sc);
        } catch (PAException e) {
            //@todo : throw exception 
            return null;
        }
    }
    

}
