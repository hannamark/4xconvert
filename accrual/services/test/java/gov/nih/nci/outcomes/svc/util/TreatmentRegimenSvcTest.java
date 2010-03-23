package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 17, 2010
 *
 */

public class TreatmentRegimenSvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void treatmentRegimenTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatment = new TreatmentRegimenSvcDto();
        treatment.setName(StConverter.convertToSt("TreatmentRegimen Name"));
        treatment.setDescription(StConverter.convertToSt("TreatmentRegimen Desc"));
        treatment.setAction(SvcConstants.CREATE);        
        trList.add(treatment);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTreatmentRegimens().get(0).getIdentifier()));
        
        svcDto.setTreatmentRegimens(new ArrayList<TreatmentRegimenSvcDto>());
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens().get(0).getIdentifier().getExtension());
        
        treatment = pSvcList.get(0).getTreatmentRegimens().get(0);
        treatment.setName(StConverter.convertToSt("TreatmentRegimen Name Edited"));
        treatment.setAction(SvcConstants.UPDATE);
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        trList.add(treatment);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        
        svcDto.setTreatmentRegimens(trList);
        pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens());
    }
}
