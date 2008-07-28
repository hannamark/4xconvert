package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.enums.AssigningAuthorityCode;
import gov.nih.nci.pa.test.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class PersonIdentificationTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }    
    /**
     * 
     */
    @Test
    public void createPersonIdentificationTest() {
        
        Person p = PersonTest.createPersonObj();
        Session session  = TestSchema.getSession();
        TestSchema.addUpdObject(p);
        PersonIdentification create = createPersonIdentificationObj(p);
        TestSchema.addUpdObject(create);
        Serializable cid = create.getId();
        assertNotNull(cid);
        
        PersonIdentification saved = (PersonIdentification) session.load(PersonIdentification.class, cid);
        assertNotNull(saved);
        
        
        assertEquals("Assigning authority does not match " , create.getAssigningAuthorityCode() , 
                saved.getAssigningAuthorityCode());
        assertEquals("Indetifer value not match " , create.getIdentifierValue() , 
                saved.getIdentifierValue());
        assertEquals("Primary Indidator does not match" , create.getPrimaryIndicator() , 
                saved.getPrimaryIndicator());

    }
    
    public static PersonIdentification createPersonIdentificationObj(Person p) {
        PersonIdentification pi = new PersonIdentification();
        pi.setAssigningAuthorityCode(AssigningAuthorityCode.PO);
        pi.setIdentifierValue("P0001");
        pi.setPrimaryIndicator(true);
        pi.setPerson(p);
        return pi;
    }
    

}
