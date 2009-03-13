package gov.nih.nci.coppa.po.grid.dto.transform;

import static org.junit.Assert.assertEquals;

import org.iso._21090.II;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.services.person.PersonDTO;

public class PersonTransformerTest extends 
AbstractTransformerTestBase <PersonTransformer, Person, PersonDTO> {
	 /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = "2.16.840.1.113883.3.26.4.1";
    
	@Override
	public PersonDTO makeDtoSimple() {
		 Ii id = new Ii();
		 id.setRoot(PERSON_ROOT);
		 id.setIdentifierName(PERSON_IDENTIFIER_NAME);
		 id.setExtension("123");
		PersonDTO dto = new PersonDTO();
		dto.setIdentifier(id);
		dto.setStatusCode(new CDTransformerTest().makeDtoSimple());
		dto.setTelecomAddress(new DSETTelTransformerTest().makeDtoSimple());
		dto.setPostalAddress(new ADTransformerTest().makeDtoSimple());
		dto.setName(new ENPNTransformerTest().makeDtoSimple());
		return dto;
	}

	@Override
	public Person makeXmlSimple() {
	   II id = new II();
	   id.setRoot(PERSON_ROOT);
	   id.setIdentifierName(PERSON_IDENTIFIER_NAME);
	   id.setExtension("123");
		
	   Person xml = new Person();
	   xml.setIdentifier(id);
	   xml.setStatusCode(new CDTransformerTest().makeXmlSimple());
       xml.setTelecomAddress(new DSETTelTransformerTest().makeXmlSimple());
       xml.setPostalAddress(new ADTransformerTest().makeXmlSimple());
       xml.setName(new ENPNTransformerTest().makeXmlSimple());
       return xml;
	}

	@Override
	public void verifyDtoSimple(PersonDTO x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),PERSON_IDENTIFIER_NAME);
		new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
		new DSETTelTransformerTest().verifyDtoSimple(x.getTelecomAddress());
		new ADTransformerTest().verifyDtoSimple(x.getPostalAddress());
		new ENPNTransformerTest().verifyDtoSimple(x.getName());
	}

	@Override
	public void verifyXmlSimple(Person x) {
		assertEquals(x.getIdentifier().getExtension(), "123");
		assertEquals(x.getIdentifier().getIdentifierName(),PERSON_IDENTIFIER_NAME);
		new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
		new DSETTelTransformerTest().verifyXmlSimple(x.getTelecomAddress());
		new ADTransformerTest().verifyXmlSimple(x.getPostalAddress());
		new ENPNTransformerTest().verifyXmlSimple(x.getName());
	}

}
