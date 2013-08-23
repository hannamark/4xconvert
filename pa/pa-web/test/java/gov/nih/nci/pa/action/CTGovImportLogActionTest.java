package gov.nih.nci.pa.action;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;

/**
 * @author ADas
 */
public class CTGovImportLogActionTest extends AbstractPaActionTest {
    private CTGovImportLogAction ctGovImportLogAction;
    private CTGovSyncServiceLocal ctGovSyncServiceLocal;
    
    /**
     * Initialization method.
     * @throws PAException if an error occurs
     */
    @Before
    public void setUp() throws PAException {
        ctGovImportLogAction = new CTGovImportLogAction();
        ctGovSyncServiceLocal = mock(CTGovSyncServiceLocal.class);
        ctGovImportLogAction.setCtGovSyncService(ctGovSyncServiceLocal);
        when(ctGovSyncServiceLocal.getLogEntries(any(String.class), any(String.class), any(String.class), 
                any(String.class), any(String.class), any(String.class), any(Date.class), 
                any(Date.class))).thenReturn(new ArrayList<CTGovImportLog>());
        ctGovImportLogAction.prepare();        
    }
    
    @Test
    public void testExecute() {
        assertEquals("success", ctGovImportLogAction.execute());
    }
    
    @Test    
    public void testQuery() {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 3);
        Date endDate = calendar.getTime();
        ctGovImportLogAction.setLogsOnOrAfter(dateFormat.format(startDate));
        ctGovImportLogAction.setLogsOnOrBefore(dateFormat.format(endDate));
        ctGovImportLogAction.setAction("Update");
        ctGovImportLogAction.setImportStatus("Failure");
        ctGovImportLogAction.setNciIdentifier("NCI");
        ctGovImportLogAction.setNctIdentifier("NCT");
        ctGovImportLogAction.setUserCreated("User");
        ctGovImportLogAction.setOfficialTitle("Title");
        assertEquals("success", ctGovImportLogAction.query());
        assertNotNull(ctGovImportLogAction.getCtGovImportLogs());        
    }
}
