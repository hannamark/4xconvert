package gov.nih.nci.po.web.search;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.web.AbstractPoTest;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;


public class SearchFamilyActionTest extends AbstractPoTest {

    private SearchFamilyAction action;

    @Before
    public void setUp() {
        action = new SearchFamilyAction();
    }

    @Test
    public void testList() {
        assertEquals(Action.SUCCESS, action.list());
    }
    
}
