package gov.nih.nci.coppa.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Assert;

/**
 * This class has common utility method to load fixture data.
 * 
 * @author Rohit Gupta
 * 
 */
public class FixtureDataUtil {

    private static Connection conn = null;
    private static ResultSetHandler<Object[]> h = null;

    private static void setDBSetup() {
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

    public static void createOrgAliasesData(long organizationId) {
        try {
            setDBSetup();            
            long aliasId1, aliasId2;
            QueryRunner queryRunner = new QueryRunner();
            // insert data into "Alias" table
            aliasId1 = getNextSequenceId(conn, h);            
            queryRunner.update(conn,
                    "insert into alias(id,value) values(?, ?)", aliasId1, "test org alias 1");

            aliasId2 = getNextSequenceId(conn, h);
            queryRunner.update(conn,
                    "insert into alias(id,value) values(?, ?)", aliasId2, "test org alias 2");

            // insert data into "organization_alias" table
            queryRunner.update(conn,
                            "insert into organization_alias(organization_id,alias_id,idx) values(?, ?, 0)", organizationId, aliasId1);
            queryRunner.update(conn,
                            "insert into organization_alias(organization_id,alias_id,idx) values(?, ?, 1)", organizationId, aliasId2);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside createOrgAliasesData. The exception is: "+ e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static long getNextSequenceId(Connection conn, ResultSetHandler<Object[]> h) {
        long nextDbId = 0;
        try {
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn, "select nextval('hibernate_sequence')", h);
            nextDbId = ((Long) result[0]).longValue();
        } catch (SQLException e) {
            Assert.fail("Exception occured inside getNextSequenceId. The exception is: " + e);
        } 
        return nextDbId;
    }
}
