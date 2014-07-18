/**
 * 
 */
package gov.nih.nci.pa.webservices;

/**
 * @author dkrylov
 * 
 */
public class PoEntityNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message String
     */
    public PoEntityNotFoundException(String message) {
        super(message);
    }

}
