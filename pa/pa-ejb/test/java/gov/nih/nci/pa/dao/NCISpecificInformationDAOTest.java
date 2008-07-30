package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//import java.util.List;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
//import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class NCISpecificInformationDAOTest {
	private static final Logger LOG  = Logger.getLogger(NCISpecificInformationDAOTest.class);
    /**
     * 
     * @throws Exception Exception
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

        	nciDAO.updateNCISpecificInformation(nciData);
            nciSpecificInformationDTO = nciDAO.getNCISpecificInformation(studyProtocolId);
        	LOG.info("nciSpecificInformationDTO.getMonitorCode() is:" 
      		      + nciSpecificInformationDTO.getMonitorCode());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     
/*
       assertEquals("MonitorCode does not match " , nciSpecificInformationDTO.getMonitorCode().getName() , 
        		nciData.getMonitorCode()    );
        assertEquals("ReportingDataSetMethodCode does not match " , nciSpecificInformationDTO.getReportingDataSetMethodCode().getName() , 
        		nciData.getReportingDataSetMethodCode()  );
        assertEquals("SummaryFourFundingCategoryCode does not match " , nciSpecificInformationDTO.getReportingDataSetMethodCode().getName() , 
        		nciData.getSummaryFourFundingCategoryCode() );
*/
    }
}
