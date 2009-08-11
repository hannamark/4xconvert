package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

public class NCISpecificInformationActionTest extends AbstractPaActionTest {

    NCISpecificInformationAction nciSpecificInformationAction;
    NCISpecificInformationWebDTO nciSpecificInformationWebDTO;
    
    @Before
    public void setUp() throws PAException {
        nciSpecificInformationAction = new NCISpecificInformationAction();
        nciSpecificInformationWebDTO = new NCISpecificInformationWebDTO();
        nciSpecificInformationWebDTO.setAccrualReportingMethodCode("");
        nciSpecificInformationAction.setNciSpecificInformationWebDTO(nciSpecificInformationWebDTO);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
    }
    
    @Test
    public void testExecute() {
        String result = nciSpecificInformationAction.execute();
        assertEquals("success", result);
    }

    @Test
    public void testQuery() {
        String result = nciSpecificInformationAction.query();
        assertEquals("error", result);
    }

    @Test
    public void testUpdate() {
        String result = nciSpecificInformationAction.update();
        assertEquals("error", result);
    }

    @Test(expected=Exception.class)
    public void testDisplayOrg() {
        String result = nciSpecificInformationAction.displayOrg();
        assertEquals("error", result);
    }

    @Test
    public void testLookup1() {
        String result = nciSpecificInformationAction.lookup1();
        assertEquals("success", result);
    }

}
