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
package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.nih.nci.accrual.dto.util.SearchTrialCriteriaDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Aug 25, 2009
 */
public class SearchTrialServiceTest extends AbstractServiceTest<SearchTrialService> {
    SearchTrialService bean;

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        AccrualCsmUtil.csmUtil = new MockCsmUtil();
        bean = new SearchTrialBean();
    }

    @Test
    public void searchUser1() throws Exception {
        search(MockCsmUtil.users.get(0).getLoginName(), true);
    }

    @Test
    public void searchUser2() throws Exception {
        search(MockCsmUtil.users.get(1).getLoginName(), true);
    }

    @Test
    public void searchUser3() throws Exception {
        search(MockCsmUtil.users.get(2).getLoginName(), false);
    }

    public void search(String loginName, boolean shouldBeAuthorized) throws Exception {
        St authUser = StConverter.convertToSt(loginName);
        int goodCount = shouldBeAuthorized ? 1: 0;

        // second study is inactive
        bean.search(new SearchTrialCriteriaDto(), authUser);
        assertEquals(goodCount, bean.search(new SearchTrialCriteriaDto(), authUser).size());

        // get by assigned identifier
        SearchTrialCriteriaDto crit = new SearchTrialCriteriaDto();
        crit.setAssignedIdentifier(StConverter.convertToSt(TestSchema.studyProtocols.get(0).getIdentifier()));
        assertEquals(goodCount, bean.search(crit, authUser).size());
        crit.setAssignedIdentifier(BST);
        assertEquals(0, bean.search(crit, authUser).size());

        // get by title
        crit = new SearchTrialCriteriaDto();
        crit.setOfficialTitle(StConverter.convertToSt(TestSchema.studyProtocols.get(0).getOfficialTitle()));
        assertEquals(goodCount, bean.search(crit, authUser).size());
        crit.setOfficialTitle(BST);
        assertEquals(0, bean.search(crit, authUser).size());

        // get by title
        crit = new SearchTrialCriteriaDto();
        crit.setLeadOrgTrialIdentifier(StConverter.convertToSt(TestSchema.studySites.get(0).getLocalStudyProtocolIdentifier()));
        assertEquals(goodCount, bean.search(crit, authUser).size());
        crit.setLeadOrgTrialIdentifier(BST);
        assertEquals(0, bean.search(crit, authUser).size());
    }

    @Test
    public void getTrialSummaryByStudyProtocolIi() throws Exception {
        SearchTrialResultDto result = bean.getTrialSummaryByStudyProtocolIi(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertNotNull(result);

        try {
            bean.getTrialSummaryByStudyProtocolIi(BII);
            fail();
        } catch (RemoteException e) {
            // expected behavior
        }
    }

    @Test
    public void getStudyOverallStatus() throws Exception {
        List<SearchTrialResultDto> rList = bean.search(new SearchTrialCriteriaDto(), BST);
        for (SearchTrialResultDto r : rList) {
            if (IiConverter.convertToLong(r.getStudyProtocolIdentifier()).equals(TestSchema.studyProtocols.get(0).getId())) {
                assertEquals(StudyStatusCode.ACTIVE, StudyStatusCode.getByCode(r.getStudyStatusCode().getCode()));
            }
            if (IiConverter.convertToLong(r.getStudyProtocolIdentifier()).equals(TestSchema.studyProtocols.get(1).getId())) {
                assertNull(StudyStatusCode.getByCode(r.getStudyStatusCode().getCode()));
            }
        }
    }
}
