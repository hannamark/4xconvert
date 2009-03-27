package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Storing the protocol ids for every incoming messages.
 * @author  Harsha
 * @since  03/25/2009
 */
@Entity
@Table(name = "MESSAGES_LOG_AUDIT")
public class MessageLogAudit extends AbstractEntity {
    
    private Long studyProtocolIdentifier;
    private Long messageIdentifier;
    
    /**
     * @return the studyProtocolIdentifier
     */
    @Column(name = "study_protocol_identifier")
    public Long getStudyProtocolIdentifier() {
        return studyProtocolIdentifier;
    }
    /**
     * @param studyProtocolIdentifier the studyProtocolIdentifier to set
     */
    public void setStudyProtocolIdentifier(Long studyProtocolIdentifier) {
        this.studyProtocolIdentifier = studyProtocolIdentifier;
    }
    /**
     * @return the messageIdentifier
     */
    @Column(name = "message_log_identifier")
    public Long getMessageIdentifier() {
        return messageIdentifier;
    }
    /**
     * @param messageIdentifier the messageIdentifier to set
     */
    public void setMessageIdentifier(Long messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }
 
   

    
    
  
}
