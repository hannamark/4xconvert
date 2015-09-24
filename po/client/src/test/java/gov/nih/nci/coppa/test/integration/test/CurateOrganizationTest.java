/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.coppa.test.integration.test;

import gov.nih.nci.coppa.test.DataGeneratorUtil;
import gov.nih.nci.coppa.test.FixtureDataUtil;
import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.TestConvertHelper;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.commons.dbutils.QueryRunner;
import org.openqa.selenium.By;

public class CurateOrganizationTest extends AbstractCurateTest {

    private static final String CTEP_ORG_ROOT = "2.16.840.1.113883.3.26.6.2";

    private static final int DEFAULT_TEXT_COL_LENGTH = 160;

    private final Map<Ii, OrganizationDTO> catalogOrgs = new HashMap<Ii, OrganizationDTO>();
    private OrganizationEntityServiceRemote orgService;
    private HealthCareFacilityCorrelationServiceRemote hcfService;
    protected OrganizationEntityServiceRemote getOrgService() {
        return orgService;
    }

    protected HealthCareFacilityCorrelationServiceRemote getHcfService() {
        return hcfService;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        if (orgService == null) {
            orgService = RemoteServiceHelper.getOrganizationEntityService();
        }

        if (hcfService == null) {
            hcfService = RemoteServiceHelper.getHealthCareFacilityCorrelationService();
        }
    }
    
    @SuppressWarnings("deprecation")
    public void testNullifyOrgAndSeeNoEmailOrPhoneError() throws Exception {
        final QueryRunner r = new QueryRunner();

        // Create orgs & roles.
        String nullifyIntoName = "Nullify Into " + UUID.randomUUID().toString();
        Ii nullifyIntoID = remoteCreateAndCatalog(create(nullifyIntoName));
        r.update(conn, "update organization set status='ACTIVE' where id="
                + nullifyIntoID.getExtension());

        String beingNullifiedName = "Being Nullified "
                + UUID.randomUUID().toString();
        Ii beingNullifiedID = remoteCreateAndCatalog(create(beingNullifiedName));
        Ii hcfID = remoteCreateHcfWithCtepId(beingNullifiedID);
        r.update(conn, "update organization set status='ACTIVE' where id="
                + beingNullifiedID.getExtension());
        r.update(conn,
                "update healthcarefacility set status='ACTIVE' where id="
                        + hcfID.getExtension());

        // Create Person.
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        Ii personID = remoteCreateAndCatalog(createPerson(firstName, lastName));
        r.update(conn, "update person set status='ACTIVE' where id="
                + personID.getExtension());
        
        // Link Person and Org via HCP
        HealthCareProviderDTO hcpDTO = makeHcp(beingNullifiedID, personID);
        Ii hcpID = RemoteServiceHelper.getHealthCareProviderCorrelationService().createActiveCorrelation(hcpDTO);
        r.update(conn,
                "update healthcareprovider set status='ACTIVE' where id="
                        + hcpID.getExtension());
        
        // now remove email and phone number from HCP record. This should prevent
        // nullification.
        r.update(conn,
                "delete from hcp_email where hcp_id="+hcpID.getExtension());
        r.update(conn,
                "delete from hcp_phone where hcp_id="+hcpID.getExtension());
        
        System.out.println("Nullifying "+beingNullifiedName+" into "+nullifyIntoName);
        
        // Finally, try to nullify.
        loginAsCurator();
        searchForOrgByPoId(beingNullifiedID);
        clickAndWait("org_id_" + beingNullifiedID.getExtension());
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
        
        s.select("curateEntityForm.organization.statusCode", "NULLIFIED");
        s.click("select_duplicate");
        waitForElementToBecomeAvailable(By.id("popupFrame"), 10);
        s.selectFrame("popupFrame");
        waitForElementToBecomeAvailable(By.id("submitDuplicateOrganizationForm"), 10);
        s.type("duplicateOrganizationForm_criteria_id", nullifyIntoID.getExtension());
        clickAndWait("submitDuplicateOrganizationForm");
        waitForElementToBecomeVisible(By.id("mark_as_dup_"+nullifyIntoID.getExtension()), 30);
        clickAndWait("mark_as_dup_"+nullifyIntoID.getExtension());
        driver.switchTo().defaultContent();
        s.chooseOkOnNextConfirmation();
        clickAndWait("save_button");
        
        // We now should be on Curate Error page.
        assertEquals(
                "PO: Persons and Organizations - Organization Details : Error",
                s.getTitle());
        assertTrue(s
                .isTextPresent("Message: Either a phone number or an email address is required., "
                        + "Entity/Role: HealthCareProvider, Entity/Role ID: "
                        + hcpID.getExtension()
                        + ", Scoper Organization ID: "
                        + nullifyIntoID.getExtension()
                        + ", "
                        + "Player ID: "+personID.getExtension()+", "
                        + "Value with error: gov.nih.nci.po.data.bo.HealthCareProvider"));

    }
    
