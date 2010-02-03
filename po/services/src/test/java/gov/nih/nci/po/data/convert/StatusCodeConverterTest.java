package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Arrays;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class StatusCodeConverterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMap() {
        assertEquals(EntityStatus.values().length, StatusCodeConverter.STATUS_MAP.size());
        // make sure values are all EntityStatus
        assertTrue(StatusCodeConverter.STATUS_MAP.values().containsAll(Arrays.asList(EntityStatus.values())));
        // make sure keys are all string
        String[] keys = (String[]) StatusCodeConverter.STATUS_MAP.keySet().toArray(new String[StatusCodeConverter.STATUS_MAP.size()]);

        for (String s : keys) {
            assertEquals(s.toLowerCase(), s);
        }
    }

    @Test
    public void testConvertToStatusEnum() {
        Cd iso = null;
        EntityStatus result = StatusCodeConverter.convertToStatusEnum(iso);
        assertNull(result);

        iso = new Cd();
        try {
            StatusCodeConverter.convertToStatusEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setNullFlavor(NullFlavor.NI);

        result = StatusCodeConverter.convertToStatusEnum(iso);
        assertNull(result);


        iso = new Cd();
        iso.setCode("foo");
        try {
            StatusCodeConverter.convertToStatusEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setCode("inactive");
        EntityStatus expResult = EntityStatus.INACTIVE;
        result = StatusCodeConverter.convertToStatusEnum(iso);
        assertEquals(expResult, result);

        //case insensitive mapping
        iso.setCode("ActivE");
        assertFalse(StatusCodeConverter.STATUS_MAP.containsKey(iso.getCode()));
        expResult = EntityStatus.ACTIVE;
        result = StatusCodeConverter.convertToStatusEnum(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToCd() {
        EntityStatus cs = null;
        Cd result = StatusCodeConverter.convertToCd(cs);
        assertNotNull(result.getNullFlavor());

        cs = EntityStatus.PENDING;
        result = StatusCodeConverter.convertToCd(cs);
        assertNull(result.getNullFlavor());
        assertEquals("pending", result.getCode());
    }

    @Test
    public void testCdConverter() {
        Cd iso = new Cd();
        iso.setCode("nullified");

        StatusCodeConverter.CdConverter cvt = new StatusCodeConverter.CdConverter();
        try {
            cvt.convert(java.net.URI.class, iso);
            fail();
        } catch(UnsupportedOperationException e){
        }


        EntityStatus result = cvt.convert(EntityStatus.class, iso);
        EntityStatus expected = EntityStatus.NULLIFIED;
        assertEquals(expected, result);

    }

    @Test
    public void testEnumConverter() {
        EntityStatus code = EntityStatus.ACTIVE;
        StatusCodeConverter.EnumConverter cvt = new StatusCodeConverter.EnumConverter();
        try {
            cvt.convert(java.net.URI.class, code);
            fail();
        } catch(UnsupportedOperationException e){
        }

        Cd result = cvt.convert(Cd.class, code);
        assertEquals("active", result.getCode());
    }

}