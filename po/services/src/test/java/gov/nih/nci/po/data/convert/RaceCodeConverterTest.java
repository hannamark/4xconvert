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
import gov.nih.nci.po.data.bo.PersonRace;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 *
 * @author mshestopalov
 */
public class RaceCodeConverterTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testMap() {
        assertEquals(PersonRace.values().length, RaceCodeConverter.STATUS_MAP.size());
        // make sure values are all PersonRace
        assertTrue(RaceCodeConverter.STATUS_MAP.values().containsAll(Arrays.asList(PersonRace.values())));
        // make sure keys are all string
        String[] keys = (String[]) RaceCodeConverter.STATUS_MAP.keySet().toArray(new String[RaceCodeConverter.STATUS_MAP.size()]);

        for (String s : keys) {
            assertEquals(s.toLowerCase(), s);
        }
    }

    @Test
    public void testConvertToStatusEnum() {
        Cd iso = null;
        PersonRace result = RaceCodeConverter.convertToRaceEnum(iso);
        assertNull(result);

        iso = new Cd();
        try {
            RaceCodeConverter.convertToRaceEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setNullFlavor(NullFlavor.NI);

        result = RaceCodeConverter.convertToRaceEnum(iso);
        assertNull(result);


        iso = new Cd();
        iso.setCode("foo");
        try {
            RaceCodeConverter.convertToRaceEnum(iso);
            fail();
        } catch(PoIsoConstraintException x) {
        }

        iso.setCode("white");
        PersonRace expResult = PersonRace.WHITE;
        result = RaceCodeConverter.convertToRaceEnum(iso);
        assertEquals(expResult, result);

        //case insensitive mapping
        iso.setCode("WhitE");
        assertFalse(RaceCodeConverter.STATUS_MAP.containsKey(iso.getCode()));
        expResult = PersonRace.WHITE;
        result = RaceCodeConverter.convertToRaceEnum(iso);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertToCd() {
        PersonRace cs = null;
        Cd result = RaceCodeConverter.convertToCd(cs);
        assertNotNull(result.getNullFlavor());

        cs = PersonRace.ASIAN;
        result = RaceCodeConverter.convertToCd(cs);
        assertNull(result.getNullFlavor());
        assertEquals("asian", result.getCode());
    }

    @Test
    public void testCdConverter() {
        Cd iso = new Cd();
        iso.setCode("black_or_african_american");
        DSet<Cd> dset = new DSet<Cd>();
        dset.setItem(new HashSet<Cd>());
        dset.getItem().add(iso);

        RaceCodeConverter.DSetConverter cvt = new RaceCodeConverter.DSetConverter();
        try {
            cvt.convert(java.net.URI.class, dset);
            fail();
        } catch(UnsupportedOperationException e){
        }


        Set<PersonRace> result = cvt.convert(Set.class, dset);
        PersonRace expected = PersonRace.BLACK_OR_AFRICAN_AMERICAN;
        assertEquals(expected, result.iterator().next());

    }

    @Test
    public void testEnumConverter() {
        PersonRace code = PersonRace.BLACK_OR_AFRICAN_AMERICAN;
        Set<PersonRace> pset = new HashSet<PersonRace>();
        pset.add(code);
        RaceCodeConverter.EnumConverter cvt = new RaceCodeConverter.EnumConverter();
        try {
            cvt.convert(java.net.URI.class, pset);
            fail();
        } catch(UnsupportedOperationException e){
        }

        DSet<Cd> result = cvt.convert(DSet.class, pset);
        assertEquals("black_or_african_american", result.getItem().iterator().next().getCode());
    }

}
