package gov.nih.nci.po.services.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.ISOUtils;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceBeanTest extends PersonServiceBeanTest {

    private PersonEntityServiceRemote remote;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getPersonEntityServiceBeanAsRemote();
    }

    private String findValueByType(EnPn enpn, EntityNamePartType type) {
        for (Enxp part : enpn.getPart()) {
            if (part.getType().equals(type)) {
                return part.getValue();
            }
        }
        return null;
    }

    @Test
    public void getPerson() {
        Person per = makePerson();
        long id = (Long) PoHibernateUtil.getCurrentSession().save(per);
        PersonDTO result = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));
        assertEquals(per.getId().longValue(), Long.parseLong(result.getIdentifier().getExtension()));
        assertEquals(per.getFirstName(), findValueByType(result.getName(), EntityNamePartType.GIV));
        assertEquals(per.getLastName(), findValueByType(result.getName(), EntityNamePartType.FAM));
    }

    private Person makePerson() {
        Person p = new Person();
        p.setStatusCode(EntityStatus.NEW);
        p.setPrefix("Dr.");
        p.setFirstName("Dixie");
        p.setLastName("Tavela");

        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", getDefaultCountry());
        p.setPostalAddress(a);
        return p;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPersonRemoteException() throws EntityValidationException {
        PersonDTO dto = new PersonDTO();
        Ii isoId = new Ii();
        isoId.setRoot("test");
        isoId.setExtension("99");
        dto.setIdentifier(isoId);
        remote.createPerson(dto);
    }

    @Test
    public void createPersonRemote() throws EntityValidationException {
        PersonDTO dto = new PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue("Firsty");
        dto.getName().getPart().add(part);
        part = new Enxp(EntityNamePartType.FAM);
        part.setValue("McGillicutty");
        dto.getName().getPart().add(part);
        Address a = makePerson().getPostalAddress();
        PoHibernateUtil.getCurrentSession().save(a.getCountry());
        dto.setPostalAddress(AddressConverter.SimpleConverter.convertToAd(a));
        Ii id = remote.createPerson(dto);
        assertNotNull(id);
        assertNotNull(id.getExtension());
        Person p = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, IiConverter.convertToLong(id));
        assertEquals(findValueByType(dto.getName(), EntityNamePartType.FAM), p.getLastName());
        assertEquals(findValueByType(dto.getName(), EntityNamePartType.GIV), p.getFirstName());
    }

    @Test
    public void validate() {

        PersonDTO dto = new PersonDTO();
        Ii isoId = new Ii();
        isoId.setRoot("test");
        isoId.setExtension("99");
        dto.setIdentifier(isoId);
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue("Firsty");
        dto.getName().getPart().add(part);
        Map<String, String[]> errors = remote.validate(dto);
        assertEquals(2, errors.size());
        assertTrue(errors.containsKey("lastName"));
        assertTrue(errors.containsKey("postalAddress"));

        Ad add = new Ad();
        add.setPart(new ArrayList<Adxp>());
        Adxp apart = Adxp.createAddressPart(AddressPartType.STA);
        apart.setValue("My State");
        add.getPart().add(apart);
        dto.setPostalAddress(add);
        errors = remote.validate(dto);
        assertEquals(5, errors.size());
        assertTrue(errors.containsKey("lastName"));
        assertTrue(errors.containsKey("postalAddress.streetAddressLine"));
        assertTrue(errors.containsKey("postalAddress.cityOrMunicipality"));
        assertTrue(errors.containsKey("postalAddress.postalCode"));
        assertTrue(errors.containsKey("postalAddress.country"));
        assertFalse(errors.containsKey("postalAddress.stateOrProvince"));// for clarity

    }

    @Test
    public void findByFirstName() throws EntityValidationException {
        init();

        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "a", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(1, result.size());
    }

    @Test
    public void findByFirstNameWildcardAsFirstAndLast() throws EntityValidationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "%a%", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(1, result.size());
    }

    @Test
    public void findByFirstNameWildcardsInSitu() throws EntityValidationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "b%b", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void findByFirstNameWilcardAttempt1() throws EntityValidationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "?a?", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void findByFirstNameWilcardAttempt2() throws EntityValidationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "_a_", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void findByFirstNameNoMatches() throws EntityValidationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "foobar", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    private void init() throws EntityValidationException {
        PersonDTO dto = new PersonDTO();
        dto.setName(PersonNameConverterUtil.convertToEnPn("bab", "m", "a", "c", "d"));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals("a", p.getLastName());
        assertEquals("bab", p.getFirstName());
        assertEquals("m", p.getMiddleName());
        assertEquals("c", p.getPrefix());
        assertEquals("d", p.getSuffix());
        Address a = makePerson().getPostalAddress();
        PoHibernateUtil.getCurrentSession().save(a.getCountry());
        dto.setPostalAddress(AddressConverter.SimpleConverter.convertToAd(a));

        Ii id = remote.createPerson(dto);
        assertNotNull(id);
        assertNotNull(id.getExtension());

        PersonDTO personDTO = remote.getPerson(id);
        Person p2 = (Person) PoXsnapshotHelper.createModel(personDTO);
        assertEquals("a", p2.getLastName());
        assertEquals("bab", p2.getFirstName());
        assertEquals("m", p2.getMiddleName());
        assertEquals("c", p2.getPrefix());
        assertEquals("d", p2.getSuffix());
        assertNotNull(p2.getPostalAddress());
    }

    @Test
    public void findByMiddleName() throws EntityValidationException {
        init();

        PersonDTO crit = new PersonDTO();
        /* This is a flaw in the ISO to BO Conversion process regarding GIVEN NAMES */
        crit.setName(PersonNameConverterUtil.convertToEnPn("%", "m", null, null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(1, result.size());

        crit.setName(PersonNameConverterUtil.convertToEnPn(null, "foobar", null, null, null));
        result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void updatePerson() throws EntityValidationException, URISyntaxException {
        long id = super.createPerson();
        Person p = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);
        PersonDTO dto = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));
        assertEquals(EntityStatus.NEW, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        //dto.setName(StringConverter.convertToEnOn("newName"));
        Adxp adl = Adxp.createAddressPart(AddressPartType.ADL);
        adl.setValue("additional ADL");
        dto.getPostalAddress().getPart().add(adl);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().add(email);
        remote.updatePerson(dto);
        @SuppressWarnings("unchecked")
        List<PersonCR> l = PoHibernateUtil.getCurrentSession().createCriteria(PersonCR.class).list();
        assertEquals(1, l.size());
        PersonCR cr = l.get(0);
//        assertEquals("newName", cr.getName());
        assertEquals(p.getPostalAddress().getDeliveryAddressLine() + " additional ADL", cr.getPostalAddress().getDeliveryAddressLine());
        assertTrue(CollectionUtils.exists(cr.getEmail(), new Predicate(){
            public boolean evaluate(Object object) {
                return ((Email)object).getValue().equals("another.email@example.com");
            }
        }));
        assertEquals(EntityStatus.NEW, cr.getStatusCode());
    }

    @Test(expected=IllegalArgumentException.class)
    public void updatePersonChangeCtatus() throws EntityValidationException {
        long id = super.createPerson();
        PersonDTO dto = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));
        assertEquals(EntityStatus.NEW, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        dto.setStatusCode(StatusCodeConverter.convertToCd(EntityStatus.DEPRECATED));
        remote.updatePerson(dto);
    }

    @Test
    public void updatePersonStatus() throws EntityValidationException {
        long id = super.createPerson();
        Ii ii = ISOUtils.ID_PERSON.convertToIi(id);
        Cd newStatus = StatusCodeConverter.convertToCd(EntityStatus.DEPRECATED);
        remote.updatePersonStatus(ii, newStatus);
        @SuppressWarnings("unchecked")
        List<PersonCR> l = PoHibernateUtil.getCurrentSession().createCriteria(PersonCR.class).list();
        assertEquals(1, l.size());
        PersonCR cr = l.get(0);
        assertEquals(cr.getStatusCode(), EntityStatus.DEPRECATED);
    }
}
