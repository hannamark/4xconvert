package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.DiagnosisSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 16, 2010
 *
 */

public class DiagnosisSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void diagnosisTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        DiagnosisSvcDto diagnosis = new DiagnosisSvcDto();
        diagnosis.setName(StConverter.convertToSt("Diagnosis1"));
        Ts ts = new Ts();
        ts.setValue(new Date());
        diagnosis.setCreateDate(ts);
        diagnosis.setResultCode(CdConverter.convertStringToCd("Diagnosis1"));
        diagnosis.setAction(SvcConstants.CREATE);
        
        svcDto.setDiagnosis(diagnosis);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getIdentifier()));
        
        svcDto.setDiagnosis(new DiagnosisSvcDto());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getDiagnosis().getIdentifier().getExtension());
        
        diagnosis = pSvcList.get(0).getDiagnosis();
        diagnosis.setResultCode(CdConverter.convertStringToCd("Diagnosis1 Edited"));
        diagnosis.setAction(SvcConstants.UPDATE);
        svcDto.setDiagnosis(diagnosis);
        svcDto = osb.write(svcDto);
        
        svcDto.setDiagnosis(null);
        pSvcList = osb.get(svcDto);
        assertNull(pSvcList.get(0).getDiagnosis());
    }
}
