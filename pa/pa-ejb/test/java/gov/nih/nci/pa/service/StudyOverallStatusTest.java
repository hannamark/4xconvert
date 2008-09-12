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
public class StudyOverallStatusTest {
    private StudyOverallStatusServiceRemote remoteEjb = new StudyOverallStatusServiceBean();;
    Ii pid;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
    }    
    
    @Test
    public void update() throws Exception {
        StudyOverallStatusDTO dto = 
            remoteEjb.getCurrentStudyOverallStatusByStudyProtocol(pid);
        try {
            remoteEjb.updateStudyOverallStatus(dto);
            fail("StudyOverallStatus objects cannot be modified.");
        } catch (PAException e) {
            // expected behavior
        }
        
        // Following tests assume current status is ACTIVE, ACTIVE can transition 
        //   to CLOSED_TO_ACCRUAL, and ACTIVE cannot transition to COMPLETE.
        assertTrue(StudyStatusCode.ACTIVE.equals(CdConverter.convertToStudyStatusCode(dto.getStatusCode())));
        assertTrue(StudyStatusCode.ACTIVE.canTransitionTo(StudyStatusCode.CLOSED_TO_ACCRUAL));
        assertFalse(StudyStatusCode.ACTIVE.canTransitionTo(StudyStatusCode.COMPLETE));
        try {
            dto.setIi(IiConverter.convertToIi((Long) null)); 
            dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.COMPLETE));
            remoteEjb.updateStudyOverallStatus(dto);
            fail("StudyOverallStatus transitions must follow business rules.");
        } catch (PAException e) {
            // expected behavior
        }
        dto.setIi(IiConverter.convertToIi((Long) null));
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.CLOSED_TO_ACCRUAL));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("2/2/2009")));
        remoteEjb.updateStudyOverallStatus(dto);
        
        StudyOverallStatusDTO result = 
            remoteEjb.getCurrentStudyOverallStatusByStudyProtocol(pid);
        assertNotNull (IiConverter.convertToLong(result.getIi()));
        assertEquals (CdConverter.convertToStudyStatusCode(result.getStatusCode())
                    , CdConverter.convertToStudyStatusCode(dto.getStatusCode()));
        assertEquals (TsConverter.convertToTimestamp(result.getStatusDate())
                    , TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals (IiConverter.convertToLong(pid)
                    , IiConverter.convertToLong(result.getStudyProtocolidentifier()));
        
    }
    
    @Test 
    public void getByProtocol() throws Exception {
        List<StudyOverallStatusDTO> statusList = 
            remoteEjb.getStudyOverallStatusByStudyProtocol(pid);
        assertEquals(2, statusList.size());
        
        StudyOverallStatusDTO dto = 
            remoteEjb.getCurrentStudyOverallStatusByStudyProtocol(pid);
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIi())
                , (IiConverter.convertToLong(dto.getIi())));
    }
 
}
