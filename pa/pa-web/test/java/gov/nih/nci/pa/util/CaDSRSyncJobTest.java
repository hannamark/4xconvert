package gov.nih.nci.pa.util;

import static org.mockito.Mockito.mock;
import gov.nih.nci.pa.service.PAException;

import org.junit.Before;
import org.junit.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class CaDSRSyncJobTest {
    private CaDSRSyncJob job;
    private JobExecutionContext context;
    private CaDSRSyncHelper helper;
    @Before
    public void setup() {
        job = new CaDSRSyncJob();
        helper = mock(CaDSRSyncHelper.class);
    }
    @Test
    public void executetest() throws JobExecutionException, PAException {
        job.execute(context);
    }
}
