package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.GetterSetterTesterUtil;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import org.hibernate.Query;
import org.junit.Test;

public class OrgEntityServiceSearchCriteriaTest extends AbstractHibernateTestCase {

    @Test
    public void testGettersAndSetters() throws Exception {
        AnnotatedBeanSearchCriteria<Organization> sc = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(sc);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testHasAtLeastOneCriterionSpecified() {
        AnnotatedBeanSearchCriteria<Organization> noCrit = new AnnotatedBeanSearchCriteria<Organization>(
                new Organization());
        assertFalse(noCrit.hasOneCriterionSpecified());

        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(
                new Organization());
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().setName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPhone().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getFax().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getTty().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getEmail().add(new Email("test@example.com"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getUrl().add(new URL("http://www.example.com/"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setCityOrMunicipality("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setDeliveryAddressLine("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setPostalCode("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setStateOrProvince("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setStreetAddressLine("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        Country country = new Country();
        country.setId(1L);
        yesCrit.getCriteria().getPostalAddress().setCountry(country);
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().setStatusCode(EntityStatus.ACTIVE);
        assertTrue(yesCrit.hasOneCriterionSpecified());

    }

    @Test
    @SuppressWarnings("deprecation")
    public void test1() {
        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(
                new Organization());
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setCountry(new Country());
        assertFalse(yesCrit.hasOneCriterionSpecified());
    }

    @Test
    public void testCountrySearchableOnAlpha3() {
        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(
                new Organization());
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setCountry(new Country(null, null, null, "a"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
    }

    @Test
    public void testHasAtLeastOneCriterionSpecified_TelecomAddressFields() {

        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(
                new Organization());
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().getEmail().add(new Email());
        yesCrit.getCriteria().getEmail().add(new Email());
        assertFalse(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().getEmail().add(new Email(null));
        assertFalse(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().getEmail().add(new Email(""));
        assertFalse(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().getEmail().add(new Email(null));
        yesCrit.getCriteria().getEmail().add(new Email(""));
        assertFalse(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().getEmail().add(new Email(null));
        yesCrit.getCriteria().getEmail().add(new Email("a"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().getEmail().add(new Email(""));
        yesCrit.getCriteria().getEmail().add(new Email("a"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

    }

    @Test
    public void getQuery_TelecomAddressFields1() {

        AnnotatedBeanSearchCriteria<Organization> yesCrit;
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email());
        yesCrit.getCriteria().getEmail().add(new Email());
        Query query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj", query.getQueryString());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email(null));
        query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj", query.getQueryString());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email(""));
        query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj", query.getQueryString());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email(null));
        yesCrit.getCriteria().getEmail().add(new Email(""));
        query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj", query.getQueryString());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email(null));
        yesCrit.getCriteria().getEmail().add(new Email("a"));
        query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj join obj.email obj_email "
                + "WHERE  (  ( lower(obj_email.value) like :obj_email_value_0 )  ) ",
                query.getQueryString());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email(""));
        yesCrit.getCriteria().getEmail().add(new Email("a"));
        query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj join obj.email obj_email "
                + "WHERE  (  ( lower(obj_email.value) like :obj_email_value_0 )  ) ",
                query.getQueryString());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization(), false);
        yesCrit.getCriteria().getEmail().add(new Email("a"));
        yesCrit.getCriteria().getEmail().add(new Email("b"));
        query = yesCrit.getQuery("", false);
        assertEquals(" SELECT DISTINCT obj FROM gov.nih.nci.po.data.bo.Organization obj join obj.email obj_email "
                + "WHERE  (  ( lower(obj_email.value) like :obj_email_value_0"
                + "   OR   lower(obj_email.value) like :obj_email_value_1 )  ) ", query.getQueryString());
    }

}
