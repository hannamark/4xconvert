package gov.nih.nci.coppa.test.remoteapi;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.person.PersonDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceSearchTest extends BasePersonEntityServiceTest {
    private final Map<Ii, PersonDTO> catalog = new HashMap<Ii, PersonDTO>();

    private Ii remoteCreateAndCatalog(PersonDTO person) throws EntityValidationException {
        Ii id = getPersonService().createPerson(person);
        person.setIdentifier(id);
        catalog.put(id, person);
        return id;
    }

    private PersonDTO create(String fName, String mName, String lName, String prefix, String suffix) {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(fName, mName, lName, prefix, suffix));

        p.setPostalAddress(RemoteApiUtils.createAd("street", "delivery", "city", "state", "zip", "USA"));
        return p;
    }

    private static boolean testDataLoaded = false;

    @Before
    public void initData() throws Exception {
        if (!testDataLoaded) {
            remoteCreateAndCatalog(create("Abc", "Mid1", "Klm", "Mr.", "II"));
            remoteCreateAndCatalog(create("Def", "Mid2", "Nop", "Ms.", "VI"));
            remoteCreateAndCatalog(create("Ghi", "Mid3", "Qrs", "Mrs.", "XI"));
            remoteCreateAndCatalog(create("ADG", "Mid4", "KNQ", "Dr.", "I"));
            remoteCreateAndCatalog(create("beh", "Mid5", "lor", null, null));
            testDataLoaded = true;
        }
    }

    @Test
    public void findByFamilyNameExact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "Klm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByFamilyNameExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "klm", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByFamilyNameSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "K", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByFamilyNameInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn(null, null, "L", null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByGivenNameExact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("Abc", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenNameExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("abc", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenNameSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("b", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }

    @Test
    public void findByGivenNameInsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("E", null, null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(2, results.size());
    }
    @Test
    public void findByGivenName2Exact() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "Mid1", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenName2ExactInsensitive() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "mid1", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

    @Test
    public void findByGivenName2Substring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "id", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(5, results.size());
    }

    @Test
    public void findByGivenName2InsensitiveSubstring() {
        PersonDTO p = new PersonDTO();
        p.setName(RemoteApiUtils.convertToEnPn("%", "ID1", null, null, null));
        List<PersonDTO> results = getPersonService().search(p);
        assertEquals(1, results.size());
    }

}
