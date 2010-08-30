package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.po.grid.dto.transform.po.ClinicalResearchStaffTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.CorrelationNodeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.EntityNodeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.HealthCareFacilityTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.HealthCareProviderTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdentifiedOrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.IdentifiedPersonTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationalContactTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OversightCommitteeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PatientTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.ResearchOrganizationTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of Transformer(s).
 *
 * @author smatyas
 *
 */
public final class TransformerRegistry {

    private static final Map<Class<?>, Transformer<?, ?>> VALUES = new HashMap<Class<?>, Transformer<?, ?>>();

    static {
        VALUES.put(ClinicalResearchStaffDTO.class, ClinicalResearchStaffTransformer.INSTANCE);
        VALUES.put(HealthCareFacilityDTO.class, HealthCareFacilityTransformer.INSTANCE);
        VALUES.put(HealthCareProviderDTO.class, HealthCareProviderTransformer.INSTANCE);
        VALUES.put(IdentifiedOrganizationDTO.class, IdentifiedOrganizationTransformer.INSTANCE);
        VALUES.put(IdentifiedPersonDTO.class, IdentifiedPersonTransformer.INSTANCE);
        VALUES.put(ResearchOrganizationDTO.class, ResearchOrganizationTransformer.INSTANCE);
        VALUES.put(OversightCommitteeDTO.class, OversightCommitteeTransformer.INSTANCE);
        VALUES.put(OrganizationalContactDTO.class, OrganizationalContactTransformer.INSTANCE);
        VALUES.put(PatientDTO.class, PatientTransformer.INSTANCE);
        VALUES.put(CorrelationNodeDTO.class, CorrelationNodeTransformer.INSTANCE);
        VALUES.put(EntityNodeDto.class, EntityNodeTransformer.INSTANCE);

    }

    /**
     * Public singleton.
     */
    public static final TransformerRegistry INSTANCE = new TransformerRegistry();

    private TransformerRegistry() {
    }

    /**
     * @param type DTO type to translate
     * @return transformer for the type requested
     */
    public Transformer<?, ?> getTransformer(Class<?> type) {
        Transformer<?, ?> transformer = VALUES.get(type);
        if (transformer == null) {
            throw new RuntimeException("Unable to find Transformer for type " + type);
        }
        return transformer;
    }

    /**
     * @return an unmodifiable version of the registry
     */
    public static Map<Class<?>, Transformer<?, ?>> getRegistry() {
        return Collections.unmodifiableMap(VALUES);
    }
}
