package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StringMapTransformerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
        StringMapTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_NULL() throws DtoTransformException {
        StringMap result = StringMapTransformer.INSTANCE.toXml(null);
    }

    @Test
    public void testToXml_Empty() throws DtoTransformException {
        StringMap result = StringMapTransformer.INSTANCE.toXml(new HashMap<String, String[]>());
    }

    @Test
    public void testToXml_1() throws DtoTransformException {
        HashMap<String, String[]> input = new HashMap<String, String[]>();
        String key = "1";
        String[] value = { "2", "3" };
        input.put(key, value);
        StringMap result = StringMapTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().size());
        assertEquals("1", result.getEntry().get(0).getKey());
        assertNotNull(result.getEntry().get(0).getValue());
        assertEquals("2", result.getEntry().get(0).getValue().get(0));
        assertEquals("3", result.getEntry().get(0).getValue().get(1));
    }

//    @Test
    public void testToXml_2() throws DtoTransformException {
        HashMap<String, String[]> input = new HashMap<String, String[]>();
        String key = "1";
        String[] value = null;
        input.put(key, value);
        StringMap result = StringMapTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().size());
        assertEquals("1", result.getEntry().get(0).getKey());
        assertNull(result.getEntry().get(0).getValue());
    }

    @Test
    public void testToXml_3() throws DtoTransformException {
        HashMap<String, String[]> input = new HashMap<String, String[]>();
        String key = "1";
        String[] value = {};
        input.put(key, value);
        StringMap result = StringMapTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().size());
        assertEquals("1", result.getEntry().get(0).getKey());
        assertNotNull(result.getEntry().get(0).getValue());
        assertEquals(0, result.getEntry().get(0).getValue().size());
    }

}
