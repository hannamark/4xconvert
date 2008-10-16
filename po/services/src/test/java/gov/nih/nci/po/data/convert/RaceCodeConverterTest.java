package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.RaceCode;
import gov.nih.nci.services.PoIsoConstraintException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class RaceCodeConverterTest {

    @Test
    public void testConvertToRaceCode() {
        DSet<Cd> d = new DSet<Cd>();
        HashSet<Cd> s = new HashSet<Cd>();
        d.setItem(s);
        for (RaceCode sc : RaceCode.values()) {
            Cd cd = new Cd();
            cd.setCode(sc.getValue());
            s.add(cd);
        }
        Set<RaceCode> result = RaceCodeConverter.convertToRaceCodeSet(d);
        assertEquals(s.size(), result.size());
        assertTrue(result.containsAll(Arrays.asList(RaceCode.values())));
    }

    @Test
    public void testConvertToCd() {
        Set<RaceCode> s = new HashSet<RaceCode>();
        s.addAll(Arrays.asList(RaceCode.values()));
        DSet<Cd> result = RaceCodeConverter.convertToDsetOfCd(s);
        assertEquals(s.size(), result.getItem().size());
        for(Cd cd : result.getItem()) {
            assertTrue(s.contains(RaceCodeConverter.STATUS_MAP.get(cd.getCode())));
        }
    }
    
    @Test
    public void testCdConverter() {
        RaceCodeConverter.DSetCdConverter c = new RaceCodeConverter.DSetCdConverter();
        
        assertNull(c.convert(Set.class, null));
        
        DSet<Cd> d = new DSet<Cd>();
        HashSet<Cd> s = new HashSet<Cd>();
        d.setItem(s);
        Set<RaceCode> result = c.convert(Set.class, d);
        assertNull(result);
        
        Cd cd = new Cd();
        cd.setNullFlavor(NullFlavor.NA);
        s.add(cd);
        result = c.convert(Set.class, d);
        assertEquals(0, result.size());
        
        cd.setNullFlavor(null);
        cd.setCode("");
        try {
            c.convert(Set.class, d);
            fail();
        } catch(PoIsoConstraintException ex) {
        }
        
        cd.setCode(null);
        try {
            c.convert(Set.class, d);
            fail();
        } catch(PoIsoConstraintException ex) {
        }
        
        cd.setCode(RaceCode.AI_AN.getValue());
        result = c.convert(Set.class, d);
        assertEquals(RaceCode.AI_AN, result.iterator().next());
        
        try {
            c.convert(Thread.class, d);
            fail();
        } catch(UnsupportedOperationException ex) {
        }
        
        s.clear();
        s.add(null);        
        assertTrue(c.convert(Set.class, d).isEmpty());
    }
    
    @Test
    public void testEnumConverter() {
        RaceCodeConverter.EnumConverter c = new RaceCodeConverter.EnumConverter();
        
        Set<RaceCode> s = null;
        DSet<Cd> result = c.convert(DSet.class, null);
        assertNull(result);

        s = new HashSet<RaceCode>();
        try {
            c.convert(Thread.class, s);
            fail();
        } catch(UnsupportedOperationException ex) {
        }
        
        result = c.convert(DSet.class, s);
        assertNull(result);
        
        s.add(RaceCode.AI_AN);
        result = c.convert(DSet.class, s);
        assertEquals(1, result.getItem().size());
        assertEquals(RaceCode.AI_AN.getValue(), result.getItem().iterator().next().getCode());
        
        s.clear();
        s.add(null);
        result = c.convert(DSet.class, s);
        assertEquals(1, result.getItem().size());
        assertNotNull(result.getItem().iterator().next().getNullFlavor());
    }

}