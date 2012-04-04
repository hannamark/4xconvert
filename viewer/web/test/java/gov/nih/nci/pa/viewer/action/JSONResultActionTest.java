package gov.nih.nci.pa.viewer.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Monish Dombla
 *
 */
public class JSONResultActionTest extends AbstractViewerActionTest{

    private ProtocolQueryServiceLocal protocolQueryService;
    private JSONResultAction action;
    
    @Before
    public void setUp() throws PAException {
        action = new JSONResultAction();
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        ServiceLocator serviceLocator = mock(ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(serviceLocator);
        when(serviceLocator.getProtocolQueryService()).thenReturn(protocolQueryService);
    }
    
    @Test
    public void testLoadOfficalTitles() throws PAException {
        List<String> officialtitles = new ArrayList<String>();
        officialtitles.add("One");
        officialtitles.add("Two");
        when(PaRegistry.getProtocolQueryService().getOfficialTitles(any(String.class))).thenReturn(officialtitles);
        action.loadOfficalTitles();
        assertNotNull(action.getOfficialTitles());
        assertEquals(2, action.getOfficialTitles().size());
    }
}
