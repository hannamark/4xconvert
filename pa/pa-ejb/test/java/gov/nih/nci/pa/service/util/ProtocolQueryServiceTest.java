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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.HealthCareProviderTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyContactTest;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyInboxTest;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteTest;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author NAmiruddin
 *
 */
public class ProtocolQueryServiceTest {
    private ProtocolQueryServiceBean bean = new ProtocolQueryServiceBean();
    private ProtocolQueryServiceLocal localEjb = bean;
    private Long spId = null;
    private Long leadOrgId = null;
    private Long principalInvestigator = null;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        createStudyProtocol("1", false, Boolean.FALSE, false, false, false);
    }

    @Test
    public void getStudyProtocolByCriteriaTest() throws Exception {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNciIdentifier("nci");
        List<StudyProtocolQueryDTO> results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());

        assertEquals("Title does not match.", results.get(0).getOfficialTitle(), "Cancer for kids");
        assertEquals("NCI Identifier does not match." , results.get(0).getNciIdentifier(), "NCI-2009-00001");

        criteria.setStudyStatusCode("In Review");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());

        criteria.setStudyStatusCode("Active");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(StudyStatusCode.ACTIVE, results.get(0).getStudyStatusCode());

        criteria.setDocumentWorkflowStatusCode("Verification Pending");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());

        criteria.setDocumentWorkflowStatusCode("Accepted");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertFalse(results.isEmpty());
        assertEquals(DocumentWorkflowStatusCode.ACCEPTED, results.get(0).getDocumentWorkflowStatusCode());

        criteria.setLeadOrganizationTrialIdentifier("Local1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Local1", results.get(0).getLocalStudyProtocolIdentifier());

        criteria.setOfficialTitle("Cancer");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("Cancer for kids", results.get(0).getOfficialTitle());

        criteria.setPhaseCode("I");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(PhaseCode.I, results.get(0).getPhaseCode());

        criteria.setPhaseAdditionalQualifierCode("Pilot");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(PhaseAdditionalQualifierCode.PILOT, results.get(0).getPhaseAdditionalQualifier());

        criteria.setPrimaryPurposeCode("Basic Science");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());

        criteria.setNctNumber("NCT1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());

        criteria.setSubmissionType("O");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());

        criteria.setStudyMilestone("Submission Received Date");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());

        criteria.setStudyMilestone("Submission Acceptance Date");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(MilestoneCode.SUBMISSION_ACCEPTED, results.get(0).getStudyMilsetone());
        criteria.setLeadOrganizationId("123");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());

        criteria.setLeadOrganizationId(leadOrgId.toString());
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals(leadOrgId, results.get(0).getLeadOrganizationId());

        criteria.setPrincipalInvestigatorId("123");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());

        criteria.setPrincipalInvestigatorId(principalInvestigator.toString());
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());

        criteria.setNciIdentifier(null);
        criteria.setOtherIdentifier("OTHER-2");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertTrue(results.isEmpty());

        criteria.setOtherIdentifier("OTHER-1");
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertTrue(results.get(0).getOtherIdentifiers().contains("OTHER-1"));

        criteria.setInBoxProcessing(Boolean.TRUE);
        results = localEjb.getStudyProtocolByCriteria(criteria);
        assertEquals("Size does not match.", 1, results.size());
        assertNotNull(results.get(0).getStudyInboxId());
        assertNotNull(results.get(0).getUpdatedComments());
        assertNotNull(results.get(0).getUpdatedDate());

        createStudyProtocol("2", true, Boolean.FALSE, false, false, false);
        StudyProtocolQueryCriteria otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setNciIdentifier("NCI");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.",2 , results.size());

        otherCriteria.setExcludeRejectProtocol(Boolean.FALSE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.",2 , results.size());

        otherCriteria.setNciIdentifier(null);
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.",1, results.size());
        assertFalse(DocumentWorkflowStatusCode.REJECTED == results.get(0).getDocumentWorkflowStatusCode());

        createStudyProtocol("3", false, Boolean.TRUE, false, false, false);
        createStudyProtocol("4", false, Boolean.TRUE, false, false, false);
        createStudyProtocol("5", false, Boolean.TRUE, false, false, false);

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setNciIdentifier("NCI");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 5, results.size());

        otherCriteria.setTrialCategory("N");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 2, results.size());
        for (StudyProtocolQueryDTO dto : results) {
            assertEquals("Non Proprietary Trial", dto.getTrialCategory());
        }

        otherCriteria.setTrialCategory("P");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 3, results.size());
        for (StudyProtocolQueryDTO dto : results) {
            assertEquals("Proprietary Trial", dto.getTrialCategory());
        }

        StudyProtocol sp = createStudyProtocol("6", false, Boolean.FALSE, false, false, false);
        RegistryUser owner = sp.getStudyOwners().iterator().next();

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setUserId(owner.getId());
        otherCriteria.setUserLastCreated("user");
        otherCriteria.setMyTrialsOnly(Boolean.FALSE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 6, results.size());

        otherCriteria.setMyTrialsOnly(Boolean.TRUE);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());


        createStudyProtocol("7", false, Boolean.FALSE, true, false, false);

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setSearchOnHold(false);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 7, results.size());

        otherCriteria.setSearchOnHold(true);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());

        createStudyProtocol("8", false, Boolean.FALSE, false, true, false);

        otherCriteria.setSearchOnHold(false);
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 8, results.size());

        otherCriteria.setStudyLockedBy(true);
        otherCriteria.setUserLastCreated("user");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());
        assertEquals("user", results.get(0).getStudyCheckoutByUsername());

        createStudyProtocol("9", false, Boolean.FALSE, false, false, true);

        otherCriteria = new StudyProtocolQueryCriteria();
        otherCriteria.setOfficialTitle("Cancer");
        otherCriteria.setSubmissionType("Original");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 8, results.size());

        otherCriteria.setSubmissionType("Amendment");
        results = localEjb.getStudyProtocolByCriteria(otherCriteria);
        assertEquals("Size does not match.", 1, results.size());
    }

    @Test
    public void getTrialSummaryByStudyProtocolIdTest() throws Exception {
        StudyProtocolQueryDTO data = localEjb.getTrialSummaryByStudyProtocolId(spId);
        assertNotNull(data);
        assertEquals("Title does not match  " , data.getOfficialTitle(), "Cancer for kids");
        assertEquals("NCI Identifier does not match  " , data.getNciIdentifier(), "NCI-2009-00001");
    }

    @Test
    public void getStudyProtocolByOrgIdentifierTest() throws Exception {
        createStudyProtocol("2", false, Boolean.FALSE, false, false, false);
        List<StudyProtocol> results = localEjb.getStudyProtocolByOrgIdentifier(Long.valueOf(1));
        assertEquals(1, results.size());

        results = localEjb.getStudyProtocolByOrgIdentifier(Long.valueOf(2));
        assertEquals(1, results.size());
    }

    @Test(expected=PAException.class)
    public void nullParameter1() throws Exception {
        localEjb.getTrialSummaryByStudyProtocolId(null);
    }

    @Test(expected=PAException.class)
    public void nullParameter2() throws Exception {
        localEjb.getTrialSummaryByStudyProtocolId(Long.valueOf(1000));
    }


    private StudyProtocol createStudyProtocol(String orgId, boolean createRejected, Boolean isPropTrial,
            boolean onHold, boolean locked, boolean amendment) {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        sp.setProprietaryTrialIndicator(isPropTrial);

        if (amendment) {
            sp.setSubmissionNumber(2);
            sp.setAmendmentNumber("2");
            sp.setAmendmentReasonCode(AmendmentReasonCode.ADMINISTRATIVE);
            sp.setAmendmentDate(new Timestamp(new Date().getTime()));
        } else {
            sp.setSubmissionNumber(1);
            sp.setAmendmentDate(null);
            sp.setAmendmentNumber(null);
        }

        Ii otherId = new Ii();
        otherId.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);
        otherId.setIdentifierName(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME);
        otherId.setExtension("OTHER-" + orgId);
        sp.getOtherIdentifiers().add(otherId);

        TestSchema.addUpdObject(sp);
        spId = sp.getId();

        Organization o = OrganizationTest.createOrganizationObj();
        o.setIdentifier(orgId);
        TestSchema.addUpdObject(o);
        leadOrgId = o.getId();

        Person p = PersonTest.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        principalInvestigator = p.getId();

        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);

        HealthCareProvider hcp = HealthCareProviderTest.createHealthCareProviderObj(p, o);
        TestSchema.addUpdObject(hcp);

        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);

        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);

        StudyContact sc = StudyContactTest.createStudyContactObj(sp, c, hcp, crs);
        TestSchema.addUpdObject(sc);
        sp.getStudyContacts().add(sc);

        StudyContact sc2 = StudyContactTest.createStudyContactObj(sp, c, hcp, crs);
        sc2.setRoleCode(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR);
        TestSchema.addUpdObject(sc2);
        sp.getStudyContacts().add(sc2);

        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);

        StudySite spc = StudySiteTest.createStudySiteObj(sp, hcf);
        spc.setLocalStudyProtocolIdentifier("Local" + orgId);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        sp.getStudySites().add(spc);

        StudySite spc2 = StudySiteTest.createStudySiteObj(sp, hcf);
        spc2.setLocalStudyProtocolIdentifier("NCT" + orgId);
        spc2.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        spc2.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc2);
        sp.getStudySites().add(spc2);

        Date now = new Date();
        StudyOverallStatus sos = new StudyOverallStatus();
        sos.setStatusCode(StudyStatusCode.IN_REVIEW);
        sos.setStatusDate(new Timestamp(now.getTime()));
        sos.setCommentText("In Review");
        sos.setStudyProtocol(sp);
        TestSchema.addUpdObject(sos);
        sp.getStudyOverallStatuses().add(sos);

        now = new Date();
        StudyOverallStatus sos2 = new StudyOverallStatus();
        sos2.setStatusCode(StudyStatusCode.ACTIVE);
        sos2.setStatusDate(new Timestamp(now.getTime()));
        sos2.setCommentText("Active");
        sos2.setStudyProtocol(sp);
        TestSchema.addUpdObject(sos2);
        sp.getStudyOverallStatuses().add(sos2);

        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        dws.setStudyProtocol(sp);
        dws.setStatusCode(DocumentWorkflowStatusCode.VERIFICATION_PENDING);
        dws.setCommentText("Pending");
        TestSchema.addUpdObject(dws);
        sp.getDocumentWorkflowStatuses().add(dws);
        TestSchema.addUpdObject(sp);

        DocumentWorkflowStatus dws2 = new DocumentWorkflowStatus();
        dws2.setStudyProtocol(sp);
        dws2.setStatusCode(DocumentWorkflowStatusCode.ACCEPTED);
        dws2.setCommentText("Accepted");
        TestSchema.addUpdObject(dws2);
        sp.getDocumentWorkflowStatuses().add(dws2);

        if (createRejected) {
            DocumentWorkflowStatus dws3 = new DocumentWorkflowStatus();
            dws3.setStudyProtocol(sp);
            dws3.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
            dws3.setCommentText("Rejected");
            TestSchema.addUpdObject(dws3);
            sp.getDocumentWorkflowStatuses().add(dws3);
        }

        now = new Date();
        StudyMilestone milestone = new StudyMilestone();
        milestone.setStudyProtocol(sp);
        milestone.setMilestoneDate(new Timestamp(now.getTime()));
        milestone.setMilestoneCode(MilestoneCode.SUBMISSION_RECEIVED);
        TestSchema.addUpdObject(milestone);
        sp.getStudyMilestones().add(milestone);

        now = new Date();
        StudyMilestone milestone2 = new StudyMilestone();
        milestone2.setStudyProtocol(sp);
        milestone2.setMilestoneDate(new Timestamp(now.getTime()));
        milestone2.setMilestoneCode(MilestoneCode.SUBMISSION_ACCEPTED);
        TestSchema.addUpdObject(milestone2);
        sp.getStudyMilestones().add(milestone2);

        StudyInbox si = StudyInboxTest.createStudyInboxobj(sp);
        TestSchema.addUpdObject(si);
        sp.getStudyInbox().add(si);

        StudyInbox si2 = StudyInboxTest.createStudyInboxobj(sp);
        si2.setCloseDate(null);
        TestSchema.addUpdObject(si2);
        sp.getStudyInbox().add(si2);

        if (onHold) {
            now = new Date();
            StudyOnhold soh = new StudyOnhold();
            soh.setOnholdReasonCode(OnholdReasonCode.OTHER);
            soh.setOnholdReasonText("On hold.");
            soh.setOnholdDate(new Timestamp(now.getTime()));
            soh.setStudyProtocol(sp);
            TestSchema.addUpdObject(soh);
            sp.getStudyOnholds().add(soh);
        }

        if (locked) {
            StudyCheckout co = new StudyCheckout();
            co.setUserIdentifier("user");
            co.setStudyProtocol(sp);
            TestSchema.addUpdObject(co);
            sp.getStudyCheckout().add(co);
        }
        TestSchema.addUpdObject(sp);
        return sp;
    }
}
