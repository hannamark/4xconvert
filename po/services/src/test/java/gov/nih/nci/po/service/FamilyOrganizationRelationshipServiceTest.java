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
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.PropertyValueException;
import org.hibernate.validator.InvalidStateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mshestopalov
 * 
 */
public class FamilyOrganizationRelationshipServiceTest extends AbstractServiceBeanTest {
    private FamilyOrganizationRelationshipServiceBean familyOrgRelServiceLocal;
    private OrganizationRelationshipServiceLocal orgRelServiceLocal = mock(OrganizationRelationshipServiceBean.class);
    private Date today = DateUtils.truncate(new Date(), Calendar.DATE);
    private Date oldDate;
    private Country country = new Country("testorg", "996", "IJ", "IJI");

    @Before
    public void setUpData() {
        familyOrgRelServiceLocal = (FamilyOrganizationRelationshipServiceBean) EjbTestHelper
                .getFamilyOrganizationRelationshipService();
        familyOrgRelServiceLocal.setOrgRelService(orgRelServiceLocal);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, 01, 02);
        oldDate = DateUtils.truncate(cal.getTime(), Calendar.DATE);
        PoHibernateUtil.getCurrentSession().save(country);
    }

    @After
    public void teardown() {
        familyOrgRelServiceLocal = null;
        orgRelServiceLocal = null;
    }

    @Test
    public void testFamilyOrgRelationship() throws EntityValidationException, JMSException, ParseException {
        // create test
        Family savedFam = createFamily();
        assertNotNull(savedFam.getId());

        Organization savedOrg = createOrg();
        assertNotNull(savedOrg.getId());

        FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
        famOrgRel.setOrganization(savedOrg);
        famOrgRel.setFamily(savedFam);
        famOrgRel.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRel.setStartDate(oldDate);

        long famOrgRelId = familyOrgRelServiceLocal.create(famOrgRel);
        assertEquals(oldDate, familyOrgRelServiceLocal.getActiveStartDate(savedFam.getId(), savedOrg.getId()));
        FamilyOrganizationRelationship savedFamOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil
                .getCurrentSession().load(FamilyOrganizationRelationship.class, famOrgRelId);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        assertNotNull(savedFamOrgRel.getId());

        // update test, get test
        savedFamOrgRel.setFunctionalType(FamilyFunctionalType.CONTRACTUAL);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        savedFamOrgRel.setStartDate(sdf.parse("01/01/2009"));
        familyOrgRelServiceLocal.updateEntity(savedFamOrgRel);
        FamilyOrganizationRelationship freshFamOrgRel = familyOrgRelServiceLocal.getById(famOrgRelId);
        assertEquals(freshFamOrgRel.getFunctionalType(), FamilyFunctionalType.CONTRACTUAL);
        assertEquals(sdf.format(freshFamOrgRel.getStartDate()), "01/01/2009");

        Organization savedOrg2 = createOrg();
        assertNotNull(savedOrg2.getId());

        FamilyOrganizationRelationship famOrgRel2 = new FamilyOrganizationRelationship();
        famOrgRel2.setOrganization(savedOrg2);
        famOrgRel2.setFamily(savedFam);
        famOrgRel2.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRel2.setStartDate(oldDate);

        familyOrgRelServiceLocal.create(famOrgRel2);
        assertEquals(2, ((Family) PoHibernateUtil.getCurrentSession().load(Family.class, savedFam.getId()))
                .getFamilyOrganizationRelationships().size());

        // create second fam for test
        Family family2 = new Family();
        family2.setName("FamilyName2");
        family2.setStartDate(oldDate);
        family2.setStatusCode(FamilyStatus.NULLIFIED);
        long famId2 = EjbTestHelper.getFamilyServiceBean().create(family2);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Family savedFam2 = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, famId2);
        assertNotNull(savedFam2.getId());

        FamilyOrganizationRelationship famOrgRel3 = new FamilyOrganizationRelationship();
        famOrgRel3.setOrganization(savedOrg2);
        famOrgRel3.setFamily(savedFam2);
        famOrgRel3.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRel3.setStartDate(oldDate);

        familyOrgRelServiceLocal.create(famOrgRel3);
        assertEquals(2,
                ((Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, savedOrg2.getId()))
                        .getFamilyOrganizationRelationships().size());
    }

    @Test(expected = PropertyValueException.class)
    public void testNotNullValidation() {
        familyOrgRelServiceLocal.create(new FamilyOrganizationRelationship());
    }

    @Test
    public void testEndDateInTheFuture() throws EntityValidationException, JMSException {
        try {
            createFamOrgRel(createFamily(), createOrg(), new Date(), new Date(new Date().getTime() + 300000000));
            fail("InvalidStateException expected");
        } catch(InvalidStateException e) {
            assertEquals(2, e.getInvalidValues().length);
            assertEquals("Invalid date range based on family relationships.", e.getInvalidValues()[0].getMessage());
            assertEquals("(fieldName) must not be in the future.", e.getInvalidValues()[1].getMessage());
            assertEquals("endDate", e.getInvalidValues()[1].getPropertyName());
        }
    }

    @Test
    public void testEndDateBeforeStartDate() throws EntityValidationException, JMSException {
        try {
            createFamOrgRel(createFamily(), createOrg(), new Date(new Date().getTime() - 300000000), new Date(new Date().getTime() - 500000000));
            fail("InvalidStateException expected");
        } catch(InvalidStateException e) {
            assertEquals(1, e.getInvalidValues().length);
            assertEquals("startDate must be before endDate.", e.getInvalidValues()[0].getMessage());
        }
    }

    @Test
    public void testGetActiveRelationships() throws EntityValidationException, JMSException, ParseException {
        // Create all the needed data
        Family savedFam = createFamily();
        assertNotNull(savedFam.getId());

        Organization savedOrg = createOrg();
        long famOrgRelId1 = createFamOrgRel(savedFam, savedOrg);
        assertEquals(1, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());

        Organization savedOrg2 = createOrg();
        long famOrgRelId2 = createFamOrgRel(savedFam, savedOrg2);
        assertEquals(2, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());

        Organization savedOrg3 = createOrg();
        long famOrgRelId3 = createFamOrgRel(savedFam, savedOrg3);
        assertEquals(3, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());

        // test that inter-organization relationships don't cause duplicates to be returned by getActiveRelationships
        // (PO-3323)
        OrganizationRelationship or = new OrganizationRelationship();
        or.setFamily(savedFam);
        or.setHierarchicalType(FamilyHierarchicalType.PEER);
        or.setOrganization(savedOrg);
        or.setRelatedOrganization(savedOrg2);
        or.setStartDate(oldDate);
        EjbTestHelper.getOrganizationRelationshipService().create(or);
        assertEquals(3, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());

        // now deactivate the relationship to make sure the active count decreases
        FamilyOrganizationRelationship savedFamOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil
                .getCurrentSession().load(FamilyOrganizationRelationship.class, famOrgRelId1);
        savedFamOrgRel.setEndDate(new Date());
        familyOrgRelServiceLocal.updateEntity(savedFamOrgRel);
        FamilyOrganizationRelationship updatedFamOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil
                .getCurrentSession().load(FamilyOrganizationRelationship.class, famOrgRelId1);
        assertNotNull(updatedFamOrgRel.getEndDate());
        assertTrue(CollectionUtils.isEmpty(orgRelServiceLocal.getActiveOrganizationRelationships(updatedFamOrgRel
                .getFamily().getId(), updatedFamOrgRel.getOrganization().getId())));
        assertEquals(2, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());

        savedFamOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil.getCurrentSession().load(
                FamilyOrganizationRelationship.class, famOrgRelId2);
        savedFamOrgRel.setEndDate(new Date());
        familyOrgRelServiceLocal.updateEntity(savedFamOrgRel);
        assertEquals(1, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());

        savedFamOrgRel = (FamilyOrganizationRelationship) PoHibernateUtil.getCurrentSession().load(
                FamilyOrganizationRelationship.class, famOrgRelId3);
        savedFamOrgRel.setEndDate(new Date());
        familyOrgRelServiceLocal.updateEntity(savedFamOrgRel);
        assertEquals(0, familyOrgRelServiceLocal.getActiveRelationships(savedFam.getId()).size());
    }

    @Test
    public void testGetEarliestStartDate() throws EntityValidationException, JMSException {
        // Create all the needed data
        Family savedFam = createFamily();

        Organization savedOrg = createOrg();
        createFamOrgRel(savedFam, savedOrg, oldDate);
        Organization savedOrg2 = createOrg();
        createFamOrgRel(savedFam, savedOrg2, today);
        Organization savedOrg3 = createOrg();
        createFamOrgRel(savedFam, savedOrg3, today);
        assertEquals(oldDate, familyOrgRelServiceLocal.getEarliestStartDate(savedFam.getId()));
    }

    @Test
    public void testGetLatestStartDate() throws EntityValidationException, JMSException {
        // Create all the needed data
        Family savedFam = createFamily();

        Organization savedOrg = createOrg();
        createFamOrgRel(savedFam, savedOrg, oldDate);
        assertEquals(oldDate, familyOrgRelServiceLocal.getLatestStartDate(savedFam.getId()));

        Organization savedOrg2 = createOrg();
        createFamOrgRel(savedFam, savedOrg2, DateUtils.addDays(oldDate, 1));
        assertEquals(DateUtils.addDays(oldDate, 1), familyOrgRelServiceLocal.getLatestStartDate(savedFam.getId()));

        Organization savedOrg3 = createOrg();
        createFamOrgRel(savedFam, savedOrg3, today, today);
        assertEquals(today, familyOrgRelServiceLocal.getLatestStartDate(savedFam.getId()));
    }

    @Test
    public void testGetLatestEndDate() throws EntityValidationException, JMSException {
        // Create all the needed data
        Family savedFam = createFamily();
        Organization savedOrg = createOrg();

        createFamOrgRel(savedFam, savedOrg, oldDate, null);
        Organization savedOrg2 = createOrg();
        createFamOrgRel(savedFam, savedOrg2, oldDate, oldDate);
        Organization savedOrg3 = createOrg();
        createFamOrgRel(savedFam, savedOrg3, today, today);
        assertEquals(today, familyOrgRelServiceLocal.getLatestEndDate(savedFam.getId()));
    }

    @Test
    public void testGetLatestAllowableStartDate() throws EntityValidationException, JMSException {
        // Create all the needed data
        Family savedFam = createFamily();

        Organization savedOrg = createOrg();
        createFamOrgRel(savedFam, savedOrg, oldDate);

        when(orgRelServiceLocal.getEarliestStartDate(anyLong(), anyLong())).thenReturn(null);
        assertEquals(today, familyOrgRelServiceLocal.getLatestAllowableStartDate(savedFam.getId()));

        when(orgRelServiceLocal.getEarliestStartDate(anyLong(), anyLong())).thenReturn(today);
        assertEquals(today, familyOrgRelServiceLocal.getLatestAllowableStartDate(savedFam.getId()));
    }

    @Test
    public void testGetEarliestAllowableEndDate() throws EntityValidationException, JMSException {
        // Create all the needed data
        Family family = new Family();
        family.setName("FamilyName");
        family.setStartDate(DateUtils.addDays(oldDate, -1));
        family.setStatusCode(FamilyStatus.ACTIVE);
        long id = EjbTestHelper.getFamilyServiceBean().create(family);
        Family savedFam = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);

        Organization savedOrg = createOrg();
        createFamOrgRel(savedFam, savedOrg, oldDate);

        when(orgRelServiceLocal.getLatestStartDate(anyLong(), anyLong())).thenReturn(null);
        when(orgRelServiceLocal.getLatestEndDate(anyLong(), anyLong())).thenReturn(null);
        assertEquals(oldDate, familyOrgRelServiceLocal.getEarliestAllowableEndDate(id));

        when(orgRelServiceLocal.getLatestStartDate(anyLong(), anyLong())).thenReturn(DateUtils.addDays(oldDate, 1));
        when(orgRelServiceLocal.getLatestEndDate(anyLong(), anyLong())).thenReturn(null);
        assertEquals(DateUtils.addDays(oldDate, 1), familyOrgRelServiceLocal.getEarliestAllowableEndDate(id));

        when(orgRelServiceLocal.getLatestStartDate(anyLong(), anyLong())).thenReturn(null);
        when(orgRelServiceLocal.getLatestEndDate(anyLong(), anyLong())).thenReturn(DateUtils.addDays(oldDate, 2));
        assertEquals(DateUtils.addDays(oldDate, 2), familyOrgRelServiceLocal.getEarliestAllowableEndDate(id));

        when(orgRelServiceLocal.getLatestStartDate(anyLong(), anyLong())).thenReturn(DateUtils.addDays(oldDate, 3));
        when(orgRelServiceLocal.getLatestEndDate(anyLong(), anyLong())).thenReturn(DateUtils.addDays(oldDate, 4));
        assertEquals(DateUtils.addDays(oldDate, 4), familyOrgRelServiceLocal.getEarliestAllowableEndDate(id));

        when(orgRelServiceLocal.getLatestStartDate(anyLong(), anyLong())).thenReturn(DateUtils.addDays(oldDate, 6));
        when(orgRelServiceLocal.getLatestEndDate(anyLong(), anyLong())).thenReturn(DateUtils.addDays(oldDate, 5));
        assertEquals(DateUtils.addDays(oldDate, 6), familyOrgRelServiceLocal.getEarliestAllowableEndDate(id));
    }

    private long createFamOrgRel(Family savedFam, Organization savedOrg) throws EntityValidationException {
        return createFamOrgRel(savedFam, savedOrg, oldDate);
    }

    private long createFamOrgRel(Family savedFam, Organization savedOrg, Date startDate)
            throws EntityValidationException {
        return createFamOrgRel(savedFam, savedOrg, startDate, null);
    }

    private long createFamOrgRel(Family savedFam, Organization savedOrg, Date startDate, Date endDate)
            throws EntityValidationException {
        FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
        famOrgRel.setOrganization(savedOrg);
        famOrgRel.setFamily(savedFam);
        famOrgRel.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRel.setStartDate(startDate);
        famOrgRel.setEndDate(endDate);
        long famOrgRelId = familyOrgRelServiceLocal.create(famOrgRel);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        return famOrgRelId;
    }

    private Family createFamily() {
        Family family = new Family();
        family.setName("FamilyName");
        family.setStartDate(oldDate);
        family.setStatusCode(FamilyStatus.NULLIFIED);
        long id = EjbTestHelper.getFamilyServiceBean().create(family);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        return (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
    }

    private Organization createOrg() throws EntityValidationException, JMSException {
        Address mailingAddress = new Address("test", "test", "test", "test", country);

        Organization org = new Organization();
        org.setName(StringUtils.repeat("testO", 32));
        org.setPostalAddress(mailingAddress);
        org.setStatusCode(EntityStatus.NULLIFIED);
        org.getEmail().add(new Email("foo@example.com"));
        org.getUrl().add(new URL("http://example.com"));
        long orgId = EjbTestHelper.getOrganizationServiceBean().create(org);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        Organization savedOrg = (Organization) PoHibernateUtil.getCurrentSession().load(Organization.class, orgId);
        assertNotNull(savedOrg.getId());
        return savedOrg;
    }

}
