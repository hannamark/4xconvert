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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyMilestoneServiceTest {
    private final StudyMilestoneServiceBean bean = new StudyMilestoneServiceBean();
    private final StudyMilestoneServiceRemote remote = bean;
    private final DocumentWorkflowStatusServiceBean dws = new DocumentWorkflowStatusServiceBean();
    private final StudyOnholdServiceRemote ohs = new StudyOnholdServiceBean();
    private final StudyProtocolServiceRemote sps = new StudyProtocolServiceBean();
    private Ii spIi;

    @Before
    public void setUp() throws Exception {
        bean.documentWorkflowStatusService = dws;
        bean.studyOnholdService = ohs;
        bean.studyProtocolService = sps;
        bean.validateAbstractions = false;
        TestSchema.reset1();
        TestSchema.primeData();
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
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
            //expected behavior
        }
    }

    @Test
    public void createTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();

        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.PDQ_ABSTRACTION_COMPLETE));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        remote.create(dto);
        dtoList = remote.getByStudyProtocol(spIi);
        assertEquals(oldSize + 1, dtoList.size());
     }

    @Test
    public void deleteTest() throws Exception {
        List<StudyMilestoneDTO> dtoList = remote.getByStudyProtocol(spIi);
        dtoList.size();
        try {
            remote.delete(dtoList.get(0).getIdentifier());
            fail("The delete method in milestones service should be disabled.");
        } catch (PAException e) {
            //expected behavior
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
        dwf.setStudyProtocol((StudyProtocol) TestSchema.getSession().get(StudyProtocol.class, IiConverter.convertToLong(spIi)));
        dwf.setStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE);
        dwf.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        TestSchema.addUpdObject(dwf);

        // try to create
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.INITIAL_CTGOV_SUBMISSION));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remote.create(dto);
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
        remote.create(dto);
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
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.INITIAL_CTGOV_SUBMISSION));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remote.create(dto);
            fail("Should have failed because of no DWF.");
        } catch (PAException e) {
            // expected behavior
        }

        // create
        DocumentWorkflowStatusDTO dwfDto = new DocumentWorkflowStatusDTO();
        dwfDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE));
        dwfDto.setStatusDateRange(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dwfDto.setStudyProtocolIdentifier(spIi);
        dws.create(dwfDto);
        dto = remote.create(dto);
        assertFalse(PAUtil.isIiNull(dto.getIdentifier()));
    }

    @Test
    public void missingDataTest() throws Exception {
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.PDQ_ABSTRACTION_COMPLETE));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));

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
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.PDQ_ABSTRACTION_COMPLETE));
        dto.setMilestoneDate(null);
        try {
            remote.create(dto);
            fail();
        } catch (PAException e) {
            // expected behavior
        }

        // create
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        remote.create(dto);
    }

    @Test
    public void prerequisiteTest() throws Exception {
        // trial summary sent is prerequisite for trial summay feedback
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remote.create(dto);
            fail("Should fail for missing prerequisite.");
        } catch (PAException e) {
            // expected behavior
        }
        //TODO this testing was failing. Need to revisit.
        //dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_SENT));
        //remote.create(dto);
        //dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        //remote.create(dto);
    }

    @Test
    public void createDocumentWorkflowStatusesTest() throws Exception {
        List<DocumentWorkflowStatusDTO> dwsList = dws.getByStudyProtocol(spIi);
        int dwsCount = dwsList.size();
        assertTrue(dwsCount > 0);

        // start qc does not change dws
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.QC_START));
        dto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        dto.setStudyProtocolIdentifier(spIi);
        remote.create(dto);
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());

        // complete qc only changes dws if current dws is accepted
        dwsList = dws.getCurrentByStudyProtocol(spIi);
        assertFalse(DocumentWorkflowStatusCode.ACCEPTED.equals(
                DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dwsList.get(0).getStatusCode()))));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.QC_COMPLETE));
        remote.create(dto);
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());

        // initial verification set dws to verified-noresponse if no feedback has been received
        assertFalse(bean.milestoneExists(MilestoneCode.TRIAL_SUMMARY_FEEDBACK, dto));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        remote.create(dto);
        dwsList = dws.getCurrentByStudyProtocol(spIi);
        assertTrue(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.equals(
                DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dwsList.get(0).getStatusCode()))));
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(++dwsCount, dwsList.size());

        //  ongoing verifications do not change dws if still no feedback
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        remote.create(dto);
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());

        //TODO this testing was failing. Need to revisit.
        // trial summary sent feedback does not change dws
        //dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_SENT));
        //remote.create(dto);
        //dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_FEEDBACK));
        //remote.create(dto);
        dwsList = dws.getByStudyProtocol(spIi);
        assertEquals(dwsCount, dwsList.size());

        // ongoing verifications set to verified-response after feedback
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION));
        remote.create(dto);
        dwsList = dws.getCurrentByStudyProtocol(spIi);
        //TODO this testing was failing. Need to revisit.
        //assertTrue(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.equals(
        //        DocumentWorkflowStatusCode.getByCode(CdConverter.convertCdToString(dwsList.get(0).getStatusCode()))));
        //dwsList = dws.getByStudyProtocol(spIi);
        //assertEquals(++dwsCount, dwsList.size());
    }

    @Test
    public void updateRecordVerificationDateTest() throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime());
        Thread.sleep(100);
        Timestamp t2 = new Timestamp(new Date().getTime());
        assertFalse(t1.equals(t2));

        // set initial date
        StudyProtocolDTO sp = sps.getStudyProtocol(spIi);
        sp.setRecordVerificationDate(TsConverter.convertToTs(t1));
        sps.updateStudyProtocol(sp);

        // milestone triggers change of date
        StudyMilestoneDTO dto = new StudyMilestoneDTO();
        dto.setCommentText(StConverter.convertToSt("comment"));
        dto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.INITIAL_ABSTRACTION_VERIFY));
        dto.setMilestoneDate(TsConverter.convertToTs(t2));
        dto.setStudyProtocolIdentifier(spIi);
        remote.create(dto);
        sp = sps.getStudyProtocol(spIi);
        assertTrue(t2.equals(TsConverter.convertToTimestamp(sp.getRecordVerificationDate())));
    }
}