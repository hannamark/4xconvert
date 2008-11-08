package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Ii intIi;
    
    @Before
    public void setUp() throws Exception {
        remoteEjb = new ArmServiceBean();
        paRemoteEjb = new PlannedActivityServiceBean();
        TestSchema.reset1();
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.armIds.get(0));
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
        intIi = IiConverter.convertToIi(TestSchema.plannedActivityIds.get(0));
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
    public void createTestWithInterventions() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDescriptionText(StConverter.convertToSt("description of arm"));
        dto.setIdentifier(new Ii());
        Set<Ii> intSet = new HashSet<Ii>();
        intSet.add(intIi);
        DSet<Ii> intDSet = new DSet<Ii>();
        intDSet.setItem(intSet);
        dto.setInterventions(intDSet);
        dto.setName(StConverter.convertToSt("name"));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
        try {
            remoteEjb.create(dto);
            fail("Test should have failed for null in userLastUpdated.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        dto.setUserLastUpdated(StConverter.convertToSt("Jane"));
        ArmDTO resultDto = remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, dtoList.size());
        Set<Ii> resultIntSet = resultDto.getInterventions().getItem();
        assertEquals(1, resultIntSet.size());
        assertEquals(IiConverter.convertToLong(intIi), IiConverter.convertToLong((Ii) resultIntSet.toArray()[0]));
        
    }
    @Test
    public void createTestNoInterventions() throws Exception {
        List<ArmDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        ArmDTO dto = new ArmDTO();
        dto.setStudyProtocolIdentifier(spIi);
        dto.setDescriptionText(StConverter.convertToSt("description of arm"));
        dto.setIdentifier(new Ii());
        dto.setInterventions(new DSet<Ii>());
        dto.setName(StConverter.convertToSt("name"));
        dto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
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
        assertTrue(originalCount > 0);
        remoteEjb.delete(ii);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals (originalCount - 1, dtoList.size());
    }
}
