package gov.nih.nci.pa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;


/**
 * This class is an abstract concept that contains attributes common to all types of study documents.
 * 
 * @author Naveen Amiruddin
 * @since 07/07/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Document extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private String officialTitle = null;

    /** . **/
    public List<DocumentIdentification> documentIdentifications = new ArrayList<DocumentIdentification>(); 
    
    /**
     * The formal title of the document. 
     * @return officialTitle
     */
    @Column(name = "OFFICIAL_TITLE")
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * 
     * @return documentIdentifications
     */
    
    @OneToMany(mappedBy = "document")
    public List<DocumentIdentification> getDocumentIdentifications() {
        return documentIdentifications;
    }

    /**
     * 
     * @param documentIdentifications documentIdentifications
     */
    
    public void setDocumentIdentifications(
            List<DocumentIdentification> documentIdentifications) {
        this.documentIdentifications = documentIdentifications;
    }
    
}
