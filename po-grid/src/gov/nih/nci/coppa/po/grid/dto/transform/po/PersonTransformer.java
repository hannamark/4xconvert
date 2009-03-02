package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.grid.dto.transform.ADTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.CDTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSET_TELTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.ENTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;
import gov.nih.nci.services.person.PersonDTO;


public class PersonTransformer implements Transformer<Person, PersonDTO> {

    public static final PersonTransformer INSTANCE = new PersonTransformer();

    private PersonTransformer() {}

    public Person toXml(PersonDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Person d = new Person();
        d.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setName(ENTransformer.ENPN_INSTANCE.toXml(input.getName()));
        d.setPostalAddress(ADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        d.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        d.setTelecomAddress(DSET_TELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        return d;
    }

    public PersonDTO toDto(Person input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        PersonDTO d = new PersonDTO();
        d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setName(ENTransformer.ENPN_INSTANCE.toDto(input.getName()));
        d.setPostalAddress(ADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        d.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        d.setTelecomAddress(DSET_TELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        return d;
    }
}
