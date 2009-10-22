package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


public class StudyInboxTest {
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
    public void createStudyInboxTest() {
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyInbox create = createStudyInboxobj(sp);
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
        
        StudyInbox saved = new StudyInbox();
        saved = (StudyInbox) session.load(StudyInbox.class, id);
        assertEquals("idName " , create.getComments() , 
                saved.getComments());
        assertEquals("openDate " , create.getOpenDate() , 
                saved.getOpenDate());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    } /**
     * 
     * @param sp StudyProtocol
     * @return StudyInbox
     */
    public static StudyInbox createStudyInboxobj(StudyProtocol sp) {
    	StudyInbox create = new StudyInbox();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setComments("idName");
        create.setOpenDate(now);
        create.setCloseDate(now);
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
