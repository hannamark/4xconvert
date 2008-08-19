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
        AbstractOrganizationSearchCriteria osc = new OrgEntityServiceSearchCriteria();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(osc);
    }

    @Test
    public void testHasAtLeastOneCriterionSpecified() {
        OrgEntityServiceSearchCriteria noCrit = new OrgEntityServiceSearchCriteria();
        assertFalse(noCrit.hasOneCriterionSpecified());
        
        OrgEntityServiceSearchCriteria yesCrit = new OrgEntityServiceSearchCriteria();
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().setName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().setAbbreviatedName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().setDescription("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPhone().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getFax().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getTty().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getEmail().add(new Email("test@example.com"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getUrl().add(new URL("http://www.example.com/"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setCityOrMunicipality("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setDeliveryAddressLine("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setPostalCode("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setStateOrProvince("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setStreetAddressLine("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setCountry(new Country());
        assertFalse(yesCrit.hasOneCriterionSpecified());

        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setCountry(new Country(null, null, null, "a"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getOrganization().getPostalAddress().setCountry(new Country(null, null, null, null));
        yesCrit.getOrganization().getPostalAddress().getCountry().setId(new Long(1));
        assertFalse(yesCrit.hasOneCriterionSpecified());
        
        
        //Not used...
        yesCrit.setOrganization(new Organization());
        yesCrit.getOrganization().setStatusCode(EntityStatus.CURATED);
        assertFalse(yesCrit.hasOneCriterionSpecified());
    }

}
