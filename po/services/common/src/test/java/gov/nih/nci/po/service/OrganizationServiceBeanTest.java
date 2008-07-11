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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ContactInfo;
import gov.nih.nci.po.data.bo.GetterSetterTesterUtil;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.alternate.AlternateProvider;
import gov.nih.nci.po.data.bo.alternate.ProviderOrganization;
import gov.nih.nci.po.data.common.Country;
import gov.nih.nci.po.data.common.CurationStatus;
import gov.nih.nci.po.data.common.OrganizationType;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.PropertyValueException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Tests the organization service.
 *
 * @author Scott Miller
 */
public class OrganizationServiceBeanTest extends AbstractHibernateTestCase {

    public static OrganizationType ORG_TYPE = new OrganizationType("name");

    private OrganizationServiceBean orgService;
    private OrganizationType defaultOrgType;
    private OrganizationType auxOrgType;
    private Country defaultCountry;
    User user;



    @Before
    public void setUpData() {
        orgService = EjbTestHelper.getOrganizationServiceBean();

        OrganizationType tmpOrgType = new OrganizationType("defaultOrgType");
        Serializable orgTypeId = PoHibernateUtil.getCurrentSession().save(tmpOrgType);
        OrganizationType tmpOrgType2 = new OrganizationType("auxOrgType");
        Serializable orgTypeId2 = PoHibernateUtil.getCurrentSession().save(tmpOrgType2);

        Serializable countryId = PoHibernateUtil.getCurrentSession().save(
                new Country("defaultCountryName", "997", "JJ", "JJI"));

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        defaultCountry = (Country) PoHibernateUtil.getCurrentSession().get(Country.class, countryId);
        defaultOrgType = (OrganizationType) PoHibernateUtil.getCurrentSession().get(OrganizationType.class, orgTypeId);
        auxOrgType = (OrganizationType) PoHibernateUtil.getCurrentSession().get(OrganizationType.class, orgTypeId2);

        user = new User();
        user.setLoginName("unittest" + new Random().nextLong());
        user.setFirstName("first");
        user.setLastName("last");
        user.setUpdateDate(new Date());
        PoHibernateUtil.getCurrentSession().save(user);
        UsernameHolder.setUser(user.getLoginName());
    }

    @After
    public void teardown() {
        orgService = null;
    }

    public long createOrganization() {
        return createOrganization("defaultName", "defaultCity");
    }

    public long createOrganization(String oName, String cityOrMunicipality) {
        Address mailingAddress = new Address("defaultStreetAddress", cityOrMunicipality, "defaultState", "12345",
                defaultCountry);
        ContactInfo contactInfo = new ContactInfo(mailingAddress);
        Organization org = new Organization(contactInfo);
        org.setName(oName);
        org.setAbbreviationName("defaultOrgCode");
        org.setCurationStatus(CurationStatus.NEW);
        org.getPrimaryContactInfo().setOrganization(org);
        org.getTypes().add(defaultOrgType);
        org.getTypes().add(auxOrgType);
        addAlternateId(org, "altCode");
        long orgId = orgService.create(org);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        return orgId;
    }

    /**
     * Test creating a Org with invalid input.
     */
    @Test
    public void testCreateOrgWithInvalidInput() {
        try {
            orgService.create(new Organization(new ContactInfo()));
            fail("Expected illegal argument exception");
        } catch (PropertyValueException e) {
            // expected
        }
    }

    @Test
    public void testAlternateProviderFields() throws Exception {
        AlternateProvider alternateProvider = new AlternateProvider();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(alternateProvider);
    }

    @Test
    public void testCreateOrg() {
        Country country = new Country("testorg", "996", "IJ", "IJI");
        PoHibernateUtil.getCurrentSession().save(country);
        PoHibernateUtil.getCurrentSession().save(ORG_TYPE);

        Organization org = new Organization(new ContactInfo());
        org.setName("testOrg");
        org.setAbbreviationName("abbr");
        Address mailingAddress = new Address("test", "test", "test", "test", country);
        org.getPrimaryContactInfo().setMailingAddress(mailingAddress);
        org.getPrimaryContactInfo().setOrganization(org);
        addAlternateId(org, "altCode");
        org.getTypes().add(ORG_TYPE);
        org.setCurationStatus(CurationStatus.REJECTED);

        long orgId = orgService.create(org);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Organization retrievedOrg = orgService.getOrganization(orgId);
        assertEquals(new Long(orgId), retrievedOrg.getId());
        assertTrue(!retrievedOrg.getAltIds().isEmpty());
        assertTrue(!retrievedOrg.getTypes().isEmpty());
        assertEquals(CurationStatus.NEW, retrievedOrg.getCurationStatus());

        List<Organization> orgs = getAllOrganizations();
        assertEquals(1, orgs.size());
        assertEquals(new Long(orgId), orgs.get(0).getId());
    }

    @SuppressWarnings("unchecked")
    private List<Organization> getAllOrganizations() {
        return PoHibernateUtil.getCurrentSession().createQuery("from " + Organization.class.getName()).list();
    }



    private void addAlternateId(Organization org, String code) {
        @SuppressWarnings("unchecked")
        List<AlternateProvider> l = PoHibernateUtil.getCurrentSession().createQuery(
                "FROM " + AlternateProvider.class.getName()).list();
        AlternateProvider ap = null;
        if (l.isEmpty()) {
            ap = new AlternateProvider();
            ap.setCode("test");
            ap.setName("Test provider");
            PoHibernateUtil.getCurrentSession().save(ap);
        } else {
            ap = l.get(0);
        }

        ProviderOrganization providerOrg = new ProviderOrganization();
        providerOrg.setAlternateProvider(ap);
        providerOrg.setOrganization(org);
        providerOrg.setAlternateProviderIdentifier(code);

        org.getAltIds().add(providerOrg);
    }

}
