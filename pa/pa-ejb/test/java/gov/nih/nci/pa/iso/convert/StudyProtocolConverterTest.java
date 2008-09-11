package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

/**
 * test class for studyprotocol converter.
 * @author NAmiruddin
 *
 */
public class StudyProtocolConverterTest  {
    
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
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(sp);
        assertStudyProtocol(sp , spDTO);
 
        
    }
    
    /**
     * 
     */
    @Test    
    public void convertFromDtoToDomainTest() {
        StudyProtocol create = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(create);
        assertNotNull(create.getId());
        //convert to DTO
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(create);
        StudyProtocol sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO);
        assertStudyProtocol(sp , spDTO);
        
    }

    private void assertStudyProtocol(StudyProtocol sp , StudyProtocolDTO spDTO) {
        assertEquals("Acronym does not match " , sp.getAcronym(), spDTO.getAcronym().getValue());
/*        assertEquals("Allocation code does not match " , 
                sp.getAllocationCode().getCode(), 
                spDTO.getAllocationCode().getCode()); */
        assertEquals("Accrual Reporting Method code does not match " , 
                sp.getAccrualReportingMethodCode().getCode(), 
                spDTO.getAccrualReportingMethodCode().getCode());
        assertEquals("Expanded Access Indicator does not  match " , 
                sp.getExpandedAccessIndicator(), spDTO.getExpandedAccessIndicator().getValue());
        assertEquals("Identifer does not match " , sp.getIdentifier() , spDTO.getIdentifier().getExtension());
        assertEquals("Monitor code does not match " , sp.getMonitorCode().getCode(), spDTO.getMonitorCode().getCode());
        assertEquals("Official Title does not match " , sp.getOfficialTitle() , spDTO.getOfficialTitle().getValue());
        assertEquals("Phase code does not match " , sp.getPhaseCode().getCode(), spDTO.getPhaseCode().getCode());
        assertEquals("PrimaryCompletionDate  does not match " , 
                sp.getPrimaryCompletionDate(), TsConverter.convertToTimestamp(spDTO.getPrimaryCompletionDate()));
        assertEquals("PrimaryCompletionDateTypeCode  does not match " , 
                sp.getPrimaryCompletionDateTypeCode().getCode(), 
                spDTO.getPrimaryCompletionDateTypeCode().getCode());
        assertEquals("StartDate Does not match ", sp.getStartDate() , TsConverter.convertToTimestamp(spDTO.getStartDate()));  
        assertEquals("StartDate Type code Does not match ", sp.getStartDateTypeCode().getCode() , 
                spDTO.getStartDateTypeCode().getCode());  
//        assertEquals("Status Date Does not match ", sp.getStatusDate() , spDTO.getStatusDate());  
        
    }
}
