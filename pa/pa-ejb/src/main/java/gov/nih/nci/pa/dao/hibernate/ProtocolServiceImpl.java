package gov.nih.nci.pa.dao.hibernate;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

//import org.hibernate.Session;

//import gov.nih.nci.pa.bo.Protocol;
import gov.nih.nci.pa.service.IProtocolService;
//import gov.nih.nci.pa.util.HibernateUtil;

/**
 * @author Harsha
 */
public class ProtocolServiceImpl implements IProtocolService {

    /**
     * @param id object id
     * @return long title
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String getProtocolLongTitleText(long id) {
//      Session s = HibernateUtil.getCurrentSession();
//      s.flush();
//      Protocol p = (Protocol) s.get(Protocol.class, id);
//      return p.getLongTitleText();
      return "A Phase I study of Taxol in refractory leukemia in children";
    }
    
}