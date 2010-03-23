package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.accrual.outweb.enums.ResponseInds;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.DiseaseEvaluationSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.pa.enums.BestResponseCode;
import gov.nih.nci.pa.enums.DiseaseStatusCode;
import gov.nih.nci.pa.enums.PatientVitalStatus;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 17, 2010
 *
 */

public class DiseaseEvaluationSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void diseaseEvaluationTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatmentRegimen = new TreatmentRegimenSvcDto();
        List<DiseaseEvaluationSvcDto> dEvaluations = new ArrayList<DiseaseEvaluationSvcDto>();
        DiseaseEvaluationSvcDto diseaseEvaluation = new DiseaseEvaluationSvcDto();
        Ts ts = new Ts();
        ts.setValue(new Date());
        diseaseEvaluation.setAssessmentType(CdConverter.convertStringToCd("2-D Echocardiogram"));
        diseaseEvaluation.setBestResponse(CdConverter.convertToCd(BestResponseCode.COMPLETE_RESPONSE));
        diseaseEvaluation.setBestResponseDate(ts);
        diseaseEvaluation.setDiseaseStatus(CdConverter.convertToCd(DiseaseStatusCode.COMPLETE_REMISSION));
        diseaseEvaluation.setDiseaseStatusDate(ts);
        diseaseEvaluation.setEvaluationDate(ts);
        diseaseEvaluation.setResponseInd(CdConverter.convertToCd(ResponseInds.YES));
        diseaseEvaluation.setVitalStatus(CdConverter.convertToCd(PatientVitalStatus.ALIVE));
        diseaseEvaluation.setAction(SvcConstants.CREATE);
        dEvaluations.add(diseaseEvaluation);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setDiseaseEvaluations(dEvaluations);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTreatmentRegimens().get(0).getIdentifier()));
        
        List<DiseaseEvaluationSvcDto> evaluationList = new ArrayList<DiseaseEvaluationSvcDto>();
        svcDto.setTreatmentRegimens(new ArrayList<TreatmentRegimenSvcDto>());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        trList = pSvcList.get(0).getTreatmentRegimens();
        List<TreatmentRegimenSvcDto> newTRList = new ArrayList<TreatmentRegimenSvcDto>();
        for (TreatmentRegimenSvcDto dto : trList) {
            dto.setDiseaseEvaluations(new ArrayList<DiseaseEvaluationSvcDto>());
            newTRList.add(dto);
            svcDto.setTreatmentRegimens(newTRList);
            pSvcList = osb.get(svcDto);
            evaluationList = pSvcList.get(0).getTreatmentRegimens().get(0).getDiseaseEvaluations();
            if (!evaluationList.isEmpty()) {
                break;
            }
        }
        assertNotNull(evaluationList.size());
        
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        treatmentRegimen.setDiseaseEvaluations(evaluationList);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens().get(0).getDiseaseEvaluations());
    }
}
