package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyCheckoutTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    @Test
    public void createStudyCheckoutTest() {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyCheckout create = createStudyCheckoutobj(sp);
        Session session  = HibernateUtil.getCurrentSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spSaved);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);
        
        StudyCheckout saved = new StudyCheckout();
        saved = (StudyCheckout) session.load(StudyCheckout.class, id);
        assertEquals("User Identifier does not match " , create.getUserIdentifier() , 
                saved.getUserIdentifier());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    }
    /**
     * 
     * @param sp StudyProtocol
     * @return Document
     */
    public static StudyCheckout createStudyCheckoutobj(StudyProtocol sp) {
        StudyCheckout create = new StudyCheckout();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setUserIdentifier("Abstractor");
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
