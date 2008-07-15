package gov.nih.nci.po.service;

/**
 * Validation exceptions for SearchCriteria.
 * @author smatyas
 */
public class SearchCriteriaValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * @param msg error message
     */
    public SearchCriteriaValidationException(String msg) {
        super(msg);
    }
}