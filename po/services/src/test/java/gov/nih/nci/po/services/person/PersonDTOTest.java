package gov.nih.nci.po.services.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RaceCode;
import gov.nih.nci.po.data.convert.IdConverter.PersonIdConverter;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.person.PersonDTO;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

public class PersonDTOTest {
    PersonDTO dto;

    @Before
    public void setup() {
        dto = new PersonDTO();
    }

    @Test
    public void convertIdentifier() {
        dto.setIdentifier(new PersonIdConverter().convertToIi(1l));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(new Long(1), p.getId());
    }

    @Test
    public void convertEnPn1() {
        EnPn name = new EnPn();
        name.setNullFlavor(NullFlavor.NI);
        dto.setName(name);
        Person p = PoXsnapshotHelper.createModel(dto);
        assertNull(p.getFirstName());
        assertNull(p.getLastName());
        assertNull(p.getMiddleName());
        assertNull(p.getSuffix());
        assertNull(p.getPrefix());
    }

    @Test
    public void convertEnPn2() {
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", "m", "a", "c", "d"));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals("b", p.getFirstName());
        assertEquals("a", p.getLastName());
        assertEquals("m", p.getMiddleName());
        assertEquals("c", p.getPrefix());
        assertEquals("d", p.getSuffix());
    }

    @Test
    public void convertEnPn3() {
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", null, null, null, null));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals("b", p.getFirstName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn4() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, "c", null, null, null));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals("c", p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }
    
    @Test
    public void convertEnPnOneGivenNameMeansFirstName() {
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", "c", null, null, null));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals("b", p.getFirstName());
        assertEquals("c", p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn5() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, null, "d", null, null));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(null, p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals("d", p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn6() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, null, null, "e", null));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(null, p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals("e", p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn7() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, null, null, null, "f"));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(null, p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals("f", p.getSuffix());
    }

    @Test
    public void covertRaceCode1() {
        DSet<Cd> raceCode = new DSet<Cd>();
        raceCode.setItem(new HashSet<Cd>());
        Cd race1 = new Cd();
        race1.setCode("01");
        raceCode.getItem().add(race1);
        dto.setRaceCode(raceCode);
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(RaceCode.WH, p.getRaces().iterator().next());
        assertEquals(1, p.getRaces().size());
    }
    
    @Test
    public void covertRaceCode2() {
        DSet<Cd> raceCode = new DSet<Cd>();
        raceCode.setItem(new HashSet<Cd>());
        Cd race1 = new Cd();
        race1.setCode("01");
        raceCode.getItem().add(race1);
        Cd race2 = new Cd();
        race2.setCode("03");
        raceCode.getItem().add(race2);
        dto.setRaceCode(raceCode);
        Person p = PoXsnapshotHelper.createModel(dto);
        
        assertEquals(2, p.getRaces().size());
        assertTrue(p.getRaces().contains(RaceCode.WH));
        assertTrue(p.getRaces().contains(RaceCode.WH));
    }
    
    @Test
    public void covertRaceCode3() {
        DSet<Cd> raceCode = new DSet<Cd>();
        raceCode.setItem(new HashSet<Cd>());
        Cd race1 = new Cd();
        race1.setCode("01");
        raceCode.getItem().add(race1);
        Cd race2 = new Cd();
        race2.setCode("03");
        raceCode.getItem().add(race2);
        Cd race3 = new Cd();
        race3.setNullFlavor(NullFlavor.ASKU);
        raceCode.getItem().add(race3);
        dto.setRaceCode(raceCode);
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(2, p.getRaces().size());
        assertTrue(p.getRaces().contains(RaceCode.WH));
        assertTrue(p.getRaces().contains(RaceCode.B_AA));
    }
    
    @Test
    public void covertRaceCode4() {
        DSet<Cd> raceCode = new DSet<Cd>();
        raceCode.setItem(new HashSet<Cd>());
        Cd race3 = new Cd();
        race3.setNullFlavor(NullFlavor.ASKU);
        raceCode.getItem().add(race3);
        dto.setRaceCode(raceCode);
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals(0, p.getRaces().size());
    }

}
