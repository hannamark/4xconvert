package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class DocumentTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createDocumentTest() {
        
        Document create = new Document();
        try {
            create.setOfficialTitle("Breast Cancer..");
            Serializable cid = session.save(create);
            assertNotNull(cid);
            System.out.println("...4");
            Document saved = (Document) session.load(Document.class, cid);
            System.out.println("...5");
            assertNotNull(saved);
            System.out.println("...6");
            assertEquals("Official Title does not match " , create.getOfficialTitle() , saved.getOfficialTitle());
            transaction.commit();
            System.out.println("...8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
