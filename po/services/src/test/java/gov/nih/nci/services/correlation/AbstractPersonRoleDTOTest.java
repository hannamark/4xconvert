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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.CountryTestUtil;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Test to verify the conversion to and from the person role is valid.
 * @author Scott Miller
 */
public abstract class AbstractPersonRoleDTOTest extends AbstractHibernateTestCase {

    private Country defaultCountry;

    public void setDefaultCountry(Country defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    public Country getDefaultCountry() {
        return defaultCountry;
    }

    @Before
    public void setUpTestData() {
        defaultCountry = CountryTestUtil.save(new Country("Afghanistan", "004", "AF", "AFG"));
    }

    protected AbstractPersonRole fillInExamplePersonRoleFields(AbstractPersonRole pr) {
        pr.setId(1L);
        pr.setPlayer(new Person());
        pr.getPlayer().setId(2L);
        pr.setScoper(new Organization());
        pr.getScoper().setId(3L);
        pr.setStatus(RoleStatus.PENDING);
        pr.setEmail(new ArrayList<Email>());
        pr.getEmail().add(new Email("me@example.com"));
        pr.setPhone(new ArrayList<PhoneNumber>());
        pr.getPhone().add(new PhoneNumber("123-456-7890"));
        pr.setFax(new ArrayList<PhoneNumber>());
        pr.getFax().add(new PhoneNumber("098-765-4321"));
        pr.setTty(new ArrayList<PhoneNumber>());
        pr.getTty().add(new PhoneNumber("111-222-3333"));
        pr.setUrl(new ArrayList<URL>());
        pr.getUrl().add(new URL("http://www.example.com"));
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", defaultCountry);
        pr.setPostalAddresses(Collections.singleton(a));
        return pr;
    }

    abstract protected AbstractPersonRole getExampleTestClass();

    abstract protected void verifyTestClassFields(AbstractPersonRoleDTO dto);

    /**
     * Tests creating a snapshot from a model with every field populated.
     */
    @Test
    public void testCreateFullSnapshotFromModel() {
        AbstractPersonRole perRole = getExampleTestClass();
        AbstractPersonRoleDTO dto = (AbstractPersonRoleDTO) PoXsnapshotHelper.createSnapshot(perRole);

        // check person id
        Ii expectedIi = new Ii();
        expectedIi.setExtension("2");
        expectedIi.setDisplayable(true);
        expectedIi.setScope(IdentifierScope.OBJ);
        expectedIi.setReliability(IdentifierReliability.ISS);
        expectedIi.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.PERSON_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getPlayerIdentifier()));

