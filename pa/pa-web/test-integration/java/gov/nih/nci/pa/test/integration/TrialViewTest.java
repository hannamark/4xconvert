package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;


public class TrialViewTest extends ResultsReportingStudyContactsTest {

    protected static final int OP_WAIT_TIME = SystemUtils.IS_OS_LINUX ? 15000
            : 2000;
    //String baseUrl=null;
    
  public TrialViewTest() {
      super();
      baseUrl ="/pa/protected/trialViewquery.action?studyProtocolId=";
  }
   

    @SuppressWarnings("deprecation")
    @Test
    public void testViewPageDisplayed() throws SQLException, ParseException {
        TrialInfo trial = createAcceptedTrial();
        loginAsSuperAbstractor();
        searchAndSelectTrial(trial.title);
        openAndWait(baseUrl+trial.id);
        
        long count = getRecordCount(trial.id);
        assertTrue(count ==0);
        
        assertTrue(selenium.isTextPresent("No study record changes found."));
        assertTrue(selenium.isTextPresent("No trial documents exist on the trial"));
    }
    
    private long getRecordCount(long trialId)
            throws SQLException {
        String sql;
        
            sql = "select count(*) from study_record_change where study_protocol_identifier ="+trialId;
       
        long trialCount =0;
        QueryRunner runner = new QueryRunner();
        final List<Object[]> results = runner.query(connection, sql,
                new ArrayListHandler());
        for (Object[] row : results) {
             trialCount = (long) row[0];
           
        }
        return trialCount;
    }
   
}
