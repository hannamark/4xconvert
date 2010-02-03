package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.PersonSex;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Arrays;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class SexCodeConverterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMap() {
        assertEquals(PersonSex.values().length, SexCodeConverter.STATUS_MAP.size());
        // make sure values are all PersonSex
        assertTrue(SexCodeConverter.STATUS_MAP.values().containsAll(Arrays.asList(PersonSex.values())));
        // make sure keys are all string
        String[] keys = (String[]) SexCodeConverter.STATUS_MAP.keySet().toArray(new String[SexCodeConverter.STATUS_MAP.size()]);

        for (String s : keys) {
            assertEquals(s.toLowerCase(), s);
        }
    }

    @Test
    public void testConvertToStatusEnum() {
        Cd iso = null;
        PersonSex result = SexCodeConverter.convertToStatusEnum(iso);
        assertNull(result);

        iso = new Cd();
        try {
            SexCodeConverter.convertToStatusEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setNullFlavor(NullFlavor.NI);

        result = SexCodeConverter.convertToStatusEnum(iso);
        assertNull(result);


        iso = new Cd();
        iso.setCode("foo");
        try {
            SexCodeConverter.convertToStatusEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setCode("male");
        PersonSex expResult = PersonSex.MALE;
        result = SexCodeConverter.convertToStatusEnum(iso);
        assertEquals(expResult, result);

        //case insensitive mapping
        iso.setCode("MalE");
        assertFalse(SexCodeConverter.STATUS_MAP.containsKey(iso.getCode()));
        expResult = PersonSex.MALE;
        result = SexCodeConverter.convertToStatusEnum(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToCd() {
        PersonSex cs = null;
        Cd result = SexCodeConverter.convertToCd(cs);
        assertNotNull(result.getNullFlavor());

        cs = PersonSex.FEMALE;
        result = SexCodeConverter.convertToCd(cs);
        assertNull(result.getNullFlavor());
        assertEquals("female", result.getCode());
    }

    @Test
    public void testCdConverter() {
        Cd iso = new Cd();
        iso.setCode("unknown");

        SexCodeConverter.CdConverter cvt = new SexCodeConverter.CdConverter();
        try {
            cvt.convert(java.net.URI.class, iso);
            fail();
        } catch(UnsupportedOperationException e){
        }


        PersonSex result = cvt.convert(PersonSex.class, iso);
        PersonSex expected = PersonSex.UNKNOWN;
        assertEquals(expected, result);

    }

    @Test
    public void testEnumConverter() {
        PersonSex code = PersonSex.MALE;
        SexCodeConverter.EnumConverter cvt = new SexCodeConverter.EnumConverter();
        try {
            cvt.convert(java.net.URI.class, code);
            fail();
        } catch(UnsupportedOperationException e){
        }

        Cd result = cvt.convert(Cd.class, code);
        assertEquals("male", result.getCode());
    }

}