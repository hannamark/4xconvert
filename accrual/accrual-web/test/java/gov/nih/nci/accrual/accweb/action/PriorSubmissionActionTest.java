package gov.nih.nci.accrual.accweb.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;

import com.mockrunner.mock.web.MockHttpServletResponse;
import com.opensymphony.xwork2.Action;

public class PriorSubmissionActionTest extends AbstractAccrualActionTest {

    private PriorSubmissionsAction action;
    private String sTestDate = "1/1/2012";
    private Timestamp tsTestDate = PAUtil.dateStringToTimestamp(sTestDate);
    private RegistryUser testRu = null;
    private Long testBatchId = 123L;

    @Before
    public void initAction() throws PAException, URISyntaxException {
        action = new PriorSubmissionsAction();
        action.prepare();
        List<HistoricalSubmissionDto> hsList = new ArrayList<HistoricalSubmissionDto>();
        HistoricalSubmissionDto hsItem = new HistoricalSubmissionDto();
        hsItem.setBatchFileIdentifier(testBatchId);
        hsList.add(hsItem);
        when(action.getSubmissionHistorySvc().search(any(Timestamp.class), any(Timestamp.class), any(RegistryUser.class)))
            .thenReturn(hsList);
        BatchFile bf = new BatchFile();
        File file = new File(this.getClass().getResource("/CDUS_Complete.txt").toURI());
        bf.setFileLocation(file.getAbsolutePath());
        when(action.getBatchFileSvc().getById(anyLong())).thenReturn(bf);
        testRu = PaServiceLocator.getInstance().getRegistryUserService().getUser(CaseSensitiveUsernameHolder.getUser());
        ServletActionContext.setResponse(new MockHttpServletResponse());
    }

    @Override
    @Test
    public void executeTest() {
        assertEquals(Action.SUCCESS, action.execute());
    }

    @Test
    public void executeWithDatesTest() throws PAException {
        action.setDateFrom(sTestDate);
        action.setDateTo(sTestDate);
        assertEquals(Action.SUCCESS, action.execute());
        verify(action.getSubmissionHistorySvc()).search(tsTestDate, tsTestDate, testRu);
    }

    @Test
    public void viewDocFileTest() throws PAException {
        action.setBatchFileId(testBatchId);
        assertEquals(Action.NONE, action.viewDoc());
        assertFalse(action.hasActionErrors());
    }

    @Test
    public void viewDocFileNotFoundTest() throws PAException {
        assertEquals(Action.SUCCESS, action.viewDoc());
        assertTrue(action.getActionErrors().contains("Error retrieving file."));
    }
}