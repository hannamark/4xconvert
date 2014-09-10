package gov.nih.nci.po.webservices.service.simple.soap;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.TestUtils;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.webservices.service.simple.AbstractOrganizationServiceTest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationRoleStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationRoleStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationStatusRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.ChangeOrganizationStatusResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRoleResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRoleByIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRoleByIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByCtepIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByCtepIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByOrgIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.GetOrganizationRolesByOrgIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.OrganizationService;
import gov.nih.nci.po.webservices.service.simple.soap.organization.OrganizationService_Service;
import gov.nih.nci.po.webservices.service.simple.soap.organization.RoleType;
import gov.nih.nci.po.webservices.service.simple.soap.organization.SearchOrganizationsRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.SearchOrganizationsResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRoleRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRoleResponse;
import gov.nih.nci.po.webservices.service.utils.AuthUtils;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.FundingMechanism;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.OversightCommitteeType;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.types.ResearchOrganizationType;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This is an Integration test class for OrganizationService(SOAP).
 * 
 * @author Rohit Gupta
 * 
 */
public class OrganizationServiceTest extends AbstractOrganizationServiceTest {

    private OrganizationService orgService = null;

    private static final String SPECIAL_CHARS_STR = TestUtils
            .getUTF8TestString();

