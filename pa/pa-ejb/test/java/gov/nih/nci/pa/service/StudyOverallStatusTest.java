/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusTest {
    private Session sess;
    StudyOverallStatusServiceRemote remoteEjb;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        sess = TestSchema.getSession();
        remoteEjb = new StudyOverallStatusServiceBean();
    }    
    
    @Test
    public void getByStudyProtocol() throws Exception {
        Ii pid = IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0));
        List<StudyOverallStatusDTO> statusList = 
            remoteEjb.getStudyOverallStatusByStudyProtocol(pid);
        assertEquals(statusList.size(), 2);
        
        StudyOverallStatusDTO currentStatus = 
            remoteEjb.getCurrentStudyOverallStatusByStudyProtocol(pid);
        assertEquals(IiConverter.convertToLong(statusList.get(1).getIi())
                , (IiConverter.convertToLong(currentStatus.getIi())));
        
    }

}
