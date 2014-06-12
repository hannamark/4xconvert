package gov.nih.nci.po.webservices.aop;

import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.aop.ThrowsAdvice;

import java.util.Map;

/**
 * This is Advice for handling Exception.
 * 
 * @author Rohit Gupta
 * 
 */
public class ExceptionHandlerAdvice implements ThrowsAdvice {

    /**
     * After Throwing advice.
     * 
     * @param e
     *            -Exception
     */
    @SuppressWarnings({ "PMD.UseStringBufferForStringAppends", "rawtypes" })
    public void afterThrowing(Exception e) {

        String excepMessage = e.getMessage();
        Throwable eCause = e.getCause();
        String rootCause = ExceptionUtils.getRootCauseMessage(e);

        if (eCause instanceof EntityValidationException) {
            EntityValidationException eve = (EntityValidationException) eCause;
            Map<String, String[]> errors = eve.getErrors();
            excepMessage += " The invalid elements are: ";
            for (Map.Entry entry : errors.entrySet()) {
                excepMessage = excepMessage + entry.getKey() + " ";
            }
            throw new ServiceException(excepMessage, e);
        } else if (rootCause.contains("ObjectNotFoundException")
                || (e instanceof EntityNotFoundException)) {
            throw new EntityNotFoundException(rootCause, e);
        } else {
            throw new ServiceException(rootCause, e);
        }
    }
}
