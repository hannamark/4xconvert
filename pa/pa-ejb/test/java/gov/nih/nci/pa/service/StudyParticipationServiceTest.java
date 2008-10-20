/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipation;
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
    Long studyId;
    Ii studyIi;
    Long participationId;
    Ii participationIi;
    Long facilityId;
    Ii facilityIi;
    
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
    }
    @Test
    public void get() throws Exception {
        StudyParticipationDTO spDto = remoteEjb.get(participationIi);
        StudyParticipation spBo = StudyParticipationConverter.convertFromDtoToDomain(spDto);
        assertEquals(studyId, spBo.getStudyProtocol().getId());
        assertEquals(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getName()
                    , spBo.getFunctionalCode().getName());
        assertEquals(StatusCode.ACTIVE.getName(), spBo.getStatusCode().getName());
        assertEquals("Local SP ID 01", spBo.getLocalStudyProtocolIdentifier());
    }
    @Test
    public void create() throws Exception {
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        spDto.setIi(IiConverter.convertToIi((Long) null)); 
        spDto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        spDto.setHealthcareFacilityIi(facilityIi);
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Local SP ID 02"));
        spDto.setStatusCode(CdConverter.convertToCd(StatusCode.ACTIVE));
        spDto.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("6/15/2008")));
        spDto.setStudyProtocolIi(participationIi);
        StudyParticipationDTO result = remoteEjb.create(spDto);
        assertFalse(PAUtil.isIiNull(result.getIi()));
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
        assertEquals(participationId, IiConverter.convertToLong(spList.get(0).getIi()));
    }
}
