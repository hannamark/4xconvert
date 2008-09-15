package gov.nih.nci.pa.util;

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
public class PaPropertyReader {
    private static final Logger LOG = Logger.getLogger(PaPropertyReader.class);
    private static final String RESOURCE_NAME = "pa.properties";
    private static Properties props = null;
    private static String lookUpServer = "ws.naming.server";
    private static String lookUpPort = "ws.naming.port";
    static {
        try {
            props = new Properties();
            props.load(PaPropertyReader.class.getClassLoader().getResourceAsStream(RESOURCE_NAME));
        } catch (Exception e) {
            LOG.error("Unable to read pa.properties ", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * 
     * @return String for the server name
     */
    public static String getLookUpServerInfo() {
        String server = props.getProperty(lookUpServer);
        String port = props.getProperty(lookUpPort);
        return server + ":" + port;
    }
}
