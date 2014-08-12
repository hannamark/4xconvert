package gov.nih.nci.webservices.rest.client;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Base setup class for all REST clients
 * 
 * @author vinodh
 * 
 */
public class RestClient {

    private static final Logger LOG = Logger.getLogger(RestClient.class);

    /**
     * RestTemplate instance
     */
    private final RestTemplate template;
    /**
     * Properties placeholder instance
     */
    private final RestClientProperties restClientProperties;

    /**
     * HttpClient instance
     */
    private CloseableHttpClient httpClient;
    /**
     * Credentials instance
     */
    private final Credentials credentials;
    /**
     * Authentication scope for the credentials
     */
    private final AuthScope authScope;

    /**
     * Constructor
     * 
     * @param template
     *            - RestTemplate instance
     * @param restClientProperties
     *            - RestClient properties placeholder instance
     * @param credentials
     *            - Credentials instance
     * @param authScope
     *            - Authentication scope for the credentials
     */
    public RestClient(RestTemplate template,
            RestClientProperties restClientProperties, Credentials credentials,
            AuthScope authScope) {
        this.template = template;
        this.restClientProperties = restClientProperties;
        this.credentials = credentials;
        this.authScope = authScope;

        configureRestTemplate();
    }

    /**
     * Configures RestTemplate with the required items
     */
    private void configureRestTemplate() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(authScope, credentials);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        httpClient = httpClientBuilder.build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                httpClient);

        template.setRequestFactory(factory);
        LOG.info("Configured RestTemplate with httpclient factory");
    }

    /**
     * Gets RestTemplate.
     * 
     * @return instance of RestTemplate
     */
    public RestTemplate getRestTemplate() {
        return template;
    }

    /**
     * Gets HttPClient
     * 
     * @return instance of HttpClient
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Gets RestClientProperties
     * 
     * @return property placeholder instance
     */
    public RestClientProperties getRestClientProperties() {
        return restClientProperties;
    }

    /**
     * Returns HTTPHeaders for JSON Mediatype
     * 
     * @param mediaType
     *            MediaType.APPLICATION_JSON
     * @return HTTPHeaders
     */
    public HttpHeaders getAcceptJsonHeaders(MediaType mediaType) {
        return getAcceptHeaders(MediaType.APPLICATION_JSON);
    }

    /**
     * Returns HTTPHeaders for XML Mediatype
     * 
     * @param mediaType
     *            MediaType.APPLICATION_XML
     * @return HTTPHeaders
     */
    public HttpHeaders getAcceptXMLHeaders(MediaType mediaType) {
        return getAcceptHeaders(MediaType.APPLICATION_XML);
    }

    /**
     * Returns HTTPHeaders for Mediatype
     * 
     * @param mediaType
     *            MediaType
     * @return HTTPHeaders
     */
    public HttpHeaders getAcceptHeaders(MediaType mediaType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }

    /**
     * Constructs HttpEntity instance with json http headers and inputs params
     * 
     * @param vars
     *            input params map
     * @return HttpEntity
     */
    public HttpEntity<MultiValueMap<String, String>> getJsonHttpHeaders(
            MultiValueMap<String, String> vars) {
        MultiValueMap<String, String> tmpvars = vars;
        if (tmpvars == null) {
            tmpvars = new LinkedMultiValueMap<String, String>();
        }
        return new HttpEntity<MultiValueMap<String, String>>(vars,
                getAcceptJsonHeaders(MediaType.APPLICATION_JSON));
    }

    /**
     * Constructs HttpEntity instance with xml http headers and inputs params
     * 
     * @param vars
     *            input params map
     * @return HttpEntity
     */
    public HttpEntity<MultiValueMap<String, String>> getXMLHttpHeaders(
            MultiValueMap<String, String> vars) {
        MultiValueMap<String, String> tmpvars = vars;
        if (tmpvars == null) {
            tmpvars = new LinkedMultiValueMap<String, String>();
        }
        return new HttpEntity<MultiValueMap<String, String>>(vars,
                getAcceptJsonHeaders(MediaType.APPLICATION_XML));
    }
}
