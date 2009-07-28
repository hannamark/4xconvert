package gov.nih.nci.coppa.test.integration.test;

/**
 * Adds a Person and performs a Name, Contact, Address and Id search for the added
 *     organization.
 *   Additionally this will test - No rows returned and nothing selected tests
 * @author Bill Mason
 *
 */
public class SearchPersonTest extends AbstractPoWebTest {
    private String lastName ="lastName" + Long.toString(System.currentTimeMillis());
    private String firstName = "Jakson";
    private String email = "email@email.com";
    private String poId = null;

    public void testSearchPerson(){
        loginAsCurator();
        openCreatePerson();

        createPerson("PENDING", "Dr", firstName, "L", lastName, "III",
                getAddress(), email, "703-111-2345", "http://www.createperson.com", "703-111-1234");
        openSearchPerson();
        verifySearchForm();
        searchByName();
        searchByEmail();
        noRowsReturnedTest();
        nothingSelectedTest();
       }

    private void nothingSelectedTest() {
        clear();
        clickAndWaitButton("submitSearchOrganizationForm");
        assertTrue("At least one criterion message is missing", selenium.isTextPresent("At least one criterion must be provided"));
        assertTrue("Nothing found message is missing", selenium.isTextPresent("Nothing found to display"));
        assertTrue("No items found message is missing", selenium.isTextPresent("No items found"));
    }

    private void noRowsReturnedTest() {
        selenium.type("searchPersonForm_criteria_org", "jf yfnfufigkmi");
        clickAndWaitButton("submitSearchOrganizationForm");
        assertTrue(selenium.isTextPresent("Nothing found to display"));
        assertTrue(selenium.isTextPresent("No items found"));
    }

    private void searchByEmail() {
        selenium.type("searchPersonForm_criteria_email", email);
        clickAndWaitButton("submitSearchOrganizationForm");
        verify();
    }

    private void searchByName() {
        selenium.type("searchPersonForm_criteria_firstName", firstName);
        selenium.type("searchPersonForm_criteria_lastName", lastName);
        clickAndWaitButton("submitSearchOrganizationForm");
        verify();
    }

    private void verify() {
        int thirdColumn = 3;
        int row = getRow(lastName, thirdColumn);
        if (row == -1){
            fail("Did not find " + lastName + " in search results");
        }else{
            setPoId(row);
            assertEquals(poId, selenium.getTable("row."+row+".0"));
            assertEquals(firstName, selenium.getTable("row."+row+".2"));
            assertEquals(lastName, selenium.getTable("row."+row+".3"));
            assertEquals("PENDING", selenium.getTable("row."+row+".8"));
            assertEquals("NONE", selenium.getTable("row."+row+".9"));
            clear();
        }
    }


    private void setPoId(int row) {
        if (poId == null) {
            poId = selenium.getTable("row."+row+".0");
        }
    }

    private void verifySearchForm() {
        assertTrue("Person first name is missing", selenium.isElementPresent("searchPersonForm_criteria_firstName"));
        assertTrue("Person last name is missing", selenium.isElementPresent("searchPersonForm_criteria_lastName"));
        assertTrue("Person email is missing", selenium.isElementPresent("searchPersonForm_criteria_email"));
        assertTrue("Organization Affiliation is missing", selenium.isElementPresent("searchPersonForm_criteria_org"));
        assertTrue("Investigator CTEP Identifier is missing", selenium.isElementPresent("searchPersonForm_criteria_ctepId"));
    }

    private void clear() {
        openSearchPerson();
     }
}
