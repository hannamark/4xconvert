package gov.nih.nci.po.service.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.CurationStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceBeanTest extends AbstractHibernateTestCase {

    private PersonEntityServiceRemote remote;
    private static Country USA = new Country("United States", "001", "US", "USA");

    /**
     * setup the service.
     */
    @Before
    public void setupService() {
        remote = EjbTestHelper.getPersonEntityServiceBeanAsRemote();
    }

    private String findValueByType(EnPn enpn, EntityNamePartType type) {
        for (Enxp part : enpn.getPart()) {
            if (part.getType().equals(type)) {
                return part.getValue();
            }
        }
        return null;
    }

    @Test
    public void getPerson() {

        PoHibernateUtil.getCurrentSession().save(USA);
        Person per = makePerson();
        long id = (Long) PoHibernateUtil.getCurrentSession().save(per);
        PersonDTO result = remote.getPerson(id);
        assertEquals(per.getId().longValue(), Long.parseLong(result.getIdentifier().getExtension()));
        assertEquals(per.getFirstName(), findValueByType(result.getName(), EntityNamePartType.GIV));
        assertEquals(per.getLastName(), findValueByType(result.getName(), EntityNamePartType.FAM));
    }

    private Person makePerson() {
        Person p = new Person();
        p.setCurationStatus(CurationStatus.NEW);
        p.setPrefix("Dr.");
        p.setFirstName("Dixie");
        p.setLastName("Tavela");
        return p;
    }

    @Test
    public void createPerson() throws Exception {
        PersonDTO dto = new PersonDTO();
        Ii isoId = new Ii();
        isoId.setRoot("test");
        isoId.setExtension("99");
        dto.setIdentifier(isoId);
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue("Firsty");
        dto.getName().getPart().add(part);
        part = new Enxp(EntityNamePartType.FAM);
        part.setValue("McGillicutty");
        dto.getName().getPart().add(part);
        long id = remote.createPerson(dto);
        assertNotSame(id, Long.valueOf(isoId.getExtension())); // make sure this id was not used
        Person p = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, id);
        assertEquals(findValueByType(dto.getName(), EntityNamePartType.FAM), p.getLastName());
        assertEquals(findValueByType(dto.getName(), EntityNamePartType.GIV), p.getFirstName());
    }

    @Test
    public void validate() {

        PersonDTO dto = new PersonDTO();
        Ii isoId = new Ii();
        isoId.setRoot("test");
        isoId.setExtension("99");
        dto.setIdentifier(isoId);
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue("Firsty");
        dto.getName().getPart().add(part);
        Map<String, String[]> errors = remote.validate(dto);
        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("lastName"));
    }
}
