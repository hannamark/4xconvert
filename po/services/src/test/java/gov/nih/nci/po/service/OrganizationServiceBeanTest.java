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
package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.AuditType;
import com.fiveamsolutions.nci.commons.util.HibernateHelper;

/**
 * Tests the organization service.
 *
 * @author Scott Miller
 */
public class OrganizationServiceBeanTest extends AbstractServiceBeanTest {

    private OrganizationServiceBean orgServiceBean;
    private Long ctepOrgId = null;

    public OrganizationServiceBean getOrgServiceBean() {
        return orgServiceBean;
    }

    @Before
    public void setUpData() {

        orgServiceBean = EjbTestHelper.getOrganizationServiceBean();
        initCtepOrg();
    }

    private void initCtepOrg() {
        Organization ctep = getBasicOrganization();
        ctep.setName(PoConstants.CTEP_ORG_NAME);

        PoHibernateUtil.getCurrentSession().save(ctep);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        ctepOrgId = ctep.getId();

    }

    protected void removeCtepOrg() {
        Organization ctep = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, ctepOrgId);

        if (ctep != null) {
            PoHibernateUtil.getCurrentSession().delete(ctep);
        }

    }


    @After
    public void teardown() {
        orgServiceBean = null;
    }

    public Organization getBasicOrganization() {
        Address mailingAddress =
                new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345", getDefaultCountry());
        Organization org = new Organization();
        org.setPostalAddress(mailingAddress);
        org.setName("oName");
        org.setStatusCode(EntityStatus.PENDING);

        Address a =
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
        a.setDeliveryAddressLine("deliveryAddressLine");
        org.setPostalAddress(a);

        org.getEmail().add(new Email("abc@example.com"));
        org.getEmail().add(new Email("def@example.com"));

        org.getPhone().add(new PhoneNumber("111-111-1111"));
        org.getPhone().add(new PhoneNumber("123-123-1234"));

        org.getFax().add(new PhoneNumber("222-222-2222"));
        org.getFax().add(new PhoneNumber("234-234-2345"));

        org.getTty().add(new PhoneNumber("333-333-3333"));
        org.getTty().add(new PhoneNumber("345-345-3456"));

        org.getUrl().add(new URL("http://www.example.com/abc"));
        org.getUrl().add(new URL("http://www.example.com/def"));
        
        org.getAlias().add(new Alias("org alias"));
        return org;
    }

    protected long createOrganization(Organization org) throws JMSException, EntityValidationException {
        return createOrganization(org, null);
    }

    protected long createOrganization(Organization org, String ctepId) throws EntityValidationException, JMSException {
        assertNull(org.getStatusDate());
        Long id = null;

        if (ctepId == null) {
            id = getOrgServiceBean().create(org);
        } else {
            id = getOrgServiceBean().create(org, ctepId);
        }

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Organization saved = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        assertNotNull(saved.getStatusDate());

        // adjust the expected value to NEW
        org.setStatusCode(EntityStatus.PENDING);
        verifyEquals(org, saved);
        PoHibernateUtil.getCurrentSession().flush();

        List<AuditLogRecord> alr = AuditTestUtil.find(Organization.class, saved.getId());
        AuditTestUtil.assertDetail(alr, AuditType.INSERT, "name", null, org.getName(), false);

        MessageProducerTest.assertMessageCreated(org, getOrgServiceBean(), true);
        return id;
    }

    private void verifyEquals(Organization expected, Organization found) {
        assertEquals(expected.getId(), found.getId());
        assertEquals(expected.getStatusCode(), found.getStatusCode());
        assertEquals(expected.getName(), found.getName());

        assertEquals(expected.getEmail().size(), found.getEmail().size());
        assertEquals(expected.getPhone().size(), found.getPhone().size());
        assertEquals(expected.getFax().size(), found.getFax().size());
        assertEquals(expected.getTty().size(), found.getTty().size());
        assertEquals(expected.getUrl().size(), found.getUrl().size());
        
        assertEquals(expected.getAlias().size(), found.getAlias().size());

        for (int i=0; i<expected.getAlias().size(); i++) {
            assertEquals(expected.getAlias().get(i).getValue(), found.getAlias().get(i).getValue());
        }

    }

    public long createOrganization() throws EntityValidationException, JMSException {
        return createOrganization("defaultName", "defaultCity");
    }

    public long createOrganization(String oName, String cityOrMunicipality) throws EntityValidationException,
            JMSException {
        long orgId = createOrganizationNoSessionFlushAndClear(oName, cityOrMunicipality);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        return orgId;
    }

    public long createOrganizationNoSessionFlushAndClear() throws EntityValidationException, JMSException {
        return createOrganizationNoSessionFlushAndClear("defaultName", "defaultCity");
    }

    public long createOrganizationNoSessionFlushAndClear(String oName, String cityOrMunicipality)
            throws EntityValidationException, JMSException {
        Address mailingAddress = new Address("defaultStreetAddress", cityOrMunicipality, "defaultState", "12345",
                getDefaultCountry());
        Organization org = new Organization();
        org.setPostalAddress(mailingAddress);
        org.setName(oName);
        org.setStatusCode(EntityStatus.PENDING);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        org.getAlias().add(new Alias("org alias"));
        long orgId = orgServiceBean.create(org);
        return orgId;
    }

    /**
     * Test creating a Org with invalid input.
     */
    @Test(expected = EntityValidationException.class)
    public void testCreateOrgWithInvalidInput() throws Exception {
        orgServiceBean.create(new Organization());
    }

    /**
     * Test creating a Org with a name that exceed maximum limit.
     *
     * @throws JMSException
     */
    @Test
    public void testCreateOrgWithNameTooLong() throws JMSException {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);

        Organization org = new Organization();
        // 161 char long name string
        String name = StringUtils.repeat("1", 161);
        org.setName(name);
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.NULLIFIED);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));

        try {
            orgServiceBean.create(org);
            fail("expected to receive an EntityValidationException on .name property");
        } catch (EntityValidationException e) {
            // ensure an error exists on the name field
            assertNotNull(e.getErrors().get("name"));
        }
    }

    @Test
    public void testCreateOrg() throws EntityValidationException, JMSException {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);

        Organization org = new Organization();
        // 32 * 5 = 160 chars long
        org.setName(StringUtils.repeat("testO", 32));
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.PENDING);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        org.getAlias().add(new Alias("test org alias"));

        long orgId = orgServiceBean.create(org);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization retrievedOrg = orgServiceBean.getById(orgId);
        assertEquals(new Long(orgId), retrievedOrg.getId());
        assertEquals(EntityStatus.PENDING, retrievedOrg.getStatusCode());
        assertTrue(retrievedOrg.getAlias().size() == 1);
        assertEquals("test org alias", retrievedOrg.getAlias().get(0).getValue());        

        List<Organization> orgs = getAllOrganizations();
        assertEquals(2, orgs.size());
        assertEquals(new Long(orgId), orgs.get(1).getId());

        MessageProducerTest.assertMessageCreated(retrievedOrg, getOrgServiceBean(), true);
    }
    
    @Test
    public void testCurateOrgAlias() throws EntityValidationException, JMSException {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);

        Organization org = new Organization();
        // 32 * 5 = 160 chars long
        org.setName(StringUtils.repeat("testO", 32));
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.PENDING);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        org.getAlias().add(new Alias("test org alias"));

        long orgId = orgServiceBean.create(org);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization retrievedOrg = orgServiceBean.getById(orgId);
        assertEquals(new Long(orgId), retrievedOrg.getId());
        assertEquals(EntityStatus.PENDING, retrievedOrg.getStatusCode());
        assertTrue(retrievedOrg.getAlias().size() == 1);
        assertEquals("test org alias", retrievedOrg.getAlias().get(0).getValue());               

        MessageProducerTest.assertMessageCreated(retrievedOrg, getOrgServiceBean(), true);
        // clear existing & add a new Alias
        retrievedOrg.getAlias().clear();
        retrievedOrg.getAlias().add(new Alias("test updated org alias"));
        orgServiceBean.curate(retrievedOrg);     
        
        // get the updated org & have asserts
        retrievedOrg = orgServiceBean.getById(orgId);
        assertTrue(retrievedOrg.getAlias().size() == 1);
        assertEquals("test updated org alias", retrievedOrg.getAlias().get(0).getValue());
        
        // add another Alias to the existing one
        retrievedOrg.getAlias().add(new Alias("test alias 2"));
        orgServiceBean.curate(retrievedOrg);        
        retrievedOrg = orgServiceBean.getById(orgId);
        assertTrue(retrievedOrg.getAlias().size() == 2);
        assertEquals("test updated org alias", retrievedOrg.getAlias().get(0).getValue());
        assertEquals("test alias 2", retrievedOrg.getAlias().get(1).getValue());
    }

    @Test
    public void testOrgWithCtepRolesStatusChange() throws EntityValidationException, JMSException {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);
        // create org
        Organization org = new Organization();
        org.setName("test");
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.PENDING);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        org.getAlias().add(new Alias("test org alias"));

        long orgId = orgServiceBean.create(org);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization retrievedOrg = orgServiceBean.getById(orgId);
        // create ctep hcf
        HealthCareFacility hcf = new HealthCareFacility();
        Address mailingAddress2 = new Address("test", "test", "test", "test", country);
        hcf.getPostalAddresses().add(mailingAddress2);
        hcf.setStatus(RoleStatus.PENDING);
        hcf.getEmail().add(new Email("foo@example.com"));
        hcf.getUrl().add(new URL("http://example.com"));
        hcf.setPlayer(retrievedOrg);
        Ii ctepIi = new Ii();
        ctepIi.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepIi.setIdentifierName("name");
        ctepIi.setExtension("CTEP");
        hcf.getOtherIdentifiers().add(ctepIi);

        long hcfId = EjbTestHelper.getHealthCareFacilityServiceBean().create(hcf);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        MessageProducerTest.assertMessageCreated(retrievedOrg, getOrgServiceBean(), true);

        // create non-ctep hcf

        HealthCareFacility hcfNotCtep = new HealthCareFacility();
        Address mailingAddress3 = new Address("test", "test", "test", "test", country);
        hcfNotCtep.getPostalAddresses().add(mailingAddress3);
        hcfNotCtep.setStatus(RoleStatus.PENDING);
        hcfNotCtep.getEmail().add(new Email("foo@example.com"));
        hcfNotCtep.getUrl().add(new URL("http://example.com"));
        hcfNotCtep.setPlayer(retrievedOrg);

        long hcfNotCtepId = EjbTestHelper.getHealthCareFacilityServiceBean().create(hcfNotCtep);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        
        // type code
        final ResearchOrganizationType type = (ResearchOrganizationType) PoHibernateUtil
                .getCurrentSession().get(ResearchOrganizationType.class, 1L);
       
        // create ctep ro
        ResearchOrganization ro = new ResearchOrganization();
        Address mailingAddress4 = new Address("test", "test", "test", "test", country);
        ro.getPostalAddresses().add(mailingAddress4);
        ro.setStatus(RoleStatus.PENDING);
        ro.getEmail().add(new Email("foo@example.com"));
        ro.getUrl().add(new URL("http://example.com"));
        ro.setPlayer(retrievedOrg);       
        ro.setTypeCode(type);
        Ii ctepRoIi = new Ii();
        ctepRoIi.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepRoIi.setIdentifierName("ro id name");
        ctepRoIi.setExtension("CTEP");
        ro.getOtherIdentifiers().add(ctepRoIi);

        long roId = EjbTestHelper.getResearchOrganizationServiceBean().create(ro);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization freshOrg = orgServiceBean.getById(orgId);
        assertEquals(2, freshOrg.getHealthCareFacilities().size());
        assertEquals(1, freshOrg.getResearchOrganizations().size());

        freshOrg.setStatusCode(EntityStatus.ACTIVE);
        orgServiceBean.curate(freshOrg);

        MessageProducerTest.assertMessageCreated(retrievedOrg, getOrgServiceBean(), false);

        // check that hcf1 is ACTIVE
        HealthCareFacility hcf1 = EjbTestHelper.getHealthCareFacilityServiceBean().getById(hcfId);
        assertEquals(RoleStatus.ACTIVE, hcf1.getStatus());
        // check that hcf2 is still PENDING
        HealthCareFacility hcf2 = EjbTestHelper.getHealthCareFacilityServiceBean().getById(hcfNotCtepId);
        assertEquals(RoleStatus.PENDING, hcf2.getStatus());
        // check that ro1 is ACTIVE
        ResearchOrganization ro1 = EjbTestHelper.getResearchOrganizationServiceBean().getById(roId);
        assertEquals(RoleStatus.ACTIVE, ro1.getStatus());

    }

    @SuppressWarnings("unchecked")
    private List<Organization> getAllOrganizations() {
        return PoHibernateUtil.getCurrentSession().createQuery("from " + Organization.class.getName()).list();
    }

    @Test
    public void curatePENDINGtoPENDINGthenNOAnnouncementMessagePublished() throws EntityValidationException,
            JMSException {
        Organization o = getBasicOrganization();
        long id = createOrganization(o);
        o = getOrgServiceBean().getById(id);
        o.setStatusCode(EntityStatus.PENDING);
        getOrgServiceBean().curate(o);
        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.PENDING, result.getStatusCode());
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), false);
    }

    @Test
    public void curateWithNoChanges() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        long id = createOrganization(o);
        o = getOrgServiceBean().getById(id);
        o.setStatusCode(EntityStatus.ACTIVE);
        getOrgServiceBean().curate(o);
        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.ACTIVE, result.getStatusCode());
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), false);
    }

    @Test
    public void curateWithChanges() throws Exception {
        Organization o = getBasicOrganization();
        long id = createOrganization(o);
        Thread.sleep(10);
        o = getOrgServiceBean().getById(id);
        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        o.setStatusCode(EntityStatus.ACTIVE);
        Date oldDate = o.getStatusDate();
        getOrgServiceBean().curate(o);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization result = getOrgServiceBean().getById(id);
        assertTrue(result.getStatusDate().getTime() > oldDate.getTime());
        assertEquals(EntityStatus.ACTIVE, result.getStatusCode());
        assertEquals(1, result.getEmail().size());
        assertEquals(1, result.getFax().size());
        assertEquals(1, result.getPhone().size());
        assertEquals(1, result.getTty().size());
        assertEquals(1, result.getUrl().size());

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), false);
    }

    @Test
    public void curateToNullifiedWithDuplicateOf() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        o.setName("org1name");
        o.getAlias().clear();
        o.getAlias().add(new Alias("org1alias"));

        Organization o2 = getBasicOrganization();
        o2.setName("org2name");
        o2.getAlias().clear();
        o2.getAlias().add(new Alias("org2alias"));

        long id = createOrganization(o);

        long id2 = createOrganization(o2, "CTEP2");
        o = getOrgServiceBean().getById(id);

        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);
        getOrgServiceBean().curate(o);

        Organization retrievedOrg1 = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.NULLIFIED, retrievedOrg1.getStatusCode());
        assertEquals(o2.getId(), retrievedOrg1.getDuplicateOf().getId());
        assertEquals(1, retrievedOrg1.getEmail().size());
        assertEquals(1, retrievedOrg1.getFax().size());
        assertEquals(1, retrievedOrg1.getPhone().size());
        assertEquals(1, retrievedOrg1.getTty().size());
        assertEquals(1, retrievedOrg1.getUrl().size());

        Set<IdentifiedOrganization> org1Identities = retrievedOrg1.getIdentifiedOrganizations();
        assertTrue(org1Identities.isEmpty());

        Organization retrievedOrg2 = getOrgServiceBean().getById(id2);
        assertEquals(3, retrievedOrg2.getAlias().size());
        assertEquals("org2alias", retrievedOrg2.getAlias().get(0).getValue());
        assertEquals("org1alias", retrievedOrg2.getAlias().get(1).getValue());
        assertEquals("org1name", retrievedOrg2.getAlias().get(2).getValue());

        Set<IdentifiedOrganization> org2Identities = retrievedOrg2.getIdentifiedOrganizations();

        assertEquals(1, org2Identities.size());
        assertEquals("CTEP2", org2Identities.iterator().next().getAssignedIdentifier().getExtension());

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), false);
    }

    @Test
    public void curateToPENDINGWithDuplicateOf() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        verifyCurateThrowsInvalidStateException(o, id2, EntityStatus.PENDING);
    }

    @Test
    public void curateToACTIVEWithDuplicateOf() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        verifyCurateThrowsInvalidStateException(o, id2, EntityStatus.ACTIVE);
    }

    @Test
    public void curateToINACTIVEWithDuplicateOf() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        // to avoid "Illegal curation transition from PENDING to INACTIVE" set org's status to ACTIVE to test INACTIVE
        // transition
        o.setStatusCode(EntityStatus.ACTIVE);
        o.setDuplicateOf(null);
        PoHibernateUtil.getCurrentSession().update(o);
        PoHibernateUtil.getCurrentSession().flush();
        verifyCurateThrowsInvalidStateException(o, id2, EntityStatus.INACTIVE);
    }

    private void verifyCurateThrowsInvalidStateException(Organization orgToBeCurated, long dupId,
            EntityStatus entityStatus) throws JMSException {
        Organization o2;
        orgToBeCurated.setStatusCode(entityStatus);
        o2 = getOrgServiceBean().getById(dupId);
        orgToBeCurated.setDuplicateOf(o2);
        Map<String, String[]> errors = HibernateHelper.validate(orgToBeCurated);
        assertFalse(errors.isEmpty());
        try {
            getOrgServiceBean().curate(orgToBeCurated);
            PoHibernateUtil.getCurrentSession().flush();
            fail();
        } catch (InvalidStateException e) {
            InvalidValue invalidValue = e.getInvalidValues()[0];
            assertEquals(null, invalidValue.getPropertyName());
            assertEquals(null, invalidValue.getPropertyPath());
            assertEquals(orgToBeCurated, invalidValue.getBean());
            assertEquals(orgToBeCurated, invalidValue.getRootBean());
            assertEquals(orgToBeCurated, invalidValue.getValue());
            assertEquals("Duplicates may only be specified when status is NULLIFIED", invalidValue.getMessage());
        }
        /*
         * Commented out the assertion on jms messages being published b/c there is not an easy way to verify that the
         * message will not be produced when the EJB is in-container. Furthermore, the ill, if any, side-effect of
         * publishing a message when no updates really occurred should be handled by the remote system
         * MessageProducerTest.assertNoMessageCreated(orgToBeCurated, getOrgServiceBean());
         */
    }

    @Test
    public void curateWithChangesAndCRrs() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        long id = createOrganization(o);

        OrganizationCRServiceBean organizationCRServiceBean = EjbTestHelper.getOrganizationCRServiceBean();
        OrganizationCR cr = new OrganizationCR(o);
        OrganizationDTO oDto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(o);
        oDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(oDto, cr, AbstractOrganization.class);
        cr.setId(null);
        cr.setStatusCode(o.getStatusCode());
        organizationCRServiceBean.create(cr);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        o = getOrgServiceBean().getById(id);
        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        assertFalse(o.getChangeRequests().isEmpty());

        o.setStatusCode(EntityStatus.ACTIVE);
        getOrgServiceBean().curate(o);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.ACTIVE, result.getStatusCode());
        assertEquals(1, result.getEmail().size());
        assertEquals(1, result.getFax().size());
        assertEquals(1, result.getPhone().size());
        assertEquals(1, result.getTty().size());
        assertEquals(1, result.getUrl().size());

        assertTrue(result.getChangeRequests().isEmpty());
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), false);
    }

    @Test
    public void curatorCreatesOrgAsPENDING() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.PENDING);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), true);
    }

    @Test
    public void curatorCreatesOrgAsACTIVE() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.ACTIVE);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), true);
    }

    @Test
    public void curatorCreatesOrgAsNULLIFIED() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.NULLIFIED);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), true);
    }

    @Test
    public void curatorCreatesOrgAsINACTIVE() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.INACTIVE);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), true);
    }

    private Organization curatorCreatesOrg(EntityStatus status) throws JMSException {
        Organization o = getBasicOrganization();

        o.setStatusCode(status);
        o.setDuplicateOf(null);
        getOrgServiceBean().curate(o);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization result = getOrgServiceBean().getById(o.getId());
        assertEquals(status, result.getStatusCode());
        assertEquals(status, result.getPriorEntityStatus());
        return result;
    }

    @Test
    public void curateToNullifiedWithDuplicateOfAndPointAssociatedRolesToDuplicateOrg()
            throws EntityValidationException, JMSException {
        final Country c = EjbTestHelper.getCountryServiceBean().getCountry(getDefaultCountry().getId());
        setDefaultCountry(c);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();        
        long id = createOrganization(o);

        ResearchOrganization org1Ro = new ResearchOrganization();
        org1Ro.setPlayer(o);
        org1Ro.setOtherIdentifiers(new HashSet<Ii>());
        org1Ro.getOtherIdentifiers().add(getCtepId("CTEP ID ORG 1 RO"));

        long org1RoId = EjbTestHelper.getResearchOrganizationServiceBean().create(org1Ro);

        org1Ro = EjbTestHelper.getResearchOrganizationServiceBean().getById(org1RoId);

        assertEquals(1, org1Ro.getOtherIdentifiers().size());

        o = getOrgServiceBean().getById(id);

        o.getEmail().size();
        o.getUrl().size();
        o.getPhone().size();
        o.getTty().size();
        o.getFax().size();
        o.getPostalAddress().getCountry().getStates().size();

        long id2 = createOrganization(o2);


        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setPlayer(o);
        hcf.setStatus(RoleStatus.PENDING);
        hcf.setName("HCF Name");
        hcf.setOtherIdentifiers(new HashSet<Ii>());
        hcf.getOtherIdentifiers().add(getCtepId("CTEP ID ORG 1 HCF"));

        HealthCareFacilityServiceLocal healthCareFacilityServiceBean = EjbTestHelper.getHealthCareFacilityServiceBean();
        long hcfId = healthCareFacilityServiceBean.create(hcf);
        hcf = healthCareFacilityServiceBean.getById(hcfId);

        PersonServiceBeanTest pst = new PersonServiceBeanTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }
        };
        // Don't load the data since duplicate entries would be created.
        // pst.loadData();
        pst.setUpData();
        long perId = pst.createPerson();
        PersonServiceBean personServiceBean = EjbTestHelper.getPersonServiceBean();
        Person person = personServiceBean.getById(perId);

        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setScoper(o);
        hcp.setPlayer(person);
        hcp.setScoper(o);
        hcp.setEmail(new ArrayList<Email>());
        hcp.getEmail().add(new Email("me@example.com"));
        hcp.setPhone(new ArrayList<PhoneNumber>());
        hcp.getPhone().add(new PhoneNumber("123-456-7890"));
        hcp.setFax(new ArrayList<PhoneNumber>());
        hcp.getFax().add(new PhoneNumber("098-765-4321"));
        hcp.setTty(new ArrayList<PhoneNumber>());
        hcp.getTty().add(new PhoneNumber("111-222-3333"));
        hcp.setUrl(new ArrayList<URL>());
        hcp.getUrl().add(new URL("http://www.example.com"));
        Address mailingAddress = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345", c);
        hcp.setPostalAddresses(new HashSet<Address>());
        hcp.getPostalAddresses().add(mailingAddress);
        hcp.setCertificateLicenseText("license text");
        HealthCareProviderServiceBean hcpSB = EjbTestHelper.getHealthCareProviderServiceBean();
        long hcpId = hcpSB.create(hcp);
        hcp = hcpSB.getById(hcpId);

        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);
        getOrgServiceBean().curate(o);

        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.NULLIFIED, result.getStatusCode());

        hcf = healthCareFacilityServiceBean.getById(hcfId);
        assertEquals(o2.getId(), hcf.getPlayer().getId());
        hcp = hcpSB.getById(hcpId);
        assertEquals(o2.getId(), hcp.getScoper().getId());


        //o2 should have the ro, but it should not have any identifiers
        hcf = EjbTestHelper.getHealthCareFacilityServiceBean().getById(hcfId);
        assertNoCtepIds("hcf", hcf.getId());
        assertTrue(hcf.getPlayer().getId() == o2.getId());
        assertTrue(hcf.getOtherIdentifiers().isEmpty());

        org1Ro = EjbTestHelper.getResearchOrganizationServiceBean().getById(org1RoId);

        assertNoCtepIds("ro", org1Ro.getId());

        assertTrue(org1Ro.getPlayer().getId() == o2.getId());
        assertTrue(org1Ro.getOtherIdentifiers().isEmpty());

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(), false);
    }

    private void assertNoCtepIds(String prefix, Long id) {
        String sql = String.format(
                        "select root from %s_otheridentifier where %s_id=:id and root=:root",
                        prefix,
                        prefix
                    );

        Query query = PoHibernateUtil.getCurrentSession().createSQLQuery(sql);
        query.setParameter("id", id);
        query.setParameter("root", PoConstants.ORG_CTEP_ID_ROOT);

        List hits = query.list();
        assertTrue("CTEP Identifiers were not removed from the research organization", hits.isEmpty());
    }

    private Ii getCtepId( String ctepIdString) {
        Ii ctepId = new Ii();

        ctepId.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
        ctepId.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
        ctepId.setExtension(ctepIdString);

        return ctepId;
    }

    @Test
    public void curateToNullifiedWithInactiveDuplicateOf()
            throws EntityValidationException, JMSException {
        final Country c = EjbTestHelper.getCountryServiceBean().getCountry(getDefaultCountry().getId());
        setDefaultCountry(c);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
       
        final Session session = PoHibernateUtil.getCurrentSession();
        session.createSQLQuery("update organization set status='INACTIVE' where id="+id2).executeUpdate();      
        session.createSQLQuery("update organization set status='ACTIVE' where id="+id).executeUpdate();     
        session.flush();
        
        o = getOrgServiceBean().getById(id);
        o.getEmail().size();
        o.getUrl().size();
        o.getPhone().size();
        o.getTty().size();
        o.getFax().size();
        o.getPostalAddress().getCountry().getStates().size();
       
        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setPlayer(o);
       
        hcf.setName("HCF Name");
        HealthCareFacilityServiceLocal healthCareFacilityServiceBean = EjbTestHelper.getHealthCareFacilityServiceBean();
        long hcfId = healthCareFacilityServiceBean.create(hcf);
                
        session.flush();
        session.createSQLQuery("update healthcarefacility set status='ACTIVE' where id="+hcfId).executeUpdate();        
        session.flush();
               
        hcf = healthCareFacilityServiceBean.getById(hcfId);
        
        PersonServiceBeanTest pst = new PersonServiceBeanTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }
        };
        // Don't load the data since duplicate entries would be created.
        // pst.loadData();
        pst.setUpData();
        session.flush();
        session.clear();
        
        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);
        getOrgServiceBean().curate(o);

        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.NULLIFIED, result.getStatusCode());

        hcf = healthCareFacilityServiceBean.getById(hcfId);
        assertEquals(RoleStatus.SUSPENDED, hcf.getStatus());
       

       
    }
    
    @Test
    public void curateToNullifiedWithFamilyRel()
            throws EntityValidationException, JMSException {
        final Country c = EjbTestHelper.getCountryServiceBean().getCountry(getDefaultCountry().getId());
        setDefaultCountry(c);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
       
        final Session session = PoHibernateUtil.getCurrentSession();
        
        o = getOrgServiceBean().getById(id);
        o.getEmail().size();
        o.getUrl().size();
        o.getPhone().size();
        o.getTty().size();
        o.getFax().size();
        o.getPostalAddress().getCountry().getStates().size();
        
        // Create family
        Family family = new Family();
        family.setName("Family");
        final Date date = new Date(System.currentTimeMillis()-100000000);
        family.setStartDate(date);
        family.setStatusCode(FamilyStatus.ACTIVE);
        long fid = EjbTestHelper.getFamilyServiceBean().create(family);
        session.flush();      
        family = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, fid);        
        
        // Create Family Org Rel
        FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
        famOrgRel.setOrganization(o);
        famOrgRel.setFamily(family);
        famOrgRel.setFunctionalType(FamilyFunctionalType.AFFILIATION);
        famOrgRel.setStartDate(date);        
        long famOrgRelId = EjbTestHelper.getFamilyOrganizationRelationshipService().createEntity(famOrgRel);
        session.flush();       
        famOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil.getCurrentSession().load(FamilyOrganizationRelationship.class, famOrgRelId);        
         
        PersonServiceBeanTest pst = new PersonServiceBeanTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }
        };
        // Don't load the data since duplicate entries would be created.
        // pst.loadData();
        pst.setUpData();
        session.flush();
        session.clear();
        
        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);
        getOrgServiceBean().curate(o);
        session.flush(); 

        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.NULLIFIED, result.getStatusCode());
        
        famOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil
                .getCurrentSession().load(FamilyOrganizationRelationship.class,
                        famOrgRelId);
        assertNotNull(famOrgRel.getEndDate());
       
    }


    @Test
    public void curateToNullifiedWithDuplicateOfAndPointCtepRolesToDuplicateActiveOrg()
            throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        // remove elements from the different CollectionType properties to ensure proper persistence
        o.getEmail().remove(0);
        o.getFax().remove(0);
        o.getPhone().remove(0);
        o.getTty().remove(0);
        o.getUrl().remove(0);

        HealthCareFacility hcf = new HealthCareFacility();
        hcf.setPlayer(o);
        hcf.setName("HCF Name");
        Ii ctepIi = new Ii();
        ctepIi.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepIi.setIdentifierName("CTEP HCF 1");
        ctepIi.setExtension("CTEP");
        hcf.getOtherIdentifiers().add(ctepIi);
        HealthCareFacilityServiceLocal healthCareFacilityServiceBean = EjbTestHelper.getHealthCareFacilityServiceBean();
        long hcfId = healthCareFacilityServiceBean.create(hcf);
        hcf = healthCareFacilityServiceBean.getById(hcfId);
        assertEquals(hcf.getStatus(), RoleStatus.PENDING);

        // switch o2 status to active
        o2.setStatusCode(EntityStatus.ACTIVE);
        getOrgServiceBean().curate(o2);

        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);
        getOrgServiceBean().curate(o);

        Organization result = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.NULLIFIED, result.getStatusCode());
        assertEquals(1, result.getEmail().size());
        assertEquals(1, result.getFax().size());
        assertEquals(1, result.getPhone().size());
        assertEquals(1, result.getTty().size());
        assertEquals(1, result.getUrl().size());

        hcf = healthCareFacilityServiceBean.getById(hcfId);
        assertEquals(o2.getId(), hcf.getPlayer().getId());
        assertEquals(o2.getStatusCode(), EntityStatus.ACTIVE);
        assertEquals(hcf.getStatus(), RoleStatus.ACTIVE);

        Set<Ii> i = hcf.getOtherIdentifiers();
        assertNotNull(i);

    }

    @Test
    public void curateToNullifiedWithDuplicateOfAndPointAssociatedRolesToDuplicateOrgWithAutomatedMerging()
            throws EntityValidationException, JMSException {
        final Country c = EjbTestHelper.getCountryServiceBean().getCountry(getDefaultCountry().getId());
        setDefaultCountry(c);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        o.getEmail().size();
        o.getUrl().size();
        o.getPhone().size();
        o.getTty().size();
        o.getFax().size();
        o.getPostalAddress().getCountry().getStates().size();

        OversightCommittee oc1 = new OversightCommittee();
        oc1.setPlayer(o);
        oc1.setTypeCode(getOversightCommitee());
        OversightCommitteeServiceLocal correlationSB = EjbTestHelper.getOversightCommitteeServiceBean();
        long correlationId = correlationSB.create(oc1);
        oc1 = correlationSB.getById(correlationId);

        OversightCommittee oc2 = new OversightCommittee();
        oc2.setPlayer(o2);
        oc2.setTypeCode(getOversightCommitee());
        OversightCommitteeServiceLocal correlation2SB = EjbTestHelper.getOversightCommitteeServiceBean();
        long correlation2Id = correlation2SB.create(oc2);
        oc2 = correlation2SB.getById(correlation2Id);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);

        getOrgServiceBean().curate(o);
        o = getOrgServiceBean().getById(id);
        o2 = getOrgServiceBean().getById(id2);
        assert(EntityStatus.NULLIFIED.equals(o.getStatusCode()));
        assert(o.getDuplicateOf().equals(o2));
        oc1 = getOversightCommitteeServiceBean().getById(correlationId);
        oc2 = getOversightCommitteeServiceBean().getById(correlation2Id);
        assert(oc1.getPlayer().getId().equals(o.getId()));
        assert(oc2.getPlayer().getId().equals(o2.getId()));
        assert(EntityStatus.NULLIFIED.equals(oc1.getPlayer().getStatusCode()));
        assert(oc1.getDuplicateOf() != null);
        assert(oc1.getDuplicateOf().getId() != null);
        assert(oc2.getId() != null);
        assert(oc1.getDuplicateOf().getId().equals(oc2.getId()));

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(),false);
    }

    @Test
    public void curateToNullifiedWithDuplicateOfAndPointAssociatedRolesToDuplicateOrgWithAutomatedMerging2()
            throws EntityValidationException, JMSException {
        final Country c = EjbTestHelper.getCountryServiceBean().getCountry(getDefaultCountry().getId());
        setDefaultCountry(c);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        o.getEmail().size();
        o.getUrl().size();
        o.getPhone().size();
        o.getTty().size();
        o.getFax().size();
        o.getPostalAddress().getCountry().getStates().size();

        ResearchOrganization rorg1 = new ResearchOrganization();
        rorg1.setPlayer(o);
        rorg1.setTypeCode(getResearchOrgType());
        rorg1.setFundingMechanism(getResearchOrgType().getFundingMechanisms().first());
        ResearchOrganizationServiceLocal correlationSB = EjbTestHelper.getResearchOrganizationServiceBean();
        long correlationId = correlationSB.create(rorg1);
        rorg1 = correlationSB.getById(correlationId);

        ResearchOrganization rorg2 = new ResearchOrganization();
        rorg2.setPlayer(o2);
        rorg2.setTypeCode(getResearchOrgType());
        rorg2.setFundingMechanism(getResearchOrgType().getFundingMechanisms().first());
        ResearchOrganizationServiceLocal correlation2SB = EjbTestHelper.getResearchOrganizationServiceBean();
        long correlation2Id = correlation2SB.create(rorg2);
        rorg2 = correlation2SB.getById(correlation2Id);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);

        getOrgServiceBean().curate(o);
        o = getOrgServiceBean().getById(id);
        o2 = getOrgServiceBean().getById(id2);
        assert(EntityStatus.NULLIFIED.equals(o.getStatusCode()));
        assert(o.getDuplicateOf().equals(o2));
        rorg1 = getResearchOrganizationServiceBean().getById(correlationId);
        rorg2 = getResearchOrganizationServiceBean().getById(correlation2Id);
        assert(rorg1.getPlayer().getId().equals(o.getId()));
        assert(rorg2.getPlayer().getId().equals(o2.getId()));
        assert(EntityStatus.NULLIFIED.equals(rorg1.getPlayer().getStatusCode()));
        assert(rorg1.getDuplicateOf().getId().equals(rorg2.getId()));

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(),false);
    }

    @Test
    public void curateToNullifiedWithDuplicateOfAndPointAssociatedRolesToDuplicateOrgWithAutomatedMerging3()
            throws EntityValidationException, JMSException {
        final Country c = EjbTestHelper.getCountryServiceBean().getCountry(getDefaultCountry().getId());
        setDefaultCountry(c);
        Organization o = getBasicOrganization();
        Organization o2 = getBasicOrganization();
        long id = createOrganization(o);
        long id2 = createOrganization(o2);
        o = getOrgServiceBean().getById(id);
        o.getEmail().size();
        o.getUrl().size();
        o.getPhone().size();
        o.getTty().size();
        o.getFax().size();
        o.getPostalAddress().getCountry().getStates().size();

        ResearchOrganization rorg1 = new ResearchOrganization();
        rorg1.setPlayer(o);
        rorg1.setTypeCode(getResearchOrgType());
        rorg1.setFundingMechanism(getResearchOrgType().getFundingMechanisms().first());
        ResearchOrganizationServiceLocal correlationSB = EjbTestHelper.getResearchOrganizationServiceBean();
        long correlationId = correlationSB.create(rorg1);
        rorg1 = correlationSB.getById(correlationId);

        ResearchOrganization rorg2 = new ResearchOrganization();
        rorg2.setPlayer(o2);
        rorg2.setTypeCode(getResearchOrgType());
        rorg2.setFundingMechanism(getResearchOrgType().getFundingMechanisms().first());
        ResearchOrganizationServiceLocal correlation2SB = EjbTestHelper.getResearchOrganizationServiceBean();
        long correlation2Id = correlation2SB.create(rorg2);
        rorg2 = correlation2SB.getById(correlation2Id);

        OversightCommittee oc1 = new OversightCommittee();
        oc1.setPlayer(o);
        oc1.setTypeCode(getOversightCommitee());
        OversightCommitteeServiceLocal oc1SB = EjbTestHelper.getOversightCommitteeServiceBean();
        long oc1Id = oc1SB.create(oc1);
        oc1 = oc1SB.getById(oc1Id);

        OversightCommittee oc2 = new OversightCommittee();
        oc2.setPlayer(o2);
        oc2.setTypeCode(getOversightCommitee());
        OversightCommitteeServiceLocal oc2SB = EjbTestHelper.getOversightCommitteeServiceBean();
        long oc2Id = oc2SB.create(oc2);
        oc2 = oc2SB.getById(oc2Id);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);

        getOrgServiceBean().curate(o);
        o = getOrgServiceBean().getById(id);
        o2 = getOrgServiceBean().getById(id2);
        assert(EntityStatus.NULLIFIED.equals(o.getStatusCode()));
        assert(o.getDuplicateOf().equals(o2));

        oc1 = getOversightCommitteeServiceBean().getById(correlationId);
        oc2 = getOversightCommitteeServiceBean().getById(correlation2Id);
        assert(oc1.getPlayer().getId().equals(o.getId()));
        assert(oc2.getPlayer().getId().equals(o2.getId()));
        assert(EntityStatus.NULLIFIED.equals(oc1.getPlayer().getStatusCode()));
        assert(oc1.getDuplicateOf().getId().equals(oc2.getId()));

        rorg1 = getResearchOrganizationServiceBean().getById(correlationId);
        rorg2 = getResearchOrganizationServiceBean().getById(correlation2Id);
        assert(rorg1.getPlayer().getId().equals(o.getId()));
        assert(rorg2.getPlayer().getId().equals(o2.getId()));
        assert(EntityStatus.NULLIFIED.equals(rorg1.getPlayer().getStatusCode()));
        assert(rorg1.getDuplicateOf().getId().equals(rorg2.getId()));


        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean(),false);
    }

    @Test
    public void testCount() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        getOrgServiceBean().create(o);

        Calendar cal = Calendar.getInstance();
        cal.set(2008, 01, 02);
        Date oldDate = DateUtils.truncate(cal.getTime(), Calendar.DATE);

        FamilyServiceLocal familyServiceBean = PoRegistry.getFamilyService();
        Family familyOne = getFamily("test1", oldDate);
        Family familyTwo = getFamily("test2", oldDate);
        familyServiceBean.create(familyOne);
        familyServiceBean.create(familyTwo);

        FamilyOrganizationRelationshipServiceLocal familyOrgRelServiceLocal = PoRegistry.getFamilyOrganizationRelationshipService();
        FamilyOrganizationRelationship famOrgRelOne = new FamilyOrganizationRelationship();
        famOrgRelOne.setOrganization(o);
        famOrgRelOne.setFamily(familyOne);
        famOrgRelOne.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRelOne.setStartDate(familyOne.getStartDate());
        FamilyOrganizationRelationship famOrgRelTwo = new FamilyOrganizationRelationship();
        famOrgRelTwo.setOrganization(o);
        famOrgRelTwo.setFamily(familyTwo);
        famOrgRelTwo.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRelTwo.setStartDate(familyTwo.getStartDate());

        familyOrgRelServiceLocal.createEntity(famOrgRelOne);
        familyOrgRelServiceLocal.createEntity(famOrgRelTwo);

        OrganizationSearchCriteria criteria = new OrganizationSearchCriteria();
       
        assertEquals(2L, getOrgServiceBean().count(criteria));
    }


    @Test
    public void testDeactivateOrganization() throws JMSException, EntityValidationException {
        long organizationId = createOrganization();
        Organization organization
                = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, organizationId);


        //org is active
        organization.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(organization);

        //org has active roles not created by me
        User otherUser = createRandomUser();

        List<AbstractOrganizationRole> organizationRoles = createRolesForOrganization(organization, otherUser);
        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            //org role is not created by me, and is not overidden
            assertFalse(getUser().equals(organizationRole.getCreatedBy()));
            assertNull(organizationRole.getOverriddenBy());

            PoHibernateUtil.getCurrentSession().save(organizationRole);
        }

        PoHibernateUtil.getCurrentSession().flush();

        //when the org is set to pending
        organization.setStatusCode(EntityStatus.INACTIVE);
        orgServiceBean.curate(organization);

        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            //roles are inactive
            assertEquals(RoleStatus.SUSPENDED, organizationRole.getStatus());

            //roles are overridden
            assertEquals(String.format("Overridden by not set for %s", organizationRole.getClass().getCanonicalName()),
                    getUser(),
                    organizationRole.getOverriddenBy());
        }

    }

    private User createRandomUser() {
        String randomString = "test-" + RandomStringUtils.randomAlphanumeric(5);
        User otherUser = new User();
        otherUser.setFirstName(randomString);
        otherUser.setLastName(randomString);
        otherUser.setLoginName(randomString);
        otherUser.setUpdateDate(new Date());

        PoHibernateUtil.getCurrentSession().save(otherUser);

        return otherUser;
    }

    private List<AbstractOrganizationRole> createRolesForOrganization(Organization organization, User createdBy) {
        List<AbstractOrganizationRole> results = new ArrayList<AbstractOrganizationRole>();

        ResearchOrganization researchOrganization = new ResearchOrganization();
        researchOrganization.setPlayer(organization);
        researchOrganization.setName("researchOrg");
        researchOrganization.setStatus(RoleStatus.ACTIVE);
        researchOrganization.setCreatedBy(createdBy);

        ResearchOrganizationType researchOrganizationType = (ResearchOrganizationType) PoHibernateUtil
                .getCurrentSession().get(ResearchOrganizationType.class, 1L);
        researchOrganization.setTypeCode(researchOrganizationType);

        results.add(researchOrganization);

        HealthCareFacility healthCareFacility = new HealthCareFacility();
        healthCareFacility.setPlayer(organization);
        healthCareFacility.setName("healthCareFacility");
        healthCareFacility.setStatus(RoleStatus.ACTIVE);
        healthCareFacility.setCreatedBy(createdBy);

        results.add(healthCareFacility);


        OversightCommittee oversightCommittee = new OversightCommittee();
        oversightCommittee.setPlayer(organization);
        oversightCommittee.setTypeCode(getOversightCommitee());
        oversightCommittee.setStatus(RoleStatus.ACTIVE);
        oversightCommittee.setCreatedBy(createdBy);
        results.add(oversightCommittee);

        return results;
    }

    @Test
    public void testDeactivateOrganizationWithOverriddenRole() throws JMSException, EntityValidationException {
        //org is active
        long organizationId = createOrganization();
        Organization organization
                = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, organizationId);

        organization.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(organization);

        //org role is not created by me, and is overidden by someone else
        User otherUser = createRandomUser();

        List<AbstractOrganizationRole> organizationRoles = createRolesForOrganization(organization, otherUser);
        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            assertFalse(getUser().equals(organizationRole.getCreatedBy()));
            organizationRole.setOverriddenBy(otherUser);
            PoHibernateUtil.getCurrentSession().save(organizationRole);
        }

        PoHibernateUtil.getCurrentSession().flush();

        //when the org is set to INACTIVE
        organization.setStatusCode(EntityStatus.INACTIVE);
        orgServiceBean.curate(organization);


        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            //roles are inactive
            assertEquals(RoleStatus.SUSPENDED, organizationRole.getStatus());

            //roles are overridden
            assertEquals(getUser(), organizationRole.getOverriddenBy());
        }

    }

    @Test
    public void testDeactivateOwnedOrganization() throws JMSException, EntityValidationException {
        //org is active
        long organizationId = createOrganization();
        Organization organization
                = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, organizationId);

        organization.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(organization);

        //org roles are active
        //org role is createdBy me and not overridden
        List<AbstractOrganizationRole> organizationRoles = createRolesForOrganization(organization, getUser());
        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            assertTrue(getUser().equals(organizationRole.getCreatedBy()));
            organizationRole.setOverriddenBy(null);
            PoHibernateUtil.getCurrentSession().save(organizationRole);
        }
        PoHibernateUtil.getCurrentSession().flush();

        //org is set to inactive
        organization.setStatusCode(EntityStatus.INACTIVE);
        orgServiceBean.curate(organization);


        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            //roles are inactive
            assertEquals(RoleStatus.SUSPENDED, organizationRole.getStatus());

            //roles are not overridden
            assertNull(organizationRole.getOverriddenBy());
        }
    }

    @Test
    public void testDeactiveOwnedOrgWithRoleOverriddenByMe() throws JMSException, EntityValidationException {
        //org is active
        long organizationId = createOrganization();
        Organization organization
                = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, organizationId);

        organization.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(organization);

        //org roles are active
        //org role is createdBy me and is overridden by me

        List<AbstractOrganizationRole> organizationRoles = createRolesForOrganization(organization, getUser());

        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            organizationRole.setOverriddenBy(getUser());
            PoHibernateUtil.getCurrentSession().save(organizationRole);
        }

        PoHibernateUtil.getCurrentSession().flush();

        //org updated to INACTIVE
        organization.setStatusCode(EntityStatus.INACTIVE);
        orgServiceBean.curate(organization);

        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            //roles are inactive
            assertEquals(RoleStatus.SUSPENDED, organizationRole.getStatus());

            //roles are still overridden by me
            assertEquals(getUser(), organizationRole.getOverriddenBy());
        }
    }

    @Test
    public void testDeactiveOwnedOrgWithRoleOverridden() throws JMSException, EntityValidationException {
        //org is active
        long organizationId = createOrganization();
        Organization organization
                = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, organizationId);

        organization.setStatusCode(EntityStatus.ACTIVE);
        PoHibernateUtil.getCurrentSession().save(organization);

        //org roles are active
        //org role is createdBy me and is overridden by someone else
        User otherUser = createRandomUser();

        List<AbstractOrganizationRole> organizationRoles = createRolesForOrganization(organization, getUser());

        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            organizationRole.setOverriddenBy(otherUser);
            PoHibernateUtil.getCurrentSession().save(organizationRole);
        }

        PoHibernateUtil.getCurrentSession().flush();

        //org is set to INACTIVE
        organization.setStatusCode(EntityStatus.INACTIVE);
        orgServiceBean.curate(organization);

        for (AbstractOrganizationRole organizationRole : organizationRoles) {
            //roles are inactive
            assertEquals(RoleStatus.SUSPENDED, organizationRole.getStatus());

            //roles are overridden
            assertEquals(getUser(), organizationRole.getOverriddenBy());
        }
    }

    public static Family getFamily(String name, Date date) {
        Family family = new Family();
        family.setName(name);
        family.setStartDate(date);
        family.setStatusCode(FamilyStatus.ACTIVE);
        return family;
    }
    
    
    @Test
    public void getDuplicateOfNullifiedOrg() throws EntityValidationException, JMSException {
        Organization o = getBasicOrganization();
        o.setName("org1");       

        Organization o2 = getBasicOrganization();
        o2.setName("org2");

        long id = createOrganization(o, "CTEP01");
        long id2 = createOrganization(o2, "CTEP02");
        
        o = getOrgServiceBean().getById(id);    
        o.setStatusCode(EntityStatus.NULLIFIED);
        o2 = getOrgServiceBean().getById(id2);
        o.setDuplicateOf(o2);
        getOrgServiceBean().curate(o);
        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization retrievedOrg1 = getOrgServiceBean().getById(id);
        assertEquals(EntityStatus.NULLIFIED, retrievedOrg1.getStatusCode());
        assertEquals(o2.getId(), retrievedOrg1.getDuplicateOf().getId());
        
        Set<IdentifiedOrganization> org1Identities = retrievedOrg1.getIdentifiedOrganizations();
        assertEquals(0, org1Identities.size());      
        
        assertEquals(o2.getId(), orgServiceBean.getDuplicateOfNullifiedOrg("CTEP01"));
        assertNull(orgServiceBean.getDuplicateOfNullifiedOrg("CTEP02"));

    }

}
