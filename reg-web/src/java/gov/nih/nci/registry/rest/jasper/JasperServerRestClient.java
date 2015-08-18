/**
 * 
 */
package gov.nih.nci.registry.rest.jasper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.rest.jasper.Users.User;
import gov.nih.nci.registry.rest.jasper.Users.User.Roles;

/**
 * @author vpoluri
 *
 */
public class JasperServerRestClient {

    private static final Logger LOG = Logger.getLogger(JasperServerRestClient.class);
    
   
    private String baseUrl = "http://localhost:8080/jasperserver/rest/user";
    private String username = "jasperadmin";
    private String password = "jasperadmin";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private LookUpTableServiceRemote lookupTableService;
    private ObjectFactory objFact;
    private Marshaller userMarshaller;
    private Unmarshaller usersUnmarshaller;
    private int httpTimeout;
    private static final Integer HTTP_SUCCESS_CODE = 200;
    /**
     * 
     * @param baseURL - Jasper server base URL
     * @param jasperAdminUser - Jasper admin username
     * @param jasperAdminPwd - Jasper admin password
     */
    
    public JasperServerRestClient(String baseURL, String jasperAdminUser, String jasperAdminPwd) {
    try {
        
        baseUrl = baseURL;
        username = jasperAdminUser;
        password = jasperAdminPwd;
        lookupTableService = PaRegistry.getLookUpTableService();
        JAXBContext usersJxContext = JAXBContext.newInstance(Users.class);
        usersUnmarshaller = usersJxContext.createUnmarshaller();

        JAXBContext userJxContext = JAXBContext.newInstance(User.class);
        userMarshaller = userJxContext.createMarshaller();
        userMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        userMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        objFact = new ObjectFactory();
        try {
            httpTimeout = Integer.parseInt(getPropertyValue("jasper.rest.http.timeout.millis"));
        } catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
        } catch (PAException e) {
            LOG.error(e.getMessage(), e);
        }
        
        
    } catch (JAXBException e) {

        LOG.error(e.getMessage(), e);
    }
    }
    
    /**
     * 
     * @param name
     *            get property value
     * @return property value
     * @throws PAException
     *             throws PAException
     */
    private String getPropertyValue(String name) throws PAException {
    String retString = "";

    try {

        LookUpTableServiceRemote lookupBean = getLookUpTableService();
        retString = lookupBean.getPropertyValue(name);

    } catch (Exception ex) {
        LOG.log(Priority.ERROR, ex.getMessage());
        throw new PAException(ex);
    }
    return retString;
    }
    
    /**
     * @return lookup table service
     */
    private LookUpTableServiceRemote getLookUpTableService() {
    return lookupTableService;
    }

    /**
     * 
     * @param users - Users object to convert to XML
     * @return - XML string
     */
    private String marshallXML(Object users) {

    String responseXML = "";
    try {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        userMarshaller.marshal(users, outputStream);
        responseXML = outputStream.toString();

    } catch (JAXBException e) {
        LOG.error(e.getMessage(), e);
    }

    return responseXML;
    }

    /**
     * 
     * @param xml - XML string to marshall to Users obj
     * @return - Users object
     */
    private Users unmarshallXML(String xml) {
    try {

        InputStream xmlStrm = getStringStream(xml);
        JAXBElement<Users> usersJx = usersUnmarshaller.unmarshal(new StreamSource(xmlStrm), Users.class);
        xmlStrm.close();
        Users users = usersJx.getValue();

        return users;

    } catch (Exception e) {
        LOG.error(e.getMessage(), e);
        return null;
    }
    }

    /**
     * Gives details of all users available on Jasper server
     * @return Users object
     */
    public Users getAllUserDetails() {
    Users users = null;
    String xmlResponse = sendHTTPRequest(baseUrl, GET, null);
    if (xmlResponse.length() > HTTP_SUCCESS_CODE.toString().length()) {
        users = unmarshallXML(xmlResponse);
    }
    return users;
    }

    /**
     * Give perticular Jasper user details
     * @param userName - Username
     * @return - Users object
     */
    public Users getUserDetails(String userName) {
    Users users = null;
    String xmlResponse = sendHTTPRequest(baseUrl + "/" + userName, GET, null);
    if (xmlResponse.length() > HTTP_SUCCESS_CODE.toString().length()) {
        users = unmarshallXML(xmlResponse);
    }

    return users;
    }

    /**
     * Removes a user role
     * @param user - User object
     * @param role - Role name in the String format
     * @return - Returns any string returned
     */
    public String removeRole(User user, String role) {

    List<Roles> userRoles = user.getRoles();
    boolean roleFound = false;

    Iterator<Roles> rolesItr = userRoles.iterator();

    while (rolesItr.hasNext()) {
        if (role.equalsIgnoreCase(rolesItr.next().getRoleName())) {
        roleFound = true;
        rolesItr.remove();
        }
    }

    String postBody = marshallXML(user);

    LOG.debug(postBody);
    
    String response = "";
    if (roleFound) {
        response = sendHTTPRequest(baseUrl + "/" + user.getUsername(), POST, postBody);
        LOG.debug("post response: " + response);
    }

    return response;
    }
    
    
    /**
     * Converts give user corresponding roles into list of Roles objects
     * @param reportIds - Comma separated report Ids
     * @param reportGroupMap - report groups map
     * @return - List of Roles objects
     */
    private List<Roles> getRolesFromReportIds(String reportIds, Map<String, String> reportGroupMap) {

    ArrayList<Roles> updateRoles = new ArrayList<Roles>();
    String[] reportIdsArr = reportIds.split("[,]");

        for (String reportId : reportIdsArr) {
        updateRoles.add(getRole(reportGroupMap.get(reportId)));
        }
    
    return updateRoles;
    }
    
    /**
     * Get all Roles from the group configured in the pa_properties
     * @param reportGroupMap - map of user id and roles
     * @return - List of Jasper converted Roles
     */
    private List<Roles> getAllRolesFromGroups(Map<String, String> reportGroupMap) {
    
    List<Roles> updateRoles = new ArrayList<Roles>();
    
    for (String groupName : reportGroupMap.values()) {
        updateRoles.add(getRole(groupName));
        }
    
    return updateRoles;
    }
    
    /**
     * 
     * @param roleName - converts string role into Japser Role
     * @return
     */
    private Roles getRole(String roleName) {
    Roles roles = objFact.createUsersUserRoles();
    roles.setRoleName(roleName);
    roles.setExternallyDefined("false");
    
    return roles;
    }
    
    /**
     * Invokes the REST service to update user specific roles
     * @param user - User object for which role needs to be updated
     * @param reportIds - Comma separated report ids
     * @param reportGroupMap - user-role group from pa_properties
     * @return - Response after update
     */
    public String updateRoles(User user, String reportIds, Map<String, String> reportGroupMap) {

    String response = "";
    List<Roles> userRoles = user.getRoles();

    List<Roles> allRegistryRolesList = getAllRolesFromGroups(reportGroupMap);
    
    // remove
    
    Iterator<Roles> rolesItr = userRoles.iterator();

    while (rolesItr.hasNext()) {
        String roleName = rolesItr.next().getRoleName();
        
        for (Roles roles : allRegistryRolesList) {
        if (roles.getRoleName().equalsIgnoreCase(roleName)) {
            rolesItr.remove();
            break;
            }
        }
    }
    
    if (reportIds != null && reportIds.length() > 0) {
        
        List<Roles> updateRolesList = getRolesFromReportIds(reportIds, reportGroupMap);
        userRoles.addAll(updateRolesList);
        
        LOG.debug("User: " + user.getUsername());
        for (Roles roles : updateRolesList) {
        
        LOG.debug("  Role: " + roles.getRoleName());
        }
    }

    String postBody = marshallXML(user);

    LOG.debug("post body: " + postBody);
    response = sendHTTPRequest(baseUrl + "/" + user.getUsername(), POST, postBody);
    LOG.debug("post response: " + response);

    return response;
    }

    /**
     * adds a role to the User
     * @param user - User
     * @param role - Role 
     * @return - Response
     */
    public String addRole(User user, String role) {

    List<Roles> userRoles = user.getRoles();
    boolean roleFound = false;

    for (Roles roleObj : userRoles) {
        if (role.equalsIgnoreCase(roleObj.getRoleName())) {
        roleFound = true;
        }
    }

    String response = "";

    if (!roleFound) {
        Roles roles = objFact.createUsersUserRoles();

        roles.setExternallyDefined("false");
        roles.setRoleName(role);
        user.getRoles().add(roles);

        String postBody = marshallXML(user);

        response = sendHTTPRequest(baseUrl + "/" + user.getUsername(), POST, postBody);
        LOG.debug("post response: " + response);

    } else {

        LOG.warn("Role already exist for the user");
    }

    return response;
    }

    /**
     * Invokes REST api to update the user on Jasper server
     * @param restUrl - Jasper server URL
     * @param method - HTTP Method
     * @param postBody - Post body 
     * @return - Response after update
     */
    private String sendHTTPRequest(String restUrl, 
                                   String method, 
                                   String postBody) {
    int httpResponseCode = -1;
    LOG.debug("url: " + restUrl);
    StringBuilder httpResponse = new StringBuilder();
    BufferedReader reader = null; 
    HttpURLConnection urlConnection = null;
    try {
        URL url = new URL(restUrl);
        urlConnection = setUsernamePassword(url);
        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

        if (postBody != null && postBody.length() > 0) {
        LOG.debug("postBody: " + postBody);
        setPostBody(urlConnection, postBody);
        }

        httpResponseCode = urlConnection.getResponseCode();

        if (httpResponseCode != HTTP_SUCCESS_CODE) {
        return httpResponse.append(httpResponseCode).toString();
        }

        reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
        httpResponse.append(line);
        }
        

        return httpResponse.toString();
    } catch (IOException e) {
        throw new RuntimeException(e);
    } finally {
        if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        }
        
        if (urlConnection != null) {
        urlConnection.disconnect();
        }
    }

    }

    /**
     * Sets username and password to the http connection
     * @param url - URL object
     * @return - HttpURLConnection
     * @throws IOException - unable to open connection
     */
    private HttpURLConnection setUsernamePassword(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setConnectTimeout(httpTimeout);
    urlConnection.setReadTimeout(httpTimeout);
    String authString = username + ":" + password;
    String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
    urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
    return urlConnection;
    }

    /**
     * Sets post body to the http request
     * @param urlConnection - URL Connection
     * @param postBody - Post body 
     * @throws IOException - throws IOException
     */
    private void setPostBody(HttpURLConnection urlConnection, String postBody) throws IOException {

    OutputStream outputStream = urlConnection.getOutputStream();
    outputStream.write(postBody.getBytes());
    outputStream.flush();
    }

    /**
     * Get string as stream
     * @param str - String object 
     * @return - Input stream for the object
     */
    private InputStream getStringStream(String str) {
    return new ByteArrayInputStream(str.getBytes());
    }

    /**
     * Returns baser url
     * @return - base url
     */
    public String getBaseUrl() {
    return baseUrl;
    }

    /**
     * sets base url
     * @param baseUrl - base url
     */
    public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    }

    /**
     * returns username
     * @return - username
     */ 
    public String getUsername() {
    return username;
    }

    /**
     * sets username
     * @param username - username
     */
    public void setUsername(String username) {
    this.username = username;
    }

    /**
     * returns password
     * @return - pass
     */
    public String getPassword() {
    return password;
    }

    /**
     * sets password
     * @param password - pass
     */
    public void setPassword(String password) {
    this.password = password;
    }

}
