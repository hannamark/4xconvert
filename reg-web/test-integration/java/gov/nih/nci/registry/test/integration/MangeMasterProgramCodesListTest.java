package gov.nih.nci.registry.test.integration;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 *Integration test for Manage Program Codes List screen.
 */
public class MangeMasterProgramCodesListTest  extends AbstractRegistrySeleniumTest {

    /**
     * Test the program codes menu items
     * @throws Exception exception
     */
    @Test
    public void testProgramCodesDataTableWithFilteringPagingAndSorting() throws Exception {
        loginAndAcceptDisclaimer();
        waitForElementToBecomeVisible(By.linkText("Administration"), 2);
        hoverLink("Administration");
        waitForElementToBecomeVisible(By.linkText("Program Codes"), 2);
        assertTrue(selenium.isTextPresent("Program Codes"));
        hoverLink("Program Codes");
        assertTrue(selenium.isTextPresent("Manage Master List"));
        clickAndWait("link=Manage Master List");
        recreateFamilies();
        associateProgramCodesToFamilies();
        assertTrue(selenium.isTextPresent("Program Code"));
        assertTrue(selenium.isTextPresent("Program Name"));
        assertTrue(selenium.isTextPresent("Search:"));
        assertTrue(selenium.isTextPresent("Cancer Program1"));
        assertTrue(selenium.isTextPresent("PG1"));
        // verify that program codes are sorted
        assertEquals("PG1",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[1]/td[1]")).getText());
        assertEquals("PG10",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[2]/td[1]")).getText());
        assertEquals("PG11",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[3]/td[1]")).getText());
        assertEquals("PG12",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[4]/td[1]")).getText());
        assertEquals("PG6",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[9]/td[1]")).getText());
        assertEquals("PG7",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr[10]/td[1]")).getText());
        // verify for INACTIVE program codes
        assertTrue(selenium.isTextPresent("(INACTIVE) Cancer Program2"));
        assertTrue(selenium.isTextPresent("(INACTIVE) Cancer Program6"));
        //verify pagination present
        selenium.isTextPresent("Showing 1 to 10 of 12 ");
      // filter program codes.
        selenium.typeKeys("//div[@id='programCodesTable_filter']/descendant::label/descendant::input", "11");
      //should see filtered data only
        assertEquals("PG11",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr/td[1]")).getText());
        assertEquals("Cancer Program11",driver.findElement(By.xpath("//table[@id='programCodesTable']/tbody/tr/td[2]")).getText());
        assertTrue(selenium.isTextPresent("Showing 1 to 1 of 1 (filtered from 12 total entries)"));
        logoutUser();
    }
    

    private void recreateFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from family");
        qr.update(connection, String.format("INSERT INTO family( identifier, po_id, " +
                "rep_period_end, rep_period_len_months)VALUES (1, 1, '%s', 12)", date(180)));
    }

    private void associateProgramCodesToFamilies() throws Exception {
        QueryRunner qr = new QueryRunner();
        qr.update(connection, "delete from program_code");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG1', 'Cancer Program1', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG2', 'Cancer Program2', 'INACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG3', 'Cancer Program3', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG4', 'Cancer Program4', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG5', 'Cancer Program5', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG6', 'Cancer Program6', 'INACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG7', 'Cancer Program7', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG8', 'Cancer Program8', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG9', 'Cancer Program9', 'ACTIVE')");
        qr.update(connection, "insert into program_code (family_id, program_code, program_name, status_code) " +
                "values (1,'PG10', 'Cancer Program10', 'INACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG11', 'Cancer Program11', 'ACTIVE')");
        qr.update(connection, "insert into program_code ( family_id, program_code, program_name, status_code) " +
                "values (1,'PG12', 'Cancer Program12', 'ACTIVE')");

    }

}
