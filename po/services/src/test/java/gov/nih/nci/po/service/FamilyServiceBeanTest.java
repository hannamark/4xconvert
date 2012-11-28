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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.data.dao.FamilyUtilDao;
import gov.nih.nci.po.util.FamilyDateValidator;
import gov.nih.nci.po.util.OrgRelStartDateValidator;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;

import java.util.Calendar;
import java.util.Date;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vrushali
 *
 */
public class FamilyServiceBeanTest extends AbstractServiceBeanTest {
    private FamilyServiceBean familyServiceBean;
    private OrganizationRelationshipServiceLocal orgRelServiceLocal = mock(OrganizationRelationshipServiceBean.class);
    private OrganizationServiceBean orgLocal;
    private final FamilyOrganizationRelationshipServiceBean famOrgRelService = mock(FamilyOrganizationRelationshipServiceBean.class);
    private final FamilyUtilDao familyUtilDao = mock(FamilyUtilDao.class);
    private final Date today = DateUtils.truncate(new Date(), Calendar.DATE);

    @Before
    public void setUpData() {
        famOrgRelService.setOrgRelService(orgRelServiceLocal);
        familyServiceBean = EjbTestHelper.getFamilyServiceBean();
        familyServiceBean.setFamilyOrgRelService(famOrgRelService);
        familyServiceBean.setOrgRelService(orgRelServiceLocal);
        orgLocal = EjbTestHelper.getOrganizationServiceBean();
        FamilyDateValidator.setFamilyService(familyServiceBean);
        OrgRelStartDateValidator.setFamilyDao(familyUtilDao);
        when(familyUtilDao.getActiveStartDate(any(Session.class), anyLong(), anyLong())).thenReturn(today);
    }

    @After
    public void teardown() {
        FamilyDateValidator.setFamilyService(PoRegistry.getFamilyService());
        OrgRelStartDateValidator.setFamilyDao(new FamilyUtilDao());
        familyServiceBean = null;
        orgRelServiceLocal = null;
    }
    
    public long createFamily() throws EntityValidationException {
        Family family = getFamily();
        family.setStartDate(null);
        long id = familyServiceBean.create(family);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
        return id;
    }
    
    @Test
    public void testFamily() throws Exception {
        long id = createFamily();
        Family saved = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
        assertNotNull(saved.getName());
        Family toUpdate = familyServiceBean.getById(saved.getId());
        assertNotNull(toUpdate.getId());
        toUpdate.setName("UpdatedFamilyName");
        familyServiceBean.updateEntity(toUpdate);
        Family get = familyServiceBean.getById(toUpdate.getId());
        assertEquals(get.getName(),toUpdate.getName());
        Family tosearch = new Family();
        tosearch.setName("UpdatedFamilyName");
        AnnotatedBeanSearchCriteria<Family> scriteria = new AnnotatedBeanSearchCriteria<Family>(tosearch);
        assertEquals(familyServiceBean.search(scriteria).size(),1);
    }
    @Test
    public void testFamilyStatusChangesToINACTVE() throws EntityValidationException, JMSException {
        long id = getBasicFamily();
        Family toUpdate = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
        assertNull(toUpdate.getEndDate());
        for (FamilyOrganizationRelationship updateFamOrgEntity : toUpdate.getFamilyOrganizationRelationships()) {
            assertNull(updateFamOrgEntity.getEndDate());
        }
        for (OrganizationRelationship updateOrgRelEntity : toUpdate.getOrganizationRelationships()) {
            assertNull(updateOrgRelEntity.getEndDate());
        }
        toUpdate.setStatusCode(FamilyStatus.INACTIVE);
        toUpdate.setEndDate(today);
        familyServiceBean.updateEntity(toUpdate);
        Family updatedEntity = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
        assertEquals(FamilyStatus.INACTIVE, updatedEntity.getStatusCode());
        for (FamilyOrganizationRelationship updateFamOrgEntity : updatedEntity.getFamilyOrganizationRelationships()) {
            assertNotNull(updateFamOrgEntity.getEndDate());
            assertTrue(CollectionUtils.isEmpty(orgRelServiceLocal.getActiveOrganizationRelationships(id, updateFamOrgEntity.getOrganization().getId())));
        }
    }

    @Test
    public void testFamilyStatusChangesToNULLIFIED() throws EntityValidationException, JMSException {
        long id = getBasicFamily();
        Family toUpdate = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
        for (FamilyOrganizationRelationship updateFamOrgEntity : toUpdate.getFamilyOrganizationRelationships()) {
            assertNull(updateFamOrgEntity.getEndDate());
            assertTrue(CollectionUtils.isNotEmpty(orgRelServiceLocal.getActiveOrganizationRelationships(id, updateFamOrgEntity.getOrganization().getId())));
        }
        for (OrganizationRelationship updateOrgRelEntity : toUpdate.getOrganizationRelationships()) {
            assertNull(updateOrgRelEntity.getEndDate());
        }
        toUpdate.setStatusCode(FamilyStatus.NULLIFIED);
        familyServiceBean.updateEntity(toUpdate);
        Family updatedEntity = (Family) PoHibernateUtil.getCurrentSession().load(Family.class, id);
        assertEquals(FamilyStatus.NULLIFIED, updatedEntity.getStatusCode());
        for (FamilyOrganizationRelationship updateFamOrgEntity : updatedEntity.getFamilyOrganizationRelationships()) {
            assertNotNull(updateFamOrgEntity.getEndDate());
            assertTrue(CollectionUtils.isEmpty(orgRelServiceLocal.getActiveOrganizationRelationships(id, updateFamOrgEntity.getOrganization().getId())));
        }
    }

