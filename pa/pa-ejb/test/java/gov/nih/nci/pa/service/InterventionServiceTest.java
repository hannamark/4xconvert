package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

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
    @Test
    public void searchTest() throws Exception {
        InterventionDTO searchCriteria = new InterventionDTO();
        searchCriteria.setName(StConverter.convertToSt("CHOCOLATE"));
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.DIETARY_SUPPLEMENT));
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(StConverter.convertToSt("xCHOCOLATE"));
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.DIETARY_SUPPLEMENT));
        r = remoteEjb.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.DIETARY_SUPPLEMENT));
        r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.GENETIC));
        r = remoteEjb.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setName(null);
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.GENETIC));
        try {
            r = remoteEjb.search(searchCriteria);
            fail("Service should throw PAException when searching w/o name.  ");
        } catch(PAException e) {
            // expected behavior
        }

        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        searchCriteria.setTypeCode(null);
        try {
            r = remoteEjb.search(searchCriteria);
            fail("Service should throw PAException when searching w/o typeCode.  ");
        } catch(PAException e) {
            // expected behavior
        }
    }
}
