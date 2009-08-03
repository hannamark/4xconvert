package gov.nih.nci.coppa.test.integration.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.test.remoteapi.RemoteApiUtils;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;

import javax.naming.NamingException;

public class CreatePersonWithClinicalResearchStaffTest extends AbstractPoWebTest {

    public void testPerson() throws EntityValidationException, NamingException, URISyntaxException, InterruptedException {
        loginAsCurator();
        String orgId = createRemoteOrgWithApostrophe();
        openCreatePerson();
        createPerson();
        accessClinicalResearchStaffScreen();
        addClinicalResearchStaffAffiliateOrg(orgId);
    }

   
    private void accessClinicalResearchStaffScreen() {
        clickAndWait("link=Manage Clinical Research Staff(s)");
        verifyTrue(selenium.isTextPresent("Clinical Research Staff Information"));
    }
    
    private void addClinicalResearchStaffAffiliateOrg(String orgId) throws InterruptedException {
        clickAndWaitButton("add_button");
        verifyTrue(selenium.isTextPresent("Clinical Research Staff Role Information"));
        clickAndWaitButton("select_scoper");
        selenium.type("duplicateOrganizationForm_criteria_organization_id", orgId);
        clickAndWaitButton("submitDuplicateOrganizationForm");
        clickAndWaitButton("mark_as_dup_".concat(orgId));
        pause(5000);
        verifyTrue(selenium.isTextPresent("Org'With'Apos (" + orgId + ")"));
    }
    
    private String createRemoteOrgWithApostrophe() throws EntityValidationException, NamingException, URISyntaxException {
      
        OrganizationDTO dto = new OrganizationDTO();
        dto.setName(RemoteApiUtils.convertToEnOn("Org'With'Apos"));
        dto.setPostalAddress(RemoteApiUtils.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        dto.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        dto.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        dto.getTelecomAddress().getItem().add(url);
        Ii id = RemoteServiceHelper.getOrganizationEntityService().createOrganization(dto);
        return id.getExtension();
    }

    
}
