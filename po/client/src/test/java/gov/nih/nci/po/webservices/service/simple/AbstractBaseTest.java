package gov.nih.nci.po.webservices.service.simple;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.FixtureDataUtil;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Alias;
import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.HealthCareProvider;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.types.OrganizationalContact;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.ResearchOrganization;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.After;

/**
 * This is a base class with common code to be used across different testcases.
 * 
 * @author Rohit Gupta
 * 
 */
public abstract class AbstractBaseTest {
    private Connection conn = null;
    private ResultSetHandler<Object[]> h = null;
    protected gov.nih.nci.po.webservices.types.Person person;
    protected gov.nih.nci.po.webservices.types.Organization org;

    public void setUp() throws Exception {
        // setting up gov.nih.nci.po.webservices.types.Person
        person = new Person();
        person.setPrefix("Mr.");
        person.setFirstName("Johny");
        person.setMiddleName("L");
        person.setLastName("Miller");
        person.setSuffix("Sr");
        person.setStatus(EntityStatus.PENDING);

        person.setAddress(getJaxbAddressList().get(0));
        person.getContact().addAll(getJaxbContactList());

        // setting up gov.nih.nci.po.webservices.types.Organization
        org = new Organization();
        org.setName("Mayo Clinic");
        org.setStatus(EntityStatus.PENDING);
        org.setAddress(getJaxbAddressList().get(0));
        org.getContact().addAll(getJaxbContactList());

        // getting the database connection
        conn = DataGeneratorUtil.getJDBCConnection();

        // Create a ResultSetHandler implementation to convert the
        // first row into an Object[].
        h = new ResultSetHandler<Object[]>() {
            public Object[] handle(ResultSet rs) throws SQLException {
                if (!rs.next()) {
                    return null;
                }
                ResultSetMetaData meta = rs.getMetaData();
                int cols = meta.getColumnCount();
                Object[] result = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    result[i] = rs.getObject(i + 1);
                }
                return result;
            }
        };

    }

    @After
    public void cleanup() {
        DbUtils.closeQuietly(this.conn);
    }

    /**
     * This method is used to get list of JAXB Address.
     */
    protected List<gov.nih.nci.po.webservices.types.Address> getJaxbAddressList() {
        List<gov.nih.nci.po.webservices.types.Address> addressList = new ArrayList<Address>();
        gov.nih.nci.po.webservices.types.Address address1 = new Address();
        address1.setLine1("13621 Leagcy Circle");
        address1.setLine2("Apt G");
        address1.setCity("Herndon");
        address1.setStateOrProvince("VA");
        address1.setCountry(CountryISO31661Alpha3Code.USA);
        address1.setPostalcode("20171");

        gov.nih.nci.po.webservices.types.Address address2 = new Address();
        address2.setLine1("200 1st St");
        address2.setLine2("SW # W4");
        address2.setCity("Rochester");
        address2.setStateOrProvince("MN");
        address2.setCountry(CountryISO31661Alpha3Code.USA);
        address2.setPostalcode("55901");

        addressList.add(address1);
        addressList.add(address2);

        return addressList;
    }

    /**
     * This method is used to get list of JAXB Contacts.
     */
    protected List<gov.nih.nci.po.webservices.types.Contact> getJaxbContactList() {
        List<gov.nih.nci.po.webservices.types.Contact> contactList = new ArrayList<Contact>();

        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue("my.email@mayoclinic.org");

        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("571-456-1245");

        Contact faxContact = new Contact();
        faxContact.setType(ContactType.FAX);
        faxContact.setValue("571-456-1278");

        Contact ttyContact = new Contact();
        ttyContact.setType(ContactType.TTY);
        ttyContact.setValue("571-123-1123");

        Contact urlContact = new Contact();
        urlContact.setType(ContactType.URL);
        urlContact.setValue("http://www.mayoclinic.org");

        contactList.add(emailContact);
        contactList.add(phoneContact);
        contactList.add(faxContact);
        contactList.add(ttyContact);
        contactList.add(urlContact);
        return contactList;
    }

    /**
     * This method is used to get UPDATED list of JAXB Contacts.
     */
    protected List<gov.nih.nci.po.webservices.types.Contact> getJaxbUpdatedContactList() {
        List<gov.nih.nci.po.webservices.types.Contact> contactList = new ArrayList<Contact>();

        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue("my.updated.email@mayoclinic.org");

        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("314-213-1245");

        Contact faxContact = new Contact();
        faxContact.setType(ContactType.FAX);
        faxContact.setValue("314-213-1278");

        Contact ttyContact = new Contact();
        ttyContact.setType(ContactType.TTY);
        ttyContact.setValue("314-213-1123");

        Contact urlContact = new Contact();
        urlContact.setType(ContactType.URL);
        urlContact.setValue("http://www.updatedmayoclinic.org");

        contactList.add(emailContact);
        contactList.add(phoneContact);
        contactList.add(faxContact);
        contactList.add(ttyContact);
        contactList.add(urlContact);
        return contactList;
    }

    protected void checkPersonDetails(
            gov.nih.nci.po.webservices.types.Person reqPerson,
            gov.nih.nci.po.webservices.types.Person resPerson, boolean isUpdate) {
        try {

            QueryRunner queryRunner = new QueryRunner();
            // get the person/address from the database
            Object[] result = queryRunner
                    .query(conn,
                            "SELECT  person.prefix,  person.firstname, person.lastname, person.suffix, person.status, "
                                    + "address.streetaddressline,  address.deliveryaddressline,address.cityormunicipality, "
                                    + "address.postalcode, address.stateorprovince, country.alpha3 "
                                    + "FROM  address,  person, country WHERE address.country_id = country.id AND "
                                    + "person.postal_address_id = address.id AND person.id=?",
                            h, resPerson.getId());

            Assert.assertEquals(reqPerson.getPrefix(), resPerson.getPrefix());
            Assert.assertEquals(reqPerson.getPrefix(), result[0]);
            Assert.assertEquals(reqPerson.getFirstName(),
                    resPerson.getFirstName());
            Assert.assertEquals(reqPerson.getFirstName(), result[1]);
            Assert.assertEquals(reqPerson.getLastName(),
                    resPerson.getLastName());
            Assert.assertEquals(reqPerson.getLastName(), result[2]);
            Assert.assertEquals(reqPerson.getSuffix(), resPerson.getSuffix());
            Assert.assertEquals(reqPerson.getSuffix(), result[3]);
            Assert.assertEquals(reqPerson.getStatus().value(), resPerson
                    .getStatus().value());
            Assert.assertEquals(reqPerson.getStatus().value(), result[4]);
            Assert.assertEquals(reqPerson.getAddress().getLine1(), resPerson
                    .getAddress().getLine1());
            Assert.assertEquals(reqPerson.getAddress().getLine1(), result[5]);
            Assert.assertEquals(reqPerson.getAddress().getLine2(), resPerson
                    .getAddress().getLine2());
            Assert.assertEquals(reqPerson.getAddress().getLine2(), result[6]);
            Assert.assertEquals(reqPerson.getAddress().getCity(), resPerson
                    .getAddress().getCity());
            Assert.assertEquals(reqPerson.getAddress().getCity(), result[7]);
            Assert.assertEquals(reqPerson.getAddress().getPostalcode(),
                    resPerson.getAddress().getPostalcode());
            Assert.assertEquals(reqPerson.getAddress().getPostalcode(),
                    result[8]);
            Assert.assertEquals(reqPerson.getAddress().getStateOrProvince(),
                    resPerson.getAddress().getStateOrProvince());
            Assert.assertEquals(reqPerson.getAddress().getStateOrProvince(),
                    result[9]);
            Assert.assertEquals(reqPerson.getAddress().getCountry().value(),
                    resPerson.getAddress().getCountry().value());
            Assert.assertEquals(reqPerson.getAddress().getCountry().value(),
                    result[10]);

            if (isUpdate) {
                // check for the UPDATED contact details
                checkPersonContactDetails(reqPerson, resPerson,
                        "my.updated.email@mayoclinic.org", "314-213-1245",
                        "314-213-1278", "314-213-1123",
                        "http://www.updatedmayoclinic.org");
            } else {
                checkPersonContactDetails(reqPerson, resPerson,
                        "my.email@mayoclinic.org", "571-456-1245",
                        "571-456-1278", "571-123-1123",
                        "http://www.mayoclinic.org");
            }
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkPersonDetails. The exception is: "
                    + e);
        }
    }

    protected void checkPersonContactDetails(
            gov.nih.nci.po.webservices.types.Person reqPerson,
            gov.nih.nci.po.webservices.types.Person resPerson, String email,
            String phone, String fax, String tty, String url) {
        try {
            // Asserts for contact details in returned person object
            for (Contact contact : resPerson.getContact()) {
                if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                    Assert.assertEquals(email, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                    Assert.assertEquals(phone, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                    Assert.assertEquals(fax, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                    Assert.assertEquals(tty, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                    Assert.assertEquals(url, contact.getValue());
                }
            }

            // check for contact details(email) in DB
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from person_email where person_id=?)",
                            h, resPerson.getId());
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from person_phone where person_id=?)",
                            h, resPerson.getId());
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from person_fax where person_id=?)",
                            h, resPerson.getId());
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from person_tty where person_id=?)",
                            h, resPerson.getId());
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from person_url where person_id=?)",
                            h, resPerson.getId());
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkPersonContactDetails. The exception is: "
                    + e);
        }
    }

    protected void checkPersonRoleAddressDetails(PersonRole reqRole,
            PersonRole resRole) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = null;

            if (reqRole instanceof HealthCareProvider) {
                // get the HCP Address from database & use in Assert check
                result = queryRunner
                        .query(conn,
                                "SELECT  address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, address.postalcode, address.stateorprovince, country.alpha3 FROM hcp_address, address, country WHERE  hcp_address.address_id = address.id AND  address.country_id = country.id AND hcp_address.hcp_id = ?",
                                h, resRole.getId());
            } else if (reqRole instanceof OrganizationalContact) {
                // get the OC Address from database & use in Assert check
                result = queryRunner
                        .query(conn,
                                "SELECT  address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, address.postalcode, address.stateorprovince, country.alpha3 FROM orgcontact_address, address, country WHERE  orgcontact_address.address_id = address.id AND  address.country_id = country.id AND orgcontact_address.orgcontact_id = ? ",
                                h, resRole.getId());
            } else if (reqRole instanceof ClinicalResearchStaff) {
                // get the CRS Address from database & use in Assert check
                result = queryRunner
                        .query(conn,
                                "SELECT  address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, address.postalcode, address.stateorprovince, country.alpha3 FROM crs_address, address, country WHERE  crs_address.address_id = address.id AND  address.country_id = country.id AND crs_address.crs_id = ? ",
                                h, resRole.getId());
            }

            Assert.assertEquals(reqRole.getAddress().get(0).getLine1(), resRole
                    .getAddress().get(0).getLine1());
            Assert.assertEquals(reqRole.getAddress().get(0).getLine1(),
                    result[0]);

            Assert.assertEquals(reqRole.getAddress().get(0).getLine2(), resRole
                    .getAddress().get(0).getLine2());
            Assert.assertEquals(reqRole.getAddress().get(0).getLine2(),
                    result[1]);

            Assert.assertEquals(reqRole.getAddress().get(0).getCity(), resRole
                    .getAddress().get(0).getCity());
            Assert.assertEquals(reqRole.getAddress().get(0).getCity(),
                    result[2]);

            Assert.assertEquals(reqRole.getAddress().get(0).getPostalcode(),
                    resRole.getAddress().get(0).getPostalcode());
            Assert.assertEquals(reqRole.getAddress().get(0).getPostalcode(),
                    result[3]);

            Assert.assertEquals(reqRole.getAddress().get(0)
                    .getStateOrProvince(), resRole.getAddress().get(0)
                    .getStateOrProvince());
            Assert.assertEquals(reqRole.getAddress().get(0)
                    .getStateOrProvince(), result[4]);

            Assert.assertEquals(reqRole.getAddress().get(0).getCountry()
                    .value(), resRole.getAddress().get(0).getCountry().value());
            Assert.assertEquals(reqRole.getAddress().get(0).getCountry()
                    .value(), result[5]);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkPersonRoleAddressDetails. The exception is: "
                    + e);
        }
    }

    protected void checkPersonRoleContactDetails(PersonRole reqRole,
            PersonRole resRole, String email, String phone, String fax,
            String tty, String url) {
        // Asserts for contact details in returned PersonRole object
        for (Contact contact : resRole.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(email, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(phone, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(fax, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(tty, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(url, contact.getValue());
            }
        }

        if (reqRole instanceof HealthCareProvider) {
            checkHCPContactInDB(resRole.getId(), email, phone, fax, tty, url);
        } else if (reqRole instanceof OrganizationalContact) {
            checkOrgContactInDB(resRole.getId(), email, phone, fax, tty, url);
        } else if (reqRole instanceof ClinicalResearchStaff) {
            checkCRSContactInDB(resRole.getId(), email, phone, fax, tty, url);
        }
    }

    protected void checkHCPContactInDB(long id, String email, String phone,
            String fax, String tty, String url) {
        try {
            QueryRunner queryRunner = null;
            Object[] result = null;

            // check for contact details(email) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from hcp_email where hcp_id=?)",
                            h, id);
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from hcp_phone where hcp_id=?)",
                            h, id);
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from hcp_fax where hcp_id=?)",
                            h, id);
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from hcp_tty where hcp_id=?)",
                            h, id);
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url) in DB
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from hcp_url where hcp_id=?)",
                            h, id);
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkHCPContactInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOrgContactInDB(long id, String email, String phone,
            String fax, String tty, String url) {
        try {
            QueryRunner queryRunner = null;
            Object[] result = null;

            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from orgcontact_email where orgcontact_id=?)",
                            h, id);
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from orgcontact_phone where orgcontact_id=?)",
                            h, id);
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from orgcontact_fax where orgcontact_id=?)",
                            h, id);
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from orgcontact_tty where orgcontact_id=?)",
                            h, id);
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from orgcontact_url where orgcontact_id=?)",
                            h, id);
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrgContactInDB. The exception is: "
                    + e);
        }
    }

    protected void checkCRSContactInDB(long id, String email, String phone,
            String fax, String tty, String url) {
        try {
            QueryRunner queryRunner = null;
            Object[] result = null;

            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from crs_email where crs_id=?)",
                            h, id);
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from crs_phone where crs_id=?)",
                            h, id);
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from crs_fax where crs_id=?)",
                            h, id);
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from crs_tty where crs_id=?)",
                            h, id);
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from crs_url where crs_id=?)",
                            h, id);
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkCRSContactInDB. The exception is: "
                    + e);
        }
    }

    protected void checkPersonStatusInDB(long personId, String expStatus) {
        try {
            // check for person status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from person where id=?", h, personId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkPersonStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkHCPStatusInDB(long perRoleId, String expStatus) {
        try {
            // check for person status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from healthcareprovider where id=?", h,
                    perRoleId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkHCPStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOrgContactStatusInDB(long perRoleId, String expStatus) {
        try {
            // check for person status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from organizationalcontact where id=?", h,
                    perRoleId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOCStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOrganizationDetails(String orgName,
            gov.nih.nci.po.webservices.types.Organization reqOrg,
            gov.nih.nci.po.webservices.types.Organization resOrg, String email,
            String phone, String fax, String tty, String url) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            // get the organization/address from database & use in Assert check
            Object[] result = queryRunner
                    .query(conn,
                            "SELECT organization.name, organization.status, address.streetaddressline, address.deliveryaddressline, "
                                    + "address.cityormunicipality, address.postalcode, address.stateorprovince, "
                                    + "country.alpha3 FROM organization, address, country "
                                    + "WHERE organization.postal_address_id = address.id AND address.country_id = country.id AND organization.id=?",
                            h, resOrg.getId());
            // name doesn't change, so take it as method param
            Assert.assertEquals(orgName, resOrg.getName());
            Assert.assertEquals(orgName, result[0]);

            Assert.assertEquals(reqOrg.getStatus().value(), resOrg.getStatus()
                    .value());
            Assert.assertEquals(reqOrg.getStatus().value(), result[1]);

            Assert.assertEquals(reqOrg.getAddress().getLine1(), resOrg
                    .getAddress().getLine1());
            Assert.assertEquals(reqOrg.getAddress().getLine1(), result[2]);

            Assert.assertEquals(reqOrg.getAddress().getLine2(), resOrg
                    .getAddress().getLine2());
            Assert.assertEquals(reqOrg.getAddress().getLine2(), result[3]);

            Assert.assertEquals(reqOrg.getAddress().getCity(), resOrg
                    .getAddress().getCity());
            Assert.assertEquals(reqOrg.getAddress().getCity(), result[4]);

            Assert.assertEquals(reqOrg.getAddress().getPostalcode(), resOrg
                    .getAddress().getPostalcode());
            Assert.assertEquals(reqOrg.getAddress().getPostalcode(), result[5]);

            Assert.assertEquals(reqOrg.getAddress().getStateOrProvince(),
                    resOrg.getAddress().getStateOrProvince());
            Assert.assertEquals(reqOrg.getAddress().getStateOrProvince(),
                    result[6]);

            Assert.assertEquals(reqOrg.getAddress().getCountry().value(),
                    resOrg.getAddress().getCountry().value());
            Assert.assertEquals(reqOrg.getAddress().getCountry().value(),
                    result[7]);
            Assert.assertEquals(reqOrg.getCtepId(), resOrg.getCtepId());
            
            // Asserts for contact details in returned organization object
            for (Contact contact : resOrg.getContact()) {
                if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                    Assert.assertEquals(email, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                    Assert.assertEquals(phone, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                    Assert.assertEquals(fax, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                    Assert.assertEquals(tty, contact.getValue());
                } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                    Assert.assertEquals(url, contact.getValue());
                }
            }

            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from organization_email where organization_id=?)",
                            h, resOrg.getId());
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from organization_phone where organization_id=?)",
                            h, resOrg.getId());
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from organization_fax where organization_id=?)",
                            h, resOrg.getId());
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from organization_tty where organization_id=?)",
                            h, resOrg.getId());
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from organization_url where organization_id=?)",
                            h, resOrg.getId());
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrganizationDetails. The exception is: "
                    + e);
        }
    }
    
    protected void checkOrganizationCRDetails(String orgUpName,
            gov.nih.nci.po.webservices.types.Organization reqOrg,
            String email, String phone, String fax, String tty, String url) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            
            // get the OrgCR Id from the database for the given orgId
            Object[] result = queryRunner.query(conn,"select id from organizationcr where target =?", h, reqOrg.getId());
            long crId = ((Long) result[0]).longValue();
            
            // get the organizationCR/address from database & use in Assert check
            queryRunner = new QueryRunner();                        
            result = queryRunner.query(conn,
                            "SELECT organizationcr.name, organizationcr.status, address.streetaddressline, address.deliveryaddressline, "
                                    + "address.cityormunicipality, address.postalcode, address.stateorprovince, "
                                    + "country.alpha3 FROM organizationcr, address, country "
                                    + "WHERE organizationcr.postal_address_id = address.id AND address.country_id = country.id AND organizationcr.id=?",
                            h, crId);
            
            assertEquals(orgUpName, result[0]);            
            assertEquals(reqOrg.getStatus().value(), result[1]);
            assertEquals(reqOrg.getAddress().getLine1(), result[2]);
            assertEquals(reqOrg.getAddress().getLine2(), result[3]);
            assertEquals(reqOrg.getAddress().getCity(), result[4]);
            assertEquals(reqOrg.getAddress().getPostalcode(), result[5]);
            assertEquals(reqOrg.getAddress().getStateOrProvince(),result[6]);
            assertEquals(reqOrg.getAddress().getCountry().value(), result[7]);            
            
            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from email where id=(select email_id from org_cr_email where org_cr_id=?)", h, crId);
            assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from phonenumber where id=(select phone_id from org_cr_phone where org_cr_id=?)",h, crId);
            assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from phonenumber where id=(select fax_id from org_cr_fax where org_cr_id=?)",h, crId);
            assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from phonenumber where id=(select tty_id from org_cr_tty where org_cr_id=?)",h, crId);
            assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from url where id=(select url_id from org_cr_url where org_cr_id=?)",h, crId);
            assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrganizationCRDetails. The exception is: "+ e);
        }
    }

    protected void checkOrgAliases(String orgName, OrganizationSearchResult osr) {
        try {
            // Use the BeanListHandler implementation to convert all
            // ResultSet rows into a List of String.
            ResultSetHandler<List<Alias>> hl = new BeanListHandler<Alias>(
                    Alias.class);

            // Asserts for OrgName in returned organization name or alias list
            boolean isAliasPresent = false;
            if (osr.getOrganizationName().equalsIgnoreCase(orgName)
                    || isJaxbAliasListContainsName(osr.getAlias(), orgName)) {
                isAliasPresent = true;
            }

            if (!isAliasPresent) {
                Assert.fail("Org Alias is not present in the OrgName or OrgAlias.");
            }

            // check for Alias in the DB
            isAliasPresent = false;
            QueryRunner queryRunner = new QueryRunner();
            List<Alias> aliases = queryRunner
                    .query(conn,
                            "select value from alias where id in (select alias_id from organization_alias where organization_id=?)",
                            hl, osr.getId());
            for (int i = 0; i < aliases.size(); i++) {
                if (aliases.get(i).getValue().equals(orgName)) {
                    isAliasPresent = true;
                    break;
                }
            }
            if (!isAliasPresent) {
                Assert.fail("Org Alias is not present in the DB-OrgName or OrgAlias.");
            }

        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrgAliases. The exception is: "
                    + e);
        }
    }

    private boolean isJaxbAliasListContainsName(
            List<gov.nih.nci.po.webservices.types.Alias> aliasList, String str) {
        for (gov.nih.nci.po.webservices.types.Alias alias : aliasList) {
            if (alias.getValue().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    protected void checkOrgRoleAliases(String orgRoleName,
            OrganizationRole orgRol) {
        try {
            // Use the BeanListHandler implementation to convert all
            // ResultSet rows into a List of String.
            ResultSetHandler<List<Alias>> hl = new BeanListHandler<Alias>(
                    Alias.class);

            // Asserts for OrgName in returned organization name or alias list
            boolean isAliasPresent = false;

            // check for Alias in the DB
            isAliasPresent = false;
            QueryRunner queryRunner = new QueryRunner();
            List<Alias> aliases = null;
            if (orgRol instanceof HealthCareFacility) {
                aliases = queryRunner
                        .query(conn,
                                "select value from alias where id in (select alias_id from hcf_alias where hcf_id=?)",
                                hl, orgRol.getId());
            } else if (orgRol instanceof ResearchOrganization) {
                aliases = queryRunner
                        .query(conn,
                                "select value from alias where id in (select alias_id from ro_alias where ro_id=?)",
                                hl, orgRol.getId());
            }

            for (int i = 0; i < aliases.size(); i++) {
                if (aliases.get(i).getValue().equals(orgRoleName)) {
                    isAliasPresent = true;
                    break;
                }
            }
            if (!isAliasPresent) {
                Assert.fail("Org Alias is not present in the DB-OrgRoleName or OrgRoleAlias.");
            }

        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrgRoleAliases. The exception is: "
                    + e);
        }
    }

    protected void checkCRSStatusInDB(long perRoleId, String expStatus) {
        try {
            // check for person status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from clinicalresearchstaff where id=?", h,
                    perRoleId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkCRSStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOrgRoleAddressDetails(OrganizationRole reqRole,
            OrganizationRole resRole) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = null;

            if (reqRole instanceof HealthCareFacility) {
                // get the HCF Address from database & use in Assert check
                result = queryRunner
                        .query(conn,
                                "SELECT  address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, "
                                        + " address.postalcode, address.stateorprovince, country.alpha3 FROM hcf_address, address, country "
                                        + "WHERE  hcf_address.address_id = address.id AND  address.country_id = country.id AND hcf_address.hcf_id = ?",
                                h, resRole.getId());
            } else if (reqRole instanceof OversightCommittee) {
                // get the OC Address from database & use in Assert check
                result = queryRunner
                        .query(conn,
                                "SELECT  address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, "
                                        + "address.postalcode, address.stateorprovince, country.alpha3 FROM oc_address, address, country "
                                        + "WHERE  oc_address.address_id = address.id AND  address.country_id = country.id AND oc_address.oc_id = ?",
                                h, resRole.getId());
            } else if (reqRole instanceof ResearchOrganization) {
                // get the RO Address from database & use in Assert check
                result = queryRunner
                        .query(conn,
                                "SELECT  address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, "
                                        + "address.postalcode, address.stateorprovince, country.alpha3 FROM ro_address, address, country "
                                        + "WHERE  ro_address.address_id = address.id AND  address.country_id = country.id AND ro_address.ro_id = ?",
                                h, resRole.getId());
            }

            Assert.assertEquals(reqRole.getAddress().get(0).getLine1(), resRole
                    .getAddress().get(0).getLine1());
            Assert.assertEquals(reqRole.getAddress().get(0).getLine1(),
                    result[0]);

            Assert.assertEquals(reqRole.getAddress().get(0).getLine2(), resRole
                    .getAddress().get(0).getLine2());
            Assert.assertEquals(reqRole.getAddress().get(0).getLine2(),
                    result[1]);

            Assert.assertEquals(reqRole.getAddress().get(0).getCity(), resRole
                    .getAddress().get(0).getCity());
            Assert.assertEquals(reqRole.getAddress().get(0).getCity(),
                    result[2]);

            Assert.assertEquals(reqRole.getAddress().get(0).getPostalcode(),
                    resRole.getAddress().get(0).getPostalcode());
            Assert.assertEquals(reqRole.getAddress().get(0).getPostalcode(),
                    result[3]);

            Assert.assertEquals(reqRole.getAddress().get(0)
                    .getStateOrProvince(), resRole.getAddress().get(0)
                    .getStateOrProvince());
            Assert.assertEquals(reqRole.getAddress().get(0)
                    .getStateOrProvince(), result[4]);

            Assert.assertEquals(reqRole.getAddress().get(0).getCountry()
                    .value(), resRole.getAddress().get(0).getCountry().value());
            Assert.assertEquals(reqRole.getAddress().get(0).getCountry()
                    .value(), result[5]);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrgRoleAddressDetails. The exception is: "
                    + e);
        }
    }

    protected void checkOrgRoleContactDetails(OrganizationRole reqRole,
            OrganizationRole resRole, String email, String phone, String fax,
            String tty, String url) {
        // Asserts for contact details in returned org role object
        for (Contact contact : resRole.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(email, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(phone, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(fax, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(tty, contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(url, contact.getValue());
            }
        }

        if (reqRole instanceof HealthCareFacility) {
            checkHCFContactInDB(resRole.getId(), email, phone, fax, tty, url);
        } else if (reqRole instanceof OversightCommittee) {
            checkOCContactInDB(resRole.getId(), email, phone, fax, tty, url);
        } else if (reqRole instanceof ResearchOrganization) {
            checkROContactInDB(resRole.getId(), email, phone, fax, tty, url);
        }
    }

    protected void checkHCFContactInDB(long id, String email, String phone,
            String fax, String tty, String url) {
        try {
            QueryRunner queryRunner = null;
            Object[] result = null;

            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from hcf_email where hcf_id=?)",
                            h, id);
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from hcf_phone where hcf_id=?)",
                            h, id);
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from hcf_fax where hcf_id=?)",
                            h, id);
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from hcf_tty where hcf_id=?)",
                            h, id);
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from hcf_url where hcf_id=?)",
                            h, id);
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkHCFContactInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOCContactInDB(long id, String email, String phone,
            String fax, String tty, String url) {
        try {

            QueryRunner queryRunner = null;
            Object[] result = null;

            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from oc_email where oc_id=?)",
                            h, id);
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from oc_phone where oc_id=?)",
                            h, id);
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from oc_fax where oc_id=?)",
                            h, id);
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from oc_tty where oc_id=?)",
                            h, id);
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from oc_url where oc_id=?)",
                            h, id);
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOCContactInDB. The exception is: "
                    + e);
        }
    }

    protected void checkROContactInDB(long id, String email, String phone,
            String fax, String tty, String url) {
        try {

            QueryRunner queryRunner = null;
            Object[] result = null;

            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from email where id=(select email_id from ro_email where ro_id=?)",
                            h, id);
            Assert.assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select phone_id from ro_phone where ro_id=?)",
                            h, id);
            Assert.assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select fax_id from ro_fax where ro_id=?)",
                            h, id);
            Assert.assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from phonenumber where id=(select tty_id from ro_tty where ro_id=?)",
                            h, id);
            Assert.assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner
                    .query(conn,
                            "select value from url where id=(select url_id from ro_url where ro_id=?)",
                            h, id);
            Assert.assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkROContactInDB. The exception is: "
                    + e);
        }
    }

    protected void checkHCFStatusInDB(long orgId, String expStatus) {
        try {
            // check for HCF status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from healthcarefacility where id=?", h,
                    orgId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkHCFStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOverCommStatusInDB(long orgId, String expStatus) {
        try {
            // check for HCF status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from oversightcommittee where id=?", h,
                    orgId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOCStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkROStatusInDB(long orgId, String expStatus) {
        try {
            // check for HCF status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from researchorganization where id=?", h,
                    orgId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkROStatusInDB. The exception is: "
                    + e);
        }
    }

    protected void checkOrgStatusInDB(long orgId, String expStatus) {
        try {
            // check for organization status
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select status from organization where id=?", h, orgId);
            Assert.assertEquals(expStatus, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkOrgStatusInDB. The exception is: "
                    + e);
        }
    }
    
    protected void checkHCFChangeRequestDetails(String rolUpName, OrganizationRole reqRole,
                        String email, String phone, String fax,
                        String tty, String url) {
      
        try {
            QueryRunner queryRunner = new QueryRunner();
            
            // get the HCF-CR Id from the database for the given hcfId
            Object[] result = queryRunner.query(conn,"select id from healthcarefacilitycr where target =?", h, reqRole.getId());
            long crId = ((Long) result[0]).longValue();
            
            // get the hcfCR from database & use in Assert check
            queryRunner = new QueryRunner();                        
            result = queryRunner.query(conn, "SELECT name, status from healthcarefacilitycr where id=?", h, crId);            
            assertEquals(rolUpName, result[0]);   // check the name    
            if ("INACTIVE".equals(reqRole.getStatus().value())) {
                assertEquals("SUSPENDED", result[1]); // check status    
            }            
            
            // get the hcfCR address from database & use in Assert check
            queryRunner = new QueryRunner();                        
            result = queryRunner.query(conn,
                            "SELECT address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, address.postalcode, "
                            + "address.stateorprovince, country.alpha3 FROM hcfcr_address, address, country "
                             + "WHERE hcfcr_address.address_id = address.id AND address.country_id = country.id AND hcfcr_address.hcfcr_id=?",
                             h, crId);           
            assertEquals(reqRole.getAddress().get(0).getLine1(), result[0]);
            assertEquals(reqRole.getAddress().get(0).getLine2(), result[1]);
            assertEquals(reqRole.getAddress().get(0).getCity(), result[2]);
            assertEquals(reqRole.getAddress().get(0).getPostalcode(), result[3]);
            assertEquals(reqRole.getAddress().get(0).getStateOrProvince(),result[4]);
            assertEquals(reqRole.getAddress().get(0).getCountry().value(), result[5]);
            
            // check for contact details(email)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from email where id=(select email_id from hcfcr_email where hcfcr_id=?)", h, crId);
            assertEquals(email, result[0]);

            // check for contact details(phone)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from phonenumber where id=(select phone_id from hcfcr_phone where hcfcr_id=?)",h, crId);
            assertEquals(phone, result[0]);

            // check for contact details(fax)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from phonenumber where id=(select fax_id from hcfcr_fax where hcfcr_id=?)",h, crId);
            assertEquals(fax, result[0]);

            // check for contact details(tty)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from phonenumber where id=(select tty_id from hcfcr_tty where hcfcr_id=?)",h, crId);
            assertEquals(tty, result[0]);

            // check for contact details(url)
            queryRunner = new QueryRunner();
            result = queryRunner.query(conn,
                            "select value from url where id=(select url_id from hcfcr_url where hcfcr_id=?)",h, crId);
            assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkHCFChangeRequestDetails for id: "+ reqRole.getId()+". The exception is: "+ e);
        }
    }
    

    protected void checkROChangeRequestDetails(String rolUpName, OrganizationRole reqRole,
            String email, String phone, String fax,
            String tty, String url) {

        try {
        QueryRunner queryRunner = new QueryRunner();
        
        // get the RO-CR Id from the database for the given hcfId
        Object[] result = queryRunner.query(conn,"select id from researchorganizationcr where target =?", h, reqRole.getId());
        long crId = ((Long) result[0]).longValue();
        
        // get the roCR from database & use in Assert check
        queryRunner = new QueryRunner();                        
        result = queryRunner.query(conn, "SELECT name, status from researchorganizationcr where id=?", h, crId);            
        assertEquals(rolUpName, result[0]);   // check the name      
        if ("INACTIVE".equals(reqRole.getStatus().value())) {
            assertEquals("SUSPENDED", result[1]); // check status    
        }       
        
        // get the roCR address from database & use in Assert check
        queryRunner = new QueryRunner();                        
        result = queryRunner.query(conn,
                        "SELECT address.streetaddressline, address.deliveryaddressline, address.cityormunicipality, address.postalcode, "
                        + "address.stateorprovince, country.alpha3 FROM rocr_address, address, country "
                         + "WHERE rocr_address.address_id = address.id AND address.country_id = country.id AND rocr_address.rocr_id=?",
                         h, crId);           
        assertEquals(reqRole.getAddress().get(0).getLine1(), result[0]);
        assertEquals(reqRole.getAddress().get(0).getLine2(), result[1]);
        assertEquals(reqRole.getAddress().get(0).getCity(), result[2]);
        assertEquals(reqRole.getAddress().get(0).getPostalcode(), result[3]);
        assertEquals(reqRole.getAddress().get(0).getStateOrProvince(),result[4]);
        assertEquals(reqRole.getAddress().get(0).getCountry().value(), result[5]);
        
        // check for contact details(email)
        queryRunner = new QueryRunner();
        result = queryRunner.query(conn,
                        "select value from email where id=(select email_id from rocr_email where rocr_id=?)", h, crId);
        assertEquals(email, result[0]);
        
        // check for contact details(phone)
        queryRunner = new QueryRunner();
        result = queryRunner.query(conn,
                        "select value from phonenumber where id=(select phone_id from rocr_phone where rocr_id=?)",h, crId);
        assertEquals(phone, result[0]);
        
        // check for contact details(fax)
        queryRunner = new QueryRunner();
        result = queryRunner.query(conn,
                        "select value from phonenumber where id=(select fax_id from rocr_fax where rocr_id=?)",h, crId);
        assertEquals(fax, result[0]);
        
        // check for contact details(tty)
        queryRunner = new QueryRunner();
        result = queryRunner.query(conn,
                        "select value from phonenumber where id=(select tty_id from rocr_tty where rocr_id=?)",h, crId);
        assertEquals(tty, result[0]);
        
        // check for contact details(url)
        queryRunner = new QueryRunner();
        result = queryRunner.query(conn,
                        "select value from url where id=(select url_id from rocr_url where rocr_id=?)",h, crId);
        assertEquals(url, result[0]);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside checkROChangeRequestDetails for id: "+ reqRole.getId()+". The exception is: "+ e);
        }
    }    
    
    protected void createIdentifiedPerson(long personId, String ctepId) {
        try {
            QueryRunner queryRunner = new QueryRunner();
                       
            Object[] result = queryRunner.query(conn,
                    "select id from organization where name = 'Cancer Therapy Evaluation Program'", h);
            long ctepOrgId = ((Long) result[0]).longValue();
            
            // insert data into "identifiedperson" table
            long idenOrgId = FixtureDataUtil.getNextSequenceId(conn, h);
            queryRunner.update(conn,
                    "insert into identifiedperson(id,assigned_identifier_extension,assigned_identifier_identifier_name,assigned_identifier_root,status,scoper_id,player_id) values(?, ?, 'Identified person identifier','2.16.840.1.113883.3.26.6.1','ACTIVE',?,?)",idenOrgId, ctepId, ctepOrgId, personId);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createIdentifiedPerson. The exception is: " + e);
        }
    }

    protected int getReponseCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    protected String getResponseContentType(HttpResponse response) {
        return EntityUtils.getContentMimeType(response.getEntity());
    }

    protected String getResponseMessage(HttpResponse response)
            throws ParseException, IOException {
        return EntityUtils.toString(response.getEntity());
    }

    public void tearDown() {
        DbUtils.closeQuietly(conn);
    }
}
