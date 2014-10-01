package gov.nih.nci.services.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAdl;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonEthnicGroup;
import gov.nih.nci.po.data.bo.PersonRace;
import gov.nih.nci.po.data.bo.PersonSex;
import gov.nih.nci.po.data.convert.IdConverter.PersonIdConverter;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.util.MockCountryServiceLocator;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;

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
        dto.setIdentifier(new PersonIdConverter().convertToIi(1L));
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

    /**
     * This is the data from the CTEP mapping document.  If this test does not pass, we need
     * to talk to CTEP about why the data they are providing isn't enough for us to persist.
     *
     * @throws Exception on error
     */
    @Test
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
        cnt.setCodeSystem("ISO 3166-1 alpha-3 code");
        part.add(cnt);

        dto.setPostalAddress(ad);

        Cd statusCode = new Cd();
        statusCode.setCode("active");
        dto.setStatusCode(statusCode);

        Cd sexCode = new Cd();
        sexCode.setCode("MALE");
        dto.setSexCode(sexCode);

        Cd raceCode = new Cd();
        raceCode.setCode("white");
        DSet<Cd> raceCodes = new DSet<Cd>();
        raceCodes.setItem(new HashSet<Cd>());
        raceCodes.getItem().add(raceCode);
        Cd raceCode2 = new Cd();
        raceCode2.setCode("black_or_african_american");
        raceCodes.getItem().add(raceCode2);
        dto.setRaceCode(raceCodes);

        Cd ethnicCode = new Cd();
        ethnicCode.setCode("hispanic_or_latino");
        DSet<Cd> ethnicCodes = new DSet<Cd>();
        ethnicCodes.setItem(new HashSet<Cd>());
        ethnicCodes.getItem().add(ethnicCode);
        Cd ethnicCode2 = new Cd();
        ethnicCode2.setCode("not_hispanic_or_latino");
        ethnicCodes.getItem().add(ethnicCode2);
        dto.setEthnicGroupCode(ethnicCodes);

        Ts birthDate = new Ts();
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        birthDate.setValue(sdf.parse("09/28/1980"));
        dto.setBirthDate(birthDate);

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
        assertTrue(HibernateHelper.validate(p).isEmpty());
        assertEquals(30, p.getId().longValue());
        assertEquals("777 Preston Research Building", p.getPostalAddress().getStreetAddressLine());
        assertEquals("Division of Hematology/Oncology", p.getPostalAddress().getDeliveryAddressLine());
        assertEquals("Nashville", p.getPostalAddress().getCityOrMunicipality());
        assertEquals("TN", p.getPostalAddress().getStateOrProvince());
        assertEquals("USA", p.getPostalAddress().getCountry().getAlpha3());
        assertEquals("37232-6307", p.getPostalAddress().getPostalCode());
        assertEquals(EntityStatus.ACTIVE, p.getStatusCode());
        assertEquals(PersonSex.MALE, p.getSexCode());

        assertEquals(2, p.getRaceCode().size());
        assertTrue(p.getRaceCode().contains(PersonRace.WHITE));
        assertTrue(p.getRaceCode().contains(PersonRace.BLACK_OR_AFRICAN_AMERICAN));


        assertEquals(2, p.getEthnicGroupCode().size());
        assertTrue(p.getEthnicGroupCode().contains(PersonEthnicGroup.HISPANIC_OR_LATINO));
        assertTrue(p.getEthnicGroupCode().contains(PersonEthnicGroup.NOT_HISPANIC_OR_LATINO));
        assertEquals("09/28/1980", sdf.format(p.getBirthDate()));
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
        dto.setIdentifier(new PersonIdConverter().convertToIi(1L));
        Person bo = (Person) PoXsnapshotHelper.createModel(dto);
        PersonDTO copy = (PersonDTO) PoXsnapshotHelper.createSnapshot(bo);
        EqualsBuilder.reflectionEquals(dto.getIdentifier(), copy.getIdentifier());
    }
}
