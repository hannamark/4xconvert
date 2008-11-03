package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
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
        //TestSchema.reset();
               
    }    

    /**
     * 
     */
    @Test
    public void convertFromDomainToDTOTest() {
        Session session  = TestSchema.getSession();

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        session.save(sp);
        //TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(sp);
        assertStudyProtocol(sp , spDTO);
 
        
    }

    @Test
    public void convertFromDomainToDTOTest1() {
        Session session  = TestSchema.getSession();

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        session.save(sp);
        //TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(sp , new StudyProtocolDTO());
        assertStudyProtocol(sp , spDTO);
 
        
    }
    
    /**
     * 
     */
    @Test    
    public void convertFromDtoToDomainTest() {
        Session session  = TestSchema.getSession();
        StudyProtocol create = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        //TestSchema.addUpdObject(create);
        session.save(create);
        assertNotNull(create.getId());
        //convert to DTO
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(create);
        StudyProtocol sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO);
        assertStudyProtocol(sp , spDTO);
        
    }

    @Test    
    public void convertFromDtoToDomainTest1() {
        Session session  = TestSchema.getSession();
        StudyProtocol create = StudyProtocolTest.createStudyProtocolObj(new StudyProtocol());
        //TestSchema.addUpdObject(create);
        session.save(create);
        assertNotNull(create.getId());
        //convert to DTO
        StudyProtocolDTO spDTO = StudyProtocolConverter.convertFromDomainToDTO(create);
        StudyProtocol sp = StudyProtocolConverter.convertFromDTOToDomain(spDTO , new StudyProtocol());
        assertStudyProtocol(sp , spDTO);
        
    }
    
    /**
     * 
     * @param sp sp
     * @param spDTO spDTO
     */
    public void assertStudyProtocol(StudyProtocol sp , StudyProtocolDTO spDTO) {
        assertEquals(sp.getAcronym(), spDTO.getAcronym().getValue());
        assertEquals(sp.getAccrualReportingMethodCode().getCode(), spDTO.getAccrualReportingMethodCode().getCode());
        assertEquals(sp.getIdentifier() , spDTO.getAssignedIdentifier().getExtension());
        assertEquals(sp.getDataMonitoringCommitteeAppointedIndicator(),
                spDTO.getDataMonitoringCommitteeAppointedIndicator().getValue());
        assertEquals(sp.getDelayedpostingIndicator(), spDTO.getDelayedpostingIndicator().getValue());
        assertEquals(sp.getExpandedAccessIndicator(), spDTO.getExpandedAccessIndicator().getValue());
        assertEquals(sp.getFdaRegulatedIndicator(), spDTO.getFdaRegulatedIndicator().getValue());
        assertEquals(sp.getId().toString() , spDTO.getIdentifier().getExtension());
        assertEquals(sp.getOfficialTitle() , spDTO.getOfficialTitle().getValue());
        assertEquals(sp.getPhaseCode().getCode(), spDTO.getPhaseCode().getCode());
        assertEquals(sp.getPhaseOtherText(), spDTO.getPhaseOtherText().getValue());
        assertEquals(sp.getPrimaryCompletionDate(), TsConverter.convertToTimestamp(spDTO.getPrimaryCompletionDate()));
        assertEquals(sp.getPrimaryCompletionDateTypeCode().getCode(), 
                spDTO.getPrimaryCompletionDateTypeCode().getCode());
        assertEquals(sp.getPrimaryPurposeCode().getCode(), spDTO.getPrimaryPurposeCode().getCode());
        assertEquals(sp.getPrimaryPurposeOtherText(), spDTO.getPrimaryPurposeOtherText().getValue());
        assertEquals(sp.getPublicDescription(), spDTO.getPublicDescription().getValue());
        assertEquals(sp.getPublicTitle(), spDTO.getPublicTitle().getValue());
        assertEquals(sp.getRecordVerificationDate() , 
                TsConverter.convertToTimestamp(spDTO.getRecordVerificationDate()));
        assertEquals(sp.getScientificDescription(), spDTO.getScientificDescription().getValue());
        assertEquals(sp.getSection801Indicator(), spDTO.getSection801Indicator().getValue());
        assertEquals(sp.getStartDate() , TsConverter.convertToTimestamp(spDTO.getStartDate()));  
        assertEquals(sp.getStartDateTypeCode().getCode() , spDTO.getStartDateTypeCode().getCode());  
        
    }
}
