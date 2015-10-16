package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.test.integration.AbstractPaSeleniumTest.TrialInfo;
import gov.nih.nci.pa.test.integration.support.Batch;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * 
 * @author Reshma Koganti
 *
 */
@Batch(number = 1)
public class NCISpecificInformationTest extends AbstractPaSeleniumTest {
     @Test
     public void testConsortiaTrialCategoryCodeValues() throws SQLException {
         loginAsSuperAbstractor();
          TrialInfo info = createAcceptedTrial(true);
          logoutUser();
          selectTrialInPA(info);
          assertTrue(selenium.isElementPresent("link=NCI Specific Information"));
          clickAndWait("link=NCI Specific Information");
          assertTrue(selenium.isElementPresent("id=nciSpecificInformationWebDTO.consortiaTrialCategoryCode"));
          selenium.select("id=nciSpecificInformationWebDTO.consortiaTrialCategoryCode","label=No - Institutional");
          clickAndWait("link=Save");
          assertTrue(selenium.isTextPresent("Message. Record Updated."));
          String valueCode = getConsortiaTrialCategory(info);
          assertTrue(StringUtils.equalsIgnoreCase(valueCode, "INSTITUTIONAL"));
     }
     
     private String getConsortiaTrialCategory(TrialInfo trial)
             throws SQLException {
         QueryRunner runner = new QueryRunner();
         return (String) runner
                 .query(connection,
                         "select consortia_trial_category from study_protocol "
                 + "where identifier="
                 + (trial.id != null ? trial.id : getTrialIdByNciId(trial.nciID)),
                         new ArrayHandler())[0];
     }
}