    @SuppressWarnings("deprecation")
    public void testNullifyOrgAndSeeBadEmailError() throws Exception {
        final QueryRunner r = new QueryRunner();

        // Create orgs & roles.
        String nullifyIntoName = "Nullify Into " + UUID.randomUUID().toString();
        Ii nullifyIntoID = remoteCreateAndCatalog(create(nullifyIntoName));
        r.update(conn, "update organization set status='ACTIVE' where id="
                + nullifyIntoID.getExtension());

        String beingNullifiedName = "Being Nullified "
                + UUID.randomUUID().toString();
        Ii beingNullifiedID = remoteCreateAndCatalog(create(beingNullifiedName));
        Ii hcfID = remoteCreateHcfWithCtepId(beingNullifiedID);
        r.update(conn, "update organization set status='ACTIVE' where id="
                + beingNullifiedID.getExtension());
        r.update(conn,
                "update healthcarefacility set status='ACTIVE' where id="
                        + hcfID.getExtension());

        // Create Person.
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        Ii personID = remoteCreateAndCatalog(createPerson(firstName, lastName));
        r.update(conn, "update person set status='ACTIVE' where id="
                + personID.getExtension());
        
        // Link Person and Org via HCP
        HealthCareProviderDTO hcpDTO = makeHcp(beingNullifiedID, personID);
        Ii hcpID = RemoteServiceHelper.getHealthCareProviderCorrelationService().createActiveCorrelation(hcpDTO);
        r.update(conn,
                "update healthcareprovider set status='ACTIVE' where id="
                        + hcpID.getExtension());
        
        // now mess up email address.
        r.update(conn,
                "update email set value='bademailaddr@' where id=(select max(id) from email)");
               
        System.out.println("Nullifying "+beingNullifiedName+" into "+nullifyIntoName);
        
        // Finally, try to nullify.
        loginAsCurator();
        searchForOrgByPoId(beingNullifiedID);
        clickAndWait("org_id_" + beingNullifiedID.getExtension());
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
        
        s.select("curateEntityForm.organization.statusCode", "NULLIFIED");
        s.click("select_duplicate");
        waitForElementToBecomeAvailable(By.id("popupFrame"), 10);
        s.selectFrame("popupFrame");
        waitForElementToBecomeAvailable(By.id("submitDuplicateOrganizationForm"), 10);
        s.type("duplicateOrganizationForm_criteria_id", nullifyIntoID.getExtension());
        clickAndWait("submitDuplicateOrganizationForm");
        waitForElementToBecomeVisible(By.id("mark_as_dup_"+nullifyIntoID.getExtension()), 30);
        clickAndWait("mark_as_dup_"+nullifyIntoID.getExtension());
        driver.switchTo().defaultContent();
        s.chooseOkOnNextConfirmation();
        clickAndWait("save_button");
        
        // We now should be on Curate Error page.
        assertEquals(
                "PO: Persons and Organizations - Organization Details : Error",
                s.getTitle());
        assertTrue(s
                .isTextPresent("Message: (fieldName) is not a well-formed email address, Entity/Role: HealthCareProvider, Entity/Role ID: "
                        + hcpID.getExtension()
                        + ", Scoper Organization ID: "
                        + nullifyIntoID.getExtension()
                        + ", "
                        + "Player ID: "+personID.getExtension()+", "
                        + "Value with error: bademailaddr@."));

    }

    
    
