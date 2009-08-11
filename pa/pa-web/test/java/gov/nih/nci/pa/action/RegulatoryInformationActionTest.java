/**
 * 
 */
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class RegulatoryInformationActionTest extends AbstractPaActionTest {

    private static RegulatoryInformationAction regulatoryInformationAction;
    
    @Before
    public void setUp(){
        regulatoryInformationAction = new RegulatoryInformationAction();
        regulatoryInformationAction.setLst(null);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
    }
    
    /**
     * Test method for {@link gov.nih.nci.pa.action.RegulatoryInformationAction#update()}.
     */
    @Test(expected=Exception.class)
    public void testUpdate() {
        regulatoryInformationAction.setLst("");
        regulatoryInformationAction.setSelectedRegAuth("");
        regulatoryInformationAction.update();
        assertTrue(regulatoryInformationAction.hasFieldErrors());
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.RegulatoryInformationAction#query()}.
     */
    @Test(expected=Exception.class)
    public void testQuery() {
        String result = regulatoryInformationAction.query();
        assertEquals("success", result);
    }

    /**
     * Test method for {@link gov.nih.nci.pa.action.RegulatoryInformationAction#getRegAuthoritiesList()}.
     */
    @Test
    public void testGetRegAuthoritiesList() {
        regulatoryInformationAction.getRegAuthoritiesList();
        assertNotNull(regulatoryInformationAction.getRegIdAuthOrgList());
    }

}
