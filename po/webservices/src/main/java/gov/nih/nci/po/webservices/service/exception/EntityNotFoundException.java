/**
 * 
 */
package gov.nih.nci.po.webservices.service.exception;

/**
 * This exception is thrown when an entity is not found in the database.
 * 
 * @author Rohit Gupta
 * 
 */
public class EntityNotFoundException extends ServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            - Exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     *            - Throwable
     */
    public EntityNotFoundException(Throwable cause) {
        super(cause);

    }

    /**
     * @param message
     *            - Exception message
     * @param cause
     *            - Throwable
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
