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
import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since 11/12/2009
 */
public class PerformedObservationResultServiceTest
        extends AbstractServiceTest<PerformedObservationResultService> {

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        bean = new PerformedObservationResultBeanLocal();
    }

    @Test
    public void get() throws Exception {
        PerformedDiagnosisDto dto1 = bean.getPerformedDiagnosis(IiConverter.convertToIi(TestSchema.performedDiagnosis.get(0).getId()));
        assertNotNull(dto1);
        PerformedImageDto dto2 = bean.getPerformedImage(IiConverter.convertToIi(TestSchema.performedImages.get(0).getId()));
        assertNotNull(dto2);
        PerformedHistopathologyDto dto3 = bean.getPerformedHistopathology(IiConverter.convertToIi(TestSchema.performedHistopathologies.get(0).getId()));
        assertNotNull(dto3);
        PerformedClinicalResultDto dto4 = bean.getPerformedClinicalResult(IiConverter.convertToIi(TestSchema.performedClinicalResults.get(0).getId()));
        assertNotNull(dto4);
        PerformedMedicalHistoryResultDto dto5 = bean.getPerformedMedicalHistoryResult(IiConverter.convertToIi(TestSchema.performedMedicalHistoryResults.get(0).getId()));
        assertNotNull(dto5);
        PerformedLesionDescriptionDto dto6 = bean.getPerformedLesionDescription(IiConverter.convertToIi(TestSchema.performedLesionDescriptions.get(0).getId()));
        assertNotNull(dto6);
        try {
            dto1 = bean.getPerformedDiagnosis(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto2 = bean.getPerformedImage(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto3 = bean.getPerformedHistopathology(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto4 = bean.getPerformedClinicalResult(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto5 = bean.getPerformedMedicalHistoryResult(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
        try {
            dto6 = bean.getPerformedLesionDescription(BII);
        } catch (RemoteException e) {
            // expected behavior
        }
    }
    @Test
    public void create() throws Exception {
        //PerformedDiagnosis
        PerformedDiagnosisDto pddto = new  PerformedDiagnosisDto();
        pddto.setResultDateRange(IvlConverter.convertTs().convertToIvl(PAUtil.dateStringToTimestamp("11/12/2009"),null));
        pddto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        
        PerformedDiagnosisDto pdr = bean.createPerformedDiagnosis(pddto);
        assertNotNull(pdr);
        assertNotNull(pdr.getIdentifier().getExtension());
   
        Timestamp newValue = PAUtil.dateStringToTimestamp("11/13/2009");
        assertFalse(newValue.equals(TestSchema.performedDiagnosis.get(0).getResultDateRangeLow()));
        PerformedDiagnosisDto pddto2 = bean.getPerformedDiagnosis(IiConverter.convertToIi(TestSchema.performedDiagnosis.get(0).getId()));
        pddto2.setResultDateRange(IvlConverter.convertTs().convertToIvl(newValue,null));
        pddto2.setResultCode(CdConverter.convertStringToCd("test"));
        PerformedDiagnosisDto r2 = bean.updatePerformedDiagnosis(pddto2);
        assertTrue(pddto2.getResultCode().getCode().equals(r2.getResultCode().getCode()));
        assertTrue(newValue.equals(IvlConverter.convertTs().convertLow(r2.getResultDateRange())));
        
      //PerformedImage
        PerformedImageDto pidto = new  PerformedImageDto();
        pidto.setResultDateRange(IvlConverter.convertTs().convertToIvl(PAUtil.dateStringToTimestamp("11/12/2009"),null));
        pidto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        pidto.setImageIdentifier(IiConverter.convertToIi(1L));
        pidto.setSeriesIdentifier(IiConverter.convertToIi(1L));
        
        PerformedImageDto pi = bean.createPerformedImage(pidto);
        assertNotNull(pi);
        assertNotNull(pi.getIdentifier().getExtension());
   
        Timestamp pinewValue = PAUtil.dateStringToTimestamp("11/13/2009");
        assertFalse(pinewValue.equals(TestSchema.performedImages.get(0).getResultDateRangeLow()));
        PerformedImageDto pidto2 = bean.getPerformedImage(IiConverter.convertToIi(TestSchema.performedImages.get(0).getId()));
        pidto2.setResultDateRange(IvlConverter.convertTs().convertToIvl(newValue,null));
        pidto2.setResultCode(CdConverter.convertStringToCd("test"));
        pidto2.setImageIdentifier(IiConverter.convertToIi(1L));
        pidto2.setSeriesIdentifier(IiConverter.convertToIi(1L));
        PerformedImageDto ri2 = bean.updatePerformedImage(pidto2);
        assertTrue(pidto2.getResultCode().getCode().equals(ri2.getResultCode().getCode()));
        assertTrue(pidto2.getImageIdentifier().getExtension().equals(ri2.getImageIdentifier().getExtension()));
        assertTrue(pidto2.getSeriesIdentifier().getExtension().equals(ri2.getSeriesIdentifier().getExtension()));
        assertTrue(newValue.equals(IvlConverter.convertTs().convertLow(r2.getResultDateRange())));
        
        //PerformedClinicalResult
        PerformedClinicalResultDto pcrdto = new  PerformedClinicalResultDto();
        pcrdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        pcrdto.setStageCodingSystem(CdConverter.convertStringToCd("StageCodingSystem"));
        Pq resultQuantity = new Pq();
        resultQuantity.setUnit("unit");
        resultQuantity.setValue(new BigDecimal(1));
        pcrdto.setResultQuantity(resultQuantity); 
        
        PerformedClinicalResultDto pcr = bean.createPerformedClinicalResult(pcrdto);
        assertNotNull(pcr);
        assertNotNull(pcr.getIdentifier().getExtension());
   
        PerformedClinicalResultDto pcrdto2 = bean.getPerformedClinicalResult(IiConverter.convertToIi(TestSchema.performedClinicalResults.get(0).getId()));
        pcrdto2.setStageCodingSystem(CdConverter.convertStringToCd("test"));
        PerformedClinicalResultDto pcrr2 = bean.updatePerformedClinicalResult(pcrdto2);
        assertTrue(pcrdto2.getStageCodingSystem().getCode().equals(pcrr2.getStageCodingSystem().getCode()));
        
        //PerformedHistopathology
        PerformedHistopathologyDto phpdto = new  PerformedHistopathologyDto();
        phpdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        phpdto.setGradeCode(CdConverter.convertStringToCd("GradeCode"));
        phpdto.setDescription(StConverter.convertToSt("description"));
        
        PerformedHistopathologyDto phpr = bean.createPerformedHistopathology(phpdto);
        assertNotNull(phpr);
        assertNotNull(phpr.getIdentifier().getExtension());
   
        PerformedHistopathologyDto phpdto2 = bean.getPerformedHistopathology(IiConverter.convertToIi(TestSchema.performedHistopathologies.get(0).getId()));
        phpdto2.setGradeCode(CdConverter.convertStringToCd("test"));
        PerformedHistopathologyDto phpr2 = bean.updatePerformedHistopathology(phpdto2);
        assertTrue(phpdto2.getGradeCode().getCode().equals(phpr2.getGradeCode().getCode()));
        assertTrue(phpdto2.getDescription().getValue().equals(phpr2.getDescription().getValue()));
        
      //PerformedMedicalHistoryResult
        PerformedMedicalHistoryResultDto pmhrdto = new  PerformedMedicalHistoryResultDto();
        pmhrdto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        pmhrdto.setDescription(StConverter.convertToSt("PriorTherapy Description"));
        resultQuantity.setUnit("Unitary");
        pmhrdto.setResultQuantity(resultQuantity);
        
        PerformedMedicalHistoryResultDto pmhrr = bean.createPerformedMedicalHistoryResult(pmhrdto);
        assertNotNull(pmhrr);
        assertNotNull(pmhrr.getIdentifier().getExtension());
   
        PerformedMedicalHistoryResultDto pmhrdto2 = bean.getPerformedMedicalHistoryResult(IiConverter.convertToIi(TestSchema.performedMedicalHistoryResults.get(0).getId()));
        pmhrdto2.setTypeCode(CdConverter.convertStringToCd(PerformedObservationResultTypeCode.VITAL_STATUS.getCode()));
        PerformedMedicalHistoryResultDto pmhrr2 = bean.updatePerformedMedicalHistoryResult(pmhrdto2);
        assertTrue(pmhrdto2.getTypeCode().getCode().equals(pmhrr2.getTypeCode().getCode()));
        assertTrue(pmhrdto2.getDescription().getValue().equals(pmhrr2.getDescription().getValue()));
        
      //PerformedLesionDescription
        PerformedLesionDescriptionDto plddto = new  PerformedLesionDescriptionDto();
        plddto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        plddto.setLesionNumber(IntConverter.convertToInt("2"));
        
        PerformedLesionDescriptionDto pldr = bean.createPerformedLesionDescription(plddto);
        assertNotNull(pldr);
        assertNotNull(pldr.getIdentifier().getExtension());
   
        PerformedLesionDescriptionDto plddto2 = bean.getPerformedLesionDescription(IiConverter.convertToIi(TestSchema.performedLesionDescriptions.get(0).getId()));
        plddto2.setLesionNumber(IntConverter.convertToInt("4"));
        PerformedLesionDescriptionDto pldr2 = bean.updatePerformedLesionDescription(plddto2);
        assertTrue(plddto2.getLesionNumber().getValue().equals(pldr2.getLesionNumber().getValue()));
    }
    @Test
    public void getByPerformedActivity() throws Exception {
        List<PerformedDiagnosisDto> rList1 = bean.getPerformedDiagnosisByPerformedActivity(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertTrue(0 < rList1.size());
        List<PerformedImageDto> rList2 = bean.getPerformedImageByPerformedActivity(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertTrue(0 < rList2.size());
        List<PerformedHistopathologyDto> rList3 = bean.getPerformedHistopathologyByPerformedActivity(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertTrue(0 < rList3.size());
        List<PerformedClinicalResultDto> rList4 = bean.getPerformedClinicalResultByPerformedActivity(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertTrue(0 < rList4.size());
        List<PerformedMedicalHistoryResultDto> rList5 = bean.getPerformedMedicalHistoryResultByPerformedActivity(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertTrue(0 < rList5.size());
        List<PerformedLesionDescriptionDto> rList6 = bean.getPerformedLesionDescriptionByPerformedActivity(IiConverter.convertToIi(TestSchema.performedObservations.get(0).getId()));
        assertTrue(0 < rList6.size());
    }  
}
