package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.services.person.PersonDTO;

import org.junit.Before;
import org.junit.Test;

public class EnPnConverterTest {
	PersonDTO poPerson = new PersonDTO();  
	
	@Before
    public void setUp() {
		poPerson.setName(EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix"));
		
	}

	@Test
	public void testConvertToPaPerson() {
			    
		 Person person = EnPnConverter.convertToPaPerson(poPerson);
		 assertNotNull(person);
		 assertEquals("Testing first name","firstName",person.getFirstName());
	}

	/*@Test
	public void testConvertToPaPersonDTO() {
		PaPersonDTO paPersonDTO = EnPnConverter.convertToPaPersonDTO(poPerson);
		assertEquals("testing last name", "lastName",paPersonDTO.getLastName());
		 assertNull(paPersonDTO.getFullName());
	}
*/
	@Test
	public void testConvertToEnPn() {
		EnPn enpn = EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix");
		assertEquals("Testing last name", "lastName",enpn.getPart().get(0).getValue());
		assertEquals("Testing first name", "firstName",enpn.getPart().get(1).getValue());
	}

}
