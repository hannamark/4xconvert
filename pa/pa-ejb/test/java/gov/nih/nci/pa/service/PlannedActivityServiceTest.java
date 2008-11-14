/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
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
    private ArmServiceRemote remoteArmEjb = new ArmServiceBean();
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
        assertEquals(TestSchema.interventionIds.get(0), IiConverter.convertToLong(dto.getInterventionIdentifier()));
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
        dto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        dto.setInterventionIdentifier(null);
        dto.setLeadProductIndicator(null);
        dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.BEHAVIORAL));
        try {
            remoteEjb.create(dto);
            fail("Test should have failed for null in interventionIdentifier.  ");
        } catch (PAException e) {
            // expected behavior, failure for not logging user
        }
        dto.setInterventionIdentifier(IiConverter.convertToIi(TestSchema.interventionIds.get(0)));
        remoteEjb.create(dto);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, dtoList.size());
    }
    @Test
    public void updateTest() throws Exception {
        PlannedActivityDTO dto = remoteEjb.get(ii);
        assertFalse(ActivitySubcategoryCode.DRUG.equals(
                ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode()))));
        dto.setSubcategoryCode(CdConverter.convertToCd(ActivitySubcategoryCode.DRUG));
        PlannedActivityDTO resultDto = remoteEjb.update(dto);
        assertTrue(ActivitySubcategoryCode.DRUG.equals(
                ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(resultDto.getSubcategoryCode()))));
    }
    @Test
    public void deleteTest() throws Exception {
        List<PlannedActivityDTO> dtoList = remoteEjb.getByStudyProtocol(spIi);
        int originalCount = dtoList.size();
        for (Long armId : TestSchema.armIds) {
            ArmDTO arm = remoteArmEjb.get(IiConverter.convertToIi(armId));
            arm.setInterventions(null);
            remoteArmEjb.update(arm);
        }
        remoteEjb.delete(ii);
        dtoList = remoteEjb.getByStudyProtocol(spIi);
        assertEquals (originalCount - 1, dtoList.size());
    }
}
