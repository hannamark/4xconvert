package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

public class UnitOfMeasurementTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
    }
    
   @Test
    public void createUnitOfMeasurementTest() {
        try {
            UnitOfMeasurement create = createUnitOfMeasurementObj();
            TestSchema.addUpdObject(create);
            assertNotNull(create.getId());
            Serializable id = create.getId();
            UnitOfMeasurement saved = (UnitOfMeasurement) HibernateUtil.getCurrentSession().load(UnitOfMeasurement.class, id);
            assertEquals(create.getCode() , saved.getCode());
            assertEquals(create.getCodingSystem() , saved.getCodingSystem());
            assertEquals(create.getCodingSystemName() , saved.getCodingSystemName());
            assertEquals(create.getConceptId() , saved.getConceptId());
            assertEquals(create.getDescription() , saved.getDescription());
            assertEquals(create.getDisplayName() , saved.getDisplayName());
            assertEquals(create.getId() , saved.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       
    public static UnitOfMeasurement createUnitOfMeasurementObj() {
        UnitOfMeasurement uof = new UnitOfMeasurement();
        uof.setCode("Code");
        uof.setCodingSystem("codingSystem");
        uof.setCodingSystemName("codingSystemName");
        uof.setConceptId("conceptId");
        uof.setDescription("description");
        uof.setDisplayName("displayName");
        uof.setPublicId("publicId");
        return uof;
    }


}
