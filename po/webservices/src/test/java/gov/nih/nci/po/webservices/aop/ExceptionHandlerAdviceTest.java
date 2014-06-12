package gov.nih.nci.po.webservices.aop;

import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the test class for ExceptionHandlerAdvice.
 * 
 * @author Rohit Gupta
 * 
 */
public class ExceptionHandlerAdviceTest {

    /**
     * Testcase for EntityValidationException
     */
    @Test
    public void testEntityValidationException() {
        String excepMessage = null;

        Map<String, String[]> errorMap = new HashMap<String, String[]>();
        String[] error = { "703@456&454" };
        errorMap.put("Phone number", error);

        ExceptionHandlerAdvice exAdvice = new ExceptionHandlerAdvice();
        Throwable cause = new EntityValidationException(errorMap);
        Exception e = new Exception(cause);
        try {
            exAdvice.afterThrowing(e);
        } catch (ServiceException se) {
            excepMessage = se.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The invalid elements are: Phone number"));

    }

    /**
     * Testcase for ServiceException
     */
    @Test
    public void testServiceException() {
        String excepMessage = null;
        ExceptionHandlerAdvice exAdvice = new ExceptionHandlerAdvice();
        Throwable cause = new ServiceException(
                "The Person couldn't be created as either person is null.");
        Exception e = new Exception(cause);
        try {
            exAdvice.afterThrowing(e);
        } catch (ServiceException se) {
            excepMessage = se.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The Person couldn't be created as either person is null."));
    }

}
