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

package gov.nih.nci.accrual.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedProcedureDto;
import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Aug 29, 2009
 */
public class PerformedActivityServiceTest
        extends AbstractServiceTest<PerformedActivityService> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        bean = new PerformedActivityBeanLocal();
    }

    @Test
    public void get() throws Exception {
        PerformedSubjectMilestoneDto dto1 = bean.getPerformedSubjectMilestone(IiConverter.convertToIi(TestSchema.performedSubjectMilestones.get(0).getId()));
        assertNotNull(dto1);
        PerformedObservationDto dto2 = bean.getPerformedObservation(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertNotNull(dto2);
        PerformedImagingDto dto3 = bean.getPerformedImaging(IiConverter.convertToIi(TestSchema.performedImagings.get(0).getId()));
        assertNotNull(dto3);
        PerformedSubstanceAdministrationDto dto4 = bean.getPerformedSubstanceAdministration(IiConverter.convertToIi(TestSchema.performedSubstanceAdministrations.get(0).getId()));
        assertNotNull(dto4);
        PerformedRadiationAdministrationDto dto5 = bean.getPerformedRadiationAdministration(IiConverter.convertToIi(TestSchema.performedRadiationAdministrations.get(0).getId()));
        assertNotNull(dto5);
        PerformedProcedureDto dto6 = bean.getPerformedProcedure(IiConverter.convertToIi(TestSchema.performedProcedures.get(0).getId()));
        assertNotNull(dto6);
        try {
            dto1 = bean.getPerformedSubjectMilestone(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto2 = bean.getPerformedObservation(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto3 = bean.getPerformedImaging(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto4 = bean.getPerformedSubstanceAdministration(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto5 = bean.getPerformedRadiationAdministration(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto6 = bean.getPerformedProcedure(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
    }
    @Test
    public void create() throws Exception {
        //PerformedSubjectMilestone
        PerformedSubjectMilestoneDto psmdto = new  PerformedSubjectMilestoneDto();
        psmdto.setInformedConsentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("7/7/2009")));
        psmdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));

        PerformedSubjectMilestoneDto psmr = bean.createPerformedSubjectMilestone(psmdto);
        assertNotNull(psmr);
        
        Timestamp newValue = PAUtil.dateStringToTimestamp("2/3/2003");
        assertFalse(newValue.equals(TestSchema.performedSubjectMilestones.get(0).getInformedConsentDate()));
        PerformedSubjectMilestoneDto psmdto2 = bean.getPerformedSubjectMilestone(IiConverter.convertToIi(TestSchema.performedSubjectMilestones.get(0).getId()));
        psmdto2.setInformedConsentDate(TsConverter.convertToTs(newValue));
        PerformedSubjectMilestoneDto psmr2 = bean.updatePerformedSubjectMilestone(psmdto2);
        assertTrue(newValue.equals(TsConverter.convertToTimestamp(psmr2.getInformedConsentDate())));
        
        //PerformedObservation
        PerformedObservationDto podto = new  PerformedObservationDto();
        podto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        List<Cd> cds = new ArrayList<Cd>();
        cds.add(CdConverter.convertStringToCd("methodCode"));
        podto.setMethodCode(DSetConverter.convertCdListToDSet(cds));
        podto.setTargetSiteCode(CdConverter.convertStringToCd("targetSiteCode"));

        PerformedObservationDto por = bean.createPerformedObservation(podto);
        assertNotNull(por);
        
        PerformedObservationDto podto2 = bean.getPerformedObservation(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        podto2.setTargetSiteCode(CdConverter.convertStringToCd("targetSiteCode2"));
        PerformedObservationDto por2 = bean.updatePerformedObservation(podto2);
        assertTrue(podto2.getTargetSiteCode().getCode().equals(por2.getTargetSiteCode().getCode()));
        
        //PerformedImaging
        PerformedImagingDto pidto = new  PerformedImagingDto();
        pidto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));        
        pidto.setContrastAgentEnhancementIndicator(BlConverter.convertToBl(true));

        PerformedObservationDto pir = bean.createPerformedObservation(pidto);
        assertNotNull(pir);
        
        PerformedImagingDto pidto2 = bean.getPerformedImaging(IiConverter.convertToIi(TestSchema.performedImagings.get(0).getId()));
        pidto2.setContrastAgentEnhancementIndicator(BlConverter.convertToBl(false));
        PerformedImagingDto pir2 = bean.updatePerformedImaging(pidto2);
        assertTrue(pidto2.getContrastAgentEnhancementIndicator().getValue().equals(pir2.getContrastAgentEnhancementIndicator().getValue()));
        
        //PerformedSubstanceAdministration
        PerformedSubstanceAdministrationDto psadto = new PerformedSubstanceAdministrationDto();
        psadto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));        
        psadto.setDoseDescription(StConverter.convertToSt("Dose"));
        psadto.setDoseRegimen(StConverter.convertToSt(">"));
        Pq dose = new Pq();
        dose.setUnit("unit");
        dose.setValue(new BigDecimal(1));
        psadto.setDose(dose);
        psadto.setDoseFormCode(CdConverter.convertStringToCd("TABLET"));
        psadto.setDoseFrequencyCode(CdConverter.convertStringToCd("BID"));
        psadto.setRouteOfAdministrationCode(CdConverter.convertStringToCd("ORAL"));
        PerformedSubstanceAdministrationDto psar = bean.createPerformedSubstanceAdministration(psadto);
        assertNotNull(psar);
        
        PerformedSubstanceAdministrationDto psadto2 = bean.getPerformedSubstanceAdministration(IiConverter.convertToIi(TestSchema.performedSubstanceAdministrations.get(0).getId()));
        psadto2.setDoseDescription(StConverter.convertToSt("Dose Changed"));
        PerformedSubstanceAdministrationDto psar2 = bean.updatePerformedSubstanceAdministration(psadto2);
        assertTrue(psadto2.getDoseDescription().getValue().equals(psar2.getDoseDescription().getValue()));
        
        //PerformedRadiationAdministration
        PerformedRadiationAdministrationDto pradto = new PerformedRadiationAdministrationDto();
        pradto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId())); 
        Pq doseTotal = new Pq();
        doseTotal.setUnit("unit");
        doseTotal.setValue(new BigDecimal(1));
        pradto.setDoseTotal(doseTotal);
        pradto.setMachineTypeCode(CdConverter.convertStringToCd("machineTypeCode"));
        PerformedRadiationAdministrationDto prar = bean.createPerformedRadiationAdministration(pradto);
        assertNotNull(prar);
        
        PerformedRadiationAdministrationDto pradto2 = bean.getPerformedRadiationAdministration(IiConverter.convertToIi(TestSchema.performedRadiationAdministrations.get(0).getId()));
        pradto2.setMachineTypeCode(CdConverter.convertStringToCd("machineCode"));
        PerformedRadiationAdministrationDto prar2 = bean.updatePerformedRadiationAdministration(pradto2);
        assertTrue(pradto2.getMachineTypeCode().getCode().equals(prar2.getMachineTypeCode().getCode()));
        
        //PerformedProcedure
        PerformedProcedureDto ppdto = new PerformedProcedureDto();
        ppdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));        
        ppdto.setTextDescription(StConverter.convertToSt("PerformedProcedure"));
        ppdto.setCategoryCode(CdConverter.convertStringToCd("PerformedProcedure"));
        PerformedProcedureDto ppr = bean.createPerformedProcedure(ppdto);
        assertNotNull(ppr);
        
        PerformedProcedureDto ppdto2 = bean.getPerformedProcedure(IiConverter.convertToIi(TestSchema.performedProcedures.get(0).getId()));
        ppdto2.setTextDescription(StConverter.convertToSt("Text Changed"));
        PerformedProcedureDto ppr2 = bean.updatePerformedProcedure(ppdto2);
        assertTrue(ppdto2.getTextDescription().getValue().equals(ppr2.getTextDescription().getValue()));
    }
    @Test
    public void getByStudyProtocol() throws Exception {
        List<PerformedSubjectMilestoneDto> rList1 = bean.getPerformedSubjectMilestoneByStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertTrue(0 < rList1.size());
        List<PerformedObservationDto> rList2 = bean.getPerformedObservationByStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertTrue(0 < rList2.size());
        List<PerformedImagingDto> rList3 = bean.getPerformedImagingByStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertTrue(0 < rList3.size());
        List<PerformedSubstanceAdministrationDto> rList4 = bean.getPerformedSubstanceAdministrationByStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertTrue(0 < rList4.size());
        List<PerformedRadiationAdministrationDto> rList5 = bean.getPerformedRadiationAdministrationByStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertTrue(0 < rList5.size());
        List<PerformedProcedureDto> rList6 = bean.getPerformedProcedureByStudyProtocol(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertTrue(0 < rList6.size());
    }
    
    @Test
    public void subjectMilestoneExceptions() throws Exception {
        try {
            bean.getByStudySubject(null);
            fail();
        } catch (RemoteException ex) {
            // expected
        }
        
        Ii ii = IiConverter.convertToIi("test ii");
        
        try {
            bean.getByStudySubject(ii);
            fail();
        } catch (Exception ex) {
            // expected
        }
        
        ii = IiConverter.convertToIi(TestSchema.studySubjects.get(0).getId());
        
        List<PerformedSubjectMilestoneDto> dto = bean.getByStudySubject(ii);
        assertNotNull(dto);
    }
}
