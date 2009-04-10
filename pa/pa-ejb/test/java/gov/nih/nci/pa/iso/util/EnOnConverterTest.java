package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.service.util.LookUpTableServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.organization.OrganizationDTO;

import org.junit.Before;
import org.junit.Test;

public class EnOnConverterTest {
	OrganizationDTO org = new OrganizationDTO(); 
	private LookUpTableServiceBean bean = new LookUpTableServiceBean();
    private LookUpTableServiceRemote remoteEjb = bean;
    

	
	@Before
    public void setUp() {
		org.setName(EnOnConverter.convertToEnOn("org"));
		org.setIdentifier(IiConverter.converToPoOrganizationalContactIi("1"));
		Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
		org.setPostalAddress(address);
		TestSchema.reset();
		
	}
	@Test
	public void testConvertToEnOn() {
		
		String str=null;
		assertNull(EnOnConverter.convertEnOnToString(EnOnConverter.convertToEnOn(str)));
	}
	
	@Test
	public void testConvertPoOrganizationDTO() throws Exception{
		Country country = createCountryObj();
        TestSchema.addUpdObject(country);
		List<Country> con = remoteEjb.getCountries();
		PaOrganizationDTO paOrgDTO = EnOnConverter.convertPoOrganizationDTO(org, con);
		assertEquals("Testing org name", "org", paOrgDTO.getName());
		assertEquals("Testing Country name", "United States Of America", paOrgDTO.getCountry());
		
	}
	
	public static Country createCountryObj() {
        Country c1 = new Country();
        c1.setAlpha2("US");
        c1.setAlpha3("USA");
        c1.setName("United States Of America");
        return c1;
        
    }
	
}
