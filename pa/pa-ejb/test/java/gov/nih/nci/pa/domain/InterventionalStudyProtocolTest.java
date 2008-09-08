package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class InterventionalStudyProtocolTest {

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
    public void createInterventionalStudyProtocolTest() {
        InterventionalStudyProtocol create = createInterventionalStudyProtocolObj();
        Session session  = TestSchema.getSession();
        Serializable cid = session.save(create);
        assertNotNull(cid);
        InterventionalStudyProtocol saved = (InterventionalStudyProtocol) session.load(InterventionalStudyProtocol.class, cid);
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
        assertEquals("DelayedpostingIndicator does not match " , 
                create.getDelayedpostingIndicator() , saved.getDelayedpostingIndicator());
        assertEquals("FdaRegulatedIndicator does not match " , 
                create.getFdaRegulatedIndicator() , saved.getFdaRegulatedIndicator());
        assertEquals("Allocation Code does not match " , 
                create.getAllocationCode() , saved.getAllocationCode());
    
    }
    
    /**
     * 
     * @return StudyProtocol
     */    
    public static InterventionalStudyProtocol createInterventionalStudyProtocolObj() {
        InterventionalStudyProtocol isp = new InterventionalStudyProtocol();
        
        isp.setAcronym("Acronym .....");
        isp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        isp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        isp.setExpandedAccessIndicator(Boolean.TRUE);
        isp.setIdentifier("NCI-2008-0001");
        isp.setMonitorCode(MonitorCode.CCR);
        isp.setOfficialTitle("Cancer for kids");
        isp.setPhaseCode(PhaseCode.I);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        isp.setPrimaryCompletionDate(now);
        isp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        now = new java.sql.Timestamp((new java.util.Date()).getTime());
        isp.setStartDate(now);
        isp.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        isp.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        isp.setUserLastUpdated("Abstractor");
        isp.setFdaRegulatedIndicator(Boolean.FALSE);
        isp.setAllocationCode(AllocationCode.NA);
        isp.setDelayedpostingIndicator(Boolean.FALSE);
        
        return isp;
    }
    

}
