package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;
import gov.nih.nci.po.webservices.convert.bridg.PersonTransformer;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.criterion.Restrictions;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class PersonServiceImplTest {

    PersonServiceImpl service;
    ServiceLocator oldLocator;
    DbTestUtil dbTestUtil;

    @Before
    public void setup() {
        service = new PersonServiceImpl();

        oldLocator = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(new TestServiceLocator());

        dbTestUtil = DbTestUtil.getInstance();
        dbTestUtil.setup();

        PoHibernateUtil.getCurrentSession().save( ModelUtils.getDefaultCountry() );
    }

    @After
    public void tearDown() {
        PoRegistry.getInstance().setServiceLocator(oldLocator);
        dbTestUtil.tearDown();
    }

    @Test
    public void testCreate() throws EntityValidationException {

        Person person = getBasicPerson();

        Id createdId = service.create(person);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));
    }



    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithId() throws EntityValidationException {

        Person person = getBasicPerson();

        II id = new II();
        id.setRoot(IdConverter.PERSON_ROOT);
        id.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        id.setExtension("123");

        person.setIdentifier(id);

        service.create(person);

    }

    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException {
        //Should not be able to create it with a null name
        Person person = getBasicPerson();
        person.setName(null);

        service.create(person);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));


        List<Person> results = service.query(person, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {

        EntityStatus[] nonactive = new EntityStatus[]{EntityStatus.NULLIFIED, EntityStatus.PENDING, EntityStatus.INACTIVE};

        //stage 5 active, 5 non-active instances
        for(int i=0; i<5; i++ ) {

            gov.nih.nci.po.data.bo.Person activeInstance = ModelUtils.getBasicPerson();
            activeInstance.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance);

            gov.nih.nci.po.data.bo.Person nonActiveInstance = ModelUtils.getBasicPerson();
            nonActiveInstance.setStatusCode(nonactive[i % nonactive.length]);
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        List<Person> results = service.query(person, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(person, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {

        //stage 5 active
        for(int i=0; i<=service.getMaxHitsPerRequest(); i++ ) {

            gov.nih.nci.po.data.bo.Person activeInstance = ModelUtils.getBasicPerson();
            activeInstance.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        service.query(person, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {

        EntityStatus[] nonactive = new EntityStatus[]{EntityStatus.NULLIFIED, EntityStatus.PENDING, EntityStatus.INACTIVE};

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.Person activeInstance = ModelUtils.getBasicPerson();
            activeInstance.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance);

            gov.nih.nci.po.data.bo.Person nonActiveInstance = ModelUtils.getBasicPerson();
            nonActiveInstance.setStatusCode(nonactive[i % nonactive.length]);
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        List<Person> results = service.query(person, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(results.size(), 2);

        results = service.query(person, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(results.size(), 2);

        results = service.query(person, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(results.size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        service.query(person, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.Person instance = ModelUtils.getBasicPerson();
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        //update it
        PersonDTO dto = (PersonDTO) PoXsnapshotHelper.createSnapshot(instance);
        Person xml = PersonTransformer.INSTANCE.toXml(dto);

        //change the address
        Address address = ModelUtils.getBasicAddress();
        address.setStreetAddressLine("12345 " + RandomStringUtils.randomAlphabetic(5) + " Street NE");

        Ad newAddress = AddressConverter.SimpleConverter.convertToAd(address);
        xml.setPostalAddress(ADTransformer.INSTANCE.toXml(newAddress));

        service.update(xml);

        //retrieve it
        PersonCR cr = (PersonCR) PoHibernateUtil.getCurrentSession().createCriteria(PersonCR.class)
            .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //compare it
        assertEquals(address.getStreetAddressLine(), cr.getPostalAddress().getStreetAddressLine());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithNullId() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.Person instance = ModelUtils.getBasicPerson();

        //update it
        PersonDTO dto = (PersonDTO) PoXsnapshotHelper.createSnapshot(instance);
        Person xml = PersonTransformer.INSTANCE.toXml(dto);

        //change the address
        Address address = ModelUtils.getBasicAddress();
        address.setStreetAddressLine("12345 " + RandomStringUtils.randomAlphabetic(5) + " Street NE");

        Ad newAddress = AddressConverter.SimpleConverter.convertToAd(address);
        xml.setPostalAddress(ADTransformer.INSTANCE.toXml(newAddress));

        service.update(xml);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatusImproperly() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.Person instance = ModelUtils.getBasicPerson();

        //update it
        PersonDTO dto = (PersonDTO) PoXsnapshotHelper.createSnapshot(instance);
        Person xml = PersonTransformer.INSTANCE.toXml(dto);

        //change the status
        CD newStatusCode = new CD();
        newStatusCode.setCode(EntityStatus.ACTIVE.toString());
        xml.setStatusCode(newStatusCode);

        service.update(xml);

    }

    @Test
    public void testUpdateStatus() throws EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.Person instance = ModelUtils.getBasicPerson();
        instance.setStatusCode(EntityStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.PersonIdConverter().convertToIi(instance.getId());

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        service.updateStatus(instanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));

        //retrieve it
        PersonCR cr = (PersonCR) PoHibernateUtil.getCurrentSession().createCriteria(PersonCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //verify
        assertEquals(EntityStatus.ACTIVE, cr.getStatusCode());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Ii ii = new IdConverter.PersonIdConverter().convertToIi(RandomUtils.nextLong());

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatusWithNullId() throws EntityValidationException {
        Ii ii = new IdConverter.PersonIdConverter().convertToIi(null);

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
    }


    @Test
    public void testValidateWithNoErrors() {
        Person person = getBasicPerson();
        StringMap errors = service.validate(person);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        Person person = getBasicPerson();
        person.getTelecomAddress().getItem().clear();
        StringMap errors = service.validate(person);
        assertEquals("Expected errors were not detected.", errors.getEntry().size(), 1);
    }


    @Test
    public void testGetById() throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Person person = ModelUtils.getBasicPerson();
        PoHibernateUtil.getCurrentSession().save(person);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.PersonIdConverter().convertToIi(person.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        Person hit = service.getById(instanceId);
        assertNotNull(hit);
    }

    @Test (expected = NullifiedEntityException.class)
    public void testGetNullifiedById() throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Person activeInstance = ModelUtils.getBasicPerson();
        activeInstance.setStatusCode(EntityStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.PersonIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        service.getById(instanceId);
    }

    @Test
    public void testGetNonExistingInstanceById() throws NullifiedEntityException {
        Ii ii = new IdConverter.PersonIdConverter().convertToIi(RandomUtils.nextLong());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        Person hit = service.getById(instanceId);
        assertNull(hit);
    }

    public Person getBasicPerson() {

        gov.nih.nci.po.data.bo.Person person = ModelUtils.getBasicPerson();

        PersonDTO d = (PersonDTO) PoXsnapshotHelper.createSnapshot(person);

        try {
            return PersonTransformer.INSTANCE.toXml(d);
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }

    }


}
