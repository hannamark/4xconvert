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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.audit.AuditLogDetail;
import gov.nih.nci.po.audit.AuditLogRecord;
import gov.nih.nci.po.audit.AuditType;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ContactInfo;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.CurationStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

public class PersonServiceBeanTest extends AbstractHibernateTestCase {

    private static final Logger LOG = Logger.getLogger(PersonServiceBeanTest.class);

    private PersonServiceBean personServiceBean;

    public Country defaultCountry;
    //private Country defaultCountry2;


    User user;

    @Before
    public void setup() {
        personServiceBean = EjbTestHelper.getPersonServiceBean();

        defaultCountry = new Country("USA", "840", "US", "USA");
        PoHibernateUtil.getCurrentSession().save(defaultCountry);


        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        assertNotNull(defaultCountry);

        user = new User();
        user.setLoginName("unittest" + new Random().nextLong());
        user.setFirstName("first");
        user.setLastName("last");
        user.setUpdateDate(new Date());
        PoHibernateUtil.getCurrentSession().save(user);
        UsernameHolder.setUser(user.getLoginName());
    }

    @After
    public void teardown() {
        personServiceBean = null;
    }

    public Person getBasicPerson() {
        ContactInfo ci = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        Person person = new Person(ci);
        person.setCurationStatus(null);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci.setPerson(person);
        return person;
    }

    @Test
    public void createPerson() throws EntityValidationException {
        Person person = getBasicPerson();
        long id = personServiceBean.create(person);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Person savedPerson = (Person) PoHibernateUtil.getCurrentSession().load(Person.class, id);

        // adjust the expected value to NEW
        person.setCurationStatus(CurationStatus.NEW);
        verifyEquals(person, savedPerson);
        assertEquals(person.getContactInfos().size(), savedPerson.getContactInfos().size());
        assertEquals(1, savedPerson.getContactInfos().size());
        verifyEquals(person.getContactInfos().get(0).getMailingAddress(), savedPerson.getContactInfos().get(0)
                .getMailingAddress());

        assertEquals(new Long(id), savedPerson.getPreferredContactInfo().getPerson().getId());
        PoHibernateUtil.getCurrentSession().flush();

        List<AuditLogRecord> alr = find(Person.class, savedPerson.getId());
        assertDetail(alr, AuditType.INSERT, "firstName", null, "fName", false);
        assertDetail(alr, AuditType.INSERT, "lastName", null, "lName", false);
        ContactInfo ci = savedPerson.getPreferredContactInfo();
        assertDetail(alr, AuditType.INSERT, "preferred_contact_info_id", null, ci.getId().toString(), true);

        alr = find(ContactInfo.class, ci.getId());
        assertDetail(alr, null, "person_id", null, savedPerson.getId().toString(), true);
        assertDetail(alr, null, "mailing_address_id", null, ci.getMailingAddress().getId().toString(), true);

        alr = find(Address.class, ci.getMailingAddress().getId());
        assertDetail(alr, AuditType.INSERT, "streetAddressLine", null, "123 abc ave", false);
        assertDetail(alr, AuditType.INSERT, "country_id", null, defaultCountry.getId().toString(), true);
    }

    @Test
    public void createPersonWithNonNullId() throws EntityValidationException {
        ContactInfo ci = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        Person person = new Person(ci);
        person.setCurationStatus(null);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci.setPerson(person);

        long id = personServiceBean.create(person);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Person savedPerson = personServiceBean.getPerson(id);

        // adjust the expected value to NEW
        person.setCurationStatus(CurationStatus.NEW);
        verifyEquals(person, savedPerson);
        assertEquals(person.getContactInfos().size(), savedPerson.getContactInfos().size());
        verifyEquals(person.getContactInfos().get(0).getMailingAddress(), savedPerson.getContactInfos().get(0)
                .getMailingAddress());

        assertEquals(new Long(id), savedPerson.getPreferredContactInfo().getPerson().getId());
    }

    @Test
    public void createPersonWithNonNullOrNonNewCurationStatusSpecifiedDefaultsToNew() throws EntityValidationException {
        ContactInfo ci = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        Person person = new Person(ci);
        person.setCurationStatus(CurationStatus.CURATED);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci.setPerson(person);

        long id = personServiceBean.create(person);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Person savedPerson = personServiceBean.getPerson(id);

        // adjust the expected value to NEW
        person.setCurationStatus(CurationStatus.NEW);
        verifyEquals(person, savedPerson);
        assertEquals(person.getContactInfos().size(), savedPerson.getContactInfos().size());
        verifyEquals(person.getContactInfos().get(0).getMailingAddress(), savedPerson.getContactInfos().get(0)
                .getMailingAddress());

        assertEquals(person.getId(), savedPerson.getPreferredContactInfo().getPerson().getId());
    }

