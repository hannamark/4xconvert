/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.DisplayTrialOwnershipInformation;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * @author aevansel@5amsolutions.com
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RegistryUserBeanLocal implements RegistryUserServiceLocal {
    private static final Logger LOG = Logger.getLogger(RegistryUserBeanLocal.class);
    private static final int INDEX_USER_ID = 0;
    private static final int INDEX_FIRST_NAME = 1;
    private static final int INDEX_LAST_NAME = 2;
    private static final int INDEX_EMAIL = 3;
    private static final int INDEX_TRIAL_ID = 4;
    private static final int INDEX_NCI_IDENTIFIER = 5;
    private static final int INDEX_ORG_ID = 6;
    private static final int INDEX_LEAD_ID = 7;


    /**
     * {@inheritDoc}
     */
    public RegistryUser createUser(RegistryUser user) throws PAException {
        HibernateUtil.getCurrentSession().saveOrUpdate(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUser updateUser(RegistryUser user) throws PAException {
        HibernateUtil.getCurrentSession().merge(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasTrialAccess(String loginName, Long studyProtocolId) throws PAException {
        RegistryUser myUser = getUser(loginName);
        return hasTrialAccess(myUser, studyProtocolId);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTrialOwner(Long userId, Long studyProtocolId) throws PAException {
        RegistryUser myUser = getUserById(userId);
        StudyProtocol studyProtocol =
            (StudyProtocol) HibernateUtil.getCurrentSession().get(StudyProtocol.class, studyProtocolId);
        if (myUser == null) {
            throw new PAException("Could not find user.");
        }
        return studyProtocol.getStudyOwners().contains(myUser);
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasTrialAccess(RegistryUser user, Long studyProtocolId) throws PAException {
        StudyProtocol studyProtocol =
            (StudyProtocol) HibernateUtil.getCurrentSession().get(StudyProtocol.class, studyProtocolId);

        // first check that the user isn't already a trial owner
        if (studyProtocol.getStudyOwners().contains(user)) {
            return true;
        }

        // check that the user is an admin of something in at all
        if (!UserOrgType.ADMIN.equals(user.getAffiliatedOrgUserType())) {
            return false;
        }

        // second check that the user isn't the lead org admin. The first study site will be the lead org if it exists.
        StudySite leadOrg = studyProtocol.getStudySites().isEmpty() ? null
                : studyProtocol.getStudySites().iterator().next();
        if (leadOrg != null && StringUtils.equals(leadOrg.getResearchOrganization().getOrganization().getIdentifier(),
                user.getAffiliatedOrganizationId().toString())) {
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RegistryUser getUser(String loginName) throws PAException {
        RegistryUser registryUser = null;
        try {
            // /first get the CSM user
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            User csmUser = upManager.getUser(loginName);

            // if csm user exists retrieve the registry user
            if (csmUser != null) {
                Session session = HibernateUtil.getCurrentSession();

                // HQL query
                String hql = "select reguser from RegistryUser reguser where reguser.csmUserId = :csmuserId ";

                // construct query object
                Query query = session.createQuery(hql);
                query.setParameter("csmuserId", csmUser.getUserId());
                registryUser = (RegistryUser) query.uniqueResult();
            }

        } catch (Exception cse) {
            throw new PAException("CSM exception while retrieving user: " + loginName, cse);
        }

        return registryUser;
    }

    /**
     * {@inheritDoc}
     */
    public RegistryUser getUserById(Long userId) throws PAException {
        RegistryUser registryUser = null;
        if (userId != null) {
            try {
                Session session = HibernateUtil.getCurrentSession();
                registryUser = (RegistryUser) session.get(RegistryUser.class, userId);
            } catch (Exception e) {
                throw new PAException(" CSM exception while retrieving  user with id: " + userId, e);
            }
        }
        return registryUser;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        if (userType == null) {
            throw new PAException("UserOrgType cannot be null.");
        }
        Session session = HibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, "regUser")
            .add(Property.forName("regUser.affiliatedOrganizationId").isNotNull())
            .add(Restrictions.eq("regUser.affiliatedOrgUserType", userType));

        List<RegistryUser> registryUserList = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        for (RegistryUser usr : registryUserList) {
            PAServiceUtils servUtil = new PAServiceUtils();
            usr.setAffiliateOrg(servUtil.getOrgName(IiConverter.convertToPoOrganizationIi(
                    String.valueOf(usr.getAffiliatedOrganizationId()))));
        }
        return registryUserList;
    }

    /**
     * {@inheritDoc}
     */
    public boolean doesRegistryUserExist(String loginName) {
        RegistryUser registryUser = null;
        try {
            registryUser = getUser(loginName);
        } catch (PAException e) {
            LOG.error("Error retrieving user.", e);
        }
        return registryUser != null;
    }

    /**
     *
     * @param regUser user
     * @return list of user
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        Session session = HibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, "regUser");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        if (regUser != null) {
            if (regUser.getAffiliatedOrgUserType() != null
                    && StringUtils.isNotEmpty(regUser.getAffiliatedOrgUserType().getCode())) {
                criteria.add(Restrictions.eq("regUser.affiliatedOrgUserType", regUser.getAffiliatedOrgUserType()));
            }
            addCriteria(criteria, regUser.getAffiliatedOrganizationId(), "regUser.affiliatedOrganizationId");
            addCriteria(criteria, regUser.getPoOrganizationId(), "regUser.poOrganizationId");
            addCriteria(criteria, regUser.getPoPersonId(), "regUser.poPersonId");
            addCriteria(criteria, regUser.getCsmUserId(), "regUser.csmUserId");

            addCriteria(criteria, regUser.getEmailAddress(), "regUser.emailAddress");
            addCriteria(criteria, regUser.getPrsOrgName(), "regUser.prsOrgName");
            addCriteria(criteria, regUser.getFirstName(), "regUser.firstName");
            addCriteria(criteria, regUser.getLastName(), "regUser.lastName");
        }
        return criteria.list();
    }

    /**
     * @param trialOwnershipInfo the criteria object.
     * @param affiliatedOrgId the affiliated org id.
     * @return list of trial ownership information objects.
     * @throws PAException on error.
     */
    @SuppressWarnings("unchecked")
    public List<DisplayTrialOwnershipInformation> searchTrialOwnership(DisplayTrialOwnershipInformation
            trialOwnershipInfo, Long affiliatedOrgId) throws PAException {
        List<DisplayTrialOwnershipInformation> lst = new ArrayList<DisplayTrialOwnershipInformation>();
        StringBuffer hql = new StringBuffer();
        hql.append("select sowner.id, sowner.firstName, sowner.lastName, sowner.emailAddress, "
                + "sp.id, otherid.extension, sowner.affiliatedOrganizationId, sps.localStudyProtocolIdentifier "
                + "from StudyProtocol as sp left outer join sp.documentWorkflowStatuses as dws "
                + "left outer join sp.studySites as sps "
                + "left outer join sps.researchOrganization as ro left outer join ro.organization as org "
                + " left outer join sp.studyOwners as sowner left outer join sp.otherIdentifiers otherid where '")
                .append(affiliatedOrgId.toString())
                .append("' in (select researchOrganization.organization.identifier from StudySite "
                        + "where functionalCode ='")
                .append(StudySiteFunctionalCode.LEAD_ORGANIZATION)
                .append("' and studyProtocol.id = sp.id) and dws.statusCode  <> '")
                .append(DocumentWorkflowStatusCode.REJECTED)
                .append("' and (dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
                        + "where sp.id=dws1.studyProtocol) or dws.id is null) and sps.functionalCode = '")
                .append(StudySiteFunctionalCode.LEAD_ORGANIZATION)
                .append("' and otherid.root = '")
                .append(IiConverter.STUDY_PROTOCOL_ROOT)
                .append("' and sowner.id IS NOT NULL ");

        String criteriaClause = getTrialOwnershipInformationSearchCriteria(trialOwnershipInfo);
        if (StringUtils.isNotEmpty(criteriaClause)) {
            hql.append(criteriaClause);
        }

        Session session = HibernateUtil.getCurrentSession();
        Query query = session.createQuery(hql.toString());
        for (Iterator<Object[]> iter = query.iterate(); iter.hasNext();) {
            Object[] row = iter.next();
            DisplayTrialOwnershipInformation trialInfo = new DisplayTrialOwnershipInformation();
            trialInfo.setUserId(ObjectUtils.toString(row[INDEX_USER_ID]));
            trialInfo.setFirstName(ObjectUtils.toString(row[INDEX_FIRST_NAME]));
            trialInfo.setLastName(ObjectUtils.toString(row[INDEX_LAST_NAME]));
            trialInfo.setEmailAddress(ObjectUtils.toString(row[INDEX_EMAIL]));
            trialInfo.setTrialId(ObjectUtils.toString(row[INDEX_TRIAL_ID]));
            trialInfo.setNciIdentifier(ObjectUtils.toString(row[INDEX_NCI_IDENTIFIER]));
            trialInfo.setAffiliatedOrgId(ObjectUtils.toString(row[INDEX_ORG_ID]));
            trialInfo.setLeadOrgId(ObjectUtils.toString(row[INDEX_LEAD_ID]));
            lst.add(trialInfo);
        }

        return lst;
    }

    private String getTrialOwnershipInformationSearchCriteria(DisplayTrialOwnershipInformation criteria) {
        StringBuffer criteriaClause = new StringBuffer();
        criteriaClause.append(addCriteria("sowner.firstName", criteria.getFirstName()))
            .append(addCriteria("sowner.lastName", criteria.getLastName()))
            .append(addCriteria("sowner.emailAddress", criteria.getEmailAddress()))
            .append(addCriteria("sp.otherIdentifiers.extension", criteria.getNciIdentifier()));
        return criteriaClause.toString();
    }

    private void addCriteria(Criteria criteria, Long id, String criteriaName) {
        if (id != null) {
            criteria.add(Restrictions.eq(criteriaName, id));
        }
    }

    private void addCriteria(Criteria criteria, String criteriaValue, String criteriaName) {
        if (StringUtils.isNotEmpty(criteriaValue)) {
            criteria.add(Restrictions.ilike(criteriaName, criteriaValue + "%"));
        }
    }

    private String addCriteria(String criteriaName, String criteriaValue) {
        StringBuffer retVal = new StringBuffer();
        if (StringUtils.isNotEmpty(criteriaValue)) {
            retVal.append(" and (lower(").append(criteriaName).append(") like lower('").append(criteriaValue)
                .append("%')) ");
        }
        return retVal.toString();
    }

    /**
     * Assign ownership of given protocol to given user.
     * @param userId user id
     * @param studyProtocolId study protocol id
     * @throws PAException on error
     */
    public void assignOwnership(Long userId, Long studyProtocolId) throws PAException {
        RegistryUser usr =  getUser(userId, studyProtocolId);
        StudyProtocol sp = new StudyProtocol();
        sp.setId(studyProtocolId);
        usr.getStudyProtocols().add(sp);
        HibernateUtil.getCurrentSession().update(usr);
        HibernateUtil.getCurrentSession().flush();
    }

    /**
     * remove ownership .
     * @param userId user id
     * @param studyProtocolId study protocol id
     * @throws PAException on error
     */
    public void removeOwnership(Long userId, Long studyProtocolId) throws PAException {
        RegistryUser usr = getUser(userId, studyProtocolId);
        Set<StudyProtocol> studyProtocols = usr.getStudyProtocols();
        for (Iterator<StudyProtocol> iter = studyProtocols.iterator(); iter.hasNext();) {
            StudyProtocol sp = iter.next();
            if (sp.getId().equals(studyProtocolId)) {
                iter.remove();
            }
        }
        HibernateUtil.getCurrentSession().saveOrUpdate(usr);
        HibernateUtil.getCurrentSession().flush();
    }

    private RegistryUser getUser(Long userId, Long studyProtocolId) throws PAException {
        // to assign the ownership add a record to study_owner
        if (userId == null) {
            throw new PAException("user id cannot be null.");
        }
        if (studyProtocolId == null) {
            throw new PAException("studyProtocol id cannot be null.");
        }
        RegistryUser usr =  getUserById(userId);
        if (usr == null) {
            throw new PAException("user not found.");
        }
        return usr;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getTrialOwnerNames(Long studyProtocolId) throws PAException {
        List<String> names = new ArrayList<String>();
        StudyProtocol studyProtocol =
            (StudyProtocol) HibernateUtil.getCurrentSession().get(StudyProtocol.class, studyProtocolId);

        for (RegistryUser myUser : studyProtocol.getStudyOwners()) {
            names.add(myUser.getFirstName() + " " + myUser.getLastName());
        }

        return names;
    }

    /**
     * {@inheritDoc}
     */
    public Set<RegistryUser> getAllTrialOwners(Long studyProtocolId) throws PAException {
        return ((StudyProtocol) HibernateUtil.getCurrentSession()
                .get(StudyProtocol.class, studyProtocolId)).getStudyOwners();
    }


}
