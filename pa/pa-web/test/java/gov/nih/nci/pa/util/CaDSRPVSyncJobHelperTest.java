package gov.nih.nci.pa.util;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.PermissibleValue;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
public class CaDSRPVSyncJobHelperTest {
    CaDSRPVSyncJobHelper helper;
    private PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    private PlannedMarkerSyncWithCaDSRServiceLocal permissibleService = mock(PlannedMarkerSyncWithCaDSRServiceLocal.class);
    ApplicationService appService = mock(ApplicationService.class);
    CaDSRPVSyncJobHelper helperMock = mock(CaDSRPVSyncJobHelper.class);
    /** The CDE public Id for Assay Type Attribute. */
    private static final Long CDE_PUBLIC_ID = 5473L;

    @Before
    public void setUp() throws Exception {
        helper = new CaDSRPVSyncJobHelper();
        helper.setPlannedMarkerService(plannedMarkerService);
        helper.setAppService(appService);
        helper.setPermissibleService(permissibleService);
        EnumeratedValueDomain vd = new EnumeratedValueDomain();
        vd.setId("1");

        DataElement de = new DataElement();
        de.setValueDomain(vd);

        List<Object> deResults = new ArrayList<Object>();
        deResults.add(de);

        ApplicationService appService = mock(ApplicationService.class);
        when(appService.search(eq(DataElement.class), any(DataElement.class)))
                .thenReturn(deResults);

        PermissibleValue pv = new PermissibleValue();
        pv.setValue("N-Cadherin");

        ValueMeaning vm = new ValueMeaning();
        vm.setLongName("N-Cadherin");
        vm.setDescription("cadherin");
        vm.setPublicID(2578250L);
        pv.setValueMeaning(vm);

        ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
        vdpv.setPermissibleValue(pv);
        vdpv.setId("1");

        List<Object> results = new ArrayList<Object>();
        results.add(vdpv);

        when(appService.query(any(DetachedCriteria.class))).thenReturn(results);

        helper.setAppService(appService);
    }

    @Test
    public void getAllValuesFromCaDSRTest() throws PAException {
        List<CaDSRDTO> list = helper.getAllValuesFromCaDSR();
        assertTrue(list.size() > 0);
    }
}
