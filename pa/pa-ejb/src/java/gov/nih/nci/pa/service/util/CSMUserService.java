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
import gov.nih.nci.pa.iso.convert.AbstractStudyProtocolConverter;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;

import java.util.List;
import java.util.Set;

import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

/**
 * Service for managing csm users (create, retrieve and update).
 * @author Bala Nair
 *
 */
public class CSMUserService implements CSMUserUtil {

    private static final String CSM_LOOKUP_ERR_MSG = "CSM exception while retrieving CSM user: ";
    private static final Logger LOG  = Logger.getLogger(CSMUserService.class);
    private static CSMUserUtil registryUserService = null;

    static {
        setRegistryUserService(new CSMUserService());
    }

    /**
     * {@inheritDoc}
     */
    public User createCSMUser(RegistryUser user, String loginName, String password) throws PAException {
        User createdCSMUser = null;
        try {
            // create the csm user
            User csmUser = new User();
            // get values from Registry User object and set in CSM User object
            csmUser.setLoginName(loginName);
            csmUser.setPassword(password);
            csmUser.setFirstName(user.getFirstName());
            csmUser.setLastName(user.getLastName());
            csmUser.setOrganization(user.getAffiliateOrg());
            csmUser.setPhoneNumber(user.getPhone());
            ///create new user in CSM table
            UserProvisioningManager upManager = SecurityServiceProvider.
                                            getUserProvisioningManager("pa");
            upManager.createUser(csmUser);
            // assign the created user to the appropriate group
            // read the CSM group name from the properties
            String submitterGroup = PaEarPropertyReader.getCSMSubmitterGroup();
            upManager.assignUserToGroup(loginName, submitterGroup);
            createdCSMUser = upManager.getUser(loginName);

        } catch (CSException cse) {
            throw new PAException("CSM exception while creating CSM user: " + loginName, cse);
        }

        return createdCSMUser;

    }

    /**
     * {@inheritDoc}
     */
    public User updateCSMUser(RegistryUser user, String loginName, String password) throws PAException {
        User createdCSMUser = null;
        try {
            // create the csm user
            User csmUser = new User();
            UserProvisioningManager upManager = SecurityServiceProvider.
            getUserProvisioningManager("pa");
            csmUser  = upManager.getUser(loginName);

            // get values from Registry User object and set in CSM User object
            csmUser.setUserId(user.getCsmUserId());
            csmUser.setLoginName(loginName);
            csmUser.setPassword(password);
            csmUser.setFirstName(user.getFirstName());
            csmUser.setLastName(user.getLastName());
            csmUser.setOrganization(user.getAffiliateOrg());
            csmUser.setPhoneNumber(user.getPhone());
            ///update the user info in CSM table
            upManager.modifyUser(csmUser);
            // assign the updated user to the appropriate group
            // read the CSM group name from the properties
            String submitterGroup = PaEarPropertyReader.getCSMSubmitterGroup();
            upManager.assignUserToGroup(loginName, submitterGroup);
            createdCSMUser = upManager.getUser(loginName);
        } catch (CSException cse) {
            throw new PAException("CSM exception while updating CSM user: " + loginName, cse);
        }

        return createdCSMUser;

    }

    /**
     * {@inheritDoc}
     */
    public User getCSMUser(String loginName) throws PAException {
        User csmUser = null;
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            csmUser = upManager.getUser(loginName);
            if (csmUser == null) {
                LOG.info("Unable to look up CSM user for login name: " + loginName);
            }
        } catch (CSConfigurationException csce) {
            throw new PAException(CSM_LOOKUP_ERR_MSG + loginName, csce);
        } catch (CSException cse) {
            throw new PAException(CSM_LOOKUP_ERR_MSG + loginName, cse);
        }

        return csmUser;

    }

    /**
     * {@inheritDoc}
     */
    public User getCSMUserById(Long id) throws PAException {
        User csmUser = null;
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            csmUser = upManager.getUserById(id.toString());
        } catch (CSConfigurationException csce) {
            throw new PAException(CSM_LOOKUP_ERR_MSG + id, csce);
        } catch (CSException cse) {
            throw new PAException(CSM_LOOKUP_ERR_MSG + id, cse);
        }
        return csmUser;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Set<User> getCSMUsers() throws PAException {
        Set<User> csmUsers = null;
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            String submitterGroup = PaEarPropertyReader.getCSMSubmitterGroup();
            Long submitterGroupId = null;
            Group sGroup = new Group();
            sGroup.setGroupName(submitterGroup);
            GroupSearchCriteria sCriteria = new GroupSearchCriteria(sGroup);
            List<Group> groups = upManager.getObjects(sCriteria);
            for (Group group : groups) {
                if (submitterGroup.equals(group.getGroupName())) {
                    submitterGroupId = group.getGroupId();
                    break;
                }

            }
            csmUsers = upManager.getUsers(String.valueOf(submitterGroupId));
        } catch (CSConfigurationException csce) {
            throw new PAException("CSM exception while retrieving CSM users", csce);
        } catch (CSException cse) {
            throw new PAException("CSM exception while retrieving CSM users", cse);
        }
        return csmUsers;
    }


    /**
     * {@inheritDoc}
     */
    public void assignUserToGroup(String loginName, String groupName) throws PAException {
        try {
            UserProvisioningManager upManager = SecurityServiceProvider.getUserProvisioningManager("pa");
            upManager.assignUserToGroup(loginName, groupName);
        } catch (Exception e) {
            throw new PAException("CSM exception while adding " + loginName + " to " + groupName + " group.", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public User lookupUser(SessionContext ejbContext) throws PAException {
        User user = null;
        String userName = null;
        if (ejbContext != null && ejbContext.getCallerPrincipal() != null) {
            userName = ejbContext.getCallerPrincipal().getName();
            user = CSMUserService.getInstance().getCSMUser(userName);
        }
        if (user == null) {
            throw new PAException("Unable to lookup user for: " + userName + " in SessionContext: " + ejbContext);
        }
        return user;
    }

    /**
     *
     * @return RegistryUserService
     */
    public static CSMUserUtil getInstance() {
        return  getRegistryUserService();
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public static void setRegistryUserService(CSMUserUtil registryUserService) {
        CSMUserService.registryUserService = registryUserService;
        AbstractStudyProtocolConverter.setCsmUserUtil(CSMUserService.registryUserService);
    }

    /**
     * @return the registryUserService
     */
    public static CSMUserUtil getRegistryUserService() {
        return registryUserService;
    }

}
