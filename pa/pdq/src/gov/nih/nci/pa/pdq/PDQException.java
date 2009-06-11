package gov.nih.nci.pa.pdq;

/**
 * @author hreinhart
 *
 */
public class PDQException extends Exception {

    /**
     * 
     */
    public PDQException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public PDQException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public PDQException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public PDQException(Throwable cause) {
        super(cause);
    }

}
