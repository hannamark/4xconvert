package gov.nih.nci.pa.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.dao.NCISpecificInformationDAO;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author gnaveh
 *
 */
public class NCISpecificInformationServiceImplTest {
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
    public void getNCISpecificInformationTest() {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long studyProtocolId = sp.getId();
        
        
        NCISpecificInformationDAO nciDAO = new NCISpecificInformationDAO();
        NCISpecificInformationDTO nciSpecificInformationDTO = null;
        try {
        	nciSpecificInformationDTO = nciDAO.getNCISpecificInformation(studyProtocolId);
        } catch (PAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(nciSpecificInformationDTO);        
    }
    
    /**
     * 
     */
    @Test
    public void updateNCISpecificInformationTest() {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long studyProtocolId = sp.getId();
        
        
        NCISpecificInformationData nciData = new NCISpecificInformationData();
        NCISpecificInformationDAO nciDAO = new NCISpecificInformationDAO();
        NCISpecificInformationDTO nciSpecificInformationDTO = null;
        try {
        	nciData.setStudyProtocolID(studyProtocolId.toString());
        	nciData.setMonitorCode("CTEP");
        	nciData.setReportingDataSetMethodCode("ABBREVIATED");
        	nciData.setSummaryFourFundingCategoryCode("EXTERNALLY_PEER_REVIEWED");

        	nciSpecificInformationDTO = nciDAO.updateNCISpecificInformation(nciData);            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
        assertEquals("studyProtocolId does not match " , studyProtocolId.toString() , 
                 nciData.getStudyProtocolID()    );
        assertEquals("MonitorCode does not match " , nciSpecificInformationDTO.getMonitorCode().getName(), 
                 nciData.getMonitorCode() );
    }    
}
