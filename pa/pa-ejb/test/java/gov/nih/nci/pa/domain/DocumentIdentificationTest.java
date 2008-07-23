package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.AssigningAuthorityCode;
import gov.nih.nci.pa.test.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit test for Document class.
 * @author Naveen Amiruddin
 *
 */
public class DocumentIdentificationTest {
    

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }    
    /**
     * 
     */
    @Test
    public void createDocumentIdentificationTest() {
        Session session = TestSchema.getSession();

        Document doc = DocumentTest.createDocumentObj();
        TestSchema.addUpdObject(doc);
        Serializable id = doc.getId();
        Document docSaved = (Document) session.get(Document.class, id);

        assertNotNull(id);
        assertNotNull(docSaved);
        
        DocumentIdentification docIdentifier = createDocumentIdentificationObj(docSaved);

        // assign document to 
        TestSchema.addUpdObject(docIdentifier);

        Serializable docIdentifierId = docIdentifier.getId();
        DocumentIdentification docIdentifierSaved = 
            (DocumentIdentification) session.get(DocumentIdentification.class, docIdentifierId);
        
        assertEquals("Identifier value does not match" , 
                docIdentifier.getIdentifier(), docIdentifierSaved.getIdentifier());
        assertEquals("Assigning Authority Code does not match" , 
                docIdentifier.getAssigningAuthorityCode(), docIdentifierSaved.getAssigningAuthorityCode());

    }
    
    public static DocumentIdentification createDocumentIdentificationObj(Document doc) {

        DocumentIdentification docIdentifier = new DocumentIdentification();
        docIdentifier.setAssigningAuthorityCode(AssigningAuthorityCode.NCI);
        docIdentifier.setIdentifier("NCI_2008_0001");
        docIdentifier.setDocument(doc);
        return docIdentifier ;
    }

}
