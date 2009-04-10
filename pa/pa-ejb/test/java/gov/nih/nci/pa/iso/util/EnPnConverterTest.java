package gov.nih.nci.pa.iso.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.Tel;
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
		poPerson.setIdentifier(IiConverter.converToPoPersonIi("1"));
		 List<String> phones = new ArrayList<String>();
		 String phone="1111111111";
		 String email="a@a.com";
	        phones.add(phone);
	        List<String> emails = new ArrayList<String>();
	        emails.add(email);
	        DSet<Tel> dsetList = null;
	        dsetList =  DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
	        dsetList =  DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
		  poPerson.setTelecomAddress(dsetList);
		  Ad address = AddressConverterUtil.create("101 Renner rd", "deliveryAddress", "Richardson", "TX", "75081", "USA");
		  poPerson.setPostalAddress(address);
		
	}

	@Test
	public void testConvertToPaPerson() {
			    
		 Person person = EnPnConverter.convertToPaPerson(poPerson);
		 assertNotNull(person);
		 assertEquals("Testing first name","firstName",person.getFirstName());
	}

	@Test
	public void testConvertToPaPersonDTO() {
		PaPersonDTO paPersonDTO = EnPnConverter.convertToPaPersonDTO(poPerson);
		assertEquals("testing last name", "lastName",paPersonDTO.getLastName());
		assertEquals("testing last name","a@a.com",paPersonDTO.getEmail());
	}

	@Test
	public void testConvertToEnPn() {
		EnPn enpn = EnPnConverter.convertToEnPn("firstName", "middleName", "lastName", "prefix", "suffix");
		assertEquals("Testing last name", "lastName",enpn.getPart().get(0).getValue());
		assertEquals("Testing first name", "firstName",enpn.getPart().get(1).getValue());
	}

}
