package gov.nih.nci.coppa.po.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.po.grid.dto.transform.po.ClinicalResearchStaffTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.HealthCareFacilityTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.HealthCareProviderTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdentifiedOrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdentifiedPersonTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationalContactTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OversightCommitteeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PatientTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.ResearchOrganizationTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.Map;

import org.junit.Test;

public class TransformerRegistryTest {

    @Test (expected=UnsupportedOperationException.class)
    public void testGetRegistry() {
        Map<Class<?>, Transformer<?,?>> tMap = TransformerRegistry.getRegistry();
        assertNotNull(tMap);
        assertEquals(9, tMap.size());
        tMap.clear();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetTransformer() {
        //#1
        Transformer trans = TransformerRegistry.INSTANCE.getTransformer(ClinicalResearchStaffDTO.class);
        assertTrue(trans instanceof ClinicalResearchStaffTransformer);
        //#2
        trans = TransformerRegistry.INSTANCE.getTransformer(HealthCareFacilityDTO.class);
        assertTrue(trans instanceof HealthCareFacilityTransformer);
        //#3
        trans = TransformerRegistry.INSTANCE.getTransformer(HealthCareProviderDTO.class);
        assertTrue(trans instanceof HealthCareProviderTransformer);
        //#4
        trans = TransformerRegistry.INSTANCE.getTransformer(IdentifiedOrganizationDTO.class);
        assertTrue(trans instanceof IdentifiedOrganizationTransformer);
        //#5
        trans = TransformerRegistry.INSTANCE.getTransformer(IdentifiedPersonDTO.class);
        assertTrue(trans instanceof IdentifiedPersonTransformer);
        //#6
        trans = TransformerRegistry.INSTANCE.getTransformer(ResearchOrganizationDTO.class);
        assertTrue(trans instanceof ResearchOrganizationTransformer);
        //#7
        trans = TransformerRegistry.INSTANCE.getTransformer(OversightCommitteeDTO.class);
        assertTrue(trans instanceof OversightCommitteeTransformer);
        //#8
        trans = TransformerRegistry.INSTANCE.getTransformer(OrganizationalContactDTO.class);
        assertTrue(trans instanceof OrganizationalContactTransformer);
        //#9
        trans = TransformerRegistry.INSTANCE.getTransformer(PatientDTO.class);
        assertTrue(trans instanceof PatientTransformer);
    }

    @Test (expected=RuntimeException.class)
    public void testGetTranspormerWithNull() {
        TransformerRegistry.INSTANCE.getTransformer(null);
    }

    @Test (expected=RuntimeException.class)
    public void testGetTranspormerWithUnknown() {
        TransformerRegistry.INSTANCE.getTransformer(PersonDTO.class);
    }


}
