package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.po.webservices.types.Organization;
import junit.framework.Assert;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
