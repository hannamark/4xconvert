package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class OrganizationalContactTest {
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
    public void createOrganizationalContactTest() {
        Session session  = TestSchema.getSession();
        try {
        Transaction t = session.beginTransaction();    
        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = PersonTest.createPersonObj();
        TestSchema.addUpdObject(p);
        
        OrganizationalContact create = createOrganizationalContactObj(o ,  p);
        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(id);
        OrganizationalContact saved = (OrganizationalContact) session.load(OrganizationalContact.class, id);
        assertEquals("Healcare Provider does not match " , create.getId(), saved.getId());
        t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    /**
     * 
     * @param p Person
     * @return HealthCareProvider
     */
    public static  OrganizationalContact createOrganizationalContactObj(Organization o  , Person p) {
        OrganizationalContact oc = new OrganizationalContact();
        oc.setOrganization(o);
        oc.setIdentifier("abc");
        oc.setPerson(p);
        oc.setStatusCode(StatusCode.PENDING);
        return oc;
    }
    
}
