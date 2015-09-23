/**
 * 
 */
package gov.nih.nci.coppa.test.integration.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * @author dkrylov
 * 
 */
public abstract class AbstractCurateTest extends AbstractPoWebTest {

    protected PersonEntityServiceRemote personService;

    protected final Map<Ii, PersonDTO> catalogPersons = new HashMap<Ii, PersonDTO>();

    @Override
    public void setUp() throws Exception {
        super.setUp();

        if (personService == null) {
            personService = RemoteServiceHelper.getPersonEntityService();
        }
    }

    protected Ii remoteCreateAndCatalog(PersonDTO org)
            throws EntityValidationException, CurationException {
        Ii id = getPersonService().createPerson(org);
        org.setIdentifier(id);
        catalogPersons.put(id, org);
        return id;
    }

    protected PersonEntityServiceRemote getPersonService() {
        return personService;
    }

    protected PersonDTO createPerson(String firstName, String lastName)
            throws URISyntaxException {
        return createPerson(firstName, null, lastName, null, null,
                TestConvertHelper.createAd("123 abc ave.", null, "mycity",
                        "VA", "12345", "USA"));
    }

    protected PersonDTO createPerson(String firstName, String middleName,
            String lastName, String prefix, String suffix, Ad postalAddress)
            throws URISyntaxException {
        PersonDTO person = new PersonDTO();
        person.setName(TestConvertHelper.convertToEnPn(firstName, middleName,
                lastName, prefix, suffix));

        person.setPostalAddress(postalAddress);
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        person.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        person.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        person.getTelecomAddress().getItem().add(url);
        return person;
    }
}
