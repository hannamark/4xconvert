package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.jboss.annotation.security.SecurityDomain;

/**
 * @author Vrushali
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SecurityDomain("pa")
@RolesAllowed({"gridClient", "client", "Abstractor" , "Submitter" , "Outcomes" })
public class StudyProtocolStageServiceBean extends StudyProtocolStageBeanLocal
    implements StudyProtocolStageServiceRemote {

}
