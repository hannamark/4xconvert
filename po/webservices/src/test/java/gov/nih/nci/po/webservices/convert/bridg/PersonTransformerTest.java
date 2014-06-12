package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETCDTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTelTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENPNTransformerTest;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.services.person.PersonDTO;
import org.iso._21090.II;

import static org.junit.Assert.assertEquals;

public class PersonTransformerTest extends AbstractTransformerTestBase <PersonTransformer, Person, PersonDTO> {
    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = Constants.NCI_OID + ".1";

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
        dto.setSexCode(new CDTransformerTest().makeDtoSimple());
        dto.setRaceCode(new DSETCDTransformerTest().makeDtoSimple());
        dto.setEthnicGroupCode(new DSETCDTransformerTest().makeDtoSimple());
        dto.setBirthDate(new TSTransformerTest().makeDtoSimple());
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
        xml.setSexCode(new CDTransformerTest().makeXmlSimple());
        xml.setRaceCode(new DSETCDTransformerTest().makeXmlSimple());
        xml.setEthnicGroupCode(new DSETCDTransformerTest().makeXmlSimple());
        xml.setBirthDate(new TSTransformerTest().makeXmlSimple());
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
        new CDTransformerTest().verifyDtoSimple(x.getSexCode());
        new DSETCDTransformerTest().verifyDtoSimple(x.getRaceCode());
        new DSETCDTransformerTest().verifyDtoSimple(x.getEthnicGroupCode());
        new TSTransformerTest().verifyDtoSimple(x.getBirthDate());
    }

    @Override
    public void verifyXmlSimple(Person x) {
        assertEquals(x.getIdentifier().getExtension(), "123");
        assertEquals(x.getIdentifier().getIdentifierName(),PERSON_IDENTIFIER_NAME);
        new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
        new DSETTelTransformerTest().verifyXmlSimple(x.getTelecomAddress());
        new ADTransformerTest().verifyXmlSimple(x.getPostalAddress());
        new ENPNTransformerTest().verifyXmlSimple(x.getName());
        new CDTransformerTest().verifyXmlSimple(x.getSexCode());
        new DSETCDTransformerTest().verifyXmlSimple(x.getRaceCode());
        new DSETCDTransformerTest().verifyXmlSimple(x.getEthnicGroupCode());
        new TSTransformerTest().verifyXmlSimple(x.getBirthDate());
    }

}
