package gov.nih.nci.coppa.web;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cagrid.syncgts.bean.SyncDescription;
import gov.nih.nci.cagrid.syncgts.core.SyncGTS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

/**
 * Handles kicking off the Sync GTS thread in order to synch with the target
 * grid trust fabric periodically.
 * 
 * @author Denis G. Krylov
 */
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public class SyncGTSListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(SyncGTSListener.class);
    private final Properties properties = new Properties();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    public void contextInitialized(ServletContextEvent context) {
        try {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("/gridauth.properties"));

            LOG.info("Running SyncGTS once now...");
            syncWithTargetGrid(getTargetGridName());
            LOG.info("SyncGTS looking good.");

        } catch (Exception e) {
            LOG.error("Sync GTS periodic sync may NOT be available!");
            LOG.error(e, e);
        }

    }

    private void syncWithTargetGrid(String gridName) throws Exception { // NOPMD

        LOG.info("Target grid: " + gridName);

        File certsDir = new File(SystemUtils.USER_HOME, ".globus/certificates");
        certsDir.mkdirs(); 
        LOG.info("Wiping out content of " + certsDir.getCanonicalPath());
        FileUtils.cleanDirectory(certsDir);
        LOG.info("Attempting to install root CA certificates into "
                + certsDir.getCanonicalPath());
        InputStream certsZipIs = null;
        try {
            certsZipIs = SyncGTSListener.class.getResourceAsStream("/"
                    + gridName + "/certificates/certs.zip");
            if (certsZipIs == null) {
                throw new RuntimeException(
                        "Cannot find ROOT CA certificates for this grid; aborting...");
            }
            unzip(certsZipIs, certsDir);
        } finally {
            IOUtils.closeQuietly(certsZipIs);
        }

        LOG.info("Attempting to synchronize with " + gridName
                + " grid trust fabric...");
        InputStream syncDescStream = null;
        try {
            syncDescStream = SyncGTSListener.class.getResourceAsStream("/"
                    + gridName + "/sync-description.xml");
            if (syncDescStream == null) {
                LOG.error("Cannot find sync-description.xml for this grid; aborting...");
                throw new RuntimeException(
                        "Cannot find sync-description.xml for this grid; aborting...");
            }
            SyncDescription description = (SyncDescription) Utils
                    .deserializeObject(new InputStreamReader(syncDescStream),
                            SyncDescription.class);
            // Sync with the Trust Fabric Once
            SyncGTS.getInstance().syncOnce(description);
            SyncGTS.getInstance().syncAndResyncInBackground(description, true);
        } finally {
            IOUtils.closeQuietly(syncDescStream);
        }
    }

    /**
     * Unzips a zip compressed file to a specific directory.
     * 
     * @param zip
     *            The zip compressed file
     * @param location
     *            The location to unzip into
     * @throws IOException
     */
    private void unzip(InputStream zipFileInput, File location)
            throws IOException {
        ZipInputStream zipInput = new ZipInputStream(zipFileInput);
        ZipEntry entry = null;
        String baseDir = location.getAbsolutePath();
        while ((entry = zipInput.getNextEntry()) != null) {
            String name = entry.getName();
            File outFile = new File(baseDir + File.separator + name);
            if (entry.isDirectory()) {
                outFile.mkdirs();
            } else {
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }
                outFile.createNewFile();
                BufferedOutputStream fileOut = new BufferedOutputStream(
                        new FileOutputStream(outFile));
                IOUtils.copy(zipInput, fileOut);
                fileOut.flush();
                fileOut.close();
            }
        }
        zipInput.close();
    }

    /**
     * @return
     */
    private String getTargetGridName() {
        return properties.getProperty("grid.target");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // NOOP
    }

}
