package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.ObservationalStudyProtocolTest;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

public class ObservationalStudyProtocolConverterTest {
    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }    

    /**
     * 
     */
    @Test
    public void convertFromDomainToDTOTest() {
        ObservationalStudyProtocol osp = ObservationalStudyProtocolTest.createObservationalStudyProtocolObj();
        TestSchema.addUpdObject(osp);
        assertNotNull(osp.getId());
        ObservationalStudyProtocolDTO ospDTO = ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);        
        assertObservationalStudyProtocol(osp , ospDTO);
    }    

    /**
     * 
     */
    @Test    
    public void convertFromDtoToDomainTest() {
        ObservationalStudyProtocol create = ObservationalStudyProtocolTest.createObservationalStudyProtocolObj();
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        //convert to DTO
        ObservationalStudyProtocolDTO ospDTO = ObservationalStudyProtocolConverter.convertFromDomainToDTO(create);
        ObservationalStudyProtocol osp = ObservationalStudyProtocolConverter.convertFromDTOToDomain(ospDTO);
        assertObservationalStudyProtocol(osp , ospDTO);
        
    }
    
    private void assertObservationalStudyProtocol(
            ObservationalStudyProtocol osp, ObservationalStudyProtocolDTO ospDTO) {
        assertEquals("StudyModelCode does not match " , osp.getStudyModelCode().getCode(), ospDTO.getStudyModelCode().getCode());
        assertEquals("TimePerspectiveCode does not match " , osp.getTimePerspectiveCode().getCode(), 
                ospDTO.getTimePerspectiveCode().getCode());
        assertEquals("BiospecimenDescription does not match " , 
                osp.getBiospecimenDescription(), ospDTO.getBiospecimenDescription().getValue());
        assertEquals("BiospecimenRetentionCode does not  match " , 
                osp.getBiospecimenRetentionCode().getCode(), ospDTO.getBiospecimenRetentionCode().getCode());
        assertEquals("NumberOfGroups does not match " , osp.getNumberOfGroups() , ospDTO.getNumberOfGroups().getValue());
        assertEquals("Identifer does not match " , osp.getIdentifier() , ospDTO.getAssignedIdentifier().getExtension());
    }
}
