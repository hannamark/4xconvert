package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.ArmTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentWorkflowStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.InterventionalStudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.PlannedActivityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.PlannedEligibilityCriterionTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyDiseaseTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyIndldeTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOnholdTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOutcomeMeasureTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOverallStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyProtocolTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRecruitmentStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyRelationshipTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.TransformerRegistry;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

import java.util.Map;

import org.junit.Test;

public class TransformerRegistryTest {

    @Test (expected=UnsupportedOperationException.class)
    public void testGetRegistry() {
        Map<Class<?>, Transformer<?,?>> tMap = TransformerRegistry.getRegistry();
        assertNotNull(tMap);
        assertEquals(20, tMap.size());
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
        trans = TransformerRegistry.INSTANCE.getTransformer(StudySiteContactDTO.class);
        assertTrue(trans instanceof StudySiteContactTransformer);
        //#9
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyOutcomeMeasureDTO.class);
        assertTrue(trans instanceof StudyOutcomeMeasureTransformer);
        //#10
        trans = TransformerRegistry.INSTANCE.getTransformer(StudySiteDTO.class);
        assertTrue(trans instanceof StudySiteTransformer);
        //#11
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyOverallStatusDTO.class);
        assertTrue(trans instanceof StudyOverallStatusTransformer);
        //#12
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyDiseaseDTO.class);
        assertTrue(trans instanceof StudyDiseaseTransformer);
        //#13
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyContactDTO.class);
        assertTrue(trans instanceof StudyContactTransformer);
        //#14
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyOnholdDTO.class);
        assertTrue(trans instanceof StudyOnholdTransformer);
        //#15
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyIndldeDTO.class);
        assertTrue(trans instanceof StudyIndldeTransformer);
        //#16
        trans = TransformerRegistry.INSTANCE.getTransformer(StudyRelationshipDTO.class);
        assertTrue(trans instanceof StudyRelationshipTransformer);
        //#17
        trans = TransformerRegistry.INSTANCE.getTransformer(DocumentWorkflowStatusDTO.class);
        assertTrue(trans instanceof DocumentWorkflowStatusTransformer);
        //#18
        trans = TransformerRegistry.INSTANCE.getTransformer(PlannedActivityDTO.class);
        assertTrue(trans instanceof PlannedActivityTransformer);
        //#19
        trans = TransformerRegistry.INSTANCE.getTransformer(PlannedEligibilityCriterionDTO.class);
        assertTrue(trans instanceof PlannedEligibilityCriterionTransformer);
        //#20
        trans = TransformerRegistry.INSTANCE.getTransformer(DocumentDTO.class);
        assertTrue(trans instanceof DocumentTransformer);
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
