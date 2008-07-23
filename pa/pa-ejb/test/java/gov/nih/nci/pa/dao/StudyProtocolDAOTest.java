package gov.nih.nci.pa.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;
import java.util.List;

import gov.nih.nci.pa.domain.CommonTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;
import gov.nih.nci.pa.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class StudyProtocolDAOTest {
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
               
    }
    
    @Test
    public void getStudyProtocolByCriteriaTest() {
        List<Object> obj = null ;
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        
        StudyProtocolDAO spDAO = new StudyProtocolDAO();
        StudyProtocolQueryCriteria qspc = new StudyProtocolQueryCriteria();
        qspc.setOfficialTitle(sp.getOfficialTitle());
        try {
             obj = spDAO.getStudyProtocolByCriteria(qspc);
        } catch (PAException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(obj);
        
    }

}
