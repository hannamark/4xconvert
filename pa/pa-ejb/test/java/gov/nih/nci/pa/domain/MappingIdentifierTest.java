package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.util.TestSchema;

import java.io.Serializable;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;


public class MappingIdentifierTest {
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
    public void createMappingIdentifierTest() {
        
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        MappingIdentifier create = createMappingIdentifierobj(sp);
        Session session  = TestSchema.getSession();
        
        TestSchema.addUpdObject(sp);
        assertNotNull(sp);
        Serializable spid = sp.getId();
        StudyProtocol spSaved = (StudyProtocol) session.load(StudyProtocol.class, spid);
        assertNotNull(spSaved);
        assertNotNull(spid);

        TestSchema.addUpdObject(create);
        Serializable id = create.getId();
        assertNotNull(create);
        
        MappingIdentifier saved = new MappingIdentifier();
        saved = (MappingIdentifier) session.load(MappingIdentifier.class, id);
        assertEquals("idName " , create.getIdentifierName() , 
                saved.getIdentifierName());
        assertEquals("User Last updated does not match " , 
                create.getUserLastUpdated() , saved.getUserLastUpdated());
        assertEquals("Date Last updated does not match " , 
                create.getDateLastUpdated() , saved.getDateLastUpdated());
    } /**
     * 
     * @param sp StudyProtocol
     * @return MappingIdentifier
     */
    public static MappingIdentifier createMappingIdentifierobj(StudyProtocol sp) {
    	MappingIdentifier create = new MappingIdentifier();
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        create.setStudyProtocol(sp);
        create.setIdentifierName("idName");
        create.setFromIdentifier(1L);
        create.setToIdentifier(2L);
        create.setUserLastUpdated("Abstractor");
        create.setDateLastUpdated(now);
        return create;
    }
}
