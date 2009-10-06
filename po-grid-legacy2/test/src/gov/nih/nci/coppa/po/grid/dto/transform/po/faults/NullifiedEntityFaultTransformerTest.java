package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class NullifiedEntityFaultTransformerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
        NullifiedEntityFaultTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_NullifiedEntriesIsNull_1() throws DtoTransformException {
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();
        map.put(null, null);
        NullifiedEntityFault fault = NullifiedEntityFaultTransformer.INSTANCE.toXml(new NullifiedEntityException(map));
        assertNotNull(fault);
        assertNotNull(fault.getNullifiedEntries());
        assertEquals(0, fault.getNullifiedEntries().getEntry().length);
    }

    @Test
    public void testToXml_Null() throws DtoTransformException {
        NullifiedEntityFault fault = NullifiedEntityFaultTransformer.INSTANCE.toXml(null);
        assertNull(fault);
    }

    @Test
    public void testToXml_WithNullifiedEntries() throws DtoTransformException {
        Ii id = new Ii();
        id.setExtension("123");
        Ii duplicateOf = new Ii();
        duplicateOf.setExtension("456");
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();
        map.put(id, duplicateOf);
        NullifiedEntityFault fault = NullifiedEntityFaultTransformer.INSTANCE.toXml(new NullifiedEntityException(map));

        assertNotNull(fault);
        assertNotNull(fault.getNullifiedEntries());
        assertEquals(1, fault.getNullifiedEntries().getEntry().length);
        assertEquals(id.getExtension(), fault.getNullifiedEntries().getEntry()[0].getKey());
        assertEquals(duplicateOf.getExtension(), fault.getNullifiedEntries().getEntry()[0].getValue());
    }

}
