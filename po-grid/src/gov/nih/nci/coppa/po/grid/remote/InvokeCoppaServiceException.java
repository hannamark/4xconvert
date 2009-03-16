package gov.nih.nci.coppa.po.grid.remote;

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
