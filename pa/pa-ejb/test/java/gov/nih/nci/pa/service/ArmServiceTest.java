package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class ArmServiceTest {
    private ArmServiceRemote remoteEjb = new ArmServiceBean();
    private PlannedActivityServiceRemote paRemoteEjb = new PlannedActivityServiceBean();
    private Ii ii;
    private Ii spIi;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.armIds.get(0));
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
     }    
    @Test 
    public void getTest() throws Exception {
        ArmDTO dto = remoteEjb.get(ii);
        assertTrue("ARM 01".equals(StConverter.convertToString(dto.getName())));
        assertNotNull(dto.getInterventions());
        Collection<Ii> interventionIis = dto.getInterventions().getItem();
        assertTrue(0 < interventionIis.size());
        PlannedActivityDTO paDto = paRemoteEjb.get((Ii) interventionIis.toArray()[0]);
        assertEquals(IiConverter.convertToLong(paDto.getStudyProtocolIdentifier())
                   , IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    }
    @Test
    public void getByStudyProtocolTest() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(1, dtoList.size());
    }
    @Test
    public void createTest() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        try {
            remoteEjb.create(dto);
            fail("Test should have failed for null in userLastUpdated.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        dto.setUserLastUpdated(StConverter.convertToSt("Jane"));
        remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, dtoList.size());
    }
    @Test
    public void updateTest() throws Exception {
        ArmDTO dto = remoteEjb.get(ii);
        String oldName = StConverter.convertToString(dto.getName());
        String newName = "new name";
        assertFalse(newName.equals(oldName));
        dto.setName(StConverter.convertToSt(newName));
        dto.setUserLastUpdated(null);
        try {
            remoteEjb.update(dto);
            fail("Test should have failed for null in userLastUpdated.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        dto.setUserLastUpdated(StConverter.convertToSt("Jane"));
        remoteEjb.update(dto);
        ArmDTO newDto = remoteEjb.get(ii);
        assertTrue(newName.equals(StConverter.convertToString(newDto.getName())));
    }
    @Test
    public void deleteTest() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        remoteEjb.delete(ii);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals (originalCount - 1, dtoList.size());
    }
}
