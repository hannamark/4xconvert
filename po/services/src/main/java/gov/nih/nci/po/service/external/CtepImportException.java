package gov.nih.nci.po.service.external;

/**
 * @author Hugh Reinhart
 * @since Feb 22, 2013
 */
public class CtepImportException extends Exception {

    private static final long serialVersionUID = -211894506846113486L;

    /**
     * @param shortMessage a short message designed for display in PO import screen
     * @param message the message
     */
    public CtepImportException(String shortMessage, String message) {
        super(message);
        this.shortMessage = shortMessage;
    }

    /**
     * @param shortMessage a short message designed for display in PO import screen
     * @param message the message
     * @param cause the cause
     */
    public CtepImportException(String shortMessage, String message, Throwable cause) {
        super(message, cause);
        this.shortMessage = shortMessage;
    }

    private String shortMessage;

    /**
     * @return the shortMessage
     */
    public String getShortMessage() {
        return shortMessage;
    }

    /**
     * @param shortMessage the shortMessage to set
     */
    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }
}
