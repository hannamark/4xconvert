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
import gov.nih.nci.pa.domain.DocumentWorkFlowStatusTest;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyOverallStatusTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusServiceTest {
    private StudyOverallStatusServiceBean bean = new StudyOverallStatusServiceBean();
    private StudyOverallStatusServiceRemote remoteEjb = bean;
    private final DocumentWorkflowStatusServiceBean dws = new DocumentWorkflowStatusServiceBean();
    private final StudyProtocolServiceLocal sps = new StudyProtocolServiceBean();
    Ii pid;

    @Before
    public void setUp() throws Exception {
        bean.setDwsService(dws);
        bean.setStudyProtocolService(sps);
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocolIds.get(0));

    }

    @Test
    public void updateTest() throws Exception {
        StudyOverallStatusDTO dto = remoteEjb.getCurrentByStudyProtocol(pid);
        try {
            remoteEjb.create(dto);
            fail("StudyOverallStatus objects cannot be modified.");
        } catch (PAException e) {
            // expected behavior
        }

        // Following tests assume current status is ACTIVE, ACTIVE can transition
        // to CLOSED_TO_ACCRUAL, and ACTIVE cannot transition to COMPLETE.
        assertTrue(StudyStatusCode.ACTIVE.getCode().equals(dto.getStatusCode().getCode()));
        assertTrue(StudyStatusCode.ACTIVE.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(StudyStatusCode.ACTIVE.canTransitionTo(StudyStatusCode.COMPLETE));
        try {
            dto.setIdentifier(IiConverter.convertToIi((Long) null));
            dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
            dto.setStudyProtocolIdentifier(pid);
            remoteEjb.create(dto);
            fail("StudyOverallStatus transitions must follow business rules.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setIdentifier(IiConverter.convertToIi((Long) null));
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.CLOSED_TO_ACCRUAL));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("2/2/2009")));
        dto.setStudyProtocolIdentifier(pid);
        remoteEjb.create(dto);

        StudyOverallStatusDTO result = remoteEjb.getCurrentByStudyProtocol(pid);
        assertNotNull(IiConverter.convertToLong(result.getIdentifier()));
        assertEquals(result.getStatusCode().getCode(), dto.getStatusCode().getCode());
        assertEquals(TsConverter.convertToTimestamp(result.getStatusDate()),
                     TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals(IiConverter.convertToLong(pid), IiConverter.convertToLong(result.getStudyProtocolIdentifier()));

    }



    /**
     * Tests the creation of the intermediate study overall status when moving from In Review to Active
     * and Active to Closed to Accrual and Intervention
     * @throws Exception
     */
    @Test
    public void testIntermediateStudyOverallStatusCreation() throws Exception {
        StudyOverallStatusConverter statusConverter = Converters.get(StudyOverallStatusConverter.class);
        InterventionalStudyProtocol sp = new InterventionalStudyProtocol();
        sp = (InterventionalStudyProtocol) StudyProtocolTest.createStudyProtocolObj(sp);
        sp = StudyProtocolTest.createInterventionalStudyProtocolObj(sp);
        TestSchema.addUpdObject(sp);
        Ii spId = IiConverter.convertToStudyProtocolIi(sp.getId());

        DocumentWorkflowStatus docWorkflow = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(docWorkflow);

        StudyOverallStatus inReview = StudyOverallStatusTest.createStudyOverallStatusobj(sp);
        inReview.setStatusCode(StudyStatusCode.IN_REVIEW);
        remoteEjb.create(statusConverter.convertFromDomainToDto(inReview));
        assertEquals(remoteEjb.getByStudyProtocol(spId).size(), 1);

        StudyOverallStatus active = StudyOverallStatusTest.createStudyOverallStatusobj(sp);
        active.setStatusCode(StudyStatusCode.ACTIVE);
        remoteEjb.create(statusConverter.convertFromDomainToDto(active));

        StudyOverallStatusDTO approved = null;
        for (StudyOverallStatusDTO dto : remoteEjb.getByStudyProtocol(spId)) {
            if (StringUtils.equals(dto.getStatusCode().getCode(), StudyStatusCode.APPROVED.getCode())) {
                approved = dto;
                break;
            }
        }
        assertNotNull("Approved intermediate status not found.", approved);
        assertEquals("Approved and In Review should have the same status date", active.getStatusDate(),
                TsConverter.convertToTimestamp(approved.getStatusDate()));

        StudyOverallStatus closedToAccrualAndIntervention = StudyOverallStatusTest.createStudyOverallStatusobj(sp);
        closedToAccrualAndIntervention.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        remoteEjb.create(statusConverter.convertFromDomainToDto(closedToAccrualAndIntervention));

        StudyOverallStatusDTO closedToAccrual = null;
        for (StudyOverallStatusDTO dto : remoteEjb.getByStudyProtocol(spId)) {
            if (StringUtils.equals(dto.getStatusCode().getCode(), StudyStatusCode.CLOSED_TO_ACCRUAL.getCode())) {
                closedToAccrual = dto;
                break;
            }
        }
        assertNotNull("Closed to Accrual intermediate status not found.", closedToAccrual);
        assertEquals("Closed to Accrual and Closed to Accrual and Intervention should have the same status date",
                    closedToAccrualAndIntervention.getStatusDate(),
                    TsConverter.convertToTimestamp(closedToAccrual.getStatusDate()));
    }


    @Test
    public void getByProtocolTest() throws Exception {
        List<StudyOverallStatusDTO> statusList = remoteEjb.getByStudyProtocol(pid);
        assertEquals(2, statusList.size());

        StudyOverallStatusDTO dto = remoteEjb.getCurrentByStudyProtocol(pid);
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIdentifier()),
                     (IiConverter.convertToLong(dto.getIdentifier())));
    }

    @Test
    public void createTest() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = StudyProtocolTest.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/1999")));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        Ii initialIi = null;
        dto.setIdentifier(initialIi);
        assertTrue(PAUtil.isIiNull(dto.getIdentifier()));
        StudyOverallStatusDTO resultDto = remoteEjb.create(dto);
        assertFalse(PAUtil.isIiNull(resultDto.getIdentifier()));
    }

    @Test
    public void nullInDateTest() throws Exception {
        StudyProtocol spNew = StudyProtocolTest.createStudyProtocolObj();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);

        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.ACTIVE));
        dto.setStatusDate(null);
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(spNew.getId()));
        dto.setIdentifier(null);
        try {
            remoteEjb.create(dto);
            fail("PAException should have been thrown for null in status date.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setStatusDate(TsConverter.convertToTs(null));
        try {
            remoteEjb.create(dto);
            fail("PAException should have been thrown for Ts null in status date.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2000")));
        dto = remoteEjb.create(dto);
        assertFalse(PAUtil.isIiNull(dto.getIdentifier()));
    }

    @Test
    public void iiRootTest() throws Exception {
        List<StudyOverallStatusDTO> dtoList = remoteEjb.getByStudyProtocol(pid);
        assertTrue(dtoList.size() > 0);
        StudyOverallStatusDTO dto = dtoList.get(0);
        assertEquals(dto.getIdentifier().getRoot(), IiConverter.STUDY_OVERALL_STATUS_ROOT);
        assertTrue(StringUtils.isNotEmpty(dto.getIdentifier().getIdentifierName()));
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
}
