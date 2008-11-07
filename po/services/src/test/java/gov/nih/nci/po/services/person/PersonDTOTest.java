package gov.nih.nci.po.services.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAdl;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RaceCode;
import gov.nih.nci.po.data.bo.SexCode;
import gov.nih.nci.po.data.convert.IdConverter.PersonIdConverter;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.util.MockCountryServiceLocator;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.services.PoIsoConstraintException;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonDTOTest {
    PersonDTO dto;
    ServiceLocator oldLocator = null;

    @Before
    public void setup() {
        dto = new PersonDTO();
        oldLocator = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(new MockCountryServiceLocator());
    }

    @After
    public void after() {
        PoRegistry.getInstance().setServiceLocator(oldLocator);
    }

    @Test
    public void convertIdentifier() {
        dto.setIdentifier(new PersonIdConverter().convertToIi(1l));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(new Long(1), p.getId());
    }

    @Test
    public void convertEnPn1() {
        EnPn name = new EnPn();
        name.setNullFlavor(NullFlavor.NI);
        dto.setName(name);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertNull(p.getFirstName());
        assertNull(p.getLastName());
        assertNull(p.getMiddleName());
        assertNull(p.getSuffix());
        assertNull(p.getPrefix());
    }

    @Test
    public void convertEnPn2() {
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", "m", "a", "c", "d"));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals("b", p.getFirstName());
        assertEquals("a", p.getLastName());
        assertEquals("m", p.getMiddleName());
        assertEquals("c", p.getPrefix());
        assertEquals("d", p.getSuffix());
    }

    @Test
    public void convertEnPn3() {
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", null, null, null, null));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals("b", p.getFirstName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn4() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, "c", null, null, null));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals("c", p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPnOneGivenNameMeansFirstName() {
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", "c", null, null, null));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals("b", p.getFirstName());
        assertEquals("c", p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn5() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, null, "d", null, null));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(null, p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals("d", p.getLastName());
        assertEquals(null, p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn6() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, null, null, "e", null));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(null, p.getFirstName());
        assertEquals(null, p.getMiddleName());
        assertEquals(null, p.getLastName());
        assertEquals("e", p.getPrefix());
        assertEquals(null, p.getSuffix());
    }

    @Test
    public void convertEnPn7() {
        dto.setName(PersonNameConverterUtil.convertToEnPn(null, null, null, null, "f"));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
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
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
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
        Person p = (Person) PoXsnapshotHelper.createModel(dto);

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
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
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
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(0, p.getRaces().size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void covertRaceCodeCdCodeEmpty() {
        dto.setRaceCode(new DSet<Cd>());
        dto.getRaceCode().setItem(new HashSet<Cd>());
        Cd race = new Cd();
        race.setCode("");
        dto.getRaceCode().getItem().add(race);
        try {
            PoXsnapshotHelper.createModel(dto);
            fail();
        } catch (PoIsoConstraintException e) {
            assertEquals("code must be set", e.getMessage());
        }
    }
    @Test
    @SuppressWarnings("unchecked")
    public void covertRaceCodeCdCodeNull() {
        dto.setRaceCode(new DSet<Cd>());
        dto.getRaceCode().setItem(new HashSet<Cd>());
        Cd race = new Cd();
        race.setCode(null);
        dto.getRaceCode().getItem().add(race);
        try {
            PoXsnapshotHelper.createModel(dto);
            fail();
        } catch (PoIsoConstraintException e) {
            assertEquals("code must be set", e.getMessage());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void covertRaceCodeCdHasNullFlavor() {
        dto.setRaceCode(new DSet<Cd>());
        dto.getRaceCode().setItem(new HashSet<Cd>());
        Cd race = new Cd();
        race.setNullFlavor(NullFlavor.ASKU);
        dto.getRaceCode().getItem().add(race);
        Person p;
        p = (Person) PoXsnapshotHelper.createModel(dto);
        assertTrue(p.getRaces().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void covertRaceCodeUnsupportedCode() {
        dto.setRaceCode(new DSet<Cd>());
        dto.getRaceCode().setItem(new HashSet<Cd>());
        Cd race = new Cd();
        race.setCode("Z");
        dto.getRaceCode().getItem().add(race);
        try {
            PoXsnapshotHelper.createModel(dto);
            fail();
        } catch (PoIsoConstraintException e) {
            assertEquals("unsupported code " + race.getCode(), e.getMessage());
        }
    }

    @Test
    public void covertSexCode1() {
        Cd sex = new Cd();
        sex.setCode("F");
        dto.setSexCode(sex);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(SexCode.F, p.getSex());
    }

    @Test
    public void covertSexCode2() {
        Cd sex = new Cd();
        sex.setCode("M");
        dto.setSexCode(sex);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(SexCode.M, p.getSex());
    }

    @Test
    public void covertSexCode3() {
        Cd sex = new Cd();
        sex.setCode("U");
        dto.setSexCode(sex);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(SexCode.U, p.getSex());
    }

    @Test
    public void covertSexCode4() {
        Cd sex = new Cd();
        sex.setCode("un");
        dto.setSexCode(sex);
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals(SexCode.UN, p.getSex());
    }

    @Test
    public void covertSexCodeCdCodeEmpty() {
        Cd sex = new Cd();
        sex.setCode("");
        dto.setSexCode(sex);
        try {
            PoXsnapshotHelper.createModel(dto);
            fail();
        } catch (PoIsoConstraintException e) {
            assertEquals("code must be set", e.getMessage());
        }
    }
    @Test
    public void covertSexCodeCdCodeNull() {
        Cd sex = new Cd();
        sex.setCode(null);
        dto.setSexCode(sex);
        try {
            PoXsnapshotHelper.createModel(dto);
            fail();
        } catch (PoIsoConstraintException e) {
            assertEquals("code must be set", e.getMessage());
        }
    }
    @Test
    public void covertSexCodeCdHasNullFlavor() {
        Cd sex = new Cd();
        sex.setNullFlavor(NullFlavor.ASKU);
        dto.setSexCode(sex);
        Person p;
        p = (Person) PoXsnapshotHelper.createModel(dto);
        assertNull(p.getSex());
    }

    @Test
    public void covertSexCodeUnsupportedCode() {
        Cd sex = new Cd();
        sex.setCode("Z");
        dto.setSexCode(sex);
        try {
            PoXsnapshotHelper.createModel(dto);
            fail();
        } catch (PoIsoConstraintException e) {
            assertEquals("unsupported code " + sex.getCode(), e.getMessage());
        }
    }

    /**
     * This is the data from the CTEP mapping document.  If this test does not pass, we need
     * to talk to CTEP about why the data they are providing isn't enough for us to persist.
     *
     * @throws Exception on error
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testCTEPDataset() throws Exception {
        dto.setIdentifier(new Ii());
        dto.getIdentifier().setDisplayable(Boolean.TRUE);
        dto.getIdentifier().setExtension("30");
        dto.getIdentifier().setIdentifierName("CTEP_IDENTIFIER");
        dto.getIdentifier().setRoot("CTEP_ROOT");

        Ad ad = new Ad();
        List<Adxp> part = new ArrayList<Adxp>();
        ad.setPart(part);

        AdxpAl al = new AdxpAl();
        al.setValue("777 Preston Research Building");
        part.add(al);

        AdxpAdl adl = new AdxpAdl();
        adl.setValue("Division of Hematology/Oncology");
        part.add(adl);

        AdxpCty cty = new AdxpCty();
        cty.setValue("Nashville");
        part.add(cty);

        AdxpSta sta = new AdxpSta();
        sta.setValue("TN");
        part.add(sta);

        AdxpZip zip = new AdxpZip();
        zip.setValue("37232-6307");
        part.add(zip);

        AdxpCnt cnt = new AdxpCnt();
        cnt.setValue("United States");
        cnt.setCode("USA");
        part.add(cnt);

        dto.setPostalAddress(ad);

        Cd statusCode = new Cd();
        statusCode.setCode("active");
        dto.setStatusCode(statusCode);

        dto.setRaceCode(null);
        dto.setSexCode(null); // TODO

        EnPn pn = new EnPn();
        List<Enxp> part2 = pn.getPart();

        Enxp enxp = new Enxp(EntityNamePartType.FAM);
        enxp.setValue("Sosman");
        part2.add(enxp);

        enxp = new Enxp(EntityNamePartType.GIV);
        enxp.setValue("Jeffrey");
        part2.add(enxp);

        enxp = new Enxp(EntityNamePartType.GIV);
        enxp.setValue("A.");
        part2.add(enxp);

        enxp = new Enxp(EntityNamePartType.PFX);
        enxp.setValue("Dr.");
        part2.add(enxp);

        dto.setName(pn);

        DSet<Tel> telecomAddresses = new DSet<Tel>();
        telecomAddresses.setItem(new HashSet<Tel>());
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:jeff.sosman@vanderbilt.edu"));
        telecomAddresses.getItem().add(email);

        TelPhone phone = new TelPhone();
        phone.setValue(new URI("x-text-fax:(615)%20343-7602"));
        telecomAddresses.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("tel:(615)%20322-4967"));
        telecomAddresses.getItem().add(phone);

        dto.setTelecomAddress(telecomAddresses);

        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertNotNull(p);
        assertTrue(PoHibernateUtil.validate(p).isEmpty());
        assertEquals(30, p.getId().longValue());
        assertEquals("777 Preston Research Building", p.getPostalAddress().getStreetAddressLine());
        assertEquals("Division of Hematology/Oncology", p.getPostalAddress().getDeliveryAddressLine());
        assertEquals("Nashville", p.getPostalAddress().getCityOrMunicipality());
        assertEquals("TN", p.getPostalAddress().getStateOrProvince());
        assertEquals("USA", p.getPostalAddress().getCountry().getAlpha3());
        assertEquals("37232-6307", p.getPostalAddress().getPostalCode());
        assertEquals(EntityStatus.ACTIVE, p.getStatusCode());
        assertTrue(CollectionUtils.isEmpty(p.getRaces()));
        assertNull(p.getSex());
        assertEquals("Sosman", p.getLastName());
        assertEquals("Jeffrey", p.getFirstName());
        assertEquals("A.", p.getMiddleName());
        assertEquals("Dr.", p.getPrefix());
        assertEquals(1, p.getEmail().size());
        assertEquals("jeff.sosman@vanderbilt.edu", p.getEmail().get(0).getValue());
        assertEquals(1, p.getFax().size());
        assertEquals("(615) 343-7602", p.getFax().get(0).getValue()); // notice %20 converted to space
        assertEquals(1, p.getPhone().size());
        assertEquals("(615) 322-4967", p.getPhone().get(0).getValue()); // notice %20 converted to space
    }

    // regression test for https://jira.5amsolutions.com/browse/PO-601
    @Test
    public void roundTripIdentifier() {
        dto.setIdentifier(new PersonIdConverter().convertToIi(1l));
        Person bo = (Person) PoXsnapshotHelper.createModel(dto);
        PersonDTO copy = (PersonDTO) PoXsnapshotHelper.createSnapshot(bo);
        EqualsBuilder.reflectionEquals(dto.getIdentifier(), copy.getIdentifier());
    }
}
