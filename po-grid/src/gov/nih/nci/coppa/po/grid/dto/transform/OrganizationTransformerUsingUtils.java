package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.po.Person;

public class OrganizationTransformerUsingUtils implements Transformer<Person, gov.nih.nci.services.person.PersonDTO> {

    public gov.nih.nci.services.person.PersonDTO transform(Person input) throws DtoTransformException {
        gov.nih.nci.services.person.PersonDTO res = new gov.nih.nci.services.person.PersonDTO();
        res = transform(input, res);
        return res;
    }

    public gov.nih.nci.services.person.PersonDTO transform(Person input, gov.nih.nci.services.person.PersonDTO res)
            throws DtoTransformException {
        res = (gov.nih.nci.services.person.PersonDTO) new ObjectTransformer().transform(input, res);
        return res;
    }

}
