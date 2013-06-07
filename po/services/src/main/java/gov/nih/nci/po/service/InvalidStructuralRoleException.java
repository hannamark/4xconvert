/**
 * 
 */
package gov.nih.nci.po.service;

import java.util.HashMap;
import java.util.Map;

/**
 * A sub-type of {@link CurateEntityValidationException} that indicates to the caller that an entity could not
 * curated successfully because one or more of its roles would be in an invalid state.
 * @author Denis G. Krylov
 * 
 */
public class InvalidStructuralRoleException extends
        CurateEntityValidationException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param errors validation messages
     */
    public InvalidStructuralRoleException(Map<String, String[]> errors) {
        super(errors);
    }

    /**
     * @param msg
     *            validation messages
     */
    public InvalidStructuralRoleException(String msg) {
        this(new HashMap<String, String[]>());
        getErrors().put("", new String[] {msg});
    }

}
