package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;
import gov.nih.nci.po.webservices.convert.bridg.HealthCareFacilityTransformer;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.criterion.Restrictions;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareFacilityServiceImplTest {
    private HealthCareFacilityServiceImpl service;
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

        this.service = new HealthCareFacilityServiceImpl();
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
        HealthCareFacility healthCareFacility = getBasicBridgHealthCareFacility();

        Id createdId = service.create(healthCareFacility);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithId() throws EntityValidationException {

        HealthCareFacility healthCareFacility = getBasicBridgHealthCareFacility();

        II id = new II();
        id.setRoot(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(IdConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension("123");

        healthCareFacility.setIdentifier(IdTransformerTest.convertIIToDSETII(id));

        service.create(healthCareFacility);

    }

    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException {
        //Should not be able to create it with a null player
        HealthCareFacility healthCareFacility = getBasicBridgHealthCareFacility();
        healthCareFacility.setPlayerIdentifier(null);

        service.create(healthCareFacility);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));


        List<HealthCareFacility> results = service.query(healthCareFacility, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {

        RoleStatus[] nonactive = new RoleStatus[]{RoleStatus.NULLIFIED, RoleStatus.PENDING, RoleStatus.SUSPENDED};

        //stage 5 active, 5 non-active instances
        for(int i=0; i<5; i++ ) {

            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);

            gov.nih.nci.po.data.bo.HealthCareFacility nonActiveInstance = ModelUtils.getBasicHealthCareFacility();
            nonActiveInstance.setStatus(nonactive[i % nonactive.length]);

            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        List<HealthCareFacility> results = service.query(healthCareFacility, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(healthCareFacility, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {
        //stage 5 active
        for(int i=0; i<=service.getMaxHitsPerRequest(); i++ ) {

            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        service.query(healthCareFacility, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {

        RoleStatus[] nonactive = new RoleStatus[]{RoleStatus.NULLIFIED, RoleStatus.PENDING, RoleStatus.SUSPENDED};

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);



            gov.nih.nci.po.data.bo.HealthCareFacility nonActiveInstance = ModelUtils.getBasicHealthCareFacility();
            nonActiveInstance.setStatus(nonactive[i % nonactive.length]);

            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        List<HealthCareFacility> results = service.query(healthCareFacility, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(results.size(), 2);

        results = service.query(healthCareFacility, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(results.size(), 2);

        results = service.query(healthCareFacility, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(results.size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        service.query(healthCareFacility, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.HealthCareFacility instance = ModelUtils.getBasicHealthCareFacility();
        PoHibernateUtil.getCurrentSession().save(instance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        assertEquals(1, instance.getPhone().size());
        //update it
        HealthCareFacilityDTO dto = (HealthCareFacilityDTO) PoXsnapshotHelper.createSnapshot(instance);
        HealthCareFacility xml = HealthCareFacilityTransformer.INSTANCE.toXml(dto);

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
        HealthCareFacilityCR cr = (HealthCareFacilityCR) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacilityCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //compare it
        assertEquals(2, cr.getPostalAddresses().size());
    }


    @Test
    public void testUpdateStatus() throws EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.HealthCareFacility instance = ModelUtils.getBasicHealthCareFacility();
        instance.setStatus(RoleStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(instance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(instance.getId());

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        service.updateStatus(instanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));

        //retrieve it
        HealthCareFacilityCR cr = (HealthCareFacilityCR) PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacilityCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //verify
        assertEquals(RoleStatus.ACTIVE, cr.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(RandomUtils.nextLong());

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));
    }

    @Test
    public void testValidateWithNoErrors() {
        HealthCareFacility healthCareFacility = getBasicBridgHealthCareFacility();
        CD statusCd = new Cd();
        statusCd.setCode(RoleStatus.PENDING.toString());
        healthCareFacility.setStatus(statusCd);
        StringMap errors = service.validate(healthCareFacility);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        HealthCareFacility healthCareFacility = getBasicBridgHealthCareFacility();
        healthCareFacility.getTelecomAddress().getItem().clear();
        StringMap errors = service.validate(healthCareFacility);
        assertEquals("Expected errors were not detected.", errors.getEntry().size(), 1);
    }

    @Test
    public void testGetByPlayerIds() throws NullifiedRoleException {
        //create 5 with org1
        Organization organization1 = ModelUtils.getBasicOrganization();
        organization1.setName("Org1");
        PoHibernateUtil.getCurrentSession().save(organization1);

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            activeInstance.setPlayer(organization1);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
        }

        //create 5 with player2
        Organization organization2 = ModelUtils.getBasicOrganization();
        organization2.setName("Org2");
        PoHibernateUtil.getCurrentSession().save(organization2);

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            activeInstance.setPlayer(organization2);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
        }

        //create 5 with player3
        Organization organization3 = ModelUtils.getBasicOrganization();
        organization3.setName("Org3");
        PoHibernateUtil.getCurrentSession().save(organization3);

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            activeInstance.setPlayer(organization3);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
        }

        PoHibernateUtil.getCurrentSession().flush();

        //check []
        List<Id> ids = new ArrayList<Id>();
        List<HealthCareFacility> hits = service.getByPlayerIds(ids);
        //expect 0
        assertEquals(0, hits.size());

        //check [1]

        ids.add(getOrganizationId(organization1));
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(5, hits.size());

        //check [1,1]
        ids.add(getOrganizationId(organization1));
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(5, hits.size());

        //check [1,2]
        ids.clear();
        ids.add(getOrganizationId(organization1));
        ids.add(getOrganizationId(organization2));
        hits = service.getByPlayerIds(ids);
        //expect 10
        assertEquals(10, hits.size());

        //check [1,2,3]
        ids.add(getOrganizationId(organization3));
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
        gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        HealthCareFacility hit = service.getById(instanceId);
        assertNotNull(hit);
    }

    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedById() throws NullifiedRoleException {
        gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
        activeInstance.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        service.getById(instanceId);
    }

    @Test
    public void testGetNonExistingInstanceById() throws NullifiedRoleException {
        Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(RandomUtils.nextLong());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        HealthCareFacility hit = service.getById(instanceId);
        assertNull(hit);
    }

    @Test
    public void testGetByIds() throws NullifiedRoleException {
        List<Long> instanceIds = new ArrayList<Long>();

        for (int i=0; i<5; i++) {
            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
            instanceIds.add(activeInstance.getId());
        }

        PoHibernateUtil.getCurrentSession().flush();

        List<Id> ids = new ArrayList<Id>();
        for (Long instanceId : instanceIds) {
            Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }
        List<HealthCareFacility> hits = service.getByIds(ids);
        assertEquals(5, hits.size());
    }


    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedByIds() throws NullifiedRoleException {
        List<Long> instanceIds = new ArrayList<Long>();

        for (int i=0; i<5; i++) {
            gov.nih.nci.po.data.bo.HealthCareFacility activeInstance = ModelUtils.getBasicHealthCareFacility();
            if (i == 0) {
                activeInstance.setStatus(RoleStatus.NULLIFIED);
            }

            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);


            instanceIds.add(activeInstance.getId());
        }

        PoHibernateUtil.getCurrentSession().flush();

        List<Id> ids = new ArrayList<Id>();
        for (Long instanceId : instanceIds) {
            Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }
        service.getByIds(ids);
    }

    @Test
    public void testGetByIdsWithEmptyInput() throws NullifiedRoleException {
        List<HealthCareFacility> hits = service.getByIds(Collections.EMPTY_LIST);
        assertEquals(0, hits.size());
    }

    private Id getOrganizationId(Organization organization) {
        Ii ii = new IdConverter.HealthCareFacilityIdConverter().convertToIi(organization.getId());
        return IdTransformer.INSTANCE.toXml(ii);
    }

    private final HealthCareFacility getBasicBridgHealthCareFacility()  {
        II player = new II();
        player.setRoot(IdConverter.ORG_ROOT);
        player.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        player.setExtension(organization.getId().toString());

        HealthCareFacility healthCareFacility = new HealthCareFacility();


        healthCareFacility.setPlayerIdentifier(player);

        DSet<Tel> telSet = new DSet<Tel>();
        telSet.setItem(new HashSet<Tel>());
        ModelUtils.ContactSpec contactSpec = ModelUtils.ContactSpec.newInstance()
                .withEmail("bob@foo.org")
                .withPhone("123-456-7890")
                .withUrl("http://www.example.org");

        try {
            healthCareFacility.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(contactSpec.asDset()));
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }


        Address address = ModelUtils.getBasicAddress();
        AddressConverter.SetConverter converter = new AddressConverter.SetConverter();
        HashSet<Address> addresses = new HashSet<Address>();
        addresses.add(address);
        DSet<Ad> dset = converter.convert(DSet.class, addresses);

        healthCareFacility.setPostalAddress(DSETADTransformer.INSTANCE.toXml(dset));
        healthCareFacility.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return healthCareFacility;
    }


}
