package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

public class DoseFormTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
    }
    
   @Test
    public void createDoseFormTest() {
        try {
            DoseForm create = createDoseFormObj();
            TestSchema.addUpdObject(create);
            assertNotNull(create.getId());
            Serializable id = create.getId();
            DoseForm saved = (DoseForm) HibernateUtil.getCurrentSession().load(DoseForm.class, id);
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
       
    public static DoseForm createDoseFormObj() {
        DoseForm df = new DoseForm();
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
