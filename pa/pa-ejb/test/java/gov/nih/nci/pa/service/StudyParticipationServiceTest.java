/**
 *
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
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
public class StudyParticipationServiceTest {
    private StudyParticipationServiceRemote remoteEjb = new StudyParticipationServiceBean();
    private StudyParticipationConverter studyParticipationConverter = new StudyParticipationConverter();
    Long studyId;
    Ii studyIi;
    Long participationId;
    Ii participationIi;
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
        studyIi = IiConverter.convertToIi(studyId);
        participationId = TestSchema.studyParticipationIds.get(0);
        participationIi = IiConverter.convertToIi(participationId);
        facilityId = TestSchema.healthCareFacilityIds.get(0);
        facilityIi = IiConverter.convertToIi(facilityId);
        researchOrgId = TestSchema.researchOrganizationIds.get(0);
        researchOrgIi = IiConverter.convertToIi(researchOrgId);
        oversightCommitteeId = TestSchema.oversightCommitteeIds.get(0);
        oversightCommitteeIi = IiConverter.convertToIi(oversightCommitteeId);
    }
    @Test
    public void get() throws Exception {
        StudyParticipationDTO spDto = remoteEjb.get(participationIi);
        StudyParticipation spBo = studyParticipationConverter.convertFromDtoToDomain(spDto);
        assertEquals(studyId, spBo.getStudyProtocol().getId());
        assertEquals(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getName()
                    , spBo.getFunctionalCode().getName());
        assertEquals(StatusCode.ACTIVE.getName(), spBo.getStatusCode().getName());
        assertEquals("Local SP ID 01", spBo.getLocalStudyProtocolIdentifier());
    }
    @Test
    public void create() throws Exception {
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        spDto.setIdentifier(IiConverter.convertToIi((Long) null));
        spDto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        spDto.setHealthcareFacilityIi(facilityIi);
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP ID 02"));
        spDto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        spDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("6/15/2008")));
        spDto.setStudyProtocolIdentifier(participationIi);
        StudyParticipationDTO result = remoteEjb.create(spDto);
        assertFalse(PAUtil.isIiNull(result.getIdentifier()));
        assertEquals(CdConverter.convertCdToString(spDto.getFunctionalCode())
                , CdConverter.convertCdToString(result.getFunctionalCode()));
    }
    @Test
    public void delete() throws Exception {
        remoteEjb.delete(participationIi);
        try {
            remoteEjb.get(participationIi);
            fail("get() should have thrown an exception after delete().");
        } catch(PAException e) {
            // expected behavior
        }
    }
    @Test
    public void getByProtocol() throws Exception {
        List<StudyParticipationDTO> spList = remoteEjb.getByStudyProtocol(studyIi);
        assertEquals(participationId, IiConverter.convertToLong(spList.get(0).getIdentifier()));
        List<StudyParticipationDTO> spList2 = remoteEjb.getByStudyProtocol(studyIi, spList);
        assertEquals(participationId, IiConverter.convertToLong(spList2.get(0).getIdentifier()));
        List<StudyParticipationDTO> spList3 = remoteEjb.getByStudyProtocol(studyIi, spList2.get(0));
        assertEquals(participationId, IiConverter.convertToLong(spList3.get(0).getIdentifier()));
        
    }
    @Test
    public void businessRules() throws Exception {
        StudyParticipationDTO dto = remoteEjb.get(participationIi);
        dto.setHealthcareFacilityIi(null);
        dto.setResearchOrganizationIi(null);
        try {
            remoteEjb.update(dto);
            fail("Should have thrown an exception for either Healthcare Facility or Research Organization must be set.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setHealthcareFacilityIi(facilityIi);
        dto.setResearchOrganizationIi(researchOrgIi);
        try {
            remoteEjb.update(dto);
            fail("Should have thrown an exception for either Healthcare Facility or Research Organization must null.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setHealthcareFacilityIi(null);
        try {
            remoteEjb.update(dto);
            // expected behavior
        } catch (PAException e) {
            fail("Exception thrown during update which should have worked.  ");
        }
    }
    @Test
    public void enforceOnlyOneOversightCommittee() throws Exception {
        StudyParticipationDTO sp1 = remoteEjb.get(participationIi);
        
        // set first study participation IRB
        sp1.setOversightCommitteeIi(oversightCommitteeIi);
        sp1.setReviewBoardApprovalDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2001")));
        sp1.setReviewBoardApprovalNumber(StConverter.convertToSt("approval number"));
        sp1.setReviewBoardApprovalStatusCode(CdConverter.convertToCd(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED));
        remoteEjb.update(sp1);
        sp1 = remoteEjb.get(participationIi);
        assertFalse(PAUtil.isIiNull(sp1.getOversightCommitteeIi()));

        // create another study participation IRB
        sp1.setIdentifier(null);
        StudyParticipationDTO sp2 = remoteEjb.create(sp1);
        assertFalse(PAUtil.isIiNull(sp2.getOversightCommitteeIi()));

        // confirm first one modified
        sp1 = remoteEjb.get(participationIi);
        assertTrue(PAUtil.isIiNull(sp1.getOversightCommitteeIi()));
        assertNull(TsConverter.convertToTimestamp(sp1.getReviewBoardApprovalDate()));
        assertNull(StConverter.convertToString(sp1.getReviewBoardApprovalNumber()));
        assertTrue(PAUtil.isCdNull(sp1.getReviewBoardApprovalStatusCode()));
    }
}
