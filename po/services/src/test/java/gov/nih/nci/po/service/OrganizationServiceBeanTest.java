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
import gov.nih.nci.po.audit.AuditLogRecord;
import gov.nih.nci.po.audit.AuditType;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the organization service.
 *
 * @author Scott Miller
 */
public class OrganizationServiceBeanTest extends AbstractBeanTest {

    private OrganizationServiceBean orgServiceBean;

    public OrganizationServiceBean getOrgServiceBean() {
        return orgServiceBean;
    }

    @Before
    public void setUpData() {
        orgServiceBean = EjbTestHelper.getOrganizationServiceBean();
    }

    @After
    public void teardown() {
        orgServiceBean = null;
    }

    public Organization getBasicOrganization() {
        Address mailingAddress = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                getDefaultCountry());
        Organization org = new Organization();
        org.setPostalAddress(mailingAddress);
        org.setName("oName");
        org.setStatusCode(EntityStatus.PENDING);

        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
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
        return org;
    }

    protected long createOrganization(Organization org) throws EntityValidationException {
        assertNull(org.getStatusDate());
        long id = getOrgServiceBean().create(org);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Organization saved = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, id);
        assertNotNull(saved.getStatusDate());

        // adjust the expected value to NEW
        org.setStatusCode(EntityStatus.PENDING);
        verifyEquals(org, saved);
        PoHibernateUtil.getCurrentSession().flush();

        List<AuditLogRecord> alr = AuditTestUtil.find(Organization.class, saved.getId());
        AuditTestUtil.assertDetail(alr, AuditType.INSERT, "name", null, "oName", false);
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
    }

    public long createOrganization() throws EntityValidationException {
        return createOrganization("defaultName", "defaultCity", "defaultOrgCode", "defaultDescription");
    }

    public long createOrganization(String oName, String cityOrMunicipality, String abbrvName, String desc)
            throws EntityValidationException {
        long orgId = createOrganizationNoSessionFlushAndClear(oName, cityOrMunicipality, abbrvName, desc);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        return orgId;
    }
    
    public long createOrganizationNoSessionFlushAndClear() throws EntityValidationException {
        return createOrganizationNoSessionFlushAndClear("defaultName", "defaultCity", "defaultOrgCode", "defaultDescription");
    }
    
    public long createOrganizationNoSessionFlushAndClear(String oName, String cityOrMunicipality, String abbrvName, String desc) throws EntityValidationException {
        Address mailingAddress = new Address("defaultStreetAddress", cityOrMunicipality, "defaultState", "12345",
                getDefaultCountry());
        Organization org = new Organization();
        org.setPostalAddress(mailingAddress);
        org.setName(oName);
        org.setStatusCode(EntityStatus.PENDING);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
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
     */
    @Test
    public void testCreateOrgWithNameTooLong() {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);

        Organization org = new Organization();
        //161 char long name string
        String name = StringUtils.repeat("1", 161);
        org.setName(name);
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.NULLIFIED);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));

        try {
            long orgId = orgServiceBean.create(org);
            fail("expected to receive an EntityValidationException on .name property");
        } catch (EntityValidationException e) {
            System.out.println(e.getErrors());
            //ensure an error exists on the name field
            assertNotNull(e.getErrors().get("name"));
        }
    }
    
    
    @Test
    public void testCreateOrg() throws EntityValidationException {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);

        Organization org = new Organization();
        //32 * 5 = 160 chars long
        org.setName(StringUtils.repeat("testO",32));
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.NULLIFIED);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));

        long orgId = orgServiceBean.create(org);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization retrievedOrg = orgServiceBean.getById(orgId);
        assertEquals(new Long(orgId), retrievedOrg.getId());
        assertEquals(EntityStatus.PENDING, retrievedOrg.getStatusCode());

        List<Organization> orgs = getAllOrganizations();
        assertEquals(1, orgs.size());
        assertEquals(new Long(orgId), orgs.get(0).getId());
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
        MessageProducerTest.assertNoMessageCreated(o, getOrgServiceBean());
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
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
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
        assertTrue(result.getStatusDate().getTime() >  oldDate.getTime());
        assertEquals(EntityStatus.ACTIVE, result.getStatusCode());
        assertEquals(1, result.getEmail().size());
        assertEquals(1, result.getFax().size());
        assertEquals(1, result.getPhone().size());
        assertEquals(1, result.getTty().size());
        assertEquals(1, result.getUrl().size());

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
    }

    @Test
    public void curateToNullifiedWithDuplicateOf() throws EntityValidationException, JMSException {
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

        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
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
        Map<String, String[]> errors = PoHibernateUtil.validate(orgToBeCurated);
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
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
    }


    @Test
    public void curatorCreatesOrgAsPENDING() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.PENDING);
        MessageProducerTest.assertNoMessageCreated(o, getOrgServiceBean());
    }

    @Test
    public void curatorCreatesOrgAsACTIVE() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.ACTIVE);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
    }

    @Test
    public void curatorCreatesOrgAsNULLIFIED() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.NULLIFIED);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
    }

    @Test
    public void curatorCreatesOrgAsINACTIVE() throws JMSException {
        Organization o = curatorCreatesOrg(EntityStatus.INACTIVE);
        MessageProducerTest.assertMessageCreated(o, getOrgServiceBean());
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
}
