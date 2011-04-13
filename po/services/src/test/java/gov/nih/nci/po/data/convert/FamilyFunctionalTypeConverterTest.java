package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Arrays;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class FamilyFunctionalTypeConverterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMap() {
        assertEquals(FamilyFunctionalType.values().length, FamilyFunctionalTypeConverter.STATUS_MAP.size());
        // make sure values are all FamilyFunctionalType
        assertTrue(FamilyFunctionalTypeConverter.STATUS_MAP.values().containsAll(Arrays.asList(FamilyFunctionalType.values())));
        // make sure keys are all string
        String[] keys = (String[]) FamilyFunctionalTypeConverter.STATUS_MAP.keySet().toArray(new String[FamilyFunctionalTypeConverter.STATUS_MAP.size()]);

        for (String s : keys) {
            assertEquals(s.toLowerCase(), s);
        }
    }

    @Test(expected = PoIsoConstraintException.class)
    public void testConvertToStatusEnumEmptyCd() {
        Cd iso = new Cd();
        FamilyFunctionalTypeConverter.convertToTypeEnum(iso);
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertToStatusEnumWrongCode() {
        Cd iso = new Cd();
        iso.setCode("foo");
        FamilyFunctionalTypeConverter.convertToTypeEnum(iso);
    }
    
    @Test
    public void testConvertToStatusEnum() {
        Cd iso = null;
        FamilyFunctionalType result = FamilyFunctionalTypeConverter.convertToTypeEnum(iso);
        assertNull(result);

        iso = new Cd();
        iso.setNullFlavor(NullFlavor.NI);

        result = FamilyFunctionalTypeConverter.convertToTypeEnum(iso);
        assertNull(result);
        iso = new Cd();
        iso.setCode("affiliation");
        FamilyFunctionalType expResult = FamilyFunctionalType.AFFILIATION;
        result = FamilyFunctionalTypeConverter.convertToTypeEnum(iso);
        assertEquals(expResult, result);

        //case insensitive mapping
        iso.setCode("ORganiZational");
        assertFalse(FamilyFunctionalTypeConverter.STATUS_MAP.containsKey(iso.getCode()));
        expResult = FamilyFunctionalType.ORGANIZATIONAL;
        result = FamilyFunctionalTypeConverter.convertToTypeEnum(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToCd() {
        FamilyFunctionalType cs = null;
        Cd result = FamilyFunctionalTypeConverter.convertToCd(cs);
        assertNotNull(result.getNullFlavor());

        cs = FamilyFunctionalType.ORGANIZATIONAL;
        result = FamilyFunctionalTypeConverter.convertToCd(cs);
        assertNull(result.getNullFlavor());
        assertEquals("organizational", result.getCode());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testCdConverterBadClass() {
        Cd iso = new Cd();
        iso.setCode("contractual");
        FamilyFunctionalTypeConverter.CdConverter cvt = new FamilyFunctionalTypeConverter.CdConverter();
        cvt.convert(java.net.URI.class, iso);
    }


    @Test
    public void testCdConverter() {
        Cd iso = new Cd();
        iso.setCode("contractual");
        FamilyFunctionalTypeConverter.CdConverter cvt = new FamilyFunctionalTypeConverter.CdConverter();
        FamilyFunctionalType result = cvt.convert(FamilyFunctionalType.class, iso);
        FamilyFunctionalType expected = FamilyFunctionalType.CONTRACTUAL;
        assertEquals(expected, result);
    }

    @Test
    public void testEnumConverter() {
        FamilyFunctionalType code = FamilyFunctionalType.AFFILIATION;
        FamilyFunctionalTypeConverter.EnumConverter cvt = new FamilyFunctionalTypeConverter.EnumConverter();
        Cd result = cvt.convert(Cd.class, code);
        assertEquals("affiliation", result.getCode());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testEnumConverterBadClass() {
        FamilyFunctionalType code = FamilyFunctionalType.AFFILIATION;
        FamilyFunctionalTypeConverter.EnumConverter cvt = new FamilyFunctionalTypeConverter.EnumConverter();
        cvt.convert(java.net.URI.class, code);
    }


}