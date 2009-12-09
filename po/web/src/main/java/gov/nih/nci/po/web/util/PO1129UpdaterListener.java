package gov.nih.nci.po.web.util;

import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * This class is executed by LiquiBase to do a one time conversion from the 
 * serialized fundingMechanism of ResearchOrganization to fundingMechanim_id. 
 * See PO-1129 for more. 
 * 
 * @author ludetc
 *
 */
public class PO1129UpdaterListener implements ServletContextListener {
    
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(PO1129UpdaterListener.class);
    
    /**
     * Move all IDs from deprecated column to new column.
     * {@inheritDoc}
     */
    public void contextInitialized(ServletContextEvent arg)  {
        LOG.info("Migrating Funding Mechanism IDs");
        PoHibernateUtil.getHibernateHelper().openAndBindSession();
        PoRegistry.getInstance().getServiceLocator().getResearchOrganizationService().migrateFundingMechanism();
        PoHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
    }

    /**
     * unused.
     * {@inheritDoc}
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        LOG.info("");
    }

    
}
