package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class PersonTest {
    

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
    public void createPesrsonTest() {
        Person create = createPersonObj();
        Session session  = TestSchema.getSession();
        TestSchema.addUpdObject(create);
        Serializable cid = create.getId();
        assertNotNull(cid);
        Person saved = (Person) session.load(Person.class, cid);
        assertEquals("First Name does not match " , create.getFirstName() , saved.getFirstName());
        assertEquals("Last Name does not match " , create.getLastName() , saved.getLastName());
        assertEquals("Middle Name does not match " , create.getMiddleName() , saved.getMiddleName());
        assertEquals("Id does not match " , create.getId() , saved.getId());
    }
    
    /**
     * 
     * @return Person p
     */
    public static Person createPersonObj() {
        Person p = new Person();
        p.setFirstName("Naveen");
        p.setLastName("Amiruddin");
        p.setMiddleName("S");
        return p;
    }

}
