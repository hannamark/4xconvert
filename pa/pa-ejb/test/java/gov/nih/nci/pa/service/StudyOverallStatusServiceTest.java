/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusServiceTest extends AbstractHibernateTestCase {
    private final DocumentWorkflowStatusServiceLocal documentWorkflowStatusService = new DocumentWorkflowStatusBeanLocal();
    private final StudyProtocolServiceLocal studyProtocolService =  new StudyProtocolServiceBean();
    private final StudyOverallStatusServiceBean bean = new StudyOverallStatusServiceBean();
    private Ii spIi;

    /**
     * Initialization method.
     */
    @Before
    public void setUp() {
        CSMUserService.setInstance(new MockCSMUserService());

        bean.setDocumentWorkFlowStatusService(documentWorkflowStatusService);
        bean.setStudyProtocolService(studyProtocolService);

        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
    }

    @Test
    public void create() throws Exception {
        TestSchema.addAbstractedWorkflowStatus(IiConverter.convertToLong(spIi));

        StudyOverallStatusDTO dto = bean.getCurrentByStudyProtocol(spIi);
        try {
            bean.create(dto);
            fail("StudyOverallStatus objects cannot be modified.");
        } catch (PAException e) {
            // expected behavior
        }

        // Following tests assume current status is ACTIVE.
        assertTrue(StudyStatusCode.ACTIVE.getCode().equals(dto.getStatusCode().getCode()));
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.CLOSED_TO_ACCRUAL));
        dto.setStatusDate(TsConverter.convertToTs(TestSchema.TOMMORROW));
        dto.setStudyProtocolIdentifier(spIi);
        bean.create(dto);

        StudyOverallStatusDTO result = bean.getCurrentByStudyProtocol(spIi);
        assertNotNull(IiConverter.convertToLong(result.getIdentifier()));
        assertEquals(result.getStatusCode().getCode(), dto.getStatusCode().getCode());
        assertEquals(TsConverter.convertToTimestamp(result.getStatusDate()),
                     TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals(IiConverter.convertToLong(spIi), IiConverter.convertToLong(result.getStudyProtocolIdentifier()));
    }

    @Test
    public void update() throws Exception {
        StudyOverallStatusDTO currentStatus = bean.getCurrentByStudyProtocol(spIi);
        currentStatus.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        currentStatus.setReasonText(StConverter.convertToSt("Trial submitted with incorrect overall status."));
        currentStatus.setStatusDate(TsConverter.convertToTs(TestSchema.TODAY));

        StudyOverallStatusDTO updatedStatus = bean.update(currentStatus);
        assertEquals(CdConverter.convertCdToString(currentStatus.getStatusCode()),
                CdConverter.convertCdToString(updatedStatus.getStatusCode()));
        assertEquals(StConverter.convertToString(currentStatus.getReasonText()),
                StConverter.convertToString(updatedStatus.getReasonText()));
        assertEquals(TsConverter.convertToTimestamp(currentStatus.getStatusDate()),
                TsConverter.convertToTimestamp(updatedStatus.getStatusDate()));
    }

    /**
     * Tests the creation of the intermediate study overall status when moving from In Review to Active
     * and Active to Closed to Accrual and Intervention
     * @throws Exception
     */
    @Test
    public void testIntermediateStudyOverallStatusCreation() throws Exception {
        InterventionalStudyProtocol sp = createStudyProtocol();
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);

        StudyOverallStatus active = createStudyOverallStatusobj(sp);
        active.setStatusCode(StudyStatusCode.ACTIVE);
        bean.create(statusConverter.convertFromDomainToDto(active));

        StudyOverallStatus criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.APPROVED);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        List<StudyOverallStatus> results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus approved = results.get(0);
        assertTrue(approved.isSystemCreated());
        assertEquals("Approved and In Review should have the same status date", active.getStatusDate(),
                approved.getStatusDate());

        StudyOverallStatus closedToAccrualAndIntervention = createStudyOverallStatusobj(sp);
        closedToAccrualAndIntervention.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        bean.create(statusConverter.convertFromDomainToDto(closedToAccrualAndIntervention));

        criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus closedToAccrual = results.get(0);
        assertNotNull("Closed to Accrual intermediate status not found.", closedToAccrual);
        assertTrue(closedToAccrual.isSystemCreated());
        assertEquals("Closed to Accrual and Closed to Accrual and Intervention should have the same status date",
                    closedToAccrualAndIntervention.getStatusDate(), closedToAccrual.getStatusDate());
    }

    /**
     * Tests the creation of the intermediate study overall status when moving from In Review to Enrolling by
     * Invitation.
     * @throws Exception
     */
    @Test
    public void testIntermediateStatusFromReviewToEnrollingByInvitation() throws Exception {
        InterventionalStudyProtocol sp = createStudyProtocol();
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);

        StudyOverallStatus enrollingByInvitation = createStudyOverallStatusobj(sp);
        enrollingByInvitation.setStatusCode(StudyStatusCode.ENROLLING_BY_INVITATION);
        bean.create(statusConverter.convertFromDomainToDto(enrollingByInvitation));

        StudyOverallStatus criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.APPROVED);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        List<StudyOverallStatus> results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus approved = results.get(0);
        assertTrue(approved.isSystemCreated());
        assertEquals("Approved and Enrolling By Invitation should have the same status date", enrollingByInvitation.getStatusDate(),
                approved.getStatusDate());
    }

    /**
     * Tests the creation of the intermediate study overall status when moving from Enrolling by Invitation to
     * Closed to Accrual & Intervention.
     * @throws Exception
     */
    @Test
    public void testIntermediateStatusFromEnrollingByInvitationToClosedToAccrualAndIntervention() throws Exception {
        InterventionalStudyProtocol sp = createStudyProtocol();
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);

        StudyOverallStatus enrollingByInvitation = createStudyOverallStatusobj(sp);
        enrollingByInvitation.setStatusCode(StudyStatusCode.ENROLLING_BY_INVITATION);
        bean.create(statusConverter.convertFromDomainToDto(enrollingByInvitation));

        StudyOverallStatus closeToAandI = createStudyOverallStatusobj(sp);
        closeToAandI.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        bean.create(statusConverter.convertFromDomainToDto(closeToAandI));

        StudyOverallStatus criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        List<StudyOverallStatus> results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus systemCreated = results.get(0);
        assertTrue(systemCreated.isSystemCreated());
        assertEquals("Closed To Accrual and Closed to Accrual and Intervention should have the same status date",
                closeToAandI.getStatusDate(), systemCreated.getStatusDate());
    }

    /**
     * Tests the creation of the intermediate study overall status when moving from Enrolling by Invitation to
     * Completed.
     * @throws Exception
     */
    @Test
    public void testIntermediateStatusFromEnrollingByInvitationToCompleted() throws Exception {
        InterventionalStudyProtocol sp = createStudyProtocol();
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);

        StudyOverallStatus enrollingByInvitation = createStudyOverallStatusobj(sp);
        enrollingByInvitation.setStatusCode(StudyStatusCode.ENROLLING_BY_INVITATION);
        bean.create(statusConverter.convertFromDomainToDto(enrollingByInvitation));

        StudyOverallStatus completed = createStudyOverallStatusobj(sp);
        completed.setStatusCode(StudyStatusCode.COMPLETE);
        bean.create(statusConverter.convertFromDomainToDto(completed));

        StudyOverallStatus criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        List<StudyOverallStatus> results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus systemCreated = results.get(0);
        assertTrue(systemCreated.isSystemCreated());
        assertEquals("Closed To Accrual and Completed should have the same status date",
                completed.getStatusDate(), systemCreated.getStatusDate());

        criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        systemCreated = results.get(0);
        assertTrue(systemCreated.isSystemCreated());
        assertEquals("Closed To Accrual and Completed should have the same status date",
                completed.getStatusDate(), systemCreated.getStatusDate());
    }

    /**
     * Tests the creation of the intermediate study overall status when moving from Temporarily closed to Accrual
     * to Administratively Complete.
     * @throws Exception
     */
    @Test
    public void testTemporaryAccrualIntermediateStatusCreation() throws Exception {
        InterventionalStudyProtocol sp = createStudyProtocol();
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);

        StudyOverallStatus active = createStudyOverallStatusobj(sp);
        active.setStatusCode(StudyStatusCode.ACTIVE);
        bean.create(statusConverter.convertFromDomainToDto(active));

        StudyOverallStatus temporarilyClosedToAccrual = createStudyOverallStatusobj(sp);
        temporarilyClosedToAccrual.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL);
        temporarilyClosedToAccrual.setCommentText("Setting to Temporarily Closed to Accrual.");
        bean.create(statusConverter.convertFromDomainToDto(temporarilyClosedToAccrual));

        StudyOverallStatus administrativelyComplete = createStudyOverallStatusobj(sp);
        administrativelyComplete.setStatusCode(StudyStatusCode.ADMINISTRATIVELY_COMPLETE);
        administrativelyComplete.setCommentText("Setting to Administratively Complete.");
        bean.create(statusConverter.convertFromDomainToDto(administrativelyComplete));

        StudyOverallStatus criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        List<StudyOverallStatus> results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus temporarilyClosedToAccrualAndIntervention = results.get(0);
        assertTrue(temporarilyClosedToAccrualAndIntervention.isSystemCreated());
        assertNotNull("Temporarily Closed to Accrual and Intervention intermediate status not found.",
                temporarilyClosedToAccrualAndIntervention);
        assertEquals("Administratively Complete and Temporarily Closed to Accrual & Intervention "
                + "should have the same status date", administrativelyComplete.getStatusDate(),
                temporarilyClosedToAccrualAndIntervention.getStatusDate());
    }

    /**
     * Tests the creation of the intermediate study overall status when moving from Active to Accrual to Complete.
     * @throws Exception
     */
    @Test
    public void testClosedAccrualIntermediateStatusCreation() throws Exception {
        InterventionalStudyProtocol sp = createStudyProtocol();
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);

        StudyOverallStatus active = createStudyOverallStatusobj(sp);
        active.setStatusCode(StudyStatusCode.ACTIVE);
        bean.create(statusConverter.convertFromDomainToDto(active));

        StudyOverallStatus complete = createStudyOverallStatusobj(sp);
        complete.setStatusCode(StudyStatusCode.COMPLETE);
        bean.create(statusConverter.convertFromDomainToDto(complete));

        StudyOverallStatus criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        List<StudyOverallStatus> results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus closedToAccrual = results.get(0);
        assertNotNull("Closed to Accrual intermediate status not found.", closedToAccrual);
        assertTrue(closedToAccrual.isSystemCreated());
        assertEquals("Complete and Closed to Accrual should have the same status date", complete.getStatusDate(),
                closedToAccrual.getStatusDate());

        criteria = new StudyOverallStatus();
        criteria.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        criteria.setStudyProtocol(new StudyProtocol());
        criteria.getStudyProtocol().setId(sp.getId());

        results =  bean.search(new AnnotatedBeanSearchCriteria<StudyOverallStatus>(criteria));
        assertEquals(1, results.size());

        StudyOverallStatus closedToAccrualAndIntervention = results.get(0);
        assertNotNull("Closed to Accrual & Intervention intermediate status not found.", closedToAccrualAndIntervention);
        assertTrue(closedToAccrualAndIntervention.isSystemCreated());
        assertEquals("Complete and Closed to Accrual & Intervention should have the same status date",
                complete.getStatusDate(), closedToAccrualAndIntervention.getStatusDate());
    }

    private InterventionalStudyProtocol createStudyProtocol() throws PAException {
        InterventionalStudyProtocol sp = new InterventionalStudyProtocol();
        sp = (InterventionalStudyProtocol) TestSchema.createStudyProtocolObj(sp);
        sp = TestSchema.createInterventionalStudyProtocolObj(sp);
        TestSchema.addUpdObject(sp);

        DocumentWorkflowStatus docWorkflow = TestSchema.createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(docWorkflow);

        Ii spId = IiConverter.convertToStudyProtocolIi(sp.getId());
        StudyOverallStatus inReview = createStudyOverallStatusobj(sp);
        inReview.setStatusCode(StudyStatusCode.IN_REVIEW);
        bean.create(Converters.get(StudyOverallStatusConverter.class).convertFromDomainToDto(inReview));
        assertEquals(bean.getByStudyProtocol(spId).size(), 1);

        return sp;
    }

    @Test
    public void getByProtocolTest() throws Exception {
        List<StudyOverallStatusDTO> statusList = bean.getByStudyProtocol(spIi);
        assertEquals(2, statusList.size());

        StudyOverallStatusDTO dto = bean.getCurrentByStudyProtocol(spIi);
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIdentifier()),
                     (IiConverter.convertToLong(dto.getIdentifier())));
    }

    @Test
    public void createTest() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = TestSchema.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/1999")));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        Ii initialIi = null;
        dto.setIdentifier(initialIi);
        assertTrue(ISOUtil.isIiNull(dto.getIdentifier()));
        StudyOverallStatusDTO resultDto = bean.create(dto);
        assertFalse(ISOUtil.isIiNull(resultDto.getIdentifier()));
    }

    @Test
    public void nullInDateTest() throws Exception {
        StudyProtocol spNew = TestSchema.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(null);
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        dto.setIdentifier(null);
        try {
            bean.create(dto);
            fail("PAException should have been thrown for null in status date.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setStatusDate(TsConverter.convertToTs(null));
        try {
            bean.create(dto);
            fail("PAException should have been thrown for Ts null in status date.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2000")));
        dto = bean.create(dto);
        assertFalse(ISOUtil.isIiNull(dto.getIdentifier()));
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyOverallStatusDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        StudyOverallStatusDTO dto = dtoList.get(0);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.STUDY_OVERALL_STATUS_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
    @Test
    public void createWithReasonTextTest() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = TestSchema.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ADMINISTRATIVELY_COMPLETE));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/1999")));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        Ii initialIi = null;
        dto.setIdentifier(initialIi);
        try {
             bean.create(dto);
        } catch(PAException e) {
            assertTrue(StringUtils.startsWith(e.getMessage(), "Validation Exception A reason must be entered when the study status "));
        }
        dto.setReasonText(StConverter.convertToSt(RandomStringUtils.random(2001)));
        try {
            bean.create(dto);
        } catch(PAException e) {
           assertTrue(StringUtils.startsWith(e.getMessage(), "Validation Exception Reason must be less than 2000 characters."));
        }
        dto.setReasonText(StConverter.convertToSt(RandomStringUtils.random(2000)));
        bean.create(dto);
    }

    @Test
    public void validateWithBadStatusCode() throws Exception {
        TestSchema.addAbstractedWorkflowStatus(IiConverter.convertToLong(spIi));
        StudyProtocolDTO spDto = studyProtocolService.getStudyProtocol(spIi);
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertStringToCd("bad status code"));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/1999")));
        dto.setReasonText(StConverter.convertToSt(RandomStringUtils.random(2001)));
        dto.setStudyProtocolIdentifier(spIi);
        dto.setIdentifier(null);
        try {
            bean.validate(dto, spDto);
        } catch (PAException e) {
            assertTrue(e.getMessage().startsWith("Validation Exception Invalid new study status: 'bad status code'."));
        }
    }

    private StudyOverallStatus createStudyOverallStatusobj(StudyProtocol sp) {
        StudyOverallStatus create = new StudyOverallStatus();
        Timestamp now = new Timestamp(new Date().getTime());
        create.setStudyProtocol(sp);
        create.setStatusCode(StudyStatusCode.ACTIVE);
        create.setStatusDate(now);
        create.setUserLastUpdated(TestSchema.getUser());
        create.setDateLastUpdated(now);
        return create;
    }

    /**
     * test the createStudyRecruitmentStatus method.
     */
    @Test
    public void testCreateStudyRecruitmentStatus() {
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        StudyOverallStatus bo = new StudyOverallStatus();
        bo.setStatusCode(StudyStatusCode.ACTIVE);
        bo.setStatusDate(PAUtil.dateStringToTimestamp("1/1/2001"));
        bo.setStudyProtocol(sp);
        bo.setCommentText(null);
        StudyRecruitmentStatus srs = bean.createStudyRecruitmentStatus(bo);
        assertNotNull("No result returned", srs);
        assertEquals("Wrong status code", RecruitmentStatusCode.ACTIVE, srs.getStatusCode());
        assertEquals("Wrong status date", PAUtil.dateStringToTimestamp("1/1/2001"),bo.getStatusDate());
        assertEquals("Wrong strudy protocol", sp,bo.getStudyProtocol());
    }
}
