package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ModelUtils {

    private static final Country DEFAULT_COUNTRY;

    static {
        DEFAULT_COUNTRY = new Country("United States", "840", "US", "USA");
    }

    public static Person getBasicPerson() {
        Person person = new Person();
        person.setStatusCode(null);
        person.setFirstName("fName");
        person.setLastName("lName");

        Address a =
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
        a.setDeliveryAddressLine("deliveryAddressLine");
        person.setPostalAddress(a);

        person.getEmail().add(new Email("abc@example.com"));
        person.getEmail().add(new Email("def@example.com"));

        person.getPhone().add(new PhoneNumber("111-111-1111"));
        person.getPhone().add(new PhoneNumber("123-123-1234"));

        person.getFax().add(new PhoneNumber("222-222-2222"));
        person.getFax().add(new PhoneNumber("234-234-2345"));

        person.getTty().add(new PhoneNumber("333-333-3333"));
        person.getTty().add(new PhoneNumber("345-345-3456"));

        person.getUrl().add(new URL("http://www.example.com/abc"));
        person.getUrl().add(new URL("http://www.example.com/def"));

        person.setStatusCode(EntityStatus.ACTIVE);
        return person;
    }

    public static Country getDefaultCountry() {
        return DEFAULT_COUNTRY;
    }

    public static Organization getBasicOrganization() {
        Address mailingAddress =
                new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345", getDefaultCountry());
        Organization org = new Organization();

        org.setPostalAddress(mailingAddress);
        org.setName("oName");
        org.setStatusCode(EntityStatus.ACTIVE);

        Address a =
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
        a.setDeliveryAddressLine("deliveryAddressLine");
        org.setPostalAddress(a);

        org.getEmail().add(new Email("abc@example.com"));
        org.getEmail().add(new Email("def@example.com"));

        org.getPhone().add(new PhoneNumber("111-111-1111"));
        org.getPhone().add(new PhoneNumber("123-123-1234"));

        org.getFax().add(new PhoneNumber("222-222-2222"));
        org.getFax().add(new PhoneNumber("234-234-2345"));

        org.getTty().add(new PhoneNumber("333-333-3333"));
        org.getTty().add(new PhoneNumber("345-345-3456"));

        org.getUrl().add(new URL("http://www.example.com/abc"));
        org.getUrl().add(new URL("http://www.example.com/def"));
        return org;
    }


    public static Address getBasicAddress() {
        return new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
    }


    public static ClinicalResearchStaff getBasicClinicalResearchStaff() {
        ClinicalResearchStaff clinicalResearchStaff = new ClinicalResearchStaff();

        clinicalResearchStaff.setPlayer(getBasicPerson());
        clinicalResearchStaff.setScoper(getBasicOrganization());


        clinicalResearchStaff.getTty().add(new PhoneNumber("333-333-3333"));
        clinicalResearchStaff.getTty().add(new PhoneNumber("345-345-3456"));

        clinicalResearchStaff.getEmail().add(new Email("abc@example.com"));
        clinicalResearchStaff.getEmail().add(new Email("def@example.com"));

        clinicalResearchStaff.getFax().add(new PhoneNumber("333-333-3333"));
        clinicalResearchStaff.getFax().add(new PhoneNumber("345-345-3456"));

        clinicalResearchStaff.getPhone().add(new PhoneNumber("123-456-7890"));
        clinicalResearchStaff.getPostalAddresses().add(
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry())
        );

        clinicalResearchStaff.setStatus(RoleStatus.ACTIVE);
        clinicalResearchStaff.setStatusDate(new Date());
        clinicalResearchStaff.getUrl().add(new URL("http://www.example.org"));

        return clinicalResearchStaff;
    }

    public static HealthCareFacility getBasicHealthCareFacility() {
        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setName("Basic Healthcare Facility");
        healthCareFacility.setPlayer(getBasicOrganization());

        healthCareFacility.getTty().add(new PhoneNumber("333-333-3333"));
        healthCareFacility.getEmail().add(new Email("abc@example.com"));
        healthCareFacility.getFax().add(new PhoneNumber("333-333-3333"));
        healthCareFacility.getPhone().add(new PhoneNumber("123-456-7890"));

        healthCareFacility.getPostalAddresses().add(
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry())
        );

        healthCareFacility.setStatus(RoleStatus.ACTIVE);
        healthCareFacility.setStatusDate(new Date());
        healthCareFacility.getUrl().add(new URL("http://www.example.org"));

        Ii ctepIdentifier = new Ii();
        ctepIdentifier.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepIdentifier.setExtension("CTEP_EXTENSION");
        healthCareFacility.getOtherIdentifiers().add(ctepIdentifier);

        return healthCareFacility;
    }

    public static HealthCareProvider getBasicHealthCareProvider() {
        HealthCareProvider healthCareProvider = new HealthCareProvider();
        healthCareProvider.setPlayer(getBasicPerson());
        healthCareProvider.setScoper(getBasicOrganization());
        healthCareProvider.getTty().add(new PhoneNumber("333-333-3333"));
        healthCareProvider.getEmail().add(new Email("abc@example.com"));
        healthCareProvider.getFax().add(new PhoneNumber("333-333-3333"));
        healthCareProvider.getPhone().add(new PhoneNumber("123-456-7890"));

        healthCareProvider.getPostalAddresses().add(
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry())
        );

        healthCareProvider.setStatus(RoleStatus.ACTIVE);
        healthCareProvider.setStatusDate(new Date());
        healthCareProvider.getUrl().add(new URL("http://www.example.org"));



        return healthCareProvider;
    }
    public static IdentifiedOrganization getBasicIdentifiedOrganization() {
        return getBasicIdentifiedOrganization(getBasicOrganization());
    }

    public static IdentifiedOrganization getBasicIdentifiedOrganization(Organization organization) {
        IdentifiedOrganization identifiedOrganization = new IdentifiedOrganization();

        identifiedOrganization.setPlayer(getBasicOrganization());
        identifiedOrganization.setScoper(getBasicOrganization());
        identifiedOrganization.setAssignedIdentifier(new IdConverter.OrgIdConverter().convertToIi(organization.getId()));
        identifiedOrganization.setStatusDate(new Date());
        identifiedOrganization.setStatus(RoleStatus.PENDING);

        return  identifiedOrganization;
    }

    public static IdentifiedPerson getBasicIdentifiedPerson() {
        return getBasicIdentifiedPerson(getBasicPerson());
    }

    public static IdentifiedPerson getBasicIdentifiedPerson(Person person2) {
        IdentifiedPerson identifiedPerson = new IdentifiedPerson();

        identifiedPerson.setPlayer(getBasicPerson());
        identifiedPerson.setScoper(getBasicOrganization());
        identifiedPerson.setAssignedIdentifier(new IdConverter.OrgIdConverter().convertToIi(person2.getId()));
        identifiedPerson.setStatusDate(new Date());
        identifiedPerson.setStatus(RoleStatus.PENDING);

        return  identifiedPerson;
    }

    public static OrganizationalContactType getBasicOrganizationalContactType() {
        return new OrganizationalContactType("basicOrgContactType");
    }

    public static OrganizationalContact getBasicOrganizationalContact() {
        return getBasicOrganizationalContact(getBasicOrganizationalContactType());
    }

    public static OrganizationalContact getBasicOrganizationalContact(OrganizationalContactType contactType) {
        OrganizationalContact organizationalContact = new OrganizationalContact();

        organizationalContact.setPlayer(getBasicPerson());
        organizationalContact.setScoper(getBasicOrganization());
        organizationalContact.setType(contactType);

        organizationalContact.getTty().add(new PhoneNumber("333-333-3333"));
        organizationalContact.getTty().add(new PhoneNumber("345-345-3456"));

        organizationalContact.getEmail().add(new Email("abc@example.com"));
        organizationalContact.getEmail().add(new Email("def@example.com"));

        organizationalContact.getFax().add(new PhoneNumber("333-333-3333"));
        organizationalContact.getFax().add(new PhoneNumber("345-345-3456"));

        organizationalContact.getPhone().add(new PhoneNumber("123-456-7890"));
        organizationalContact.getPostalAddresses().add(
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry())
        );

        organizationalContact.setStatus(RoleStatus.ACTIVE);
        organizationalContact.setStatusDate(new Date());
        organizationalContact.getUrl().add(new URL("http://www.example.org"));

        return organizationalContact;
    }

    public static OversightCommitteeType getBasicOversightCommitteeType() {
        return new OversightCommitteeType("basicOversightCommitteeType");
    }

    public static OversightCommittee getBasicOversightCommittee() {
        return getBasicOversightCommittee(getBasicOversightCommitteeType());
    }

    public static OversightCommittee getBasicOversightCommittee(OversightCommitteeType defaultType) {
        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setPlayer(getBasicOrganization());
        oversightCommittee.setTypeCode(defaultType);

        oversightCommittee.getTty().add(new PhoneNumber("333-333-3333"));
        oversightCommittee.getEmail().add(new Email("abc@example.com"));
        oversightCommittee.getFax().add(new PhoneNumber("333-333-3333"));
        oversightCommittee.getPhone().add(new PhoneNumber("123-456-7890"));

        oversightCommittee.getPostalAddresses().add(
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry())
        );

        oversightCommittee.setStatus(RoleStatus.ACTIVE);
        oversightCommittee.setStatusDate(new Date());
        oversightCommittee.getUrl().add(new URL("http://www.example.org"));

        Ii ctepIdentifier = new Ii();
        ctepIdentifier.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepIdentifier.setExtension("CTEP_EXTENSION");
        oversightCommittee.getOtherIdentifiers().add(ctepIdentifier);

        return oversightCommittee;
    }

    public static ResearchOrganizationType getBasicResearchOrganizationType() {
        return new ResearchOrganizationType("basicResearchOrganizationType","description");
    }

    public static ResearchOrganization getBasicResearchOrganization() {
        return getBasicResearchOrganization(getBasicResearchOrganizationType());
    }

    public static ResearchOrganization getBasicResearchOrganization(ResearchOrganizationType defaultType) {
        ResearchOrganization researchOrganization = new ResearchOrganization();
        researchOrganization.setPlayer(getBasicOrganization());
        researchOrganization.setTypeCode(defaultType);

        researchOrganization.getTty().add(new PhoneNumber("333-333-3333"));
        researchOrganization.getEmail().add(new Email("abc@example.com"));
        researchOrganization.getFax().add(new PhoneNumber("333-333-3333"));
        researchOrganization.getPhone().add(new PhoneNumber("123-456-7890"));

        researchOrganization.getPostalAddresses().add(
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry())
        );

        researchOrganization.setStatus(RoleStatus.ACTIVE);
        researchOrganization.setStatusDate(new Date());
        researchOrganization.getUrl().add(new URL("http://www.example.org"));

        Ii ctepIdentifier = new Ii();
        ctepIdentifier.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepIdentifier.setExtension("CTEP_EXTENSION");
        researchOrganization.getOtherIdentifiers().add(ctepIdentifier);

        return researchOrganization;
    }

    public static class ContactSpec implements Contactable {

        private List<Email> emails;
        private List<PhoneNumber> faxes;
        private List<PhoneNumber> phoneNumbers;
        private List<URL> urls;
        private List<PhoneNumber> ttys;

        public ContactSpec() {
            emails = new ArrayList<Email>();
            faxes = new ArrayList<PhoneNumber>();
            phoneNumbers = new ArrayList<PhoneNumber>();
            urls = new ArrayList<URL>();
            ttys = new ArrayList<PhoneNumber>();
        }

        @Override
        public List<Email> getEmail() {
            return emails;
        }

        @Override
        public List<PhoneNumber> getFax() {
            return faxes;
        }

        @Override
        public List<PhoneNumber> getPhone() {
            return phoneNumbers;
        }

        @Override
        public List<URL> getUrl() {
            return urls;
        }

        @Override
        public List<PhoneNumber> getTty() {
            return ttys;
        }

        public static ContactSpec newInstance() {
            return new ContactSpec();
        }

        public ContactSpec withEmail(String email) {
            Email emailInstance = new Email(email);
            this.emails.add(emailInstance);
            return this;
        }

        public ContactSpec withFax(String fax) {
            PhoneNumber faxInstance = new PhoneNumber(fax);
            this.faxes.add(faxInstance);
            return this;
        }

        public ContactSpec withPhone(String phoneNumber) {
            PhoneNumber phoneNumberInstance = new PhoneNumber(phoneNumber);
            this.faxes.add(phoneNumberInstance);
            return this;
        }

        public ContactSpec withUrl(String url) {
            URL urlInstance = new URL(url);
            this.urls.add(urlInstance);
            return this;
        }

        public ContactSpec withTty(String tty) {
            PhoneNumber ttyInstance = new PhoneNumber(tty);
            this.faxes.add(ttyInstance);
            return this;
        }

        public DSet<Tel> asDset() {

            Set<Tel> set = new HashSet<Tel>();
            for (Email email : emails) {
                TelEmail val = new TelEmail();
                val.setValue(URI.create("mailto:" + email.getValue()));
                set.add( val );
            }

            for (PhoneNumber phoneNumber : faxes) {
                TelPhone val = new TelPhone();
                val.setValue(URI.create("x-text-fax:" + phoneNumber.getValue()));
                set.add( val );
            }

            for (PhoneNumber phoneNumber : phoneNumbers) {
                TelPhone val = new TelPhone();
                val.setValue(URI.create("tel:" + phoneNumber.getValue()));
                set.add( val );
            }

            for (URL url : urls) {
                TelUrl val = new TelUrl();
                val.setValue(URI.create("file:" + url.getValue()));
                set.add( val );
            }

            for (PhoneNumber tty : ttys) {
                TelPhone val = new TelPhone();
                val.setValue(URI.create("x-text-tel:" + tty.getValue()));
                set.add( val );
            }

            DSet<Tel> result = new DSet<Tel>();
            result.setItem(set);
            return result;
        }

    }
}
