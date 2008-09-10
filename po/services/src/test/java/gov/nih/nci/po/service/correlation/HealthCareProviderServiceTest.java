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
package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AbstractBeanTest;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Scott Miller
 */
public class HealthCareProviderServiceTest extends AbstractBeanTest {

    ServiceLocator locator = new TestServiceLocator();

    private Person basicPerson = null;
    private Organization basicOrganization = null;

    @Before
    public void setUpData() throws Exception {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.setDefaultCountry(getDefaultCountry());
        orgTest.setUser(getUser());
        orgTest.setUpData();
        long orgId = orgTest.createOrganization();
        basicOrganization = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, orgId);

        // create person
        PersonServiceBeanTest personTest = new PersonServiceBeanTest();
        personTest.setDefaultCountry(getDefaultCountry());
        personTest.setUser(getUser());
        basicPerson = personTest.getBasicPerson();
        basicPerson.setStatusCode(EntityStatus.NEW);
        PoHibernateUtil.getCurrentSession().save(basicPerson);
        PoHibernateUtil.getCurrentSession().flush();
    }

    private HealthCareProvider getSampleHcp()  {
        HealthCareProvider hcp = new HealthCareProvider();
        hcp.setPerson(basicPerson);
        hcp.setOrganization(basicOrganization);
        hcp.setStatusDate(new Date());
        hcp.setEmail(new ArrayList<Email>());
        hcp.getEmail().add(new Email("me@test.com"));
        hcp.setPhone(new ArrayList<PhoneNumber>());
        hcp.getPhone().add(new PhoneNumber("123-456-7890"));
        hcp.setFax(new ArrayList<PhoneNumber>());
        hcp.getFax().add(new PhoneNumber("098-765-4321"));
        hcp.setTty(new ArrayList<PhoneNumber>());
        hcp.getTty().add(new PhoneNumber("111-222-3333"));
        hcp.setUrl(new ArrayList<URL>());
        hcp.getUrl().add(new URL("http://www.google.com"));
        hcp.setCertificateLicenseText("test cert");
        Address mailingAddress = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345", getDefaultCountry());
        hcp.setPostalAddresses(new HashSet<Address>());
        hcp.getPostalAddresses().add(mailingAddress);
        return hcp;
    }

    private void verifyRetrievedHcp(HealthCareProvider expected, HealthCareProvider actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCertificateLicenseText(), actual.getCertificateLicenseText());
        assertEquals(expected.getEmail().size(), actual.getEmail().size());
        assertEquals(expected.getPerson().getId(), actual.getPerson().getId());
        assertEquals(expected.getOrganization().getId(), actual.getOrganization().getId());
        assertEquals(expected.getFax().size(), actual.getFax().size());
        assertEquals(expected.getPhone().size(), actual.getPhone().size());
        assertEquals(expected.getTty().size(), actual.getTty().size());
        assertEquals(expected.getUrl().size(), actual.getUrl().size());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPostalAddresses().size(), actual.getPostalAddresses().size());
    }

    /**
     * Test a simple create and get.
     */
    @Test
    public void testSimpleCreateAndGet() throws Exception {
        HealthCareProvider hcp = getSampleHcp();
        locator.getHealthCareProviderService().create(hcp);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        HealthCareProvider retrievedHcp = locator.getHealthCareProviderService().getById(hcp.getId());
        verifyRetrievedHcp(hcp, retrievedHcp);
    }

    @Test
    public void testGetByIds() throws Exception {
        HealthCareProvider hcp = getSampleHcp();
        locator.getHealthCareProviderService().create(hcp);

        HealthCareProvider hcp2 = getSampleHcp();
        locator.getHealthCareProviderService().create(hcp2);

        Long[] ids = {hcp.getId(), hcp2.getId()};
        List<HealthCareProvider> hcps = locator.getHealthCareProviderService().getByIds(ids);
        assertEquals(2, hcps.size());

        ids = new Long[1];
        ids[0] = hcp2.getId();
        hcps = locator.getHealthCareProviderService().getByIds(ids);
        assertEquals(1, hcps.size());

        hcps = locator.getHealthCareProviderService().getByIds(null);
        assertEquals(0, hcps.size());

        hcps = locator.getHealthCareProviderService().getByIds(new Long[0]);
        assertEquals(0, hcps.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByTooManyIds () {
        locator.getHealthCareProviderService().getByIds(new Long[501]);
    }
}
