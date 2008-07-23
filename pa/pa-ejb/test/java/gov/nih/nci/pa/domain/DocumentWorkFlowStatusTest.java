package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
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
public class DocumentWorkFlowStatusTest {

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
                create.getDocumentWorkflowStatusCode() , 
                saved.getDocumentWorkflowStatusCode());
        assertEquals("Document Workflow Status date not match " , 
                create.getDocumentWorkflowStatusDate() , 
                saved.getDocumentWorkflowStatusDate());
        assertEquals("Study Protocol not match " , 
                create.getStudyProtocol() , 
                saved.getStudyProtocol());

    }
        
    public static DocumentWorkflowStatus createDocumentWorkflowStatus(StudyProtocol sp) {
        DocumentWorkflowStatus create = new DocumentWorkflowStatus();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        create.setDocumentWorkflowStatusDate(now);
        return create ;
    }
}


