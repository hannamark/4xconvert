package gov.nih.nci.coppa.iso;

/**
 *
 * @author mshestopalov
 *
 */
public class IsoCloneException extends RuntimeException {

    private static final long serialVersionUID = -8652832715145657044L;

    /**
     * const.
     * @param e runtime.
     */
    public IsoCloneException(Throwable e) {
        super(e);
    }

}
