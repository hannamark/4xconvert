package gov.nih.nci.services;

import gov.nih.nci.coppa.iso.Ii;

import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


/**
 * Interceptor to catch any NULLIFIED entities and throw a NullifiedEntityNodeException.
 */
public class NullifiedEntityNodeInterceptor extends AbstractBaseNullifiedInterceptor {
        
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
        }
        return returnValue;
    }
    
    private void handleEntityNodeDto(InvocationContext invContext, EntityNodeDto dto) 
    throws NullifiedEntityException {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
        
        Entry<Ii, Ii> entry = handleEntity(invContext, dto);
        
        if (entry != null) {
            nullifiedEntities.put(entry.getKey(), entry.getValue());
        }
        
        nullifiedEntities.putAll(handleCorrelations(dto));

        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedEntityException(nullifiedEntities);
        }
    }
    
    private Map<Ii, Ii> handleCorrelations(EntityNodeDto dto) {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();

        if (dto.getPlayers() != null) {
            nullifiedEntities.putAll(handle(dto.getPlayers()));        
        }
        if (dto.getScopers() != null) {
            nullifiedEntities.putAll(handle(dto.getScopers()));
        }
            
        return nullifiedEntities;
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
   
    
    
    private Map<Ii, Ii> handle(CorrelationDto[] corDtos) {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
        for (CorrelationDto corDto : corDtos) {
            Entry<Ii, Ii> entry = handle(corDto);
            if (entry != null) {
               nullifiedEntities.put(entry.getKey(), entry.getValue()); 
            }
        }
        
        return nullifiedEntities;
        
    }
}
