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
package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.Constants;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusActionTest extends AbstractPaActionTest {
    private StudyOverallStatusAction testAction;
    StudyProtocolQueryDTO spDTO;

    @Before 
    public void setupStudyOverallStatusActionTests() throws Exception {
        testAction = new StudyOverallStatusAction();
        testAction.prepare();
    }

    @Test
    public void testDisplayStatus() throws Exception {
        testAction.execute();
        assertEquals(ActualAnticipatedTypeCode.ACTUAL.getCode(), testAction.getStartDateType());
        assertEquals("01/01/2000" , testAction.getStartDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED.getCode(), testAction.getCompletionDateType());
        assertEquals("04/15/2010" , testAction.getCompletionDate());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode(), testAction.getCurrentTrialStatus());
        assertEquals("02/01/2008", testAction.getStatusDate());
    }
    
    @Test
    public void testDisplayHistory() throws Exception {
        testAction.execute();
        testAction.historypopup();
        List<StudyOverallStatusWebDTO> rslt = testAction.getOverallStatusList();
        assertEquals(2, rslt.size());
        assertEquals(StudyStatusCode.APPROVED.getDisplayName(), rslt.get(0).getStatusCode());
        assertEquals("01/01/2008", rslt.get(0).getStatusDate());
        assertEquals(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName(), rslt.get(1).getStatusCode());
        assertEquals("02/01/2008", rslt.get(1).getStatusDate());
    }
    
    @Test
    public void testTransitionToAdministrativelyComplete() throws Exception {
        String tracker18328 = "Current Trial Status Date and Primary Completion Date must be the same when " +
                "Current Trial Status is 'Administratively Complete'.";
        testAction.execute();
        
        // completion date != status date
        testAction.setCurrentTrialStatus(StudyStatusCode.ADMINISTRATIVELY_COMPLETE.getCode());
        testAction.setStatusDate("3/1/2008");
        testAction.setStatusReason("statusReason");
        testAction.setCompletionDate("3/2/2008");
        testAction.setCompletionDateType("Actual");
        testAction.update();
        assertNotNull(ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
        //assertTrue(testAction.getActionErrors().contains(tracker18328));
        
        // anticipated completion date 
        testAction.setCompletionDate("3/1/2008");
        testAction.setCompletionDateType("Anticipated");
        testAction.update();
        assertNotNull(ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
        
        // successful update
        testAction.setCompletionDateType("Actual");
        testAction.update();
        assertEquals(Constants.UPDATE_MESSAGE, ServletActionContext.getRequest().getAttribute(Constants.SUCCESS_MESSAGE));
    }
    
    @Test
    public void testTransitionValidationsOccurFirst() throws Exception {
        testAction.execute();
        
        // illegal transition plus date error
        testAction.setCurrentTrialStatus(StudyStatusCode.APPROVED.getCode());
        testAction.setStatusDate("3/1/2008");
        testAction.setCompletionDate("3/1/2008");
        testAction.setCompletionDateType("Actual");
        testAction.update();
        assertTrue(testAction.hasActionErrors());
        String errs = testAction.getActionErrors().toString();
        assertTrue(errs.contains("Illegal study status transition"));
     }
}
