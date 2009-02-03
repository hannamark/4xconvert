package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.services.person.PersonDTO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.iso._21090.DSET_TEL;

public class PersonTransformer implements Transformer<Person, PersonDTO> {
    protected static Logger logger = LogManager.getLogger(PersonTransformer.class);

    public PersonDTO transform(Person input) throws DtoTransformException {
        PersonDTO res = new PersonDTO();
        res = transform(input, res);
        return res;
    }

    public PersonDTO transform(Person input, PersonDTO res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setIdentifier(new IITransformer().transform(input.getIdentifier()));
        res.setName(new ENPNTransformer().transform(input.getName()));
        res.setPostalAddress(new ADTransformer().transform(input.getPostalAddress()));
        // res.setSexCode(new CDTransformer().transform(input.getSexCode()));
        res.setStatusCode(new CDTransformer().transform(input.getStatusCode()));
        DSET_TELTransformer<Tel> dsetTransformer = new DSET_TELTransformer<Tel>();
        gov.nih.nci.coppa.iso.DSet<Tel> telAddress = dsetTransformer.transform(input.getTelecomAddress());
        res.setTelecomAddress(telAddress);
        return res;
    }

    public Person transform(PersonDTO input) throws DtoTransformException {
        Person res = new Person();
        res = transform(input, res);
        return res;
    }

    public Person transform(PersonDTO input, Person res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setIdentifier(new IITransformer().transform(input.getIdentifier()));
        res.setName(new ENPNTransformer().transform(input.getName()));
        res.setPostalAddress(new ADTransformer().transform(input.getPostalAddress()));
        // res.setSexCode(new CDTransformer().transform(input.getSexCode()));
        res.setStatusCode(new CDTransformer().transform(input.getStatusCode()));
        DSET_TEL telAddress = new DSET_TELTransformer<Tel>().transform(input.getTelecomAddress());
        res.setTelecomAddress(telAddress);
        return res;
    }
}
