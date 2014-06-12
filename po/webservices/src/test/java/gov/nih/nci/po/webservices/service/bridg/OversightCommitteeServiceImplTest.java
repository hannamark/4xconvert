package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformerTest;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OversightCommitteeCR;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;
import gov.nih.nci.po.webservices.convert.bridg.OversightCommitteeTransformer;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.criterion.Restrictions;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OversightCommitteeServiceImplTest {
    private OversightCommitteeServiceImpl service;
    private ServiceLocator oldLocator;
    private DbTestUtil dbTestUtil;
    private Person person;
    private Organization organization;
    private OversightCommitteeType defaultType;

    @Before
    public void setUp() throws Exception {


        oldLocator = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(new TestServiceLocator());

        dbTestUtil = DbTestUtil.getInstance();
        dbTestUtil.setup();

        this.service = new OversightCommitteeServiceImpl();
        // this.service.setMaxHitsPerRequest(100);

        //stage person
        person = ModelUtils.getBasicPerson();
        PoHibernateUtil.getCurrentSession().save( ModelUtils.getDefaultCountry());
        PoHibernateUtil.getCurrentSession().save(person);

        //stage org
        organization = ModelUtils.getBasicOrganization();
        PoHibernateUtil.getCurrentSession().save(organization);

        defaultType = new OversightCommitteeType("defaultType");
        PoHibernateUtil.getCurrentSession().save(defaultType);

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
        OversightCommittee oversightCommittee = getBasicBridgOversightCommittee();

        Id createdId = service.create(oversightCommittee);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithId() throws EntityValidationException {

        OversightCommittee oversightCommittee = getBasicBridgOversightCommittee();

        II id = new II();
        id.setRoot(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(IdConverter.CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension("123");

        oversightCommittee.setIdentifier(IdTransformerTest.convertIIToDSETII(id));

        service.create(oversightCommittee);

    }

    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException {
        //Should not be able to create it with a null player
        OversightCommittee oversightCommittee = getBasicBridgOversightCommittee();
        oversightCommittee.setPlayerIdentifier(null);

        service.create(oversightCommittee);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));


        List<OversightCommittee> results = service.query(oversightCommittee, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {

        RoleStatus[] nonactive = new RoleStatus[]{RoleStatus.NULLIFIED, RoleStatus.PENDING, RoleStatus.SUSPENDED};

        //stage 5 active, 5 non-active instances
        for(int i=0; i<5; i++ ) {

            gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);

            gov.nih.nci.po.data.bo.OversightCommittee nonActiveInstance = ModelUtils.getBasicOversightCommittee(defaultType);
            nonActiveInstance.setStatus(nonactive[i % nonactive.length]);

            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        List<OversightCommittee> results = service.query(oversightCommittee, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(oversightCommittee, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {
        //stage 5 active
        for(int i=0; i<=service.getMaxHitsPerRequest(); i++ ) {

            gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        service.query(oversightCommittee, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {

        RoleStatus[] nonactive = new RoleStatus[]{RoleStatus.NULLIFIED, RoleStatus.PENDING, RoleStatus.SUSPENDED};

        for(int i=0; i<5; i++ ) {
            gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
            activeInstance.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);



            gov.nih.nci.po.data.bo.OversightCommittee nonActiveInstance = ModelUtils.getBasicOversightCommittee(defaultType);
            nonActiveInstance.setStatus(nonactive[i % nonactive.length]);

            PoHibernateUtil.getCurrentSession().save(nonActiveInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(nonActiveInstance);

        }

        PoHibernateUtil.getCurrentSession().flush();

        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        List<OversightCommittee> results = service.query(oversightCommittee, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(results.size(), 2);

        results = service.query(oversightCommittee, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(results.size(), 2);

        results = service.query(oversightCommittee, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(results.size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        service.query(oversightCommittee, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.OversightCommittee instance = ModelUtils.getBasicOversightCommittee(defaultType);
        PoHibernateUtil.getCurrentSession().save(instance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        assertEquals(1, instance.getPhone().size());
        //update it
        OversightCommitteeDTO dto = (OversightCommitteeDTO) PoXsnapshotHelper.createSnapshot(instance);
        OversightCommittee xml = OversightCommitteeTransformer.INSTANCE.toXml(dto);

        OversightCommitteeType newType = new OversightCommitteeType("newType");
        PoHibernateUtil.getCurrentSession().save(newType);
        PoHibernateUtil.getCurrentSession().flush();

        CD newTypeCd = new CD();
        newTypeCd.setCode(newType.getCode());
        xml.setTypeCode(newTypeCd);

        service.update(xml);

        //retrieve it
        OversightCommitteeCR cr = (OversightCommitteeCR) PoHibernateUtil.getCurrentSession().createCriteria(OversightCommitteeCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //compare it
        assertEquals(newType, cr.getTypeCode());
    }


    @Test
    public void testUpdateStatus() throws EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.OversightCommittee instance = ModelUtils.getBasicOversightCommittee(defaultType);
        instance.setStatus(RoleStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(instance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(instance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(instance.getId());

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        service.updateStatus(instanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));

        //retrieve it
        OversightCommitteeCR cr = (OversightCommitteeCR) PoHibernateUtil.getCurrentSession().createCriteria(OversightCommitteeCR.class)
                .add(Restrictions.eq("target.id", instance.getId())).uniqueResult();

        //verify
        assertEquals(RoleStatus.ACTIVE, cr.getStatus());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(RandomUtils.nextLong());

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));
    }

    @Test
    public void testValidateWithNoErrors() {
        OversightCommittee oversightCommittee = getBasicBridgOversightCommittee();
        CD statusCd = new Cd();
        statusCd.setCode(RoleStatus.PENDING.toString());
        oversightCommittee.setStatus(statusCd);
        StringMap errors = service.validate(oversightCommittee);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        OversightCommittee oversightCommittee = getBasicBridgOversightCommittee();
        oversightCommittee.setPlayerIdentifier(null);
        StringMap errors = service.validate(oversightCommittee);
        assertEquals("Expected errors were not detected.", errors.getEntry().size(), 1);
    }

    @Test
    public void testGetByPlayerIds() throws NullifiedRoleException {
        //create 5 with org1
        Organization organization1 = ModelUtils.getBasicOrganization();
        organization1.setName("Org1");
        PoHibernateUtil.getCurrentSession().save(organization1);

        Organization organization2 = ModelUtils.getBasicOrganization();
        organization2.setName("Org2");
        PoHibernateUtil.getCurrentSession().save(organization2);

        Organization organization3 = ModelUtils.getBasicOrganization();
        organization3.setName("Org3");
        PoHibernateUtil.getCurrentSession().save(organization3);

        gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
        activeInstance.setPlayer(organization1);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);

        activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
        activeInstance.setPlayer(organization2);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);


        activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
        activeInstance.setPlayer(organization3);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);

        PoHibernateUtil.getCurrentSession().flush();

        //check []
        List<Id> ids = new ArrayList<Id>();
        List<OversightCommittee> hits = service.getByPlayerIds(ids);
        //expect 0
        assertEquals(0, hits.size());

        //check [1]

        ids.add(getOrganizationId(organization1));
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(1, hits.size());

        //check [1,1]
        ids.add(getOrganizationId(organization1));
        hits = service.getByPlayerIds(ids);
        //expect 5
        assertEquals(1, hits.size());

        //check [1,2]
        ids.clear();
        ids.add(getOrganizationId(organization1));
        ids.add(getOrganizationId(organization2));
        hits = service.getByPlayerIds(ids);
        //expect 2
        assertEquals(2, hits.size());

        //check [1,2,3]
        ids.add(getOrganizationId(organization3));
        hits = service.getByPlayerIds(ids);
        //expect 3
        assertEquals(3, hits.size());

        //check [2,3]
        ids.remove(0);
        hits = service.getByPlayerIds(ids);
        //expect 2
        assertEquals(2, hits.size());

        //check [3]
        ids.remove(0);
        hits = service.getByPlayerIds(ids);
        //expect 1
        assertEquals(1, hits.size());
    }




    @Test
    public void testGetById() throws NullifiedRoleException {
        gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        OversightCommittee hit = service.getById(instanceId);
        assertNotNull(hit);
    }

    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedById() throws NullifiedRoleException {
        gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
        activeInstance.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
        PoHibernateUtil.getCurrentSession().save(activeInstance);
        PoHibernateUtil.getCurrentSession().flush();

        Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(activeInstance.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        service.getById(instanceId);
    }

    @Test
    public void testGetNonExistingInstanceById() throws NullifiedRoleException {
        Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(RandomUtils.nextLong());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        OversightCommittee hit = service.getById(instanceId);
        assertNull(hit);
    }

    @Test
    public void testGetByIds() throws NullifiedRoleException {
        List<Long> instanceIds = new ArrayList<Long>();

        for (int i=0; i<5; i++) {
            gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
            PoHibernateUtil.getCurrentSession().save(activeInstance.getPlayer());
            PoHibernateUtil.getCurrentSession().save(activeInstance);
            instanceIds.add(activeInstance.getId());
        }

        PoHibernateUtil.getCurrentSession().flush();

        List<Id> ids = new ArrayList<Id>();
        for (Long instanceId : instanceIds) {
            Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }
        List<OversightCommittee> hits = service.getByIds(ids);
        assertEquals(5, hits.size());
    }


    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedByIds() throws NullifiedRoleException {
        List<Long> instanceIds = new ArrayList<Long>();

        for (int i=0; i<5; i++) {
            gov.nih.nci.po.data.bo.OversightCommittee activeInstance = ModelUtils.getBasicOversightCommittee(defaultType);
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
            Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }
        service.getByIds(ids);
    }

    @Test
    public void testGetByIdsWithEmptyInput() throws NullifiedRoleException {
        List<OversightCommittee> hits = service.getByIds(Collections.EMPTY_LIST);
        assertEquals(0, hits.size());
    }

    private Id getOrganizationId(Organization organization) {
        Ii ii = new IdConverter.OversightCommitteeIdConverter().convertToIi(organization.getId());
        return IdTransformer.INSTANCE.toXml(ii);
    }

    private final OversightCommittee getBasicBridgOversightCommittee()  {
        II player = new II();
        player.setRoot(IdConverter.ORG_ROOT);
        player.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        player.setExtension(organization.getId().toString());

        OversightCommittee oversightCommittee = new OversightCommittee();


        oversightCommittee.setPlayerIdentifier(player);

        CD typeCd = new CD();
        typeCd.setCode(defaultType.getCode());
        oversightCommittee.setTypeCode(typeCd);

        oversightCommittee.setStatus(new CD().withCode(RoleStatus.ACTIVE.toString()));

        return oversightCommittee;
    }
}
