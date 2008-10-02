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
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.CdConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.RoleStatusConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.AbstractBeanTest;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.PersonRoleDTO;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Scott Miller
 *
 */
public abstract class AbstractStructrualRoleRemoteServiceTest<T extends CorrelationDto, CR extends CorrelationChangeRequest<?>> extends AbstractBeanTest {

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
        basicPerson.setStatusCode(EntityStatus.PENDING);
        PoHibernateUtil.getCurrentSession().save(basicPerson);
        PoHibernateUtil.getCurrentSession().flush();
    }

    abstract protected T getSampleDto() throws Exception;

    abstract void verifyDto(T expected, T actual);

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
    }

    @Test
    public void testGetByIds() throws Exception {
        CorrelationService<T> service = getCorrelationService();

        Ii id1 = service.createCorrelation(getSampleDto());
        Ii id2 = service.createCorrelation(getSampleDto());

        Ii[] ids = {id1, id2};
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
    public void testSearch() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        T dto1 = getSampleDto();
        dto1.setIdentifier(service.createCorrelation(dto1));
        T dto2 = getSampleDto();
        dto2.setIdentifier(service.createCorrelation(dto2));

        List<T> list = service.search(dto1);
        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(dto1.getIdentifier().getExtension(), list.get(0).getIdentifier().getExtension());

        dto1.getIdentifier().setExtension(dto1.getIdentifier().getExtension() + "1");
        list = service.search(dto1);
        assertNotNull(list);
        assertEquals(0, list.size());
    }

    @SuppressWarnings("unchecked")
    protected void fillInPersonRoleDate(PersonRoleDTO pr) throws Exception {
        Ii ii = new Ii();
        ii.setExtension("" + basicPerson.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.PERSON_ROOT);
        pr.setPersonIdentifier(ii);

        ii = new Ii();
        ii.setExtension("" + basicOrganization.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        pr.setOrganizationIdentifier(ii);

        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:me@test.com"));
        tels.getItem().add(email);

        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:111-222-3333"));
        tels.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("x-text-fax:222-222-3333"));
        tels.getItem().add(phone);

        phone = new TelPhone();
        phone.setValue(new URI("x-text-tel:333-222-3333"));
        tels.getItem().add(phone);

        TelUrl url = new TelUrl();
        url.setValue(new URI("http://www.google.com"));
        tels.getItem().add(url);

        pr.setTelecomAddress(tels);

        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality",
                "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3());
        pr.setPostalAddress(new DSet<Ad>());
        pr.getPostalAddress().setItem(Collections.singleton(ad));
    }

    protected void verifyPersonRoleDto(PersonRoleDTO e, PersonRoleDTO a) {
        assertEquals(e.getOrganizationIdentifier().getExtension(), a.getOrganizationIdentifier().getExtension());
        assertEquals(e.getPersonIdentifier().getExtension(), a.getPersonIdentifier().getExtension());
        assertEquals("pending", a.getStatus().getCode());
        assertEquals(e.getPostalAddress().getItem().size(), a.getPostalAddress().getItem().size());
        assertEquals(e.getTelecomAddress().getItem().size(), a.getTelecomAddress().getItem().size());
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

    @Test(expected=IllegalArgumentException.class)
    public void incorrectUpdateCorrelationStatus() throws Exception {
        CorrelationService<T> service = getCorrelationService();
        Ii id = service.createCorrelation(getSampleDto());
        T dto = service.getCorrelation(id);
        assertEquals(RoleStatus.PENDING, CdConverter.convertToRoleStatus(dto.getStatus()));
        dto.setStatus(RoleStatusConverter.convertToCd(RoleStatus.SUSPENDED));
        service.updateCorrelation(dto);
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

}