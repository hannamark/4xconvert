package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.test.util.TestSchema;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author NAmiruddin
 *
 */
public class PersonDAOTest {
    
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
    public void createPersonDAOTest() {
        
        PersonDAO p = new PersonDAO();
//        p.getAllPrincipalInvestigators();
   
        assertNotNull(p);
    }
    

}
