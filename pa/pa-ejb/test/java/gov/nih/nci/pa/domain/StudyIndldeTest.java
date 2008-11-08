package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.NihInstHolderCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class StudyIndldeTest {
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
    public void createStudyIndldeTest() {

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        StudyIndlde create = createStudyIndldeobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);
        
        StudyIndlde saved = new StudyIndlde();
        saved = (StudyIndlde) session.load(StudyIndlde.class, id);
        
        assertEquals("ExpandedAccessStatusCode does not match " , create.getExpandedAccessStatusCode() , 
                saved.getExpandedAccessStatusCode());
        assertEquals("ExpandedAccessIndicator does not match " , create.getExpandedAccessIndicator() , 
                saved.getExpandedAccessIndicator());
        assertEquals("HolderTypeCode does not match " , create.getHolderTypeCode() , 
                saved.getHolderTypeCode());
        assertEquals("NihInstHolderCode does not match " , create.getNihInstHolderCode() , 
                saved.getNihInstHolderCode());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    }
    /**
     * 
     * @param sp StudyProtocol
     * @return StudyIndlde
     */
    public static StudyIndlde createStudyIndldeobj(StudyProtocol sp) {
        StudyIndlde create = new StudyIndlde();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setExpandedAccessStatusCode(ExpandedAccessStatusCode.AVAILABLE);
        create.setStudyProtocol(sp);
        create.setExpandedAccessIndicator(Boolean.TRUE);
        create.setHolderTypeCode(HolderTypeCode.NIH);
        //create.setNihInstHolderCode(NihInstHolderCode.NCRR);
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
