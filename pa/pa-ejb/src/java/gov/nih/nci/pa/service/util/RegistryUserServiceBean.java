/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
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

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Bala Nair
 *
 */
@Stateless
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RegistryUserServiceBean implements RegistryUserServiceRemote {

    private static final Logger LOG = Logger
            .getLogger(RegistryUserServiceBean.class);

    /**
     * Create a new Registry User.
     *
     * @param user user
     * @return user
     * @throws PAException PAException
     */
    public RegistryUser createUser(RegistryUser user)
    throws PAException {
        Session session = null;
        // first create the Registry user
        session = HibernateUtil.getCurrentSession();
        session.saveOrUpdate(user);
        return user;
    }

    /**
     * Update an existing Registry User.
     *
     * @param user  user
     * @return user
     * @throws PAException PAException
     */
    public RegistryUser updateUser(RegistryUser user)
    throws PAException {
        Session session = null;
        // first create the Registry user
        session = HibernateUtil.getCurrentSession();
        session.merge(user);
        return user;
    }

    /**
     * Retrieves user by login name.
     *
     * @param loginName loginName
     * @return user
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public RegistryUser getUser(String loginName)
                            throws PAException {
        RegistryUser registryUser = null;
        Session session = null;
        List<RegistryUser> queryList = new ArrayList<RegistryUser>();
        try {
            // /first get the CSM user
            UserProvisioningManager upManager = SecurityServiceProvider
                    .getUserProvisioningManager("pa");
            User csmUser = upManager.getUser(loginName);

            // if csm user exists retrieve the registry user
            if (csmUser != null) {
                LOG.info(" CSM User ID = " + csmUser.getUserId());
                session = HibernateUtil.getCurrentSession();

                Query query = null;
                // HQL query
                String hql = "select reguser "
                              + "from RegistryUser reguser "
                              + "where reguser.csmUserId = :csmuserId ";
                LOG.info(" query RegistryUser = " + hql);

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
            LOG.error(" CSM Exception while retrieving  user : "
                                        + loginName, cse);
            throw new PAException(" CSM exception while "
                    + "retrieving  user :" + loginName, cse);
        }

        return registryUser;

    }

    /**
     * @param userId  csm user id
     * @return user
     */
    public RegistryUser getUserById(Long userId) {
        RegistryUser registryUser = null;
        Session session;
        if (userId != null) {
            session = HibernateUtil.getCurrentSession();
            registryUser = (RegistryUser) session.get(RegistryUser.class, userId);
        }
        return registryUser;
    }
    /**
     * @param userType type of user
     * @return list of pending user admin 
     * @throws PAException on error
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
}
