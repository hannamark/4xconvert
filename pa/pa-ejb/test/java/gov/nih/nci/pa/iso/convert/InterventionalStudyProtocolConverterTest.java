package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class InterventionalStudyProtocolConverterTest   {

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
        InterventionalStudyProtocol isp = (InterventionalStudyProtocol) 
            StudyProtocolTest.createStudyProtocolObj(new InterventionalStudyProtocol());
        isp.setAllocationCode(AllocationCode.NON_RANDOMIZED_TRIAL);
        isp.setBlindingSchemaCode(BlindingSchemaCode.DOUBLE_BLIND);
        isp.setDesignConfigurationCode(DesignConfigurationCode.CROSSOVER);
        isp.setNumberOfInterventionGroups(Integer.valueOf(1));
        isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
        isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
        session.save(isp);
        //TestSchema.addUpdObject(sp);
        assertNotNull(isp.getId());
        InterventionalStudyProtocolDTO ispDTO = (InterventionalStudyProtocolDTO) 
            InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
        
        assertInterventionalStudyProtocol(isp , ispDTO);
 
        
    }

    /**
     * 
     */
    @Test
    public void convertFromDTOToDomainTest() {
        Session session  = TestSchema.getSession();

        InterventionalStudyProtocol isp = (InterventionalStudyProtocol) 
            StudyProtocolTest.createStudyProtocolObj(new InterventionalStudyProtocol());
        isp.setAllocationCode(AllocationCode.NON_RANDOMIZED_TRIAL);
        isp.setBlindingSchemaCode(BlindingSchemaCode.DOUBLE_BLIND);
        isp.setDesignConfigurationCode(DesignConfigurationCode.CROSSOVER);
        isp.setNumberOfInterventionGroups(Integer.valueOf(1));
        isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
        isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
        session.save(isp);
        //TestSchema.addUpdObject(sp);
        assertNotNull(isp.getId());
        InterventionalStudyProtocolDTO ispDTO = (InterventionalStudyProtocolDTO) 
            InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
        isp = InterventionalStudyProtocolConverter.convertFromDTOToDomain(ispDTO);
        assertInterventionalStudyProtocol(isp , ispDTO);
    }
    
    /**
     * 
     * @param isp isp
     * @param ispDTO ispDTO
     */
    public void assertInterventionalStudyProtocol(
                InterventionalStudyProtocol isp , InterventionalStudyProtocolDTO ispDTO) {
        new StudyProtocolConverterTest().assertStudyProtocol(isp , ispDTO);
        assertEquals(isp.getAllocationCode().getCode(), ispDTO.getAllocationCode().getCode());
        assertEquals(isp.getBlindingSchemaCode().getCode(), ispDTO.getBlindingSchemaCode().getCode());
        assertEquals(isp.getDesignConfigurationCode().getCode(), ispDTO.getDesignConfigurationCode().getCode());
        assertEquals(isp.getNumberOfInterventionGroups(), ispDTO.getNumberOfInterventionGroups().getValue());
        assertEquals(2,ispDTO.getBlindedRoleCode().getItem().size());
    }

}
