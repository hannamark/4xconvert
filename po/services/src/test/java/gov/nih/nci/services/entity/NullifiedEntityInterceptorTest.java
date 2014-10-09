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
package gov.nih.nci.services.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.PersonSearchCriteria;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.EntityDto;
import gov.nih.nci.services.correlation.NullifiedRoleInterceptorTest.TestInvocationContext;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.entity.NullifiedEntityInterceptor;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceBean;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

public class NullifiedEntityInterceptorTest  extends AbstractServiceBeanTest {
    NullifiedEntityInterceptor interceptor;
    TestInvocationContext testContext;

    @Before
    public void init() {
        interceptor = new NullifiedEntityInterceptor();
        testContext = new TestInvocationContext();
    }

    @Test
    public void checkForNullifiedUsingOrganizationDTO() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        OrganizationEntityServiceBean oesb = new OrganizationEntityServiceBean();
        OSvcBean svcLocal = new OSvcBean();
        oesb.setOrganizationServiceBean(svcLocal);
        testContext.target = oesb;


        Organization o1 = createOrganization(-1L, EntityStatus.PENDING, null);
        testContext.returnValue = PoXsnapshotHelper.createSnapshot(o1);
        assertEquals(testContext.returnValue, interceptor.checkForNullified(testContext));


