package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.InterventionalStudyProtocolTest;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.test.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

public class InterventionalStudyProtocolConverterTest {

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
        InterventionalStudyProtocol isp = InterventionalStudyProtocolTest.createInterventionalStudyProtocolObj();
        TestSchema.addUpdObject(isp);
        assertNotNull(isp.getId());
        InterventionalStudyProtocolDTO ispDTO = InterventionalStudyProtocolConverter.convertFromDomainToDTO(isp);
        assertInterventionalStudyProtocol(isp , ispDTO);
    }

    /**
     * 
     */
    @Test    
    public void convertFromDtoToDomainTest() {
        InterventionalStudyProtocol create = InterventionalStudyProtocolTest.createInterventionalStudyProtocolObj();
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        //convert to DTO
        InterventionalStudyProtocolDTO ispDTO = InterventionalStudyProtocolConverter.convertFromDomainToDTO(create);
        InterventionalStudyProtocol isp = InterventionalStudyProtocolConverter.convertFromDTOToDomain(ispDTO);
        assertInterventionalStudyProtocol(isp , ispDTO);
        
    }
    

    private void assertInterventionalStudyProtocol(InterventionalStudyProtocol sp , InterventionalStudyProtocolDTO spDTO) {
        assertEquals("Acronym does not match " , sp.getAcronym(), spDTO.getAcronym().getValue());
        assertEquals("Allocation code does not match " , 
                sp.getAllocationCode().getCode(), 
                spDTO.getAllocationCode().getCode()); 
        assertEquals("Accrual Reporting Method code does not match " , 
                sp.getAccrualReportingMethodCode().getCode(), 
                spDTO.getAccrualReportingMethodCode().getCode());
        assertEquals("Expanded Access Indicator does not  match " , 
                sp.getExpandedAccessIndicator(), spDTO.getExpandedAccessIndicator().getValue());
        assertEquals("Identifer does not match " , sp.getIdentifier() , spDTO.getIdentifier().getExtension());
        assertEquals("Monitor code does not match " , sp.getMonitorCode().getCode(), spDTO.getMonitorCode().getCode());
        assertEquals("Official Title does not match " , sp.getOfficialTitle() , spDTO.getOfficialTitle().getValue());
        assertEquals("Phase code does not match " , sp.getPhaseCode().getCode(), spDTO.getPhaseCode().getCode());
//        assertEquals("PrimaryCompletionDate  does not match " , 
//                sp.getPrimaryCompletionDate(), spDTO.getPrimaryCompletionDate());
        assertEquals("PrimaryCompletionDateTypeCode  does not match " , 
                sp.getPrimaryCompletionDateTypeCode().getCode(), 
                spDTO.getPrimaryCompletionDateTypeCode().getCode());
//        assertEquals("StartDate Does not match ", sp.getStartDate() , spDTO.getStartDate());  
        assertEquals("StartDate Type code Does not match ", sp.getStartDateTypeCode().getCode() , 
                spDTO.getStartDateTypeCode().getCode());  
//        assertEquals("Status Date Does not match ", sp.getStatusDate() , spDTO.getStatusDate());
        assertEquals("StartDate Type code Does not match ", sp.getStartDateTypeCode().getCode() , 
                spDTO.getStartDateTypeCode().getCode());  
        assertEquals("delayedpostingIndicator  Does not match ", sp.getDelayedpostingIndicator() , 
                spDTO.getDelayedpostingIndicator().getValue());  
        assertEquals("fdaRegulatedIndicator  Does not match ", sp.getFdaRegulatedIndicator() , 
                spDTO.getFdaRegulatedIndicator().getValue());  
        assertEquals("section801Indicator  Does not match ", sp.getSection801Indicator() , 
                spDTO.getSection801Indicator().getValue());  
        
        
    }

}
