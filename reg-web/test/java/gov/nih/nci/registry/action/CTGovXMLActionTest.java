/**
 *
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

/**
 * Test CTGovXMLAction
 * @author bpickeral
 * 
 */
public class CTGovXMLActionTest extends AbstractRegWebTest {
   
    private static final String AUTHORIZATION_FAILED = "Authorization failed. User does not have ownership of the trial.";
    private static final String TEST_STRING = "test xml string";
    private final CTGovXmlGeneratorServiceLocal ctService = mock(CTGovXmlGeneratorServiceLocal.class);   
    private final StudyProtocolServiceLocal studyProtocolService = mock(StudyProtocolServiceLocal.class);
    private final RegistryUserService registryUserService = mock(RegistryUserService.class);

    /**
     * Test retrieveCtGovXML streams the generated xml as a file.
     * @throws PAException on PA error
     * @throws IOException on I/O error
     */
    @Test
    public void testRetrieveCtGovXML() throws PAException, IOException {
        CTGovXMLAction action = createCTGovXMLActionMock();
        when(action.isUserOwnerOfStudyProtocol(any(Ii.class))).thenReturn(true);
        action.setId("test id");
        action.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        when(ctService.generateCTGovXml((Ii) anyObject())).thenReturn(TEST_STRING);

        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        BufferedReader br = new BufferedReader(new InputStreamReader(action.getXmlFile()));
        assertTrue(br.readLine().contains(TEST_STRING));
    }

    /**
     * Test retrieveCtGovXML streams an error message when a PAException is thrown.
     * @throws PAException
     * @throws IOException
     */
    @Test
    public void testRetrieveCtGovXMLException() throws PAException, IOException {
        CTGovXMLAction action = createCTGovXMLActionMock();
        when(action.isUserOwnerOfStudyProtocol(any(Ii.class))).thenReturn(true);
        action.setId("test id");
        action.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        when(ctService.generateCTGovXml((Ii) anyObject())).thenThrow(new PAException());

        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        BufferedReader br = new BufferedReader(new InputStreamReader(action.getXmlFile()));
        assertTrue(br.readLine().contains("error"));
    }

    /**
     * Test retrieveCtGovXML streams an error message when a PAException is thrown.
     * @throws PAException
     * @throws IOException
     */
    @Test
    public void testRetrieveCtGovXMLNoTrialAccess() throws PAException, IOException {
        CTGovXMLAction action = createCTGovXMLActionMock();
        when(action.isUserOwnerOfStudyProtocol(any(Ii.class))).thenReturn(false);
        action.setId("test id");
        action.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);

        assertEquals("downloadXMLFile", action.retrieveCtGovXML());

        BufferedReader br = new BufferedReader(new InputStreamReader(action.getXmlFile()));
        assertTrue(br.readLine().contains(AUTHORIZATION_FAILED));
    }

    /**
     * Test if the user has access to trial
     * @throws PAException
     */
    @Test
    public void isUserOwnerOfStudyProtocolTrue() throws PAException {
        CTGovXMLAction action = createCTGovXMLActionMock();
        when(action.getUserName()).thenReturn("user");      
        Ii ii = createIi();
        StudyProtocolDTO studyProtocol = new StudyProtocolDTO();
        studyProtocol.setIdentifier(ii);
        when(studyProtocolService.getStudyProtocol(ii)).thenReturn(studyProtocol);
        when(registryUserService.getUser("user")).thenReturn(createRegistryUser());
        when(
             registryUserService.isTrialOwner(createRegistryUser().getId(),
                                              IiConverter.convertToLong(studyProtocol.getIdentifier())))
            .thenReturn(true);
        doCallRealMethod().when(action).isUserOwnerOfStudyProtocol(ii);

        boolean result = action.isUserOwnerOfStudyProtocol(ii);

        assertTrue(result);

    }

    /**
     * Test if the user has access to trial
     * @throws PAException
     */
    @Test
    public void isUserOwnerOfStudyProtocolFalse() throws PAException {
        CTGovXMLAction action = createCTGovXMLActionMock();
        when(action.getUserName()).thenReturn("user");       
        Ii ii = createIi();
        StudyProtocolDTO studyProtocol = new StudyProtocolDTO();
        studyProtocol.setIdentifier(ii);
        when(studyProtocolService.getStudyProtocol(ii)).thenReturn(studyProtocol);

        when(registryUserService.getUser("user")).thenReturn(createRegistryUser());
        when(
             registryUserService.isTrialOwner(createRegistryUser().getId(),
                                              IiConverter.convertToLong(studyProtocol.getIdentifier())))
            .thenReturn(false);

        doCallRealMethod().when(action).isUserOwnerOfStudyProtocol(ii);

        boolean result = action.isUserOwnerOfStudyProtocol(ii);

        assertFalse(result);

    }

    /**
     * Test if the user has access to trial
     * @throws PAException
     */
    @Test
    public void isUserOwnerOfStudyProtocolException() throws PAException {
        CTGovXMLAction action = createCTGovXMLActionMock();
        when(action.getUserName()).thenReturn("user");       
        Ii ii = createIi();
        when(studyProtocolService.getStudyProtocol(ii)).thenThrow(new PAException(""));

        doCallRealMethod().when(action).isUserOwnerOfStudyProtocol(ii);

        boolean result = action.isUserOwnerOfStudyProtocol(ii);

        assertFalse(result);

    }

    private RegistryUser createRegistryUser() {       
        RegistryUser user = new RegistryUser();
        user.setId(2L);
        return user;
    }
   

    private Ii createIi() {
        final Ii ii = new Ii();
        ii.setExtension("2");
        ii.setRoot("root");
        return ii;
    }

    private CTGovXMLAction createCTGovXMLActionMock() {
        CTGovXMLAction action = mock(CTGovXMLAction.class);
        doCallRealMethod().when(action).retrieveCtGovXML();
        doCallRealMethod().when(action).setCtService(ctService);       
        doCallRealMethod().when(action).setStudyProtocolService(studyProtocolService);
        doCallRealMethod().when(action).setRegistryUserService(registryUserService);
        doCallRealMethod().when(action).getXmlFile();
        action.setCtService(ctService);      
        action.setStudyProtocolService(studyProtocolService);
        action.setRegistryUserService(registryUserService);
        return action;
    }

}
