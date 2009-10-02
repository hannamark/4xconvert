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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import gov.nih.nci.accrual.dto.util.POPatientDto;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.util.PoJndiServiceLocator;
import gov.nih.nci.accrual.util.PoServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorPoInterface;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.util.JNDIUtil;
import gov.nih.nci.services.correlation.PatientCorrelationServiceRemote;

/**
 * @author lhebel
 *
 */
public class POPatientServiceTest extends AbstractServiceTest<POPatientService>
{
    private PoServiceLocator psl;

    /* (non-Javadoc)
     * @see gov.nih.nci.accrual.service.AbstractServiceTest#instantiateServiceBean()
     */
    @Override
    @Before
    public void instantiateServiceBean() throws Exception
    {
        psl = PoServiceLocator.getInstance();
        psl.setServiceLocator(new MockPoServiceLocator());
        bean = new POPatientBean();
    }
    
    @Test
    public void checkLocator() {
        ServiceLocatorPoInterface pi = psl.getServiceLocator();
        assertNotNull(pi);
    }

    @Test
    public void create() throws Exception {
        POPatientDto dto = new POPatientDto();
        assertNotNull(dto);
        dto.setDuplicateOf(new Ii());
        dto.setIdentifier(null);
        dto.setPlayerIdentifier(new Ii());
        dto.setPostalAddress(new DSet());
        Ii scoper = new Ii();
        scoper.setExtension("0");
        dto.setScoperIdentifier(scoper);
        dto.setStatus(new Cd());
        dto.setTelecomAddress(new DSet<Tel>());
        POPatientDto dto2 = null;
        dto2 = bean.create(dto);
        assertNotNull(dto2);
        Ii dup = dto2.getDuplicateOf();
        assertNotNull(dup);
        Ii id = dto2.getIdentifier();
        assertNotNull(id);
        Ii player = dto2.getPlayerIdentifier();
        assertNotNull(player);
        DSet paddr = dto2.getPostalAddress();
        assertNotNull(paddr);
        scoper = dto2.getScoperIdentifier();
        assertNotNull(scoper);
        Cd status = dto2.getStatus();
        assertNotNull(status);
        DSet<Tel> tel = dto2.getTelecomAddress();
        assertNotNull(tel);
    }
    
    @Test
    public void createWithNull() throws Exception {
        try {
            POPatientDto dto = bean.create(null);
            fail();
        } catch(RemoteException ex) {
            // expected to fail
        }
    }
    
    @Test
    public void createThrowCuration() throws Exception {
        POPatientDto dto = new POPatientDto();
        Ii scoper = new Ii();
        scoper.setExtension("1");
        dto.setScoperIdentifier(scoper);
        
        try {
            dto = bean.create(dto);
            fail();
        } catch(RemoteException ex) {
            // expected to fail
        }
    }
    
    @Test
    public void createThrowEntity() throws Exception {
        POPatientDto dto = new POPatientDto();
        Ii scoper = new Ii();
        scoper.setExtension("2");
        dto.setScoperIdentifier(scoper);
        
        try {
            dto = bean.create(dto);
            fail();
        } catch(RemoteException ex) {
            // expected to fail
        }
    }
    
    @Test
    public void get() throws Exception {
        Ii patient = new Ii();
        patient.setExtension("0");
        POPatientDto dto = bean.get(patient);
        assertNotNull(dto);
    }
    
    @Test
    public void getWithNull() throws Exception {
        Ii patient = new Ii();
        POPatientDto dto = null;
        try {
            dto = bean.get(patient);
            fail();
        } catch (RemoteException ex) {
            // expected because the patient id is null
        }
    }
    
    @Test
    public void getThrowsNull() throws Exception {
        Ii patient = new Ii();
        patient.setExtension("1");
        POPatientDto dto = null;
        try {
            dto = bean.get(patient);
            fail();
        } catch (RemoteException ex) {
            // expected because the patient id is null
        }
    }
    
    @Test
    public void update() throws Exception {
        Ii patient = new Ii();
        patient.setExtension("0");
        POPatientDto dto = bean.get(patient);
        Cd status = dto.getStatus();
        status.setCode("active");
        bean.update(dto);
    }
    
    @Test
    public void updateWithNull() throws Exception {
        try {
            bean.update(null);
            fail();
        } catch (RemoteException ex) {
            // expected with null object
        }
    }
    
    @Test
    public void updateThrowsEntity() throws Exception {
        try {
            POPatientDto dto = new POPatientDto();
            Cd status = new Cd();
            status.setCode("suspended");
            dto.setStatus(status);
            bean.update(dto);
            fail();
        } catch (RemoteException ex) {
            // expected with null object
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void remoteCheck() throws Exception {
        PoJndiServiceLocator psl = new PoJndiServiceLocator();
        try {
            PatientCorrelationServiceRemote obj = psl.getPatientCorrelationService();
        } catch (Exception ex) {
            // expected, ignore it
        } catch (Error ex) {
            // expected, ignore it
        }
        
        try {
            Object obj = JNDIUtil.lookup("jndi");
        } catch (Exception ex) {
            // expected, ignore it
        } catch (Error ex) {
            // expected, ignore it
        }
    }
}
