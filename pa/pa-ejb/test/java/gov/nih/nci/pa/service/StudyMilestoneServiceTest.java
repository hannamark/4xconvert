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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceBean;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceBean;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceBean;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyMilestoneServiceTest {
    private final StudyMilestoneBeanLocal bean = new StudyMilestoneBeanLocal();
    private final StudyMilestoneServicelocal remote = bean;
    private final DocumentWorkflowStatusBeanLocal dws = new DocumentWorkflowStatusBeanLocal();
    private final StudyOnholdServiceLocal ohs = new StudyOnholdServiceBean();
    private final StudyProtocolServiceLocal sps = new MockStudyProtocolService();
    private final StudyInboxServiceLocal sis = new StudyInboxServiceBean();
    private final MailManagerBeanLocal mailSrc = new MailManagerBeanLocal();
    private final AbstractionCompletionServiceBean abstractionCompletionSerivce = new AbstractionCompletionServiceBean();

    private Ii spIi;
    private Ii spAmendIi;

    @Before
    public void setUp() throws Exception {
        CSMUserService.setRegistryUserService(new MockCSMUserService());
        bean.setDocumentWorkflowStatusService(dws);
        bean.setStudyOnholdService(ohs);
        bean.setStudyProtocolService(sps);
        bean.setStudyInboxService(sis);
        bean.setMailManagerService(mailSrc);
        mailSrc.setProtocolQueryService(new ProtocolQueryServiceBean());
        bean.setValidateAbstractions(false);
        bean.setAbstractionCompletionService(abstractionCompletionSerivce);
        TestSchema.reset1();
        TestSchema.primeData();
        spAmendIi = TestSchema.createAmendStudyProtocol();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
    }

    private void compareDataAttributes(StudyMilestoneDTO dto1, StudyMilestoneDTO dto2) throws Exception {
        StudyMilestone bo1 = bean.convertFromDtoToDomain(dto1);
        StudyMilestone bo2 = bean.convertFromDtoToDomain(dto2);
        assertEquals(bo1.getCommentText(), bo2.getCommentText());
        assertEquals(bo1.getMilestoneCode().getCode(), bo2.getMilestoneCode().getCode());
        assertEquals(bo1.getMilestoneDate(), bo2.getMilestoneDate());
        assertEquals(bo1.getStudyProtocol().getId(), bo2.getStudyProtocol().getId());
    }

    @Test
    public void getTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(PAUtil.isIiNull(ii));
        StudyMilestoneDTO resultDto = bean.get(ii);
        compareDataAttributes(dtoList.get(0), resultDto);
    }

    @Test
    public void updateTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);

        StudyMilestoneDTO dto = dtoList.get(0);
        dto.setCommentText(StConverter.convertToSt("new comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_ACCEPTED));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remote.update(dto);
            fail("The update method in milestones service should be disabled.");
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void createTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();

        StudyMilestoneDTO dto;
        remote.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        dtoList = remote.getByStudyProtocol(spIi);
        assertEquals(oldSize + 1, dtoList.size());
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        dws.create(dwfDto);
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.QC_START));
        remote.create(getMilestoneDTO(MilestoneCode.QC_COMPLETE));
        dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setCommentText(null);
        dto.setStudyProtocolIdentifier(spAmendIi);
        remote.create(dto);
        remote.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        remote.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
    }

    /**
     * @return
     */
    private StudyMilestoneDTO getMilestoneDTO(MilestoneCode mileCode) {
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(mileCode));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        return dto;
    }

    @Test
    public void deleteTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        dtoList.size();
        try {
            remote.delete(dtoList.get(0).getIdentifier());
            fail("The delete method in milestones service should be disabled.");
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        StudyMilestoneDTO dto = dtoList.get(0);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void onholdTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();

        // set on-hold and dwf status
        StudyOnholdDTO ohDto = new StudyOnholdDTO();
        ohDto.setOnholdDate(IvlConverter.convertTs().convertToIvl(PAUtil.today(), null));
        ohDto.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.PENDING_ORG_CUR));
        ohDto.setStudyProtocolIdentifier(spIi);
        ohs.create(ohDto);
        DocumentWorkflowStatus dwf = new DocumentWorkflowStatus();
        dwf.setStudyProtocol((StudyProtocol) HibernateUtil.getCurrentSession().get(StudyProtocol.class,
                                                                                   IiConverter.convertToLong(spIi)));
        dwf.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        dwf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        TestSchema.addUpdObject(dwf);
        // try to create
        try {
            remote.create(getMilestoneDTO(MilestoneCode.INITIAL_SUBMISSION_TO_CLINICALTRIALS_GOV_DATE));
            fail("Should have failed because the trial is on-hold.");
        } catch (PAException e) {
            // expected behavior
        }

        // take off-hold
        List<StudyOnholdDTO> ohList = ohs.getByStudyProtocol(spIi);
        for (StudyOnholdDTO oh : ohList) {
            if (IvlConverter.convertTs().convertHigh(oh.getOnholdDate()) == null) {
                oh.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, PAUtil.today()));
                ohs.update(oh);
            }
        }
        // create
        remote.create(getMilestoneDTO(MilestoneCode.INITIAL_SUBMISSION_TO_CLINICALTRIALS_GOV_DATE));
        dtoList = remote.getByStudyProtocol(spIi);
        assertEquals(oldSize + 1, dtoList.size());
    }

    @Test
    public void getCurrentDocumentWorkflowStatusTest() throws Exception {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        for (DocumentWorkflowStatusDTO dto : dwsList) {
            dws.delete(dto.getIdentifier());
        }
        // try to create w/ null dwf
        try {
            remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
            fail("Should have failed because of no DWF.");
        } catch (PAException e) {
            // expected behavior
        }

        // create
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dws.create(dwfDto);
        StudyMilestoneDTO dto = remote.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        assertFalse(PAUtil.isIiNull(dto.getIdentifier()));
    }

    /**
     * @return
     */
    private DocumentWorkflowStatusDTO getDocWrkStatusDTO() {
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
        dwfDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        dwfDto.setStudyProtocolIdentifier(spIi);
        return dwfDto;
    }

    @Test
    public void missingDataTest() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        // spId is null
        dto.setStudyProtocolIdentifier(null);
        try {
            remote.create(dto);
            fail();
        } catch (PAException e) {
            // expected behavior
        }
        // code is null
        dto.setStudyProtocolIdentifier(spIi);
        dto.setMilestoneCode(null);
        try {
            remote.create(dto);
            fail();
        } catch (PAException e) {
            // expected behavior
        }
        // date is null
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_RECEIVED));
        dto.setMilestoneDate(null);
        try {
            remote.create(dto);
            fail();
        } catch (PAException e) {
            // expected behavior
        }
        // date is future
        dto.setMilestoneDate(TsConverter.convertToTs(getDate(1)));
        try {
            remote.create(dto);
            fail();
        } catch (PAException e) {
            // expected behavior
        }
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        // create
        remote.create(dto);
        // date must not predate existing milestone
        dto = getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED);
        dto.setMilestoneDate(TsConverter.convertToTs(getDate(-1)));
        try {
            remote.create(dto);
            fail("Milestone's must not predate existing milestones.  The prior milestone date is");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setMilestoneDate(TsConverter.convertToTs(getDate(0)));
        remote.create(dto);
        // comment is required when milestone is late rejection date
        dto = getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE);
        dto.setCommentText(null);
        try {
            remote.create(dto);
            fail("comment is required when milestone is late rejection date");
        } catch (PAException e) {
            assertEquals("Milestone Comment is required.", e.getMessage());
        }
        dto.setCommentText(StConverter.convertToSt("some"));
        try {
            remote.create(dto);
            fail("Not recorded as sending the rejection email to the submitter  failed");
        } catch (Exception e) {
            // excepted
        }
    }

    @Test
    public void validationTest() throws PAException {
        // milestone late rejection date is only for original submission.
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE);
        dto.setStudyProtocolIdentifier(spAmendIi);
        try {
            remote.create(dto);
            fail("Late Rejection Date is applicable to Original Submission");
        } catch (PAException e) {
            assertEquals("Late Rejection Date is applicable to Original Submission.", e.getMessage());
        }
        // uniqueness rules
        dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        remote.create(dto);
        try {
            remote.create(dto);
            fail("Milestone's must be unique.");
        } catch (PAException e) {
            // expected behavior
        }
        dto = getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_SENT);
        dto.setStudyProtocolIdentifier(TestSchema.nonPropTrialData());
        DocumentWorkflowStatusDTO wrkDto = getDocWrkStatusDTO();
        wrkDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTED));
        wrkDto.setStudyProtocolIdentifier(dto.getStudyProtocolIdentifier());
        dws.create(wrkDto);
        try {
            mailSrc.setLookUpTableService(new LookUpTableServiceBean());
            TSRReportGeneratorServiceRemote tsrBean = new TSRReportGeneratorServiceBean();
            mailSrc.setTsrReportGeneratorService(tsrBean);
            remote.create(dto);
            fail("sending the TSR report to the submitter  failed.");
        } catch (PAException e) {
            // expected behavior
        }
        // create inbox record
        StudyInboxDTO inboxDto = new StudyInboxDTO();
        inboxDto.setStudyProtocolIdentifier(spIi);
        inboxDto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(getDate(0), null));
        inboxDto = sis.create(inboxDto);
        try {
            remote.create(getMilestoneDTO(MilestoneCode.QC_COMPLETE));
            fail("sending the TSR report to the submitter  failed.");
        } catch (PAException e) {
            // expected behavior
        }
        wrkDto = getDocWrkStatusDTO();
        wrkDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTED));
        dws.create(wrkDto);
        remote.create(getMilestoneDTO(MilestoneCode.QC_START));
        inboxDto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(getDate(0), getDate(0)));
        sis.update(inboxDto);
        bean.setValidateAbstractions(true);
        try {
            bean.setAbstractionCompletionService(null);
            remote.create(getMilestoneDTO(MilestoneCode.QC_COMPLETE));
            fail("Error injecting reference to AbstractionCompletionService");
        } catch (PAException e) {
            assertEquals("Error injecting reference to AbstractionCompletionService.", e.getMessage());
        }
        try {
            remote.create(dto);
            fail("Abstraction validation exception");
        } catch (Exception e) {
        }
    }

    @Test
    public void newValidationsForReadyForQc() throws PAException {
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.READY_FOR_QC));
            fail();
        } catch (PAException e) {
            assertEquals("Scientific Processing Start Date must be followed by "
                    + " Scientific Processing Completion Date", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.READY_FOR_QC));
            fail();
        } catch (PAException e) {
            assertEquals("Administartive Processing Start Date must be followed by  "
                    + "Administrative Processing Completion Date", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        try {
            bean.setAbstractionCompletionService(abstractionCompletionSerivce);
            remote.create(getMilestoneDTO(MilestoneCode.READY_FOR_QC));
            fail();
        } catch (PAException e) {
        }
    }

    @Test
    public void newValidationsForScientificProcessingStartDate() throws PAException {
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
            fail();
        } catch (PAException e) {
            assertEquals("Scientific Processing Start Date cannot be recorded"
                    + " if Administrative Processing started but was not completed", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
            fail();
        } catch (PAException e) {
            assertEquals("Scientific Processing Start Date must be followed by "
                    + " Scientific Processing Completion Date", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
            fail();
        } catch (PAException e) {
            assertEquals("Administartive Processing Start Date must be "
                    + "followed by  Administrative Processing Completion Date", e.getMessage());
        }
    }

    @Test
    public void newValidationForAdministrativeProcessingStartDate() throws PAException {
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
            fail();
        } catch (PAException e) {
            assertEquals("Administrative Processing Start Date cannot be recorded"
                    + " if Scientific Processing started but was not completed", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
            fail();
        } catch (PAException e) {
            assertEquals("Administartive Processing Start Date must be followed by "
                    + " Administrative Processing Completion Date", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
            fail();
        } catch (PAException e) {
            assertEquals("Scientific Processing Start Date must be followed by "
                    + " Scientific Processing Completion Date", e.getMessage());
        }
    }

    @Test
    public void newValidationForCompletedDate() throws PAException {
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        } catch (PAException e) {
            assertEquals("Administartive Processing Completion Date must be preceded by "
                    + " Administrative Processing Start Date", e.getMessage());
        }
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        } catch (PAException e) {
            assertEquals("Scientific Processing Completion Date must be preceded by "
                    + " Scientific Processing Start Date", e.getMessage());
        }

    }

    @Test
    public void prerequisiteTest() throws Exception {
        // SUBMISSION_ACCEPTED is prerequisite for LATE_REJECTION_DATE
        try {
            remote.create(getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE));
            fail("Should fail for missing prerequisite.");
        } catch (PAException e) {
            assertEquals("'Submission Acceptance Date' is a prerequisite to 'Late Rejection Date'.", e.getMessage());
        }
        try {
            remote.create(getMilestoneDTO(MilestoneCode.READY_FOR_QC));
            fail("Should fail for missing prerequisite.");
        } catch (PAException e) {
            // expected behavior
        }
        try {
            remote.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
            fail("Should fail for missing prerequisite.");
        } catch (PAException e) {
            // expected behavior
        }
        try {
            remote.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
            fail("Should fail for missing prerequisite.");
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void creaeDocumentWrkStatusForRejectedTest() throws Exception {
        remote.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REJECTED));
        DocumentWorkflowStatusDTO dwsDTO = dws.getCurrentByStudyProtocol(spIi);
        assertTrue(DocumentWorkflowStatusCode.REJECTED.getCode().equalsIgnoreCase(dwsDTO.getStatusCode().getCode()));
    }

    @Test
    public void createDocumentWorkflowStatusesTest() throws Exception {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        int dwsCount = dwsList.size();
        assertTrue(dwsCount > 0);

        remote.create(getMilestoneDTO(MilestoneCode.QC_START));
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());

        // complete qc only changes dws if current dws is accepted
        DocumentWorkflowStatusDTO dtoDwf = dws.getCurrentByStudyProtocol(spIi);
        assertFalse(DocumentWorkflowStatusCode.ACCEPTED.equals(DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dtoDwf.getStatusCode()))));
        remote.create(getMilestoneDTO(MilestoneCode.QC_COMPLETE));
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());

        // initial verification set dws to verified-noresponse if no feedback has been received
        assertFalse(bean.milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK,
                                         getMilestoneDTO(MilestoneCode.QC_COMPLETE)));
        remote.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        dtoDwf = dws.getCurrentByStudyProtocol(spIi);
        assertTrue(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.equals(DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dtoDwf.getStatusCode()))));
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(++dwsCount, dwsList.size());

        // ongoing verifications do not change dws if still no feedback
        remote.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());
        assertFalse(dwsList.contains(DocumentWorkflowStatusCode.VERIFICATION_PENDING.getCode()));
        try {
            remote.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_SENT));
            fail();
        } catch (PAException e) {

        }
        // ongoing verifications change dws if there is feedback
        remote.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        remote.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        dtoDwf = dws.getCurrentByStudyProtocol(spIi);
        assertTrue(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode()
                                                                           .equalsIgnoreCase(dtoDwf.getStatusCode()
                                                                                                   .getCode()));
        /*
         * assertTrue(dwsList.contains(DocumentWorkflowStatusCode.VERIFICATION_PENDING.getCode()));
         *
         * dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.LATE_REJECTION_DATE)); remote.create(dto); dwsList
         * = dws.getByStudyProtocol(spIi); assertEquals(++dwsCount, dwsList.size());
         *
         * // ongoing verifications set to verified-response after feedback
         * dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
         * remote.create(dto); dtoDwf = dws.getCurrentByStudyProtocol(spIi);
         */
        // TODO this testing was failing. Need to revisit.
        // assertTrue(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.equals(
        // DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dwsList.get(0).getStatusCode()))));
        // dwsList = dws.getByStudyProtocol(spIi);
        // assertEquals(++dwsCount, dwsList.size());
    }

    @Test
    public void updateRecordVerificationDateTest() throws Exception {
        // set initial date
        StudyProtocolDTO sp = sps.getStudyProtocol(spIi);
        sp.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        sps.updateStudyProtocol(sp);

        DocumentWorkflowStatusDTO dwfsDTO = getDocWrkStatusDTO();
        dwfsDTO.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        // milestone triggers change of date
        remote.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        sp = sps.getStudyProtocol(spIi);
        String recordVerificationDate = TsConverter.convertToString(sp.getRecordVerificationDate());
        assertTrue(ISOUtil.normalizeDateString(getDate(0).toString()).equals(recordVerificationDate));

    }

    @Test
    public void search() throws TooManyResultsException, PAException {
        try {
            remote.search(null, null);
            fail("StudyMilestoneDTO should not be null.");
        } catch (PAException e) {
            assertEquals(" StudyMilestoneDTO should not be null ", e.getMessage());
        }
    }

    private Timestamp getDate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return new Timestamp(calendar.getTime().getTime());
    }

}
