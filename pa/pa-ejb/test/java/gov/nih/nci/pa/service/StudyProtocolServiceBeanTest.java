package gov.nih.nci.pa.service;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import gov.nih.nci.coppa.iso.Ii;
//import gov.nih.nci.pa.domain.StudyProtocol;
//import gov.nih.nci.pa.domain.StudyProtocolTest;
//import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.test.util.TestSchema;
//import gov.nih.nci.pa.util.IsoConverter;
//import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyProtocolServiceBeanTest {

    //StudyProtocolServiceBean sps = new StudyProtocolServiceBean();
    
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
    public void getStudyProtocol() {
      //@todo: include coppa iso.jar for testing packing
        /*
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        Session session  = TestSchema.getSession();
        StudyProtocolDTO spDTO = null;
        
        
        try {
            
            TestSchema.addUpdObject(sp);
            Long id = sp.getId();
            assertNotNull("id cannot be null" , id);
            Ii ii = IsoConverter.convertIdToIsoIi(id);
            spDTO = sps.getStudyProtocol(ii);
            assertNotNull("StudyProtocolDTO cannot be null",spDTO);
            assertEquals("Monitor code does not match ", sp.getMonitorCode().getCode()
                    , spDTO.getMonitorCode().getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        assertNotNull("to be changed");
        
    }

}
