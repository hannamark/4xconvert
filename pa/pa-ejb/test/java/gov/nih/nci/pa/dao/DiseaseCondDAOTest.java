package gov.nih.nci.pa.dao;

import static org.junit.Assert.*;

import java.util.List;

import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.test.util.TestSchema;
import gov.nih.nci.pa.util.HibernateUtil;

import org.junit.Before;
import org.junit.Test;

public class DiseaseCondDAOTest {
    
    DiseaseCondDAO diseaseCondDAO = new DiseaseCondDAO();

    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();        
    }
    
    @Test
    public void getDiseaseCondition() throws Exception{
        List list =  diseaseCondDAO.getDiseaseCondition(1L);
       assertEquals("1AV55FGSY44", ((DiseaseConditionDTO)list.get(0)).getDiseaseCode());
    }
}
