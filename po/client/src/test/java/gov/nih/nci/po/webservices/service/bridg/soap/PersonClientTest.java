package gov.nih.nci.po.webservices.service.bridg.soap;

import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.enterpriseservices.entities.person.CreateRequest;
import gov.nih.nci.enterpriseservices.entities.person.CreateResponse;
import gov.nih.nci.enterpriseservices.entities.person.GetByIdRequest;
import gov.nih.nci.enterpriseservices.entities.person.GetByIdResponse;
import gov.nih.nci.enterpriseservices.entities.person.QueryRequest;
import gov.nih.nci.enterpriseservices.entities.person.QueryResponse;
import gov.nih.nci.enterpriseservices.entities.person.UpdateRequest;
import gov.nih.nci.enterpriseservices.entities.person.UpdateResponse;
import gov.nih.nci.enterpriseservices.entities.person.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.entities.person.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.entities.person.ValidateRequest;
import gov.nih.nci.enterpriseservices.entities.person.ValidateResponse;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.PersonSex;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.webservices.service.bridg.soap.person.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.person.NullifiedEntityFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.person.PersonPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.person.PersonService;
import gov.nih.nci.po.webservices.service.bridg.soap.person.TooManyResultsFaultFaultMessage;
import gov.nih.nci.po.webservices.service.utils.AuthUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.iso._21090.CD;
import org.iso._21090.DSETTEL;
import org.iso._21090.ENPN;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.TELPhone;
import org.junit.Before;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class PersonClientTest {

    private static final Country USA;

    static {
        USA = new Country("United States", "840", "US", "USA");
    }

    PersonPortType port;

    @Before
    public void setup() throws MalformedURLException {
        QName serviceName = new QName(
                "http://enterpriseservices.nci.nih.gov/entities/Person",
                "PersonService");
        URL url = new URL(getServiceUrlString());

        AuthUtils.addBasicAuthSupport(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        PersonService service = new PersonService(url,
                serviceName);

        AuthUtils.removeBasicAuthSupport();

        port = service.getPersonPortTypePort();

        Binding binding = ((BindingProvider)port).getBinding();

        AuthUtils.addWsSecurityUTSupport(
                binding,
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );


    }


    public static String getServiceUrlString() {
        return "http://" + TstProperties.getServerHostname() + ":"
                + TstProperties.getServerPort()
                + "/po-webservices/services/bridg/PersonService?wsdl";
    }

    @Test
    public void testCreateWithoutAuthentication() throws EntityValidationFaultFaultMessage {
        CreateRequest request = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setPerson(builder.build());

        request.setPerson(payloadWrapper);
        try{
            AuthUtils.addWsSecurityUTSupport(
                    ((BindingProvider)port).getBinding(),
                    "bogusUser",
                    "bogusPassword"
            );

            port.create(request);
            fail();
        } catch (WebServiceException wse){
            assertEquals("The server sent HTTP status code 401: Unauthorized", wse.getMessage().trim());
        }
    }

    @Test
    public void testCreate() throws EntityValidationFaultFaultMessage {
        CreateRequest request = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setPerson(builder.build());

        request.setPerson(payloadWrapper);
        CreateResponse response = port.create(request);
        assertNotNull(Long.parseLong(response.getId().getExtension()));
    }

    @Test
    public void testCreateInvalid() throws EntityValidationFaultFaultMessage {
        //create a person with no telecom info
        CreateRequest request = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address);

        payloadWrapper.setPerson(builder.build());

        request.setPerson(payloadWrapper);
        try{
            port.create(request);
            fail("An EntityValidationFaultFaultMessage was expected here.");
        }catch (EntityValidationFaultFaultMessage e) {
            assertEquals(1, e.getFaultInfo().getErrors().getEntry().size());
        }
    }

    //GET
    @Test
    public void testGetById() throws NullifiedEntityFaultFaultMessage, EntityValidationFaultFaultMessage {

        //stage a person
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setPerson(builder.build());

        createRequest.setPerson(payloadWrapper);
        CreateResponse createResponse = port.create(createRequest);


        //retrieve them
        GetByIdRequest getByIdRequest = new GetByIdRequest();
        getByIdRequest.setId(new GetByIdRequest.Id());
        getByIdRequest.getId().setId(createResponse.getId());

        GetByIdResponse getByIdResponse = port.getById(getByIdRequest);
        assertNotNull(getByIdResponse.getPerson());
    }

    @Test(expected = NullPointerException.class)
    public void testGetByNonExistantId() throws NullifiedEntityFaultFaultMessage {
        //retrieve them
        GetByIdRequest getByIdRequest = new GetByIdRequest();

        Long randomIdValue = RandomUtils.nextLong();
        Ii randomIi = new IdConverter.PersonIdConverter().convertToIi(randomIdValue);
        Id randomId = IdTransformer.INSTANCE.toXml(randomIi);
        getByIdRequest.getId().setId(randomId);

        port.getById(getByIdRequest);
    }

    @Test(expected = NullifiedEntityFaultFaultMessage.class)
    public void testGetNullifiedById() throws EntityValidationFaultFaultMessage, NullifiedEntityFaultFaultMessage, SQLException {
        //stage person
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setPerson(builder.build());

        createRequest.setPerson(payloadWrapper);

        CreateResponse createResponse = port.create(createRequest);


        //nullify the person
        Statement statement = DataGeneratorUtil.getJDBCConnection().createStatement();
        statement.executeUpdate("update person set status='NULLIFIED' where id=" + createResponse.getId().getExtension());


        //retrieve the person
        GetByIdRequest getByIdRequest = new GetByIdRequest();
        getByIdRequest.setId(new GetByIdRequest.Id());
        getByIdRequest.getId().setId(createResponse.getId());

        port.getById(getByIdRequest);
    }

    //UPDATE
    @Test
    public void testUpdate() throws EntityValidationFaultFaultMessage, NullifiedEntityFaultFaultMessage {

        //create a person
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setPerson(builder.build());

        createRequest.setPerson(payloadWrapper);
        CreateResponse createResponse = port.create(createRequest);

        //update last name
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setPerson(new UpdateRequest.Person());

        Person updatedPerson = retrieve(createResponse.getId());

        updatedPerson = PersonBuilder.newInstance(updatedPerson).withFirstName("Jane").withGender(PersonSex.FEMALE).build();
        updateRequest.getPerson().setPerson(updatedPerson);


        UpdateResponse updateResponse = port.update(updateRequest);
        assertNotNull(updateResponse);

    }

    private Person retrieve(Id id) throws NullifiedEntityFaultFaultMessage {
        GetByIdRequest getByIdRequest = new GetByIdRequest();
        getByIdRequest.setId(new GetByIdRequest.Id());
        getByIdRequest.getId().setId(id);

        GetByIdResponse getByIdResponse = port.getById(getByIdRequest);

        return getByIdResponse.getPerson();
    }

    //UPDATE STATUS
    @Test
    public void testUpdateStatus() throws EntityValidationFaultFaultMessage, NullifiedEntityFaultFaultMessage {
        //create a person
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Person payloadWrapper = new CreateRequest.Person();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setPerson(builder.build());

        createRequest.setPerson(payloadWrapper);
        CreateResponse createResponse = port.create(createRequest);


        //update to active
        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest();
        UpdateStatusRequest.TargetId targetId = new UpdateStatusRequest.TargetId();
        targetId.setId(createResponse.getId());

        updateStatusRequest.setTargetId(targetId);

        UpdateStatusRequest.StatusCode newStatusCode = new UpdateStatusRequest.StatusCode();
        Cd newStatusCd = new Cd();
        newStatusCd.setCode(EntityStatus.ACTIVE.toString());
        newStatusCode.setCd(newStatusCd);
        updateStatusRequest.setStatusCode(newStatusCode);
        UpdateStatusResponse updateStatusResponse = port.updateStatus(updateStatusRequest);
        assertNotNull(updateStatusResponse);

    }

    //QUERY
    @Test
    public void testQueryWithNullLimitOffset() throws TooManyResultsFaultFaultMessage, EntityValidationFaultFaultMessage {
        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        //create 2 males
        for (int i=0; i<2; i++) {
            Person person = PersonBuilder.newInstance()
                    .withFirstName("John")
                    .withLastName("Doe")
                    .withStatusCode(EntityStatus.PENDING)
                    .withBirthDate(new Date())
                    .withGender(PersonSex.MALE)
                    .withAddress(address)
                    .withPhone("555-555-5555")
                    .build();

            CreateRequest createRequest = new CreateRequest();

            CreateRequest.Person payloadWrapper = new CreateRequest.Person();

            payloadWrapper.setPerson(person);

            createRequest.setPerson(payloadWrapper);
            port.create(createRequest);
        }

        //create 2 females
        for (int i=0; i<2; i++) {
            Person person = PersonBuilder.newInstance()
                    .withFirstName("Jane")
                    .withLastName("Doe")
                    .withStatusCode(EntityStatus.PENDING)
                    .withBirthDate(new Date())
                    .withGender(PersonSex.FEMALE)
                    .withAddress(address)
                    .withPhone("555-555-5555")
                    .build();

            CreateRequest createRequest = new CreateRequest();

            CreateRequest.Person payloadWrapper = new CreateRequest.Person();

            payloadWrapper.setPerson(person);

            createRequest.setPerson(payloadWrapper);
            port.create(createRequest);
        }

        //search for males
        QueryRequest queryRequest = new QueryRequest();
        Person example = PersonBuilder.newInstance().withFirstName("Jane").withGender(PersonSex.FEMALE).build();
        queryRequest.setPerson(new QueryRequest.Person());
        queryRequest.getPerson().setPerson(example);

        QueryResponse queryResponse = port.query(queryRequest);

        assertTrue(2<=queryResponse.getPerson().size());

        for (Person retrievedPerson : queryResponse.getPerson()) {

            String firstName = null;

            for (ENXP part : retrievedPerson.getName().getPart()) {
                if (part.getType() == EntityNamePartType.GIV) {
                    firstName = part.getValue();
                    break;
                }
            }

            assertEquals("Jane", firstName);
            assertEquals("female", retrievedPerson.getSexCode().getCode());
        }

    }

    //VALIDATE
    @Test
    public void testValidateWithErrors() {
        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address);

        ValidateRequest validateRequest = new ValidateRequest();
        validateRequest.setPerson(new ValidateRequest.Person());
        validateRequest.getPerson().setPerson(builder.build());
        ValidateResponse validateResponse = port.validate(validateRequest);

        StringMap errorMap = validateResponse.getStringMap();
        assertNotNull(errorMap);
        assertEquals(1, errorMap.getEntry().size());
    }

    @Test
    public void testValidateWithNoErrors() {
        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        PersonBuilder builder = PersonBuilder.newInstance()
                .withFirstName("John")
                .withLastName("Doe")
                .withStatusCode(EntityStatus.PENDING)
                .withBirthDate(new Date())
                .withGender(PersonSex.MALE)
                .withAddress(address)
                .withPhone("555-555-5555");

        ValidateRequest validateRequest = new ValidateRequest();
        validateRequest.setPerson(new ValidateRequest.Person());
        validateRequest.getPerson().setPerson(builder.build());
        ValidateResponse validateResponse = port.validate(validateRequest);

        StringMap errorMap = validateResponse.getStringMap();
        assertNotNull(errorMap);
        assertEquals(0, errorMap.getEntry().size());
    }


    public static class PersonBuilder {

        private Person person;

        public static final PersonBuilder newInstance() {
            return new PersonBuilder();
        }

        public static final PersonBuilder newInstance(Person person) {
            return new PersonBuilder(person);
        }

        private PersonBuilder() {
            this.person = new Person();

        }

        private PersonBuilder(Person person) {
            this.person = person;

        }

        public Person build() {
            return person;
        }


        public PersonBuilder withFirstName(String first) {

            if (person.getName() == null) {
                person.setName(new ENPN());
            }

            ENXP firstName = new ENXP();
            firstName.setType(EntityNamePartType.GIV);
            firstName.setValue(first);

            ENXP currentValue = null;
            for (ENXP part : person.getName().getPart()) {
                if (part.getType() == EntityNamePartType.GIV) {
                    currentValue = part;
                    break;
                }
            }

            person.getName().getPart().remove(currentValue);

            person.getName().getPart().add(firstName);
            return this;
        }

        public PersonBuilder withLastName(String last) {
            ENXP lastName = new ENXP();
            lastName.setType(EntityNamePartType.FAM);
            lastName.setValue(last);

            ENXP currentValue = null;
            for (ENXP part : person.getName().getPart()) {
                if (part.getType() == EntityNamePartType.FAM) {
                    currentValue = part;
                    break;
                }
            }

            person.getName().getPart().remove(currentValue);

            person.getName().getPart().add(lastName);
            return this;
        }


        public PersonBuilder withStatusCode(EntityStatus status) {
            CD statusCode = new CD();
            statusCode.setCode(status.toString());
            person.setStatusCode(statusCode);
            return this;
        }


        public PersonBuilder withBirthDate(Date date) {
            Ts ts = new Ts();
            ts.setValue(date);
            try {
                person.setBirthDate(TSTransformer.INSTANCE.toXml(ts));
            } catch (DtoTransformException e) {
                throw new RuntimeException(e);
            }
            return this;
        }

        public PersonBuilder withGender(PersonSex gender) {
            CD code = new CD();
            code.setCode(gender.toString());
            person.setSexCode(code);
            return this;
        }

        public PersonBuilder withAddress(Address address) {
            Ad ad = AddressConverter.SimpleConverter.convertToAd(address);
            person.setPostalAddress(ADTransformer.INSTANCE.toXml(ad));
            return this;
        }

        public PersonBuilder withPhone(String phoneNumber) {
            if (person.getTelecomAddress() == null) {
                person.setTelecomAddress(new DSETTEL());
            }
            TELPhone phone = new TELPhone();
            phone.setValue("tel:" + phoneNumber);
            person.getTelecomAddress().getItem().add(phone);
            return this;
        }
    }

}
