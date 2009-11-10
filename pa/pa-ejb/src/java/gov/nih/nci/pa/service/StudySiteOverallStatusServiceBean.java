package gov.nih.nci.pa.service;

import gov.nih.nci.pa.util.HibernateSessionInterceptor;

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
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SecurityDomain("pa")
@RolesAllowed({"gridClient", "client" , "Abstractor" , "Submitter" , "Outcomes" })
public  class StudySiteOverallStatusServiceBean extends StudySiteOverallStatusBeanLocal 
 implements StudySiteOverallStatusServiceRemote {
    

}
