/**
 * 
 */
package gov.nih.nci.pa.service.internal;

import gov.nih.nci.pa.domain.StratumGroup;
import gov.nih.nci.pa.iso.convert.StratumGroupConverter;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.service.AbstractStudyIsoService;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StratumGroupBeanLocal extends 
AbstractStudyIsoService<StratumGroupDTO, StratumGroup, StratumGroupConverter> implements StratumGroupServiceLocal {

}
