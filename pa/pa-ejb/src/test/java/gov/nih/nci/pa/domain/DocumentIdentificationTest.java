package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.AssigningAuthorityCode;

import java.io.Serializable;

import org.junit.Test;

/**
 * Junit test for Document class.
 * @author Naveen Amiruddin
 *
 */
public class DocumentIdentificationTest extends CommonTest {
    
    
    /**
     * 
     */
    @Test
    public void createDocumentIdentificationTest() {
        Document doc = new Document();
        
        // create document identification
        DocumentIdentification docIdentifier = new DocumentIdentification();
        docIdentifier.setAssigningAuthorityCode(AssigningAuthorityCode.NCI);

        docIdentifier.setIdentifier("NCI_2008_0001");
       
        Serializable id = session.save(doc);
        Document docSaved = (Document) session.get(Document.class, id);
        assertNotNull(id);
        assertNotNull(docSaved);
        
        // assign document to 
        docIdentifier.setDocument(docSaved);
        Serializable docIdentifierId = session.save(docIdentifier);
        DocumentIdentification docIdentifierSaved = 
            (DocumentIdentification) session.get(DocumentIdentification.class, docIdentifierId);
        assertEquals("Identifier value does not match" , 
                docIdentifier.getIdentifier(), docIdentifierSaved.getIdentifier());
        assertEquals("Assigning Authority Code does not match" , 
                docIdentifier.getAssigningAuthorityCode(), docIdentifierSaved.getAssigningAuthorityCode());

    }

}
