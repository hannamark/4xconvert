package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudyResourcingTest;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

public class StudyResourcingServiceBeanTest {
    
    private StudyResourcingServiceRemote remoteEjb = new StudyResourcingServiceBean();
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    @Test
    public void getTest() throws Exception {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        
        StudyResourcing sr =  StudyResourcingTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());
        
        StudyResourcingDTO srDTO = remoteEjb.getsummary4ReportedResource(IiConverter.convertToIi(sp.getId()));
        assertNotNull(srDTO);
        StudyResourcingDTO srDTO2 = remoteEjb.getStudyResourceByID(srDTO.getIdentifier());
        assertNotNull(srDTO2);
        remoteEjb.deleteStudyResourceByID(srDTO2);
        
    }
    
    @Test
    public void createStudyResourcingTest() throws Exception {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        
        StudyResourcing sr =  StudyResourcingTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());
        
        StudyResourcingDTO srDTO1 = new StudyResourcingDTO();
        srDTO1.setSerialNumber(IntConverter.convertToInt("123"));
        srDTO1.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
        
        StudyResourcingDTO srDTO2 = remoteEjb.createStudyResourcing(srDTO1);
        assertNotNull(srDTO2);
        
        //assertEquals (srDTO1.getFundingMechanismCode().getCode() , srDTO2.getFundingMechanismCode().getCode());
        assertEquals (srDTO1.getSerialNumber().getValue(), srDTO2.getSerialNumber().getValue());
        
    }

    @Test
    public void updateStudyResourcingTest() throws Exception {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        assertNotNull(sp.getId());
        
        StudyResourcing sr =  StudyResourcingTest.createStudyResourcingObj(sp);
        TestSchema.addUpdObject(sr);
        assertNotNull(sr.getId());
        
        StudyResourcingDTO srDTO1 = new StudyResourcingDTO();
        srDTO1.setSerialNumber(IntConverter.convertToInt("123"));
        srDTO1.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
        
        StudyResourcingDTO srDTO2 = remoteEjb.createStudyResourcing(srDTO1);
        assertNotNull(srDTO2);
        assertEquals (srDTO1.getSerialNumber().getValue(), srDTO2.getSerialNumber().getValue());

        srDTO2.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
        srDTO2.setSerialNumber(IntConverter.convertToInt("123123"));
        StudyResourcingDTO srDTO3 = remoteEjb.updateStudyResourcing(srDTO2);
        assertNotNull(srDTO3);
        assertEquals (srDTO3.getSerialNumber().getValue().toString(), "123123");

    
    }

}
