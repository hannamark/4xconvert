package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETCDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ENTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.services.person.PersonDTO;

/**
 * Transforms Persons.
 */
public final class PersonTransformer
    extends AbstractTransformer<Person, PersonDTO>
    implements Transformer<Person, PersonDTO> {

    /**
     * Public singleton.
     */
    public static final PersonTransformer INSTANCE = new PersonTransformer();

    private PersonTransformer() { }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Person toXml(PersonDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Person d = new Person();
        d.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setName(ENTransformer.ENPN_INSTANCE.toXml(input.getName()));
        d.setPostalAddress(ADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        d.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        d.setBirthDate(TSTransformer.INSTANCE.toXml(input.getBirthDate()));
        d.setSexCode(CDTransformer.INSTANCE.toXml(input.getSexCode()));
        d.setRaceCode(DSETCDTransformer.INSTANCE.toXml(input.getRaceCode()));
        d.setEthnicGroupCode(DSETCDTransformer.INSTANCE.toXml(input.getEthnicGroupCode()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public PersonDTO toDto(Person input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        PersonDTO d = new PersonDTO();
        d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setName(ENTransformer.ENPN_INSTANCE.toDto(input.getName()));
        d.setPostalAddress(ADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        d.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        d.setBirthDate(TSTransformer.INSTANCE.toDto(input.getBirthDate()));
        d.setSexCode(CDTransformer.INSTANCE.toDto(input.getSexCode()));
        d.setRaceCode(DSETCDTransformer.INSTANCE.toDto(input.getRaceCode()));
        d.setEthnicGroupCode(DSETCDTransformer.INSTANCE.toDto(input.getEthnicGroupCode()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public Person[] createXmlArray(int arg0) throws DtoTransformException {
        return new Person[arg0];
    }
}
