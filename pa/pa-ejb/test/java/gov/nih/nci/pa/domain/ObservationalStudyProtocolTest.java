package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class ObservationalStudyProtocolTest {

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
    public void createObservationalStudyProtocolTest() {
        ObservationalStudyProtocol create = createObservationalStudyProtocolObj();
        Session session  = TestSchema.getSession();
        Serializable cid = session.save(create);
        assertNotNull(cid);
        ObservationalStudyProtocol saved = (ObservationalStudyProtocol) session.load(ObservationalStudyProtocol.class, cid);
        assertNotNull(saved);
        assertEquals("StudyModelCode does not match " , create.getStudyModelCode().getCode(), saved.getStudyModelCode().getCode());
        assertEquals("TimePerspectiveCode does not match " , create.getTimePerspectiveCode().getCode(), 
                            saved.getTimePerspectiveCode().getCode());
        assertEquals("BiospecimenDescription does not match " , 
                create.getBiospecimenDescription(), saved.getBiospecimenDescription());
        assertEquals("BiospecimenRetentionCode does not  match " , 
                create.getBiospecimenRetentionCode().getCode(), saved.getBiospecimenRetentionCode().getCode());
        assertEquals("NumberOfGroups does not match " , create.getNumberOfGroups() , saved.getNumberOfGroups());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    
    }
    
    /**
     * 
     * @return StudyProtocol
     */    
    public static ObservationalStudyProtocol createObservationalStudyProtocolObj() {
        ObservationalStudyProtocol osp = new ObservationalStudyProtocol();
        
        osp.setStudyModelCode(StudyModelCode.CASE_CONTROL);
        osp.setTimePerspectiveCode(TimePerspectiveCode.PROSPECTIVE);
        osp.setBiospecimenDescription("BiospecimenDescription");
        osp.setBiospecimenRetentionCode(BiospecimenRetentionCode.RETAINED);
        osp.setNumberOfGroups(4);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        now = new java.sql.Timestamp((new java.util.Date()).getTime());
        osp.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        osp.setUserLastUpdated("Abstractor");
        
        return osp;
    }
    
}
