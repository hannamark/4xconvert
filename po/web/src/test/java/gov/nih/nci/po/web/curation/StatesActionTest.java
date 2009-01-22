package gov.nih.nci.po.web.curation;

import org.junit.Test;
import static org.junit.Assert.*;

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
        assertEquals(100L, instance.getCountry().getId().longValue());
    }

}