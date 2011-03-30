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

import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Implements the CRUD.
 * 
 * @author vrushali
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrganizationRelationshipServiceBean extends AbstractBaseServiceBean<OrganizationRelationship> implements
        OrganizationRelationshipServiceLocal {

    /**
     * 
     */
    private static final String ORG_ID_PARAM = "orgId";
    private static final String FAMILY_ID_PARAM = "familyId";
    private static final String ORGREL_FAMILY_ID_EXP = " orgRel where orgRel.family.id = :" + FAMILY_ID_PARAM;

    /**
     * {@inheritDoc}
     */
    public long create(OrganizationRelationship orgRel) throws EntityValidationException {
        if (orgRel.getStartDate() == null) {
            orgRel.setStartDate(DateUtils.truncate(new Date(), Calendar.DATE));
        }
        OrganizationRelationship orgRelLink = new OrganizationRelationship();
        orgRelLink.setFamily(orgRel.getFamily());
        orgRelLink.setOrganization(orgRel.getRelatedOrganization());
        orgRelLink.setRelatedOrganization(orgRel.getOrganization());
        orgRelLink.setHierarchicalType(FamilyHierarchicalType.getPairValue(orgRel.getHierarchicalType()));
        orgRelLink.setStartDate(orgRel.getStartDate());
        orgRelLink.setEndDate(orgRel.getEndDate());
        super.createHelper(orgRelLink);
        return super.createHelper(orgRel);
    }

    /**
     * {@inheritDoc}
     */
    public void updateEntity(OrganizationRelationship updatedEntity) throws EntityValidationException {
        Map<String, String[]> errors = validate(updatedEntity);
        if (errors != null && !errors.isEmpty()) {
            throw new EntityValidationException(errors);
        }
        super.update(updatedEntity);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<OrganizationRelationship> getActiveOrganizationRelationships(Long familyId, Long orgId) {
        String hql = "from " + OrganizationRelationship.class.getName() + ORGREL_FAMILY_ID_EXP
                + " and organization.id = :" + ORG_ID_PARAM + " and endDate is null";
        Query query = PoHibernateUtil.getCurrentSession().createQuery(hql);
        query.setLong(FAMILY_ID_PARAM, familyId);
        query.setLong(ORG_ID_PARAM, orgId);
        return query.list();
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationRelationship getActiveOrganizationRelationship(Long familyId, Long orgId, Long relatedOrgId) {
        String hql = "from " + OrganizationRelationship.class.getName() + ORGREL_FAMILY_ID_EXP
                + " and organization.id = :" + ORG_ID_PARAM
                + " and relatedOrganization.id = :relOrgId and endDate is null";
        Query query = PoHibernateUtil.getCurrentSession().createQuery(hql);
        query.setLong(FAMILY_ID_PARAM, familyId);
        query.setLong(ORG_ID_PARAM, orgId);
        query.setLong("relOrgId", relatedOrgId);
        return (OrganizationRelationship) query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    public Date getEarliestStartDate(Long familyId, Long orgId) {
        String hql = "select min(orgRel.startDate) from " + OrganizationRelationship.class.getName()
                + ORGREL_FAMILY_ID_EXP + " and " + "(organization.id = :" + ORG_ID_PARAM
                + " or relatedOrganization.id = :" + ORG_ID_PARAM + ")";
        Query query = PoHibernateUtil.getCurrentSession().createQuery(hql);
        query.setLong(FAMILY_ID_PARAM, familyId);
        query.setLong(ORG_ID_PARAM, orgId);
        return (Date) query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    public Date getEarliestStartDate(Long familyId) {
        String hql = "select min(orgRel.startDate) from " + OrganizationRelationship.class.getName()
                + ORGREL_FAMILY_ID_EXP;
        Query query = PoHibernateUtil.getCurrentSession().createQuery(hql);
        query.setLong(FAMILY_ID_PARAM, familyId);
        return (Date) query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    public Date getLatestEndDate(Long familyId) {
        String hql = "select max(orgRel.endDate) from " + OrganizationRelationship.class.getName()
                + ORGREL_FAMILY_ID_EXP;
        Query query = PoHibernateUtil.getCurrentSession().createQuery(hql);
        query.setLong(FAMILY_ID_PARAM, familyId);
        return (Date) query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    public Date getLatestEndDate(Long familyId, Long orgId) {
        Session s = null;
        try {
            Connection conn = PoHibernateUtil.getCurrentSession().connection();
            s = PoHibernateUtil.getHibernateHelper().getSessionFactory().openSession(conn);
            String hql = "select max(orgRel.endDate) from " + OrganizationRelationship.class.getName()
                    + ORGREL_FAMILY_ID_EXP + " and " + "(organization.id = :" + ORG_ID_PARAM
                    + " or relatedOrganization.id = :" + ORG_ID_PARAM + ")";
            Query query = s.createQuery(hql);
            query.setLong(FAMILY_ID_PARAM, familyId);
            query.setLong(ORG_ID_PARAM, orgId);
            return (Date) query.uniqueResult();
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
