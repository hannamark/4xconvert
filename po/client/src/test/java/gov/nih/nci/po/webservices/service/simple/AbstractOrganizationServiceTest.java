package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.po.webservices.types.Organization;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Assert;

/**
 * This is a base class with common code to be used across REST/SOAP based
 * Integration test class for OrganizationService.
 * 
 * @author Rohit Gupta
 * 
 */
public abstract class AbstractOrganizationServiceTest extends AbstractBaseTest {

    protected Connection conn = null;
    private ResultSetHandler<Object[]> h = null;
    protected Organization org1 = null;
    protected Organization org2 = null;
    protected long aliasId1, aliasId2;

    protected void createOrgAliasesData(long organizationId) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            // insert data into "Alias" table
            aliasId1 = getNextSequenceId();
            queryRunner.update(conn,
                    "insert into alias(id,value) values(?, ?)", aliasId1,
                    "test org alias 1");

            aliasId2 = getNextSequenceId();
            queryRunner.update(conn,
                    "insert into alias(id,value) values(?, ?)", aliasId2,
                    "test org alias 2");

            // insert data into "organization_alias" table
            queryRunner
                    .update(conn,
                            "insert into organization_alias(organization_id,alias_id,idx) values(?, ?, 0)",
                            organizationId, aliasId1);
            queryRunner
                    .update(conn,
                            "insert into organization_alias(organization_id,alias_id,idx) values(?, ?, 1)",
                            organizationId, aliasId2);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside createOrgAliasesData. The exception is: "
                    + e);
        }
    }
    
    protected void createROAliasesData(long roId) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            // insert data into "Alias" table
            long aliasId1 = getNextSequenceId();
            queryRunner.update(conn,
                    "insert into alias(id,value) values(?, ?)", aliasId1,
                    "test RO alias 1");            

            // insert data into "ro_alias" table
            queryRunner
                    .update(conn,
                            "insert into ro_alias(ro_id,alias_id,idx) values(?, ?, 0)",
                            roId, aliasId1);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside createOrgAliasesData. The exception is: "
                    + e);
        }
    }

    protected void setUpOrganizationServiceData() {
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
    }
    
    protected void createIdentifiedOrganization(long organizationId, String ctepId) {
        try {
            setUpOrganizationServiceData();
            QueryRunner queryRunner = new QueryRunner();            
            Object[] result = queryRunner.query(conn,
                    "select id from organization where name = 'Cancer Therapy Evaluation Program'", h);
            long ctepOrgId = ((Long) result[0]).longValue();
            
            // insert data into "IdentifiedOrganization" table
            long idenOrgId = getNextSequenceId();
            queryRunner.update(conn,
                    "insert into identifiedorganization(id,assigned_identifier_displayable,assigned_identifier_extension,"
                    + "assigned_identifier_identifier_name,assigned_identifier_reliability, assigned_identifier_root, assigned_identifier_scope,"
                    + "status, statusdate, scoper_id, player_id) values(?, 'TRUE', ?, 'CTEP ID','VRF','2.16.840.1.113883.3.26.6.2','OBJ','ACTIVE','2008-12-30',?,?)", 
                    idenOrgId, ctepId, ctepOrgId, organizationId);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createIdentifiedOrganization. The exception is: "
                    + e);
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }

    private long getNextSequenceId() {
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

}
