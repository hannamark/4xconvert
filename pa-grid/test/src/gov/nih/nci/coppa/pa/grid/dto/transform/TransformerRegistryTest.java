package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.pa.grid.dto.Transformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.ArmTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyResourcingTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.TransformerRegistry;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;

import java.util.Map;

import org.junit.Test;

public class TransformerRegistryTest {

    @Test (expected=UnsupportedOperationException.class)
    public void testGetRegistry() {
        Map<Class<?>, Transformer<?,?>> tMap = TransformerRegistry.getRegistry();
        assertNotNull(tMap);
        assertEquals(2, tMap.size());
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
