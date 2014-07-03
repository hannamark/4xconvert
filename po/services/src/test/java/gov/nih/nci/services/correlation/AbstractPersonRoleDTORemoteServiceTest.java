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
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.StatusCodeConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;

/**
 * @author Scott Miller
 *
 */
public abstract class AbstractPersonRoleDTORemoteServiceTest<T extends AbstractPersonRoleDTO,
    CR extends CorrelationChangeRequest<?>> extends AbstractStructrualRoleRemoteServiceTest<T, CR> {

    @SuppressWarnings("unchecked")
    protected void fillInPersonRoleDate(AbstractPersonRoleDTO pr) throws Exception {
        Ii ii = new Ii();
        ii.setExtension("" + basicPerson.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.PERSON_ROOT);
        pr.setPlayerIdentifier(ii);

        ii = new Ii();
        ii.setExtension("" + basicOrganization.getId());
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        pr.setScoperIdentifier(ii);

        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());
        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:me@example.com"));
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
        url.setValue(new URI("http://www.example.com"));
        tels.getItem().add(url);

        pr.setTelecomAddress(tels);

        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality",
                "stateOrProvince", "postalCode", getDefaultCountry().getAlpha3(), getDefaultCountry().getName());
        pr.setPostalAddress(new DSet<Ad>());
        pr.getPostalAddress().setItem(Collections.singleton(ad));
    }

    @SuppressWarnings("unchecked")
    protected void setUpCorrelation2Address(AbstractPersonRoleDTO correlation1, AbstractPersonRoleDTO correlation2) {
        Ad ad = (Ad) correlation1.getPostalAddress().getItem().iterator().next();
        for (Adxp part : ad.getPart()) {
            if (!part.getType().equals(AddressPartType.CNT)) {
                part.setValue(part.getValue()+ "2");
            }
        }
        correlation2.getPostalAddress().setItem(Collections.singleton(ad));
    }

    protected void setUpCorrelation2Tels(AbstractPersonRoleDTO correlation2) throws Exception {
        for (Tel t : correlation2.getTelecomAddress().getItem()) {
            if (t instanceof TelEmail || t instanceof TelUrl) {
                t.setValue(
                        new URI(t.getValue().toString() + "n"));
            } else {
                t.setValue(new URI(t.getValue().toString() + "x2"));
            }
        }
    }

    protected abstract void modifySubClassSpecificFieldsForCorrelation2(T correlation2);

    @Override
    @SuppressWarnings("unchecked")
    public void testSearch() throws Exception {
        Organization org2 = new Organization();
        org2.setName("org2 name");
        org2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        org2.setStatusCode(EntityStatus.ACTIVE);
        org2.getEmail().add(new Email("foo@example.com"));
        org2.getUrl().add(new URL("http://example.com"));
        PoHibernateUtil.getCurrentSession().saveOrUpdate(org2);

        Person person2 = new Person();
        person2.setFirstName("fname2");
        person2.setLastName("lname2");
        person2.setMiddleName("mname2");
        person2.setPostalAddress(new Address("1600 Penn Ave", "Washington", "DC", "20202", getDefaultCountry()));
        person2.setStatusCode(EntityStatus.ACTIVE);
        person2.getEmail().add(new Email("foo@example.com"));
        person2.getUrl().add(new URL("http://example.com"));
        PoHibernateUtil.getCurrentSession().saveOrUpdate(person2);

        T correlation1 = getSampleDto();
        Ii id1 = getCorrelationService().createCorrelation(correlation1);

        T correlation2 = getSampleDto();
        correlation2.getPlayerIdentifier().setExtension("" + person2.getId());
        correlation2.getScoperIdentifier().setExtension("" + org2.getId());
        setUpCorrelation2Address(correlation1, correlation2);
        setUpCorrelation2Tels(correlation2);
        modifySubClassSpecificFieldsForCorrelation2(correlation2);
        Ii id2 = getCorrelationService().createCorrelation(correlation2);

        T searchCriteria = null;

        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        searchCriteria = getEmptySearchCriteria();
        try {
            getCorrelationService().search(searchCriteria);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        // test search by primary id
        Ii id = new Ii();
        id.setExtension(id1.getExtension());
        id.setRoot(id1.getRoot());
        id.setIdentifierName(id1.getIdentifierName());
        id.setDisplayable(id1.getDisplayable());
        id.setReliability(id1.getReliability());
        id.setScope(id1.getScope());
        searchCriteria.setIdentifier(IiConverter.convertToDsetIi(id));
        List<T> results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        searchCriteria.getIdentifier().getItem().iterator().next().setExtension(id2.getExtension());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        // search by person id
        searchCriteria.setIdentifier(null);
        searchCriteria.setPlayerIdentifier(correlation1.getPlayerIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id1.getExtension());

        // search by org id
        searchCriteria.setPlayerIdentifier(null);
        searchCriteria.setScoperIdentifier(correlation2.getScoperIdentifier());
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

        // test search by address
        Ad ad = (Ad) correlation2.getPostalAddress().getItem().iterator().next();
        searchCriteria.setScoperIdentifier(null);
        searchCriteria.setPostalAddress(new DSet<Ad>());
        searchCriteria.getPostalAddress().setItem(Collections.singleton(ad));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

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
                assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(),
                        id2.getExtension());
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

        // verify the starts with search of all address parts
        Ad cor1Ad = (Ad) getSampleDto().getPostalAddress().getItem().iterator().next();
        searchCriteria.setPostalAddress(new DSet<Ad>());
        searchCriteria.getPostalAddress().setItem(Collections.singleton(cor1Ad));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());

        for (Adxp part : cor1Ad.getPart()) {
            if (!part.getType().equals(AddressPartType.CNT)) {
                part.setValue(part.getValue().toUpperCase() + "2");

                results = getCorrelationService().search(searchCriteria);
                assertEquals(1, results.size());
                assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(),
                        id2.getExtension());

                part.setValue(part.getValue().substring(0, part.getValue().length() - 1).toUpperCase());

                results = getCorrelationService().search(searchCriteria);
                assertEquals(2, results.size());
            }
        }

        searchByStatus(searchCriteria, results);

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
        for (Tel t : correlation1.getTelecomAddress().getItem()) {
            searchCriteria.getTelecomAddress().getItem().add(t);
            results = getCorrelationService().search(searchCriteria);
            assertEquals(2, results.size());
        }
        searchCriteria.getTelecomAddress().getItem().clear();
        for (Tel t : getSampleDto().getTelecomAddress().getItem()) {
            if (t instanceof TelEmail || t instanceof TelUrl) {
                t.setValue(
                        new URI(t.getValue().toString() + "n"));
            } else {
                t.setValue(new URI(t.getValue().toString() + "x2"));
            }
            searchCriteria.getTelecomAddress().getItem().add(t);
            results = getCorrelationService().search(searchCriteria);
            assertEquals(1, results.size());
            assertEquals(results.get(0).getIdentifier().getItem().iterator().next().getExtension(), id2.getExtension());

            searchCriteria.getTelecomAddress().getItem().remove(t);
        }

        searchCriteria.getTelecomAddress().getItem().clear();
        testSearchOnSubClassSpecificFields(correlation1, id2, searchCriteria);
    }

    protected void searchByStatus(T searchCriteria, List<T> results) {
        // search by status
        searchCriteria.setPostalAddress(null);
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.PENDING));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(2, results.size());
        searchCriteria.setStatus(StatusCodeConverter.convertToCd(EntityStatus.ACTIVE));
        results = getCorrelationService().search(searchCriteria);
        assertEquals(0, results.size());
    }

    abstract protected void testSearchOnSubClassSpecificFields(T correlation1, Ii id2, T searchCriteria)
            throws NullifiedRoleException;

    protected void verifyPersonRoleDto(AbstractPersonRoleDTO e, AbstractPersonRoleDTO a) {
        assertEquals(e.getScoperIdentifier().getExtension(), a.getScoperIdentifier().getExtension());
        assertEquals(e.getPlayerIdentifier().getExtension(), a.getPlayerIdentifier().getExtension());
        assertEquals("pending", a.getStatus().getCode());
        assertEquals(e.getPostalAddress().getItem().size(), a.getPostalAddress().getItem().size());
        assertEquals(e.getTelecomAddress().getItem().size(), a.getTelecomAddress().getItem().size());
    }
}
