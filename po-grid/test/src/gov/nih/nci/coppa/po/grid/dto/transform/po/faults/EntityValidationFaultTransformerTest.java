package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.*;
import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class EntityValidationFaultTransformerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
        EntityValidationFaultTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_ErrorsIsNull() throws DtoTransformException {
        EntityValidationFault fault = EntityValidationFaultTransformer.INSTANCE.toXml(new EntityValidationException((Map<String, String[]>) null));
        
        assertNotNull(fault);
        assertNull(fault.getErrors());
    }

    @Test
    public void testToXml_WithErrors() throws DtoTransformException {
        String key = "123";
        String[] values = {"456", "567"};
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(key, values);
        EntityValidationFault fault = EntityValidationFaultTransformer.INSTANCE.toXml(new EntityValidationException((Map<String, String[]>) map));
        
        assertNotNull(fault);
        assertNotNull(fault.getErrors());
        assertEquals(1, fault.getErrors().getEntry().length);
        assertEquals(key, fault.getErrors().getEntry()[0].getKey());
        assertEquals(values[0], fault.getErrors().getEntry()[0].getValue()[0]);
        assertEquals(values[1], fault.getErrors().getEntry()[0].getValue()[1]);
    }

}
