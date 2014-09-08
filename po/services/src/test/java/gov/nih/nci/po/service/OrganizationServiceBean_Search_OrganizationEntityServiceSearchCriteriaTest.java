package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

public class OrganizationServiceBean_Search_OrganizationEntityServiceSearchCriteriaTest extends OrganizationServiceBeanTest {

    AnnotatedBeanSearchCriteria<Organization> sc;

    @Before
    public void initData2() {
        sc = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
    }

    @Override
    public long createOrganization() throws EntityValidationException, JMSException {
        return super.createOrganization(getBasicOrganization());
    }

    @Test
    public void findByName() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrganization("testName", "defaultCity");
        createOrganization("abc", "defaultCity");

        sc.getCriteria().setName("%Nam%");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().setName("%a%");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getCriteria().setName("foobar");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByEmail() throws EntityValidationException, JMSException {
        removeCtepOrg();
        /* create a person with two email addresses */
        createOrganization();

        /* find a person with a matching email address */
        sc.getCriteria().getEmail().add(new Email("abc"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        /* then find a person with another matching email address */
        sc.getCriteria().getEmail().add(new Email("def"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getEmail().add(new Email("ghi"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getEmail().add(new Email("def"));
        sc.getCriteria().getEmail().add(new Email("ghi"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByPhone() throws EntityValidationException, JMSException {
        removeCtepOrg();
        /* create a person with two phone numbers */
        createOrganization();

        initData2();
        sc.getCriteria().getPhone().add(new PhoneNumber("111"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getPhone().add(new PhoneNumber("123"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getPhone().add(new PhoneNumber("345"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getPhone().add(new PhoneNumber("345"));
        sc.getCriteria().getPhone().add(new PhoneNumber("123"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByFax() throws EntityValidationException, JMSException {
        removeCtepOrg();
        /* create a person with two phone numbers */
        createOrganization();

        initData2();
        sc.getCriteria().getFax().add(new PhoneNumber("222"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getFax().add(new PhoneNumber("234"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getFax().add(new PhoneNumber("456"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getFax().add(new PhoneNumber("234"));
        sc.getCriteria().getFax().add(new PhoneNumber("456"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByTty() throws EntityValidationException, JMSException {
        removeCtepOrg();
        /* create a person with two phone numbers */
        createOrganization();

        initData2();
        sc.getCriteria().getTty().add(new PhoneNumber("333"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getTty().add(new PhoneNumber("345"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getTty().add(new PhoneNumber("567"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getTty().add(new PhoneNumber("345"));
        sc.getCriteria().getTty().add(new PhoneNumber("567"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByUrl() throws EntityValidationException, JMSException {
        removeCtepOrg();
        /* create a person with two phone numbers */
        createOrganization();

        initData2();
        sc.getCriteria().getUrl().add(new URL("http://www.example.com/abc"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getUrl().add(new URL("http://www.example.com/def"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getUrl().add(new URL("http://www.example.com/ghi"));
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        initData2();
        sc.getCriteria().getUrl().add(new URL("http://www.example.com/d"));
        sc.getCriteria().getUrl().add(new URL("http://www.example.com/g"));
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
    }

    private void createOrgsWithAddresses() throws EntityValidationException, JMSException {
        Organization p = getBasicOrganization();
        Address pa = new Address("P.O. Box 12345", "New-City", "VA", "12345-6789", getDefaultCountry());
        pa.setDeliveryAddressLine("c/o Mark Wild");
        p.setPostalAddress(pa);
        createOrganization(p);

        p = getBasicOrganization();
        pa = new Address("4567 First Ave.", "Harper's Ferry", "WV", "98765-4321", getDefaultCountry());
        pa.setDeliveryAddressLine("c/o John Doe");
        p.setPostalAddress(pa);
        createOrganization(p);

        p = getBasicOrganization();
        pa = new Address("P.O. Box 12364", "Old-City", "PA", "12345-6789", getDefaultCountry());
        p.setPostalAddress(pa);
        createOrganization(p);
    }

    @Test
    public void findByAddressStreetAddressLine() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();
        sc.getCriteria().setPostalAddress(new Address());

        sc.getCriteria().getPostalAddress().setStreetAddressLine("P.O. Box");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setStreetAddressLine("P.O. Box 1234");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        // contains will find substring
        sc.getCriteria().getPostalAddress().setStreetAddressLine("345");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setStreetAddressLine("Z");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByAddressDeliveryAddressLine() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();
        sc.getCriteria().setPostalAddress(new Address());

        sc.getCriteria().getPostalAddress().setDeliveryAddressLine("c/o");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setDeliveryAddressLine("c/o John");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setDeliveryAddressLine("c/o Mark");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setDeliveryAddressLine("%");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setDeliveryAddressLine("Z");
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByAddressCity() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();
        sc.getCriteria().setPostalAddress(new Address());

        sc.getCriteria().getPostalAddress().setCityOrMunicipality("New-");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());
        // contains will find the substring
        sc.getCriteria().getPostalAddress().setCityOrMunicipality("-City");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        // contains search will find substrings in Harper's Ferry.
        sc.getCriteria().getPostalAddress().setCityOrMunicipality("'");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setCityOrMunicipality("Harper'");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setCityOrMunicipality("%");
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
    }

    @Test
    public void findByAddressPostalCode() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();
        sc.getCriteria().setPostalAddress(new Address());

        sc.getCriteria().getPostalAddress().setPostalCode("12345-");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
        // contains will find substring dash between all zip codes.
        sc.getCriteria().getPostalAddress().setPostalCode("-");
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setPostalCode("123");
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setPostalCode("987");
        assertEquals(1, getOrgServiceBean().count(sc));
        assertEquals(1, getOrgServiceBean().search(sc).size());

        sc.getCriteria().getPostalAddress().setPostalCode("%");
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void findByAddressCountry() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();

        sc.getCriteria().setPostalAddress(new Address());
        Country c = new Country();
        c.setId(getDefaultCountry().getId() - 1L);
        sc.getCriteria().getPostalAddress().setCountry(c);
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        c = new Country();
        c.setId(getDefaultCountry().getId());
        sc.getCriteria().getPostalAddress().setCountry(c);
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());

    }


    @Test
    public void findByStatusCode() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();

        sc.getCriteria().setStatusCode(EntityStatus.ACTIVE);
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.getCriteria().setStatusCode(EntityStatus.PENDING);
        assertEquals(3, getOrgServiceBean().count(sc));
        assertEquals(3, getOrgServiceBean().search(sc).size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findByStatusCodeEnsureRejectedAreNotReturned() throws EntityValidationException, JMSException {
        removeCtepOrg();
        createOrgsWithAddresses();
        Query query = PoHibernateUtil.getCurrentSession().createQuery("from Organization o");
        List<Organization> list = query.list();
        Organization o = list.iterator().next();
        assertTrue(EntityStatus.PENDING.canTransitionTo(EntityStatus.NULLIFIED));
        o.setStatusCode(EntityStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        sc.getCriteria().setStatusCode(EntityStatus.NULLIFIED);
        assertEquals(0, getOrgServiceBean().count(sc));
        assertEquals(0, getOrgServiceBean().search(sc).size());

        sc.getCriteria().setStatusCode(EntityStatus.PENDING);
        assertEquals(2, getOrgServiceBean().count(sc));
        assertEquals(2, getOrgServiceBean().search(sc).size());
    }
    
    @Test
    public void familySearch() throws Exception {
        removeCtepOrg();
        Calendar cal = Calendar.getInstance();
        cal.set(2008, 01, 02);
        Date oldDate = DateUtils.truncate(cal.getTime(), Calendar.DATE);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        createOrganization(o);
        createOrganization(o2);
       
        Family family = getFamily("FamilyName", oldDate);
        long id = EjbTestHelper.getFamilyServiceBean().create(family);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Family savedFam = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
        assertNotNull(savedFam.getId());
              
        FamilyOrganizationRelationshipServiceLocal familyOrgRelServiceLocal 
            = PoRegistry.getFamilyOrganizationRelationshipService();
        FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
        famOrgRel.setOrganization(o);
        famOrgRel.setFamily(savedFam);
        famOrgRel.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRel.setStartDate(savedFam.getStartDate());
        familyOrgRelServiceLocal.createEntity(famOrgRel);
        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        FamilyOrganizationRelationship famOrgRel2 = new FamilyOrganizationRelationship();
        famOrgRel2.setOrganization(o2);
        famOrgRel2.setFamily(savedFam);
        famOrgRel2.setFunctionalType(FamilyFunctionalType.CONTRACTUAL);
        famOrgRel2.setStartDate(savedFam.getStartDate());
        familyOrgRelServiceLocal.createEntity(famOrgRel2);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        LimitOffset page = new LimitOffset(100, 0);
        Organization orgBO = new Organization();
        orgBO.setName("oName");
        Family fam = new Family();
        fam.setName("milyNa");
        FamilyOrganizationRelationship searchFamOrgRel = new FamilyOrganizationRelationship();
        searchFamOrgRel.setFamily(fam);
        orgBO.getFamilyOrganizationRelationships().add(searchFamOrgRel);
        sc = new AnnotatedBeanSearchCriteria<Organization>(orgBO);
        int maxLimit = Math.min(page.getLimit(), 101);
        PageSortParams<Organization> params = new PageSortParams<Organization>(maxLimit, page
                .getOffset(), OrganizationSortCriterion.ORGANIZATION_ID, false);
        assertEquals(2, getOrgServiceBean().search(sc, params).size()); 
   }
}
