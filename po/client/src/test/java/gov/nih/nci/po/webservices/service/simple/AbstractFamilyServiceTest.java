package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.CreateOrganizationResponse;
import gov.nih.nci.po.webservices.service.simple.soap.organization.OrganizationService;
import gov.nih.nci.po.webservices.service.simple.soap.organization.OrganizationService_Service;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationRequest;
import gov.nih.nci.po.webservices.service.simple.soap.organization.UpdateOrganizationResponse;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Organization;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.namespace.QName;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Assert;

/**
 * This is a base class with common code to be used across REST/SOAP based
 * Integration test class for FamilyService.
 * 
 * @author Rohit Gupta
 * 
 */
public abstract class AbstractFamilyServiceTest extends AbstractBaseTest {

    protected static Connection conn = null;
    private static ResultSetHandler<Object[]> h = null;
    private static OrganizationService orgService = null; // need to create Org
    protected static Organization org1 = null;
    protected static Organization org2 = null;
    protected static long familyId1, familyId2;
    protected static long famOrgRelId1, famOrgRelId2, famOrgRelId3, famOrgRelId4;
    private static long orgRelId1, orgRelId2, orgRelId3, orgRelId4;

    public static void setUpFamilyServiceData() throws Exception {
        // get OrganizationService
        QName orgServiceName = new QName(
                "http://soap.simple.service.webservices.po.nci.nih.gov/organization/",
                "OrganizationService");
        URL orgUrl = new URL(TstProperties.getOrgServiceURL());
        OrganizationService_Service orgServ = new OrganizationService_Service(
                orgUrl, orgServiceName);
        orgService = orgServ.getOrganizationServicePort();

        // getting the database connection
        conn = DataGeneratorUtil.getJDBCConnection();
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

        // initialize the DB Ids
        familyId1 = getNextSequenceId();
        familyId2 = getNextSequenceId();

        famOrgRelId1 = getNextSequenceId();
        famOrgRelId2 = getNextSequenceId();
        famOrgRelId3 = getNextSequenceId();
        famOrgRelId4 = getNextSequenceId();

        orgRelId1 = getNextSequenceId();
        orgRelId2 = getNextSequenceId();
        orgRelId3 = getNextSequenceId();
        orgRelId4 = getNextSequenceId();

        // create few Organizations
        org1 = createActiveOrganization("Royal Perth Hospital");
        org2 = createActiveOrganization("Sacred Heart Hospital");

        // create few Family
        createFamilyData();

        // create few FamilyMember (BO-FamilyOrganizationRelationship)
        createFamilyMemberData();

        // create few FamilyMemberRelationship(BO-OrganizationRelationship)
        createFamilyMemberRelationshipData();
    }

    protected static Organization createActiveOrganization(String orgName) {
        Organization org = new Organization();
        org.setName(orgName);
        org.setStatus(EntityStatus.PENDING);
        org.setAddress(getJaxbAddress());
        CreateOrganizationRequest request = new CreateOrganizationRequest();
        request.setOrganization(org);
        CreateOrganizationResponse response = orgService
                .createOrganization(request);
        Organization retOrg = response.getOrganization();

        // make the organization ACTIVE
        retOrg.setStatus(EntityStatus.ACTIVE);
        UpdateOrganizationRequest upRequest = new UpdateOrganizationRequest();
        upRequest.setOrganization(retOrg);
        UpdateOrganizationResponse res = orgService
                .updateOrganization(upRequest);
        return res.getOrganization();
    }