    protected HealthCareProviderDTO makeHcp(Ii orgID, Ii personID) throws Exception {
        HealthCareProviderDTO dto = new HealthCareProviderDTO();
        dto.setScoperIdentifier(orgID);
        dto.setPlayerIdentifier(personID);
        dto.setTelecomAddress(new DSet<Tel>());
        dto.getTelecomAddress().setItem(new HashSet<Tel>());
        
        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-654"));
        dto.getTelecomAddress().getItem().add(ph1);
        
        TelEmail email = new TelEmail();
        email.setValue(new URI(TelEmail.SCHEME_MAILTO+ ":hcpemail@example.com"));
        dto.getTelecomAddress().getItem().add(email);
        return dto;
    }

    public void testCurateNewOrg() throws Exception {        
        /* create a new org via remote API. */
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

        loginAsCurator();

        searchForOrgByPoId(id);

        // click on item to curate
        clickAndWait("org_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present

        String tempName = name.concat(name);
        selenium.type("curateEntityForm_organization_name", tempName);
        assertEquals(tempName, selenium.getValue("curateEntityForm_organization_name"));
        clickAndWait("reset_button");
        
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));
        assertTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present

        // verify the presence of required indicator in create screen.
        verifyRequiredIndicators(true);

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();
        
        verifyAlias();

        saveAsActive(id);
    }

    private void searchForOrgByPoId(Ii id) {
        openSearchOrganization();
        selenium.type("searchOrganizationForm_criteria_id", id.getExtension());
        clickAndWait("submitSearchOrganizationForm");
    }

    private void verifyRequiredIndicators(boolean expectedValue) {
        /*verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm.organization.postalAddress.country");
        verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_organization_postalAddress_streetAddressLine");
        verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_organization_postalAddress_cityOrMunicipality");
        */
        verifyPresenceOfRequiredIndicator(expectedValue, "organization.postalAddress.stateOrProvince");
        //verifyPresenceOfRequiredIndicator(expectedValue, "curateEntityForm_organization_postalAddress_postalCode");
    }

    public void testCurateOrgWithCRs() throws Exception {
        
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

        OrganizationDTO orgDTO = getOrgService().getOrganization(id);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        orgDTO.getTelecomAddress().getItem().add(email);
        getOrgService().updateOrganization(orgDTO);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI(TelPhone.SCHEME_TEL + ":123-456-7890"));
        orgDTO.getTelecomAddress().getItem().add(phone);
        getOrgService().updateOrganization(orgDTO);
        TelPhone fax = new TelPhone();
        fax.setValue(new URI(TelPhone.SCHEME_X_TEXT_FAX + ":234-567-8901"));
        orgDTO.getTelecomAddress().getItem().add(fax);
        getOrgService().updateOrganization(orgDTO);
        TelPhone tty = new TelPhone();
        tty.setValue(new URI(TelPhone.SCHEME_X_TEXT_TEL + ":345-678-9012"));
        orgDTO.getTelecomAddress().getItem().add(tty);
        getOrgService().updateOrganization(orgDTO);

        loginAsCurator();

        searchForOrgByPoId(id);

        // click on item to curate
        clickAndWait("org_id_" + id.getExtension());
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();
        
        verifyAlias();

        saveAsActive(id);
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToActive() throws Exception {
        
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();

        saveAsActive(id);
    }

   

