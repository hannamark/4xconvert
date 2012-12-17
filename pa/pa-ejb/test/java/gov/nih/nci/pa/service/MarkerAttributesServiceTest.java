package gov.nih.nci.pa.service;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.TestSchema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class MarkerAttributesServiceTest extends AbstractHibernateTestCase {
    private final MarkerAttributesBeanLocal bean = new MarkerAttributesBeanLocal();

    @Before
    public void init() throws Exception {
        CSMUserService.setInstance(new MockCSMUserService());
        TestSchema.primeData();
    }

    @Test
    public void getTest() throws PAException {
        Map<String, String> result = bean.getAllMarkerAttributes();
        assertTrue(result.size() > 0);
        assertTrue(StringUtils.equals(result.get("ASSAY_TYPE0"), "PCR"));
        assertTrue(StringUtils.equals(result.get("EVALUATION_TYPE0"),
                "Level / Quantity"));
        assertTrue(StringUtils.equals(result.get("BIOMARKER_PURPOSE0"),
                "Eligibility Criterion"));
        assertTrue(StringUtils.equals(result.get("BIOMARKER_USE0"), "Integral"));
        assertTrue(StringUtils.equals(result.get("SPECIMEN_TYPE0"), "Serum"));
        assertTrue(StringUtils.equals(result.get("SPECIMEN_COLLECTION0"),
                "Mandatory"));
    }

    @Test
    public void getvalueTypeTest() throws PAException {
        List<String> result = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.ASSAY_TYPE);
        assertTrue(result.size() > 0);
        assertTrue(StringUtils.equals(result.get(0), "PCR"));
        List<String> resultEval = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.EVALUATION_TYPE);
        assertTrue(resultEval.size() > 0);
        assertTrue(StringUtils.equals(resultEval.get(0), "Level / Quantity"));
        List<String> resultBio = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.BIOMARKER_PURPOSE);
        assertTrue(resultBio.size() > 0);
        assertTrue(StringUtils.equals(resultBio.get(0), "Eligibility Criterion"));
        List<String> resultBioUse = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.BIOMARKER_USE);
        assertTrue(resultBioUse.size() > 0);
        assertTrue(StringUtils.equals(resultBioUse.get(0), "Integral"));
        List<String> resultspe = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.SPECIMEN_TYPE);
        assertTrue(resultspe.size() > 0);
        assertTrue(StringUtils.equals(resultspe.get(0), "Serum"));
        List<String> resultspeCol = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.SPECIMEN_COLLECTION);
        assertTrue(resultspeCol.size() > 0);
        assertTrue(StringUtils.equals(resultspeCol.get(0), "Mandatory"));
    }

    @Test
    public void updateMarkerTest() throws PAException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("PCR", "PCR");
        map.put("MicroArray", "MicroArray");
        bean.updateMarker(BioMarkerAttributesCode.ASSAY_TYPE, map);
        List<String> result = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.ASSAY_TYPE);
        assertTrue(result.size() == 2);
        map.clear();
        map.put("Retrospective Research", "Retrospective Research");
        map.put("Treatment Assignment", "Treatment Assignment");
        map.put("Other (specify)", "Other");
        bean.updateMarker(BioMarkerAttributesCode.BIOMARKER_PURPOSE, map);
        result.clear();
        result = MarkerAttributesBeanLocal.getTypeValues(BioMarkerAttributesCode.BIOMARKER_PURPOSE);
        assertTrue(result.size() == 3);
    }

}