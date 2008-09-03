package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyProtocolDAOTest {
    
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
    public void getStudyProtocolByCriteriaTest() {
        /*
        List<Object> obj = null;
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        qspc.setOfficialTitle(sp.getOfficialTitle());
        try {
             obj = spDAO.getStudyProtocolByCriteria(qspc);
        } catch (PAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        List<Object> obj = null;
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();

         sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
         id = sp.getId();
        
        try {
            obj = spDAO.getStudyProtocolByCriteria(qspc);
       } catch (PAException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       //System.out.println(obj.size()); 
        assertNotNull(obj);
        
    }

}
