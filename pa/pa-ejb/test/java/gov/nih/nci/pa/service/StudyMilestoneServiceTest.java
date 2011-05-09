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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
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
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author hreinhart
 * 
 */
public class StudyMilestoneServiceTest extends AbstractHibernateTestCase {
    private final StudyMilestoneBeanLocal bean = new StudyMilestoneBeanLocal();
    private final DocumentWorkflowStatusBeanLocal dws = new DocumentWorkflowStatusBeanLocal();
    private final StudyOnholdServiceLocal ohs = new StudyOnholdServiceBean();
    private final StudyProtocolServiceLocal sps = new StudyProtocolServiceBean();
    private final StudyInboxServiceLocal sis = new StudyInboxServiceBean();
    private final MailManagerBeanLocal mailSrc = new MailManagerBeanLocal();
    private final AbstractionCompletionServiceBean abstractionCompletionSerivce = new AbstractionCompletionServiceBean();

    private Ii spIi;
    private Ii spAmendIi;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        CSMUserService.setRegistryUserService(new MockCSMUserService());

        ServiceLocator paSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        when(paSvcLoc.getDocumentWorkflowStatusService()).thenReturn(new DocumentWorkflowStatusBeanLocal());

        bean.setStudyOnholdService(ohs);
        bean.setStudyProtocolService(sps);
        bean.setStudyInboxService(sis);
        bean.setMailManagerService(mailSrc);
        mailSrc.setProtocolQueryService(new ProtocolQueryServiceBean());
        bean.setValidateAbstractions(false);
        bean.setAbstractionCompletionService(abstractionCompletionSerivce);
        TestSchema.primeData();
        spIi = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));
        spAmendIi = TestSchema.createAmendStudyProtocol();
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
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(PAUtil.isIiNull(ii));
        StudyMilestoneDTO resultDto = bean.get(ii);
        compareDataAttributes(dtoList.get(0), resultDto);
    }

    @Test
    public void updateTest() throws Exception {
        expectedException.expect(PAException.class);
        expectedException.expectMessage("The update() method in the StudyMilestoneService has been disabled.");

        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);

        StudyMilestoneDTO dto = dtoList.get(0);
        dto.setCommentText(StConverter.convertToSt("new comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_ACCEPTED));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        bean.update(dto);
    }

    @Test
    public void createTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        dtoList = bean.getByStudyProtocol(spIi);
        assertEquals(oldSize + 2, dtoList.size());
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        dws.create(dwfDto);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setCommentText(null);
        dto.setStudyProtocolIdentifier(spAmendIi);
        bean.create(dto);
        PaHibernateUtil.getCurrentSession().flush();
    }

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
        expectedException.expect(PAException.class);
        expectedException.expectMessage("The delete() method in the StudyMilestoneService has been disabled.");
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        dtoList.size();
        bean.delete(dtoList.get(0).getIdentifier());
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        StudyMilestoneDTO dto = dtoList.get(0);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }

    @Test
    public void checkRequiredDataRulesMissingStudyProtocol() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setStudyProtocolIdentifier(null);
        checkMilestoneFailure(dto, "Check the Ii value; null found.  ");
    }

    @Test
    public void checkRequiredDataRulesMissingCode() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setMilestoneCode(null);
        checkMilestoneFailure(dto, "Milestone code is required.");
    }

    @Test
    public void checkDateRulesMissingDate() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setMilestoneDate(null);
        checkMilestoneFailure(dto, "Milestone date is required.");
    }

    @Test
    public void checkDateRulesFutureDate() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() + 86400000L)));
        checkMilestoneFailure(dto, "Milestone dates may not be in the future.");
    }

    @Test
    public void checkDateRulesPastLastMilestone() throws Exception {
        StudyMilestoneDTO dto1 = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        bean.create(dto1);
        StudyMilestoneDTO dto2 = getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED);
        dto2.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() - 86400000L)));
        String msg = "A milestone cannot predate existing milestones. The prior milestone date is {0}.";
        Timestamp lastDate = TsConverter.convertToTimestamp(dto1.getMilestoneDate());
        String lastMileStoneDate = PAUtil.normalizeDateStringWithTime(lastDate.toString());
        String message = MessageFormat.format(msg, lastMileStoneDate);
        checkMilestoneFailure(dto2, message);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
    }

    @Test
    public void checkLateRejectionRulesMissingComment() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE);
        dto.setCommentText(null);
        checkMilestoneFailure(dto, "Milestone Comment is required.");
    }

    @Test
    public void checkLateRejectionRulesNotOriginal() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.LATE_REJECTION_DATE);
        dto.setStudyProtocolIdentifier(spAmendIi);
        checkMilestoneFailure(dto, "Late Rejection Date is applicable to Original Submission.");
    }

    @Test
    public void checkOnHoldRules() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        // set on-hold and dwf status
        StudyOnholdDTO ohDto = new StudyOnholdDTO();
        ohDto.setOnholdDate(IvlConverter.convertTs().convertToIvl(PAUtil.today(), null));
        ohDto.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.PENDING_ORG_CUR));
        ohDto.setStudyProtocolIdentifier(spIi);
        ohs.create(ohDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        String msg = "The milestone \"Administrative QC Completed Date\" cannot be recorded if there is an active"
                + " on-hold record.";
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, msg);
        // take off-hold
        List<StudyOnholdDTO> ohList = ohs.getByStudyProtocol(spIi);
        for (StudyOnholdDTO oh : ohList) {
            if (IvlConverter.convertTs().convertHigh(oh.getOnholdDate()) == null) {
                oh.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, PAUtil.today()));
                ohs.update(oh);
            }
        }
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
    }

    @Test
    public void checkInboxRules() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        // create inbox record
        StudyInboxDTO inboxDto = new StudyInboxDTO();
        inboxDto.setStudyProtocolIdentifier(spIi);
        inboxDto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(getDate(0), null));
        inboxDto = sis.create(inboxDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        String msg = "The milestone \"Administrative QC Completed Date\" cannot be recorded if there is an active"
                + " In box record.";
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, msg);
        inboxDto.setInboxDateRange(IvlConverter.convertTs().convertToIvl(getDate(0), getDate(0)));
        sis.update(inboxDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
    }

    

    @Test
    public void checkUniquenessRules() throws Exception {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED);
        bean.create(dto);
        String pat = "The milestone \"Submission Received Date\" must be unique.  It was previously recorded on {0}.";
        String date = PAUtil.normalizeDateString(TsConverter.convertToTimestamp(dto.getMilestoneDate()).toString());
        String msg = MessageFormat.format(pat, date);
        checkMilestoneFailure(MilestoneCode.SUBMISSION_RECEIVED, msg);
    }

    @Test
    public void checkScientificProcessingStartDate() throws PAException {
        String canNotReachMsg = "\"Scientific Processing Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific Processing Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, canNotReachMsg);
    }

    @Test
    public void checkScientificProcessingCompletedDate() throws PAException {
        String canNotReachMsg = "\"Scientific Processing Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific Processing Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, canNotReachMsg);
    }

    @Test
    public void checkScientificReadyForQCDate() throws PAException {
        String canNotReachMsg = "\"Ready for Scientific QC Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Ready for Scientific QC Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_READY_FOR_QC, canNotReachMsg);
    }

    @Test
    public void checkScientificQCStartDate() throws PAException {
        String canNotReachMsg = "\"Scientific QC Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific QC Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_START, canNotReachMsg);
    }

    @Test
    public void checkScientificQCCompletedDate() throws PAException {
        String canNotReachMsg = "\"Scientific QC Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Scientific QC Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.SCIENTIFIC_QC_COMPLETE, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeProcessingStartDate() throws PAException {
        String canNotReachMsg = "\"Administrative Processing Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative Processing Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeProcessingCompletedDate() throws PAException {
        String canNotReachMsg = "\"Administrative Processing Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative Processing Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeReadyForQCDate() throws PAException {
        String canNotReachMsg = "\"Ready for Administrative QC Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Ready for Administrative QC Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeQCStartDate() throws PAException {
        String canNotReachMsg = "\"Administrative QC Start Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative QC Start Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_START, canNotReachMsg);
    }

    @Test
    public void checkAdministrativeQCCompletedDate() throws PAException {
        String canNotReachMsg = "\"Administrative QC Completed Date\" can not be reached at this stage.";
        String alreadyReacheddMsg = "\"Administrative QC Completed Date\" already reached.";
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, alreadyReacheddMsg);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, canNotReachMsg);
    }

    @Test
    public void checkDocumentWorkflowStatusRules() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        for (DocumentWorkflowStatusDTO dto : dws.getByStudyProtocol(spIi)) {
            dws.delete(dto.getIdentifier());
        }
        String msg = "The processing status must be 'Accepted', 'Abstracted', 'Verification Pending', "
                + "'Abstraction Verified Response', or 'Abstraction Verified No Response' when entering the milestone "
                + "'Administrative Processing Start Date'.  The current processing status is null.";
        checkMilestoneFailure(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, msg);
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        dws.create(dwfDto);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
    }

    @Test
    public void checkAbstractionsRules() throws Exception {
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        dws.create(dwfDto);
        bean.setValidateAbstractions(true);
        bean.setAbstractionCompletionService(null);
        String msg = "Error injecting reference to AbstractionCompletionService.";
        checkMilestoneFailure(MilestoneCode.READY_FOR_TSR, msg);
    }
    
    @Test
    public void checkTrialSummaryFeedbackPrerequisiteOK() throws PAException {
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
    }

    @Test
    public void checkTrialSummaryFeedbackPrerequisiteMissing() throws PAException {
        addAbstractedWorkflowStatus();
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        String msg = "\"Trial Summary Report Sent Date\" is a prerequisite to "
                + "\"Submitter Trial Summary Report Feedback Date\".";
        checkMilestoneFailure(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, msg);
    }

    @Test
    public void checkLateRejectionPrerequisiteMissing() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        String msg = "\"Submission Acceptance Date\" is a prerequisite to \"Late Rejection Date\".";
        checkMilestoneFailure(MilestoneCode.LATE_REJECTION_DATE, msg);
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
    public void checkTSRSentMail() throws PAException {
        StudyMilestoneDTO dto = getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_SENT);
        dto.setStudyProtocolIdentifier(TestSchema.nonPropTrialData());
        mailSrc.setLookUpTableService(new LookUpTableServiceBean());
        TSRReportGeneratorServiceRemote tsrBean = new TSRReportGeneratorServiceBean();
        mailSrc.setTsrReportGeneratorService(tsrBean);
        String msg = "Trial Summary Report Sent Date' could not be recorded as sending the TSR report to the submitter  failed.";
        checkMilestoneFailure(dto, msg);
    }

    private void checkMilestoneFailure(MilestoneCode milestone, String message) {
        try {
            bean.create(getMilestoneDTO(milestone));
            fail();
        } catch (PAException e) {
            assertEquals(message, e.getMessage());
        }
    }

    private void checkMilestoneFailure(StudyMilestoneDTO milestoneDTO, String message) {
        try {
            bean.create(milestoneDTO);
            fail();
        } catch (PAException e) {
            assertEquals(message, e.getMessage());
        }
    }

    private void addAbstractedWorkflowStatus() throws PAException {
        DocumentWorkflowStatusDTO dwfDto = getDocWrkStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTED));
        dws.create(dwfDto);
    }

    @Test
    public void createDocumentWorkflowStatusForRejectedTest() throws Exception {
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkDWS(1, DocumentWorkflowStatusCode.SUBMITTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_REJECTED));
        checkDWS(2, DocumentWorkflowStatusCode.REJECTED);
    }

    @Test
    public void createDocumentWorkflowStatusesUntilAbstracted() throws Exception {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(0, dwsList.size());
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_RECEIVED));
        checkDWS(1, DocumentWorkflowStatusCode.SUBMITTED);
        bean.create(getMilestoneDTO(MilestoneCode.SUBMISSION_ACCEPTED));
        checkDWS(2, DocumentWorkflowStatusCode.ACCEPTED);
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_START));
        bean.create(getMilestoneDTO(MilestoneCode.ADMINISTRATIVE_QC_COMPLETE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_READY_FOR_QC));
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_START));
        checkDWS(2, DocumentWorkflowStatusCode.ACCEPTED);
        bean.create(getMilestoneDTO(MilestoneCode.SCIENTIFIC_QC_COMPLETE));
        checkDWS(3, DocumentWorkflowStatusCode.ABSTRACTED);
    }

    @Test
    public void createDocumentWorkflowStatusesWithNOResponse() throws Exception {
        addAbstractedWorkflowStatus();
        checkDWS(1, DocumentWorkflowStatusCode.ABSTRACTED);
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        checkDWS(2, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        bean.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        checkDWS(2, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        bean.create(getMilestoneDTO(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        checkDWS(3, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
    }

    @Test
    public void createDocumentWorkflowStatusesWithResponse() throws Exception {
        addAbstractedWorkflowStatus();
        checkDWS(1, DocumentWorkflowStatusCode.ABSTRACTED);
        bean.create(getMilestoneDTO(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        checkDWS(1, DocumentWorkflowStatusCode.ABSTRACTED);
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        checkDWS(2, DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
    }

    private void checkDWS(int expectedSize, DocumentWorkflowStatusCode expectedStatus) throws PAException {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        assertEquals("Wrong size of Document workflow status list", expectedSize, dwsList.size());
        assertEquals("Wrong Document workflow status", expectedStatus, getCurrentDocumentWorkflowStatus());
    }

    private DocumentWorkflowStatusCode getCurrentDocumentWorkflowStatus() throws PAException {
        DocumentWorkflowStatusDTO dtoDwf = dws.getCurrentByStudyProtocol(spIi);
        return (dtoDwf != null) ? DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dtoDwf
            .getStatusCode())) : null;
    }

    @Test
    public void updateRecordVerificationDateTest() throws Exception {
        addAbstractedWorkflowStatus();
        // set initial date
        StudyProtocolDTO sp = sps.getStudyProtocol(spIi);
        sp.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        sps.updateStudyProtocol(sp);

        DocumentWorkflowStatusDTO dwfsDTO = getDocWrkStatusDTO();
        dwfsDTO.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ACCEPTED));
        // milestone triggers change of date
        bean.create(getMilestoneDTO(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        sp = sps.getStudyProtocol(spIi);
        String recordVerificationDate = TsConverter.convertToString(sp.getRecordVerificationDate());
        assertTrue(ISOUtil.normalizeDateString(getDate(0).toString()).equals(recordVerificationDate));

    }

    @Test
    public void searchNotFound() throws TooManyResultsException, PAException {
        StudyMilestoneDTO studyMilestone = new StudyMilestoneDTO();
        studyMilestone.setStudyProtocolIdentifier(spIi);
        studyMilestone.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        LimitOffset limitOffset = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudyMilestoneDTO> milestones = bean.search(studyMilestone, limitOffset);
        assertEquals("There should only be one milestone present for TRIAL_SUMMARY_FEEDBACK", 0, milestones.size());
    }

    @Test
    public void searchFound() throws TooManyResultsException, PAException {
        StudyMilestoneDTO studyMilestone = new StudyMilestoneDTO();
        studyMilestone.setStudyProtocolIdentifier(spIi);
        studyMilestone.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_SENT));
        LimitOffset limitOffset = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<StudyMilestoneDTO> milestones = bean.search(studyMilestone, limitOffset);
        assertEquals("There should only be one milestone present for TRIAL_SUMMARY_SENT", 3, milestones.size());
        StudyMilestoneDTO milestone = milestones.get(0);
        assertEquals(milestone.getMilestoneCode(), studyMilestone.getMilestoneCode());
        assertEquals(milestone.getStudyProtocolIdentifier(), studyMilestone.getStudyProtocolIdentifier());
    }

    private Timestamp getDate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return new Timestamp(calendar.getTime().getTime());
    }

}
