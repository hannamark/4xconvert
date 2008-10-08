package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.DocumentTypeCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;
/**
 * 
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name =  "DOCUMENT")
public class Document extends AbstractEntity {

    private DocumentTypeCode typeCode;
    private StudyProtocol studyProtocol;
    private Boolean activeIndicator;
    private String fileName;
    private String inactiveCommentText;
    
    /**
     * 
     * @return typeCode
     */
    @Column(name = "TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public DocumentTypeCode getTypeCode() {
        return typeCode;
    }
    
    /**
     * 
     * @param typeCode typeCode
     */
    public void setTypeCode(DocumentTypeCode typeCode) {
        this.typeCode = typeCode;
    }
    
    /**
     * 
     * @return protocol
     */
    @ManyToOne
    @JoinColumn(name = "STUDY_PROTOCOL_ID", updatable = false)
    @NotNull
    public StudyProtocol getStudyProtocol() {
        return studyProtocol;
    }
    
    /**
     * @param studyProtocol studyProtocol
     */
    public void setStudyProtocol(StudyProtocol studyProtocol) {
        this.studyProtocol = studyProtocol;
    }
    
    /**
     * 
     * @return activeIndicator
     */
    @Column(name = "ACTIVE_INDICATOR")
    public Boolean getActiveIndicator() {
        return activeIndicator;
    }
    
    
    /**
     * @param activeIndicator activeIndicator
     */
    public void setActiveIndicator(Boolean activeIndicator) {
        this.activeIndicator = activeIndicator;
    }
    
    /**
     * 
     * @return fileName
     */
    @Column(name = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }
    
    
    /**
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * 
     * @return inactiveCommentText
     */
    @Column(name = "INACTIVE_COMMENT_TEXT")
    public String getInactiveCommentText() {
        return inactiveCommentText;
    }
    
    /**
     * 
     * @param inactiveCommentText inactiveCommentText
     */
    public void setInactiveCommentText(String inactiveCommentText) {
        this.inactiveCommentText = inactiveCommentText;
    }
}
