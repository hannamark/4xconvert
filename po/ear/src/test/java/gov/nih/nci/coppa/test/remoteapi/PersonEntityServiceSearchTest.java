package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.convert.util.PersonNameConverterUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceSearchTest extends BasePersonEntityServiceTest {
    private Map<Ii, PersonDTO> catalog = new HashMap<Ii, PersonDTO>();

    private Ii remoteCreateAndCatalog(PersonDTO person) throws EntityValidationException {
        Ii id = getPersonService().createPerson(person);
        person.setIdentifier(id);
        catalog.put(id, person);
        return id;
    }

    private PersonDTO create(String fName, String lName, String prefix, String suffix) {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn(fName, lName, prefix, suffix));
        return p;
    }

    private static boolean testDataLoaded = false;

    @Before
    public void initData() throws Exception {
        if (!testDataLoaded) {
            remoteCreateAndCatalog(create("Abc", "Klm", "Mr.", "II"));
            remoteCreateAndCatalog(create("Def", "Nop", "Ms.", "VI"));
            remoteCreateAndCatalog(create("Ghi", "Qrs", "Mrs.", "XI"));
            remoteCreateAndCatalog(create("ADG", "KNQ", "Dr.", "I"));
            remoteCreateAndCatalog(create("beh", "lor", null, null));
            testDataLoaded = true;
        }
    }

    @Test
    public void findByFamilyNameExact() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn(null, "Klm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByFamilyNameExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn(null, "klm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByFamilyNameSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn(null, "K", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByFamilyNameInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn(null, "L", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }
    @Test
    public void findByGivenNameExact() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn("Abc", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }
    
    @Test
    public void findByGivenNameExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn("abc", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }
    
    @Test
    public void findByGivenNameSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn("b", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }
    
    @Test
    public void findByGivenNameInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(PersonNameConverterUtil.convertToEnPn("E", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

}
