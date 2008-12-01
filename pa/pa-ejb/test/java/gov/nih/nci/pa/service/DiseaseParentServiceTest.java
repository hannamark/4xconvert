package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.DiseaseParent;
import gov.nih.nci.pa.domain.DiseaseParentTest;
import gov.nih.nci.pa.domain.DiseaseTest;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class DiseaseParentServiceTest {
    private DiseaseParentServiceBean bean = new DiseaseParentServiceBean();
    private DiseaseParentServiceRemote remote = bean;
    private DiseaseServiceBean diseaseBean = new DiseaseServiceBean();
    private Ii dIi;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        dIi = IiConverter.convertToIi(TestSchema.diseaseIds.get(0));
     }
    
    private void compareDataAttributes(DiseaseParent bo1, DiseaseParent bo2) {
        assertEquals(bo1.getParentDiseaseCode(), bo2.getParentDiseaseCode());
        assertEquals(bo1.getStatusCode(), bo2.getStatusCode());
        assertEquals(bo1.getStatusDateRangeLow(), bo2.getStatusDateRangeLow());
    }
    
    @Test
    public void getTest() throws Exception {
        List<DiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(PAUtil.isIiNull(ii));
        DiseaseParentDTO resultDto = bean.get(ii);
        assertFalse(PAUtil.isIiNull(resultDto.getIdentifier()));
    }

    @Test
    public void getTreeTest() throws Exception {
        Long childId = IiConverter.convertToLong(dIi);
        List<DiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        Ii parentDiseaseIi = dtoList.get(0).getParentDiseaseIdentifier();
        List<DiseaseParentDTO> dtoListChildren = bean.getByParentDisease(parentDiseaseIi);
        List<Long> childIdList = new ArrayList<Long>();
        for (DiseaseParentDTO d : dtoListChildren) {
            childIdList.add(IiConverter.convertToLong(d.getDiseaseIdentifier()));
        }
        assertTrue(childIdList.contains(childId));
    }

    @Test
    public void createTest() throws Exception {
        DiseaseDTO tn = diseaseBean.create(diseaseBean.convertFromDomainToDto(DiseaseTest.createDiseaseObj("toenail cancer")));
        Disease toeNail = new Disease();
        toeNail.setId(IiConverter.convertToLong(tn.getIdentifier()));
        Disease toe = diseaseBean.convertFromDtoToDomain(diseaseBean.get(dIi));
        
        DiseaseParent bo = DiseaseParentTest.createDiseaseParentObj(toeNail, toe);
        assertNull(bo.getId());
        DiseaseParentDTO resultDto = remote.create(bean.convertFromDomainToDto(bo));
        DiseaseParent resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertNotNull(resultBo.getId());
    }

    @Test
    public void updateTest() throws Exception {
        List<DiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        DiseaseParentDTO dto = dtoList.get(0);
        DiseaseParent bo = bean.convertFromDtoToDomain(dto);
        assertFalse(ActiveInactiveCode.INACTIVE.equals(bo.getStatusCode()));
        bo.setStatusCode(ActiveInactiveCode.INACTIVE);
        
        DiseaseParentDTO resultDto = remote.update(bean.convertFromDomainToDto(bo));
        DiseaseParent resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertEquals(bo.getId(), resultBo.getId());
    }
    
    @Test 
    public void deleteTest() throws Exception {
        List<DiseaseParentDTO> dtoList = bean.getByChildDisease(dIi);
        assertTrue(dtoList.size() > 0);
        int oldSize = dtoList.size();
        Ii ii = dtoList.get(0).getIdentifier();
        remote.delete(ii);
        dtoList = bean.getByChildDisease(dIi);
        assertEquals(oldSize - 1, dtoList.size());
    }
}