    @Test
    public void testGetLatestAllowableStartDate() throws EntityValidationException, JMSException {
        long id = getBasicFamily();
        Family family = familyServiceBean.getById(id);

        when(famOrgRelService.getEarliestStartDate(anyLong())).thenReturn(null);
        assertEquals(today, familyServiceBean.getLatestAllowableStartDate(id));
        
        when(famOrgRelService.getEarliestStartDate(anyLong())).thenReturn(today);
        assertEquals(today, familyServiceBean.getLatestAllowableStartDate(id));
        
        when(famOrgRelService.getEarliestStartDate(anyLong())).thenReturn(DateUtils.addDays(family.getStartDate(), 3));
        assertEquals(DateUtils.addDays(family.getStartDate(), 3), familyServiceBean.getLatestAllowableStartDate(id));
    }

    @Test
    public void testGetEarliestAllowableEndDate() throws EntityValidationException, JMSException {
        long id = getBasicFamily();
        Family family = familyServiceBean.getById(id);
        
        assertEquals(today, familyServiceBean.getEarliestAllowableEndDate(null));
        
        when(famOrgRelService.getLatestStartDate(anyLong())).thenReturn(today);
        assertEquals(today, familyServiceBean.getEarliestAllowableEndDate(id));
        
        when(famOrgRelService.getLatestEndDate(anyLong())).thenReturn(null);
        assertEquals(today, familyServiceBean.getEarliestAllowableEndDate(id));
        
        when(famOrgRelService.getLatestStartDate(anyLong())).thenReturn(DateUtils.addDays(family.getStartDate(), 3));
        assertEquals(DateUtils.addDays(family.getStartDate(), 3), familyServiceBean.getEarliestAllowableEndDate(id));
        
        when(orgRelServiceLocal.getLatestStartDate(anyLong())).thenReturn(DateUtils.addDays(family.getStartDate(), 4));
        assertEquals(DateUtils.addDays(family.getStartDate(), 4), familyServiceBean.getEarliestAllowableEndDate(id));
        
        when(orgRelServiceLocal.getLatestEndDate(anyLong())).thenReturn(DateUtils.addDays(family.getStartDate(), 5));
        assertEquals(DateUtils.addDays(family.getStartDate(), 5), familyServiceBean.getEarliestAllowableEndDate(id));

        when(famOrgRelService.getLatestEndDate(anyLong())).thenReturn(DateUtils.addDays(family.getStartDate(), 6));
        assertEquals(DateUtils.addDays(family.getStartDate(), 6), familyServiceBean.getEarliestAllowableEndDate(id));
    }

    private long getBasicFamily() throws EntityValidationException, JMSException {
        famOrgRelService.setOrgRelService(new OrganizationRelationshipServiceBean());
        familyServiceBean.setFamilyOrgRelService(famOrgRelService);
        long orgId = createOrg();
        Family family = getFamily();
        family.setStatusCode(FamilyStatus.ACTIVE);
        long id = familyServiceBean.create(family);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
        famOrgRel.setFunctionalType(FamilyFunctionalType.AFFILIATION);
        famOrgRel.setStartDate(today);
        famOrgRel.setOrganization(orgLocal.getById(orgId));
        famOrgRel.setFamily(familyServiceBean.getById(id));
        famOrgRelService.create(famOrgRel);

        long newOrgId = createOrg();
        PoHibernateUtil.getCurrentSession().flush();

        OrganizationRelationshipServiceBean orgRelService = new OrganizationRelationshipServiceBean();
        OrganizationRelationship orgRel = new OrganizationRelationship();
        orgRel.setOrganization(orgLocal.getById(orgId));
        orgRel.setRelatedOrganization(orgLocal.getById(newOrgId));
        orgRel.setFamily(familyServiceBean.getById(id));
        orgRel.setHierarchicalType(FamilyHierarchicalType.PARENT);
        orgRel.setStartDate(famOrgRel.getStartDate());
        orgRelService.create(orgRel);

        PoHibernateUtil.getCurrentSession().flush();
        return id;
    }
    
    private long createOrg() throws EntityValidationException, JMSException {
        Organization org = new Organization();
        Address a = new Address("streetAddressLine", "cityOrMunicipality", "MD", "postalCode",
                  getDefaultCountry());
        org.setPostalAddress(a);
        org.setName("Some Org Name " + new Date());
        org.getEmail().add(new Email("abc@example.com"));
        return orgLocal.create(org);
    }
    
    private Family getFamily() {
        Family family = new Family();
        family.setName("FamilyName" + new Date());
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 01, 02);
        family.setStartDate(DateUtils.truncate(cal.getTime(), Calendar.DATE));
        family.setStatusCode(FamilyStatus.NULLIFIED);
        return family;
    }
}
