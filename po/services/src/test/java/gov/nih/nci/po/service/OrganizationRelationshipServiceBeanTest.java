/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po-app Software License (the License) is between NCI and You. You (or
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
 * its rights in the po-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po-app Software; (ii) distribute and
 * have distributed to and by third parties the po-app Software and any
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.data.dao.FamilyUtilDao;
import gov.nih.nci.po.util.FamilyDateValidator;
import gov.nih.nci.po.util.OrgRelStartDateValidator;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.hibernate.validator.InvalidStateException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrganizationRelationshipServiceBeanTest extends AbstractServiceBeanTest {
    private OrganizationRelationshipServiceLocal orgRelServiceBean;
    private FamilyOrganizationRelationshipServiceBean famOrganizationRelationshipService = mock(FamilyOrganizationRelationshipServiceBean.class);
    private FamilyServiceLocal familyServiceBean;
    private OrganizationServiceLocal orgServiceBean;
    private Calendar cal = Calendar.getInstance();
    private Date oldDate;
    private FamilyUtilDao familyUtilDao = mock(FamilyUtilDao.class);

    @Before
    public void setUpData() {
        orgRelServiceBean = EjbTestHelper.getOrganizationRelationshipService();
        familyServiceBean = EjbTestHelper.getFamilyServiceBean();
        ((FamilyOrganizationRelationshipServiceBean) famOrganizationRelationshipService)
                .setOrgRelService(orgRelServiceBean);
        ((FamilyServiceBean) familyServiceBean).setFamilyOrgRelService(famOrganizationRelationshipService);
        orgServiceBean = EjbTestHelper.getOrganizationServiceBean();
        FamilyDateValidator.setFamilyService(familyServiceBean);
        cal.set(2011, 01, 02);
        oldDate = DateUtils.truncate(cal.getTime(), Calendar.DATE);
        OrgRelStartDateValidator.setFamilyDao(familyUtilDao);
        when(familyUtilDao.getActiveStartDate(any(Session.class), anyLong(), anyLong())).thenReturn(oldDate);
    }

    @After
    public void teardown() {
        FamilyDateValidator.setFamilyService(PoRegistry.getFamilyService());
        OrgRelStartDateValidator.setFamilyDao(new FamilyUtilDao());
        orgRelServiceBean = null;
    }

    @Test
    public void testOrganizationRelationship() throws EntityValidationException, JMSException {
        OrganizationRelationship orgRel = createOrgRelationship();
        long id = orgRel.getId();
        assertNotNull(id);
        assertNotNull(orgRel.getStartDate());
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        OrganizationRelationship saved = (OrganizationRelationship) PoHibernateUtil.getCurrentSession().load(
                OrganizationRelationship.class, id);
        assertNotNull(saved.getFamily().getName());

        OrganizationRelationship get = orgRelServiceBean.getById(id);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        assertEquals(get.getFamily().getName(), orgRel.getFamily().getName());

        OrganizationRelationship tosearch = new OrganizationRelationship();
        Family familyCriteria = new Family();
        familyCriteria.setName("UpdatedFamilyName");
        tosearch.setFamily(familyServiceBean.getById(orgRel.getFamily().getId()));
        AnnotatedBeanSearchCriteria<OrganizationRelationship> scriteria = new AnnotatedBeanSearchCriteria<OrganizationRelationship>(
                tosearch);
        assertEquals(orgRelServiceBean.search(scriteria).size(), 2);

        tosearch = new OrganizationRelationship();
        tosearch.setOrganization(orgServiceBean.getById(orgRel.getOrganization().getId()));
        scriteria = new AnnotatedBeanSearchCriteria<OrganizationRelationship>(tosearch);
        assertEquals(orgRelServiceBean.search(scriteria).size(), 1);
    }

    @Test
    public void testUpdateRelationship() throws EntityValidationException, JMSException {
        long id = createOrgRelationship().getId();
        OrganizationRelationship toUpdate = orgRelServiceBean.getById(id);
        toUpdate.getFamily().setName("UpdatedFamilyName");
        orgRelServiceBean.updateEntity(toUpdate);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        // ensures that hierarchical Type is not updatable.
        toUpdate.setHierarchicalType(FamilyHierarchicalType.CHILD);

        orgRelServiceBean.updateEntity(toUpdate);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        toUpdate = orgRelServiceBean.getById(id);
        assertFalse(FamilyHierarchicalType.CHILD.equals(toUpdate.getHierarchicalType()));
        assertEquals(toUpdate.getHierarchicalType(), FamilyHierarchicalType.PEER);
    }

    public OrganizationRelationship createOrgRelationship() throws EntityValidationException, JMSException {
        OrganizationRelationship orgRel = getBasicOrgRelation();
        orgRel.setStartDate(oldDate);
        orgRel.setHierarchicalType(FamilyHierarchicalType.PEER);
        orgRelServiceBean.create(orgRel);
        return orgRel;
    }

    @Test
    public void createOrgRelWithStartDate() throws EntityValidationException, JMSException {
        OrganizationRelationship orgRel = getBasicOrgRelation();
        orgRel.setStartDate(oldDate);
        Date startDate = orgRel.getStartDate();
        orgRelServiceBean.create(orgRel);
        assertNotNull(orgRel.getStartDate());
        assertEquals(startDate, orgRel.getStartDate());
    }

    @Test
    public void createOrgRelWithEndDateInTheFuture() throws EntityValidationException, JMSException {
        OrganizationRelationship orgRel = getBasicOrgRelation();
        orgRel.setStartDate(oldDate);
        orgRel.setEndDate(DateUtils.addDays(new Date(), +2));
        try {
            orgRelServiceBean.create(orgRel);
            fail("InvalidStateException expected");
        } catch (InvalidStateException e) {
            assertEquals(1, e.getInvalidValues().length);
            assertEquals("(fieldName) must not be in the future.", e.getInvalidValues()[0].getMessage());
            assertEquals("endDate", e.getInvalidValues()[0].getPropertyName());
        }
    }

    @Test
    public void createOrgRelWithValidEndDate() throws EntityValidationException, JMSException {
        OrganizationRelationship orgRel = getBasicOrgRelation();
        orgRel.setStartDate(oldDate);
        orgRel.setEndDate(DateUtils.addDays(new Date(), -2));
        orgRelServiceBean.create(orgRel);
    }

    @Test
    public void testUniqueRelationship() throws EntityValidationException, JMSException {
        // ensures that the there will be only one Active relation
        OrganizationRelationship orgRel = getBasicOrgRelation();
        orgRel.setRelatedOrganization(orgServiceBean.getById(createOrg()));
        long orgId = orgRel.getOrganization().getId();
        long relOrgId = orgRel.getRelatedOrganization().getId();
        long famId = orgRel.getFamily().getId();
        orgRelServiceBean.create(orgRel);
        OrganizationRelationship anotherSimilarOrg = new OrganizationRelationship();
        anotherSimilarOrg.setHierarchicalType(FamilyHierarchicalType.PEER);
        anotherSimilarOrg.setFamily(familyServiceBean.getById(famId));
        anotherSimilarOrg.setRelatedOrganization(orgServiceBean.getById(orgId));
        anotherSimilarOrg.setOrganization(orgServiceBean.getById(relOrgId));
        try {
            orgRelServiceBean.create(anotherSimilarOrg);
            fail("InvalidStateException expected");
        } catch (InvalidStateException e) {
            assertEquals(1, e.getInvalidValues().length);
            assertEquals("Two organization should only have one Active relationship (e.g. no endDate) within a family", e.getInvalidValues()[0].getMessage());
        }
    }

    @Test
    public void testCreateRelationship() throws EntityValidationException, JMSException {
        // ensures that the bi-directional link is created
        // e.g. save A is Parent of B. Then confirm that a the link B is a child of A exists in the db.
        OrganizationRelationship orgRel = getBasicOrgRelation();
        orgRel.setStartDate(oldDate);
        orgRel.setHierarchicalType(FamilyHierarchicalType.PARENT);
        long id = orgRelServiceBean.create(orgRel);

        OrganizationRelationship tosearch = new OrganizationRelationship();
        tosearch.setFamily(familyServiceBean.getById(orgRel.getFamily().getId()));
        AnnotatedBeanSearchCriteria<OrganizationRelationship> scriteria = new AnnotatedBeanSearchCriteria<OrganizationRelationship>(
                tosearch);
        List<OrganizationRelationship> list = orgRelServiceBean.search(scriteria);
        for (OrganizationRelationship orgRel1 : list) {

            if (id != orgRel1.getId()) {
                assertEquals(orgRel1.getHierarchicalType(), FamilyHierarchicalType.CHILD);
            }

        }
    }

    @Test
    public void testGetActiveOrganizationRelationships() throws EntityValidationException, JMSException {
        OrganizationRelationship or = createOrgRelationship();
        Long orgId = or.getOrganization().getId();
        assertEquals(1, orgRelServiceBean.getActiveOrganizationRelationships(or.getFamily().getId(), orgId).size());

        or.setEndDate(DateUtils.truncate(new Date(), Calendar.DATE));
        orgRelServiceBean.updateEntity(or);
        assertEquals(0, orgRelServiceBean.getActiveOrganizationRelationships(or.getFamily().getId(), orgId).size());
    }

    @Test
    public void testGetActiveOrganizationRelationship() throws EntityValidationException, JMSException {
        OrganizationRelationship or = createOrgRelationship();
        assertNotNull(orgRelServiceBean.getActiveOrganizationRelationship(or.getFamily().getId(), or.getOrganization()
                .getId(), or.getRelatedOrganization().getId()));

        or.setEndDate(DateUtils.truncate(new Date(), Calendar.DATE));
        orgRelServiceBean.updateEntity(or);
        assertNull(orgRelServiceBean.getActiveOrganizationRelationship(or.getFamily().getId(), or.getOrganization()
                .getId(), or.getRelatedOrganization().getId()));
    }

    private OrganizationRelationship getBasicOrgRelation() throws EntityValidationException, JMSException {
        OrganizationRelationship orgRel = new OrganizationRelationship();
        orgRel.setFamily(familyServiceBean.getById(createFamily()));
        orgRel.setOrganization(orgServiceBean.getById(createOrg()));
        orgRel.setRelatedOrganization(orgServiceBean.getById(createOrg()));
        orgRel.setHierarchicalType(FamilyHierarchicalType.PEER);
        createFamOrgRel(orgRel.getFamily(), orgRel.getOrganization());
        return orgRel;
    }

    private void createFamOrgRel(Family family, Organization organization) throws EntityValidationException {
        FamilyOrganizationRelationship relationship = new FamilyOrganizationRelationship();
        relationship.setFamily(family);
        relationship.setOrganization(organization);
        relationship.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        relationship.setStartDate(family.getStartDate());
        famOrganizationRelationshipService.create(relationship);
    }

    private long createFamily() {
        Family family = new Family();
        family.setName("FamilyName");
        family.setStartDate(oldDate);
        return familyServiceBean.create(family);
    }

    private long createOrg() throws EntityValidationException, JMSException {
        Organization org = new Organization();
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "stateOrProvince", "postalCode",
                getDefaultCountry());
        org.setPostalAddress(a);
        org.setName("Some Org Name");
        org.getEmail().add(new Email("abc@example.com"));
        org.getAlias().add(new Alias("test org alias"));
        return orgServiceBean.create(org);
    }
}
