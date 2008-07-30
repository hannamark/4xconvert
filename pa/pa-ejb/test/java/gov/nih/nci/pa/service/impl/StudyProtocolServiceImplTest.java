package gov.nih.nci.pa.service.impl;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyOverallStatusTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.test.util.TestSchema;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyProtocolServiceImplTest {
    
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
     * @throws PAException  PAException
     */
    @Test
    public  void getStudyProtocolByCriteriaTest() throws PAException {
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Long id = sp.getId();
        
        StudyOverallStatus sos = new StudyOverallStatusTest().createStudyOverallStatusobj(sp);
        TestSchema.addUpdObject(sos);
        Long sid = sp.getId();
        
        StudyProtocolServiceImpl spsImpl = new StudyProtocolServiceImpl();
        StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();
        spqc.setStudyProtocolId(id);
        List<StudyProtocolQueryDTO> spDtos = spsImpl.getStudyProtocolByCriteria(spqc); 
        assertNotNull(spDtos);
        assertEquals(" size of StudyProtocolQueryDTO does not match " , spDtos.size() , 1);
        assertEquals(" protocol title  does not match " , spDtos.get(0).getOfficialTitle() , sp.getOfficialTitle());
        
    }
    
    
}

