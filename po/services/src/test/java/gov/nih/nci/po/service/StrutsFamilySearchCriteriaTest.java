package gov.nih.nci.po.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class StrutsFamilySearchCriteriaTest {

    private StrutsFamilySearchCriteria sc;

    @Before
    public void init() {
        sc = new StrutsFamilySearchCriteria();
    }
    
    @Test
    public void constructor() {
        assertNotNull(sc.getCriteria());
    }
    
    @Test
    public void hasOneCriterionSpecified() {
        assertTrue(sc.hasOneCriterionSpecified());
    }
}

