package gov.nih.nci.outcomes.svc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.accrual.util.MockPaInterventionBean;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.outcomes.svc.AbstractOutcomesSvcTest;
import gov.nih.nci.outcomes.svc.dto.CycleSvcDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.SurgerySvcDto;
import gov.nih.nci.outcomes.svc.dto.TreatmentRegimenSvcDto;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @author Kalpana Guthikonda
 * @since Mar 31, 2010
 *
 */

public class SurgerySvcTest extends AbstractOutcomesSvcTest {
    
    @Test
    public void surgeryTest() throws Exception {
        PatientSvcDto svcDto = new PatientSvcDto();
        svcDto.setIdentifier(IiConverter.convertToIi(TestSchema.studySubjects.get(1).getId()));
        
        List<TreatmentRegimenSvcDto> trList = new ArrayList<TreatmentRegimenSvcDto>();
        TreatmentRegimenSvcDto treatmentRegimen = new TreatmentRegimenSvcDto();
        List<CycleSvcDto> cycles = new ArrayList<CycleSvcDto>();
        CycleSvcDto cycle = new CycleSvcDto();
        Ts ts = new Ts();
        ts.setValue(new Date());
        cycle.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(1).getId()));
        
        List<SurgerySvcDto> surgeries = new ArrayList<SurgerySvcDto>();
        SurgerySvcDto sur = new SurgerySvcDto();
        sur.setAction(SvcConstants.CREATE);
        sur.setInterventionId(MockPaInterventionBean.list.get(0).getIdentifier());
        sur.setCreateDate(ts);
        surgeries.add(sur);
        
        cycle.setSurgeries(surgeries);
        cycles.add(cycle);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setCycles(cycles);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);
        assertFalse(PAUtil.isIiNull(svcDto.getTreatmentRegimens().get(0).getCycles().get(0).getSurgeries().get(0).getIdentifier()));
        
        trList = new ArrayList<TreatmentRegimenSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        cycles = new ArrayList<CycleSvcDto>(); 
        cycle = new CycleSvcDto(); 
        cycle.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(1).getId()));
        cycle.setSurgeries(new ArrayList<SurgerySvcDto>());
        cycles.add(cycle);
        treatmentRegimen.setCycles(cycles);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        List<PatientSvcDto> pSvcList = osb.get(svcDto);
        assertNotNull(pSvcList.get(0).getTreatmentRegimens().get(0).getCycles().get(0).getSurgeries().get(0).getIdentifier().getExtension());
        

        trList = new ArrayList<TreatmentRegimenSvcDto>();
        cycles = new ArrayList<CycleSvcDto>();
        treatmentRegimen = new TreatmentRegimenSvcDto();
        surgeries = new ArrayList<SurgerySvcDto>();
        sur = pSvcList.get(0).getTreatmentRegimens().get(0).getCycles().get(0).getSurgeries().get(0);
        sur.setInfo(StConverter.convertToSt("Test"));
        sur.setAction(SvcConstants.UPDATE);
        cycle = new CycleSvcDto();
        cycle.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(1).getId()));
        surgeries.add(sur);        
        cycle.setSurgeries(surgeries);
        cycles.add(cycle);
        treatmentRegimen.setIdentifier(IiConverter.convertToIi(TestSchema.performedActivities.get(0).getId()));
        treatmentRegimen.setCycles(cycles);
        trList.add(treatmentRegimen);
        svcDto.setTreatmentRegimens(trList);
        svcDto = osb.write(svcDto);        
        pSvcList = osb.get(svcDto);
    }
}
