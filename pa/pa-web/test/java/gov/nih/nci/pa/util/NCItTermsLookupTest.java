package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.InterventionWebDTO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test NCIt look up service
 * @author Gopalakrishnan Unnikrishnan
 *
 */
public class NCItTermsLookupTest {

    NCItTermsLookup lookup;

    @Before
    public void setUp() throws Exception {
        lookup = new NCItTermsLookup();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testlookupDisease() throws Exception {
        DiseaseWebDTO disease = lookup.lookupDisease("C4878");
        assertNotNull(disease);
        assertEquals("C4878", disease.getNtTermIdentifier());
        assertEquals("Lung Carcinoma", disease.getPreferredName());
        assertFalse(disease.getAlterNameList().isEmpty());
        assertTrue(disease.getAlterNameList().contains("Cancer of Lung"));
        assertTrue(disease.getAlterNameList().contains("Cancer of the Lung"));
        assertTrue(disease.getAlterNameList().contains("Carcinoma of the Lung"));
        assertFalse(disease.getParentTermList().isEmpty());
        assertTrue(disease.getParentTermList().contains("C2916: Carcinoma"));
        assertTrue(disease.getParentTermList().contains("C7377: Malignant Lung Neoplasm"));
        assertFalse(disease.getChildTermList().isEmpty());
        assertTrue(disease.getChildTermList().contains("C27925: Asbestos-Related Lung Carcinoma"));
        assertTrue(disease.getChildTermList().contains("C5641: Occult Lung Carcinoma"));
        assertTrue(disease.getChildTermList().contains("C45544: Lung Mucoepidermoid Carcinoma"));
        
    }

    @Test
    public void testlookupNonExistingDisease() throws Exception {
        DiseaseWebDTO disease = lookup.lookupDisease("C1");
        assertNull(disease);
    }

    @Test
    public void testlookupInterventionNoSynonym() throws Exception {
        InterventionWebDTO intervention = lookup.lookupIntervention("C2810");
        assertNotNull(intervention);
        assertEquals("C2810", intervention.getNtTermIdentifier());
        assertEquals("SB-AS02B Adjuvant", intervention.getName());
        assertTrue(intervention.getAlterNames().isEmpty());
    }

    @Test
    public void testlookupIntervention() throws Exception {
        InterventionWebDTO intervention = lookup.lookupIntervention("C26446");
        assertNotNull(intervention);
        assertEquals("C26446", intervention.getNtTermIdentifier());
        assertEquals("Dendritic Cell-Autologous Lung Tumor Vaccine", intervention.getName());
        assertFalse(intervention.getAlterNames().isEmpty());
        assertTrue(intervention.getAlterNames().contains("DCVax-Lung"));
    }

    @Test
    public void testlookupNonExistingIntervention() throws Exception {
        InterventionWebDTO intervention = lookup.lookupIntervention("C1");
        assertNull(intervention);
    }

}
