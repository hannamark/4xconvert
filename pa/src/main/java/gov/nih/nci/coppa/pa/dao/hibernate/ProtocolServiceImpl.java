package gov.nih.nci.coppa.pa.dao.hibernate;

import gov.nih.nci.coppa.pa.service.IProtocolService;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Harsha
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProtocolServiceImpl extends HibernateDaoSupport implements IProtocolService {

	public void getProtocol() {
		// TODO Auto-generated method stub
		
	}

}