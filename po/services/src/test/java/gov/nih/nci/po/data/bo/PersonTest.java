package gov.nih.nci.po.data.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.po.data.common.Country;
import gov.nih.nci.po.data.common.CurationStatus;
import gov.nih.nci.po.data.common.OrganizationType;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.io.Serializable;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class PersonTest extends AbstractHibernateTestCase {
    private Country defaultCountry;
    private OrganizationType defaultOrgType;

    @SuppressWarnings("deprecation")
    @Before
    public void init() {
        Country country = new Country("USA", "840", "US", "USA");
        Serializable cid = PoHibernateUtil.getCurrentSession().save(country);

        defaultCountry = (Country) PoHibernateUtil.getCurrentSession().get(Country.class, cid);
        assertNotNull(defaultCountry);

        OrganizationType tmp = new OrganizationType();
        tmp.setName("defaultOrgType");
        Serializable otId = PoHibernateUtil.getCurrentSession().save(tmp);
        defaultOrgType = (OrganizationType) PoHibernateUtil.getCurrentSession().get(OrganizationType.class, otId);
    }

    @Test
    public void createPersonWithMultipleCIsWithMultipleOrgs() {
        Organization savedOrg1 = createAndSaveOrganization();
        Organization savedOrg2 = createAndSaveOrganization();
        Organization savedOrg3 = createAndSaveOrganization();
        Organization savedOrg4 = createAndSaveOrganization();

        Address address1 = new Address("a", "b", "c", "d", defaultCountry);
        Address address2 = new Address("a", "b", "c", "d", defaultCountry);
        Address address3 = new Address("a", "b", "c", "d", defaultCountry);
        Address address4 = new Address("a", "b", "c", "d", defaultCountry);

        ContactInfo ci1 = createCI(address1, "ci1");
        PoHibernateUtil.getCurrentSession().save(ci1);
        ContactInfo ci2 = createCI(address2, "ci2");
        PoHibernateUtil.getCurrentSession().save(ci2);
        ContactInfo ci3 = createCI(address3, "ci3");
        PoHibernateUtil.getCurrentSession().save(ci3);
        ContactInfo ci4 = createCI(address4, "ci4");
        PoHibernateUtil.getCurrentSession().save(ci4);

        ci1.setOrganization(savedOrg1);
        ci2.setOrganization(savedOrg2);
        ci3.setOrganization(savedOrg3);
        ci4.setOrganization(savedOrg4);

        Person person = new Person(ci1);
        person.setCurationStatus(CurationStatus.NEW);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");

        ci1.setPerson(person);
        ci2.setPerson(person);
        ci3.setPerson(person);
        ci4.setPerson(person);

        person.getContactInfos().add(ci2);
        person.getContactInfos().add(ci3);
        person.getContactInfos().add(ci4);

        Long personId = (Long) PoHibernateUtil.getCurrentSession().save(person);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Person saved = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, personId);

        assertNotNull(saved.getPreferredContactInfo());
        assertEquals(saved.getContactInfos().get(0).getId(), saved.getPreferredContactInfo().getId());

        assertEquals(4, saved.getContactInfos().size());
        assertNotNull(saved.getContactInfos().get(0).getPerson());
        assertNotNull(saved.getContactInfos().get(1).getPerson());
        assertNotNull(saved.getContactInfos().get(2).getPerson());
        assertNotNull(saved.getContactInfos().get(3).getPerson());
        assertEquals(personId, saved.getContactInfos().get(0).getPerson().getId());
        assertEquals(personId, saved.getContactInfos().get(1).getPerson().getId());
        assertEquals(personId, saved.getContactInfos().get(2).getPerson().getId());
        assertEquals(personId, saved.getContactInfos().get(3).getPerson().getId());

        verifyCIData(saved.getContactInfos().get(0));
        verifyCIData(saved.getContactInfos().get(1));
        verifyCIData(saved.getContactInfos().get(2));
        verifyCIData(saved.getContactInfos().get(3));

        //verify Org associations
        assertNotNull(saved.getContactInfos().get(0).getOrganization());
        assertEquals(savedOrg1.getId(), saved.getContactInfos().get(0).getOrganization().getId());

        assertNotNull(saved.getContactInfos().get(1).getOrganization());
        assertEquals(savedOrg2.getId(), saved.getContactInfos().get(1).getOrganization().getId());

        assertNotNull(saved.getContactInfos().get(2).getOrganization());
        assertEquals(savedOrg3.getId(), saved.getContactInfos().get(2).getOrganization().getId());

        assertNotNull(saved.getContactInfos().get(3).getOrganization());
        assertEquals(savedOrg4.getId(), saved.getContactInfos().get(3).getOrganization().getId());

    }

    private void verifyCIData(ContactInfo savedCI) {
        assertEquals("a@b.com", savedCI.getEmail().get(0).getValue());
        assertEquals("b@c.com", savedCI.getEmail().get(1).getValue());
        assertEquals("c@d.com", savedCI.getEmail().get(2).getValue());
        assertEquals("d@e.com", savedCI.getEmail().get(3).getValue());

        assertEquals("000-456-7890", savedCI.getFax().get(0).getValue());
        assertEquals("111-456-7890", savedCI.getFax().get(1).getValue());
        assertEquals("222-456-7890", savedCI.getFax().get(2).getValue());
        assertEquals("333-456-7890", savedCI.getFax().get(3).getValue());

        assertEquals("444-456-7890", savedCI.getPhone().get(0).getValue());
        assertEquals("555-456-7890", savedCI.getPhone().get(1).getValue());
        assertEquals("666-456-7890", savedCI.getPhone().get(2).getValue());
        assertEquals("777-456-7890", savedCI.getPhone().get(3).getValue());

        assertEquals("http://a.com", savedCI.getUrl().get(0).getValue());
        assertEquals("http://b.com", savedCI.getUrl().get(1).getValue());
        assertEquals("http://c.com", savedCI.getUrl().get(2).getValue());
        assertEquals("http://d.com", savedCI.getUrl().get(3).getValue());
    }

    private static ContactInfo createCI(Address address1, String title) {
        ContactInfo ci = new ContactInfo(address1);

        ci.getEmail().add(new Email("a@b.com"));
        ci.getEmail().add(new Email("b@c.com"));
        ci.getEmail().add(new Email("c@d.com"));
        ci.getEmail().add(new Email("d@e.com"));

        ci.getFax().add(new PhoneNumber("000-456-7890"));
        ci.getFax().add(new PhoneNumber("111-456-7890"));
        ci.getFax().add(new PhoneNumber("222-456-7890"));
        ci.getFax().add(new PhoneNumber("333-456-7890"));

        ci.getPhone().add(new PhoneNumber("444-456-7890"));
        ci.getPhone().add(new PhoneNumber("555-456-7890"));
        ci.getPhone().add(new PhoneNumber("666-456-7890"));
        ci.getPhone().add(new PhoneNumber("777-456-7890"));

        ci.getUrl().add(new URL("http://a.com"));
        ci.getUrl().add(new URL("http://b.com"));
        ci.getUrl().add(new URL("http://c.com"));
        ci.getUrl().add(new URL("http://d.com"));
        ci.setTitle(title);
        return ci;
    }

    @Test
    public void createPersonWithPCIWithAssociatedOrganization() {
        //Org
        Organization savedOrg1 = createAndSaveOrganization();

        Address address1 = new Address("a", "b", "c", "d", defaultCountry);
        ContactInfo ci1 = createCI(address1, "ci1");
        Person person = new Person(ci1);
        person.setCurationStatus(CurationStatus.NEW);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci1.setPerson(person);

        ci1.setOrganization(savedOrg1);

        Long personId = (Long) PoHibernateUtil.getCurrentSession().save(person);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Person saved = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, personId);

        assertNotNull(saved.getPreferredContactInfo());
        assertEquals(saved.getContactInfos().get(0).getId(), saved.getPreferredContactInfo().getId());
        assertEquals(personId, saved.getContactInfos().get(0).getPerson().getId());
        verifyCIData(saved.getContactInfos().get(0));

        assertNotNull(saved.getPreferredContactInfo().getOrganization());
        assertEquals(savedOrg1.getId(), saved.getPreferredContactInfo().getOrganization().getId());

    }

    private Organization createAndSaveOrganization() {
        Address address2 = new Address("a", "b", "c", "d", defaultCountry);
        ContactInfo orgCI = createCI(address2, "orgCITitle");
        Organization organization = new Organization(orgCI);
        organization.setAbbreviationName("tstOrg");
        organization.setName("orgName");
        organization.setCurationStatus(CurationStatus.NEW);
        organization.getTypes().add(defaultOrgType);
        orgCI.setOrganization(organization);
        Long orgId = (Long) PoHibernateUtil.getCurrentSession().save(organization);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        assertNotNull(orgId);
        Organization savedOrg = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, orgId);
        assertNotNull(savedOrg);
        assertEquals("orgName", savedOrg.getName());
        assertEquals("tstOrg", savedOrg.getAbbreviationName());
        assertEquals(CurationStatus.NEW, savedOrg.getCurationStatus());
        verifyCIData(savedOrg.getPrimaryContactInfo());

        return savedOrg;
    }

    @Test
    public void createPersonWithSpecialities() {

        Address address1 = new Address("a", "b", "c", "d", defaultCountry);
        ContactInfo ci1 = createCI(address1, "ci1");
        Person person = new Person(ci1);
        person.setCurationStatus(CurationStatus.NEW);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci1.setPerson(person);

        Long personId = (Long) PoHibernateUtil.getCurrentSession().save(person);

        Speciality speciality1 = new Speciality("specialityName1", true, true, person);
        PoHibernateUtil.getCurrentSession().save(speciality1);
        person.getSpecialities().add(speciality1);
        Speciality speciality2 = new Speciality("specialityName2", true, true, person);
        PoHibernateUtil.getCurrentSession().save(speciality2);
        person.getSpecialities().add(speciality2);
        Speciality speciality3 = new Speciality("specialityName3", true, true, person);
        PoHibernateUtil.getCurrentSession().save(speciality3);
        person.getSpecialities().add(speciality3);
        Speciality speciality4 = new Speciality("specialityName4", true, true, person);
        PoHibernateUtil.getCurrentSession().save(speciality4);
        person.getSpecialities().add(speciality4);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Person saved = (Person) PoHibernateUtil.getCurrentSession().get(Person.class, personId);

        assertNotNull(saved.getPreferredContactInfo());
        assertEquals(saved.getContactInfos().get(0).getId(), saved.getPreferredContactInfo().getId());
        assertEquals(personId, saved.getContactInfos().get(0).getPerson().getId());
        verifyCIData(saved.getContactInfos().get(0));

        assertEquals(4, saved.getSpecialities().size());
        assertEquals(personId, saved.getSpecialities().get(0).getPerson().getId());
        assertEquals(personId, saved.getSpecialities().get(1).getPerson().getId());
        assertEquals(personId, saved.getSpecialities().get(2).getPerson().getId());
        assertEquals(personId, saved.getSpecialities().get(3).getPerson().getId());
    }

}
