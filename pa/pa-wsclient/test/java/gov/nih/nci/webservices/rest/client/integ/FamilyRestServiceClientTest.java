package gov.nih.nci.webservices.rest.client.integ;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.po.ws.common.types.FamilyMember;
import gov.nih.nci.webservices.rest.client.FamilyRestServiceClient;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * JUnit class for FamilyRestServiceClient
 * @author vinodh.c
 *
 */
@Ignore
public class FamilyRestServiceClientTest {
	
	static ApplicationContext ctx;
	static FamilyRestServiceClient client;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath:rest-client-context.xml");
		client = ctx.getBean(FamilyRestServiceClient.class);
	}
	
	@Test
	public void testSearchByName () {
		Family family = new Family();
		family.setName("Arizona");
		List<Family> famLst = client.search(family);
		assertNotNull(famLst);
		assertFalse(famLst.isEmpty());
	}
	
	@Test
	public void testSearchByMemberId () {
		Family family = new Family();
		FamilyMember fm = new FamilyMember();
		fm.setOrganizationId(33922L);
		family.getMember().add(fm);
		
		List<Family> famLst = client.search(family);
		assertNotNull(famLst);
		assertFalse(famLst.isEmpty());
	}
	
	

	
	
}