    private void verifyEquals(Person expected, Person found) {
        assertEquals(expected.getId(), found.getId());
        assertEquals(expected.getCurationStatus(), found.getCurationStatus());
        assertEquals(expected.getDateOfBirth().getTime(), found.getDateOfBirth().getTime());
        assertEquals(expected.getFirstName(), found.getFirstName());
        assertEquals(expected.getLastName(), found.getLastName());
    }

    private void verifyEquals(Address expected, Address found) {
        assertEquals(expected.getId(), found.getId());
        assertEquals(expected.getCityOrMunicipality(), found.getCityOrMunicipality());
        assertEquals(expected.getStateOrProvince(), found.getStateOrProvince());
        assertEquals(expected.getStreetAddressLine(), found.getStreetAddressLine());
        assertEquals(expected.getPostalCode(), found.getPostalCode());
        assertEquals(expected.getCountry().getId(), found.getCountry().getId());
        assertEquals(expected.getCountry().getName(), found.getCountry().getName());
    }

    private ContactInfo createContactInfo(String streetAddressLine, String cityOrMunicipality, String postalCode,
            String stateOrProvince, Country country) {
        ContactInfo ci = new ContactInfo(createAddress(streetAddressLine, cityOrMunicipality, postalCode,
                stateOrProvince, country));
        return ci;
    }

    private Address createAddress(String streetAddressLine, String cityOrMunicipality, String postalCode,
            String stateOrProvince, Country country) {
        return new Address(streetAddressLine, cityOrMunicipality, stateOrProvince, postalCode, country);
    }


    @Test
    public void createPersonWithMultipleContactInfos() throws EntityValidationException {
        ContactInfo ci = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        Person person = new Person(ci);
        person.setCurationStatus(CurationStatus.NEW);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci.setPerson(person);
        ContactInfo ci2 = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        person.getContactInfos().add(ci2);
        ci2.setPerson(person);

        long id = personServiceBean.create(person);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Person savedPerson = personServiceBean.getPerson(id);

        assertEquals(person.getFirstName(), savedPerson.getFirstName());
        assertEquals(person.getLastName(), savedPerson.getLastName());
        assertNotNull(savedPerson.getContactInfos().get(0).getPerson());
        assertNotNull(savedPerson.getContactInfos().get(1).getPerson());
        assertEquals(new Long(id), savedPerson.getContactInfos().get(0).getPerson().getId());
        assertEquals(new Long(id), savedPerson.getContactInfos().get(1).getPerson().getId());
    }

    @Test
    public void createPersonWithMultipleContactInfosButPreferredCIIsNotAddedToCIList() throws EntityValidationException {
        ContactInfo ci = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        Person person = new Person(ci);
        person.setCurationStatus(CurationStatus.NEW);
        Date now = new Date();
        person.setDateOfBirth(now);
        person.setFirstName("fName");
        person.setLastName("lName");
        ci.setPerson(person);
        ContactInfo ci2 = createContactInfo("123 abc ave", "reston", "12345", "VA", defaultCountry);
        person.getContactInfos().add(ci2);
        ci2.setPerson(person);

        long id = personServiceBean.create(person);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Person savedPerson = personServiceBean.getPerson(id);

        assertEquals(person.getFirstName(), savedPerson.getFirstName());
        assertEquals(person.getLastName(), savedPerson.getLastName());
        assertNotNull(savedPerson.getContactInfos().get(0).getPerson());
        assertNotNull(savedPerson.getContactInfos().get(1).getPerson());
        assertEquals(new Long(id), savedPerson.getContactInfos().get(0).getPerson().getId());
        assertEquals(new Long(id), savedPerson.getContactInfos().get(1).getPerson().getId());
    }

    @SuppressWarnings("unchecked")
    private List<AuditLogRecord> find(Class<?> type, Long entityId) {
        String str = "FROM " + AuditLogRecord.class.getName() + " alr "
                     + "WHERE alr.entityName = :entityName "
                     + "  AND alr.entityId = :entityId";
        Query q = PoHibernateUtil.getCurrentSession().createQuery(str);
        q.setLong("entityId", entityId);
        q.setString("entityName", type.getSimpleName());
        List<AuditLogRecord> result = q.list();

        assertTrue(!result.isEmpty());

        return result;
    }

    private void assertDetail(List<AuditLogRecord> alr, AuditType auditType,
            String attribute, String oldVal, String newVal, boolean foreignKey) {
        LOG.debug(String.format("record scan: %s, %s, %s", attribute, oldVal, newVal));
        for (AuditLogRecord r : alr) {
            LOG.debug("examining record: " + r);
            if (auditType == null || r.getType().equals(auditType)) {
                LOG.debug("correct audit type found");
                for (AuditLogDetail ald : r.getDetails()) {
                    LOG.debug(ald.getAttribute() + " " + ald.getOldValue() + " " + ald.getNewValue());
                    if (ald.getAttribute().equals(attribute)
                            && ObjectUtils.equals(ald.getOldValue(), oldVal)
                            && ObjectUtils.equals(ald.getNewValue(), newVal)) {
                        LOG.debug("Correct details found");
                        return;
                    }
                }
            }
        }
        assertTrue(false);
    }

}

