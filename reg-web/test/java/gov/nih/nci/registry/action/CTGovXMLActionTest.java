/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;


/**
 * Test CTGovXMLAction
 * @author bpickeral
 *
 */
public class CTGovXMLActionTest extends AbstractRegWebTest {
    private static final String TEST_STRING = "test xml string";
    private CTGovXMLAction action;
    private final CTGovXmlGeneratorServiceLocal ctService = mock(CTGovXmlGeneratorServiceLocal.class);

    @Before
    public void setup() throws PAException {
        action = new CTGovXMLAction();
        action.setCtService(ctService);
    }

    /**
     * Test retrieveCtGovXML streams the generated xml as a file.
     * @throws PAException on PA error
     * @throws IOException on I/O error
     */
    @Test
    public void testRetrieveCtGovXML() throws PAException, IOException {
        action.setId("test id");
        action.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        when(ctService.generateCTGovXml((Ii) anyObject())).thenReturn(TEST_STRING);

        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        BufferedReader br= new BufferedReader(new InputStreamReader(action.getXmlFile()));
        assertTrue(br.readLine().contains(TEST_STRING));
    }

    /**
     * Test retrieveCtGovXML streams an error message when a PAException is thrown.
     * @throws PAException
     * @throws IOException
     */
    @Test
    public void testRetrieveCtGovXMLException() throws PAException, IOException {
        action.setId("test id");
        action.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        when(ctService.generateCTGovXml((Ii) anyObject())).thenThrow(new PAException());

        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        BufferedReader br= new BufferedReader(new InputStreamReader(action.getXmlFile()));
        assertTrue(br.readLine().contains("error"));
    }

}
