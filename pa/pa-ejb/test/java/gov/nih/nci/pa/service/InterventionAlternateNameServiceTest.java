package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class InterventionAlternateNameServiceTest {
    private InterventionAlternateNameServiceRemote remoteEjb = new InterventionAlternateNameServiceBean();
    private Ii ii;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        ii = IiConverter.convertToIi(TestSchema.interventionIds.get(0));
     }    
    
    @Test 
    public void getByInterventionTest() throws Exception {
        List<InterventionAlternateNameDTO> dtoList = remoteEjb.getByIntervention(ii);
        assertEquals(2, dtoList.size());
     }
}
