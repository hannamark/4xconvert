package gov.nih.nci.coppa.pa.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.pa.grid.dto.Transformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.ArmTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.TransformerRegistry;
import gov.nih.nci.pa.iso.dto.ArmDTO;

import java.util.Map;

import org.junit.Test;

public class TransformerRegistryTest {

    @Test (expected=UnsupportedOperationException.class)
    public void testGetRegistry() {
        Map<Class<?>, Transformer<?,?>> tMap = TransformerRegistry.getRegistry();
        assertNotNull(tMap);
        assertEquals(1, tMap.size());
        tMap.clear();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetTransformer() {
        //#1
        Transformer trans = TransformerRegistry.INSTANCE.getTransformer(ArmDTO.class);
        assertTrue(trans instanceof ArmTransformer);
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
