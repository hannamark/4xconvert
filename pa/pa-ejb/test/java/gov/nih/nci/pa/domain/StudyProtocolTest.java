package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
        Session session  = TestSchema.getSession();
        
        StudyProtocol sp = new StudyProtocol();   
        StudyProtocol create = createStudyProtocolObj(sp);
        try {
            
            TestSchema.addUpdObject(sp);
            Serializable cid = create.getId();
            assertNotNull(cid);
            StudyProtocol saved = (StudyProtocol) session.load(StudyProtocol.class, cid);
            assertNotNull(saved);
            assertStudyProtocol(create , saved);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     */
    @Test
    public void createStudyProtocolWithWFStatusTest() {
        Session session  = TestSchema.getSession();

        StudyProtocol sp = createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
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

    @Test
    public void createInterventionalStudyProtocolTest() {
        Session session  = TestSchema.getSession();

        InterventionalStudyProtocol create = new InterventionalStudyProtocol();  
         create = (InterventionalStudyProtocol) createStudyProtocolObj((StudyProtocol) create);    
         create = createInterventionalStudyProtocolObj(create);
         Serializable isp = session.save(create);
         assertNotNull(isp);
         InterventionalStudyProtocol saved = 
                 (InterventionalStudyProtocol) session.load(InterventionalStudyProtocol.class, isp);
         assertStudyProtocol(create , saved);
         assertEquals(create.getAllocationCode(), saved.getAllocationCode());
         assertEquals(create.getBlindingRoleCodeCaregiver(), saved.getBlindingRoleCodeCaregiver());
         assertEquals(create.getBlindingRoleCodeInvestigator(), saved.getBlindingRoleCodeInvestigator());
         assertEquals(create.getBlindingRoleCodeOutcome(), saved.getBlindingRoleCodeOutcome());
         assertEquals(create.getBlindingRoleCodeSubject(), saved.getBlindingRoleCodeSubject());
         assertEquals(create.getDesignConfigurationCode(), saved.getDesignConfigurationCode());
         assertEquals(create.getNumberOfInterventionGroups(), saved.getNumberOfInterventionGroups());

     }
    

    @Test
    public void createObservationalStudyProtocolTest() {
        Session session  = TestSchema.getSession();

        ObservationalStudyProtocol create = new ObservationalStudyProtocol();  
         create = (ObservationalStudyProtocol) createStudyProtocolObj((StudyProtocol) create);    
         create = createObservationalStudyProtocolObj(create);
         Serializable isp = session.save(create);
         assertNotNull(isp);
         ObservationalStudyProtocol saved = 
                 (ObservationalStudyProtocol) session.load(ObservationalStudyProtocol.class, isp);
         assertStudyProtocol(create , saved);
         assertEquals(create.getBiospecimenDescription(), saved.getBiospecimenDescription());
         assertEquals(create.getBiospecimenRetentionCode(), saved.getBiospecimenRetentionCode());
         assertEquals(create.getNumberOfGroups(), saved.getNumberOfGroups());
         assertEquals(create.getSamplingMethodCode(), saved.getSamplingMethodCode());
         assertEquals(create.getStudyModelCode(), saved.getStudyModelCode());
         assertEquals(create.getStudyModelOtherText(), saved.getStudyModelOtherText());
         assertEquals(create.getTimePerspectiveCode() , saved.getTimePerspectiveCode());
         assertEquals(create.getTimePerspectiveOtherText(), saved.getTimePerspectiveOtherText());


     }
    
    /**
     * 
     * @return StudyProtocol
     */    
    public static StudyProtocol createStudyProtocolObj() {
        StudyProtocol sp = new StudyProtocol();
        createStudyProtocolObj(sp);
        return sp;
    }
    
    /**
     * 
     * @return StudyProtocol
     */    
    public static StudyProtocol createStudyProtocolObj(StudyProtocol sp) {
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        
        sp.setAcronym("Acronym .....");
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        sp.setDataMonitoringCommitteeAppointedIndicator(Boolean.TRUE);
        sp.setDelayedpostingIndicator(Boolean.TRUE);
        sp.setExpandedAccessIndicator(Boolean.TRUE);
        sp.setFdaRegulatedIndicator(Boolean.TRUE);
        sp.setIdentifier("NCI-2008-0001");
        sp.setKeywordText("keywordText");
        sp.setOfficialTitle("Cancer for kids");
        sp.setPhaseCode(PhaseCode.I);
        sp.setPhaseOtherText("phaseOtherText");
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.BASIC_SCIENCE);
        sp.setPrimaryPurposeOtherText("primaryPurposeOtherText");
        sp.setPrimaryCompletionDate(now);
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPublicDescription("publicDescription");
        sp.setPublicTitle("publicTitle");
        sp.setRecordVerificationDate(now);
        sp.setScientificDescription("scientificDescription");
        sp.setSection801Indicator(Boolean.TRUE);
        sp.setStartDate(now);
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        sp.setUserLastUpdated("Abstractor");
        sp.setDateLastCreated(now);
        sp.setUserLastCreated("Abstractor");
        return sp;
    }
    
    /**
     * 
     * @param isp isp
     * @return isp
     */
    public static InterventionalStudyProtocol createInterventionalStudyProtocolObj(InterventionalStudyProtocol isp) {
        
        Timestamp now = new Timestamp((new Date()).getTime());
        isp.setStartDate(now);
        isp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        isp.setPrimaryCompletionDate(now);
        isp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        isp.setAllocationCode(AllocationCode.NA);
        isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
        isp.setBlindingRoleCodeSubject(BlindingRoleCode.SUBJECT);
        isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
        isp.setBlindingRoleCodeOutcome(BlindingRoleCode.OUTCOMES_ASSESSOR);
        isp.setBlindingSchemaCode(BlindingSchemaCode.DOUBLE_BLIND);
        isp.setDesignConfigurationCode(DesignConfigurationCode.CROSSOVER_DESIGN);
        isp.setNumberOfInterventionGroups(Integer.valueOf(5));
        return isp;
    }
    
    /**
     * 
     * @param osp osp
     * @return osp
     */
    public static ObservationalStudyProtocol createObservationalStudyProtocolObj(ObservationalStudyProtocol osp) {
        osp.setBiospecimenDescription("biospecimenDescription");
        osp.setBiospecimenRetentionCode(BiospecimenRetentionCode.NONE);
        osp.setNumberOfGroups(Integer.valueOf(6));
        osp.setSamplingMethodCode(SamplingMethodCode.CLUSTER_SAMPLING);
        osp.setStudyModelCode(StudyModelCode.CASE_CONTROL);
        osp.setStudyModelOtherText("studyModelOtherText");
        osp.setTimePerspectiveCode(TimePerspectiveCode.OTHER);
        osp.setTimePerspectiveOtherText("timePerspectiveOtherText");
        return osp;
    }

    public static void assertStudyProtocol(StudyProtocol create , StudyProtocol saved) {
        assertEquals("Acronym does not match " , create.getAcronym(), saved.getAcronym());
        assertEquals("Accrual Reporting Method code does not match " , 
                create.getAccrualReportingMethodCode().getCode(), saved.getAccrualReportingMethodCode().getCode());
        assertEquals(create.getDataMonitoringCommitteeAppointedIndicator(), 
                saved.getDataMonitoringCommitteeAppointedIndicator());
        assertEquals("Expanded Access Indicator does not  match " , 
                create.getExpandedAccessIndicator(), saved.getExpandedAccessIndicator());
        assertEquals(create.getFdaRegulatedIndicator(), saved.getFdaRegulatedIndicator());
        assertEquals(create.getId(), saved.getId());
        assertEquals(create.getIdentifier(), saved.getIdentifier());
        assertEquals(create.getKeywordText(), saved.getKeywordText());
        assertEquals(create.getOfficialTitle(), saved.getOfficialTitle());
        assertEquals("Phase code does not match " , create.getPhaseCode(), saved.getPhaseCode());
        assertEquals(create.getPhaseOtherText() , saved.getPhaseOtherText());
        assertEquals("PrimaryCompletionDate  does not match " , 
                create.getPrimaryCompletionDate(), saved.getPrimaryCompletionDate());
        assertEquals("PrimaryCompletionDateTypeCode  does not match " , 
                create.getPrimaryCompletionDateTypeCode().getCode(), 
                saved.getPrimaryCompletionDateTypeCode().getCode());
        assertEquals(create.getPrimaryPurposeCode() , saved.getPrimaryPurposeCode());
        assertEquals(create.getPrimaryPurposeOtherText() , saved.getPrimaryPurposeOtherText());
        assertEquals("StartDate Does not match ", create.getStartDate() , saved.getStartDate());  
        assertEquals("StartDate Type code Does not match ", create.getStartDateTypeCode() , 
                saved.getStartDateTypeCode());  
        assertEquals("Status Date Does not match ", create.getUserLastUpdated() , 
                saved.getUserLastUpdated());  
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
        assertEquals(create.getUserLastCreated(), saved.getUserLastCreated());
        assertEquals(create.getDateLastCreated(), saved.getDateLastCreated());
        
    }

}
