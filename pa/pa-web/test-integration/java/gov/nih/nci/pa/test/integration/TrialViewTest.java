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
        
        long count = getRecordCount(trial.id,"DATA");
        assertTrue(count ==0);
        
        assertTrue(selenium.isTextPresent("No Data Discrepancies found."));
        assertTrue(selenium.isTextPresent("No Trial Documents exist on the trial."));
    }
    
    private long getRecordCount(long trialId, String typeCode)
            throws SQLException {
        String sql;
        
            sql = "select count(*) from study_notes where study_protocol_identifier ="+trialId+" and study_note_type='"+typeCode+"'";
       
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
