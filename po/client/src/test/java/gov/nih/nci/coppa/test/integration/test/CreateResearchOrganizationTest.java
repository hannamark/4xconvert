package gov.nih.nci.coppa.test.integration.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class CreateResearchOrganizationTest extends OrganizationWebTest {
    
    private final String SELECT_A_ROLE_TYPE = "CCOP";
    private final String SELECT_ROLE_WITH_ONE_FUNDING = "Cancer Center";
    private final String SELECT_ROLE_WITH_MULTIPLE_FUNDING = "Network";
    private final String CODE_FOR_ONE_FUNDING = "P30";
    private final String VALUE_FOR_MULTIPLE_FUNDING = "--Select a Funding Mechanism--";
    
    private final String FUNDING_MECH_TO_LOOK_FOR = "U10 - Cooperative Clinical Research Cooperative Agreements";
    
    
    /**
     * Verifies PO-924 via UI
     */
    public void testVerifyReasearchOrganizationTypeOrder() throws Exception {
        getToCreateResearchOrganization();
        
        List<String> selectOptions = Arrays.asList(selenium.getSelectOptions("role.typeCode"));
        TreeSet<String> ts = new TreeSet<String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });
        
        ts.addAll(selectOptions);

        assertTrue(selectOptions.size() > 5);
        
        Iterator<String> selectOptionsIterator = selectOptions.iterator();
        Iterator<String> tsIterator = ts.iterator();
        while(selectOptionsIterator.hasNext()) {
          assertEquals(selectOptionsIterator.next(), tsIterator.next());
        }
    }
 
    /**
     * Verifies PO-979 via UI
     */
    public void testVerifyReasearchOrganizationFundingMechanismHasDescription() throws Exception {
        getToCreateResearchOrganization();
        
        selenium.select("role.typeCode", "label=" + SELECT_A_ROLE_TYPE);
        
        Thread.sleep(1000);
        
        List<String> selectOptions = Arrays.asList(selenium.getSelectOptions("role.fundingMechanismSelect"));
        assertTrue(selectOptions.contains(FUNDING_MECH_TO_LOOK_FOR));        
    }
        
    public void getToCreateResearchOrganization() throws Exception {
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
        
    }
    
    public void testFundingMechanismIsSelectedIfOnlyOneOption() throws Exception {
        getToCreateResearchOrganization();
        
        selenium.select("role.typeCode", SELECT_ROLE_WITH_ONE_FUNDING);
        Thread.sleep(1000);
            
        String selectedLabel = selenium.getSelectedLabel("role.fundingMechanismSelect");
        assertTrue(selectedLabel.startsWith(CODE_FOR_ONE_FUNDING));
        
        selenium.select("role.typeCode", SELECT_ROLE_WITH_MULTIPLE_FUNDING);
        Thread.sleep(1000);
        
        selectedLabel = selenium.getSelectedLabel("role.fundingMechanismSelect");
        assertEquals(selectedLabel, VALUE_FOR_MULTIPLE_FUNDING);        
    }
    
}
