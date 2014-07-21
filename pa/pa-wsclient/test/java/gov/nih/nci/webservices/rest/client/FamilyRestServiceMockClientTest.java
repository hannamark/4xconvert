package gov.nih.nci.webservices.rest.client;

import static org.junit.Assert.*;
/** Spring 3.2.x use these */
 import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.po.ws.common.types.FamilyList;
import gov.nih.nci.po.ws.common.types.FamilyMember;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration(locations = {"classpath:/rest-client-context.xml"})
public class FamilyRestServiceMockClientTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private FamilyRestServiceClient familyRestServiceClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    
    @Test
    public void testSearchByName() throws JsonGenerationException, JsonMappingException, IOException {
    	String fsUrl = familyRestServiceClient.getRestClientProperties().getFamilyServiceUrl();
    	fsUrl = fsUrl + "?name=Arizona";
    	
    	Family family = new Family();
    	family.setName("Arizona");
		
		FamilyList fl = new FamilyList();
		fl.getFamily().add(family);
		
		ObjectMapper om = new ObjectMapper();
		String response = om.writeValueAsString(fl);
    	
        mockServer.expect(requestTo(fsUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
		
        List<Family> result = familyRestServiceClient.search(family);

        mockServer.verify();
        
        assertNotNull(result);
		assertFalse(result.isEmpty());
    }
    
    @Test
    public void testSearchByMemberId() throws JsonGenerationException, JsonMappingException, IOException {
    	String fsUrl = familyRestServiceClient.getRestClientProperties().getFamilyServiceUrl();
    	fsUrl = fsUrl + "?organizationId=33922";
    	
    	Family family = new Family();
		FamilyMember fm = new FamilyMember();
		fm.setOrganizationId(33922L);
		family.getMember().add(fm);
		
		FamilyList fl = new FamilyList();
		fl.getFamily().add(family);
		
		ObjectMapper om = new ObjectMapper();
		String response = om.writeValueAsString(fl);
    	
        mockServer.expect(requestTo(fsUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
		
        List<Family> result = familyRestServiceClient.search(family);

        mockServer.verify();
        
        assertNotNull(result);
		assertFalse(result.isEmpty());
    }
    
    
}