/*
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
* 
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
* 
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract 
* or otherwise,or
*  
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or 
* 
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to 
* 
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so; 
* 
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof); 
* 
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
* 
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
* 
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally 
* appear.
* 
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
* 
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
* 
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
* 
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN 
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
* 
* 
*/
package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
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
         create = (InterventionalStudyProtocol) createStudyProtocolObj(create);    
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
         create = (ObservationalStudyProtocol) createStudyProtocolObj(create);    
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
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setAmendmentReasonCode(AmendmentReasonCode.OTHER);
        sp.setStatusDate(now);
        sp.setAmendmentDate(now);
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
        isp.setDesignConfigurationCode(DesignConfigurationCode.CROSSOVER);
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
        assertEquals(create.getAmendmentReasonCode(),saved.getAmendmentReasonCode());
        assertEquals(create.getStatusCode(),saved.getStatusCode());
       
        
    }

}
