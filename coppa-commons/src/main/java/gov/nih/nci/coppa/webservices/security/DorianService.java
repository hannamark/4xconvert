package gov.nih.nci.coppa.webservices.security;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.cagrid.gaards.authentication.BasicAuthentication;
import org.cagrid.gaards.authentication.client.AuthenticationClient;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.Principal;
import java.util.Properties;

/**
 * This utility class provides a simple wrapper around utilizing Dorian
 * for authentication.
 *
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidThrowingRawExceptionTypes" })
public class DorianService {
    private static final Logger LOG = Logger.getLogger(DorianService.class);

    private static final int CERT_LIFETIME = 12;
    private static final String CTRP_CI = "ctrp.env.ci";

    private static final DorianService INSTANCE = new DorianService();

    private String targetGrid;
    private String authorizationUrl;
    private String dorianUrl;

    /**
     *
     * @return An instance.
     */
    public static final DorianService getInstance() {
        return INSTANCE;
    }

    /**
     * Authenticates against Dorian for the given username and password, and create a Principal
     * for the given JAAS Realm.
     * @param username The username.
     * @param password The password.
     * @param realm The JAAS Realm.
     * @return The Principal object resulting from the call to Dorian, or null if
     * authentication failed.
     * @throws IOException
     */
    public Principal authenticate(String username, String password, Realm realm) {
        initialize();


        Principal result = null;

        if (StringUtils.isBlank(dorianUrl)
                && Boolean.valueOf(System.getProperty(CTRP_CI))) {
            LOG.warn("Authentication service URL is blank; however "
                    + CTRP_CI
                    + " runtime property is set to true: we are running in a CI environment. "
                    + "Skipping grid authentication and going directly to CSM.");
            result = new GenericPrincipal(realm, username, password);
        } else {
            GlobusCredential gc = authenticate(dorianUrl, authorizationUrl, username, password);
            if (gc != null) {
                result = new GenericPrincipal(realm, gc.getIdentity(), password);
            }
        }


        return result;
    }



    private GlobusCredential authenticate(String dorianURL,
                                          String authenticationServiceURL, String userId, String password) {

        try {
            // Create credential
            BasicAuthentication auth = new BasicAuthentication();
            auth.setUserId(userId);
            auth.setPassword(password);

            // Authenticate to the IdP (DorianIdP) using credential
            AuthenticationClient authClient = new AuthenticationClient(
                    authenticationServiceURL);
            SAMLAssertion saml = authClient.authenticate(auth);

            // Requested Grid Credential lifetime (12 hours)
            CertificateLifetime lifetime = new CertificateLifetime();
            lifetime.setHours(CERT_LIFETIME);

            // Request PKI/Grid Credential
            GridUserClient dorian = new GridUserClient(dorianURL);
            GlobusCredential credential = dorian.requestUserCertificate(saml,
                    lifetime);
            return credential;
        } catch (Exception e) {
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            return null;
        }
    }



    private synchronized void initialize() {
        initTargetGrid();
        initUrls();

        Validate.notEmpty(targetGrid, "No target grid specified");
        Validate.notEmpty(authorizationUrl, "No authorization url specified");
        Validate.notEmpty(dorianUrl, "No Dorian url specified");
    }



    private synchronized void initTargetGrid() {
        if (this.targetGrid == null) {
            Properties p = new Properties();
            try {
                p.load(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("/gridauth.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Can not read resource /gridauth.properties", e);
            }

            this.targetGrid = StringUtils.trimToEmpty(p.getProperty("grid.target"));


        }
    }


    private synchronized void initUrls() {
        if (this.authorizationUrl == null || this.dorianUrl == null) {

            //init dorian and auth urls
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(this.getClass().getClassLoader()
                        .getResourceAsStream("/gridauth-config.xml"));

                NodeList gridList = doc.getElementsByTagName("grid");

                Element authServiceInfo = null;

                for (int i = 0; i < gridList.getLength(); i++) {
                    Element grid = (Element) gridList.item(i);
                    String currentGrid = grid.getAttributes().getNamedItem("name").getNodeValue();
                    if (this.targetGrid.equalsIgnoreCase(currentGrid)) {
                        authServiceInfo
                                = (Element) grid.getElementsByTagName("authentication-service-information").item(0);
                        break;
                    }
                }

                Validate.notNull(authServiceInfo);

                initAuthServiceUrl(authServiceInfo);
                initDorianUrl(authServiceInfo);

            } catch (ParserConfigurationException e) {
                throw new RuntimeException("Can not read resource /gridauth-config.xml", e);
            } catch (SAXException e) {
                throw new RuntimeException("Can not read resource /gridauth-config.xml", e);
            } catch (IOException e) {
                throw new RuntimeException("Can not read resource /gridauth-config.xml", e);
            }
        }
    }


    private synchronized void initDorianUrl(Element authServiceInfo) {
        Element dorianInfo = (Element) authServiceInfo
                .getElementsByTagName("dorian-information")
                .item(0);

        this.dorianUrl = dorianInfo
                .getElementsByTagName("service-url")
                .item(0)
                .getFirstChild().getNodeValue().trim();
    }

    private synchronized void initAuthServiceUrl(Element authServiceInfo) {
        this.authorizationUrl = authServiceInfo
                .getElementsByTagName("service-url")
                .item(0)
                .getFirstChild().getNodeValue().trim();
    }



}
