package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.FixtureDataUtil;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.ResearchOrganization;

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
    
    protected void createOrgAliasesData(long organizationId) {
            FixtureDataUtil.createOrgAliasesData(organizationId);
    }
    
    protected void createROAliasesData(long roId) {
        try {
            setUpOrganizationServiceData(); 
            QueryRunner queryRunner = new QueryRunner();
            // insert data into "Alias" table
            long aliasId1 = FixtureDataUtil.getNextSequenceId(conn, h);
            queryRunner.update(conn,
                    "insert into alias(id,value) values(?, ?)", aliasId1, "test RO alias 1");            

            // insert data into "ro_alias" table
            queryRunner.update(conn,
                            "insert into ro_alias(ro_id,alias_id,idx) values(?, ?, 0)", roId, aliasId1);

        } catch (SQLException e) {
            Assert.fail("Exception occured inside createOrgAliasesData for roId + "+roId + ".The exception is: "+ e);
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }    
    
    protected void createIdentifiedOrganization(long organizationId, String ctepId) {
        try {
            setUpOrganizationServiceData();
            QueryRunner queryRunner = new QueryRunner();            
            Object[] result = queryRunner.query(conn,
                    "select id from organization where name = 'Cancer Therapy Evaluation Program'", h);
            long ctepOrgId = ((Long) result[0]).longValue();
            
            // insert data into "IdentifiedOrganization" table
            long idenOrgId = FixtureDataUtil.getNextSequenceId(conn, h);
            queryRunner.update(conn,
                    "insert into identifiedorganization(id,assigned_identifier_displayable,assigned_identifier_extension,"
                    + "assigned_identifier_identifier_name,assigned_identifier_reliability, assigned_identifier_root, assigned_identifier_scope,"
                    + "status, statusdate, scoper_id, player_id) values(?, 'TRUE', ?, 'CTEP ID','VRF','2.16.840.1.113883.3.26.6.2','OBJ','ACTIVE','2008-12-30',?,?)", 
                    idenOrgId, ctepId, ctepOrgId, organizationId);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createIdentifiedOrganization. The exception is: " + e);
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
    
    protected void createHcfCtepId(long hcfId, String ctepId) {
        try {
            setUpOrganizationServiceData();
            QueryRunner queryRunner = new QueryRunner(); 
            
            // insert data into "hcf_otheridentifier" table
            queryRunner.update(conn,
                    "insert into hcf_otheridentifier(hcf_id, displayable , extension, identifier_name, reliability, root, scope) values(?, 'TRUE', ?, 'CTEP ID','VRF','2.16.840.1.113883.3.26.6.2','OBJ')", 
                    hcfId, ctepId);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createHcfCtepId. The exception is: " + e);
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
    
    protected void createROCtepId(long roId, String ctepId) {
        try {
            setUpOrganizationServiceData();
            QueryRunner queryRunner = new QueryRunner(); 
            
            // insert data into "ro_otheridentifier" table
            queryRunner.update(conn,
                    "insert into ro_otheridentifier(ro_id, displayable , extension, identifier_name, reliability, root, scope) values(?, 'TRUE', ?, 'CTEP ID','VRF','2.16.840.1.113883.3.26.6.2','OBJ')", 
                    roId, ctepId);
        } catch (SQLException e) {
            Assert.fail("Exception occured inside createROCtepId. The exception is: " + e);
        }finally{
            DbUtils.closeQuietly(conn);
        }
    }
    
    /**
     * This method is used to Override the Entity.
     * @param clazz Class
     * @param id EntityId
     */
    protected void updateOverriddenBy(Class clazz, long id){
        try {
            setUpOrganizationServiceData();
            QueryRunner queryRunner = new QueryRunner();
            long johnDoeUserId = getCTRPQATester1UserId();            
          
            if (Organization.class.isAssignableFrom(clazz)) {
                queryRunner.update(conn, "update organization set overridden_by_id = ?", johnDoeUserId);                
            } else if (HealthCareFacility.class.isAssignableFrom(clazz)) {
                queryRunner.update(conn, "update healthcarefacility set overridden_by_id = ?", johnDoeUserId);                   
            } else if (ResearchOrganization.class.isAssignableFrom(clazz)) {
                queryRunner.update(conn, "update researchorganization set overridden_by_id = ?", johnDoeUserId);
            }         
        } catch (SQLException e) {
            Assert.fail("Exception occured inside updateOverriddenBy for id + "+id + ".The exception is: "+ e);
        }finally{
            DbUtils.closeQuietly(conn);
        }        
    }    
    
    private long getCTRPQATester1UserId() {
        long userId = 0;
        try {
            QueryRunner queryRunner = new QueryRunner();
            Object[] result = queryRunner.query(conn,"select user_id from csm_user where login_name like '%CTRPQATester1%'", h);
            userId = ((Long) result[0]).longValue();
        } catch (SQLException e) {
            Assert.fail("Exception occured inside getJohnDoeUserId. The exception is: "+ e);
        }
        return userId;
    }

}
