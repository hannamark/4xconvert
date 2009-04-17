package gov.nih.nci.coppa.services.pa.grid.remote;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public class InvokeCoppaServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvokeCoppaServiceException(String message) {
		super(message);
	}

	public InvokeCoppaServiceException(Throwable cause) {
		super(cause);
	}

	public InvokeCoppaServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
