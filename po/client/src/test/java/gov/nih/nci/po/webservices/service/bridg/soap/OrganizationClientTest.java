package gov.nih.nci.po.webservices.service.bridg.soap;


import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.enterpriseservices.entities.organization.CreateRequest;
import gov.nih.nci.enterpriseservices.entities.organization.CreateResponse;
import gov.nih.nci.enterpriseservices.entities.organization.GetByIdRequest;
import gov.nih.nci.enterpriseservices.entities.organization.GetByIdResponse;
import gov.nih.nci.enterpriseservices.entities.organization.QueryRequest;
import gov.nih.nci.enterpriseservices.entities.organization.QueryResponse;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateRequest;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateResponse;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateStatusRequest;
import gov.nih.nci.enterpriseservices.entities.organization.UpdateStatusResponse;
import gov.nih.nci.enterpriseservices.entities.organization.ValidateRequest;
import gov.nih.nci.enterpriseservices.entities.organization.ValidateResponse;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.EntityValidationFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.NullifiedEntityFaultFaultMessage;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.OrganizationPortType;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.OrganizationService;
import gov.nih.nci.po.webservices.service.bridg.soap.organization.TooManyResultsFaultFaultMessage;
import gov.nih.nci.po.webservices.service.utils.AuthUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.iso._21090.CD;
import org.iso._21090.DSETTEL;
import org.iso._21090.ENON;
import org.iso._21090.ENXP;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OrganizationClientTest {
   
    private static final Country USA;

    static {
        USA = new Country("United States", "840", "US", "USA");
    }

    OrganizationPortType port;

    @Before
    public void setup() throws MalformedURLException {
        QName serviceName = new QName(
                "http://enterpriseservices.nci.nih.gov/entities/Organization",
                "OrganizationService");
        URL url = new URL(getServiceUrlString());

        AuthUtils.addBasicAuthSupport(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        OrganizationService service = new OrganizationService(url,
                serviceName);

        AuthUtils.removeBasicAuthSupport();

        port = service.getOrganizationPortTypePort();

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
                + "/po-webservices/services/bridg/OrganizationService?wsdl";
    }

    @Test
    public void testCreateWithoutAuthentication() throws EntityValidationFaultFaultMessage {
        CreateRequest request = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setOrganization(builder.build());

        request.setOrganization(payloadWrapper);
        try{
            AuthUtils.addWsSecurityUTSupport(
                    ((BindingProvider)port).getBinding(),
                    "bogusUser",
                    "bogusPassword"
            );
            port.create(request);
            fail();
        } catch (WebServiceException wse) {
            assertEquals("The server sent HTTP status code 401: Unauthorized", wse.getMessage().trim());
        }
    }

    //CREATE
    @Test
    public void testCreate() throws EntityValidationFaultFaultMessage {
        CreateRequest request = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setOrganization(builder.build());

        request.setOrganization(payloadWrapper);
        CreateResponse response = port.create(request);
        assertNotNull(Long.parseLong(response.getId().getExtension()));
    }

    @Test
    public void testCreateInvalid() throws EntityValidationFaultFaultMessage {
        //create a organization with no telecom info
        CreateRequest request = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING);

        payloadWrapper.setOrganization(builder.build());

        request.setOrganization(payloadWrapper);
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

        //stage a organization
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setOrganization(builder.build());

        createRequest.setOrganization(payloadWrapper);
        CreateResponse createResponse = port.create(createRequest);


        //retrieve them
        GetByIdRequest getByIdRequest = new GetByIdRequest();
        getByIdRequest.setId(new GetByIdRequest.Id());
        getByIdRequest.getId().setId(createResponse.getId());

        GetByIdResponse getByIdResponse = port.getById(getByIdRequest);
        assertNotNull(getByIdResponse.getOrganization());
    }

    @Test(expected = NullPointerException.class)
    public void testGetByNonExistantId() throws NullifiedEntityFaultFaultMessage {
        //retrieve them
        GetByIdRequest getByIdRequest = new GetByIdRequest();

        Long randomIdValue = RandomUtils.nextLong();
        Ii randomIi = new IdConverter.OrgIdConverter().convertToIi(randomIdValue);
        Id randomId = IdTransformer.INSTANCE.toXml(randomIi);
        getByIdRequest.getId().setId(randomId);

        port.getById(getByIdRequest);
    }

    @Test(expected = NullifiedEntityFaultFaultMessage.class)
    public void testGetNullifiedById() throws EntityValidationFaultFaultMessage, NullifiedEntityFaultFaultMessage, SQLException {
        //stage organization
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");
        payloadWrapper.setOrganization(builder.build());

        createRequest.setOrganization(payloadWrapper);
        CreateResponse createResponse = port.create(createRequest);

        //nullify the organization
        Statement statement = DataGeneratorUtil.getJDBCConnection().createStatement();
        statement.executeUpdate("update organization set status='NULLIFIED' where id=" + createResponse.getId().getExtension());

        //retrieve the organization
        GetByIdRequest getByIdRequest = new GetByIdRequest();
        getByIdRequest.setId(new GetByIdRequest.Id());
        getByIdRequest.getId().setId(createResponse.getId());

        port.getById(getByIdRequest);
    }

    //UPDATE
    @Test
    public void testUpdate() throws EntityValidationFaultFaultMessage, NullifiedEntityFaultFaultMessage {

        //create a organization
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setOrganization(builder.build());

        createRequest.setOrganization(payloadWrapper);
        CreateResponse createResponse = port.create(createRequest);

        //update last name
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setOrganization(new UpdateRequest.Organization());

        Organization updatedOrganization = retrieve(createResponse.getId());

        updatedOrganization = OrganizationBuilder
                .newInstance(updatedOrganization).withName("ORGANIZATION ABC PLUS DEF").build();
        updateRequest.getOrganization().setOrganization(updatedOrganization);


        UpdateResponse updateResponse = port.update(updateRequest);
        assertNotNull(updateResponse);

    }

    private Organization retrieve(Id id) throws NullifiedEntityFaultFaultMessage {
        GetByIdRequest getByIdRequest = new GetByIdRequest();
        getByIdRequest.setId(new GetByIdRequest.Id());
        getByIdRequest.getId().setId(id);

        GetByIdResponse getByIdResponse = port.getById(getByIdRequest);

        return getByIdResponse.getOrganization();
    }

    //UPDATE STATUS
    @Test
    public void testUpdateStatus() throws EntityValidationFaultFaultMessage, NullifiedEntityFaultFaultMessage {
        //create a organization
        CreateRequest createRequest = new CreateRequest();

        CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

        Address address = new Address();
        address.setCityOrMunicipality("Herndon");
        address.setStateOrProvince("VA");
        address.setStreetAddressLine("123 Elm St");
        address.setCountry(USA);
        address.setPostalCode("20176");

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");

        payloadWrapper.setOrganization(builder.build());

        createRequest.setOrganization(payloadWrapper);
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

        //create 2 with 555 phone number
        for (int i=0; i<2; i++) {
            Organization organization = OrganizationBuilder.newInstance()
                    .withName("Organization " + i)
                    .withStatusCode(EntityStatus.PENDING)
                    .withAddress(address)
                    .withPhone("555-555-5555")
                    .build();

            CreateRequest createRequest = new CreateRequest();

            CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

            payloadWrapper.setOrganization(organization);

            createRequest.setOrganization(payloadWrapper);
            port.create(createRequest);
        }

        //create 2 with 111 phone number
        for (int i=0; i<2; i++) {
            Organization organization = OrganizationBuilder.newInstance()
                    .withName("Organization " + i)
                    .withStatusCode(EntityStatus.PENDING)
                    .withAddress(address)
                    .withPhone("111-111-1111")
                    .build();

            CreateRequest createRequest = new CreateRequest();

            CreateRequest.Organization payloadWrapper = new CreateRequest.Organization();

            payloadWrapper.setOrganization(organization);

            createRequest.setOrganization(payloadWrapper);
            port.create(createRequest);
        }


        //search for males
        QueryRequest queryRequest = new QueryRequest();
        Organization example = OrganizationBuilder.newInstance().withPhone("555-555-5555").build();
        queryRequest.setOrganization(new QueryRequest.Organization());
        queryRequest.getOrganization().setOrganization(example);

        QueryResponse queryResponse = port.query(queryRequest);

        assertTrue(2<=queryResponse.getOrganization().size());

        for (Organization retrievedOrganization : queryResponse.getOrganization()) {
            assertEquals("tel:555-555-5555", retrievedOrganization.getTelecomAddress().getItem().get(0).getValue());
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

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(null)
                .withPhone("555-555-5555");

        ValidateRequest validateRequest = new ValidateRequest();
        validateRequest.setOrganization(new ValidateRequest.Organization());
        validateRequest.getOrganization().setOrganization(builder.build());
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

        OrganizationBuilder builder = OrganizationBuilder.newInstance()
                .withName("Organization ABC")
                .withStatusCode(EntityStatus.PENDING)
                .withAddress(address)
                .withPhone("555-555-5555");

        ValidateRequest validateRequest = new ValidateRequest();
        validateRequest.setOrganization(new ValidateRequest.Organization());
        validateRequest.getOrganization().setOrganization(builder.build());
        ValidateResponse validateResponse = port.validate(validateRequest);

        StringMap errorMap = validateResponse.getStringMap();
        assertNotNull(errorMap);
        assertEquals(0, errorMap.getEntry().size());
    }


    public static class OrganizationBuilder {

        private Organization organization;

        public static final OrganizationBuilder newInstance() {
            return new OrganizationBuilder();
        }

        public static final OrganizationBuilder newInstance(Organization organization) {
            return new OrganizationBuilder(organization);
        }

        public OrganizationBuilder() {
            this.organization = new Organization();

        }

        private OrganizationBuilder(Organization organization) {
            this.organization = organization;

        }

        public Organization build() {
            return organization;
        }


        public OrganizationBuilder withName(String first) {

            if (organization.getName() == null) {
                organization.setName(new ENON());
            }

            ENXP name = new ENXP();
            name.setValue(first);

            organization.getName().getPart().clear();
            organization.getName().getPart().add(name);
            return this;
        }


        public OrganizationBuilder withStatusCode(EntityStatus status) {
            CD statusCode = new CD();
            statusCode.setCode(status.toString());
            organization.setStatusCode(statusCode);
            return this;
        }


        public OrganizationBuilder withAddress(Address address) {
            Ad ad = AddressConverter.SimpleConverter.convertToAd(address);
            organization.setPostalAddress(ADTransformer.INSTANCE.toXml(ad));
            return this;
        }

        public OrganizationBuilder withPhone(String phoneNumber) {
            if (organization.getTelecomAddress() == null) {
                organization.setTelecomAddress(new DSETTEL());
            }
            TELPhone phone = new TELPhone();
            phone.setValue("tel:" + phoneNumber);
            organization.getTelecomAddress().getItem().add(phone);
            return this;
        }
    }
}
