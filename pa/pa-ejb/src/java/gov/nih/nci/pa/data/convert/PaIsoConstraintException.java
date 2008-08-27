/**
 * 
 */
package gov.nih.nci.pa.data.convert;

/**
  * This exception is used to indicate that ISO data was provided that PA can not handle.
  * The data likely complies to the ISO standard, but is not processable by PA.  Modified
  * from code originally written for PO.
  * @author Hugh Reinhart
  */
public class PaIsoConstraintException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that takes the message.
     * @param message the message.
     */
    public PaIsoConstraintException(String message) {
        super(message);
    }

    /**
     * Constructor that takes the message.
     * @param message the message.
     * @param cause root cause.
     */
    public PaIsoConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
