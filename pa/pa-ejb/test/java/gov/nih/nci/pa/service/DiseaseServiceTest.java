package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseTest;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * @author hreinhart
 *
 */
public class DiseaseServiceTest {
    private DiseaseServiceBean bean = new DiseaseServiceBean();
    private DiseaseServiceRemote remote = bean;
    private Ii ii;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.diseaseIds.get(0));
     }
    
    private void compareDataAttributes(Disease bo1, Disease bo2) {
        assertEquals(bo1.getDiseaseCode(), bo2.getDiseaseCode());
        assertEquals(bo1.getMenuDisplayName(), bo2.getMenuDisplayName());
        assertEquals(bo1.getNtTermIdentifier(), bo2.getNtTermIdentifier());
        assertEquals(bo1.getPreferredName(), bo2.getPreferredName());
        assertEquals(bo1.getStatusCode(), bo2.getStatusCode());
        assertEquals(bo1.getStatusDateRangeLow(), bo2.getStatusDateRangeLow());
    }
    
    @Test
    public void getTest() throws Exception {
        DiseaseDTO dto = remote.get(ii);
        assertFalse(PAUtil.isIiNull(dto.getIdentifier()));
    }

    @Test
    public void createTest() throws Exception {
        Disease bo = DiseaseTest.createDiseaseObj("crud");
        assertNull(bo.getId());
        DiseaseDTO dto = bean.convertFromDomainToDto(bo);
        DiseaseDTO resultDto = remote.create(dto);
        Disease resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertNotNull(resultBo.getId());
    }

    @Test
    public void updateTest() throws Exception {
        DiseaseDTO dto = remote.get(ii);
        Disease bo = bean.convertFromDtoToDomain(dto);
        assertFalse("new name".equals(bo.getPreferredName()));
        bo.setPreferredName("new name");
        dto = bean.convertFromDomainToDto(bo);
        DiseaseDTO resultDto = remote.update(dto);
        Disease resultBo = bean.convertFromDtoToDomain(resultDto);
        this.compareDataAttributes(bo, resultBo);
        assertTrue("new name".equals(resultBo.getPreferredName()));
    }
    
    @Test 
    public void deleteTest() throws Exception {
        remote.delete(ii);
        try {
            remote.get(ii);
        } catch (PAException e) {
            // expected behavior
            return;
        }
        fail();
    }
    @Test
    public void searchTest() throws Exception {
        DiseaseDTO searchCriteria = new DiseaseDTO();
        searchCriteria.setPreferredName(StConverter.convertToSt("Toe"));
        List<DiseaseDTO> r = bean.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("xToe"));
        r = bean.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setPreferredName(StConverter.convertToSt("Piggy"));
        r = bean.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setPreferredName(null);
        try {
            r = bean.search(searchCriteria);
            fail("Service should throw PAException when searching w/o name.  ");
        } catch(PAException e) {
            // expected behavior
        }
    }
}
