package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.domain.StudyDisease;
import gov.nih.nci.pa.domain.StudyDiseaseTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
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
public class StudyDiseaseServiceTest {
    private StudyDiseaseServiceBean bean = new StudyDiseaseServiceBean();
    private StudyDiseaseServiceRemote remote = bean;
    private Ii spIi;
    private Ii dIi;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        spIi = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
        dIi = IiConverter.convertToIi(TestSchema.diseaseIds.get(1));
    }

    private void compareDataAttributes(StudyDisease bo1, StudyDisease bo2) {
        assertEquals(bo1.getDisease().getId(), bo2.getDisease().getId());
        assertEquals(bo1.getLeadDiseaseIndicator(), bo2.getLeadDiseaseIndicator());
        assertEquals(bo1.getStudyProtocol().getId(), bo2.getStudyProtocol().getId());
    }
    
    @Test
    public void getTest() throws Exception {
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        Ii ii = dtoList.get(0).getIdentifier();
        assertFalse(PAUtil.isIiNull(ii));
        StudyDiseaseDTO resultDto = bean.get(ii);
        assertFalse(PAUtil.isIiNull(resultDto.getIdentifier()));
    }

    @Test
    public void createTest() throws Exception {
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();
        
        Disease disease = new Disease();
        disease.setId(IiConverter.convertToLong(dIi)); 
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(spIi));
        StudyDisease bo = StudyDiseaseTest.createStudyDiseaseObj(studyProtocol, disease);
        assertNull(bo.getId());
        StudyDisease resultBo = bean.convertFromDtoToDomain(remote.create(bean.convertFromDomainToDto(bo)));
        compareDataAttributes(bo, resultBo);
        assertNotNull(resultBo.getId());
        dtoList = bean.getByStudyProtocol(spIi);
        assertEquals(oldSize + 1, dtoList.size());
    }
    
    @Test
    public void updateTest() throws Exception {
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        assertTrue(dtoList.size() > 0);
        StudyDiseaseDTO dto = dtoList.get(0);
        StudyDisease bo = bean.convertFromDtoToDomain(dto);
        assertFalse(bo.getLeadDiseaseIndicator());
        bo.setLeadDiseaseIndicator(true);
        StudyDiseaseDTO resultDto = remote.update(bean.convertFromDomainToDto(bo));
        StudyDisease resultBo = bean.convertFromDtoToDomain(resultDto);
        compareDataAttributes(bo, resultBo);
        assertEquals(bo.getId(), resultBo.getId());
    }
    
    @Test
    public void deleteTest() throws Exception {
        List<StudyDiseaseDTO> dtoList = bean.getByStudyProtocol(spIi);
        int oldSize = dtoList.size();
        Ii ii = dtoList.get(0).getIdentifier();
        remote.delete(ii);
        dtoList = bean.getByStudyProtocol(spIi);
        assertEquals(oldSize - 1, dtoList.size());
    }

}
