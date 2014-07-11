package gov.nih.nci.services.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAdl;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Patient;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.data.bo.PersonEthnicGroup;
import gov.nih.nci.po.data.bo.PersonRace;
import gov.nih.nci.po.data.bo.PersonSex;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.DateConverter;
import gov.nih.nci.po.data.convert.EthnicGroupCodeConverter;
import gov.nih.nci.po.data.convert.ISOUtils;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.RaceCodeConverter;
import gov.nih.nci.po.data.convert.SexCodeConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.TsConverter;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.service.CountryTestUtil;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.correlation.PatientRemoteServiceTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.PatientDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceBean;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityServiceProvider.class)
public class PersonEntityServiceBeanTest extends PersonServiceBeanTest {

    private PersonEntityServiceRemote remote;

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getPersonEntityServiceBeanAsRemote();
    }


    @Before
    public void mockSecurity() {
        UserProvisioningManager userProvisioningManager = mock(UserProvisioningManager.class);
        when(userProvisioningManager.getUser(anyString())).thenAnswer(
                new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        return (User) PoHibernateUtil.getCurrentSession().createCriteria(User.class)
                                .add(Restrictions.eq("loginName", invocation.getArguments()[0])).uniqueResult();
                    }
                }
        );


        mockStatic(SecurityServiceProvider.class);
        try {
            PowerMockito.when(SecurityServiceProvider.getUserProvisioningManager("po")).thenReturn(userProvisioningManager);
        } catch (CSException e) {
            throw new RuntimeException(e);
        }
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
    public void getPerson() throws NullifiedEntityException {
        Person per = makePerson();
        long id = (Long) PoHibernateUtil.getCurrentSession().save(per);
        PersonDTO result = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));
        assertEquals(per.getId().longValue(), Long.parseLong(result.getIdentifier().getExtension()));
        assertEquals(per.getFirstName(), findValueByType(result.getName(), EntityNamePartType.GIV));
        assertEquals(per.getLastName(), findValueByType(result.getName(), EntityNamePartType.FAM));
    }

    @Test
    public void getPatient() throws NullifiedEntityException, ParseException {
        Patient pat = makePatient();

        Ii patIi = ISOUtils.ID_PERSON.convertToIi(pat.getId());
        patIi.setExtension("PT" + patIi.getExtension());
        PersonDTO result = remote.getPerson(patIi);
        // everything other than the status and 4 bio fields should be masked nullflavor
        assertEquals(gov.nih.nci.iso21090.NullFlavor.MSK, result.getName().getNullFlavor());
        assertEquals(gov.nih.nci.iso21090.NullFlavor.MSK, result.getPostalAddress().getNullFlavor());
        assertEquals(gov.nih.nci.iso21090.NullFlavor.MSK, (result.getTelecomAddress().getItem().iterator()
                .next()).getNullFlavor());
        assertEquals(RoleStatus.ACTIVE, CdConverter.convertToRoleStatus(result.getStatusCode()));

        // 4 prop fields
        assertEquals(IdConverter.PATIENT_PREFIX + pat.getId(), result.getIdentifier().getExtension());
        assertEquals(pat.getBirthDate(), TsConverter.convertToDate(result.getBirthDate()));
        assertEquals(pat.getSexCode(), SexCodeConverter.convertToStatusEnum(result.getSexCode()));
        assertEquals(pat.getEthnicGroupCode().iterator().next(), EthnicGroupCodeConverter
                .convertToEthnicGroupEnum((Cd) result.getEthnicGroupCode().getItem().iterator().next()));
        assertEquals(pat.getRaceCode().iterator().next(), RaceCodeConverter.convertToRaceEnum((Cd) result
                .getRaceCode().getItem().iterator().next()));
    }

    private Person makePerson() {
        Person p = new Person();
        p.setStatusCode(EntityStatus.PENDING);
        p.setPrefix("Dr.");
        p.setFirstName("Dixie");
        p.setLastName("Tavela");

        Address a = makeAddress();
        p.setPostalAddress(a);
        p.getEmail().add(new Email("abc@example.com"));
        p.getUrl().add(new URL("http://example.com"));
        return p;
    }

    private Patient makePatient() throws ParseException {
        Patient p = new Patient();
        p.setStatus(RoleStatus.ACTIVE);

        Address a = makeAddress();
        p.getPostalAddresses().add(a);
        p.getEmail().add(new Email("abc@example.com"));
        p.getUrl().add(new URL("http://example.com"));

        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        p.setBirthDate(sdf.parse("09/28/1980"));
        p.setSexCode(PersonSex.MALE);
        p.getEthnicGroupCode().add(PersonEthnicGroup.NOT_HISPANIC_OR_LATINO);
        p.getRaceCode().add(PersonRace.WHITE);

        Organization org = new Organization();
        org.setName("test org");
        org.setPostalAddress(makeAddress());
        org.setStatusCode(EntityStatus.ACTIVE);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        PoHibernateUtil.getCurrentSession().save(org);

        p.setScoper(org);
        PoHibernateUtil.getCurrentSession().save(p);
        return p;
    }

    private Address makeAddress() {
        Address a =
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
        return a;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPersonRemoteException() throws EntityValidationException, CurationException {
        PersonDTO dto = new PersonDTO();
        Ii isoId = new Ii();
        isoId.setRoot("test");
        isoId.setExtension("99");
        dto.setIdentifier(isoId);
        remote.createPerson(dto);
    }

    @Test
    public void createPersonRemote() throws EntityValidationException, URISyntaxException, CurationException {
        PersonDTO dto = createPersonWithNoPhones();
        Tel t = new Tel();
        String phone = "201-555-0123x4756";
        t.setValue(new URI("tel", phone, null));
        dto.getTelecomAddress().getItem().add(t);

        Ii id = remote.createPerson(dto);
        assertNotNull(id);
        assertNotNull(id.getExtension());
        Person p = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, IiConverter.convertToLong(id));
        assertEquals(findValueByType(dto.getName(), EntityNamePartType.FAM), p.getLastName());
        assertEquals(findValueByType(dto.getName(), EntityNamePartType.GIV), p.getFirstName());
        assertNotNull("CreatedBy not set on creation.", p.getCreatedBy());
        assertEquals(getUser(), p.getCreatedBy());
    }
    
    private PersonDTO createPersonWithNoPhones() throws URISyntaxException {
        PersonDTO dto = new PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue("Firsty");
        dto.getName().getPart().add(part);
        part = new Enxp(EntityNamePartType.FAM);
        part.setValue("McGillicutty");
        dto.getName().getPart().add(part);
        Address a = makeAddress();
        PoHibernateUtil.getCurrentSession().save(a.getCountry());
        dto.setPostalAddress(AddressConverter.SimpleConverter.convertToAd(a));

        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://example.com"));
        dto.getTelecomAddress().getItem().add(url);
        
        return dto;
    }
    
    @Test
    public void createPersonWithInvalidPhone() throws EntityValidationException, URISyntaxException, CurationException {
        PersonDTO dto = createPersonWithNoPhones();
        
        TelPhone t = new TelPhone();
        String phone = "201-555-0123x4756i";
        t.setValue(new URI("tel", phone, null));
        dto.getTelecomAddress().getItem().add(t);
        
        try {
            remote.createPerson(dto);
        } catch (EntityValidationException eve) {
            assertTrue(eve.getErrors().containsKey("Phone number 201-555-0123x4756i"));
        }
   } 
   
    @Test
    public void createPersonWithInvalidFax() throws EntityValidationException, URISyntaxException, CurationException {
        PersonDTO dto = createPersonWithNoPhones();
        
        TelPhone t = new TelPhone();
        String phone = "201-555-0123x4756i";
        t.setValue(new URI("x-text-fax", phone, null));
        dto.getTelecomAddress().getItem().add(t);
        
        try {
            remote.createPerson(dto);
        } catch (EntityValidationException eve) {
            assertTrue(eve.getErrors().containsKey("Phone number 201-555-0123x4756i"));
        }
   } 
    
   @Test
   public void createPersonWithInvalidTty() throws EntityValidationException, URISyntaxException, CurationException {
        PersonDTO dto = createPersonWithNoPhones();
        
        TelPhone t = new TelPhone();
        String phone = "201-555-0123x4756i";
        t.setValue(new URI("x-text-tel", phone, null));
        dto.getTelecomAddress().getItem().add(t);
        
        try {
            remote.createPerson(dto);
        } catch (EntityValidationException eve) {
            assertTrue(eve.getErrors().containsKey("Phone number 201-555-0123x4756i"));
        }
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
        assertEquals(3, errors.size());
        assertTrue(errors.containsKey("lastName"));
        assertTrue(errors.containsKey("postalAddress"));        

        Ad add = new Ad();
        add.setPart(new ArrayList<Adxp>());
        Adxp apart = new AdxpSta();
        apart.setValue("My State");
        add.getPart().add(apart);
        dto.setPostalAddress(add);
        errors = remote.validate(dto);
        assertEquals(5, errors.size());
        assertTrue(errors.containsKey("lastName"));
        assertTrue(errors.containsKey("postalAddress.streetAddressLine"));
        assertTrue(errors.containsKey("postalAddress.cityOrMunicipality"));
        assertFalse(errors.containsKey("postalAddress.postalCode"));
        assertTrue(errors.containsKey("postalAddress.country"));
        assertFalse(errors.containsKey("postalAddress.stateOrProvince"));// for clarity
        

    }

    @Test
    public void findByFirstName() throws EntityValidationException, NullifiedEntityException, URISyntaxException,
            CurationException {
        init();

        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "a", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(1, result.size());
    }

    @Test
    public void findByFirstNameWildcardAsFirstAndLast() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "%a%", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(1, result.size());
    }

    @Test
    public void findByFirstNameWildcardsInSitu() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "b%b", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void findByFirstNameWilcardAttempt1() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "?a?", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void findByFirstNameWilcardAttempt2() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "_a_", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    @Test
    public void findByFirstNameNoMatches() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        init();
        PersonDTO crit = new PersonDTO();
        crit.setName(PersonNameConverterUtil.convertToEnPn(null, null, "foobar", null, null));
        List<PersonDTO> result = remote.search(crit);
        assertEquals(0, result.size());
    }

    private void init() throws EntityValidationException, NullifiedEntityException, URISyntaxException,
            CurationException {
        PersonDTO dto = new PersonDTO();
        dto.setName(PersonNameConverterUtil.convertToEnPn("bab", "m", "a", "c", "d"));
        Person p = (Person) PoXsnapshotHelper.createModel(dto);
        assertEquals("a", p.getLastName());
        assertEquals("bab", p.getFirstName());
        assertEquals("m", p.getMiddleName());
        assertEquals("c", p.getPrefix());
        assertEquals("d", p.getSuffix());
        Address a = makeAddress();
        PoHibernateUtil.getCurrentSession().save(a.getCountry());
        dto.setPostalAddress(AddressConverter.SimpleConverter.convertToAd(a));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        Tel t = new Tel();
        String phone = "201-555-0123x4756";
        t.setValue(new URI("tel", phone, null));
        telco.getItem().add(t);
        dto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://example.com"));
        dto.getTelecomAddress().getItem().add(url);
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
    public void findByMiddleName() throws EntityValidationException, NullifiedEntityException, URISyntaxException,
            CurationException {
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
    public void updatePerson() throws EntityValidationException, URISyntaxException, NullifiedEntityException,
            JMSException {
        long id = super.createPerson();
        Person p = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);
        PersonDTO dto = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));
        assertEquals(EntityStatus.PENDING, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        // dto.setName(StringConverter.convertToEnOn("newName"));
        Adxp adl = new AdxpAdl();
        adl.setValue("additional ADL");
        dto.getPostalAddress().getPart().add(adl);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        dto.getTelecomAddress().getItem().clear();
        dto.getTelecomAddress().getItem().add(email);
        remote.updatePerson(dto);
        @SuppressWarnings("unchecked")
        List<PersonCR> l = PoHibernateUtil.getCurrentSession().createCriteria(PersonCR.class).list();
        assertEquals(1, l.size());
        PersonCR cr = l.get(0);
        // assertEquals("newName", cr.getName());
        assertEquals(p.getPostalAddress().getDeliveryAddressLine() + " additional ADL", cr.getPostalAddress()
                .getDeliveryAddressLine());
        assertEquals(1, cr.getEmail().size());
        assertEquals("another.email@example.com", cr.getEmail().get(0).getValue());
        assertEquals(EntityStatus.PENDING, cr.getStatusCode());
    }

    private long createPatient() throws Exception {

        PatientRemoteServiceTest patCTest = new PatientRemoteServiceTest();
        patCTest.setDefaultCountry(CountryTestUtil.save(new Country("Zcountry Zworld", "999", "ZZ", "ZZZ")));
        patCTest.setUpData();
        PatientDTO patDto = patCTest.getSampleDto();

        Ii id = EjbTestHelper.getPatientCorrelationServiceRemote().createCorrelation(patDto);
        assertNotNull(id);
        assertNotNull(id.getExtension());

        Ii patIi = ISOUtils.ID_PERSON.convertToIi(Long.valueOf(id.getExtension()));
        patIi.setExtension(IdConverter.PATIENT_PREFIX + patIi.getExtension());
        PersonDTO result = remote.getPerson(patIi);

        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        result.setBirthDate(DateConverter.convertToTs(sdf.parse("09/28/1980")));
        result.setSexCode(SexCodeConverter.convertToCd(PersonSex.MALE));
        result.getEthnicGroupCode().getItem().add(
                EthnicGroupCodeConverter.convertToCd(PersonEthnicGroup.NOT_HISPANIC_OR_LATINO));
        result.getRaceCode().getItem().add(RaceCodeConverter.convertToCd(PersonRace.WHITE));

        remote.updatePerson(result);

        return Long.valueOf(id.getExtension());

    }

    @Test
    public void updatePatient() throws Exception {
        long id = this.createPatient();

        Patient p = (Patient) PoHibernateUtil.getCurrentSession().get(Patient.class, id);
        Ii patIi = ISOUtils.ID_PERSON.convertToIi(id);
        patIi.setExtension(IdConverter.PATIENT_PREFIX + patIi.getExtension());
        PersonDTO result = remote.getPerson(patIi);

        Cd sexCode = new Cd();
        sexCode.setCode("FEMALE");
        result.setSexCode(sexCode);
        remote.updatePerson(result);

        PersonDTO fresh = remote.getPerson(patIi);
        assertEquals("female", fresh.getSexCode().getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updatePersonChangeCtatus() throws EntityValidationException, NullifiedEntityException, JMSException {
        long id = super.createPerson();
        PersonDTO dto = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));
        assertEquals(EntityStatus.PENDING, StatusCodeConverter.convertToStatusEnum(dto.getStatusCode()));
        dto.setStatusCode(StatusCodeConverter.convertToCd(EntityStatus.INACTIVE));
        remote.updatePerson(dto);
    }

    @Test
    public void updatePersonStatus() throws EntityValidationException, JMSException {
        long id = super.createPerson();
        Ii ii = ISOUtils.ID_PERSON.convertToIi(id);
        Cd newStatus = StatusCodeConverter.convertToCd(EntityStatus.INACTIVE);
        remote.updatePersonStatus(ii, newStatus);
        @SuppressWarnings("unchecked")
        List<PersonCR> l = PoHibernateUtil.getCurrentSession().createCriteria(PersonCR.class).list();
        assertEquals(1, l.size());
        PersonCR cr = l.get(0);
        assertEquals(cr.getStatusCode(), EntityStatus.INACTIVE);
    }

    @Test
    public void search() throws TooManyResultsException, EntityValidationException, JMSException {
        int max = 7;
        PersonEntityServiceBean.setMaxResultsReturnedLimit(max - 2);
        for (int i = 0; i < max; i++) {
            createPerson();
        }

        PersonDTO sc = new PersonDTO();
        sc.setName(PersonNameConverterUtil.convertToEnPn("fName", null, null, null, null));
        List<PersonDTO> results;
        // verify walking forward with a page size of 1
        LimitOffset page = new LimitOffset(1, -1);
        for (int j = 0; j < max; j++) {
            page.next();
            results = remote.search(sc, page);
            assertEquals(1, results.size());
        }
        // walk past the last record
        page.next();
        results = remote.search(sc, page);
        assertEquals(0, results.size());

        // walk back to first record
        for (int j = 0; j < max; j++) {
            page.previous();
            results = remote.search(sc, page);
            assertEquals(1, results.size());
        }
        // try to walk before first record, first record always returned
        page.previous();
        results = remote.search(sc, page);
        assertEquals(1, results.size());

        // verify TooManyResultsException is thrown
        try {
            remote.search(sc, new LimitOffset(max, 0));
            fail();
        } catch (TooManyResultsException e) {
        }

        // verify TooManyResultsException is thrown
        try {
            remote.search(sc, new LimitOffset(max - 1, 0));
            fail();
        } catch (TooManyResultsException e) {
        }

        // verify results are returned
        page = new LimitOffset(max - 2, 0);
        results = remote.search(sc, page);
        assertEquals(page.getLimit(), results.size());

        // verify results are returned
        page = new LimitOffset(max - 3, 0);
        results = remote.search(sc, page);
        assertEquals(page.getLimit(), results.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNoIdentifier() throws Exception {
        long id = super.createPerson();
        PersonDTO dto = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));

        dto.setIdentifier(null);
        remote.updatePerson(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithWrongIdentifier() throws Exception {
        long id = super.createPerson();
        PersonDTO dto = remote.getPerson(ISOUtils.ID_PERSON.convertToIi(id));

        Ii wrongId = new Ii();
        wrongId.setRoot(IdConverter.PERSON_ROOT);
        wrongId.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        wrongId.setExtension("999");
        dto.setIdentifier(wrongId);
        remote.updatePerson(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatusWithWrongIdentifier() throws Exception {

        Cd cd = new Cd();
        cd.setCode("suspended"); // maps to SUSPENDED

        Ii wrongId = new Ii();
        wrongId.setRoot(IdConverter.PERSON_ROOT);
        wrongId.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        wrongId.setExtension("999");

        remote.updatePersonStatus(wrongId, cd);
    }
}
