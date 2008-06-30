package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.enums.StudyPhaseCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.test.util.TestSchema;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProtocolDAOTest {
    
    ProtocolDAO dao = new ProtocolDAO();
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        TestSchema.primeData();        
    }
    
    @Test
    public void testGetByProtocolId() {
        Protocol prot = new Protocol();
        prot.setLongTitleText("testGetById");
        assertNull(prot.getId());
        
        TestSchema.addUpdObject(prot);
        Long newPid = prot.getId();
        HibernateUtil.getCurrentSession().flush();
        assertNotNull(newPid);
        
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        sc.setProtocolId(newPid);
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc);            
            assertEquals(newPid, results.get(0).getProtocolId());
            assertEquals("testGetById", results.get(0).getLongTitleText());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }
    
    @Test
    public void testGetByNciIdentifier() {
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc); 
            assertEquals(2, results.size());
            
            sc.setNciIdentifier("nciIdentifier.1");
            results = dao.getProtocol(sc); 
            assertEquals(1, results.size());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }

    @Test
    public void testGetByLongTitleText() {
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc); 
            assertEquals(2, results.size());
            
            sc.setLongTitleText("longTitleText.0");
            results = dao.getProtocol(sc); 
            assertEquals(1, results.size());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }
    
    @Test
    public void testGetByLeadOrganizationId() {
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc); 
            assertEquals(2, results.size());
            
            sc.setLeadOrganizationId(
                    TestSchema.healthcareSiteIds.get(0).toString());
            results = dao.getProtocol(sc); 
            assertEquals(1, results.size());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }

    @Test
    public void testGetByLeadOrganizationProtocolId() {
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc); 
            assertEquals(2, results.size());
            
            sc.setLeadOrganizationProtocolId("leadOrganizationProtocolId.0");
            results = dao.getProtocol(sc); 
            assertEquals(1, results.size());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }

    @Test
    public void testGetByStudyPhaseCode() {
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc); 
            assertEquals(2, results.size());
            
            sc.setStudyPhaseCode(StudyPhaseCode.PHASE2.getCode());
            results = dao.getProtocol(sc); 
            assertEquals(1, results.size());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }

    @Test
    public void testGetByStudyTypeCode() {
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        
        try {
            List<ProtocolDTO> results = dao.getProtocol(sc); 
            assertEquals(2, results.size());
            
            sc.setStudyTypeCode(StudyTypeCode.TREATMENT);
            results = dao.getProtocol(sc); 
            assertEquals(1, results.size());
        } catch (PAException e) {
            fail(e.getMessage());  
        }
    }

 
}
  