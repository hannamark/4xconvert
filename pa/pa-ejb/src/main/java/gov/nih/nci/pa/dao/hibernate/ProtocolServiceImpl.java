package gov.nih.nci.pa.dao.hibernate;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Session;

import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.persistence.BO2DTO;
import gov.nih.nci.pa.service.IProtocolService;
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
      Session s = HibernateUtil.getCurrentSession();
      Protocol p = new Protocol();
      p = (Protocol) s.get(Protocol.class, id);
      if (p == null) {
        s.load(p, Long.valueOf(id));
      }
      ProtocolDTO rslt = new ProtocolDTO();
      rslt = BO2DTO.convert(p);
      return rslt;
    }
    
}