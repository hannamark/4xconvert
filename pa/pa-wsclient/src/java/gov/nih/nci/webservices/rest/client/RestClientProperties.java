package gov.nih.nci.webservices.rest.client;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * REST client properties placeholder. Populated from pa-wsclient.properties.
 * 
 * @author vinodh.rc
 * 
 */
public class RestClientProperties {

    @Value("${po.ws.rest.protocol}")
    private String protocol;

    @Value("${po.ws.rest.hostname}")
    private String host;

    @Value("${po.ws.rest.port}")
    private int port;

    @Value("${po.ws.rest.apipath}")
    private String apiPath;

    @Value("${po.ws.rest.username}")
    private String userName;

    @Value("${po.ws.rest.password}")
    private String password;

    private String baseUrl;

    @Value("${po.ws.rest.familyservice}")
    private String familyServiceUrl;

    @Value("${po.ws.rest.familyorganizationservice}")
    private String familyOrgServiceUrl;

    /**
     * 
     * @return Protocol String
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * 
     * @return Host String
     */
    public String getHost() {
        return host;
    }

    /**
     * 
     * @return Port value int
     */
    public int getPort() {
        return port;
    }

    /**
     * 
     * @return Rest services context part
     */
    public String getApiPath() {
        return apiPath;
    }

    /**
     * 
     * @return REST Services auth username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @return REST Services auth password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return Base url to contact rest services
     */
    public String getBaseUrl() {
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = getProtocol() + "://" + getHost() + ":" + getPort()
                    + getApiPath();
        }
        return baseUrl;
    }

    /**
     * 
     * @return Family rest service url
     */
    public String getFamilyServiceUrl() {
        return getBaseUrl() + familyServiceUrl;
    }

    /**
     * 
     * @return Family Organization Relationship service url
     */
    public String getFamilyOrganizationRelationshipServiceUrl() {
        return getBaseUrl() + familyOrgServiceUrl;
    }

}
