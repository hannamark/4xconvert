/**
 * 
 */
package gov.nih.nci.pa.util;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author dkrylov
 * 
 */
@SuppressWarnings("deprecation")
public class QuartzInitializerServlet extends
        org.quartz.ee.servlet.QuartzInitializerServlet {

    private static final Logger LOG = Logger
            .getLogger(QuartzInitializerServlet.class);

    private final Timer timer = new Timer(true);

    /**
     * 
     */
    private static final long serialVersionUID = 8969010341751838861L;

    @Override
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        final StdSchedulerFactory factory = (StdSchedulerFactory) sc
                .getServletContext().getAttribute(QUARTZ_FACTORY_KEY);
        timer.schedule(new TimerTask() {
            private String lastValue;

            @Override
            public void run() {
                try {
                    String cron = PaRegistry.getLookUpTableService()
                            .getPropertyValue("ctgov.ftp.schedule");
                    if (!StringUtils.equals(lastValue, cron)) {
                        LOG.warn("Setting 'ctgovUploadJob' schedule to " + cron
                                + "; previous schedule was " + lastValue);
                        Scheduler scheduler = factory.getScheduler();
                        CronTrigger trigger = new CronTrigger(
                                "ctgovUploadJobTrigger", "DailyTriggerGroup",
                                "ctgovUploadJob", "DEFAULT", cron);
                        trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
                        scheduler.rescheduleJob("ctgovUploadJobTrigger",
                                "DailyTriggerGroup", trigger);
                        LOG.warn("Next fire time: " + trigger.getNextFireTime());
                        lastValue = cron;
                    }
                } catch (Exception e) {
                    LOG.error(e, e);
                }
            }
        }, 0, DateUtils.MILLIS_PER_MINUTE);

    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            timer.cancel();
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

}
