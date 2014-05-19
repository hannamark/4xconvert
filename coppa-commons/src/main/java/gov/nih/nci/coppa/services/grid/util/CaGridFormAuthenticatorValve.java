/**
 * 
 */
package gov.nih.nci.coppa.services.grid.util;

import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.catalina.Realm;
import org.apache.catalina.Session;
import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.authenticator.SavedRequest;
import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.cagrid.gaards.authentication.BasicAuthentication;
import org.cagrid.gaards.authentication.client.AuthenticationClient;
import org.cagrid.gaards.dorian.client.GridUserClient;
import org.cagrid.gaards.dorian.federation.CertificateLifetime;
import org.globus.gsi.GlobusCredential;
import org.jboss.as.web.security.ExtendedFormAuthenticator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An <b>Authenticator</b> and <b>Valve</b> implementation of CAGRID FORM BASED
 * Authentication. Based on former CSM CGMM implementation.
 * 
 * @author Denis G. Krylov
 * 
 */
public class CaGridFormAuthenticatorValve extends ExtendedFormAuthenticator { // NOPMD
    private static final int CERT_LIFETIME = 12;

    private static final String AUTHENTICATION_SOURCE_MAP = "AUTHENTICATION_SOURCE_MAP";

    private static final Logger LOG = Logger
            .getLogger(CaGridFormAuthenticatorValve.class);

    private static final String CTRP_CI = "ctrp.env.ci";

    private Properties properties;
    private Document gridConfig;
    private static Map<String, String> authSourceMap;
    
    // CHECKSTYLE:OFF
    private static CaGridFormAuthenticatorValve INSTANCE; // NOPMD
    
    /**
     * 
     */
    public CaGridFormAuthenticatorValve() {
        INSTANCE = this;
    }

