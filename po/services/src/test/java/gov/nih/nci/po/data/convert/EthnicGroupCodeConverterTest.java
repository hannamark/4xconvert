package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.PersonEthnicGroup;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 *
 * @author mshestopalov
 */
public class EthnicGroupCodeConverterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMap() {
        assertEquals(PersonEthnicGroup.values().length, EthnicGroupCodeConverter.STATUS_MAP.size());
        // make sure values are all PersonEthnic
        assertTrue(EthnicGroupCodeConverter.STATUS_MAP.values().containsAll(Arrays.asList(PersonEthnicGroup.values())));
        // make sure keys are all string
        String[] keys = (String[]) EthnicGroupCodeConverter.STATUS_MAP.keySet().toArray(new String[EthnicGroupCodeConverter.STATUS_MAP.size()]);

        for (String s : keys) {
            assertEquals(s.toLowerCase(), s);
        }
    }

    @Test
    public void testConvertToStatusEnum() {
        Cd iso = null;
        PersonEthnicGroup result = EthnicGroupCodeConverter.convertToEthnicGroupEnum(iso);
        assertNull(result);

        iso = new Cd();
        try {
            EthnicGroupCodeConverter.convertToEthnicGroupEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setNullFlavor(NullFlavor.NI);

        result = EthnicGroupCodeConverter.convertToEthnicGroupEnum(iso);
        assertNull(result);


        iso = new Cd();
        iso.setCode("foo");
        try {
            EthnicGroupCodeConverter.convertToEthnicGroupEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setCode("hispanic_or_latino");
        PersonEthnicGroup expResult = PersonEthnicGroup.HISPANIC_OR_LATINO;
        result = EthnicGroupCodeConverter.convertToEthnicGroupEnum(iso);
        assertEquals(expResult, result);

        //case insensitive mapping
        iso.setCode("Hispanic_or_latinO");
        assertFalse(EthnicGroupCodeConverter.STATUS_MAP.containsKey(iso.getCode()));
        expResult = PersonEthnicGroup.HISPANIC_OR_LATINO;
        result = EthnicGroupCodeConverter.convertToEthnicGroupEnum(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToCd() {
        PersonEthnicGroup cs = null;
        Cd result = EthnicGroupCodeConverter.convertToCd(cs);
        assertNotNull(result.getNullFlavor());

        cs = PersonEthnicGroup.HISPANIC_OR_LATINO;
        result = EthnicGroupCodeConverter.convertToCd(cs);
        assertNull(result.getNullFlavor());
        assertEquals("hispanic_or_latino", result.getCode());
    }

    @Test
    public void testCdConverter() {
        Cd iso = new Cd();
        iso.setCode("hispanic_or_latino");
        DSet<Cd> dset = new DSet<Cd>();
        dset.setItem(new HashSet<Cd>());
        dset.getItem().add(iso);

        EthnicGroupCodeConverter.DSetConverter cvt = new EthnicGroupCodeConverter.DSetConverter();
        try {
            cvt.convert(java.net.URI.class, dset);
            fail();
        } catch(UnsupportedOperationException e){
        }


        Set<PersonEthnicGroup> result = cvt.convert(Set.class, dset);
        PersonEthnicGroup expected = PersonEthnicGroup.HISPANIC_OR_LATINO;
        assertEquals(expected, result.iterator().next());

    }

    @Test
    public void testEnumConverter() {
        PersonEthnicGroup code = PersonEthnicGroup.NOT_HISPANIC_OR_LATINO;
        Set<PersonEthnicGroup> pset = new HashSet<PersonEthnicGroup>();
        pset.add(code);
        EthnicGroupCodeConverter.EnumConverter cvt = new EthnicGroupCodeConverter.EnumConverter();
        try {
            cvt.convert(java.net.URI.class, pset);
            fail();
        } catch(UnsupportedOperationException e){
        }

        DSet<Cd> result = cvt.convert(DSet.class, pset);
        assertEquals("not_hispanic_or_latino", result.getItem().iterator().next().getCode());
    }

}
