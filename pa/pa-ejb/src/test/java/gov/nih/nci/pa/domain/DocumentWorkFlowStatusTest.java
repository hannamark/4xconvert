package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import java.io.Serializable;

import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class DocumentWorkFlowStatusTest extends CommonTest {

    /**
     * 
     */
    @Test
    public void createStudyProtocolTest() {
        
        StudyProtocol sp = new StudyProtocol();
        DocumentWorkflowStatus create = new DocumentWorkflowStatus();
        
        try {
            sp.setOfficialTitle("Breast Cancer..");
            sp.setMonitorCode(MonitorCode.CCR);
            sp.setPhaseCode(PhaseCode.I);
            java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
            Serializable spid = session.save(sp);
            StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
            assertNotNull(spid);
            create.setStudyProtocol(spSaved);
            create.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
            create.setDocumentWorkflowStatusDate(now);
            Serializable id = session.save(create);
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

            System.out.println("end");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
