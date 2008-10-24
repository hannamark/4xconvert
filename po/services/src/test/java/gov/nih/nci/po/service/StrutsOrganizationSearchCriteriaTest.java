package gov.nih.nci.po.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class StrutsOrganizationSearchCriteriaTest {

    private StrutsOrganizationSearchCriteria sc;

    @Before
    public void init() {
        sc = new StrutsOrganizationSearchCriteria();
    }
    
    @Test
    public void constructor() {
        assertNotNull(sc.getCriteria());
        assertNotNull(sc.getEmailEntry());
        assertNotNull(sc.getFaxEntry());
        assertNotNull(sc.getPhoneEntry());
        assertNotNull(sc.getTtyEntry());
        assertNotNull(sc.getUrlEntry());
        assertNotNull(sc.getOrganization().getPostalAddress());
        assertNotNull(sc.getOrganization().getPostalAddress().getCountry());
    }
    
    @Test
    public void hasOneCriterionSpecified() {
        assertFalse(sc.hasOneCriterionSpecified());
        
        init();
        sc.getEmailEntry().setValue("foo");
        assertTrue(sc.hasOneCriterionSpecified());
        
        init();
        sc.getPhoneEntry().setValue("foo");
        assertTrue(sc.hasOneCriterionSpecified());
        
        init();
        sc.getFaxEntry().setValue("foo");
        assertTrue(sc.hasOneCriterionSpecified());
        
        init();
        sc.getTtyEntry().setValue("foo");
        assertTrue(sc.hasOneCriterionSpecified());
        
        init();
        sc.getUrlEntry().setValue("foo");
        assertTrue(sc.hasOneCriterionSpecified());
    }
}

