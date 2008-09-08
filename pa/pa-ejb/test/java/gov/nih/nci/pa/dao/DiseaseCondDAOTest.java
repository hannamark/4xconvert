package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.test.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * .
 *
 */
public class DiseaseCondDAOTest {
    
    DiseaseCondDAO diseaseCondDAO = new DiseaseCondDAO();

    /**
     * 
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        //TestSchema.reset1();
        //TestSchema.primeData();        
    }
    
    @Test
    public void getDiseaseCondition() throws Exception{
        assertEquals("","");
       // List list =  diseaseCondDAO.getDiseaseCondition(1L);
        //assertEquals("1AV55FGSY44", ((DiseaseConditionDTO) list.get(0)).getDiseaseCode());
    }
}
