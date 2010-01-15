package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

public class DoseFrequencyTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
    }
    
   @Test
    public void createDoseFrequencyTest() {
        try {
            DoseFrequency create = createDoseFrequencyObj();
            TestSchema.addUpdObject(create);
            assertNotNull(create.getId());
            Serializable id = create.getId();
            DoseFrequency saved = (DoseFrequency) HibernateUtil.getCurrentSession().load(DoseFrequency.class, id);
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
       
    public static DoseFrequency createDoseFrequencyObj() {
        DoseFrequency df = new DoseFrequency();
        df.setCode("Code");
        df.setCodingSystem("codingSystem");
        df.setCodingSystemName("codingSystemName");
        df.setConceptId("conceptId");
        df.setDescription("description");
        df.setDisplayName("displayName");
        df.setPublicId("publicId");
        return df;
    }


}
