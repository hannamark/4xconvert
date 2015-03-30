package gov.nih.nci.po.data.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Logging CTRP JMS messages.
 * @author Kalpana Guthikonda
 */

@Entity
public class CtepJMSLogRecord {

    private String messageId;
    private String ctepJmsMsg;
    private Date createdDate;
    private String processingErrors;

    /**
     * For unit tests only.
     * @param messageId msg id
     * @param ctepJmsMsg jms msg
     * @param createdDate date
     */
    public CtepJMSLogRecord(String messageId, String ctepJmsMsg, Date createdDate) {
        this.messageId = messageId;
        this.ctepJmsMsg = ctepJmsMsg;
        this.createdDate = createdDate;
    }

    /**
     * @deprecated hibernate-only constructor
     */
    @Deprecated
    public CtepJMSLogRecord() {
        // for hibernate only - do nothing
        super();
    }

    /**
     * @return messageId
     */
    @Id
    @Column(name = "messageid")
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId to set messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return ctepJmsMsg
     */
    @Column(name = "ctepjms_msg")
    public String getCtepJmsMsg() {
        return ctepJmsMsg;
    }

    /**
     * @param ctepJmsMsg to set ctepJmsMsg
     */
    public void setCtepJmsMsg(String ctepJmsMsg) {
        this.ctepJmsMsg = ctepJmsMsg;
    }

    /**
     * @return createdDate
     */
    @Column(name = "created_date")
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate to set createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the processingErrors
     */
    @Column(name = "processing_errors")
    public String getProcessingErrors() {
        return processingErrors;
    }

    /**
     * @param processingErrors the processingErrors to set
     */
    public void setProcessingErrors(String processingErrors) {
        this.processingErrors = processingErrors;
    }

}
