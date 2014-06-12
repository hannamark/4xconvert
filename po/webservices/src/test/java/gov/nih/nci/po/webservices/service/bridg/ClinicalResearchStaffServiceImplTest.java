package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ClinicalResearchStaffCR;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;
import gov.nih.nci.po.webservices.convert.bridg.ClinicalResearchStaffTransformer;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.criterion.Restrictions;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ClinicalResearchStaffServiceImplTest {

    private ClinicalResearchStaffServiceImpl service;
    private ServiceLocator oldLocator;
    private DbTestUtil dbTestUtil;
    private Person person;
    private Organization organization;

    @Before
    public void setUp() throws Exception {


        oldLocator = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(new TestServiceLocator());

        dbTestUtil = DbTestUtil.getInstance();
        dbTestUtil.setup();

        this.service = new ClinicalResearchStaffServiceImpl();
        // this.service.setMaxHitsPerRequest(100);

        //stage person
        person = ModelUtils.getBasicPerson();
        PoHibernateUtil.getCurrentSession().save( ModelUtils.getDefaultCountry());
        PoHibernateUtil.getCurrentSession().save(person);

        //stage org
        organization = ModelUtils.getBasicOrganization();
        PoHibernateUtil.getCurrentSession().save(organization);

        //flush the data
        PoHibernateUtil.getCurrentSession().flush();

    }


    @After
    public void tearDown() {
        PoRegistry.getInstance().setServiceLocator(oldLocator);
        dbTestUtil.tearDown();
    }


    @Test
    public void testCreate() throws EntityValidationException {
        ClinicalResearchStaff clinicalResearchStaff = getBasicBridgClinicalResearchStaff();

        Id createdId = service.create(clinicalResearchStaff);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));
    }

    @Test
    public void testCreateDuplicateEntity() throws EntityValidationException {
        ClinicalResearchStaff clinicalResearchStaff = getBasicBridgClinicalResearchStaff();

        //first call should succeed
        service.create(clinicalResearchStaff);

        try{
            service.create(clinicalResearchStaff);
            fail("Can not create an existing instance.");
        }catch (Exception e) {

        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithId() throws EntityValidationException {

        ClinicalResearchStaff clinicalResearchStaff = getBasicBridgClinicalResearchStaff();

        II id = new II();
        id.setRoot(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(IdConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension("123");

        clinicalResearchStaff.setIdentifier(IdTransformerTest.convertIIToDSETII(id));

        service.create(clinicalResearchStaff);

    }

    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException {
        //Should not be able to create it with a null player
        ClinicalResearchStaff clinicalResearchStaff = getBasicBridgClinicalResearchStaff();
        clinicalResearchStaff.setPlayerIdentifier(null);

        service.create(clinicalResearchStaff);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();
        clinicalResearchStaff.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));


        List<ClinicalResearchStaff> results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {

        RoleStatus[] nonactive = new RoleStatus[]{RoleStatus.NULLIFIED, RoleStatus.PENDING, RoleStatus.SUSPENDED};

        //stage 5 active, 5 non-active instances
        for(int i=0; i<5; i++ ) {

            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);

            gov.nih.nci.po.data.bo.ClinicalResearchStaff nonActiveInstance = ModelUtils.getBasicClinicalResearchStaff();
            nonActiveInstance.setStatus(nonactive[i % nonactive.length]);

            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();
        clinicalResearchStaff.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        List<ClinicalResearchStaff> results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {
        //stage 5 active
        for(int i=0; i<=service.getMaxHitsPerRequest(); i++ ) {

            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();
        clinicalResearchStaff.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        service.query(clinicalResearchStaff, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {

        RoleStatus[] nonactive = new RoleStatus[]{RoleStatus.NULLIFIED, RoleStatus.PENDING, RoleStatus.SUSPENDED};

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);



            gov.nih.nci.po.data.bo.ClinicalResearchStaff nonActiveInstance = ModelUtils.getBasicClinicalResearchStaff();
            nonActiveInstance.setStatus(nonactive[i % nonactive.length]);

            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();
        clinicalResearchStaff.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        List<ClinicalResearchStaff> results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(results.size(), 2);

        results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(results.size(), 2);

        results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(results.size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();
        clinicalResearchStaff.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        service.query(clinicalResearchStaff, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.ClinicalResearchStaff instance = ModelUtils.getBasicClinicalResearchStaff();
        PoHibernateUtil.getCurrentSession().save(instance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(instance.getScoper());
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        assertEquals(1, instance.getPhone().size());
        //update it
        ClinicalResearchStaffDTO dto = (ClinicalResearchStaffDTO) PoXsnapshotHelper.createSnapshot(instance);
        ClinicalResearchStaff xml = ClinicalResearchStaffTransformer.INSTANCE.toXml(dto);

        //add an address
        Address address = ModelUtils.getBasicAddress();
        address.setStreetAddressLine("12345 Elm Street NE");

        HashSet<Address> addresses = new HashSet<Address>();
        addresses.addAll(instance.getPostalAddresses());
        addresses.add(address);

        AddressConverter.SetConverter converter = new AddressConverter.SetConverter();
        DSet<Ad> dset = converter.convert(DSet.class, addresses);

        xml.setPostalAddress(DSETADTransformer.INSTANCE.toXml(dset));

        service.update(xml);

        //retrieve it
        ClinicalResearchStaffCR cr = (ClinicalResearchStaffCR) PoHibernateUtil.getCurrentSession().createCriteria(ClinicalResearchStaffCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //compare it
        assertEquals(2, cr.getPostalAddresses().size());
    }


    @Test
    public void testUpdateStatus() throws EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.ClinicalResearchStaff instance = ModelUtils.getBasicClinicalResearchStaff();
        instance.setStatus(RoleStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(instance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(instance.getScoper());
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(instance.getId());

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        service.updateStatus(instanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));

        //retrieve it
        ClinicalResearchStaffCR cr = (ClinicalResearchStaffCR) PoHibernateUtil.getCurrentSession().createCriteria(ClinicalResearchStaffCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //verify
        assertEquals(RoleStatus.ACTIVE, cr.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(RandomUtils.nextLong());

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));
    }

    @Test
    public void testValidateWithNoErrors() {
        ClinicalResearchStaff clinicalResearchStaff = getBasicBridgClinicalResearchStaff();
        StringMap errors = service.validate(clinicalResearchStaff);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        ClinicalResearchStaff clinicalResearchStaff = getBasicBridgClinicalResearchStaff();
        clinicalResearchStaff.getTelecomAddress().getItem().clear();
        StringMap errors = service.validate(clinicalResearchStaff);
        assertEquals("Expected errors were not detected.", errors.getEntry().size(), 1);
    }

    @Test
    public void testGetByPlayerIds() throws NullifiedRoleException {
        //create 5 with player1
        Person person1 = ModelUtils.getBasicPerson();
        person1.setFirstName("Player");
        person1.setLastName("One");
        PoHibernateUtil.getCurrentSession().save(person1);

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            activeInstance.setPlayer(person1);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
        }

        //create 5 with player2
        Person person2 = ModelUtils.getBasicPerson();
        person2.setFirstName("Player");
        person2.setLastName("Two");
        PoHibernateUtil.getCurrentSession().save(person2);

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            activeInstance.setPlayer(person2);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
        }

        //create 5 with player3
        Person person3 = ModelUtils.getBasicPerson();
        person3.setFirstName("Player");
        person3.setLastName("Three");
        PoHibernateUtil.getCurrentSession().save(person3);

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            activeInstance.setPlayer(person3);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
        }

        PoHibernateUtil.getCurrentSession().flush();

        //check []
        List<Id> ids = new ArrayList<Id>();
        List<ClinicalResearchStaff> hits = service.getByPlayerIds(ids);
        //expect 0
        assertEquals(0, hits.size());

        //check [1]

        ids.add(getPersonId(person1));
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(5, hits.size());

        //check [1,1]
        ids.add(getPersonId(person1));
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(5, hits.size());

        //check [1,2]
        ids.clear();
        ids.add(getPersonId(person1));
        ids.add(getPersonId(person2));
        hits = service.getByPlayerIds(ids);
        //expect 10
        assertEquals(10, hits.size());

        //check [1,2,3]
        ids.add(getPersonId(person3));
        hits = service.getByPlayerIds(ids);
        //expect 15
        assertEquals(15, hits.size());

        //check [2,3]
        ids.remove(0);
        hits = service.getByPlayerIds(ids);
        //expect 10
        assertEquals(10, hits.size());

        //check [3]
        ids.remove(0);
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(5, hits.size());
    }




    @Test
    public void testGetById() throws NullifiedRoleException {
        gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        ClinicalResearchStaff hit = service.getById(instanceId);
        assertNotNull(hit);
    }

    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedById() throws NullifiedRoleException {
        gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
        activeInstance.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        service.getById(instanceId);
    }

    @Test
    public void testGetNonExistingInstanceById() throws NullifiedRoleException {
        Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(RandomUtils.nextLong());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        ClinicalResearchStaff hit = service.getById(instanceId);
        assertNull(hit);
    }

    @Test
    public void testGetByIds() throws NullifiedRoleException {
        List<Long> instanceIds = new ArrayList<Long>();

        for (int i=0; i<5; i++) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
            instanceIds.add(activeInstance.getId());
        }

        PoHibernateUtil.getCurrentSession().flush();

        List<Id> ids = new ArrayList<Id>();
        for (Long instanceId : instanceIds) {
            Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }
        List<ClinicalResearchStaff> hits = service.getByIds(ids);
        assertEquals(5, hits.size());
    }


    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedByIds() throws NullifiedRoleException {
        List<Long> instanceIds = new ArrayList<Long>();

        for (int i=0; i<5; i++) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff activeInstance = ModelUtils.getBasicClinicalResearchStaff();
            if (i == 0) {
                activeInstance.setStatus(RoleStatus.NULLIFIED);
            }

            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance.getScoper());
            PoHibernateUtil.getCurrentSession().save(activeInstance);


            instanceIds.add(activeInstance.getId());
        }

        PoHibernateUtil.getCurrentSession().flush();

        List<Id> ids = new ArrayList<Id>();
        for (Long instanceId : instanceIds) {
            Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }
        service.getByIds(ids);
    }

    @Test
    public void testGetByIdsWithEmptyInput() throws NullifiedRoleException {
        List<ClinicalResearchStaff> hits = service.getByIds(Collections.EMPTY_LIST);
        assertEquals(0, hits.size());
    }


    private Id getPersonId(Person person) {
        Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(person.getId());
        return IdTransformer.INSTANCE.toXml(ii);
    }

    private final ClinicalResearchStaff getBasicBridgClinicalResearchStaff()  {
        II player = new II();
        player.setRoot(IdConverter.PERSON_ROOT);
        player.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        player.setExtension(person.getId().toString());

        II scoper = new II();
        scoper.setRoot(IdConverter.ORG_ROOT);
        scoper.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        scoper.setExtension(organization.getId().toString());

        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();


        clinicalResearchStaff.setPlayerIdentifier(player);

        clinicalResearchStaff.setScoperIdentifier(scoper);



        DSet<Tel> telSet = new DSet<Tel>();
        telSet.setItem(new HashSet<Tel>());
        ContactSpec contactSpec = ContactSpec.newInstance()
                .withEmail("bob@foo.org")
                .withPhone("123-456-7890")
                .withUrl("http://www.example.org");

        try {
            clinicalResearchStaff.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(contactSpec.asDset()));
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }


        Address address = ModelUtils.getBasicAddress();
        AddressConverter.SetConverter converter = new AddressConverter.SetConverter();
        HashSet<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        DSet<Ad> dset = converter.convert(DSet.class, addresses);

        clinicalResearchStaff.setPostalAddress(DSETADTransformer.INSTANCE.toXml(dset));
        clinicalResearchStaff.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return clinicalResearchStaff;
    }


    public static class ContactSpec implements Contactable {

        private List<Email> emails;
        private List<PhoneNumber> faxes;
        private List<PhoneNumber> phoneNumbers;
        private List<URL> urls;
        private List<PhoneNumber> ttys;

        public ContactSpec() {
            emails = new ArrayList<Email>();
            faxes = new ArrayList<PhoneNumber>();
            phoneNumbers = new ArrayList<PhoneNumber>();
            urls = new ArrayList<URL>();
            ttys = new ArrayList<PhoneNumber>();
        }

        @Override
        public List<Email> getEmail() {
            return emails;
        }

        @Override
        public List<PhoneNumber> getFax() {
            return faxes;
        }

        @Override
        public List<PhoneNumber> getPhone() {
            return phoneNumbers;
        }

        @Override
        public List<URL> getUrl() {
            return urls;
        }

        @Override
        public List<PhoneNumber> getTty() {
            return ttys;
        }

        public static ContactSpec newInstance() {
            return new ContactSpec();
        }

        public ContactSpec withEmail(String email) {
            Email emailInstance = new Email(email);
            this.emails.add(emailInstance);
            return this;
        }

        public ContactSpec withFax(String fax) {
            PhoneNumber faxInstance = new PhoneNumber(fax);
            this.faxes.add(faxInstance);
            return this;
        }

        public ContactSpec withPhone(String phoneNumber) {
            PhoneNumber phoneNumberInstance = new PhoneNumber(phoneNumber);
            this.faxes.add(phoneNumberInstance);
            return this;
        }

        public ContactSpec withUrl(String url) {
            URL urlInstance = new URL(url);
            this.urls.add(urlInstance);
            return this;
        }

        public ContactSpec withTty(String tty) {
            PhoneNumber ttyInstance = new PhoneNumber(tty);
            this.faxes.add(ttyInstance);
            return this;
        }

        public DSet<Tel> asDset() {

            Set<Tel> set = new HashSet<Tel>();
            for (Email email : emails) {
                TelEmail val = new TelEmail();
                val.setValue(URI.create("mailto:" + email.getValue()));
                set.add( val );
            }

            for (PhoneNumber phoneNumber : faxes) {
                TelPhone val = new TelPhone();
                val.setValue(URI.create("x-text-fax:" + phoneNumber.getValue()));
                set.add( val );
            }

            for (PhoneNumber phoneNumber : phoneNumbers) {
                TelPhone val = new TelPhone();
                val.setValue(URI.create("tel:" + phoneNumber.getValue()));
                set.add( val );
            }

            for (URL url : urls) {
                TelUrl val = new TelUrl();
                val.setValue(URI.create("file:" + url.getValue()));
                set.add( val );
            }

            for (PhoneNumber tty : ttys) {
                TelPhone val = new TelPhone();
                val.setValue(URI.create("x-text-tel:" + tty.getValue()));
                set.add( val );
            }

            DSet<Tel> result = new DSet<Tel>();
            result.setItem(set);
            return result;
        }

    }


}
