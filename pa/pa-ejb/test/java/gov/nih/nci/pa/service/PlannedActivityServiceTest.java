/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class PlannedActivityServiceTest {
    private PlannedActivityServiceRemote remoteEjb = new PlannedActivityServiceBean();
    private Ii ii;
    private Ii spIi;
    private Ii armIi;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.plannedActivityIds.get(0));
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
        armIi = IiConverter.convertToIi(TestSchema.armIds.get(0));
     }    
    @Test 
    public void getTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertTrue("name".equals(StConverter.convertToString(dto.getName())));
        assertTrue("alternateName".equals(StConverter.convertToString(dto.getAlternateName())));
    }
    @Test
    public void getByStudyProtocolTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(1, dtoList.size());
    }
    @Test
    public void getByArmTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByArm(armIi);
        assertEquals(1, dtoList.size());
    }
    @Test
    public void createTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        PlannedActivityDTO dto = new PlannedActivityDTO();
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remoteEjb.create(dto);
//            fail("Test should have failed for null in userLastUpdated.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 2, dtoList.size());
    }
    @Test
    public void updateTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertTrue("name".equals(StConverter.convertToString(dto.getName())));
        assertTrue("alternateName".equals(StConverter.convertToString(dto.getAlternateName())));
        PlannedActivityDTO updateDto = new PlannedActivityDTO();
        updateDto.setIdentifier(dto.getIdentifier());
        updateDto.setName(StConverter.convertToSt("new name"));
        try {
            remoteEjb.update(updateDto);
            //fail("Test should have failed for null in userLastUpdated.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        PlannedActivityDTO resultDto = remoteEjb.update(updateDto);
        assertTrue("new name".equals(StConverter.convertToString(resultDto.getName())));
        assertTrue("alternateName".equals(StConverter.convertToString(resultDto.getAlternateName())));
    }
    @Test
    public void deleteTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        remoteEjb.delete(ii);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals (originalCount - 1, dtoList.size());
    }
}
