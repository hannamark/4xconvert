package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class InterventionServiceTest {
    private InterventionServiceRemote remoteEjb = new InterventionServiceBean();
    private Ii ii;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.interventionIds.get(0));
     }    
    
    @Test 
    public void getTest() throws Exception {
        InterventionDTO dto = remoteEjb.get(ii);
        assertEquals(StConverter.convertToString(dto.getName()), "Chocolate Bar");
    }
}
