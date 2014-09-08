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
package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.AbstractIdentifiedOrganization;
import gov.nih.nci.po.data.bo.AbstractIdentifiedPerson;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Contactable;
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
import gov.nih.nci.po.data.bo.UsOrCanEnforceable;
import gov.nih.nci.po.service.AbstractBaseServiceBean;
import gov.nih.nci.po.service.AbstractCuratableServiceBean;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.util.TestServiceLocator;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

/**
 * Skeleton for testing structural role services.
 *
 * @param <T> structural role under test
 */
public abstract class AbstractStructrualRoleServiceTest<T extends Correlation> extends AbstractServiceBeanTest {

    ServiceLocator locator = new TestServiceLocator();
    protected Person basicPerson = null;
    protected Organization basicOrganization = null;



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
    
    abstract T getNewStructuralRole();

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

        assertNull(((CuratableRole<?, ?>) structuralRole).getStatusDate());

        AbstractCuratableServiceBean<T> service = getService();

        service.create(structuralRole);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        T retrievedRole = service.getById(structuralRole.getId());
        verifyStructuralRole(structuralRole, retrievedRole);

        assertNotNull(((CuratableRole<?, ?>) retrievedRole).getStatusDate());
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

    @Test
    public void testGetByPlayerIds() throws Exception {
        AbstractCuratableServiceBean<T> service = getService();

        T sr1 = createSample();

        T sr2 = createSample();

        T sr3 = createNullifiedSample();

        Long[] ids = null;
        if (sr1 instanceof AbstractPersonRole || sr1 instanceof AbstractIdentifiedPerson){
            ids = new Long[1];
            ids[0] = basicPerson.getId();
        } else if (sr1 instanceof AbstractOrganizationRole) {
            ids = new Long[2];
            ids[0] = ((AbstractOrganizationRole) sr1).getPlayer().getId();
            ids[1] = ((AbstractOrganizationRole) sr2).getPlayer().getId();
        } else if (sr2 instanceof AbstractIdentifiedOrganization) {
            ids = new Long[1];
            ids[0] = basicOrganization.getId();
        } else {
            throw new Exception("Can't figure out what type of SR dealing with.");
        }

        List<T> srs = service.getByPlayerIds(ids);
        assertEquals(2, srs.size());

        srs = service.getByPlayerIds(null);
        assertEquals(0, srs.size());

        srs = service.getByPlayerIds(new Long[0]);
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

    protected T createNullifiedSample() throws Exception {
        T r = createSample();
        r.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().update(r);
        return r;
    }

    @Test
    public void cascadePlayerStatusChange_Nullified() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (PlayedRole.class.isAssignableFrom(myType)) {
            CuratableRole<?, ?> r = createSample();
            assertEquals(RoleStatus.PENDING, r.getStatus());
            CuratableEntity<?, ?> entity = ((PlayedRole<?>)r).getPlayer();
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

    @Test
    public void cascadePlayerStatusChange_Inactive() throws Exception {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> myType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        if (PlayedRole.class.isAssignableFrom(myType)) {
            // make everything ACTIVE
            CuratableRole<?, ?> r = createSample();
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

            CuratableEntity<?, ?> entity = ((PlayedRole<?>)r).getPlayer();
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
            CuratableRole<?, ?> r = createSample();
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
            CuratableRole<?, ?> r = createSample();
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

    @Test
    @SuppressWarnings("unchecked")
    public void testOtherIdentifiersValidator() throws Exception {
        AbstractCuratableServiceBean<T> service = getService();

        T sr = createSample();
        // verify that the empty set will pass validation
        Map<String, String[]> errors = service.validate(sr);
        assertEquals(0, errors.size());

        String[] errorStrings = null;

        if (sr instanceof AbstractRole) {
            AbstractRole abstractRole = (AbstractRole) sr;
            abstractRole.setOtherIdentifiers(null);
            errors = service.validate(sr);
            assertEquals(1, errors.size());
            errorStrings = errors.get("otherIdentifiers");
            assertEquals(3, errorStrings.length);
            assertEquals("Invalid Ii per ISO OCL constraints.", errorStrings[0]);
            assertEquals("Extension must be set", errorStrings[1]);
            assertEquals("Root must be set", errorStrings[2]);

            Set nonIiSet = new HashSet();
            Ii ii = new Ii();
            nonIiSet.add(ii);
            abstractRole.setOtherIdentifiers(nonIiSet);
            errors = service.validate(sr);
            assertEquals(1, errors.size());
            errorStrings = errors.get("otherIdentifiers");
            assertEquals(3, errorStrings.length);
            assertEquals("Invalid Ii per ISO OCL constraints.", errorStrings[0]);
            assertEquals("Extension must be set", errorStrings[1]);
            assertEquals("Root must be set", errorStrings[2]);

            abstractRole.setOtherIdentifiers(new LinkedHashSet<Ii>());
        }

        Ii otherIi = new Ii();
        sr.getOtherIdentifiers().add(otherIi);
        errors = service.validate(sr);
        assertEquals(1, errors.size());
        errorStrings = errors.get("otherIdentifiers");
        assertEquals(3, errorStrings.length);
        assertEquals("Invalid Ii per ISO OCL constraints.", errorStrings[0]);
        assertEquals("Extension must be set", errorStrings[1]);
        assertEquals("Root must be set", errorStrings[2]);

        otherIi.setNullFlavor(NullFlavor.NI);
        errors = service.validate(sr);
        assertEquals(1, errors.size());
        errorStrings = errors.get("otherIdentifiers");
        assertEquals(2, errorStrings.length);
        assertEquals("Extension must be set", errorStrings[0]);
        assertEquals("Root must be set", errorStrings[1]);

        otherIi.setRoot("1234");
        errors = service.validate(sr);
        assertEquals(1, errors.size());
        errorStrings = errors.get("otherIdentifiers");
        assertEquals(2, errorStrings.length);
        assertEquals("Invalid Ii per ISO OCL constraints.", errorStrings[0]);
        assertEquals("Extension must be set", errorStrings[1]);

        otherIi.setNullFlavor(null);
        otherIi.setRoot("1234");
        errors = service.validate(sr);
        assertEquals(1, errors.size());
        errorStrings = errors.get("otherIdentifiers");
        assertEquals(1, errorStrings.length);
        assertEquals("Extension must be set", errorStrings[0]);

        otherIi.setExtension("1234");
        otherIi.setRoot(null);
        errors = service.validate(sr);
        assertEquals(1, errors.size());
        errorStrings = errors.get("otherIdentifiers");
        assertEquals(2, errorStrings.length);
        assertEquals("Invalid Ii per ISO OCL constraints.", errorStrings[0]);
        assertEquals("Root must be set", errorStrings[1]);

        otherIi.setExtension("1234");
        otherIi.setRoot("5678");
        errors = service.validate(sr);
        assertEquals(0, errors.size());

        // add an invalid Ii in addition to the valid Ii
        Ii anotherIi = new Ii();
        sr.getOtherIdentifiers().add(anotherIi);
        errors = service.validate(sr);
        assertEquals(1, errors.size());
        errorStrings = errors.get("otherIdentifiers");
        assertEquals(3, errorStrings.length);
        assertEquals("Invalid Ii per ISO OCL constraints.", errorStrings[0]);
        assertEquals("Extension must be set", errorStrings[1]);
        assertEquals("Root must be set", errorStrings[2]);
    }
    
    @Test
    public void testSearchByPhoneNumber() throws Exception {
        T obj = createSample();
        if (obj instanceof Contactable) {
            ((Contactable) obj).getPhone().add(new PhoneNumber("101-555-8888"));
            ((Contactable) obj).getPhone().add(new PhoneNumber("202-555-8888"));
            PoHibernateUtil.getCurrentSession().update(obj);
            
            obj = getSampleStructuralRole();
            ((Contactable) obj).getPhone().add(new PhoneNumber("202-555-8888"));
            ((Contactable) obj).getPhone().add(new PhoneNumber("303-555-8888"));
            getService().create(obj);
     
            obj = getNewStructuralRole();
            ((Contactable) obj).getPhone().add(new PhoneNumber("101-555-8888"));
            
            List<T> obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(1, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getPhone().add(new PhoneNumber("202-555-8888"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(2, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getPhone().add(new PhoneNumber("404-555-8888"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(0, obj_result.size());
        }
    }
    
    @Test(expected = EntityValidationException.class)
    public void testInvalidPhoneNumberCreate() throws Exception {
        T obj = createSample();
        if ((obj instanceof UsOrCanEnforceable)     
                && ((UsOrCanEnforceable) obj).isUsOrCanadaAddress()) {
            obj = getSampleStructuralRole();
            ((Contactable) obj).getPhone().add(new PhoneNumber("202-555-8888ext4"));
            getService().create(obj);
        } else {
            throw new EntityValidationException(null);
        }
    }
    
    @Test(expected = EntityValidationException.class)
    public void testInvalidFaxNumberCreate() throws Exception {
        T obj = createSample();
        if ((obj instanceof UsOrCanEnforceable)     
                && ((UsOrCanEnforceable) obj).isUsOrCanadaAddress()) {
            obj = getSampleStructuralRole();
            ((Contactable) obj).getFax().add(new PhoneNumber("2025558888"));
            getService().create(obj);
        } else {
            throw new EntityValidationException(null);
        }
    }
    
    @Test(expected = EntityValidationException.class)
    public void testInvalidTtyNumberCreate() throws Exception {
        T obj = createSample();
        if ((obj instanceof UsOrCanEnforceable)     
                && ((UsOrCanEnforceable) obj).isUsOrCanadaAddress()) {
            obj = getSampleStructuralRole();
            ((Contactable) obj).getTty().add(new PhoneNumber("2025558888"));
            getService().create(obj);
        } else {
            throw new EntityValidationException(null);
        }
    }
    
    @Test
    public void testSearchByEmail() throws Exception {
        T obj = createSample();
        if (obj instanceof Contactable) {
            ((Contactable) obj).getEmail().add(new Email("sample1@example.com"));
            ((Contactable) obj).getEmail().add(new Email("sample2@example.com"));
            PoHibernateUtil.getCurrentSession().update(obj);
        
            obj = getSampleStructuralRole();
            ((Contactable) obj).getEmail().add(new Email("sample2@example.com"));
            ((Contactable) obj).getEmail().add(new Email("sample3@example.com"));
            getService().create(obj);
 
            obj = getNewStructuralRole();
            ((Contactable) obj).getEmail().add(new Email("sample3@example.com"));
        
            List<T> obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(1, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getEmail().add(new Email("sample2"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(2, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getEmail().add(new Email("sample4@example.com"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(0, obj_result.size());
        }

    }

    @Test
    public void testSearchByFaxNumber() throws Exception {
        T obj = createSample();
        if (obj instanceof Contactable) {
            ((Contactable) obj).getFax().add(new PhoneNumber("101-555-8888"));
            ((Contactable) obj).getFax().add(new PhoneNumber("202-555-8888"));
            PoHibernateUtil.getCurrentSession().update(obj);
            
            obj = getSampleStructuralRole();
            ((Contactable) obj).getFax().add(new PhoneNumber("202-555-8888"));
            ((Contactable) obj).getFax().add(new PhoneNumber("303-555-8888"));
            getService().create(obj);
     
            obj = getNewStructuralRole();
            ((Contactable) obj).getFax().add(new PhoneNumber("101-555-8888"));
            
            List<T> obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(1, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getFax().add(new PhoneNumber("202-555-8888"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(2, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getFax().add(new PhoneNumber("404-555-8888"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(0, obj_result.size());
        }
    }

    @Test
    public void testSearchByTtyNumber() throws Exception {
        T obj = createSample();
        if (obj instanceof Contactable) {
            ((Contactable) obj).getTty().add(new PhoneNumber("101-555-8888"));
            ((Contactable) obj).getTty().add(new PhoneNumber("202-555-8888"));
            PoHibernateUtil.getCurrentSession().update(obj);
            
            obj = getSampleStructuralRole();
            ((Contactable) obj).getTty().add(new PhoneNumber("202-555-8888"));
            ((Contactable) obj).getTty().add(new PhoneNumber("303-555-8888"));
            getService().create(obj);
     
            obj = getNewStructuralRole();
            ((Contactable) obj).getTty().add(new PhoneNumber("101-555-8888"));
            
            List<T> obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(1, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getTty().add(new PhoneNumber("202-555-8888"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(2, obj_result.size());
    
            obj = getNewStructuralRole();
            ((Contactable) obj).getTty().add(new PhoneNumber("404-555-8888"));
            
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(0, obj_result.size());
        }
    }

    @Test
    public void testSearchByURL() throws Exception {
        T obj = createSample();
        if (obj instanceof Contactable) {
            ((Contactable) obj).getUrl().add(new URL("http://111.com"));
            ((Contactable) obj).getUrl().add(new URL("http://222.com"));
            PoHibernateUtil.getCurrentSession().update(obj);
        
            obj = getSampleStructuralRole();
            ((Contactable) obj).getUrl().add(new URL("http://222.com"));
            ((Contactable) obj).getUrl().add(new URL("http://333.com"));
            getService().create(obj);
        
            obj = getNewStructuralRole();
            ((Contactable) obj).getUrl().add(new URL("http://111.com"));
            List<T> obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(1, obj_result.size());
        
            obj = getNewStructuralRole();
            ((Contactable) obj).getUrl().add(new URL("http://222.com"));
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(2, obj_result.size());
        
            obj = getNewStructuralRole();
            ((Contactable) obj).getUrl().add(new URL("http://444.com"));
            obj_result = getService().search(new AnnotatedBeanSearchCriteria<T>(obj)); 
            assertEquals(0, obj_result.size());
        }
    }



}
