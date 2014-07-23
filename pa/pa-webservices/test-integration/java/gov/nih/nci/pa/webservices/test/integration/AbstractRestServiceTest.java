/**
 * 
 */
package gov.nih.nci.pa.webservices.test.integration;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest;
import gov.nih.nci.pa.test.integration.util.TestProperties;
import gov.nih.nci.pa.webservices.types.TrialRegistrationConfirmation;

/**
 * @author dkrylov
 * 
 */
public abstract class AbstractRestServiceTest extends AbstractPaSeleniumTest {
    @SuppressWarnings("deprecation")
    protected DefaultHttpClient httpClient = null;
    protected AuthScope authScope;

    protected String baseURL = "";

    protected static final String APPLICATION_XML = "application/xml";

    @SuppressWarnings("deprecation")
    public void setUp() throws Exception {
        super.setUp();
        httpClient = new DefaultHttpClient();

        authScope = new AuthScope(TestProperties.getServerHostname(),
                TestProperties.getServerPort());
        Credentials credentials = new UsernamePasswordCredentials(
                "submitter-ci", "Coppa#12345");
        httpClient.getCredentialsProvider().setCredentials(authScope,
                credentials);

        baseURL = "http://" + TestProperties.getServerHostname() + ":"
                + TestProperties.getServerPort() + "/services";

    }

    protected int getReponseCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    @SuppressWarnings("deprecation")
    protected String getResponseContentType(HttpResponse response) {
        return EntityUtils.getContentMimeType(response.getEntity());
    }

    protected TrialRegistrationConfirmation unmarshalTrialRegistrationConfirmation(
            HttpEntity httpEntity) throws JAXBException, ParseException,
            IOException {
        JAXBContext jaxbContext = JAXBContext
                .newInstance(TrialRegistrationConfirmation.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        String orgXMLStr = EntityUtils.toString(httpEntity, "utf-8");
        LOG.info(orgXMLStr);
        JAXBElement<TrialRegistrationConfirmation> jaxbEle = (JAXBElement<TrialRegistrationConfirmation>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(orgXMLStr)),
                        TrialRegistrationConfirmation.class);
        return jaxbEle.getValue();
    }
}
