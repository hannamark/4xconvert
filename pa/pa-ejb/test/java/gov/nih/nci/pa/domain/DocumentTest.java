package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.nih.nci.pa.test.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class DocumentTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
        
    /**
     * 
     */
    @Test
    public void createDocumentTest() {
        
        Document create = createDocumentObj();
        Session session = TestSchema.getSession();
        try {
            TestSchema.addUpdObject(create);
            Serializable cid = create.getId();
            assertNotNull(cid);
            Document saved = (Document) session.load(Document.class, cid);
            assertNotNull(saved);
            assertEquals("Official Title does not match " , create.getOfficialTitle() , saved.getOfficialTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Document createDocumentObj() {
        Document create = new Document();
        create.setOfficialTitle("Breast Cancer..");
        return create ;
    }
}
