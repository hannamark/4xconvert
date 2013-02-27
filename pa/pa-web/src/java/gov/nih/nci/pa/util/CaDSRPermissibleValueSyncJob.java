package gov.nih.nci.pa.util;


import org.quartz.Job;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRPermissibleValueSyncJob implements Job {
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(CaDSRPermissibleValueSyncJob.class);
    
    /** execute.
     * @param context JobExecutionContext
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("executing permissible value job");
        try {
            CaDSRPVSyncJobHelper helper = new CaDSRPVSyncJobHelper();
            helper.updatePlannedMarkerSyncTable();
            LOG.info("Success permissible value job...........");
        } catch (Exception e) {
            LOG.error("error", e);
//            try {
//                PaRegistry.getMailManagerService().sendErrorToCTROMail
//                ("reshma.koganti@gmail.com", "reshma.koganti@semanticbits.com", e.getMessage());
//            } catch (PAException e1) { 
//                LOG.error("error  sending mail", e1);
//            }
        }
    }
}
