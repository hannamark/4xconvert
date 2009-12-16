package gov.nih.nci.accrual.web.dto.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TreatmentSiteWebDTOTest {

	TreatmentSiteWebDTO treatmentSiteWebDTO;


    @Before
    public void initDto() {
    	treatmentSiteWebDTO = new TreatmentSiteWebDTO();
    	treatmentSiteWebDTO.setName("Kelly");
    	treatmentSiteWebDTO.setCity("Richardson");
    	treatmentSiteWebDTO.setState("TX");
    	treatmentSiteWebDTO.setZipCode("75082");
    	treatmentSiteWebDTO.setCountry("USA");
    }
    
    @Test
    public void countryTest() {
      assertNotNull(treatmentSiteWebDTO.getCountry());
    }    
    @Test
    public void zipTest() {
      assertNotNull(treatmentSiteWebDTO.getZipCode());
    }
    @Test
    public void stateTest() {
      assertNotNull(treatmentSiteWebDTO.getState());
    }
    @Test
    public void cityTest() {
      assertNotNull(treatmentSiteWebDTO.getCity());
    }
    @Test
    public void nameTest() {
      assertNotNull(treatmentSiteWebDTO.getName());
    }
}
