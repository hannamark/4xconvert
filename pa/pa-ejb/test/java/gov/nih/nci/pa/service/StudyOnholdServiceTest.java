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
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyOnholdServiceTest {
    private StudyOnholdServiceRemote remote = new StudyOnholdServiceBean();
    private Long spId;
    private Ii spIi;
    
    private String time1 = "1/1/2000";
    private String time2 = "1/1/2099";
    private Timestamp now = new Timestamp(new Date().getTime());
    private String today = PAUtil.normalizeDateString(now.toString());
    private String reasonText = "reason";
    private OnholdReasonCode reasonCode = OnholdReasonCode.INVALID_GRANT;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        spId = TestSchema.studyProtocolIds.get(0);
        spIi = IiConverter.convertToIi(spId);
    }
    
    @Test
    public void createTest() throws Exception {
        // test reason code constraints
        StudyOnholdDTO x = new StudyOnholdDTO();
        x.setOnholdReasonCode(CdConverter.convertToCd(null));
        x.setOnholdReasonText(StConverter.convertToSt(reasonText));
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(time1, now));
        x.setStudyProtocolIdentifier(spIi);
        try {
            remote.create(x);
            fail("Should have throw exception for reason code required.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_REASON_CODE, e.getFieldNumber());
        }

        // test low date constraints
        x.setOnholdReasonCode(CdConverter.convertToCd(reasonCode));
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, null));
        try {
            remote.create(x);
            fail("Should have throw exception on-hold date required.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_DATE_LOW, e.getFieldNumber());
        }
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(time2, null));
        try {
            remote.create(x);
            fail("Should have throw exception for on-hold date can't be in future.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_DATE_LOW, e.getFieldNumber());
        }
        
        // test high date constraints
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(now, time2));
        try {
            remote.create(x);
            fail("Should have throw exception for off-hold date can't be in future.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_DATE_HIGH, e.getFieldNumber());
        }
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(now, time1));
        try {
            remote.create(x);
            fail("Should have throw exception for off-hold date before on-hold date.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_DATE_HIGH, e.getFieldNumber());
        }

        // test successful create
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(now, now));
        StudyOnholdDTO y = remote.create(x);
        assertEquals(now, IvlConverter.convertTs().convertLow(y.getOnholdDate()));
        assertEquals(now, IvlConverter.convertTs().convertHigh(y.getOnholdDate()));
        assertEquals(reasonText, StConverter.convertToString(y.getOnholdReasonText()));
        assertEquals(reasonCode, OnholdReasonCode.getByCode(CdConverter.convertCdToString(y.getOnholdReasonCode())));
        assertEquals(spId, IiConverter.convertToLong(y.getIdentifier()));
    }

    @Test
    public void getByStudyProtocolTest() throws Exception {
        List<StudyOnholdDTO> rList = remote.getByStudyProtocol(spIi);
        int originalCount = rList.size();
        StudyOnholdDTO x = new StudyOnholdDTO();
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        x.setOnholdReasonCode(CdConverter.convertToCd(OnholdReasonCode.PENDING_ORG_CUR));
        x.setOnholdReasonText(StConverter.convertToSt("reason"));
        x.setStudyProtocolIdentifier(spIi);
        remote.create(x);
        rList = remote.getByStudyProtocol(spIi);
        assertEquals(originalCount + 1, rList.size());
    }

    @Test
    public void updateTest() throws Exception {
        // create an on-hold
        StudyOnholdDTO x = new StudyOnholdDTO();
        x.setOnholdReasonCode(CdConverter.convertToCd(reasonCode));
        x.setOnholdReasonText(StConverter.convertToSt(reasonText));
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(now, null));
        x.setStudyProtocolIdentifier(spIi);
        StudyOnholdDTO y = remote.create(x);
        Ii ohIi = y.getIdentifier();
        assertFalse(PAUtil.isIiNull(ohIi));
        
        // confirm date rules (only off-hold date changes)
        y = new StudyOnholdDTO();
        y.setIdentifier(ohIi);
        y.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, time2));
        try {
            remote.update(y);
            fail("Should have throw exception for off-hold date can't be in future.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_DATE_HIGH, e.getFieldNumber());
        }
        y.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, time1));
        try {
            remote.update(y);
            fail("Should have throw exception for off-hold date before on-hold date.");
        } catch (PAFieldException e) {
            assertEquals(StudyOnholdServiceBean.FN_DATE_HIGH, e.getFieldNumber());
        }

        // test good update
        y.setOnholdDate(IvlConverter.convertTs().convertToIvl(null, now));
        StudyOnholdDTO z = remote.update(y);
        assertEquals(IiConverter.convertToLong(ohIi), IiConverter.convertToLong(z.getIdentifier()));
        assertEquals(now, IvlConverter.convertTs().convertLow(z.getOnholdDate()));
        assertEquals(now, IvlConverter.convertTs().convertHigh(z.getOnholdDate()));
        assertEquals(reasonText, StConverter.convertToString(z.getOnholdReasonText()));
        assertEquals(reasonCode, OnholdReasonCode.getByCode(CdConverter.convertCdToString(z.getOnholdReasonCode())));
        assertEquals(spId, IiConverter.convertToLong(z.getIdentifier()));
    }
    
    @Test
    public void storeTimeTest() throws Exception {
        // time should be stored if today
        StudyOnholdDTO x = new StudyOnholdDTO();
        x.setOnholdReasonCode(CdConverter.convertToCd(reasonCode));
        x.setOnholdReasonText(StConverter.convertToSt(reasonText));
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(today, today));
        x.setStudyProtocolIdentifier(spIi);
        StudyOnholdDTO y = remote.create(x);
        assertTrue(PAUtil.dateStringToTimestamp(today).before(IvlConverter.convertTs().convertLow(y.getOnholdDate())));
        assertTrue(PAUtil.dateStringToTimestamp(today).before(IvlConverter.convertTs().convertHigh(y.getOnholdDate())));
        
        // time not stored if not today
        x.setIdentifier(null);
        x.setOnholdDate(IvlConverter.convertTs().convertToIvl(time1, time1));
        y = remote.create(x);
        assertTrue(PAUtil.dateStringToTimestamp(time1).equals(IvlConverter.convertTs().convertLow(y.getOnholdDate())));
        assertTrue(PAUtil.dateStringToTimestamp(time1).equals(IvlConverter.convertTs().convertHigh(y.getOnholdDate())));
    }
}