        Organization o2 = createOrganization(1L, EntityStatus.NULLIFIED, o1);
        testContext.returnValue = PoXsnapshotHelper.createSnapshot(o2);
        svcLocal.getById = o2;
        try {
            interceptor.checkForNullified(testContext);
            fail("Expected NullifiedEntityException for Ii.extension="
                    + ((EntityDto)testContext.returnValue).getIdentifier().getExtension());
        } catch (NullifiedEntityException e) {
            assertTrue(e.getNullifiedEntities().containsKey(((EntityDto)testContext.returnValue).getIdentifier()));
            Ii duplicateIi = e.getNullifiedEntities().get(((EntityDto)testContext.returnValue).getIdentifier());
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), o1.getId().toString());
        }
    }

    @Test
    public void checkForNullifiedUsingPersonDTO() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        PersonEntityServiceBean oesb = new PersonEntityServiceBean();
        PSvcBean svcLocal = new PSvcBean();
        oesb.setPersonServiceBean(svcLocal);
        testContext.target = oesb;


        Person p1 = new Person();
        p1.setId(-1L);
        p1.setStatusCode(EntityStatus.PENDING);
        testContext.returnValue = PoXsnapshotHelper.createSnapshot(p1);


        assertEquals(testContext.returnValue, interceptor.checkForNullified(testContext));
        Person p2 = new Person();
        p2.setStatusCode(EntityStatus.NULLIFIED);
        p2.setId(1L);
        p2.setDuplicateOf(p1);
        testContext.returnValue = PoXsnapshotHelper.createSnapshot(p2);
        try {
            svcLocal.getById = p2;
            interceptor.checkForNullified(testContext);
            fail("Expected NullifiedEntityException for Ii.extension="
                    + ((EntityDto)testContext.returnValue).getIdentifier().getExtension());
        } catch (NullifiedEntityException e) {
            Ii identifier = ((EntityDto)testContext.returnValue).getIdentifier();
            assertTrue(e.getNullifiedEntities().containsKey(identifier));
            Ii duplicateIi = e.getNullifiedEntities().get(identifier);
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), p1.getId().toString());
        }
    }

    @Test
    public void checkForNullifiedUsingCollectionContainingNonSupportedTypes() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        TestInvocationContext testCxt = new TestInvocationContext();
        ArrayList<String> list = new ArrayList<String>();
        list.add("notused");
        testCxt.returnValue = list;
        assertEquals(testCxt.returnValue, interceptor.checkForNullified(testCxt));
    }

    @Test
    public void checkForNullifiedUsingCollectionContainingSupportedTypePersonDTO() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        TestInvocationContext testCxt = new TestInvocationContext();
        PersonEntityServiceBean oesb = new PersonEntityServiceBean();
        PSvcBean svcLocal = new PSvcBean();
        oesb.setPersonServiceBean(svcLocal);
        testCxt.target = oesb;


        Person p1 = new Person();
        p1.setId(-1L);
        p1.setStatusCode(EntityStatus.PENDING);
        ArrayList<PersonDTO> list = new ArrayList<PersonDTO>();
        list.add((PersonDTO) PoXsnapshotHelper.createSnapshot(p1));
        testCxt.returnValue = list;
        assertEquals(testCxt.returnValue, interceptor.checkForNullified(testCxt));


        Person p2 = new Person();
        p2.setStatusCode(EntityStatus.NULLIFIED);
        p2.setId(1L);
        p2.setDuplicateOf(p1);
        list = new ArrayList<PersonDTO>();
        PersonDTO p2dto = (PersonDTO) PoXsnapshotHelper.createSnapshot(p2);
        list.add(p2dto);
        testCxt.returnValue = list;
        try {
            svcLocal.getById = p2;
            interceptor.checkForNullified(testCxt);
            fail("Expected NullifiedEntityException");
        } catch (NullifiedEntityException e) {
            assertTrue(e.getNullifiedEntities().containsKey(p2dto.getIdentifier()));
            Ii duplicateIi = e.getNullifiedEntities().get(p2dto.getIdentifier());
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), p1.getId().toString());
        }
    }

    @Test
    public void checkForNullifiedUsingCollectionContainingSupportedTypeOrganizationDTO() throws Exception {
        PoHibernateUtil.getCurrentSession().beginTransaction();
        OrganizationEntityServiceBean oesb = new OrganizationEntityServiceBean();
        OSvcBean svcLocal = new OSvcBean();
        oesb.setOrganizationServiceBean(svcLocal);
        testContext.target = oesb;


        Organization o1 = createOrganization(-1L, EntityStatus.PENDING, null);
        ArrayList<EntityDto> list = new ArrayList<EntityDto>();
        list.add((OrganizationDTO) PoXsnapshotHelper.createSnapshot(o1));
        testContext.returnValue = list;
        assertEquals(testContext.returnValue, interceptor.checkForNullified(testContext));


        Organization o2  = createOrganization(1L, EntityStatus.NULLIFIED, o1);
        list = new ArrayList<EntityDto>();
        EntityDto o2dto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(o2);
        list.add(o2dto);
        testContext.returnValue = list;
        try {
            svcLocal.getById = o2;
            interceptor.checkForNullified(testContext);
            fail("Expected NullifiedEntityException");
        } catch (NullifiedEntityException e) {
            assertTrue(e.getNullifiedEntities().containsKey(o2dto.getIdentifier()));
            Ii duplicateIi = e.getNullifiedEntities().get(o2dto.getIdentifier());
            assertNotNull(duplicateIi);
            assertEquals(duplicateIi.getExtension(), o1.getId().toString());
        }
    }

    public static Organization createOrganization(long id, EntityStatus entityStatus, Organization duplicateOf) {
        Organization o1 = new Organization();
        o1.setId(id);
        o1.setStatusCode(entityStatus);
        o1.setDuplicateOf(duplicateOf);
        return o1;
    }

    public static class PSvcBean implements PersonServiceLocal {
        private Person getById;

        public long create(Person person) throws EntityValidationException {
            return 0;
        }

        public Person getById(long id) {
            return getById;
        }
        
        public void setPersonForTesting(Person per) {
            getById = per;
        }

        public void update(Person updatedEntity) {

        }

        public Map<String, String[]> validate(Person entity) {
            return null;
        }

        public int count(SearchCriteria<Person> criteria) {
            return 0;
        }

        public List<Person> search(SearchCriteria<Person> criteria) {
            return null;
        }

        public List<Person> search(SearchCriteria<Person> criteria, PageSortParams<Person> pageSortParams) {
            return null;
        }

        public void curate(Person curatedPerson) throws JMSException {

        }
        @Override
        public List<PersonSearchDTO> search(PersonSearchCriteria criteria,
                PageSortParams<PersonSearchDTO> pageSortParams) {       
            return new ArrayList<PersonSearchDTO>();
        }

        @Override
        public int count(PersonSearchCriteria criteria) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public List<PersonSearchDTO> getInboxPersons(
                PageSortParams<PersonSearchDTO> pageSortParams) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int countInboxPersons() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public long create(Person person, String ctepId)
                throws EntityValidationException, JMSException {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void curate(Person curatedPerson, String ctepId)
                throws EntityValidationException, JMSException {
            // TODO Auto-generated method stub
            
        }
    }

    public static class OSvcBean implements OrganizationServiceLocal {

        private Organization getById;

        public long create(Organization org) throws EntityValidationException {
            return 0;
        }

        public void curate(Organization curatedOrg) {

        }

        public Organization getById(long id) {
            return getById;
        }
        
        public void setOrgForTesting(Organization org) {
            getById = org;
        }

        public void update(Organization updatedEntity) {

        }

        public Map<String, String[]> validate(Organization entity) {
            return null;
        }

        public int count(SearchCriteria<Organization> criteria) {
            return 0;
        }

        public List<Organization> search(SearchCriteria<Organization> criteria) {
            return null;
        }

        public List<Organization> search(SearchCriteria<Organization> criteria,
                PageSortParams<Organization> pageSortParams) {
            return null;
        }

        public Set<Correlation> getAssociatedPlayedRoles(Organization o) {
            return null;
        }

        public Set<Correlation> getAssociatedScopedRoles(Organization o) {
            return null;
        }

        @Override
        public List<OrganizationSearchDTO> search(
                OrganizationSearchCriteria criteria,
                PageSortParams<OrganizationSearchDTO> pageSortParams) {           
            return new ArrayList<OrganizationSearchDTO>();
        }

        @Override
        public long count(OrganizationSearchCriteria criteria) {          
            return 0;
        }
        
        @Override
        public List<OrganizationSearchDTO> getInboxOrgs(
                PageSortParams<OrganizationSearchDTO> pageSortParams) {
            return new ArrayList<OrganizationSearchDTO>();
        }

        @Override
        public long countInboxOrgs() {
           
            return 0;
        }

        @Override
        public void removeChangeRequest(OrganizationCR cr) {
          
            
        }

        @Override
        public void override(Overridable overridable, User overriddenBy) {
            
        }

        @Override
        public long create(Organization org, String ctepId)
                throws EntityValidationException, JMSException {
            return 0;
        }

        @Override
        public void curate(Organization curatedOrg, String ctepId)
                throws EntityValidationException, JMSException {
            
        }

        @Override
        public void curateWithoutCRProcessing(Organization curatedOrg)
                throws JMSException {
           
            
        }

        @Override
        public Long getDuplicateOfNullifiedOrg(String ctepID) {          
            return null;
        }
    }
}
