package gov.nih.nci.accrual.web.dto.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PhysicianWebDTOTest {

	PhysicianWebDTO physicianWebDto;


    @Before
    public void initDto() {
    	physicianWebDto = new PhysicianWebDTO();
    	physicianWebDto.setFirstName("LIsa");
    	physicianWebDto.setLastName("Kelly");
    	physicianWebDto.setMiddleName("G");
    	physicianWebDto.setCity("Richardson");
    	physicianWebDto.setState("TX");
    	physicianWebDto.setZipCode("75082");
    	physicianWebDto.setCountry("USA");
    }
    
    @Test
    public void countryTest() {
      assertNotNull(physicianWebDto.getCountry());
    }    
    @Test
    public void zipTest() {
      assertNotNull(physicianWebDto.getZipCode());
    }
    @Test
    public void stateTest() {
      assertNotNull(physicianWebDto.getState());
    }
    @Test
    public void cityTest() {
      assertNotNull(physicianWebDto.getCity());
    }
    @Test
    public void middleNameTest() {
      assertNotNull(physicianWebDto.getMiddleName());
    }
    @Test
    public void lastNameTest() {
      assertNotNull(physicianWebDto.getLastName());
    }
    @Test
    public void firstNameTest() {
      assertNotNull(physicianWebDto.getFirstName());
    }
}
