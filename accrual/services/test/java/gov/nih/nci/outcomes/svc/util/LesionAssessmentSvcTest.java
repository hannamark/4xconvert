package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.LesionAssessmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.pa.enums.LesionMeasurementMethodCode;
import gov.nih.nci.pa.enums.MeasurableEvaluableDiseaseTypeCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 31, 2010
 *
 */

public class LesionAssessmentSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void lesionAssessmentTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatmentRegimen = new TreatmentRegimenSvcDto();
        List<LesionAssessmentSvcDto> lesionAssessments = new ArrayList<LesionAssessmentSvcDto>();
        LesionAssessmentSvcDto lesionAssessment = new LesionAssessmentSvcDto();
        Ts ts = new Ts();
        ts.setValue(new Date());
        lesionAssessment.setClinicalAssessmentDate(ts);
        Pq lesionLongestDiameter = new Pq();
        lesionLongestDiameter.setValue(new BigDecimal("2"));
        lesionLongestDiameter.setUnit("Years");
        lesionAssessment.setLesionLongestDiameter(lesionLongestDiameter);
        lesionAssessment.setLesionSite(CdConverter.convertStringToCd("Mammary Gland")); 
        lesionAssessment.setLesionNum(IiConverter.convertToIi(1L));
        lesionAssessment.setMeasurableEvaluableDiseaseType(CdConverter.convertToCd(MeasurableEvaluableDiseaseTypeCode.EVALUABLE));
        lesionAssessment.setLesionMeasurementMethod(CdConverter.convertToCd(LesionMeasurementMethodCode.BONE_SCAN));
        lesionAssessment.setAction(SvcConstants.CREATE);
        lesionAssessments.add(lesionAssessment);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setLesionAssessments(lesionAssessments);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTreatmentRegimens().get(0).getLesionAssessments().get(0).getIdentifier()));
        
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setLesionAssessments(new ArrayList<LesionAssessmentSvcDto>());
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens().get(0).getLesionAssessments().get(0).getIdentifier().getExtension());
        

        trList = new ArrayList<TreatmentRegimenSvcDto>();
        lesionAssessments = new ArrayList<LesionAssessmentSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        lesionAssessment = pSvcList.get(0).getTreatmentRegimens().get(0).getLesionAssessments().get(0);
        lesionLongestDiameter = new Pq();
        lesionLongestDiameter.setValue(new BigDecimal("4"));
        lesionLongestDiameter.setUnit("Years");
        lesionAssessment.setImageIdentifier(IiConverter.convertToIi(1L));
        lesionAssessment.setImageSeriesIdentifier(IiConverter.convertToIi(1L));
        lesionAssessment.setLesionLongestDiameter(lesionLongestDiameter);
        lesionAssessment.setMeasurableEvaluableDiseaseType(CdConverter.convertToCd(MeasurableEvaluableDiseaseTypeCode.BOTH));
        lesionAssessment.setAction(SvcConstants.UPDATE);
        lesionAssessments.add(lesionAssessment);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setLesionAssessments(lesionAssessments);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        pSvcList = osb.get(svcDto);
        
        lesionAssessments = new ArrayList<LesionAssessmentSvcDto>();
        lesionAssessment = pSvcList.get(0).getTreatmentRegimens().get(0).getLesionAssessments().get(0);
        lesionAssessment.setAction(SvcConstants.DELETE);
        lesionAssessments.add(lesionAssessment);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setLesionAssessments(lesionAssessments);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertNull(svcDto.getTreatmentRegimens().get(0).getLesionAssessments().get(0));        
    }
}
