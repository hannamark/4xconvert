/**
 * 
 */
package gov.nih.nci.po.webservices.service.exception;

/**
 * @author Denis G. Krylov
 * 
 */
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            - Exception message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     *            - Throwable
     */
    public ServiceException(Throwable cause) {
        super(cause);

    }

    /**
     * @param message
     *            - Exception message
     * @param cause
     *            - Throwable
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
