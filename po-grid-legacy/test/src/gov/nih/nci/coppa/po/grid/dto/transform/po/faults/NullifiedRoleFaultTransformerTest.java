package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class NullifiedRoleFaultTransformerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
        NullifiedRoleFaultTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_NullifiedEntriesIsNull() throws DtoTransformException {
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();
        map.put(null, null);
        NullifiedRoleFault fault = NullifiedRoleFaultTransformer.INSTANCE.toXml(new NullifiedRoleException(map));
        
        assertNotNull(fault);
        assertNotNull(fault.getNullifiedEntries());
        assertEquals(0, fault.getNullifiedEntries().getEntry().length);
    }

    @Test
    public void testToXml_WithNullifiedEntries() throws DtoTransformException {
        Ii id = new Ii();
        id.setExtension("123");
        Ii duplicateOf = new Ii();
        duplicateOf.setExtension("456");
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();
        map.put(id, duplicateOf);
        NullifiedRoleFault fault = NullifiedRoleFaultTransformer.INSTANCE.toXml(new NullifiedRoleException(map));
        
        assertNotNull(fault);
        assertNotNull(fault.getNullifiedEntries());
        assertEquals(1, fault.getNullifiedEntries().getEntry().length);
        assertEquals(id.getExtension(), fault.getNullifiedEntries().getEntry()[0].getKey());
        assertEquals(duplicateOf.getExtension(), fault.getNullifiedEntries().getEntry()[0].getValue());
    }

}
