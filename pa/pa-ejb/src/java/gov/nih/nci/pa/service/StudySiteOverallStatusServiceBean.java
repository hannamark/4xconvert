package gov.nih.nci.pa.service;


import static gov.nih.nci.pa.service.AbstractBaseIsoService.ABSTRACTOR_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.CLIENT_ROLE;
import static gov.nih.nci.pa.service.AbstractBaseIsoService.SUBMITTER_ROLE;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.jboss.annotation.security.SecurityDomain;

/**
 *
 * @author Vrushali
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SecurityDomain("pa")
@RolesAllowed({CLIENT_ROLE, ABSTRACTOR_ROLE, SUBMITTER_ROLE })
public  class StudySiteOverallStatusServiceBean extends StudySiteOverallStatusBeanLocal
 implements StudySiteOverallStatusServiceRemote {


}
