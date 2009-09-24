package gov.nih.nci.coppa.test.integration.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class CreateResearchOrganizationTest extends OrganizationWebTest {
    
    /**
     * Verifies PO-924 via UI
     */
    public void testVerifyReasearchOrganizationTypeOrder() throws Exception {
        loginAsCurator();
        openCreateOrganization();
        
        // create a new org
        addOrganization();
        openSearchOrganization();

        // search the same org, this will set the id. 
        searchByName(false);
        
        // click on item to curate
        clickAndWait("//a[@id='org_id_" + poId + "']/span/span");
        waitForPageToLoad();
        waitForTelecomFormsToLoad();
        assertEquals(orgName, selenium.getValue("curateEntityForm_organization_name"));   
        
        openAndWait("po-web/protected/roles/organizational/ResearchOrganization/start.action?organization=" + poId);
        clickAndWait("//a[@id='add_button']/span/span");
      
        List<String> selectOptions = Arrays.asList(selenium.getSelectOptions("role.typeCode"));
        TreeSet<String> ts = new TreeSet<String>(selectOptions);

        assertTrue(selectOptions.size() > 5);
        
        Iterator<String> selectOptionsIterator = selectOptions.iterator();
        Iterator<String> tsIterator = ts.iterator();
        while(selectOptionsIterator.hasNext()) {
          assertEquals(selectOptionsIterator.next(), tsIterator.next());
        }
    }
}
