package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseAltername;
import gov.nih.nci.pa.domain.DiseaseAlternameTest;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class DiseaseAlternameServiceTest {
    private DiseaseAlternameServiceBean bean = new DiseaseAlternameServiceBean();
    private DiseaseAlternameServiceRemote remote = bean;
    private Ii dIi;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        dIi = IiConverter.convertToIi(TestSchema.diseaseIds.get(0));
     }
    
    private void compareDataAttributes(DiseaseAltername bo1, DiseaseAltername bo2) {
        assertEquals(bo1.getAlternateName(), bo2.getAlternateName());
        assertEquals(bo1.getStatusCode(), bo2.getStatusCode());
        assertEquals(bo1.getStatusDateRangeLow(), bo2.getStatusDateRangeLow());
    }
    
    @Test
    public void getTest() throws Exception {
        List<DiseaseAlternameDTO> dtoList = bean.getByDisease(dIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(PAUtil.isIiNull(ii));
        DiseaseAlternameDTO resultDto = bean.get(ii);
        assertFalse(PAUtil.isIiNull(resultDto.getIdentifier()));
    }

    @Test
    public void createTest() throws Exception {
        List<DiseaseAlternameDTO> dtoList = bean.getByDisease(dIi);
        int oldSize = dtoList.size();
        
        Disease disease = new Disease();
        disease.setId(IiConverter.convertToLong(dIi));        
        DiseaseAltername bo = DiseaseAlternameTest.createDiseaseAlternameObj("new name", disease);
        assertNull(bo.getId());
        DiseaseAltername resultBo = bean.convertFromDtoToDomain(remote.create(bean.convertFromDomainToDto(bo)));
        compareDataAttributes(bo, resultBo);
        assertNotNull(resultBo.getId());
    }

    @Test
    public void updateTest() throws Exception {
        List<DiseaseAlternameDTO> dtoList = bean.getByDisease(dIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        DiseaseAlternameDTO dto = remote.get(ii);
        DiseaseAltername bo = bean.convertFromDtoToDomain(dto);
        assertFalse("new name".equals(bo.getAlternateName()));
        bo.setAlternateName("new name");
        dto = bean.convertFromDomainToDto(bo);
        DiseaseAlternameDTO resultDto = remote.update(dto);
        DiseaseAltername resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertTrue("new name".equals(resultBo.getAlternateName()));
    }
    
    @Test 
    public void deleteTest() throws Exception {
        List<DiseaseAlternameDTO> dtoList = bean.getByDisease(dIi);
        assertTrue(dtoList.size() > 0);
        int oldSize = dtoList.size();
        Ii ii = dtoList.get(0).getIdentifier();
        remote.delete(ii);
        dtoList = bean.getByDisease(dIi);
        assertEquals(oldSize - 1, dtoList.size());
    }
}
