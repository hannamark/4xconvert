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
import gov.nih.nci.pa.action.AbstractPaActionTest;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
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
public class CaDSRSyncHelperTest extends AbstractPaActionTest {
    CaDSRSyncHelper helper;
    private MarkerAttributesServiceLocal markerAttributesService = mock(MarkerAttributesServiceLocal.class);
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
    }

    @Test
    public void updateMarkerTablesTest() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("ELISA", "ELISA");
        when(helperMock.getCaDSRValues(CDE_PUBLIC_ID_ASSAY)).thenReturn(map);
        helper.updateMarkerTables();        
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_ASSAY);
        assertTrue(map.size() > 0);
        assertTrue(map.get("Microarray").equals("Microarray"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_USE);
        assertTrue(map.size() > 0);
        assertTrue(map.get("Integral").equals("Integral"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_PURPOSE);
        assertTrue(map.size() > 0);
        assertTrue(map.get("Stratification Factor").equals("Stratification Factor"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN);
        assertTrue(map.size() > 0);
        assertTrue(map.get("Serum").equals("Serum"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_SP_COL);
        assertTrue(map.size() > 0);
        assertTrue(map.get("Mandatory").equals("Mandatory"));
        map = helper.getCaDSRValues(CDE_PUBLIC_ID_EVAL);
        assertTrue(map.size() > 0);
        assertTrue(map.get("Methylation").equals("Methylation"));
        
    }

}
