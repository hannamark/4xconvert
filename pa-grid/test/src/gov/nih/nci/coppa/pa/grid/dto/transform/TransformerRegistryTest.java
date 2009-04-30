package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.Transformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.ArmTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.InterventionalStudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyParticipationContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRecruitmentStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.TransformerRegistry;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

import java.util.Map;

import org.junit.Test;

public class TransformerRegistryTest {

    @Test (expected=UnsupportedOperationException.class)
    public void testGetRegistry() {
        Map<Class<?>, Transformer<?,?>> tMap = TransformerRegistry.getRegistry();
        assertNotNull(tMap);
        assertEquals(8, tMap.size());
        tMap.clear();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetTransformer() {
        //#1
        Transformer trans = TransformerRegistry.INSTANCE.getTransformer(ArmDTO.class);
        assertTrue(trans instanceof ArmTransformer);
        //#2
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyResourcingDTO.class);
        assertTrue(trans instanceof StudyResourcingTransformer);
        //#3
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyProtocolDTO.class);
        assertTrue(trans instanceof StudyProtocolTransformer);
        //#4
        trans = TransformerRegistry.INSTANCE.getTransformer(InterventionalStudyProtocolDTO.class);
        assertTrue(trans instanceof InterventionalStudyProtocolTransformer);
        //#5
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyRegulatoryAuthorityDTO.class);
        assertTrue(trans instanceof StudyRegulatoryAuthorityTransformer);
        //#6
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyRecruitmentStatusDTO.class);
        assertTrue(trans instanceof StudyRecruitmentStatusTransformer);
        //#7
        trans = TransformerRegistry.INSTANCE.getTransformer(StudySiteAccrualStatusDTO.class);
        assertTrue(trans instanceof StudySiteAccrualStatusTransformer);
        //#8
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyParticipationContactDTO.class);
        assertTrue(trans instanceof StudyParticipationContactTransformer);
    }

    @Test (expected=RuntimeException.class)
    public void testGetTranspormerWithNull() {
        TransformerRegistry.INSTANCE.getTransformer(null);
    }

    @Test (expected=RuntimeException.class)
    public void testGetTranspormerWithUnknown() {
        TransformerRegistry.INSTANCE.getTransformer(String.class);
    }


}
