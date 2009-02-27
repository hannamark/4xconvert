package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.services.person.PersonDTO;


public class PersonTransformer implements Transformer<Person, PersonDTO> {

    public static final PersonTransformer INSTANCE = new PersonTransformer();

    private PersonTransformer() {}

    public Person toXml(PersonDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Person d = new Person();
        copyToXml(input, d);
        return d;
    }

    public void copyToXml(PersonDTO source, Person target) throws DtoTransformException {
        target.setIdentifier(IITransformer.INSTANCE.toXml(source.getIdentifier()));
        target.setName(ENTransformer.ENPN_INSTANCE.toXml(source.getName()));
        target.setPostalAddress(ADTransformer.INSTANCE.toXml(source.getPostalAddress()));
        target.setStatusCode(CDTransformer.INSTANCE.toXml(source.getStatusCode()));
        target.setTelecomAddress(DSET_TELTransformer.INSTANCE.toXml(source.getTelecomAddress()));
    }

    public PersonDTO toDto(Person input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        PersonDTO d = new PersonDTO();
        copyToDto(input, d);
        return d;
    }

    public void copyToDto(Person source, PersonDTO target) throws DtoTransformException {
        target.setIdentifier(IITransformer.INSTANCE.toDto(source.getIdentifier()));
        target.setName(ENTransformer.ENPN_INSTANCE.toDto(source.getName()));
        target.setPostalAddress(ADTransformer.INSTANCE.toDto(source.getPostalAddress()));
        target.setStatusCode(CDTransformer.INSTANCE.toDto(source.getStatusCode()));
        target.setTelecomAddress(DSET_TELTransformer.INSTANCE.toDto(source.getTelecomAddress()));
    }
}
