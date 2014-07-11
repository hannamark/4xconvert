package gov.nih.nci.services;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.entity.NullifiedEntityInterceptor;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Interceptor to catch any NULLIFIED entities and throw a NullifiedEntityNodeException.
 */
public class NullifiedEntityNodeInterceptor extends NullifiedEntityInterceptor {
        
    /**
     * Ensures that no object(s) returned have a NULLIFIED entity status.
     *
     * @param invContext the method context
     * @return the method result
     * @throws Exception if invoking the method throws an exception.
     */
    @AroundInvoke
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public Object checkForNullified(InvocationContext invContext) throws Exception {
        // make the call to the underlying method. This method (checkForNullified) wraps the intended method.
        Object returnValue = invContext.proceed();
        if (returnValue instanceof EntityNodeDto) {
            handleEntityNodeDto(invContext, (EntityNodeDto) returnValue);
        } else if (returnValue instanceof Collection) {
            handleCollection(invContext, (Collection<?>) returnValue);
        }
        return returnValue;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Entry<Ii, Ii> handleCollectionElement(InvocationContext invContext, Object element) {
        Entry<Ii, Ii> entry = null;
        if (element instanceof EntityNodeDto) {
            entry = handleEntity(invContext, (EntityNodeDto) element);
        } 
        return entry;
    }
    
    private void handleEntityNodeDto(InvocationContext invContext, EntityNodeDto dto) 
    throws NullifiedEntityException {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
        
        Entry<Ii, Ii> entry = handleEntity(invContext, dto);
        
        if (entry != null) {
            nullifiedEntities.put(entry.getKey(), entry.getValue());
        }
        
        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedEntityException(nullifiedEntities);
        }
    }

    private Entry<Ii, Ii> handleEntity(InvocationContext invContext, EntityNodeDto dto) {
        EntityDto entityDto = dto.getEntityDto();
        if (entityDto instanceof OrganizationDTO) {
            return handle(invContext, (OrganizationDTO) entityDto);
        } else if (entityDto instanceof PersonDTO) {
            return handle(invContext, (PersonDTO) entityDto);
        }
        
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OrganizationServiceLocal getOrganizationServiceBean(InvocationContext invContext) {
        BusinessServiceBean bBean = (BusinessServiceBean) invContext.getTarget();
        return bBean.getOrganizationServiceBean();
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersonServiceLocal getPersonServiceBean(InvocationContext invContext) {
        BusinessServiceBean bBean = (BusinessServiceBean) invContext.getTarget();
        return bBean.getPersonServiceBean();
    }
   
   
}
