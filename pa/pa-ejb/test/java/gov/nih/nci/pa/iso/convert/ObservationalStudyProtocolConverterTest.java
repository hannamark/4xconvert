package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.ObservationalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class ObservationalStudyProtocolConverterTest   {

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
        Session session  = TestSchema.getSession();

        ObservationalStudyProtocol osp = (ObservationalStudyProtocol) 
            StudyProtocolTest.createStudyProtocolObj(new ObservationalStudyProtocol());
        
        osp.setBiospecimenDescription("BiospecimenDescription");
        osp.setBiospecimenRetentionCode(BiospecimenRetentionCode.NONE);
        osp.setNumberOfGroups(Integer.valueOf(1));
        osp.setSamplingMethodCode(SamplingMethodCode.CLUSTER_SAMPLING);
        osp.setStudyModelCode(StudyModelCode.CASE_CONTROL);
        osp.setStudyModelOtherText("studyModelOtherText");
        osp.setTimePerspectiveCode(TimePerspectiveCode.OTHER);
        osp.setTimePerspectiveOtherText("timePerspectiveOtherText");
        session.save(osp);
        //TestSchema.addUpdObject(osp);
        assertNotNull(osp.getId());
        ObservationalStudyProtocolDTO ospDTO = (ObservationalStudyProtocolDTO) 
            ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);
        
        assertObservationalStudyProtocol(osp , ospDTO);
 
        
    }

    /**
     * 
     */
    @Test
    public void convertFromDTOToDomainTest() {
        Session session  = TestSchema.getSession();

        ObservationalStudyProtocol osp = (ObservationalStudyProtocol) 
            StudyProtocolTest.createStudyProtocolObj(new ObservationalStudyProtocol());
        osp.setBiospecimenDescription("BiospecimenDescription");
        osp.setBiospecimenRetentionCode(BiospecimenRetentionCode.NONE);
        osp.setNumberOfGroups(Integer.valueOf(1));
        osp.setSamplingMethodCode(SamplingMethodCode.CLUSTER_SAMPLING);
        osp.setStudyModelCode(StudyModelCode.CASE_CONTROL);
        osp.setStudyModelOtherText("studyModelOtherText");
        osp.setTimePerspectiveCode(TimePerspectiveCode.OTHER);
        osp.setTimePerspectiveOtherText("timePerspectiveOtherText");

        session.save(osp);
        //TestSchema.addUpdObject(osp);
        assertNotNull(osp.getId());
        ObservationalStudyProtocolDTO ospDTO = (ObservationalStudyProtocolDTO) 
            ObservationalStudyProtocolConverter.convertFromDomainToDTO(osp);
        osp = ObservationalStudyProtocolConverter.convertFromDTOToDomain(ospDTO);

        assertObservationalStudyProtocol(osp , ospDTO);
    }
    
    /**
     * 
     * @param osp osp
     * @param ospDTO ospDTO
     */
    public void assertObservationalStudyProtocol(
            ObservationalStudyProtocol osp , ObservationalStudyProtocolDTO ospDTO) {
        new StudyProtocolConverterTest().assertStudyProtocol(osp , ospDTO);
        assertEquals(osp.getBiospecimenDescription() , ospDTO.getBiospecimenDescription().getValue());
        assertEquals(osp.getBiospecimenRetentionCode().getCode() , ospDTO.getBiospecimenRetentionCode().getCode());
        assertEquals(osp.getNumberOfGroups() , ospDTO.getNumberOfGroups().getValue());
        assertEquals(osp.getSamplingMethodCode().getCode() , ospDTO.getSamplingMethodCode().getCode());
        assertEquals(osp.getStudyModelCode().getCode() , ospDTO.getStudyModelCode().getCode());
        assertEquals(osp.getStudyModelOtherText(), ospDTO.getStudyModelOtherText().getValue());
        assertEquals(osp.getTimePerspectiveCode().getCode() , ospDTO.getTimePerspectiveCode().getCode());
        assertEquals(osp.getTimePerspectiveOtherText() , ospDTO.getTimePerspectiveOtherText().getValue());
    }

}
