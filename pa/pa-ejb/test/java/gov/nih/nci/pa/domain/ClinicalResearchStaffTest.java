package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class ClinicalResearchStaffTest   {
    
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
    public void createClinicalResearchStaffTest() {
        
        Session session  = TestSchema.getSession();
        Organization o = OrganizationTest.createOrganizationObj();
        //session.save(o);
        TestSchema.addUpdObject(o);
        assertNotNull(o.getId());
        
        Person p = PersonTest.createPersonObj();
        //session.save(p);
        TestSchema.addUpdObject(p);
        assertNotNull(p.getId());
        ClinicalResearchStaff crs = createClinicalResearchStaffObj(o, p);
        try {
        Transaction t = session.beginTransaction();    
        //session.save(crs);
        TestSchema.addUpdObject(crs);
        //t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ClinicalResearchStaff createClinicalResearchStaffObj(Organization o , Person p) {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setOrganization(o);
        crs.setPerson(p);
        crs.setIdentifier("abc");
        crs.setStatusCode(StatusCode.PENDING);
        return  crs;
    }


}
