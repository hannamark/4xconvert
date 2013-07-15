package gov.nih.nci.pa.service;

import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * 
 * @author Reshma.Koganti
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(PaHibernateSessionInterceptor.class)
public class TrialDataVerificationServiceBean extends
    TrialDataVerificationBeanLocal implements
    TrialDataVerificationServiceRemote {

}
