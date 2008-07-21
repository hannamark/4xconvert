package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.AssigningAuthorityCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;


/**
 * The unique identification of a document in a specified context.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "Document_Identification")
public class DocumentIdentification extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;
    
    private AssigningAuthorityCode assigningAuthorityCode;
    private Boolean primaryIndicator; 
    private String identifier;
    private Document document;
    /** 
     * 
     * @return AssigningAuthorityCode
     */
    @Column(name = "ASSIGNING_AUTHORITY_CODE")
    @Enumerated(EnumType.STRING)    
    public AssigningAuthorityCode getAssigningAuthorityCode() {
        return assigningAuthorityCode;
    }

    /**
     * 
     * @param assigningAuthorityCode assigningAuthorityCode
     */
    public void setAssigningAuthorityCode(
            AssigningAuthorityCode assigningAuthorityCode) {
        this.assigningAuthorityCode = assigningAuthorityCode;
    }
    
    /**
     * 
     * @return primaryIndicator
     */
    @Column(name = "PRIMARY_INDICATOR")
    public Boolean getPrimaryIndicator() {
        return primaryIndicator;
    }

    /**
     * 
     * @param primaryIndicator primaryIndicator
     */
    public void setPrimaryIndicator(Boolean primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }

    /**
     * 
     * @return identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 
     * @return Document
     */
    
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID", updatable = false)
    @NotNull
    public Document getDocument() {
        return document;
    }
    
    /**
     * 
     * @param document document
     */
    
    public void setDocument(Document document) {
        this.document = document;
    } 
}