    @Before
    public void setUp() throws Exception {

        super.setUp();

        // get OrganizationService
        QName orgServiceName = new QName(
                "http://soap.simple.service.webservices.po.nci.nih.gov/organization/",
                "OrganizationService");
        URL url = new URL(TstProperties.getOrgServiceURL());

        AuthUtils.addBasicAuthSupport(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        OrganizationService_Service service = new OrganizationService_Service(
                url, orgServiceName);


        AuthUtils.removeBasicAuthSupport();

        orgService = service.getOrganizationServicePort();

        final Binding binding = ((BindingProvider) orgService).getBinding();
        AuthUtils.addWsSecurityUTSupport(binding, TstProperties.getWebserviceUsername(), TstProperties.getWebservicePassword());

    }

    @Test
    public void testCreateOrganizationWithoutAuthentication() {
        AuthUtils.addWsSecurityUTSupport(((BindingProvider) orgService).getBinding(), "bogusUser", "bogusPassword");
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        try {
            orgService.createOrganization(request);
            fail();
        } catch (WebServiceException wse) {
            assertEquals("The server sent HTTP status code 401: Unauthorized", wse.getMessage().trim());
        }
    }

    /**
     * Testcase for OrganizationService-createOrganization
     */
    @Test
    public void testCreateOrganization() {
        CreateOrganizationRequest request = new CreateOrganizationRequest();        
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization retOrg = response.getOrganization();
        Assert.assertNotNull(retOrg);
        Assert.assertNotNull(retOrg.getId());
        checkOrganizationDetails(org.getName(), org, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");
    }
    
    /**
     * Testcase for OrganizationService-createOrganization -- ACTIVE Status
     */
    @Test
    public void testCreateActiveOrganization() {
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        org.setStatus(EntityStatus.ACTIVE);
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization retOrg = response.getOrganization();
        Assert.assertNotNull(retOrg);
        Assert.assertNotNull(retOrg.getId());
        checkOrganizationDetails(org.getName(), org, retOrg,
                "my.email@mayoclinic.org", "571-456-1245", "571-456-1278",
                "571-123-1123", "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganization-Organization is Null
     */
    @Test
    public void testCreateNullOrganization() {
        String excepMessage = null;
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(null);
        try {
            orgService.createOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage.contains("'{organization}' is expected"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-database ID is
     * present
     */
    @Test
    public void testCreateOrganizationIdPresent() {
        String excepMessage = null;
        org.setId(12345l);
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        try {
            orgService.createOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("IllegalArgumentException: id must be null on calls to create"));

    }
    
    
    /**
     * Testcase for OrganizationService-createOrganization- CTEP ID is
     * present
     */
    @Test
    public void testCreateOrganizationCtepIdPresent() {
        String excepMessage ="";
        org.setCtepId("VA212");
        CreateOrganizationRequest request = new CreateOrganizationRequest();        
        request.setOrganization(org);
        try {
            orgService.createOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage.contains("Organization couldn't be created as CTEP ID VA212 is passed in the request"));
    }

    /**
     * Testcase for OrganizationService-createOrganization-Address not present
     */
    @Test
    public void testCreateOrganizationAddressNotPresent() {
        String excepMessage = null;
        org.setAddress(null);
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        try {
            orgService.createOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("gov.nih.nci.po.webservices.types\":address}' is expected"));

    }

    /**
     * Testcase for OrganizationService-createOrganization-PhoneNumber is
     * invalid
     */
    @Test
    public void testCreateOrganizationPhoneInvalid() {
        String excepMessage = null;
        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("703@35@234");
        org.getContact().set(1, phoneContact); // set invalid phone number
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        try {
            orgService.createOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Organization couldn't be created as data is invalid."));
        Assert.assertTrue(excepMessage
                .contains("phone[0].value Phone number 703@35@234"));
    }

    /**
     * Testcase for OrganizationService-updateOrganization
     */
    @Test
    public void testUpdateOrganization() {
        // create an Organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization createdOrg = response.getOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo"); // will be added in Alias
        createdOrg.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdOrg.setAddress(getJaxbAddressList().get(1));
        // clear the existing contacts & set new one
        createdOrg.getContact().clear();
        createdOrg.getContact().addAll(getJaxbUpdatedContactList()); // updated

        // now update the created organization
        UpdateOrganizationRequest updateRequest = new UpdateOrganizationRequest();
        updateRequest.setOrganization(createdOrg);
        UpdateOrganizationResponse upResponse = orgService
                .updateOrganization(updateRequest);
        Organization updatedOrg = upResponse.getOrganization();

        // Assert checks
        checkOrganizationDetails(org.getName(), createdOrg, updatedOrg,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");

        // check for Alias
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setId(response.getOrganization().getId());
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        checkOrgAliases("My Mayo", orgList.get(0)); // check alias
    }

    /**
     * Testcase for OrganizationService-updateOrganization - aliases
     */
    @Test
    public void testUpdateOrganizationAliases() {
        // create an Organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization createdOrg = response.getOrganization();

        // now change name of the organization
        createdOrg.setName("My Mayo 1111"); // will be added in Alias
        // now update the created organization
        UpdateOrganizationRequest upRequest = new UpdateOrganizationRequest();
        upRequest.setOrganization(createdOrg);
        orgService.updateOrganization(upRequest);
        // check for Alias
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setId(createdOrg.getId());
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        checkOrgAliases("My Mayo 1111", orgList.get(0)); // check alias

        // now again change name of the organization
        createdOrg.setName("My Mayo 2222"); // will be added in Alias
        // now update the organization
        upRequest = new UpdateOrganizationRequest();
        upRequest.setOrganization(createdOrg);
        orgService.updateOrganization(upRequest);
        // check for Alias
        soRequest = new SearchOrganizationsRequest();
        criteria = new OrganizationSearchCriteria();
        criteria.setId(createdOrg.getId());
        soRequest.setOrganizationSearchCriteria(criteria);
        soResponse = orgService.searchOrganizations(soRequest);
        orgList = soResponse.getOrganizationSearchResultList();
        Assert.assertTrue(orgList.get(0).getAlias().size() == 2);
        checkOrgAliases("My Mayo 2222", orgList.get(0)); // check alias

        // now pass the same name of the organization
        createdOrg.setName(org.getName()); // nothing added to alias
        // now update the organization
        upRequest = new UpdateOrganizationRequest();
        upRequest.setOrganization(createdOrg);
        orgService.updateOrganization(upRequest);
        // check for Alias
        soRequest = new SearchOrganizationsRequest();
        criteria = new OrganizationSearchCriteria();
        criteria.setId(createdOrg.getId());
        soRequest.setOrganizationSearchCriteria(criteria);
        soResponse = orgService.searchOrganizations(soRequest);
        orgList = soResponse.getOrganizationSearchResultList();
        Assert.assertTrue(orgList.get(0).getOrganizationName()
                .equalsIgnoreCase(org.getName()));
        Assert.assertTrue(orgList.get(0).getAlias().size() == 2);

    }
    
    /**
     * Testcase for OrganizationService-updateOrganization- create a Change Request
     */
    @Test
    public void testUpdateOrganization_create_ChangeRequest() {
        // create an Organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization createdOrg = response.getOrganization();
        
        // set Overridden by CTRPQATester1
        updateOverriddenBy(Organization.class, createdOrg.getId());

        // now change some attributes of this organization
        String orgUpName ="My Mayo 123";
        createdOrg.setName(orgUpName); // CR should have this name
        createdOrg.setStatus(EntityStatus.ACTIVE);
        // address is updated with another address object
        createdOrg.setAddress(getJaxbAddressList().get(1));
        // clear the existing contacts & set new one
        createdOrg.getContact().clear();
        createdOrg.getContact().addAll(getJaxbUpdatedContactList()); // updated

        // now update the created organization, it should create a Change Request
        UpdateOrganizationRequest updateRequest = new UpdateOrganizationRequest();
        updateRequest.setOrganization(createdOrg);
        orgService.updateOrganization(updateRequest);      
        
        checkOrganizationCRDetails(orgUpName, createdOrg, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org");   
    }

    /**
     * Testcase for OrganizationService-updateOrganization-OrgNotFoundInDB
     */
    @Test
    public void testUpdateOrganizationForOrgNotFoundInDB() {
        String excepMessage = null;
        UpdateOrganizationRequest request = new UpdateOrganizationRequest();
        org.setId(999999888888777777l); // Id not present in the DB
        request.setOrganization(org);
        try {
            orgService.updateOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("EntityNotFoundException: Couldn't update the Organization for organizationID 999999888888777777 as Organization is not found in the DB."));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-IdNotPresentInRequest
     */
    @Test
    public void testUpdateOrganizationForIdNotPresentInRequest() {
        String excepMessage = null;
        UpdateOrganizationRequest request = new UpdateOrganizationRequest();
        org.setId(null); // Id not present in the request
        request.setOrganization(org);
        try {
            orgService.updateOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The Organization couldn't be updated as either organization or organizationId is null"));
    }

    /**
     * Testcase for OrganizationService-updateOrganization-CTEP ID Present
     */
    @Test
    public void testUpdateOrganizationCtepIdPresent() {
        String excepMessage = "";
        // create an Organization first
        Organization createdOrg = createActiveOrganization();

        // now change some attributes of the newly created organization
        createdOrg.setName("My Mayo"); // name updated
        createdOrg.setCtepId("MD212"); 

        // now update the created organization
        UpdateOrganizationRequest updateRequest = new UpdateOrganizationRequest();
        updateRequest.setOrganization(createdOrg);
        try {
            orgService.updateOrganization(updateRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage.contains("Organization couldn't be updated as CTEP ID MD212 is passed in the request"));
    }
    /**
     * Testcase for OrganizationService-updateOrganization-Org object is Null
     */
    @Test
    public void testUpdateOrganizationForNullOrg() {
        String excepMessage = null;
        UpdateOrganizationRequest request = new UpdateOrganizationRequest();
        request.setOrganization(null);
        try {
            orgService.updateOrganization(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage.contains("'{organization}' is expected"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus
     */
    @Test
    public void testChangeOrganizationStatus() {
        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        // The created Org has status PENDING
        Organization retOrg = response.getOrganization();

        // change the organization status now
        ChangeOrganizationStatusRequest cosRequest = new ChangeOrganizationStatusRequest();
        cosRequest.setOrganizationID(retOrg.getId());
        cosRequest.setStatus(EntityStatus.ACTIVE);
        ChangeOrganizationStatusResponse cosResponse = orgService
                .changeOrganizationStatus(cosRequest);
        Assert.assertEquals(EntityStatus.ACTIVE.value(), cosResponse
                .getOrganization().getStatus().value());

        // check for the status in the DB also
        checkOrgStatusInDB(retOrg.getId(), EntityStatus.ACTIVE.value());
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-OrgNotFoundInDB
     */
    @Test
    public void testChangeOrganizationStatusForOrgNotFoundInDB() {
        String excepMessage = null;
        ChangeOrganizationStatusRequest cosRequest = new ChangeOrganizationStatusRequest();
        cosRequest.setOrganizationID(999999888888777777l); // Id not existing in
                                                           // DB
        cosRequest.setStatus(EntityStatus.ACTIVE);
        try {
            orgService.changeOrganizationStatus(cosRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("Couldn't update the Organization Status"));
        Assert.assertTrue(excepMessage
                .contains("Organization is not found in the DB"));
    }

    /**
     * Testcase for
     * OrganizationService-changeOrganizationStatus-InvalidStatusTransition
     */
    @Test
    public void testChangeOrganizationStatusForInvalidTransition() {
        String excepMessage = null;
        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        // The created Org has status PENDING
        Organization retOrg = response.getOrganization();

        // change the organization status now to make it ACTIVE
        ChangeOrganizationStatusRequest cosRequest = new ChangeOrganizationStatusRequest();
        cosRequest.setOrganizationID(retOrg.getId());
        cosRequest.setStatus(EntityStatus.ACTIVE);
        ChangeOrganizationStatusResponse cosResponse = orgService
                .changeOrganizationStatus(cosRequest);
        Assert.assertEquals(EntityStatus.ACTIVE.value(), cosResponse
                .getOrganization().getStatus().value());

        // now try to change it to PENDING, which should fail
        try {
            cosRequest.setOrganizationID(retOrg.getId());
            cosRequest.setStatus(EntityStatus.PENDING);
            orgService.changeOrganizationStatus(cosRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("Illegal curation transition from ACTIVE to PENDING"));

        // check Org Status in the DB (it shouldn't change)
        checkOrgStatusInDB(retOrg.getId(), EntityStatus.ACTIVE.value());
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-IdNotPresent
     */
    @Test
    public void testChangeOrganizationStatusForIDNotPresent() {
        String excepMessage = null;
        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        orgService.createOrganization(request);

        // try change the organization status without giving ID
        ChangeOrganizationStatusRequest cosRequest = new ChangeOrganizationStatusRequest();
        // cosRequest.setOrganizationID(retOrg.getId()); // no ID
        cosRequest.setStatus(EntityStatus.ACTIVE);
        try {
            orgService.changeOrganizationStatus(cosRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("Couldn't update the Organization Status"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-InvalidStatus
     */
    @Test
    public void testChangeOrganizationStatusForInvalidStatus() {
        String excepMessage = null;
        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        // The created Org has status PENDING
        Organization retOrg = response.getOrganization();

        // change the organization status now to make it NULL
        ChangeOrganizationStatusRequest cosRequest = new ChangeOrganizationStatusRequest();
        try {
            cosRequest.setOrganizationID(retOrg.getId());
            cosRequest.setStatus(null);
            orgService.changeOrganizationStatus(cosRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{status}' is expected"));
    }

    /**
     * Testcase for OrganizationService-getOrganization
     */
    @Test
    public void testGetOrganization() {

        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();        
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization createdOrg = response.getOrganization();

        Assert.assertNotNull(createdOrg);

        GetOrganizationRequest goRequest = new GetOrganizationRequest();
        goRequest.setOrganizationID(createdOrg.getId());
        GetOrganizationResponse goresponse = orgService
                .getOrganization(goRequest);
        Organization retOrg = goresponse.getOrganization();
        Assert.assertEquals(org.getName(), retOrg.getName());
        Assert.assertEquals(EntityStatus.PENDING.value(), retOrg.getStatus()
                .value());
        Assert.assertEquals(createdOrg.getId(), retOrg.getId());
        assertEquals(createdOrg.getCtepId(), retOrg.getCtepId());
    }

    /**
     * Testcase for OrganizationService-getOrganization
     */
    @Test
    public void testGetOrganizationForOrgNotFound() {
        GetOrganizationRequest goRequest = new GetOrganizationRequest();
        goRequest.setOrganizationID(999999888888777777l);
        GetOrganizationResponse goresponse = orgService
                .getOrganization(goRequest);
        Organization retOrg = goresponse.getOrganization();

        Assert.assertNull(retOrg);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Name
     */
    @Test
    public void testSearchOrganizationsByOrgName() {
        String randomName = RandomStringUtils.random(60, true, true);
        // create few Organizations
        org.setName(randomName);// for LIKE search
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create another organization
        org.setName("Test_" + randomName); // for LIKE search
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create another organization
        org.setName(randomName + " abc"); // for LIKE search
        request.setOrganization(org);
        orgService.createOrganization(request);

        // search by Organization Name
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setOrganizationName(randomName);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        Assert.assertTrue(orgList.size() == 3);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct matching name
            Assert.assertTrue(osr.getOrganizationName().contains(randomName)); // LIKE
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Alias
     */
    @Test
    public void testSearchOrganizationsByOrgAlias() {
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse res = orgService.createOrganization(request);
        Organization createdOrg = res.getOrganization();

        // now create Aliases for this organization
        createOrgAliasesData(createdOrg.getId());

        // search by OrganizationID ( & check that Alias is present in result)
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setId(createdOrg.getId());
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have aliases
            Assert.assertTrue(osr.getAlias().size() == 2);
            Assert.assertTrue(osr.getAlias().get(0).getValue()
                    .equals("test org alias 1"));
        }

        // search by Organization Alias (flag is true)
        soRequest = new SearchOrganizationsRequest();
        criteria = new OrganizationSearchCriteria();
        criteria.setOrganizationName("test org alias 1");
        criteria.setSearchAliases(true); // set the flag
        soRequest.setOrganizationSearchCriteria(criteria);
        soResponse = orgService.searchOrganizations(soRequest);
        orgList = soResponse.getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        Assert.assertTrue(orgList.size() >= 1);
        boolean isOrgPresent = false;
        for (OrganizationSearchResult osr : orgList) {
            if (osr.getId() == createdOrg.getId()) {
                isOrgPresent = true;
            }
        }
        if (isOrgPresent == false) {
            Assert.fail("Organization is not fetched using Alias.");
        }

        // search by Organization Alias (flag is false -- no result found)
        soRequest = new SearchOrganizationsRequest();
        criteria = new OrganizationSearchCriteria();
        criteria.setOrganizationName("test org alias 1");
        criteria.setSearchAliases(false); // don't search using alias
        soRequest.setOrganizationSearchCriteria(criteria);
        soResponse = orgService.searchOrganizations(soRequest);
        orgList = soResponse.getOrganizationSearchResultList();

        Assert.assertTrue(orgList.size() == 0);
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Org Name having
     * special characters
     */
    @Test
    public void testSearchOrganizationsByOrgNameHavingSpecialChars() {
        // create few Organizations
        org.setName(SPECIAL_CHARS_STR);// for LIKE search
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create another organization
        org.setName("Test_" + SPECIAL_CHARS_STR); // for LIKE search
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create another organization
        org.setName(SPECIAL_CHARS_STR + " abc"); // for LIKE search
        request.setOrganization(org);
        orgService.createOrganization(request);

        // search by Organization Name
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setOrganizationName(SPECIAL_CHARS_STR);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        // rerun the testcase might have more records
        Assert.assertTrue(orgList.size() >= 3);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct matching name
            Assert.assertTrue(osr.getOrganizationName().contains(
                    SPECIAL_CHARS_STR)); // LIKE
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by FamilyName
     */
    @Test
    public void testSearchOrganizationsByFamilyName() {
        // relying on any existing organization
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setFamilyName("M.");
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        if (CollectionUtils.isNotEmpty(orgList)) {
            for (OrganizationSearchResult osr : orgList) {
                // check that fetch Orgs have correct matching family name
                Assert.assertTrue(osr.getFamilyName().contains("M.")); // LIKE
            }
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by PO database Id
     */
    @Test
    public void testSearchOrganizationsByPOId() {
        String randomName = RandomStringUtils.random(59, true, true);
        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        org.setName(randomName);
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);

        // search by Organization ID
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setId(response.getOrganization().getId()); // set the DB Id
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        Assert.assertTrue(orgList.size() == 1);
        // check that the Organization is same as what we are expecting
        Assert.assertEquals(randomName, orgList.get(0).getOrganizationName());
    }
    
    /**
     * Testcase for OrganizationService-searchOrganizations- by CTEP ID
     */
    @Test
    public void testSearchOrganizationsByCtepId() {        
        // create an organization first
        Organization createdOrg = createActiveOrganization();
        
        // create HCF
        String hcfCtepId = RandomStringUtils.random(10, true, true);
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        CreateOrganizationRoleRequest corReq = new CreateOrganizationRoleRequest();
        corReq.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse corRes = orgService.createOrganizationRole(corReq);      
        
        // now set the CTEP ID for this HCF
        createHcfCtepId(corRes.getOrganizationRole().getId(), hcfCtepId);
        
        // search the Org using HCF CTEP ID
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria(); 
        criteria.setCtepID(hcfCtepId);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService.searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse.getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        Assert.assertTrue(orgList.size() >= 1);
        boolean createdOrgPresent = false;
        for (OrganizationSearchResult osr : orgList) {
            // also check that the collection contain just created Org
            if (osr.getId() == createdOrg.getId()) {
                createdOrgPresent = true;
                Assert.assertEquals(hcfCtepId, osr.getHcfCtepID());
                Assert.assertTrue(StringUtils.isBlank(osr.getRoCtepID())); // RO CTEP ID was not set
                Assert.assertTrue(StringUtils.isBlank(osr.getOrgCtepId())); // IO CTEP ID was not set
            }
        }
        if (!createdOrgPresent) {
            Assert.fail("The Org Search using HCF CTEP ID doesn't contain newly created organization.");
        }
        
        
        // create RO
        String roCtepId = RandomStringUtils.random(10, true, true);
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());        
        corReq = new CreateOrganizationRoleRequest();
        corReq.setOrganizationRole(ro);        
        corRes = orgService.createOrganizationRole(corReq);    
        
        // now set the CTEP ID for this RO
        createROCtepId(corRes.getOrganizationRole().getId(), roCtepId);
        
        // search the Org using RO CTEP ID
        soRequest = new SearchOrganizationsRequest();
        criteria = new OrganizationSearchCriteria();   
        criteria.setCtepID(roCtepId);
        soRequest.setOrganizationSearchCriteria(criteria);
        soResponse = orgService.searchOrganizations(soRequest);
        orgList = soResponse.getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        Assert.assertTrue(orgList.size() >= 1);
        createdOrgPresent = false;
        for (OrganizationSearchResult osr : orgList) {
            // also check that the collection contain just created Org
            if (osr.getId() == createdOrg.getId()) {
                createdOrgPresent = true;
                Assert.assertEquals(roCtepId, osr.getRoCtepID());
                Assert.assertEquals(hcfCtepId, osr.getHcfCtepID()); // HCF CTEP ID was set above
                Assert.assertTrue(StringUtils.isBlank(osr.getOrgCtepId())); // IO CTEP ID was not set
            }
        }
        if (!createdOrgPresent) {
            Assert.fail("The Org Search using RO CTEP ID doesn't contain newly created organization.");
        }
        
        // create IdentifiedOrganization with CETP ID and search the Org by that CTEP ID
        String ioCtepId = RandomStringUtils.random(10, true, true);
        createIdentifiedOrganization(createdOrg.getId(), ioCtepId);
        // search the Org using IO CTEP ID
        soRequest = new SearchOrganizationsRequest();
        criteria = new OrganizationSearchCriteria();
        criteria.setCtepID(ioCtepId);
        soRequest.setOrganizationSearchCriteria(criteria);
        soResponse = orgService.searchOrganizations(soRequest);
        orgList = soResponse.getOrganizationSearchResultList();

        Assert.assertNotNull(orgList);
        Assert.assertTrue(orgList.size() >= 1);
        createdOrgPresent = false;
        for (OrganizationSearchResult osr : orgList) {
            // also check that the collection contain just created Org
            if (osr.getId() == createdOrg.getId()) {
                createdOrgPresent = true;
                Assert.assertEquals(ioCtepId, osr.getOrgCtepId()); 
                Assert.assertEquals(roCtepId, osr.getRoCtepID()); // RO CTEP ID was set above
                Assert.assertEquals(hcfCtepId, osr.getHcfCtepID()); // HCF CTEP ID was set above
            }
        }
        if (!createdOrgPresent) {
            Assert.fail("The Org Search using IO CTEP ID doesn't contain newly created organization.");
        }
        
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Status
     */
    @Test
    public void testSearchOrganizationsByStatus() {
        // create an organization
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request); // created status is PENDING
        long createdOrgId = response.getOrganization().getId();
        // change the organization status to ACTIVE (as we can't directly set
        // PENDING -->INACTIVE)
        ChangeOrganizationStatusRequest cosRequest = new ChangeOrganizationStatusRequest();
        cosRequest.setOrganizationID(createdOrgId);
        cosRequest.setStatus(EntityStatus.ACTIVE);
        ChangeOrganizationStatusResponse cosResponse = orgService
                .changeOrganizationStatus(cosRequest);
        Assert.assertEquals(EntityStatus.ACTIVE.value(), cosResponse
                .getOrganization().getStatus().value());

        // Now change the organization status to INACTIVE
        cosRequest.setOrganizationID(createdOrgId);
        cosRequest.setStatus(EntityStatus.INACTIVE);
        cosResponse = orgService.changeOrganizationStatus(cosRequest);
        Assert.assertEquals(EntityStatus.INACTIVE.value(), cosResponse
                .getOrganization().getStatus().value());

        // now search by status "INACTIVE"
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setStatusCode(EntityStatus.INACTIVE);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        // If we rerun, then there might be more than 1 such org
        Assert.assertTrue(orgList.size() >= 1);

        boolean createdOrgPresent = false;
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct status
            Assert.assertTrue(osr.getStatusCode().value()
                    .equalsIgnoreCase(EntityStatus.INACTIVE.value()));
            // also check that the collection contain just created Org
            if (osr.getId() == createdOrgId) {
                createdOrgPresent = true;
            }
        }

        if (!createdOrgPresent) {
            Assert.fail("The returned collection doesn't contain newly created organization.");
        }

    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by Country
     */
    @Test
    public void testSearchOrganizationsByCountry() {
        // create few organizations with country as India
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        org.getAddress().setCountry(CountryISO31661Alpha3Code.IND);
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setCountry(CountryISO31661Alpha3Code.IND);
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setCountry(CountryISO31661Alpha3Code.IND);
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setCountry(CountryISO31661Alpha3Code.IND);
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setCountry(CountryISO31661Alpha3Code.IND);
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setCountry(CountryISO31661Alpha3Code.IND);
        request.setOrganization(org);
        orgService.createOrganization(request);

        // now search by Country as India and limit the record to 4
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setCountryCode(CountryISO31661Alpha3Code.IND);
        criteria.setLimit(4); // set limit to 4
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        Assert.assertTrue(orgList.size() == 4);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct country
            Assert.assertTrue(osr.getCountryCode().value()
                    .equalsIgnoreCase(CountryISO31661Alpha3Code.IND.value()));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by AddressLine1
     */
    @Test
    public void testSearchOrganizationsByAddressLine1() {
        String randomLine1 = RandomStringUtils.random(63, true, true);
        org.getAddress().setLine1(randomLine1);

        // create few organizations with this address line
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setLine1(randomLine1 + "abcxyz"); // for LIKE search
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setLine1("123_" + randomLine1); // for LIKE search
        request.setOrganization(org);
        orgService.createOrganization(request);

        // now search by address-line1
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setLine1(randomLine1);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        Assert.assertTrue(orgList.size() == 3);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct address-line1
            Assert.assertTrue(osr.getLine1().contains(randomLine1));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by AddressLine1 -
     * having special characters
     */
    @Test
    public void testSearchOrganizationsByAddressLine1HavingSpecialChars() {
        // create few organizations with this address line
        CreateOrganizationRequest request = new CreateOrganizationRequest();

        org.getAddress().setLine1(SPECIAL_CHARS_STR);
        request.setOrganization(org);
        orgService.createOrganization(request);

        // for LIKE search
        org.getAddress().setLine1(SPECIAL_CHARS_STR + "abcxyz");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // for LIKE search
        org.getAddress().setLine1("123_" + SPECIAL_CHARS_STR);
        request.setOrganization(org);
        orgService.createOrganization(request);

        // now search by address-line1
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setLine1(SPECIAL_CHARS_STR);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        // testcase rerun might have more records
        Assert.assertTrue(orgList.size() >= 3);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct address-line1
            Assert.assertTrue(osr.getLine1().contains(SPECIAL_CHARS_STR));
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations- by ZipCode
     */
    @Test
    public void testSearchOrganizationsByZipCode() {
        String randomPostalCode = RandomStringUtils.random(10, false, true);
        org.getAddress().setPostalcode(randomPostalCode);

        // create few organizations with this ZipCode
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setPostalcode("11" + randomPostalCode); // for LIKE
                                                                 // search
        request.setOrganization(org);
        orgService.createOrganization(request);

        org.getAddress().setPostalcode(randomPostalCode + "21"); // for LIKE
                                                                 // search
        request.setOrganization(org);
        orgService.createOrganization(request);

        // now search by ZipCode
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setPostalcode(randomPostalCode);
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        Assert.assertTrue(orgList.size() == 3);
        for (OrganizationSearchResult osr : orgList) {
            // check that fetch Orgs have correct randomPostalCode
            Assert.assertTrue(osr.getPostalcode().contains(randomPostalCode));
        }
    }

    /**
     * Testcase OrganizationService-searchOrganizations-for Offset & Limit
     */
    @Test
    public void testSearchOrganizationsForOffsetAndLimit() {
        // create few organizations with country as Singapore
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("00000000000000000000000000000000000000");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 2nd organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("11111111111111111111111111111111111111");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 3rd organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("22222222222222222222222222222222222222");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 4th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("33333333333333333333333333333333333333");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 5th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("44444444444444444444444444444444444444");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 6th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("55555555555555555555555555555555555555");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 7th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("66666666666666666666666666666666666666");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 8th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("77777777777777777777777777777777777777");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // create 9th organization
        org.getAddress().setCountry(CountryISO31661Alpha3Code.SGP);
        org.setName("888888888888888888888888888888888888888");
        request.setOrganization(org);
        orgService.createOrganization(request);

        // search by Country with Offset & Limit
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        criteria.setCountryCode(CountryISO31661Alpha3Code.SGP);
        criteria.setOffset(2);
        criteria.setLimit(3); // set limit to 3
        soRequest.setOrganizationSearchCriteria(criteria);
        SearchOrganizationsResponse soResponse = orgService
                .searchOrganizations(soRequest);
        List<OrganizationSearchResult> orgList = soResponse
                .getOrganizationSearchResultList();
        Assert.assertTrue(orgList.size() == 3);

        for (int i = 0; i < orgList.size(); i++) {
            OrganizationSearchResult osr = orgList.get(i);
            if (i == 0) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "22222222222222222222222222222222222222"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.SGP.value()));
            } else if (i == 1) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "33333333333333333333333333333333333333"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.SGP.value()));
            } else if (i == 2) {
                Assert.assertTrue(osr.getOrganizationName().equalsIgnoreCase(
                        "44444444444444444444444444444444444444"));
                Assert.assertTrue(osr
                        .getCountryCode()
                        .value()
                        .equalsIgnoreCase(CountryISO31661Alpha3Code.SGP.value()));
            }
        }
    }

    /**
     * Testcase for OrganizationService-searchOrganizations-Search Criteria is
     * empty
     */
    @Test
    public void testSearchOrganizationCriteriaEmpty() {
        String excepMessage = null;
        SearchOrganizationsRequest soRequest = new SearchOrganizationsRequest();
        // criteria is empty
        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
        soRequest.setOrganizationSearchCriteria(criteria);
        try {
            orgService.searchOrganizations(soRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("OneCriterionRequiredException: At least one criterion must be provided"));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole null
     */
    @Test
    public void testCreateOrganizationRoleForNullOrgRole() {
        String excepMessage = null;
        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(null);
        try {
            orgService.createOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{organizationRole}' is expected"));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole ID
     * present in the request
     */
    @Test
    public void testCreateOrganizationRoleForRoleIdPresent() {
        String excepMessage = null;
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setId(12345l);
        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        try {
            orgService.createOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("organizationRoleId is present in the request"));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-HCF
     */
    @Test
    public void testCreateOrganizationRoleHCF() {
        // create a new organization
        Organization organization = createActiveOrganization();

        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();
        Assert.assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof HealthCareFacility);
        Assert.assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.PENDING, orgRole.getStatus());
        // check the address details from DB for HCF
        checkOrgRoleAddressDetails(hcf, orgRole);
        checkOrgRoleContactDetails(hcf, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }
    
    /**
     * Testcase for OrganizationService-createOrganizationRole-HCF-having CTEP ID
     */
    @Test
    public void testCreateOrganizationRoleHCFHavingCtepId() {
        String excepMessage ="";
        // create a new organization
        Organization organization = createActiveOrganization();

        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());
        hcf.setCtepId("VA212");

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        try {
            orgService.createOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        
        Assert.assertTrue(excepMessage
                .contains("The OrganizationRole couldn't be created as CTEP ID VA212 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OverComm
     */
    @Test
    public void testCreateOrganizationRoleOC() {
        // create a new organization
        Organization organization = createActiveOrganization();

        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(oc);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();
        Assert.assertNotNull(orgRole);
        Assert.assertTrue(orgRole instanceof OversightCommittee);
        Assert.assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.ACTIVE, orgRole.getStatus());

        checkOrgRoleAddressDetails(oc, orgRole);
        checkOrgRoleContactDetails(oc, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-RO
     */
    @Test
    public void testCreateOrganizationRoleRO() {
        // create a new organization
        Organization organization = createActiveOrganization();

        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        ResearchOrganization orgRole = (ResearchOrganization) response.getOrganizationRole();
        Assert.assertNotNull(orgRole);
        Assert.assertNotNull(orgRole.getId());
        assertEquals(EntityStatus.PENDING, orgRole.getStatus());
        assertEquals("NWK", orgRole.getType().name());
        assertEquals(ro.getFundingMechanism().value(), orgRole.getFundingMechanism().value());

        checkOrgRoleAddressDetails(ro, orgRole);
        checkOrgRoleContactDetails(ro, orgRole, "my.email@mayoclinic.org",
                "571-456-1245", "571-456-1278", "571-123-1123",
                "http://www.mayoclinic.org");
    }
    
    /**
     * Testcase for OrganizationService-createOrganizationRole-RO-
     * CTEP ID is passed.
     */
    @Test
    public void testCreateOrganizationRoleROWithCTEPId() {
        String excepMessage ="";
        // create a new organization
        Organization organization = createActiveOrganization();

        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setCtepId("MD255");
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        try {
            orgService.createOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("The OrganizationRole couldn't be created as CTEP ID MD255 is passed in the request."));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OrgRole null
     */
    @Test
    public void testUpdateOrganizationRoleForNullOrgRole() {
        String excepMessage = null;
        UpdateOrganizationRoleRequest request = new UpdateOrganizationRoleRequest();
        request.setOrganizationRole(null);
        try {
            orgService.updateOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{organizationRole}' is expected"));
    }

    /**
     * Testcase for OrganizationService-createOrganizationRole-OrgRole ID not
     * present in the request
     */
    @Test
    public void testUpdateOrganizationRoleForRoleIdNotPresent() {
        String excepMessage = null;
        UpdateOrganizationRoleRequest request = new UpdateOrganizationRoleRequest();
        HealthCareFacility hcf = getHealthCareFacilityObj(); // DB id not
                                                             // present
        request.setOrganizationRole(hcf);
        try {
            orgService.updateOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage.contains("organizationRoleId is null"));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF
     */
    @Test
    public void testUpdateOrganizationRoleHCF() {
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        HealthCareFacility createdHCF = (HealthCareFacility) response
                .getOrganizationRole();
        Assert.assertTrue(createdHCF instanceof HealthCareFacility);

        // now update the HCF details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdHCF.setName("Mayo HCF 111"); // added to alias, name not change
        // update the address
        createdHCF.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        createdHCF.getContact().clear(); // clear existing
        createdHCF.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setOrganizationRole(createdHCF);
        UpdateOrganizationRoleResponse upResponse = orgService
                .updateOrganizationRole(upRequest);
        HealthCareFacility upHCF = (HealthCareFacility) upResponse
                .getOrganizationRole();
        // check for the updated address details
        checkOrgRoleAddressDetails(createdHCF, upHCF);
        // check for the updated contact details
        checkOrgRoleContactDetails(createdHCF, upHCF,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
        Assert.assertEquals(hcf.getName(), upHCF.getName()); // no name changed
        checkOrgRoleAliases("Mayo HCF 111", upHCF);
    }
    
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF- CTEP ID
     */
    @Test
    public void testUpdateOrganizationRoleHCFHavingCtepId() {
        String excepMessage= "";
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());
        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        HealthCareFacility createdHCF = (HealthCareFacility) response
                .getOrganizationRole();
        Assert.assertTrue(createdHCF instanceof HealthCareFacility);

        // now update the HCF details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdHCF.setName("Mayo HCF 111"); // added to alias, name not change
        createdHCF.setCtepId("MD211");
        upRequest.setOrganizationRole(createdHCF);
        try {
            orgService.updateOrganizationRole(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        
        Assert.assertTrue(excepMessage
                .contains("The OrganizationRole couldn't be updated as CTEP ID MD211 is passed in the request."));
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-HCF - create a Change Request
     */
    @Test
    public void testUpdateOrganizationRoleHCF_create_ChangeRequest() {
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        HealthCareFacility createdHCF = (HealthCareFacility) response
                .getOrganizationRole();
        Assert.assertTrue(createdHCF instanceof HealthCareFacility);
        
        // set Overridden by CTRPQATester1
        updateOverriddenBy(HealthCareFacility.class, createdHCF.getId());

        // now update the HCF details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        String rolUpName ="Mayo HCF 111";
        createdHCF.setName(rolUpName); // CR should have this name
        // update the address
        createdHCF.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        createdHCF.getContact().clear(); // clear existing
        createdHCF.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setOrganizationRole(createdHCF);
        orgService.updateOrganizationRole(upRequest);     
       
        checkHCFChangeRequestDetails(rolUpName, createdHCF, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org");    
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-OC
     */
    @Test
    public void testUpdateOrganizationRoleOC() {
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a OC first
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(oc);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OversightCommittee createdOC = (OversightCommittee) response
                .getOrganizationRole();
        Assert.assertTrue(createdOC instanceof OversightCommittee);

        // now update the OC details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdOC.setType(OversightCommitteeType.RESEARCH_ETHICS_BOARD);
        // update the address
        createdOC.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        createdOC.getContact().clear(); // clear existing
        createdOC.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setOrganizationRole(createdOC);
        UpdateOrganizationRoleResponse upResponse = orgService
                .updateOrganizationRole(upRequest);
        OversightCommittee upOC = (OversightCommittee) upResponse
                .getOrganizationRole();
        Assert.assertEquals(OversightCommitteeType.RESEARCH_ETHICS_BOARD
                .value(), upOC.getType().value());
        // check for the updated address details
        checkOrgRoleAddressDetails(createdOC, upOC);
        // check for the updated contact details
        checkOrgRoleContactDetails(createdOC, upOC,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO
     */
    @Test
    public void testUpdateOrganizationRoleRO() {

        // create a new organization
        Organization organization = createActiveOrganization();

        // create a RO first
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        ResearchOrganization createdRO = (ResearchOrganization) response
                .getOrganizationRole();
        Assert.assertNotNull(createdRO);
        Assert.assertTrue(createdRO instanceof ResearchOrganization);

        // now update the RO details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdRO.setName("Mayo RO 111"); // added to alias, name not change
        createdRO.setType(ResearchOrganizationType.RSB);
        createdRO.setFundingMechanism(FundingMechanism.U_10);
        // update the address
        createdRO.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        createdRO.getContact().clear(); // clear existing
        createdRO.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setOrganizationRole(createdRO);
        UpdateOrganizationRoleResponse upResponse = orgService
                .updateOrganizationRole(upRequest);
        ResearchOrganization upRO = (ResearchOrganization) upResponse
                .getOrganizationRole();
        Assert.assertEquals(ResearchOrganizationType.RSB.value(), upRO
                .getType().value());
        assertEquals("U10", upRO
                .getFundingMechanism().value());
        // check for the updated address details
        checkOrgRoleAddressDetails(createdRO, upRO);
        // check for the updated contact details
        checkOrgRoleContactDetails(createdRO, upRO,
                "my.updated.email@mayoclinic.org", "314-213-1245",
                "314-213-1278", "314-213-1123",
                "http://www.updatedmayoclinic.org");
        Assert.assertEquals(ro.getName(), upRO.getName()); // no name changed
        checkOrgRoleAliases("Mayo RO 111", upRO);
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO-CTEP ID
     */
    @Test
    public void testUpdateOrganizationRoleROHavingCtepId() {
        String excepMessage ="";
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a RO first
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        ResearchOrganization createdRO = (ResearchOrganization) response.getOrganizationRole();
        Assert.assertNotNull(createdRO);
        Assert.assertTrue(createdRO instanceof ResearchOrganization);

        // now update the RO details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdRO.setName("Mayo RO 111"); // added to alias, name not change
        createdRO.setCtepId("VA222");
        upRequest.setOrganizationRole(createdRO);
        try {
            orgService.updateOrganizationRole(upRequest);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        
        Assert.assertTrue(excepMessage
                .contains("The OrganizationRole couldn't be updated as CTEP ID VA222 is passed in the request."));
    }
    
    /**
     * Testcase for OrganizationService-updateOrganizationRole-RO -  create a Change Request
     */
    @Test
    public void testUpdateOrganizationRoleRO_create_ChangeRequest() {

        // create a new organization
        Organization organization = createActiveOrganization();

        // create a RO first
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        ResearchOrganization createdRO = (ResearchOrganization) response
                .getOrganizationRole();
        Assert.assertNotNull(createdRO);
        Assert.assertTrue(createdRO instanceof ResearchOrganization);
        
        // set Overridden by CTRPQATester1
        updateOverriddenBy(ResearchOrganization.class, createdRO.getId());

        // now update the RO details
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        String rolUpName ="Mayo RO 111";
        createdRO.setName(rolUpName); // CR should have this name
        createdRO.setType(ResearchOrganizationType.RSB);
        createdRO.setFundingMechanism(FundingMechanism.U_10);
        // update the address
        createdRO.getAddress().set(0, getJaxbAddressList().get(1));
        // update the contact details
        createdRO.getContact().clear(); // clear existing
        createdRO.getContact().addAll(getJaxbUpdatedContactList());// UPDATED
        upRequest.setOrganizationRole(createdRO);
        UpdateOrganizationRoleResponse upResponse = orgService.updateOrganizationRole(upRequest);
        ResearchOrganization upRO = (ResearchOrganization) upResponse.getOrganizationRole();
        
        assertEquals(ResearchOrganizationType.RSB.value(), upRO.getType().value());
        assertEquals("U10", upRO.getFundingMechanism().value());
        checkROChangeRequestDetails(rolUpName, createdRO, "my.updated.email@mayoclinic.org", 
                "314-213-1245", "314-213-1278", "314-213-1123", "http://www.updatedmayoclinic.org");  
    }

    /**
     * Testcase for
     * OrganizationService-updateOrganizationRole-HCF-OrgRoleNotFoundInDB
     */
    @Test
    public void testUpdateOrganizationRoleHCFForOrgRoleNotFoundInDB() {
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setId(999999888888777777l); // Id not present in the DB

        String excepMessage = null;
        UpdateOrganizationRoleRequest request = new UpdateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        try {
            orgService.updateOrganizationRole(request);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("EntityNotFoundException: The OrganizationRole couldn't be updated for ID 999999888888777777 as OrganizationRole is not found in the DB."));
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole- Aliases-HCF
     */
    @Test
    public void testUpdateOrganizationRoleAliasesHCF() {
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        HealthCareFacility createdHCF = (HealthCareFacility) response
                .getOrganizationRole();
        Assert.assertTrue(createdHCF instanceof HealthCareFacility);

        // now update the HCF name
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdHCF.setName("Mayo HCF 111"); // added to alias, name not change
        upRequest.setOrganizationRole(createdHCF);
        UpdateOrganizationRoleResponse upResponse = orgService
                .updateOrganizationRole(upRequest);
        HealthCareFacility upHCF = (HealthCareFacility) upResponse
                .getOrganizationRole();
        Assert.assertEquals(hcf.getName(), upHCF.getName()); // no name changed
        // check for alias
        checkOrgRoleAliases("Mayo HCF 111", createdHCF);

        // now again change name of the HCF
        upRequest = new UpdateOrganizationRoleRequest();
        createdHCF.setName("Mayo HCF 222"); // added to alias, name not change
        upRequest.setOrganizationRole(createdHCF);
        orgService.updateOrganizationRole(upRequest);
        // check for alias
        checkOrgRoleAliases("Mayo HCF 111", createdHCF);
        checkOrgRoleAliases("Mayo HCF 222", createdHCF);

        // now again update HCF but sending same name
        upRequest = new UpdateOrganizationRoleRequest();
        createdHCF.setName(hcf.getName()); // no change in name or alias
        upRequest.setOrganizationRole(createdHCF);
        upResponse = orgService.updateOrganizationRole(upRequest);
        upHCF = (HealthCareFacility) upResponse.getOrganizationRole();
        Assert.assertEquals(hcf.getName(), upHCF.getName()); // no name changed
        // check for alias
        checkOrgRoleAliases("Mayo HCF 111", createdHCF);
        checkOrgRoleAliases("Mayo HCF 222", createdHCF);
    }

    /**
     * Testcase for OrganizationService-updateOrganizationRole- Aliases-RO
     */
    @Test
    public void testUpdateOrganizationRoleAliasesRO() {
        // create a new organization
        Organization organization = createActiveOrganization();

        // create a RO first
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        ResearchOrganization createdRO = (ResearchOrganization) response
                .getOrganizationRole();
        Assert.assertNotNull(createdRO);
        Assert.assertTrue(createdRO instanceof ResearchOrganization);

        // now update the RO name
        UpdateOrganizationRoleRequest upRequest = new UpdateOrganizationRoleRequest();
        createdRO.setName("Mayo RO 111"); // added to alias, name not change
        upRequest.setOrganizationRole(createdRO);
        UpdateOrganizationRoleResponse upResponse = orgService
                .updateOrganizationRole(upRequest);
        ResearchOrganization upRO = (ResearchOrganization) upResponse
                .getOrganizationRole();
        Assert.assertEquals(ro.getName(), upRO.getName()); // no name changed
        // check for alias
        checkOrgRoleAliases("Mayo RO 111", createdRO);

        // now again change name of the HCF
        upRequest = new UpdateOrganizationRoleRequest();
        createdRO.setName("Mayo RO 222"); // added to alias, name not change
        upRequest.setOrganizationRole(createdRO);
        orgService.updateOrganizationRole(upRequest);
        // check for alias
        checkOrgRoleAliases("Mayo RO 111", createdRO);
        checkOrgRoleAliases("Mayo RO 222", createdRO);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId
     */
    @Test
    public void testGetOrganizationRolesByOrgId() {
        // create a organization
        Organization organization = createActiveOrganization();

        // create a role-HCF for that organization
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());
        CreateOrganizationRoleRequest corRequest = new CreateOrganizationRoleRequest();
        corRequest.setOrganizationRole(hcf);
        orgService.createOrganizationRole(corRequest);

        // create a role-RO for that organization
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());
        CreateOrganizationRoleRequest roRequest = new CreateOrganizationRoleRequest();
        roRequest.setOrganizationRole(ro);
        orgService.createOrganizationRole(roRequest);

        // get the Roles by the OrgId.
        GetOrganizationRolesByOrgIdRequest req = new GetOrganizationRolesByOrgIdRequest();
        req.setOrganizationID(organization.getId());
        GetOrganizationRolesByOrgIdResponse res = orgService
                .getOrganizationRolesByOrgId(req);
        List<OrganizationRole> orgRoleList = res.getOrganizationRoleList();
        Assert.assertTrue(orgRoleList.size() == 2);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByOrgId -- for Org
     * not found in DB
     */
    @Test
    public void testGetOrganizationRolesByOrgIdForOrgNotFoundInDB() {
        String excepMessage = null;
        // get the Roles by the OrgId.
        GetOrganizationRolesByOrgIdRequest req = new GetOrganizationRolesByOrgIdRequest();
        req.setOrganizationID(999999888888777777l); // Id not present in the DB
        try {
            orgService.getOrganizationRolesByOrgId(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("The OrganizationRole couldn't be fetched"));
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-HCF
     */
    @Test
    public void testGetOrganizationRoleByIdHCF() {
        // create a organization
        Organization organization = createActiveOrganization();

        // create a role-HCF
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest corRequest = new CreateOrganizationRoleRequest();
        corRequest.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse corResponse = orgService
                .createOrganizationRole(corRequest);

        // get the Role by the RoleId(DB Id)
        GetOrganizationRoleByIdRequest req = new GetOrganizationRoleByIdRequest();
        req.setOrganizationRoleID(corResponse.getOrganizationRole().getId());
        req.setRoleType(RoleType.HEALTH_CARE_FACILITY);
        GetOrganizationRoleByIdResponse res = orgService
                .getOrganizationRoleById(req);
        HealthCareFacility retOrgRole = (HealthCareFacility) res
                .getOrganizationRole();
        Assert.assertEquals(hcf.getName(), retOrgRole.getName());
        Assert.assertEquals(hcf.getCtepId(), retOrgRole.getCtepId());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-OC
     */
    @Test
    public void testGetOrganizationRoleByIdOC() {
        // create a organization
        Organization organization = createActiveOrganization();

        // create a role-OC
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(organization.getId());
        CreateOrganizationRoleRequest corRequest = new CreateOrganizationRoleRequest();
        corRequest.setOrganizationRole(oc);

        CreateOrganizationRoleResponse corResponse = orgService
                .createOrganizationRole(corRequest);
        OrganizationRole orgRole = corResponse.getOrganizationRole();

        // get the Role by the RoleId(DB Id)
        GetOrganizationRoleByIdRequest req = new GetOrganizationRoleByIdRequest();
        req.setOrganizationRoleID(orgRole.getId()); // set the Id
        req.setRoleType(RoleType.OVERSIGHT_COMMITTEE);

        GetOrganizationRoleByIdResponse res = orgService
                .getOrganizationRoleById(req);

        OversightCommittee retOrgRole = (OversightCommittee) res
                .getOrganizationRole();
        Assert.assertTrue(orgRole.getId().longValue() == retOrgRole.getId()
                .longValue());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-RO
     */
    @Test
    public void testGetOrganizationRoleByIdRO() {
        // create a organization
        Organization organization = createActiveOrganization();

        // create a role-RO
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());
        CreateOrganizationRoleRequest roRequest = new CreateOrganizationRoleRequest();
        roRequest.setOrganizationRole(ro);

        CreateOrganizationRoleResponse corResponse = orgService
                .createOrganizationRole(roRequest);
        OrganizationRole orgRole = corResponse.getOrganizationRole();

        // get the Role by the RoleId(DB Id)
        GetOrganizationRoleByIdRequest req = new GetOrganizationRoleByIdRequest();
        req.setOrganizationRoleID(orgRole.getId()); // set the Id
        req.setRoleType(RoleType.RESEARCH_ORGANIZATION);

        GetOrganizationRoleByIdResponse res = orgService
                .getOrganizationRoleById(req);

        ResearchOrganization retOrgRole = (ResearchOrganization) res
                .getOrganizationRole();
        Assert.assertTrue(orgRole.getId().longValue() == retOrgRole.getId()
                .longValue());
        Assert.assertEquals(ro.getName(), retOrgRole.getName());
        Assert.assertEquals(ro.getCtepId(), retOrgRole.getCtepId());
        assertEquals(ro.getType().value(), retOrgRole.getType().value());
        assertEquals(ro.getFundingMechanism().value(), retOrgRole.getFundingMechanism().value());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-HCF
     */
    @Test
    public void testGetOrganizationRoleByIdForRoleNotFoundInDB() {
        // get the Role by the RoleId(DB Id)
        GetOrganizationRoleByIdRequest req = new GetOrganizationRoleByIdRequest();
        req.setOrganizationRoleID(999999888888777777l); // Id not present in the
                                                        // DB
        req.setRoleType(RoleType.HEALTH_CARE_FACILITY);
        GetOrganizationRoleByIdResponse res = orgService
                .getOrganizationRoleById(req);

        Assert.assertNull(res.getOrganizationRole());
    }

    /**
     * Testcase for OrganizationService-getOrganizationRoleById-for Null
     * RoleType
     */
    @Test
    public void testGetOrganizationRoleByIdForNullRoleType() {
        // create a organization
        Organization organization = createActiveOrganization();

        // create a role-HCF
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest corRequest = new CreateOrganizationRoleRequest();
        corRequest.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse corResponse = orgService
                .createOrganizationRole(corRequest);

        String excepMessage = null;
        // get the Role by the RoleId(DB Id)
        GetOrganizationRoleByIdRequest req = new GetOrganizationRoleByIdRequest();
        req.setOrganizationRoleID(corResponse.getOrganizationRole().getId());
        req.setRoleType(null); // RoleType is NULL
        try {
            orgService.getOrganizationRoleById(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{roleType}' is expected"));
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByCtepId
     */    
    @Test
    public void testGetOrganizationRolesByCtepId() {
        String randomCtepId = RandomStringUtils.random(10, true, true);

        // create an organization first
        Organization createdOrg = createActiveOrganization();
        
        // create HCF
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(createdOrg.getId());
        CreateOrganizationRoleRequest corReq = new CreateOrganizationRoleRequest();
        corReq.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse corRes = orgService.createOrganizationRole(corReq);      
        
        // now set the CTEP ID for this HCF
        createHcfCtepId(corRes.getOrganizationRole().getId(), randomCtepId);

        // create RO
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(createdOrg.getId());        
        corReq = new CreateOrganizationRoleRequest();
        corReq.setOrganizationRole(ro);        
        corRes = orgService.createOrganizationRole(corReq);    
        
        // now set the CTEP ID for this RO
        createROCtepId(corRes.getOrganizationRole().getId(), randomCtepId);

        // get the Roles by the CtepId.
        GetOrganizationRolesByCtepIdRequest req = new GetOrganizationRolesByCtepIdRequest();
        req.setCtepID(randomCtepId);
        GetOrganizationRolesByCtepIdResponse res = orgService.getOrganizationRolesByCtepId(req);
        List<OrganizationRole> orgRoleList = res.getOrganizationRoleList();
        Assert.assertTrue(orgRoleList.size() == 2);
    }

    /**
     * Testcase for OrganizationService-getOrganizationRolesByCtepId
     */
    @Test
    public void testGetOrganizationRolesByCtepIdForNotCtepIdNotFoundInDB() {
        String randomCtepId = RandomStringUtils.random(78, true, true);

        // get the Roles by the CtepId.
        GetOrganizationRolesByCtepIdRequest req = new GetOrganizationRolesByCtepIdRequest();
        req.setCtepID(randomCtepId); // CtepId not found in DB
        GetOrganizationRolesByCtepIdResponse res = orgService
                .getOrganizationRolesByCtepId(req);
        List<OrganizationRole> orgRoleList = res.getOrganizationRoleList();
        Assert.assertTrue(orgRoleList.size() == 0);
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-HCF
     */
    @Test
    public void testChangeOrganizationRoleStatusHCF() {
        Organization organization = createActiveOrganization();

        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();
        Assert.assertTrue(orgRole instanceof HealthCareFacility);

        // now change the status
        ChangeOrganizationRoleStatusRequest req = new ChangeOrganizationRoleStatusRequest();
        req.setOrganizationRoleID(orgRole.getId());
        req.setRoleType(RoleType.HEALTH_CARE_FACILITY);
        req.setStatus(EntityStatus.NULLIFIED);
        ChangeOrganizationRoleStatusResponse res = orgService
                .changeOrganizationRoleStatus(req);

        Assert.assertEquals(EntityStatus.NULLIFIED.value(), res.getOrganizationRole().getStatus().value());
        checkHCFStatusInDB(res.getOrganizationRole().getId(), EntityStatus.NULLIFIED.value());
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-OC
     */
    @Test
    public void testChangeOrganizationRoleStatusOC() {
        Organization organization = createActiveOrganization();

        // create a OC first
        OversightCommittee oc = getOversightCommitteeObj();
        oc.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(oc);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();
        Assert.assertTrue(orgRole instanceof OversightCommittee);

        // now change the status
        ChangeOrganizationRoleStatusRequest req = new ChangeOrganizationRoleStatusRequest();
        req.setOrganizationRoleID(orgRole.getId());
        req.setRoleType(RoleType.OVERSIGHT_COMMITTEE);
        req.setStatus(EntityStatus.NULLIFIED);
        ChangeOrganizationRoleStatusResponse res = orgService
                .changeOrganizationRoleStatus(req);

        Assert.assertEquals(EntityStatus.NULLIFIED.value(), res.getOrganizationRole().getStatus().value());

        checkOverCommStatusInDB(res.getOrganizationRole().getId(), EntityStatus.NULLIFIED.value());
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-RO
     */
    @Test
    public void testChangeOrganizationRoleStatusRO() {
        Organization organization = createActiveOrganization();

        // create a RO first
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();
        Assert.assertTrue(orgRole instanceof ResearchOrganization);
        
        // create the alias for the RO               
        createROAliasesData(orgRole.getId());

        // now change the status
        ChangeOrganizationRoleStatusRequest req = new ChangeOrganizationRoleStatusRequest();
        req.setOrganizationRoleID(orgRole.getId());
        req.setRoleType(RoleType.RESEARCH_ORGANIZATION);
        req.setStatus(EntityStatus.NULLIFIED);
        ChangeOrganizationRoleStatusResponse res = orgService
                .changeOrganizationRoleStatus(req);

        Assert.assertEquals(EntityStatus.NULLIFIED.value(), res.getOrganizationRole().getStatus().value());

        checkROStatusInDB(res.getOrganizationRole().getId(), EntityStatus.NULLIFIED.value());
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-Role not found
     * in DB
     */
    @Test
    public void testChangeOrganizationRoleStatusForNotFoundInDB() {
        String excepMessage = null;
        ChangeOrganizationRoleStatusRequest req = new ChangeOrganizationRoleStatusRequest();

        req.setOrganizationRoleID(999999888888777777l); // Id not present in DB
        req.setRoleType(RoleType.HEALTH_CARE_FACILITY);
        req.setStatus(EntityStatus.INACTIVE);
        try {
            orgService.changeOrganizationRoleStatus(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Couldn't update the OrganizationRole Status for orgRoleID 999999888888777777 as OrganizationRole is not found in the DB"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-RoleType is
     * NULL
     */
    @Test
    public void testChangeOrganizationRoleStatusForNullRoleType() {
        Organization organization = createActiveOrganization();
        String excepMessage = null;
        // create a RO first
        ResearchOrganization ro = getResearchOrganizationObj();
        ro.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(ro);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();

        // now change the status, RoleType is NULL
        ChangeOrganizationRoleStatusRequest req = new ChangeOrganizationRoleStatusRequest();
        req.setOrganizationRoleID(orgRole.getId());
        req.setRoleType(null); // RoleType is NULL
        req.setStatus(EntityStatus.INACTIVE);
        try {
            orgService.changeOrganizationRoleStatus(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("One of '{roleType}' is expected"));
    }

    /**
     * Testcase for OrganizationService-changeOrganizationStatus-for invalid
     * transition
     */
    /**
     * Testcase for
     * OrganizationService-changeOrganizationStatus-InvalidStatusTransition
     */
    @Test
    public void testChangeOrganizationRoleStatusForInvalidTransition() throws SQLException {

        String excepMessage = null;
        Organization organization = createActiveOrganization();

        // create a HCF first
        HealthCareFacility hcf = getHealthCareFacilityObj();
        hcf.setOrganizationId(organization.getId());

        CreateOrganizationRoleRequest request = new CreateOrganizationRoleRequest();
        request.setOrganizationRole(hcf);
        CreateOrganizationRoleResponse response = orgService
                .createOrganizationRole(request);
        OrganizationRole orgRole = response.getOrganizationRole();
        Assert.assertTrue(orgRole instanceof HealthCareFacility);

        activateRole(orgRole);

        // now change the status to PENDING
        ChangeOrganizationRoleStatusRequest req = new ChangeOrganizationRoleStatusRequest();
        req.setOrganizationRoleID(orgRole.getId());
        req.setRoleType(RoleType.HEALTH_CARE_FACILITY);
        req.setStatus(EntityStatus.PENDING);
        try {
            orgService.changeOrganizationRoleStatus(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Illegal curation transition from ACTIVE to PENDING"));
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    private Organization createActiveOrganization() {
        // create an organization first
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        org.setStatus(EntityStatus.ACTIVE);
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization retOrg = response.getOrganization();
        return retOrg;
    }

    private HealthCareFacility getHealthCareFacilityObj() {
        HealthCareFacility hcf = new HealthCareFacility();        
        hcf.setName("Mayo HCF");
        hcf.setOrganizationId(1l);
        hcf.setStatus(EntityStatus.PENDING);
        hcf.getAddress().add(getJaxbAddressList().get(0));
        hcf.getContact().addAll(getJaxbContactList());
        return hcf;
    }

    private OversightCommittee getOversightCommitteeObj() {
        OversightCommittee oc = new OversightCommittee();
        oc.setType(OversightCommitteeType.ETHICS_COMMITTEE);
        oc.setOrganizationId(1l);
        oc.setStatus(EntityStatus.ACTIVE);
        oc.getAddress().add(getJaxbAddressList().get(0));
        oc.getContact().addAll(getJaxbContactList());
        return oc;
    }

    private ResearchOrganization getResearchOrganizationObj() {
        ResearchOrganization ro = new ResearchOrganization();        
        ro.setName("Mayo RO");
        ro.setOrganizationId(1l);
        ro.setType(ResearchOrganizationType.NWK);
        ro.setFundingMechanism(FundingMechanism.G_11);
        ro.setStatus(EntityStatus.PENDING);
        ro.getAddress().add(getJaxbAddressList().get(0));
        ro.getContact().addAll(getJaxbContactList());
        return ro;
    }

    private void activateRole(OrganizationRole role) throws SQLException {
        role.setStatus(EntityStatus.ACTIVE);

        String sql = String.format(
                "update %s set status='ACTIVE' where id=?",
                role.getClass().getSimpleName().toLowerCase()
        );

        if (conn == null) {
            conn = DataGeneratorUtil.getJDBCConnection();
        }

        PreparedStatement statement = null;

        try {
            statement = DataGeneratorUtil.getJDBCConnection().prepareStatement(sql);
            statement.setLong(1, role.getId());
            statement.executeUpdate();
        } finally {
            DbUtils.closeQuietly(statement);
        }
    }
}
