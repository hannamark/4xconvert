package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.pa.enums.DocumentTypeCode;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class DocumentTest {
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    /**
     * 
     */
    @Test
    public void createDocumentTest() {
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        Document create = createDocumentobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);
        
        Document saved = new Document();
        saved = (Document) session.load(Document.class, id);
        assertEquals("Document type code does not match " , create.getTypeCode() , 
                saved.getTypeCode());
        assertEquals("Document FileName does not match " , create.getFileName() , 
                saved.getFileName());
        assertEquals("Document ActiveIndicator date does not match " , create.getActiveIndicator() , 
                saved.getActiveIndicator());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    } /**
     * 
     * @param sp StudyProtocol
     * @return Document
     */
    public static Document createDocumentobj(StudyProtocol sp) {
        Document create = new Document();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setTypeCode(DocumentTypeCode.Protocol_Document);
        create.setActiveIndicator(true);
        create.setFileName("Protocol_Document.doc");
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