    /**
     * Authenticate the user making this request, based on the specified login
     * configuration. Return <code>true</code> if any specified constraint has
     * been satisfied, or <code>false</code> if we have created a response
     * challenge already.
     * 
     * @param request
     *            Request we are processing
     * @param response
     *            Response we are creating
     * @param config
     *            Login configuration describing how authentication should be
     *            performed
     * 
     * @exception IOException
     *                if an input/output error occurs
     * @return boolean
     */
    // CHECKSTYLE:OFF
    public boolean authenticate(Request request, HttpServletResponse response, // NOPMD
            LoginConfig config) throws IOException {

        // Have we already authenticated someone?
        Principal principal = request.getUserPrincipal();
        String ssoId = (String) request.getNote(Constants.REQ_SSOID_NOTE);
        if (principal != null) {           
            // Associate the session with any existing SSO session
            if (ssoId != null) // NOPMD
                associate(ssoId, request.getSessionInternal(true));
            return (true);
        }
        
        // Is there an SSO session against which we can try to reauthenticate?
        if (ssoId != null) {           
            // Try to reauthenticate using data cached by SSO.  If this fails,
            // either the original SSO logon was of DIGEST or SSL (which
            // we can't reauthenticate ourselves because there is no
            // cached username and password), or the realm denied
            // the user's reauthentication for some reason.
            // In either case we have to prompt the user for a logon */
            if (reauthenticateFromSSO(ssoId, request)) // NOPMD
                return true;
        }

        Session session = request.getSessionInternal(true);

        // Have we authenticated this user before but have caching disabled?
        if (!cache) {
            String username = (String) session
                    .getNote(Constants.SESS_USERNAME_NOTE);
            String password = (String) session
                    .getNote(Constants.SESS_PASSWORD_NOTE);
            if ((username != null) && (password != null)) {
                principal = context.getRealm().authenticate(username, password);
                if (principal != null) { // NOPMD
                    session.setNote(Constants.FORM_PRINCIPAL_NOTE, principal);
                    if (!matchRequest(request)) {
                        register(request, response, principal,
                                HttpServletRequest.FORM_AUTH, username,
                                password);
                        return (true);
                    }
                }
            }
        }

        // Is this the re-submit of the original request URI after successful
        // authentication? If so, forward the *original* request instead.
        if (matchRequest(request)) {
            session = request.getSessionInternal(true);
            principal = (Principal) session
                    .getNote(Constants.FORM_PRINCIPAL_NOTE);
            register(request, response, principal,
                    HttpServletRequest.FORM_AUTH,
                    (String) session.getNote(Constants.SESS_USERNAME_NOTE),
                    (String) session.getNote(Constants.SESS_PASSWORD_NOTE));
            // If we're caching principals we no longer need the username
            // and password in the session, so remove them
            if (cache) {
                session.removeNote(Constants.SESS_USERNAME_NOTE);
                session.removeNote(Constants.SESS_PASSWORD_NOTE);
            }
            if (restoreRequest(request, session)) {
                return (true);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return (false);
            }
        }

        loadGridAuthConfig();
        loadGridAuthProperties();
        buildAuthSourceMap();
        request.setAttribute(AUTHENTICATION_SOURCE_MAP,
                Collections.unmodifiableMap(this.authSourceMap));

        // Acquire references to objects we will need to evaluate
        MessageBytes uriMB = MessageBytes.newInstance();
        CharChunk uriCC = uriMB.getCharChunk();
        uriCC.setLimit(-1);
        String contextPath = request.getContextPath();
        String requestURI = request.getDecodedRequestURI();

        // Is this the action request from the login page?
        boolean loginAction = requestURI.startsWith(contextPath)
                && requestURI.endsWith(Constants.FORM_ACTION);

        // No -- Save this request and redirect to the form login page
        if (!loginAction) {
            session = request.getSessionInternal(true);
            try {
                saveRequest(request, session);
            } catch (IOException ioe) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        sm.getString("authenticator.requestBodyTooBig"));
                return (false);
            }
            forwardToLoginPage(request, response, config);
            return (false);
        }

        // Yes -- Acknowledge the request, validate the specified credentials
        // and redirect to the error page if they are not correct
        request.getResponse().sendAcknowledgement();
        Realm realm = context.getRealm();
        if (characterEncoding != null) {
            request.setCharacterEncoding(characterEncoding);
        }
        String username = request.getParameter(Constants.FORM_USERNAME);
        String password = request.getParameter(Constants.FORM_PASSWORD);
        String authenticationServiceURL = request
                .getParameter("authenticationServiceURL");

        // Check if there is sso id as well as sessionkey
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            forwardToLoginPage(request, response, config);
            return (false);
        }

        principal = performGridAuthentication(username, password,
                authenticationServiceURL);

        if (principal == null) {
            forwardToErrorPage(request, response, config);
            return false;
        }
        
        username = principal.getName();
        principal = realm.authenticate(username, password); // this will go through JAAS LoginModules
        if (principal == null) {
            forwardToErrorPage(request, response, config);
            return false;
        }

        if (session == null) // NOPMD
            session = request.getSessionInternal(false);
        if (session == null) {           
            if (landingPage == null) {
                response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT,
                        sm.getString("authenticator.sessionExpired"));
            } else {
                // Make the authenticator think the user originally requested
                // the landing page
                String uri = request.getContextPath() + landingPage;
                SavedRequest saved = new SavedRequest();
                saved.setRequestURI(uri);
                request.getSessionInternal(true).setNote(
                        Constants.FORM_REQUEST_NOTE, saved);
                response.sendRedirect(response.encodeRedirectURL(uri));
            }
            return (false);
        }

        // Save the authenticated Principal in our session
        session.setNote(Constants.FORM_PRINCIPAL_NOTE, principal);

        // Save the username and password as well
        session.setNote(Constants.SESS_USERNAME_NOTE, username);
        session.setNote(Constants.SESS_PASSWORD_NOTE, password);

        // Redirect the user to the original request URI (which will cause
        // the original request to be restored)
        requestURI = savedRequestURL(session);
        if (requestURI == null)
            if (landingPage == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        sm.getString("authenticator.formlogin"));
            } else {
                // Make the authenticator think the user originally requested
                // the landing page
                String uri = request.getContextPath() + landingPage;
                SavedRequest saved = new SavedRequest();
                saved.setRequestURI(uri);
                session.setNote(Constants.FORM_REQUEST_NOTE, saved);
                response.sendRedirect(response.encodeRedirectURL(uri));
            }
        else {
            response.sendRedirect(response.encodeRedirectURL(requestURI));
        }
        return (false);
    }

    private Principal performGridAuthentication(String username,
            String password, String authServiceURL) {
        if (StringUtils.isBlank(authServiceURL)
                && Boolean.valueOf(System.getProperty(CTRP_CI))) {
            LOG.warn("Authentication service URL passed via the login form is blank; however "
                    + CTRP_CI
                    + " runtime property is set to true: we are running in a CI environment. "
                    + "Skipping grid authentication and going directly to CSM.");
            return new GenericPrincipal(context.getRealm(), username, password);
        }
        checkAuthServiceURLValidity(authServiceURL);
        GlobusCredential gc = authenticate(authServiceURL, username, password);
        return gc != null ? new GenericPrincipal(context.getRealm(),
                gc.getIdentity(), password) : null;

    }

    private GlobusCredential authenticate(String authServiceURL,
            String username, String password) {
        String dorianURL = findDorian(authServiceURL);
        return authenticate(dorianURL, authServiceURL, username, password);
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

    private String findDorian(String authServiceURL) {

        String targetGrid = getTargetGridName();
        NodeList authServices = getAuthServiceInfoNodeList(targetGrid);

        for (int j = 0; j < authServices.getLength(); j++) {
            Element authServiceInfo = (Element) authServices.item(j);
            String serviceURL = authServiceInfo
                    .getElementsByTagName("service-url").item(0)
                    .getFirstChild().getNodeValue().trim();
            if (serviceURL.equals(authServiceURL)) {
                return ((Element) authServiceInfo.getElementsByTagName(
                        "dorian-information").item(0))
                        .getElementsByTagName("service-url").item(0)
                        .getFirstChild().getNodeValue().trim();
            }
        }
        throw new IllegalArgumentException(
                "Unable to match Authentication Service URL with a Dorian: "
                        + authServiceURL);

    }
    
    /**
     * @param authServiceURL
     * @return
     */
    public static final String findDorianByAuthenticationServiceURL(
            String authServiceURL) {
        return INSTANCE != null ? INSTANCE.findDorian(authServiceURL) : "";
    }

    /**
     * @return
     */
    private String getTargetGridName() {
        return properties.getProperty("grid.target");
    }

    /**
     * This method basically checks to see whether the user has manipulated
     * authentication service URL in the browser.
     * 
     * @param authServiceURL
     */
    private void checkAuthServiceURLValidity(String authServiceURL) {
        for (String url : authSourceMap.values()) {
            if (url.equals(authServiceURL)) {
                return;
            }
        }
        throw new IllegalArgumentException(
                "Authentication service is not recognized in the current grid environment: "
                        + authServiceURL);
    }

    /**
     * @throws IOException
     */
    private synchronized void buildAuthSourceMap() { // NOPMD
        if (authSourceMap != null) {
            return;
        }

        String targetGrid = getTargetGridName();
        Map<String, String> map = new LinkedHashMap<String, String>();

        NodeList authServices = getAuthServiceInfoNodeList(targetGrid);

        if (authServices != null) {
            for (int j = 0; j < authServices.getLength(); j++) {
                Element authServiceInfo = (Element) authServices.item(j);
                String serviceName = authServiceInfo
                        .getElementsByTagName("service-name").item(0)
                        .getFirstChild().getNodeValue().trim();
                String serviceURL = authServiceInfo
                        .getElementsByTagName("service-url").item(0)
                        .getFirstChild().getNodeValue().trim();
                map.put(serviceName, serviceURL);
            }

        }
        if (map.isEmpty()) {
            throw new IllegalArgumentException(
                    "Unable to match the target grid "
                            + targetGrid
                            + "specified in "
                            + "gridauth.properties with any known grid configuration");
        }
        authSourceMap = map;

    }

    /**
     * @param targetGrid
     * @param authServices
     * @return
     */
    private NodeList getAuthServiceInfoNodeList(String targetGrid) {
        if (StringUtils.isBlank(targetGrid)) {
            throw new IllegalArgumentException(
                    "Target grid is not specified in gridauth.properties on classpath");
        }

        NodeList gridList = gridConfig.getElementsByTagName("grid");
        for (int i = 0; i < gridList.getLength(); i++) {
            Element grid = (Element) gridList.item(i);
            if (targetGrid.trim().equalsIgnoreCase(
                    grid.getAttributes().getNamedItem("name").getNodeValue())) {
                return grid
                        .getElementsByTagName("authentication-service-information");

            }
        }
        return null;
    }

    private synchronized void loadGridAuthConfig() throws IOException {
        if (this.gridConfig == null) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(this.getClass().getClassLoader()
                        .getResourceAsStream("/gridauth-config.xml"));
                this.gridConfig = doc;
            } catch (ParserConfigurationException e) {
                LOG.error(e);
                throw new IOException(e);
            } catch (SAXException e) {
                LOG.error(e);
                throw new IOException(e);
            }
        }

    }

    /**
     * @throws IOException
     */
    private synchronized void loadGridAuthProperties() throws IOException {
        if (this.properties == null) {
            Properties p = new Properties();
            p.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("/gridauth.properties"));
            this.properties = p;
        }
    }

    /**
     * @return the authSourceMap
     */
    public static Map<String, String> getAuthSourceMap() {
        return authSourceMap;
    }

}
