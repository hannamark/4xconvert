package gov.nih.nci.po.webservices.convert.simple;

/**
 * Exception class for PO.
 * 
 * @author Rohit Gupta
 * 
 */
public class ConverterException extends RuntimeException {

    private static final long serialVersionUID = -412014421822871391L;

    /**
     * no argument constructor.
     */
    public ConverterException() {
        super();
    }

    /**
     * String constructor.
     * 
     * @param message
     *            message
     */
    public ConverterException(String message) {
        super(message);

    }

    /**
     * String and Throwable constructor.
     * 
     * @param message
     *            message
     * @param t
     *            Throwable
     * 
     */
    public ConverterException(String message, Throwable t) {
        super(message, t);
    }

}
