package gov.nih.nci.pa.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
public class PlannedMarkerSyncWithCaDSRServiceTest extends
        AbstractHibernateTestCase {
    private final PlannedMarkerSyncWithCaDSRBeanLocal bean = new PlannedMarkerSyncWithCaDSRBeanLocal();
    
    private final PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);

    @Before
    public void setUp() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();

        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);

    }

    @Test
    public void getValuesByIdTest() throws PAException {
        TestSchema.createPlannedMarker();
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesById(1L);
        assertTrue(list.size() > 0);
    }

    @Test
    public void getValuesByNameTest() throws PAException {
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean
                .getValuesByName("Marker #1");
        assertTrue(list.size() > 0);
    }

    @Test
    public void getIdentifierByCadsrIdTest() throws PAException {
        List<Number> list = bean.getIdentifierByCadsrId(12345L);
        assertTrue(list.size() > 0);
    }

    @Test
    public void getPendingIdentifierByCadsrNameTest() throws PAException {
        List<Number> list = bean.getPendingIdentifierByCadsrName("Marker #1");
        assertTrue(list.size() > 0);
    }

    @Test
    public void getPendingIdentifierByCadsrNameTest1() throws PAException {
        List<Number> list = bean.getPendingIdentifierByCadsrName("name1");
        assertTrue(list.size() > 0);
    }

    @Test
    public void syncTableWithCaDSRTest() throws PAException {
        
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        List<CaDSRDTO> valuesList = new ArrayList<CaDSRDTO>();
        CaDSRDTO value = new CaDSRDTO();
        value.setPublicId(12345L);
        value.setVmName("name");
        value.setVmMeaning("meaning");
        value.setVmDescription("description");
        valuesList.add(value);
        bean.syncTableWithCaDSR(valuesList);
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesByName("name");
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void updateValuesTest() throws PAException {
        bean.updateValues(12345L, "Aneuploidy", "Aneuploidy", "Aneuploidy", ActiveInactivePendingCode.ACTIVE.getName());
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesByName("Aneuploidy");
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void updateValueByNameTest() throws PAException {
        bean.insertValues(null, "PI3K", "PI3K", null , ActiveInactivePendingCode.PENDING.getName());
        bean.updateValueByName(null, "PI3K", "PI3K", null , ActiveInactivePendingCode.PENDING.getName());
        List<PlannedMarkerSyncWithCaDSRDTO> list = bean.getValuesByName("PI3K");
        assertTrue(list.size() > 0);
    }
    
    @Test 
    public void updateStatusCodeTest() throws PAException {
        bean.insertValues(1348L, "PI3K", "PI3K", null , ActiveInactivePendingCode.PENDING.getName());
        List<Number> list = bean.getIdentifierByCadsrId(1348L);
        bean.updateStatusCode(1348L, ActiveInactivePendingCode.ACTIVE.getName());
        List<PlannedMarkerSyncWithCaDSRDTO> list1 = bean.getValuesById(list.get(0).longValue());
        assertTrue(list.size() > 0);
    }

}
