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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceBean;
import gov.nih.nci.pa.service.internal.StudySiteAccrualStatusBeanLocal;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Sep 3, 2009
 */
public class StudySiteAccrualAccessServiceTest {
    private static final Long csmUserId = 23L;
    private static final String requestDetails = "request details";
    private Long id;
    private Long ssId;

    StudySiteAccrualAccessServiceLocal bean;
    StudySiteAccrualStatusBeanLocal statusBean;

    @Before
    public void setUp() throws Exception {
        StudySiteAccrualAccessServiceBean bean = new StudySiteAccrualAccessServiceBean();
        statusBean = new StudySiteAccrualStatusServiceBean();
        bean.studySiteAccrualStatusService = statusBean;
        StudySiteAccrualAccessServiceBean.lastUpdate = new Timestamp(new Date().getTime());
        User user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setLoginName("john.doe@yahoo.com");
        Set<User> users = new HashSet<User>();
        users.add(user);
        StudySiteAccrualAccessServiceBean.submitterList = users;
        this.bean = bean;
        TestSchema.reset1();
        TestSchema.primeData();
        ssId = TestSchema.studySiteIds.get(0);
     }

    @Test
    public void create() throws Exception {
        StudySiteAccrualAccessDTO dto = new StudySiteAccrualAccessDTO();
        dto.setCsmUserId(csmUserId);
        dto.setRequestDetails(requestDetails);
        dto.setStudySiteId(ssId);
        dto.setStatusCode(ActiveInactiveCode.ACTIVE);
        assertNull(dto.getId());
        StudySiteAccrualAccessDTO r = bean.create(dto);
        assertNotNull(r.getId());
        assertEquals(csmUserId, r.getCsmUserId());
        assertEquals(requestDetails, r.getRequestDetails());
        assertEquals(ssId, r.getStudySiteId());
        assertNotNull(r.getUserLastCreated());
        assertNotNull(r.getDateLastCreated());
        assertNull(r.getUserLastUpdated());
        assertNull(r.getDateLastUpdated());
        assertEquals(ActiveInactiveCode.ACTIVE, r.getStatusCode());
        id = r.getId();
    }

    @Test
    public void get() throws Exception {
        create();
        StudySiteAccrualAccessDTO r = bean.get(id);
        assertEquals(csmUserId, r.getCsmUserId());
        assertEquals(ssId, r.getStudySiteId());
        assertEquals(requestDetails, r.getRequestDetails());
    }

    @Test
    public void getByStudySite() throws Exception {
        create();
        List<StudySiteAccrualAccessDTO> rList = bean.getByStudyProtocol(ssId);
        boolean found = false;
        for (StudySiteAccrualAccessDTO r : rList) {
            found = r.getId() == id ? true : found;
        }
        assertTrue(found);
    }

    @Test
    public void update() throws Exception {
        String updatedRequestDetails = "xxx";
        create();
        StudySiteAccrualAccessDTO bo = bean.get(id);
        assertFalse(bo.getRequestDetails().equals(updatedRequestDetails));
        bo.setRequestDetails(updatedRequestDetails);
        StudySiteAccrualAccessDTO r = bean.update(bo);
        assertTrue(r.getRequestDetails().equals(updatedRequestDetails));
        assertNotNull(r.getUserLastUpdated());
        assertNotNull(r.getDateLastUpdated());
    }

    @Test
    public void createOnlyForAccruingSites() throws Exception {
        assertFalse(RecruitmentStatusCode.WITHDRAWN.isEligibleForAccrual());
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setStudySiteIi(IiConverter.convertToStudySiteIi(ssId));
        dto.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.WITHDRAWN));
        dto.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        statusBean.createStudySiteAccrualStatus(dto);
        try {
            create();
        } catch (PAException e) {
            // expected behavior
        }
    }
}
