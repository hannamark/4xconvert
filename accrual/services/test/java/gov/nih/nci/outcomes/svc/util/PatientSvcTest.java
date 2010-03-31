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
package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.accrual.outweb.enums.PathologyGradeSystems;
import gov.nih.nci.accrual.outweb.enums.PathologyGrades;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.DiagnosisSvcDto;
import gov.nih.nci.outcomes.svc.dto.OffTreatmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.PathologySvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.RadiationSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.outcomes.svc.exception.OutcomesFieldException;
import gov.nih.nci.pa.enums.OffTreatmentReasonCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.RadiationProcedureTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Mar 10, 2010
 *
 */
public class PatientSvcTest extends AbstractOutcomesSvcTest {

    private final static int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
    
    @Test
    public void createPatient() throws Exception {
        PatientSvcDto p = new PatientSvcDto();
        p.setAction(SvcConstants.CREATE);
        p.setAssignedIdentifier(StConverter.convertToSt("001"));
        p.setBirthDate(dateStringToTs("01/01/2000"));
        p.setCountryAlpha3(StConverter.convertToSt("USA"));
        p.setEthnicCode(CdConverter.convertToCd(PatientEthnicityCode.HISPANIC));
        p.setGenderCode(CdConverter.convertToCd(PatientGenderCode.FEMALE));
        p.setPaymentMethodCode(null);
        List<Cd> cdList = new ArrayList<Cd>();
        cdList.add(CdConverter.convertToCd(PatientRaceCode.WHITE));
        p.setRaceCode(DSetConverter.convertCdListToDSet(cdList));
        assertTrue(PAUtil.isIiNull(p.getIdentifier()));
        try {
            osb.write(p);
            fail();
        } catch(Exception e) {
            //excepted
        }
        p.setAssignedIdentifier(StConverter.convertToSt("002"));
        
        DiagnosisSvcDto diagnosis = new DiagnosisSvcDto();
        diagnosis.setName(StConverter.convertToSt("liver metastases"));
        Ts ts = new Ts();
        ts.setValue(new Date());
        diagnosis.setCreateDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() - MILLIS_IN_DAY)));
        diagnosis.setResultCode(CdConverter.convertStringToCd("liver metastases"));
        diagnosis.setAction(SvcConstants.CREATE);        
        p.setDiagnosis(diagnosis);
        
        PathologySvcDto pathology = new PathologySvcDto();
        pathology.setGrade(CdConverter.convertToCd(PathologyGrades.ANAPLASTIC));
        pathology.setGradeSystem(CdConverter.convertToCd(PathologyGradeSystems.FUHRMAN));
        pathology.setAction(SvcConstants.CREATE);        
        p.setPathology(pathology);
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatment = new TreatmentRegimenSvcDto();
        treatment.setName(StConverter.convertToSt("TR #1"));
        treatment.setDescription(StConverter.convertToSt("TR Round 1"));
        treatment.setAction(SvcConstants.CREATE);        
        trList.add(treatment);
        p.setTreatmentRegimens(trList);
        
        OffTreatmentSvcDto offTreatment = new OffTreatmentSvcDto();
        offTreatment.setLastTreatmentDate(ts);
        offTreatment.setOffTreatmentReason(CdConverter.convertToCd(OffTreatmentReasonCode.FOLLOWUP_COMPLETED));
        offTreatment.setAction(SvcConstants.CREATE);
        treatment.setOffTreatment(offTreatment);
        
        p = osb.write(p);
        assertFalse(PAUtil.isIiNull(p.getIdentifier()));
        
        p.setAction(SvcConstants.UPDATE);
        p.setEthnicCode(CdConverter.convertToCd(PatientEthnicityCode.NOT_REPORTED));
        p.setGenderCode(CdConverter.convertToCd(PatientGenderCode.MALE));
        p.setPaymentMethodCode(CdConverter.convertToCd(PaymentMethodCode.MEDICARE));
        cdList = new ArrayList<Cd>();
        cdList.add(CdConverter.convertToCd(PatientRaceCode.NOT_REPORTED));
        p.setRaceCode(DSetConverter.convertCdListToDSet(cdList));
        p.setDiagnosis(null);   
        p.setPathology(null);
        
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        treatment = p.getTreatmentRegimens().get(0);
        treatment.setDescription(StConverter.convertToSt("TR Round 2"));
        treatment.setAction(SvcConstants.UPDATE);        
        
        List<CycleSvcDto> cycles = new ArrayList<CycleSvcDto>();
        CycleSvcDto cycle = new CycleSvcDto();
        cycle.setCreateDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() - MILLIS_IN_DAY)));
        cycle.setName(StConverter.convertToSt("Cycle Name"));
        cycle.setAction(SvcConstants.CREATE);
        
        List<RadiationSvcDto> radiations = new ArrayList<RadiationSvcDto>();
        RadiationSvcDto rad = new RadiationSvcDto();
        rad.setAction(SvcConstants.CREATE);
        Pq dose = new Pq();
        dose.setValue(new BigDecimal("2"));
        dose.setUnit("Unit/g");
        rad.setDose(dose);
        rad.setDoseFreq(CdConverter.convertStringToCd("QIS"));
        rad.setDuration(dose);
        rad.setRadDate(ts);
        rad.setTotalDose(dose);
        rad.setType(CdConverter.convertToCd(RadiationProcedureTypeCode.EXTENSIVE_RADIATION));
        radiations.add(rad);
        cycle.setRadiations(radiations);
        
        cycles.add(cycle);
        treatment.setCycles(cycles);
        
        offTreatment = treatment.getOffTreatment();
        offTreatment.setOffTreatmentReason(CdConverter.convertToCd(OffTreatmentReasonCode.OTHER));
        offTreatment.setAction(SvcConstants.UPDATE);
        treatment.setOffTreatment(offTreatment);
        trList.add(treatment);
        p.setTreatmentRegimens(trList);
        
        p = osb.write(p);
        assertFalse(PAUtil.isIiNull(p.getIdentifier()));
        
        Ii studySubjectIi = p.getIdentifier();
        p = new PatientSvcDto();
        p = osb.getById(studySubjectIi);
        assertNotNull(p);
        assertNotNull(p.getDiagnosis().getIdentifier());
        assertNotNull(p.getPathology().getIdentifier());
        assertNotNull(p.getTreatmentRegimens().get(0).getIdentifier());
        assertNotNull(p.getTreatmentRegimens().get(0).getOffTreatment().getIdentifier());
        assertNotNull(p.getTreatmentRegimens().get(0).getCycles().get(0).getIdentifier());
        assertNotNull(p.getTreatmentRegimens().get(0).getCycles().get(0).getRadiations().get(0).getIdentifier());
        
        Ii patientIi =  p.getIdentifier();
        p = new PatientSvcDto();
        p.setIdentifier(patientIi);
        p.setAction(SvcConstants.DELETE);
        p = osb.write(p);
        assertNull(p);
    }

    @Test
    public void baseClassValidationTestInvalidAction() throws Exception {
        PatientSvcDto p = new PatientSvcDto();
        p.setAction(CdConverter.convertStringToCd("xyzzy"));
        p.setAssignedIdentifier(StConverter.convertToSt("001"));
        p.setBirthDate(dateStringToTs("01/01/2000"));
        p.setCountryAlpha3(StConverter.convertToSt("USA"));
        p.setEthnicCode(CdConverter.convertToCd(PatientEthnicityCode.HISPANIC));
        p.setGenderCode(CdConverter.convertToCd(PatientGenderCode.FEMALE));
        p.setPaymentMethodCode(null);
        List<Cd> cdList = new ArrayList<Cd>();
        cdList.add(CdConverter.convertToCd(PatientRaceCode.WHITE));
        p.setRaceCode(DSetConverter.convertCdListToDSet(cdList));
        try {
            osb.write(p);
            fail();
        } catch (OutcomesFieldException e) {
            assertEquals(PatientSvcDto.class, e.getDtoClass());
            assertEquals("action", e.getField());
        }
    }

    @Test
    public void baseClassValidationTestNullAction() throws Exception {
        PatientSvcDto p = new PatientSvcDto();
        p.setAction(null);
        p.setAssignedIdentifier(StConverter.convertToSt("001"));
        p.setBirthDate(dateStringToTs("01/01/2000"));
        p.setCountryAlpha3(StConverter.convertToSt("USA"));
        p.setEthnicCode(CdConverter.convertToCd(PatientEthnicityCode.HISPANIC));
        p.setGenderCode(CdConverter.convertToCd(PatientGenderCode.FEMALE));
        p.setPaymentMethodCode(null);
        List<Cd> cdList = new ArrayList<Cd>();
        cdList.add(CdConverter.convertToCd(PatientRaceCode.WHITE));
        p.setRaceCode(DSetConverter.convertCdListToDSet(cdList));
        assertTrue(PAUtil.isIiNull(p.getIdentifier()));
        p = osb.write(p);
        assertTrue(PAUtil.isIiNull(p.getIdentifier()));
    }
}