        // check org id
        expectedIi.setExtension("3");
        expectedIi.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.ORG_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getScoperIdentifier()));

        // test status code
        assertEquals("pending", dto.getStatus().getCode());

        // test dest of tel
        boolean emailFound = false;
        boolean phoneFound = false;
        boolean faxFound = false;
        boolean ttyFound = false;
        boolean urlFound = false;
        for (Tel tel : dto.getTelecomAddress().getItem()) {
            String value = tel.getValue().toString();
            if (tel instanceof TelEmail) {
                assertEquals("mailto:me@example.com", value);
                emailFound = true;
            } else if (tel instanceof TelPhone) {
                if (value.startsWith("tel")) {
                    assertEquals("tel:123-456-7890", value);
                    phoneFound = true;
                } else if (value.startsWith("x-text-fax")) {
                    assertEquals("x-text-fax:098-765-4321", value);
                    faxFound = true;
                } else if (value.startsWith("x-text-tel")) {
                    assertEquals("x-text-tel:111-222-3333", value);
                    ttyFound = true;
                }
            } else if (tel instanceof TelUrl) {
                assertEquals("http://www.example.com", value);
                urlFound = true;
            }
        }
        assertTrue(emailFound);
        assertTrue(phoneFound);
        assertTrue(faxFound);
        assertTrue(ttyFound);
        assertTrue(urlFound);

        assertEquals(1, dto.getPostalAddress().getItem().size());
        Ad ad = (Ad)dto.getPostalAddress().getItem().iterator().next();
        List<Adxp> parts = ad.getPart();
        assertEquals(5, parts.size());
        for(Adxp a : parts) {
            if (a instanceof AdxpCnt) { assertEquals(defaultCountry.getAlpha3(), a.getCode());}
            else if (a instanceof AdxpZip) { assertEquals("postalCode", a.getValue());}
            else if (a instanceof AdxpSta) { assertEquals("stateOrProvince", a.getValue());}
            else if (a instanceof AdxpCty) { assertEquals("cityOrMunicipality", a.getValue());}
            else if (a instanceof AdxpAl) { assertEquals("streetAddressLine", a.getValue());} else {
                fail(a.getClass().getName());
            }
        }

        // test instance specific fields
        verifyTestClassFields(dto);
    }

    @SuppressWarnings("unchecked")
    protected AbstractPersonRoleDTO fillInPersonRoleDTOFields(AbstractPersonRoleDTO pr, Long personId, Long orgId)
            throws URISyntaxException {
        Ii ii = new Ii();
        ii.setExtension("" + personId);
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.PERSON_ROOT);
        pr.setPlayerIdentifier(ii);

        ii = new Ii();
        ii.setExtension("" + orgId);
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        pr.setScoperIdentifier(ii);

        Cd status = new Cd();
        status.setCode("active");
        pr.setStatus(status);

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

        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode", defaultCountry.getAlpha3(), defaultCountry.getName());
        pr.setPostalAddress(new DSet<Ad>());
        pr.getPostalAddress().setItem(Collections.singleton(ad));

        return pr;
    }

    abstract protected AbstractPersonRoleDTO getExampleTestClassDTO(Long personId, Long orgId) throws URISyntaxException;

    abstract protected void verifyTestClassDTOFields(AbstractPersonRole pr);

    /**
     * Test creating the model from the snapshot.
     */
    @Test
    public void testCreatFullModelFromSnapshot() throws Exception {
        // create org
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();
        long orgId = orgTest.createOrganization();

        // create person
        PersonServiceBeanTest personTest = new PersonServiceBeanTest();
        personTest.setDefaultCountry(orgTest.getDefaultCountry());
        personTest.setUser(orgTest.getUser());
        Person basicPerson = personTest.getBasicPerson();
        basicPerson.setStatusCode(EntityStatus.PENDING);
        Long personId = (Long) PoHibernateUtil.getCurrentSession().save(basicPerson);
        PoHibernateUtil.getCurrentSession().flush();

        // create bo from snapshot and verify values
        AbstractPersonRoleDTO dto = getExampleTestClassDTO(personId, orgId);
        AbstractPersonRole bo = (AbstractPersonRole) PoXsnapshotHelper.createModel(dto);
        assertEquals(1L, bo.getId().longValue());
        assertEquals(orgId, bo.getScoper().getId().longValue());
        assertEquals(personId, bo.getPlayer().getId());
        assertEquals(RoleStatus.ACTIVE, bo.getStatus());

        assertEquals(1, bo.getEmail().size());
        assertEquals("me@example.com", bo.getEmail().get(0).getValue());

        assertEquals(1, bo.getPhone().size());
        assertEquals("111-222-3333", bo.getPhone().get(0).getValue());
        assertEquals(1, bo.getFax().size());
        assertEquals("222-222-3333", bo.getFax().get(0).getValue());
        assertEquals(1, bo.getTty().size());
        assertEquals("333-222-3333", bo.getTty().get(0).getValue());
        assertEquals(1, bo.getUrl().size());
        assertEquals("http://www.example.com", bo.getUrl().get(0).getValue());

        assertEquals(1, bo.getPostalAddresses().size());
        Address a = bo.getPostalAddresses().iterator().next();
        assertEquals("streetAddressLine", a.getStreetAddressLine());
        assertEquals("deliveryAddressLine", a.getDeliveryAddressLine());
        assertEquals("cityOrMunicipality", a.getCityOrMunicipality());
        assertEquals("stateOrProvince", a.getStateOrProvince());
        assertEquals("postalCode", a.getPostalCode());
        assertEquals(defaultCountry.getId(), a.getCountry().getId());

        // verify that the instance specific fields are correct
        verifyTestClassDTOFields(bo);
    }

    @Test
    public void testSnapshotToModelToSnapshot() {
        // verify that moving from snapshot to model and back to snapshot results in original data.
    }
}
