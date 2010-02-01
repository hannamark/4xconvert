package gov.nih.nci.pa.pdq;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class DownloadTerminology {
    private static final Logger LOG = Logger.getLogger(DownloadTerminology.class);
    
    private static String filename = "Terminology.tar.gz";
    
    public static void process() throws IOException {
        
        preCheck();
        LOG.info("Compled Pre Check ...........");
        cleanUp();
        LOG.info("Compled Clean up ...........");
        download();
        LOG.info("Compled Download ...........");
        extract();
        LOG.info("Compled Extraction ...........");
    }
    
    
    private static void download() throws IOException {
      FTPClient client = new FTPClient();
      try {
          client.connect("cipsftp.nci.nih.gov");
          boolean login = client.login("scenpro", "UO8cbV^");
          if (!login) {
              System.out.println("Unable to login to PDQ");
              System.exit(0);
          }
          FileOutputStream fos = null;
          fos = new FileOutputStream(filename);
          client.retrieveFile("/" + filename, fos);
          fos.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
        
    }
    private static void extract() throws IOException {
        InputStream is = getInputStream(filename);
        TarArchiveInputStream tais = new TarArchiveInputStream(is);
        TarArchiveEntry tea = null ;
        int i = 0 ;
        while ((tea = tais.getNextTarEntry()) != null) {
            if (tea.getName().endsWith(".xml")) {
                i++;
                int b = tais.available(); 
                byte[] data = new byte[b];
                tais.read(data);
                FileOutputStream fos = new FileOutputStream(tea.getName());
                fos.write(data);
                fos.close();
            }
                
            
        }
        
     
    }

    private static void preCheck() {
        File disease = new File("disease.sql");
        File intervention = new File("intervention.sql");
        if (disease.exists()) {
            System.out.println(" file exist, move disease.sql to scripts before continuing");
            System.exit(0);
        }
        if (intervention.exists()) {
            System.out.println(" file exist, move intervention.sql to scripts before continuing");
            System.exit(0);
        }
        
    }
    private static void cleanUp() {
        File ter = new File(filename);
        ter.delete();
        File f = new File("./Terminology");
        File[] files = f.listFiles();
        System.out.println(files.length);
        for (int i = 0 ; i < files.length ; i++) {
            File file = files[i];
            if (file.getName().endsWith(".xml")) {
                file.delete();
            }
            
        }
        
    }
    private static InputStream getInputStream(String tarFileName) throws IOException{
        
           return new GZIPInputStream(new FileInputStream(new File(tarFileName)));
    }
    
}
