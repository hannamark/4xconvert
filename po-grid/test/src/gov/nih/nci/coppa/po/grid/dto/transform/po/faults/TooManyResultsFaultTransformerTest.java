package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.po.faults.TooManyResultsFault;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.services.TooManyResultsException;

import org.junit.Test;

public class TooManyResultsFaultTransformerTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testToDto() throws DtoTransformException {
        TooManyResultsFaultTransformer.INSTANCE.toDto(null);
    }

    @Test
    public void testToXml_NullifiedEntriesIsNull_1() throws DtoTransformException {
        TooManyResultsFault fault = TooManyResultsFaultTransformer.INSTANCE.toXml(new TooManyResultsException(5));
        assertNotNull(fault);
        assertEquals(5,fault.getMaxResults());
    }

    @Test
    public void testToXml_WithNullifiedEntries() throws DtoTransformException {
        assertNull(TooManyResultsFaultTransformer.INSTANCE.toXml(null));
    }

}
