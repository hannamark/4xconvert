package gov.nih.nci.po.services.person;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.convert.IdConverter.PersonIdConverter;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.person.PersonDTO;

import org.junit.Test;


public class PersonDTOTest {

    @Test
    public void convert() {
        PersonDTO dto = new PersonDTO();
        dto.setIdentifier(new PersonIdConverter().convertToIi(1l));
        dto.setName(PersonNameConverterUtil.convertToEnPn("b", "m", "a", "c", "d"));
        Person p = PoXsnapshotHelper.createModel(dto);
        assertEquals("a", p.getLastName());
        assertEquals("b", p.getFirstName());
        assertEquals("m", p.getMiddleName());
        assertEquals("c", p.getPrefix());
        assertEquals("d", p.getSuffix());
        assertEquals(new Long(1), p.getId());
    }

}
