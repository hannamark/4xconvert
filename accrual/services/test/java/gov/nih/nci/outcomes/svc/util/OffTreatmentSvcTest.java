package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.OffTreatmentSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.pa.enums.OffTreatmentReasonCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 18, 2010
 *
 */

public class OffTreatmentSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void offTreatmentTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatmentRegimen = new TreatmentRegimenSvcDto();
        OffTreatmentSvcDto offTreatment = new OffTreatmentSvcDto();
        Ts ts = new Ts();
        ts.setValue(new Date());
        offTreatment.setLastTreatmentDate(ts);
        offTreatment.setOffTreatmentReason(CdConverter.convertToCd(OffTreatmentReasonCode.FOLLOWUP_COMPLETED));
        offTreatment.setAction(SvcConstants.CREATE);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setOffTreatment(offTreatment);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTreatmentRegimens().get(0).getOffTreatment().getIdentifier()));
        
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setOffTreatment(new OffTreatmentSvcDto());
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens().get(0).getOffTreatment().getIdentifier().getExtension());
        

        trList = new ArrayList<TreatmentRegimenSvcDto>();
        offTreatment = new OffTreatmentSvcDto();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        offTreatment = pSvcList.get(0).getTreatmentRegimens().get(0).getOffTreatment();
        offTreatment.setOffTreatmentReason(CdConverter.convertToCd(OffTreatmentReasonCode.LOST_TO_FOLLOWUP));
        offTreatment.setAction(SvcConstants.UPDATE);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setOffTreatment(offTreatment);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertNotNull(svcDto.getTreatmentRegimens().get(0).getOffTreatment());
    }
}
