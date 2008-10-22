package gov.nih.nci.po.service;

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

import org.junit.Test;

public class OrgEntityServiceSearchCriteriaTest {

    @Test
    public void testGettersAndSetters() throws Exception {
        AnnotatedBeanSearchCriteria<Organization> sc = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(sc);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testHasAtLeastOneCriterionSpecified() {
        AnnotatedBeanSearchCriteria<Organization> noCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(noCrit.hasOneCriterionSpecified());

        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().setName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().setAbbreviatedName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().setDescription("name");
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
    public void test1() {
        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setCountry(new Country());
        assertFalse(yesCrit.hasOneCriterionSpecified());
    }
    
    @Test
    public void test2() {
        AnnotatedBeanSearchCriteria<Organization> yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit = new AnnotatedBeanSearchCriteria<Organization>(new Organization());
        yesCrit.getCriteria().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getCriteria().getPostalAddress().setCountry(new Country(null, null, null, "a"));
        assertFalse(yesCrit.hasOneCriterionSpecified());
    }
}
