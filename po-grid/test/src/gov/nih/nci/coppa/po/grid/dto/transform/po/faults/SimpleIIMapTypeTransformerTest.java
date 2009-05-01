package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.faults.SimpleIIMapType;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;

import java.util.HashMap;

import org.junit.Test;

public class SimpleIIMapTypeTransformerTest {

    @Test (expected=UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
        SimpleIIMapTypeTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_NULL() throws DtoTransformException {
        SimpleIIMapType result = SimpleIIMapTypeTransformer.INSTANCE.toXml(null);
    }
    
    @Test
    public void testToXml_Empty() throws DtoTransformException {
        SimpleIIMapType result = SimpleIIMapTypeTransformer.INSTANCE.toXml(new HashMap<Ii,Ii>());
    }
    
    @Test
    public void testToXml_1() throws DtoTransformException {
        HashMap<Ii,Ii> input = new HashMap<Ii,Ii>();
        Ii key = new Ii();
        key.setExtension("1");
        Ii value = new Ii();
        value.setExtension("2");
        input.put(key, value);
        SimpleIIMapType result = SimpleIIMapTypeTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().length);
        assertEquals("1", result.getEntry()[0].getKey());
        assertEquals("2", result.getEntry()[0].getValue());
    }
    
    @Test
    public void testToXml_2() throws DtoTransformException {
        HashMap<Ii,Ii> input = new HashMap<Ii,Ii>();
        Ii key = new Ii();
        key.setExtension("1");
        input.put(key, null);
        SimpleIIMapType result = SimpleIIMapTypeTransformer.INSTANCE.toXml(input);
        assertNotNull(result);
        assertNotNull(result.getEntry());
        assertEquals(1, result.getEntry().length);
        assertEquals("1", result.getEntry()[0].getKey());
        assertEquals(null, result.getEntry()[0].getValue());
    }

}
