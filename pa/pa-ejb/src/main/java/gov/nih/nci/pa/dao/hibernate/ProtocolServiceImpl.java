package gov.nih.nci.pa.dao.hibernate;



import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Session;

import gov.nih.nci.pa.dao.ProtocolDAO;
import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.persistence.BO2DTO;
import gov.nih.nci.pa.service.IProtocolService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.util.HibernateUtil;

/**
 * @author Harsha
 */
public class ProtocolServiceImpl implements IProtocolService {

    /**
     * @param id object id
     * @return protocol transfer object
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ProtocolDTO getProtocol(long id) {
      Session s = null;
      if (s == null) {
          s = HibernateUtil.getCurrentSession();
      }
      Protocol p = new Protocol();
      //Protocol getp = (Protocol) s.get(Protocol.class, id);
      //if (getp == null) {
         // p = new Protocol();
          s.load(p, Long.valueOf(id));          
     // }      
          s.evict(p);
      return BO2DTO.convert(p);
    }

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
       return null;
    }
    }
   

}