public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullifyWithDuplicateId() throws Exception {
        
        /* create an org to serve as a duplicate */
        Ii dupId = createNewOrg();
        FixtureDataUtil.createOrgAliasesData(Long.parseLong(dupId.getExtension()));
        Ii id = createNewOrg();
        FixtureDataUtil.createOrgAliasesData(Long.parseLong(id.getExtension()));

        loginAsCurator();
        searchForOrgByPoId(id);
        clickAndWait("org_id_" + id.getExtension());
        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        /* wait for in-browser js to execute via select's onchange event */
        pause(1000);
        selenium.isVisible("//div[@id='duplicateOfDiv']");
        clickAndWait("select_duplicate");
        selenium.selectFrame("popupFrame");
        selenium.type("duplicateOrganizationForm_criteria_id", dupId.getExtension());

        /* search for dups */
        selenium.click("//a[@id='submitDuplicateOrganizationForm']/span/span");
        /* wait for results to load */
        waitForElementById("mark_as_dup_" + dupId.getExtension(), 30);
        /* select record to use at duplicate */
        clickAndWait("mark_as_dup_" + dupId.getExtension());

        driver.switchTo().defaultContent();

        saveAsNullified(id);

        /* Verify PO-469 */
        try {
            getOrgService().getOrganization(id);
            fail("Expected NullifiedEntityException for Ii.extension=" + id.getExtension());
        } catch (NullifiedEntityException e) {
            Map<Ii, Ii> nullifiedEntities = e.getNullifiedEntities();
            assertEquals(1, nullifiedEntities.keySet().size());
            assertEquals(id.getExtension(), nullifiedEntities.keySet().iterator().next().getExtension());
            assertEquals(dupId.getExtension(), nullifiedEntities.values().iterator().next().getExtension());
        }
    }

    private Ii createNewOrg() throws EntityValidationException, URISyntaxException, CurationException {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name)); // 'createdBy' is set to 'curator' there
        return id;
    }

    public void testCurateNewOrgThenCurateAfterRemoteUpdateToNullifyWithNoDuplicateId() throws Exception {
        
        Ii orgId = createNewOrgWithCtepRoleThenCurateAsActive();
        openAndWait("po-web/protected/organization/curate/start.action?organization.id=" + orgId.getExtension());

        waitForTelecomFormsToLoad();

        // method exits on certain page
        assertEquals("PO: Persons and Organizations - Organization Details", selenium.getTitle());
        
    }


    public void testCurateNewOrgThenCurateAfterRemoteUpdateToInactive() throws Exception {        
        Ii id = curateNewOrgThenCurateAfterRemoteUpdate();
        saveAsInactive(id);
    }
    /**
     * Testcase for Override functionality https://tracker.nci.nih.gov/browse/PO-7335
     * 
     */
    public void testOverrideOrg() throws Exception {        
        /* create a new org via remote API. */
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name)); // 'createdBy' is set to 'curator' there

        // Step1:: Org not overridden, Org creator logs in.
        loginAsCurator(); // user who created the Org
        searchForOrgByPoId(id); // search for the just created Org        
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));
        verifyTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present
        verifyTrue(selenium.isTextPresent("PO Curator"));
        verifyTrue(selenium.isElementPresent("//div[@id='wwlbl_overriddenBy']")); // 'overriddenBy' should be present
        verifyTrue(selenium.isTextPresent("Not Overridden"));
        assertFalse(selenium.isElementPresent("//button[@type='button' and @id='override_button']")); // 'override button' shouldn't be present
        
        String tempName = name.concat(name);
        selenium.type("curateEntityForm_organization_name", tempName);
        assertEquals(tempName, selenium.getValue("curateEntityForm_organization_name"));
        clickAndWait("reset_button");
        
        waitForTelecomFormsToLoad();
        waitForAliasFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));
        verifyRequiredIndicators(true); // verify the presence of required indicator in create screen.
        verifyPostalAddress(ENTITYTYPE.organization);
        verifyTelecom();        
        verifyAlias();
        
        verifyTrue(selenium.isTextPresent("Not Overridden"));     
        assertFalse(selenium.isElementPresent("//button[@type='button' and @id='override_button']")); // 'override button' shouldn't be present
        verifyFalse(selenium.isTextPresent("Override"));
        
        saveAsActive(id);
        logoutUser(); //'curator' logs out
        
        // Step2:: Org not overridden, other curator (who didn't create the Org) logs in.
        loginAsJohnDoe(); // some other curator
        searchForOrgByPoId(id);         
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForPageToLoad();
        verifyTrue(selenium.isElementPresent("//div[@id='wwlbl_createdBy']")); // 'createdBy' should be present
        verifyTrue(selenium.isTextPresent("PO Curator"));
        verifyTrue(selenium.isElementPresent("//div[@id='wwlbl_overriddenBy']")); // 'overriddenBy' should be present
        verifyTrue(selenium.isTextPresent("Not Overridden"));
        verifyTrue(selenium.isElementPresent("//div[@id='wwlbl_organization.name']")); // OrgName label is present
        verifyFalse(selenium.isElementPresent("curateEntityForm_organization_name")); // OrgName 'text box' shouldn't be present (Non Editable)
        
        clickAndWait("override_button"); // click on Override button
        
        verifyFalse(selenium.isTextPresent("Not Overridden")); 
        verifyTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the Org
        verifyTrue(selenium.isEditable("curateEntityForm_organization_name")); // OrgName should be editable
        selenium.type("curateEntityForm_organization_name", "Org updated name ABC");
        verifyFalse(selenium.isElementPresent("//button[@id='override_button']")); // 'override button' should NOT be present
        verifyFalse(selenium.isTextPresent("Override"));
        clickAndWait("save_button"); 
        logoutUser(); //'John Doe' logs out
        
        //Step3: Curator who Overrode the Org logs in
        loginAsJohnDoe(); // curator who overrode the Org
        searchForOrgByPoId(id);         
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForPageToLoad();
        verifyTrue(selenium.isElementPresent("curateEntityForm_organization_name")); // OrgName 'text box' should be present (Editable)
        verifyTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the Org
        logoutUser(); //'John Doe' logs out
        
        //Step4: Curator who created the Org logs in (but Org was Overrode by other user)
        loginAsCurator(); 
        searchForOrgByPoId(id);         
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForPageToLoad();  
        verifyTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the Org
        verifyFalse(selenium.isElementPresent("curateEntityForm_organization_name")); // OrgName 'text box' shouldn't be present (Non Editable)
        assertTrue(selenium.isTextPresent("Override"));
        clickAndWait("override_button"); // click on Override button
        verifyTrue(selenium.isTextPresent("PO Curator")); // curator has overridden the Org
        verifyTrue(selenium.isElementPresent("curateEntityForm_organization_name")); // OrgName 'text box' should be present (Editable)
    }

    /**
     * Testcase for Override functionality https://tracker.nci.nih.gov/browse/PO-7335
     * Organization is having a ChangeRequest also.
     */
    public void testOrverrideOrgWithCRs() throws Exception {        
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

        // create a CR
        OrganizationDTO orgDTO = getOrgService().getOrganization(id);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        orgDTO.getTelecomAddress().getItem().add(email);
        getOrgService().updateOrganization(orgDTO);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI(TelPhone.SCHEME_TEL + ":123-456-7890"));
        orgDTO.getTelecomAddress().getItem().add(phone);
        getOrgService().updateOrganization(orgDTO);
        TelPhone fax = new TelPhone();
        fax.setValue(new URI(TelPhone.SCHEME_X_TEXT_FAX + ":234-567-8901"));
        orgDTO.getTelecomAddress().getItem().add(fax);
        getOrgService().updateOrganization(orgDTO);
        TelPhone tty = new TelPhone();
        tty.setValue(new URI(TelPhone.SCHEME_X_TEXT_TEL + ":345-678-9012"));
        orgDTO.getTelecomAddress().getItem().add(tty);
        getOrgService().updateOrganization(orgDTO);

        // Step1 : Curator who created the Org, logs in
        loginAsCurator();
        searchForOrgByPoId(id);        
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        // Verify that CR is present
        verifyTrue(selenium.isTextPresent("Change Request Information"));
        verifyTrue(selenium.isTextPresent("Copy")); // copy button
        verifyFalse(selenium.isTextPresent("Override"));
        logoutUser(); //'curator' logs out
        
        // Step2 : Org not overridden, other curator (who didn't create the Org) logs in.
        loginAsJohnDoe(); // some other curator
        searchForOrgByPoId(id);         
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForPageToLoad();
        verifyFalse(selenium.isElementPresent("curateEntityForm_organization_name")); // OrgName 'text box' shouldn't be present (Non Editable)
        verifyFalse(selenium.isTextPresent("Copy")); // copy button not present
        verifyTrue(selenium.isTextPresent("Override")); //Override button present
        
        clickAndWait("override_button"); // click on Override button
        verifyTrue(selenium.isTextPresent("Copy")); // copy button
        verifyTrue(selenium.isTextPresent("jdoe01")); // JohnDoe has overridden the Org
        verifyTrue(selenium.isEditable("curateEntityForm_organization_name")); // OrgName should be editable
        selenium.type("curateEntityForm_organization_name", "Org updated name ABC");
        clickAndWait("save_button"); 
        logoutUser(); //'John Doe' logs out
    }
    
    /**
     * Testcase for adding comment to an Org having a CR https://tracker.nci.nih.gov/browse/PO-7856
     */
    public void testAddCommentToOrgWithCRs() throws Exception {        
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));

        // create a CR
        OrganizationDTO orgDTO = getOrgService().getOrganization(id);
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:another.email@example.com"));
        orgDTO.getTelecomAddress().getItem().add(email);
        getOrgService().updateOrganization(orgDTO);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI(TelPhone.SCHEME_TEL + ":123-456-7890"));
        orgDTO.getTelecomAddress().getItem().add(phone);
        getOrgService().updateOrganization(orgDTO);
        TelPhone fax = new TelPhone();
        fax.setValue(new URI(TelPhone.SCHEME_X_TEXT_FAX + ":234-567-8901"));
        orgDTO.getTelecomAddress().getItem().add(fax);
        getOrgService().updateOrganization(orgDTO);
        TelPhone tty = new TelPhone();
        tty.setValue(new URI(TelPhone.SCHEME_X_TEXT_TEL + ":345-678-9012"));
        orgDTO.getTelecomAddress().getItem().add(tty);
        getOrgService().updateOrganization(orgDTO);
        
        // Org not overridden, other curator (who didn't create the Org) logs in.
        loginAsJohnDoe(); // some other curator
        searchForOrgByPoId(id);         
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForPageToLoad();
        verifyFalse(selenium.isElementPresent("curateEntityForm_organization_name")); // OrgName 'text box' shouldn't be present (Non Editable)
        // Verify that CR is present
        verifyTrue(selenium.isTextPresent("Change Request Information"));
        verifyFalse(selenium.isTextPresent("Copy")); // copy button not present
        
        // add a comment
        selenium.type("curateEntityForm.organization.commentsText", "test comment by John");     
        clickAndWait("save_button"); 
        logoutUser(); //'John Doe' logs out
        
        // logic again & access the Org. The CR should still be there.
        loginAsJohnDoe(); // some other curator
        searchForOrgByPoId(id);         
        clickAndWait("org_id_" + id.getExtension()); // click on item to curate
        waitForPageToLoad();
        // Verify that CR is present
        verifyTrue(selenium.isTextPresent("Change Request Information"));
        logoutUser(); //'John Doe' logs out
    }

    private Ii createNewOrgThenCurateAsActive() throws EntityValidationException, URISyntaxException, CurationException {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name)); // 'createdBy' is set to 'curator' there

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        searchForOrgByPoId(id);

        // click on item to curate
        clickAndWait("org_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();
        
//        verifyAlias();

        saveAsActive(id);
        return id;
    }

    private Ii createNewOrgWithCtepRoleThenCurateAsActive() throws EntityValidationException, URISyntaxException, CurationException {
        // create a new org via remote API.
        String name = DataGeneratorUtil.words(DEFAULT_TEXT_COL_LENGTH, 'Y', 10);
        Ii id = remoteCreateAndCatalog(create(name));
        remoteCreateHcfWithCtepId(id);

        if (!isLoggedIn()) {
            loginAsCurator();
        }

        searchForOrgByPoId(id);

        // click on item to curate
        clickAndWait("org_id_" + id.getExtension());
        waitForTelecomFormsToLoad();
        assertEquals(name, selenium.getValue("curateEntityForm_organization_name"));

        verifyPostalAddress(ENTITYTYPE.organization);

        verifyTelecom();
        
        
        verifyAlias();

        return id;
    }

    private Ii curateNewOrgThenCurateAfterRemoteUpdate() throws EntityValidationException, NullifiedEntityException,
            URISyntaxException, CurationException {
        Ii id = createNewOrgThenCurateAsActive();

        OrganizationDTO proposedState = remoteGetOrganization(id);
        String newCrName = "a realistic name";
        proposedState.setName(TestConvertHelper.convertToEnOn(newCrName));
        remoteUpdate(proposedState);

        searchForOrgByPoId(id);

        // click on item to curate
        clickAndWait("org_id_" + id.getExtension());
        waitForTelecomFormsToLoad();

        // method exits on certain page
        assertEquals("PO: Persons and Organizations - Organization Details - Comparison", selenium.getTitle());

        return id;
    }

    private void saveAsActive(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=ACTIVE");
        clickAndWaitSaveButton();
        assertEquals("PO: Persons and Organizations - Organization Details", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsInactive(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=INACTIVE");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();        
        assertEquals("PO: Persons and Organizations - Organization Details", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullified(Ii id) {
        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();       
        clickAndWaitSaveButton();        
        assertEquals("PO: Persons and Organizations - Organization Details", selenium.getTitle());
        assertFalse(selenium.isElementPresent("//a[@id='org_id_" + id.getExtension() + "']/span/span"));
    }

    private void saveAsNullifiedFail(Ii id) throws InterruptedException {
        selenium.select("curateEntityForm.organization.statusCode", "label=NULLIFIED");
        selenium.chooseOkOnNextConfirmation();
        clickAndWaitSaveButton();        
        pause(1000);
        assertTrue(selenium.isTextPresent("A duplicate Organization must be provided."));
    }

    private Ii remoteCreateAndCatalog(OrganizationDTO org) throws EntityValidationException, CurationException {
        Ii id = getOrgService().createOrganization(org);    
        Long orgId= IiConverter.convertToLong(id);
        setCreatedByInOrg(orgId);
        org.setIdentifier(id);
        catalogOrgs.put(id, org);
        return id;
    }
    
    

    private Ii remoteCreateHcfWithCtepId(Ii orgId) throws EntityValidationException, CurationException, URISyntaxException {
        Ii id = this.getHcfService().createCorrelation(createHcfFromCtep(orgId));
        return id;
    }

    private OrganizationDTO create(String name) throws URISyntaxException {
        return create(name, TestConvertHelper.createAd("123 abc ave.", null, "mycity", "VA", "12345", "USA"));
    }

    private OrganizationDTO create(String name, Ad postalAddress) throws URISyntaxException {
        OrganizationDTO org = new OrganizationDTO();
        org.setName(TestConvertHelper.convertToEnOn(name));
        org.setPostalAddress(postalAddress);
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        org.setTelecomAddress(telco);

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        org.getTelecomAddress().getItem().add(email);

        TelUrl url = new TelUrl();
        url.setValue(new URI(DEFAULT_URL));
        org.getTelecomAddress().getItem().add(url);
        return org;
    }

    private HealthCareFacilityDTO createHcfFromCtep(Ii orgId) throws URISyntaxException {
        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setPlayerIdentifier(orgId);
        hcf.setName(TestConvertHelper.convertToEnOn("ctep role"));
        Cd status = new Cd();
        status.setCode("PENDING");
        hcf.setStatus(status);
        Ii ctep = new Ii();
        ctep.setRoot(CTEP_ORG_ROOT);
        ctep.setExtension("AAA");
        ctep.setIdentifierName("CTEP ID");
        hcf.setIdentifier(new DSet<Ii>());
        hcf.getIdentifier().setItem(new HashSet<Ii>());
        hcf.getIdentifier().getItem().add(ctep);
        DSet<Ad> addys = new DSet<Ad>();
        Set<Ad> set = new ListOrderedSet();
        addys.setItem(set);
        addys.getItem().add(TestConvertHelper.createAd("123 abc ave.", null, "mycity", "WY", "12345", "USA"));
        hcf.setPostalAddress(addys);

        hcf.setTelecomAddress(new DSet<Tel>());
        hcf.getTelecomAddress().setItem(new HashSet<Tel>());

        TelPhone ph1 = new TelPhone();
        ph1.setValue(new URI(TelPhone.SCHEME_TEL + ":123-123-6543"));
        hcf.getTelecomAddress().getItem().add(ph1);
        
        //TelEmail email = new TelEmail();
        //email.setValue(new URI(TelEmail.SCHEME_MAILTO + ":hcfemail@example.com"));
        //hcf.getTelecomAddress().getItem().add(email);
        
        return hcf;
    }

    private OrganizationDTO remoteGetOrganization(Ii id) throws NullifiedEntityException {
        return getOrgService().getOrganization(id);
    }

    private void remoteUpdate(OrganizationDTO proposedState) throws EntityValidationException {
        getOrgService().updateOrganization(proposedState);
    }
}
