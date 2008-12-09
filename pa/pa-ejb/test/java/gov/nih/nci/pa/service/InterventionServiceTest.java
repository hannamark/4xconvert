package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
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
    private InterventionAlternateNameServiceRemote ianService = new InterventionAlternateNameServiceBean();
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
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(StConverter.convertToSt("xCHOCOLATE"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(0, r.size());

        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        r = remoteEjb.search(searchCriteria);
        assertEquals(1, r.size());

        searchCriteria.setName(null);
        searchCriteria.setTypeCode(CdConverter.convertToCd(InterventionTypeCode.GENETIC));
        try {
            r = remoteEjb.search(searchCriteria);
            fail("Service should throw PAException when searching w/o name.  ");
        } catch(PAException e) {
            // expected behavior
        }
    }
    @Test
    public void noInactiveInterventionAlternateNameRecordsReturnedBySearchTest() throws Exception {
        InterventionDTO searchCriteria = new InterventionDTO();
        searchCriteria.setName(StConverter.convertToSt("HERSHEY"));
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        int size = r.size();
        assertTrue(size > 0);
        Ii interventionIi = r.get(0).getIdentifier();
        List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(interventionIi);
        for (InterventionAlternateNameDTO ian : ianList) {
            ian.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.INACTIVE));
            ianService.update(ian);
        }
        
        r = remoteEjb.search(searchCriteria);
        assertEquals(size - 1, r.size());
    }
    @Test
    public void noInactiveInterventionRecordsReturnedBySearchTest() throws Exception {
        InterventionDTO searchCriteria = new InterventionDTO();
        searchCriteria.setName(StConverter.convertToSt("CHOCOLATE"));
        List<InterventionDTO> r = remoteEjb.search(searchCriteria);
        int size = r.size();
        assertTrue(size > 0);
        r.get(0).setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.INACTIVE));
        remoteEjb.update(r.get(0));
        r = remoteEjb.search(searchCriteria);
        assertEquals(size - 1, r.size());
    }
}
