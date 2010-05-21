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
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

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
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({"PMD.CyclomaticComplexity" })
public class RegistryUserBeanLocal implements RegistryUserServiceLocal {
    private static final Logger LOG = Logger.getLogger(RegistryUserBeanLocal.class);

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
        // first check that the user isn't already a trial owner
        for (StudyProtocol sp : myUser.getStudyProtocols()) {
            if (sp.getId().equals(studyProtocolId)) {
                return true;
            }
        }
        
        // check that the user is an admin of something in at all
        if (!myUser.getAffiliatedOrgUserType().equals(UserOrgType.ADMIN)) {
            return false;
        }
        
        // second check that the user isn't the lead org admin
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StudyProtocol spObj = (StudyProtocol) session.get(StudyProtocol.class, studyProtocolId); 
        
        for (StudySite sSites : spObj.getStudySites()) {
            if (sSites.getFunctionalCode().equals(StudySiteFunctionalCode.LEAD_ORGANIZATION) 
                    && sSites.getResearchOrganization().getOrganization()
                    .getId().equals(myUser.getAffiliatedOrganizationId())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RegistryUser getUser(String loginName) throws PAException {
        RegistryUser registryUser = null;
        Session session = null;
        List<RegistryUser> queryList = new ArrayList<RegistryUser>();
        try {
            // /first get the CSM user
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            User csmUser = upManager.getUser(loginName);

            // if csm user exists retrieve the registry user
            if (csmUser != null) {
                LOG.info(" CSM User ID = " + csmUser.getUserId());
                session = HibernateUtil.getCurrentSession();

                Query query = null;
                // HQL query
                String hql = "select reguser from RegistryUser reguser where reguser.csmUserId = :csmuserId ";
                LOG.info("query RegistryUser = " + hql);

                // construct query object
                query = session.createQuery(hql);
                query.setParameter("csmuserId", csmUser.getUserId());
                queryList = query.list();
                // there should only one user with the login name
                if (queryList != null && !queryList.isEmpty()) {
                    registryUser = queryList.get(0);
                }
            }

        } catch (Exception cse) {
            LOG.error("CSM Exception while retrieving user: " + loginName, cse);
            throw new PAException("CSM exception while retrieving user: " + loginName, cse);
        }

        return registryUser;

    }
    
    /**
     * {@inheritDoc}
     */
    public RegistryUser getUserById(Long userId) throws PAException {
        RegistryUser registryUser = null;
        Session session;
        if (userId != null) {
            try {
                session = HibernateUtil.getCurrentSession();
                registryUser = (RegistryUser) session.get(RegistryUser.class, userId);
            } catch (Exception e) {
                LOG.error(" CSM Exception while retrieving user with id: " + userId, e);
                throw new PAException(" CSM exception while retrieving  user with id: " + userId, e);
            }
        }
        return registryUser;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<RegistryUser> getUserByUserOrgType(UserOrgType userType) throws PAException {
        if (userType == null) {
            throw new PAException("UserOrgType cannot be null.");
        }
        Session session = null;
        List<RegistryUser> registryUserList = new ArrayList<RegistryUser>();
        session = HibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, "regUser") 
            .add(Property.forName("regUser.affiliatedOrganizationId").isNotNull())
            .add(Restrictions.eq("regUser.affiliatedOrgUserType", userType));
        
        registryUserList = criteria.list();
        for (RegistryUser usr : registryUserList) {
            PAServiceUtils servUtil = new PAServiceUtils();
            usr.setAffiliateOrg(servUtil.getOrgName(IiConverter.convertToPoOrganizationIi(
                    String.valueOf(usr.getAffiliatedOrganizationId()))));
        }
        return registryUserList;
    }
    /**
     * 
     * @param regUser user
     * @return list of user
     * @throws PAException on error
     */
    public List<RegistryUser> search(RegistryUser regUser) throws PAException {
        Session session = null;
        List<RegistryUser> registryUserList = new ArrayList<RegistryUser>();
        session = HibernateUtil.getCurrentSession();
        Criteria criteria = session.createCriteria(RegistryUser.class, "regUser");
        if (regUser != null) {
            if (regUser.getAffiliatedOrgUserType() != null 
                    && StringUtils.isNotEmpty(regUser.getAffiliatedOrgUserType().getCode())) {
                criteria.add(Restrictions.eq("regUser.affiliatedOrgUserType",
                        regUser.getAffiliatedOrgUserType()));
            }
            if (regUser.getAffiliatedOrganizationId() != null) {
                criteria.add(Restrictions.eq("regUser.affiliatedOrganizationId",
                        regUser.getAffiliatedOrganizationId()));
            }
            if (regUser.getPoOrganizationId() != null) {
                criteria.add(Restrictions.eq("regUser.poOrganizationId",
                        regUser.getPoOrganizationId()));
            }
            if (regUser.getPoPersonId() != null) {
                criteria.add(Restrictions.eq("regUser.poPersonId",
                        regUser.getPoPersonId()));
            }
            if (regUser.getCsmUserId() != null) {
                criteria.add(Restrictions.eq("regUser.csmUserId",
                        regUser.getCsmUserId()));
            }
            if (StringUtils.isNotEmpty(regUser.getEmailAddress())) {
                criteria.add(Restrictions.eq("regUser.emailAddress",
                        regUser.getEmailAddress()));
            }
            if (StringUtils.isNotEmpty(regUser.getPrsOrgName())) {
                criteria.add(Restrictions.eq("regUser.prsOrgName",
                        regUser.getPrsOrgName()));
            }
        }
        registryUserList = criteria.list();
        return registryUserList;
    }
    
    

}
