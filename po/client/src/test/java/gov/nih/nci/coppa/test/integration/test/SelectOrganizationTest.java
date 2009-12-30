package gov.nih.nci.coppa.test.integration.test;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;


public class SelectOrganizationTest extends OrganizationWebTest {

    
    
    /**
     * This test verifies the selector content and behavior outside of the modal (submodal) popup framework. 
     */
    public void testVerifyDetailsOutsideSubmodalPopup() {
        loginAsCurator();
        openCreateOrganization();
        
        // create a new org
        addOrganization();
        poId =  selenium.getText("wwctrl_organization.id");
        poId.trim();
        assertNotEquals("null", poId);
        
        openSelectOrganization();

        //input search results
        selenium.type("duplicateOrganizationForm_criteria_organization_id", poId);
        clickAndWaitButton("submitDuplicateOrganizationForm");
        //wait for div to load
        waitForElementById("mark_as_dup_" + poId, 5);

        selenium.click("link="+poId);
        //wait for div to load
        waitForElementById("selector_org_back_to_search_results", 5);
        
        //verify that the org details are correct
        verifyTrue(selenium.isElementPresent("link=exact:http://sample.url.org"));
        verifyTrue(selenium.isTextPresent("tty-number"));
        verifyTrue(selenium.isTextPresent("123-fax-0908"));
        verifyTrue(selenium.isTextPresent("899-090-0987"));
        verifyTrue(selenium.isTextPresent("emailAddress@example.com"));
        //address info
        verifyEquals("30345", selenium.getText("wwctrl_address.postalCode"));
        verifyEquals("GA", selenium.getText("wwctrl_address.stateOrProvince"));
        verifyEquals("Atlanta", selenium.getText("wwctrl_address.cityOrMunicipality"));
        verifyEquals("160 Delivery Ave.", selenium.getText("wwctrl_address.deliveryAddressLine"));
        verifyEquals("400 First Street", selenium.getText("wwctrl_address.streetAddressLine"));
        verifyEquals("United States", selenium.getText("wwctrl_address.country"));
        //org details
        verifyEquals(orgName, selenium.getText("wwctrl_organization.name"));
        verifyEquals(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), selenium.getText("wwctrl_organization.statusDate"));
        verifyEquals(poId, selenium.getText("wwctrl_organization.id"));

        selenium.click("//a[@id='selector_org_back_to_search_results']/span/span");
        selenium.click("//a[@id='selector_org_back_to_search_form_top']/span/span");
        verifyEquals(poId, selenium.getValue("duplicateOrganizationForm_criteria_organization_id"));

    }

    private void openSelectOrganization() {
        selenium.open("/po-web/protected/selector/organization/search.action");

        verifyEquals("Find Organization(s)", selenium.getTitle());
        verifyEquals("Basic Identifying Information", selenium.getText("//form[@id='duplicateOrganizationForm']/div/div[1]/div[1]/h2"));
        verifyEquals("Address Information", selenium.getText("//form[@id='duplicateOrganizationForm']/div/div[1]/div[2]/h2"));
    }
}