    protected static long getNextSequenceId() {
        long nextDbId = 0;
        try {
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,
                    "select nextval('hibernate_sequence')", h);
            nextDbId = ((Long) result[0]).longValue();
        } catch (SQLException e) {
            Assert.fail("Exception occured inside getNextSequenceId. The exception is: "
                    + e);
        }
        return nextDbId;
    }

    protected static void createFamilyData() {
        // create few Family records
        try {
            QueryRunner queryRunner = new QueryRunner();
            queryRunner
                    .update(conn,
                            "insert into family(id,statuscode, startdate, name) values(?, 'ACTIVE','2009-01-01', 'Arizona Cancer Center')",
                            familyId1);
            queryRunner
                    .update(conn,
                            "insert into family(id,statuscode, startdate, name) values(?, 'ACTIVE','2009-01-01', 'Duke Cancer Institute')",
                            familyId2);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createFamilyData. The exception is: "
                    + e);
        }
    }

    protected static void createFamilyMemberData() {
        // create few FamilyMember(BO-FamilyOrganizationRelationship)
        try {
            QueryRunner queryRunner = new QueryRunner();
            // insert record for Family1--and--2 other Orgs combination
            queryRunner
                    .update(conn,
                            "insert into familyorganizationrelationship(id,startdate, functionaltype, family_id, organization_id) values(?, '2009-01-01', 'ORGANIZATIONAL', ?, ?)",
                            famOrgRelId1, familyId1, org1.getId());
            queryRunner
                    .update(conn,
                            "insert into familyorganizationrelationship(id,startdate, functionaltype, family_id, organization_id) values(?, '2009-01-01', 'ORGANIZATIONAL', ?, ?)",
                            famOrgRelId2, familyId1, org2.getId());

            // insert record for Family2--and--2 other Orgs combination
            queryRunner
                    .update(conn,
                            "insert into familyorganizationrelationship(id,startdate, functionaltype, family_id, organization_id) values(?, '2009-01-01', 'ORGANIZATIONAL', ?, ?)",
                            famOrgRelId3, familyId2, org1.getId());
            queryRunner
                    .update(conn,
                            "insert into familyorganizationrelationship(id,startdate, functionaltype, family_id, organization_id) values(?, '2009-01-01', 'ORGANIZATIONAL', ?, ?)",
                            famOrgRelId4, familyId2, org2.getId());
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createFamilyMemberData. The exception is: "
                    + e);
        }
    }

    protected static void createFamilyMemberRelationshipData() {
        // create few FamilyMemberRelationship(BO-OrganizationRelationship)
        try {
            // created a related Organization
            Organization relOrg = createActiveOrganization("Alexander Fleming Cancer Center");

            QueryRunner queryRunner = new QueryRunner();
            // insert record for Family1--and--2 other Orgs combination
            queryRunner
                    .update(conn,
                            "insert into organizationrelationship(id, startdate, hierarchicaltype, relatedorganization_id, family_id, organization_id) values(?, '2009-01-01', 'PEER', ?, ?, ?);",
                            orgRelId1, relOrg.getId(), familyId1, org1.getId());
            queryRunner
                    .update(conn,
                            "insert into organizationrelationship(id, startdate, hierarchicaltype, relatedorganization_id, family_id, organization_id) values(?, '2009-01-01', 'PARENT', ?, ?, ?);",
                            orgRelId2, relOrg.getId(), familyId1, org2.getId());

            // insert record for Family2(id-1001)--2 other Orgs combination
            queryRunner
                    .update(conn,
                            "insert into organizationrelationship(id, startdate, hierarchicaltype, relatedorganization_id, family_id, organization_id) values(?, '2009-01-01', 'PEER', ?, ?, ?);",
                            orgRelId3, relOrg.getId(), familyId2, org1.getId());
            queryRunner
                    .update(conn,
                            "insert into organizationrelationship(id, startdate, hierarchicaltype, relatedorganization_id, family_id, organization_id) values(?, '2009-01-01', 'PARENT', ?, ?, ?);",
                            orgRelId4, relOrg.getId(), familyId2, org2.getId());
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createFamilyMemberData. The exception is: "
                    + e);
        }
    }

    protected static gov.nih.nci.po.webservices.types.Address getJaxbAddress() {
        gov.nih.nci.po.webservices.types.Address address1 = new Address();
        address1.setLine1("13621 Leagcy Circle");
        address1.setLine2("Apt G");
        address1.setCity("Herndon");
        address1.setStateOrProvince("VA");
        address1.setCountry(CountryISO31661Alpha3Code.USA);
        address1.setPostalcode("20171");

        return address1;
    }
}
