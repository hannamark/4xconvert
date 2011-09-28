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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestRegistryUserSchema;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author NAmiruddin
 *
 */
public class RegistryUserServiceTest extends AbstractHibernateTestCase {

    private RegistryUserServiceRemote remoteEjb = new MockRegistryUserServiceBean();

    /**
     * Initialization method.
     */
    @Before
    public void setUp() {
        TestRegistryUserSchema.primeData();
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
    }

    /**
     * Test the createUser method.
     * @throws PAException if an error occurs
     */
    @Test
    public void createUserTest() throws PAException {
        RegistryUser create = createRegisterUserObj();
        RegistryUser saved = remoteEjb.createUser(create);
        assertRegistryUser(create, saved);
    }

    /**
     * Test the updateUser method.
     * @throws PAException if an error occurs
     */
    @Test
    public void updateUserTest() throws PAException {
        RegistryUser create = createRegisterUserObj();
        remoteEjb.createUser(create);
        create.setFirstName("firstnamechanged");
        RegistryUser saved = remoteEjb.updateUser(create);
        assertRegistryUser(create, saved);
    }

    /**
     * Test the getUserById method.
     * @throws PAException if an error occurs
     */
    @Test
    public void getUserById() throws PAException {
        RegistryUser usr = remoteEjb.getUserById(TestRegistryUserSchema.randomUserId);
        assertNotNull(usr);
    }

    /**
     * Test the getUserByUserOrgType method.
     * @throws PAException if an error occurs
     */
    @Test
    public void getUserByUserOrgType() throws PAException {
        List<RegistryUser> usrLst = remoteEjb.getUserByUserOrgType(UserOrgType.PENDING_ADMIN);
        assertEquals(0, usrLst.size());
        usrLst = remoteEjb.getUserByUserOrgType(UserOrgType.ADMIN);
        assertTrue(usrLst.size() >= 1);
    }

    /**
     * Test the hasTrialAccess method.
     * @throws PAException if an error occurs
     */
    @Test
    public void hasTrialAccess() throws PAException {
        Long spId = TestRegistryUserSchema.studyProtocolId;
        assertTrue(remoteEjb.hasTrialAccess("leadOrgAdminTest", spId));
        assertFalse(remoteEjb.hasTrialAccess("trialOwnerTest", spId));
        assertFalse(remoteEjb.hasTrialAccess("randomUserTest", spId));
    }

    /**
     * Test the hasTrialAccess method.
     * @throws PAException if an error occurs
     */
    @Test
    public void getTrialOwnerNames() throws PAException {
        Long spId = TestRegistryUserSchema.studyProtocolId;
        Long userId = TestRegistryUserSchema.randomUserId;
        remoteEjb.assignOwnership(userId, spId);
        List<String> list = remoteEjb.getTrialOwnerNames(spId);
        assertTrue(list.contains("random random"));
    }

    /**
     * Test the getAllTrialOwners method.
     * @throws PAException if an error occurs
     */
    @Test
    public void getTrialOwners() throws PAException {
        Long spId = TestRegistryUserSchema.studyProtocolId;
        Long userId = TestRegistryUserSchema.trialOwnerUserId;
        remoteEjb.assignOwnership(userId, spId);
        Set<RegistryUser> regUsers = remoteEjb.getAllTrialOwners(spId);
        assertEquals(1, regUsers.size());
        assertEquals("owner", regUsers.iterator().next().getLastName());
    }

    /**
     * Test the search method.
     * @throws PAException if an error occurs
     */
    @Test
    public void search() throws PAException {
        List<RegistryUser> usrLst = remoteEjb.search(new RegistryUser());
        assertNotNull(usrLst);
    }

    /**
     * Test the assignOwnership method.
     * @throws PAException if an error occurs
     */
    @Test
    public void assignOwnership() throws PAException {
        Long spId = TestRegistryUserSchema.studyProtocolId;
        Long userId = TestRegistryUserSchema.randomUserId;
        remoteEjb.assignOwnership(userId, spId);
        assertTrue(remoteEjb.isTrialOwner(userId, spId));
    }

    /**
     * Test the removeOwnership method.
     * @throws PAException if an error occurs
     */
    @Test
    public void removeOwnership() throws PAException {
        Long spId = TestRegistryUserSchema.studyProtocolId;
        Long userId = TestRegistryUserSchema.randomUserId;
        remoteEjb.removeOwnership(userId, spId);
        assertFalse(remoteEjb.isTrialOwner(userId, spId));
    }

    private RegistryUser createRegisterUserObj() {
        RegistryUser create = new RegistryUser();
        create.setAddressLine("xxxxx");

        create.setAffiliateOrg("aff");
        create.setCity("city");
        create.setCountry("country");
        create.setCsmUserId(Long.valueOf(1));
        create.setFirstName("firstname");
        create.setLastName("lastname");
        create.setMiddleName("middlename");
        create.setPhone("1111");
        create.setPostalCode("00000");
        create.setState("va");
        create.setPrsOrgName("prsOrgName");
        create.setAffiliatedOrganizationId(501L);
        create.setAffiliatedOrgUserType(UserOrgType.ADMIN);
        return create;
    }

    private void assertRegistryUser(RegistryUser create, RegistryUser saved) {
        assertNotNull(saved);
        assertNotNull(create);
        assertEquals("Address does not match  ", create.getAddressLine(), saved.getAddressLine());
        assertEquals("Affliate Org not match  ", create.getAffiliateOrg(), saved.getAffiliateOrg());
        assertEquals("City does not match  ", create.getCity(), saved.getCity());
        assertEquals("Country does not match  ", create.getCountry(), saved.getCountry());
        assertEquals("CSM User id does not match  ", create.getCsmUserId(), saved.getCsmUserId());
        assertEquals("First name does not match  ", create.getFirstName(), saved.getFirstName());
        assertEquals("Last name does not match  ", create.getLastName(), saved.getLastName());
        assertEquals("Middle name does not match  ", create.getMiddleName(), saved.getMiddleName());
        assertEquals("Phone does not match  ", create.getPhone(), saved.getPhone());
        assertEquals("Postal code does not match  ", create.getPostalCode(), saved.getPostalCode());
        assertEquals("User Org Type does not match ", create.getAffiliatedOrgUserType(),
                     saved.getAffiliatedOrgUserType());
    }
    
    /**
     * Test the getLoginNamesByEmailAddress method.
     */
    @Test
    public void testGetLoginNamesByEmailAddress() {
        String emailAddress = "username@nci.nih.gov";
        List<String> loginNames = remoteEjb.getLoginNamesByEmailAddress(emailAddress);
        assertNotNull("No result returned", loginNames);
        assertEquals("Wrong result size", 1, loginNames.size());
        assertEquals("Wrong name returned", "randomUserTest", loginNames.get(0));
    }

}
