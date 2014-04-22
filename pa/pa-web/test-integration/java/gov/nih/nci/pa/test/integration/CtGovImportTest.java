package gov.nih.nci.pa.test.integration;

import java.sql.SQLException;

import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class CtGovImportTest extends AbstractPaSeleniumTest {

    @Test
    public void testImportNCT01721876() throws Exception {
        importAndVerify("NCT01721876");
    }
    
    @Test
    public void testImportNCT01875705() throws Exception {
        importAndVerify("NCT01875705");
    }
    
    @Test
    public void testImportNCT01806064() throws Exception {
        importAndVerify("NCT01806064");
    }

    
    @Test
    public void testImportNCT01756729() throws Exception {
        importAndVerify("NCT01756729");
    }

    
    @Test
    public void testImportNCT01576406() throws Exception {
        importAndVerify("NCT01576406");
    }

    
    @Test
    public void testImportNCT00738699() throws Exception {
        importAndVerify("NCT00738699");
    }

    
    @Test
    public void testImportNCT00038610() throws Exception {
        importAndVerify("NCT00038610");
    }


    private void importAndVerify(String nctID) throws SQLException {
        deactivateTrialByNctId(nctID);
        loginAsSuperAbstractor();
        clickAndWait("id=importCtGovMenuOption");
        selenium.type("id=nctID", nctID);
        clickAndWait("link=Search Studies");
        assertTrue(selenium.isTextPresent("One item found."));
        clickAndWait("link=Import Trial");
        assertTrue(selenium
                .isTextPresent("Trial "
                        + nctID
                        + " has been imported and registered in CTRP system successfully."));
        clickAndWait("id=trialSearchMenuOption");
        selenium.type("id=identifier", nctID);
        selenium.select("id=identifierType", "NCT");
        clickAndWait("link=Search");
        assertTrue(selenium.isTextPresent("One item found"));
        clickAndWait("xpath=//table[@id='row']//tr[1]//td[1]/a");

        assertTrue(selenium.getText("id=displaySubmitterLink").contains(
                "ClinicalTrials.gov Import"));
        assertTrue(selenium.getText("id=td_CTGOV_value").contains(nctID));

    }

}
