package gov.nih.nci.pa.pdq;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class DownloadTerminology {
    private static final Logger LOG = Logger.getLogger(DownloadTerminology.class);

    private static String filename = "Terminology.tar.gz";
    private static final int BUFFER = 2048;

    public void process() throws IOException {
        preCheck();
        LOG.info("Completed Pre Check ...........");
        cleanUp();
        LOG.info("Completed Clean up ...........");
        download();
        LOG.info("Completed Download ...........");
        extract();
        LOG.info("Completed Extraction ...........");
    }


    private void download() throws IOException {
      final FTPClient client = new FTPClient();
      try {
          client.connect("cipsftp.nci.nih.gov");
          if (!client.login("scenpro", "UO8cbV^")) {
              LOG.error("Unable to login to PDQ");
              throw new IOException("Unable to login to FTP Server.");
          }
          client.setFileType(FTP.BINARY_FILE_TYPE);
          FileOutputStream fos = null;
          fos = new FileOutputStream(filename);
          client.retrieveFile("/" + filename, fos);
          fos.close();
      } catch (IOException e) {
          LOG.error(e);
      }

    }
    private void extract() throws IOException {
        final TarArchiveInputStream tais = new TarArchiveInputStream(getInputStream(filename));
        TarArchiveEntry tea = tais.getNextTarEntry() ;
        BufferedOutputStream dest = null;
        FileOutputStream fos = null;
        byte data[] = null;
        while (tea != null) {
            if (tea.getName().endsWith(".xml")) {
                data = new byte[BUFFER];
                fos = new FileOutputStream(tea.getName());
                dest = new BufferedOutputStream(fos, BUFFER);
                int count = tais.read(data, 0, BUFFER);
                while (count != -1) {
                   dest.write(data, 0, count);
                   count = tais.read(data, 0, BUFFER);
                }
                dest.flush();
                dest.close();
            }
            tea = tais.getNextTarEntry();
        }
    }

    private void preCheck() {
        new File("disease.sql").delete();
        new File("intervention.sql").delete();
    }

    private void cleanUp() {
        new File(filename).delete();
        final File[] files = new File("./Terminology").listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".xml")) {
                file.delete();
            }

        }

    }

    private InputStream getInputStream(final String tarFileName) throws IOException {
        return new GZIPInputStream(new FileInputStream(new File(tarFileName)));
    }

}
