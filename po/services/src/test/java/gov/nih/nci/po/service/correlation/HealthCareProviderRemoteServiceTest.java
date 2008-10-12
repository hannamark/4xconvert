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
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OneCriterionRequiredException;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author Scott Miller
 *
 */
public class HealthCareProviderRemoteServiceTest
    extends AbstractStructrualRoleRemoteServiceTest<HealthCareProviderDTO, HealthCareProviderCR> {

    /**
     * {@inheritDoc}
     */
    @Override
    CorrelationService<HealthCareProviderDTO> getCorrelationService() {
       return EjbTestHelper.getHealthCareProviderCorrelationServiceRemote();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected HealthCareProviderDTO getSampleDto() throws Exception {
        HealthCareProviderDTO pr = new HealthCareProviderDTO();
        fillInPersonRoleDate(pr);
        St st = new St();
        st.setValue("testCertLicense");
        pr.setCertificateLicenseText(st);
        return pr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void verifyDto(HealthCareProviderDTO e, HealthCareProviderDTO a) {
        assertEquals(e.getCertificateLicenseText().getValue(), a.getCertificateLicenseText().getValue());
        verifyPersonRoleDto(e, a);
    }

    @Test(expected = EntityValidationException.class)
    public void testCreateWithException() throws Exception {
        HealthCareProviderDTO pr = new HealthCareProviderDTO();
        getCorrelationService().createCorrelation(pr);
    }

    @Test
    public void testValidate() throws Exception {
        HealthCareProviderDTO pr = new HealthCareProviderDTO();
        Map<String, String[]> errors = getCorrelationService().validate(pr);
        assertEquals(3, errors.keySet().size());
    }

    @Override
    protected void alter(HealthCareProviderDTO dto) {
        dto.setCertificateLicenseText(StringConverter.convertToSt("some new Cert License Text"));
    }

    @Override
    protected void verifyAlterations(HealthCareProviderCR cr) {
        super.verifyAlterations(cr);
        assertEquals("some new Cert License Text", cr.getCertificateLicenseText());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void testSearch() throws Exception {
        Organization org2 = new Organization();
        org2.setAbbreviatedName("org2 abbreviated name");
        org2.setName("org2 name");
        org2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        org2.setStatusCode(EntityStatus.ACTIVE);
        org2.setStatusDate(new Date());
        PoHibernateUtil.getCurrentSession().saveOrUpdate(org2);

        Person person2 = new Person();
        person2.setFirstName("fname2");
        person2.setLastName("lname2");
        person2.setMiddleName("mname2");
        person2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        person2.setStatusCode(EntityStatus.ACTIVE);
        person2.setStatusDate(new Date());
        PoHibernateUtil.getCurrentSession().saveOrUpdate(person2);

        HealthCareProviderDTO correlation1 = getSampleDto();
        Ii id1 = getCorrelationService().createCorrelation(correlation1);

        HealthCareProviderDTO correlation2 = getSampleDto();
        correlation2.getPersonIdentifier().setExtension("" + person2.getId());
        correlation2.getOrganizationIdentifier().setExtension("" + org2.getId());
        Ad ad = AddressConverterUtil.create("1 Tacoma Ave", "Apt 101", "Portland",
                "OR", "97202", getDefaultCountry().getAlpha3());
        correlation2.getPostalAddress().setItem(Collections.singleton(ad));
        correlation2.getTelecomAddress().getItem().clear();
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:me@test.com"));
        correlation2.getTelecomAddress().getItem().add(email);
        TelPhone phone = new TelPhone();
        phone.setValue(new URI("tel:444-555-6666"));
        correlation2.getTelecomAddress().getItem().add(phone);
        phone = new TelPhone();
        phone.setValue(new URI("x-text-fax:777-888-9999"));
        correlation2.getTelecomAddress().getItem().add(phone);
        phone = new TelPhone();
        phone.setValue(new URI("x-text-tel:333-222-1111"));
        correlation2.getTelecomAddress().getItem().add(phone);
        TelUrl url = new TelUrl();
        url.setValue(new URI("http://www.microsoft.com"));
        correlation2.getTelecomAddress().getItem().add(url);
        correlation2.getCertificateLicenseText().setValue("text 2");
        Ii id2 = getCorrelationService().createCorrelation(correlation2);

        HealthCareProviderDTO searchCriteria = null;

        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        searchCriteria = new HealthCareProviderDTO();
        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // test search by primary id
        searchCriteria.setIdentifier(new Ii());
        searchCriteria.getIdentifier().setExtension(id1.getExtension());
        searchCriteria.getIdentifier().setRoot(id1.getRoot());
        searchCriteria.getIdentifier().setIdentifierName(id1.getIdentifierName());
        searchCriteria.getIdentifier().setDisplayable(id1.getDisplayable());
        searchCriteria.getIdentifier().setReliability(id1.getReliability());
        searchCriteria.getIdentifier().setScope(id1.getScope());
        List<HealthCareProviderDTO> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id1.getExtension());

        searchCriteria.getIdentifier().setExtension(id2.getExtension());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id2.getExtension());

        // search by person id
        searchCriteria.setIdentifier(null);
        searchCriteria.setPersonIdentifier(correlation1.getPersonIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id1.getExtension());

        // search by org id
        searchCriteria.setPersonIdentifier(null);
        searchCriteria.setOrganizationIdentifier(correlation2.getOrganizationIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id2.getExtension());

        // test search by address
        searchCriteria.setOrganizationIdentifier(null);
        searchCriteria.setPostalAddress(new DSet<Ad>());
        searchCriteria.getPostalAddress().setItem(Collections.singleton(ad));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id2.getExtension());

        // remove 1 part of address at a time making sure the search still works
        while (ad.getPart().size() > 1) {
            Adxp part = ad.getPart().remove(0);
            if (part.getType().equals(AddressPartType.CNT)) {
                // remove country last
                ad.getPart().remove(0);
                ad.getPart().add(part);
            }
            results = getCorrelationService().search(searchCriteria);
            if (ad.getPart().size() == 1) {
                // when on just country there are 2 results
                assertEquals(2, results.size());
            } else {
                assertEquals(1, results.size());
                assertEquals(results.get(0).getIdentifier().getExtension(), id2.getExtension());
            }
        }

        // no parts left of address, so there should be no criteria.
        try {
            ad.getPart().remove(0);
            results = getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // search by status
        searchCriteria.setPostalAddress(null);
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.PENDING));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.ACTIVE));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        // search by telco
        searchCriteria.setStatus(null);
        searchCriteria.setTelecomAddress(new DSet<Tel>());
        searchCriteria.getTelecomAddress().setItem(new HashSet<Tel>());
        try {
            results = getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }
        searchCriteria.getTelecomAddress().getItem().add(email);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());

        searchCriteria.getTelecomAddress().getItem().add(phone);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id2.getExtension());

        searchCriteria.getTelecomAddress().getItem().add(url);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id2.getExtension());

        phone = new TelPhone();
        phone.setValue(new URI("tel:111-222-3333"));
        searchCriteria.getTelecomAddress().getItem().add(phone);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());

        searchCriteria.getTelecomAddress().getItem().clear();
        searchCriteria.getTelecomAddress().getItem().add(phone);
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getExtension(), id1.getExtension());

        // search by cert text
        searchCriteria.getTelecomAddress().getItem().clear();
        searchCriteria.setCertificateLicenseText(new St());
        searchCriteria.getCertificateLicenseText().setValue("text 2");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());

        searchCriteria.getCertificateLicenseText().setValue("text 2dfjsd");
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());
    }
}
