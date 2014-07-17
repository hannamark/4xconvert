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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.audit.AuditLogRecord;
import com.fiveamsolutions.nci.commons.audit.AuditType;
import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class PersonServiceBeanTest extends AbstractServiceBeanTest {

    private PersonServiceBean personServiceBean;
    private ServiceLocator serviceLocatorToRestore;
    private Organization ctepOrganization;

    public PersonServiceBean getPersonServiceBean() {
        return personServiceBean;
    }

    @Before
    public void setUpData() {        
        personServiceBean = EjbTestHelper.getPersonServiceBean();

        ctepOrganization = new Organization();
        ctepOrganization.setName("Cancer Therapy Evaluation Program");
        ctepOrganization.setStatusCode(EntityStatus.ACTIVE);
        ctepOrganization.setPostalAddress(new Address());
        ctepOrganization.getPostalAddress().setStreetAddressLine("bogus");
        ctepOrganization.getPostalAddress().setCityOrMunicipality("city");
        ctepOrganization.getPostalAddress().setStateOrProvince("VA");
        ctepOrganization.getPostalAddress().setPostalCode("12345");
        ctepOrganization.getPostalAddress().setCountry(getDefaultCountry());
        ctepOrganization.getEmail().add(new Email("abc@example.com"));
        PoHibernateUtil.getCurrentSession().save(ctepOrganization);
    }

    @After
    public void teardown() {
        personServiceBean = null;

        if (serviceLocatorToRestore != null) {
            PoRegistry.getInstance().setServiceLocator(serviceLocatorToRestore);
        }
    }

    public Person getBasicPerson() {
        Person person = new Person();
        person.setStatusCode(null);
        person.setFirstName("fName");
        person.setLastName("lName");

        Address a =
                new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                        getDefaultCountry());
        a.setDeliveryAddressLine("deliveryAddressLine");
        person.setPostalAddress(a);

        person.getEmail().add(new Email("abc@example.com"));
        person.getEmail().add(new Email("def@example.com"));

        person.getPhone().add(new PhoneNumber("111-111-1111"));
        person.getPhone().add(new PhoneNumber("123-123-1234"));

        person.getFax().add(new PhoneNumber("222-222-2222"));
        person.getFax().add(new PhoneNumber("234-234-2345"));

        person.getTty().add(new PhoneNumber("333-333-3333"));
        person.getTty().add(new PhoneNumber("345-345-3456"));

        person.getUrl().add(new URL("http://www.example.com/abc"));
        person.getUrl().add(new URL("http://www.example.com/def"));
        return person;
    }

    @Test
    public void create() throws EntityValidationException, JMSException {
        createPerson();
    }
    
    @Test
    public void createActive() throws EntityValidationException, JMSException {
        Person person = getBasicPerson();
        person.setStatusCode(EntityStatus.ACTIVE);
        
        long id = personServiceBean.create(person);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Person savedPerson = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

        // adjust the expected value to NEW
        person.setStatusCode(EntityStatus.ACTIVE);
        verifyEquals(person, savedPerson);
        PoHibernateUtil.getCurrentSession().flush();        
    }
    
    
    
    @Test
    public void createPersonWithCtepId() throws EntityValidationException, JMSException {   
        setupMockData();
        String ctepId="123456";
        Person person = getBasicPerson();
        person.setStatusCode(EntityStatus.ACTIVE);        
        long id = personServiceBean.create(person, ctepId);        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Person savedPerson = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);
        
        verifyEquals(person, savedPerson);
        PoHibernateUtil.getCurrentSession().flush();        
    }

    @Test
    public void updatePersonWithCtepId() throws EntityValidationException, JMSException {   
        setupMockData();
        String ctepId="123456";
        Person person = getBasicPerson();
        person.setStatusCode(EntityStatus.ACTIVE);        
        long id = personServiceBean.create(person);        
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();        
        
        // now update the person
        person.setFirstName("update fname");
        personServiceBean.curate(person, ctepId);


        Person savedPerson = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);
        
        verifyEquals(person, savedPerson);

        assertCtepId(savedPerson, ctepId);


        for (int i=0; i<3; i++) {
            ctepId = ctepId + i;
            personServiceBean.curate(person, ctepId);

            person = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

            assertCtepId(person, ctepId);

            PoHibernateUtil.getCurrentSession().flush();
        }
    }

    @Test
    public void updatePersonWithMultipleCtepId() throws EntityValidationException, JMSException {
        setupMockData();
        String ctepId="123456";
        Person person = getBasicPerson();
        person.setStatusCode(EntityStatus.ACTIVE);
        long id = personServiceBean.create(person);

        for (int i=0; i<3; i++) {
            Ii assignedIdentifier = new Ii();
            assignedIdentifier.setRoot(PoConstants.PERSON_CTEP_ID_ROOT);
            assignedIdentifier.setIdentifierName(PoConstants.PERSON_CTEP_ID_IDENTIFIER_NAME);
            assignedIdentifier.setExtension("ctepId" + i);

            IdentifiedPerson identifiedPerson = new IdentifiedPerson();
            identifiedPerson.setAssignedIdentifier(assignedIdentifier);
            identifiedPerson.setPlayer(person);
            identifiedPerson.setScoper(ctepOrganization);
            identifiedPerson.setStatus(RoleStatus.ACTIVE);
            PoHibernateUtil.getCurrentSession().save(identifiedPerson);
        }

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // now update the person
        person.setFirstName("update fname");
        personServiceBean.curate(person, ctepId);


        Person savedPerson = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

        verifyEquals(person, savedPerson);

        assertCtepId(savedPerson, ctepId);
    }

    private void assertCtepId(Person person, String ctepId) {
        List<IdentifiedPerson> identifiedPersons =
                PoHibernateUtil.getCurrentSession().createCriteria(IdentifiedPerson.class)
                    .add(Restrictions.eq("player.id", person.getId())).list();

        assertEquals(1, identifiedPersons.size());
        assertEquals(ctepId, identifiedPersons.get(0).getAssignedIdentifier().getExtension());
    }

    private ServiceLocator setupMockData() throws EntityValidationException, JMSException{
        ServiceLocator serviceLocator = null;
        serviceLocator = mock(ServiceLocator.class);

        serviceLocatorToRestore = PoRegistry.getInstance().getServiceLocator();
        PoRegistry.getInstance().setServiceLocator(serviceLocator);

        // Mock setup for getting Organization
        OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(
                orgSerLocal);
        when(
                orgSerLocal.search(isA(OrganizationSearchCriteria.class),
                        isA(PageSortParams.class))).thenReturn(
                getOrgSearchDtoList());

        when(orgSerLocal.getById(any(Long.class)))
                .thenAnswer( new Answer<Organization>() {
                    @Override
                    public Organization answer(InvocationOnMock invocation) throws Throwable {
                        return (Organization) PoHibernateUtil.getCurrentSession()
                                .get(Organization.class, (Long) invocation.getArguments()[0]);
                    }
                });
        // Mock setup for getting IdentifiedPerson
        IdentifiedPersonServiceLocal ipSerLocal = EjbTestHelper.getIdentifiedPersonServiceBean();
        personServiceBean.setIdenPerServ(ipSerLocal);
        when(serviceLocator.getIdentifiedPersonService()).thenReturn(
                ipSerLocal);

        return serviceLocator;
    }
    
    private List<OrganizationSearchDTO> getOrgSearchDtoList() {
        List<OrganizationSearchDTO> orgSearchDtoList = new ArrayList<OrganizationSearchDTO>();
        OrganizationSearchDTO dto = new OrganizationSearchDTO();
        dto.setId(1l);
        dto.setName("Cancer Therapy Evaluation Program");
        dto.setStatusCode("ACTIVE");
        dto.setStatusDate(new Date());
        orgSearchDtoList.add(dto);
        return orgSearchDtoList;
    }    
       

    public long createPerson() throws EntityValidationException, JMSException {
        return createPerson(getBasicPerson());
    }

    protected long createPerson(Person person) throws EntityValidationException, JMSException {
        long id = personServiceBean.create(person);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Person savedPerson = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

        // adjust the expected value to NEW
        person.setStatusCode(EntityStatus.PENDING);
        verifyEquals(person, savedPerson);
        PoHibernateUtil.getCurrentSession().flush();

        List<AuditLogRecord> alr = AuditTestUtil.find(Person.class, savedPerson.getId());
        AuditTestUtil.assertDetail(alr, AuditType.INSERT, "firstName", null, "fName", false);
        AuditTestUtil.assertDetail(alr, AuditType.INSERT, "lastName", null, "lName", false);
        return id;
    }

    @Test
    public void createPersonWithNonNullOrNonNewCurationStatusSpecifiedDefaultsToNew() throws EntityValidationException,
            JMSException {
        Person person = getBasicPerson();
        person.setStatusCode(EntityStatus.ACTIVE);
        person.setFirstName("fName");
        person.setLastName("lName");

        long id = personServiceBean.create(person);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Person savedPerson = personServiceBean.getById(id);

        // adjust the expected value to NEW
        person.setStatusCode(EntityStatus.ACTIVE);
        verifyEquals(person, savedPerson);
    }

    private void verifyEquals(Person expected, Person found) {
        assertEquals(expected.getId(), found.getId());
        assertEquals(expected.getStatusCode(), found.getStatusCode());
        assertEquals(expected.getFirstName(), found.getFirstName());
        assertEquals(expected.getLastName(), found.getLastName());

        assertEquals(expected.getEmail().size(), found.getEmail().size());
        assertEquals(expected.getPhone().size(), found.getPhone().size());
        assertEquals(expected.getFax().size(), found.getFax().size());
        assertEquals(expected.getTty().size(), found.getTty().size());
        assertEquals(expected.getUrl().size(), found.getUrl().size());
    }

}
