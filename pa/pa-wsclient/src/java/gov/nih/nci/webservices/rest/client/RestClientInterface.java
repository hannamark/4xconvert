package gov.nih.nci.webservices.rest.client;

/**
 * RestClientInterface
 * 
 * @author vinodh.rc
 * 
 */
public interface RestClientInterface {

    /**
     * 
     * @return RestClient instance
     */
    RestClient getRestClient();

    /**
     * Sets RestClient instance
     * 
     * @param restClient
     *            RestClient instance
     */
    void setRestClient(RestClient restClient);

    /**
     * Return rest client properties placeholder
     * 
     * @return RestClientProperties instance
     */
    RestClientProperties getRestClientProperties();

    /**
     * Sets rest client properties placeholder
     * 
     * @param restClientProperties
     *            RestClientProperties instance
     */
    void setRestClientProperties(RestClientProperties restClientProperties);

}