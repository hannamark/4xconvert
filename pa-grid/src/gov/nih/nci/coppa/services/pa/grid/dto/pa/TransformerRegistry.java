package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

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

    private static Map<Class<?>, Transformer<?, ?>> values = new HashMap<Class<?>, Transformer<?, ?>>();

    static {
        values.put(ArmDTO.class, ArmTransformer.INSTANCE);
        values.put(StudyResourcingDTO.class, StudyResourcingTransformer.INSTANCE);
        values.put(StudyProtocolDTO.class, StudyProtocolTransformer.INSTANCE);
        values.put(InterventionalStudyProtocolDTO.class, InterventionalStudyProtocolTransformer.INSTANCE);
        values.put(StudyRegulatoryAuthorityDTO.class, StudyRegulatoryAuthorityTransformer.INSTANCE);
        values.put(StudyRecruitmentStatusDTO.class, StudyRecruitmentStatusTransformer.INSTANCE);
        values.put(StudySiteAccrualStatusDTO.class, StudySiteAccrualStatusTransformer.INSTANCE);
        values.put(StudyParticipationContactDTO.class, StudyParticipationContactTransformer.INSTANCE);
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
        Transformer<?, ?> transformer = values.get(type);
        if (transformer == null) {
            throw new RuntimeException("Unable to find Transformer for type " + type);
        }
        return transformer;
    }

    /**
     * @return an unmodifiable version of the registry
     */
    public static Map<Class<?>, Transformer<?, ?>> getRegistry() {
        return Collections.unmodifiableMap(values);
    }
}
