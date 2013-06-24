/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.iso.convert.StratumGroupConverter;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StratumGroupBeanLocal extends
AbstractStudyIsoService<StratumGroupDTO, StratumGroup, StratumGroupConverter> implements StratumGroupServiceLocal {

}
