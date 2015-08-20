package gov.nih.nci.registry.rest.jasper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.registry.rest.jasper.Users.User;
import gov.nih.nci.registry.service.MockRestClientNCITServer;

public class JasperServerRestClientTest {

	private JasperServerRestClient restClient;
	public static final int NCIT_API_MOCK_PORT = (int) (60000+Math.random()*400);
    MockRestClientNCITServer mockRestClientNCITServer = new MockRestClientNCITServer();
	private Map<String, String> reportGroupMap;
    
    
	@Before
    public void setup() throws Exception {
		 mockRestClientNCITServer.startServer(NCIT_API_MOCK_PORT);
		 String baseURL = "http://localhost:"+NCIT_API_MOCK_PORT+"/reports/rest/user";
		
		String jasperAdmin = "jasperadmin";
		String jasperPwd = "jasperadmin";
		
		restClient = new JasperServerRestClient(baseURL, jasperAdmin, jasperPwd, true);
		reportGroupMap = new HashMap<String, String>();
		
		reportGroupMap.put("DT4","ROLE_DT4");
		reportGroupMap.put("DT3","ROLE_DT3|ORG_2");
	}
	@Test
	public void testGetAllUserDetails() {
		
		Users resp = restClient.getAllUserDetails();
		
		assertNotNull(resp);

	}

	@Test
	public void testGetUserDetails() {
		
		Users resp = restClient.getUserDetails("firstName");
		assertNotNull(resp);
		assertEquals("firstName", resp.getUser().get(0).getUsername());
	}

/*	@Test
	public void testRemoveRole() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testUpdateRoles() {
		Users users = restClient.getUserDetails("firstName");
		User user = users.getUser().get(0);
		String reportIds = "DT4";
		String upResp = restClient.updateRoles(user, reportIds , reportGroupMap);
		assertNotNull(upResp);
	}
	
	@Test
	public void testUpdateRolesWithOrg() {
		Users users = restClient.getUserDetails("firstName");
		User user = users.getUser().get(0);
		String reportIds = "DT3";
		String upResp = restClient.updateRoles(user, reportIds , reportGroupMap);
		assertNotNull(upResp);
	}

	@After
    public void tearDown() throws Exception {
        mockRestClientNCITServer.stopServer();
    }
}
