package gov.nih.nci.po.service;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author smatyas
 *
 */
public class CurateEntityValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final Map<String, String[]> errors;

    /**
     * @param errors validation messages
     */
    public CurateEntityValidationException(Map<String, String[]> errors) {
        super();
        this.errors = errors;
    }

    /**
     * @return error messages
     */
    public String getErrorMessages() {
        return EntityValidationException.getFormattedErrorMsgs(errors);
    }
    /**
     * messages followed by trace.
     * 
     * @param s stream.
     */
    @Override
    public void printStackTrace(PrintWriter s) {
        s.println(getErrorMessages());
        super.printStackTrace(s);
    }

    /**
     * messages followed by trace.
     * 
     * @param s stream.
     */
    @Override
    public void printStackTrace(PrintStream s) {
        s.println(getErrorMessages());
        super.printStackTrace(s);
    }
    
    /**
     * @return all validation error messages keyed by the offending property path.
     */
    public Map<String, String[]> getErrors() {
        return errors;
    }

}
