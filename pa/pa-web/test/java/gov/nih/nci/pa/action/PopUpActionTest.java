/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PopUpActionTest extends AbstractPaActionTest {

    PopUpAction popUpAction;
    
    @Before
    public void setUp(){
        popUpAction = new PopUpAction();
        List<String> countryList = new ArrayList <String>();
        getSession().setAttribute("countrylist",countryList);
        
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#lookupcontactpersons()}.
     */
    @Test
    public void testLookupcontactpersons() throws Exception {
        String result = popUpAction.lookupcontactpersons();
        assertEquals("contactpersons",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#lookuporgs()}.
     */
    @Test
    public void testLookuporgs() {
        String result = popUpAction.lookuporgs();
        assertEquals("orgs",result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.PopUpAction#displayOrgList()}.
     */
    @Test
    public void testLookuppersons() {
        String result = popUpAction.lookuppersons();
        assertEquals("persons",result);
    }

    
   
}
