package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Arrays;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class FamilyStatusCodeConverterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMap() {
        assertEquals(FamilyStatus.values().length, FamilyStatusCodeConverter.STATUS_MAP.size());
        // make sure values are all FamilyStatus
        assertTrue(FamilyStatusCodeConverter.STATUS_MAP.values().containsAll(Arrays.asList(FamilyStatus.values())));
        // make sure keys are all string
        String[] keys = (String[]) FamilyStatusCodeConverter.STATUS_MAP.keySet().toArray(new String[FamilyStatusCodeConverter.STATUS_MAP.size()]);

        for (String s : keys) {
            assertEquals(s.toLowerCase(), s);
        }
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertEmptyCd() {
        Cd iso = new Cd();
        FamilyStatusCodeConverter.convertToStatusEnum(iso);
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertCdBadCode() {
        Cd iso = new Cd();
        iso.setCode("foo");
        FamilyStatusCodeConverter.convertToStatusEnum(iso);
    }
    
    @Test
    public void testConvertToStatusEnum() {
        Cd iso = null;
        FamilyStatus result = FamilyStatusCodeConverter.convertToStatusEnum(iso);
        assertNull(result);

        iso = new Cd();
        iso.setNullFlavor(NullFlavor.NI);
        result = FamilyStatusCodeConverter.convertToStatusEnum(iso);
        assertNull(result);

        iso = new Cd();
        iso.setCode("inactive");
        FamilyStatus expResult = FamilyStatus.INACTIVE;
        result = FamilyStatusCodeConverter.convertToStatusEnum(iso);
        assertEquals(expResult, result);

        //case insensitive mapping
        iso.setCode("ActivE");
        assertFalse(FamilyStatusCodeConverter.STATUS_MAP.containsKey(iso.getCode()));
        expResult = FamilyStatus.ACTIVE;
        result = FamilyStatusCodeConverter.convertToStatusEnum(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToCd() {
        FamilyStatus cs = null;
        Cd result = FamilyStatusCodeConverter.convertToCd(cs);
        assertNotNull(result.getNullFlavor());

        cs = FamilyStatus.ACTIVE;
        result = FamilyStatusCodeConverter.convertToCd(cs);
        assertNull(result.getNullFlavor());
        assertEquals("active", result.getCode());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testCdConverterBadClass() {
        Cd iso = new Cd();
        iso.setCode("nullified");
        FamilyStatusCodeConverter.CdConverter cvt = new FamilyStatusCodeConverter.CdConverter();
        cvt.convert(java.net.URI.class, iso);
    }
    
    @Test
    public void testCdConverter() {
        Cd iso = new Cd();
        iso.setCode("nullified");
        FamilyStatusCodeConverter.CdConverter cvt = new FamilyStatusCodeConverter.CdConverter();
        FamilyStatus result = cvt.convert(FamilyStatus.class, iso);
        FamilyStatus expected = FamilyStatus.NULLIFIED;
        assertEquals(expected, result);

    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testEnumConverterBadClass() {
        FamilyStatus code = FamilyStatus.ACTIVE;
        FamilyStatusCodeConverter.EnumConverter cvt = new FamilyStatusCodeConverter.EnumConverter();
        cvt.convert(java.net.URI.class, code);   
    }
    
    @Test
    public void testEnumConverter() {
        FamilyStatus code = FamilyStatus.ACTIVE;
        FamilyStatusCodeConverter.EnumConverter cvt = new FamilyStatusCodeConverter.EnumConverter();
        Cd result = cvt.convert(Cd.class, code);
        assertEquals("active", result.getCode());
    }

}