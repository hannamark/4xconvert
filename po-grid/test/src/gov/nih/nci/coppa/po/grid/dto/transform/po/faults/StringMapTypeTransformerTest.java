package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.faults.SimpleIIMapType;
import gov.nih.nci.coppa.po.faults.StringMapType;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;

import java.util.HashMap;

import org.junit.Test;

public class StringMapTypeTransformerTest {


    @Test (expected=UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
       StringMapTypeTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_NULL() throws DtoTransformException {
        StringMapType result = StringMapTypeTransformer.INSTANCE.toXml(null);
    }
    
    @Test
    public void testToXml_Empty() throws DtoTransformException {
        StringMapType result = StringMapTypeTransformer.INSTANCE.toXml(new HashMap<String,String[]>());
    }
    
    @Test
    public void testToXml_1() throws DtoTransformException {
        HashMap<String,String[]> input = new HashMap<String,String[]>();
        String key = "1";
        String[] value = {"2","3"};
        input.put(key, value);
        StringMapType result = StringMapTypeTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().length);
        assertEquals("1", result.getEntry()[0].getKey());
        assertNotNull(result.getEntry()[0].getValue());
        assertEquals("2", result.getEntry()[0].getValue()[0]);
        assertEquals("3", result.getEntry()[0].getValue()[1]);
    }
    
    @Test
    public void testToXml_2() throws DtoTransformException {
        HashMap<String,String[]> input = new HashMap<String,String[]>();
        String key = "1";
        String[] value = null;
        input.put(key, value);
        StringMapType result = StringMapTypeTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().length);
        assertEquals("1", result.getEntry()[0].getKey());
        assertNull(result.getEntry()[0].getValue());
    }
    @Test
    public void testToXml_3() throws DtoTransformException {
        HashMap<String,String[]> input = new HashMap<String,String[]>();
        String key = "1";
        String[] value = {};
        input.put(key, value);
        StringMapType result = StringMapTypeTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().length);
        assertEquals("1", result.getEntry()[0].getKey());
        assertNotNull(result.getEntry()[0].getValue());
        assertEquals(0, result.getEntry()[0].getValue().length);
    }

}
