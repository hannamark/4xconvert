package gov.nih.nci.services;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.NullifiedRoleInterceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



/**
 * Interceptor to catch any NULLIFIED entities and throw a NullifiedCorrelationNodeException.
 */
public class NullifiedCorrelationNodeInterceptor extends NullifiedRoleInterceptor {
        
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
        if (returnValue instanceof CorrelationNodeDTO) {
            handleDTO((CorrelationNodeDTO) returnValue);
        } else if (returnValue instanceof Collection) {
            handleCollection((Collection<?>) returnValue);
        }
        
        return returnValue;
    }
    
    private void handleCollection(Collection<?> collection)
    throws NullifiedRoleException {
        if (collection == null) {
            return;
        }
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();
        for (Object object : collection) {
            Entry<Ii, Ii> entry = null;
            if (object instanceof CorrelationNodeDTO) {
                entry = handle(((CorrelationNodeDTO) object).getCorrelation());
            }
            if (entry != null) {
                nullifiedEntities.put(entry.getKey(), entry.getValue());
            }
        }
        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedRoleException(nullifiedEntities);
        }
    }

    private void handleDTO(CorrelationNodeDTO dto) throws NullifiedRoleException {
        super.handleDTO(dto.getCorrelation());
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected OrganizationServiceLocal getOrganizationServiceBean(InvocationContext invContext) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersonServiceLocal getPersonServiceBean(InvocationContext invContext) {
        return null;
    }

    
   
  

    
}
