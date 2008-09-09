package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.List;

import gov.nih.nci.pa.enums.AllocationCode;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.util.TestSchema;


import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyProtocolTest  {

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
    public void createStudyProtocolTest() {
        
        StudyProtocol create = createStudyProtocolObj();
        Session session  = TestSchema.getSession();
        Serializable cid = session.save(create);
        assertNotNull(cid);
        StudyProtocol saved = (StudyProtocol) session.load(StudyProtocol.class, cid);
        assertNotNull(saved);
        assertEquals("Acronym does not match " , create.getAcronym(), saved.getAcronym());
        assertEquals("Data Monitoring Appointed Commitee does not match " , create.getDataMonitoringCommitteeAppointedIndicator(), 
                            saved.getDataMonitoringCommitteeAppointedIndicator());
        assertEquals("Accrual Reporting Method code does not match " , 
                create.getAccrualReportingMethodCode().getCode(), saved.getAccrualReportingMethodCode().getCode());
        assertEquals("Expanded Access Indicator does not  match " , 
                create.getExpandedAccessIndicator(), saved.getExpandedAccessIndicator());
        assertEquals("Identifer does not match " , create.getIdentifier() , saved.getIdentifier());
        assertEquals("Monitor code does not match " , create.getMonitorCode(), saved.getMonitorCode());
        assertEquals("Official Title does not match " , create.getOfficialTitle() , saved.getOfficialTitle());
        assertEquals("Phase code does not match " , create.getPhaseCode(), saved.getPhaseCode());
        assertEquals("PrimaryCompletionDate  does not match " , 
                create.getPrimaryCompletionDate(), saved.getPrimaryCompletionDate());
        assertEquals("PrimaryCompletionDateTypeCode  does not match " , 
                create.getPrimaryCompletionDateTypeCode().getCode(), 
                saved.getPrimaryCompletionDateTypeCode().getCode());
        assertEquals("StartDate Does not match ", create.getStartDate() , saved.getStartDate());  
        assertEquals("StartDate Type code Does not match ", create.getStartDateTypeCode() , 
                saved.getStartDateTypeCode());  
        assertEquals("Status Date Does not match ", create.getUserLastUpdated() , 
                saved.getUserLastUpdated());  
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
            
    }
    
    /**
     * 
     */
    @Test
    public void createStudyProtocolWithWFStatusTest() {
        Session session  = TestSchema.getSession();

        StudyProtocol sp = createStudyProtocolObj();
        //TestSchema.addUpdObject(sp);
        Serializable cid = session.save(sp);
        assertNotNull(sp.getId());

        DocumentWorkflowStatus dfs1 = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(dfs1);
        assertNotNull(dfs1.getId());

        DocumentWorkflowStatus dfs2 = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(dfs2);
        assertNotNull(dfs2.getId());

        StudyProtocol saved = (StudyProtocol) session.load(StudyProtocol.class, sp.getId());
        List dwfs = saved.getDocumentWorkflowStatuses();
//        assertEquals("Document Workflow status size does not match " , 
//                saved.getDocumentWorkflowStatuses().size() , 2);
        //@todo: this is not working to fix it
        
    }
    
    /**
     * 
     * @return StudyProtocol
     */    
    public static StudyProtocol createStudyProtocolObj() {
        StudyProtocol sp = new StudyProtocol();
        
        sp.setAcronym("Acronym .....");
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setExpandedAccessIndicator(Boolean.TRUE);
        sp.setIdentifier("NCI-2008-0001");
        sp.setMonitorCode(MonitorCode.CCR);
        sp.setOfficialTitle("Cancer for kids");
        sp.setPhaseCode(PhaseCode.I);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sp.setPrimaryCompletionDate(now);
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        now = new java.sql.Timestamp((new java.util.Date()).getTime());
        sp.setStartDate(now);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        sp.setUserLastUpdated("Abstractor");
        return sp;
    }
    
}
