package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class DocumentWorkFlowStatusTest {

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
    public void createDocumentWorkFlowStatusTest() {
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Session session  = TestSchema.getSession();
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);
        DocumentWorkflowStatus create = createDocumentWorkflowStatus(spSaved);
        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        
        DocumentWorkflowStatus saved = 
                (DocumentWorkflowStatus) session.load(DocumentWorkflowStatus.class, id);
        assertEquals("Document Workflow Status code does not match " , 
                create.getStatusCode() , saved.getStatusCode());
        assertEquals("Common text does not match " , 
                create.getCommentText() , saved.getCommentText());
        assertEquals("Document Workflow Status date not match " , create.getStatusDateRangeLow() , 
                saved.getStatusDateRangeLow());
        assertEquals("Study Protocol not match " , create.getStudyProtocol() , saved.getStudyProtocol());
        assertEquals("User Last updated does not match " , create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , create.getDateLastUpdated() , saved.getDateLastUpdated());
        

    }
    
    /**
     * 
     * @param sp StudyProtocol
     * @return DocumentWorkflowStatus
     */
    public  static DocumentWorkflowStatus createDocumentWorkflowStatus(StudyProtocol sp) {
        DocumentWorkflowStatus create = new DocumentWorkflowStatus();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        create.setStatusDateRangeLow(now);
        create.setCommentText("Common Text");
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}


