/**
 * 
 */
package gov.nih.nci.pa.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.domain.StudySiteTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderServiceBean;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class PaPersonDTOTest {
	  private PAHealthCareProviderServiceBean bean = new PAHealthCareProviderServiceBean();
	   private PAHealthCareProviderRemote remoteEjb = bean;
	@Before
    public void setUp() throws Exception {
        TestSchema.reset();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = PersonTest.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        StudySite spc = StudySiteTest.createStudySiteObj(sp, hcf);
        TestSchema.addUpdObject(spc);
        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);
        StudySiteContact spcc = new StudySiteContact();
        spcc.setClinicalResearchStaff(crs);
        spcc.setRoleCode(StudySiteContactRoleCode.SUBMITTER);
        spcc.setStudySite(spc);
        spcc.setStudyProtocol(sp);
        spcc.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        TestSchema.addUpdObject(spcc);
    }
	
	
	/**
	 * Test method for {@link gov.nih.nci.pa.dto.PaPersonDTO#setId(java.lang.Long)}.
	 */
	@Test
	public void testGetSet() throws Exception {
		 List<PaPersonDTO> data = remoteEjb.getPersonsByStudySiteId(Long.valueOf(1),"SUBMITTER");
		 data.get(0).setFirstName("TestFN");
		 data.get(0).setLastName("TestLN");
		 assertEquals("Testing first name failed","TestFN",data.get(0).getFirstName() );
		 assertEquals("Testing last name failed","TestLN",data.get(0).getLastName() );
		 assertNotNull("Testing Full name failed",data.get(0).getFullName() );
		 data.get(0).setStreetAddress("101 Renner rd");
		 data.get(0).setCity("Richardson");
		 data.get(0).setState("TX");
		 data.get(0).setCountry("USA");
		 data.get(0).setZip("75081");
		 data.get(0).setEmail("a@a.com");
		 data.get(0).setPhone("1110001111");
		 data.get(0).setTty("tty");
		 data.get(0).setUrl("www.url.com");
		 data.get(0).setFax("222222222");
		 
		 assertNotNull("Testing Address failed",data.get(0).getAddress());
		 assertNotNull("Testing Email",data.get(0).getEmail());
		 assertNotNull("Testing Phone",data.get(0).getPhone());
		 assertNotNull("Testing role name",data.get(0).getRoleName());
		 assertEquals("Testing tty","tty",data.get(0).getTty());
		 assertEquals("Testing url","www.url.com",data.get(0).getUrl());
		 assertEquals("Testing fax","222222222",data.get(0).getFax());
		
	}

	

}
