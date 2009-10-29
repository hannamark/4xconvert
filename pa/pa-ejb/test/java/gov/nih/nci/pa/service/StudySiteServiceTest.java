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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test service and converter.
 * @author hreinhart
 *
 */
public class StudySiteServiceTest {
    private StudySiteServiceRemote remoteEjb = new StudySiteServiceBean();
    private StudySiteConverter studySiteConverter = new StudySiteConverter();
    Long studyId;
    Ii studyIi;
    Long siteId;
    Ii siteIi;
    Long facilityId;
    Ii facilityIi;
    Long researchOrgId;
    Ii researchOrgIi;
    Long oversightCommitteeId;
    Ii oversightCommitteeIi;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        studyId = TestSchema.studyProtocolIds.get(0);
        studyIi = IiConverter.convertToStudyProtocolIi(studyId);
        siteId = TestSchema.studySiteIds.get(0);
        siteIi = IiConverter.convertToIi(siteId);
        facilityId = TestSchema.healthCareFacilityIds.get(0);
        facilityIi = IiConverter.convertToIi(facilityId);
        researchOrgId = TestSchema.researchOrganizationIds.get(0);
        researchOrgIi = IiConverter.convertToIi(researchOrgId);
        oversightCommitteeId = TestSchema.oversightCommitteeIds.get(0);
        oversightCommitteeIi = IiConverter.convertToIi(oversightCommitteeId);
    }
    @Test
    public void get() throws Exception {
        StudySiteDTO spDto = remoteEjb.get(siteIi);
        StudySite spBo = studySiteConverter.convertFromDtoToDomain(spDto);
        assertEquals(studyId, spBo.getStudyProtocol().getId());
        assertEquals(StudySiteFunctionalCode.LEAD_ORGANIZATION.getName()
                    , spBo.getFunctionalCode().getName());
        assertEquals(FunctionalRoleStatusCode.ACTIVE.getName(), spBo.getStatusCode().getName());
        assertEquals("Local SP ID 01", spBo.getLocalStudyProtocolIdentifier());
    }
    @Test
    public void create() throws Exception {
        int accrualNum = 63;
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setIdentifier(null);
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        spDto.setOversightCommitteeIi(oversightCommitteeIi);
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP ID 02"));
        spDto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        spDto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(PAUtil.dateStringToTimestamp("1/1/2005"),null));
        spDto.setReviewBoardApprovalStatusCode
            (CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED));
        spDto.setReviewBoardApprovalNumber(StConverter.convertToSt("777"));
        spDto.setStudyProtocolIdentifier(studyIi);
        spDto.setTargetAccrualNumber(IntConverter.convertToInt(accrualNum));
        StudySiteDTO result = remoteEjb.create(spDto);
        assertFalse(PAUtil.isIiNull(result.getIdentifier()));
        assertEquals(CdConverter.convertCdToString(spDto.getFunctionalCode())
                , CdConverter.convertCdToString(result.getFunctionalCode()));
        assertTrue(accrualNum == IntConverter.convertToInteger(result.getTargetAccrualNumber()));

        LimitOffset pagingParams = new LimitOffset(1, 1);
        StudySiteDTO dto2 = new StudySiteDTO();
        dto2.setStudyProtocolIdentifier(studyIi);
        List<StudySiteDTO> list = remoteEjb.search(dto2, pagingParams);
        assertEquals(1,list.size());
    }
    @Test
    public void delete() throws Exception {
        remoteEjb.delete(siteIi);
        try {
            StudySiteDTO spDto = remoteEjb.get(siteIi);
            assertNull(spDto);
        } catch(PAException e) {
            // expected behavior
        }
    }
    @Test
    public void getByProtocol() throws Exception {
        List<StudySiteDTO> spList = remoteEjb.getByStudyProtocol(studyIi);
        assertEquals(siteId, IiConverter.convertToLong(spList.get(0).getIdentifier()));
        List<StudySiteDTO> spList2 = remoteEjb.getByStudyProtocol(studyIi, spList);
        assertEquals(siteId, IiConverter.convertToLong(spList2.get(0).getIdentifier()));
        List<StudySiteDTO> spList3 = remoteEjb.getByStudyProtocol(studyIi, spList2.get(0));
        assertEquals(siteId, IiConverter.convertToLong(spList3.get(0).getIdentifier()));
        
    }
    @Test
    public void businessRules() throws Exception {
        StudySiteDTO dto = remoteEjb.get(siteIi);
        dto.setOversightCommitteeIi(null);
        dto.setReviewBoardApprovalStatusCode
        (CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED));
        try {
            remoteEjb.update(dto);
            fail("Should have thrown an exception for either oversight committee must be set.");
        } catch (PAException e) {
            // expected behavior
        }
       
    }
   /* @Test
    public void enforceOnlyOneOversightCommittee() throws Exception {
        StudyParticipationDTO sp1 = remoteEjb.get(siteIi);
        
        // set first study site IRB
        sp1.setHealthcareFacilityIi(null);
        sp1.setOversightCommitteeIi(oversightCommitteeIi);
        sp1.setReviewBoardApprovalDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2001")));
        sp1.setReviewBoardApprovalNumber(StConverter.convertToSt("approval number"));
        sp1.setReviewBoardApprovalStatusCode(CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT));
        remoteEjb.update(sp1);
        sp1 = remoteEjb.get(siteIi);
        assertFalse(PAUtil.isIiNull(sp1.getOversightCommitteeIi()));

        // create another study site IRB
        sp1.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        sp1.setReviewBoardApprovalStatusCode(CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED));
        StudyParticipationDTO sp2 = remoteEjb.create(sp1);
        assertFalse(PAUtil.isIiNull(sp2.getOversightCommitteeIi()));

        // confirm first one modified
        sp1 = remoteEjb.get(siteIi);
        assertTrue(PAUtil.isIiNull(sp1.getOversightCommitteeIi()));
        assertNull(TsConverter.convertToTimestamp(sp1.getReviewBoardApprovalDate()));
        assertNull(StConverter.convertToString(sp1.getReviewBoardApprovalNumber()));
        assertTrue(PAUtil.isCdNull(sp1.getReviewBoardApprovalStatusCode()));
    }*/
//    @Test 
    public void enforceOnlyOneOrgFunctionPerStudy() throws Exception {
        StudySiteDTO sp = remoteEjb.get(siteIi);
        
        // set functional role for first study site
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        sp = remoteEjb.update(sp);

        // create another identical study site
        sp.setIdentifier(null);
        try {
            remoteEjb.create(sp);
            fail("Trying to assign the same organization with the same functional role to a study "
                    + "twice should throw PADuplicateException.");
        } catch (PADuplicateException e) {
            // expected behavior
        }
        
        // create a site with different role
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.FUNDING_SOURCE));
        sp = remoteEjb.create(sp);
        assertFalse(PAUtil.isIiNull(sp.getIdentifier()));
        assertFalse(siteId.equals(IiConverter.convertToLong(sp.getIdentifier())));
    }
    @Test 
    public void iiRootTest() throws Exception {
        StudySiteDTO dto = remoteEjb.get(siteIi);
        assertEquals(dto.getStudyProtocolIdentifier().getRoot(), IiConverter.STUDY_PROTOCOL_ROOT);
    }
}
