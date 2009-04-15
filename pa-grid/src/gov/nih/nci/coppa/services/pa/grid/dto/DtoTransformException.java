package gov.nih.nci.coppa.services.pa.grid.dto;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public class DtoTransformException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construct with empty cause and message.
     */
    public DtoTransformException() {
    }

    /**
     * Construct with given message and empty cause.
     *
     * @param message message.
     */
    public DtoTransformException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Construct with given cause and empty message.
     *
     * @param cause underlying cause.
     */
    public DtoTransformException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct with given cause and message.
     *
     * @param message message.
     * @param cause underlying cause.
     */
    public DtoTransformException(String message, Throwable cause) {
        super(message, cause);
    }

}
