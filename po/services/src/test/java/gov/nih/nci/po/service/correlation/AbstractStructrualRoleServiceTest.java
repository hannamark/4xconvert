/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The COPPA PO
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This COPPA PO Software License (the License) is between NCI and You. You (or
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
 * its rights in the COPPA PO Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the COPPA PO Software; (ii) distribute and
 * have distributed to and by third parties the COPPA PO Software and any
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.ScopedRole;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AbstractBeanTest;
import gov.nih.nci.po.service.AbstractCuratableServiceBean;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.jms.JMSException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Skeleton for testing structural role services.
 *
 * @param <T> structural role under test
 */
public abstract class AbstractStructrualRoleServiceTest<T extends Correlation> extends AbstractBeanTest {

    ServiceLocator locator = new TestServiceLocator();
    protected Person basicPerson = null;
    protected Organization basicOrganization = null;

    protected void fillinPersonRoleFields(AbstractPersonRole pr) {
        pr.setPlayer(basicPerson);
        pr.setScoper(basicOrganization);
        pr.setEmail(new ArrayList<Email>());
        pr.getEmail().add(new Email("me@test.com"));
        pr.setPhone(new ArrayList<PhoneNumber>());
        pr.getPhone().add(new PhoneNumber("123-456-7890"));
        pr.setFax(new ArrayList<PhoneNumber>());
        pr.getFax().add(new PhoneNumber("098-765-4321"));
        pr.setTty(new ArrayList<PhoneNumber>());
        pr.getTty().add(new PhoneNumber("111-222-3333"));
        pr.setUrl(new ArrayList<URL>());
        pr.getUrl().add(new URL("http://www.google.com"));
        Address mailingAddress = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345", getDefaultCountry());
        pr.setPostalAddresses(new HashSet<Address>());
        pr.getPostalAddresses().add(mailingAddress);
    }

    protected void verifyPersonRole(AbstractPersonRole expected, AbstractPersonRole actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmail().size(), actual.getEmail().size());
        assertEquals(expected.getPlayer().getId(), actual.getPlayer().getId());
        assertEquals(expected.getScoper().getId(), actual.getScoper().getId());
        assertEquals(expected.getFax().size(), actual.getFax().size());
        assertEquals(expected.getPhone().size(), actual.getPhone().size());
        assertEquals(expected.getTty().size(), actual.getTty().size());
        assertEquals(expected.getUrl().size(), actual.getUrl().size());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPostalAddresses().size(), actual.getPostalAddresses().size());
    }

    @Before
    public void setUpData() throws Exception {
        createAndGetOrganization();

        // create person
        PersonServiceBeanTest personTest = new PersonServiceBeanTest();
        personTest.setDefaultCountry(getDefaultCountry());
        personTest.setUser(getUser());
        basicPerson = personTest.getBasicPerson();
        basicPerson.setStatusCode(EntityStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(basicPerson);
        PoHibernateUtil.getCurrentSession().flush();
    }

    abstract T getSampleStructuralRole() throws Exception;

    abstract void verifyStructuralRole(T expected, T actual);

    @SuppressWarnings("unchecked")
    protected AbstractCuratableServiceBean<T> getService() {
        // find the correct service via reflection
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];

        for (Method m : locator.getClass().getDeclaredMethods()) {
            Class<?> serviceReturnClass = m.getReturnType();
            if (serviceReturnClass == null) {
                continue;
            }
            Type[] genericInterfaces = serviceReturnClass.getGenericInterfaces();
            if (genericInterfaces == null || genericInterfaces.length == 0
                    || !(genericInterfaces[0] instanceof ParameterizedType)) {
                continue;
            }
            ParameterizedType pt = (ParameterizedType) genericInterfaces[0];
            if (pt == null) {
                continue;
            }
            Class<?> serviceType = (Class<?>) pt.getActualTypeArguments()[0];
            if (myType.equals(serviceType)) {
                try {
                    return (AbstractCuratableServiceBean<T>) m.invoke(locator);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new RuntimeException("There doesn't appear to be a method on the "
                + "service locator that returns the corret type!! try adding "+myType.getSimpleName()+"ServiceLocal ServiceLocator.get" + myType.getSimpleName()+"Service();");
    }

    /**
     * Test a simple create and get.
     */
    @Test
    public void testSimpleCreateAndGet() throws Exception {
        T structuralRole = getSampleStructuralRole();

        if (structuralRole instanceof CuratableRole) {
            CuratableRole<?, ?> c = (CuratableRole<?, ?>) structuralRole;
            assertNull(c.getStatusDate());
        }

        AbstractCuratableServiceBean<T> service = getService();

        service.create(structuralRole);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        T retrievedRole = service.getById(structuralRole.getId());
        verifyStructuralRole(structuralRole, retrievedRole);

        if (retrievedRole instanceof CuratableRole) {
            CuratableRole<?, ?> c = (CuratableRole<?, ?>) retrievedRole;
            assertNotNull(c.getStatusDate());
        }
    }

    @Test
    public void testGetByIds() throws Exception {
        AbstractCuratableServiceBean<T> service = getService();

        T sr1 = createSample();

        T sr2 = createSample();

        Long[] ids = {sr1.getId(), sr2.getId()};
        List<T> srs = service.getByIds(ids);
        assertEquals(2, srs.size());

        ids = new Long[1];
        ids[0] = sr2.getId();
        srs = service.getByIds(ids);
        assertEquals(1, srs.size());

        srs = service.getByIds(null);
        assertEquals(0, srs.size());

        srs = service.getByIds(new Long[0]);
        assertEquals(0, srs.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByTooManyIds () {
        getService().getByIds(new Long[501]);
    }

    protected T createSample() throws Exception {
        AbstractCuratableServiceBean<T> service = getService();
        T r = getSampleStructuralRole();
        service.create(r);
        return r;
    }

    @Test
    public void cascadePlayerStatusChange_Nullified() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (PlayedRole.class.isAssignableFrom(myType)) {
            CuratableRole r = (CuratableRole) createSample();
            assertEquals(RoleStatus.PENDING, r.getStatus());
            CuratableEntity entity = ((PlayedRole<?>)r).getPlayer();
            assertEquals(EntityStatus.PENDING, entity.getStatusCode());
            entity.setStatusCode(EntityStatus.NULLIFIED);
            if (entity instanceof Organization){
                locator.getOrganizationService().curate((Organization)entity);
            } else {
                locator.getPersonService().curate((Person)entity);
            }
            assertEquals(RoleStatus.NULLIFIED, r.getStatus());
        }
    }

    @BeforeClass
    public static void setUpJNDI() {
        contextBuilder.bind("po/ResearchOrganizationServiceBean/local", EjbTestHelper.getResearchOrganizationServiceBean());
        contextBuilder.bind("po/IdentifiedOrganizationServiceBean/local", EjbTestHelper.getIdentifiedOrganizationServiceBean());
        contextBuilder.bind("po/IdentifiedPersonServiceBean/local", EjbTestHelper.getIdentifiedPersonServiceBean());
        contextBuilder.bind("po/HealthCareProviderServiceBean/local", EjbTestHelper.getHealthCareProviderServiceBean());
        contextBuilder.bind("po/HealthCareFacilityServiceBean/local", EjbTestHelper.getHealthCareFacilityServiceBean());
        contextBuilder.bind("po/ClinicalResearchStaffServiceBean/local", EjbTestHelper.getClinicalResearchStaffServiceBean());
        contextBuilder.bind("po/OrganizationalContactServiceBean/local", EjbTestHelper.getOrganizationalContactService());
        contextBuilder.bind("po/OversightCommitteeServiceBean/local", EjbTestHelper.getOversightCommitteeServiceBean());
    }

    @AfterClass
    public static void tearDownJNDI() {
        contextBuilder.clear();
    }

    @Test
    public void cascadePlayerStatusChange_Inactive() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (PlayedRole.class.isAssignableFrom(myType)) {
            // make everything ACTIVE
            CuratableRole<?, ?> r = (CuratableRole<?, ?>) createSample();
            CuratableEntity<?, ?> player = ((PlayedRole<?>)r).getPlayer();
            player.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(player);
            basicOrganization.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(basicOrganization);
            basicPerson.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(basicPerson);
            r.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(r);
            PoHibernateUtil.getCurrentSession().flush();

            CuratableEntity entity = ((PlayedRole<?>)r).getPlayer();
            entity.setStatusCode(EntityStatus.INACTIVE);
            if (entity instanceof Organization){
                locator.getOrganizationService().curate((Organization)entity);
            } else {
                locator.getPersonService().curate((Person)entity);
            }
            assertEquals(RoleStatus.SUSPENDED, r.getStatus());
        }
    }

    @Test
    public void cascadeScoperStatusChange_Nullified() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (ScopedRole.class.isAssignableFrom(myType)) {
            CuratableRole<?, ?> r = (CuratableRole<?, ?>) createSample();
            assertEquals(RoleStatus.PENDING, r.getStatus());
            basicOrganization.setStatusCode(EntityStatus.NULLIFIED);
            locator.getOrganizationService().curate(basicOrganization);
            assertEquals(RoleStatus.NULLIFIED, r.getStatus());
        }
    }

    @Test
    public void cascadeScoperStatusChange_Inactive() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (ScopedRole.class.isAssignableFrom(myType)) {
            // make everything ACTIVE
            CuratableRole<?, ?> r = (CuratableRole<?, ?>) createSample();
            basicOrganization.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(basicOrganization);
            basicPerson.setStatusCode(EntityStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(basicPerson);
            r.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().update(r);
            PoHibernateUtil.getCurrentSession().flush();

            basicOrganization.setStatusCode(EntityStatus.INACTIVE);
            locator.getOrganizationService().curate(basicOrganization);
            assertEquals(RoleStatus.SUSPENDED, r.getStatus());
        }
    }

    protected void createAndGetOrganization() throws EntityValidationException, JMSException {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.setDefaultCountry(getDefaultCountry());
        orgTest.setUser(getUser());
        orgTest.setUpData();
        long orgId;
        orgId = orgTest.createOrganizationNoSessionFlushAndClear();
        PoHibernateUtil.getCurrentSession().flush();
        basicOrganization = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, orgId);
    }
}
