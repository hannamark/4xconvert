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
package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.RoleStatusConverter;
import gov.nih.nci.po.service.AbstractBeanTest;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.AbstractCorrelationServiceBean;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.jms.JMSException;

import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Scott Miller
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityServiceProvider.class)
public abstract class AbstractStructrualRoleRemoteServiceTest<T extends CorrelationDto, CR extends CorrelationChangeRequest<?>>
        extends AbstractServiceBeanTest {

    protected Person basicPerson = null;
    protected Organization basicOrganization = null;
    private final Class<CR> typeCRArgument;

    /**
     * default constructor.
     */
    @SuppressWarnings("unchecked")
    public AbstractStructrualRoleRemoteServiceTest() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        typeCRArgument = (Class<CR>) parameterizedType.getActualTypeArguments()[1];
    }

    @Before
    public void setUpData() throws Exception {
        createAndSetOrganization();

        // create person
        PersonServiceBeanTest personTest = new PersonServiceBeanTest();
        personTest.setDefaultCountry(getDefaultCountry());
        personTest.setUser(getUser());
        basicPerson = personTest.getBasicPerson();
        basicPerson.setStatusCode(EntityStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(basicPerson);
        PoHibernateUtil.getCurrentSession().flush();
    }

    @Before
    public void mockSecurity() {
        UserProvisioningManager userProvisioningManager = mock(UserProvisioningManager.class);
        when(userProvisioningManager.getUser(anyString())).thenAnswer(
                new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        return (User) PoHibernateUtil.getCurrentSession().createCriteria(User.class)
                                .add(Restrictions.eq("loginName", invocation.getArguments()[0])).uniqueResult();
                    }
                }
        );


        mockStatic(SecurityServiceProvider.class);
        try {
            PowerMockito.when(SecurityServiceProvider.getUserProvisioningManager("po")).thenReturn(userProvisioningManager);
        } catch (CSException e) {
            throw new RuntimeException(e);
        }
    }

    protected void createAndSetOrganization() throws EntityValidationException, JMSException {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.setDefaultCountry(getDefaultCountry());
        orgTest.setUser(getUser());
        orgTest.setUpData();
        long orgId = orgTest.createOrganizationNoSessionFlushAndClear();
        PoHibernateUtil.getCurrentSession().flush();
        basicOrganization = (Organization) PoHibernateUtil.getCurrentSession().get(Organization.class, orgId);
    }

    abstract protected T getSampleDto() throws Exception;

    abstract void verifyDto(T expected, T actual);
    protected abstract void verifyCreatedBy(long l);

    abstract CorrelationService<T> getCorrelationService();

    @Test
    public void testSimpleCreateAndGet() throws Exception {
        T dto = getSampleDto();
        CorrelationService<T> service = getCorrelationService();
        Ii id1 = service.createCorrelation(dto);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        T retrievedRole = service.getCorrelation(id1);
        verifyDto(dto, retrievedRole);
        verifyCreatedBy(Long.parseLong(id1.getExtension()));
    }




    @Test
    public void testGetByIds() throws Exception {
        CorrelationService<T> service = getCorrelationService();

        Ii id1 = service.createCorrelation(getSampleDto());
        Ii id2 = service.createCorrelation(getSampleDto());

        Ii[] ids = { id1, id2 };
        List<T> srs = service.getCorrelations(ids);
        assertEquals(2, srs.size());

        ids = new Ii[1];
        ids[0] = id2;
        srs = service.getCorrelations(ids);
        assertEquals(1, srs.size());

        srs = service.getCorrelations(new Ii[0]);
        assertEquals(0, srs.size());
    }

    @Test
    public abstract void testSearch() throws Exception;

    @Test
    public void testSearch2() throws Exception {
        CorrelationService<T> correlationService = getCorrelationService();
        int max = 7;
        AbstractCorrelationServiceBean.setMaxResultsReturnedLimit(max - 2);
        for (int i = 0; i < max; i++) {
            T correlation1 = getSampleDto();
            Ii id1 = correlationService.createCorrelation(correlation1);
        }
        T sc = getEmptySearchCriteria();
        search2StatusChange(sc);

        List<T> results;
        // verify walking forward with a page size of 1
        LimitOffset page = new LimitOffset(1, -1);
        for (int j = 0; j < max; j++) {
            page.next();
            results = correlationService.search(sc, page);
            assertEquals(1, results.size());
        }
        // walk past the last record
        page.next();
        results = correlationService.search(sc, page);
        assertEquals(0, results.size());

        // walk back to first record
        for (int j = 0; j < max; j++) {
            page.previous();
            results = correlationService.search(sc, page);
            assertEquals(1, results.size());
        }
        // try to walk before first record, first record always returned
        page.previous();
        results = correlationService.search(sc, page);
        assertEquals(1, results.size());

        // verify TooManyResultsException is thrown
        try {
            correlationService.search(sc, new LimitOffset(max, 0));
            fail();
        } catch (TooManyResultsException e) {
        }

        // verify TooManyResultsException is thrown
        try {
            correlationService.search(sc, new LimitOffset(max - 1, 0));
            fail();
        } catch (TooManyResultsException e) {
        }

        // verify results are returned
        page = new LimitOffset(max - 2, 0);
        results = correlationService.search(sc, page);
        assertEquals(page.getLimit(), results.size());

        // verify results are returned
        page = new LimitOffset(max - 3, 0);
        results = correlationService.search(sc, page);
        assertEquals(page.getLimit(), results.size());
    }

    @Test
    public void updateCorrelation() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        T dto = service.getCorrelation(id);
        alter(dto);
        service.updateCorrelation(dto);
        @SuppressWarnings("unchecked")
        List<CR> l = PoHibernateUtil.getCurrentSession().createCriteria(typeCRArgument).list();
        assertEquals(1, l.size());
        CR cr = l.get(0);
        verifyAlterations(cr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectUpdateCorrelationStatus() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        T dto = service.getCorrelation(id);
        assertEquals(RoleStatus.PENDING, CdConverter.convertToRoleStatus(dto.getStatus()));
        dto.setStatus(RoleStatusConverter.convertToCd(RoleStatus.SUSPENDED));
        service.updateCorrelation(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCorrelationWithNoISSIdentifier() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        T dto = service.getCorrelation(id);
        for (Ii ii : dto.getIdentifier().getItem()) {
            if (id.equals(ii)) {
                ii.setRoot("bad root" + ii.getRoot());
            }
        }
        service.updateCorrelation(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithNoIdentifier() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        T dto = service.getCorrelation(id);
        dto.getIdentifier().getItem().clear();
        service.updateCorrelation(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateWithWrongIdentifier() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        T dto = service.getCorrelation(id);
        dto.getIdentifier().getItem().clear();
        Ii wrongId = new Ii();
        wrongId.setExtension("999");
        wrongId.setRoot("foo" + IdConverter.BASE_ROOT);
        dto.getIdentifier().getItem().add(wrongId);
        service.updateCorrelation(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatusWithWrongIdentifier() throws Exception {

        Cd cd = new Cd();
        cd.setCode("suspended"); // maps to SUSPENDED

        Ii wrongId = new Ii();
        wrongId.setExtension("999");
        wrongId.setRoot("foo" + IdConverter.BASE_ROOT);

        getCorrelationService().updateCorrelationStatus(wrongId, cd);
    }

    @Test
    public void updateCorrelationStatus() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        service.getCorrelation(id);
        Cd newStatus = RoleStatusConverter.convertToCd(RoleStatus.NULLIFIED);
        service.updateCorrelationStatus(id, newStatus);
        @SuppressWarnings("unchecked")
        List<CR> l = PoHibernateUtil.getCurrentSession().createCriteria(typeCRArgument).list();
        assertEquals(1, l.size());
        CR cr = l.get(0);
        assertEquals(cr.getStatus(), RoleStatus.NULLIFIED);
    }

    protected abstract void alter(T dto) throws Exception;

    protected void verifyAlterations(CR cr) {
        assertEquals(RoleStatus.PENDING, cr.getStatus());
    }

    protected void testNullifiedRoleNotFoundInSearch(Ii id2, T searchCriteria, Class<?> clazz) {
        searchCriteria.setIdentifier(IiConverter.convertToDsetIi(id2));
        List<T> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());

        Correlation role =
                (Correlation) PoHibernateUtil.getCurrentSession().get(clazz, Long.parseLong(id2.getExtension()));
        role.setStatus(RoleStatus.NULLIFIED);
        PoHibernateUtil.getCurrentSession().saveOrUpdate(role);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());
    }

    protected abstract T getEmptySearchCriteria();

    protected void search2StatusChange(T sc) {
        sc.setStatus(RoleStatusConverter.convertToCd(RoleStatus.PENDING));
    }
}
