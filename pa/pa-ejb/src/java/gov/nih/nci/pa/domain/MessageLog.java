package gov.nih.nci.pa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Harsha
 * @since 12/09/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "MESSAGES_LOG")
public class MessageLog implements Serializable {

    private static final long serialVersionUID = 8652324159502559218L;
    private Long id;
    //private StudyProtocol studyProtocol;
    private Long studyProtocol;
    private String entityName;
    private String assignedIdentifier;
    private Date dateCreated;
    private String messageAction;
    private Boolean result; 
    private String exceptionMessage;
    
    /**
     * set id.
     * @param id id
     */
     public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)       
    @Column(name = "IDENTIFIER")
    public Long getId() {
        return this.id;
    }
    
//    /**
//     * 
//     * @return StudyProtocol
//     */
//    @ManyToOne
//    @JoinColumn(name = "STUDY_PROTOCOL_IDENTIFIER", nullable = false)
//    public Long getStudyProtocol() {
//        return studyProtocol;
//    }
//    
//    /**
//     * 
//     * @param studyProtocol studyProtocol
//     */
//    public void setStudyProtocol(StudyProtocol studyProtocol) {
//        this.studyProtocol = studyProtocol;
//    }

    /**
     * 
     * @return statusDateRangeLow
     */
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreated() {
        return this.dateCreated;
    }

    /**
     * @param createdDate last updated date
     */
    public void setDateCreated(Date createdDate) {
        this.dateCreated = createdDate;
    }

    /**
     * @return the entityName
     */
    @Column(name = "ENTITY_NAME")
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the assignedIdentifier
     */
     @Column(name = "ASSIGNED_IDENTIFIER")
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }

    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }

    /**
     * @return the messageAction
     */
    @Column(name = "MESSAGE_ACTION")
    public String getMessageAction() {
        return messageAction;
    }

    /**
     * @param messageAction the messageAction to set
     */
    public void setMessageAction(String messageAction) {
        this.messageAction = messageAction;
    }

    /**
     * @return the result
     */
    @Column(name = "RESULT")
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

    /**
     * @return the exceptionMessage
     */
    @Column(name = "EXCEPTION_MESSAGE")
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * @param exceptionMessage the exceptionMessage to set
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * @return the studyProtocol
     */
    @Column(name = "STUDY_PROTOCOL_IDENTIFIER")
    public Long getStudyProtocol() {
        return studyProtocol;
    }

    /**
     * @param studyProtocol the studyProtocol to set
     */
    public void setStudyProtocol(Long studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    
   }
