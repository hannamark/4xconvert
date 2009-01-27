package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertEquals;

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
        instance.setCountryId(100L);
        String result = instance.loadCountry();
        assertEquals(Action.SUCCESS, result);
        assertEquals(100L, instance.getCountry().getId().longValue());
    }

}