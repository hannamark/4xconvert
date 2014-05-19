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
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRSyncHelperTest extends AbstractHibernateTestCase {
    CaDSRSyncHelper helper;
    private MarkerAttributesServiceLocal markerAttributesService = mock(MarkerAttributesServiceLocal.class);
    private PlannedMarkerServiceLocal plannedMarkerService = mock(PlannedMarkerServiceLocal.class);
    LookUpTableServiceRemote lookUpTableSrv = new LookUpTableServiceBean();
    ApplicationService appService = mock(ApplicationService.class);
    CaDSRSyncHelper helperMock = mock(CaDSRSyncHelper.class);
    /** The CDE public Id for Assay Type Attribute. */
    private static final Long CDE_PUBLIC_ID_ASSAY = 64731L;
    /** The CDE public Id for BioMarker Use Attribute. */
    private static final Long CDE_PUBLIC_ID_USE = 2939411L;
    /** The CDE public Id for BioMarker Purpose Attribute. */
    private static final Long CDE_PUBLIC_ID_PURPOSE = 2939397L;
    /** The CDE public Id for Specimen Type Attribute. */
    private static final Long CDE_PUBLIC_ID_SPECIMEN = 3111302L;
    /** The CDE public Id for Specimen Collection Attribute. */
    private static final Long CDE_PUBLIC_ID_SP_COL = 2939404L;
    /** The CDE public Id for EvaluationType Attribute. */
    private static final Long CDE_PUBLIC_ID_EVAL = 3645784L;
    @Before
    public void setUp() throws Exception {
        helper = new CaDSRSyncHelper();
        helper.setMarkerAttributesService(markerAttributesService);
        helper.setPlannedMarkerService(plannedMarkerService);
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
        when(PaRegistry.getMarkerAttributesService()).thenReturn(markerAttributesService);
        when(PaRegistry.getPlannedMarkerService()).thenReturn(plannedMarkerService);
        when(PaRegistry.getLookUpTableService()).thenReturn(lookUpTableSrv);
        helper.setLookUpTableService(lookUpTableSrv);
        EnumeratedValueDomain vd = new EnumeratedValueDomain();
        vd.setId("1");
        
        DataElement de = new DataElement();
        de.setValueDomain(vd);

        List<Object> deResults = new ArrayList<Object>();
        deResults.add(de);

        ApplicationService appService = mock(ApplicationService.class);
        when(appService.search(eq(DataElement.class), any(DataElement.class))).thenReturn(deResults);

        PermissibleValue pv = new PermissibleValue();
        pv.setValue("ELISA");

        ValueMeaning vm = new ValueMeaning();
        vm.setLongName("ELISA");
        vm.setDescription("ELISA");
        vm.setPublicID(2578250L);
        pv.setValueMeaning(vm);

        ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
        vdpv.setPermissibleValue(pv);
        vdpv.setId("1");

        List<Object> results = new ArrayList<Object>();
        results.add(vdpv);

        when(appService.query(any(DetachedCriteria.class))).thenReturn(results);

        helper.setAppService(appService);
        TestSchema.caDSRSyncJobProperties();
        
    }

    @Test
    public void updateMarkerTablesTest() throws Exception {
        Map<Long , Map<String, String>> map = new HashMap<Long , Map<String, String>>();
        Map<String, String> result = new HashMap<String, String>();
        result.put("ELISA","ELISA");
        map.put(2558713L, result);
        when(helperMock.getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F)).thenReturn(map);
        helper.updateMarkerTables();        
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_ASSAY, 4.0F);
        assertTrue(map.size() > 0);
        Map<String, String> value = map.get(2575508L);
        
        assertTrue(value.get("Microarray").equals("Microarray"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_USE, 1.0F);
        assertTrue(map.size() > 0);
        value.clear();
        value = map.get(2944941L);
        assertTrue(value.get("Integral").equals("Integral"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_PURPOSE, 1.0F);
        assertTrue(map.size() > 0);
        value.clear();
        value = map.get(2939394L);
        assertTrue(value.get("Stratification Factor").equals("Stratification Factor"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN, 1.0F);
        assertTrue(map.size() > 0);
        value.clear();
        value = map.get(3004972L);
        assertTrue(value.get("Serum").equals("Serum"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_SP_COL, 1.0F);
        assertTrue(map.size() > 0);
        value.clear();
        value = map.get(2939403L);
        assertTrue(value.get("Mandatory").equals("Mandatory"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_EVAL, 1.0F);
        assertTrue(map.size() > 0);
        value.clear();
        value = map.get(3079271L);
        assertTrue(value.get("Methylation").equals("Methylation"));    
    }
    
    
    @Test
    public void syncPlannedMarkerAttributes() throws Exception {
        Map<Long, Map<String, String>> map = new HashMap<Long, Map<String,String>>();
        Map<String, String> value = new HashMap<String, String>();
        value.put("Microarray", "Microarray");
        map.put(2575508L, value);
        when(markerAttributesService.attributeValuesWithCaDSR(BioMarkerAttributesCode.ASSAY_TYPE)).thenReturn(map);
        helper.syncPlannedMarkerAttributes();
        Map<String, String> value1 = new HashMap<String, String>();
        value1.put("ELISA1", "ELISA1");
        map.put(2558713L, value1);
        when(markerAttributesService.attributeValuesWithCaDSR(BioMarkerAttributesCode.ASSAY_TYPE)).thenReturn(map);
        helper.syncPlannedMarkerAttributes();
    }

}
