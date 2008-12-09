package gov.nih.nci.pa.util;




import gov.nih.nci.pa.service.PAException;

import java.io.File;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Property File reader which can be extended for passing in the run time
 * properties for PA.
 * 
 * @author Harsha
 * @since 09/14/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD")
public class PaEarPropertyReader {
    private static final Logger LOG = Logger.getLogger(PaEarPropertyReader.class);
    private static final String RESOURCE_NAME = "paear.properties";
    private static Properties props = null;
    private static String docUploadPath = "doc.upload.path";
    private static String lookUpServer = "po.server.name";
    private static String lookUpPort = "po.port.number";
    private static String csmSubmitterGroup = "csm.submitter.group";
    private static String allowedUploadFileTypes = "allowed.uploadfile.types";
 
    
    static {
        try {
            props = new Properties();
            props.load(PaEarPropertyReader.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        } catch (Exception e) {
            LOG.error("Unable to read paear.properties ", e);
            throw new IllegalStateException(e);
        }
    }


    /**
      * 
      * @return folder path
      * @throws PAException e
      */
    public static String getDocUploadPath() throws PAException {
        String folderPath = props.getProperty(docUploadPath);
        if (folderPath == null) {
            LOG.error(" doc.upload.path does not have value in paear.properties ");
            throw new PAException("doc.upload.path does not have value in paear.properties");
        }
        File f = new File(folderPath);
        if (!f.isDirectory()) {
            LOG.error(folderPath + " is not a valid directory ");
            throw new PAException(folderPath + " is not a valid directory ");
        }    
        return folderPath;
    }
    
    /**
      *
      * @return String for the server name
      * @throws PAException on error
      */
   public static String getLookUpServerInfo() throws PAException {
       String server = props.getProperty(lookUpServer);
       if (server == null) {
           LOG.error("'server' does not have value in paear.properties ");
           throw new PAException("'server' does not have value in paear.properties");
       }
       String port = props.getProperty(lookUpPort);
       if (port == null) {
           LOG.error("'port' does not have value in paear.properties ");
           throw new PAException("'port' does not have value in paear.properties");
       }
       return server + ":" + port;
   }
   
   /**
   *
   * @return String for the CSM Trial Submitter Group
   * @throws PAException on error
   */
    public static String getCSMSubmitterGroup() throws PAException {
        String submitterGroupName = props.getProperty(csmSubmitterGroup);
        if (submitterGroupName == null) {
            LOG.error("'submitterGroupName' does not have value in paear.properties ");
            throw new PAException("'submitterGroupName' does not have value in paear.properties");
        }
        return submitterGroupName;
    }
    
    /**
    *
    * @return a comma separated String of allowed upload file types
    */
     public static String getAllowedUploadFileTypes() {
         String allowedFileTypes = props.getProperty(allowedUploadFileTypes);
         if (allowedFileTypes == null) {
             LOG.error("'allowedUploadFileTypes' does not have value in paear.properties ");
         }
         return allowedFileTypes;
     }

   
   /**
    * @return the properties
    */
   public static Properties getProperties() {
       return props;
   }
   
}
