package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.opensymphony.xwork2.Action;

/**
 *
 * @author gax
 */
public class StatesActionTest {


    @Test
    public void testLoadCountry() {
        StatesAction instance = new StatesAction();
        instance.setCountryId("100");
        String result = instance.loadCountry();
        assertEquals(Action.SUCCESS, result);
        assertEquals(100L, instance.getCountry().getId().longValue());
    }

    @Test
    public void testField() {
        StatesAction instance = new StatesAction();
        instance.setField("a");
        assertEquals(instance.getField(), "a");
        instance.setField("a.b");
        assertEquals(instance.getField(), "a.b");
        instance.setField("abc");
        assertEquals(instance.getField(), "abc");
        instance.setField("organization.postalAddress.stateOrProvince");
        assertEquals(instance.getField(), "organization.postalAddress.stateOrProvince");
        try {
            instance.setField("a.");
            fail();
        } catch (IllegalArgumentException ex) {
        }  
        try {
            instance.setField("organization.postal Address.stateOrProvince");
            fail();
        } catch (IllegalArgumentException ex) {
        }
        try {
            instance.setField(".a");
            fail();
        } catch (IllegalArgumentException ex) {
        }
        
    }
    
    @Test
    public void testFieldNoCatastrophicBacktrackingPO8677() {
        StatesAction instance = new StatesAction();
        try {
            instance.setField("organization.postalAddress.stateOrProvince.");
            fail();
        } catch (IllegalArgumentException ex) {
        }

    }

}