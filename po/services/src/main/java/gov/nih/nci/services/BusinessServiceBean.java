package gov.nih.nci.services;


import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Entity;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.util.PoHibernateSessionInterceptor;
import gov.nih.nci.services.entity.NullifiedEntityException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.jboss.annotation.security.SecurityDomain;

import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;

/**
 * Remote Service that contains methods which scope extends a simple entity. 
 *
 * @author ludetc
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ AuthorizationInterceptor.class, PoHibernateSessionInterceptor.class,
    NullifiedEntityNodeInterceptor.class })
@SecurityDomain("po")
public class BusinessServiceBean implements BusinessServiceRemote {

    private static final String DEFAULT_METHOD_ACCESS_ROLE = "client";
    
    private OrganizationServiceLocal orgService;
    private PersonServiceLocal personService;

    /**
     * @param svc service, injected
     */
    @EJB
    public void setOrganizationServiceBean(OrganizationServiceLocal svc) {
        this.orgService = svc;
    }

    /**
     * @return orgService that was injected by container.
     */
    public OrganizationServiceLocal getOrganizationServiceBean() {
        return this.orgService;
    }

    /**
     * @param svc service, injected
     */
    @EJB
    public void setPersonServiceBean(PersonServiceLocal svc) {
        this.personService = svc;
    }
    
    /**
     * @return orgService that was injected by container.
     */
    public PersonServiceLocal getPersonServiceBean() {
        return this.personService;
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public EntityNodeDto getEntityByIdWithCorrelations(Ii id, Cd[] players, Cd[] scopers) 
    throws NullifiedEntityException {       

        Entity entity = null;
        if (IdConverter.PERSON_ROOT.equals(id.getRoot())) {        
            entity = personService.getById(IiConverter.convertToLong(id));
        } else {
            entity = orgService.getById(IiConverter.convertToLong(id));      
        }
        if (entity == null) {
            return null;
        } else {
            return EntityNodeDtoConverter.convertToEntityNodeDto(entity, players, scopers);
        }
    }



}
