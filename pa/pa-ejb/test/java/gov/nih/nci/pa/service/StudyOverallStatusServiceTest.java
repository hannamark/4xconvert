/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusServiceTest {
    private StudyOverallStatusServiceRemote remoteEjb = new StudyOverallStatusServiceBean();
    private StudyProtocolServiceRemote protocolEjb = new StudyProtocolServiceBean();
    Ii pid;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }    
    
    @Test
    public void updateTest() throws Exception {
        StudyOverallStatusDTO dto = 
            remoteEjb.getCurrentByStudyProtocol(pid).get(0);
        try {
            remoteEjb.create(dto);
            fail("StudyOverallStatus objects cannot be modified.");
        } catch (PAException e) {
            // expected behavior
        }
        
        // Following tests assume current status is ACTIVE, ACTIVE can transition 
        //   to CLOSED_TO_ACCRUAL, and ACTIVE cannot transition to COMPLETE.
        assertTrue(StudyStatusCode.ACTIVE.getCode().equals(dto.getStatusCode().getCode()));
        assertTrue(StudyStatusCode.ACTIVE.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(StudyStatusCode.ACTIVE.canTransitionTo(StudyStatusCode.COMPLETE));
        try {
            dto.setIi(IiConverter.convertToIi((Long) null)); 
            dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
            remoteEjb.create(dto);
            fail("StudyOverallStatus transitions must follow business rules.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setIi(IiConverter.convertToIi((Long) null));
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.CLOSED_TO_ACCRUAL));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("2/2/2009")));
        remoteEjb.create(dto);
        
        StudyOverallStatusDTO result = 
            remoteEjb.getCurrentByStudyProtocol(pid).get(0);
        assertNotNull (IiConverter.convertToLong(result.getIi()));
        assertEquals (result.getStatusCode().getCode(), dto.getStatusCode().getCode());
        assertEquals (TsConverter.convertToTimestamp(result.getStatusDate())
                    , TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals (IiConverter.convertToLong(pid)
                    , IiConverter.convertToLong(result.getStudyProtocolIi()));
        
    }
    
    @Test 
    public void getByProtocolTest() throws Exception {
        List<StudyOverallStatusDTO> statusList = 
            remoteEjb.getByStudyProtocol(pid);
        assertEquals(2, statusList.size());
        
        StudyOverallStatusDTO dto = 
            remoteEjb.getCurrentByStudyProtocol(pid).get(0);
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIi())
                , (IiConverter.convertToLong(dto.getIi())));
    }
    
    @Test
    public void createTest() throws Exception {
        // simulate creating new protocol using registry
        StudyProtocol spNew = new StudyProtocol();
        spNew.setOfficialTitle("New Protocol");
        TestSchema.addUpdObject(spNew);
        
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/1999")));
        dto.setStudyProtocolIi(IiConverter.convertToIi(spNew.getId()));
        Ii initialIi = null;
        dto.setIi(initialIi);
        assertTrue(PAUtil.isIiNull(dto.getIi()));
        StudyOverallStatusDTO resultDto = remoteEjb.create(dto);
        assertFalse(PAUtil.isIiNull(resultDto.getIi()));
    }
 
}
