package gov.nih.nci.coppa.services.pa.grid.remote;

/**
 * Exception thrown if there is a problem calling a remote EJB.
 */
public class InvokeCoppaServiceException extends RuntimeException {
    /**
     * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
     * If you need to modify this file (bug?), change in po-grid and re-import to this location.
     */

    private static final long serialVersionUID = 1L;

    /**
     * @param message error message
     */
    public InvokeCoppaServiceException(String message) {
        super(message);
    }

    /**
     * @param cause underlying cause of the exception
     */
    public InvokeCoppaServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message error message
     * @param cause underlying cause of the exception
     */
    public InvokeCoppaServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
