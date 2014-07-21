package gov.nih.nci.webservices.rest.client.integ;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.po.ws.common.types.FamilyList;
import gov.nih.nci.webservices.rest.client.FamilyRestServiceClient;
import gov.nih.nci.webservices.rest.client.RestClient;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Ignore
public class RestClientTest {
	static ApplicationContext ctx;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath:rest-client-context.xml");
	}

	@Test
	public void testGetRestTemplate() {
		RestClient restClient = ctx.getBean(RestClient.class);
		assertNotNull(restClient);
		assertNotNull(restClient.getRestTemplate());
	}

	@Test
	public void testGetHttpClient() {
		RestClient restClient = ctx.getBean(RestClient.class);
		assertNotNull(restClient);
		assertNotNull(restClient.getRestTemplate());
	}
	
	//@Test
	public void testMarshallingHttpConverter() {
		MarshallingHttpMessageConverter cnvrtr = ctx.getBean(MarshallingHttpMessageConverter.class);
		assertNotNull(cnvrtr);
		assertNotNull(cnvrtr.getSupportedMediaTypes());
	}
	
	@Test
	public void testFamilyServiceClient() {
		FamilyRestServiceClient restClient = ctx.getBean(FamilyRestServiceClient.class);
		Family family = new Family();
		family.setName("Arizona");
		List<Family> famLst = restClient.search(family);
		assertNotNull(famLst);
		assertFalse(famLst.isEmpty());
	}
	
	/**
	 * TO BE REMOVED - Integration test
	 */
	@Test
	public void testFamilyService() {
		RestClient restClient = ctx.getBean(RestClient.class);
		String fsUrl = restClient.getRestClientProperties().getFamilyServiceUrl();
		
		MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
		vars.add("name", "Arizona");
		
		fsUrl = fsUrl + "?name=Arizona";
		
		HttpEntity<MultiValueMap<String, String>> request = restClient.getJsonHttpHeaders(vars);
		
		ResponseEntity<FamilyList> output = restClient.getRestTemplate().exchange(fsUrl, HttpMethod.GET, 
				request, FamilyList.class);
		assertNotNull(output.getBody());
		assertNotNull(output.getBody().getFamily());
	}
	

}
